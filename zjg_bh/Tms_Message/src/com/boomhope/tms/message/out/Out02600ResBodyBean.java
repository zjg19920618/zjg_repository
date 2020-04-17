package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 
 * Title:行内汇划-前置02600
 * @author hk
 */
@XStreamAlias("BODY")
public class Out02600ResBodyBean {
	private String SVR_JRNL_NO;//核心流水号
	private String SVR_DATE;//核心日期
	private String BUSI_JRNL_NO;//前置流水号
	private String VAT_SUB;//增值税科目
	private String VAT_AMT;//增值税
	private String CTF_SUB;//手续费科目
	private String CTF_ATM;//税后手续费
	private String TRAN_TIME;//交易时间
	
	
	public String getSVR_JRNL_NO() {
		return SVR_JRNL_NO;
	}
	public void setSVR_JRNL_NO(String sVR_JRNL_NO) {
		SVR_JRNL_NO = sVR_JRNL_NO;
	}
	public String getSVR_DATE() {
		return SVR_DATE;
	}
	public void setSVR_DATE(String sVR_DATE) {
		SVR_DATE = sVR_DATE;
	}
	public String getBUSI_JRNL_NO() {
		return BUSI_JRNL_NO;
	}
	public void setBUSI_JRNL_NO(String bUSI_JRNL_NO) {
		BUSI_JRNL_NO = bUSI_JRNL_NO;
	}
	public String getVAT_SUB() {
		return VAT_SUB;
	}
	public void setVAT_SUB(String vAT_SUB) {
		VAT_SUB = vAT_SUB;
	}
	public String getVAT_AMT() {
		return VAT_AMT;
	}
	public void setVAT_AMT(String vAT_AMT) {
		VAT_AMT = vAT_AMT;
	}
	public String getCTF_SUB() {
		return CTF_SUB;
	}
	public void setCTF_SUB(String cTF_SUB) {
		CTF_SUB = cTF_SUB;
	}
	public String getCTF_ATM() {
		return CTF_ATM;
	}
	public void setCTF_ATM(String cTF_ATM) {
		CTF_ATM = cTF_ATM;
	}
	public String getTRAN_TIME() {
		return TRAN_TIME;
	}
	public void setTRAN_TIME(String tRAN_TIME) {
		TRAN_TIME = tRAN_TIME;
	}
	
	
}
