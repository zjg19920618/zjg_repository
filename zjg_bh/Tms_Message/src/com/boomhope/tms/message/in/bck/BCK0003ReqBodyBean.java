package com.boomhope.tms.message.in.bck;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * Title:普通定期存单预计利息
 * Description:
 * @author mouchunyue
 * @date 2016年9月12日 下午2:16:10
 */
@XStreamAlias("Body")
public class BCK0003ReqBodyBean {

	private String AcctNO;

	public String getAcctNO() {
		return AcctNO;
	}

	public void setAcctNO(String acctNO) {
		AcctNO = acctNO;
	}
	
	
}
