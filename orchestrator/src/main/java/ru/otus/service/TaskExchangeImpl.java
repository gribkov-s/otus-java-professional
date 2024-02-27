package ru.otus.service;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.otus.model.Task;
import ru.otus.service.taskchannel.TaskChannel;
import ru.otus.service.taskhandler.TaskHandler;

import java.util.Map;
import java.util.concurrent.*;

@Service
public class TaskExchangeImpl implements TaskExchange {
    private static final Logger log = LoggerFactory.getLogger(TaskExchangeImpl.class);

    @Autowired
    private final Map<String, TaskHandler<?>> handlers;

    private final TaskChannel inputTaskChannel;

    @Autowired
    public TaskExchangeImpl(TaskChannel inputTaskChannel) {
        this.inputTaskChannel = inputTaskChannel;
        this.handlers = new ConcurrentHashMap<>();
    }

    @Override
    @PostConstruct
    public void run() {
        log.info("TaskExchange is running with handlers: {}",
                String.join(", ", handlers.keySet()));
        exchange();
    }

    @Scheduled(fixedDelay=100)
    private void exchange() {
        Task task = inputTaskChannel.take();
        if (task != null) {
            String taskId = task.getId();
            String handlerId = task.getTaskTypeTitle();
            TaskHandler<?> handler = handlers.get(handlerId);
            if (handler != null) {
                handler.handle(task);
                log.info("Task: {} was sent to handler: {}", taskId, handlerId);
            } else {
                log.warn("Task: {} was sent to handler: {}, which doesn't exist", taskId, handlerId);
            }
        }
    }
}
