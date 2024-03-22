package ru.otus.cassandra.mapper;

import com.datastax.driver.core.ResultSet;
import ru.otus.model.State;
import java.util.List;

public class StateMapperHorizontal implements ResultSetMapper<State, String> {
    private final List<String> dataFields;

    public StateMapperHorizontal(List<String> dataFields) {
        this.dataFields = dataFields;
    }

    public State map(ResultSet resultSet, String id) {
        var state = new State(id);
        resultSet.forEach(r ->
            dataFields.forEach(f -> {
                Object v = r.getObject(f);
                state.addData(f, v);
            })
        );
        return state;
    }
}
