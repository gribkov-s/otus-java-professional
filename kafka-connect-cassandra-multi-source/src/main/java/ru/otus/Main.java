package ru.otus;

import ru.otus.cassandra.cluster.CassandraCluster;
import ru.otus.cassandra.cluster.CassandraCredAuthCluster;
import ru.otus.cassandra.conf.CassandraCredAuthConf;
import ru.otus.cassandra.connector.CassandraConnectorImpl;

public class Main {
    public static void main(String[] args) {

        System.setProperty("com.datastax.driver.USE_NATIVE_CLOCK", "false");

        var conf = new CassandraCredAuthConf("localhost", 9042, "cassandra", "cassandra");
        var cluster = new CassandraCredAuthCluster(conf);
        var connector = new CassandraConnectorImpl(cluster);
        connector.connect();
        var session = connector.getSession();
        var set = session.execute("SELECT * FROM sef.metrics_2 WHERE id = '000'");
        set.forEach(System.out::println);
        connector.close();
    }
}
