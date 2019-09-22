package com.caojm.lessons.jvm.gc;

/**
 * ${DESCRIPTION}
 *
 * @author <a href=mailto:caojianmin@jd.com>caojianmin1</a>
 * @create 2019-09-22 下午8:15
 **/
public class EdenFirstTestUnit {
    private static final int _1MB = 1024 * 1024;

    /**
     * 测试JVM内存的分配，新生代和老年代的分区
     * 参数：-verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8
     * 输出gc日志， 堆内存初始化大小20M，堆内存最大20M，年轻代大小10M， 输出GC的详细日志，Eden的区域是一个survivor区域的8倍
     */
    public static void testAllocation() {
        byte[] allocation1, allocation2, allocation3, allocation4;

        allocation1 = new byte[2 * _1MB];    //申请两兆
        allocation2 = new byte[2 * _1MB];
        allocation3 = new byte[2 * _1MB];
        //这里我们再eden已经申请了8M的空间
        allocation4 = new byte[4 * _1MB];
    }

    public static void main(String[] args){
        testAllocation();

    }

}
