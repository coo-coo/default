package com.coo.d.lyfcb;

import org.apache.log4j.Logger;

import com.kingstar.ngbf.u.base.NgbfProcess;

public final class MainProcess extends NgbfProcess {
	
	private static Logger logger = Logger.getLogger(MainProcess.class);
	
	public static void main(String[] args) {
		try {
			MainProcess process = new MainProcess();
			for (String str : args) {
				String[] temp = str.split("-");
				context.argsMap.put(temp[0], temp[1]);
			}
			process.initAndStart();
		} catch (Exception e) {
			logger.error("应用启动失败!", e);
		}
	}
}
