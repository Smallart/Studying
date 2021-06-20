package com.small.common.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 数据域范围注解
 * @author ruoyi
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DataScope {
    /**
     * 部门表的别名
     * @return
     */
    String deptAlias() default "";

    /**
     * 用户表别名
     * @return
     */
    String userAlias() default "";
}
