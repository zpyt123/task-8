package com.jnshu.model;

import java.util.Date;

public class MonitorTime {
    private String className;
    private  String methodName;
    private Date LogTime;
    private  Long Comsumetime;
    public MonitorTime(){}

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Date getLogTime() {
        return LogTime;
    }

    public void setLogTime(Date logTime) {
        LogTime = logTime;
    }

        public Long getComsumetime() {
        return Comsumetime;
    }

    public void setComsumetime(Long comsumetime) {
        Comsumetime = comsumetime;
    }

    @Override
    public String toString() {
        return "MonitorYime{" +
                "className='" + className + '\'' +
                ", methodName='" + methodName + '\'' +
                ", LogTime='" + LogTime + '\'' +
                ", Comsumetime='" + Comsumetime + '\'' +
                '}';
    }
}
