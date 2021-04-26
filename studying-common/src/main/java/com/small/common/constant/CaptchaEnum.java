package com.small.common.constant;

/**
 * 验证码类型
 * @author Liang
 */
public enum CaptchaEnum {
    /**
     * gif类型
     */
    GIFCAPTCHA(1),
    /**
     * 中文类型
     */
    CHINESECAPTCHA(2),
    /**
     * 中文gif
     */
    CHINESEGIFCAPTCHA(3),
    /**
     * 算术类型
     */
    ARITHMETICCAPTCHA(4),

    ERROR(-1);
    private int index;

    CaptchaEnum(int index) {
        this.index = index;
    }

    public static CaptchaEnum getValue(int index){
        for (CaptchaEnum value : values()) {
            if (value.index==index){
                return value;
            }
        }
        return ERROR;
    }
}
