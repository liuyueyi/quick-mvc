package com.hust.hui.mvc.core.exception;

/**
 * Created by yihui on 2017/8/23.
 */
public class BeanAlreadyDefinedException extends RuntimeException {

    public BeanAlreadyDefinedException(String message) {
        super(message);
    }
}
