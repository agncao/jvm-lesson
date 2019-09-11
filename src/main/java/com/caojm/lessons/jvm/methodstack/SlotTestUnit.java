package com.caojm.lessons.jvm.methodstack;

/**
 * slot是可复用的表现在：某个变量超出了作用域，这些slot可以被其他复用
 *
 * @author <a href=mailto:caojianmin@jd.com>caojianmin1</a>
 * @create 2019-09-11 下午2:12
 **/
public class SlotTestUnit {
    public void test(int x){
        int i=3;
        if(x>3){
            int b=4;
            int a=1;
        }
        int c=2;
        int d=6;
    }
}
