package com.boomhope.Bill.TransService.BillPrint.Interface;

import com.boomhope.Bill.Util.FieldSort;
import com.boomhope.Bill.Util.Number;

/**
 * 积享存和如意加产品关联产品查询
 * @author Administrator
 *
 */
@Number(id = 1)
public class AgreementJXRJGLNoInfo {

	//账号||子账号||户名||产品名称||关联账号||关联子账号||扣款账号||解约日期
	
	@FieldSort(NO = 0)
	private String accNo;//账号
	@FieldSort(NO = 1)
	private String subAccNo;//子账号
	@FieldSort(NO = 2)
	private String accName;//户名
	@FieldSort(NO = 3)
	private String productName;//产品名称
	@FieldSort(NO = 4)
	private String cognateAccNo;//关联账号
	@FieldSort(NO = 5)
	private String cognateSubAccNo;//关联子账号
	@FieldSort(NO = 6)
	private String pushMoneyAccNo;//扣款账号
	@FieldSort(NO = 7)
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
	public String getCognateAccNo() {
		return cognateAccNo;
	}
	public void setCognateAccNo(String cognateAccNo) {
		this.cognateAccNo = cognateAccNo;
	}
	public String getCognateSubAccNo() {
		return cognateSubAccNo;
	}
	public void setCognateSubAccNo(String cognateSubAccNo) {
		this.cognateSubAccNo = cognateSubAccNo;
	}
	public String getPushMoneyAccNo() {
		return pushMoneyAccNo;
	}
	public void setPushMoneyAccNo(String pushMoneyAccNo) {
		this.pushMoneyAccNo = pushMoneyAccNo;
	}
	public String getOutDate() {
		return outDate;
	}
	public void setOutDate(String outDate) {
		this.outDate = outDate;
	}
	
	
	
	
}
