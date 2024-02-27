package ru.otus.dao;

import ru.otus.model.Parameters;
import ru.otus.model.ParametersContent;

public interface ParametersDao {
    Parameters findById(String id);
    Parameters save(Parameters parameters);
    Parameters updateContent(ParametersContent parametersContent);
    String delete(String id);
}
