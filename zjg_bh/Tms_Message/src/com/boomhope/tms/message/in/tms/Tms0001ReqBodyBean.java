package com.boomhope.tms.message.in.tms;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 * 设备登录(TMS_0001)请求报文体Bean
 * @author shaopeng
 *
 */
@XStreamAlias("Body")  
public class Tms0001ReqBodyBean {
	
	private String MachineIp;	// 机器IP
	
	public void setMachineIp(String machineIp){
		this.MachineIp = machineIp;
	}
	
	public String getMachineIp(){
		return this.MachineIp;
	}

}
