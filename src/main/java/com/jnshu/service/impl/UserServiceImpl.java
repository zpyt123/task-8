package com.jnshu.service.impl;

import com.jnshu.dao.ProfessionDao;
import com.jnshu.dao.UserDao;

import com.jnshu.model.User;
import com.jnshu.service.UserService;
import com.jnshu.util.MemcachedUtil;
import com.jnshu.util.RedisUtil;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;
    @Autowired
    ProfessionDao professionDao;
    @Autowired
    RedisUtil redisUtil;

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    //更新学员信息
    public String updateUser(User user) {
        user.setUpdate_at(new Date());
        return userDao.updateUser(user);

    }

    @Override
    public User getId(Integer id) {
        User user;
            if (MemcachedUtil.get("stuName" + id) != null) {
                user = (User) MemcachedUtil.get("stuName" + id);
                logger.info("缓存中存在的数据");
                return user;
            } else {
                user = userDao.getId(id);
               String stuName = new String();
                //数据存在写进缓存中
                try {
                    boolean isSuccess =MemcachedUtil.set(stuName, user);
                 //   logger.info("写入缓存成功" + isSuccess);
                } catch (Exception e) {
                    e.printStackTrace();

                }return user;
            }

    }

    @Override
    public User getId1(Integer id) {
        return userDao.getId(id);
    }


    @Override
    public Integer insertUser(User user) {
        //添加新学员信息
        return userDao.insertUser(user);
    }

    @Override
    public List<User> findListUser() {
        return userDao.findListUser();
    }

    //使用缓存
    @Override
    public List<User> findListUser1() {
        List<User> user;
            if (redisUtil.get("userDemo") != null) {
                user = (List<User>) redisUtil.get("user");
                logger.info("这是拿到的缓存：" + user + "================");
                return user;
            } else {
                user = userDao.findListUser();
                String v = new String();
                try {
                    boolean isSuccess = redisUtil.set(v, user,1000*60*10);
                 //   logger.info("这是数据库中拿的数据");
                 //   logger.info("是否成功：" + isSuccess);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return user;
            }

    }


    @Override
    public Integer deleteUser(Integer id) {
        //数据不存在时先更新缓存
        userDao.deleteUser(id);
        try{
            redisUtil.del("name" + id);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return userDao.deleteUser(id);
    }




    @Override
    public Integer findIdUser(Integer id) throws Exception {
        //查找学员信息

        return userDao.findIdUser(id);
    }


}