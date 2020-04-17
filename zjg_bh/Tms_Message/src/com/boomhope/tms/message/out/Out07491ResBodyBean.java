package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * Title:客户手机绑定信息查询【前置-07491】
 * Description:前置返回
 * @author zhang.m
 * @date 2016年10月20日 下午14:45:13
 */
@XStreamAlias("BODY")
public class Out07491ResBodyBean {
	private String STAT;//状态
	private String TEL_NO;//手机号
	public String getSTAT() {
		return STAT;
	}
	public void setSTAT(String sTAT) {
		STAT = sTAT;
	}
	public String getTEL_NO() {
		return TEL_NO;
	}
	public void setTEL_NO(String tEL_NO) {
		TEL_NO = tEL_NO;
	}
}
