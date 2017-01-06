package com.dinosaur.directive;

import com.dinosaur.core.freemarker.Tag;
import com.dinosaur.core.shiro.ShiroUser;
import com.dinosaur.module.user.dao.UserDAO;
import freemarker.template.TemplateModelException;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Map;

/**
 * 菜单指令
 * @Author Alcott Hawk
 * @Date 1/6/2017
 */
@Component
public class menuTag extends Tag{

    @Autowired
    private UserDAO userDAO;

    @Override
    public Object exec(Map map) throws TemplateModelException {
        ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();

        return null;
    }
}
