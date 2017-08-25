package com.hust.hui.mvc.core.aop.process;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 切面执行器集合
 * <p>
 * Created by yihui on 2017/8/24.
 */
@Data
public class ProcessCollect {

    private List<BeforeProcess> beforeList = new ArrayList<>();


    private List<AroundProcess> aroundList = new ArrayList<>();


    private List<AfterProcess> afterList = new ArrayList<>();


    public void addBeforeProcess(BeforeProcess b) {
        beforeList.add(b);
    }


    public void addAroundProcess(AroundProcess r) {
        aroundList.add(r);
    }


    public void addAfterProcess(AfterProcess a) {
        afterList.add(a);
    }
}
