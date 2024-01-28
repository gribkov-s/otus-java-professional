package ru.otus.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.dao.MessageDao;
import ru.otus.dto.MessageDto;
import ru.otus.mapper.MessageMapper;
import ru.otus.model.Message;

@RestController
@RequestMapping("/messages")
public class MessageRestController {

    private final MessageDao messageDao;
    private final MessageMapper messageMapper;

    public MessageRestController(MessageDao messageDao,
                                 MessageMapper messageMapper) {
        this.messageDao = messageDao;
        this.messageMapper = messageMapper;
    }

    @GetMapping("/{id}")
    public MessageDto getMessage(@PathVariable String id) {
        Message message = messageDao.findById(id);
        return messageMapper.toDto(message);
    }

    @PostMapping
    public MessageDto saveMessage(Model model,
                                  @RequestBody MessageDto messageDto) {
        Message message = messageMapper.toModel(messageDto);
        Message savedMessage = messageDao.save(message);
        return messageMapper.toDto(savedMessage);
    }

    @PutMapping
    public MessageDto updateMessage(Model model,
                                    @RequestBody MessageDto messageDto) {
        Message message = messageMapper.toModel(messageDto);
        Message updatedMessage = messageDao.update(message);
        return messageMapper.toDto(updatedMessage);
    }

    @DeleteMapping("/{id}")
    public String deleteMessage(@PathVariable String id) {
        return messageDao.delete(id);
    }
}
