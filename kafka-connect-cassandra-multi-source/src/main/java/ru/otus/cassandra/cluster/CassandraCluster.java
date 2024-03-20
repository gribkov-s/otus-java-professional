package ru.otus.cassandra.cluster;

import com.datastax.driver.core.Cluster;
import ru.otus.cassandra.conf.CassandraConf;

public interface CassandraCluster {
    Cluster build();
}
