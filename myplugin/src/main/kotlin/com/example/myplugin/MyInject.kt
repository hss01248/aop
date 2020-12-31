package com.example.myplugin

import com.android.build.gradle.AppExtension
import javassist.ClassPool
import org.gradle.api.Project
import java.io.File

object MyInject {

    val classPool:ClassPool = ClassPool.getDefault()

    fun injectOnCreate(path: String, project: Project, android: AppExtension) {
        classPool.appendClassPath(path)
        classPool.appendClassPath(android.bootClasspath[0].toString())
        classPool.importPackage("android.os.Bundle")

        val dir  = File(path)
        if (dir.isDirectory()) {
             val files = dir.walk()
            files.forEach {
                //println("file in dir:"+it.name)
                //println("file in dir:"+it.absolutePath)
                ///Users/hss/github/aop/plugindemo/build/intermediates/javac/debug/classes/com/hss01248/plugindemo/MainActivity.class
                if(it.name.endsWith(".class")){
                    val idx = it.absolutePath.indexOf("/classes/");
                    val classP = it.absolutePath.substring(idx+9).replace("/",".")
                    println("classname:"+classP)
                }
                        if (it.name.equals("MainActivity.class")) {
                            // 获取 MainActivity
                            val ctClass = classPool.getCtClass("com.hss01248.plugindemo.MainActivity")
                            System.out.println("ctClass = " + ctClass)

                            // 解冻
                            if (ctClass.isFrozen()) {
                                ctClass.defrost()
                            }

                            // 获取到 onCreate() 方法
                            val ctMethod = ctClass.getDeclaredMethod("onCreate")
                            System.out.println("ctMethod = " + ctMethod)
                            // 插入日志打印代码
                            val insertBeforeStr = "android.util.Log.e(\"--->\", \"Hello222\");"

                            ctMethod.insertBefore(insertBeforeStr)
                            ctClass.writeFile(path)
                            ctClass.detach()
                        }
            }
        }else{
            val files = dir.walk()
            files.forEach {
                println("file in dir:" + it.name)
                println("file in dir:" + it.absolutePath)
            }
        }
    }

}
