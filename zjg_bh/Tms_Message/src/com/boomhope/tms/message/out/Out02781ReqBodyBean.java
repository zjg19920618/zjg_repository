package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 
 * 账户限额查询【02879】-前置02781
 * @author hk
 */
@XStreamAlias("BODY")
public class Out02781ReqBodyBean {
	public String DB_CR_FLAG;//借贷标志
	public String ACCT_NO;//账号
	public String OPPO_ACCT_NO;//对方账户
	public String TRAN_AMT;//发生额
	
	
	public String getDB_CR_FLAG() {
		return DB_CR_FLAG;
	}
	public void setDB_CR_FLAG(String dB_CR_FLAG) {
		DB_CR_FLAG = dB_CR_FLAG;
	}
	public String getACCT_NO() {
		return ACCT_NO;
	}
	public void setACCT_NO(String aCCT_NO) {
		ACCT_NO = aCCT_NO;
	}
	public String getOPPO_ACCT_NO() {
		return OPPO_ACCT_NO;
	}
	public void setOPPO_ACCT_NO(String oPPO_ACCT_NO) {
		OPPO_ACCT_NO = oPPO_ACCT_NO;
	}
	public String getTRAN_AMT() {
		return TRAN_AMT;
	}
	public void setTRAN_AMT(String tRAN_AMT) {
		TRAN_AMT = tRAN_AMT;
	}
	
	
	
	
	
}
