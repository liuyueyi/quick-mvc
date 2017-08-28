package com.hust.hui.mvc.test.core.util;

import com.hust.hui.mvc.core.annotation.type.Service;
import com.hust.hui.mvc.test.aspect.LogDot;

/**
 * Created by yihui on 2017/8/23.
 */
@Service
public class AspectDemoService {

    @LogDot
    public void print(String ans) {
        System.out.println(ans);
    }

    @LogDot
    public int add(int a, int b) {
        System.out.println("add");
        return a + b;
    }

}
