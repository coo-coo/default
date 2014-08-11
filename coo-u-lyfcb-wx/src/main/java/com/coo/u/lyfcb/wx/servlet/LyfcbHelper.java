package com.coo.u.lyfcb.wx.servlet;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.coo.s.lyfcb.model.Apply;
import com.coo.s.lyfcb.model.Card;
import com.coo.s.lyfcb.model.Site;
import com.coo.s.lyfcb.service.IBizService;
import com.ericxu131.exwechat.model.message.Message;
import com.ericxu131.exwechat.model.message.NewsMessage;
import com.ericxu131.exwechat.model.message.TextMessage;
import com.kingstar.ngbf.s.util.SpringContextFactory;

public class LyfcbHelper {

	public static List<Site> findSiteAll() {
		return getBizService().findSiteAll();
	}

	public static List<Card> findCardAll(String siteSeq) {
		return getBizService().findCardAll(siteSeq);
	}

	public static void makeApply(Apply apply) {
		getBizService().makeApply(apply);
	}

	// <站点seq，站点名>
	private static Map<String, String> map = new HashMap<String, String>();

	public static Map<String, String> getMap() {
		return map;
	}

	static {
		for (Site site : findSiteAll()) {
			map.put(site.getSeq(), site.getName());
		}
	}

	// 创建申请单
	public static Apply builderApply(Message message) {
		Apply apply = new Apply();
		apply.setUuid(message.getFromUserName());
		return apply;
	}

	// 随机获取一个卡号
	public static String builderRandomCard(Map<String, String> map) {
		String[] keys = map.keySet().toArray(new String[0]);
		Random random = new Random();
		String randomKey = keys[random.nextInt(keys.length)];
		String randomValue = map.get(randomKey);
		return randomValue;
	}

	// 获取当前日期的后一天
	public static String getNextDay() {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		Date date = calendar.getTime();
		return sdf.format(date);
	}

	/**
	 * 获得业务服务接口
	 * 
	 * @return
	 */
	public static IBizService getBizService() {
		// return (IBizService) SpringContextFactory
		// .getSpringBean(IBizService.SPRING_KEY_MOCK);
		return (IBizService) SpringContextFactory
				.getSpringBean(IBizService.SPRING_KEY_DB);
	}

	// 回复TextMessage
	public static TextMessage replyTextMessage(Message message) {
		TextMessage responseTextMessage = new TextMessage();
		responseTextMessage.setFromUserName(message.getToUserName());
		responseTextMessage.setToUserName(message.getFromUserName());
		return responseTextMessage;
	}

	// 回复NewsMessage
	public static NewsMessage replyNewsMessage(Message message) {
		NewsMessage responseTextMessage = new NewsMessage();
		responseTextMessage.setFromUserName(message.getToUserName());
		responseTextMessage.setToUserName(message.getFromUserName());
		return responseTextMessage;
	}
}
