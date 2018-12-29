package com.spring.aop.aspectj.advisor;

import com.spring.aop.service.UserServiceDeclareParents;
import com.spring.aop.service.impl.UserServiceDeclareParentsImpl;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.DeclareParents;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class UserServiceAspectJ {
//    将UserServiceDeclareParentsImpl的函数添加给了DeclareParentsUserServiceImpl
    @DeclareParents(value = "com.spring.aop.service.impl.DeclareParentsUserServiceImpl", defaultImpl = UserServiceDeclareParentsImpl.class)
    public UserServiceDeclareParents userServiceDeclareParents;

    //    这个没有代理UserServiceProxyAnnotationClassImpl，有问题
    @Before(value = "execution(* com.spring.aop.service.impl.UserService*+.*(..)) && args()")
    public void userServiceBefore(JoinPoint point) {
        for (Object object : point.getArgs()) {
            if (object != null) System.out.println("param = " + object);
        }
        System.out.println("before userServiceProxyBefore");
    }

    @Before(value = "execution(* com.spring.aop.service.impl.UserService*+.*(..)) && args(hi)", argNames = "hi")
    public void userServiceBeforeArgs(String hi) {
        System.out.println("before userServiceProxyBefore args " + hi);
    }

    @Pointcut(value = "")
    public void userServicePointcut() {
        System.out.println("pointcut");
    }
}
