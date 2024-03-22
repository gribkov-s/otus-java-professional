package ru.otus.model;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class State {
    private String id;
    private final Map<String, Object> data;

    public State(String id) {
        this.id = id;
        this.data = new HashMap<>();
    }

    public void addData(String k, Object v) {
        this.data.put(k, v);
    }

    public void addData(State state) {
        String idAdd = state.getId();
        if (this.id.equals(idAdd)) {
            Map<String, Object> dataAdd = state.getData();
            this.data.putAll(dataAdd);
        } else {
            throw new RuntimeException("Ids are not equal.");
        }
    }
}
