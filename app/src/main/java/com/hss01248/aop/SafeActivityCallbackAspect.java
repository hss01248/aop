package com.hss01248.aop;

import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * by hss
 * data:2020/7/17
 * desc:
 */
@Aspect
public class SafeActivityCallbackAspect {

    public static final String TAG = "SafeActivityCallbackAspect";

    @Pointcut("execution(* android.app.Application.ActivityLifecycleCallbacks.on**(..))")
    public void methodTime() {

    }

    @Around("methodTime()")
    public Object weaveJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();
        //String funName = methodSignature.getMethod().getAnnotation(TimeSpend.class).value();

        //统计时间
        long begin = System.currentTimeMillis();
        Object result = null;
        try {
             result = joinPoint.proceed();
        }catch (Throwable throwable){
            throwable.printStackTrace();
        }
        long duration = System.currentTimeMillis() - begin;
        Log.e(TAG,joinPoint.getArgs()[0]+"."+methodName+"  耗时:"+duration+"ms" );

        return result;
    }
}
