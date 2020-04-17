package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 * 个人IC卡验证-前置07602
 * @author hk
 *
 */
@XStreamAlias("BODY")  
public class Out07602ReqBodyBean{
	
	private String IC_CHL;	//渠道号
	private String CARD_NO;	//卡号
	private String AID;	//AID
	private String ARQC;	//ARQC
	private String ARQC_SRC_DATA;	//ARQC生成数据
	private String TERM_CHK_VALUE;	//终端验证结果
	private String APP_ACCT_SEQ;	//应用主账户序列号
	private String ISS_APP_DATA;	//发卡行应用数据
	private String CARD_POV;	//卡片有效期
	
	public String getIC_CHL() {
		return IC_CHL;
	}
	public void setIC_CHL(String iC_CHL) {
		IC_CHL = iC_CHL;
	}
	public String getCARD_NO() {
		return CARD_NO;
	}
	public void setCARD_NO(String cARD_NO) {
		CARD_NO = cARD_NO;
	}
	public String getAID() {
		return AID;
	}
	public void setAID(String aID) {
		AID = aID;
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
	public String getTERM_CHK_VALUE() {
		return TERM_CHK_VALUE;
	}
	public void setTERM_CHK_VALUE(String tERM_CHK_VALUE) {
		TERM_CHK_VALUE = tERM_CHK_VALUE;
	}
	public String getAPP_ACCT_SEQ() {
		return APP_ACCT_SEQ;
	}
	public void setAPP_ACCT_SEQ(String aPP_ACCT_SEQ) {
		APP_ACCT_SEQ = aPP_ACCT_SEQ;
	}
	public String getISS_APP_DATA() {
		return ISS_APP_DATA;
	}
	public void setISS_APP_DATA(String iSS_APP_DATA) {
		ISS_APP_DATA = iSS_APP_DATA;
	}
	public String getCARD_POV() {
		return CARD_POV;
	}
	public void setCARD_POV(String cARD_POV) {
		CARD_POV = cARD_POV;
	}
	
}
