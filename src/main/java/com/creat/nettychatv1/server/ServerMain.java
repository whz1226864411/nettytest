package com.creat.nettychatv1.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created by Administrator on 2018-02-20.
 */
public class ServerMain {

    private final int port;

    public ServerMain(int port) {
        this.port = port;
    }

    public static void main(String[] args) {
        new ServerMain(12580).start();
    }

    public void start() {
        EventLoopGroup acceptor = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(acceptor,worker)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ServerIniterHandler());
        try {
            Channel channel = bootstrap.bind(port).sync().channel();
            System.out.println("server is running");
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            acceptor.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
