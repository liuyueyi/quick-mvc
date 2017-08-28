package com.hust.hui.mvc.test.aspect;

/**
 * Created by yihui on 2017/8/25.
 */

import com.hust.hui.mvc.core.aop.JoinPoint;
import com.hust.hui.mvc.core.aop.ProceedingJoinPoint;
import com.hust.hui.mvc.core.aop.anno.After;
import com.hust.hui.mvc.core.aop.anno.Around;
import com.hust.hui.mvc.core.aop.anno.Aspect;
import com.hust.hui.mvc.core.aop.anno.Before;

import java.util.Arrays;

@Aspect
public class LogAspect {

    @Before(LogDot.class)
    public void before1(JoinPoint joinPoint) {
        System.out.println("before1! point: " + Arrays.asList(joinPoint.getArgs()));
    }

    @Before(LogDot.class)
    public void before2(JoinPoint joinPoint) {
        System.out.println("before2! point: " + Arrays.asList(joinPoint.getArgs()));
    }

    @After(LogDot.class)
    public void after1(JoinPoint joinPoint) {
        System.out.println("after! point: " + Arrays.asList(joinPoint.getArgs()));
    }

    @After(LogDot.class)
    public void after2(JoinPoint joinPoint) {
        System.out.println("after2! point: " + Arrays.asList(joinPoint.getArgs()));
    }


    @Around(LogDot.class)
    public Object around1(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("around1! point: " + Arrays.asList(joinPoint.getArgs()));
        Object result = joinPoint.proceed();
        System.out.println("around1 over! ans: " + result);
        return result;
    }

    @Around(LogDot.class)
    public Object around2(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("around2! point: " + Arrays.asList(joinPoint.getArgs()));
        Object result = joinPoint.proceed();
        System.out.println("around2 over! ans: " + result);
        return result;
    }
}
