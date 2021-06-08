package com.small.frame.manager.facotry;

import com.small.common.constant.Constants;
import com.small.common.utils.*;
import com.small.frame.manager.AsyncManager;
import com.small.frame.shiro.session.domain.OnlineSession;
import com.small.system.domain.SysLoginInfo;
import com.small.system.domain.SysUserOnline;
import com.small.system.service.ISysLoginInfoService;
import com.small.system.service.ISysUserService;
import com.small.system.service.impl.SysUserOnlineServiceImpl;
import com.small.system.service.ISysUserOnlineService;
import eu.bitwalker.useragentutils.UserAgent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.TimerTask;

/**
 * 异步线程工厂,用来存储相关信息
 * @author ruoyi
 */
public class AsyncFactory {
    private static final Logger log  = LoggerFactory.getLogger("sys-user");

    /**
     * 将在线用户放入数据库中
     * @param session
     * @return
     */
    public static TimerTask syncSessionToDb(final OnlineSession session){
        return new TimerTask() {
            @Override
            public void run() {
                SysUserOnline sysUserOnline = new SysUserOnline();
                sysUserOnline.setSessionId(String.valueOf(session.getId()));
                sysUserOnline.setBrowser(session.getBrowser());
                sysUserOnline.setExpireTime(session.getTimeout());
                sysUserOnline.setIp(session.getHost());
                sysUserOnline.setLastAccessTime(session.getLastAccessTime());
                sysUserOnline.setLoginLocation(AddressUtils.getRealAddressByIp(session.getHost()));
                sysUserOnline.setLoginName(session.getLoginName());
                sysUserOnline.setOs(session.getOs());
                sysUserOnline.setStartTimeStamp(session.getStartTimestamp());
                sysUserOnline.setStatus(session.getStatus());
                ISysUserOnlineService sysUserOnlineService =SpringUtils.getBean(SysUserOnlineServiceImpl.class);
                sysUserOnlineService.save(sysUserOnline);
            }
        };
    }

    /**
     * 将登录信息放入数据库中
     * @return
     */
    public static TimerTask syncLoginInfoToDb(String loginName,String status,String message){
        UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
        String ip = ShiroUtils.getSession().getHost();
        return new TimerTask() {
            @Override
            public void run() {
                String address = AddressUtils.getRealAddressByIp(ip);
                StringBuilder logInfo = new StringBuilder();
                logInfo.append(LogUtils.getBlock(ip));
                logInfo.append(address);
                logInfo.append(LogUtils.getBlock(loginName));
                logInfo.append(LogUtils.getBlock(status));
                logInfo.append(LogUtils.getBlock(message));
                // 打印信息到日志中
                log.info(logInfo.toString());
                // 获得操作系统信息
                String os = userAgent.getOperatingSystem().getName();
                // 获得浏览器的信息
                String browser = userAgent.getBrowser().getName();

                SysLoginInfo sysLoginInfo = new SysLoginInfo();
                sysLoginInfo.setLoginName(loginName);
                sysLoginInfo.setBrowser(browser);
                sysLoginInfo.setLoginLocation(AddressUtils.getRealAddressByIp(ip));
                sysLoginInfo.setIpAddr(ip);
                sysLoginInfo.setMsg(message);
                sysLoginInfo.setOs(os);
                if (Constants.LOGIN_SUCCESS.equals(status)||Constants.LOGOUT.equals(status)||Constants.REGISTER.equals(status)){
                    sysLoginInfo.setStatus(Constants.SUCCESS);
                }else if (Constants.LOGIN_FAIL.equals(status)){
                    sysLoginInfo.setStatus(Constants.FAIL);
                }
                // 插入数据
                SpringUtils.getBean(ISysLoginInfoService.class).save(sysLoginInfo);
            }
        };
    }
}
