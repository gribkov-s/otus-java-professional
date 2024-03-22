package ru.otus;

import ru.otus.cassandra.cluster.CassandraCredAuthCluster;
import ru.otus.cassandra.conf.CassandraCredAuthConf;
import ru.otus.cassandra.connector.CassandraConnectorImpl;
import ru.otus.cassandra.mapper.StateMapperHorizontal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        System.setProperty("com.datastax.driver.USE_NATIVE_CLOCK", "false");

        var conf = new CassandraCredAuthConf(
                new String[]{"localhost"},
                9042,
                "cassandra",
                "cassandra");

        var cluster = new CassandraCredAuthCluster(conf);
        var connector = new CassandraConnectorImpl(cluster);
        connector.connect();
        var session = connector.getSession();

        String id = "000";
        List<String> dataFields = new ArrayList<>();
        dataFields.add("amount");
        dataFields.add("num");

        String query = "SELECT amount, num FROM sef.metrics_3 WHERE id = '000'";

        var mapper = new StateMapperHorizontal(dataFields);

        var set = session.execute(query);
        var state = mapper.map(set, id);
        Map<String, Object> data = state.getData();
        //var x = data.get("amount");
        System.out.println(data);

        connector.close();
    }
}
