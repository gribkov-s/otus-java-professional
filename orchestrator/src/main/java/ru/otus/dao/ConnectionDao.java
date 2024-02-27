package ru.otus.dao;

import ru.otus.model.Connection;
import ru.otus.model.ConnectionContent;

public interface ConnectionDao {
    Connection findById(String id);
    Connection save(Connection connection);
    Connection updateContent(ConnectionContent connectionContent);
    String delete(String id);
}
