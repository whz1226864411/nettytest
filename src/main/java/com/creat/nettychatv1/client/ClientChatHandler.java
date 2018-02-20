package com.creat.nettychatv1.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by Administrator on 2018-02-20.
 */
public class ClientChatHandler extends SimpleChannelInboundHandler<String>{
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        System.out.println(s);
    }
}
