package ru.otus.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.otus.db.repository.ConnectionRepository;
import ru.otus.db.sessionmanager.TransactionManager;
import ru.otus.model.Connection;
import ru.otus.model.ConnectionContent;

@Service
public class DbConnectionDao implements ConnectionDao {
    private static final Logger log = LoggerFactory.getLogger(DbConnectionDao.class);

    private final TransactionManager transactionManager;
    private final ConnectionRepository connectionRepository;

    public DbConnectionDao(TransactionManager transactionManager,
                           ConnectionRepository connectionRepository) {
        this.transactionManager = transactionManager;
        this.connectionRepository = connectionRepository;
    }

    @Override
    public Connection findById(String id) {
        return connectionRepository.findById(id).orElseThrow(() ->
                new RuntimeException(
                        String.format("Connection with id: %s not found", id)));
    }

    @Override
    public Connection save(Connection connection) {
        return transactionManager.doInTransaction(() -> {
            connection.setNew(true);
            var savedConnection = connectionRepository.save(connection);
            log.info("Saved connection: {}", savedConnection.getId());
            return savedConnection;
        });
    }

    @Override
    public Connection updateContent(ConnectionContent connectionContent) {
        return transactionManager.doInTransaction(() -> {
            String connectionId = connectionContent.getConnectionId();
            Connection connection = connectionRepository.findById(connectionId)
                    .orElseThrow(() ->
                            new RuntimeException(
                                    String.format("Connection with id: %s not found", connectionId)));
            Connection updatedConnection = connection.updateContent(connectionContent);
            updatedConnection.setNew(false);
            Connection savedConnection = connectionRepository.save(updatedConnection);
            log.info("Updated connection: {}", savedConnection.getId());
            return savedConnection;
        });
    }

    @Override
    public String delete(String id) {
        return transactionManager.doInTransaction(() -> {
            connectionRepository.deleteById(id);
            log.info("Deleted connection: {}", id);
            return id;
        });
    }
}
