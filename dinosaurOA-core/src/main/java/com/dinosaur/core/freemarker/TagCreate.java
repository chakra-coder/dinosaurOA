package com.dinosaur.core.freemarker;

import com.dinosaur.core.context.ApplicationContextHolder;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import org.apache.commons.lang3.StringUtils;
import java.util.List;

/**
 * 标签创建器
 * <b>接收模板那页面传递的bean名称参数，从application中获取对应时代bean对象并返回</b>
 * @Author Alcott Hawk
 * @Date 12/30/2016
 * @version 1.0
 */
public class TagCreate implements TemplateMethodModelEx{
    @Override
    public Object exec(List arguments) throws TemplateModelException {
        SimpleScalar beanid = (SimpleScalar) arguments.get(0);
        if(StringUtils.isBlank(beanid.toString())){
            throw new TemplateModelException("beanid is null");
        }
        return ApplicationContextHolder.getBean(beanid.toString());
    }
}
