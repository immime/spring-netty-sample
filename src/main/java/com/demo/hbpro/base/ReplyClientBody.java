package com.demo.hbpro.base;

/**
 * 回复客户端的消息体
 */
public class ReplyClientBody extends ReplyBody {
	private static final long serialVersionUID = -4962335222678430899L;
	private String clientInfo;

    public ReplyClientBody(String clientInfo) {
        this.clientInfo = clientInfo;
    }

    public String getClientInfo() {
        return clientInfo;
    }

    public void setClientInfo(String clientInfo) {
        this.clientInfo = clientInfo;
    }
}
