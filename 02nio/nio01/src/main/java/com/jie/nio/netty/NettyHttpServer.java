package com.jie.nio.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.epoll.EpollChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author Jie.LJ.Liu
 * @date 2021/9/17 15:31
 */
public class NettyHttpServer {
    public static void main(String[] args) throws InterruptedException {
        int port = 8888;
        EventLoopGroup boosGroup = new NioEventLoopGroup(2);
        EventLoopGroup workGroup = new NioEventLoopGroup(16);

        try {

            ServerBootstrap server = new ServerBootstrap();
            server.option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childOption(ChannelOption.SO_REUSEADDR, true)
                    .childOption(ChannelOption.SO_RCVBUF, 32 * 1024)
                    .childOption(ChannelOption.SO_SNDBUF, 32 * 1024)
                    .childOption(EpollChannelOption.SO_REUSEPORT, true)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);

            server.group(boosGroup, workGroup).channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler())
                    .childHandler(new HttpInitializer());

            Channel channel = server.bind(port).sync().channel();
            System.out.println("Netty server started, url: http://127.0.0.1:" + port + "/");
            channel.closeFuture().sync();
        } finally {
            boosGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
