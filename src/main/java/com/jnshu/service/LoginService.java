package com.jnshu.service;

import com.jnshu.model.Login;

import java.util.List;

public interface LoginService {
        //根据id查找用户
        Login findIdLogin(Integer id) throws Exception;
        //根据用户名查询
        Login findNameLogin(String userName) throws Exception;
        //查询全部用户
        List<Login> findListLogin() throws Exception;
        //添加新用户
        boolean insertLogin(Login login) throws Exception;
        //删除用户
        boolean deleteLogin(Integer id)throws Exception;
        //更新用户信息
        boolean updateLogin(Login login)throws Exception;
         //查找用户手机
         Login findPhoneLogin(String phone) throws Exception;


}
