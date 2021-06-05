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

        //仅仅取得选择键，后设置感兴趣的IO事件
        sk = channel.register(workSelector, SelectionKey.OP_READ);
        System.out.println("work selector is active");
        //将本Handler作为sk选择键的附件，方便事件dispatch
        sk.attach(this);

        //向sk选择键注册Read就绪事件
//        sk.interestOps(SelectionKey.OP_READ);
    }

    public synchronized void handle() {
        try {
            if (state == RECEIVE){
                System.out.println(Thread.currentThread().getName()+" start to reading");
                //从通道读
                int length = 0;
                while ((length = socketChannel.read(byteBuffer)) > 0) {
                    System.out.println(new String(byteBuffer.array(), 0, length));
                }

                //读完后，准备开始写入通道,byteBuffer切换成读模式
                byteBuffer.flip();
                //读完后，注册write就绪事件
                sk.interestOps(SelectionKey.OP_WRITE);
                //读完后,进入发送的状态
                state=WRITING;
            }else if (state == WRITING) {
                System.out.println(Thread.currentThread().getName()+" start to respond");
                socketChannel.write(byteBuffer);
                byteBuffer.clear();
                //写完后,注册read就绪事件
                sk.interestOps(SelectionKey.OP_READ);
                state=RECEIVE;;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void run() {
        pool.execute(this::handle);
        try {
            Thread.sleep(200);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    class AsynTask implements Runnable{

        @Override
        public void run() {
            MultiThreadHandler.this.handle();
        }
    }
}