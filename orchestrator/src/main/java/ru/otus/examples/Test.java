package ru.otus.examples;

import ru.otus.model.TaskType;

import java.util.concurrent.ConcurrentHashMap;

public class Test {

    public static void main(String[] args) {
        var m = new ConcurrentHashMap<String, String>();
        m.put("a", "1");
        var r = m.get("b");
        System.out.println(r);
    }
}
