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


    @Before
    public void setup() throws Exception {
        apc = new ApplicationContext();
        testAppcation = apc.getBean("testAppcation", TestAppcation.class);
    }

    @Test
    public void testApc() throws Exception {
        testAppcation.drawText();
        System.out.println("over");
    }

}
