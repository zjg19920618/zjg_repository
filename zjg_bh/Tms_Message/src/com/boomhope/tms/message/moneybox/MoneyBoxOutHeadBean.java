package com.boomhope.tms.message.moneybox;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 * 报文头Bean
 */
@XStreamAlias("MessageHead")  
public class MoneyBoxOutHeadBean {

	private String ServiceName="";//服务名
	private String MethodName="";//接口名
	private String BranchNum="";//分行机构号
	private String SubBranchNum="";//支行机构号
	private String Channel="";
	private String TermNum="";
	private String TransDate="";
	public String getServiceName() {
		return ServiceName;
	}
	public void setServiceName(String serviceName) {
		ServiceName = serviceName;
	}
	public String getMethodName() {
		return MethodName;
	}
	public void setMethodName(String methodName) {
		MethodName = methodName;
	}
	public String getBranchNum() {
		return BranchNum;
	}
	public void setBranchNum(String branchNum) {
		BranchNum = branchNum;
	}
	public String getSubBranchNum() {
		return SubBranchNum;
	}
	public void setSubBranchNum(String subBranchNum) {
		SubBranchNum = subBranchNum;
	}
	public String getChannel() {
		return Channel;
	}
	public void setChannel(String channel) {
		Channel = channel;
	}
	public String getTermNum() {
		return TermNum;
	}
	public void setTermNum(String termNum) {
		TermNum = termNum;
	}
	public String getTransDate() {
		return TransDate;
	}
	public void setTransDate(String transDate) {
		TransDate = transDate;
	}
}
