package com.boomhope.Bill.TransService.AccTransfer.Bean;

import com.boomhope.Bill.Util.FieldSort;
import com.boomhope.Bill.Util.Number;

/**
 * 转账客户列表信息
 * @author Administrator
 *
 */
@Number(id = 1)
public class PaymentTermsMsgBean {
	
	//付款卡号||收款账号||收款账户名||收款账号开户行名||收款账号开户行号||收款账号清算行名||收款账号清算行号||收款人地址
	@FieldSort(NO = 0)
	private String paymentCardNO;//付款卡号
	@FieldSort(NO = 1)
	private String PayeeAcctNo;//收款账号
	@FieldSort(NO = 2)
	private String PayeeName;//收款账户名
	@FieldSort(NO = 3)
	private String PayeeHbrName;//收款账号开户行名
	@FieldSort(NO = 4)
	private String PayeeHbrNo;//收款账号开户行号
	@FieldSort(NO = 5)
	private String recvClrBankName;//收款账号清算行名
	@FieldSort(NO = 6)
	private String recvClrBankNo;//收款账号清算行号
	@FieldSort(NO = 7)
	private String payeeAddr;//收款人地址
	
	public String getPaymentCardNO() {
		return paymentCardNO;
	}
	public void setPaymentCardNO(String paymentCardNO) {
		this.paymentCardNO = paymentCardNO;
	}
	public String getPayeeAcctNo() {
		return PayeeAcctNo;
	}
	public void setPayeeAcctNo(String payeeAcctNo) {
		PayeeAcctNo = payeeAcctNo;
	}
	public String getPayeeName() {
		return PayeeName;
	}
	public void setPayeeName(String payeeName) {
		PayeeName = payeeName;
	}
	public String getPayeeHbrName() {
		return PayeeHbrName;
	}
	public void setPayeeHbrName(String payeeHbrName) {
		PayeeHbrName = payeeHbrName;
	}
	public String getPayeeHbrNo() {
		return PayeeHbrNo;
	}
	public void setPayeeHbrNo(String payeeHbrNo) {
		PayeeHbrNo = payeeHbrNo;
	}
	public String getRecvClrBankName() {
		return recvClrBankName;
	}
	public void setRecvClrBankName(String recvClrBankName) {
		this.recvClrBankName = recvClrBankName;
	}
	public String getRecvClrBankNo() {
		return recvClrBankNo;
	}
	public void setRecvClrBankNo(String recvClrBankNo) {
		this.recvClrBankNo = recvClrBankNo;
	}
	public String getPayeeAddr() {
		return payeeAddr;
	}
	public void setPayeeAddr(String payeeAddr) {
		this.payeeAddr = payeeAddr;
	}
	
	
}
