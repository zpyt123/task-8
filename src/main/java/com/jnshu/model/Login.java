package com.jnshu.model;

import java.io.Serializable;
import java.util.Date;

public class Login implements Serializable{
    private static final long serialVersionUID = 6256787784222102919L;
    private Integer id;
    private String userName;
    private String phone;
    private String mail;
    private String salt;
    private String fileName;
    private String password;
    private String stuType;
    private Date creatTime;
    private Date updateTime;
    private Date loginTime;
    private  Integer mailState;
    private String photo;

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public  Login(){}
    public Login(Integer id, String fileName){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getUserName() {
        return userName;
    }
    public String getMail() {
        return mail;
    }
    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhone() {
        return phone;
    }

    public Integer getMailState() {
        return mailState;
    }

    public void setMailState(Integer mailState) {
        this.mailState = mailState;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStuType() {
        return stuType;
    }

    public void setStuType(String stuType) {
        this.stuType = stuType;
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return "Login{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", phone='" + phone + '\'' +
                ", mail='" + mail + '\'' +
                ", salt='" + salt + '\'' +
                ", fileName='" + fileName + '\'' +
                ", password='" + password + '\'' +
                ", stuType='" + stuType + '\'' +
                ", creatTime=" + creatTime +
                ", updateTime=" + updateTime +
                ", loginTime=" + loginTime +
                ", mailState=" + mailState +
                ", photo='" + photo + '\'' +
                '}';
    }
}

