package ru.otus.service.taskchannel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.otus.model.Task;
import ru.otus.service.taskhandler.HttpGetTaskHandler;

import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
@Qualifier("handledTaskChannel")
public class HandledTaskChannel implements TaskChannel {
    private static final Logger log = LoggerFactory.getLogger(HandledTaskChannel.class);

    private final Queue<Task> taskQueue = new ConcurrentLinkedQueue<>();

    @Override
    public boolean push(Task task) {
        return taskQueue.offer(task);
    }

    @Override
    public Task take() {
        return taskQueue.poll();
    }
}
