package com.hust.hui.mvc.core.exception;

/**
 * Created by yihui on 2017/8/23.
 */
public class BeanNotFoundException extends RuntimeException {

    public BeanNotFoundException(String msg) {
        super(msg);
    }

    public BeanNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
