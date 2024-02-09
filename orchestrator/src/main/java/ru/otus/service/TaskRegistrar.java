package ru.otus.service;

import ru.otus.model.Task;

public interface TaskRegistrar {
    void register(Task task);
    void unregister(String taskId);
    Long getCounter(String taskId);
    void run();
}
