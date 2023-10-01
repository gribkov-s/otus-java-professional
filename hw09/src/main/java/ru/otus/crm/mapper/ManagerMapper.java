package ru.otus.crm.mapper;

import com.electronwill.nightconfig.core.NullObject;
import ru.otus.core.entity.EntityMapper;
import ru.otus.crm.model.Manager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ManagerMapper implements EntityMapper<Manager> {
    @Override
    public Manager map(ResultSet rs) {
        try {
            var no = rs.getLong(1);
            var label = rs.getString(2);
            var param1 = rs.getString(3);
            return new Manager(no, label, param1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Object> takeApart(Manager entity) {
        Object label = entity.getLabel();
        String param1 = entity.getParam1();
        Object param1Opt = param1 == null ? Optional.empty() : param1;//Optional.ofNullable(param1);
        return List.of(label, param1Opt);
    }
}
