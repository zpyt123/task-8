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
public class TimeAspectUserService {
    /*测试接口的性能，时间测试*/
    private static Logger logger = LoggerFactory.getLogger(TimeAspectUserService.class);
//    //一分钟，60000ms
//    private static final long ONE_MINUTE = 60000;

    @Pointcut("execution( * com.jnshu.service.impl.LoginRmiImpl.*(..))")
    public void userServiceTime() {
    }
    /*
     * 进入方法后打印日志
     */
//    @Before("myMethod()")
//    public void before(JoinPoint joinPoint){
//        logger.debug(this.getMethodName(joinPoint)+"start"+ DateUtil.(new Date()));
//    }

    @Around(value = "userServiceTime()")
    public Object dbLogTimer(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
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
        //调用目标函数
        logger.debug("----userService接口切面开始---");

        //单位毫秒
        long start = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        long time = System.currentTimeMillis() - start;
        monitorTime.setComsumetime(time);
        logger.info(" db" + " 响应时间：" + monitorTime.getComsumetime() + " ms" + "运行信息：运行" + monitorTime.getClassName()+ "方法名" + monitorTime.getMethodName() + "执行时间" + monitorTime.getLogTime());
        return result;

    }
}
