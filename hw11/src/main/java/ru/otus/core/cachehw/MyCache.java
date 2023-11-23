package ru.otus.core.cachehw;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class MyCache<K, V> implements HwCache<K, V> {

    private final Map<K, V> cache = new WeakHashMap<>();
    private final List<HwListener<K, V>> listeners  = new ArrayList<>();

    @Override
    public void put(K key, V value) {
        cache.put(key, value);
        listeners.forEach(
                kvHwListener -> kvHwListener.notify(key, value, "PUT"));
    }

    @Override
    public void remove(K key) {
        V value = cache.remove(key);
        listeners.forEach(
                kvHwListener -> kvHwListener.notify(key, value, "REMOVE"));
    }

    @Override
    public V get(K key) {
        V value = cache.get(key);
        listeners.forEach(
                kvHwListener -> kvHwListener.notify(key, value, "GET"));
        return value;
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        listeners.remove(listener);
    }
}
