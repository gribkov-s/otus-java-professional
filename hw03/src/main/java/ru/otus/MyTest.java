package ru.otus;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Id;
import ru.otus.annotations.Test;

public class MyTest {

    @Before
    void doBefore() throws Throwable {
        //throw new Exception("!!!");
        System.out.println("doBefore");
    }

    @Test
    static void test() throws Throwable {
        throw new Exception("!!!");
    }

    @Test
    static void testAgain() throws Throwable {
        throw new Exception("!!?");
    }

    @After
    void doAfter() throws Throwable {
        //throw new Exception("!!!");
        System.out.println("doAfter");
    }

    @Before
    void doBeforeOther() throws Throwable {
        //throw new Exception("!!!");
        System.out.println("doBeforeOther");
    }

    @Test
    static void testOther() throws Throwable {
        ///hrow new Exception("!!!");
    }

    @After
    void doAfterOther() throws Throwable {
        //throw new Exception("!!!");
        System.out.println("doAfterOther");
    }
}
