package ru.otus.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.otus.db.repository.ConnectionTemplateRepository;
import ru.otus.db.sessionmanager.TransactionManager;
import ru.otus.model.ConnectionTemplate;
import java.util.List;

@Service
public class DbConnectionTemplateDao implements ConnectionTemplateDao {
    private static final Logger log = LoggerFactory.getLogger(DbConnectionTemplateDao.class);

    private final TransactionManager transactionManager;
    private final ConnectionTemplateRepository connectionTemplateRepository;

    public DbConnectionTemplateDao(TransactionManager transactionManager,
                                   ConnectionTemplateRepository connectionTemplateRepository) {
        this.transactionManager = transactionManager;
        this.connectionTemplateRepository = connectionTemplateRepository;
    }

    @Override
    public List<String> findAllIds() {
        return connectionTemplateRepository.findAllIds();
    }

    @Override
    public ConnectionTemplate findById(String id) {
        return connectionTemplateRepository.findById(id).orElseThrow(() ->
                new RuntimeException(
                        String.format("Connection template with id: %s not found", id)));
    }

    @Override
    public ConnectionTemplate save(ConnectionTemplate connectionTemplate) {
        return transactionManager.doInTransaction(() -> {
            connectionTemplate.setNew(true);
            ConnectionTemplate savedTemplate = connectionTemplateRepository.save(connectionTemplate);
            log.info("Saved connection template: {}", savedTemplate.getId());
            return savedTemplate;
        });
    }

    @Override
    public String delete(String id) {
        return transactionManager.doInTransaction(() -> {
            connectionTemplateRepository.deleteById(id);
            log.info("Deleted connection template: {}", id);
            return id;
        });
    }
}
