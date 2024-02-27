package ru.otus.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.dao.MessageTemplateDao;
import ru.otus.dto.MessageTemplateDto;
import ru.otus.mapper.MessageTemplateMapper;
import ru.otus.model.MessageTemplate;

import java.util.List;

@RestController
@RequestMapping("/message-templates")
public class MessageTemplateRestController {

    private final MessageTemplateDao messageTemplateDao;
    private final MessageTemplateMapper messageTemplateMapper;

    public MessageTemplateRestController(MessageTemplateDao messageTemplateDao,
                                         MessageTemplateMapper messageTemplateMapper) {
        this.messageTemplateDao = messageTemplateDao;
        this.messageTemplateMapper = messageTemplateMapper;
    }

    @GetMapping("/ids")
    public ResponseEntity<List<String>> getMessageTemplatesIds() {
        return ResponseEntity.ok(
                messageTemplateDao.findAllIds());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageTemplateDto> getMessageTemplate(@PathVariable String id) {
        MessageTemplate messageTemplate = messageTemplateDao.findById(id);
        return ResponseEntity.ok(
                messageTemplateMapper.toDto(messageTemplate));
    }

    @PostMapping
    public ResponseEntity<MessageTemplateDto> saveMessageTemplate(Model model,
                                                  @RequestBody MessageTemplateDto messageTemplateDto) {
        MessageTemplate messageTemplate = messageTemplateMapper.toModel(messageTemplateDto);
        MessageTemplate savedMessageTemplate = messageTemplateDao.save(messageTemplate);
        return ResponseEntity.ok(
                messageTemplateMapper.toDto(savedMessageTemplate));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMessageTemplate(@PathVariable String id) {
        return ResponseEntity.ok(
                messageTemplateDao.delete(id));
    }
}
