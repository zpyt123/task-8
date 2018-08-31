package com.jnshu.main;



import org.springframework.context.support.ClassPathXmlApplicationContext;

public class RmiService {
    public static void main (String args[])throws InterruptedException{
        ClassPathXmlApplicationContext cx = new ClassPathXmlApplicationContext("spring-mybatis.xml");
        System.out.println("启动服务端服务");
        Object lock =new Object();
        synchronized (lock) {
            lock.wait();
        }

    }
}
