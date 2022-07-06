package com.borovyk.beans;

import com.borovyk.annotation.Bean;

@Bean
public class Child3Bean implements ParentBean {

    @Override
    public void print() {
        System.out.println("Hello from third bean.");
    }

}
