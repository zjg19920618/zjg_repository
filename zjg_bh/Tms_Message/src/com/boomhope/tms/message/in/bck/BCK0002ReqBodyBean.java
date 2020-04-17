package com.boomhope.tms.message.in.bck;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 
 * Title:账号信息综合查询BODY
 * Description:
 * @author mouchunyue
 * @date 2016年9月12日 下午4:31:24
 */
@XStreamAlias("Body")
public class BCK0002ReqBodyBean {

	private String AcctNo;//账号
	private String SubAcctNo;//子账号
	private String ChkPin;//是否验密
	private String Passwd;//密码
	private String CERT_TYPE;//凭证种类
	private String CERT_NO_ADD;//凭证号
	
	public String getCERT_TYPE() {
		return CERT_TYPE;
	}
	public void setCERT_TYPE(String cERT_TYPE) {
		CERT_TYPE = cERT_TYPE;
	}
	public String getCERT_NO_ADD() {
		return CERT_NO_ADD;
	}
	public void setCERT_NO_ADD(String cERT_NO_ADD) {
		CERT_NO_ADD = cERT_NO_ADD;
	}
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
	public String getChkPin() {
		return ChkPin;
	}
	public void setChkPin(String chkPin) {
		ChkPin = chkPin;
	}
	public String getPasswd() {
		return Passwd;
	}
	public void setPasswd(String passwd) {
		Passwd = passwd;
	}
	
}
