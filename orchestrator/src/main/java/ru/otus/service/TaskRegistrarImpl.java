package ru.otus.service;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.otus.model.Task;
import ru.otus.service.taskchannel.TaskChannel;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TaskRegistrarImpl implements TaskRegistrar {
    private static final Logger log = LoggerFactory.getLogger(TaskExchangeImpl.class);

    private final Map<String, Long> tasks = new ConcurrentHashMap<>();

    private final TaskChannel nextTaskChannel;

    @Autowired
    public TaskRegistrarImpl(TaskChannel nextTaskChannel) {
        this.nextTaskChannel = nextTaskChannel;
    }

    @Override
    public void register(Task task) {
        tasks.putIfAbsent(task.getId(), 0L);
        log.info("Task: {} was registered", task.getId());
    }

    @Override
    public void unregister(String taskId) {
        Long result = tasks.remove(taskId);
        if (result == null) {
            log.info("Task: {} isn't registered", taskId);
        } else {
            log.info("Task: {} was unregistered", taskId);
        }
    }

    @Override
    public Long getCounter(String taskId) {
        return Optional.ofNullable(tasks.get(taskId)).orElseThrow(() ->
                        new RuntimeException(String.format("Task %s isn't registered", taskId)));
    }

    @Override
    @PostConstruct
    public void run() {
        log.info("TaskRegistrar is running");
        check();
    }

    @Scheduled(fixedDelay=100)
    private void check() {
        Task task = nextTaskChannel.take();
        if (task != null) {
            String taskId = task.getId();
            Long result = tasks.computeIfPresent(taskId, (k, v) -> v + 1);
            if (result != null) {
                log.info("Task {} was sent to {} cycle", taskId, result);
            } else {
                log.info("Task {} was cancelled", taskId);
            }
        }
    }
}
