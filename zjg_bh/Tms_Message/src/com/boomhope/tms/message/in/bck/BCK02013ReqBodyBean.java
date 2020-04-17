package com.boomhope.tms.message.in.bck;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *大额普通汇兑往帐发送交易接口（通用）02013
 * @author hk
 *
 */
@XStreamAlias("Body")  
public class BCK02013ReqBodyBean {
	
	private String MSG_TYPE;//报文类型
	private String CURRENCY;//币种
	private String BOOK_CARD;//折卡选择
	private String PAY_ACCT_NO;//付款人账号
	private String PAY_CARD_NO;//付款人卡号
	private String PAY_NAME;//付款人名称
	private String PAY_ADDR;//付款人地址
	private String BALANCE;//余额
	private String SETT_TYPE;//结算方式
	private String DRAW_COND;//支取条件
	private String PASSWORD;//密码
	private String CERT_TYPE;//凭证种类
	private String CERT_NO;//凭证号码
	private String SUMM_CODE;//摘要代码
	private String SUMM_TEXT;//摘要内容
	private String REMIT_AMT;//汇款金额
	private String FEE_TYPE;//手续费类型
	private String FEE;//手续费
	private String POST_FEE;//邮电费
	private String BUSI_TYPE;//业务种类
	private String RECV_BANK_NO;//接收行行号
	private String RECV_BANK_NAME;//接收行行名
	private String RECV_CLR_BANK_NO;//接收行清算行号
	private String RECV_CLR_BANK_NAME;//接收行清算行名
	private String PAYEE_HBR_NO;//收款人开户行号
	private String PAYEE_HBR_NAME;//收款人开户行名
	private String PAYEE_ACCT_NO;//收款人账号
	private String PAYEE_NAME;//收款人户名
	private String PAYEE_ADDR;//收款人地址
	private String ORIG_SEND_DATE;//原发报日期
	private String ORIG_TRAN_NO;//原交易序号
	private String ORIG_TRUST_DATE;//原委托日期
	private String ORIG_CMT_NO;//原CMT号码
	private String ORIG_AMT;//原金额
	private String ORIG_PAY_ACCT_NO;//原付款人账号 
	private String ORIG_PAY_NAME;//原付款人名称
	private String ORIG_PAYEE_ACCT_NO;//原收款人账号
	private String ORIG_PAYEE_NAME;//原收款人名称
	private String ORIG_APPD_TEXT;//原附言
	private String APPD_TEXT;//附言
	private String BUSI_CLASS;//业务类型
	private String BUSI_PRIORITY;//业务优先级
	private String PAY_HBR_NO;//付款人开户行行号
	private String PAY_HBR_NAME;//付款人开户行名称
	private String PAY_HBR_INST_NO;//付款人开户机构号
	private String REMARK;//备注
	private String REMARK2;//备注2
	private String APPD_TEXT2;//附言2
	private String RETURN_TEXT;//退汇原因
	private String TASK_ID;//任务号
	private String OPER_DATE;//操作日期
	private String CORE_PRJ_NO;//核心项目编号
	private String CORE_PRO_CODE;//核心项目代码
	private String CNAPS_MSG_TYPE;//二代报文种类
	private String PP_No;//平盘单号
	private String TRANSFER_FLAG;//转账标志
	private String NEXT_DAY_SEND_FLAG;//次日发送标志
	private String TIMED_SEND_TIME;//定时发送时间
	private String TEL_NO;//添加手机号
	
	public String getMSG_TYPE() {
		return MSG_TYPE;
	}
	public void setMSG_TYPE(String mSG_TYPE) {
		MSG_TYPE = mSG_TYPE;
	}
	public String getCURRENCY() {
		return CURRENCY;
	}
	public void setCURRENCY(String cURRENCY) {
		CURRENCY = cURRENCY;
	}
	public String getBOOK_CARD() {
		return BOOK_CARD;
	}
	public void setBOOK_CARD(String bOOK_CARD) {
		BOOK_CARD = bOOK_CARD;
	}
	public String getPAY_ACCT_NO() {
		return PAY_ACCT_NO;
	}
	public void setPAY_ACCT_NO(String pAY_ACCT_NO) {
		PAY_ACCT_NO = pAY_ACCT_NO;
	}
	public String getPAY_CARD_NO() {
		return PAY_CARD_NO;
	}
	public void setPAY_CARD_NO(String pAY_CARD_NO) {
		PAY_CARD_NO = pAY_CARD_NO;
	}
	public String getPAY_NAME() {
		return PAY_NAME;
	}
	public void setPAY_NAME(String pAY_NAME) {
		PAY_NAME = pAY_NAME;
	}
	public String getPAY_ADDR() {
		return PAY_ADDR;
	}
	public void setPAY_ADDR(String pAY_ADDR) {
		PAY_ADDR = pAY_ADDR;
	}
	public String getBALANCE() {
		return BALANCE;
	}
	public void setBALANCE(String bALANCE) {
		BALANCE = bALANCE;
	}
	public String getSETT_TYPE() {
		return SETT_TYPE;
	}
	public void setSETT_TYPE(String sETT_TYPE) {
		SETT_TYPE = sETT_TYPE;
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
	public String getCERT_TYPE() {
		return CERT_TYPE;
	}
	public void setCERT_TYPE(String cERT_TYPE) {
		CERT_TYPE = cERT_TYPE;
	}
	public String getCERT_NO() {
		return CERT_NO;
	}
	public void setCERT_NO(String cERT_NO) {
		CERT_NO = cERT_NO;
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
	public String getREMIT_AMT() {
		return REMIT_AMT;
	}
	public void setREMIT_AMT(String rEMIT_AMT) {
		REMIT_AMT = rEMIT_AMT;
	}
	public String getFEE_TYPE() {
		return FEE_TYPE;
	}
	public void setFEE_TYPE(String fEE_TYPE) {
		FEE_TYPE = fEE_TYPE;
	}
	public String getFEE() {
		return FEE;
	}
	public void setFEE(String fEE) {
		FEE = fEE;
	}
	public String getPOST_FEE() {
		return POST_FEE;
	}
	public void setPOST_FEE(String pOST_FEE) {
		POST_FEE = pOST_FEE;
	}
	public String getBUSI_TYPE() {
		return BUSI_TYPE;
	}
	public void setBUSI_TYPE(String bUSI_TYPE) {
		BUSI_TYPE = bUSI_TYPE;
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
	public String getRECV_CLR_BANK_NO() {
		return RECV_CLR_BANK_NO;
	}
	public void setRECV_CLR_BANK_NO(String rECV_CLR_BANK_NO) {
		RECV_CLR_BANK_NO = rECV_CLR_BANK_NO;
	}
	public String getRECV_CLR_BANK_NAME() {
		return RECV_CLR_BANK_NAME;
	}
	public void setRECV_CLR_BANK_NAME(String rECV_CLR_BANK_NAME) {
		RECV_CLR_BANK_NAME = rECV_CLR_BANK_NAME;
	}
	public String getPAYEE_HBR_NO() {
		return PAYEE_HBR_NO;
	}
	public void setPAYEE_HBR_NO(String pAYEE_HBR_NO) {
		PAYEE_HBR_NO = pAYEE_HBR_NO;
	}
	public String getPAYEE_HBR_NAME() {
		return PAYEE_HBR_NAME;
	}
	public void setPAYEE_HBR_NAME(String pAYEE_HBR_NAME) {
		PAYEE_HBR_NAME = pAYEE_HBR_NAME;
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
	public String getPAYEE_ADDR() {
		return PAYEE_ADDR;
	}
	public void setPAYEE_ADDR(String pAYEE_ADDR) {
		PAYEE_ADDR = pAYEE_ADDR;
	}
	public String getORIG_SEND_DATE() {
		return ORIG_SEND_DATE;
	}
	public void setORIG_SEND_DATE(String oRIG_SEND_DATE) {
		ORIG_SEND_DATE = oRIG_SEND_DATE;
	}
	public String getORIG_TRAN_NO() {
		return ORIG_TRAN_NO;
	}
	public void setORIG_TRAN_NO(String oRIG_TRAN_NO) {
		ORIG_TRAN_NO = oRIG_TRAN_NO;
	}
	public String getORIG_TRUST_DATE() {
		return ORIG_TRUST_DATE;
	}
	public void setORIG_TRUST_DATE(String oRIG_TRUST_DATE) {
		ORIG_TRUST_DATE = oRIG_TRUST_DATE;
	}
	public String getORIG_CMT_NO() {
		return ORIG_CMT_NO;
	}
	public void setORIG_CMT_NO(String oRIG_CMT_NO) {
		ORIG_CMT_NO = oRIG_CMT_NO;
	}
	public String getORIG_AMT() {
		return ORIG_AMT;
	}
	public void setORIG_AMT(String oRIG_AMT) {
		ORIG_AMT = oRIG_AMT;
	}
	public String getORIG_PAY_ACCT_NO() {
		return ORIG_PAY_ACCT_NO;
	}
	public void setORIG_PAY_ACCT_NO(String oRIG_PAY_ACCT_NO) {
		ORIG_PAY_ACCT_NO = oRIG_PAY_ACCT_NO;
	}
	public String getORIG_PAY_NAME() {
		return ORIG_PAY_NAME;
	}
	public void setORIG_PAY_NAME(String oRIG_PAY_NAME) {
		ORIG_PAY_NAME = oRIG_PAY_NAME;
	}
	public String getORIG_PAYEE_ACCT_NO() {
		return ORIG_PAYEE_ACCT_NO;
	}
	public void setORIG_PAYEE_ACCT_NO(String oRIG_PAYEE_ACCT_NO) {
		ORIG_PAYEE_ACCT_NO = oRIG_PAYEE_ACCT_NO;
	}
	public String getORIG_PAYEE_NAME() {
		return ORIG_PAYEE_NAME;
	}
	public void setORIG_PAYEE_NAME(String oRIG_PAYEE_NAME) {
		ORIG_PAYEE_NAME = oRIG_PAYEE_NAME;
	}
	public String getORIG_APPD_TEXT() {
		return ORIG_APPD_TEXT;
	}
	public void setORIG_APPD_TEXT(String oRIG_APPD_TEXT) {
		ORIG_APPD_TEXT = oRIG_APPD_TEXT;
	}
	public String getAPPD_TEXT() {
		return APPD_TEXT;
	}
	public void setAPPD_TEXT(String aPPD_TEXT) {
		APPD_TEXT = aPPD_TEXT;
	}
	public String getBUSI_CLASS() {
		return BUSI_CLASS;
	}
	public void setBUSI_CLASS(String bUSI_CLASS) {
		BUSI_CLASS = bUSI_CLASS;
	}
	public String getBUSI_PRIORITY() {
		return BUSI_PRIORITY;
	}
	public void setBUSI_PRIORITY(String bUSI_PRIORITY) {
		BUSI_PRIORITY = bUSI_PRIORITY;
	}
	public String getPAY_HBR_NO() {
		return PAY_HBR_NO;
	}
	public void setPAY_HBR_NO(String pAY_HBR_NO) {
		PAY_HBR_NO = pAY_HBR_NO;
	}
	public String getPAY_HBR_NAME() {
		return PAY_HBR_NAME;
	}
	public void setPAY_HBR_NAME(String pAY_HBR_NAME) {
		PAY_HBR_NAME = pAY_HBR_NAME;
	}
	public String getPAY_HBR_INST_NO() {
		return PAY_HBR_INST_NO;
	}
	public void setPAY_HBR_INST_NO(String pAY_HBR_INST_NO) {
		PAY_HBR_INST_NO = pAY_HBR_INST_NO;
	}
	public String getREMARK() {
		return REMARK;
	}
	public void setREMARK(String rEMARK) {
		REMARK = rEMARK;
	}
	public String getREMARK2() {
		return REMARK2;
	}
	public void setREMARK2(String rEMARK2) {
		REMARK2 = rEMARK2;
	}
	public String getAPPD_TEXT2() {
		return APPD_TEXT2;
	}
	public void setAPPD_TEXT2(String aPPD_TEXT2) {
		APPD_TEXT2 = aPPD_TEXT2;
	}
	public String getRETURN_TEXT() {
		return RETURN_TEXT;
	}
	public void setRETURN_TEXT(String rETURN_TEXT) {
		RETURN_TEXT = rETURN_TEXT;
	}
	public String getTASK_ID() {
		return TASK_ID;
	}
	public void setTASK_ID(String tASK_ID) {
		TASK_ID = tASK_ID;
	}
	public String getOPER_DATE() {
		return OPER_DATE;
	}
	public void setOPER_DATE(String oPER_DATE) {
		OPER_DATE = oPER_DATE;
	}
	public String getCORE_PRJ_NO() {
		return CORE_PRJ_NO;
	}
	public void setCORE_PRJ_NO(String cORE_PRJ_NO) {
		CORE_PRJ_NO = cORE_PRJ_NO;
	}
	public String getCORE_PRO_CODE() {
		return CORE_PRO_CODE;
	}
	public void setCORE_PRO_CODE(String cORE_PRO_CODE) {
		CORE_PRO_CODE = cORE_PRO_CODE;
	}
	public String getCNAPS_MSG_TYPE() {
		return CNAPS_MSG_TYPE;
	}
	public void setCNAPS_MSG_TYPE(String cNAPS_MSG_TYPE) {
		CNAPS_MSG_TYPE = cNAPS_MSG_TYPE;
	}
	public String getPP_No() {
		return PP_No;
	}
	public void setPP_No(String pP_No) {
		PP_No = pP_No;
	}
	public String getTRANSFER_FLAG() {
		return TRANSFER_FLAG;
	}
	public void setTRANSFER_FLAG(String tRANSFER_FLAG) {
		TRANSFER_FLAG = tRANSFER_FLAG;
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
