package com.caojm.lessons.multithread;

import java.util.concurrent.TimeUnit;

/**
 * 可见性：一个线程修改，另一个线程能被通知到。可以重复读
 *
 * 对比一下[1]和[2]有无volatile的情况
 * JMM内存模型：每个线程都有自己的内存，会从主内存将变量的值copy到自己的缓冲区里，就不会读主内存的值
 * 如果无volatile：(1)在[4]处启动线程时，由于此线程非常的忙，所以一直没空从主内存加载新的值，此线程中的running 就一直是变更前的值(即true)
 *              (2) 如果在 【3】处sleep一下，cpu可能会空闲一下，这时候可能会读到running变更后的值。
 *
 * 如果有volatile：一但running被其他线程改写并写回到主内存时，主内存会通知到线程自己的内存区值已经发生变更
 *
 * @author <a href="mailto:caojianmin@jd.com">caojianmin</a>
 * @create 2020/01/27 23:03
 */
public class VolatileTestUnit {
    //[1]
//    private volatile boolean running=true;

    //[2]
    private boolean running=true;

    public void go(){
        System.out.println("I'm started");

        while (running){

            /*
            //[3]
            try {
                TimeUnit.MILLISECONDS.sleep(200);
            }catch (Exception e){
                System.out.println(e);
            }
             */

        }

        System.out.println("stop to run");
    }

    public static void main(String[] args) {
        VolatileTestUnit testUnit = new VolatileTestUnit();
        //[4]
        Thread t=new Thread(()->testUnit.go());
        t.start();
        try {
            TimeUnit.SECONDS.sleep(1);
        }catch (Exception e){
            System.out.println(e);
        }
        testUnit.running=false;
    }
}
