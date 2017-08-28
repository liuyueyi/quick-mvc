package com.hust.hui.mvc.core.aop.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by yihui on 2017/8/19.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface After {
    /**
     * 自定义的注解，方法上有这个注解，则会被改切面拦截
     * @return
     */
    Class value();

    /**
     * 排序，越小优先级越高
     * @return
     */
    int order() default 0;
}
