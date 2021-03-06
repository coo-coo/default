package com.coo.u.lyfcb.rest;

import java.util.List;

import org.apache.log4j.Logger;
import org.mortbay.log.Log;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.coo.s.lyfcb.model.Apply;
import com.coo.s.lyfcb.model.Card;
import com.coo.s.lyfcb.model.Site;
import com.kingstar.ngbf.s.ntp.SimpleMessage;
import com.kingstar.ngbf.s.ntp.SimpleMessageHead;
import com.kingstar.ngbf.s.ntp.spi.Token;
import com.kingstar.ngbf.s.util.NgbfRuntimeException;

/**
 * 卡号信息业务维护:SBQ http://ip:port/lyfcb/rest/
 */
@Controller
@RequestMapping("/")
public class AdminRestService {

	private Logger logger = Logger.getLogger(AdminRestService.class);

	/**
	 * 获得登录人信息
	 */
	@RequestMapping(value = "admin/info/token/{token}", method = RequestMethod.GET)
	@ResponseBody
	public SimpleMessage<?> adminInfo(@PathVariable("token") String token) {
		SimpleMessage<?> sm = null;
		// 账号、密码从配置文件中获得的
		Token t = AdminHelper.getAccountToken(token);
		if (t != null) {
			sm = SimpleMessage.ok().set("token", t.getToken())
					.set("account", t.getAccount()).set("role", t.getRole())
					.set("partition", t.getPartition());
		} else {
			sm = new SimpleMessage<Object>(
					SimpleMessageHead.BIZ_ERROR.repMsg("Token不存在!"));
		}
		return sm;
	}

	/**
	 * Admin登陆
	 */
	@RequestMapping(value = "admin/login/username/{username}/password/{password}", method = RequestMethod.GET)
	@ResponseBody
	public SimpleMessage<?> adminLogin(
			@PathVariable("username") String username,
			@PathVariable("password") String password) {
		SimpleMessage<?> sm = new SimpleMessage<Object>(SimpleMessageHead.OK);
		try {
			logger.debug("username:" + username + ", password:" + password);
			// 账号、密码从配置文件中获得的，Token已经存储
			Token token = AdminHelper.adminLogin(username, password);
			
			sm = SimpleMessage.ok().set("token", token.getToken())
					.set("account", token.getAccount());
		} catch (NgbfRuntimeException e) {
			sm = new SimpleMessage<Object>(SimpleMessageHead.BIZ_ERROR.repMsg(e
					.getMessage()));
		}
		return sm;
	}

	/**
	 * Admin登出
	 */
	@RequestMapping(value = "admin/logout/token/{token}", method = RequestMethod.GET)
	@ResponseBody
	public SimpleMessage<?> adminLogout(@PathVariable("token") String token) {
		// 清除Token
		AdminHelper.adminLogout(token);
		return SimpleMessage.ok();
	}

	/**
	 * 获得所有的站点的信息
	 */
	@RequestMapping(value = "site/all", method = RequestMethod.GET)
	@ResponseBody
	public SimpleMessage<Site> siteAll() {
		SimpleMessage<Site> sm = new SimpleMessage<Site>(SimpleMessageHead.OK);
		// 从MYSQL数据库中获得
		List<Site> items = AdminHelper.getBizService().findSiteAll();
		for (Site item : items) {
			sm.addRecord(item);
		}
		return sm;
	}

	@RequestMapping(value = "apply/all/status/{status}", method = RequestMethod.GET)
	@ResponseBody
	public SimpleMessage<Apply> applyAll(@PathVariable("status") String status) {
		SimpleMessage<Apply> sm = new SimpleMessage<Apply>(SimpleMessageHead.OK);
		// 从MYSQL数据库中获得
		List<Apply> items = AdminHelper.findApplyAll(status);
		for (Apply item : items) {
			sm.addRecord(item);
		}
		return sm;
	}
	
	@RequestMapping(value = "apply/all", method = RequestMethod.GET)
	@ResponseBody
	public SimpleMessage<Apply> applyAll() {
		SimpleMessage<Apply> sm = new SimpleMessage<Apply>(SimpleMessageHead.OK);
		// 从MYSQL数据库中获得
		List<Apply> items = AdminHelper.findApplyAll();
		for (Apply item : items) {
			sm.addRecord(item);
		}
		return sm;
	}
	
	@RequestMapping(value = "apply/site/{site}/status/{status}", method = RequestMethod.GET)
	@ResponseBody
	public SimpleMessage<Apply> applyAll(@PathVariable("site") String site,@PathVariable("status") String status) {
		SimpleMessage<Apply> sm = new SimpleMessage<Apply>(SimpleMessageHead.OK);
		// 从MYSQL数据库中获得
		List<Apply> items = AdminHelper.findApplyBySite(site, status);
		for (Apply item : items) {
			sm.addRecord(item);
		}
		return sm;
	}

	@RequestMapping(value = "apply/update/uuid/{uuid}/status/{status}/token/{token}", method = RequestMethod.GET)
	@ResponseBody
	public SimpleMessage<?> applyUpdate(@PathVariable("uuid") String uuid,
			@PathVariable("status") String status,@PathVariable("token") String token) {
		SimpleMessage<?> sm = SimpleMessage.ok();
		try {
			// 进行信息的更新返回
			AdminHelper.updateApplyStatus(uuid, status,token);
		} catch (Exception e) {
			sm = SimpleMessage.blank().head(
					SimpleMessageHead.BIZ_ERROR.repMsg(e.getMessage()));
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
		// 从MYSQL数据库中获得
		List<Card> items = AdminHelper.getBizService().findCardAll(seq);
		for (Card item : items) {
			sm.addRecord(item);
		}
		return sm;
	}

	/**
	 * TODO 卡号删除操作，操作员信息Token
	 */
	@RequestMapping(value = "card/delete/uuid/{uuid}", method = RequestMethod.GET)
	@ResponseBody
	public SimpleMessage<?> cardDelete(@PathVariable("uuid") String uuid) {
		logger.debug("cardDelete:\t" + uuid);
		AdminHelper.deleteCard(uuid);
		return SimpleMessage.ok();
	}
	
	/**
	 * 创建站点:POST , var param = { "info" : '{"seq":"' + seq + '","name":"' + name
	 * + '","address":"' + address + '","telephone":"' + telephone +
	 * '","startTime":"' + startTime + '","endTime":"' + endTime + '"}'};
	 */
	@RequestMapping(value = "site/create", method = RequestMethod.POST)
	@ResponseBody
	public SimpleMessage<?> siteCreate(String info) {
		logger.debug(info);
		// 创建返回消息
		SimpleMessage<?> sm = SimpleMessage.ok();
		try {
			// 进行保存
			AdminHelper.saveSite(info);
		} catch (Exception e) {
			sm = SimpleMessage.blank().head(
					SimpleMessageHead.BIZ_ERROR.repMsg(e.getMessage()));
		}
		return sm;
	}
	
	
	/**
	 * 创建站点:POST , var param = { "info" : '{"seq":"' + seq + '","name":"' + name
	 * + '","address":"' + address + '","telephone":"' + telephone +
	 * '","startTime":"' + startTime + '","endTime":"' + endTime + '"}'};
	 */
	@RequestMapping(value = "site/update", method = RequestMethod.POST)
	@ResponseBody
	public SimpleMessage<?> siteUpdate(String info) {
		// 创建返回消息
		SimpleMessage<?> sm = SimpleMessage.ok();
		try {
			// 进行修改
			AdminHelper.updateSite(info);
		} catch (Exception e) {
			sm = SimpleMessage.blank().head(
					SimpleMessageHead.BIZ_ERROR.repMsg(e.getMessage()));
		}
		return sm;
	}
	
	
	/**
	 * TODO 卡号删除操作，操作员信息Token
	 */
	@RequestMapping(value = "site/delete/uuid/{uuid}", method = RequestMethod.GET)
	@ResponseBody
	public SimpleMessage<?> siteDelete(@PathVariable("uuid") String uuid) {
		logger.debug("cardDelete:\t" + uuid);
		AdminHelper.deleteSite(uuid);
		return SimpleMessage.ok();
	}

	/**
	 * 创建卡号
	 */
	@RequestMapping(value = "card/create", method = RequestMethod.POST)
	@ResponseBody
	public SimpleMessage<?> cardCreate(String info) {
		logger.debug(info);
		// 创建返回消息
		SimpleMessage<?> sm = SimpleMessage.ok();
		try {
			// 进行保存
			AdminHelper.saveCard(info);
		} catch (Exception e) {
			sm = SimpleMessage.blank().head(
					SimpleMessageHead.BIZ_ERROR.repMsg(e.getMessage()));
		}
		return sm;
	}
}
