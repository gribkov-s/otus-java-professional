package ru.otus.db.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.otus.model.ConnectionTemplate;
import java.util.List;

@Repository
public interface ConnectionTemplateRepository extends JpaRepository<ConnectionTemplate, String> {
    @Query(value = "SELECT ct.id FROM ConnectionTemplate ct")
    List<String> findAllIds();
}
