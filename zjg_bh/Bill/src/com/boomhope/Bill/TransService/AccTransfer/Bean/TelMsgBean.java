package com.boomhope.Bill.TransService.AccTransfer.Bean;

import com.boomhope.Bill.Util.FieldSort;
import com.boomhope.Bill.Util.Number;

/**
 * 权限明细查询 (短信提醒文件)
 * @author Administrator
 *
 */
@Number(id = 1)
public class TelMsgBean {
	
	//序号|卡号|客户名称|绑定手机号|交易日期|交易机构|交易柜员|状态
	@FieldSort(NO = 0)
	private String No;//序号
	@FieldSort(NO = 1)
	private String CardNo;//卡号
	@FieldSort(NO = 2)
	private String Name;//客户名称
	@FieldSort(NO = 3)
	private String TelNo;//绑定手机号
	@FieldSort(NO = 4)
	private String TranDate;//交易日期
	@FieldSort(NO = 5)
	private String TranHbrNo;//交易机构
	@FieldSort(NO = 6)
	private String TranTellerNo;//交易柜员
	@FieldSort(NO = 7)
	private String state;//状态
	public String getNo() {
		return No;
	}
	public void setNo(String no) {
		No = no;
	}
	public String getCardNo() {
		return CardNo;
	}
	public void setCardNo(String cardNo) {
		CardNo = cardNo;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getTelNo() {
		return TelNo;
	}
	public void setTelNo(String telNo) {
		TelNo = telNo;
	}
	public String getTranDate() {
		return TranDate;
	}
	public void setTranDate(String tranDate) {
		TranDate = tranDate;
	}
	public String getTranHbrNo() {
		return TranHbrNo;
	}
	public void setTranHbrNo(String tranHbrNo) {
		TranHbrNo = tranHbrNo;
	}
	public String getTranTellerNo() {
		return TranTellerNo;
	}
	public void setTranTellerNo(String tranTellerNo) {
		TranTellerNo = tranTellerNo;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
}
