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
		Map<String, String> map = new HashMap<String, String>();
		for (Site site : sites) {
			map.put(site.getSeq(), site.getName());
		}
		// 申请单卡号信息设置
		// 接收文本信息
		if (message instanceof TextMessage) {
			TextMessage textMessage = (TextMessage) message;
			String content = textMessage.getContent();
			if (map.containsKey(content)) {
				List<Card> cards = LyfcbHelper.findCardAll(content);
				StringBuilder builder = new StringBuilder();
				builder.append(map.get(content) + ":" + "\n");
				builder.append("可领取卡片：" + cards.size() + "张" + "\n");
				builder.append("可领取卡号：" + "\n");
				StringBuilder cardBuilder = new StringBuilder();
				// mapCard.clear();
				Map<String, String> mapCard = new HashMap<String, String>();
				for (Card card : cards) {
					cardBuilder.append(card.getSiteSeq() + ":" + card.getSeq()
							+ "\n");
					mapCard.put(card.getSeq(), card.getSeq());
					mapCardManager.put(message.getFromUserName(), mapCard);
				}
				TextMessage responseMessage = LyfcbHelper
						.replyTextMessage(message);
				responseMessage.setContent(String.format("%s",
						builder.toString() + cardBuilder.toString()+"\n"+"请回复选择的卡号，无需选号请直接回复1"));
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
				}else{
					responseMessage.setContent(String.format("%s","该站点无卡片资源申请！"));
				}
				return responseMessage;
			}

			// 基本信息
			// //////////////////////////////////////////////////////////////////
			if (content.indexOf(",") != -1||content.indexOf("，") != -1) {
				String formatContent = content.replace("，", ",");
				String[] info = PubString.stringToArray(formatContent, ",");
				TextMessage responseMessage = LyfcbHelper
						.replyTextMessage(message);
				if (info.length != 3) {
					responseMessage.setContent(String
							.format("%s", "您输入格式 不正确!"));
					return responseMessage;
				}
				Apply apply = ApplyManager.getInstance().getApply(
						message.getFromUserName());
				//姓名
				apply.setMemberName(info[0]);
				//手机号
				apply.setMemberMobile(info[1]) ;
				//身份证好
				apply.setMemberIdCard(info[2]);
				//申请单提交
				
				LyfcbHelper.makeApply(apply);
				responseMessage.setContent(String.format("%s",
						"尊敬的" + info[0] + "您好，谢谢您对洛阳公益自行车的关注，您的办卡申请已成功上传，请于"
								+ LyfcbHelper.getNextDay() + "后，9:00-17:00至"
								+ map.get(apply.getSiteSeq())
								+ "站点领取自行车借车卡，领卡时请携带身份证原件及复印件和200押金，谢谢"));
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
							+ "\n");
				}
				responseMessage.setContent(String
						.format("您可以在下列站点领取卡片，请回复序号查看该站点可领取的卡片数量和具体卡号:"
								+ "\n" + "%s", builder.toString()));
				return responseMessage;
			}

			// 办卡须知
			if (INameSpace.MENU_KEY_BKXZ.equals(clickEvent.getEventKey())) {
				// 处理逻辑写在这里
				TextMessage responseMessage = LyfcbHelper
						.replyTextMessage(message);
				StringBuilder builder = new StringBuilder();
				builder.append("办卡时间：上午9:00--11：00,下午2：00--5:00"+"\n");
				builder.append("办卡须知："+"\n");
				builder.append("1、本地居民需携带本人身份证或户口本原件及复印件、办卡时需缴纳200元保证金办理；"+"\n");
				builder.append("2、外地居民凭二代身份证与暂住证或学生证（相关单位证明），及其复印件，缴纳200元保证金办理；"+"\n");
				builder.append("3、外地游客，凭二代身份证、军官证、护照等有效证件原件及复印缴纳600元保证金办理；"+"\n");
				builder.append("4、每个市民只能办一张借车卡，如有重复系统自动停用其中一张，且退卡时需收取20元重复办卡费。");
				
				responseMessage.setContent(String
						.format("%s", builder.toString()));
				return responseMessage;
			}

			// 在线客服
			if (INameSpace.MENU_KEY_ZXKF.equals(clickEvent.getEventKey())) {
				// 处理逻辑写在这里
				TextMessage responseMessage = LyfcbHelper
						.replyTextMessage(message);
				responseMessage.setContent(String
						.format("%s", "您好！ 感谢您选择低碳环保，平安出行的洛阳市福彩公益自行车。关注洛阳市公益自行车管理中心公众号，为您的出行保驾护航。在线客服上午9：00-11：00,下午2：00-5：00。市民热线：80881000。地址：洛阳市九都路与解放路交叉口向东50米。低碳生活，感谢有您！"));
				return responseMessage;
			}
		}
		return null;
	}

}
