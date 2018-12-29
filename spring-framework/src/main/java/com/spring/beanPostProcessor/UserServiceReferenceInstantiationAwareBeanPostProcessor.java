package com.spring.beanPostProcessor;

import com.spring.annatation.UserServiceReferenceAnnotation;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.InjectionMetadata;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.lang.Nullable;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class UserServiceReferenceInstantiationAwareBeanPostProcessor implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {
    private BeanFactory beanFactory;
    private final ConcurrentMap<String, UserServiceInjectionMetadata> injectionMetadataCache =
            new ConcurrentHashMap<String, UserServiceInjectionMetadata>(256);

    public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName)
            throws BeansException {
        UserServiceInjectionMetadata injectionMetadata = buildReferenceMetadata(bean.getClass(), beanName, pvs);
        try {
            injectionMetadata.inject(bean, beanName, pvs);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    private UserServiceInjectionMetadata buildReferenceMetadata(final Class<?> beanClass, String beanName, PropertyValues pvs) {
        String key = (beanName == null || "".equals(beanName) ? beanName : beanClass.getName());
        UserServiceInjectionMetadata userServiceInjectionMetadata = this.injectionMetadataCache.get(key);
        if (InjectionMetadata.needsRefresh(userServiceInjectionMetadata, beanClass)) {
            synchronized (this.injectionMetadataCache) {
                userServiceInjectionMetadata = this.injectionMetadataCache.get(key);
                if (InjectionMetadata.needsRefresh(userServiceInjectionMetadata, beanClass)) {
                    if (userServiceInjectionMetadata != null) {
                        userServiceInjectionMetadata.clear(pvs);
                    }
                    Collection<UserServiceFieldElement> fieldElements = findReferenceMetadata(beanClass);
                    userServiceInjectionMetadata = new UserServiceInjectionMetadata(beanClass, fieldElements);
                    this.injectionMetadataCache.put(key, userServiceInjectionMetadata);

                }
            }
        }
        return userServiceInjectionMetadata;
    }

    private List<UserServiceFieldElement> findReferenceMetadata(Class<?> clazz) {
        final List<UserServiceFieldElement> elements = new LinkedList<UserServiceFieldElement>();
        ReflectionUtils.doWithFields(clazz, new ReflectionUtils.FieldCallback() {
            @Override
            public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
                UserServiceReferenceAnnotation userServiceReferenceAnnotation = AnnotationUtils.getAnnotation(field, UserServiceReferenceAnnotation.class);
                if (userServiceReferenceAnnotation == null) return;
                elements.add(new UserServiceFieldElement(field, userServiceReferenceAnnotation));
            }
        }, new ReflectionUtils.FieldFilter() {
            @Override
            public boolean matches(Field field) {
                return !Modifier.isStatic(field.getModifiers());
            }
        });
        return elements;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    private static class UserServiceInjectionMetadata extends InjectionMetadata {
        public UserServiceInjectionMetadata(Class<?> targetClass, Collection<UserServiceFieldElement> elements) {
            super(targetClass, combine(elements));
        }

        private static <T> Collection<T> combine(Collection<? extends T>... elements) {
            List<T> allElements = new ArrayList<T>();
            for (Collection<? extends T> e : elements) {
                allElements.addAll(e);
            }
            return allElements;
        }
    }

    private class UserServiceFieldElement extends InjectionMetadata.InjectedElement {
        private final UserServiceReferenceAnnotation userServiceReferenceAnnotation;

        protected UserServiceFieldElement(Field field, UserServiceReferenceAnnotation userServiceReferenceAnnotation) {
            super(field, null);
            this.userServiceReferenceAnnotation = userServiceReferenceAnnotation;
        }

        protected void inject(Object target, @Nullable String requestingBeanName, @Nullable PropertyValues pvs)
                throws Throwable {

            if (this.isField) {
                Field field = (Field) this.member;
                ReflectionUtils.makeAccessible(field);
                Object object = getObject(field);
                field.set(target, object);
            } else {
                if (checkPropertySkipping(pvs)) {
                    return;
                }
                try {
                    Method method = (Method) this.member;
                    ReflectionUtils.makeAccessible(method);
                    method.invoke(target, getResourceToInject(target, requestingBeanName));
                } catch (InvocationTargetException ex) {
                    throw ex.getTargetException();
                }
            }
        }

        private Object getObject(Field field) {
            if (beanFactory.containsBean(field.getName())) return beanFactory.getBean(field.getName());
            String[] names = BeanFactoryUtils.beanNamesForTypeIncludingAncestors((ListableBeanFactory) beanFactory, field.getType());
            if (names.length > 1 || names.length == 0) {
                StringBuffer buffer = new StringBuffer();
                buffer.append(field.getName()).append(" have too many beans or have no bean");
                throw new RuntimeException(buffer.toString());
            }
            return beanFactory.getBean(names[0]);
        }
    }
}
