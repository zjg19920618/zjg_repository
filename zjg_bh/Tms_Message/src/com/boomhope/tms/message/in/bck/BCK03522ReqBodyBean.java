package com.boomhope.tms.message.in.bck;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * @Description: 电子账户 子账户销户【35104】-前置03522
 * @author zjg
 * @date 2017年07月04日 上午10:30:47
 */
@XStreamAlias("Body")
public class BCK03522ReqBodyBean {
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
	private BCK03522ReqAgentInfBean AGENT_INF;//代理人信息
	private String PIN_VAL_FLAG ;//验密标志
	private String CERT_NO ;//凭证号、
	
	/*以下信息不上送前置*/
	private String FLOW_ID;//业务流水ID
	private String MACHINE_NO;//终端编号
	private String UNIT_CODE;//机构代码
	private String CANEL_TYPE;//销户类型
	private String OPPO_NAME;//姓名
	private String CHECKSTATUS;//标记存单是否自动识别，1自动识别，2手动输入
	
	private String ACCT_NO;//账号-子账号
	private String AMOUNT;//本金
	private String CARDNO;//银行卡号
	private String PRONAME;//产品名称
	private String PROCODE;//产品编号
	private String CARDNAME;//姓名
	
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
	
	public BCK03522ReqAgentInfBean getAGENT_INF() {
		return AGENT_INF;
	}
	public void setAGENT_INF(BCK03522ReqAgentInfBean aGENT_INF) {
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
	public String getFLOW_ID() {
		return FLOW_ID;
	}
	public void setFLOW_ID(String fLOW_ID) {
		FLOW_ID = fLOW_ID;
	}
	public String getMACHINE_NO() {
		return MACHINE_NO;
	}
	public void setMACHINE_NO(String mACHINE_NO) {
		MACHINE_NO = mACHINE_NO;
	}
	public String getUNIT_CODE() {
		return UNIT_CODE;
	}
	public void setUNIT_CODE(String uNIT_CODE) {
		UNIT_CODE = uNIT_CODE;
	}
	public String getCANEL_TYPE() {
		return CANEL_TYPE;
	}
	public void setCANEL_TYPE(String cANEL_TYPE) {
		CANEL_TYPE = cANEL_TYPE;
	}
	public String getOPPO_NAME() {
		return OPPO_NAME;
	}
	public void setOPPO_NAME(String oPPO_NAME) {
		OPPO_NAME = oPPO_NAME;
	}
	public String getCHECKSTATUS() {
		return CHECKSTATUS;
	}
	public void setCHECKSTATUS(String cHECKSTATUS) {
		CHECKSTATUS = cHECKSTATUS;
	}
	public String getACCT_NO() {
		return ACCT_NO;
	}
	public void setACCT_NO(String aCCT_NO) {
		ACCT_NO = aCCT_NO;
	}
	public String getAMOUNT() {
		return AMOUNT;
	}
	public void setAMOUNT(String aMOUNT) {
		AMOUNT = aMOUNT;
	}
	public String getCARDNO() {
		return CARDNO;
	}
	public void setCARDNO(String cARDNO) {
		CARDNO = cARDNO;
	}
	public String getPRONAME() {
		return PRONAME;
	}
	public void setPRONAME(String pRONAME) {
		PRONAME = pRONAME;
	}
	public String getPROCODE() {
		return PROCODE;
	}
	public void setPROCODE(String pROCODE) {
		PROCODE = pROCODE;
	}
	public String getCARDNAME() {
		return CARDNAME;
	}
	public void setCARDNAME(String cARDNAME) {
		CARDNAME = cARDNAME;
	}
	
}
