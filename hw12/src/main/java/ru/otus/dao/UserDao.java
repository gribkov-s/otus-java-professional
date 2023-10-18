package ru.otus.dao;

import ru.otus.model.Client;
import ru.otus.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    User saveUser(User user);

    Optional<User> findByLogin(String login);
}
