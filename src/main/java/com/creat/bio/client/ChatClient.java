package com.creat.bio.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Administrator on 2018-02-14.
 */
public class ChatClient {

    private final String host;
    private final int port;

    public ChatClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public static void main(String[] args) {
        new ChatClient("127.0.0.1",12580).start();
    }

    public void start() {
        Socket client = null;
        Scanner scanner = new Scanner(System.in);
        try {
            client = new Socket(host, port);
            if (client != null) {
                OutputStream outputStream = client.getOutputStream();
                final Socket cc = client;
                new Thread(new Runnable() {
                    public void run() {
                        try {
                            InputStream inputStream = cc.getInputStream();
                            byte[] buf = new byte[256];
                            int len = 0;
                            while ((len = inputStream.read(buf)) != -1){
                                System.out.println(new String(buf, 0 ,len));
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }finally {
                            if (cc != null) {
                                try {
                                    cc.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }).start();
                while (scanner.hasNextLine()) {
                    String in = scanner.nextLine();
                    if (in.equals("exit")) {
                        break;
                    }
                    outputStream.write(in.getBytes());
                    outputStream.flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (scanner != null) {
                scanner.close();
            }
            if (client != null) {
                try {
                    client.shutdownOutput();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
