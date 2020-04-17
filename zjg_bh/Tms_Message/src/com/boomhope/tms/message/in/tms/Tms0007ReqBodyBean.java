package com.boomhope.tms.message.in.tms;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 * 设备登录(TMS_0001)请求报文体Bean
 * @author shaopeng
 *
 */
@XStreamAlias("Body")  
public class Tms0007ReqBodyBean {
	
	private String MachineNo;	// 机器编号
	private String BranchNo;	// 机构号
	private String status;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMachineNo() {
		return MachineNo;
	}
	public void setMachineNo(String machineNo) {
		MachineNo = machineNo;
	}
	public String getBranchNo() {
		return BranchNo;
	}
	public void setBranchNo(String branchNo) {
		BranchNo = branchNo;
	}

}
