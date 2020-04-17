package com.boomhope.Bill.TransService.BillPrint.Interface;

import java.util.ArrayList;
import java.util.List;

public class ICBankBean {

	private String ICAccNo;//银行卡号
	private String type;//类型（7银行卡  11电子帐户）数字描述
	private String typeStr;//类型汉字描述
	private List<ICSubAccNo> subAccNo = new ArrayList<ICSubAccNo>();
	
	public String getICAccNo() {
		return ICAccNo;
	}
	public void setICAccNo(String iCAccNo) {
		ICAccNo = iCAccNo;
	}
	public List<ICSubAccNo> getSubAccNo() {
		return subAccNo;
	}
	public void setSubAccNo(List<ICSubAccNo> subAccNo) {
		this.subAccNo = subAccNo;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTypeStr() {
		return typeStr;
	}
	public void setTypeStr(String typeStr) {
		if(typeStr.equals("7")){
			this.typeStr = "银行卡";
		}else{
			this.typeStr = "电子帐户";
		}
	}
	
}
