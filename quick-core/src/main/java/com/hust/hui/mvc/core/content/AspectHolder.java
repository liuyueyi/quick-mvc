package com.hust.hui.mvc.core.content;

import com.hust.hui.mvc.core.aop.process.AfterProcess;
import com.hust.hui.mvc.core.aop.process.AroundProcess;
import com.hust.hui.mvc.core.aop.process.BeforeProcess;
import com.hust.hui.mvc.core.aop.process.ProcessCollect;

import java.util.List;
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


    public List<BeforeProcess> beforeProcesses;
    public List<AfterProcess> afterProcesses;
    public List<AroundProcess> aroundProcesses;


    public List<BeforeProcess> getBeforeProcess() {
        return beforeProcesses;
    }


    public List<AfterProcess> getAfterProcess() {
        return afterProcesses;
    }


    public List<AroundProcess> getAroundProcess() {
        return aroundProcesses;
    }
}


