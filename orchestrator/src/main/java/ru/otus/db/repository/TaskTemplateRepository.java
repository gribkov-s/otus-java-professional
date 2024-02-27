package ru.otus.db.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.otus.model.TaskTemplate;

import java.util.List;

@Repository
public interface TaskTemplateRepository extends JpaRepository<TaskTemplate, String> {
    @Query(value = "SELECT tt.id FROM TaskTemplate tt")
    List<String> findAllIds();
}
