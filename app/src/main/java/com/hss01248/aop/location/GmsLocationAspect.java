package com.hss01248.aop.location;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class GmsLocationAspect {
    private static final String TAG = "LocationListenerAspect";

    //找不到就报:java.util.zip.ZipException: zip file is empty
    @Pointcut("execution(* com.google.android.gms.location.LocationListener.onLocationChanged(..))")
    public void onResul(){
    }

    @Around("onResul()")
    public Object weaveJoinPoint2(ProceedingJoinPoint joinPoint) throws Throwable {
        LocationListenerAspect.check(joinPoint);
        return  joinPoint.proceed();
    }
}
