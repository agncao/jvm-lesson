package com.caojm.lessons.jvm.clazzcode;

/**
 * ${DESCRIPTION}
 *
 * @author <a href=mailto:caojianmin@jd.com>caojianmin1</a>
 * @create 2019-09-08 下午5:15
 **/
public class ClazzByteCodeTestUnit {
    private int seed;
    private static String Hello= "Hello";   //会放在常量池

    private static final int Step = 3;

    public int increaseStep(){
        return  seed+Step;
    }

    public static void main(String[] args) {
        ClazzByteCodeTestUnit testUnit = new ClazzByteCodeTestUnit();
        System.out.println(testUnit.increaseStep());
    }
}
