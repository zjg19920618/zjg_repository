package com.boomhope.Bill.TransService.BillPrint.Interface;

import com.boomhope.Bill.Util.FieldSort;
import com.boomhope.Bill.Util.Number;

/**
 * 根据客户号查询所有卡号信息【90001】-前置07520
 * @author Administrator
 *
 */
@Number(id = 1)
public class CardsInfo {

	//文件格式：总笔数n
	//卡号(19)||帐户类型(1)||开户机构名称(30)||开户日期(10)||币种(2)||钞汇标志(1)||余额(19.2)||可用余额(19.2)||账户状态(4)||开户机构号(9)
	//账户状态说明:
	//[0] : N-正常 *-销户 */
	//[1] : 1_卡临时挂失 2_卡正式挂失 3_挂失申请书临时挂失  4_挂失申请书正式挂失 5_密码临时挂失6_密码正式挂失

	
	@FieldSort(NO = 0)
	private String card_No;//卡号
	@FieldSort(NO = 1)
	private String card_Type;//账户类型
	@FieldSort(NO = 2)
	private String open_inst;//开户机构名称
	@FieldSort(NO = 3)
	private String open_Date;//开户日期
	@FieldSort(NO = 4)
	private String currency;//币种
	@FieldSort(NO = 5)
	private String ch_mark;//钞汇标志
	@FieldSort(NO = 6)
	private String balance;//余额
	@FieldSort(NO = 7)
	private String use_Balance;//可用余额
	@FieldSort(NO = 8)
	private String card_State;//账户状态
	@FieldSort(NO = 9)
	private String open_inst_No;//开户机构号
	public String getCard_No() {
		return card_No;
	}
	public void setCard_No(String card_No) {
		this.card_No = card_No;
	}
	public String getCard_Type() {
		return card_Type;
	}
	public void setCard_Type(String card_Type) {
		this.card_Type = card_Type;
	}
	public String getOpen_inst() {
		return open_inst;
	}
	public void setOpen_inst(String open_inst) {
		this.open_inst = open_inst;
	}
	public String getOpen_Date() {
		return open_Date;
	}
	public void setOpen_Date(String open_Date) {
		this.open_Date = open_Date;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getCh_mark() {
		return ch_mark;
	}
	public void setCh_mark(String ch_mark) {
		this.ch_mark = ch_mark;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public String getUse_Balance() {
		return use_Balance;
	}
	public void setUse_Balance(String use_Balance) {
		this.use_Balance = use_Balance;
	}
	public String getCard_State() {
		return card_State;
	}
	public void setCard_State(String card_State) {
		this.card_State = card_State;
	}
	public String getOpen_inst_No() {
		return open_inst_No;
	}
	public void setOpen_inst_No(String open_inst_No) {
		this.open_inst_No = open_inst_No;
	}
	
	
	
	
	
	
	
}
