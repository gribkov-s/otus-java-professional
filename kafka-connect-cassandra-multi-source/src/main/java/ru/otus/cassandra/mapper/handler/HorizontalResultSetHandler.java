package ru.otus.cassandra.mapper.handler;

import com.datastax.driver.core.ResultSet;
import ru.otus.model.State;
import ru.otus.model.inputschema.HorizontalInputSchema;

import java.util.List;

public class HorizontalResultSetHandler extends ResultSetHandler {
    private HorizontalInputSchema schema;

    public HorizontalResultSetHandler(HorizontalInputSchema schema) {
        super(schema);
    }

    @Override
    public State handle(ResultSet resultSet, State state) {
        List<String> dataFields = schema.getValueFields();
        resultSet.forEach(r ->
                dataFields.forEach(f -> {
                    Object v = r.getObject(f);
                    state.addData(f, v);
                })
        );
        return state;
    }
}
