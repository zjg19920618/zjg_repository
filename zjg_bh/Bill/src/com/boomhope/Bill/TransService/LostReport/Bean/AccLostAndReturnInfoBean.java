package com.boomhope.Bill.TransService.LostReport.Bean;

import com.boomhope.Bill.Util.FieldSort;
import com.boomhope.Bill.Util.Number;

/***
 *存单挂失补发返回文件信息
 */
@Number(id = 1)
public class AccLostAndReturnInfoBean {
	
	// 账号||客户号||产品代码||户名||币种||开户金额||结存额||存入日||起息日||
	//到期日||存期||利率||支取方式||自动转存标志||预计利息
	
	@FieldSort(NO = 0)
	private String accNo;//账号
	@FieldSort(NO = 1)
	private String custNo;//客户号
	@FieldSort(NO = 2)
	private String proCode;//产品代码
	@FieldSort(NO = 3)
	private String custName;//户名
	@FieldSort(NO = 4)
	private String currency;//币种
	@FieldSort(NO = 5)
	private String openATM;//开户金额
	@FieldSort(NO =6)
	private String balance;//结存额
	@FieldSort(NO =7)
	private String openDate;//存入日
	@FieldSort(NO =8)
	private String startRateDate;//起息日
	@FieldSort(NO = 9)
	private String endIntDate;//到期日
	@FieldSort(NO = 10)
	private String depTerm;//存期
	@FieldSort(NO = 11)
	private String openRate;//开户利率
	@FieldSort(NO = 12)
	private String getStype;//支取方式
	@FieldSort(NO = 13)
	private String reMark;//自动转存标志
	@FieldSort(NO = 14)
	private String rateSum;//预计利息
	
	
	public String getAccNo() {
		return accNo;
	}
	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}
	public String getCustNo() {
		return custNo;
	}
	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}
	public String getProCode() {
		return proCode;
	}
	public void setProCode(String proCode) {
		this.proCode = proCode;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
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
	public String getOpenDate() {
		return openDate;
	}
	public void setOpenDate(String openDate) {
		this.openDate = openDate;
	}
	public String getStartRateDate() {
		return startRateDate;
	}
	public void setStartRateDate(String startRateDate) {
		this.startRateDate = startRateDate;
	}
	public String getEndIntDate() {
		return endIntDate;
	}
	public void setEndIntDate(String endIntDate) {
		this.endIntDate = endIntDate;
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
	public String getGetStype() {
		return getStype;
	}
	public void setGetStype(String getStype) {
		this.getStype = getStype;
	}
	public String getReMark() {
		return reMark;
	}
	public void setReMark(String reMark) {
		this.reMark = reMark;
	}
	public String getRateSum() {
		return rateSum;
	}
	public void setRateSum(String rateSum) {
		this.rateSum = rateSum;
	}
	
	
	
	
}
