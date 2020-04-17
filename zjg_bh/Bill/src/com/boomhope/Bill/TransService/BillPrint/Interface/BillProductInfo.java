package com.boomhope.Bill.TransService.BillPrint.Interface;

import com.boomhope.Bill.Util.FieldSort;
import com.boomhope.Bill.Util.Number;


/***
 *子账户打印存单实体类
 */
@Number(id = 1)
public class BillProductInfo {

	//卡号||子账号||余额||产品代码||产品名称||开户机构||机构名称||户名||开户日期||起息日期
	//||到期日期||开户利率||存期||科目号||类型（7卡11电子帐户）||自动转存标志(0/不自动  1/自动)
	//||支付条件(0/无   1/凭密码   2/凭证件    3/凭印鉴    4/凭印鉴和密码)
	@FieldSort(NO = 0)
	private String accNo;//卡号
	@FieldSort(NO = 1)
	private String subAccNo;//子账号
	@FieldSort(NO = 2)
	private String ATM;//余额
	@FieldSort(NO = 3)
	private String productCode;//产品代码
	@FieldSort(NO = 4)
	private String productName;//产品名称
	@FieldSort(NO = 5)
	private String openInstNo;//开户机构
	@FieldSort(NO = 6)
	private String openInstName;//机构名称
	@FieldSort(NO = 7)
	private String custName;//户名
	@FieldSort(NO = 8)
	private String openDate;//开户日期
	@FieldSort(NO = 9)
	private String startIntDate;//起息日期
	@FieldSort(NO = 10)
	private String endIntDate;//到期日期
	@FieldSort(NO = 11)
	private String openRate;//开户利率
	@FieldSort(NO = 12)
	private String depTerm;//存期
	@FieldSort(NO = 13)
	private String itemNo;//科目号
	@FieldSort(NO = 14)
	private String type;//类型（7卡11电子帐户）
	@FieldSort(NO = 15)
	private String exchFlag;//自动转存标志(0：不自动转存   1：自动转存)
	@FieldSort(NO = 16)
	private String drawCond;//支付条件(0/无   1/凭密码  2/凭证件  3/凭印鉴   4/凭印鉴和密码)

	public String getProductCode() {
		return productCode;
	}

	public BillProductInfo setProductCode(String productCode) {
		this.productCode = productCode;
		return this;
	}

	public String getAccNo() {
		return accNo;
	}

	public BillProductInfo setAccNo(String accNo) {
		this.accNo = accNo;
		return this;
	}

	public String getSubAccNo() {
		return subAccNo;
	}

	public BillProductInfo setSubAccNo(String subAccNo) {
		this.subAccNo = subAccNo;
		return this;
	}

	public String getATM() {
		return ATM;
	}

	public BillProductInfo setATM(String aTM) {
		ATM = aTM;
		return this;
	}

	public String getProductName() {
		return productName;
	}

	public BillProductInfo setProductName(String productName) {
		this.productName = productName;
		return this;
	}

	public String getOpenInstNo() {
		return openInstNo;
	}

	public BillProductInfo setOpenInstNo(String openInstNo) {
		this.openInstNo = openInstNo;
		return this;
	}

	public String getOpenInstName() {
		return openInstName;
	}

	public BillProductInfo setOpenInstName(String openInstName) {
		this.openInstName = openInstName;
		return this;
	}

	public String getCustName() {
		return custName;
	}

	public BillProductInfo setCustName(String custName) {
		this.custName = custName;
		return this;
	}

	public String getOpenDate() {
		return openDate;
	}

	public BillProductInfo setOpenDate(String openDate) {
		this.openDate = openDate;
		return this;
	}

	public String getStartIntDate() {
		return startIntDate;
	}

	public BillProductInfo setStartIntDate(String startIntDate) {
		this.startIntDate = startIntDate;
		return this;
	}

	public String getEndIntDate() {
		return endIntDate;
	}

	public BillProductInfo setEndIntDate(String endIntDate) {
		this.endIntDate = endIntDate;
		return this;
	}

	public String getOpenRate() {
		return openRate;
	}

	public BillProductInfo setOpenRate(String openRate) {
		this.openRate = openRate;
		return this;
	}

	public String getDepTerm() {
		return depTerm;
	}

	public BillProductInfo setDepTerm(String depTerm) {
		this.depTerm = depTerm;
		return this;
	}

	public String getItemNo() {
		return itemNo;
	}

	public BillProductInfo setItemNo(String itemNo) {
		this.itemNo = itemNo;
		return this;
	}

	public String getType() {
		return type;
	}

	public BillProductInfo setType(String type) {
		this.type = type;
		return this;
	}

	public String getExchFlag() {
		return exchFlag;
	}

	public BillProductInfo setExchFlag(String exchFlag) {
		this.exchFlag = exchFlag;
		return this;
	}

	public String getDrawCond() {
		return drawCond;
	}

	public BillProductInfo setDrawCond(String drawCond) {
		this.drawCond = drawCond;
		return this;
	}

	
}
