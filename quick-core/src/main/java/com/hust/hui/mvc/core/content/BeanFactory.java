package com.hust.hui.mvc.core.content;

import com.hust.hui.mvc.core.annotation.field.Autowired;
import com.hust.hui.mvc.core.annotation.type.Bean;
import com.hust.hui.mvc.core.aop.anno.After;
import com.hust.hui.mvc.core.aop.anno.Around;
import com.hust.hui.mvc.core.aop.anno.Aspect;
import com.hust.hui.mvc.core.aop.anno.Before;
import com.hust.hui.mvc.core.aop.process.AfterProcess;
import com.hust.hui.mvc.core.aop.process.AroundProcess;
import com.hust.hui.mvc.core.aop.process.BeforeProcess;
import com.hust.hui.mvc.core.aop.process.ProcessCollect;
import com.hust.hui.mvc.core.exception.BeanAlreadyDefinedException;
import com.hust.hui.mvc.core.exception.BeanNotFoundException;
import com.hust.hui.mvc.core.proxy.CglibProxyFactory;
import com.hust.hui.mvc.core.util.ConfUtil;
import com.hust.hui.mvc.core.util.StrUtil;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by yihui on 2017/8/23.
 */
public class BeanFactory {

    private static BeanFactory instance = new BeanFactory();

    public static BeanFactory getInstance() {
        return instance;
    }

    private BeanFactory() {
    }


    private Set<Class<?>> beanClasses;


    /**
     * 所有自动实例化的bean的映射表，
     * key为bean name
     * - (如果注解中有指定value值，则bean name就是value值；若没有指定，则是首字母小写的简单类名）
     * - bean name 区分大小写
     * <p>
     * 为了避免bean name相同的问题，将value也保存为一个Map映射表
     */
    private Map<String, Map<Class, Object>> nameBeanMap;


    /**
     * class到bean的映射表
     */
    private Map<Class, Object> clzBeanMap;


    public void init(String pkg) throws IOException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {

        // scan all class in the package
        loadScanClass(pkg);


        // instance aspect and maintain aspect Map
        instanceAspect();


        // instance autowired bean
        instanceBean();


        // inject dependency bean
        ioc();
    }


    private Set<Class<?>> loadScanClass(String path) throws IOException {
        beanClasses = ConfUtil.loadScanClasses(path);
        return beanClasses;
    }


    /**
     * 自定义注解 及 切该注解的所有切面的映射关系
     * <p>
     * key 为切面拦截的自定义注解的class对象
     * value 为该注解所对应的所有切面集合
     */
    private Map<Class, ProcessCollect> aspectAnoMap = new ConcurrentHashMap<>();


    private void instanceAspect() throws IllegalAccessException, InstantiationException {
        clzBeanMap = new ConcurrentHashMap<>();
        Object aspect;
        for (Class clz : beanClasses) {
            if (!clz.isAnnotationPresent(Aspect.class)) {
                continue;
            }

            aspect = clz.newInstance();


            // 将aspect 丢入bean Map中， 因此aspect也支持ioc
            clzBeanMap.put(clz, aspect);

            // 扫描切面的方法
            Class ano;
            ProcessCollect processCollect;
            Method[] methods = clz.getDeclaredMethods();
            for (Method method : methods) {
                Before before = method.getAnnotation(Before.class);
                if (before != null) {
                    BeforeProcess beforeProcess = new BeforeProcess();
                    beforeProcess.setAspect(aspect);
                    beforeProcess.setMethod(method);

                    ano = before.value();
                    processCollect = aspectAnoMap.computeIfAbsent(ano, k -> new ProcessCollect());
                    processCollect.addBeforeProcess(beforeProcess);
                }


                After after = method.getAnnotation(After.class);
                if (after != null) {
                    AfterProcess afterProcess = new AfterProcess();
                    afterProcess.setAspect(aspect);
                    afterProcess.setMethod(method);


                    ano = after.value();
                    processCollect = aspectAnoMap.computeIfAbsent(ano, k -> new ProcessCollect());
                    processCollect.addAfterProcess(afterProcess);
                }


                Around around = method.getAnnotation(Around.class);
                if (around != null) {
                    AroundProcess aroundProcess = new AroundProcess();
                    aroundProcess.setAspect(aspect);
                    aroundProcess.setMethod(method);

                    ano = around.value();
                    processCollect = aspectAnoMap.computeIfAbsent(ano, k -> new ProcessCollect());
                    processCollect.addAroundProcess(aroundProcess);
                }

            }
        }


        AspectHolder.getInstance().processCollectMap = aspectAnoMap;
    }


    /**
     * 实例化自动加载的bean
     *
     * @return
     */
    private Map<String, Map<Class, Object>> instanceBean() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        nameBeanMap = new ConcurrentHashMap<>();
        clzBeanMap = new ConcurrentHashMap<>();


        Annotation[] typeAnos;
        String tmpBeanName;
        Method tmpMethod;
        Object tmpBean;
        Map<Class, Object> tmpClzMap;
        for (Class clz : beanClasses) {
            if (clz.isInterface()) {
                continue;
            }


            // 获取类上注解
            typeAnos = clz.getAnnotations();
            if (typeAnos.length == 0) {
                continue;
            }


            for (Annotation ano : typeAnos) {
                if (ano instanceof Bean || ano.annotationType().isAnnotationPresent(Bean.class)) { // 需要加载bean
                    tmpMethod = ano.annotationType().getMethod("value", null);
                    if (tmpMethod != null) {
                        tmpBeanName = (String) tmpMethod.invoke(ano, null);
                    } else {
                        tmpBeanName = null;
                    }

                    if (StringUtils.isEmpty(tmpBeanName)) {
                        tmpBeanName = StrUtil.lowerFirstChar(clz.getSimpleName());
                    }


                    if (nameBeanMap.containsKey(tmpBeanName)) {
                        tmpClzMap = nameBeanMap.get(tmpBeanName);
                    } else {
                        tmpClzMap = new ConcurrentHashMap<>();
                    }

                    if (tmpClzMap.containsKey(clz)) {
                        throw new BeanAlreadyDefinedException("bean " + tmpBeanName + " class: " + clz.getName() + " has already defined!");
                    }


                    tmpBean = instanceClz(clz);


                    tmpClzMap.put(clz, tmpBean);
                    clzBeanMap.put(clz, tmpBean);
                    nameBeanMap.put(tmpBeanName, tmpClzMap);
                    break;
                }
            }
        }

        return nameBeanMap;
    }


    /**
     * 根据class创建实例 or 代理
     *
     * @param clz
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private Object instanceClz(Class clz) throws IllegalAccessException, InstantiationException {
        Method[] methods = clz.getMethods();

        Annotation[] anos;
        for (Method method : methods) {
            anos = method.getAnnotations();
            for (Annotation a : anos) {
                if (aspectAnoMap.containsKey(a.annotationType())) {
                    return CglibProxyFactory.getProxy(clz);
                }
            }
        }

        return clz.newInstance();
    }


    /**
     * 依赖注入
     */
    private void ioc() throws IllegalAccessException {

        Field[] fields;
        String beanName;
        Object bean;
        for (Object obj : clzBeanMap.values()) {
            fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (!field.isAnnotationPresent(Autowired.class)) {
                    continue;
                }

                Autowired autowired = field.getAnnotation(Autowired.class);
                beanName = StringUtils.isBlank(autowired.value()) ?
                        StrUtil.lowerFirstChar(field.getName()) : autowired.value();
                bean = getBeanOfType(beanName, field.getType());

                if (bean == null) {
                    throw new BeanNotFoundException("bean: " + beanName + " not found! bean class: " + field.getClass().getName());
                }

                field.setAccessible(true);
                field.set(obj, bean);
            }
        }
    }


    public Object[] getBeanOfName(String name) {
        Map<Class, Object> map = nameBeanMap.get(name);
        if (map == null || map.isEmpty()) {
            return new Object[0];
        }

        return map.values().toArray();
    }


    @SuppressWarnings("unchecked")
    public <T> T getBeanOfName(String name, Class<T> clz) {
        if (!nameBeanMap.containsKey(name)) {
            return null;
        }

        Map<Class, Object> map = nameBeanMap.get(name);
        return (T) map.get(clz);
    }


    @SuppressWarnings("unchecked")
    public <T> T getBeanOfType(String name, Class<T> type) {
        if (!nameBeanMap.containsKey(name)) {
            return null;
        }

        Map<Class, Object> map = nameBeanMap.get(name);
        Class[] interfaces;
        for (Class clz : map.keySet()) {
            interfaces = clz.getInterfaces();
            for (Class i : interfaces) {
                if (i == type) {
                    return (T) map.get(clz);
                }
            }
        }

        return null;
    }


    @SuppressWarnings("unchecked")
    public <T> T getBeanOfType(Class<T> clz) {
        return (T) clzBeanMap.get(clz);
    }


    public boolean containBean(String name) {
        return nameBeanMap.containsKey(name);
    }


    /**
     * 运行后动态装载自定义生成的bean
     *
     * @param name
     * @param bean
     * @return
     */
    public boolean registerBean(String name, Object bean) {
        Map<Class, Object> clzMap = nameBeanMap.get(name);
        if (clzMap == null) {
            clzMap = new ConcurrentHashMap<>();
        }


        if (clzMap.containsKey(bean.getClass())) {
            throw new BeanAlreadyDefinedException("bean : " + name + " class: " + bean.getClass() + " has already defined and loaded!");
        }


        clzBeanMap.put(bean.getClass(), bean);
        nameBeanMap.put(name, clzMap);
        return true;
    }


}
