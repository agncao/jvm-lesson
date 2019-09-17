package com.caojm.lessons.jvm.methodstack.dispatch;

/**
 * 方法的静态分派场景：方法的重载
 * 方法的重载是一种静态行为,判断的依据：入参的参数类型
 *
 * @author <a href=mailto:caojianmin@jd.com>caojianmin1</a>
 * @create 2019-09-11 下午2:42
 **/
public class StaticDispatchTestUnit {
    public void test(Grandpa object){
        System.out.println("Grandpa");
    }
    public void test(Father object){
        System.out.println("Father");
    }
    public void test(Son object){
        System.out.println("Son");
    }

    public static void main(String[] args) {
        StaticDispatchTestUnit testUnit = new StaticDispatchTestUnit();

        Grandpa father = new Father();
        Grandpa son = new Son();

        testUnit.test(father);
        testUnit.test(son);

    }
}

class Grandpa{

}

class Father extends Grandpa{

}

class Son extends Father{

}