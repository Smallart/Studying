package com.small.frame.shiro.realm;

import com.small.common.exceptions.user.UserException;
import com.small.frame.shiro.service.SysLoginService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 实现Shiro的Realm用于鉴权或是授予相关权限
 * @author Liang
 */
public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private SysLoginService loginService;

    /**
     * 授权
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    /**
     * 鉴权
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException, UserException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        String userName = usernamePasswordToken.getUsername();
        String password = "";
        if (usernamePasswordToken.getPassword()!=null){
            password = new String(usernamePasswordToken.getPassword());
        }
        loginService.login(userName,password);
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(userName, password, getName());
        return info;
    }
}
