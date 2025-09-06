package com.vayana.projectmanagement.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Aspect
@Component
public class ServiceLoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(ServiceLoggingAspect.class);

    @Around("within(@org.springframework.stereotype.Service *)&& !within(com.vayana.projectmanagement.service.CustomUserDetailsService)")
    public Object logServiceMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String className = signature.getDeclaringTypeName();
        String methodName = signature.getName();

        long startTime = System.currentTimeMillis();
        logger.info("START: {}.{} at {}", className, methodName, Instant.now());

        try {
            Object result = joinPoint.proceed();
            long endTime = System.currentTimeMillis();
            logger.info("END: {}.{} at {} | Duration: {} ms", className, methodName, Instant.now(), (endTime - startTime));
            return result;
        } catch (Throwable ex) {
            long endTime = System.currentTimeMillis();
            logger.info("ERROR: {}.{} at {} | Duration: {} ms", className, methodName, Instant.now(), (endTime - startTime));
            throw ex;
        }
    }
}