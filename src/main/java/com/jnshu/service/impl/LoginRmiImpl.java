package com.jnshu.service.impl;
import com.jnshu.service.LoginService;
import com.jnshu.model.Login;
import com.jnshu.model.Profession;
import com.jnshu.model.User;
import com.jnshu.util.*;
import com.jnshu.service.LoginRMI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.jnshu.dao.ProfessionDao;
import com.jnshu.dao.UserDao;
import java.util.Arrays;
import java.util.List;
@Service
@Component
public class LoginRmiImpl  implements LoginRMI {
    @Autowired
    UserDao userDao;
    @Autowired
    ProfessionDao professionDao;
    @Autowired
    LoginService loginService;
    @Autowired
    SMSUtil smsUtil;
    @Autowired
    SendMailSDK sendMailSDK;
    @Autowired
    AliyunOSSAPI aliyunOSSAPI;
    @Autowired
    RedisUtil redisUtil;
    private static Logger log = LoggerFactory.getLogger(LoginRmiImpl.class);
    @Override
    public Boolean loginName(String userName, String password) {
        //加密密码
        String passwordMD5 = Md5Util.stringToMD5(password + userName);
        //加密后的密码是
        log.info("passwordMD5" + passwordMD5);
        try {
            Login login = loginService.findNameLogin(userName);
            log.debug("login: " + login.toString());
            if (login.getUserName().equals(userName) && login.getPassword().equals(passwordMD5)) {
                log.info("登录成功" + login);
                return true;
            }
            log.info("登录失败");
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            log.info("用户不存在:" + userName);
        }
        return false;

    }

    @Override
    public Boolean iphoneVerify(String phone) throws Exception {
        Login login = loginService.findPhoneLogin(phone);
        if (login != null) {
            log.info("用户存在，请重新输入");
            return false;
        } else {
            log.info("用户不存在，发送验证码");
            String code = RandNum.getNumLength(6);
            log.info("注册的发的验证码" + code);
            redisUtil.set("code", code, 1000 * 60 * 5);
            smsUtil.sendSMS(phone,code, "5");
            log.info("发送成功:");
            return true;
        }
    }

    @Override
    public Boolean register(String code,Login login) throws Exception {
        log.info("前端传来的对象是" + login +"验证码"+code);
        //查询用户是否存在
        Login l = loginService.findNameLogin(login.getUserName());
        if (l != null) {
            log.info("用户存在");
            return false;
        } else {
            log.info("用户不存在,开始注册");
            //比对用户验证码
            Object o = redisUtil.get("code");
            //验证码正确，开始注册
            if (o.equals(code)) {
                //使用用户名加盐
                String salt = login.getUserName();
                //为用户名加盐
                String passwordMD5 = Md5Util.stringToMD5(login.getPassword() + salt);
                log.info("加密后的密码是:" + passwordMD5);
                login.setMailState(1);
                login.setPassword(passwordMD5);
                //添加进数据库，注册成功
                loginService.insertLogin(login);
                log.info("注册信息是:" + login);
                return true;
            }
            return false;
        }

    }

    @Override
    public Boolean sendMail(String url, Login login) throws Exception {
        String httpUrl = url.split("/sendMail")[0] + "/verifyMail";
        log.info("访问项目网址是" + httpUrl);
        //生成验证码
        String randInt = RandNum.getNumLength(6);
        log.info("生成的验证码是:" + randInt);
        //写入用户邮箱状态
        login.setMailState(1);
        //写入缓存中
        redisUtil.set("randInt",randInt, 1000 * 60 * 5);
        //发送邮件接口
        return sendMailSDK.sendMail(httpUrl, login, randInt);

    }

    @Override
    public Boolean verifyMail(String randInt) {
        //传进来的缓存进行比对，确认缓存中的数据
        Object o = redisUtil.get("randInt");
        //比对缓存
        if (o.equals(randInt)){
            log.info("比对数据库信息正确");
            return true;
        }
        return false;
    }

    @Override
    public String uploadPhoto(MultipartFile file, Integer id) throws Exception {
        String fileName = file.getOriginalFilename();
        //调用接口上传文件
        Boolean a = aliyunOSSAPI.updateFile(id,file,fileName);
        System.out.println("fileName==="+file.getOriginalFilename());
        System.out.println("fileSize===="+file.getSize());
        System.out.println("fileContentType==="+file.getContentType());
        System.out.println("fileString==="+ file.toString());
        System.out.println("name==="+file.getName());
        System.out.println("fileInputStream==="+file.getInputStream());
        System.out.println("fileBytes====" + Arrays.toString(file.getBytes()));
        if (a){
            log.info("成功上传文件");
            String photo = aliyunOSSAPI.getUrl(fileName);
            log.info("生成的url是"+photo);
            //更新到数据库中
            Login login = loginService.findIdLogin(id);
            //更新用户的图片
            try {
                login.setPhoto(photo);
                loginService.updateLogin(login);
                log.info("上传成功"+login);
                return photo;
            }catch (Exception e){
                log.info("插入失败"+login);
                return null;
            }
        }
        return  null;
    }
    @Override
    //获取职业信息
    public List<Profession> getAllProfession() throws Exception {
        List<Profession> professions;
        if (redisUtil.get("professions") != null) {
            professions = (List<Profession>) redisUtil.get("professions");
            log.info("这是从缓存中取出来的数据" + professions + "------ ------");
            return professions;
        } else {
            professions = professionDao.getAllProfession();
            try {
                boolean isSuccess = redisUtil.set("professions",professions,1000*30*60);
                     log.info("写入状态" + isSuccess);
                   log.info("写进缓存：" + professions);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return professions;
    }
    @Override
    //获取学员总数
    public Integer allStudent() throws Exception {
        Integer a;
        if (redisUtil.get("a") != null) {
            a = (Integer) redisUtil.get("a");
            log.info("这是缓存中取出来的：" + a + "================");
            return a;
        } else {
            a = userDao.countStudent();
            try {
                boolean isSuccess = redisUtil.set("a", a);
                   log.info("是否成功：" + isSuccess);
                   log.info("将数据放进缓存中");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return a;
        }
    }
    @Override
    //获取工作人员信息
    public Integer studentWork() throws Exception {
        if (redisUtil.get("b")!= null) {
           Integer b = (Integer) redisUtil.get("b");
            log.info("这是缓存中取出来的：" + b + "================");
            return b;
        } else {
            log.info("进来了，这是什么东西");
         Integer  b = userDao.countWork();
            try {
                boolean isSuccess = redisUtil.set("b", b,1000*60*30);
                  log.info("写进成功成功：" + isSuccess);
                  log.info("将数据放进缓存中");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return b;
        }
    }
    @Override
    public List<User> findGoodUser() throws Exception {
        List<User> u;
        if (redisUtil.get("u")!= null) {
            u = (List<User>) redisUtil.get("u");
            log.info("这是从缓存中拿出来的的数据：" + u + "================");
            return u;
        } else {
            u = userDao.getGoodUser();
            try {
                boolean isSuccess = redisUtil.set("u",u,1000*60*30);
                  log.info("这是从数据库中拿到的数据");
                  log.info("写进成功：" + isSuccess);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return u;
    }

}
