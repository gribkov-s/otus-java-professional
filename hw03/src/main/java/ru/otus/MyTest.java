package ru.otus;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Id;
import ru.otus.annotations.Test;

public class MyTest {

    @Before
    @Id(group = "a")
    void doBefore() throws Throwable {
        //throw new Exception("!!!");
    }

    @Test
    @Id(group = "a")
    static void test() throws Throwable {
        //throw new Exception("!!!");
    }

    @Test
    @Id(group = "a")
    static void testAgain() throws Throwable {
        throw new Exception("!!?");
    }

    @After
    @Id(group = "a")
    void doAfter() throws Throwable {
        //throw new Exception("!!!");
    }

    @Before
    @Id(group = "b")
    void doBeforeOther() throws Throwable {
        //throw new Exception("!!!");
    }

    @Test
    @Id(group = "b")
    static void testOther() throws Throwable {
        throw new Exception("!!!");
    }

    @After
    @Id(group = "b")
    void doAfterOther() throws Throwable {
        //throw new Exception("!!!");
    }
}
