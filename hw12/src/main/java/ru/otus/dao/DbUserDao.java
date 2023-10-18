package ru.otus.dao;

import jakarta.persistence.Column;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.model.User;
import ru.otus.db.repository.DataTemplate;
import ru.otus.db.sessionmanager.TransactionManager;

import java.util.Optional;

public class DbUserDao implements UserDao {
    private static final Logger log = LoggerFactory.getLogger(DbUserDao.class);

    private final DataTemplate<User> userDataTemplate;
    private final TransactionManager transactionManager;


    public DbUserDao(TransactionManager transactionManager, DataTemplate<User> userDataTemplate) throws NoSuchFieldException {
        this.transactionManager = transactionManager;
        this.userDataTemplate = userDataTemplate;
    }

    @Override
    public User saveUser(User user) {
        return transactionManager.doInTransaction(session -> {
            var userCloned = user.clone();
            if (user.getId() == null) {
                var savedUser = userDataTemplate.insert(session, userCloned);
                log.info("created user: {}", userCloned);
                return savedUser;
            }
            var savedUser = userDataTemplate.update(session, userCloned);
            log.info("updated user: {}", savedUser);
            return savedUser;
        });
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return transactionManager.doInReadOnlyTransaction(session -> {
            var query = session.createQuery(
                    "SELECT user FROM User user WHERE user.login=:login", User.class);
            query.setParameter("login", login);
            return query.uniqueResultOptional();
        });
    }
}
