package ru.otus.service.taskhandler;

import ru.otus.model.Task;
import ru.otus.service.taskhandler.interpreter.TaskInterpreter;

public abstract class TaskHandler<T> {

    TaskInterpreter<T> interpreter;

    public abstract void handle(Task task);
}
