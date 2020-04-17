package com.boomhope.tms.message.in.bck;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 
 * Title:唐豆收回查询
 * Description:
 * @author zjg
 * @date 2017年07月04日 下午3:23:47
 */
@XStreamAlias("Body")
public class BCK07622ResBodyBean {

	private String CustNo;//客户号
	private String CustName;//客户名称
	private String ProductCode;//产品代码
	private String OpenDate;//开户日期
	private String DepTerm;//存期
	private String CurBal;//账户当前余额
	private String PayAmt;//支取金额
	private String TermCode;//唐豆期次代码
	private String ExchangeMode;//唐豆兑换方式
	private String OrgExchangeMode;//原唐豆兑换方式
	private String OrgExchangeProp;//原唐豆兑现比例
	private String Back_Count;//收回唐豆数量
	private String Back_Exchange_Amt;//收回兑现金额
	
	public String getCustNo() {
		return CustNo;
	}
	public void setCustNo(String custNo) {
		CustNo = custNo;
	}
	public String getCustName() {
		return CustName;
	}
	public void setCustName(String custName) {
		CustName = custName;
	}
	public String getProductCode() {
		return ProductCode;
	}
	public void setProductCode(String productCode) {
		ProductCode = productCode;
	}
	public String getOpenDate() {
		return OpenDate;
	}
	public void setOpenDate(String openDate) {
		OpenDate = openDate;
	}
	public String getDepTerm() {
		return DepTerm;
	}
	public void setDepTerm(String depTerm) {
		DepTerm = depTerm;
	}
	public String getCurBal() {
		return CurBal;
	}
	public void setCurBal(String curBal) {
		CurBal = curBal;
	}
	public String getPayAmt() {
		return PayAmt;
	}
	public void setPayAmt(String payAmt) {
		PayAmt = payAmt;
	}
	public String getTermCode() {
		return TermCode;
	}
	public void setTermCode(String termCode) {
		TermCode = termCode;
	}
	public String getExchangeMode() {
		return ExchangeMode;
	}
	public void setExchangeMode(String exchangeMode) {
		ExchangeMode = exchangeMode;
	}
	public String getOrgExchangeMode() {
		return OrgExchangeMode;
	}
	public void setOrgExchangeMode(String orgExchangeMode) {
		OrgExchangeMode = orgExchangeMode;
	}
	public String getOrgExchangeProp() {
		return OrgExchangeProp;
	}
	public void setOrgExchangeProp(String orgExchangeProp) {
		OrgExchangeProp = orgExchangeProp;
	}
	public String getBack_Count() {
		return Back_Count;
	}
	public void setBack_Count(String back_Count) {
		Back_Count = back_Count;
	}
	public String getBack_Exchange_Amt() {
		return Back_Exchange_Amt;
	}
	public void setBack_Exchange_Amt(String back_Exchange_Amt) {
		Back_Exchange_Amt = back_Exchange_Amt;
	}
	
}
