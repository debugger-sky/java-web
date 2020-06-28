package com.java.web.common.socket;

import java.net.Socket;

public class ServerService {

    /* temp */
    public boolean allowIp(Socket client, String... array) {
        System.out.println("\n\n==============================================================================");
        System.out.println("socket accepted!!! " + "client port="+client.getPort() + " client ip="+client.getInetAddress());
        System.out.println("==============================================================================\n\n");

        for(String ip : array) {
            if(client.getInetAddress().toString().equals(ip)) {
                return true;
            }
        }
        System.out.println("other socket closed\n\n");
        return false;
    }

}
