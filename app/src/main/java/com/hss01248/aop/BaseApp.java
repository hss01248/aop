package com.hss01248.aop;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.callbacklib.CallbackUtil;
import com.hss01248.aop.okhttp.OkhttpAspect;

import okhttp3.OkHttpClient;

/**
 * by hss
 * data:2020/7/17
 * desc:
 */
public class BaseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(@NonNull Activity activity) {

            }

            @Override
            public void onActivityResumed(@NonNull Activity activity) {
                //int i = 9/0;
            }

            @Override
            public void onActivityPaused(@NonNull Activity activity) {

            }

            @Override
            public void onActivityStopped(@NonNull Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {

            }
        });
        CallbackUtil.init(this);

        OkhttpAspect.addHook(new OkhttpAspect.OkhttpHook() {
            @Override
            public void beforeBuild(OkHttpClient.Builder builder) {
                Log.e("hook",builder.toString());
            }
        });

    }
}
