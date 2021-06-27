package com.byond.shifts.service.shared.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
public class AspectControl {

    @Before("execution(* com.byond.shifts.service.*..*(..))")
    public void logEntry(JoinPoint joinPoint) {
        if (log.isDebugEnabled()) {
            String className = joinPoint.getSignature().getDeclaringType().getSimpleName();
            log.debug("Enter Method {}.{} Params: {}", className, joinPoint.getSignature().getName(), joinPoint.getArgs());
        }
    }

    @AfterReturning(value = "execution(* com.byond.shifts.service.*..*(..))", returning = "result")
    public void logExitAfterReturn(JoinPoint joinPoint, Object result) {
        if (log.isDebugEnabled()) {
            String className = joinPoint.getSignature().getDeclaringType().getSimpleName();
            log.debug("Return Method {}.{} Result: {}.", className, joinPoint.getSignature().getName(), result);
        }
    }

    @AfterThrowing(pointcut = "execution(* com.byond.shifts.service.*..*(..))", throwing = "ex")
    public void logError(Exception ex) {
        ex.printStackTrace();
    }
}