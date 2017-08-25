package com.hust.hui.mvc.test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by yihui on 2017/8/24.
 */
public class PrintProxy implements InvocationHandler {

    private Object obj;

    public PrintProxy(Object obj) {
        this.obj = obj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before");
        Object ans = method.invoke(obj, args);
        System.out.println("after");
        return ans;
    }
}
