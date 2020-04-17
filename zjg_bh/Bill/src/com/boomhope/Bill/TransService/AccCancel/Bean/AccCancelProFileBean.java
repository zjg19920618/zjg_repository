package com.boomhope.Bill.TransService.AccCancel.Bean;

import com.boomhope.Bill.Util.FieldSort;
import com.boomhope.Bill.Util.Number;


/**
 * 销户接口(07624 03522 03517)获取衍生品文件
 * @author ZhangJianGang
 *
 */
@Number(id = 1)
public class AccCancelProFileBean {
	
	//衍生品文件格式：
	//衍生品类型||衍生品名称||收回总数量||实际收回数量||实际收回金额||收回客户号||收回姓名||备用列1||备用列2||备用列3||备用列4||备用列5\n
	@FieldSort(NO = 0)
	private String protype;//衍生品类型
	@FieldSort(NO = 1)
	private String proName;//衍生品名称
	@FieldSort(NO = 2)
	private String getProAllNumber;//收回总数量
	@FieldSort(NO = 3)
	private String getProSjNumber;//实际收回数量
	@FieldSort(NO = 4)
	private String getProSjAmt;//实际收回金额
	@FieldSort(NO = 5)
	private String getCustNo;//收回客户号
	@FieldSort(NO = 6)
	private String getCustName;//收回姓名
	@FieldSort(NO = 7)
	private String beiYong1;//备用列1
	@FieldSort(NO = 8)
	private String beiYong2;//备用列2
	@FieldSort(NO = 9)
	private String beiYong3;//备用列3
	@FieldSort(NO = 10)
	private String beiYong4;//备用列4
	@FieldSort(NO = 11)
	private String beiyong5;//备用列5
	
	public String getProtype() {
		return protype;
	}
	public void setProtype(String protype) {
		this.protype = protype;
	}
	public String getProName() {
		return proName;
	}
	public void setProName(String proName) {
		this.proName = proName;
	}
	public String getGetProAllNumber() {
		return getProAllNumber;
	}
	public void setGetProAllNumber(String getProAllNumber) {
		this.getProAllNumber = getProAllNumber;
	}
	public String getGetProSjNumber() {
		return getProSjNumber;
	}
	public void setGetProSjNumber(String getProSjNumber) {
		this.getProSjNumber = getProSjNumber;
	}
	public String getGetProSjAmt() {
		return getProSjAmt;
	}
	public void setGetProSjAmt(String getProSjAmt) {
		this.getProSjAmt = getProSjAmt;
	}
	public String getGetCustNo() {
		return getCustNo;
	}
	public void setGetCustNo(String getCustNo) {
		this.getCustNo = getCustNo;
	}
	public String getGetCustName() {
		return getCustName;
	}
	public void setGetCustName(String getCustName) {
		this.getCustName = getCustName;
	}
	public String getBeiYong1() {
		return beiYong1;
	}
	public void setBeiYong1(String beiYong1) {
		this.beiYong1 = beiYong1;
	}
	public String getBeiYong2() {
		return beiYong2;
	}
	public void setBeiYong2(String beiYong2) {
		this.beiYong2 = beiYong2;
	}
	public String getBeiYong3() {
		return beiYong3;
	}
	public void setBeiYong3(String beiYong3) {
		this.beiYong3 = beiYong3;
	}
	public String getBeiYong4() {
		return beiYong4;
	}
	public void setBeiYong4(String beiYong4) {
		this.beiYong4 = beiYong4;
	}
	public String getBeiyong5() {
		return beiyong5;
	}
	public void setBeiyong5(String beiyong5) {
		this.beiyong5 = beiyong5;
	}
	
}
