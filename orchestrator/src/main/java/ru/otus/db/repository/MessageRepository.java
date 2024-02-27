package ru.otus.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.model.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, String> { }
