package com.coo.u.lyfcb.service;

import java.io.File;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.stereotype.Component;

import com.coo.u.lyfcb.model.Account;
import com.coo.u.lyfcb.model.ModelManager;
import com.kingstar.ngbf.s.util.PubString;
import com.kingstar.ngbf.s.util.SpringContextAware;
import com.kingstar.ngbf.s.util.XmlUtil;

/**
 * 账号管理,从conf/ngbf-account.xml下获取
 * @author boqing.shen
 *
 */
@Component("LyfcbAccountService")
public class AccountService {
	
	private static Logger logger = Logger.getLogger(DbBizServiceImpl.class);
	
	@PostConstruct
	public void init() {
		// 读取conf/ngbf-account.xml资源,形成账号信息
		try {
			String path = SpringContextAware.getApplicationContext().getResource("")
					.getFile().getAbsolutePath();
			// 去掉末尾的"lib",转到conf/ngbf-account.xml
			path = path.substring(0,path.length()-3) + "conf/ngbf-account.xml";
			// 加载账号信息
			loadAccount(path);
			
//			Collection<Account> list = ModelManager.get().getAccounts().values();
//			for (Account account : list) {
//				logger.debug(account.getAccount() + "\t" + account.getPassword());
//			}
			logger.debug(path);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("-------------------------------");
	}
	
	/**
	 * 读取conf/ngbf-account.xml文件
	 * @param filePath
	 */
	private static void loadAccount(String filePath) {
		try {
			Document doc = XmlUtil.createDocument(new File(filePath));
			@SuppressWarnings("unchecked")
			List<Element> eles = doc.selectNodes("//ngbf/resource");
			for (Element e : eles) {
				String account = getNotNullAttrValue(e, "account");
				String password = getNotNullAttrValue(e, "password");
				String partition = getNotNullAttrValue(e, "partition");
				String role = getNotNullAttrValue(e, "role");
				Account a = new Account(account,partition,role);
				a.setPassword(password);
				ModelManager.get().getAccounts().put(account, a);
			}
		} catch (Exception e) {
			logger.error(filePath + "文件加载错误,系统找不到指定的路径," + e.getMessage());
		}
	}
	
	private static String getNotNullAttrValue(Element e, String name) {
		return PubString.get(XmlUtil.getAttributeValue(e, name));
	}
	
}
