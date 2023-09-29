package ru.otus.core.repository;

import ru.otus.core.entity.EntityMapper;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface DataTemplate<T> {
    Optional<T> findById(Connection connection, EntityMapper<T> mapper, long id);

    List<T> findAll(Connection connection, EntityMapper<T> mapper);

    long insert(Connection connection, EntityMapper<T> mapper, T entity);

    void update(Connection connection, EntityMapper<T> mapper, T entity);
}
