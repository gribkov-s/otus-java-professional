package ru.otus.listener.homework;

import ru.otus.listener.Listener;
import ru.otus.model.Message;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HistoryListener implements Listener, HistoryReader {

    Map<Long, Message> history = new HashMap<>();

    @Override
    public void onUpdated(Message msg) {
        long id = msg.getId();
        history.put(id, msg.copy());
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        return Optional.ofNullable(
                history.getOrDefault(id, null));
    }
}
