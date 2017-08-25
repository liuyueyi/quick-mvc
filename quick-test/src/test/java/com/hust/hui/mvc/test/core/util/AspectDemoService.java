package com.hust.hui.mvc.test.core.util;

/**
 * Created by yihui on 2017/8/23.
 */
public class AspectDemoService {

    public void print(String ans) {
        System.out.println(ans);
    }

    public int add(int a, int b) {
        System.out.println("add");
        return a + b;
    }

}
