package ru.otus.model;

import java.util.ArrayList;
import java.util.List;

public class ObjectForMessage {
    private List<String> data;

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return data.toString();
    }

    public ObjectForMessage copy() {
            var cloneData = new ArrayList<String>(this.data);
            var clone = new ObjectForMessage();
            clone.setData(cloneData);
            return clone;
    }
}
