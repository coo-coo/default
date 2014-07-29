package com.coo.u.lyfcb.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.coo.u.lyfcb.model.Apply;
import com.coo.u.lyfcb.model.Card;
import com.coo.u.lyfcb.model.Site;
import com.coo.u.lyfcb.service.IBizService;
import com.coo.u.lyfcb.service.ModelManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kingstar.ngbf.s.ntp.spi.Token;
import com.kingstar.ngbf.s.util.NgbfRuntimeException;
import com.kingstar.ngbf.s.util.PubString;
import com.kingstar.ngbf.s.util.SpringContextFactory;
import com.kingstar.ngbf.s.util.StringUtil;

public final class AdminHelper {

	// 存储所有的Token，TODO
	// private static Map<String,Token> tokens = new HashMap<String,Token>();

	private static Logger logger = Logger.getLogger(AdminHelper.class);

	/**
	 * 仅存留一个Admin的Token，单Admin模型
	 */
	private static Token adminToken = null;

	public Token getAdminToken() {
		return adminToken;
	}

	public static void setAdminToken(Token adminToken) {
		AdminHelper.adminToken = adminToken;
	}

	/**
	 * 将键值对的Json数据格式转化成为Map对象
	 * 
	 * @param jsonInfo
	 * @return
	 */
	public static Map<String, Object> transform(String jsonInfo) {
		Gson gson = new Gson();
		Map<String, Object> item = gson.fromJson(jsonInfo,
				new TypeToken<HashMap<String, Object>>() {
				}.getType());
		return item;
	}

	/**
	 * 获得指定状态的申请列表
	 */
	@SuppressWarnings("unchecked")
	public static List<Apply> findApplyAll(String status) {
		String sql = "select * from " + Apply.T_NAME + " where status = ?";
		Object[] params = new Object[1];
		params[0] = status;
		return (List<Apply>)ModelManager.get().find(sql, null, Apply.class);
	}

	/**
	 * 保存站点信息,保存HTML传过来的Json字符串
	 */
	public static void saveSite(String jsonInfo) throws NgbfRuntimeException {
		Map<String, Object> item = AdminHelper.transform(jsonInfo);
		String seq = getStr(item, "seq");
		String name = getStr(item, "name");
		String address = getStr(item, "address");
		String telephone = getStr(item, "telephone");
		String startTime = getStr(item, "startTime");
		String endTime = getStr(item, "endTime");
		// 合法性判定
		if (PubString.isNullOrSpace(seq)) {
			throw new NgbfRuntimeException("站点序号不能为空!");
		}
		Object[] params = new Object[7];
		params[0] = StringUtil.uuid();
		params[1] = seq;
		params[2] = name;
		params[3] = address;
		params[4] = telephone;
		params[5] = startTime;
		params[6] = endTime;
		String sql = "insert into "
				+ Site.T_NAME
				+ "(uuid,seq,name,address,telephone,startTime,endTime,deposit,longitude,latitude,cityCode,note)"
				+ " values(?,?,?,?,?,?,?,200.0,0.0,0.0,'LY','')";
		logger.debug(sql);
		ModelManager.get().execute(sql, params);
	}

	/**
	 * 保存站点信息,保存HTML传过来的Json字符串 var param = {"info" : '{"seq":"' + seq +
	 * '","siteSeq":"' + siteSeq + '"}'};
	 */
	public static void saveCard(String jsonInfo) throws NgbfRuntimeException {

		Map<String, Object> item = AdminHelper.transform(jsonInfo);
		String seq = getStr(item, "seq");
		String siteSeq = getStr(item, "siteSeq");
		// 合法性判定
		if (PubString.isNullOrSpace(seq)) {
			throw new NgbfRuntimeException("卡号不能为空!");
		}
		Object[] params = new Object[3];
		params[0] = StringUtil.uuid();
		params[1] = seq;
		params[2] = siteSeq;
		String sql = "insert into " + Card.T_NAME
				+ "(uuid,seq,name,siteSeq,status,cityCode,note)"
				+ " values(?,?,'',?,'0','LY','')";
		logger.debug(sql);
		ModelManager.get().execute(sql, params);
	}

	public static void main(String[] args) {
		String sql = "insert into " + Site.T_NAME
				+ "(uuid,seq,name) values(?,?,?)";
		System.out.println(sql);
	}

	private static String getStr(Map<String, Object> item, String key) {
		String v = "";
		Object o = item.get(key);
		if (o != null) {
			v = o.toString();
		}
		return v;
	}

	/**
	 * 获得业务服务接口
	 * 
	 * @return
	 */
	public static IBizService getBizService() {
		return (IBizService) SpringContextFactory
				.getSpringBean(IBizService.SPRING_KEY_DB);
	}

}
