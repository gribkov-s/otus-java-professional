package ru.otus.cassandra.mapper.handler;

import com.datastax.driver.core.ResultSet;
import ru.otus.model.State;
import ru.otus.model.inputschema.InputSchema;

public abstract class ResultSetHandler {
    private final InputSchema schema;
    public ResultSetHandler(InputSchema schema) {
        this.schema = schema;
    }
    public abstract State handle(ResultSet resultSet, State state);
}
