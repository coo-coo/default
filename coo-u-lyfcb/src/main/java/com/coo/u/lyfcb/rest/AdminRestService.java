package com.coo.u.lyfcb.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.coo.u.lyfcb.model.Card;
import com.coo.u.lyfcb.model.Site;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kingstar.ngbf.s.ntp.SimpleMessage;
import com.kingstar.ngbf.s.ntp.SimpleMessageHead;
import com.kingstar.ngbf.s.ntp.spi.Token;

/**
 * 卡号信息业务维护:SBQ
 * http://ip:port/lyfcb/rest/
 */
@Controller
@RequestMapping("/")
public class AdminRestService {

	private Logger logger = Logger.getLogger(AdminRestService.class);

	/**
	 * Admin登陆
	 */
	@RequestMapping(value = "admin/login/username/{username}/password/{password}", method = RequestMethod.GET)
	@ResponseBody
	public SimpleMessage<?> adminLogin(@PathVariable("username") String username,
			@PathVariable("password") String password) {
		SimpleMessage<?> sm = new SimpleMessage<Object>(SimpleMessageHead.OK);
		logger.debug("username:" + username + ", password:" + password);
		// TODO 密码从配置文件中获得的
		if (username.equals("motu") && password.equals("motu")) {
			Token token = new Token();
			token.setAccount(username);
			token.setRole("ADMIN");
			// 设置单例登录....
			AdminHelper.get().setAdminToken(token);
			// 存储所有的Token，TODO
			sm = SimpleMessage.ok().set("token", token.getToken());
		} else {
			sm = new SimpleMessage<Object>(
					SimpleMessageHead.BIZ_ERROR.repMsg("用户名或密码不正确!"));
		}
		return sm;
	}

	/**
	 * Admin登出
	 */
	@RequestMapping(value = "admin/logout", method = RequestMethod.GET)
	@ResponseBody
	public SimpleMessage<?> adminLogout() {
		AdminHelper.get().setAdminToken(null);
		SimpleMessage<?> sm = SimpleMessage.ok();
		return sm;
	}
	
	
	/**
	 * 获得所有的站点的信息
	 */
	@RequestMapping(value = "site/all", method = RequestMethod.GET)
	@ResponseBody
	public SimpleMessage<Site> siteAll() {
		SimpleMessage<Site> sm = new SimpleMessage<Site>(SimpleMessageHead.OK);
		// TODO 从MYSQL数据库中获得
		List<Site> items = AdminHelper.get().mockSites();
		for (Site item : items) {
			sm.addRecord(item);
		}
		return sm;
	}

	/**
	 * 获得所有的站点的所有卡片信息信息
	 */
	@RequestMapping(value = "card/all/seq/{seq}", method = RequestMethod.GET)
	@ResponseBody
	public SimpleMessage<Card> cardAll(@PathVariable("seq") String seq) {
		SimpleMessage<Card> sm = new SimpleMessage<Card>(SimpleMessageHead.OK);
		// TODO 从MYSQL数据库中获得
		List<Card> items = AdminHelper.get().mockCards(seq);
		for (Card item : items) {
			sm.addRecord(item);
		}
		return sm;
	}
	
	/**
	 * 创建站点:POST , {"seq":'001',"name":'站点1'}
	 */
	@RequestMapping(value = "site/create", method = RequestMethod.POST)
	@ResponseBody
	public SimpleMessage<?> siteCreate(String info) {
		System.out.println("siteCreate:"+info);
		logger.info(info);
		// 转化成为Map对象?
		Gson gson = new Gson();
		Map<String, Object> item = gson.fromJson(info,
				new TypeToken<HashMap<String, Object>>() {
				}.getType());
		// TODO 进行保存
		logger.info(item);
		SimpleMessage<?> sm = SimpleMessage.ok();
		return sm;
	}
	
	/**
	 * 创建站点:POST , {"siteSeq":'001',"seq":'001-000001',"name":'站点1'}
	 */
	@RequestMapping(value = "card/create", method = RequestMethod.POST)
	@ResponseBody
	public SimpleMessage<?> cardCreate(String info) {
		System.out.println("cardCreate:"+info);
		logger.debug(info);
		// 转化成为Map对象?
		Gson gson = new Gson();
		Map<String, Object> item = gson.fromJson(info,
				new TypeToken<HashMap<String, Object>>() {
				}.getType());
		// TODO 进行保存
		logger.debug(item);
		SimpleMessage<?> sm = SimpleMessage.ok();
		return sm;
	}
}
