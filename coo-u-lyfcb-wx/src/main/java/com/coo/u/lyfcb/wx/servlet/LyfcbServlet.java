package com.coo.u.lyfcb.wx.servlet;

import org.springframework.stereotype.Component;

import com.coo.u.lyfcb.wx.INameSpace;
import com.ericxu131.exwechat.model.message.Message;
import com.kingstar.ngbf.s.weixin.util.WeiXinServlet;
import com.kingstar.ngbf.u.base.jetty.IJettyServlet;

//核心servlet
@Component("lyfcbServlet")
public class LyfcbServlet extends WeiXinServlet implements IJettyServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5380556977710319617L;

	@Override
	protected String getToken() {
		return INameSpace.VALID_TOKEN;
	}

	@Override
	protected Message onMessage(Message message) {
		// 调用核心业务类
		Message resMsg = CoreService.processRequest(message);
		return resMsg;
	}

	public String getMappingExpression() {
		// TODO Auto-generated method stub
		return "servelt/weixin";
	}
}
