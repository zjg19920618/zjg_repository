package com.boomhope.tms.message.account.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;


/** 
* @ClassName  Account07506ReqBodyBean 
* @Description  派发规则查询(交易码07506) 
* @author zh.m 
* @date 2016年12月4日 上午9:47:37  
*/
@XStreamAlias("BODY")
public class Account07506ReqBodyBean {
	
	private String PRODUCT_CODE;//产品代码
	private String DEP_TERM;//存期
	private String AMT;//金额
	private String ACCEPT_DATE;//兑付日期
	private String IN_INST_NO;//查询机构
	private String ACT_CHNL;//活动渠道
	private String DETAIL_NUM;//循环数
	private Account07506ReqAgentInfBean ACCT_INFO;//账户信息
	
	public String getPRODUCT_CODE() {
		return PRODUCT_CODE;
	}
	public void setPRODUCT_CODE(String pRODUCT_CODE) {
		PRODUCT_CODE = pRODUCT_CODE;
	}
	public String getDEP_TERM() {
		return DEP_TERM;
	}
	public void setDEP_TERM(String dEP_TERM) {
		DEP_TERM = dEP_TERM;
	}
	public String getAMT() {
		return AMT;
	}
	public void setAMT(String aMT) {
		AMT = aMT;
	}
	public String getACCEPT_DATE() {
		return ACCEPT_DATE;
	}
	public void setACCEPT_DATE(String aCCEPT_DATE) {
		ACCEPT_DATE = aCCEPT_DATE;
	}
	public String getIN_INST_NO() {
		return IN_INST_NO;
	}
	public void setIN_INST_NO(String iN_INST_NO) {
		IN_INST_NO = iN_INST_NO;
	}
	public String getACT_CHNL() {
		return ACT_CHNL;
	}
	public void setACT_CHNL(String aCT_CHNL) {
		ACT_CHNL = aCT_CHNL;
	}
	public String getDETAIL_NUM() {
		return DETAIL_NUM;
	}
	public void setDETAIL_NUM(String dETAIL_NUM) {
		DETAIL_NUM = dETAIL_NUM;
	}
	public Account07506ReqAgentInfBean getACCT_INFO() {
		return ACCT_INFO;
	}
	public void setACCT_INFO(Account07506ReqAgentInfBean aCCT_INFO) {
		ACCT_INFO = aCCT_INFO;
	}
	
}
