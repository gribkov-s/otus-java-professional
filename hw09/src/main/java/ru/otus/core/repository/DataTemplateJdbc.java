package ru.otus.core.repository;

import ru.otus.core.entity.EntityMapper;
import ru.otus.core.repository.executor.DbExecutor;
import ru.otus.core.entity.EntitySQLMetaData;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

/** Сохратяет объект в базу, читает объект из базы */
@SuppressWarnings("java:S1068")
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;

    public DataTemplateJdbc(DbExecutor dbExecutor, EntitySQLMetaData entitySQLMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
    }

    @Override
    public Optional<T> findById(Connection connection, EntityMapper<T> mapper, long id) {
        return dbExecutor
                .executeSelect(
                        connection,
                        entitySQLMetaData.getSelectAllSql(),
                        Collections.emptyList(),
                        rs -> {
                            try {
                                if (rs.next()) {
                                    return mapper.map(rs);
                                }
                                return null;
                            } catch (SQLException e) {
                                throw new DataTemplateException(e);
                            }
                        });
    }

    @Override
    public List<T> findAll(Connection connection, EntityMapper<T> mapper) {
        return dbExecutor
                .executeSelect(
                        connection,
                        entitySQLMetaData.getSelectAllSql(),
                        Collections.emptyList(),
                        rs -> {
                            var resList = new ArrayList<T>();
                            try {
                                while (rs.next()) {
                                    mapper.map(rs);
                                }
                                return resList;
                            } catch (SQLException e) {
                                throw new DataTemplateException(e);
                            }
                        })
                .orElseThrow(() -> new UnsupportedOperationException());
    }

    @Override
    public long insert(Connection connection, EntityMapper<T> mapper, T entity) {
        try {
            return dbExecutor.executeStatement(
                    connection,
                    entitySQLMetaData.getInsertSql(),
                    mapper.takeApart(entity));
        } catch (Exception e) {
            throw new UnsupportedOperationException(e);
        }
    }

    @Override
    public void update(Connection connection, EntityMapper<T> mapper, T entity) {
        try {
            dbExecutor.executeStatement(
                    connection,
                    entitySQLMetaData.getUpdateSql(),
                    mapper.takeApart(entity));
        } catch (Exception e) {
            throw new UnsupportedOperationException(e);
        }
    }
}
