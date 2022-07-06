package com.borovyk;

import com.borovyk.beans.Child1Bean;
import com.borovyk.beans.ParentBean;
import com.borovyk.context.ApplicationContext;
import com.borovyk.context.ApplicationContextImpl;


public class Main {

    public static void main(String[] args) {
        ApplicationContext context = new ApplicationContextImpl();
        var beanByType = context.getBean(Child1Bean.class);
        var beanByName = context.getBean("first", ParentBean.class);
        var beans = context.getAllBeans(ParentBean.class);
        System.out.println(beanByType);
        System.out.println(beanByName);
        System.out.println(beans);
    }

}
