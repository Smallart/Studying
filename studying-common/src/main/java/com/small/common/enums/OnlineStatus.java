package com.small.common.enums;

/**
 * 用户在线标志
 * @author ruoyi
 */
public enum OnlineStatus {
    /**
     * 在线
     */
    ON_LINE("on_line"),
    /**
     * 下线
     */
    OFF_LINE("off_line");
    private final String info;
    OnlineStatus(String info) {
        this.info = info;
    }
    public String getInfo(){
        return this.info;
    }
}
