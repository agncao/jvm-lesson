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
class MultiReactor implements Runnable {
    final Selector[] selectors=new Selector[2];
    SubReactor[] subReactors;

    final ServerSocketChannel serverSocket;
    AtomicInteger next = new AtomicInteger(0);
    MultiReactor(int port) throws IOException {
        selectors[0] = Selector.open();
        selectors[1] = Selector.open();
        serverSocket=ServerSocketChannel.open();
        serverSocket.socket().bind(new InetSocketAddress(port));
        serverSocket.configureBlocking(false);
        SelectionKey sk = serverSocket.register(selectors[0], SelectionKey.OP_ACCEPT); //注册accept事件
        sk.attach(new AcceptorHandler()); //调用Acceptor()为回调方法

        subReactors=new SubReactor[]{new SubReactor(selectors[0]),new SubReactor(selectors[1])};
    }

    void dispatch(SelectionKey k) {
        Runnable r = (Runnable)(k.attachment()); //调用SelectionKey绑定的调用对象
        if (r != null)
            r.run();
    }

    @Override
    public void run() {

        int i=0;
        for(SubReactor reactor:subReactors){
            Thread reactorThread = new Thread(reactor,"master-worker-"+(i++));
            reactorThread.start();
        }
    }

    public class SubReactor implements Runnable{
        final Selector selector;
        public SubReactor(Selector selector){
            this.selector=selector;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName()+" start........");
            try {
                while (!Thread.interrupted()) {//循环
                    selector.select();
                    Set selected = selector.selectedKeys();
                    Iterator it = selected.iterator();
                    while (it.hasNext()) {
                        dispatch((SelectionKey) it.next()); //dispatch分发事件
//                    it.remove();
                    }
                    selected.clear();
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
                    new MultiThreadHandler(selectors[next.get()%2], c);
                }
            }
            catch(IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException{
        Thread boss = new Thread(new MultiReactor(56678));
        boss.setName("boss-thread");
        boss.start();
    }
}