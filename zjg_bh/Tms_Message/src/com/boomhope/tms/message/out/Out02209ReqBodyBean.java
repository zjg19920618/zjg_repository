package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 
 * Title:唐豆收回
 * Description:
 * @author mouchunyue
 * @date 2016年9月9日 下午5:31:37
 */
@XStreamAlias("BODY")
public class Out02209ReqBodyBean {

	private String ACCT_NO;//账号
	private String SUB_ACCT_NO;//子账号
	private String PAY_DATE;//支取日期
	private String PAY_JRNL;//支取流水
	private String PAY_AMT;//支取金额
	private String ORG_EXCHANGE_MODE;//原唐豆兑换方式
	private String ORG_EXCHANGE_PROP;//原唐豆兑现比例
	private String BACK_COUNT;//收回唐豆数量
	private String BACK_PROP;//收回比例
	private String BACK_EXCHANGE_AMT;//收回兑现金额
	private String RECOV_TYPE;//唐豆收回方式
	private String OPPO_ACCT_NO;//对方账号
	private String PASSWORD;//对方账号密码
	public String getACCT_NO() {
		return ACCT_NO;
	}
	public void setACCT_NO(String aCCT_NO) {
		ACCT_NO = aCCT_NO;
	}
	public String getSUB_ACCT_NO() {
		return SUB_ACCT_NO;
	}
	public void setSUB_ACCT_NO(String sUB_ACCT_NO) {
		SUB_ACCT_NO = sUB_ACCT_NO;
	}
	public String getPAY_DATE() {
		return PAY_DATE;
	}
	public void setPAY_DATE(String pAY_DATE) {
		PAY_DATE = pAY_DATE;
	}
	public String getPAY_JRNL() {
		return PAY_JRNL;
	}
	public void setPAY_JRNL(String pAY_JRNL) {
		PAY_JRNL = pAY_JRNL;
	}
	public String getPAY_AMT() {
		return PAY_AMT;
	}
	public void setPAY_AMT(String pAY_AMT) {
		PAY_AMT = pAY_AMT;
	}
	public String getORG_EXCHANGE_MODE() {
		return ORG_EXCHANGE_MODE;
	}
	public void setORG_EXCHANGE_MODE(String oRG_EXCHANGE_MODE) {
		ORG_EXCHANGE_MODE = oRG_EXCHANGE_MODE;
	}
	public String getORG_EXCHANGE_PROP() {
		return ORG_EXCHANGE_PROP;
	}
	public void setORG_EXCHANGE_PROP(String oRG_EXCHANGE_PROP) {
		ORG_EXCHANGE_PROP = oRG_EXCHANGE_PROP;
	}
	public String getBACK_COUNT() {
		return BACK_COUNT;
	}
	public void setBACK_COUNT(String bACK_COUNT) {
		BACK_COUNT = bACK_COUNT;
	}
	public String getBACK_PROP() {
		return BACK_PROP;
	}
	public void setBACK_PROP(String bACK_PROP) {
		BACK_PROP = bACK_PROP;
	}
	public String getBACK_EXCHANGE_AMT() {
		return BACK_EXCHANGE_AMT;
	}
	public void setBACK_EXCHANGE_AMT(String bACK_EXCHANGE_AMT) {
		BACK_EXCHANGE_AMT = bACK_EXCHANGE_AMT;
	}
	public String getRECOV_TYPE() {
		return RECOV_TYPE;
	}
	public void setRECOV_TYPE(String rECOV_TYPE) {
		RECOV_TYPE = rECOV_TYPE;
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
	
}
