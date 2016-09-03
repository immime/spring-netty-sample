package com.demo.hbpro;

import java.util.concurrent.TimeUnit;

import com.demo.hbpro.base.AskMsg;

import io.netty.channel.socket.SocketChannel;

public class Test {
	public static void main(String[] args) throws InterruptedException {
		
		while (true) {
			SocketChannel channel = (SocketChannel) NettyChannelMap.get("001");
			if (channel != null) {
				AskMsg askMsg = new AskMsg();
				channel.writeAndFlush(askMsg);
			}
			TimeUnit.SECONDS.sleep(1000);
		}
	}
}
