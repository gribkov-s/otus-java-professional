package ru.otus.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.dao.MessageDao;
import ru.otus.dao.TaskDao;
import ru.otus.dto.*;
import ru.otus.mapper.*;
import ru.otus.model.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskRestController {

    private final TaskDao taskDao;
    private final TaskMapper taskMapper;
    private final TaskRangeMapper taskRangeMapper;
    private final TaskNextMapper taskNextMapper;

    public TaskRestController(TaskDao taskDao,
                              TaskMapper taskMapper,
                              TaskRangeMapper taskRangeMapper,
                              TaskNextMapper taskNextMapper) {
        this.taskDao = taskDao;
        this.taskMapper = taskMapper;
        this.taskRangeMapper = taskRangeMapper;
        this.taskNextMapper = taskNextMapper;
    }

    @GetMapping("/ids")
    public ResponseEntity<List<String>> getTaskIds() {
        return ResponseEntity.ok(
                taskDao.findAllIds());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> getTask(@PathVariable String id) {
        Task task = taskDao.findById(id);
        return ResponseEntity.ok(
                taskMapper.toDto(task));
    }

    @PostMapping
    public ResponseEntity<TaskDto> saveTask(Model model, @RequestBody TaskDto taskDto) {
        Task task = taskMapper.toModel(taskDto);
        Task savedTask = taskDao.save(task);
        return ResponseEntity.ok(
                taskMapper.toDto(savedTask));
    }

    @PatchMapping("/update-range")
    public ResponseEntity<TaskDto> updateRange(Model model,
                                               @RequestBody TaskRangeDto taskRangeDto) {
        TaskRange taskRange = taskRangeMapper.toModel(taskRangeDto);
        Task updatedTask = taskDao.updateRange(taskRange);
        return ResponseEntity.ok(
                taskMapper.toDto(updatedTask));
    }

    @PatchMapping("/update-next")
    public ResponseEntity<TaskDto> updateNext(Model model,
                                              @RequestBody TaskNextDto taskNextDto) {
        TaskNext taskNext = taskNextMapper.toModel(taskNextDto);
        Task updatedTask = taskDao.updateNext(taskNext);
        return ResponseEntity.ok(
                taskMapper.toDto(updatedTask));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable String id) {
        return ResponseEntity.ok(
                taskDao.delete(id));
    }
}
