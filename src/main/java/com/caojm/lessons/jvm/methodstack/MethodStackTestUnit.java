package com.caojm.lessons.jvm.methodstack;

/**
 * 当创建一个线程时，同时会创建一个栈，栈的大小和深度都是固定的
 * 一个方法开辟一块栈桢内存区域
 * iconst_1 将常量 1 压到栈定
 *
 *
 * @author <a href=mailto:caojianmin@jd.com>caojianmin1</a>
 * @create 2019-09-08 下午5:15
 **/
public class MethodStackTestUnit {
    private static final int ConstInt = 532;
    private int val=3;
    public int increaseStep(int step){
        int a = 1;
        int c= (val+a)*step;
        return c;
    }

    public static void main(String[] args) {
        MethodStackTestUnit testUnit = new MethodStackTestUnit();
        System.out.println(testUnit.increaseStep(3));
//        Thread t = new Thread(new Runnable() {
//            public void run() {
//                System.out.println("start to run");
//            }
//        });
//        t.start();

    }

}
