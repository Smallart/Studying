package com.small.frame.shiro.util;

import com.small.frame.shiro.realm.ShiroRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.mgt.SecurityManager;

/**
 * 用户授权信息
 * @author ruoyi
 */
public class AuthorizationUtils {
    public static void clearAllCacheAuthorizationInfo(){
        getRealm().clearAllCachedAuthorizationInfo();
    }

    public static ShiroRealm getRealm(){
        RealmSecurityManager rsm =(RealmSecurityManager) SecurityUtils.getSecurityManager();
        return (ShiroRealm) rsm.getRealms().iterator().next();
    }
}
