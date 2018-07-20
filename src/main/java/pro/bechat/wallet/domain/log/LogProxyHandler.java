package pro.bechat.wallet.domain.log;

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

    //@Around("target(pro.bechat.wallet.domain.support.ServiceSupport)")
    public Object aroundJoinPoint(ProceedingJoinPoint pjp) {
        Object result = null;
        Log.beforeInvoke(pjp.getTarget().getClass(), pjp.getSignature().getName(), pjp.getArgs());
        try {
            result = pjp.proceed();
            Log.returnInvoke(result);
        } catch (Throwable e) {
            Log.throwableInvoke(pjp.getTarget().getClass().getSimpleName(), pjp.getSignature().getName(), e.getMessage(),
                    Arrays.toString(new StackTraceElement[]{e.getStackTrace()[0], e.getStackTrace()[1],
                            e.getStackTrace()[2], e.getStackTrace()[3], e.getStackTrace()[5], e.getStackTrace()[6], e.getStackTrace()[7], e.getStackTrace()[8]}));
        }
        return result;
    }

    //@Around("execution(* pro.bechat.wallet.domain.support.EventService.*(..))")
    public Object around(ProceedingJoinPoint pjp) {
        Object result = null;
        Log.beforeInvoke(pjp.getTarget().getClass(), pjp.getSignature().getName(), pjp.getArgs());
        try {
            result = pjp.proceed();
            Log.returnInvoke(result); // -----(2)
        } catch (Throwable e) {
            Log.throwableInvoke("[result = exception: {%s}]", e.getMessage());
        }
        return result;
    }

    //@Before("execution(static * pro.bechat.wallet.process.*..process(..))")
    public void doBeforeInServiceLayer(JoinPoint joinPoint) {
        //TODO process code ...
    }

    //@After("execution(static * com.travelsky.ypb.*..process(..))")
    public void doAfterInServiceLayer(JoinPoint joinPoint) {
        //TODO process code...
    }


    //@Pointcut("execution(static * pro.bechat.wallet.process.*..process(..))")
    public void webLog(JoinPoint joinPoint) {
        //TODO process code...
    }

}
