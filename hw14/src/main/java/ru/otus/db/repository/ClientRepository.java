package ru.otus.db.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import ru.otus.model.Client;

@Repository
public interface ClientRepository  extends ListCrudRepository<Client, Long> {
    @Query("SELECT nextval('client_SEQ')")
    long getNextClientId();
}
