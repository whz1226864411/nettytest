package com.creat.nettychatv1.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;


/**
 * Created by Administrator on 2018-02-20.
 */
public class ServerIniterHandler extends ChannelInitializer<SocketChannel>{

    protected void initChannel(SocketChannel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        System.out.println("handler...");
        pipeline.addLast("decode", new StringDecoder());
        pipeline.addLast("encode", new StringEncoder());
        pipeline.addLast("chat", new ServerChatHandler());
    }
}
