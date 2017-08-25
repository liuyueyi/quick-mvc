package com.hust.hui.mvc.core.proxy;

import com.hust.hui.mvc.core.aop.JoinPoint;
import com.hust.hui.mvc.core.aop.ProceedingJoinPoint;
import com.hust.hui.mvc.core.aop.process.AfterProcess;
import com.hust.hui.mvc.core.aop.process.AroundProcess;
import com.hust.hui.mvc.core.aop.process.BeforeProcess;
import com.hust.hui.mvc.core.content.AspectHolder;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.apache.commons.collections4.CollectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by yihui on 2017/8/23.
 */
public class CglibProxy implements MethodInterceptor {


    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {

        JoinPoint joinPoint = new JoinPoint();
        joinPoint.setArgs(args);
        joinPoint.setMethod(method);
        joinPoint.setObj(o);

        processBeforeAdvice(joinPoint);

        Object result = processAround(joinPoint, methodProxy);

        processAfterAdvice(joinPoint, result);
        return result;
    }


    private void processBeforeAdvice(JoinPoint joinPoint) {
        String methodName = joinPoint.getMethod().getName();
        if (!("print".equalsIgnoreCase(methodName) || "add".equalsIgnoreCase(methodName))) {
            return;
        }

        List<BeforeProcess> beforeList = null;
        Annotation[] anos = joinPoint.getMethod().getAnnotations();
        for (Annotation ano: anos) {
            if (AspectHolder.getInstance().processCollectMap.containsKey(ano.annotationType())) {
                beforeList = AspectHolder.getInstance().processCollectMap.get(ano.annotationType()).getBeforeList();
            }
        }

        if (beforeList == null || beforeList.size() == 0) {
            return;
        }


        BeforeProcess temp;
        for (BeforeProcess b: beforeList) {
            temp = new BeforeProcess(b);
            temp.setJoinPoint(joinPoint);
            try {
                temp.process();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private void processAfterAdvice(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getMethod().getName();
        if (!("print".equalsIgnoreCase(methodName) || "add".equalsIgnoreCase(methodName))) {
            return;
        }


        List<AfterProcess> afterList = null;
        Annotation[] anos = joinPoint.getMethod().getAnnotations();
        for (Annotation ano: anos) {
            if (AspectHolder.getInstance().processCollectMap.containsKey(ano.annotationType())) {
                afterList = AspectHolder.getInstance().processCollectMap.get(ano.annotationType()).getAfterList();
            }
        }

        if(CollectionUtils.isEmpty(afterList)) {
            return;
        }


        AfterProcess temp;
        for(AfterProcess f: afterList) {
            temp = new AfterProcess(f);
            temp.setJoinPoint(joinPoint);
            temp.setResult(result);

            try {
                temp.process();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private Object processAround(JoinPoint joinPoint, MethodProxy proxy) throws Throwable {
        ProceedingJoinPoint proceddingJoinPoint = new ProceedingJoinPoint();
        proceddingJoinPoint.setArgs(joinPoint.getArgs());
        proceddingJoinPoint.setMethod(joinPoint.getMethod());
        proceddingJoinPoint.setObj(joinPoint.getObj());
        proceddingJoinPoint.setProxy(proxy);


        String methodName = joinPoint.getMethod().getName();
        if (!("print".equalsIgnoreCase(methodName) || "add".equalsIgnoreCase(methodName))) {
            return proxy.invokeSuper(joinPoint.getObj(), joinPoint.getArgs());
        }



        List<AroundProcess> aroundList = null;
        Annotation[] anos = joinPoint.getMethod().getAnnotations();
        for (Annotation ano: anos) {
            if (AspectHolder.getInstance().processCollectMap.containsKey(ano.annotationType())) {
                aroundList = AspectHolder.getInstance().processCollectMap.get(ano.annotationType()).getAroundList();
            }
        }

        if(CollectionUtils.isEmpty(aroundList)) {
            return proxy.invokeSuper(joinPoint.getObj(), joinPoint.getArgs());
        }


        Object result = null;
        AroundProcess temp;
        for(AroundProcess f: aroundList) {
            temp = new AroundProcess(f);
            temp.setJoinPoint(proceddingJoinPoint);

            try {
                result = temp.process();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return result;
    }
}
