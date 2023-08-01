package ru.otus.testing;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Id;
import ru.otus.annotations.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class TestRunner {

    public static void run(String className) throws Exception {
        Class<?> clazz = Class.forName(className);
        HashMap<Integer, ArrayList<Method>> grouped = groupMethods(clazz);
        StringBuilder info = new StringBuilder();

        for (Collection<Method> methods : grouped.values()) {
            Constructor<?> constructor = clazz.getConstructor();
            Object instance = constructor.newInstance();
            String beforeMsg = runForMethods(instance, methods, Before.class, true);
            String testMsg = runForMethods(instance, methods, Test.class, false);
            String afterMsg = runForMethods(instance, methods, After.class, true);
            String msg = beforeMsg + "\n" + testMsg + "\n" + afterMsg + "\n\n";
            info.append(msg);
        }

        System.out.println(info);
    }

    private static HashMap<Integer, ArrayList<Method>> groupMethods(Class<?> clazz) {
        HashMap<Integer, ArrayList<Method>> grouped = new HashMap<Integer, ArrayList<Method>>();
        Method[] methods = clazz.getDeclaredMethods();

        for (Method method : methods) {
            if (method.isAnnotationPresent(Id.class)) {
                Annotation annotation = method.getDeclaredAnnotation(Id.class);
                Integer key = annotation.hashCode();
                ArrayList<Method> value;
                if (grouped.containsKey(key)) {
                    value = grouped.get(key);
                } else {
                    value = new ArrayList<Method>();
                    grouped.put(key, value);
                }
                value.add(method);
            }
        }
        return grouped;
    }

    private static String runForMethods(Object instance,
                                        Collection<Method> methods,
                                        Class<? extends Annotation> annotation,
                                        boolean abortOnError) {
        int succeed = 0;
        int failed = 0;
        StringBuilder succeedInfo = new StringBuilder();
        StringBuilder failedInfo = new StringBuilder();

        for (Method method : methods) {
            if (method.isAnnotationPresent(annotation)) {
                try {
                    method.setAccessible(true);
                    method.invoke(instance);
                    String succeedMsg = "\n\t\t" + method.getName();
                    succeedInfo.append(succeedMsg);
                    succeed ++;
                } catch (Exception e) {
                    if (abortOnError) {
                        e.getCause().printStackTrace();
                    } else {
                        String errMsg = "\n\t\t" + method.getName() + ": " + e.getCause();
                        failedInfo.append(errMsg);
                        failed ++;
                    }
                }
            }
        }
        String name = annotation.getSimpleName();
        String messageSucceed = MessageFormat.format(
                "{0}:\n\tsucceed {1}{2}", name, succeed, succeedInfo
        );
        String messageFailed = MessageFormat.format(
                "\n\tfailed {0}{1}", failed, failedInfo
        );
        return failed == 0 ? messageSucceed : messageSucceed + messageFailed;
    }
}
