package com.boomhope.tms.message.account.in;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/** 
* @ClassName  Account03512ReqBodyBean 
* @Description   如意存明细查询【55020】--前置03512
* @author zhang.m 
* @date 2016年12月5日 上午11:56:56  
*/
@XStreamAlias("Body")
public class BCK03512ReqBodyBean {
	private String CustNo;//客户号

	public String getCustNo() {
		return CustNo;
	}

	public void setCustNo(String custNo) {
		CustNo = custNo;
	}

	
}
