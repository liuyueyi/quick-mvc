package com.hust.hui.mvc.core.util;

import com.sun.deploy.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashSet;
import java.util.Properties;
import java.util.Set;

/**
 * 从配置文件中获取基本信息
 * <p>
 * <p>
 * Created by yihui on 2017/8/19.
 */
public class ConfUtil {


    /**
     * 加载配置文件
     *
     * @return
     * @throws IOException
     */
    public static Properties loadConf(String conf) throws IOException {
        InputStream inputStream = ConfUtil.class.getClassLoader().getResourceAsStream(conf);
        Properties properties = new Properties();
        properties.load(inputStream);
        return properties;
    }


    /**
     * 根据包扫描对应目录下的所有类
     *
     * @param path
     * @return
     * @throws IOException
     */
    public static Set<Class<?>> loadScanClasses(String path) throws IOException {
        String[] paths = StringUtils.splitString(path, ",");

        Set<Class<?>> set = new LinkedHashSet<>();
        for (String p : paths) {
            set.addAll(PkgUtil.getClzFromPkg(p));
        }

        return set;
    }

}
