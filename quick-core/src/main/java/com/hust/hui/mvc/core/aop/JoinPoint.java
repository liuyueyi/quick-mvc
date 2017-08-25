package com.hust.hui.mvc.core.aop;

import lombok.Data;

import java.lang.reflect.Method;

/**
 * 包含具体服务执行者的相关信息
 *
 * Created by yihui on 2017/8/23.
 */
@Data
public class JoinPoint {

    private Object[] args;

    private Method method;

    private Object obj;
}
