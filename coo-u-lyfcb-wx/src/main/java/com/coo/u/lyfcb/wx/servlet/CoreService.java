package com.coo.u.lyfcb.wx.servlet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.coo.s.lyfcb.model.Apply;
import com.coo.s.lyfcb.model.Card;
import com.coo.s.lyfcb.model.Site;
import com.coo.u.lyfcb.wx.INameSpace;
import com.coo.u.lyfcb.wx.util.ApplyManager;
import com.ericxu131.exwechat.model.event.ClickEvent;
import com.ericxu131.exwechat.model.message.Message;
import com.ericxu131.exwechat.model.message.TextMessage;
import com.kingstar.ngbf.s.util.PubString;

/**
 * 核心业务类
 * 
 * @author bingjue.sun
 */
public class CoreService {

	private static Map<String, Map<String, String>> mapCardManager = new HashMap<String, Map<String, String>>();

	// 处理请求
	public static synchronized Message processRequest(Message message) {
		// 所有的站点
		List<Site> sites = LyfcbHelper.findSiteAll();
		Map<String, String> map = LyfcbHelper.getMap();
		// 申请单信息
		// 接收文本信息
		if (message instanceof TextMessage) {
			TextMessage textMessage = (TextMessage) message;
			String content = textMessage.getContent();
			if (map.containsKey(content)) {
				List<Card> cards = LyfcbHelper.findCardAll(content);
				StringBuilder builder = new StringBuilder();
				builder.append(map.get(content) + ":" + "\n\n");
				builder.append("可领取卡片：" + cards.size() + "张" + "\n\n");
				builder.append("可领取卡号：" + "\n\n");
				StringBuilder cardBuilder = new StringBuilder();
				// mapCard.clear();
				Map<String, String> mapCard = new HashMap<String, String>();
				for (Card card : cards) {
					cardBuilder.append(card.getSiteSeq() + ":" + card.getSeq()
							+ "\n\n");
					mapCard.put(card.getSeq(), card.getSeq());
					mapCardManager.put(message.getFromUserName(), mapCard);
				}
				TextMessage responseMessage = LyfcbHelper
						.replyTextMessage(message);
				responseMessage.setContent(String.format("%s",
						builder.toString() + cardBuilder.toString()));
				// 申请单设置站点信息
				Apply apply = LyfcbHelper.builderApply(message);
				apply.setSiteSeq(content);
				ApplyManager.getInstance()
						.add(message.getFromUserName(), apply);
				return responseMessage;
			}

			if (mapCardManager.get(message.getFromUserName()) != null
					&& (boolean) mapCardManager.get(message.getFromUserName())
							.containsKey(content)) {
				TextMessage responseMessage = LyfcbHelper
						.replyTextMessage(message);
				responseMessage.setContent(String.format("%s",
						"您已预订一张公益自行车卡，卡号是：" + content + "\n" + "请输入个人信息格式如下:"
								+ "\n" + "姓名,手机号,身份证号\n" + "例如:\n"
								+ "张三,152****,342201****"));
				// 申请单卡号信息设置
				Apply apply = ApplyManager.getInstance().getApply(
						message.getFromUserName());
				apply.setCardSeq(content);
				ApplyManager.getInstance()
						.add(message.getFromUserName(), apply);
				return responseMessage;
			}

			// //////////////////////////////////////////////////////////////////
			if ("1".equals(content)) {
				TextMessage responseMessage = LyfcbHelper
						.replyTextMessage(message);
				if (mapCardManager.get(message.getFromUserName()).size() > 0) {
					String radomCardNo = LyfcbHelper
							.builderRandomCard(mapCardManager.get(message
									.getFromUserName()));
					responseMessage.setContent(String.format("%s",
							"您已预订一张公益自行车卡，卡号是：" + radomCardNo
									+ "(此处是系统随机分配的卡号)" + "\n" + "请输入个人信息格式如下:"
									+ "\n" + "姓名,手机号,身份证号" + "\n" + "例如:\n"
									+ "张三,152****,342201****"));
					// 申请单卡号信息设置
					Apply apply = ApplyManager.getInstance().getApply(
							message.getFromUserName());
					apply.setCardSeq(radomCardNo);
					ApplyManager.getInstance().add(message.getFromUserName(),
							apply);
				}
				return responseMessage;
			}

			// 基本信息
			// //////////////////////////////////////////////////////////////////
			if (content.indexOf(",") != -1) {
				String[] info = PubString.stringToArray(content, ",");
				TextMessage responseMessage = LyfcbHelper
						.replyTextMessage(message);
				if (info.length != 3) {
					responseMessage.setContent(String
							.format("%s", "您输入格式 不正确!"));
					return responseMessage;
				}
				Apply apply = ApplyManager.getInstance().getApply(
						message.getFromUserName());
				responseMessage.setContent(String.format("%s",
						"尊敬的" + info[0] + "您好，谢谢您对洛阳公益自行车的关注，您的办卡申请已成功上传，请于"
								+ LyfcbHelper.getNextDay() + "后，9:00-17:00至"
								+ map.get(apply.getSiteSeq())
								+ "站点领取自行车借车卡，领卡时请携带身份证原件及复印件和200押金，谢谢")
						+

						"\n\n"
						+ ".name:"
						+ apply.getUuid()
						+ ","
						+ apply.getCardSeq() + "," + apply.getSiteSeq());
				return responseMessage;
			}
		}

		// //////////////////////////////////////////////////////////////////////
		// 接收自定义菜单点击事件
		if (message instanceof ClickEvent) {
			ClickEvent clickEvent = (ClickEvent) message;
			// 在线申请
			if (INameSpace.MENU_KEY_ZXSQ.equals(clickEvent.getEventKey())) {
				TextMessage responseMessage = LyfcbHelper
						.replyTextMessage(message);
				StringBuilder builder = new StringBuilder();
				for (Site site : sites) {
					builder.append(site.getSeq() + ":" + site.getName()
							+ "\n\n");
				}
				responseMessage.setContent(String
						.format("您可以在下列站点领取卡片，请回复序号查看该站点可领取的卡片数量和具体卡号:"
								+ "\n\n" + "%s", builder.toString()));
				return responseMessage;
			}

			// 办卡须知
			if (INameSpace.MENU_KEY_BKXZ.equals(clickEvent.getEventKey())) {
				// 处理逻辑写在这里
				TextMessage responseMessage = LyfcbHelper
						.replyTextMessage(message);
				responseMessage.setContent(String
						.format("Hi:%s", "你点击了办卡须知菜单！"));
				return responseMessage;
			}

			// 在线客服
			if (INameSpace.MENU_KEY_ZXKF.equals(clickEvent.getEventKey())) {
				// 处理逻辑写在这里
				TextMessage responseMessage = LyfcbHelper
						.replyTextMessage(message);
				responseMessage.setContent(String
						.format("Hi:%s", "你点击了在线客服菜单！"));
				return responseMessage;
			}
		}
		return null;
	}

}
