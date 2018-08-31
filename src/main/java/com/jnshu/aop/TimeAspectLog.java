package com.jnshu.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Aspect
@Component
public class TimeAspectLog {
    private static Logger logger = LoggerFactory.getLogger(TimeAspectLog.class);
//    //导入切点
    //监测方法执行时间
    public  void  loginServiceTime(){}
    //环绕执行
    @Around("execution( * com.jnshu.service.impl.LoginRmiImpl.*(..))")
    public Object loginTime(ProceedingJoinPoint joinPoint) throws  Throwable{
        //时间转换成yyy-mm-dd hh格式
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //执行前的系统时间
        Long   t1 =System.currentTimeMillis();
        //将当前时间进行转换
        String time1 =simpleDateFormat.format(new Date());
        //统计官网时间
        logger.info("开始网站统计："+joinPoint.getSignature().getName());
        //开始的时间
        logger.info("当前时间："+time1);
        Object object =joinPoint.proceed();
        //结束的系统时间
        Long t2 = System.currentTimeMillis();
        //执行后的系统时间
        String time2 =simpleDateFormat.format(new Date());
        //统计方法执行名称
        logger.info("结束网站统计："+joinPoint.getSignature().getName());
        logger.info("结束时间是："+time2);
        //方法耗时
        logger.info("耗时："+(t2-t1)+"毫秒");
        return object;

    }
    @Around(value = "execution( * com.jnshu.service.impl.UserServiceImpl.*(..))")
    public Object logger(ProceedingJoinPoint joinPoint) throws Throwable {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long t1 =System.currentTimeMillis();
        String time1 =simpleDateFormat.format(new Date());
        logger.info("统计网站开始："+joinPoint.getSignature().getName());
        logger.info("官网时间："+time1);
        Object object =joinPoint.proceed();
        long t2 =System.currentTimeMillis();
        String time2 = simpleDateFormat.format(new Date());
        logger.info("官网结束统计："+joinPoint.getSignature().getName());
        logger.info("结束时间："+time2);
        logger.info("耗时："+(t2-t1)+"毫秒");
        return object;

    }



}
