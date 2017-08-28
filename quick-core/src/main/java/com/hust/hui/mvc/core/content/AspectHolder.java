package com.hust.hui.mvc.core.content;

import com.hust.hui.mvc.core.aop.process.ProcessCollect;

import java.util.Map;

/**
 * Created by yihui on 2017/8/23.
 */
public class AspectHolder {

    private static class Inner {
        private static AspectHolder instance = new AspectHolder();
    }

    public static AspectHolder getInstance() {
        return Inner.instance;
    }

    private AspectHolder() {
    }


    public Map<Class, ProcessCollect> processCollectMap;
}


