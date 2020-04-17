package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 * *机构关系查询交易【20102】--前置01569
 * @author hk
 *
 */
@XStreamAlias("BODY")  
public class Out01569ReqBodyBean{
	
	private String ACCT_NO;//账号
	private String INST_CODE;//机构号
	private String SVR_INST_NO;//交易机构号
	private String CHL_NO;//渠道号
	
	public String getACCT_NO() {
		return ACCT_NO;
	}
	public void setACCT_NO(String aCCT_NO) {
		ACCT_NO = aCCT_NO;
	}
	public String getINST_CODE() {
		return INST_CODE;
	}
	public void setINST_CODE(String iNST_CODE) {
		INST_CODE = iNST_CODE;
	}
	public String getSVR_INST_NO() {
		return SVR_INST_NO;
	}
	public void setSVR_INST_NO(String sVR_INST_NO) {
		SVR_INST_NO = sVR_INST_NO;
	}
	public String getCHL_NO() {
		return CHL_NO;
	}
	public void setCHL_NO(String cHL_NO) {
		CHL_NO = cHL_NO;
	}
	

}
