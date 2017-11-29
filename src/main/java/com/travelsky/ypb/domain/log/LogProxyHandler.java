package com.travelsky.ypb.domain.log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * aop 日志记录
 */
@Component
@Aspect
@Order(-5)
public class LogProxyHandler {


    /**
     * @param pjp 环绕切点
     * @return o
     */
    //@Around("execution(public * com.travelsky.ypb.process.*..process(..))")
    @Around("target(com.travelsky.ypb.domain.support.ServiceSupport)")
    public Object aroundJoinPoint(ProceedingJoinPoint pjp) {
        Object result = null;
        // *) 函数调用前, 拦截处理, 作ThreadLocal的初始化工作
        Log.beforeInvoke(pjp.getTarget().getClass(), pjp.getSignature().getName(), pjp.getArgs()); // -----(1)
        try {
            result = pjp.proceed();
            // *) 函数成功返回后, 拦截处理, 进行日志的集中输出
            Log.returnInvoke(result); // -----(2)
        } catch (Throwable e) {
            // *) 出现异常后, 拦截处理, 进行日志集中输入 // -----(3)
            Log.throwableInvoke(pjp.getTarget().getClass().getSimpleName(), pjp.getSignature().getName(), e.getMessage(),
                    Arrays.toString(new StackTraceElement[]{e.getStackTrace()[0], e.getStackTrace()[1]}));
        }
        return result;
    }

    /**
     * @param pjp 环绕切点
     * @return o
     */
    @Around("execution(* com.travelsky.ypb.domain.support.EventService.*(..))")
    public Object around(ProceedingJoinPoint pjp) {
        Object result = null;
        // *) 函数调用前, 拦截处理, 作ThreadLocal的初始化工作
        Log.beforeInvoke(pjp.getTarget().getClass(), pjp.getSignature().getName(), pjp.getArgs()); // -----(1)
        try {
            result = pjp.proceed();
            // *) 函数成功返回后, 拦截处理, 进行日志的集中输出
            Log.returnInvoke(result); // -----(2)
        } catch (Throwable e) {
            // *) 出现异常后, 拦截处理, 进行日志集中输入 // -----(3)
            Log.throwableInvoke("[result = exception: {%s}]", e.getMessage());
        }
        return result;
    }


    /**
     * 方法调用前触发
     *
     * @param joinPoint 切点
     */
    @Before("execution(public * com.travelsky.ypb.process.*..process(..))")
    public void doBeforeInServiceLayer(JoinPoint joinPoint) {
        //TODO process code ...
    }


    /**
     * 方法调用后触发
     *
     * @param joinPoint 切点
     */
    @After("execution(public * com.travelsky.ypb.*..process(..))")
    public void doAfterInServiceLayer(JoinPoint joinPoint) {
        //TODO process code...
    }

    /**
     * @param joinPoint 切点
     */
    @Pointcut("execution(public * com.travelsky.ypb.process.*..process(..))")
    public void webLog(JoinPoint joinPoint) {
        //TODO process code...


    }

}
