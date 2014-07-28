package com.coo.u.lyfcb.model;

/**
 * 基础对象类,指定UUID,作为主键
 * @author boqing.shen
 *
 */
public class BasicEntity implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -299588410750353584L;
	
	@Column
	protected String uuid = "";

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
}
