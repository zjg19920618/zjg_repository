package com.boomhope.tms.message.in.tms;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/***
 * 外设状态上送(TMS_0001)请求报文体Bean
 * @author shaopeng
 *
 */
@XStreamAlias("Body")  
public class Tms0002ReqBodyBean{
	
	@XStreamAlias("MachineStatus")  
	private String MachineStatus;	// 机器状态
	
	@XStreamImplicit(itemFieldName="Peri")
	private List<Tms0002PeriBean> periList;	// 外设信息
	
	public void setMachineStatus(String status){
		this.MachineStatus = status;
	}
	
	public String getMachineStatus(){
		return this.MachineStatus;
	}
	
	public void setPeriList(List<Tms0002PeriBean> periList){
		this.periList = periList;
	}

	public List<Tms0002PeriBean> getPeriList(){
		return this.periList;
	}
}
