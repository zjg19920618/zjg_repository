package com.boomhope.Bill.TransService.BillPrint.Interface;

import org.apache.log4j.Logger;




public class ICSubAccNo {

	static Logger logger = Logger.getLogger(ICSubAccNo.class);
	
	private String subAccNo;//子账号
	private String ATM;//余额
	private String productCode;//产品代码
	private String productName;//产品名称
	private String openInstNo;//开户机构
	private String openInstName;//机构名称
	private String custName;//户名
	private String openDate;//开户日期
	private String startIntDate;//起息日期
	private String endIntDate;//到期日期
	private String openRate;//开户利率
	private String depTerm;//存期
	private String itemNo;//科目号
	private String exchFlag;//自动转存标志(0：不自动转存   1：自动转存)
	private String drawCond;//支付条件(0/无   1/凭密码  2/凭证件  3/凭印鉴   4/凭印鉴和密码)
	private String printerL51Str;//约享存窗口期
	
	private String realAccno;//真实账户
	private String openATM;//开户金额
	private String balance;//结存额
	private String mark;//有效标志
	private String accNoState;//账户状态
	private String channel;//渠道
	private String bill;//凭证号
	private String printState;//打印状态
	
	private String fserialno;//日期+流水号，，电子印章请求参数
	private String savingCountMinStr;	//存期  40月、50月、60月、5年
	public String getSavingCountMinStr(){
		String str = depTerm;
		try
		{
			str=str.trim().toUpperCase();
			if(str.indexOf("M")!=-1){
				if(str.startsWith("0")){
					str = str.substring(1, str.length());
				}
				str=str.replace("M", "个月");
			}else if(str.indexOf("Y")!=-1){
				Integer n=Integer.parseInt(str.replaceAll("\\D",""));
				str=n+"年";
			}else if(str.indexOf("D")!=-1){
				str=str.replace("D", "天");
			}else{
				str =str +"天";
			}
		}
		catch (Exception e)
		{
			logger.error(e);
		}
		return str;
	}
	
	public String getFserialno() {
		return fserialno;
	}
	public void setFserialno(String fserialno) {
		this.fserialno = fserialno;
	}
	private  Boolean check =false;
	
	public String getPrinterL51Str() {
		return printerL51Str;
	}
	public void setPrinterL51Str(String printerL51Str) {
		this.printerL51Str = printerL51Str;
	}
	public String getSubAccNo() {
		return subAccNo;
	}
	public void setSubAccNo(String subAccNo) {
		this.subAccNo = subAccNo;
	}
	public String getATM() {
		return ATM;
	}
	public void setATM(String aTM) {
		ATM = aTM;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getOpenInstNo() {
		return openInstNo;
	}
	public void setOpenInstNo(String openInstNo) {
		this.openInstNo = openInstNo;
	}
	public String getOpenInstName() {
		return openInstName;
	}
	public void setOpenInstName(String openInstName) {
		this.openInstName = openInstName;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getOpenDate() {
		return openDate;
	}
	public void setOpenDate(String openDate) {
		this.openDate = openDate;
	}
	public String getStartIntDate() {
		return startIntDate;
	}
	public void setStartIntDate(String startIntDate) {
		this.startIntDate = startIntDate;
	}
	public String getEndIntDate() {
		return endIntDate;
	}
	public void setEndIntDate(String endIntDate) {
		this.endIntDate = endIntDate;
	}
	public String getOpenRate() {
		return openRate;
	}
	public void setOpenRate(String openRate) {
		this.openRate = openRate;
	}
	public String getDepTerm() {
		return depTerm;
	}
	public void setDepTerm(String depTerm) {
		this.depTerm = depTerm;
	}
	public String getItemNo() {
		return itemNo;
	}
	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}
	public String getExchFlag() {
		return exchFlag;
	}
	public void setExchFlag(String exchFlag) {
		this.exchFlag = exchFlag;
	}
	public String getDrawCond() {
		return drawCond;
	}
	public void setDrawCond(String drawCond) {
		this.drawCond = drawCond;
	}
	public Boolean getCheck() {
		return check;
	}
	public void setCheck(Boolean check) {
		this.check = check;
	}
	public String getRealAccno() {
		return realAccno;
	}
	public void setRealAccno(String realAccno) {
		this.realAccno = realAccno;
	}
	public String getOpenATM() {
		return openATM;
	}
	public void setOpenATM(String openATM) {
		this.openATM = openATM;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	public String getAccNoState() {
		return accNoState;
	}
	public void setAccNoState(String accNoState) {
		this.accNoState = accNoState;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getBill() {
		return bill;
	}
	public void setBill(String bill) {
		this.bill = bill;
	}
	public String getPrintState() {
		return printState;
	}
	public void setPrintState(String printState) {
		this.printState = printState;
	}
	
}
