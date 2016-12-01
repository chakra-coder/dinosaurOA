package com.dinosaur.plat.system.service;

import com.dinosaur.core.util.EncryptUtil;
import com.dinosaur.module.user.UserService;
import com.dinosaur.module.user.entity.User;
import com.dinosaur.plat.system.entity.ShiroUser;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

/**
 * shiro 框架自定义Realm
 */
public class ShiroDbRealm extends AuthorizingRealm{

    protected UserService userService;

    public void setUserService(UserService userService){
        this.userService = userService;
    }

    /**
     * 授权回调
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
        User user = userService.getUserByUserNmae(shiroUser.loginNmae);
        if (user != null){
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            addRole(user,info);
            return info;
        }
        return null;
    }

    /**
     * 认证回调
     * @param authcToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        User user = userService.getUserByUserNmae(token.getUsername());
        if (user != null) {
            ShiroUser shiroUser = new ShiroUser(user.getName(),token.getHost(),user.getId());
            byte[] salt = EncryptUtil.decodeHex(user.getSalt());
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(shiroUser,user.getPassword(), ByteSource.Util.bytes(salt),getName());
            return info;
        } else {
            return null;
        }
    }

    /**
     * 添加用户角色
     * @param user
     * @param info
     */
    private void addRole(User user,SimpleAuthorizationInfo info){
        if(user.getGroups()!=null&&user.getGroups().size()>0){
            for (com.dinosaur.module.group.entity.Group group : user.getGroups()) {
                info.addRole(group.getName());
            }
        }
    }

}
