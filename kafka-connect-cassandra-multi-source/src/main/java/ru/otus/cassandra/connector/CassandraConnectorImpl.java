package ru.otus.cassandra.connector;

import com.datastax.driver.core.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.cassandra.cluster.CassandraCluster;

public class CassandraConnectorImpl implements CassandraConnector {
    private static final Logger LOG = LoggerFactory.getLogger(CassandraConnectorImpl.class);

    private final Cluster cluster;

    private Session session;

    public CassandraConnectorImpl(CassandraCluster clusterToBuild) {
        this.cluster = clusterToBuild.build();
    }

    public void connect() {

        Metadata metadata = cluster.getMetadata();
        LOG.info("Cluster name: " + metadata.getClusterName());

        for (Host host : metadata.getAllHosts()) {
            LOG.info("Datacenter: " + host.getDatacenter() +
                     " Host: " + host.getEndPoint() +
                     " Rack: " + host.getRack());
        }

        session = cluster.connect();
    }

    public Session getSession() {
        return this.session;
    }

    public void close() {
        session.close();
        cluster.close();
    }
}
