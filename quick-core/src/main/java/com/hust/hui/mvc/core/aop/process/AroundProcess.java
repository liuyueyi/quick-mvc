package com.hust.hui.mvc.core.aop.process;

import com.hust.hui.mvc.core.aop.ProceedingJoinPoint;
import lombok.Data;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by yihui on 2017/8/23.
 */
@Data
public class AroundProcess {

    private Object aspect;

    private Method method;

    private ProceedingJoinPoint joinPoint;

    public AroundProcess() {
    }

    public AroundProcess(AroundProcess process) {
        aspect = process.getAspect();
        method = process.getMethod();
    }

    public Object process() throws InvocationTargetException, IllegalAccessException {
        return method.invoke(aspect, joinPoint);
    }
}
