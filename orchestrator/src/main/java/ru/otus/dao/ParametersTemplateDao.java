package ru.otus.dao;

import ru.otus.model.ParametersTemplate;
import java.util.List;

public interface ParametersTemplateDao {
    List<String> findAllIds();
    ParametersTemplate findById(String id);
    ParametersTemplate save(ParametersTemplate parametersTemplate);
    String delete(String id);
}
