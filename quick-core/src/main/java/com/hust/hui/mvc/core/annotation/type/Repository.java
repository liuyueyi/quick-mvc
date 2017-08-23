package com.hust.hui.mvc.core.annotation.type;

import java.lang.annotation.*;

/**
 * Created by yihui on 2017/8/19.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Bean
public @interface Repository {

    String value() default "";

}
