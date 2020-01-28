# volatile 有序性实现
## volatile 的 happens-before 关系
![volatile 的 happens-before 关系](https://upload-images.jianshu.io/upload_images/5714666-04e61c5e5c4e91c3.png?imageMogr2/auto-orient/strip|imageView2/2/w/587/format/web)

+ happens-before 规则中有一条是 volatile 变量规则：对一个 volatile 域的写，happens-before 于任意后续对这个 volatile 域的读。
```
//假设线程A执行writer方法，线程B执行reader方法
class VolatileExample {
    int a = 0;
    volatile boolean flag = false;
    
    public void writer() {
        a = 1;              // 1 线程A修改共享变量
        flag = true;        // 2 线程A写volatile变量
    } 
    
    public void reader() {
        if (flag) {         // 3 线程B读同一个volatile变量
        int i = a;          // 4 线程B读共享变量
        ……
        }
    }
}
```
+ 根据 happens-before 规则，上面过程会建立 3 类 happens-before 关系：
    + 根据程序次序规则：1 happens-before 2 且 3 happens-before 4
    + 根据 volatile 规则：2 happens-before 3
    + 根据 happens-before 的传递性规则：1 happens-before 4
    
+ 因为以上规则，当线程 A 将 volatile 变量 flag 更改为 true 后，线程 B 能够迅速感知

## volatile 禁止重排序
+ 为了性能优化，JMM 在不改变正确语义的前提下，会允许编译器和处理器对指令序列进行重排序。JMM 提供了内存屏障阻止这种重排序
+ Java 编译器会在生成指令系列时在适当的位置会插入内存屏障指令来禁止特定类型的处理器重排序
+ JMM 会针对编译器制定 volatile 重排序规则表


[volatile 相关整理](https://www.jianshu.com/p/ccfe24b63d87)