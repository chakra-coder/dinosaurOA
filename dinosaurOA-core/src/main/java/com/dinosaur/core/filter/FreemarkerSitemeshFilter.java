package com.dinosaur.core.filter;

import com.dinosaur.core.context.ApplicationContextHolder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

/**
 * @Author Alcott Hawk
 * @Date 12/16/2016
 */
public class FreemarkerSitemeshFilter implements Filter{

    private Locale locale;
    private ApplicationContext ctx;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String localeStr = filterConfig.getInitParameter("locale");
        if(StringUtils.isNotBlank(localeStr)){
            locale = new Locale(localeStr);
        }else {
            locale = Locale.CHINA;
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse res = (HttpServletResponse)response;
        if(ctx == null){
            ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(req.getSession().getServletContext());
            if(null == ctx){
                throw new ExceptionInInitializerError("spring context is not loaded!");
            }
        }
        try {
            String name = req.getRequestURI();
            String content = ApplicationContextHolder.getServletContext().getContextPath();
            if (name.contains("/themes/default/modeler")){
                name = name.substring(content.length()+"/themes/default".length(),name.lastIndexOf(".html"));
            } else {
                name = name.substring(content.length(),name.lastIndexOf(".html"));
            }
            ApplicationContext applicationContext = ApplicationContextHolder.getApplicationContext();
            FreeMarkerViewResolver freeMarkerViewResolver = (FreeMarkerViewResolver) applicationContext.getBean("ViewResolver");
            View view = freeMarkerViewResolver.resolveViewName(name, locale);
            view.render(null, req, res);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    public void destroy() {

    }
}
