package com.boomhope.Bill.TransService.LostReport.Bean;

import com.boomhope.Bill.Util.FieldSort;
import com.boomhope.Bill.Util.Number;

/***
 *唐豆信息查询
 */
@Number(id = 1)
public class AccTdMsgBean {
	
	//客户号||客户名称||账号||子账号||客户经理编号||客户经理名称||期次代号||开户日期||赠送日期||产品代码||产品名称||存期||账户当前余额||派发数量||收回数量||结存数量||有效标志( 0/1/*有效/无效/作废)||预计兑付金额||预计收回金额||账号开户金额
	
	@FieldSort(NO = 0)
	private String custNo;//客户号
	@FieldSort(NO = 1)
	private String custName;//客户名称
	@FieldSort(NO = 2)
	private String accNo;//账号
	@FieldSort(NO = 3)
	private String subAccNo;//子账户
	@FieldSort(NO = 4)
	private String accManagerNo;//客户经理编号
	@FieldSort(NO = 5)
	private String accManagerName;//客户经理名称
	@FieldSort(NO = 6)
	private String dateNo;//期次代号
	@FieldSort(NO =7)
	private String openDate;//开户日期
	@FieldSort(NO =8)
	private String sendDate;//赠送日期
	@FieldSort(NO =9)
	private String productCode;//产品代码
	@FieldSort(NO =10)
	private String productName;//产品名称
	@FieldSort(NO =11)
	private String depTerm;//存期
	@FieldSort(NO =12)
	private String accNowAmt;//账号当前余额
	@FieldSort(NO = 13)
	private String setTdNum;//派发数量
	@FieldSort(NO = 14)
	private String getTdNum;//收回数量
	@FieldSort(NO = 15)
	private String endTdNum;//结存数量
	@FieldSort(NO = 16)
	private String mark;//有效标志
	@FieldSort(NO = 17)
	private String duifuAmt;//预计兑付金额
	@FieldSort(NO = 18)
	private String shouhuiAmt;//预计收回金额
	@FieldSort(NO = 19)
	private String accOpenAmt;//账号开户金额
	
	public String getCustNo() {
		return custNo;
	}
	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}
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
	public String getAccManagerNo() {
		return accManagerNo;
	}
	public void setAccManagerNo(String accManagerNo) {
		this.accManagerNo = accManagerNo;
	}
	public String getAccManagerName() {
		return accManagerName;
	}
	public void setAccManagerName(String accManagerName) {
		this.accManagerName = accManagerName;
	}
	public String getDateNo() {
		return dateNo;
	}
	public void setDateNo(String dateNo) {
		this.dateNo = dateNo;
	}
	public String getOpenDate() {
		return openDate;
	}
	public void setOpenDate(String openDate) {
		this.openDate = openDate;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getDepTerm() {
		return depTerm;
	}
	public void setDepTerm(String depTerm) {
		this.depTerm = depTerm;
	}
	public String getSetTdNum() {
		return setTdNum;
	}
	public void setSetTdNum(String setTdNum) {
		this.setTdNum = setTdNum;
	}
	public String getGetTdNum() {
		return getTdNum;
	}
	public void setGetTdNum(String getTdNum) {
		this.getTdNum = getTdNum;
	}
	public String getEndTdNum() {
		return endTdNum;
	}
	public void setEndTdNum(String endTdNum) {
		this.endTdNum = endTdNum;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getSendDate() {
		return sendDate;
	}
	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public String getDuifuAmt() {
		return duifuAmt;
	}
	public void setDuifuAmt(String duifuAmt) {
		this.duifuAmt = duifuAmt;
	}
	public String getShouhuiAmt() {
		return shouhuiAmt;
	}
	public void setShouhuiAmt(String shouhuiAmt) {
		this.shouhuiAmt = shouhuiAmt;
	}
	public String getAccNowAmt() {
		return accNowAmt;
	}
	public void setAccNowAmt(String accNowAmt) {
		this.accNowAmt = accNowAmt;
	}
	public String getAccOpenAmt() {
		return accOpenAmt;
	}
	public void setAccOpenAmt(String accOpenAmt) {
		this.accOpenAmt = accOpenAmt;
	}
	
}
