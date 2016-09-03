package com.demo.hbpro.base;

/**
 * 心跳检测的消息类型
 */
public class PingMsg extends BaseMsg {
	private static final long serialVersionUID = 8935110522408305339L;

	public PingMsg() {
        super();
        setType(MsgType.PING);
    }
}
