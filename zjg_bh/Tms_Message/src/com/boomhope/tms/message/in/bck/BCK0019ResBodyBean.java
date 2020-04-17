package com.boomhope.tms.message.in.bck;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * @Description: 账户信息查询及密码验证-前置03521
 * @author zjg
 * @date 2016年11月7日 上午10:30:47
 */
@XStreamAlias("Body")
public class BCK0019ResBodyBean {
	private String CARD_NO; //账号
	private String INST_NO; //开户机构
	private String CUST_NO; //客户号
	private String CERT_NO; //凭证号
	private String DRAW_COND; //支付条件
	private String DEP_TERM; //存期
	private String TYPE; //类别
	private String ACCT_TYPE; //账户种类
	private String DB_CR_FLAG; //余额方向
	private String D_BAL; //借余额
	private String C_BAL; //贷余额
	private String BALANCE; //余额
	private String STOP_AMT; //止付金额
	private String ACCT_STAT; //账户状态
	private String LOST_STAT; //挂失状态
	private String HOLD_STAT; //冻结状态
	private String STOP_STAT; //止付状态
	private String ACCT_NAME; //名称
	private String SAVE_TYPE; //储种
	private String ADDRESS; //地址
	private String ITEM; //科目
	private String CLEAR_FLAG; //结算户标志
	public String getCARD_NO() {
		return CARD_NO;
	}
	public void setCARD_NO(String cARD_NO) {
		CARD_NO = cARD_NO;
	}
	
	public String getINST_NO() {
		return INST_NO;
	}
	public void setINST_NO(String iNST_NO) {
		INST_NO = iNST_NO;
	}
	public String getCUST_NO() {
		return CUST_NO;
	}
	public void setCUST_NO(String cUST_NO) {
		CUST_NO = cUST_NO;
	}
	public String getCERT_NO() {
		return CERT_NO;
	}
	public void setCERT_NO(String cERT_NO) {
		CERT_NO = cERT_NO;
	}
	public String getDRAW_COND() {
		return DRAW_COND;
	}
	public void setDRAW_COND(String dRAW_COND) {
		DRAW_COND = dRAW_COND;
	}
	public String getDEP_TERM() {
		return DEP_TERM;
	}
	public void setDEP_TERM(String dEP_TERM) {
		DEP_TERM = dEP_TERM;
	}
	public String getTYPE() {
		return TYPE;
	}
	public void setTYPE(String tYPE) {
		TYPE = tYPE;
	}
	public String getACCT_TYPE() {
		return ACCT_TYPE;
	}
	public void setACCT_TYPE(String aCCT_TYPE) {
		ACCT_TYPE = aCCT_TYPE;
	}
	public String getDB_CR_FLAG() {
		return DB_CR_FLAG;
	}
	public void setDB_CR_FLAG(String dB_CR_FLAG) {
		DB_CR_FLAG = dB_CR_FLAG;
	}
	public String getD_BAL() {
		return D_BAL;
	}
	public void setD_BAL(String d_BAL) {
		D_BAL = d_BAL;
	}
	public String getC_BAL() {
		return C_BAL;
	}
	public void setC_BAL(String c_BAL) {
		C_BAL = c_BAL;
	}
	public String getBALANCE() {
		return BALANCE;
	}
	public void setBALANCE(String bALANCE) {
		BALANCE = bALANCE;
	}
	public String getSTOP_AMT() {
		return STOP_AMT;
	}
	public void setSTOP_AMT(String sTOP_AMT) {
		STOP_AMT = sTOP_AMT;
	}
	public String getACCT_STAT() {
		return ACCT_STAT;
	}
	public void setACCT_STAT(String aCCT_STAT) {
		ACCT_STAT = aCCT_STAT;
	}
	public String getLOST_STAT() {
		return LOST_STAT;
	}
	public void setLOST_STAT(String lOST_STAT) {
		LOST_STAT = lOST_STAT;
	}
	public String getHOLD_STAT() {
		return HOLD_STAT;
	}
	public void setHOLD_STAT(String hOLD_STAT) {
		HOLD_STAT = hOLD_STAT;
	}
	public String getSTOP_STAT() {
		return STOP_STAT;
	}
	public void setSTOP_STAT(String sTOP_STAT) {
		STOP_STAT = sTOP_STAT;
	}
	public String getACCT_NAME() {
		return ACCT_NAME;
	}
	public void setACCT_NAME(String aCCT_NAME) {
		ACCT_NAME = aCCT_NAME;
	}
	public String getSAVE_TYPE() {
		return SAVE_TYPE;
	}
	public void setSAVE_TYPE(String sAVE_TYPE) {
		SAVE_TYPE = sAVE_TYPE;
	}
	public String getADDRESS() {
		return ADDRESS;
	}
	public void setADDRESS(String aDDRESS) {
		ADDRESS = aDDRESS;
	}
	public String getITEM() {
		return ITEM;
	}
	public void setITEM(String iTEM) {
		ITEM = iTEM;
	}
	public String getCLEAR_FLAG() {
		return CLEAR_FLAG;
	}
	public void setCLEAR_FLAG(String cLEAR_FLAG) {
		CLEAR_FLAG = cLEAR_FLAG;
	}
		
	
}
