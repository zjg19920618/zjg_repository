package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 
 * Title:个人账户销户
 * Description:
 * @author mouchunyue
 * @date 2016年9月9日 下午4:32:27
 */
@XStreamAlias("BODY")
public class Out02026ReqBodyBean {
	private String ACCT_NO;//账号
	private String CERT_NO;//凭证号
	private String CUST_NO;//客户号
	private String CUST_NAME;//客户名称
	private String CURRENCY_TYPE;//币种	
	private String DRAW_COND;//支付条件
	private String PASSWORD;//密码
	private String CURRENCY;//货币号
	private String PROD_CODE;//产品代码
	private String PROD_NAME;//产品名称
	private String BALANCE;//余额
	private String DEP_TERM;//存期
	private String START_INT_DATE;//起息日
	private String OPEN_RATE;//开户利率
	private String CYC_AMT;//周期金额
	private String CYC;//周期
	private String TIMES;//次数
	private String BES_AMT;//预约金额
	private String BES_DATE;//预约日期
	private String DRAW_CURRENCY;//支取币种
	private String PRI_INTE_FLAG;//本息分开
	private String CANCEL_AMT;//销户金额
	private String PRI_INTE_WAY;//本息走向
	private String OPPO_ACCT_NO;//对方账号
	private String SUB_ACCT_NO;//子账号
	private String PRI_WAY;//本金走向
	private String OPPO_ACCT_NO1;//对方账号
	private String SUB_ACCT_NO1;//子账号
	private String INTE_WAY;//利息走向
	private String OPPO_ACCT_NO2;//对方账号
	private String SUB_ACCT_NO2;//子账号
	private String ID_TYPE;//证件类型
	private String ID_NO;//证件号码
	private String PROVE_FLAG;//证明标志
	private String CASH_ANALY_NO;//现金分析号
	private String HAV_AGENT_FLAG;//是否有代理人标志
	private Out02026ReqAgentInfBean AGENT_INF;//代理人信息
	
	public String getCURRENCY_TYPE() {
		return CURRENCY_TYPE;
	}
	public void setCURRENCY_TYPE(String cURRENCY_TYPE) {
		CURRENCY_TYPE = cURRENCY_TYPE;
	}
	public String getACCT_NO() {
		return ACCT_NO;
	}
	public void setACCT_NO(String aCCT_NO) {
		ACCT_NO = aCCT_NO;
	}
	public String getCERT_NO() {
		return CERT_NO;
	}
	public void setCERT_NO(String cERT_NO) {
		CERT_NO = cERT_NO;
	}
	public String getCUST_NO() {
		return CUST_NO;
	}
	public void setCUST_NO(String cUST_NO) {
		CUST_NO = cUST_NO;
	}
	public String getCUST_NAME() {
		return CUST_NAME;
	}
	public void setCUST_NAME(String cUST_NAME) {
		CUST_NAME = cUST_NAME;
	}
	public String getDRAW_COND() {
		return DRAW_COND;
	}
	public void setDRAW_COND(String dRAW_COND) {
		DRAW_COND = dRAW_COND;
	}
	public String getPASSWORD() {
		return PASSWORD;
	}
	public void setPASSWORD(String pASSWORD) {
		PASSWORD = pASSWORD;
	}
	public String getCURRENCY() {
		return CURRENCY;
	}
	public void setCURRENCY(String cURRENCY) {
		CURRENCY = cURRENCY;
	}
	public String getPROD_CODE() {
		return PROD_CODE;
	}
	public void setPROD_CODE(String pROD_CODE) {
		PROD_CODE = pROD_CODE;
	}
	public String getPROD_NAME() {
		return PROD_NAME;
	}
	public void setPROD_NAME(String pROD_NAME) {
		PROD_NAME = pROD_NAME;
	}
	public String getBALANCE() {
		return BALANCE;
	}
	public void setBALANCE(String bALANCE) {
		BALANCE = bALANCE;
	}
	public String getDEP_TERM() {
		return DEP_TERM;
	}
	public void setDEP_TERM(String dEP_TERM) {
		DEP_TERM = dEP_TERM;
	}
	public String getSTART_INT_DATE() {
		return START_INT_DATE;
	}
	public void setSTART_INT_DATE(String sTART_INT_DATE) {
		START_INT_DATE = sTART_INT_DATE;
	}
	public String getOPEN_RATE() {
		return OPEN_RATE;
	}
	public void setOPEN_RATE(String oPEN_RATE) {
		OPEN_RATE = oPEN_RATE;
	}
	public String getCYC_AMT() {
		return CYC_AMT;
	}
	public void setCYC_AMT(String cYC_AMT) {
		CYC_AMT = cYC_AMT;
	}
	public String getCYC() {
		return CYC;
	}
	public void setCYC(String cYC) {
		CYC = cYC;
	}
	public String getTIMES() {
		return TIMES;
	}
	public void setTIMES(String tIMES) {
		TIMES = tIMES;
	}
	public String getBES_AMT() {
		return BES_AMT;
	}
	public void setBES_AMT(String bES_AMT) {
		BES_AMT = bES_AMT;
	}
	public String getBES_DATE() {
		return BES_DATE;
	}
	public void setBES_DATE(String bES_DATE) {
		BES_DATE = bES_DATE;
	}
	public String getDRAW_CURRENCY() {
		return DRAW_CURRENCY;
	}
	public void setDRAW_CURRENCY(String dRAW_CURRENCY) {
		DRAW_CURRENCY = dRAW_CURRENCY;
	}
	public String getPRI_INTE_FLAG() {
		return PRI_INTE_FLAG;
	}
	public void setPRI_INTE_FLAG(String pRI_INTE_FLAG) {
		PRI_INTE_FLAG = pRI_INTE_FLAG;
	}
	public String getCANCEL_AMT() {
		return CANCEL_AMT;
	}
	public void setCANCEL_AMT(String cANCEL_AMT) {
		CANCEL_AMT = cANCEL_AMT;
	}
	public String getPRI_INTE_WAY() {
		return PRI_INTE_WAY;
	}
	public void setPRI_INTE_WAY(String pRI_INTE_WAY) {
		PRI_INTE_WAY = pRI_INTE_WAY;
	}
	public String getOPPO_ACCT_NO() {
		return OPPO_ACCT_NO;
	}
	public void setOPPO_ACCT_NO(String oPPO_ACCT_NO) {
		OPPO_ACCT_NO = oPPO_ACCT_NO;
	}
	public String getSUB_ACCT_NO() {
		return SUB_ACCT_NO;
	}
	public void setSUB_ACCT_NO(String sUB_ACCT_NO) {
		SUB_ACCT_NO = sUB_ACCT_NO;
	}
	public String getPRI_WAY() {
		return PRI_WAY;
	}
	public void setPRI_WAY(String pRI_WAY) {
		PRI_WAY = pRI_WAY;
	}
	public String getOPPO_ACCT_NO1() {
		return OPPO_ACCT_NO1;
	}
	public void setOPPO_ACCT_NO1(String oPPO_ACCT_NO1) {
		OPPO_ACCT_NO1 = oPPO_ACCT_NO1;
	}
	public String getSUB_ACCT_NO1() {
		return SUB_ACCT_NO1;
	}
	public void setSUB_ACCT_NO1(String sUB_ACCT_NO1) {
		SUB_ACCT_NO1 = sUB_ACCT_NO1;
	}
	public String getINTE_WAY() {
		return INTE_WAY;
	}
	public void setINTE_WAY(String iNTE_WAY) {
		INTE_WAY = iNTE_WAY;
	}
	public String getOPPO_ACCT_NO2() {
		return OPPO_ACCT_NO2;
	}
	public void setOPPO_ACCT_NO2(String oPPO_ACCT_NO2) {
		OPPO_ACCT_NO2 = oPPO_ACCT_NO2;
	}
	public String getSUB_ACCT_NO2() {
		return SUB_ACCT_NO2;
	}
	public void setSUB_ACCT_NO2(String sUB_ACCT_NO2) {
		SUB_ACCT_NO2 = sUB_ACCT_NO2;
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
	
	public Out02026ReqAgentInfBean getAGENT_INF() {
		return AGENT_INF;
	}
	public void setAGENT_INF(Out02026ReqAgentInfBean aGENT_INF) {
		AGENT_INF = aGENT_INF;
	}
	public String getPROVE_FLAG() {
		return PROVE_FLAG;
	}
	public void setPROVE_FLAG(String pROVE_FLAG) {
		PROVE_FLAG = pROVE_FLAG;
	}
	public String getCASH_ANALY_NO() {
		return CASH_ANALY_NO;
	}
	public void setCASH_ANALY_NO(String cASH_ANALY_NO) {
		CASH_ANALY_NO = cASH_ANALY_NO;
	}
	public String getHAV_AGENT_FLAG() {
		return HAV_AGENT_FLAG;
	}
	public void setHAV_AGENT_FLAG(String hAV_AGENT_FLAG) {
		HAV_AGENT_FLAG = hAV_AGENT_FLAG;
	}
	
	
}
