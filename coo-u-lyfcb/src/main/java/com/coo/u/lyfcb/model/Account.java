package com.coo.u.lyfcb.model;

/**
 * 定义简单的登录账号,参见ngbf-account.xml
 */
public class Account {

	/**
	 * 构造函数
	 */
	public Account() {

	}

	/**
	 * 构造函数
	 */
	public Account(String account, String partition, String role) {
		this.account = account;
		this.partition = partition;
		this.role = role;
	}

	/**
	 * 当前登录账号
	 */
	private String account = "";
	/**
	 * 分区账号
	 */
	private String partition = "";
	/**
	 * 系统角色,非业务角色
	 */
	private String role = "";
	/**
	 * 账号密码
	 */
	private String password = "";

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPartition() {
		return partition;
	}

	public void setPartition(String partition) {
		this.partition = partition;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
