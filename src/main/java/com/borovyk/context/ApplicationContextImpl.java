package com.borovyk.context;

import com.borovyk.annotation.Bean;
import com.borovyk.exception.NoSuchBeanException;
import com.borovyk.exception.NoUniqueBeanException;
import lombok.SneakyThrows;
import org.apache.commons.lang3.ClassUtils;
import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class ApplicationContextImpl implements ApplicationContext {

    private final Map<String, Object> storage = new HashMap<>();

    private static final String PACKAGE_NANE = "com.borovyk";

    public ApplicationContextImpl() {
        var reflections = new Reflections(PACKAGE_NANE);
        var beansTypes = reflections.getTypesAnnotatedWith(Bean.class);
        beansTypes.forEach(beanType -> {
            var nameField = beanType.getAnnotation(Bean.class).name();
            var simpleName = beanType.getSimpleName().charAt(0) + beanType.getSimpleName().substring(1);
            var name = nameField.isEmpty() ? simpleName : nameField;
            if (storage.containsKey(name)) {
                throw new NoUniqueBeanException();
            }
            storage.put(name, createInstance(beanType));
        });
    }

    @SneakyThrows
    private static <T> T createInstance(Class<T> classType) {
        return classType.getConstructor().newInstance();
    }

    @Override
    public <T> T getBean(Class<T> beanType) {
        Objects.requireNonNull(beanType);

        var foundBeans = storage.values().stream()
                .filter(bean -> ClassUtils.isAssignable(bean.getClass(), beanType))
                .toList();
        if (foundBeans.isEmpty()) {
            throw new NoSuchBeanException();
        }
        if (foundBeans.size() > 1) {
            throw new NoUniqueBeanException();
        }
        return beanType.cast(foundBeans.get(0));
    }

    @Override
    public <T> T getBean(String name, Class<T> beanType) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(beanType);

        var foundBean = storage.get(name);
        if (foundBean == null || !ClassUtils.isAssignable(foundBean.getClass(), beanType)) {
            throw new NoSuchBeanException();
        }
        return beanType.cast(foundBean);
    }

    @Override
    public <T> Map<String, ? extends T> getAllBeans(Class<T> beanType) {
        Objects.requireNonNull(beanType);

        return storage.entrySet().stream()
                .filter(entry -> ClassUtils.isAssignable(entry.getValue().getClass(), beanType))
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> beanType.cast(entry.getValue())));
    }

}
