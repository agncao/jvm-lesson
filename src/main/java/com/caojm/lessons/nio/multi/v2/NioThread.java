package com.caojm.lessons.nio.multi.v2;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author caojianmin1 <caojianmin@jd.com>
 * @description
 * @date 2021/6/5 下午11:10
 */
public class NioThread  implements Runnable{

    /**
     * 传入的  选择器-私有
     */
    private Selector selector;

    /**
     * 任务选择器数量-多线程可见
     */
    private static int workerSelectorNum;

    /**
     * 每个任务选择器对应一个 队列  -多线程可见
     */
    static BlockingQueue<SocketChannel>[] clientSocketChannelQueues;


    /**
     * 任务选择器下标的数值-用于需任务选择器队列中获取自己的阻塞队列
     * 每个worker生成自己的ID。
     *
     * 这个workerID是与<p>BlockingQueue<SocketChannel>[] clientSocketChannelQueues</p>中阻塞队列进行间接绑定了的
     */
    int id = 0;

    /**
     * 是否为工作任务的选择器线程
     * 默认为工作任务的选择器线程
     * 如果为非工作任务的选择器线程则设置为false
     */
    private boolean workerSign = true;

    /**
     * 自增ID-多线程可见
     */
    static AtomicInteger idx = new AtomicInteger();

    /**
     * Boss  选择器专用构造器
     * @param selector  boss任务选择器
     * @param workerSelectorNum 任务选择器数量
     */
    public NioThread(Selector selector, int workerSelectorNum){
        this.selector = selector;
        this.workerSelectorNum = workerSelectorNum;
        this.workerSign = false;

        clientSocketChannelQueues = new LinkedBlockingQueue[workerSelectorNum];
        initWorderSocketChannelQueues();

        System.out.println("Boss 启动");
    }


    /**
     * 初始化任务选择器所需的阻塞队列数组
     * 一个阻塞队列对应一个任务选择器
     */
    private void initWorderSocketChannelQueues() {
        for (int i = 0; i < workerSelectorNum; i++){
            clientSocketChannelQueues[i] = new LinkedBlockingQueue<>();
        }
    }

    /**
     * worker 任务选择器专用构造器
     * @param selector
     */
    public NioThread(Selector selector) {
        this.selector = selector;
        /**
         * 每个worker生成自己的ID。
         */
        id = idx.getAndIncrement() % workerSelectorNum;//任务选择器队列下标
        System.out.println("任务线程 【worker---" + id + "】启动");
    }

    @Override
    public void run() {
        try {
            while (true){
                if(selector.select(10) > 0){//10毫秒延迟获取  不完全阻塞
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = selectionKeys.iterator();
                    while (iterator.hasNext()){
                        SelectionKey selectionKey = iterator.next();
                        iterator.remove();

                        if(selectionKey.isConnectable()){
                            System.out.println("---------------selectionKey.isConnectable()......");
                        }else if(selectionKey.isAcceptable()){//客户端请求连接事件---其时只有boss选择器才能走到这一步
                            acceptHandler(selectionKey);
                        }else if(selectionKey.isReadable()){//客户端数据到达事件
                            readHandler(selectionKey);
                        }else if(selectionKey.isValid()){
                            System.out.println("---------------selectionKey.isValid()......");
                        }else if(selectionKey.isWritable()){
                            System.out.println("---------------selectionKey.isWritable()......");
                        }
                    }
                }
                /**
                 * boss 不参与这个过程
                 * 只有工作任务的选择器线程
                 * 且
                 * 对应的阻塞队列不为空的时候，才会执行
                 */
                if(workerSign && !clientSocketChannelQueues[id].isEmpty()){
                    //默认创建一个8字节的缓冲区
                    ByteBuffer byteBuffer = ByteBuffer.allocateDirect(8192);
                    //从阻塞队列中取出对应的客户端
                    SocketChannel clientSocketChannel = clientSocketChannelQueues[id].take();
                    clientSocketChannel.register(selector, SelectionKey.OP_READ,byteBuffer);
                    System.out.println("-----------------------------------------------------");
                    System.out.println("客户端连接进入：" + clientSocketChannel.socket().getPort() + "分配到workder ---" + id);
                    System.out.println("-----------------------------------------------------");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 其时这一步 只有 boss选择器可以进入
     * @param selectionKey
     * @throws IOException
     */
    private void acceptHandler(SelectionKey selectionKey) throws IOException {
        /**
         * 从选择器中获取服务端注册时的通道
         */
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
        /**
         * 接收传入的客户端SocketChannel
         */
        SocketChannel clientSocketChannel = serverSocketChannel.accept();
        clientSocketChannel.configureBlocking(false);//设置客户端类型为非阻塞

        /**
         * 由于boss选择器所在的线程并不会处理客户端的链接，
         * 他只是把接受到的选择器按照自增的规则取模后放到不同的阻塞队列当中
         * 每次来一个客户端  idx都会自增1 ，然后取模后放到不同的任务队列。
         */
        //轮询分配
        int index = idx.getAndIncrement() % workerSelectorNum;
        /**
         * 把接受到的客户端放入不同任务选择器归属的阻塞队列中
         */
        clientSocketChannelQueues[index].add(clientSocketChannel);
    }


    /**
     * 其时这一步只有worker
     * @param selectionKey
     * @throws IOException
     */
    private void readHandler(SelectionKey selectionKey) throws IOException {
        SocketChannel clientSocketChannel = (SocketChannel) selectionKey.channel();
        ByteBuffer byteBuffer = (ByteBuffer) selectionKey.attachment();
        byteBuffer.clear();
        int readNum = 0;
        try {
            while (true) {
                readNum = clientSocketChannel.read(byteBuffer);
                if (readNum < 0) {
                    System.out.println("client port --" + clientSocketChannel.socket().getPort() + "---offline---");
                    selectionKey.cancel();
                    clientSocketChannel.socket().close();
                    clientSocketChannel.close();
                } else if (readNum == 0) {
                    break;
                } else {
                    byteBuffer.flip();//每次读取之前都要反转一次
                    byte[] bytes = new byte[readNum];
                    byteBuffer.get(bytes);
                    String clientStr = new String(bytes);
                    System.out.println("client port --" + clientSocketChannel.socket().getPort() + "---data---" + clientStr);

                    String returnStr = "server get client data" + clientStr;
                    byteBuffer.clear();//把数据返回
                    byteBuffer.put(returnStr.getBytes());
                    byteBuffer.flip();//每次写出之前都要反转一次
                    while (byteBuffer.hasRemaining()) {//判断当前缓冲区中是否有数据
                        clientSocketChannel.write(byteBuffer);//把当前缓冲区中数据写回客户端。
                    }
                }
            }
        }catch(IOException e){
            e.printStackTrace();
            System.out.println("client port --" + clientSocketChannel.socket().getPort() + "---offline---");
            selectionKey.cancel();
            clientSocketChannel.socket().close();
            clientSocketChannel.close();

        }
    }
}
