package com.boomhope.tms.message.in.bck;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 
 * Title:唐豆收回查询
 * Description:
 * @author mouchunyue
 * @date 2016年9月12日 下午3:23:56
 */
@XStreamAlias("Body")
public class BCK0005ReqBodyBean {

	private String AcctNo;//账号
	private String SubAcctNo;//子账号
	private String PayDate;//日期
	private String PayJrnl;//流水号
	
	
	public String getAcctNo() {
		return AcctNo;
	}
	public void setAcctNo(String acctNo) {
		AcctNo = acctNo;
	}
	public String getSubAcctNo() {
		return SubAcctNo;
	}
	public void setSubAcctNo(String subAcctNo) {
		SubAcctNo = subAcctNo;
	}
	public String getPayDate() {
		return PayDate;
	}
	public void setPayDate(String payDate) {
		PayDate = payDate;
	}
	public String getPayJrnl() {
		return PayJrnl;
	}
	public void setPayJrnl(String payJrnl) {
		PayJrnl = payJrnl;
	}
	
}
