package com.hust.hui.mvc.core.aop.process;

import com.hust.hui.mvc.core.aop.JoinPoint;
import lombok.Data;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by yihui on 2017/8/23.
 */
@Data
public class BeforeProcess implements IAopProcess, Comparable<BeforeProcess> {

    /**
     * 切面类
     */
    private Object aspect;


    /**
     * 切面类中定义的Before方法
     */
    private Method method;


    /**
     * 被切面拦截的切点信息
     */
    private JoinPoint joinPoint;


    /**
     * 优先级
     */
    private int order;

    public BeforeProcess() {
    }

    public BeforeProcess(BeforeProcess process) {
        aspect = process.getAspect();
        method = process.getMethod();
        joinPoint = null;
    }


    /**
     * 执行切面方法
     *
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public void process() throws InvocationTargetException, IllegalAccessException {
        method.invoke(aspect, joinPoint);
    }

    @Override
    public int compareTo(BeforeProcess o) {
        return order - o.getOrder();
    }
}
