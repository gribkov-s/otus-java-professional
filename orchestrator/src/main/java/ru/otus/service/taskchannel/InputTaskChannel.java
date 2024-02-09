package ru.otus.service.taskchannel;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.otus.model.Task;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
@Qualifier("inputTaskChannel")
public class InputTaskChannel implements TaskChannel {

    private final Queue<Task> taskQueue = new ConcurrentLinkedQueue<>();

    @Override
    public boolean push(Task task) {
        return taskQueue.add(task);
    }

    @Override
    public Task take() {
        return taskQueue.poll();
    }
}
