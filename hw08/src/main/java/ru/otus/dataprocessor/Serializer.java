package ru.otus.dataprocessor;

import java.io.FileNotFoundException;
import java.util.Map;

public interface Serializer {

    void serialize(Map<String, Double> data) throws FileNotFoundException;
}
