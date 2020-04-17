package com.boomhope.tms.message.in.bck;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
*单位卡IC卡验证（核心77102）-前置03453
 * @author hk
 *
 */
@XStreamAlias("Body")  
public class BCK03453ReqBodyBean {
	
	private String CARD_NO;//卡号
	private String PASSWD;//密码
	private String TRACK2_INFO;//二磁信息
	private String FALL_BACK_FLAG;//降级标志
	private String ARQC;//ARQC
	private String ARQC_SRC_DATA;//ARQC生成数据
	private String TERM_RESULT;//终端验证结果
	private String ISSUER_APP_DATA;//发卡行应用数据
	private String ICCARD_DATA;//应用主账户序列号
	private String DATE_EXPR;//卡片有效期
	public String getCARD_NO() {
		return CARD_NO;
	}
	public void setCARD_NO(String cARD_NO) {
		CARD_NO = cARD_NO;
	}
	public String getPASSWD() {
		return PASSWD;
	}
	public void setPASSWD(String pASSWD) {
		PASSWD = pASSWD;
	}
	public String getTRACK2_INFO() {
		return TRACK2_INFO;
	}
	public void setTRACK2_INFO(String tRACK2_INFO) {
		TRACK2_INFO = tRACK2_INFO;
	}
	public String getFALL_BACK_FLAG() {
		return FALL_BACK_FLAG;
	}
	public void setFALL_BACK_FLAG(String fALL_BACK_FLAG) {
		FALL_BACK_FLAG = fALL_BACK_FLAG;
	}
	public String getARQC() {
		return ARQC;
	}
	public void setARQC(String aRQC) {
		ARQC = aRQC;
	}
	public String getARQC_SRC_DATA() {
		return ARQC_SRC_DATA;
	}
	public void setARQC_SRC_DATA(String aRQC_SRC_DATA) {
		ARQC_SRC_DATA = aRQC_SRC_DATA;
	}
	public String getTERM_RESULT() {
		return TERM_RESULT;
	}
	public void setTERM_RESULT(String tERM_RESULT) {
		TERM_RESULT = tERM_RESULT;
	}
	public String getISSUER_APP_DATA() {
		return ISSUER_APP_DATA;
	}
	public void setISSUER_APP_DATA(String iSSUER_APP_DATA) {
		ISSUER_APP_DATA = iSSUER_APP_DATA;
	}
	public String getICCARD_DATA() {
		return ICCARD_DATA;
	}
	public void setICCARD_DATA(String iCCARD_DATA) {
		ICCARD_DATA = iCCARD_DATA;
	}
	public String getDATE_EXPR() {
		return DATE_EXPR;
	}
	public void setDATE_EXPR(String dATE_EXPR) {
		DATE_EXPR = dATE_EXPR;
	}
	
	
	
	
	
}
