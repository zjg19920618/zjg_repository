package com.boomhope.tms.message.in.bck;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 单日累计划转金额查询-前置07494
 * @author zjg
 *
 */
@XStreamAlias("Body")
public class BCK07494ResBodyBean {
	private String SUM_AMT;//总金额

	public String getSUM_AMT() {
		return SUM_AMT;
	}

	public void setSUM_AMT(String sUM_AMT) {
		SUM_AMT = sUM_AMT;
	}
	
}
