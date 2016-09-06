package com.demo;

import com.demo.echo.EchoServerHandler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;

public class NettyServer {
	private int boss;
	private int worker;
	private int port;

	public NettyServer(int boss, int worker, int port) {
		this.boss = boss;
		this.worker = worker;
		this.port = port;
	}

	public void start() {
		EventLoopGroup bossGroup = new NioEventLoopGroup(boss);
		EventLoopGroup workerGroup = new NioEventLoopGroup(worker);
		try {
			ServerBootstrap bootstrap = new ServerBootstrap();
			bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
					.childHandler(new ServerInitializer());

			Channel channel = bootstrap.bind(port).sync().channel();
			ChannelFuture channelFuture = channel.closeFuture();
			channelFuture.sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}

	}
}
