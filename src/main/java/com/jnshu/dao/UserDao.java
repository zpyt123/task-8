package com.jnshu.dao;


import com.jnshu.model.User;

import java.util.List;

//学员页
public interface UserDao {
    //所有学生
    Integer countStudent() throws Exception;
    //已经工作
    Integer countWork() throws Exception;
    //根据ID查询
    Integer findIdUser(Integer id)throws  Exception;
    //添加学员
    Integer insertUser(User user);
    //删除学员
    Integer deleteUser(Integer id);
    //修改学员信息
    String updateUser(User user);
    //查找学员
    List<User> findListUser();
    //优秀学员展示
    List<User> getGoodUser()throws Exception;
    //json获取学员id
    User getId(Integer id);

}
