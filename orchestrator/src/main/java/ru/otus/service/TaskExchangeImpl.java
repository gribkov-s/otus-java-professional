package ru.otus.service;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.model.Task;
import ru.otus.service.taskhandler.TaskHandler;

import java.util.Map;
import java.util.concurrent.*;

@Service
public class TaskExchangeImpl implements TaskExchange {
    private static final Logger log = LoggerFactory.getLogger(TaskExchangeImpl.class);

    @Autowired
    private final Map<String, TaskHandler> handlers = new ConcurrentHashMap<>();
    private final ScheduledExecutorService pollingThreadPool = Executors.newSingleThreadScheduledExecutor();

    private final TaskChannel channel;

    @Autowired
    public TaskExchangeImpl(TaskChannel channel) {
        this.channel = channel;
    }

    @Override
    @PostConstruct
    public void run() {
        log.info("TaskExchange is running with handlers: {}", String.join(", ", handlers.keySet()));
        pollingThreadPool.scheduleAtFixedRate(
                this::exchange, 0, 100, TimeUnit.MILLISECONDS);
    }

    private void exchange() {
        Task task = channel.take();
        String handlerId = task.getTaskTypeTitle();
        TaskHandler handler = handlers.get(handlerId);
        if (handler != null) {
            handler.handle(task);
            log.info("Task: {} was sent to handler: {}", task.getId(), handlerId);
        } else {
            log.warn("Task: {} was sent to handler: {}, which doesn't exist", task.getId(), handlerId);
        }
    }
}
