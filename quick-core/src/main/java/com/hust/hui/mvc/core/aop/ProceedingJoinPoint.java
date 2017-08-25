package com.hust.hui.mvc.core.aop;

import lombok.Data;
import net.sf.cglib.proxy.MethodProxy;

/**
 * Created by yihui on 2017/8/23.
 */
@Data
public class ProceedingJoinPoint extends JoinPoint {

    private MethodProxy proxy;

    private Object result;


    // 是否已经执行过方法的标记
    private volatile boolean executed;


    public Object proceed() throws Throwable {
        if (!executed) {
            synchronized (this) {
                if (!executed) {
                    result = proxy.invokeSuper(getObj(), getArgs());
                    executed = true;
                }
            }
        }

        return result;
    }

}
