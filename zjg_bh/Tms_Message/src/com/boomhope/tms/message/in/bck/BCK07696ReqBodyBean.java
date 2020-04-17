package com.boomhope.tms.message.in.bck;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * Title:普通定期存单预计利息
 * Description:
 * @author zjg
 * @date 2017年07月04日 下午2:21:44
 */
@XStreamAlias("Body")
public class BCK07696ReqBodyBean {

	private String AcctNO;

	public String getAcctNO() {
		return AcctNO;
	}

	public void setAcctNO(String acctNO) {
		AcctNO = acctNO;
	}
	
	
}
