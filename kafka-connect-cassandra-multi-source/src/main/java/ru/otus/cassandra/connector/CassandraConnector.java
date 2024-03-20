package ru.otus.cassandra.connector;

import com.datastax.driver.core.Session;

public interface CassandraConnector {
    void connect();
    Session getSession();
    void close();
}
