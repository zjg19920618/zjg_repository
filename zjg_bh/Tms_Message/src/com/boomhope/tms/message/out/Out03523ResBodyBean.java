package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 *查询账户利率浮动信息【55030】-前置03523
 * @author shaopeng
 *
 */
@XStreamAlias("BODY")  
public class Out03523ResBodyBean {
	
	private String SVR_DATE;//核心日期
	private String SVR_JRNL_NO;//核心流水号
	private String OPEN_RATE;//开户利率
	private String INST_FLOAT;//机构浮动
	private String CHANNEL_FLOAT;//渠道浮动
	private String BIRTHDAY_FLOAT;//生日浮动
	private String TIME_FLOAT;//时间浮动
	private String COMBINE_FLOAT;//组合浮动
	private String FLOAT_SUM;//浮动汇总
	private String FILE_NAME_RET;//浮动利率组合文件
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
	public String getOPEN_RATE() {
		return OPEN_RATE;
	}
	public void setOPEN_RATE(String oPEN_RATE) {
		OPEN_RATE = oPEN_RATE;
	}
	public String getINST_FLOAT() {
		return INST_FLOAT;
	}
	public void setINST_FLOAT(String iNST_FLOAT) {
		INST_FLOAT = iNST_FLOAT;
	}
	public String getCHANNEL_FLOAT() {
		return CHANNEL_FLOAT;
	}
	public void setCHANNEL_FLOAT(String cHANNEL_FLOAT) {
		CHANNEL_FLOAT = cHANNEL_FLOAT;
	}
	public String getBIRTHDAY_FLOAT() {
		return BIRTHDAY_FLOAT;
	}
	public void setBIRTHDAY_FLOAT(String bIRTHDAY_FLOAT) {
		BIRTHDAY_FLOAT = bIRTHDAY_FLOAT;
	}
	public String getTIME_FLOAT() {
		return TIME_FLOAT;
	}
	public void setTIME_FLOAT(String tIME_FLOAT) {
		TIME_FLOAT = tIME_FLOAT;
	}
	public String getCOMBINE_FLOAT() {
		return COMBINE_FLOAT;
	}
	public void setCOMBINE_FLOAT(String cOMBINE_FLOAT) {
		COMBINE_FLOAT = cOMBINE_FLOAT;
	}
	public String getFLOAT_SUM() {
		return FLOAT_SUM;
	}
	public void setFLOAT_SUM(String fLOAT_SUM) {
		FLOAT_SUM = fLOAT_SUM;
	}
	public String getFILE_NAME_RET() {
		return FILE_NAME_RET;
	}
	public void setFILE_NAME_RET(String fILE_NAME_RET) {
		FILE_NAME_RET = fILE_NAME_RET;
	}
	
	

}
