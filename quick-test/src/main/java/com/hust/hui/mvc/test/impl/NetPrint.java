package com.hust.hui.mvc.test.impl;

import com.hust.hui.mvc.core.annotation.type.Service;
import com.hust.hui.mvc.test.api.IPrint;

/**
 * Created by yihui on 2017/8/23.
 */
@Service("internetPrint")
public class NetPrint implements IPrint {
    @Override
    public void print(String msg) {
        System.out.println("net print! msg: " + msg);
    }
}
