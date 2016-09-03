package com.demo.hbpro.base;

/**
 * 回复消息
 */
public class ReplyMsg extends BaseMsg {
	private static final long serialVersionUID = 4331001251333774254L;

	public ReplyMsg() {
        super();
        setType(MsgType.REPLY);
    }
    private ReplyBody body;

    public ReplyBody getBody() {
        return body;
    }

    public void setBody(ReplyBody body) {
        this.body = body;
    }
}
