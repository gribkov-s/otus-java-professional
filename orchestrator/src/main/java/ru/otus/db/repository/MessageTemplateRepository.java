package ru.otus.db.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.otus.model.MessageTemplate;

import java.util.List;

@Repository
public interface MessageTemplateRepository extends JpaRepository<MessageTemplate, String> {
    @Query(value = "SELECT id FROM message_template", nativeQuery = true)
    List<String> findAllIds();
}
