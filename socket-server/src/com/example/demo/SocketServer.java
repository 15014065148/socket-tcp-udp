package com.example.demo;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by sai.luo on 2017-8-22.
 */
public class SocketServer {
    private int port = 8888 ;
    private ServerSocket serverSocket ;

    public static void main(String[] args){
        SocketServer socketServer = new SocketServer();
//        socketServer.run();
        socketServer.runThread();
    }
    public SocketServer(){
        try {
            // 创建服务端socket ，backlog为　能建立连接的个数，超出部分会拒绝连接　抛出异常。
            serverSocket = new ServerSocket(port,10);
            System.out.println("socket 服务端 已启动");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *  一次处理一个客户端连接（串行）
     */
    public void run()  {
        while (true){
            Socket accept = null ;
            InputStream inputStream = null;
            InputStreamReader inputStreamReader = null;
            BufferedReader bufferedReader = null;
            OutputStream outputStream = null;

            try {
                 accept = serverSocket.accept();
                 inputStream = accept.getInputStream();
                 inputStreamReader = new InputStreamReader(inputStream);
                 bufferedReader = new BufferedReader(inputStreamReader);
                 String data = null;

                 while (bufferedReader.read()>0){
                     data  = data!=null? data+bufferedReader.readLine():bufferedReader.readLine();
                     System.out.println("server receive from client IP :"+ accept.getInetAddress()+"Port :" + accept.getPort()+ "\t\n data :" + data);
                 }

                 outputStream = accept.getOutputStream();
                 data = " server echo to Client : " + data ;
                 outputStream.write(data.getBytes());
                 bufferedReader.close();
                 inputStreamReader.close();
                 inputStream.close();
                 outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if (bufferedReader!=null){
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (inputStreamReader!=null){
                    try {
                        inputStreamReader.close() ;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (inputStream!=null){
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (outputStream!=null){
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (accept!=null){
                    try {
                        accept.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }

    /**
     * 多线程，可以同时处理多个客户端socket 。。
     */
    public void runThread(){
        while (true){
            Socket accept = null;
            try {
                accept = serverSocket.accept();
                SocketThread socketThread = new SocketThread(accept);
                socketThread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
