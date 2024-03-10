package ru.otus.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.util.HtmlUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.domain.Message;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentHashMap;

@Controller
public class MessageController {
    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    private static final String TOPIC_TEMPLATE = "/topic/response.";
    private static final String SPECIAL_ROOM_ID = "1408";

    private final WebClient datastoreClient;
    private final SimpMessagingTemplate template;
    private final ConcurrentHashMap<String, List<Queue<Message>>> messageBuffers;
    private final ConcurrentHashMap<String, List<String>> destinations;

    public MessageController(WebClient datastoreClient, SimpMessagingTemplate template) {
        this.datastoreClient = datastoreClient;
        this.template = template;
        this.messageBuffers = new ConcurrentHashMap<>();
        this.destinations = new ConcurrentHashMap<>();
    }

    @MessageMapping("/message.{roomId}")
    public void getMessage(@DestinationVariable String roomId, Message message) {
        if (!roomId.equals(SPECIAL_ROOM_ID)) {
            logger.info("get message:{}, roomId:{}", message, roomId);

            saveMessage(roomId, message).subscribe(msgId -> logger.info("message send id:{}", msgId));

            Message msg = new Message(HtmlUtils.htmlEscape(message.messageStr()));

            destinations.computeIfPresent(roomId, (r, ds) -> {
                ds.forEach(destination -> template.convertAndSend(destination, msg));
                return ds;
            });

            destinations.computeIfPresent(SPECIAL_ROOM_ID, (r, ds) -> {
                ds.forEach(destination -> template.convertAndSend(destination, msg));
                return ds;
            });

            messageBuffers.computeIfPresent(roomId, (r, bs) -> {
                bs.forEach(b -> b.offer(msg));
                return bs;
            });

            messageBuffers.computeIfPresent(SPECIAL_ROOM_ID, (r, bs) -> {
                bs.forEach(b -> b.offer(msg));
                return bs;
            });
        } else {
            logger.warn("Can not send messages from roomId:{}", SPECIAL_ROOM_ID);
        }
    }

    @EventListener
    public void handleSessionSubscribeEvent(SessionSubscribeEvent event) {
        var genericMessage = (GenericMessage<byte[]>) event.getMessage();
        var simpDestination = (String) genericMessage.getHeaders().get("simpDestination");

        if (simpDestination == null) {
            logger.error("Can not get simpDestination header, headers:{}", genericMessage.getHeaders());
            throw new ChatException("Can not get simpDestination header");
        }
        if (!simpDestination.startsWith(template.getUserDestinationPrefix())) {
            return;
        }
        var roomId = parseRoomId(simpDestination);
        var roomIdStr = String.valueOf(roomId);
        Queue<Message> buffer = new ConcurrentLinkedQueue<>();
        addBuffer(roomIdStr, buffer);
        logger.info("subscription for:{}, roomId:{}", simpDestination, roomId);
        /*
        /user/3c3416b8-9b24-4c75-b38f-7c96953381d1/topic/response.1
         */

        getMessagesByRoomId(roomId)
                .concatWith(Flux.fromStream(buffer.stream()))
                .doOnError(ex -> logger.error("getting messages for roomId:{} failed", roomId, ex))
                .doFinally(s -> addDestination(roomIdStr, simpDestination))
                .subscribe(message -> template.convertAndSend(simpDestination, message));
    }

    private long parseRoomId(String simpDestination) {
        try {
            var idxRoom = simpDestination.lastIndexOf(TOPIC_TEMPLATE);
            return Long.parseLong(simpDestination.substring(idxRoom).replace(TOPIC_TEMPLATE, ""));
        } catch (Exception ex) {
            logger.error("Can not get roomId", ex);
            throw new ChatException("Can not get roomId");
        }
    }

    private Mono<Long> saveMessage(String roomId, Message message) {
        return datastoreClient
                .post()
                .uri(String.format("/msg/%s", roomId))
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(message)
                .exchangeToMono(response -> response.bodyToMono(Long.class));
    }

    private Flux<Message> getMessagesByRoomId(long roomId) {
        String uri = (roomId == Long.parseLong(SPECIAL_ROOM_ID)) ? "/msg/all" : String.format("/msg/%s", roomId);
        return datastoreClient
                .get()
                .uri(uri)
                .accept(MediaType.APPLICATION_NDJSON)
                .exchangeToFlux(response -> {
                    if (response.statusCode().equals(HttpStatus.OK)) {
                        return response.bodyToFlux(Message.class);
                    } else {
                        return response.createException().flatMapMany(Mono::error);
                    }
                });
    }

    private void addDestination(String roomId, String destination) {
        if (destinations.containsKey(roomId)) {
            destinations.computeIfPresent(roomId, (k, v) -> {
                v.add(destination);
                return v;
            });
        } else {
            destinations.computeIfAbsent(roomId, k -> {
                List<String> roomDestinations = new ArrayList<>();
                roomDestinations.add(destination);
                return roomDestinations;
            });
        }
    }

    private void addBuffer(String roomId, Queue<Message> buffer) {
        if (messageBuffers.containsKey(roomId)) {
            messageBuffers.computeIfPresent(roomId, (k, v) -> {
                Collections.synchronizedList(v).add(buffer);
                return v;
            });
        } else {
            messageBuffers.computeIfAbsent(roomId, k -> {
                List<Queue<Message>> roomBuffers = new ArrayList<>();
                Collections.synchronizedList(roomBuffers).add(buffer);
                return roomBuffers;
            });
        }
    }
}
