package com.creat.nettychatv1.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Administrator on 2018-02-20.
 */
public class ServerChatHandler extends SimpleChannelInboundHandler<String>{

    private static final ChannelGroup group = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    private static AtomicInteger count = new AtomicInteger(0);
    public ServerChatHandler() {
        System.out.println(count.incrementAndGet());
    }
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        Channel channel = channelHandlerContext.channel();
        System.out.println(s);
        for (Channel ch : group) {
            if (ch != channel) {
                ch.writeAndFlush(s);
            }
        }
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("11");
        group.add(ctx.channel());
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("22");
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("66");
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("33");
        group.remove(ctx.channel());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("44");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("55");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close().sync();
    }
}
