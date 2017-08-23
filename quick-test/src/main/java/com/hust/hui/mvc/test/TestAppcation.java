package com.hust.hui.mvc.test;

import com.hust.hui.mvc.core.annotation.field.Autowired;
import com.hust.hui.mvc.core.annotation.type.Component;
import com.hust.hui.mvc.test.api.IPrint;

/**
 * Created by yihui on 2017/8/19.
 */
@Component
public class TestAppcation {

    @Autowired
    private IPrint consolePrint;

    @Autowired("filePrint")
    private IPrint print;

    public void drawText() {
        consolePrint.print("want consolePrint!");
        System.out.println("-------------------split----------------");


        print.print("want filePrint!");
        System.out.println("-------------------split----------------");
    }




}
