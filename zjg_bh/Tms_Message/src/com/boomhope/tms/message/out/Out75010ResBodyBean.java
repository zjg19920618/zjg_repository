package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 
 * Title:卡信息查询
 * Description:
 * @author mouchunyue
 * @date 2016年9月9日 下午6:13:02
 */
@XStreamAlias("BODY")
public class Out75010ResBodyBean {

	private String SVR_DATE;//核心日期
	private String SVR_JRNL;//核心流水号
	private String ITEM_NO;//科目号
	private String CARD_NO;//卡号
	private String CUST_NO;//客户号
	private String PRO_CODE;//产品代码 
	private String CURREN;//币种
	private String OPEN_INST;//开户机构
	private String OPEN_DATE;//开户日
	private String START_DATE;//起息日
	private String END_DATE;//到期日
	private String PAY_COND;//支付条件
	private String OPEN_TELLER;//开户柜员
	private String BALANCE;//结存额（主账户余额）
	private String PURSE;//钱包余额
	private String INTE_COLLECT;//利息积数
	private String STOP_AMT;//止付金额
	private String AVAL_BAL;//可用余额
	private String ACCT_STAT;//账号状态
	private String CARD_STAT;//卡状态
	private String COLD_AMT;//冻结金额
	private String ID_TYPE;//证件类型
	private String ID_NO;//证件号码
	private String ACCT_NAME;//户名
	private String CLEAN_TELLER;//清户柜员号
	private String CLEAN_INST;//清户机构
	private String CLEAN_RES;//清户原因
	private String ID_INST;//发证机构
	private String ADD_AMT;//现金累计金额
	private String ZZ_AMT;//转账累计金额
	private String CK_TOTAL_AMT;//存款累计金额
	private String OPEN_FLAG;//约定转存标志
	private String SUM_BALANCE;//总结存额
	private String DEP_TERM;//存期
	private String OPEN_RATE;//开户利率
	private String BIND_ID;//绑定账户
	private String PRO_NAME;//产品名称
	private String LAST_OPE_DTE;//最近存取日期
	public String getSVR_DATE() {
		return SVR_DATE;
	}
	public void setSVR_DATE(String sVR_DATE) {
		SVR_DATE = sVR_DATE;
	}
	public String getSVR_JRNL() {
		return SVR_JRNL;
	}
	public void setSVR_JRNL(String sVR_JRNL) {
		SVR_JRNL = sVR_JRNL;
	}
	public String getITEM_NO() {
		return ITEM_NO;
	}
	public void setITEM_NO(String iTEM_NO) {
		ITEM_NO = iTEM_NO;
	}
	public String getCARD_NO() {
		return CARD_NO;
	}
	public void setCARD_NO(String cARD_NO) {
		CARD_NO = cARD_NO;
	}
	public String getCUST_NO() {
		return CUST_NO;
	}
	public void setCUST_NO(String cUST_NO) {
		CUST_NO = cUST_NO;
	}
	public String getPRO_CODE() {
		return PRO_CODE;
	}
	public void setPRO_CODE(String pRO_CODE) {
		PRO_CODE = pRO_CODE;
	}
	public String getCURREN() {
		return CURREN;
	}
	public void setCURREN(String cURREN) {
		CURREN = cURREN;
	}
	public String getOPEN_INST() {
		return OPEN_INST;
	}
	public void setOPEN_INST(String oPEN_INST) {
		OPEN_INST = oPEN_INST;
	}
	public String getOPEN_DATE() {
		return OPEN_DATE;
	}
	public void setOPEN_DATE(String oPEN_DATE) {
		OPEN_DATE = oPEN_DATE;
	}
	public String getSTART_DATE() {
		return START_DATE;
	}
	public void setSTART_DATE(String sTART_DATE) {
		START_DATE = sTART_DATE;
	}
	public String getEND_DATE() {
		return END_DATE;
	}
	public void setEND_DATE(String eND_DATE) {
		END_DATE = eND_DATE;
	}
	public String getPAY_COND() {
		return PAY_COND;
	}
	public void setPAY_COND(String pAY_COND) {
		PAY_COND = pAY_COND;
	}
	public String getOPEN_TELLER() {
		return OPEN_TELLER;
	}
	public void setOPEN_TELLER(String oPEN_TELLER) {
		OPEN_TELLER = oPEN_TELLER;
	}
	public String getBALANCE() {
		return BALANCE;
	}
	public void setBALANCE(String bALANCE) {
		BALANCE = bALANCE;
	}
	public String getPURSE() {
		return PURSE;
	}
	public void setPURSE(String pURSE) {
		PURSE = pURSE;
	}
	public String getINTE_COLLECT() {
		return INTE_COLLECT;
	}
	public void setINTE_COLLECT(String iNTE_COLLECT) {
		INTE_COLLECT = iNTE_COLLECT;
	}
	public String getSTOP_AMT() {
		return STOP_AMT;
	}
	public void setSTOP_AMT(String sTOP_AMT) {
		STOP_AMT = sTOP_AMT;
	}
	public String getAVAL_BAL() {
		return AVAL_BAL;
	}
	public void setAVAL_BAL(String aVAL_BAL) {
		AVAL_BAL = aVAL_BAL;
	}
	public String getACCT_STAT() {
		return ACCT_STAT;
	}
	public void setACCT_STAT(String aCCT_STAT) {
		ACCT_STAT = aCCT_STAT;
	}
	public String getCARD_STAT() {
		return CARD_STAT;
	}
	public void setCARD_STAT(String cARD_STAT) {
		CARD_STAT = cARD_STAT;
	}
	public String getCOLD_AMT() {
		return COLD_AMT;
	}
	public void setCOLD_AMT(String cOLD_AMT) {
		COLD_AMT = cOLD_AMT;
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
	public String getACCT_NAME() {
		return ACCT_NAME;
	}
	public void setACCT_NAME(String aCCT_NAME) {
		ACCT_NAME = aCCT_NAME;
	}
	public String getCLEAN_TELLER() {
		return CLEAN_TELLER;
	}
	public void setCLEAN_TELLER(String cLEAN_TELLER) {
		CLEAN_TELLER = cLEAN_TELLER;
	}
	public String getCLEAN_INST() {
		return CLEAN_INST;
	}
	public void setCLEAN_INST(String cLEAN_INST) {
		CLEAN_INST = cLEAN_INST;
	}
	public String getCLEAN_RES() {
		return CLEAN_RES;
	}
	public void setCLEAN_RES(String cLEAN_RES) {
		CLEAN_RES = cLEAN_RES;
	}
	public String getID_INST() {
		return ID_INST;
	}
	public void setID_INST(String iD_INST) {
		ID_INST = iD_INST;
	}
	public String getADD_AMT() {
		return ADD_AMT;
	}
	public void setADD_AMT(String aDD_AMT) {
		ADD_AMT = aDD_AMT;
	}
	public String getZZ_AMT() {
		return ZZ_AMT;
	}
	public void setZZ_AMT(String zZ_AMT) {
		ZZ_AMT = zZ_AMT;
	}
	public String getCK_TOTAL_AMT() {
		return CK_TOTAL_AMT;
	}
	public void setCK_TOTAL_AMT(String cK_TOTAL_AMT) {
		CK_TOTAL_AMT = cK_TOTAL_AMT;
	}
	public String getOPEN_FLAG() {
		return OPEN_FLAG;
	}
	public void setOPEN_FLAG(String oPEN_FLAG) {
		OPEN_FLAG = oPEN_FLAG;
	}
	public String getSUM_BALANCE() {
		return SUM_BALANCE;
	}
	public void setSUM_BALANCE(String sUM_BALANCE) {
		SUM_BALANCE = sUM_BALANCE;
	}
	public String getDEP_TERM() {
		return DEP_TERM;
	}
	public void setDEP_TERM(String dEP_TERM) {
		DEP_TERM = dEP_TERM;
	}
	public String getOPEN_RATE() {
		return OPEN_RATE;
	}
	public void setOPEN_RATE(String oPEN_RATE) {
		OPEN_RATE = oPEN_RATE;
	}
	public String getBIND_ID() {
		return BIND_ID;
	}
	public void setBIND_ID(String bIND_ID) {
		BIND_ID = bIND_ID;
	}
	public String getPRO_NAME() {
		return PRO_NAME;
	}
	public void setPRO_NAME(String pRO_NAME) {
		PRO_NAME = pRO_NAME;
	}
	public String getLAST_OPE_DTE() {
		return LAST_OPE_DTE;
	}
	public void setLAST_OPE_DTE(String lAST_OPE_DTE) {
		LAST_OPE_DTE = lAST_OPE_DTE;
	}
	
}
