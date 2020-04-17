package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 
 * Title:普通定期存单预计利息
 * Description:
 * @author mouchunyue
 * @date 2016年9月9日 下午4:05:21
 */
@XStreamAlias("BODY")
public class Out02944ReqBodyBean {

	private String ACCT_NO;//帐号

	public String getACCT_NO() {
		return ACCT_NO;
	}

	public void setACCT_NO(String aCCT_NO) {
		ACCT_NO = aCCT_NO;
	}
	
	
}
