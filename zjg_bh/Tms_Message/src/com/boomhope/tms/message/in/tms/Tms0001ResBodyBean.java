package com.boomhope.tms.message.in.tms;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 * 设备登录(TMS_0001)响应报文BodyBean
 * @author shaopeng
 *
 */
@XStreamAlias("Body")  
public class Tms0001ResBodyBean {

	private String MachineNo;	// 机器编号
	private String BranchNo;	// 机构号
	private String keys;//工作密钥
	private String TellerNo;//柜员号
	private String macFacNum;//出厂编号
	private String unitName;//机构名称

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public void setMachineNo(String machineNo){
		this.MachineNo = machineNo;
	}
	
	public String getMachineNo(){
		return this.MachineNo;
	}
	
	public void setBranchNo(String branchNo){
		this.BranchNo = branchNo;
	}
	
	public String getBranchNo(){
		return this.BranchNo;
	}

	public String getKeys() {
		return keys;
	}

	public void setKeys(String keys) {
		this.keys = keys;
	}

	public String getTellerNo() {
		return TellerNo;
	}

	public void setTellerNo(String tellerNo) {
		TellerNo = tellerNo;
	}

	public String getMacFacNum() {
		return macFacNum;
	}

	public void setMacFacNum(String macFacNum) {
		this.macFacNum = macFacNum;
	}
	
}
