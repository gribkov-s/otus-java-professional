package ru.otus.dao;

import ru.otus.model.Message;
import ru.otus.model.MessageContent;

import java.util.List;
import java.util.Optional;

public interface MessageDao {
    Message findById(String id);
    Message save(Message message);
    Message updateContent(MessageContent messageContent);
    String delete(String id);
}
