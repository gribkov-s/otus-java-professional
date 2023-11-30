package ru.otus.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testcontainers.shaded.com.google.common.base.Stopwatch;
import ru.otus.base.AbstractHibernateTest;
import ru.otus.crm.model.Address;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Демо работы с hibernate (с абстракциями) должно ")
class DbServiceClientTest extends AbstractHibernateTest {

    @Test
    @DisplayName(" корректно сохранять, изменять и загружать клиента")
    void shouldCorrectSaveClient() {

        // given
        var client = new Client(null, "Vasya", new Address(null, "AnyStreet"),
                List.of(new Phone(null, "13-555-22"), new Phone(null, "14-666-333")));

        // when
        var savedClient = dbServiceClient.saveClient(client);
        System.out.println(savedClient);

        // then
        var loadedSavedClient = dbServiceClient.getClient(savedClient.getId());
        assertThat(loadedSavedClient)
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(savedClient);

        // when
        var savedClientUpdated = loadedSavedClient.get();
        savedClientUpdated.setName("updatedName");
        dbServiceClient.saveClient(savedClientUpdated);

        // then
        var loadedClient = dbServiceClient.getClient(savedClientUpdated.getId());
        assertThat(loadedClient).isPresent().get().usingRecursiveComparison().isEqualTo(savedClientUpdated);
        System.out.println(loadedClient);
    }

    @Test
    @DisplayName(" кэшировать клиента при первом чтении и читать из кэша при последующих быстрее чем из БД")
    void shouldReadClientFromCache() {

        // given
        var stopwatchReadFromDb = Stopwatch.createUnstarted();
        var stopwatchReadFromCache = Stopwatch.createUnstarted();
        var stopwatchReadFromDbAnother = Stopwatch.createUnstarted();
        var stopwatchReadFromCacheAnother = Stopwatch.createUnstarted();
        var client = new Client(null, "Vasya", new Address(null, "AnyStreet"),
                List.of(new Phone(null, "13-555-22"), new Phone(null, "14-666-333")));

        // when
        var savedClient = dbServiceClient.saveClient(client);
        var savedClientOther = dbServiceClient.saveClient(client);

        // then
        stopwatchReadFromDb.start();
        var loadedClient = dbServiceClient.getClient(savedClient.getId());
        var timeWithoutCache = stopwatchReadFromDb.elapsed(TimeUnit.MICROSECONDS);
        System.out.println(
                loadedClient + " - time to load for the first time, us: " + timeWithoutCache);

        stopwatchReadFromCache.start();
        var loadedClientAgain = dbServiceClient.getClient(savedClient.getId());
        var timeWithCache = stopwatchReadFromCache.elapsed(TimeUnit.MICROSECONDS);
        System.out.println(
                loadedClientAgain + " - time to load again, us: " + timeWithCache);

        stopwatchReadFromDbAnother.start();
        var loadedClientAnother = dbServiceClient.getClient(savedClientOther.getId());
        var timeWithoutCacheAnother = stopwatchReadFromDbAnother.elapsed(TimeUnit.MICROSECONDS);
        System.out.println(
                loadedClientAnother + " - time to load for the first time, us: " + timeWithoutCacheAnother);

        stopwatchReadFromCacheAnother.start();
        var loadedClientAnotherAgain = dbServiceClient.getClient(savedClientOther.getId());
        var timeWithCacheAnother = stopwatchReadFromCacheAnother.elapsed(TimeUnit.MICROSECONDS);
        System.out.println(
                loadedClientAnotherAgain + " - time to load again, us: " + timeWithCacheAnother);

        assertThat(timeWithCache).isLessThan(timeWithoutCache);
        assertThat(timeWithCacheAnother).isLessThan(timeWithoutCacheAnother);
    }
}
