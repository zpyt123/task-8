    package com.jnshu.service;

    import com.jnshu.model.User;
    import java.util.List;

    public interface UserService {
        //添加学员
        Integer insertUser(User user);
        //无redis查找学员
        List<User> findListUser();
        //有redis查找所有学员
        List<User>findListUser1();
        //id查找学员
        Integer findIdUser(Integer id) throws Exception;
        //删除学员
        Integer deleteUser(Integer id);
        //更新学员信息
        String updateUser(User user);
        //redis查找数据
        User getId(Integer id);
        //无redis
        User getId1(Integer id);
    }

