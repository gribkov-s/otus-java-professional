package ru.otus.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.dao.ConnectionDao;
import ru.otus.dto.ConnectionContentDto;
import ru.otus.dto.ConnectionDto;
import ru.otus.mapper.ConnectionContentMapper;
import ru.otus.mapper.ConnectionMapper;
import ru.otus.model.Connection;
import ru.otus.model.ConnectionContent;

@RestController
@RequestMapping("/connections")
public class ConnectionRestController {

    private final ConnectionDao connectionDao;
    private final ConnectionMapper connectionMapper;
    private final ConnectionContentMapper connectionContentMapper;

    public ConnectionRestController(ConnectionDao connectionDao,
                                    ConnectionMapper connectionMapper,
                                    ConnectionContentMapper connectionContentMapper) {
        this.connectionDao = connectionDao;
        this.connectionMapper = connectionMapper;
        this.connectionContentMapper = connectionContentMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConnectionDto> getConnection(@PathVariable String id) {
        Connection connection = connectionDao.findById(id);
        return ResponseEntity.ok(
                connectionMapper.toDto(connection));
    }

    @PostMapping
    public ResponseEntity<ConnectionDto> saveConnection(Model model,
                                                        @RequestBody ConnectionDto connectionDto) {
        Connection connection = connectionMapper.toModel(connectionDto);
        Connection savedConnection = connectionDao.save(connection);
        return ResponseEntity.ok(
                connectionMapper.toDto(savedConnection));
    }

    @PatchMapping
    public ResponseEntity<ConnectionDto> updateConnection(
            Model model,
            @RequestBody ConnectionContentDto connectionContentDto) {
        ConnectionContent connectionContent = connectionContentMapper.toModel(connectionContentDto);
        Connection updatedConnection = connectionDao.updateContent(connectionContent);
        return ResponseEntity.ok(
                connectionMapper.toDto(updatedConnection));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteConnection(@PathVariable String id) {
        return ResponseEntity.ok(
                connectionDao.delete(id));
    }
}
