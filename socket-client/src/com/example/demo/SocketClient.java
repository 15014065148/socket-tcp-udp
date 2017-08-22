package com.example.demo;

import java.io.*;
import java.net.Socket;

/**
 * Created by sai.luo on 2017-8-22.
 */
public class SocketClient {
    private static final int port = 8888 ;
    private static final String host = "localhost" ;

    public static void main(String[] args) throws IOException {
        int size = 20 ;
        OutputStream outputStream = null ;
        PrintWriter printWriter = null ;
        InputStream inputStream = null ;
        BufferedReader bufferedReader = null ;

        for (int i=0 ;i<size;i++){
            try {
                Socket socket = new Socket(host, port);
                outputStream = socket.getOutputStream();
                printWriter = new PrintWriter(outputStream);
                printWriter.write(" hello,我是客户端");
                printWriter.flush();
                socket.shutdownOutput();

                inputStream = socket.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String data = null ;
                while ((data = bufferedReader.readLine())!=null){
                    System.out.println(" client receive data from server, data is : "+data);
                }
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
            if (bufferedReader!=null){
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (printWriter!=null){
                    printWriter.close() ;
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

        }

    }
    }
}
