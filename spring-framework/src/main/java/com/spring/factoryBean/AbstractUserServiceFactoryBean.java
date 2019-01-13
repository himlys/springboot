package com.spring.factoryBean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.util.Lazy;

public abstract class AbstractUserServiceFactoryBean<T> implements FactoryBean<T>, InitializingBean, BeanFactoryAware {
    private Lazy<T> bean;
    private boolean lazyInit = true;
    private final Class<? extends T> beanInterface;
    private UserServiceFactorySupport userServiceFactorySupport;
    private BeanFactory beanFactory;

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    public AbstractUserServiceFactoryBean(Class<? extends T> beanInterface) {
        this.beanInterface = beanInterface;
    }

    @Override
    public T getObject() throws Exception {
        return bean.get();
    }

    @Override
    public Class<?> getObjectType() {
        return beanInterface;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.userServiceFactorySupport = createUserServiceFactory();
        bean = Lazy.of(this.userServiceFactorySupport.getProxy(beanInterface));
        if (!lazyInit) {
            bean.get();
        }
    }

    protected abstract UserServiceFactorySupport createUserServiceFactory();

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
