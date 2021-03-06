package com.coo.s.lyfcb.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.coo.s.lyfcb.model.Apply;
import com.coo.s.lyfcb.model.Card;
import com.coo.s.lyfcb.model.Site;

/**
 * ICardApplyService的Mock实现,用于WX测试
 * 
 * @author boqing.shen
 * 
 */
@Component(IBizService.SPRING_KEY_MOCK)
public class MockBizServiceImpl implements IBizService {

	public List<Site> findSiteAll() {
		List<Site> list = new ArrayList<Site>();
		list.add(new Site("001", "站点1"));
		list.add(new Site("002", "站点2"));
		list.add(new Site("003", "站点3"));
		list.add(new Site("004", "站点4"));
		return list;
	}

	public List<Card> findCardAll(String siteSeq) {
		List<Card> list = new ArrayList<Card>();
		list.add(new Card(siteSeq, siteSeq + "-00001"));
		list.add(new Card(siteSeq, siteSeq + "-00002"));
		list.add(new Card(siteSeq, siteSeq + "-00003"));
		list.add(new Card(siteSeq, siteSeq + "-00004"));
		list.add(new Card(siteSeq, siteSeq + "-00005"));
		list.add(new Card(siteSeq, siteSeq + "-00006"));
		return list;
	}

	public Card getRandomCard(String siteSeq) {
		long seq = System.currentTimeMillis();
		return new Card(siteSeq, "R-" + seq);
	}

	public Card getCard(String siteSeq, String seq) {
		return new Card(siteSeq, seq);
	}

	public void makeApply(Apply apply) {
		// 暂不实现
	}

	public void updateCardStatus(String siteSeq, String seq, String status) {
		// 暂不实现
	}

}
