package com.caojm.lessons.nio.single;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author caojianmin1 <caojianmin@jd.com>
 * @description
 * @date 2021/6/3 下午4:17
 */
class SingleReactor implements Runnable {
    final Selector selector;
    final ServerSocketChannel serverSocket;
    SingleReactor(int port) throws IOException {
        selector = Selector.open();
        serverSocket = ServerSocketChannel.open();
        serverSocket.socket().bind(new InetSocketAddress(port));
        serverSocket.configureBlocking(false);
        SelectionKey sk = serverSocket.register(selector, SelectionKey.OP_ACCEPT); //注册accept事件
        sk.attach(new Acceptor()); //调用Acceptor()为回调方法
    }

    /**
     * boss现成监听事件并分发
     */
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

    void dispatch(SelectionKey k) {
        Runnable r = (Runnable)(k.attachment()); //调用SelectionKey绑定的调用对象
        if (r != null)
            r.run();
    }

    // Acceptor 连接处理类
    class Acceptor implements Runnable { // inner
        public void run() {
            try {
                SocketChannel c = serverSocket.accept();
                if (c != null) {
                    System.out.println(Thread.currentThread().getName()+" accept a connection");
                    new ReadWriteHandler(selector, c);
                }
            }
            catch(IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException{
        Thread boss = new Thread(new SingleReactor(56678));
        boss.setName("boss-thread");
        boss.start();
    }
}