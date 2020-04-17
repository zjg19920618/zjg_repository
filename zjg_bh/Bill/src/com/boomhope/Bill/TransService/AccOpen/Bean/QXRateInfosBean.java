package com.boomhope.Bill.TransService.AccOpen.Bean;

import com.boomhope.Bill.Util.FieldSort;
import com.boomhope.Bill.Util.Number;



/**
 * 千禧到期利息及方式显示信息查询
 *
 */

@Number(id = 1)
public class QXRateInfosBean {

	//活动奖励文件格式：
	//规则编号||规则名称||活动编号||活动名称||赠送类型（0-利率1-衍生品）||衍生品类型||衍生品名称||明细类型||明细名称
	//||浮动最小可能值||浮动最大可能值||执行浮动值||预付数量||预付标志（0否1是）||失效日期||综合收益率||备用列1||备用列2||备用列3||备用列4\n

	@FieldSort(NO = 0)
	private String ruleNo; //规则编号
	@FieldSort(NO = 1)
	private String ruleName;//规则名称
	@FieldSort(NO = 2)
	private String actNo;//活动编号
	@FieldSort(NO =3)
	private String actName;//活动名称
	@FieldSort(NO =4)
	private String giveType;//赠送类型（0-利率1-衍生品）
	@FieldSort(NO = 5)
	private String derivativeType;//衍生品类型
	@FieldSort(NO = 6)
	private String derivativeName;//衍生品名称
	@FieldSort(NO = 7)
	private String detailType;//明细类型
	@FieldSort(NO = 8)
	private String detailName;//明细名称
	@FieldSort(NO = 9)
	private String minFuVal;//浮动最小可能值
	@FieldSort(NO = 10)
	private String maxFuVal;//浮动最大可能值
	@FieldSort(NO = 11)
	private String doFuVal;//执行浮动值
	@FieldSort(NO = 12)
	private String paySum;//预付数量
	@FieldSort(NO = 13)
	private String payMark;//预付标志（0否1是）
	@FieldSort(NO = 14)
	private String lostDate;//失效日期
	@FieldSort(NO = 15)
	private String zongGetRate;//综合收益率
	@FieldSort(NO = 16)
	private String bei1;//备用列1
	@FieldSort(NO = 17)
	private String bei2;//备用列2
	@FieldSort(NO = 18)
	private String bei3;//备用列3
	@FieldSort(NO = 19)
	private String bei4;//备用列4
	
	private String getInte;//到期利息
	private String getTdSum;//唐豆数量
	
	
	public String getGetInte() {
		return getInte;
	}
	public void setGetInte(String getInte) {
		this.getInte = getInte;
	}
	public String getGetTdSum() {
		return getTdSum;
	}
	public void setGetTdSum(String getTdSum) {
		this.getTdSum = getTdSum;
	}
	public String getRuleNo() {
		return ruleNo;
	}
	public void setRuleNo(String ruleNo) {
		this.ruleNo = ruleNo;
	}
	public String getRuleName() {
		return ruleName;
	}
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
	public String getActNo() {
		return actNo;
	}
	public void setActNo(String actNo) {
		this.actNo = actNo;
	}
	public String getActName() {
		return actName;
	}
	public void setActName(String actName) {
		this.actName = actName;
	}
	public String getGiveType() {
		return giveType;
	}
	public void setGiveType(String giveType) {
		this.giveType = giveType;
	}
	public String getDerivativeType() {
		return derivativeType;
	}
	public void setDerivativeType(String derivativeType) {
		this.derivativeType = derivativeType;
	}
	public String getDerivativeName() {
		return derivativeName;
	}
	public void setDerivativeName(String derivativeName) {
		this.derivativeName = derivativeName;
	}
	public String getDetailType() {
		return detailType;
	}
	public void setDetailType(String detailType) {
		this.detailType = detailType;
	}
	public String getDetailName() {
		return detailName;
	}
	public void setDetailName(String detailName) {
		this.detailName = detailName;
	}
	public String getMinFuVal() {
		return minFuVal;
	}
	public void setMinFuVal(String minFuVal) {
		this.minFuVal = minFuVal;
	}
	public String getMaxFuVal() {
		return maxFuVal;
	}
	public void setMaxFuVal(String maxFuVal) {
		this.maxFuVal = maxFuVal;
	}
	public String getDoFuVal() {
		return doFuVal;
	}
	public void setDoFuVal(String doFuVal) {
		this.doFuVal = doFuVal;
	}
	public String getPaySum() {
		return paySum;
	}
	public void setPaySum(String paySum) {
		this.paySum = paySum;
	}
	public String getPayMark() {
		return payMark;
	}
	public void setPayMark(String payMark) {
		this.payMark = payMark;
	}
	public String getLostDate() {
		return lostDate;
	}
	public void setLostDate(String lostDate) {
		this.lostDate = lostDate;
	}
	public String getZongGetRate() {
		return zongGetRate;
	}
	public void setZongGetRate(String zongGetRate) {
		this.zongGetRate = zongGetRate;
	}
	public String getBei1() {
		return bei1;
	}
	public void setBei1(String bei1) {
		this.bei1 = bei1;
	}
	public String getBei2() {
		return bei2;
	}
	public void setBei2(String bei2) {
		this.bei2 = bei2;
	}
	public String getBei3() {
		return bei3;
	}
	public void setBei3(String bei3) {
		this.bei3 = bei3;
	}
	public String getBei4() {
		return bei4;
	}
	public void setBei4(String bei4) {
		this.bei4 = bei4;
	}
	
	
	
}
