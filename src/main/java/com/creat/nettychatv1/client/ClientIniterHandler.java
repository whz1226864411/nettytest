package com.creat.nettychatv1.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * Created by Administrator on 2018-02-20.
 */
public class ClientIniterHandler extends ChannelInitializer<SocketChannel>{
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast("decode", new StringDecoder());
        pipeline.addLast("encode", new StringEncoder());
        pipeline.addLast("chat", new ClientChatHandler());
    }
}
