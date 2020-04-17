package com.boomhope.Bill.TransService.BillPrint.Interface;

import com.boomhope.Bill.Util.FieldSort;
import com.boomhope.Bill.Util.Number;


/**
 * 银行卡换开查询原卡号【79114】-07523
 * @author Administrator
 *
 */
@Number(id = 1)
public class HistoryCardNo{
	//	条数  \n
	// 历史卡号

	@FieldSort(NO = 0)
	private String cardNo;	//历史卡号

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	
	
}
