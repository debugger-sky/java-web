package com.java.web;

import com.java.web.common.socket.Server;
import com.java.web.sample.controller.SampleController;

import java.io.*;
import java.net.Socket;

public class WebApplication {

    public static void main(String[] args) {

        Server server = new Server();
        SampleController sampleController = new SampleController();

        while(true) {
            Socket client = server.accept();

            System.out.println("socket accepted!!! " + "client port="+client.getPort() + " client ip="+client.getInetAddress() +"\n");
            if(!"/0:0:0:0:0:0:0:1".equals(client.getInetAddress().toString())) {
                System.out.println("other socket closed\n\n");
                try {
                    client.close();
                    continue;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


            InputStream inputStream = null;
            InputStreamReader inputStreamReader = null;
            BufferedReader bufferedReader = null;
            OutputStream outputStream = null;

            try {
                inputStream = client.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream);
                bufferedReader = new BufferedReader(inputStreamReader);
                StringBuilder sb = new StringBuilder();

                // header
                String readLine;
                while((readLine = bufferedReader.readLine()) != null) {
                    if("".equals(readLine)) {
                        sb.append("\n");
                        break;
                    }
                    sb.append(readLine).append("\n");
                }
                System.out.println(sb.toString());
                sb.delete(0, sb.length());

                // body
//                sb = new StringBuilder();
//                while((readLine = bufferedReader.readLine()) != null && !"".equals(readLine)) {
//                    sb.append(readLine).append("\n");
//                }
//                System.out.println(sb.toString());
//                sb.delete(0, sb.length());


                outputStream = client.getOutputStream();

                byte[] outputBytes = sampleController.sampleData().getBytes();
                outputStream.write(outputBytes);
                outputStream.flush();

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try { if(outputStream!=null) outputStream.close(); } catch (IOException e) { }
                try { if(inputStreamReader!=null) inputStreamReader.close(); } catch (IOException e) { }
                try { if(inputStream!=null) inputStream.close(); } catch (IOException e) { }
            }

            try {
                if(client!=null) client.close();
                System.out.println("client closed");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
