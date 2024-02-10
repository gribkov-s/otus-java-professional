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
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class TaskRegistrarImpl implements TaskRegistrar {
    private static final Logger log = LoggerFactory.getLogger(TaskRegistrarImpl.class);

    private final Map<String, Long> tasks = new ConcurrentHashMap<>();
    private final Queue<Task> toNextQueue = new ConcurrentLinkedQueue<>();

    private final TaskChannel handledTaskChannel;
    private final TaskChannel nextTaskChannel;

    @Autowired
    public TaskRegistrarImpl(TaskChannel handledTaskChannel,
                             TaskChannel nextTaskChannel) {
        this.handledTaskChannel = handledTaskChannel;
        this.nextTaskChannel = nextTaskChannel;
    }

    @Override
    public void register(Task task) {
        tasks.putIfAbsent(task.getId(), 1L);
        log.info("Task: {} was registered", task.getId());
    }

    @Override
    public void unregister(String taskId) {
        Long result = tasks.remove(taskId);
        if (result == null) {
            throw new RuntimeException(String.format("Task %s isn't registered", taskId));
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
        checkHandled();
        sendNext();
    }

    @Scheduled(fixedDelay=100)
    private void checkHandled() {
        Task task = handledTaskChannel.take();
        if (task != null) {
            String taskId = task.getId();
            if (!task.isRepetitive()) {
                tasks.remove(taskId);
                log.info("Task {} was finished", taskId);
                task.getNextOpt().ifPresent(toNextQueue::offer);
            } else {
                if (tasks.containsKey(taskId)) {
                    task.getNextOpt().ifPresent(toNextQueue::offer);
                } else {
                    log.info("Task {} was stopped", taskId);
                }
            }
        }
    }

    @Scheduled(fixedDelay=100)
    private void sendNext() {
        Task task = toNextQueue.poll();
        if (task != null) {
            String taskId = task.getId();
            Long result = tasks.compute(taskId, (k, v) -> v == null ? 1 : v + 1);
            nextTaskChannel.push(task);
            if (result == 1) {
                log.info("Next task {} was sent", task.getId());
            } else {
                log.info("Task {} was sent to {} cycle", taskId, result);
            }
        }
    }
}
