package com.jnshu.dao;

import com.jnshu.model.Login;

import java.util.List;

//登录页面
public interface LoginDao {
    //根据id查找用户
     Login findIdLogin(Integer id) ;
    //根据用户名查询
    Login findNameLogin(String userName);
    //查询全部用户
    List<Login> findListLogin();
    //添加新用户
    boolean addLogin(Login login);
    //用户更新操作
    boolean updateLogin(Login login);
    //删除用户
    boolean deleteLogin(Integer id);
    //通过手机查找用户信息
    Login findPhoneLogin(String phone);

}
