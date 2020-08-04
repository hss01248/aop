package com.example.callbacklib;

import android.app.Application;

public class CallbackUtil {

    public static void init(Application application){
        application.registerActivityLifecycleCallbacks(new CrashCallback());
    }
}
