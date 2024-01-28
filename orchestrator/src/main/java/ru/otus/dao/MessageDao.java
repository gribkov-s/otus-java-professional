package ru.otus.dao;

import ru.otus.model.Message;
import java.util.List;
import java.util.Optional;

public interface MessageDao {
    Message findById(String id);
    Message save(Message message);
    Message update(Message message);
    String delete(String id);
}
