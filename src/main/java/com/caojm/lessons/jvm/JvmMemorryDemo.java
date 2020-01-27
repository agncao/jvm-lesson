package com.caojm.lessons.jvm;

import com.caojm.lessons.jvm.clazzload.inherit.Cat;

/**
 * 说明一下方法区、堆、栈之间的关系
 *
 * @author <a href=mailto:caojianmin@jd.com>caojianmin1</a>
 * @create 2019-09-21 上午11:45
 **/
public class JvmMemorryDemo {//运行时, jvm 把JvmMemorryDemo 的Class对象都放入方法区
    public   static   void  main(String[] args)  {//main 方法本身放入方法区。
        Cat cat = new Cat();   //cat是引用，所以放到栈区里， cat所指向对象放到堆里面
        cat.run();
    }
}
