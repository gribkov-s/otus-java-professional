package ru.otus.examples;

import ru.otus.model.TaskType;

import java.util.concurrent.ConcurrentHashMap;

public class Test {

    public static void main(String[] args) {
        var m = new ConcurrentHashMap<String, Long>();
        m.put("a", 1L);
        var resCompute = m.compute("a", (k, v) -> v == null ? 1 : v + 1);
        System.out.println(resCompute);
    }
}
