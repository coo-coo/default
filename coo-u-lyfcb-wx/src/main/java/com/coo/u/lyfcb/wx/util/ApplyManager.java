package com.coo.u.lyfcb.wx.util;

import java.util.HashMap;
import java.util.Map;

import com.coo.s.lyfcb.model.Apply;

public class ApplyManager {

	private static final ApplyManager instance = new ApplyManager();

	// <openId,apply>
	private Map<String, Apply> map = new HashMap<String, Apply>();

	private ApplyManager() {
	}

	public static ApplyManager getInstance() {
		return instance;
	}

	public Apply getApply(String openId) {
		Apply apply = map.get(openId);
		return apply;
	}

	public void add(String openId, Apply apply) {
		map.put(openId, apply);
	}

}
