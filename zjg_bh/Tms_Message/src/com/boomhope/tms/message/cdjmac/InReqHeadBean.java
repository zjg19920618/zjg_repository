package com.boomhope.tms.message.cdjmac;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 * 存单机请求报文头Bean
 * @author shaopeng
 *
 */
@XStreamAlias("Head")  
public class InReqHeadBean {
	
	private String TradeCode;	// 交易代码
	private String MachineNo;	// 机器编号
	private String MachineType;	// 机器类型
	private String BranchNo;	// 机构号
	private String ProductCode;	// 产品代码
	private String supTellerNo;	// 授权柜员号

	
	public void setTradeCode(String tradeCode){
		this.TradeCode = tradeCode;
	}
	
	public String getTradeCode(){
		return this.TradeCode;
	}
	
	public void setMachineNo(String machineNo){
		this.MachineNo = machineNo;
	}
	
	public String getMachineNo(){
		return this.MachineNo;
	}
	
	public void setMachineType(String machineType){
		this.MachineType = machineType;
	}
	
	public String getMachineType(){
		return this.MachineType;
	}
	
	public void setBranchNo(String branchNo){
		this.BranchNo = branchNo;
	}
	
	public String getBranchNo(){
		return this.BranchNo;
	}
	
	public void setProductCode(String productCode){
		this.ProductCode = productCode;
	}
	
	public String getProductCode(){
		return this.ProductCode;
	}

	public String getSupTellerNo() {
		return supTellerNo;
	}

	public void setSupTellerNo(String supTellerNo) {
		this.supTellerNo = supTellerNo;
	}

}
