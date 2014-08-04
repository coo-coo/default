package com.coo.u.lyfcb.wx.servlet;

import com.ericxu131.exwechat.model.message.Message;
import com.ericxu131.exwechat.web.WechatServlet;

//核心servlet
public class LyfcbServlet extends WechatServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5380556977710319617L;

	@Override
	protected String getToken() {
		// TODO
		return "sunbingjue";
	}

	@Override
	protected Message onMessage(Message message) {
		//调用核心业务类
		Message resMsg = CoreService.processRequest(message);
		return resMsg;
	}
}
