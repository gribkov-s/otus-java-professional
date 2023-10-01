package ru.otus.core.entity;

import java.util.stream.Collectors;

public class EntitySQLMetaDataImpl<T> implements EntitySQLMetaData {

    private final EntityClassMetaData<T> entityClassMetaData;

    public EntitySQLMetaDataImpl(EntityClassMetaData<T> entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData;
    }

    private String table() {
        return entityClassMetaData.getName().toLowerCase();
    }

    private String columnsAll() {
        return entityClassMetaData.getAllFields()
                .stream()
                .map(f -> f.getName().toLowerCase())
                .collect(Collectors.joining (", "));
    }

    private String columnsNotId() {
        return entityClassMetaData.getFieldsWithoutId()
                .stream()
                .map(f -> f.getName().toLowerCase())
                .collect(Collectors.joining (", "));
    }

    private String idColumn() {
        return entityClassMetaData.getIdField().getName().toLowerCase();
    }

    @Override
    public String getSelectAllSql() {
        return String.format(
                "SELECT %s FROM %s;",
                columnsAll(), table());
    }

    @Override
    public String getSelectByIdSql() {
        return String.format(
                "SELECT %s FROM %s WHERE %s = ?;",
                columnsAll(), table(), idColumn());
    }

    @Override
    public String getInsertSql() {
        String params = entityClassMetaData.getFieldsWithoutId()
                .stream()
                .map(f -> "?")
                .collect(Collectors.joining (", "));
        return String.format(
                "INSERT INTO %s(%s) VALUES (%s);",
                table(), columnsNotId(), params);
    }

    @Override
    public String getUpdateSql() {
        String params = entityClassMetaData.getFieldsWithoutId()
                .stream()
                .map(f -> f.getName() + " = ?")
                .collect(Collectors.joining (", "));
        return String.format(
                "UPDATE %s SET %s WHERE %s = ?;",
                table(), params, idColumn());
    }
}
