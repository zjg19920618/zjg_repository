package com.boomhope.Bill.TransService.LostReport.Bean;

import com.boomhope.Bill.Util.FieldSort;
import com.boomhope.Bill.Util.Number;

/**
	开卡明细联动查询【70028】-07524
*/

@Number(id = 1)
public class BillChangeOpenInfoBean {
	//	卡号||领卡日期||开户机构
	@FieldSort(NO = 0)
	private  String  card_no;//卡号
	@FieldSort(NO = 1)
	private String card_date;//领卡日期
	@FieldSort(NO = 2)
	private String open_inst;//开户机构
	
	
	public String getCard_no() {
		return card_no;
	}
	public void setCard_no(String card_no) {
		this.card_no = card_no;
	}
	public String getCard_date() {
		return card_date;
	}
	public void setCard_date(String card_date) {
		this.card_date = card_date;
	}
	public String getOpen_inst() {
		return open_inst;
	}
	public void setOpen_inst(String open_inst) {
		this.open_inst = open_inst;
	}
	
	
}
