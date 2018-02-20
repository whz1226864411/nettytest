package com.creat.bio.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018-02-14.
 */
public class ChatServer {

    private final int port;
    private boolean alive;
    private List<OutputStream> outputStreams = new ArrayList<OutputStream>();

    public ChatServer(int port) {
        this.port = port;
        alive = true;
    }

    public static void main(String[] args) {
        new ChatServer(12580).start();
    }


    public void start() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            while (alive){
                final Socket socket = serverSocket.accept();//阻塞
                System.out.println("连接上一个客户端");
                new Thread(new Runnable() {
                    public void run() {
                        Socket client = socket;
                        OutputStream outputStream = null;
                        InputStream inputStream = null;
                        try {
                            inputStream = client.getInputStream();
                            outputStream = client.getOutputStream();
                            if(inputStream != null && outputStream != null){
                                outputStreams.add(outputStream);
                                byte[] buf = new byte[256];
                                int len = 0;
                                while ((len = inputStream.read(buf)) != -1){
                                    String s = new String(buf, 0 ,len);
                                    System.out.println(s);
                                    for(OutputStream one : outputStreams) {
                                        if (one != outputStream) {
                                            one.write(s.getBytes());
                                            one.flush();
                                        }
                                    }
                                }
                                System.out.println("客户端断开连接");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }finally {
                            if (outputStream != null){
                                outputStreams.remove(outputStream);
                            }
                            if (client != null){
                                try {
                                    client.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                    }
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(serverSocket != null){
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
