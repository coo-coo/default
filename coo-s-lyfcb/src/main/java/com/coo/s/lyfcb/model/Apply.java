package com.coo.s.lyfcb.model;

/**
 * Member对Card的一次申请:SBJ
 * 
 */
public class Apply extends BasicEntity {

	public static String T_NAME = "t_apply";
	/**
	 * 
	 */
	private static final long serialVersionUID = 8490671178398356242L;

	public static String STATUS_START = "0"; // 已申请，未办理
	public static String STATUS_FINISH = "1"; // 已申请，已办理
	public static String STATUS_WITHDRAWED = "9"; // 申请后放弃
	public static String SOURCE_WX = "1"; 	// 微信申请

	/**
	 * 场所序号，参见Site.seq
	 */
	@Column
	private String siteSeq = "";
	/**
	 * 站点名称
	 */
	@Column
	private String siteName = "";
	/**
	 * 序号：12位，第一位是大写字母，剩下11位是数字
	 */
	@Column
	private String cardSeq = "";
	/**
	 * 会员Wx序号
	 */
	@Column
	private String memberOpenId = "";
	/**
	 * 会员姓名
	 */
	@Column
	private String memberName = "";
	/**
	 * 会员手机号
	 */
	@Column
	private String memberMobile = "";
	/**
	 * 会员身份证号
	 */
	@Column
	private String memberIdCard = "";
	/**
	 * 申请时间戳
	 */
	@Column
	private Long applyTs = 0l;
	/**
	 * 操作者账号
	 */
	@Column
	private String operator = "";
	/**
	 * 操作者时间戳
	 */
	@Column
	private Long operatorTs = 0l;
	/**
	 * 申请状态：
	 */
	@Column
	private String status = STATUS_START;
	
	@Column
	private String note = "";
	/**
	 * 申请来源：WX，备用缺省字段
	 */
	@Column
	private String source = SOURCE_WX;

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

	public String getMemberOpenId() {
		return memberOpenId;
	}

	public void setMemberOpenId(String memberOpenId) {
		this.memberOpenId = memberOpenId;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getMemberMobile() {
		return memberMobile;
	}

	public void setMemberMobile(String memberMobile) {
		this.memberMobile = memberMobile;
	}

	public String getMemberIdCard() {
		return memberIdCard;
	}

	public void setMemberIdCard(String memberIdCard) {
		this.memberIdCard = memberIdCard;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public long getOperatorTs() {
		return operatorTs;
	}

	public void setOperatorTs(long operatorTs) {
		this.operatorTs = operatorTs;
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

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
}
