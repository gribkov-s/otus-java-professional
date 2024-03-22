package ru.otus.cassandra.mapper;

import com.datastax.driver.core.ResultSet;
import ru.otus.model.State;

public interface ResultSetMapper<T, U> {
    public T map(ResultSet resultSet, U id);
}
