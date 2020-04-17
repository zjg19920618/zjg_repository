package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * Title:行内汇划-前置02600
 * @author hk
 */
@XStreamAlias("BODY")
public class Out02600ReqBodyBean {
	private String BUSI_TYPE;//业务类型
	private String DB_CR_FLAG;//借贷标志
	private String AGENT_INST_NO;//被代理机构号
	private String SEND_BANK_NO;//提出行行号
	private String SEND_BANK_NAME;//提出行名称
	private String PAY_ACCT_NO;//付款人账号
	private String PAY_NAME;//付款人名称
	private String DRAW_COND;//支取条件
	private String RECV_BANK_NO;//提入行行号
	private String RECV_BANK_NAME;//提入行名称
	private String PAYEE_ACCT_NO;//收款人账号
	private String PAYEE_NAME;//收款人名称 
	private String BILL_TYPE;//票据种类
	private String BILL_NO;//票据号码
	private String PIN;//支付密码
	private String AVAL_BAL;//可用余额
	private String CUEERNCY;//币种
	private String AMT;//金    额
	private String BILL_DATE;//出票日期
	private String NOTE_DATE;//提示付款日期
	private String ENDOR_NUM;//背书次数
	private String REMARK;//备注
	private String PURPOS;//用途
	private String TRN_REASON;//转账原因
	private String ORIG_CERT_TYPE;//原始凭证种类
	private String ATTACH_NUM;//张数
	private String APPD_TEXT;//附加信息
	private String SUMM_CODE;//摘要代码
	private String SUMM_TEXT;//摘要内容
	private String NEXT_DAY_SEND_FLAG;//次日发送标志
	private String TIMED_SEND_TIME;//定时发送时间
	private String TASK_IDTRNS;//任务号
	private Out02600ReqBodyDetailBean DETAIL;//背书人清单
	private String TEL_NO;//手机号
	
	public String getTASK_IDTRNS() {
		return TASK_IDTRNS;
	}
	public void setTASK_IDTRNS(String tASK_IDTRNS) {
		TASK_IDTRNS = tASK_IDTRNS;
	}
	public Out02600ReqBodyDetailBean getDETAIL() {
		return DETAIL;
	}
	public void setDETAIL(Out02600ReqBodyDetailBean dETAIL) {
		DETAIL = dETAIL;
	}
	public String getBUSI_TYPE() {
		return BUSI_TYPE;
	}
	public void setBUSI_TYPE(String bUSI_TYPE) {
		BUSI_TYPE = bUSI_TYPE;
	}
	public String getDB_CR_FLAG() {
		return DB_CR_FLAG;
	}
	public void setDB_CR_FLAG(String dB_CR_FLAG) {
		DB_CR_FLAG = dB_CR_FLAG;
	}
	public String getAGENT_INST_NO() {
		return AGENT_INST_NO;
	}
	public void setAGENT_INST_NO(String aGENT_INST_NO) {
		AGENT_INST_NO = aGENT_INST_NO;
	}
	public String getSEND_BANK_NO() {
		return SEND_BANK_NO;
	}
	public void setSEND_BANK_NO(String sEND_BANK_NO) {
		SEND_BANK_NO = sEND_BANK_NO;
	}
	public String getSEND_BANK_NAME() {
		return SEND_BANK_NAME;
	}
	public void setSEND_BANK_NAME(String sEND_BANK_NAME) {
		SEND_BANK_NAME = sEND_BANK_NAME;
	}
	public String getPAY_ACCT_NO() {
		return PAY_ACCT_NO;
	}
	public void setPAY_ACCT_NO(String pAY_ACCT_NO) {
		PAY_ACCT_NO = pAY_ACCT_NO;
	}
	public String getPAY_NAME() {
		return PAY_NAME;
	}
	public void setPAY_NAME(String pAY_NAME) {
		PAY_NAME = pAY_NAME;
	}
	public String getDRAW_COND() {
		return DRAW_COND;
	}
	public void setDRAW_COND(String dRAW_COND) {
		DRAW_COND = dRAW_COND;
	}
	public String getRECV_BANK_NO() {
		return RECV_BANK_NO;
	}
	public void setRECV_BANK_NO(String rECV_BANK_NO) {
		RECV_BANK_NO = rECV_BANK_NO;
	}
	public String getRECV_BANK_NAME() {
		return RECV_BANK_NAME;
	}
	public void setRECV_BANK_NAME(String rECV_BANK_NAME) {
		RECV_BANK_NAME = rECV_BANK_NAME;
	}
	public String getPAYEE_ACCT_NO() {
		return PAYEE_ACCT_NO;
	}
	public void setPAYEE_ACCT_NO(String pAYEE_ACCT_NO) {
		PAYEE_ACCT_NO = pAYEE_ACCT_NO;
	}
	public String getPAYEE_NAME() {
		return PAYEE_NAME;
	}
	public void setPAYEE_NAME(String pAYEE_NAME) {
		PAYEE_NAME = pAYEE_NAME;
	}
	public String getBILL_TYPE() {
		return BILL_TYPE;
	}
	public void setBILL_TYPE(String bILL_TYPE) {
		BILL_TYPE = bILL_TYPE;
	}
	public String getBILL_NO() {
		return BILL_NO;
	}
	public void setBILL_NO(String bILL_NO) {
		BILL_NO = bILL_NO;
	}
	public String getPIN() {
		return PIN;
	}
	public void setPIN(String pIN) {
		PIN = pIN;
	}
	public String getAVAL_BAL() {
		return AVAL_BAL;
	}
	public void setAVAL_BAL(String aVAL_BAL) {
		AVAL_BAL = aVAL_BAL;
	}
	public String getCUEERNCY() {
		return CUEERNCY;
	}
	public void setCUEERNCY(String cUEERNCY) {
		CUEERNCY = cUEERNCY;
	}
	public String getAMT() {
		return AMT;
	}
	public void setAMT(String aMT) {
		AMT = aMT;
	}
	public String getBILL_DATE() {
		return BILL_DATE;
	}
	public void setBILL_DATE(String bILL_DATE) {
		BILL_DATE = bILL_DATE;
	}
	public String getNOTE_DATE() {
		return NOTE_DATE;
	}
	public void setNOTE_DATE(String nOTE_DATE) {
		NOTE_DATE = nOTE_DATE;
	}
	public String getENDOR_NUM() {
		return ENDOR_NUM;
	}
	public void setENDOR_NUM(String eNDOR_NUM) {
		ENDOR_NUM = eNDOR_NUM;
	}
	public String getREMARK() {
		return REMARK;
	}
	public void setREMARK(String rEMARK) {
		REMARK = rEMARK;
	}
	public String getPURPOS() {
		return PURPOS;
	}
	public void setPURPOS(String pURPOS) {
		PURPOS = pURPOS;
	}
	public String getTRN_REASON() {
		return TRN_REASON;
	}
	public void setTRN_REASON(String tRN_REASON) {
		TRN_REASON = tRN_REASON;
	}
	public String getORIG_CERT_TYPE() {
		return ORIG_CERT_TYPE;
	}
	public void setORIG_CERT_TYPE(String oRIG_CERT_TYPE) {
		ORIG_CERT_TYPE = oRIG_CERT_TYPE;
	}
	public String getATTACH_NUM() {
		return ATTACH_NUM;
	}
	public void setATTACH_NUM(String aTTACH_NUM) {
		ATTACH_NUM = aTTACH_NUM;
	}
	public String getAPPD_TEXT() {
		return APPD_TEXT;
	}
	public void setAPPD_TEXT(String aPPD_TEXT) {
		APPD_TEXT = aPPD_TEXT;
	}
	public String getSUMM_CODE() {
		return SUMM_CODE;
	}
	public void setSUMM_CODE(String sUMM_CODE) {
		SUMM_CODE = sUMM_CODE;
	}
	public String getSUMM_TEXT() {
		return SUMM_TEXT;
	}
	public void setSUMM_TEXT(String sUMM_TEXT) {
		SUMM_TEXT = sUMM_TEXT;
	}
	public String getNEXT_DAY_SEND_FLAG() {
		return NEXT_DAY_SEND_FLAG;
	}
	public void setNEXT_DAY_SEND_FLAG(String nEXT_DAY_SEND_FLAG) {
		NEXT_DAY_SEND_FLAG = nEXT_DAY_SEND_FLAG;
	}
	public String getTIMED_SEND_TIME() {
		return TIMED_SEND_TIME;
	}
	public void setTIMED_SEND_TIME(String tIMED_SEND_TIME) {
		TIMED_SEND_TIME = tIMED_SEND_TIME;
	}
	public String getTEL_NO() {
		return TEL_NO;
	}
	public void setTEL_NO(String tEL_NO) {
		TEL_NO = tEL_NO;
	}
	
}
