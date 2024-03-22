package ru.otus.cassandra.conf;

import java.util.HashMap;
import java.util.Map;

public class CassandraCredAuthConf implements CassandraConf {


    final Map<String, Object> params;

    public CassandraCredAuthConf(String[] nodes,
                                 Integer port,
                                 String username,
                                 String password) {
        params = new HashMap<>();
        params.put("nodes", nodes);
        params.put("port", port);
        params.put("username", username);
        params.put("password", password);
    }

    @Override
    public <T> T getParameter(String name, Class<T> type) {
        Object value = params.get(name);
        if (value != null) {
            return type.cast(value);
        } else {
            throw new RuntimeException(
                    "Can not get parameter " + name +
                            " from " + this.getClass().getSimpleName());
        }
    }
}
