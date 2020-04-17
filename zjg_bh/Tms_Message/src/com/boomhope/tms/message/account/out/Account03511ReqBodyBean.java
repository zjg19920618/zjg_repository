package com.boomhope.tms.message.account.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/** 
* @ClassName  Account03511ReqBodyBean 
* @Description  产品可开立额度信息查询-03511 
* @author zhang.m 
* @date 2016年12月5日 上午11:32:39  
*/
@XStreamAlias("BODY")
public class Account03511ReqBodyBean {
	private String OPEN_CHL;//开户渠道
	private String PROD_CODE;//产品代码
	private String CUST_NO;//客户号

	public String getOPEN_CHL() {
		return OPEN_CHL;
	}
	public void setOPEN_CHL(String oPEN_CHL) {
		OPEN_CHL = oPEN_CHL;
	}
	public String getCUST_NO() {
		return CUST_NO;
	}
	public void setCUST_NO(String cUST_NO) {
		CUST_NO = cUST_NO;
	}
	public String getPROD_CODE() {
		return PROD_CODE;
	}
	public void setPROD_CODE(String pROD_CODE) {
		PROD_CODE = pROD_CODE;
	}
}
