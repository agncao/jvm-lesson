package com.caojm.lessons.nio.multi;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author caojianmin1 <caojianmin@jd.com>
 * @description
 * @date 2021/6/3 下午4:17
 */
class MultiReactorServer implements Runnable {
    ServerSocketChannel serverSocket;
    AtomicInteger next = new AtomicInteger(0);
    //选择器集合，引入多个选择器
    Selector[] selectors =null;
    //引入多个子反应器
    SubReactor[] subReactors = null;

    MultiReactorServer () throws IOException {
        //初始化多个选择器
        selectors=new Selector[]{Selector.open(),Selector.open()};
        serverSocket = ServerSocketChannel.open();
        serverSocket.socket().bind(new InetSocketAddress(8777));
        //非阻塞
        serverSocket.configureBlocking(false);
        //第一个选择器，负责监控新连接事件
        SelectionKey sk = serverSocket.register(selectors[0], SelectionKey.OP_ACCEPT);
        //绑定Handler:attach新连接监控handler处理器到SelectionKey（选择键）
        sk.attach(new AcceptorHandler(sk));
        //第一个子反应器，一子反应器负责一个选择器
        subReactors=new SubReactor[]{new SubReactor(selectors[0]),new SubReactor(selectors[1])};

    }

    @Override
    public void run() {
        int i=0;
        for(SubReactor reactor:subReactors){
            String name="master";
            if(reactor.selector!=this.selectors[0]){
                name="worker";
            }
            Thread reactorThread = new Thread(reactor,name+"-reactor-"+(i++));
            reactorThread.start();
        }

//        new Thread(new MasterReactor(selectors[0]),"master reactor").start();
//        new Thread(new WorkReactor(selectors[1]),"worker reactor").start();
    }

    public class SubReactor implements Runnable{
        final Selector selector;
        public SubReactor(Selector selector){
            this.selector=selector;
        }

        @Override
        public void run() {
            try {
                while (!Thread.interrupted()) {//循环
                    System.out.println(Thread.currentThread().getName()+" reactor listening........");

                    selector.select();
                    Set<SelectionKey>keySet = selector.selectedKeys();
                    if(Thread.currentThread().getName().indexOf("work")>=0){
                        System.out.println("event count:"+keySet.size());
                    }
                    Iterator<SelectionKey> it = keySet.iterator();
                    while (it.hasNext()) {
                        //反应器负责dispatch收到的事件
                        SelectionKey sk = it.next();
                        Runnable handler = (Runnable) sk.attachment();
                        //调用之前attach绑定到选择键的handler处理器对象
                        if (handler != null) {
                            handler.run();
                        }
                    }
                    keySet.clear();
                    System.out.println(Thread.currentThread().getName()+" reactor process over");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }
    }


    public class MasterReactor implements Runnable{
        final Selector selector;
        public MasterReactor(Selector selector){
            this.selector=selector;
        }

        @Override
        public void run() {
            try {
                while (!Thread.interrupted()) {//循环
                    System.out.println(Thread.currentThread().getName()+" reactor listening........");
                    selector.select();
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    Iterator<SelectionKey> it = selectionKeys.iterator();
                    System.out.println("event count:"+selectionKeys.size());
                    while (it.hasNext()) {
                        System.out.println(Thread.currentThread().getName()+" reactor listened an event");
                        SelectionKey sk = it.next();
                        Runnable r = (Runnable)(sk.attachment()); //调用SelectionKey绑定的调用对象
                        if (r != null) {
                            r.run();
                        }
                    }
                    System.out.println(Thread.currentThread().getName()+" reactor process over");
                    selectionKeys.clear();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }
    }

    public class WorkReactor implements Runnable{
        final Selector selector;
        public WorkReactor(Selector selector){
            this.selector=selector;
        }

        @Override
        public void run() {
            try {
                while (!Thread.interrupted()) {//循环
                    System.out.println(Thread.currentThread().getName()+" reactor listening........");
                    selector.select();
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    Iterator<SelectionKey> it = selectionKeys.iterator();
                    System.out.println("event count:"+selectionKeys.size());
                    while (it.hasNext()) {
                        System.out.println(Thread.currentThread().getName()+" reactor listened an event");
                        SelectionKey sk = it.next();
                        Runnable r = (Runnable)(sk.attachment()); //调用SelectionKey绑定的调用对象
                        if (r != null) {
                            r.run();
                        }
                    }
                    System.out.println(Thread.currentThread().getName()+" reactor process over");
                    selectionKeys.clear();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }
    }

    // Acceptor 连接处理类
    class AcceptorHandler implements Runnable { // inner
        SelectionKey selectionKey;
        AcceptorHandler(SelectionKey selectionKey){
            this.selectionKey=selectionKey;
        }
        public void run() {
            try {
                SocketChannel channel = serverSocket.accept();
                if (channel != null) {
                    System.out.println(Thread.currentThread().getName()+" accept a connection");
                    new MultiThreadHandler(selectors[1], channel);
                }
            }
            catch(IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException{
        new MultiReactorServer().run();
    }
}