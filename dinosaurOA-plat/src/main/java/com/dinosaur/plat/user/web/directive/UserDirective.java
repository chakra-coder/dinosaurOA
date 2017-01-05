package com.dinosaur.plat.user.web.directive;

import com.dinosaur.core.freemarker.Tag;
import com.dinosaur.core.shiro.ShiroUser;
import com.dinosaur.module.user.dao.UserDAO;
import com.dinosaur.module.user.entity.User;
import freemarker.template.TemplateModelException;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 用户指令<br/>
 * 获取用户信息，包括用户的扩展信息
 * @Author Alcott Hawk
 * @Date 1/5/2017
 */
@Component
public class UserDirective extends Tag{

    @Autowired
    private UserDAO userDAO;

    @Override
    public Object exec(Map map) throws TemplateModelException {
        ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        User user = userDAO.findOne(shiroUser.id);
        return user;
    }
}
