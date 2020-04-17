package com.boomhope.Bill.TransService.LostReport.Bean;

import com.boomhope.Bill.Util.FieldSort;
import com.boomhope.Bill.Util.Number;

/**
	日切接口（核心接口待完善）【00002】-前置【08169】
*/

@Number(id = 1)
public class SvrDateCheckBean08169 {
	//	核心日期
	@FieldSort(NO = 0)
	private  String  svr_date;//核心日期

	public String getSvr_date() {
		return svr_date;
	}

	public void setSvr_date(String svr_date) {
		this.svr_date = svr_date;
	}
	
	
	
	
}
