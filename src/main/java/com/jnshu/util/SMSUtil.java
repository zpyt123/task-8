package com.jnshu.util;

import com.cloopen.rest.sdk.CCPRestSmsSDK;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class SMSUtil {
    private static String account = "" ;
    private static String token = "";
    private static String appId = "";
    private static Logger log = LoggerFactory.getLogger(SMSUtil.class);

    //使用配置文件加载用户信息
    private static CCPRestSmsSDK creat() {
        CCPRestSmsSDK restAPI = new CCPRestSmsSDK();
        //初始化端口和服务器地址
        restAPI.init("app.cloopen.com", "8883");
        restAPI.setAccount(account, token);//初始化账号信息
        restAPI.setAppId(appId);//初始应用信息
        return restAPI;
    }

    public static void sendSMS(String phone, String code, String min) {
        //创建对象
        CCPRestSmsSDK restAPI = creat();
        HashMap<String, Object> result = null;
        //发送短信，模板-验证码-有效时间
        result = restAPI.sendTemplateSMS(phone, "1", new String[]{code, min});
        log.info("这是什么东西"+result.get("statusCode"));
        //判断成功与否
        if ("000000".equals(result.get("statusCode"))) {
            log.info("短信发送成功");
        } else {
            log.error("短信发送失败,错误代码为:" + result.get("statusCode"));
        }
    }

    public void setAccount(String account) {
        SMSUtil.account = account;
    }

    public  void setToken(String token) {
        SMSUtil.token = token;
    }

    public  void setAppId(String appId) {
        SMSUtil.appId = appId;
    }
}
