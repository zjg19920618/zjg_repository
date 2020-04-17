package com.boomhope.tms.message.cdjmac;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 * 存单机响应报文头Bean
 * @author shaopeng
 *
 */
@XStreamAlias("Head")  
public class InResHeadBean {
	
	private String TradeCode;	// 交易代码
	private String MachineNo;	// 机器编号
	private String MachineType;	// 机器类型
	private String BranchNo;	// 机构号
	private String ProductCode;	// 产品代码
	private String supTellerNo;	// 授权柜员号
	public String getTradeCode() {
		return TradeCode;
	}
	public void setTradeCode(String tradeCode) {
		TradeCode = tradeCode;
	}
	public String getMachineNo() {
		return MachineNo;
	}
	public void setMachineNo(String machineNo) {
		MachineNo = machineNo;
	}
	public String getMachineType() {
		return MachineType;
	}
	public void setMachineType(String machineType) {
		MachineType = machineType;
	}
	public String getBranchNo() {
		return BranchNo;
	}
	public void setBranchNo(String branchNo) {
		BranchNo = branchNo;
	}
	public String getProductCode() {
		return ProductCode;
	}
	public void setProductCode(String productCode) {
		ProductCode = productCode;
	}
	public String getSupTellerNo() {
		return supTellerNo;
	}
	public void setSupTellerNo(String supTellerNo) {
		this.supTellerNo = supTellerNo;
	}
	
}
