package ru.otus.crm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.entity.EntityMapper;
import ru.otus.core.repository.DataTemplate;
import ru.otus.core.sessionmanager.TransactionRunner;
import ru.otus.crm.mapper.ManagerMapper;
import ru.otus.crm.model.Manager;

import java.util.List;
import java.util.Optional;

public class DbServiceManagerImpl implements DBServiceManager {
    private static final Logger log = LoggerFactory.getLogger(DbServiceManagerImpl.class);

    private final DataTemplate<Manager> managerDataTemplate;
    private final TransactionRunner transactionRunner;
    private final EntityMapper<Manager> managerMapper;

    public DbServiceManagerImpl(
            TransactionRunner transactionRunner, DataTemplate<Manager> managerDataTemplate) {
        this.transactionRunner = transactionRunner;
        this.managerDataTemplate = managerDataTemplate;
        this.managerMapper = new ManagerMapper();
    }

    @Override
    public Manager saveManager(Manager manager) {
        return transactionRunner.doInTransaction(
                connection -> {
                    if (manager.getNo() == null) {
                        var managerNo = managerDataTemplate.insert(connection, managerMapper, manager);
                        var createdManager =
                                new Manager(managerNo, manager.getLabel(), manager.getParam1());
                        log.info("created manager: {}", createdManager);
                        return createdManager;
                    }
                    managerDataTemplate.update(connection, managerMapper, manager);
                    log.info("updated manager: {}", manager);
                    return manager;
                });
    }

    @Override
    public Optional<Manager> getManager(long no) {
        return transactionRunner.doInTransaction(
                connection -> {
                    var managerOptional =
                            managerDataTemplate.findById(connection, managerMapper, no);
                    log.info("manager: {}", managerOptional);
                    return managerOptional;
                });
    }

    @Override
    public List<Manager> findAll() {
        return transactionRunner.doInTransaction(
                connection -> {
                    var managerList =
                            managerDataTemplate.findAll(connection, managerMapper);
                    log.info("managerList:{}", managerList);
                    return managerList;
                });
    }
}
