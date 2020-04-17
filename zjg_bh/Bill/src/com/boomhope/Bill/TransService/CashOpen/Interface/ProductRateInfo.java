package com.boomhope.Bill.TransService.CashOpen.Interface;

import com.boomhope.Bill.Util.FieldSort;
import com.boomhope.Bill.Util.Number;


/***
 * 产品利率信息
 *
 */
@Number(id = 1)
public class ProductRateInfo {

//	查询类型为4时
	//	产品代码||存期||到期利率
	@FieldSort(NO = 0)
	private String productCode;
	@FieldSort(NO = 1)
	private String savingCount;
	@FieldSort(NO = 2)
	private String interestRate;
	@FieldSort(NO = 3)
	private String isPrint;

	public String getProductCode() {
		return productCode;
	}

	public ProductRateInfo setProductCode(String productCode) {
		this.productCode = productCode;
		return this;
	}

	public String getSavingCount() {
		return savingCount;
	}

	public ProductRateInfo setSavingCount(String savingCount) {
		this.savingCount = savingCount;
		return this;
	}

	public String getInterestRate() {
		return interestRate;
	}

	public ProductRateInfo setInterestRate(String interestRate) {
		this.interestRate = interestRate;
		return this;
	}

	public String getIsPrint() {
		return isPrint;
	}

	public ProductRateInfo setIsPrint(String isPrint) {
		this.isPrint = isPrint;
		return this;
	}
	
}
