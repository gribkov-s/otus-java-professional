package ru.otus.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.otus.db.repository.MessageTemplateRepository;
import ru.otus.db.sessionmanager.TransactionManager;
import ru.otus.model.MessageTemplate;
import java.util.List;
import java.util.Optional;

@Service
public class DbMessageTemplateDao implements MessageTemplateDao {
    private static final Logger log = LoggerFactory.getLogger(DbMessageTemplateDao.class);

    private final TransactionManager transactionManager;
    private final MessageTemplateRepository messageTemplateRepository;

    public DbMessageTemplateDao(TransactionManager transactionManager,
                                MessageTemplateRepository messageTemplateRepository) {
        this.transactionManager = transactionManager;
        this.messageTemplateRepository = messageTemplateRepository;
    }

    @Override
    public List<String> findAllIds() {
        return messageTemplateRepository.findAllIds();
    }

    @Override
    public MessageTemplate findById(String id) {
        return messageTemplateRepository.findById(id).orElseThrow(() ->
                new RuntimeException(String.format("Message template with id: %s not found", id)));
    }

    @Override
    public MessageTemplate save(MessageTemplate messageTemplate) {
        return transactionManager.doInTransaction(() -> {
            messageTemplate.setNew(true);
            MessageTemplate savedTemplate = messageTemplateRepository.save(messageTemplate);
            log.info("Saved message template: {}", messageTemplate.getId());
            return savedTemplate;
        });
    }

    @Override
    public String delete(String id) {
        return transactionManager.doInTransaction(() -> {
            messageTemplateRepository.deleteById(id);
            log.info("Deleted message template: {}", id);
            return id;
        });
    }
}
