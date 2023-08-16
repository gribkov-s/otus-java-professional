package ru.otus;

import com.google.common.collect.Streams;
import javafx.util.Pair;
import ru.otus.annotations.Log;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressWarnings("java:S106")
public class IoC {

    private IoC() {}

    static Object createWithLogging(Class<?> clazz) {
        Constructor<?> constructor = clazz.getConstructors()[0];
        try {
            InvocationHandler handler =
                    new InvHandlerByAnnotation(constructor.newInstance(), Log.class);
            return Proxy.newProxyInstance(
                    IoC.class.getClassLoader(),
                    clazz.getInterfaces(),
                    handler);
        } catch (IllegalAccessException |
                    InvocationTargetException |
                        InstantiationException e) {
            throw new RuntimeException(e);
        }
    }

    static class InvHandlerByAnnotation implements InvocationHandler {
        private final Object object;
        private final List<Pair<String, Class<?>[]>> methodsToHandle;

        InvHandlerByAnnotation(Object obj, Class<? extends Annotation> annotation) {
            this.object = obj;
            this.methodsToHandle =
                    Arrays.stream(obj.getClass().getMethods())
                            .filter(m -> m.isAnnotationPresent(annotation))
                            .map(m -> new Pair<String, Class<?>[]>(
                                    m.getName(), m.getParameterTypes()))
                            .toList();
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            boolean isHandling = methodsToHandle.stream().anyMatch(m ->
                    m.getKey().equals(method.getName()) &&
                            Arrays.equals(m.getValue(), method.getParameterTypes()));

            if (isHandling) {
                StringBuilder msg = new StringBuilder()
                        .append("executed method ")
                        .append(method.getName())
                        .append(": ");

                if (args != null) {
                    var argsValues = Arrays.stream(args).map(Object::toString);
                    var argsNames = Arrays.stream(
                            method.getParameters()).map(Parameter::getName);
                    var argsMsg = Streams.zip(argsNames, argsValues, Pair::new)
                            .map(t -> t.getKey() + " = " + t.getValue())
                            .collect(Collectors.joining(", "));
                    msg.append(argsMsg);
                } else {
                    msg.append("no args");
                }
                System.out.println(msg);
            }
            return method.invoke(object, args); //method.invoke(object, args);
        }
    }
}
