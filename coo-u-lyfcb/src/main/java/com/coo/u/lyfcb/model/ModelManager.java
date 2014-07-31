package com.coo.u.lyfcb.model;

import java.util.HashMap;
import java.util.Map;

public class ModelManager {

	private Map<String, Account> accounts = new HashMap<String, Account>();

	public Map<String, Account> getAccounts() {
		return accounts;
	}

	/**
	 * 单实例
	 */
	private static ModelManager instance;

	/**
	 * 获取实例,采用synchronized避免多线程冲突
	 * 
	 * @return
	 */
	public static synchronized ModelManager get() {
		if (instance == null) {
			instance = new ModelManager();
		}
		return instance;
	}

	/**
	 * 私有构造函数
	 */
	private ModelManager() {

	}

}
