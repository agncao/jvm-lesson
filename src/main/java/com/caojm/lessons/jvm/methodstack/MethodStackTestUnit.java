package com.caojm.lessons.jvm.methodstack;

/**
 * ${DESCRIPTION}
 *
 * @author <a href=mailto:caojianmin@jd.com>caojianmin1</a>
 * @create 2019-09-08 下午5:15
 **/
public class MethodStackTestUnit {
    private int val=3;
    public int increaseStep(int step){
        return val+step;
    }

    public static void main(String[] args) {
        MethodStackTestUnit testUnit = new MethodStackTestUnit();
        System.out.println(testUnit.increaseStep(3));
        Thread t = new Thread(new Runnable() {
            public void run() {
                System.out.println("start to run");
            }
        });
        t.start();

    }

}
