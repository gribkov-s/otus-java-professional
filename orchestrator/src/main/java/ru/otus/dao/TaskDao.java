package ru.otus.dao;

import ru.otus.model.Task;
import ru.otus.model.TaskNext;
import ru.otus.model.TaskRange;

import java.util.List;

public interface TaskDao {
    List<String> findAllIds();
    Task findById(String id);
    Task save(Task task);
    Task updateRange(TaskRange taskRange);
    Task updateNext(TaskNext next);
    String delete(String id);
}
