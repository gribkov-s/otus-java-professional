package ru.otus.db.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.otus.model.MessageTemplate;

import java.util.List;

@Repository
public interface MessageTemplateRepository extends JpaRepository<MessageTemplate, String> {
    @Query(value = "select mt.id from MessageTemplate mt")
    List<String> findAllIds();
}
