package com.example.myplugin

import org.gradle.api.Plugin
import org.gradle.api.Project

public class MyPlugin implements Plugin<Project>{

    @Override
  public   void apply(Project target) {
        System.out.println("========================");
        System.out.println("Hello gradle plugin! Powered by AppBlog.CN");
        System.out.println("========================");
    }
}