package com.small.common.base.enitity;

import com.small.common.base.BaseObject;
import lombok.Data;

/**
 * 部门实体
 * @author Liang
 */
@Data
public class SysDepartment extends BaseObject {
    /**
     * 部门ID
     */
    private Long departmentId;
    /**
     * 父类ID
     */
    private Long parentId;

    /**
     * 父类名称
     */
    private String parentName;

    /**
     * 祖先ID
     */
    private String ancestors;
    /**
     * 部门名称
     */
    private String departmentName;

    /**
     * 部门领导
     */
    private String leader;

    /**
     * 排序
     */
    private Integer orderNum;
    /**
     * 电话
     */
    private String phone;
    /**
     * 电子邮件
     */
    private String email;
    /**
     * 部门状态
     */
    private Integer status;
    /**
     * 删除标志
     */
    private Integer delFlag;
    /**
     * 是否包含子类
     */
    private Boolean isParent;

    private boolean checked;
}
