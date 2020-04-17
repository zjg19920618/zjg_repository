package com.boomhope.tms.message.in.bck;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 * BCK_0001请求报文体Bean
 * @author shaopeng
 *
 */
@XStreamAlias("Body")  
public class BCK0001ReqBodyBean {
	
	private String IdType;	// 证件类型
	private String IdNo;	// 证件号码
	private String CustomName;	// 客户名称
	
	public void setIdType(String idType){
		this.IdType = idType;
	}
	
	public String getIdType(){
		return this.IdType;
	}
	
	public void setIdNo(String idNo){
		this.IdNo = idNo;
	}
	
	public String getIdNo(){
		return this.IdNo;
	}
	
	public void setCustomName(String customName){
		this.CustomName = customName;
	}
	
	public String getCustomName(){
		return this.CustomName;
	}

}
