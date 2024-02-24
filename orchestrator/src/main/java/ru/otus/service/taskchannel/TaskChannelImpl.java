package ru.otus.service.taskchannel;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.model.Task;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@AllArgsConstructor
public class TaskChannelImpl implements TaskChannel {
    private static final Logger log = LoggerFactory.getLogger(TaskChannelImpl.class);

    private final String name;

    private final Queue<Task> taskQueue = new ConcurrentLinkedQueue<>();

    @Override
    public boolean push(Task task) {
        boolean isPushed = taskQueue.add(task);
        if (isPushed) {
            log.info("Task {} was pushed to {}.", task.getId(), this.name);
        }
        return isPushed;
    }

    @Override
    public Task take() {
        Task task = taskQueue.poll();
        if (task != null) {
            log.info("Task {} was taken from {}.", task.getId(), this.name);
        }
        return task;
    }
}
