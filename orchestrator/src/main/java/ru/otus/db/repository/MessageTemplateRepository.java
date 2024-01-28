package ru.otus.db.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.otus.model.MessageTemplate;

import java.util.List;

@Repository
public interface MessageTemplateRepository extends CrudRepository<MessageTemplate, String> {
    @Query("SELECT id FROM message_template;")
    List<String> findAllIds();
}
