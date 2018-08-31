package com.jnshu.aop;


import com.jnshu.model.MonitorTime;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

@Aspect
@Component
public class TimeAspectController {
    private static Logger logger = LoggerFactory.getLogger(TimeAspectController.class);

    //控制层性能测试
 @Pointcut("execution(* com.jnshu.service.LoginRMI*(..))")
public void  controllerTime (){}

 @Around(value = "controllerTime()")
    public Object controLogTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        //创建性能监控信息类
        MonitorTime monitorTime = new MonitorTime();
        //获取目标类名
        String clazzName = proceedingJoinPoint.getTarget().getClass().getName();
        //获取方法名
        String methodName = proceedingJoinPoint.getSignature().getName();
        //记录目标类名
        monitorTime.setClassName(clazzName);
        //记录方法名
        monitorTime.setMethodName(methodName);
        //定义返回对象，得到方法需要的参数
        monitorTime.setLogTime(new Date());
        //日志记录
        logger.debug("---controller层--");
        //时间单位
        long start = System.currentTimeMillis();
        Object result =proceedingJoinPoint.proceed();
        long  time =System.currentTimeMillis() - start;
        monitorTime.setComsumetime(time);
        logger.info(" controller" + "响应时间：" + monitorTime.getComsumetime() + " ms" + "运行信息：运行" + monitorTime.getClassName()+ "方法名" + monitorTime.getMethodName() + "执行时间" + monitorTime.getLogTime());
    return  result;
    }


}
