package ru.otus.service;

import ru.otus.model.Task;

import java.util.concurrent.TimeUnit;

public interface TaskChannel {
    boolean push(Task task);
    Task take();
}
