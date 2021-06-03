package com.caojm.lessons.jvm.clazzload;


/**
 * 双亲委派
 * 测试：能返回启动类加载器来吗?
 *
 * @author <a href="mailto:caojianmin@jd.com">caojianmin</a>
 * @create 2019/09/07 21:55
 */
public class ParentalAppointmentTestUnit {
    public static void main(String[] args) {
        Object object = new Object();
        System.out.println(">>>>>>>>>>>>");
//        System.out.println(object.getClass().getClassLoader());
//        System.out.println(ParentalAppointmentTestUnit.class.getClassLoader().getParent().getParent());
//        System.out.println(ParentalAppointmentTestUnit.class.getClassLoader().getParent());
//        System.out.println(ParentalAppointmentTestUnit.class.getClassLoader());
        System.out.println("<<<<<<<<<<<<");
    }
}
