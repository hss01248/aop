package com.hss01248.aop.location;

import android.location.Location;
import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;


//aspectj语法: https://blog.csdn.net/zhengchao1991/article/details/53391244
@Aspect
public class LocationListenerAspect {

    private static final String TAG = "LocationListenerAspect";

    @Pointcut("execution(* android.location.LocationListener.onLocationChanged(..))")
    public void onResult(){
    }
    //android.location.LocationListener
    //com.google.android.gms.location.LocationListener

    @Around("onResult()")
    public Object weaveJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        check(joinPoint);
        return  joinPoint.proceed();
    }



    public static void check(ProceedingJoinPoint joinPoint) {
        try {
            Object[] args =    joinPoint.getArgs();
            if(args != null && args.length > 0){
                for (int i = 0; i < args.length; i++) {
                    Object obj = args[i];
                    Log.d(TAG,obj+"");
                    if(obj instanceof Location){
                        Location location = (Location) obj;
                        LocationManager.location = location;
                        LocationManager.lat = location.getLatitude();
                        LocationManager.lon = location.getLongitude();
                        Log.d(TAG,"location set");
                    }
                }
            }
        }catch (Throwable throwable){
            throwable.printStackTrace();
        }
    }
}
