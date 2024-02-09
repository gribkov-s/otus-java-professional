package ru.otus.service.taskhandler;

import ru.otus.model.Task;

public interface TaskHandler {
    void handle(Task task);
}
