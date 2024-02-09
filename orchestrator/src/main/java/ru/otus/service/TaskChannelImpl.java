package ru.otus.service;

import org.springframework.stereotype.Service;
import ru.otus.model.Task;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class TaskChannelImpl implements TaskChannel {

    private Queue<Task> taskQueue;

    public TaskChannelImpl() {
        this.taskQueue = new ConcurrentLinkedQueue<>();
    }

    @Override
    public boolean push(Task task) {
        return taskQueue.add(task);
    }

    @Override
    public Task take() {
        return taskQueue.poll();
    }
}
