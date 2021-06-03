package com.caojm.lessons.jvm.clazzload.singleton;


/**
 * 问：HungerInstance.ConstHello 的访问为何没触发类的初始化
 * 答：常量会在编译阶段会存入在调用者所在类的常量池中，本质上调用者并没有用到定义该常量所在的类，也就是调用者压根和这常量所在类没有关系
 * javap -c -l HungerInstanceTestUnit 反编译验证一下，里面有个ldc，其参数是个Hello常量
 * ldc:将int,float,string等常量从常量池推送到栈顶
 */
public class HungerInstanceTestUnit {
    public static void main(String[] args) {
        System.out.println(">>>>>>>>>>>>>");
//        System.out.println(HungerInstance.ConstHello); //1、是否会生成singleton instance?
        System.out.println("HungerInstance classLoad start!");
//        System.out.println(HungerInstance.UId); //3、是否会生成singleton instance?
//        System.out.println(HungerInstance.StaticHello); //4、是否会生成singleton instance?
        HungerInstance instance = HungerInstance.getInstance();   //5、

        //------6、测试反射是否会出发类初始化 start---->
//        try {
//            Class clazz = Class.forName("com.caojm.jvm.singleton.HungerInstance");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
        //<------4、测试反射是否会出发类初始化 end <----
    }
}
