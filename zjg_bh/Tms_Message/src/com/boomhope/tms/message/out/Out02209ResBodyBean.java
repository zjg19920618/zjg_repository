package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 
 * Title:唐豆收回
 * Description:
 * @author mouchunyue
 * @date 2016年9月9日 下午5:59:19
 */
@XStreamAlias("BODY")
public class Out02209ResBodyBean {

	private String SVR_DATE;//系统日期
	private String SVR_JRNL_NO;//流水号
	private String CUST_NO;//客户号
	private String CUST_NAME;//客户名称
	private String R_BACK_COUNT;//收回唐豆数量
	private String R_BACK_EXCHANGE_AMT;//收回金额
	private String INT_FROM_ACCT;//利息支出账户
	private String PUT_INT_ACCT;//应付利息账户
	private String AMT_ACCT_NO;//现金账户
	public String getSVR_DATE() {
		return SVR_DATE;
	}
	public void setSVR_DATE(String sVR_DATE) {
		SVR_DATE = sVR_DATE;
	}
	public String getSVR_JRNL_NO() {
		return SVR_JRNL_NO;
	}
	public void setSVR_JRNL_NO(String sVR_JRNL_NO) {
		SVR_JRNL_NO = sVR_JRNL_NO;
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
	public String getR_BACK_COUNT() {
		return R_BACK_COUNT;
	}
	public void setR_BACK_COUNT(String r_BACK_COUNT) {
		R_BACK_COUNT = r_BACK_COUNT;
	}
	public String getR_BACK_EXCHANGE_AMT() {
		return R_BACK_EXCHANGE_AMT;
	}
	public void setR_BACK_EXCHANGE_AMT(String r_BACK_EXCHANGE_AMT) {
		R_BACK_EXCHANGE_AMT = r_BACK_EXCHANGE_AMT;
	}
	public String getINT_FROM_ACCT() {
		return INT_FROM_ACCT;
	}
	public void setINT_FROM_ACCT(String iNT_FROM_ACCT) {
		INT_FROM_ACCT = iNT_FROM_ACCT;
	}
	public String getPUT_INT_ACCT() {
		return PUT_INT_ACCT;
	}
	public void setPUT_INT_ACCT(String pUT_INT_ACCT) {
		PUT_INT_ACCT = pUT_INT_ACCT;
	}
	public String getAMT_ACCT_NO() {
		return AMT_ACCT_NO;
	}
	public void setAMT_ACCT_NO(String aMT_ACCT_NO) {
		AMT_ACCT_NO = aMT_ACCT_NO;
	}
	
}
