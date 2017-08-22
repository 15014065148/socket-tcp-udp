package com.example.udp;

import java.io.IOException;
import java.net.*;

/**
 * Created by sai.luo on 2017-8-22.
 */
public class UDPClient extends Thread{
    private DatagramSocket s;
    private InetAddress hostAddress;
    private byte[] buf = new byte[1000];
    private DatagramPacket dp = new DatagramPacket(buf, buf.length);
    private int id;

    public static void main(String[] args) {
        for (int i=0 ;i<100;i++){
            UDPClient udpClient = new UDPClient(i);
            udpClient.start();

        }
    }
    public UDPClient(int id) {
        this.id = id ;
        try {
            s = new DatagramSocket();
            hostAddress = InetAddress.getByName("localhost");
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        System.out.println(" udp  client starting ...");
    }

    @Override
    public void run() {
        String message = " client Id :"+id +" ,message is " +id+"\t\n";
        try {

            s.send(DgramUtil.toDatagram(message,hostAddress,8000));
            s.receive(dp);
            String receive = " client receive from server : " +dp.getAddress().getHostAddress() + "port -> " +dp.getPort() +" \t\n data is "+DgramUtil.toString(dp) ;
            System.out.println(receive);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
