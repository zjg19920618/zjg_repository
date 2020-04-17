package com.boomhope.tms.message.cdjmac;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/** 
* @ClassName  CdjOpenAccReqBodyBean
* @Description 获取存单电子签名等上传路径  
* @author zjg 
*/
@XStreamAlias("Body")
public class CdjOpenAccReqBodyBean {
	
	private String tradeName;//交易名称
	private String openAccNo;//开户账号
	private String openAccDate;//开户日期
	
	public String getTradeName() {
		return tradeName;
	}
	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}
	public String getOpenAccNo() {
		return openAccNo;
	}
	public void setOpenAccNo(String openAccNo) {
		this.openAccNo = openAccNo;
	}
	public String getOpenAccDate() {
		return openAccDate;
	}
	public void setOpenAccDate(String openAccDate) {
		this.openAccDate = openAccDate;
	}
	
	
}
