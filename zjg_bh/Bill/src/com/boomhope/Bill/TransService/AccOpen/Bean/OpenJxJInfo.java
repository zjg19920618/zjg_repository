package com.boomhope.Bill.TransService.AccOpen.Bean;

import com.boomhope.Bill.Util.FieldSort;
import com.boomhope.Bill.Util.Number;

/**
 * 加息文件数据
* @ClassName  OpenJxJInfo 
* @Description   
* @author zhang.m 
* @date 2016年12月7日 上午11:22:13
 */
@Number(id = 1)
public class OpenJxJInfo {
	
	@FieldSort(NO = 0)
	private String jxjType; //浮动利率类型(1差异化2加息券)
	@FieldSort(NO = 1)
	private String jxjNo;//浮动类型
	@FieldSort(NO = 2)
	private String jxjName;//规则名
	@FieldSort(NO =3)
	private String jxjMin;//最小可能值
	@FieldSort(NO =4)
	private String jxjMax;//最大可能值
	@FieldSort(NO = 5)
	private String jxjzhixing;//执行浮动值
	@FieldSort(NO = 6)
	private String jxjMoney;//预付利息
	@FieldSort(NO = 7)
	private String jxjBiZi;//预付标志（0否1是）
	@FieldSort(NO = 8)
	private String jxjby1;//备用列1
	@FieldSort(NO = 9)
	private String jxjby2;//备用列2
	@FieldSort(NO = 10)
	private String jxjby3;//备用列3
	@FieldSort(NO = 11)
	private String jxjby4;//备用列4
	@FieldSort(NO = 12)
	private String jxjby5;//备用列5
	
	public String getJxjType() {
		return jxjType;
	}
	public String getJxjzhixing() {
		return jxjzhixing;
	}
	public void setJxjzhixing(String jxjzhixing) {
		this.jxjzhixing = jxjzhixing;
	}
	public void setJxjType(String jxjType) {
		this.jxjType = jxjType;
	}
	public String getJxjNo() {
		return jxjNo;
	}
	public void setJxjNo(String jxjNo) {
		this.jxjNo = jxjNo;
	}
	public String getJxjName() {
		return jxjName;
	}
	public void setJxjName(String jxjName) {
		this.jxjName = jxjName;
	}
	public String getJxjMin() {
		return jxjMin;
	}
	public void setJxjMin(String jxjMin) {
		this.jxjMin = jxjMin;
	}
	public String getJxjMax() {
		return jxjMax;
	}
	public void setJxjMax(String jxjMax) {
		this.jxjMax = jxjMax;
	}
	public String getJxjMoney() {
		return jxjMoney;
	}
	public void setJxjMoney(String jxjMoney) {
		this.jxjMoney = jxjMoney;
	}
	public String getJxjBiZi() {
		return jxjBiZi;
	}
	public void setJxjBiZi(String jxjBiZi) {
		this.jxjBiZi = jxjBiZi;
	}
	public String getJxjby1() {
		return jxjby1;
	}
	public void setJxjby1(String jxjby1) {
		this.jxjby1 = jxjby1;
	}
	public String getJxjby2() {
		return jxjby2;
	}
	public void setJxjby2(String jxjby2) {
		this.jxjby2 = jxjby2;
	}
	public String getJxjby3() {
		return jxjby3;
	}
	public void setJxjby3(String jxjby3) {
		this.jxjby3 = jxjby3;
	}
	public String getJxjby4() {
		return jxjby4;
	}
	public void setJxjby4(String jxjby4) {
		this.jxjby4 = jxjby4;
	}
	public String getJxjby5() {
		return jxjby5;
	}
	public void setJxjby5(String jxjby5) {
		this.jxjby5 = jxjby5;
	}
}
