package com.coo.u.lyfcb.rest;

import java.util.ArrayList;
import java.util.List;

import com.coo.u.lyfcb.model.Card;
import com.coo.u.lyfcb.model.Site;
import com.kingstar.ngbf.s.ntp.spi.Token;

public final class AdminHelper {

	// 存储所有的Token，TODO
	// private static Map<String,Token> tokens = new HashMap<String,Token>();

	/**
	 * 仅存留一个Admin的Token，单Admin模型
	 */
	private Token adminToken = null;

	public Token getAdminToken() {
		return adminToken;
	}

	public void setAdminToken(Token adminToken) {
		this.adminToken = adminToken;
	}

	public List<Site> mockSites() {
		List<Site> list = new ArrayList<Site>();
		list.add(new Site("001", "站点1"));
		list.add(new Site("002", "站点2"));
		list.add(new Site("003", "站点3"));
		list.add(new Site("004", "站点4"));
		return list;
	}

	public List<Card> mockCards(String siteSeq) {
		List<Card> list = new ArrayList<Card>();
		list.add(new Card(siteSeq, siteSeq + "-00001"));
		list.add(new Card(siteSeq, siteSeq + "-00002"));
		list.add(new Card(siteSeq, siteSeq + "-00003"));
		list.add(new Card(siteSeq, siteSeq + "-00004"));
		list.add(new Card(siteSeq, siteSeq + "-00005"));
		list.add(new Card(siteSeq, siteSeq + "-00006"));
		return list;
	}

	/**
	 * 单实例
	 */
	private static AdminHelper instance;

	/**
	 * 获取实例,采用synchronized避免多线程冲突
	 * 
	 * @return
	 */
	public static synchronized AdminHelper get() {
		if (instance == null) {
			instance = new AdminHelper();
		}
		return instance;
	}

	/**
	 * 私有构造函数
	 */
	private AdminHelper() {

	}

}
