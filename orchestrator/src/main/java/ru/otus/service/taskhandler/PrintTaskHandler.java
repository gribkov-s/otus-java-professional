package ru.otus.service.taskhandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.otus.model.Task;
import ru.otus.service.taskchannel.TaskChannel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class PrintTaskHandler extends TaskHandler<Void> {
    private static final Logger log = LoggerFactory.getLogger(PrintTaskHandler.class);

    ExecutorService handleThreadPool =  Executors.newFixedThreadPool(1);

    private final TaskChannel handledTaskChannel;

    @Autowired
    public PrintTaskHandler(TaskChannel handledTaskChannel) {
        this.handledTaskChannel = handledTaskChannel;
    }

    @Override
    public void handle(Task task) {
        handleThreadPool.submit(() -> print(task));
    }

    private void print(Task task) {
        log.info("{} Handled task: {} by handler: {}",
                System.currentTimeMillis() / 1000,
                task.getId(),
                this.getClass().getSimpleName());
        handledTaskChannel.push(task);
    }
}
