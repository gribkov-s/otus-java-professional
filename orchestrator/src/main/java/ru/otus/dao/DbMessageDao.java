package ru.otus.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.otus.db.repository.MessageRepository;
import ru.otus.db.sessionmanager.TransactionManager;
import ru.otus.model.Message;
import ru.otus.model.MessageContent;

@Service
public class DbMessageDao implements MessageDao {
    private static final Logger log = LoggerFactory.getLogger(DbMessageDao.class);

    private final TransactionManager transactionManager;
    private final MessageRepository messageRepository;

    public DbMessageDao(TransactionManager transactionManager,
                        MessageRepository messageRepository) {
        this.transactionManager = transactionManager;
        this.messageRepository = messageRepository;
    }


    @Override
    public Message findById(String id) {
        return messageRepository.findById(id).orElseThrow(() ->
                new RuntimeException(
                        String.format("Message with id: %s not found", id)));
    }

    @Override
    public Message save(Message message) {
        return transactionManager.doInTransaction(() -> {
            message.setNew(true);
            var savedMessage = messageRepository.save(message);
            log.info("Saved message: {}", savedMessage.getId());
            return savedMessage;
        });
    }

    @Override
    public Message updateContent(MessageContent messageContent) {
        return transactionManager.doInTransaction(() -> {
            String messageId = messageContent.getMessageId();
            Message message = messageRepository.findById(messageId)
                    .orElseThrow(() ->
                            new RuntimeException(
                                    String.format("Message with id: %s not found", messageId)));
            Message updatedMessage = message.updateContent(messageContent);
            updatedMessage.setNew(false);
            Message savedMessage = messageRepository.save(updatedMessage);
            log.info("Updated message: {}", savedMessage.getId());
            return savedMessage;
        });
    }

    @Override
    public String delete(String id) {
        return transactionManager.doInTransaction(() -> {
            messageRepository.deleteById(id);
            log.info("Deleted message: {}", id);
            return id;
        });
    }
}
