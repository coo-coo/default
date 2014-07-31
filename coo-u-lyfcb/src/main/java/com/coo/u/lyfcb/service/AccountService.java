package com.coo.u.lyfcb.service;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

/**
 * 账号管理,从conf/ngbf-account.xml下获取
 * @author boqing.shen
 *
 */
@Component("LyfcbAccountService")
public class AccountService {
	
	
	@PostConstruct
	public void init() {
		// 读取conf/ngbf-account.xml资源,形成账号信息
	}
	
}
