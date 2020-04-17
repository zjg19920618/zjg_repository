package com.boomhope.Bill.TransService.BillPrint.Interface;

import com.boomhope.Bill.Util.FieldSort;
import com.boomhope.Bill.Util.Number;


/***
 *子账户列表实体类
 */
@Number(id = 1)
public class BillProductInfo2 {

	//卡号||子账号||余额||产品代码||产品名称||开户机构||机构名称||户名||开户日期||起息日期
	//||到期日期||开户利率||存期||科目号||类型（7卡11电子帐户）||自动转存标志(0/不自动  1/自动)
	//||支付条件(0/无   1/凭密码   2/凭证件    3/凭印鉴    4/凭印鉴和密码)
	@FieldSort(NO = 0)
	private String accNo;//卡号
	@FieldSort(NO = 1)
	private String subAccNo;//子账号
	@FieldSort(NO = 2)
	private String realAccno;//真实账户
	@FieldSort(NO = 3)
	private String openInstNo;//开户机构
	@FieldSort(NO = 4)
	private String openDate;//开户日期
	@FieldSort(NO = 5)
	private String endIntDate;//到期日期
	@FieldSort(NO =6)
	private String openATM;//开户金额
	@FieldSort(NO =7)
	private String productCode;//产品代码
	@FieldSort(NO =8)
	private String productName;//产品名称
	@FieldSort(NO = 9)
	private String depTerm;//存期
	@FieldSort(NO = 10)
	private String openRate;//开户利率
	@FieldSort(NO = 11)
	private String balance;//结存额
	@FieldSort(NO = 12)
	private String mark;//有效标志
	@FieldSort(NO = 13)
	private String accNoState;//账户状态
	@FieldSort(NO = 14)
	private String channel;//渠道
	@FieldSort(NO = 15)
	private String bill;//凭证号
	@FieldSort(NO = 16)
	private String printState;//打印状态
	public String getAccNo() {
		return accNo;
	}
	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}
	public String getSubAccNo() {
		return subAccNo;
	}
	public void setSubAccNo(String subAccNo) {
		this.subAccNo = subAccNo;
	}
	public String getRealAccno() {
		return realAccno;
	}
	public void setRealAccno(String realAccno) {
		this.realAccno = realAccno;
	}
	public String getOpenInstNo() {
		return openInstNo;
	}
	public void setOpenInstNo(String openInstNo) {
		this.openInstNo = openInstNo;
	}
	public String getOpenDate() {
		return openDate;
	}
	public void setOpenDate(String openDate) {
		this.openDate = openDate;
	}
	public String getEndIntDate() {
		return endIntDate;
	}
	public void setEndIntDate(String endIntDate) {
		this.endIntDate = endIntDate;
	}
	public String getOpenATM() {
		return openATM;
	}
	public void setOpenATM(String openATM) {
		this.openATM = openATM;
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
	public String getDepTerm() {
		return depTerm;
	}
	public void setDepTerm(String depTerm) {
		this.depTerm = depTerm;
	}
	public String getOpenRate() {
		return openRate;
	}
	public void setOpenRate(String openRate) {
		this.openRate = openRate;
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
