package ru.otus.crm.mapper;

import ru.otus.core.entity.EntityMapper;
import ru.otus.crm.model.Client;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ClientMapper implements EntityMapper<Client> {

    @Override
    public Client map(ResultSet rs) {
        try {
            var id = rs.getLong(1);
            var name = rs.getString(2);
            return new Client(id, name);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Object> takeApart(Client entity) {
        Object name = entity.getName();
        return List.of(name);
    }
}
