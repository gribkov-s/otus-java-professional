package ru.otus.service;

import ru.otus.model.Task;

public interface TaskReceiver {
    void onReceive(Task task);
}
