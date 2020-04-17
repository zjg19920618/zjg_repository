package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 
 * Title:唐豆收回查询
 * Description:
 * @author mouchunyue
 * @date 2016年9月9日 下午4:58:19
 */
@XStreamAlias("BODY")
public class Out02210ResBodyBean {

	private String CUST_NO;//客户号
	private String CUST_NAME;//客户名称
	private String PRODUCT_CODE;//产品代码
	private String OPEN_DATE;//开户日期
	private String DEP_TERM;//存期
	private String CUR_BAL;//账户当前余额
	private String PAY_AMT;//支取金额
	private String TERM_CODE;//唐豆期次代码
	private String EXCHANGE_MODE;//唐豆兑换方式
	private String ORG_EXCHANGE_MODE;//原唐豆兑换方式
	private String ORG_EXCHANGE_PROP;//原唐豆兑现比例
	private String BACK_COUNT;//收回唐豆数量
	private String BACK_EXCHANGE_AMT;//收回兑现金额
	
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
	public String getPRODUCT_CODE() {
		return PRODUCT_CODE;
	}
	public void setPRODUCT_CODE(String pRODUCT_CODE) {
		PRODUCT_CODE = pRODUCT_CODE;
	}
	public String getOPEN_DATE() {
		return OPEN_DATE;
	}
	public void setOPEN_DATE(String oPEN_DATE) {
		OPEN_DATE = oPEN_DATE;
	}
	public String getDEP_TERM() {
		return DEP_TERM;
	}
	public void setDEP_TERM(String dEP_TERM) {
		DEP_TERM = dEP_TERM;
	}
	public String getCUR_BAL() {
		return CUR_BAL;
	}
	public void setCUR_BAL(String cUR_BAL) {
		CUR_BAL = cUR_BAL;
	}
	public String getPAY_AMT() {
		return PAY_AMT;
	}
	public void setPAY_AMT(String pAY_AMT) {
		PAY_AMT = pAY_AMT;
	}
	public String getTERM_CODE() {
		return TERM_CODE;
	}
	public void setTERM_CODE(String tERM_CODE) {
		TERM_CODE = tERM_CODE;
	}
	public String getEXCHANGE_MODE() {
		return EXCHANGE_MODE;
	}
	public void setEXCHANGE_MODE(String eXCHANGE_MODE) {
		EXCHANGE_MODE = eXCHANGE_MODE;
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
	public String getBACK_EXCHANGE_AMT() {
		return BACK_EXCHANGE_AMT;
	}
	public void setBACK_EXCHANGE_AMT(String bACK_EXCHANGE_AMT) {
		BACK_EXCHANGE_AMT = bACK_EXCHANGE_AMT;
	}
	
	
}
