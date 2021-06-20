package com.small.common.core.text;

import com.small.common.utils.StringUtils;
import javafx.scene.transform.Shear;

/**
 * 字符串格式化
 * @author ruoyi
 */
public class StrFormatter {
    public static final String EMPTY_JSON = "{}";
    public static final char C_BACKSLASH = '\\';
    public static final char C_DELIM_START = '{';
    public static final char C_DELIM_END = '}';

    public static String format(final String strPattern, final String... argArray){
        if (StringUtils.isEmpty(strPattern)|| StringUtils.isEmpty(argArray)){
            return strPattern;
        }
        final int strPatternLength = strPattern.length();

        //初始化定义好的长度以获得更好的性能
        StringBuilder sbuf = new StringBuilder(strPatternLength+50);

        int handledPosition = 0;
        int delimIndex; // 占位符所在位置
        for (int argIndex=0;argIndex<argArray.length;argIndex++){
            // str.indexOf从指定下标开始查询第一次与子串匹配的下标
            delimIndex = strPattern.indexOf(EMPTY_JSON,handledPosition);
            if(delimIndex==-1){
                //说明没有代替的元素
                if (handledPosition==0){
                    return strPattern;
                }else {
                   // 当有多个，且这个为最后一个时
                   sbuf.append(strPattern,handledPosition,strPatternLength);
                   return sbuf.toString();
                }
            }else{
                if (delimIndex>0&&strPattern.charAt(delimIndex-1)==C_BACKSLASH){
                    if (delimIndex>1&&strPattern.charAt(delimIndex-2)==C_BACKSLASH){
                        sbuf.append(strPattern,handledPosition,delimIndex-1);
                        sbuf.append(argArray[argIndex]);
                        handledPosition = delimIndex + 2;
                    }else {
                        argIndex--;
                        sbuf.append(strPattern,handledPosition,delimIndex - 1);
                        sbuf.append(C_DELIM_START);
                        handledPosition = delimIndex + 1;
                    }
                }else{
                    // 正常占位符
                    sbuf.append(strPattern,handledPosition,delimIndex);
                    sbuf.append(argArray[argIndex]);
                    handledPosition = delimIndex + 2;
                }
            }
        }
        sbuf.append(strPattern,handledPosition,strPattern.length());
        return sbuf.toString();
    }

}
