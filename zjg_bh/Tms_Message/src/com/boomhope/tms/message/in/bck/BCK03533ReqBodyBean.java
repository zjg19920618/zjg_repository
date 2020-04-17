package com.boomhope.tms.message.in.bck;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *推荐信息录入【17034】前置-03533
 * @author ly
 *
 */
@XStreamAlias("Body")  
public class BCK03533ReqBodyBean {
	
	private String REC_NAME;//推荐人姓名
	private String REC_TEL_NO;//推荐人手机号
	private String NAME;//被推荐人姓名
	private String TEL_NO;//被推荐人手机号
	public String getREC_NAME() {
		return REC_NAME;
	}
	public void setREC_NAME(String rEC_NAME) {
		REC_NAME = rEC_NAME;
	}
	public String getREC_TEL_NO() {
		return REC_TEL_NO;
	}
	public void setREC_TEL_NO(String rEC_TEL_NO) {
		REC_TEL_NO = rEC_TEL_NO;
	}
	public String getNAME() {
		return NAME;
	}
	public void setNAME(String nAME) {
		NAME = nAME;
	}
	public String getTEL_NO() {
		return TEL_NO;
	}
	public void setTEL_NO(String tEL_NO) {
		TEL_NO = tEL_NO;
	}
	
	

	
}
