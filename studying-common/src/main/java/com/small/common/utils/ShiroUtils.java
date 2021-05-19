package com.small.common.utils;

import com.small.common.base.enitity.SysUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

/**
 * shiro的工具类
 * @author Liang
 */
public class ShiroUtils {
    /**
     * 获得凭证
     * @return
     */
    public static SysUser getPrincipal(){
        SysUser user = null;
        if (SecurityUtils.getSubject().getPrincipal()!=null){
            user = (SysUser) SecurityUtils.getSubject().getPrincipal();
        }
        return user;
    }

    /**
     * 获得当前Subject对象
     * @return
     */
    public static Subject getSubject(){
        return SecurityUtils.getSubject();
    }

    /**
     * 获得当前用户的session
     * @return
     */
    public static Session getSession(){
        return SecurityUtils.getSubject().getSession();
    }

    /**
     * 获取当前访问用户名
     * @return
     */
    public static String getLoginName(){
        String loginName = null;
        SysUser principal = getPrincipal();
        if (principal!=null){
            loginName = principal.getLoginName();
        }
        return loginName;
    }

    /**
     * 获取当前访问的用户Id
     * @return
     */
    public static Long getUserId(){
        Long userId = null;
        SysUser principal = getPrincipal();
        if (principal!=null){
            userId = principal.getUserId();
        }
        return userId;
    }


    /**
     * 登出操作
     */
    public static void loginOut(){
        Subject subject = getSubject();
        if (subject!=null){
            subject.logout();
        }
    }

}
