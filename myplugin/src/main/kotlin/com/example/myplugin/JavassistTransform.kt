package com.example.myplugin

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import org.gradle.api.Project
import com.android.build.api.transform.Context

import com.android.build.api.transform.Transform
import com.android.build.api.transform.QualifiedContent

import com.android.build.api.transform.TransformInput
import com.android.build.api.transform.TransformOutputProvider
import com.android.build.gradle.AppExtension

import com.android.utils.FileUtils
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import java.util.zip.ZipOutputStream


class JavassistTransform(project: Project, android: AppExtension) : Transform() {


    var project = project;
    val android = android;

    override fun getName(): String {
        return "JavassistTransform"
    }

    override fun getInputTypes(): MutableSet<QualifiedContent.ContentType> {
        return TransformManager.CONTENT_CLASS
    }

    override fun isIncremental(): Boolean {
       return false
    }

    override fun getScopes(): MutableSet<in QualifiedContent.Scope> {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    override fun transform(context: Context?, inputs: MutableCollection<TransformInput>?,
                           referencedInputs: MutableCollection<TransformInput>?,
                           outputProvider: TransformOutputProvider?, isIncremental: Boolean) {
        //super.transform(context, inputs, referencedInputs, outputProvider, isIncremental)
        System.out.println("----------开始Transform-----------")
        // Transform 的 inputs 分为两种类型，一直是目录，一种是 jar 包。需要分开遍历

        inputs!!.forEach {
            println("----------it.directoryInputs-----------:"+it.directoryInputs.size)
            it.directoryInputs.forEach {
                // demo1. 在MainActivity的onCreate()方法之前注入代码
                //println(it.file.absolutePath)
               // MyInject.injectOnCreate(it.file.absolutePath, project,android)

                val dir  = File(it.file.absolutePath)
                val files = dir.walk()
                files.forEach {
                    //println("file in dir:"+it.name)
                    //println("file in dir:"+it.absolutePath)
                    ///Users/hss/github/aop/plugindemo/build/intermediates/javac/debug/classes/com/hss01248/plugindemo/MainActivity.class
                    if (it.name.endsWith(".class")) {
                        val idx = it.absolutePath.indexOf("/classes/");
                        val classP = it.absolutePath.substring(idx + 9).replace("/", ".")
                        println("classname:" + classP+" , dir:"+dir.absolutePath)
                        //processDirClasses()
                    }
                }





                // 获取 output 目录
                val dest = outputProvider!!.getContentLocation(it.name, it.contentTypes,
                it.scopes, Format.DIRECTORY)
                // 将 input 的目录复制到 output 指定目录
                FileUtils.copyDirectory(it.file, dest)

            }
            println("----------it.jarInputs-----------:"+it.jarInputs.size)
            it.jarInputs.forEach{
                // 重命名输出文件（同目录 copyFile 会冲突）
                var jarName = it.name
                println(jarName)
                 val md5Name = it.file.getAbsolutePath().hashCode().toString()
                MyInject.injectOnCreate(it.file.absolutePath, project,android)

                if (jarName.endsWith(".jar")) {
                    jarName = jarName.substring(0, jarName.length - 4)
                }
                // 生成输出路径
                val outJarFile = outputProvider?.getContentLocation(jarName + md5Name, it.contentTypes,
                        it.scopes, Format.JAR)
                //使用zip来解压jar包,遍历内部的类:

                val inJarFile = it.file
                ZipInputStream(FileInputStream(inJarFile)).use { zis ->
                    ZipOutputStream(FileOutputStream(outJarFile)).use { zos ->
                        var i: ZipEntry?

                        while (zis.nextEntry.let { i = it; i != null }) {
                            val entry = i ?: break
                            zos.putNextEntry(entry)

                            //printFileCopy(inJarFile,outJarFile,entry)
                            /*jarFilters.forEach {
                                var  filter = it.second
                                if (!filter.test(entry.name)) {
                                    skip = true
                                }
                            }
                            if(skip){
                                // Skip this file
                                println("file skipped success: "+entry.name +"  in jar:"+inJarFile.absolutePath)
                                hasTarget = true;
                                continue
                            }*/
                            //println("class in jar:"+entry.name+" , jar:"+jarName)
                            //class in jar:     androidx.coordinatorlayout.R$attr.class , jar:com.google.gson:gson:2.8.5
                            if(entry.name.endsWith(".class")){
                                println("class name:"+entry.name.replace("/",".")+" , jar:"+jarName)
                            }else{
                               // print("dir :"+entry.name)
                            }
                            zis.copyTo(zos)
                            zos.closeEntry()
                            zis.closeEntry()
                        }
                    }
                }





                // 将输入内容复制到输出
                //FileUtils.copyFile(it.file, dest)
            }
        }

        System.out.println("----------结束Transform-----------")
    }

}
