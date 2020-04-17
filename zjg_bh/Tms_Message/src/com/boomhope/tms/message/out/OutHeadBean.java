package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 * 报文头Bean
 * @author shaopeng
 *
 */
@XStreamAlias("HEAD")  
public class OutHeadBean {

	private String TRAN_CODE;	// 前置交易码
	private String CARD_ACCP_TERM_ID = ""; // 设备编号
	private String MER_NO = "";	// 商户号
	private String INST_NO = "";	// 机构号
	private String TELLER_NO = "";	// 柜员号
	private String SUP_TELLER_NO = "";	// 授权柜员号
	private String CHANNEL = "";	//  渠道号
	private String CHL_TRAN_CODE = "";	// 渠道交易码
	private String TERM_JRNL_NO = "";	// 终端流水号
	private String TRAN_DATE;	// 交易日期
	private String TRAN_TIME;	// 交易时间
	public String getTRAN_CODE() {
		return TRAN_CODE;
	}
	public void setTRAN_CODE(String tRAN_CODE) {
		TRAN_CODE = tRAN_CODE;
	}
	public String getCARD_ACCP_TERM_ID() {
		return CARD_ACCP_TERM_ID;
	}
	public void setCARD_ACCP_TERM_ID(String cARD_ACCP_TERM_ID) {
		CARD_ACCP_TERM_ID = cARD_ACCP_TERM_ID;
	}
	public String getMER_NO() {
		return MER_NO;
	}
	public void setMER_NO(String mER_NO) {
		MER_NO = mER_NO;
	}
	public String getINST_NO() {
		return INST_NO;
	}
	public void setINST_NO(String iNST_NO) {
		INST_NO = iNST_NO;
	}
	public String getTELLER_NO() {
		return TELLER_NO;
	}
	public void setTELLER_NO(String tELLER_NO) {
		TELLER_NO = tELLER_NO;
	}
	public String getSUP_TELLER_NO() {
		return SUP_TELLER_NO;
	}
	public void setSUP_TELLER_NO(String sUP_TELLER_NO) {
		SUP_TELLER_NO = sUP_TELLER_NO;
	}
	public String getCHANNEL() {
		return CHANNEL;
	}
	public void setCHANNEL(String cHANNEL) {
		CHANNEL = cHANNEL;
	}
	public String getCHL_TRAN_CODE() {
		return CHL_TRAN_CODE;
	}
	public void setCHL_TRAN_CODE(String cHL_TRAN_CODE) {
		CHL_TRAN_CODE = cHL_TRAN_CODE;
	}
	public String getTERM_JRNL_NO() {
		return TERM_JRNL_NO;
	}
	public void setTERM_JRNL_NO(String tERM_JRNL_NO) {
		TERM_JRNL_NO = tERM_JRNL_NO;
	}
	public String getTRAN_DATE() {
		return TRAN_DATE;
	}
	public void setTRAN_DATE(String tRAN_DATE) {
		TRAN_DATE = tRAN_DATE;
	}
	public String getTRAN_TIME() {
		return TRAN_TIME;
	}
	public void setTRAN_TIME(String tRAN_TIME) {
		TRAN_TIME = tRAN_TIME;
	}

}
