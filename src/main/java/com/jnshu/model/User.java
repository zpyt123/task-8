package com.jnshu.model;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable{
    private static final long serialVersionUID = 1281553286341785486L;
    private Long id ;
    private  String stuName ;
    private  String stuIntroduction ;
    private Boolean isGood ;
    private  Boolean isWork ;
    private Date creat_at ;
    private  Date update_at;
    private String qq;
    private String pictrue;
    private Date entranceTime;
    private String school;
    private Integer onlineNum;
    private String dailyLink;
    private String wish;
    private String brother;
    private String whereKnown;
    public User(){}

    public Long getId() {
        return id;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getPictrue() {
        return pictrue;
    }

    public void setPictrue(String pictrue) {
        this.pictrue = pictrue;
    }

    public Date getEntranceTime() {
        return entranceTime;
    }

    public void setEntranceTime(Date entranceTime) {
        this.entranceTime = entranceTime;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public Integer getOnlineNum() {
        return onlineNum;
    }

    public void setOnlineNum(Integer onlineNum) {
        this.onlineNum = onlineNum;
    }

    public String getDailyLink() {
        return dailyLink;
    }

    public void setDailyLink(String dailyLink) {
        this.dailyLink = dailyLink;
    }

    public String getWish() {
        return wish;
    }

    public void setWish(String wish) {
        this.wish = wish;
    }

    public String getBrother() {
        return brother;
    }

    public void setBrother(String brother) {
        this.brother = brother;
    }

    public String getWhereKnown() {
        return whereKnown;
    }

    public void setWhereKnown(String whereKnown) {
        this.whereKnown = whereKnown;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public String getStuIntroduction() {
        return stuIntroduction;
    }

    public void setStuIntroduction(String stuIntroduction) {
        this.stuIntroduction = stuIntroduction;
    }

    public Boolean getGood() {
        return isGood;
    }

    public void setGood(Boolean good) {
        isGood = good;
    }

    public Boolean getWork() {
        return isWork;
    }

    public void setWork(Boolean work) {
        isWork = work;
    }

    public Date getCreat_at() {
        return creat_at;
    }

    public void setCreat_at(Date creat_at) {
        this.creat_at = creat_at;
    }

    public Date getUpdate_at() {
        return update_at;
    }

    public void setUpdate_at(Date update_at) {
        this.update_at = update_at;
    }

    @Override
    public String toString() {
        return "UserDemo{" +
                "id=" + id +
                ", stuName='" + stuName + '\'' +
                ", stuIntroduction='" + stuIntroduction + '\'' +
                ", isGood=" + isGood +
                ", isWork=" + isWork +
                ", creat_at=" + creat_at +
                ", update_at=" + update_at +
                ", qq='" + qq + '\'' +
                ", pictrue='" + pictrue + '\'' +
                ", entranceTime=" + entranceTime +
                ", school='" + school + '\'' +
                ", onlineNum=" + onlineNum +
                ", dailyLink='" + dailyLink + '\'' +
                ", wish='" + wish + '\'' +
                ", brother='" + brother + '\'' +
                ", whereKnown='" + whereKnown + '\'' +
                '}';
    }
}

