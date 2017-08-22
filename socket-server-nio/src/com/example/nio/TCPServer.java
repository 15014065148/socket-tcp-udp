package com.example.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;

/**
 * Created by sai.luo on 2017-8-22.
 */
public class TCPServer {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel open = ServerSocketChannel.open();
        //绑定到某个端口
        open.bind(new InetSocketAddress(8888));
        //设置非阻塞
        open.configureBlocking(false);
        //注册感兴趣的事件
        Selector selector = Selector.open();
        // 将选择器绑定到监听信道,只有非阻塞信道才可以注册选择器.并在注册过程中指出该信道可以进行Accept操作
        open.register(selector, SelectionKey.OP_ACCEPT);
        TCPProtocol tcpProtocol = new TCPProtocolImpl(1024);
        while (true){
            if (selector.select()==0){
                System.out.println(" wating ...");
                continue;
            }
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()){
                SelectionKey key = iterator.next();
                try{
                    if (key.isConnectable()){
                        tcpProtocol.handleConnection(key);
                    }
                    if (key.isAcceptable()){
                        tcpProtocol.handleAccept(key);
                    }
                    if (key.isReadable()){
                        tcpProtocol.handleRead(key);
                    }
                    if (key.isWritable()){
                        tcpProtocol.handleWrite(key);
                    }
                }catch (IOException e){
                    e.printStackTrace();
                    // 出现IO异常（如客户端断开连接）时移除处理过的键
                    iterator.remove();
                    continue;
                }
                iterator.remove();
            }
        }
    }
}
