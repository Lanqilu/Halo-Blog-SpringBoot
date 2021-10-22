package com.halo.blog.controller.test;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @author Halo
 * @create 2021/10/22 下午 08:12
 * @description
 */
@Aspect
@Component
public class AopAdvice {

    /**
     * com.halo.blog.controller.test 包下 任意类、任意方法、任意参数
     * execution 表示在方法执行的时候触发。
     */
    @Pointcut("execution (* com.halo.blog.controller.test.*.*(..))")
    public void test() {

    }

    /**
     * 前置通知 @Before：在目标方法调用之前调用通知
     */
    @Before("test()")
    public void beforeAdvice() {
        System.out.println("前置通知, beforeAdvice...");
    }

    /**
     * 后置通知 @After：在目标方法完成之后调用通知
     */
    @After("test()")
    public void afterAdvice() {
        System.out.println("后置通知, afterAdvice...");
    }

    /**
     * 环绕通知 @Around：在被通知的方法调用之前和调用之后执行自定义的方法
     */
    @Around("test()")
    public void aroundAdvice(ProceedingJoinPoint proceedingJoinPoint) {
        System.out.println("环绕通知, before");
        try {
            proceedingJoinPoint.proceed();
        } catch (Throwable t) {
            t.printStackTrace();
        }
        System.out.println("环绕通知, after");
    }

}
