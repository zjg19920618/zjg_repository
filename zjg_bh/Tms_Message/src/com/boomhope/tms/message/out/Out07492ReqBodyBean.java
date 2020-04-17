package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 转账客户列表信息查询-前置07492
 * BCK_07492请求报文体Bean
 * @author wang.sk
 */
@XStreamAlias("BODY")
public class Out07492ReqBodyBean {
	private String ACCT_NO;	//付款卡号
	private String ACCT_NAME; //付款人姓名
	private String QRY_CHNL;	//查询渠道
	public String getACCT_NO() {
		return ACCT_NO;
	}
	public void setACCT_NO(String aCCT_NO) {
		ACCT_NO = aCCT_NO;
	}
	public String getACCT_NAME() {
		return ACCT_NAME;
	}
	public void setACCT_NAME(String aCCT_NAME) {
		ACCT_NAME = aCCT_NAME;
	}
	public String getQRY_CHNL() {
		return QRY_CHNL;
	}
	public void setQRY_CHNL(String qRY_CHNL) {
		QRY_CHNL = qRY_CHNL;
	}
	
}
