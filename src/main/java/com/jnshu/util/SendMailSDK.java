package com.jnshu.util;

import com.alibaba.fastjson.JSONObject;
import com.jnshu.model.Login;
import com.jnshu.service.LoginService;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


public class SendMailSDK {
    @Autowired
    LoginService loginService;
    @Autowired
    RedisUtil redisUti;
    private static Logger logger = LoggerFactory.getLogger(SendMailSDK.class);
    //邮箱认证
    private String apiUser ;
    private String apiKey;
    //邮箱发送接口
    private String apiUrl ;
    //发件人邮箱
    private String from = "sendcloud@sendcloud.org";
    //收件人地址
    //private String rcpt_to = "";
    //邮件标题
    private String subject = "学生系统验证通知";
    //邮件内容
    private String sendBodyBegin = "<html><H1><a href=\"";
    private String sendBodyEnd = "\">点击验证邮箱,五分钟后失效</a></H1></html>";
    private String randInt;
    //发件人
    private String fromName = "pete";
    // 构建http请求
    private HttpPost httpPost;
    private CloseableHttpClient httpClient;
    //外部调用发送方法
    public boolean sendMail(String httpUrl, Login login) {
        return sendMailReal(login, httpUrl, subject,fromName);
    }
    public boolean sendMail(String httpUrl, Login login, String toSubject){
        return  sendMailReal(login,toSubject,httpUrl,fromName);
    }
    public boolean sendMail(String httpUrl, Login login, String toSubject, String fromName){
        return sendMailReal(login,toSubject,httpUrl,fromName);
    }
    public boolean sendMailReal(Login login, String httpUrl, String subject, String fromName) {
        //构建http请求，用户点击时发送邮件
        httpPost = new HttpPost(apiUrl);
        httpClient = HttpClients.createDefault();
        randInt = RandNum.getNumLength(6);
        //拼接url httpUrl 当前项目的根目录
        String sendBody = sendBodyBegin + httpUrl + "/" + randInt + sendBodyEnd;
        logger.info("拼接的内容是:" + sendBody);
        //http 发送内容是
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("apiUser", apiUser));
        params.add(new BasicNameValuePair("apiKey", apiKey));
        params.add(new BasicNameValuePair("to", login.getMail()));
        //后面获取的值是邮箱提供的
        params.add(new BasicNameValuePair("from", from));
        params.add(new BasicNameValuePair("fromName", fromName));
        params.add(new BasicNameValuePair("subject", subject));
        params.add(new BasicNameValuePair("html", sendBody));
        try {
            //发送请求，转换为json格式
            httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            HttpResponse response = httpClient.execute(httpPost);
            //获取返回值
            JSONObject jsonObject = JSONObject.parseObject(EntityUtils.toString(response.getEntity()));
            //判断请求是否成功
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                //判断用户邮件发送是否成功
                if ((Integer)jsonObject.get("statusCode")== 200){
                    //显示验证码
                    logger.info("验证码信息是:"+randInt);
                    //邮箱状态
                    logger.info("邮箱状态显示:"+login.getMailState());
                    //写入缓存
                    redisUti.set(randInt,randInt,1000*60*5);
                    //发送成功
                    logger.info("发送成功"+redisUti.get(randInt));
                    logger.info("返回信息是:"+jsonObject.toJSONString());
                    return true;
                }else {
                    logger.debug("邮件发送失败:");
                    logger.debug("返回信息:"+jsonObject.toJSONString());
                    return false;
                }
            } else {
                logger.debug("发送失败");
                return false;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            logger.debug("发送失败+UnsupportedEncodingException");
            return false;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            logger.debug("接受请求失败+httpClient.execute(httpPost)");
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            logger.debug("转换失败EntityUtils.toString(response.getEntity())");
            return false;
        }catch (Exception e){
            e.printStackTrace();
            logger.debug("写入数据库失败");
            return  false;
        }
    }

    public void setApiUser(String apiUser) {
        this.apiUser = apiUser;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }
}
