package com.small.common.base.enitity;
import com.small.common.base.BaseObject;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 系统用户类
 * @author Liang
 */
@Data
public class SysUser extends BaseObject implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 用户Id
     */
    private Long userId;
    /**
     * 部门Id
     */
    private Integer deptId;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 登录名
     */
    private String loginName;
    /**
     * 用户类型 00 系统用户
     * 01 注册用户
     */
    private String userType;
    /**
     * 邮箱
     */
    private String email;

    /**
     * 电话号码
     */
    private String iphone;

    /**
     * 性别
     * 0女 1男
     */
    private String sex;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 密码
     */
    private String password;
    /**
     * 盐值
     */
    private String salt;
    /**
     * 用户状态
     */
    private String status;
    /**
     * 删除标志
     */
    private String delFlag;
    /**
     * 最后登录IP地址
     */
    private String loginIp;
    /**
     * 最后登录时间
     */
    private Date loginDate;
    /**
     * 密码最新登录时间
     */
    private Date pwdUpdateTime;

    /**
     * 公司名称
     */
    private String deptName;

    /**
     * 判断是否是管理员
     * @return
     */
    public boolean isAdmin(){
        return this.userId == 1L;
    }

    public static boolean isAdmin(Long userId){
        return userId!=null&&userId==1;
    }

    /**
     * postIds
     */
    private Long[] postIds;

    /**
     * roles
     */
    private Long[] roles;

    /**
     * 用户所包含角色
     */
    private List<SysRole> sysRoles;

    /**
     * 用户所属部门
     */
    private SysDepartment department;
}
