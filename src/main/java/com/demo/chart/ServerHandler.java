package com.demo.chart;

import java.util.Date;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;

public class ServerHandler extends SimpleChannelInboundHandler<String> {
	private static ChannelGroup channelGroup;

	/**
	 * 构造函数，在spring加载的时候初始化一次。
	 */
	public ServerHandler() {
		super();
		/* 获得客户端在服务器端注册的所有信息，用于向所有客户端分发消息 */
		channelGroup = new DefaultChannelGroup("client-channel-group", null);
	}

	/**
	 * 对新连接的用户进行注册
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("新的客户端连入：" + ctx.channel().id());
		channelGroup.add(ctx.channel());
	}
	
	public ChannelGroup getChannelGroup() {
		return channelGroup;
	}

	/**
	 * 用于扑捉客户端退出的消息。 并将其从服务器端的注册表中删掉。
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable e) throws Exception {
		e.getCause().printStackTrace();
		Channel channel = ctx.channel();
		channel.close();
		if (channelGroup.contains(channel)) {
			System.out.println("一个客户端退出：" + channel.id());
			channelGroup.remove(channel);
		}
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
		String content = (String) msg;
		ChannelId id = ctx.channel().id();
		System.out.println("服务器收到" + id + " 的消息   时间：" + new Date().toString() + " 消息内容:\n" + content);
		content = id + ":" + content;
		if (content.equalsIgnoreCase("quit")) {
			ctx.channel().close();
			channelGroup.remove(ctx.channel());
			return;
		} else {
			System.out.println("开始转发到其他客户端！:size=" + channelGroup.size());
			for (Channel ch : channelGroup) {
				System.out.println("开始转发到其他客户端！:" + id);
				ch.write(content);
			}
		}
	}

}
