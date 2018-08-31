package com.jnshu.service.impl;

import com.jnshu.dao.LoginDao;
import com.jnshu.model.Login;
import com.jnshu.service.LoginService;
import com.jnshu.util.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
@Component
public class LoginServiceImpl implements LoginService {
    @Autowired
    LoginDao loginDao;
    @Autowired
    RedisUtil redisUtil;
    private static Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

    @Override
    //查找用户
    public Login findIdLogin(Integer id) {
        return loginDao.findIdLogin(id);
    }

    @Override
    public Login findNameLogin(String userName ) {
//        Login login ;
//        try {
//            if (redisUtil.get("login")!=null){
//                login = (Login) redisUtil.get("login");
//                logger.info("拿到的缓存是：" + login + "---------------");
//                return login;
//            } else {
//                login = loginDao.findNameLogin(userName);
//                boolean isSuccess = redisUtil.set("login", loginDao.findNameLogin(userName));
//                logger.info("是否成功：" + isSuccess);
//                logger.info("写入到缓存中");
//                return login;
//            }
//        }catch(Exception e){
//            e.printStackTrace();
//            login = loginDao.findNameLogin(userName);
//            logger.info("传来的信息是:"+login);
//            return login;
//        }
        return loginDao.findNameLogin(userName);
    }



    @Override
    public List<Login> findListLogin () {
        List<Login> login;
            if (redisUtil.get("login") != null) {
                login = (List<Login>) redisUtil.get("login");
                logger.info("拿到的缓存是：" + login + "------");
                return login;
            } else {
                login = loginDao.findListLogin();
                boolean isSuccess = redisUtil.set("login", loginDao.findListLogin());
                logger.info("是否成功：" + isSuccess);
                return login;
            }
    }
    @Override
    public boolean insertLogin (Login login){
        login.setSalt("zhangpen");
        login.setCreatTime(new Date());
        login.setUpdateTime(new Date());
        logger.info("输入数据："+login);
      return loginDao.addLogin(login);
    }

    @Override
    public boolean deleteLogin(Integer id) throws Exception {
        //执行先删除数据库信息
        loginDao.deleteLogin(id);
        //删除缓存
        try {
            redisUtil.del("login" + id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return loginDao.deleteLogin(id);
    }

    @Override
    public boolean updateLogin(Login login) throws Exception {
        login.setLoginTime(new Date());
        return loginDao.updateLogin(login);
    }

    @Override
    public Login findPhoneLogin(String phone) throws Exception {
        return loginDao.findPhoneLogin(phone);
    }


}
