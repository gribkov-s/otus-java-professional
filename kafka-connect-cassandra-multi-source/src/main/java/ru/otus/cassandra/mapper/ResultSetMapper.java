package ru.otus.cassandra.mapper;

import com.datastax.driver.core.ResultSet;
import ru.otus.model.State;

public interface ResultSetMapper {
    public State map(ResultSet resultSet, String id);
}
