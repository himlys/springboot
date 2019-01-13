package com.spring.factoryBean;

public class DefaultUserServiceFactoryBean extends AbstractUserServiceFactoryBean {
    public DefaultUserServiceFactoryBean(Class beanInterface) {
        super(beanInterface);
    }

    @Override
    protected UserServiceFactorySupport createUserServiceFactory() {
        UserServiceFactorySupport userServiceFactorySupport = new DefaultUserServiceFactory();
        userServiceFactorySupport.setBeanFactory(getBeanFactory());
        return userServiceFactorySupport;
    }
}
