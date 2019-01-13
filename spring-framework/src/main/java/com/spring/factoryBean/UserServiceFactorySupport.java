package com.spring.factoryBean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.cglib.proxy.Proxy;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

public abstract class UserServiceFactorySupport implements BeanClassLoaderAware, BeanFactoryAware {
    private ClassLoader classLoader;
    private BeanFactory beanFactory;

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
    public <T> T getProxy(Class<T> beanInterface){
        return (T)Proxy.newProxyInstance(classLoader,new Class[]{beanInterface},(proxy,method,args)->{
            UserServiceFactoryBeanTarget target = beanFactory.getBean(UserServiceFactoryBeanTarget.class);
            String mName = method.getName();
            Class [] methodParameterTypes = method.getParameterTypes();
            Method m = ReflectionUtils.findMethod(UserServiceFactoryBeanTarget.class,mName,methodParameterTypes);
            return m.invoke(target,args);
        });
    }
}
