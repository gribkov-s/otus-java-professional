package ru.otus.service;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.otus.model.Task;
import ru.otus.service.taskchannel.TaskChannel;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

@Service
public class TaskSchedulerImpl implements TaskScheduler {
    private static final Logger log = LoggerFactory.getLogger(TaskSchedulerImpl.class);

    private final TaskChannel nextTaskChannel;
    private final TaskChannel inputTaskChannel;

    @Autowired
    public TaskSchedulerImpl(TaskChannel nextTaskChannel,
                             TaskChannel inputTaskChannel) {
        this.nextTaskChannel = nextTaskChannel;
        this.inputTaskChannel = inputTaskChannel;
    }

    @Override
    @PostConstruct
    public void run() {
        log.info("TaskScheduler is running");
        distribute();
    }

    @Scheduled(fixedDelay=100)
    private void distribute() {
        Task task = nextTaskChannel.take();
        if (task != null) {
            if (task.isRepetitive()) {
                Long delay = task.getRangeSec();
                Executor executor = CompletableFuture.delayedExecutor(delay, TimeUnit.SECONDS);
                executor.execute(() -> {
                    inputTaskChannel.push(task);
                    log.info("Task {} was sent to exchange after waiting for {} sec",
                            task.getId(), delay);
                });
                log.info("Task {} was delayed for {} sec", task.getId(), delay);
            } else {
                inputTaskChannel.push(task);
                log.info("Task {} was sent to exchange without waiting", task.getId());
            }
        }
    }
}
