package ru.otus.service.taskchannel;

import ru.otus.model.Task;

public interface TaskChannel {
    boolean push(Task task);
    Task take();
}
