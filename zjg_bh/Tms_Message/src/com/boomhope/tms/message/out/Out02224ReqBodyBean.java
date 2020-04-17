package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 小额普通贷记往帐录入（通用）02224
 * BCK_02224请求报文体Bean
 * @author wang.sk
 */
@XStreamAlias("BODY")
public class Out02224ReqBodyBean {
	private String BUSI_OPTION;		//业务选择
	private String BUSI_CLASS;		//业务类型
	private String CURRENCY;		//币种
	private String BOOK_CARD;		//折卡选择
	private String PAY_ACCT_NO;		//付款人账号
	
	private String PAY_CARD_NO;		//付款人卡号
	private String PAY_NAME;		//付款人名称
	private String PAY_ADDR;		//付款人地址
	private String PAY_HBR_NO;		//付款人开户行号
	private String PAY_HBR_NAME;		//付款人开户行名
	
	private String PAY_HBR_INST_NO;		//付款人开户机构号
	private String DRAW_COND;		//支付条件
	private String BOOK_NO;		//存折号码
	private String PASSWORD;		//密码
	private String BALANCE;		//余额
	
	private String CERT_TYPE;		//凭证种类
	private String CERT_NO;		//凭证号码
	private String SUMM_CODE;		//摘要
	private String SUMM_TEXT;		//摘要内容
	private String REMIT_AMT;		//汇款金额
	
	private String FEE_TYPE;		//手续费类型
	private String FEE;		//手续费
	private String POST_FEE;		//邮电费
	private String BUSI_TYPE;		//业务种类
	private String ITEM_NUM;		//笔数
	
	private String TRAN_AMT;		//交易金额
	
	private String PAY_ACCT_TYPE;		//付款类型
	private String PAYEE_ACCT_TYPE;		//收款类型
	private String SETT_TYPE;		//业务模式
	private String TASK_ID;		//任务号
	private String CORE_PRJ_NO;		//核心项目编号
	
	private String CORE_PRO_CODE;		//核心产品代码
	private String REMARK1;		//备注1
	private String REMARK2;		//备注2
	private String TRANSFER_FLAG;		//转账标志
	private String NEXT_DAY_SEND_FLAG;		//次日发送标志
	
	private String TIMED_SEND_TIME;		//定时发送时间
	private Out02224ReqBodyDetailBean  DETAIL;
	private String TEL_NO;//手机号
	
	
	public Out02224ReqBodyDetailBean getDETAIL() {
		return DETAIL;
	}

	public void setDETAIL(Out02224ReqBodyDetailBean dETAIL) {
		DETAIL = dETAIL;
	}

	public String getBUSI_OPTION() {
		return BUSI_OPTION;
	}

	public void setBUSI_OPTION(String bUSI_OPTION) {
		BUSI_OPTION = bUSI_OPTION;
	}

	public String getBUSI_CLASS() {
		return BUSI_CLASS;
	}

	public void setBUSI_CLASS(String bUSI_CLASS) {
		BUSI_CLASS = bUSI_CLASS;
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

	public String getDRAW_COND() {
		return DRAW_COND;
	}

	public void setDRAW_COND(String dRAW_COND) {
		DRAW_COND = dRAW_COND;
	}

	public String getBOOK_NO() {
		return BOOK_NO;
	}

	public void setBOOK_NO(String bOOK_NO) {
		BOOK_NO = bOOK_NO;
	}

	public String getPASSWORD() {
		return PASSWORD;
	}

	public void setPASSWORD(String pASSWORD) {
		PASSWORD = pASSWORD;
	}

	public String getBALANCE() {
		return BALANCE;
	}

	public void setBALANCE(String bALANCE) {
		BALANCE = bALANCE;
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

	public String getITEM_NUM() {
		return ITEM_NUM;
	}

	public void setITEM_NUM(String iTEM_NUM) {
		ITEM_NUM = iTEM_NUM;
	}

	public String getTRAN_AMT() {
		return TRAN_AMT;
	}

	public void setTRAN_AMT(String tRAN_AMT) {
		TRAN_AMT = tRAN_AMT;
	}

	public String getPAY_ACCT_TYPE() {
		return PAY_ACCT_TYPE;
	}

	public void setPAY_ACCT_TYPE(String pAY_ACCT_TYPE) {
		PAY_ACCT_TYPE = pAY_ACCT_TYPE;
	}

	public String getPAYEE_ACCT_TYPE() {
		return PAYEE_ACCT_TYPE;
	}

	public void setPAYEE_ACCT_TYPE(String pAYEE_ACCT_TYPE) {
		PAYEE_ACCT_TYPE = pAYEE_ACCT_TYPE;
	}

	public String getSETT_TYPE() {
		return SETT_TYPE;
	}

	public void setSETT_TYPE(String sETT_TYPE) {
		SETT_TYPE = sETT_TYPE;
	}

	public String getTASK_ID() {
		return TASK_ID;
	}

	public void setTASK_ID(String tASK_ID) {
		TASK_ID = tASK_ID;
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

	public String getREMARK1() {
		return REMARK1;
	}

	public void setREMARK1(String rEMARK1) {
		REMARK1 = rEMARK1;
	}

	public String getREMARK2() {
		return REMARK2;
	}

	public void setREMARK2(String rEMARK2) {
		REMARK2 = rEMARK2;
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
