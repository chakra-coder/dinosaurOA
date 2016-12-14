package com.dinosaur.core.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.ContextLoader;

import javax.servlet.ServletContext;

/**
 * @Author Alcott Hawk
 * @Date 12/14/2016
 */
public class ApplicationContextHolder implements ApplicationContextAware{

    private static ApplicationContext applicationContext;

    /**
     * 保存spring ApplicationContext 到静态变量
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextHolder.applicationContext = applicationContext;
    }

    /**
     * 获取servletContext
     * @return
     */
    public static ServletContext getServletContext(){
        return ContextLoader.getCurrentWebApplicationContext().getServletContext();
    }

    /**
     * 获取注入到spring容器中的ApplicationContext
     * @return
     */
    public static ApplicationContext getApplicationContext(){
        checkApplicationContext();
        return applicationContext;
    }

    /**
     * 获取注入到spring容器中的bean
     * @param name 所注入的bean的名称
     * @param <T>
     * @return
     */
    public static <T>T getBean(String name){
        checkApplicationContext();
        return (T) applicationContext.getBean(name);
    }

    /**
     * 获取注入到spring容器中的bean
     * @param clazz
     * Instances of the class {@code Class} represent classes and
     * interfaces in a running Java application.  An enum is a kind of
     * class and an annotation is a kind of interface.  Every array also
     * belongs to a class that is reflected as a {@code Class} object
     * that is shared by all arrays with the same element type and number
     * of dimensions.  The primitive Java types ({@code boolean},
     * {@code byte}, {@code char}, {@code short},
     * {@code int}, {@code long}, {@code float}, and
     * {@code double}), and the keyword {@code void} are also
     * represented as {@code Class} objects.
     *
     * <p> {@code Class} has no public constructor. Instead {@code Class}
     * objects are constructed automatically by the Java Virtual Machine as classes
     * are loaded and by calls to the {@code defineClass} method in the class
     * loader.
     * @param <T>
     * @return
     */
    public static <T>T getBean(Class<T> clazz){
        checkApplicationContext();
        return (T) applicationContext.getBean(clazz);
    }

    /**
     * 检查ApplicationContext是否注入到容器
     */
    private static void checkApplicationContext(){
        if (applicationContext == null){
            throw new IllegalStateException("ApplicationContext未注入容器，请检查注入!");
        }
    }
}
