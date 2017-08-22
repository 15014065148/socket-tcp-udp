package com.example.udp;

import java.net.DatagramPacket;
import java.net.InetAddress;

/**
 * Created by sai.luo on 2017-8-22.
 */
public class DgramUtil {
    public static DatagramPacket toDatagram(String s, InetAddress destIA,
                                            int destPort) {

        byte[] buf = s.getBytes();
        return new DatagramPacket(buf, buf.length, destIA, destPort);
    }

    public static String toString(DatagramPacket p) {
        return new String(p.getData());
    }
}
