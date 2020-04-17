package com.boomhope.tms.message.in.tms;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 * 外设信息报文片段Bean
 * @author shaopeng
 *
 */
@XStreamAlias("Peri")
public class Tms0002PeriBean {
	
	@XStreamAlias("PeriId")
	private String PeriId;	// 外设id
	@XStreamAlias("PeriStatus")
	private String PeriStatus;	// 外设状态
	@XStreamAlias("PeriStatusDesc")
	private String PeriStatusDesc;	// 外设状态描述
	
	public void setPeriId(String periId){
		this.PeriId = periId;
	}
	
	public String getPeriId(){
		return this.PeriId;
	}
	
	public void setPeriStatus(String periStatus){
		this.PeriStatus = periStatus;
	}
	
	public String getPeriStatus(){
		return this.PeriStatus;
	}
	
	public void setPeriStatusDesc(String periStatusDesc){
		this.PeriStatusDesc = periStatusDesc;
	}

	public String getPeriStatusDesc(){
		return this.PeriStatusDesc;
	}
}
