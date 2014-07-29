package com.coo.u.lyfcb.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.coo.u.lyfcb.model.Apply;
import com.coo.u.lyfcb.model.Card;
import com.coo.u.lyfcb.model.Site;
import com.kingstar.ngbf.s.util.StringUtil;

/**
 * ICardApplyService的数据库实现
 * 
 * @author boqing.shen
 * 
 */
@SuppressWarnings("unchecked")
@Component(IBizService.SPRING_KEY_DB)
public class DbBizServiceImpl implements IBizService {

	private Logger logger = Logger.getLogger(DbBizServiceImpl.class);

	@PostConstruct
	public void init() {
		// logger.debug("DbBizServiceImpl...");
		// List<Site> list = findSiteAll();
		// for (Site site : list) {
		// logger.info(site.getInfo());
		// }
		// List<Card> list2 = findCardAll("001");
		// for (Card card : list2) {
		// logger.info(card.getInfo());
		// }
	}

	public static void main(String[] args) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("seq", "001");
		map.put("name", "name-001");
		map.put("uuid", "uuid-001");
		Object o = ModelManager.get().merge(map, Site.class);
		Site site = (Site) o;
		System.out.println(site.getName() + "\t" + site.getUuid());
	}

	@Override
	public List<Site> findSiteAll() {
		String sql = "select * from " + Site.T_NAME;
		return (List<Site>) ModelManager.get().find(sql, null, Site.class);
	}

	@Override
	public List<Card> findCardAll(String siteSeq) {
		String sql = "select * from " + Card.T_NAME
				+ " where siteSeq = ? and status='0'";
		Object[] params = new Object[1];
		params[0] = siteSeq;
		return (List<Card>) ModelManager.get().find(sql, params, Card.class);
	}

	@Override
	public Card getRandomCard(String siteSeq) {
		List<Card> list = findCardAll(siteSeq);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public Card getCard(String siteSeq, String seq) {
		String sql = "select * from " + Card.T_NAME
				+ " where seq=? and siteSeq = ? and status='0'";
		Object[] params = new Object[2];
		params[0] = seq;
		params[1] = siteSeq;
		List<Card> list = (List<Card>) ModelManager.get().find(sql, params,
				Card.class);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public void makeApply(Apply apply) {
		// 存储一条申请记录
		Object[] params = new Object[8];
		params[0] = StringUtil.uuid();
		params[1] = apply.getSiteSeq();
		params[2] = apply.getCardSeq();
		params[3] = apply.getMemberOpenId();
		params[4] = apply.getMemberName();
		params[5] = apply.getMemberMobile();
		params[6] = apply.getMemberIdCard();
		params[7] = System.currentTimeMillis();
		String sql = "insert into "
				+ Apply.T_NAME
				+ "(uuid,siteSeq,cardSeq,memberOpenId,memberName,memberMobile,memberIdCard,applyTs,operator,operatorTs,status,note)"
				+ " values(?,?,?,?,?,?,?,?,'',0,'0','')";
		logger.debug(sql);
		ModelManager.get().execute(sql, params);

		// 将申请的卡变为STATUS_LOCKED状态
		updateCardStatus(apply.getSiteSeq(), apply.getCardSeq(),
				Card.STATUS_LOCKED);
	}

	@Override
	public void updateCardStatus(String siteSeq, String seq, String status) {
		String sql = "update " + Card.T_NAME
				+ " set status=? where seq=? and siteSeq = ?";
		Object[] params = new Object[3];
		params[0] = status;
		params[1] = seq;
		params[2] = siteSeq;
		ModelManager.get().execute(sql, params);
	}
}
