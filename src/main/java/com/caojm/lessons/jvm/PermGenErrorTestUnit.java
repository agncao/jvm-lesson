package com.caojm.lessons.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * 元空间默认直接使用内存区域，当然也可以用参数来设置元空间的大小
 * 类的静态变量从原来的永久区挪到堆区
 *
 * @author <a href=mailto:caojianmin@jd.com>caojianmin1</a>
 * @create 2019-09-16 上午11:11
 **/
public class PermGenErrorTestUnit {
    static String  base = "string";
    public static void main(String[] args) {
        List<String> list = new ArrayList<String>();
        for (int i=0;i< Integer.MAX_VALUE;i++){
            String str = base + base;
            base = str;
            list.add(str.intern());
        }
    }
}
