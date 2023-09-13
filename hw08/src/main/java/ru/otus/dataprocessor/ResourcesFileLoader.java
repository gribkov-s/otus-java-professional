package ru.otus.dataprocessor;

import com.google.common.reflect.TypeToken;
import ru.otus.model.Measurement;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;

public class ResourcesFileLoader implements Loader {

    private final String fileName;

    public ResourcesFileLoader(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Measurement> load() {
        // читает файл, парсит и возвращает результат
        try (var inputStream = getClass().getResourceAsStream("/" + fileName);
             var reader = new BufferedReader(new InputStreamReader(inputStream))) {
            var tp = new TypeToken<ArrayList<Measurement>>(){}.getType();
            var gson = new Gson();
            return gson.fromJson(reader, tp);
        } catch (IOException e) {
            throw new FileProcessException(e);
        }
    }
}
