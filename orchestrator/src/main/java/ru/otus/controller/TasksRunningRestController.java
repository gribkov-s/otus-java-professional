package ru.otus.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.dao.TaskDao;
import ru.otus.dto.TaskTemplateDto;
import ru.otus.model.Task;
import ru.otus.model.TaskTemplate;
import ru.otus.service.TaskReceiver;
import ru.otus.service.TaskRegistrar;

@RestController
@RequestMapping("/tasks/running")
public class TasksRunningRestController {

    private final TaskDao taskDao;
    private final TaskReceiver taskReceiver;
    private final TaskRegistrar taskRegistrar;

    public TasksRunningRestController(TaskDao taskDao,
                                      TaskReceiver taskReceiver,
                                      TaskRegistrar taskRegistrar) {
        this.taskDao = taskDao;
        this.taskReceiver = taskReceiver;
        this.taskRegistrar = taskRegistrar;
    }

    @PostMapping
    public ResponseEntity<String> runTask(Model model, @RequestBody String id) {
        Task task = taskDao.findById(id);
        taskReceiver.onReceive(task);
        taskRegistrar.register(task);
        return ResponseEntity.ok(task.getId());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Long> getTaskCounter(@PathVariable String id) {
        Long counter = taskRegistrar.getCounter(id);
        return ResponseEntity.ok(counter);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> stopTask(Model model, @PathVariable String id) {
        taskRegistrar.unregister(id);
        return ResponseEntity.ok(id);
    }

}
