package com.caojm.lessons.jvm.clazzload;

import com.caojm.lessons.jvm.clazzload.inherit.Cat;

import java.io.IOException;
import java.io.InputStream;

/**
 * 判断两个类是否相同，不及要有独立的类命名空间，还须由同一个类加载器才有意义
 * 用-XX:+TracingClassLoader跟踪，当前类被两个类加载器加载了两次
 *
 * @author <a href=mailto:caojianmin@jd.com>caojianmin1</a>
 * @create 2019-09-15 上午11:23
 **/
public class ClassLoaderTest {
    public static void main(String[] args)throws Exception{
        ClassLoader myClassLoader=new ClassLoader() {
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException{
                String fileName=name.substring(name.lastIndexOf(".")+1)+".class";
                InputStream inputStream=getClass().getResourceAsStream(fileName);
                if(null==inputStream) {
                    return super.loadClass(name);
                }try {
                    byte[] bytes=new byte[inputStream.available()];
                    inputStream.read(bytes);
                    return defineClass(name,bytes,0,bytes.length);
                }catch (IOException e){
                    throw new ClassNotFoundException(name);
                }
            }
        };
        Object object = myClassLoader.loadClass("com.caojm.lessons.jvm.clazzload.ClassLoaderTest").newInstance();
        System.out.println(object.getClass());
        if(object instanceof Cat){
            System.out.println("com.caojm.lessons.jvm.clazzload.ClassLoaderTest object");
        }else {
            System.out.println("NOT com.caojm.lessons.jvm.clazzload.ClassLoaderTest object");
        }
    }
}
