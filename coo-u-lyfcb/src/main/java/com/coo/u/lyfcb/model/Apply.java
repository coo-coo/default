package com.coo.u.lyfcb.model;

/**
 * Member对Card的一次申请:SBJ
 *
 */
public class Apply extends Member{
	
	public static String STATUS_START = "0";		// 已申请，未办理
	public static String STATUS_FINISH = "1";		// 已申请，已办理
	public static String STATUS_WITHDRAWED = "9";	// 申请后放弃
	
	/**
	 * 序号：一旦创建，产生一个唯一标识号
	 */
	private String seq = "";
	/**
	 * 场所序号，参见Site.seq
	 */
	private String siteSeq = "";
	/**
	 * 序号：12位，第一位是大写字母，剩下11位是数字
	 */
	private String cardSeq = "";
	/**
	 * 会员序号
	 */
	private String memberSeq = "";
	/**
	 * 申请时间戳
	 */
	private long applyTs = 0l;
	/**
	 * 申请状态：
	 */
	private String status = "";
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getSiteSeq() {
		return siteSeq;
	}
	public void setSiteSeq(String siteSeq) {
		this.siteSeq = siteSeq;
	}
	public String getCardSeq() {
		return cardSeq;
	}
	public void setCardSeq(String cardSeq) {
		this.cardSeq = cardSeq;
	}
	public String getMemberSeq() {
		return memberSeq;
	}
	public void setMemberSeq(String memberSeq) {
		this.memberSeq = memberSeq;
	}
	public long getApplyTs() {
		return applyTs;
	}
	public void setApplyTs(long applyTs) {
		this.applyTs = applyTs;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
