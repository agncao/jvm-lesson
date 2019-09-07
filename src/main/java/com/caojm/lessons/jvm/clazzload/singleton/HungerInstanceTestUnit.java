package com.caojm.lessons.jvm.clazzload.singleton;


/**
 * HungerInstance.ConstantInt 系统既然给了初始值，按照约束应该会触发类初始化呀，那为何没生成对象
 */
public class HungerInstanceTestUnit {
    public static void main(String[] args) {
        System.out.println(HungerInstance.ConstantInt); //1、是否会生成singleton instance?
//        HungerInstance.StaticInt=0; //2、是否会生成singleton instance?
//        HungerInstance instance = HungerInstance.getInstance();   //3、

        //------4、测试反射是否会出发类初始化 start---->
//        try {
//            Class clazz = Class.forName("com.caojm.jvm.singleton.HungerInstance");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
        //<------4、测试反射是否会出发类初始化 end <----
    }
}
