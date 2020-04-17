package com.boomhope.Bill.TransService.AccOpen.Bean;

import java.text.DecimalFormat;
import java.util.Calendar;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Util.DateTool;
import com.boomhope.Bill.Util.FieldSort;
import com.boomhope.Bill.Util.Number;


/**
 * 开户衍生品返回数据 02808和03870
 * @author Administrator
 *
 */
@Number(id = 1)
public class accOpenProFileBean02808And03870
{
	static Logger logger = Logger.getLogger(accOpenProFileBean02808And03870.class);
//	衍生品类型||衍生品名称||明细类型||明细名称||浮动类型规则号||规则名||浮动最小可能值||浮动最大可能值||执行浮动值||预付数量||预付标志（0否1是）||失效日期||备用列1||备用列2||备用列3||备用列4||备用列5\n
	@FieldSort(NO = 0)
	private String derivativeType;			//衍生品类型
	@FieldSort(NO = 1)
	private String derivativeName;			//衍生品名称
	@FieldSort(NO = 2)
	private String derivativeTypeInfo;			//明细类型
	@FieldSort(NO = 3)
	private String derivativeTypeName;		//明细名称
	@FieldSort(NO = 4)
	private String floatRlueNo;			//浮动类型规则号
	@FieldSort(NO = 5)
	private String rlueName;		//规则名
	@FieldSort(NO = 6)
	private String minFloatVal;		//浮动最小可能值
	@FieldSort(NO = 7)
	private String maxFloatVal;		//浮动最大可能值
	@FieldSort(NO = 8)
	private String doFloatVal;  //执行浮动值
	@FieldSort(NO = 9)
	private String payNums;  //预付数量
	@FieldSort(NO = 10)
	private String payMark;  //预付标志（0否1是）
	@FieldSort(NO = 11)
	private String lostDate;  //失效日期
	@FieldSort(NO = 12)
	private String readyClumn1;  //备用列1
	@FieldSort(NO = 13)
	private String readyClumn2;  //备用列2
	@FieldSort(NO = 14)
	private String readyClumn3;  //备用列3
	@FieldSort(NO = 15)
	private String readyClumn4;  //备用列4
	@FieldSort(NO = 16)
	private String readyClumn5;  //备用列5
	public static Logger getLogger() {
		return logger;
	}
	public static void setLogger(Logger logger) {
		accOpenProFileBean02808And03870.logger = logger;
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
	public String getDerivativeTypeInfo() {
		return derivativeTypeInfo;
	}
	public void setDerivativeTypeInfo(String derivativeTypeInfo) {
		this.derivativeTypeInfo = derivativeTypeInfo;
	}
	public String getDerivativeTypeName() {
		return derivativeTypeName;
	}
	public void setDerivativeTypeName(String derivativeTypeName) {
		this.derivativeTypeName = derivativeTypeName;
	}
	public String getFloatRlueNo() {
		return floatRlueNo;
	}
	public void setFloatRlueNo(String floatRlueNo) {
		this.floatRlueNo = floatRlueNo;
	}
	public String getRlueName() {
		return rlueName;
	}
	public void setRlueName(String rlueName) {
		this.rlueName = rlueName;
	}
	public String getMinFloatVal() {
		return minFloatVal;
	}
	public void setMinFloatVal(String minFloatVal) {
		this.minFloatVal = minFloatVal;
	}
	public String getMaxFloatVal() {
		return maxFloatVal;
	}
	public void setMaxFloatVal(String maxFloatVal) {
		this.maxFloatVal = maxFloatVal;
	}
	public String getDoFloatVal() {
		return doFloatVal;
	}
	public void setDoFloatVal(String doFloatVal) {
		this.doFloatVal = doFloatVal;
	}
	public String getPayNums() {
		return payNums;
	}
	public void setPayNums(String payNums) {
		this.payNums = payNums;
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
	public String getReadyClumn1() {
		return readyClumn1;
	}
	public void setReadyClumn1(String readyClumn1) {
		this.readyClumn1 = readyClumn1;
	}
	public String getReadyClumn2() {
		return readyClumn2;
	}
	public void setReadyClumn2(String readyClumn2) {
		this.readyClumn2 = readyClumn2;
	}
	public String getReadyClumn3() {
		return readyClumn3;
	}
	public void setReadyClumn3(String readyClumn3) {
		this.readyClumn3 = readyClumn3;
	}
	public String getReadyClumn4() {
		return readyClumn4;
	}
	public void setReadyClumn4(String readyClumn4) {
		this.readyClumn4 = readyClumn4;
	}
	public String getReadyClumn5() {
		return readyClumn5;
	}
	public void setReadyClumn5(String readyClumn5) {
		this.readyClumn5 = readyClumn5;
	}
	
	
	
}
