package ru.otus.cassandra.conf;

public interface CassandraConf {
    <T> T getParameter(String name, Class<T> type);
}
