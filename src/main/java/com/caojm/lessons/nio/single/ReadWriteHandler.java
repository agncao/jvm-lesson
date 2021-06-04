package com.caojm.lessons.nio.single;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * @author caojianmin1 <caojianmin@jd.com>
 * @description
 * @date 2021/6/3 下午4:21
 */
public class ReadWriteHandler implements Runnable {
    final SocketChannel socketChannel;
    final SelectionKey sk;
    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
    static final int RECEIVE = 0, WRITING = 1;
    int state = RECEIVE;

    ReadWriteHandler(Selector sel, SocketChannel channel) throws IOException {
        socketChannel = channel;
        channel.configureBlocking(false);
        // Optionally try first read now
        sk = socketChannel.register(sel, 0);
        sk.attach(this); //将Handler绑定到SelectionKey上
        sk.interestOps(SelectionKey.OP_READ);
        sel.wakeup();
    }

    public void run() {
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
                sk.interestOps(SelectionKey.OP_READ);
                state=RECEIVE;
            }
//            sk.cancel();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}