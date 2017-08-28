//package com.hust.hui.mvc.test.core.util;
//
//import com.hust.hui.mvc.core.aop.JoinPoint;
//import com.hust.hui.mvc.core.aop.ProceedingJoinPoint;
//import com.hust.hui.mvc.core.aop.process.AfterProcess;
//import com.hust.hui.mvc.core.aop.process.AroundProcess;
//import com.hust.hui.mvc.core.aop.process.BeforeProcess;
//import com.hust.hui.mvc.core.content.AspectHolder;
//import com.hust.hui.mvc.core.proxy.CglibProxyFactory;
//import com.hust.hui.mvc.test.PrintProxy;
//import com.hust.hui.mvc.test.api.IPrint;
//import com.hust.hui.mvc.test.impl.ConsolePrint;
//import org.junit.Test;
//
//import java.lang.reflect.Proxy;
//import java.util.Arrays;
//
///**
// * Created by yihui on 2017/8/23.
// */
//public class AspectTest {
//
//
//    public void before1(JoinPoint joinPoint) {
//        System.out.println("before1! point: " + Arrays.asList(joinPoint.getArgs()));
//    }
//
//    public void before2(JoinPoint joinPoint) {
//        System.out.println("before2! point: " + Arrays.asList(joinPoint.getArgs()));
//    }
//
//    public void after1(JoinPoint joinPoint) {
//        System.out.println("after! point: " + Arrays.asList(joinPoint.getArgs()));
//    }
//
//    public void after2(JoinPoint joinPoint) {
//        System.out.println("after2! point: " + Arrays.asList(joinPoint.getArgs()));
//    }
//
//
//    public Object around1(ProceedingJoinPoint joinPoint) throws Throwable {
//        System.out.println("around1! point: " + Arrays.asList(joinPoint.getArgs()));
//        Object result = joinPoint.proceed();
//        System.out.println("around1 over! ans: " + result);
//        return result;
//    }
//
//
//    public Object around2(ProceedingJoinPoint joinPoint) throws Throwable {
//        System.out.println("around2! point: " + Arrays.asList(joinPoint.getArgs()));
//        Object result = joinPoint.proceed();
//        System.out.println("around2 over! ans: " + result);
//        return result;
//    }
//
//
//    public void print() {
//        System.out.println("start-------");
//
//        System.out.println("end---------");
//    }
//
//    @Test
//    public void testAspect() throws NoSuchMethodException {
//        BeforeProcess b1 = new BeforeProcess();
//        BeforeProcess b2 = new BeforeProcess();
//        AfterProcess a1 = new AfterProcess();
//        AfterProcess a2 = new AfterProcess();
//        AroundProcess r1 = new AroundProcess();
//        AroundProcess r2 = new AroundProcess();
//
//
//        b1.setAspect(this);
//        b1.setMethod(this.getClass().getMethod("before1", JoinPoint.class));
//
//        b2.setAspect(this);
//        b2.setMethod(this.getClass().getMethod("before2", JoinPoint.class));
//
//        a1.setAspect(this);
//        a1.setMethod(this.getClass().getMethod("after1", JoinPoint.class));
//
//        a2.setAspect(this);
//        a2.setMethod(this.getClass().getMethod("after2", JoinPoint.class));
//
//
//        r1.setAspect(this);
//        r1.setMethod(this.getClass().getMethod("around1", ProceedingJoinPoint.class));
//
//
//        r2.setAspect(this);
//        r2.setMethod(this.getClass().getMethod("around2", ProceedingJoinPoint.class));
//
//
//        AspectHolder.getInstance().beforeProcesses = Arrays.asList(b1, b2);
//        AspectHolder.getInstance().afterProcesses = Arrays.asList(a1, a2);
//        AspectHolder.getInstance().aroundProcesses = Arrays.asList(r1, r2);
//
//
//        AspectDemoService apc = CglibProxyFactory.getProxy(AspectDemoService.class);
//        apc.print("hello world");
//
//        int ans = apc.add(10, 20);
//        System.out.println("ans : " + ans);
//
//    }
//
//
//
//    @Test
//    public void testJdkProxy() {
//        ConsolePrint p = new ConsolePrint();
//        PrintProxy proxy = new PrintProxy(p);
//
//        IPrint print = (IPrint) Proxy.newProxyInstance(this.getClass().getClassLoader(), p.getClass().getInterfaces(), proxy);
//        print.print("hello world");
//    }
//}
