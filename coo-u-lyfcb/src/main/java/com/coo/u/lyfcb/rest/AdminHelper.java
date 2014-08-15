package com.coo.u.lyfcb.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.mortbay.log.Log;

import com.coo.s.lyfcb.model.Apply;
import com.coo.s.lyfcb.model.Card;
import com.coo.s.lyfcb.model.Site;
import com.coo.s.lyfcb.service.IBizService;
import com.coo.u.lyfcb.model.Account;
import com.coo.u.lyfcb.model.JdbcManager;
import com.coo.u.lyfcb.model.ModelManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kingstar.ngbf.s.ntp.spi.Token;
import com.kingstar.ngbf.s.util.NgbfRuntimeException;
import com.kingstar.ngbf.s.util.PubString;
import com.kingstar.ngbf.s.util.SpringContextFactory;

//import com.kingstar.ngbf.s.util.StringUtil;

public final class AdminHelper {

	// 存储所有的Token，TODO 分布式
	private static Map<String, Token> tokens = new HashMap<String, Token>();

	private static Logger logger = Logger.getLogger(AdminHelper.class);

	/**
	 * 依据Token获得Token对象
	 */
	public static Token getAccountToken(String token) {
		return tokens.get(token);
	}

	/**
	 * 管理员登录实现
	 */
	public static Token adminLogin(String username, String password)
			throws NgbfRuntimeException {
		// 登录验证
		if (PubString.isNullOrSpace(username)
				|| PubString.isNullOrSpace(password)) {
			throw new NgbfRuntimeException("用户名或密码为空");
		}
		Account account = ModelManager.get().getAccounts().get(username);
		if (account == null || !account.getPassword().equals(password)) {
			throw new NgbfRuntimeException("用户名或密码错误");
		}

		// 创建Token,并存储,单内存存储
		Token token = new Token();
		token.setAccount(username);
		token.setRole(account.getRole());
		token.setPartition(account.getPartition());
		logger.debug("token==" + token.getToken());
		// 存储Token
		tokens.put(token.getToken(), token);
		return token;
	}

	/**
	 * 登出操作实现,清除Token
	 * 
	 * @param token
	 */
	public static void adminLogout(String token) {
		tokens.remove(token);
	}

	/**
	 * 将键值对的Json数据格式转化成为Map对象
	 * 
	 * @param jsonInfo
	 * @return
	 */
	private static Map<String, Object> transform(String jsonInfo) {
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
		if (status.equals("1234")) {
			String sql = "select * from " + Apply.T_NAME;
			return (List<Apply>) JdbcManager.find(sql, null, Apply.class);
		}
		String sql = "select * from " + Apply.T_NAME + " where status = ?";
		Object[] params = new Object[1];
		params[0] = status;
		return (List<Apply>) JdbcManager.find(sql, params, Apply.class);
	}

	/**
	 * 获得所有的申请单
	 */
	@SuppressWarnings("unchecked")
	public static List<Apply> findApplyAll() {
		String sql = "select * from " + Apply.T_NAME;
		return (List<Apply>) JdbcManager.find(sql, null, Apply.class);
	}

	// 暂时定义查询所有的状态为1234，所有站点的key也为1234,,rest中不能有特殊字符
	private static String statusAll = "1234";

	/**
	 * 获得指定状态指定站点的申请列表
	 */
	@SuppressWarnings("unchecked")
	public static List<Apply> findApplyBySite(String site, String status) {
		// 查询所有
		if (site.equals(statusAll) && status.equals(statusAll)) {
			String sql = "select * from " + Apply.T_NAME;
			logger.debug(sql);
			return (List<Apply>) JdbcManager.find(sql, null, Apply.class);
		}

		// 查询某个站点的
		if (!site.equals(statusAll) && status.equals(statusAll)) {
			String sql = "select * from " + Apply.T_NAME + " where siteSeq = ?";
			logger.debug(sql);
			Object[] params = new Object[1];
			params[0] = site;
			return (List<Apply>) JdbcManager.find(sql, params, Apply.class);
		}

		// 查询某个状态的
		if (site.equals(statusAll) && !status.equals(statusAll)) {
			String sql = "select * from " + Apply.T_NAME + " where status = ?";
			logger.debug(sql);
			Object[] params = new Object[1];
			params[0] = status;
			return (List<Apply>) JdbcManager.find(sql, params, Apply.class);
		}

		// 查询某个状态的
		if (!site.equals(statusAll) && !status.equals(statusAll)) {
			String sql = "select * from " + Apply.T_NAME
					+ " where siteSeq = ? and status = ?";
			Object[] params = new Object[2];
			params[0] = site;
			params[1] = status;
			return (List<Apply>) JdbcManager.find(sql, params, Apply.class);
		}
		return new ArrayList<Apply>();
	}

	/**
	 * 操作员更新申请信息
	 */
	public static void updateApplyStatus(String uuid, String status,
			String token) throws NgbfRuntimeException {
		String sql = "update " + Apply.T_NAME
				+ " set status=?,operator=?,operatorTs=?  where uuid=?";
		// 获得登录人的信息
		Token t = getAccountToken(token);
		Object[] params = new Object[4];
		params[0] = status;
		params[1] = t.getAccount();
		params[2] = System.currentTimeMillis();
		params[3] = uuid;
		logger.debug(sql);
		JdbcManager.execute(sql, params);
	}

	/**
	 * 删除卡信息,假删除
	 * 
	 * @param uuid
	 * @throws NgbfRuntimeException
	 */
	public static void deleteCard(String uuid) throws NgbfRuntimeException {
		String sql = "update " + Card.T_NAME + " set status='9' where uuid=?";
		// TODO 操作人信息
		// Token t = getAccountToken(token);
		Object[] params = new Object[1];
		params[0] = uuid;
		logger.debug(sql);
		JdbcManager.execute(sql, params);
	}

	/**
	 * 删除站点
	 * 
	 * @param uuid
	 * @throws NgbfRuntimeException
	 */
	public static void deleteSite(String uuid) throws NgbfRuntimeException {
		String sql = "delete from t_site where uuid=?";
		Object[] params = new Object[1];
		params[0] = uuid;
		logger.debug(sql);
		JdbcManager.execute(sql, params);
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
		params[0] = genericUUid();
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
		JdbcManager.execute(sql, params);
	}

	/**
	 * 修改站点信息,保存HTML传过来的Json字符串
	 */
	public static void updateSite(String jsonInfo) throws NgbfRuntimeException {
		Map<String, Object> item = AdminHelper.transform(jsonInfo);
		String uuid = getStr(item, "uuid");
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
		Object[] params = new Object[6];
		params[0] = name;
		params[1] = address;
		params[2] = telephone;
		params[3] = startTime;
		params[4] = endTime;
		params[5] = uuid;
		String sql = "update "
				+ Site.T_NAME
				+ " set name=?,address=?,telephone=?,startTime=?,endTime=?  where uuid=?";
		logger.debug(sql + ":" + uuid + "：" + seq + ":" + name);
		JdbcManager.execute(sql, params);
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
		params[0] = genericUUid();
		params[1] = seq;
		params[2] = siteSeq;
		String sql = "insert into " + Card.T_NAME
				+ "(uuid,seq,name,siteSeq,status,cityCode,note)"
				+ " values(?,?,'',?,'0','LY','')";
		logger.debug(sql);
		JdbcManager.execute(sql, params);
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

	public static String genericUUid() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString().replace("-", "");
	}

}
