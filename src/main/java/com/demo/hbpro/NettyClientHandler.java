package com.demo.hbpro;

import java.util.Date;

import com.demo.hbpro.base.AskMsg;
import com.demo.hbpro.base.BaseMsg;
import com.demo.hbpro.base.LoginMsg;
import com.demo.hbpro.base.MsgType;
import com.demo.hbpro.base.PingMsg;
import com.demo.hbpro.base.ReplyClientBody;
import com.demo.hbpro.base.ReplyMsg;
import com.demo.hbpro.base.ReplyServerBody;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;

/**
 * Client处理类
 */
public class NettyClientHandler extends SimpleChannelInboundHandler<BaseMsg> {
	private int UNCONNECT_NUM = 0;

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if (evt instanceof IdleStateEvent) {
			if (UNCONNECT_NUM >= 4) {
				System.err.println("connect status is disconnect.");
				ctx.close();
				// 此处当重启次数达到4次之后，关闭此链接后，并重新请求进行一次登录请求
				return;
			}

			IdleStateEvent e = (IdleStateEvent) evt;
			switch (e.state()) {
			case WRITER_IDLE:
				System.out.println("send ping to server---date=" + new Date());
				PingMsg pingMsg = new PingMsg();
				ctx.writeAndFlush(pingMsg);
				UNCONNECT_NUM++;
				System.err.println("writer_idle over. and UNCONNECT_NUM=" + UNCONNECT_NUM);
				break;
			case READER_IDLE:
				System.err.println("reader_idle over.");
				UNCONNECT_NUM++;
				// 读取服务端消息超时时，直接断开该链接，并重新登录请求，建立通道
			case ALL_IDLE:
				System.err.println("all_idle over.");
				UNCONNECT_NUM++;
				// 读取服务端消息超时时，直接断开该链接，并重新登录请求，建立通道
			default:
				break;
			}
		}
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, BaseMsg msg) throws Exception {
		MsgType msgType = msg.getType();
		switch (msgType) {
		case LOGIN: {
			// 向服务器发起登录
			LoginMsg loginMsg = new LoginMsg();
			loginMsg.setPassword("alan");
			loginMsg.setUserName("lin");
			ctx.writeAndFlush(loginMsg);
		}
			break;
		case PING: {
			System.out.println("receive server ping ---date=" + new Date());
			ReplyMsg replyPing = new ReplyMsg();
			ReplyClientBody body = new ReplyClientBody("send client msg.");
			replyPing.setBody(body);
			ctx.writeAndFlush(replyPing);
		}
			break;
		case ASK: {
			AskMsg askMsg = (AskMsg) msg;
			ReplyClientBody replyClientBody = new ReplyClientBody(
					"receive server askmsg:" + askMsg.getParams().getContent());
			ReplyMsg replyMsg = new ReplyMsg();
			replyMsg.setBody(replyClientBody);
			ctx.writeAndFlush(replyMsg);
		}
			break;
		case REPLY: {
			ReplyMsg replyMsg = (ReplyMsg) msg;
			ReplyServerBody replyServerBody = (ReplyServerBody) replyMsg.getBody();
			UNCONNECT_NUM = 0;
			System.out.println(
					"UNCONNECT_NUM=" + UNCONNECT_NUM + ",receive server replymsg: " + replyServerBody.getServerInfo());
		}
		default:
			break;
		}
		ReferenceCountUtil.release(msgType);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

		System.err.println("in client exceptionCaught.");
		super.exceptionCaught(ctx, cause);
		// 出现异常时，可以发送或者记录相关日志信息，之后，直接断开该链接，并重新登录请求，建立通道

	}

}
