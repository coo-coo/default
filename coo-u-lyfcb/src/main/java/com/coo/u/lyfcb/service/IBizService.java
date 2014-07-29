package com.coo.u.lyfcb.service;

import java.util.List;

import com.coo.u.lyfcb.model.Apply;
import com.coo.u.lyfcb.model.Card;
import com.coo.u.lyfcb.model.Site;

/**
 * 卡申请相关服务,进s-lyfcb（和Model一起进）
 * 
 * @author boqing.shen
 * 
 */
public interface IBizService {

	String SPRING_KEY_MOCK = "MockLyfcbService";
	String SPRING_KEY_DB = "DbLyfcbService";

	/**
	 * 获得所有的站点
	 */
	List<Site> findSiteAll();

	/**
	 * 获得某站点的所有(有效)卡 Card.status = STATUS_FREE
	 */
	List<Card> findCardAll(String siteSeq);

	/**
	 * 获得某站点的随机(有效)卡 Card.status = STATUS_FREE
	 */
	Card getRandomCard(String siteSeq);

	/**
	 * 获得某站点某指定的(有效)卡 Card.status = STATUS_FREE
	 */
	Card getCard(String siteSeq, String seq);

	/**
	 * 创建一个申请请求
	 */
	void makeApply(Apply apply);

	/**
	 * 更新卡状态信息
	 */
	void updateCardStatus(String siteSeq, String seq, String status);
}
