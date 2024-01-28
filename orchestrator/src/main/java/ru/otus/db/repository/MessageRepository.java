package ru.otus.db.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.otus.model.Message;

@Repository
public interface MessageRepository extends CrudRepository<Message, String> { }
