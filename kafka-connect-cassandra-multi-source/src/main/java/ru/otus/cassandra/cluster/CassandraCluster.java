package ru.otus.cassandra.cluster;

import com.datastax.driver.core.Cluster;

public interface CassandraCluster {
    Cluster build();
}
