package ru.otus.dao;

import ru.otus.model.ConnectionTemplate;
import java.util.List;

public interface ConnectionTemplateDao {
    List<String> findAllIds();
    ConnectionTemplate findById(String id);
    ConnectionTemplate save(ConnectionTemplate connectionTemplate);
    String delete(String id);
}
