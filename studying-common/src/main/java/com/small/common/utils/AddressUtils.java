package com.small.common.utils;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 *  获取地址类
 * @author ruoyi
 */
public class AddressUtils {
    private static final Logger log = LoggerFactory.getLogger(AddressUtils.class);
    public static final String IP_URL = "http://whois.pconline.com.cn/ipJson.jsp";
    public static final String UNKNOWN = "XX XX";
    public static String getRealAddressByIp(String ip){
        String address = UNKNOWN;
        if (IpUtils.internalIp(ip)){
            return "内网IP";
        }
        try{
            String rspStr = HttpUtils.sendGet(IP_URL,"ip="+ip+"&json=true","GBK");
            if (!StringUtils.hasLength(rspStr)){
                log.error("获得地理位置异常{}",ip);
                return UNKNOWN;
            }
            JSONObject obj = JSONObject.parseObject(rspStr);
            String region = obj.getString("pro");
            String city = obj.getString("city");
            return String.format("%s %s",region,city);
        }catch (Exception e){
            log.error("获得地理位置异常{}",e);
        }
        return address;
    }
}
