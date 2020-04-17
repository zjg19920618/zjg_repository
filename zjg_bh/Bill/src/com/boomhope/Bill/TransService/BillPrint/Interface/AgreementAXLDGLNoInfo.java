package com.boomhope.Bill.TransService.BillPrint.Interface;

import com.boomhope.Bill.Util.FieldSort;
import com.boomhope.Bill.Util.Number;

/**
 * 安享存他立得存收益账号关联查询
 * @author Administrator
 *
 */
@Number(id = 1)
public class AgreementAXLDGLNoInfo {
	//账号||子账号1||户名||产品名称||收益卡号||子账号2||解约日期
	
	@FieldSort(NO = 0)
	private String accNo;//账号
	@FieldSort(NO = 1)
	private String subAccNo;//子账号
	@FieldSort(NO = 2)
	private String accName;//户名
	@FieldSort(NO = 3)
	private String productName;//产品名称
	@FieldSort(NO = 4)
	private String getCardNo;//收益卡号
	@FieldSort(NO = 5)
	private String subAccNo2;//子账号2
	@FieldSort(NO = 6)
	private String outDate;//解约日期
	
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
	public String getAccName() {
		return accName;
	}
	public void setAccName(String accName) {
		this.accName = accName;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getGetCardNo() {
		return getCardNo;
	}
	public void setGetCardNo(String getCardNo) {
		this.getCardNo = getCardNo;
	}
	public String getSubAccNo2() {
		return subAccNo2;
	}
	public void setSubAccNo2(String subAccNo2) {
		this.subAccNo2 = subAccNo2;
	}
	public String getOutDate() {
		return outDate;
	}
	public void setOutDate(String outDate) {
		this.outDate = outDate;
	}
	
	
}
