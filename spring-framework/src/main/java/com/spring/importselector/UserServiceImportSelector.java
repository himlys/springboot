package com.spring.importselector;

import com.spring.annatation.UserServiceAnnotation;
import com.spring.annatation.UserServiceComponentScan;
import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.*;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.*;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.springframework.core.annotation.AnnotationUtils.findAnnotation;

public class UserServiceImportSelector implements ImportSelector {
    private static final String[] IMPORTS = {
            UserServiceImportBeanDefinitionRegistrar.class.getName()};

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return IMPORTS;
    }
}

class UserServiceImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        AnnotationAttributes attributes = AnnotationAttributes.fromMap(
                importingClassMetadata.getAnnotationAttributes(UserServiceComponentScan.class.getName()));
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(UserServiceBeanDefinitionRegistryPostProcessor.class);
        String[] paths = attributes.getStringArray("path");
        Set<String> s = new HashSet<>();
        s.addAll(Arrays.asList(paths));
        builder.addPropertyValue("path", s);
        registry.registerBeanDefinition("UserServiceBeanDefinitionRegistryPostProcessor", builder.getBeanDefinition());
    }

}

class UserServiceBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor, ResourceLoaderAware, EnvironmentAware, BeanClassLoaderAware {
    private ResourceLoader resourceLoader;
    private Environment environment;
    private ClassLoader classLoader;

    public Set<String> getPath() {
        return path;
    }

    public void setPath(Set<String> path) {
        this.path = path;
    }

    private Set<String> path;

    UserServiceBeanDefinitionRegistryPostProcessor() {
    }

    UserServiceBeanDefinitionRegistryPostProcessor(Set<String> path) {
        this.path = path;
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        UserServiceClassPathBeanDefinitionScanner scanner = new UserServiceClassPathBeanDefinitionScanner(registry,
                false, environment, resourceLoader, classLoader);
        scanner.addIncludeFilter(new AnnotationTypeFilter(UserServiceAnnotation.class));
        scanner.scan(path.toArray(new String[path.size()]));
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }
}

class UserServiceClassPathBeanDefinitionScanner extends ClassPathBeanDefinitionScanner {
    public UserServiceClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry, ClassLoader classLoader) {
        super(registry);
        this.registry = registry;
        this.classLoader = classLoader;
    }

    private final ClassLoader classLoader;
    private final BeanDefinitionRegistry registry;
    private ScopeMetadataResolver scopeMetadataResolver = new AnnotationScopeMetadataResolver();

    public UserServiceClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry, boolean useDefaultFilters,
                                                     Environment environment, @Nullable ResourceLoader resourceLoader
            , ClassLoader classLoader) {
        super(registry, useDefaultFilters, environment, resourceLoader);
        this.registry = registry;
        this.classLoader = classLoader;

    }

    private BeanNameGenerator beanNameGenerator = new AnnotationBeanNameGenerator();

    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
        Assert.notEmpty(basePackages, "At least one base package must be specified");
        Set<BeanDefinitionHolder> beanDefinitions = new LinkedHashSet<>();

        for (String basePackage : basePackages) {
            Set<BeanDefinition> candidates = findCandidateComponents(basePackage);
            for (BeanDefinition candidate : candidates) {
                ScopeMetadata scopeMetadata = this.scopeMetadataResolver.resolveScopeMetadata(candidate);
                candidate.setScope(scopeMetadata.getScopeName());
                String beanName = this.beanNameGenerator.generateBeanName(candidate, this.registry);
                if (candidate instanceof AbstractBeanDefinition) {
                    postProcessBeanDefinition((AbstractBeanDefinition) candidate, beanName);
                    UserServiceAnnotation service = findAnnotation(ClassUtils.resolveClassName(candidate.getBeanClassName(), classLoader), UserServiceAnnotation.class);
                    String userName = service.userName();
                    beanName = "".equals(service.name()) ? beanName : service.name();
                    MutablePropertyValues mutablePropertyValues = new MutablePropertyValues();
                    mutablePropertyValues.add("userName", userName);
                    ((AbstractBeanDefinition) candidate).setPropertyValues(mutablePropertyValues);
                }
                if (candidate instanceof AnnotatedBeanDefinition) {
                    AnnotationConfigUtils.processCommonDefinitionAnnotations((AnnotatedBeanDefinition) candidate);
                }
                if (checkCandidate(beanName, candidate)) {
                    BeanDefinitionHolder definitionHolder = new BeanDefinitionHolder(candidate, beanName);
                    definitionHolder =
                            applyScopedProxyMode(scopeMetadata, definitionHolder, this.registry);
                    beanDefinitions.add(definitionHolder);
                    registerBeanDefinition(definitionHolder, this.registry);
                }
            }
        }
        return beanDefinitions;
    }

    static BeanDefinitionHolder applyScopedProxyMode(
            ScopeMetadata metadata, BeanDefinitionHolder definition, BeanDefinitionRegistry registry) {

        ScopedProxyMode scopedProxyMode = metadata.getScopedProxyMode();
        if (scopedProxyMode.equals(ScopedProxyMode.NO)) {
            return definition;
        }
        boolean proxyTargetClass = scopedProxyMode.equals(ScopedProxyMode.TARGET_CLASS);
        return ScopedProxyCreator.createScopedProxy(definition, registry, proxyTargetClass);
    }

}

class ScopedProxyCreator {

    public static BeanDefinitionHolder createScopedProxy(
            BeanDefinitionHolder definitionHolder, BeanDefinitionRegistry registry, boolean proxyTargetClass) {

        return ScopedProxyUtils.createScopedProxy(definitionHolder, registry, proxyTargetClass);
    }

    public static String getTargetBeanName(String originalBeanName) {
        return ScopedProxyUtils.getTargetBeanName(originalBeanName);
    }

}
