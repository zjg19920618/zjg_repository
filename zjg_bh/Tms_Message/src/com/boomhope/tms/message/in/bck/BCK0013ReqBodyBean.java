package com.boomhope.tms.message.in.bck;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * Title:单条短信发送 【前置-07190】
 * Description:访问前置
 * @author mouchunyue
 * @date 2016年10月20日 下午14:51:21
 *
 */
@XStreamAlias("Body")
public class BCK0013ReqBodyBean {
	private String TEL_NO;//手机号码
	private String CONTENT;//短信内容
	private String SEND_TIME;//发送时间
	private String MATCH_CODE;//模板编号
	public String getTEL_NO() {
		return TEL_NO;
	}
	public void setTEL_NO(String tEL_NO) {
		TEL_NO = tEL_NO;
	}
	public String getCONTENT() {
		return CONTENT;
	}
	public void setCONTENT(String cONTENT) {
		CONTENT = cONTENT;
	}
	public String getSEND_TIME() {
		return SEND_TIME;
	}
	public void setSEND_TIME(String sEND_TIME) {
		SEND_TIME = sEND_TIME;
	}
	public String getMATCH_CODE() {
		return MATCH_CODE;
	}
	public void setMATCH_CODE(String mATCH_CODE) {
		MATCH_CODE = mATCH_CODE;
	}
}
