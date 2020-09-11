package com.hss01248.aop.okhttp;

import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;

/**
 * by hss
 * data:2020/7/17
 * desc:
 */
@Aspect
public class ThreadAspect {

    private static final String TAG = "ThreadAspect";

    @Pointcut("execution(* java.lang.Thread.getStackTrace(..))")
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
        Log.e(TAG,className+","+joinPoint.getArgs()+"."+methodName+"  耗时:"+duration+"ms" );

        return result;
    }


}
