package com.spring.web.servlet.context;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.LinkedHashSet;
import java.util.Set;

public class MyAnnotationConfigServletWebServerApplicationContext extends AnnotationConfigServletWebServerApplicationContext {

    private final AnnotatedBeanDefinitionReader reader;

    private final ClassPathBeanDefinitionScanner scanner;

    private final Set<Class<?>> annotatedClasses = new LinkedHashSet<>();

    private String[] basePackages;

    /**
     * Create a new {@link AnnotationConfigServletWebServerApplicationContext} that needs
     * to be populated through {@link #register} calls and then manually
     * {@linkplain #refresh refreshed}.
     */
    public MyAnnotationConfigServletWebServerApplicationContext() {
        this.reader = new AnnotatedBeanDefinitionReader(this);
        this.scanner = new ClassPathBeanDefinitionScanner(this);
    }

    /**
     * Create a new {@link AnnotationConfigServletWebServerApplicationContext} with the
     * given {@code DefaultListableBeanFactory}. The context needs to be populated through
     * {@link #register} calls and then manually {@linkplain #refresh refreshed}.
     *
     * @param beanFactory the DefaultListableBeanFactory instance to use for this context
     */
    public MyAnnotationConfigServletWebServerApplicationContext(
            DefaultListableBeanFactory beanFactory) {
        super(beanFactory);
        this.reader = new AnnotatedBeanDefinitionReader(this);
        this.scanner = new ClassPathBeanDefinitionScanner(this);
    }

    /**
     * Create a new {@link AnnotationConfigServletWebServerApplicationContext}, deriving
     * bean definitions from the given annotated classes and automatically refreshing the
     * context.
     *
     * @param annotatedClasses one or more annotated classes, e.g. {@code @Configuration}
     *                         classes
     */
    public MyAnnotationConfigServletWebServerApplicationContext(
            Class<?>... annotatedClasses) {
        this();
        register(annotatedClasses);
        refresh();
    }

    /**
     * Create a new {@link AnnotationConfigServletWebServerApplicationContext}, scanning
     * for bean definitions in the given packages and automatically refreshing the
     * context.
     *
     * @param basePackages the packages to check for annotated classes
     */
    public MyAnnotationConfigServletWebServerApplicationContext(String... basePackages) {
        this();
        scan(basePackages);
        refresh();
    }

    /**
     * {@inheritDoc}
     * <p>
     * Delegates given environment to underlying {@link AnnotatedBeanDefinitionReader} and
     * {@link ClassPathBeanDefinitionScanner} members.
     */
    @Override
    public void setEnvironment(ConfigurableEnvironment environment) {
        super.setEnvironment(environment);
        this.reader.setEnvironment(environment);
        this.scanner.setEnvironment(environment);
    }

    /**
     * Provide a custom {@link BeanNameGenerator} for use with
     * {@link AnnotatedBeanDefinitionReader} and/or
     * {@link ClassPathBeanDefinitionScanner}, if any.
     * <p>
     * Default is
     * {@link org.springframework.context.annotation.AnnotationBeanNameGenerator}.
     * <p>
     * Any call to this method must occur prior to calls to {@link #register(Class...)}
     * and/or {@link #scan(String...)}.
     *
     * @param beanNameGenerator the bean name generator
     * @see AnnotatedBeanDefinitionReader#setBeanNameGenerator
     * @see ClassPathBeanDefinitionScanner#setBeanNameGenerator
     */
    public void setBeanNameGenerator(BeanNameGenerator beanNameGenerator) {
        this.reader.setBeanNameGenerator(beanNameGenerator);
        this.scanner.setBeanNameGenerator(beanNameGenerator);
        this.getBeanFactory().registerSingleton(
                AnnotationConfigUtils.CONFIGURATION_BEAN_NAME_GENERATOR,
                beanNameGenerator);
    }

    /**
     * Set the {@link ScopeMetadataResolver} to use for detected bean classes.
     * <p>
     * The default is an {@link AnnotationScopeMetadataResolver}.
     * <p>
     * Any call to this method must occur prior to calls to {@link #register(Class...)}
     * and/or {@link #scan(String...)}.
     *
     * @param scopeMetadataResolver the scope metadata resolver
     */
    public void setScopeMetadataResolver(ScopeMetadataResolver scopeMetadataResolver) {
        this.reader.setScopeMetadataResolver(scopeMetadataResolver);
        this.scanner.setScopeMetadataResolver(scopeMetadataResolver);
    }

    @Override
    protected void prepareRefresh() {
        this.scanner.clearCache();
        super.prepareRefresh();
    }

    protected void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
        super.postProcessBeanFactory(beanFactory);
        int i = beanFactory.getBeanDefinitionCount();
        System.out.println("postProcessBeanFactory = " + i);
    }
}
