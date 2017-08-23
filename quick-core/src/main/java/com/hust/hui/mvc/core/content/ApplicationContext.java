package com.hust.hui.mvc.core.content;

import com.hust.hui.mvc.core.util.ConfUtil;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by yihui on 2017/8/19.
 */
public class ApplicationContext {

    private static final String DEFAULT_CONF_NAME = "quickmvc.properties";

    private static final String SCAN_PKG_KEY = "scan";

    private Properties properties;

    public ApplicationContext() throws Exception {
        loadConf();
        String pkgs = properties.getProperty(SCAN_PKG_KEY);
        BeanFactory.getInstance().init(pkgs);
    }


    private void loadConf() throws IOException {
        properties = ConfUtil.loadConf(DEFAULT_CONF_NAME);
    }



    public Object[] getBean(String name) {
        return BeanFactory.getInstance().getBeanOfName(name);
    }


    public <T> T getBean(String name, Class<T> clz) {
        return BeanFactory.getInstance().getBeanOfName(name, clz);
    }


    public <T> T getBean(Class<T> clz) {
        return BeanFactory.getInstance().getBeanOfType(clz);
    }


    public boolean containBean(String name) {
        return BeanFactory.getInstance().containBean(name);
    }


    /**
     * 注册
     * @param name
     * @param bean
     * @return
     */
    public boolean register(String name, Object bean) {
        return BeanFactory.getInstance().registerBean(name, bean);
    }

}
