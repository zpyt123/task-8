package com.jnshu.dao;

import com.jnshu.model.Profession;

import java.util.List;

//职业信息页面
public interface ProfessionDao {
    List<Profession> getAllProfession()throws Exception;
}
