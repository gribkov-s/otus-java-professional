package ru.otus;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.internal.SessionFactoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.dao.ClientDao;
import ru.otus.dao.DbClientDao;
import ru.otus.dao.DbUserDao;
import ru.otus.dao.UserDao;
import ru.otus.db.migrations.MigrationsExecutorFlyway;
import ru.otus.db.repository.DataTemplate;
import ru.otus.db.repository.DataTemplateHibernate;
import ru.otus.db.repository.HibernateUtils;
import ru.otus.db.sessionmanager.TransactionManager;
import ru.otus.db.sessionmanager.TransactionManagerHibernate;
import ru.otus.model.Address;
import ru.otus.model.Client;
import ru.otus.model.Phone;
import ru.otus.model.User;
import ru.otus.server.ClientsWebServer;
import ru.otus.services.TemplateProcessor;
import ru.otus.services.TemplateProcessorImpl;
import ru.otus.services.UserAuthService;
import ru.otus.services.UserAuthServiceImpl;

/*

    // Стартовая страница
    http://localhost:8080/crm

    // Страница клиентов
    http://localhost:8080/crm/clients

*/
public class App {

    public static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";
    private static final int WEB_SERVER_PORT = 8080;
    private static final String TEMPLATES_DIR = "/templates/";

    public static void main(String[] args) throws Exception {
        var configuration = new Configuration().configure(HIBERNATE_CFG_FILE);

        var dbUrl = configuration.getProperty("hibernate.connection.url");
        var dbUserName = configuration.getProperty("hibernate.connection.username");
        var dbPassword = configuration.getProperty("hibernate.connection.password");

        var migrations = new MigrationsExecutorFlyway(dbUrl, dbUserName, dbPassword);
        migrations.executeMigrations();

        var sessionFactory = HibernateUtils
                .buildSessionFactory(configuration,
                        Client.class, Address.class, Phone.class, User.class);
        TransactionManager transactionManager = new TransactionManagerHibernate(sessionFactory);

        DataTemplate<User> userDataTemplate = new DataTemplateHibernate<>(User.class);
        UserDao userDao = new DbUserDao(transactionManager, userDataTemplate);

        DataTemplate<Client> clientDataTemplate = new DataTemplateHibernate<>(Client.class);
        ClientDao clientDao = new DbClientDao(transactionManager, clientDataTemplate);

        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);
        UserAuthService authService = new UserAuthServiceImpl(userDao);

        ClientsWebServer clientsWebServer = new ClientsWebServer(
                WEB_SERVER_PORT, authService, clientDao, gson, templateProcessor);

        clientsWebServer.start();
        clientsWebServer.join();
    }
}
