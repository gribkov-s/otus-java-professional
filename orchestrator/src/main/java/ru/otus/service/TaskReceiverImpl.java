package ru.otus.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.model.Task;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class TaskReceiverImpl implements TaskReceiver {
    private static final Logger log = LoggerFactory.getLogger(TaskReceiverImpl.class);

    private final TaskChannel channel;

    @Autowired
    public TaskReceiverImpl(TaskChannel channel) {
        this.channel = channel;
    }

    @Override
    public void onReceive(Task task) {
        channel.push(task);
        log.info("Received task: {}", task.getId());
    }
}
