package com.boomhope.tms.message.in.bck;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 客户基本信息查询 04422
 * @author hao
 *
 */
@XStreamAlias("Body")
public class BCK04422ReqBodyBean {
	private String RESOLVE_TYPE;//识别方式 必输。查询类别码，1-按客户编号查询，2-按三要素查询，3-按帐号查询
	private String ECIF_CUST_NO;//客户编号 客户编号（ECIF客户号），如果输入了客户号进行客户识别，客户三要素，账户类型，客户识别帐号不能输入
	private String PARTY_NAME;//客户姓名 客户名称，如果输入了客户名称，证件类型，证件号码也必须输入进行客户识别，客户编号、客户识别帐号、帐号类型不能输入
	private String CERT_TYPE;//证件类型 证件类型，如果输入了证件类型，证件号码，客户名称也必须输入进行客户识别，客户编号、客户识别帐号、帐号类型不能输入
	private String CERT_NO;//证件号码 证件号码，如果输入了证件号码，证件类型，客户名称也必须输入进行客户识别，客户编号、客户识别帐号、帐号类型不能输入
	private String ACCT_NO;//客户识别账号 查询类别码为3时，必输。客户三要素和客户编号不能输入
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
