package com.boomhope.Bill.TransService.BillChOpen.Bean;

import com.boomhope.Bill.Util.FieldSort;
import com.boomhope.Bill.Util.Number;

@Number(id = 1)
public class BackPrintBillBean {
   
	//账号||客户号||产品代码||户名||币种||开户金额||结存额||存入日||起息日||到期日||存期||利率||支取方式||自动转存标志||预计利息
	@FieldSort(NO = 0)
	private  String  Acc_no;//账号号
	@FieldSort(NO = 1)
	private String cust_no;//领卡日期
	@FieldSort(NO = 2)
	private String procode;//开户机构
	@FieldSort(NO = 3)
	private  String  Acc_name;//户名
	@FieldSort(NO = 4)
	private String moneyType;//币种
	@FieldSort(NO = 5)
	private String open_amt;//开户金额
	@FieldSort(NO = 6)
	private  String  endAmt;//结存额
	@FieldSort(NO = 7)
	private String open_Date;//存入日
	@FieldSort(NO = 8)
	private String start_Data;//起息日
	@FieldSort(NO = 9)
	private  String  end_Data;//到期日
	@FieldSort(NO = 10)
	private String   deptime;//存期
	@FieldSort(NO = 11)
	private String   rate;//利率
	@FieldSort(NO = 12)
	private  String  openmoney;//支取方式
	@FieldSort(NO = 13)
	private String exchFlag;//自动转存标志
	@FieldSort(NO = 14)
	private String routing;//预计利息
	@FieldSort(NO = 15)
	private String printData;//打印日期
	@FieldSort(NO = 16)
	private String printTime;//打印日期
	public String getAcc_no() {
		return Acc_no;
	}
	public void setAcc_no(String acc_no) {
		Acc_no = acc_no;
	}
	public String getCust_no() {
		return cust_no;
	}
	public void setCust_no(String cust_no) {
		this.cust_no = cust_no;
	}
	public String getProcode() {
		return procode;
	}
	public void setProcode(String procode) {
		this.procode = procode;
	}
	public String getAcc_name() {
		return Acc_name;
	}
	public void setAcc_name(String acc_name) {
		Acc_name = acc_name;
	}
	public String getMoneyType() {
		return moneyType;
	}
	public void setMoneyType(String moneyType) {
		this.moneyType = moneyType;
	}
	public String getOpen_amt() {
		return open_amt;
	}
	public void setOpen_amt(String open_amt) {
		this.open_amt = open_amt;
	}
	public String getEndAmt() {
		return endAmt;
	}
	public void setEndAmt(String endAmt) {
		this.endAmt = endAmt;
	}
	public String getOpen_Date() {
		return open_Date;
	}
	public void setOpen_Date(String open_Date) {
		this.open_Date = open_Date;
	}
	public String getStart_Data() {
		return start_Data;
	}
	public void setStart_Data(String start_Data) {
		this.start_Data = start_Data;
	}
	public String getEnd_Data() {
		return end_Data;
	}
	public void setEnd_Data(String end_Data) {
		this.end_Data = end_Data;
	}
	public String getDeptime() {
		return deptime;
	}
	public void setDeptime(String deptime) {
		this.deptime = deptime;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public String getOpenmoney() {
		return openmoney;
	}
	public void setOpenmoney(String openmoney) {
		this.openmoney = openmoney;
	}
	public String getExchFlag() {
		return exchFlag;
	}
	public void setExchFlag(String exchFlag) {
		this.exchFlag = exchFlag;
	}
	public String getRouting() {
		return routing;
	}
	public void setRouting(String routing) {
		this.routing = routing;
	}
	public String getPrintData() {
		return printData;
	}
	public void setPrintData(String printData) {
		this.printData = printData;
	}
	public String getPrintTime() {
		return printTime;
	}
	public void setPrintTime(String printTime) {
		this.printTime = printTime;
	}
	
	
}
