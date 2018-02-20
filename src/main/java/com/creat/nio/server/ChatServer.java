package com.creat.nio.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2018-02-15.
 */
public class ChatServer {

    private final int port;
    private boolean alive;
    private List<SocketChannel> socketChannelList = new ArrayList<SocketChannel>();

    public ChatServer(int port) {
        this.port = port;
        this.alive = true;
    }

    public static void main(String[] args) {
        new ChatServer(12580).start();
    }

    public void start() {
        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            ServerSocket ssocket = serverSocketChannel.socket();
            ssocket.bind(new InetSocketAddress(port));
            Selector selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            while(alive){
                selector.select();
                Set<SelectionKey> selectionKeySet = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeySet.iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    iterator.remove();
                    if (selectionKey.isAcceptable()) {
                        ServerSocketChannel ssc = (ServerSocketChannel) selectionKey.channel();
                        SocketChannel sc = ssc.accept();
                        sc.configureBlocking(false);
                        sc.register(selector, SelectionKey.OP_READ);
                        socketChannelList.add(sc);
                    }
                    if (selectionKey.isReadable()) {
                        SocketChannel client = (SocketChannel) selectionKey.channel();
                        ByteBuffer byteBuffer = ByteBuffer.allocate(256);
                        int len = 0;
                        if ((len = client.read(byteBuffer)) != -1) {
                            for(SocketChannel socketChannel : socketChannelList){
                                if (socketChannel != client) {
                                    byteBuffer.flip();
                                    socketChannel.write(byteBuffer);
                                }
                            }
                        }else {
                            socketChannelList.remove(client);
                            client.close();
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
