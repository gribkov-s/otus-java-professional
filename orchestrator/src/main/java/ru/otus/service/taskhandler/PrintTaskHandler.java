package ru.otus.service.taskhandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.otus.model.Task;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class PrintTaskHandler implements TaskHandler {
    private static final Logger log = LoggerFactory.getLogger(PrintTaskHandler.class);

    ExecutorService handleThreadPool =  Executors.newCachedThreadPool();

    @Override
    public Task handle(Task task) {
        handleThreadPool.submit(() ->
            log.info("Handled task: {} by handler: {}", task.getId(), this.getClass().getSimpleName()));

        return task.updateTimestamp();
    }
}
