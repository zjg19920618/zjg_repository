package com.boomhope.Bill.TransService.AccTransfer.Bean;

import com.boomhope.Bill.Util.FieldSort;
import com.boomhope.Bill.Util.Number;

/**
 * 单位卡权限信息
 * @author Administrator
 *
 */
@Number(id = 1)
public class UtilCardBean {
	@FieldSort(NO = 0)
	private String cardRoot;//付款卡号

	public String getCardRoot() {
		return cardRoot;
	}

	public void setCardRoot(String cardRoot) {
		this.cardRoot = cardRoot;
	}
	
}
