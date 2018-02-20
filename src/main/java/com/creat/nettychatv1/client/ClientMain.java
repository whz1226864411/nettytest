package com.creat.nettychatv1.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Scanner;

/**
 * Created by Administrator on 2018-02-20.
 */
public class ClientMain {

    private final String host;
    private final int port;

    public ClientMain(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public static void main(String[] args) {
        new ClientMain("127.0.0.1", 12580).start();
    }

    public void start() {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ClientIniterHandler());
        try {
            Channel channel = bootstrap.connect(host, port).sync().channel();
            System.out.println("ss");
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()) {
                String in = scanner.nextLine();
                if (in.equals("exit")) {
                    channel.close();
                    break;
                }
                channel.writeAndFlush(in);
            }
            System.exit(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }
}
