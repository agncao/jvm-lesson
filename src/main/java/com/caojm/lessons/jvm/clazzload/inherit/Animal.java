package com.caojm.lessons.jvm.clazzload.inherit;

/**
 * ${DESCRIPTION}
 *
 * @author <a href=mailto:caojianmin@jd.com>caojianmin1</a>
 * @create 2019-09-09 上午11:40
 **/
public abstract class Animal {
    static {
        System.out.println("Animal Class loaded");
    }
    public Animal(){
        System.out.println("Animal Object initialed");
    }
    public abstract void run();
}
