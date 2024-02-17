package com.medium.clone.config;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    // this gets executed before execution of a method
    @Before("execution(* com.medium.clone.*(..))")
    public void logMethodEntry(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        System.out.println("********* Inside A function: " + methodName + "  ***********");
    }

    // this gets executed after execution of a method
    @After("execution(* com.medium.clone.*(..))")
    public void logMethodExit(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        System.out.println("********* Outside A function: " + methodName + "  ************");
    }

    // this gets executed before and after execution of a method
//    @Around("execution(* com.medium.clone.*(..))")
//    public void logAround() {
//
//    }

    // this gets executed after successful execution of a method
//    @AfterReturning(pointcut = "execution(* com.medium.clone.*(..))", returning = "result")
//    public void logAfterMethodExecution(Object result) {
//
//    }

    // this gets executed when method throws an exception
    @AfterThrowing(pointcut = "execution(* com.medium.clone.*(..))", throwing = "exception")
    public void logAfterThrowing(Exception exception) {
        logger.error("Exception thrown: {}", exception.getMessage());
    }
}
