package com.dinosaur.core.aspect;

import com.dinosaur.core.context.ApplicationContextHolder;
import com.dinosaur.core.freemarker.TagCreate;
import freemarker.template.Configuration;
import freemarker.template.TemplateModelException;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.util.HashMap;
import java.util.Map;

/**
 * Freemarker 配置切面
 * @Author Alcott Hawk
 * @Date 12/14/2016
 */
@Component
@Aspect
public class FreemarkerConfigAspect {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private static Map<String,Object> rootMap = new HashMap<String,Object>();

    public static Map getRootMap() {
        return rootMap;
    }

    public static void setRootMap(Map rootMap) {
        FreemarkerConfigAspect.rootMap.clear();
        FreemarkerConfigAspect.rootMap = rootMap;
    }

    @Before("execution(* com.dinosaur..*..controller..*(..))")
    public void setConfig(){
        FreeMarkerConfigurer freeMarkerConfigurer = ApplicationContextHolder.getBean("freeMarkerConfigurer");
        try {
            rootMap.put("ctx", ApplicationContextHolder.getServletContext().getContextPath());
            rootMap.put("theme","default");
            rootMap.put("create",new TagCreate());
            Configuration configuration = freeMarkerConfigurer.getConfiguration();
            configuration.setSharedVaribles(rootMap);
            //configuration.setServletContextForTemplateLoading(ApplicationContextHolder.getServletContext(), "/themes/default/view/");
            freeMarkerConfigurer.setConfiguration(configuration);
        } catch (TemplateModelException e) {
            logger.error("freemarker 配置错误"+e.getMessage());
        }
    }

}
