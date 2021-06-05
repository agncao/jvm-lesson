package com.caojm.lessons.nio.multi.v2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

/**
 * @author caojianmin1 <caojianmin@jd.com>
 * @description
 * @date 2021/6/5 下午11:03
 */
public class MultiplexingThreadsReactorServer {



    /**
     * 服务端通道
     */
    private ServerSocketChannel serverSocketChannel;

    /**
     * 主选择器-主要用于客户端的的接入-OP_ACCEPT-事件
     */
    private Selector bossSelector;

    /**
     * 任务选择器-主要用于客户端数据的传入处理-OP_READ-事件
     */
    private Selector[] workerSelectors;

    /**
     * 任务选择器数量
     */
    private final int workerNum=3;


    /**
     * @throws IOException
     */
    public MultiplexingThreadsReactorServer() throws IOException {
        /**
         * 创建并绑定端口号
         */
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(8999));
        serverSocketChannel.configureBlocking(false);

        /**
         * 创建主（boss）选择器
         */
        bossSelector = Selector.open();
        /**
         * serverSocket注册到
         */
        serverSocketChannel.register(bossSelector, SelectionKey.OP_ACCEPT);

        /**
         * 初始化任务选择器-根据设定的任务选择器数量
         */
        workerSelectors = new Selector[3];
        for(int i = 0; i < workerNum; i++){
            workerSelectors[i] = Selector.open();
        }
    }


    public Selector getBossSelector() {
        return bossSelector;
    }

    public Selector[] getWorkerSelectors() {
        return workerSelectors;
    }

    public int getWorkerNum() {
        return workerNum;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        MultiplexingThreadsReactorServer server = new MultiplexingThreadsReactorServer();
        NioThread bossThreadRunnable = new NioThread(server.getBossSelector(),server.getWorkerNum());
        Thread bossThread = new Thread(bossThreadRunnable);
        bossThread.start();
        Thread.currentThread().sleep(2000);

        Thread workerThread = null;
        Selector[] workerSelectors = server.getWorkerSelectors();
        for(Selector selector : workerSelectors){
            NioThread workerNioThread = new NioThread(selector);
            workerThread = new Thread(workerNioThread);
            workerThread.start();
        }


    }
}
