package com.example.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

/**
 * Created by sai.luo on 2017-8-22.
 */
public class NIOUDPServer {
    public static void main(String[] args) throws IOException {
        //创建 datagramChannel
        DatagramChannel datagramChannel = DatagramChannel.open();
        //绑定端口号
        datagramChannel.bind(new InetSocketAddress(8001));
        //创建一个字节缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        byte b[];
        while(true) {
            buffer.clear();
            SocketAddress socketAddress = datagramChannel.receive(buffer);
            if (socketAddress != null) {
                int position = buffer.position();
                b = new byte[position];
                buffer.flip();
                for(int i=0; i<position; ++i) {
                    b[i] = buffer.get();
                }
                System.out.println("receive remote " +  socketAddress.toString() + ":"  + new String(b, "UTF-8"));
                //接收到消息后给发送方回应
                sendReback(socketAddress,datagramChannel);
            }
        }
    }

    public static void sendReback(SocketAddress socketAddress,DatagramChannel datagramChannel) throws IOException {
        String message = "I has receive your message";
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put(message.getBytes("UTF-8"));
        buffer.flip();
        datagramChannel.send(buffer, socketAddress);
    }
}
