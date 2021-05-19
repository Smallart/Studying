package com.small.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * 通过http发送方法
 * @author Ruoyi
 */
public class HttpUtils {
    private static final Logger log = LoggerFactory.getLogger(HttpUtils.class);

    /**
     * 向指定URL 发送GET方法的请求
     * @param url 发送请求的URL
     * @param param 请求参数，请求参数视为 param1=value1&param2=value2
     * @return 所代表远程资源的相应结果
     */
    public static String sendGet(String url,String param){
        return sendGet(url, param,"UTF-8");
    }

    /**
     * 向指定URL 发送GET方法的请求
     * @param url 发送请求的URL
     * @param param 请求参数，请求参数视为 param1=value1&param2=value2
     * @param contentType 编码类型
     * @return 所代表远程资源的响应结果
     */
    public static String sendGet(String url,String param,String contentType){
        StringBuilder result = new StringBuilder();
        BufferedReader in = null;
        try{
            String urlNameString = url + "?" + param;
            log.info("sendGet - {}",urlNameString);
            URL realUrl = new URL(urlNameString);
            URLConnection connection = realUrl.openConnection();
            connection.setRequestProperty("accept","*/*");
            connection.setRequestProperty("connection","keep-Alive");
            connection.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.connect();
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(),contentType));
            String line;
            while((line=in.readLine())!=null){
                result.append(line);
            }
            log.info("recv - {}",result);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            log.error("调用HttpUtils.sendGet MalformedURLException, url="+url+",param="+param,e);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("调用HttpUtils.sendGet IOException, Url="+url+",param="+param,e);
        }finally {
            try{
                if (in!=null){
                    in.close();
                }
            } catch (IOException e) {
                log.error("调用in.close Exception,url="+url+",param="+param,e);
            }
        }
        return result.toString();
    }

    /**
     * 向指定URL发送POST方法的请求
     * @param url 发送请求的URL
     * @param param 请求参数，请求参数应该是param1 = value1 & param2 = value2的形式
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url,String param){
        PrintWriter out = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        try{
            String urlNameString = url;
            log.info("sendPost - {}",urlNameString);
            URL realUrl = new URL(urlNameString);
            URLConnection conn = realUrl.openConnection();
            conn.setRequestProperty("accpet","*/*");
            conn.setRequestProperty("connection","Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Accept-Charset", "utf-8");
            conn.setRequestProperty("contentType", "utf-8");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            out = new PrintWriter(conn.getOutputStream());
            out.print(param);
            out.flush();
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
            String line;
            while((line = in.readLine())!=null){
                result.append(line);
            }
            log.info("recv - {}", result);
        } catch (MalformedURLException e) {
            log.error("调用HttpUtils.sendPost MalformedURLException, url="+url+",param="+param,e);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("调用HttpUtils.sendPost IOException, Url="+url+",param="+param,e);
        }finally
        {
            try
            {
                if (out != null)
                {
                    out.close();
                }
                if (in != null)
                {
                    in.close();
                }
            }
            catch (IOException ex)
            {
                log.error("调用in.close Exception, url=" + url + ",param=" + param, ex);
            }
        }
        return result.toString();
    }
}
