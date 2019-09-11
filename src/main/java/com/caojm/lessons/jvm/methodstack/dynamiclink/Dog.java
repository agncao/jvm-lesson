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
        System.out.println("dog running");
    }

    public static void getViceProfSalary(){
        System.out.println("return Vice Professor's salary");
    }
}
