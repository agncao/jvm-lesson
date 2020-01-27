package com.caojm.lessons.jvm.gc;

/**
 * 大对象直接进入老年区
 * -verbose:gc -Xms30M -Xmx30M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8
 *
 * @author <a href=mailto:caojianmin@jd.com>caojianmin1</a>
 * @create 2019-09-22 下午11:05
 **/
public class OldGenerationFirstTestUnit {
    private static final int _1MB = 1024 * 1024;
    byte[] allocation1, allocation2, allocation3, allocation4;

    public static void testAllocation() {
        byte[] allocation1, allocation2, allocation3, allocation4;

        allocation4 = new byte[9 * _1MB];
    }


    public static void main(String[] args){
        testAllocation();

    }
}
