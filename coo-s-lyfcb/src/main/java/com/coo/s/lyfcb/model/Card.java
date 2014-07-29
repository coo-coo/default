package com.coo.s.lyfcb.model;

/**
 * 卡:SBQ
 * 
 */
public class Card extends BasicEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7246117088814566432L;

	public static String T_NAME = "t_card";
	
	public static String STATUS_FREE = "0"; // 已创建，未申请
	public static String STATUS_LOCKED = "5"; // 已申请，未办理，被锁定 ：超时解锁?
	public static String STATUS_OWNED = "1"; // 已申请，已办理，已解锁
	public static String STATUS_WITHDRAWED = "8"; // 已注销，已挂失?

	/**
	 * 序号：12位，第一位是大写字母，剩下11位是数字
	 */
	@Column
	private String seq = "";
	/**
	 * 场所序号，参见Site.seq
	 */
	@Column
	private String siteSeq = "";
	/**
	 * 名称:备用字段
	 */
	@Column
	private String name = "";
	/**
	 * 城市编码：备用字段，缺省
	 */
	@Column
	private String cityCode = "LY";
	/**
	 * 注释:备用字段
	 */
	@Column
	private String note = "";
	/**
	 * 卡状态：
	 */
	@Column
	private String status = STATUS_FREE;

	public Card() {

	}

	public Card(String siteSeq, String seq) {
		this.siteSeq = siteSeq;
		this.seq = seq;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getSiteSeq() {
		return siteSeq;
	}

	public void setSiteSeq(String siteSeq) {
		this.siteSeq = siteSeq;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getInfo(){
		return this.getUuid() + "-" + this.getSeq() + "-" + this.getName();
	}
}
