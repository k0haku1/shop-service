package com.example.shop_service.annotation;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExecutionTimeAspect {

    @Around("@annotation(ExecutionTime)")
    public Object measureTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.nanoTime();

        Object result = joinPoint.proceed();

        long end = System.nanoTime();

        double duration = (end - start) / 1_000_000_000.0;
        System.out.println("Execution time of  : " + duration + " seconds");

        return result;
    }
}
