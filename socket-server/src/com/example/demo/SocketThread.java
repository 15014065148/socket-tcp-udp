package com.example.demo;

import java.io.*;
import java.net.Socket;
import java.nio.channels.SocketChannel;

/**
 * Created by sai.luo on 2017-8-22.
 */
public class SocketThread extends Thread {
    private Socket socket ;

    public SocketThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        OutputStream outputStream = null;
        try{
            inputStream = socket.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(inputStreamReader);
            String data = null;

            while (bufferedReader.read()>0){
                data  = data!=null? data+bufferedReader.readLine():bufferedReader.readLine();
                System.out.println("server receive from client IP :"+ socket.getInetAddress()+"Port :" + socket.getPort()+ "\t\n data :" + data);
            }

            outputStream = socket.getOutputStream();
            data = " server echo to Client : " + data ;
            outputStream.write(data.getBytes());
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            outputStream.close();
        }catch (IOException e){
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
            if (socket!=null){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
