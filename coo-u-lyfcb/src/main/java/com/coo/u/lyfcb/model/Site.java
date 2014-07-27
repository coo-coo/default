package com.coo.u.lyfcb.model;

/**
 * 取卡网点地址:ZSL
 * 
 */
public class Site {

	/**
	 * 序号：创建一个Site,给以业务序号，作为外键，001，002....
	 */
	private String seq = "";
	/**
	 * 名称
	 */
	private String name = "";
	/**
	 * 地址
	 */
	private String address = "";
	/**
	 * 联系电话
	 */
	private String telephone = "";
	/**
	 * 服务开始时间
	 */
	private String startTime = "";
	/**
	 * 服务结束时间
	 */
	private String endTime = "";
	/**
	 * 押金，单位元
	 */
	private Double deposit = 0.0;
	/**
	 * 地址经度:备用字段
	 */
	private Double longitude = 0.0;
	/**
	 * 地址维度:备用字段
	 */
	private Double latitude = 0.0;
	/**
	 * 城市编码：备用字段，缺省
	 */
	private String cityCode = "LY";
	/**
	 * 注释:备用字段
	 */
	private String note = "";

	public Site() {

	}

	public Site(String seq, String name) {
		this.seq = seq;
		this.name = name;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Double getDeposit() {
		return deposit;
	}

	public void setDeposit(Double deposit) {
		this.deposit = deposit;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
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
}
