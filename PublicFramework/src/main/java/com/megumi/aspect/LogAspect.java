package com.megumi.aspect;

import com.alibaba.fastjson.JSON;
import com.megumi.annotation.SystemLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

@Component
@Aspect
@Slf4j
public class LogAspect {
    @Pointcut("@annotation(com.megumi.annotation.SystemLog)")
    public void pt(){}

    @Around("pt()")
    public Object aroundLog(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        //由于配置了统一异常处理，故不用aop的异常处理，采用抛出形式
        Object proceed;
        //方法执行前
        handleBefore(proceedingJoinPoint);
        try {
            proceed = proceedingJoinPoint.proceed();
            //方法执行后
            handleAfter(proceed);
        } finally {
            //无论怎么样都会执行
            handleFinally();
        }
        return proceed;
    }

    private void handleFinally() {
        // 结束后换行
        log.info("=======End=======" + System.lineSeparator());
    }

    private void handleAfter(Object proceed) {
        // 打印出参
        log.info("Response       : {}",proceed.toString());
    }

    private void handleBefore(ProceedingJoinPoint proceedingJoinPoint) {
        log.info("=======Start=======");
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        log.info("=======Start=======");
        // 打印请求 URL
        log.info("URL            : {}",request.getRequestURL());
        // 打印描述信息

        //获取到被增强方法的注解对象，拿到属性值
        SystemLog systemLog = getSystemLog(proceedingJoinPoint);


        log.info("BusinessName   : {}",systemLog.businessName());
        // 打印 Http method
        log.info("HTTP Method    : {}",request.getMethod());
        // 打印调用 controller 的全路径以及执行方法
        log.info("Class Method   : {}.{}",proceedingJoinPoint.getSignature().getDeclaringTypeName(),proceedingJoinPoint.getSignature().getName());
        // 打印请求的 IP
        log.info("IP             : {}",request.getRemoteHost());
        // 打印请求入参
        log.info("Request Args   : {}", JSON.toJSONString(proceedingJoinPoint.getArgs()));
    }

    //通过切入点得到注解对象
    private SystemLog getSystemLog(ProceedingJoinPoint proceedingJoinPoint)  {
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        SystemLog systemLog = signature.getMethod().getAnnotation(SystemLog.class);
        return systemLog;
    }
}
