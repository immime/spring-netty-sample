package com.demo.hbpro.base;

/**
 * 请求类型的消息
 */
public class AskMsg extends BaseMsg {
	private static final long serialVersionUID = 5976852165755013119L;

	public AskMsg() {
        super();
        setType(MsgType.ASK);
    }
    private AskParams params;

    public AskParams getParams() {
        return params;
    }

    public void setParams(AskParams params) {
        this.params = params;
    }
}
