package com.jnshu.service;


import com.jnshu.model.Login;
import com.jnshu.model.Profession;
import com.jnshu.model.User;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.Cookie;
import java.util.List;
import java.util.Map;

public interface LoginRMI {
    //账户登录返回Boolean
    Boolean loginName(String userName, String password);
    //手机验证码验证
    Boolean iphoneVerify(String phone) throws Exception;
    //注册
    Boolean register(String code, Login login) throws Exception;
    //发送邮件接口
    Boolean sendMail(String url, Login login) throws Exception;
    //邮件验证
    Boolean verifyMail(String randInt);
    //图片上传功能
    String uploadPhoto(MultipartFile file, Integer id) throws Exception;
    //所有学生
    Integer allStudent() throws Exception;
    //已经工作学生
    Integer studentWork() throws Exception;
    //输出所有职业信息
    List<Profession> getAllProfession() throws Exception;
    //优秀学员展示
    List<User> findGoodUser() throws Exception;
}
