package ru.otus.core.entity;

import java.sql.ResultSet;
import java.util.List;

public interface EntityMapper<T> {
    T map(ResultSet rs);
    List<Object> takeApart(T entity);
}
