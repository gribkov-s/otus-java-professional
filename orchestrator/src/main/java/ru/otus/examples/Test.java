package ru.otus.examples;

import ru.otus.model.TaskType;

import java.util.concurrent.ConcurrentHashMap;

public class Test {

    public static void main(String[] args) {
        var m = new ConcurrentHashMap<String, Long>();
        var resPut = m.put("a", 1L);
        var resPut2 = m.put("a", 2L);
        var resCompute = m.computeIfPresent("a", (k, v) -> v + 1);
        var resRemove = m.remove("b");
        System.out.println(resPut);
        System.out.println(resPut2);
        System.out.println(resCompute);
        System.out.println(resRemove);
    }
}
