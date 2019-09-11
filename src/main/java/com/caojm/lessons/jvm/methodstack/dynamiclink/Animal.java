package com.caojm.lessons.jvm.methodstack.dynamiclink;

/**
 * ${DESCRIPTION}
 *
 * @author <a href=mailto:caojianmin@jd.com>caojianmin1</a>
 * @create 2019-09-09 上午11:40
 **/
public abstract class Animal {
    public abstract void run();

    public void eat(){
        System.out.println("All the animals needs to eat");
    }
}
