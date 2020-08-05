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
                println(it.file.absolutePath)
                MyInject.injectOnCreate(it.file.absolutePath, project,android)
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

                if (jarName.endsWith(".jar")) {
                    jarName = jarName.substring(0, jarName.length - 4)
                }
                // 生成输出路径
                val dest = outputProvider?.getContentLocation(jarName + md5Name, it.contentTypes,
                        it.scopes, Format.JAR)
                // 将输入内容复制到输出
                FileUtils.copyFile(it.file, dest)
            }
        }

        System.out.println("----------结束Transform-----------")
    }

}
