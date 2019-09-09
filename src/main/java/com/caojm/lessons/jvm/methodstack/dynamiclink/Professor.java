package com.caojm.lessons.jvm.methodstack.dynamiclink;

/**
 * ${DESCRIPTION}
 *
 * @author <a href=mailto:caojianmin@jd.com>caojianmin1</a>
 * @create 2019-09-09 上午11:43
 **/
public class Professor extends Employee {
    @Override
    public void getSalary() {
        System.out.println("return Professor salary");
    }

    public static void getViceProfSalary(){
        System.out.println("return Vice Professor's salary");
    }
}
