package com.example.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * Created by sai.luo on 2017-8-22.
 */
public class UDPServer {
    private static final int port = 8000 ;
    private byte[] buf = new byte[1000];
    private DatagramPacket dp = new DatagramPacket(buf, buf.length);
    private DatagramSocket socket;

    public static void main(String[] args) {
        new UDPServer();
    }
    public UDPServer() {
        try {
            socket = new DatagramSocket(port);
            while (true){
                socket.receive(dp);
                String data = DgramUtil.toString(dp);
                System.out.println("receive from client : ip -> " +dp.getAddress().getHostAddress()+" port :" + dp.getPort() +" data :" + data);
                String echo = " echo from server : " + data ;
                socket.send(DgramUtil.toDatagram(echo,dp.getAddress(),dp.getPort()));
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
