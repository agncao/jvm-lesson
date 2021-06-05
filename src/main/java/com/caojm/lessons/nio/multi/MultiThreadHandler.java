package com.caojm.lessons.nio.multi;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author caojianmin1 <caojianmin@jd.com>
 * @description
 * @date 2021/6/3 下午4:21
 */
public class MultiThreadHandler implements Runnable {
    final SocketChannel socketChannel;
    final SelectionKey sk;
    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
    static final int RECEIVE = 0, WRITING = 1;
    int state = RECEIVE;
    static ExecutorService pool= Executors.newFixedThreadPool(4);

    MultiThreadHandler(Selector workSelector, SocketChannel channel) throws IOException {
        workSelector.wakeup();
        socketChannel = channel;
        socketChannel.configureBlocking(false);
        // Optionally try first read now
        sk = socketChannel.register(workSelector, SelectionKey.OP_READ);
        sk.attach(this); //将Handler绑定到SelectionKey上
        sk.interestOps(SelectionKey.OP_READ);
    }

    public synchronized void handle() {
        try {
            if (state == RECEIVE){
                int length=0;
                while ((length=socketChannel.read(byteBuffer))>0) {
                    System.out.println(Thread.currentThread().getName()+" received: "
                            +new String(byteBuffer.array(),0,length)+",length="+length);

                }
                byteBuffer.flip();

                state = WRITING;
                // Normally also do first write now
                sk.interestOps(SelectionKey.OP_WRITE);
            }else if (state == WRITING) {
                System.out.println(Thread.currentThread().getName()+" start to respond");
                socketChannel.write(byteBuffer);
                byteBuffer.clear();
                state=RECEIVE;;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void run(){
        pool.execute(new AsynTask());
    }

    class AsynTask implements Runnable{

        @Override
        public void run() {
            MultiThreadHandler.this.handle();
        }
    }
}