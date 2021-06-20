package com.small.frame.shiro.realm;

import com.small.common.base.enitity.SysUser;
import com.small.common.exceptions.user.*;
import com.small.common.utils.ShiroUtils;
import com.small.frame.shiro.service.SysLoginService;
import com.small.system.query.SysMenuQuery;
import com.small.system.service.ISysMenuService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 实现Shiro的Realm用于鉴权或是授予相关权限
 * @author Liang
 */
public class ShiroRealm extends AuthorizingRealm {

    private final Logger log = LoggerFactory.getLogger(ShiroRealm.class);

    @Autowired
    private SysLoginService loginService;

    @Autowired
    private ISysMenuService menuService;

    /**
     * 授权
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SysUser sysUser = ShiroUtils.getPrincipal();
        SimpleAuthorizationInfo authInfo = new SimpleAuthorizationInfo();
        if (sysUser.isAdmin()){
            authInfo.addStringPermission("*:*:*");
        }else{
            SysMenuQuery sysMenuQuery = new SysMenuQuery();
            sysMenuQuery.setUserId(sysUser.getUserId());
            sysMenuQuery.setOperation(SysMenuQuery.OperationEnum.NOT_INCLUDE_M.getVal());
            List<String> perms = menuService.find(sysMenuQuery).stream().map(item -> item.getPerms()).collect(Collectors.toList());
            authInfo.addStringPermissions(perms);
        }
        return authInfo;
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
        SysUser user = null;
        try{
            user = loginService.login(userName, password);
        }catch (CaptchaException e){
            throw new AuthenticationException(e.getMessage(),e);
        }catch (UserNotExistsException e){
            throw new UnknownAccountException(e.getMessage(),e);
        }catch (UserPasswordNotMatchException e){
            throw new IncorrectCredentialsException(e.getMessage(),e);
        }catch (UserPasswordRetryLimitExceedException e){
            throw new ExcessiveAttemptsException(e.getMessage(),e);
        }catch (UserBlockedException e){
            throw new LockedAccountException(e.getMessage(),e);
        }catch (Exception e){
            log.error("对用户{}，进行登录验证未通过，原因：{}",userName,e.getMessage());
            throw new AuthenticationException(e.getMessage(),e);
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, password, getName());
        return info;
    }

    /**
     * 清理所有用户授权信息缓存
     */
    public void clearAllCachedAuthorizationInfo()
    {
        Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
        if (cache != null)
        {
            for (Object key : cache.keys())
            {
                cache.remove(key);
            }
        }
    }
}
