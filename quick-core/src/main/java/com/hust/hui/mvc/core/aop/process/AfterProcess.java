package com.hust.hui.mvc.core.aop.process;

import com.hust.hui.mvc.core.aop.JoinPoint;
import lombok.Data;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by yihui on 2017/8/23.
 */
@Data
public class AfterProcess implements IAopProcess, Comparable<AfterProcess> {

    private Object aspect;

    private Method method;

    private JoinPoint joinPoint;

    private Object result;

    /**
     * 优先级
     */
    private int order;

    public AfterProcess() {
    }

    public AfterProcess(AfterProcess process) {
        aspect = process.getAspect();
        method = process.getMethod();
    }

    public void process() throws InvocationTargetException, IllegalAccessException {
        if (method.getParameters().length > 1) {
            method.invoke(aspect, joinPoint, result);
        } else {
            method.invoke(aspect, joinPoint);
        }
    }

    @Override
    public int compareTo(AfterProcess o) {
        return order - o.getOrder();
    }
}
