package com.dinosaur.web.workflow.web.directive;

import com.dinosaur.core.freemarker.Tag;
import freemarker.template.TemplateModelException;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author Alcott Hawk
 * @Date 1/10/2017
 */
@Component
public class EnumFormConverToList extends Tag{
    @Override
    public Object exec(Map map) throws TemplateModelException {
        Object data = map.get("data");
        return data.toString();
    }
}
