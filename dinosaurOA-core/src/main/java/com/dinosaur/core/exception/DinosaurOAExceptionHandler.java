package com.dinosaur.core.exception;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author Alcott Hawk
 * @Date 1/3/2017
 */
public class DinosaurOAExceptionHandler extends AbstractHandlerExceptionResolver{

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        if (ex instanceof UrlNotFoundException){
            return new ModelAndView("view/error/404");
        } else {
            return new ModelAndView("view/error/500");
        }
    }
}
