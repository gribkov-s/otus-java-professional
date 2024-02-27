package ru.otus.service.taskhandler.interpreter;

import ru.otus.model.Task;

public interface TaskInterpreter<T> {
    T interpret(Task task);
}
