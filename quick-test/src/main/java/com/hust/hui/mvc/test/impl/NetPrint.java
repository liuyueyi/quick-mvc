package com.hust.hui.mvc.test.impl;

import com.hust.hui.mvc.core.annotation.type.Service;
import com.hust.hui.mvc.test.LogDot;
import com.hust.hui.mvc.test.api.IPrint;

/**
 * Created by yihui on 2017/8/23.
 */
@Service("internetPrint")
public class NetPrint implements IPrint {

    @Override
    @LogDot
    public void print(String msg) {
        System.out.println("net print! msg: " + msg);
        privatePrint(msg);
    }


    private void privatePrint(String msg) {
        System.out.println("inner private print msg: " + msg);
    }
}
