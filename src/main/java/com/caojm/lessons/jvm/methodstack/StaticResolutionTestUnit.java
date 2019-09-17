package com.caojm.lessons.jvm.methodstack;

import com.caojm.lessons.jvm.methodstack.dynamiclink.Dog;

/**
 * 方法静态解析演示
 *
 * @author <a href=mailto:caojianmin@jd.com>caojianmin1</a>
 * @create 2019-09-17 上午11:29
 **/
public class StaticResolutionTestUnit {
    private void sayBye() {
        System.out.println("bye");
    }

    public static void main(String[] args) {
        Dog.sayHello();//静态方法调用

        StaticResolutionTestUnit sr = new StaticResolutionTestUnit();
        sr.sayBye();//私有方法调用
    }
}
