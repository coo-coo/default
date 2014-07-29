package com.coo.u.lyfcb.model;

/**
 * 会员信息:SBJ
 * 
 */
public class Member extends BasicEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8255660390583436134L;
	/**
	 * WX的开放ID等
	 */
	private String openId = "";
	/**
	 * 序号：会员一旦创建，产生一个唯一标识号
	 */
	private String seq = "";
	/**
	 * 姓名:备用字段
	 */
	private String name = "";
	/**
	 * 性别：0=男;1=女
	 */
	private String gender = "";
	/**
	 * 出生日期：YYYYMMDD
	 */
	private String birthday = "";
	/**
	 * 城市编码：备用字段，缺省
	 */
	private String cityCode = "LY";
	/**
	 * 注释:备用字段
	 */
	private String note = "";
	/**
	 * 证件类型： （0）身份证 （1） 军官证 （ 2 ）护照 （ 3 ）其他(不支持？)
	 */
	private String cardType = "";
	/**
	 * 证件号
	 */
	private String cardNo = "";
	/**
	 * 联系手机
	 */
	private String mobile = "";
	/**
	 * 联系电话
	 */
	private String telephone = "";
	/**
	 * 住址
	 */
	private String address = "";

	// 其它信息，待定
	
	public String getOpenId() {
		return openId;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
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

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}
	
}
