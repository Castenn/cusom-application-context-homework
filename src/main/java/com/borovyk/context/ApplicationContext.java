package com.borovyk.context;

import java.util.Map;

public interface ApplicationContext {

    <T> T getBean(Class<T> beanType);

    <T> T getBean(String name, Class<T> beanType);

    <T> Map<String, ? extends T> getAllBeans(Class<T> beanType);

}
