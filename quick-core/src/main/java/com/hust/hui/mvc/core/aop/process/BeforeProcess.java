package com.hust.hui.mvc.core.aop.process;

import com.hust.hui.mvc.core.aop.JoinPoint;
import lombok.Data;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by yihui on 2017/8/23.
 */
@Data
public class BeforeProcess {

    private Object aspect;

    private Method method;

    private JoinPoint joinPoint;

    public BeforeProcess() {
    }

    public BeforeProcess(BeforeProcess process) {
        aspect = process.getAspect();
        method = process.getMethod();
        joinPoint = null;
    }

    public void process() throws InvocationTargetException, IllegalAccessException {
        method.invoke(aspect, joinPoint);
    }

}
