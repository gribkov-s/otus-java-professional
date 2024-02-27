package ru.otus.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.dao.ConnectionTemplateDao;
import ru.otus.dto.ConnectionTemplateDto;
import ru.otus.mapper.ConnectionTemplateMapper;
import ru.otus.model.ConnectionTemplate;
import java.util.List;

@RestController
@RequestMapping("/connection-templates")
public class ConnectionTemplateRestController {

    private final ConnectionTemplateDao connectionTemplateDao;
    private final ConnectionTemplateMapper connectionTemplateMapper;

    public ConnectionTemplateRestController(ConnectionTemplateDao connectionTemplateDao,
                                            ConnectionTemplateMapper connectionTemplateMapper) {
        this.connectionTemplateDao = connectionTemplateDao;
        this.connectionTemplateMapper = connectionTemplateMapper;
    }

    @GetMapping("/ids")
    public ResponseEntity<List<String>> getConnectionTemplatesIds() {
        return ResponseEntity.ok(
                connectionTemplateDao.findAllIds());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConnectionTemplateDto> getConnectionTemplate(@PathVariable String id) {
        ConnectionTemplate connectionTemplate = connectionTemplateDao.findById(id);
        return ResponseEntity.ok(
                connectionTemplateMapper.toDto(connectionTemplate));
    }

    @PostMapping
    public ResponseEntity<ConnectionTemplateDto> saveConnectionTemplate(
            Model model,
            @RequestBody ConnectionTemplateDto connectionTemplateDto) {
        ConnectionTemplate connectionTemplate = connectionTemplateMapper.toModel(connectionTemplateDto);
        ConnectionTemplate savedConnectionTemplate = connectionTemplateDao.save(connectionTemplate);
        return ResponseEntity.ok(
                connectionTemplateMapper.toDto(savedConnectionTemplate));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteConnectionTemplate(@PathVariable String id) {
        return ResponseEntity.ok(
                connectionTemplateDao.delete(id));
    }
}
