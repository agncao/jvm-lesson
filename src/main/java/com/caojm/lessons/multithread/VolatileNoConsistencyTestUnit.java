package com.caojm.lessons.multithread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Volatile只能保证可见性，不可保证一致性
 *
 * @author <a href="mailto:caojianmin@jd.com">caojianmin</a>
 * @create 2020/01/27 23:34
 */
public class VolatileNoConsistencyTestUnit {
    private volatile int count=0;

    public void increase(){
        System.out.println(Thread.currentThread().getName());
        for(int i=0;i<1000;i++){
            try {
            TimeUnit.MILLISECONDS.sleep(5);
            }catch (Exception e){
                System.out.println(e);
            }
            count++;
        }
    }

    public static void main(String[] args) {
        VolatileNoConsistencyTestUnit testUnit = new VolatileNoConsistencyTestUnit();
        List<Thread> threads = new ArrayList<>();
        for(int i=0;i<10;i++){
            threads.add(new Thread(()->testUnit.increase()));
        }

        threads.forEach(o->o.start());

        threads.forEach(o->{
            try {
                o.join();
            }catch (Exception e){
                System.out.println(e);
            }
        });

        System.out.println("count="+testUnit.count);

    }
}
