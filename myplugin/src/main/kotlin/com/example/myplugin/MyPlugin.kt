package com.example.myplugin

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class MyPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        System.out.println("========================");
        System.out.println("Hello gradle plugin! Powered by AppBlog.CN");
        System.out.println("========================");

        System.out.println("------------------开始----------------------");
        System.out.println("这是我们的自定义插件!");
        //AppExtension就是build.gradle中android{...}这一块
        val android = project.extensions.getByType(AppExtension::class.java)

        //注册一个Transform
        val classTransform =  JavassistTransform(project,android);
        android.registerTransform(classTransform);

        System.out.println("------------------结束了吗----------------------");
    }
}