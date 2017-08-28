package com.hust.hui.mvc.test.core.util;

import com.hust.hui.mvc.core.content.ApplicationContext;
import com.hust.hui.mvc.test.TestAppcation;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by yihui on 2017/8/23.
 */
public class BeanScanIocTest {

    private ApplicationContext apc;

    private TestAppcation testAppcation;

    private AspectDemoService aspectDemoService;


    @Before
    public void setup() throws Exception {
        apc = new ApplicationContext();
        testAppcation = apc.getBean("testAppcation", TestAppcation.class);


        aspectDemoService = apc.getBean("aspectDemoService", AspectDemoService.class);
    }

    @Test
    public void testApc() throws Exception {
        testAppcation.drawText();
        System.out.println("over");


        System.out.println("------------\n");
        aspectDemoService.print("aspectdemoservice------");
        System.out.println("------------\n");


        System.out.println("------------\n");
        aspectDemoService.add(10, 20);
        System.out.println("-------------\n");
    }

}
