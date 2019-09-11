package com.caojm.lessons.jvm.clazzload.inherit;

/**
 * ${DESCRIPTION}
 *
 * @author <a href=mailto:caojianmin@jd.com>caojianmin1</a>
 * @create 2019-09-09 上午11:41
 **/
public class Cat extends Animal {
    public Cat(){
        System.out.println("Cat Object initialed");
    }
    @Override
    public void run() {
        System.out.println("cat running");
    }
}
