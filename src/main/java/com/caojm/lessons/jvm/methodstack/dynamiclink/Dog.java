package com.caojm.lessons.jvm.methodstack.dynamiclink;

/**
 * ${DESCRIPTION}
 *
 * @author <a href=mailto:caojianmin@jd.com>caojianmin1</a>
 * @create 2019-09-09 上午11:43
 **/
public class Dog extends Animal {
    @Override
    public void run() {
        System.out.println("dogs running");
    }


    @Override
    public void eat(){
        System.out.println("dogs eat too much");
    }

    public static void sayHello(){
        System.out.println("wang~ wang~ wang~");
    }
}
