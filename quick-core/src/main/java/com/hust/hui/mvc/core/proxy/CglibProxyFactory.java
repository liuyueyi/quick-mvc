package com.hust.hui.mvc.core.proxy;

import net.sf.cglib.proxy.Enhancer;

/**
 * Created by yihui on 2017/8/23.
 */
public class CglibProxyFactory {

    private static Enhancer enhancer = new Enhancer();

    private static CglibProxy cglibProxy = new CglibProxy();

    @SuppressWarnings("unchecked")
    public static <T> T getProxy(Class<T> clazz){
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(cglibProxy);
        return (T) enhancer.create();
    }
}
