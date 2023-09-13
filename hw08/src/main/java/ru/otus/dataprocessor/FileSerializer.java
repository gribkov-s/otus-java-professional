package ru.otus.dataprocessor;

import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class FileSerializer implements Serializer {

    private final String fileName;

    public FileSerializer(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void serialize(Map<String, Double> data)  {
        // формирует результирующий json и сохраняет его в файл
        try (var writer = new BufferedWriter(new FileWriter(fileName))) {
            Map<String, Double> dataSorted = new TreeMap<>(data);
            var gson = new Gson();
            String json = gson.toJson(dataSorted);
            writer.write(json);
        } catch (IOException e) {
            throw new FileProcessException(e);
        }
    }
}
