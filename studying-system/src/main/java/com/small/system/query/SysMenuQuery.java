package com.small.system.query;

import com.small.common.base.BaseQuery;
import lombok.Data;

/**
 * SysMenu 查询实体
 * @author Liang 
 */
@Data
public class SysMenuQuery extends BaseQuery {
    /**
     * 菜单名词
     */
    private String menuName;

    /**
     * 菜单ID
     */
    private Long userId;

    /**
     * 菜单状态 0 显示 1 隐藏
     */
    private Integer visible;

    /**
     * 菜单类型
     */
    private String menuType;

    /**
     * 进行的操作
     */
    private Integer operation;

    public enum OperationEnum{
        // 不包含M的菜单类型
        NOT_INCLUDE_M(0),
        // 不包含F的菜单类型
        NOT_INCLUDE_F(1);
        private Integer val;

        OperationEnum(Integer val) {
            this.val = val;
        }
        public Integer getVal() {
            return this.val;
        }
    }
}
