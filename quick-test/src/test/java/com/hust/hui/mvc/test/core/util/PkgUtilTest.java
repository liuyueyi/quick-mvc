package com.hust.hui.mvc.test.core.util;

import com.hust.hui.mvc.core.util.PkgUtil;
import org.junit.Test;

import java.util.Set;

/**
 * Created by yihui on 2017/8/19.
 */
public class PkgUtilTest {

    @Test
    public void testLoadClz() {

        String path = "com.hust.hui.mvc";
        Set<Class<?>> set = PkgUtil.getClzFromPkg(path);
        System.out.println(set);
    }

}
