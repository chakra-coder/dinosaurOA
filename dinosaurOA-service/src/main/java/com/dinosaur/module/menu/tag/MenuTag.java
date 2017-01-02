package com.dinosaur.module.menu.tag;

import com.dinosaur.core.freemarker.Tag;
import com.dinosaur.core.shiro.ShiroUser;
import com.dinosaur.module.menu.dao.MenuDao;
import com.dinosaur.module.user.dao.UserDAO;
import freemarker.template.TemplateModelException;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * 菜单标签
 * <p>
 *     用户界面UI上显示的菜单的数据展示标签。获取到对应用户所在用户组下的菜单数据
 * </p>
 * @Author Alcott Hawk
 * @Date 1/2/2017
 */
public class MenuTag extends Tag{

    @Autowired
    private MenuDao menuDao;

    @Autowired
    private UserDAO userDAO;

    @Override
    public Object exec(Map map) throws TemplateModelException {
        ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        // TODO 菜单查询标签
        return null;
    }
}
