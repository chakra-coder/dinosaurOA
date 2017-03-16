package com.dinosaur.core.freemarker;

import freemarker.template.TemplateDirectiveModel;
import java.util.Map;

/**
 * 自定义指令工厂
 * <p>接入系统的自定义指令入口。<br/>通过实现该接口将自定义指令加入到系统中<br/>
 * </p>
 * @Author Alcott Hawk
 * @Date 12/31/2016
 * @version 1.0
 */
public class ITagFactory {

    public Map<String,TemplateDirectiveModel> setDirective(){
        return null;
    }

}
