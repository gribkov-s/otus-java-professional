package ru.otus.core.entity;

import ru.otus.annotations.Column;
import ru.otus.annotations.Id;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {

    private final String name;
    private final List<Field> fieldsForColumns;
    private final List<Field> fieldsForColumnsNotId;
    private final Field fieldForId;
    private final Constructor<T> сonstructor;

    public EntityClassMetaDataImpl(Class<T> clazz) {
        this.name = clazz.getSimpleName();
        this.fieldsForColumns = fieldsForColumns(clazz);
        this.fieldsForColumnsNotId = fieldsForColumnsNotId(clazz);
        this.fieldForId = fieldForId(clazz);
        this.сonstructor = constructor(clazz);
    }

    private static class FieldComparator implements Comparator<Field> {
        public int compare(Field f1, Field f2) {
            var f1o = Integer.valueOf(f1.getAnnotation(Column.class).order());
            var f2o = Integer.valueOf(f2.getAnnotation(Column.class).order());
            return f1o.compareTo(f2o);
        }
    }

    private List<Field> fieldsForColumns(Class<T> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(fld -> fld.isAnnotationPresent(Column.class))
                .sorted(new FieldComparator())
                .toList();
    }

    private List<Field> fieldsForColumnsNotId(Class<T> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(fld -> fld.isAnnotationPresent(Column.class) &&
                        !fld.isAnnotationPresent(Id.class))
                .sorted(new FieldComparator())
                .toList();
    }

    private Field fieldForId(Class<T> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(
                        String.format("No Id in %s", clazz.getName())));
    }

    private Constructor<T> constructor(Class<T> clazz) {
        var fieldClasses = Arrays.stream(clazz.getDeclaredFields())
                .filter(fld -> fld.isAnnotationPresent(Column.class))
                .sorted(new FieldComparator())
                .map(Field::getType)
                .toArray(Class[]::new);
        try {
            return clazz.getConstructor(fieldClasses);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Constructor<T> getConstructor() { return this.сonstructor; }

    @Override
    public Field getIdField() {
        return this.fieldForId;
    }

    @Override
    public List<Field> getAllFields() {
        return this.fieldsForColumns;
    }

    @Override
    public List<Field> getFieldsWithoutId() { return this.fieldsForColumnsNotId; }
}
