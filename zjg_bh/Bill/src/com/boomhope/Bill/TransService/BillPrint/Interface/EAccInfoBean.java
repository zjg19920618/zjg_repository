package com.boomhope.Bill.TransService.BillPrint.Interface;

import com.boomhope.Bill.Util.FieldSort;
import com.boomhope.Bill.Util.Number;


/**
 * 电子账户子账户列表查询【35109】（直连电子账户）-前置07819
 */
@Number(id = 1)
public class EAccInfoBean {
	
	//卡号||子账号||实际账号||开户机构||开户日||开户金额||产品代码||产品名称||存期||开户利率||结存额||有效标志（0_正常 *_作废 Q_销户）||
	//账号状态||凭证号||打印状态（0/其他-不需要打印，1-待打印，2-以打印）||到期日
	
	@FieldSort(NO = 0)
	private String eCardNo;
	@FieldSort(NO = 1)
	private String eSubAccNo;
	@FieldSort(NO = 2)
	private String eRealAccNo;
	@FieldSort(NO = 3)
	private String eOpenInstNo;//开户机构
	@FieldSort(NO = 4)
	private String eOpenDate;//开户日
	@FieldSort(NO = 5)
	private String eOpenATM;//开户金额
	@FieldSort(NO = 6)
	private String eProductCode;//产品代码
	@FieldSort(NO = 7)
	private String eProductName;//产品名称
	@FieldSort(NO = 8)
	private String eDepTerm;//存期
	@FieldSort(NO = 9)
	private String eOpenRate;//开户利率
	@FieldSort(NO = 10)
	private String eBalance;//结存额
	@FieldSort(NO = 11)
	private String eMark;//有效标志
	@FieldSort(NO = 12)
	private String eAccNoState;//账户状态
	@FieldSort(NO = 13)
	private String eCertNo;//凭证号
	@FieldSort(NO = 14)
	private String ePrintState;//打印状态（0/其他-不需要打印，1-待打印，2-以打印）
	@FieldSort(NO = 15)
	private String eEndDate;//到期日
	
	
	public String geteCardNo() {
		return eCardNo;
	}
	public void seteCardNo(String eCardNo) {
		this.eCardNo = eCardNo;
	}
	public String geteSubAccNo() {
		return eSubAccNo;
	}
	public void seteSubAccNo(String eSubAccNo) {
		this.eSubAccNo = eSubAccNo;
	}
	public String geteRealAccNo() {
		return eRealAccNo;
	}
	public void seteRealAccNo(String eRealAccNo) {
		this.eRealAccNo = eRealAccNo;
	}
	public String geteOpenInstNo() {
		return eOpenInstNo;
	}
	public void seteOpenInstNo(String eOpenInstNo) {
		this.eOpenInstNo = eOpenInstNo;
	}
	public String geteOpenDate() {
		return eOpenDate;
	}
	public void seteOpenDate(String eOpenDate) {
		this.eOpenDate = eOpenDate;
	}
	public String geteOpenATM() {
		return eOpenATM;
	}
	public void seteOpenATM(String eOpenATM) {
		this.eOpenATM = eOpenATM;
	}
	public String geteProductCode() {
		return eProductCode;
	}
	public void seteProductCode(String eProductCode) {
		this.eProductCode = eProductCode;
	}
	public String geteProductName() {
		return eProductName;
	}
	public void seteProductName(String eProductName) {
		this.eProductName = eProductName;
	}
	public String geteDepTerm() {
		return eDepTerm;
	}
	public void seteDepTerm(String eDepTerm) {
		this.eDepTerm = eDepTerm;
	}
	public String geteOpenRate() {
		return eOpenRate;
	}
	public void seteOpenRate(String eOpenRate) {
		this.eOpenRate = eOpenRate;
	}
	public String geteBalance() {
		return eBalance;
	}
	public void seteBalance(String eBalance) {
		this.eBalance = eBalance;
	}
	public String geteMark() {
		return eMark;
	}
	public void seteMark(String eMark) {
		this.eMark = eMark;
	}
	public String geteAccNoState() {
		return eAccNoState;
	}
	public void seteAccNoState(String eAccNoState) {
		this.eAccNoState = eAccNoState;
	}
	public String geteCertNo() {
		return eCertNo;
	}
	public void seteCertNo(String eCertNo) {
		this.eCertNo = eCertNo;
	}
	public String getePrintState() {
		return ePrintState;
	}
	public void setePrintState(String ePrintState) {
		this.ePrintState = ePrintState;
	}
	public String geteEndDate() {
		return eEndDate;
	}
	public void seteEndDate(String eEndDate) {
		this.eEndDate = eEndDate;
	}
	
}
