package ru.otus.service.taskhandler;

import org.asynchttpclient.AsyncCompletionHandler;
import org.asynchttpclient.BoundRequestBuilder;
import org.asynchttpclient.ListenableFuture;
import org.asynchttpclient.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.otus.model.Task;
import ru.otus.service.taskchannel.TaskChannel;
import ru.otus.service.taskhandler.interpreter.TaskInterpreter;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class HttpPostTaskHandler extends TaskHandler<BoundRequestBuilder> {
    private static final Logger log = LoggerFactory.getLogger(HttpPostTaskHandler.class);

    private final ExecutorService handleThreadPool =  Executors.newFixedThreadPool(3);
    private final ExecutorService sendThreadPool =  Executors.newFixedThreadPool(1);

    private final TaskChannel handledTaskChannel;

    @Autowired
    public HttpPostTaskHandler(TaskChannel handledTaskChannel,
                               @Qualifier("httpPostTaskInterpreter")
                                       TaskInterpreter<BoundRequestBuilder> interpreter) {
        this.handledTaskChannel = handledTaskChannel;
        this.interpreter = interpreter;
    }

    @Override
    public void handle(Task task) {
        BoundRequestBuilder postRequest = interpreter.interpret(task);
        ListenableFuture<String> responseFuture = postRequest.execute(new AsyncHttpResponseHandler());

        responseFuture.addListener(
                () -> {
                    try {
                        String response = responseFuture.get();
                        log.info("Handled task: {} by handler: {}. Result: {}",
                                task.getId(),
                                this.getClass().getSimpleName(),
                                response);
                    } catch (InterruptedException | ExecutionException e) {
                        log.warn("Failed task: {} by handler: {}\n{}",
                                task.getId(),
                                this.getClass().getSimpleName(),
                                e.getMessage());
                    }
                },
                handleThreadPool);

        sendThreadPool.submit(() -> handledTaskChannel.push(task));
    }

    private static class AsyncHttpResponseHandler extends AsyncCompletionHandler<String> {
        @Override
        public String onCompleted(Response response) throws Exception {
            return response.getResponseBody();
        }
    }
}
