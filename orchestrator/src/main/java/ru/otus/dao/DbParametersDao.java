package ru.otus.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.otus.db.repository.ParametersRepository;
import ru.otus.db.sessionmanager.TransactionManager;
import ru.otus.model.Parameters;
import ru.otus.model.ParametersContent;

@Service
public class DbParametersDao implements ParametersDao {
    private static final Logger log = LoggerFactory.getLogger(DbParametersDao.class);

    private final TransactionManager transactionManager;
    private final ParametersRepository parametersRepository;

    public DbParametersDao(TransactionManager transactionManager,
                           ParametersRepository parametersRepository) {
        this.transactionManager = transactionManager;
        this.parametersRepository = parametersRepository;
    }


    @Override
    public Parameters findById(String id) {
        return parametersRepository.findById(id).orElseThrow(() ->
                new RuntimeException(
                        String.format("Parameters with id: %s not found", id)));
    }

    @Override
    public Parameters save(Parameters parameters) {
        return transactionManager.doInTransaction(() -> {
            parameters.setNew(true);
            var savedParameters = parametersRepository.save(parameters);
            log.info("Saved parameters: {}", savedParameters.getId());
            return savedParameters;
        });
    }

    @Override
    public Parameters updateContent(ParametersContent parametersContent) {
        return transactionManager.doInTransaction(() -> {
            String parametersId = parametersContent.getParametersId();
            Parameters parameters = parametersRepository.findById(parametersId)
                    .orElseThrow(() ->
                            new RuntimeException(
                                    String.format("Parameters with id: %s not found", parametersId)));
            Parameters updatedParameters = parameters.updateContent(parametersContent);
            updatedParameters.setNew(false);
            Parameters savedParameters = parametersRepository.save(updatedParameters);
            log.info("Updated parameters: {}", savedParameters.getId());
            return savedParameters;
        });
    }

    @Override
    public String delete(String id) {
        return transactionManager.doInTransaction(() -> {
            parametersRepository.deleteById(id);
            log.info("Deleted parameters: {}", id);
            return id;
        });
    }
}
