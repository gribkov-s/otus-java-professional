package ru.otus.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.model.Connection;

@Repository
public interface ConnectionRepository extends JpaRepository<Connection, String> { }
