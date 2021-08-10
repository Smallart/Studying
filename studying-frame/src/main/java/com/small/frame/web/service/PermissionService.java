package com.small.frame.web.service;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;

/**
 * RuoYi这里有个直接通过js调用thymleaf实现调用该类的功能
 * 这个Thymeleaf中已经有例子
 * @url https://www.thymeleaf.org/doc/articles/springmvcaccessdata.html
 * 前端权限检验
 * @author ruoyi
 */
@Service
public class PermissionService {
    /**
     * 没有权限，hidden用于前端隐藏按钮
     */
    public static final String NOACCESS = "layui-hide";
    public static final String EMPTY = "";

    /**
     * 验证用户是否具备某权限，无权限返回hidden用于前端隐藏（如需要返回Boolean使用isPermitted）
     * @param permission 权限字符
     * @return
     */
    public String hasPermi(String permission){
        return isPermitted(permission)? EMPTY:NOACCESS;
    }

    /**
     * 判断用户是否拥有某个权限
     * @param permission 权限字符串
     * @return
     */
    public boolean isPermitted(String permission){
        return SecurityUtils.getSubject().isPermitted(permission);
    }

}
