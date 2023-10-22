package ru.otus.appcontainer;

import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.stream.Stream;

@SuppressWarnings("squid:S1068")
public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) {
        processConfig(initialConfigClass);
    }

    private static class MethodComparator implements Comparator<Method> {
        public int compare(Method m1, Method m2) {
            var m1o = Integer.valueOf(m1.getAnnotation(AppComponent.class).order());
            var m2o = Integer.valueOf(m2.getAnnotation(AppComponent.class).order());
            return m1o.compareTo(m2o);
        }
    }

    private void processConfig(Class<?> configClass) {
        checkConfigClass(configClass);
        try {
            Object config = configClass.getDeclaredConstructor().newInstance();

            Stream<Method> methods = Arrays.stream(configClass.getDeclaredMethods())
                    .filter(m -> m.isAnnotationPresent(AppComponent.class))
                    .sorted(new MethodComparator());

            Map<Class<?>, Object> objects = new HashMap<>();

            for (Method method : methods.toList()) {
                AppComponent component = method.getAnnotation(AppComponent.class);
                String componentName = component.name();
                Object object;
                List<? extends Class<?>> argTypes = Arrays.stream(method.getParameters())
                        .map(Parameter::getType)
                        .toList();

                if (argTypes.isEmpty()) {
                    object = method.invoke(config);
                } else {
                    List<Object> args = new ArrayList<>();
                    for (Class<?> argType: argTypes) {
                        Object arg = objects.get(argType);
                        args.add(arg);
                    }
                    object = method.invoke(config, args.toArray());
                }

                Class<?>[] types = object.getClass().getInterfaces();
                for (Class<?> tp : types) {
                    objects.put(tp, object);
                }
                appComponentsByName.put(componentName, object);
                appComponents.add(object);
                checkDuplicateNames(configClass.getSimpleName(), componentName);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(
                    String.format(
                            "Given class is not config %s",
                            configClass.getName()));
        }
    }

    private void checkDuplicateNames(String containerName, String componentName) {
        int componentsNum = appComponents.size();
        int componentsByNameNum = appComponentsByName.size();
        if (componentsNum != componentsByNameNum) throw new RuntimeException(
                String.format(
                        "App configuration %s contains more than expected components: %s.",
                        containerName,
                        componentName));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <C> C getAppComponent(Class<C> componentClass) {
        Object[] componentInterfaces = componentClass.isInterface() ?
                new Object[]{componentClass} :
                Arrays.stream(componentClass.getInterfaces()).toArray();

        List<Object> components = appComponents.stream()
                .filter(o -> Arrays.equals(o.getClass().getInterfaces(), componentInterfaces))
                .toList();

        int componentsNum = components.size();

        if (componentsNum > 1) throw new RuntimeException(
                String.format(
                        "Container contains more than expected components: %s.",
                        componentClass.getSimpleName()));

        if (componentsNum == 0) {
            throw new RuntimeException(
                    String.format(
                            "Can not get %s from container %s.",
                            componentClass.getSimpleName(),
                            this.getClass().getSimpleName()));
        } else {
            return (C) components.get(0);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <C> C getAppComponent(String componentName) {

        boolean componentIsExists = appComponentsByName.containsKey(componentName);

        if (!componentIsExists) {
            throw new RuntimeException(
                    String.format(
                            "Can not get %s from container %s.",
                            componentName,
                            this.getClass().getSimpleName()));
        } else {
            return (C) this.appComponentsByName.get(componentName);
        }
    }
}
