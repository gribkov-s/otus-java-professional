package ru.otus.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.dao.MessageDao;
import ru.otus.dto.MessageContentDto;
import ru.otus.dto.MessageDto;
import ru.otus.mapper.MessageContentMapper;
import ru.otus.mapper.MessageMapper;
import ru.otus.model.Message;
import ru.otus.model.MessageContent;

@RestController
@RequestMapping("/messages")
public class MessageRestController {

    private final MessageDao messageDao;
    private final MessageMapper messageMapper;
    private final MessageContentMapper messageContentMapper;

    public MessageRestController(MessageDao messageDao,
                                 MessageMapper messageMapper,
                                 MessageContentMapper messageContentMapper) {
        this.messageDao = messageDao;
        this.messageMapper = messageMapper;
        this.messageContentMapper = messageContentMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageDto> getMessage(@PathVariable String id) {
        Message message = messageDao.findById(id);
        return ResponseEntity.ok(
                messageMapper.toDto(message));
    }

    @PostMapping
    public ResponseEntity<MessageDto> saveMessage(Model model,
                                  @RequestBody MessageDto messageDto) {
        Message message = messageMapper.toModel(messageDto);
        Message savedMessage = messageDao.save(message);
        return ResponseEntity.ok(
                messageMapper.toDto(savedMessage));
    }

    @PatchMapping
    public ResponseEntity<MessageDto> updateMessage(Model model,
                                                    @RequestBody MessageContentDto messageContentDto) {
        MessageContent messageContent = messageContentMapper.toModel(messageContentDto);
        Message updatedMessage = messageDao.updateContent(messageContent);
        return ResponseEntity.ok(
                messageMapper.toDto(updatedMessage));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMessage(@PathVariable String id) {
        return ResponseEntity.ok(
                messageDao.delete(id));
    }
}
