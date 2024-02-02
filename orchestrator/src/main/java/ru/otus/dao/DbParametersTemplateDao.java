package ru.otus.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.otus.db.repository.ParametersTemplateRepository;
import ru.otus.db.sessionmanager.TransactionManager;
import ru.otus.model.ParametersTemplate;

import java.util.List;

@Service
public class DbParametersTemplateDao implements ParametersTemplateDao {
    private static final Logger log = LoggerFactory.getLogger(DbParametersTemplateDao.class);

    private final TransactionManager transactionManager;
    private final ParametersTemplateRepository parametersTemplateRepository;

    public DbParametersTemplateDao(TransactionManager transactionManager,
                                   ParametersTemplateRepository parametersTemplateRepository) {
        this.transactionManager = transactionManager;
        this.parametersTemplateRepository = parametersTemplateRepository;
    }

    @Override
    public List<String> findAllIds() {
        return parametersTemplateRepository.findAllIds();
    }

    @Override
    public ParametersTemplate findById(String id) {
        return parametersTemplateRepository.findById(id).orElseThrow(() ->
                new RuntimeException(
                        String.format("Parameters template with id: %s not found", id)));
    }

    @Override
    public ParametersTemplate save(ParametersTemplate parametersTemplate) {
        return transactionManager.doInTransaction(() -> {
            parametersTemplate.setNew(true);
            ParametersTemplate savedTemplate = parametersTemplateRepository.save(parametersTemplate);
            log.info("Saved parameters template: {}", savedTemplate.getId());
            return savedTemplate;
        });
    }

    @Override
    public String delete(String id) {
        return transactionManager.doInTransaction(() -> {
            parametersTemplateRepository.deleteById(id);
            log.info("Deleted parameters template: {}", id);
            return id;
        });
    }
}
