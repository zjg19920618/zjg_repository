package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * @Description: 电子账户 子账户销户【35104】-前置03522
 * @author zjg
 * @date 2016年11月7日 上午10:30:47
 */
@XStreamAlias("BODY")
public class Out03522ReqBodyBean {
	private String TRAN_CHANNEL;//渠道号
	private String CARD_NO;//卡号
	private String SUB_ACCT_NO;//子账户
	private String CAL_MODE;//结算方式
	private String CASH_ANALY_NO;//现金分析号
	private String OPPO_ACCT_NO;//对方账号
	private String PASSWORD;//密码
	private String ID_TYPE;//证件类型
	private String ID_NO;//证件号码
	private String HAV_AGENT_FLAG;//代理人标志
	private Out03522ReqAgentInfBean AGENT_INF;//代理人信息
	private String PIN_VAL_FLAG ;//验密标志
	private String CERT_NO ;//凭证号
	public String getTRAN_CHANNEL() {
		return TRAN_CHANNEL;
	}
	public void setTRAN_CHANNEL(String tRAN_CHANNEL) {
		TRAN_CHANNEL = tRAN_CHANNEL;
	}
	public String getCARD_NO() {
		return CARD_NO;
	}
	public void setCARD_NO(String cARD_NO) {
		CARD_NO = cARD_NO;
	}
	public String getSUB_ACCT_NO() {
		return SUB_ACCT_NO;
	}
	public void setSUB_ACCT_NO(String sUB_ACCT_NO) {
		SUB_ACCT_NO = sUB_ACCT_NO;
	}
	public String getCAL_MODE() {
		return CAL_MODE;
	}
	public void setCAL_MODE(String cAL_MODE) {
		CAL_MODE = cAL_MODE;
	}
	public String getCASH_ANALY_NO() {
		return CASH_ANALY_NO;
	}
	public void setCASH_ANALY_NO(String cASH_ANALY_NO) {
		CASH_ANALY_NO = cASH_ANALY_NO;
	}
	public String getOPPO_ACCT_NO() {
		return OPPO_ACCT_NO;
	}
	public void setOPPO_ACCT_NO(String oPPO_ACCT_NO) {
		OPPO_ACCT_NO = oPPO_ACCT_NO;
	}
	public String getPASSWORD() {
		return PASSWORD;
	}
	public void setPASSWORD(String pASSWORD) {
		PASSWORD = pASSWORD;
	}
	public String getID_TYPE() {
		return ID_TYPE;
	}
	public void setID_TYPE(String iD_TYPE) {
		ID_TYPE = iD_TYPE;
	}
	public String getID_NO() {
		return ID_NO;
	}
	public void setID_NO(String iD_NO) {
		ID_NO = iD_NO;
	}
	public String getHAV_AGENT_FLAG() {
		return HAV_AGENT_FLAG;
	}
	public void setHAV_AGENT_FLAG(String hAV_AGENT_FLAG) {
		HAV_AGENT_FLAG = hAV_AGENT_FLAG;
	}
	
	public Out03522ReqAgentInfBean getAGENT_INF() {
		return AGENT_INF;
	}
	public void setAGENT_INF(Out03522ReqAgentInfBean aGENT_INF) {
		AGENT_INF = aGENT_INF;
	}
	public String getPIN_VAL_FLAG() {
		return PIN_VAL_FLAG;
	}
	public void setPIN_VAL_FLAG(String pIN_VAL_FLAG) {
		PIN_VAL_FLAG = pIN_VAL_FLAG;
	}
	public String getCERT_NO() {
		return CERT_NO;
	}
	public void setCERT_NO(String cERT_NO) {
		CERT_NO = cERT_NO;
	}
	
}
