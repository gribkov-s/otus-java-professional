package ru.otus.cassandra.mapper;

import com.datastax.driver.core.ResultSet;
import ru.otus.cassandra.mapper.handler.ResultSetHandler;
import ru.otus.model.State;

public class ResultSetMapperImpl implements ResultSetMapper {
    ResultSetHandler resultSetHandler;

    public ResultSetMapperImpl(ResultSetHandler resultSetHandler) {
        this.resultSetHandler = resultSetHandler;
    }

    public State map(ResultSet resultSet, String id) {
        var state = new State(id);
        return resultSetHandler.handle(resultSet, state);
    }
}
