package ru.otus.db.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.otus.model.ParametersTemplate;

import java.util.List;

@Repository
public interface ParametersTemplateRepository extends JpaRepository<ParametersTemplate, String> {
    @Query(value = "SELECT id FROM parameters_template", nativeQuery = true)
    List<String> findAllIds();
}
