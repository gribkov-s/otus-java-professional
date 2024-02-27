package ru.otus.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.model.Parameters;

@Repository
public interface ParametersRepository extends JpaRepository<Parameters, String> { }
