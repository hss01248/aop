package com.example.myplugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import com.android.build.gradle.AppExtension

public class MyPlugin implements Plugin<Project>{

    @Override
  public   void apply(Project project) {
        System.out.println("========================");
        System.out.println("Hello gradle plugin! Powered by AppBlog.CN");
        System.out.println("========================");

        System.out.println("------------------开始----------------------");
        System.out.println("这是我们的自定义插件!");
        //AppExtension就是build.gradle中android{...}这一块
        def android = project.extensions.getByType(AppExtension)

        //注册一个Transform
        def classTransform = new JavassistTransform(project);
        android.registerTransform(classTransform);

        System.out.println("------------------结束了吗----------------------");
    }
}