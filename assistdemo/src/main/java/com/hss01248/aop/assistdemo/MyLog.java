package com.hss01248.aop.assistdemo;

import android.util.Log;

public class MyLog {

    public static int d(String tag,String msg){
       return Log.e("t"+tag,msg+".......");
    }
}
