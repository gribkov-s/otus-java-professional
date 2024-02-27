package ru.otus.dao;

import ru.otus.model.MessageTemplate;
import java.util.List;

public interface MessageTemplateDao {
    List<String> findAllIds();
    MessageTemplate findById(String id);
    MessageTemplate save(MessageTemplate messageTemplate);
    String delete(String id);
}
