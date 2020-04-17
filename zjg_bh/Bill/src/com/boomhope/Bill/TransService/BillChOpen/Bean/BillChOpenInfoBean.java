package com.boomhope.Bill.TransService.BillChOpen.Bean;

import com.boomhope.Bill.Util.FieldSort;
import com.boomhope.Bill.Util.Number;


/**
 * 个人定期存单换开【02813】-前置07516
 */
@Number(id = 1)
public class BillChOpenInfoBean {
	
	//账号||客户号||产品代码||户名||币种||开户金额||结存额||存入日||起息日||到期日||存期||利率||支取方式||自动转存标志||预计利息
	@FieldSort(NO = 0)
	private String bAccNo;//账号
	@FieldSort(NO = 1)
	private String bCustNo;//客户号
	@FieldSort(NO = 2)
	private String bProductCode;//产品代码
	@FieldSort(NO = 3)
	private String bAccName;//户名
	@FieldSort(NO = 4)
	private String bCurrencyType;//币种
	@FieldSort(NO = 5)
	private String bOpenATM;//开户金额
	@FieldSort(NO = 6)
	private String bBalance;//结存额
	@FieldSort(NO = 7)
	private String bCreateTime;//存入日
	@FieldSort(NO = 8)
	private String bValueDate;//起息日
	@FieldSort(NO = 9)
	private String bEndTime;//到期日
	@FieldSort(NO = 10)
	private String bMonthsUpperStr;//存期
	@FieldSort(NO = 11)
	private String bRate;//利率
	@FieldSort(NO = 12)
	private String bDrawCond;//支取方式
	@FieldSort(NO = 13)
	private String bAutoRedpFlag;//自动转存标志
	@FieldSort(NO = 14)
	private String bRouting;//预计利息
	
	
	public String getbAccNo() {
		return bAccNo;
	}
	public void setbAccNo(String bAccNo) {
		this.bAccNo = bAccNo;
	}
	public String getbCustNo() {
		return bCustNo;
	}
	public void setbCustNo(String bCustNo) {
		this.bCustNo = bCustNo;
	}
	public String getbProductCode() {
		return bProductCode;
	}
	public void setbProductCode(String bProductCode) {
		this.bProductCode = bProductCode;
	}
	public String getbAccName() {
		return bAccName;
	}
	public void setbAccName(String bAccName) {
		this.bAccName = bAccName;
	}
	public String getbCurrencyType() {
		return bCurrencyType;
	}
	public void setbCurrencyType(String bCurrencyType) {
		this.bCurrencyType = bCurrencyType;
	}
	public String getbOpenATM() {
		return bOpenATM;
	}
	public void setbOpenATM(String bOpenATM) {
		this.bOpenATM = bOpenATM;
	}
	public String getbBalance() {
		return bBalance;
	}
	public void setbBalance(String bBalance) {
		this.bBalance = bBalance;
	}
	public String getbCreateTime() {
		return bCreateTime;
	}
	public void setbCreateTime(String bCreateTime) {
		this.bCreateTime = bCreateTime;
	}
	public String getbValueDate() {
		return bValueDate;
	}
	public void setbValueDate(String bValueDate) {
		this.bValueDate = bValueDate;
	}
	public String getbEndTime() {
		return bEndTime;
	}
	public void setbEndTime(String bEndTime) {
		this.bEndTime = bEndTime;
	}
	public String getbMonthsUpperStr() {
		return bMonthsUpperStr;
	}
	public void setbMonthsUpperStr(String bMonthsUpperStr) {
		this.bMonthsUpperStr = bMonthsUpperStr;
	}
	public String getbRate() {
		return bRate;
	}
	public void setbRate(String bRate) {
		this.bRate = bRate;
	}
	public String getbDrawCond() {
		return bDrawCond;
	}
	public void setbDrawCond(String bDrawCond) {
		this.bDrawCond = bDrawCond;
	}
	public String getbAutoRedpFlag() {
		return bAutoRedpFlag;
	}
	public void setbAutoRedpFlag(String bAutoRedpFlag) {
		this.bAutoRedpFlag = bAutoRedpFlag;
	}
	public String getbRouting() {
		return bRouting;
	}
	public void setbRouting(String bRouting) {
		this.bRouting = bRouting;
	}
	
	
	
	
}
