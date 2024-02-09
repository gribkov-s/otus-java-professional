package ru.otus.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.dao.TaskDao;
import ru.otus.model.Task;
import ru.otus.service.TaskReceiver;

@RestController
@RequestMapping("/tasks/running")
public class TasksRunningRestController {

    private final TaskDao taskDao;
    private final TaskReceiver taskReceiver;

    public TasksRunningRestController(TaskDao taskDao,
                                      TaskReceiver taskReceiver) {
        this.taskDao = taskDao;
        this.taskReceiver = taskReceiver;
    }

    @PostMapping
    public ResponseEntity<String> runtTask(Model model, @RequestBody String taskId) {
        Task task = taskDao.findById(taskId);
        taskReceiver.onReceive(task);
        return ResponseEntity.ok(task.getId());
    }

}
