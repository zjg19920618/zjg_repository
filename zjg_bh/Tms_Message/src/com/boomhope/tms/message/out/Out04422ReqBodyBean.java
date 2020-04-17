package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 客户基本信息查询 04422
 * @author hao
 *
 */
@XStreamAlias("BODY")
public class Out04422ReqBodyBean {
	private String RESOLVE_TYPE;//识别方式
	private String ECIF_CUST_NO;//客户编号
	private String PARTY_NAME;//客户姓名
	private String CERT_TYPE;//证件类型
	private String CERT_NO;//证件号码
	private String ACCT_NO;//客户识别账号
	public String getRESOLVE_TYPE() {
		return RESOLVE_TYPE;
	}
	public void setRESOLVE_TYPE(String rESOLVE_TYPE) {
		RESOLVE_TYPE = rESOLVE_TYPE;
	}
	public String getECIF_CUST_NO() {
		return ECIF_CUST_NO;
	}
	public void setECIF_CUST_NO(String eCIF_CUST_NO) {
		ECIF_CUST_NO = eCIF_CUST_NO;
	}
	public String getPARTY_NAME() {
		return PARTY_NAME;
	}
	public void setPARTY_NAME(String pARTY_NAME) {
		PARTY_NAME = pARTY_NAME;
	}
	public String getCERT_TYPE() {
		return CERT_TYPE;
	}
	public void setCERT_TYPE(String cERT_TYPE) {
		CERT_TYPE = cERT_TYPE;
	}
	public String getCERT_NO() {
		return CERT_NO;
	}
	public void setCERT_NO(String cERT_NO) {
		CERT_NO = cERT_NO;
	}
	public String getACCT_NO() {
		return ACCT_NO;
	}
	public void setACCT_NO(String aCCT_NO) {
		ACCT_NO = aCCT_NO;
	}
}
