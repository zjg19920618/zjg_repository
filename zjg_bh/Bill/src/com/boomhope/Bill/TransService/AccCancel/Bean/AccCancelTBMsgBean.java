package com.boomhope.Bill.TransService.AccCancel.Bean;

import com.boomhope.Bill.Util.FieldSort;
import com.boomhope.Bill.Util.Number;

/**
 * 转入唐宝账户查询【55060】-前置07498
 * @author hk
 *
 */
@Number(id = 1)
public class AccCancelTBMsgBean {

	//卡号||子账号||产品代码||结存额||证件类别||证件号码||客户号||产品总额度||产品已使用额度||产品实时利率
	@FieldSort(NO = 0)
	private String cardNo;//卡号
	@FieldSort(NO = 1)
	private String subAcctNo;//子账号
	@FieldSort(NO = 2)
	private String prodectCode;//产品代码
	@FieldSort(NO = 3)
	private String endAmt;//结存额
	@FieldSort(NO = 4)
	private String idType;//证件类别
	@FieldSort(NO = 5)
	private String idNo;//证件号码
	@FieldSort(NO = 6)
	private String custNo;//客户号
	@FieldSort(NO = 7)
	private String proTotalAmt;//产品总额度
	@FieldSort(NO = 8)
	private String proUsedAmt;//产品已使用额度 
	@FieldSort(NO = 9)
	private String proRate;//产品实时利率
	
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getSubAcctNo() {
		return subAcctNo;
	}
	public void setSubAcctNo(String subAcctNo) {
		this.subAcctNo = subAcctNo;
	}
	public String getProdectCode() {
		return prodectCode;
	}
	public void setProdectCode(String prodectCode) {
		this.prodectCode = prodectCode;
	}
	public String getEndAmt() {
		return endAmt;
	}
	public void setEndAmt(String endAmt) {
		this.endAmt = endAmt;
	}
	public String getIdType() {
		return idType;
	}
	public void setIdType(String idType) {
		this.idType = idType;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public String getCustNo() {
		return custNo;
	}
	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}
	public String getProTotalAmt() {
		return proTotalAmt;
	}
	public void setProTotalAmt(String proTotalAmt) {
		this.proTotalAmt = proTotalAmt;
	}
	public String getProUsedAmt() {
		return proUsedAmt;
	}
	public void setProUsedAmt(String proUsedAmt) {
		this.proUsedAmt = proUsedAmt;
	}
	public String getProRate() {
		return proRate;
	}
	public void setProRate(String proRate) {
		this.proRate = proRate;
	}

	

}
