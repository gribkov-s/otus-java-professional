package ru.otus.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.model.Task;
import ru.otus.service.taskchannel.TaskChannel;

@Service
public class TaskReceiverImpl implements TaskReceiver {
    private static final Logger log = LoggerFactory.getLogger(TaskReceiverImpl.class);

    private final TaskChannel inputTaskChannel;

    @Autowired
    public TaskReceiverImpl(TaskChannel inputTaskChannel) {
        this.inputTaskChannel = inputTaskChannel;
    }

    @Override
    public void onReceive(Task task) {
        inputTaskChannel.push(task);
        log.info("Received task: {}", task.getId());
    }
}
