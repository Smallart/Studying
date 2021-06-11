package com.small.common.constant;

/**
 * 用户常量信息
 * @author ruoyi
 */
public class UserConstants {
    /**
     * 用户名长度限制
     */
    public static final int USERNAME_MIN_LENGTH = 2;
    public static final int USERNAME_MAX_LENGTH = 20;
    /**
     * 密码长度限制
     */
    public static final int PASSWORD_MIN_LENGTH = 5;
    public static final int PASSWORD_MAX_LENGTH = 20;

    /**
     * 用户类型
     */
    public static final String SYSTEM_USER_TYPE = "00";
    public static final String REGISTER_USER_TYPE = "01";

    /**
     * 登录名称是否唯一的返回结果
     */
    public static final String USER_NAME_NOT_UNIQUE = "1";
    public static final String USER_NAME_UNIQUE = "0";

    /**
     * 用户电话是否唯一
     */
    public static final String USER_PHONE_UNIQUE = "0";
    public static final String USER_PHONE_NOT_UNIQUE = "1";

    /**
     * 用户邮箱是否唯一
     */
    public static final String USER_EMAIL_UNIQUE = "0";
    public static final String USER_EMAIL_NOT_UNIQUE = "1";
}
