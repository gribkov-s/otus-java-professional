package ru.otus.service.taskchannel;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.otus.model.Task;

import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
@Qualifier("handledTaskChannel")
public class HandledTaskChannel implements TaskChannel {

    private final Queue<Task> taskQueue = new ConcurrentLinkedQueue<>();

    @Override
    public boolean push(Task task) {
        /*Optional<Boolean> pushResultOpt = task.getNextOpt().map(taskQueue::offer);
        return pushResultOpt.orElse(false);*/
        return taskQueue.offer(task);
    }

    @Override
    public Task take() {
        return taskQueue.poll();
    }
}
