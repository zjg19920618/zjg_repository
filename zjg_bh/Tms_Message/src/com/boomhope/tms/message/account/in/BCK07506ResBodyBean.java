package com.boomhope.tms.message.account.in;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/** 
* @ClassName  Account07506ResBodyBean 
* @Description  派发规则查询(交易码07506) 
* @author zh.m 
* @date 2016年12月4日 上午9:54:43  
*/
@XStreamAlias("Body")
public class BCK07506ResBodyBean {
	private String TERM_CODE;//期次代号
	private String TERM_NAME;//唐豆期名
	private String COUNT;//唐豆数量
	private String EXCHANGE_PROP;//唐豆兑现比例
	private String EXCHANGE_AMT;//兑现金额
	private String DEAL_TYPE;//处理标志
	public String getTERM_CODE() {
		return TERM_CODE;
	}
	public void setTERM_CODE(String tERM_CODE) {
		TERM_CODE = tERM_CODE;
	}
	public String getTERM_NAME() {
		return TERM_NAME;
	}
	public void setTERM_NAME(String tERM_NAME) {
		TERM_NAME = tERM_NAME;
	}
	public String getCOUNT() {
		return COUNT;
	}
	public void setCOUNT(String cOUNT) {
		COUNT = cOUNT;
	}
	public String getEXCHANGE_PROP() {
		return EXCHANGE_PROP;
	}
	public void setEXCHANGE_PROP(String eXCHANGE_PROP) {
		EXCHANGE_PROP = eXCHANGE_PROP;
	}
	public String getEXCHANGE_AMT() {
		return EXCHANGE_AMT;
	}
	public void setEXCHANGE_AMT(String eXCHANGE_AMT) {
		EXCHANGE_AMT = eXCHANGE_AMT;
	}
	public String getDEAL_TYPE() {
		return DEAL_TYPE;
	}
	public void setDEAL_TYPE(String dEAL_TYPE) {
		DEAL_TYPE = dEAL_TYPE;
	}
	
	
}
