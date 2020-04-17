package com.boomhope.tms.message.account.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/** 
* @ClassName  Account03870ReqBodyBean 
* @Description "积享存"个人存款开户【75100】前置【03870】   
* @author zhang.m 
* @date 2016年12月5日 下午12:06:37  
*/
@XStreamAlias("BODY")
public class Account03870ReqBodyBean {
	private String TRAN_CHANNEL;//渠道号
	private String CARD_NO;//卡号
	private String ID_TYPE;//证件代号
	private String ID_NO;//证件号码
	private String CUST_NO;//客户号
	private String CURRENCY;//币种
	private String PRO_CODE;//产品代码
	private String DEP_TERM;//存期
	private String DEP_UNIT;//存期单位
	private String PASSWORD;//密码
	private String CAL_MODE;//结算方式
	private String CASH_ANALY_NO;//现金分析号
	private String OPPO_ACCT_NO;//对方账号
	private String AUTO_REDP_FLAG;//自动转存标志
	private String LOAD_BAL;//存款金额
	private String HAV_AGENT_FLAG;//是否有代理人标志
	private Account03870ReqAgentInfBean AGENT_INF;//代理人信息
	private String RELA_ACCT_NO;//关联账号、收益账号
	private String SUB_RELA_ACCT_NO;//关联子账号
	private String CERT_PRINT;//是否打印存单
	private String CHL_ID;//渠道模块标识
	private String CUST_LEVEL;//客户评级
	
	
	public String getCERT_PRINT() {
		return CERT_PRINT;
	}
	public void setCERT_PRINT(String cERT_PRINT) {
		CERT_PRINT = cERT_PRINT;
	}
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
	public String getCUST_NO() {
		return CUST_NO;
	}
	public void setCUST_NO(String cUST_NO) {
		CUST_NO = cUST_NO;
	}
	public String getCURRENCY() {
		return CURRENCY;
	}
	public void setCURRENCY(String cURRENCY) {
		CURRENCY = cURRENCY;
	}
	public String getPRO_CODE() {
		return PRO_CODE;
	}
	public void setPRO_CODE(String pRO_CODE) {
		PRO_CODE = pRO_CODE;
	}
	public String getDEP_TERM() {
		return DEP_TERM;
	}
	public void setDEP_TERM(String dEP_TERM) {
		DEP_TERM = dEP_TERM;
	}
	public String getDEP_UNIT() {
		return DEP_UNIT;
	}
	public void setDEP_UNIT(String dEP_UNIT) {
		DEP_UNIT = dEP_UNIT;
	}
	public String getPASSWORD() {
		return PASSWORD;
	}
	public void setPASSWORD(String pASSWORD) {
		PASSWORD = pASSWORD;
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
	public String getAUTO_REDP_FLAG() {
		return AUTO_REDP_FLAG;
	}
	public void setAUTO_REDP_FLAG(String aUTO_REDP_FLAG) {
		AUTO_REDP_FLAG = aUTO_REDP_FLAG;
	}
	public String getLOAD_BAL() {
		return LOAD_BAL;
	}
	public void setLOAD_BAL(String lOAD_BAL) {
		LOAD_BAL = lOAD_BAL;
	}
	public String getHAV_AGENT_FLAG() {
		return HAV_AGENT_FLAG;
	}
	public void setHAV_AGENT_FLAG(String hAV_AGENT_FLAG) {
		HAV_AGENT_FLAG = hAV_AGENT_FLAG;
	}
	public String getRELA_ACCT_NO() {
		return RELA_ACCT_NO;
	}
	public void setRELA_ACCT_NO(String rELA_ACCT_NO) {
		RELA_ACCT_NO = rELA_ACCT_NO;
	}
	public String getSUB_RELA_ACCT_NO() {
		return SUB_RELA_ACCT_NO;
	}
	public void setSUB_RELA_ACCT_NO(String sUB_RELA_ACCT_NO) {
		SUB_RELA_ACCT_NO = sUB_RELA_ACCT_NO;
	}
	public Account03870ReqAgentInfBean getAGENT_INF() {
		return AGENT_INF;
	}
	public void setAGENT_INF(Account03870ReqAgentInfBean aGENT_INF) {
		AGENT_INF = aGENT_INF;
	}
	public String getCHL_ID() {
		return CHL_ID;
	}
	public void setCHL_ID(String cHL_ID) {
		CHL_ID = cHL_ID;
	}
	public String getCUST_LEVEL() {
		return CUST_LEVEL;
	}
	public void setCUST_LEVEL(String cUST_LEVEL) {
		CUST_LEVEL = cUST_LEVEL;
	}
	
}
