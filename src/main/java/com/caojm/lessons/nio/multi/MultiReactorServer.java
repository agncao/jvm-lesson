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
    final Selector[] selectors=new Selector[2];
    SubReactor[] subReactors;

    final ServerSocketChannel serverSocket;
    AtomicInteger next = new AtomicInteger(0);
    MultiReactorServer(int port) throws IOException {
        selectors[0] = Selector.open();
        selectors[1] = Selector.open();
        serverSocket=ServerSocketChannel.open();
        serverSocket.socket().bind(new InetSocketAddress(port));
        serverSocket.configureBlocking(false);
        SelectionKey sk = serverSocket.register(selectors[0], SelectionKey.OP_ACCEPT); //注册accept事件
        sk.attach(new AcceptorHandler()); //调用Acceptor()为回调方法

//        subReactors=new SubReactor[]{new SubReactor(selectors[0]),new SubReactor(selectors[1])};
    }

    @Override
    public void run() {

//        int i=0;
//        for(SubReactor reactor:subReactors){
//            String name="master";
//            if(reactor.selector!=this.selectors[0]){
//                name="worker";
//            }
//            Thread reactorThread = new Thread(reactor,name+"-reactor-"+(i++));
//            reactorThread.start();
//        }
        new Thread(new MasterReactor(selectors[0]),"master reactor").start();
        new Thread(new WorkReactor(selectors[1]),"worker reactor").start();
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
        public void run() {
            try {
                SocketChannel c = serverSocket.accept();
                if (c != null) {
                    System.out.println(Thread.currentThread().getName()+" accept a connection");
                    new MultiThreadHandler(selectors[1], c);
                }
            }
            catch(IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException{
        new Thread(new MultiReactorServer(56678)).start();
    }
}