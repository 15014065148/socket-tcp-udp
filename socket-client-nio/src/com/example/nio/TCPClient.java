package com.example.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

/**
 * Created by sai.luo on 2017-8-22.
 */
public class TCPClient {
    // 与服务器通信的信道
    SocketChannel socketChannel;

    // 要连接的服务器Ip地址
    private String hostIp;

    // 要连接的远程服务器在监听的端口
    private int hostListenningPort;

    public TCPClient(String hostIp, int hostListenningPort) throws IOException{
        this.hostIp = hostIp;
        this.hostListenningPort = hostListenningPort;
        initialize();
    }
    /**
     * 初始化
     * @throws IOException
     */
    private void initialize() throws IOException {
        // 打开监听信道并设置为非阻塞模式
        socketChannel=SocketChannel.open(new InetSocketAddress(hostIp, hostListenningPort));
        socketChannel.configureBlocking(false);

        // 打开并注册选择器到信道
        Selector selector = Selector.open();
        socketChannel.register(selector, SelectionKey.OP_READ);
        // 启动读取线程
        new TCPClientOperatorThread(selector);
    }

    /**
     * 发送字符串到服务器
     * @param message
     * @throws IOException
     */
    public void sendMsg(String message) throws IOException{
        ByteBuffer writeBuffer=ByteBuffer.wrap(message.getBytes("UTF-8"));
   /*     writeBuffer = ByteBuffer.allocate(message.getBytes("utf-8").length);
        writeBuffer.put(message.getBytes());
        writeBuffer.flip();*/
        socketChannel.write(writeBuffer);
    }

    public static void main(String[] args) throws IOException{
        int count = 10 ;
        for (int i=0;i<count;i++){
            TCPClient client=new TCPClient("localhost",8888);
            client.sendMsg(" 发送数据 ："+i);
        }

/*        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            String next = scanner.next();
            client.sendMsg(next);
        }*/

    }
}
