package ru.otus.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.dao.TaskTemplateDao;
import ru.otus.dto.TaskTemplateDto;
import ru.otus.mapper.TaskTemplateMapper;
import ru.otus.model.TaskTemplate;
import java.util.List;

@RestController
@RequestMapping("/task-templates")
public class TaskTemplateRestController {

    private final TaskTemplateDao taskTemplateDao;
    private final TaskTemplateMapper taskTemplateMapper;

    public TaskTemplateRestController(TaskTemplateDao taskTemplateDao,
                                      TaskTemplateMapper taskTemplateMapper) {
        this.taskTemplateDao = taskTemplateDao;
        this.taskTemplateMapper = taskTemplateMapper;
    }

    @GetMapping("/ids")
    public ResponseEntity<List<String>> getTaskTemplatesIds() {
        return ResponseEntity.ok(
                taskTemplateDao.findAllIds());
    }

    @GetMapping("/task-types")
    public ResponseEntity<List<String>> getTaskTypes() {
        return ResponseEntity.ok(
                taskTemplateDao.getAllTaskTypes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskTemplateDto> getTaskTemplate(@PathVariable String id) {
        TaskTemplate taskTemplate = taskTemplateDao.findById(id);
        return ResponseEntity.ok(
                taskTemplateMapper.toDto(taskTemplate));
    }

    @PostMapping
    public ResponseEntity<TaskTemplateDto> saveTaskTemplate(
            Model model,
            @RequestBody TaskTemplateDto taskTemplateDto) {
        TaskTemplate taskTemplate = taskTemplateMapper.toModel(taskTemplateDto);
        TaskTemplate savedTaskTemplate = taskTemplateDao.save(taskTemplate);
        return ResponseEntity.ok(
                taskTemplateMapper.toDto(savedTaskTemplate));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTaskTemplate(@PathVariable String id) {
        return ResponseEntity.ok(
                taskTemplateDao.delete(id));
    }
}
