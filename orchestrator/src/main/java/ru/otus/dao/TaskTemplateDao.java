package ru.otus.dao;

import ru.otus.model.TaskTemplate;

import java.util.List;

public interface TaskTemplateDao {
    List<String> findAllIds();
    List<String> getAllTaskTypes();
    TaskTemplate findById(String id);
    TaskTemplate save(TaskTemplate taskTemplate);
    String delete(String id);
}
