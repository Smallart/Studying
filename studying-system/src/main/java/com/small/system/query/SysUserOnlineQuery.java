package com.small.system.query;

import com.small.common.base.BaseQuery;
import lombok.Data;

import java.util.Date;

/**
 * UserOnline的查询实体
 * @author Liang
 */
@Data
public class SysUserOnlineQuery extends BaseQuery {
    /**
     * 查询的时间
     */
    private Date searchDate;
}
