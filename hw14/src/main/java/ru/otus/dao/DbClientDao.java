package ru.otus.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.otus.db.repository.ClientRepository;
import ru.otus.db.sessionmanager.TransactionManager;
import ru.otus.model.Client;
import java.util.ArrayList;
import java.util.List;

@Service
public class DbClientDao implements ClientDao {
    private static final Logger log = LoggerFactory.getLogger(DbClientDao.class);

    private final TransactionManager transactionManager;
    private final ClientRepository clientRepository;

    public DbClientDao(TransactionManager transactionManager, ClientRepository clientRepository) {
        this.transactionManager = transactionManager;
        this.clientRepository = clientRepository;
    }

    @Override
    public Client saveClient(Client client) {
        return transactionManager.doInTransaction(() -> {
            Long clientId = clientRepository.getNextClientId();
            client.setId(clientId);
            Client savedClient = clientRepository.save(client);
            log.info("saved client: {}", savedClient);
            return savedClient;
        });
    }

    @Override
    public List<Client> findAll() {
        var clientList = new ArrayList<Client>(clientRepository.findAll());
        log.info("clientList:{}", clientList);
        return clientList;
    }
}
