package com.demo.hbpro.base;

/**
 * 回复服务端消息体
 */
public class ReplyServerBody extends ReplyBody {
	private static final long serialVersionUID = 8316699962228254960L;
	private String serverInfo;
    public ReplyServerBody(String serverInfo) {
        this.serverInfo = serverInfo;
    }
    public String getServerInfo() {
        return serverInfo;
    }
    public void setServerInfo(String serverInfo) {
        this.serverInfo = serverInfo;
    }
}
