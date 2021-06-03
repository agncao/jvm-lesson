package com.caojm.lessons.jvm.clazzload;

import java.net.URL;

/**
 * 启动类都加载了哪些文件
 * 可以查看 Launcher.BootClassPathHolder ->
 * static代码块初始化了Launcher.bootClassPath 路径下的文件系统
 *
 * @author <a href="mailto:caojianmin@jd.com">caojianmin</a>
 * @create 2019/09/07 21:46
 */
public class LauncherTestUnit {
    public static void main(String[] args) {
//
//            URL[] urls = sun.misc.Launcher.getBootstrapClassPath().getURLs();
//            for(URL url : urls){
//                System.out.println(url.toExternalForm());
//            }
    }

}

/*
打印结果如下：
file:/C:/Program%20Files/Java/jdk1.8.0_131/jre/lib/resources.jar
file:/C:/Program%20Files/Java/jdk1.8.0_131/jre/lib/rt.jar
file:/C:/Program%20Files/Java/jdk1.8.0_131/jre/lib/sunrsasign.jar
file:/C:/Program%20Files/Java/jdk1.8.0_131/jre/lib/jsse.jar
file:/C:/Program%20Files/Java/jdk1.8.0_131/jre/lib/jce.jar
file:/C:/Program%20Files/Java/jdk1.8.0_131/jre/lib/charsets.jar
file:/C:/Program%20Files/Java/jdk1.8.0_131/jre/lib/jfr.jar
file:/C:/Program%20Files/Java/jdk1.8.0_131/jre/classes
 */