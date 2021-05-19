package com.small.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 方便在非SpringIoC容器中获得bean对象
 * @author ruoyi
 */
@Component
public class SpringUtils implements ApplicationContextAware, BeanFactoryPostProcessor {


    private static ApplicationContext applicationContext;

    private static ConfigurableListableBeanFactory beanFactory;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringUtils.applicationContext = applicationContext;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        this.beanFactory = configurableListableBeanFactory;
    }

    /**
     * 从IOC容器中通过name获取Bean对象
     * @param name
     * @param <T>
     * @return
     */
    public static <T> T getBean(String name){
        return (T) beanFactory.getBean(name);
    }

    public static <T> T getBean(Class<T> clz){
        System.out.println(beanFactory.getBean(clz));
        return (T) beanFactory.getBean(clz);
    }


}
