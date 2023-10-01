package ru.otus.crm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.entity.EntityMapper;
import ru.otus.core.repository.DataTemplate;
import ru.otus.core.sessionmanager.TransactionRunner;
import ru.otus.crm.mapper.ClientMapper;
import ru.otus.crm.model.Client;
import java.util.List;
import java.util.Optional;

public class DbServiceClientImpl implements DBServiceClient {
    private static final Logger log = LoggerFactory.getLogger(DbServiceClientImpl.class);

    private final DataTemplate<Client> dataTemplate;
    private final TransactionRunner transactionRunner;
    private final EntityMapper<Client> clientMapper;

    public DbServiceClientImpl(
            TransactionRunner transactionRunner,
            DataTemplate<Client> dataTemplate) {
        this.transactionRunner = transactionRunner;
        this.dataTemplate = dataTemplate;
        this.clientMapper = new ClientMapper();
    }

    @Override
    public Client saveClient(Client client) {
        return transactionRunner.doInTransaction(
                connection -> {
                    if (client.getId() == null) {
                        var clientId = dataTemplate.insert(connection, clientMapper, client);
                        var createdClient = new Client(clientId, client.getName());
                        log.info("created client: {}", createdClient);
                        return createdClient;
                    }
                    dataTemplate.update(connection, clientMapper, client);
                    log.info("updated client: {}", client);
                    return client;
                });
    }

    @Override
    public Optional<Client> getClient(long id) {
        return transactionRunner.doInTransaction(
                connection -> {
                    var clientOptional =
                            dataTemplate.findById(connection, clientMapper, id);
                    log.info("client: {}", clientOptional);
                    return clientOptional;
                });
    }

    @Override
    public List<Client> findAll() {
        return transactionRunner.doInTransaction(
                connection -> {
                    var clientList =
                            dataTemplate.findAll(connection, clientMapper);
                    log.info("clientList:{}", clientList);
                    return clientList;
                });
    }
}
