package com.caojm.lessons.multithread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 线程1向容器中加入元素,线程2检测容器大小,当容器超过5个元素线程2要中断,而运行结果并没有中断，是为何？
 * 如何改写才能达到程序想要的结果：将代码[1] 改成代码[2]
 *
 * @author <a href="mailto:caojianmin@jd.com">caojianmin</a>
 * @create 2020/01/28 13:39
 */
public class ContainerAddAndSizeTestUnit {
    //[1]
    private List list = new ArrayList();
    //[2]
//    private volatile List list = new ArrayList();

    public void add(Object o){
        list.add(o);
    }

    public int size(){
        return list.size();
    }

    public static void main(String[] args) {
        ContainerAddAndSizeTestUnit testUnit = new ContainerAddAndSizeTestUnit();
        new Thread(()->{
            for (int i=0;i<10;i++){
                testUnit.add(new Object());
                System.out.println("t1:add "+i);
                try {
                    TimeUnit.MILLISECONDS.sleep(200);
                }catch (Exception e){
                    System.out.println(e);
                }
            }
        }).start();

        new Thread(()->{
            System.out.println("t2 start");
            while (true){
                if(testUnit.size()==5){
                    break;
                }
            }
            System.out.println("t2 break");
        }).start();
    }
}
