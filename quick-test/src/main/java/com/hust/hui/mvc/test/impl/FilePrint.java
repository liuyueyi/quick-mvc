package com.hust.hui.mvc.test.impl;

import com.hust.hui.mvc.core.annotation.type.Service;
import com.hust.hui.mvc.test.LogDot;
import com.hust.hui.mvc.test.api.IPrint;

/**
 * Created by yihui on 2017/8/23.
 */
@Service
public class FilePrint implements IPrint {

    @Override
    @LogDot
    public void print(String msg) {
        System.out.println("file print! msg: " + msg);
    }
}
