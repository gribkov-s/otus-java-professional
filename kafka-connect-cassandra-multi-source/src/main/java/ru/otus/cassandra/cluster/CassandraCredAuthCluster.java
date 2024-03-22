package ru.otus.cassandra.cluster;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.SocketOptions;
import ru.otus.cassandra.conf.CassandraConf;

public class CassandraCredAuthCluster implements CassandraCluster {

    private final CassandraConf conf;

    public CassandraCredAuthCluster(CassandraConf conf) {
        this.conf = conf;
    }

    @Override
    public Cluster build() {
        Cluster.Builder clusterBuilder = Cluster.builder()
                .addContactPoints(conf.getParameter("nodes", String[].class))
                .withPort(conf.getParameter("port", Integer.class))
                .withCredentials(
                        conf.getParameter("username", String.class),
                        conf.getParameter("password", String.class));

        return clusterBuilder.build();
    }
}
