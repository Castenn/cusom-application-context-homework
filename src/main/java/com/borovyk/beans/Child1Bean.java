package com.borovyk.beans;

import com.borovyk.annotation.Bean;

@Bean(name = "first")
public class Child1Bean implements ParentBean {

    @Override
    public void print() {
        System.out.println("Hello from first bean.");
    }

}
