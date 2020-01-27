package com.caojm.lessons.jvm;

/**
 * ${DESCRIPTION}
 *
 * @author <a href="mailto:caojianmin@jd.com">caojianmin</a>
 * @create 2019/09/23 22:12
 */
public class StaticTest {
    static {
        System.out.println("1");
    }
    public StaticTest(){
        System.out.println("2");
    }
}

class SubStaticTest extends StaticTest{

    static {
        System.out.println("A");
    }
    public SubStaticTest(){
        System.out.println("B");
    }
}

class Hello{
    public static void main(String[] args) {
        StaticTest test = new SubStaticTest();
        test=new SubStaticTest();
    }
}