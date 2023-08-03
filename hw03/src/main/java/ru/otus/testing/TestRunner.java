package ru.otus.testing;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Stream;

public class TestRunner {

    public static void run(String className) throws Exception {
        Class<?> clazz = Class.forName(className);
        int succeed = 0;
        int failed = 0;
        StringBuilder succeedInfo = new StringBuilder();
        StringBuilder failedInfo = new StringBuilder();

        List<Method> beforeMethods = getMethods(clazz, Before.class);
        List<Method> testMethods = getMethods(clazz, Test.class);
        List<Method> afterMethods = getMethods(clazz, After.class);

        for (Method test : testMethods) {
            Constructor<?> constructor = clazz.getConstructor();
            Object instance = constructor.newInstance();

            for (Method before : beforeMethods) {
                runForMethod(instance, before, true);
            }

            boolean testResult = runForMethod(instance, test, false);
            if (testResult) {
                succeed ++;
                succeedInfo.append("\n\t").append(test.getName());
            } else {
                failed ++;
                failedInfo.append("\n\t").append(test.getName());
            }

            for (Method after : afterMethods) {
                runForMethod(instance, after, true);
            }
        }

        String message = MessageFormat.format(
                "\nTests for {0}:\nsucceed {1}{2}\nfailed {3}{4}",
                className, succeed, succeedInfo, failed, failedInfo
        );
        System.out.println(message);
    }

    private static List<Method> getMethods(Class<?> clazz,
                                           Class<? extends Annotation> annotation) {
        Method[] methods = clazz.getDeclaredMethods();
        Stream<Method> filteredMethods =
                Arrays.stream(methods).filter(m -> m.isAnnotationPresent(annotation));
        return filteredMethods.toList();
    }

    private static boolean runForMethod(Object instance,
                                        Method method,
                                        boolean abortOnError
                                        ) {
        Boolean result = null;
        try {
            method.setAccessible(true);
                method.invoke(instance);
                result = true;
            } catch (Exception e) {
                if (abortOnError) {
                    e.getCause().printStackTrace();
                } else {
                    result = false;
                }
            }
        return result;
    }
}
