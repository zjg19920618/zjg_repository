package com.boomhope.Bill.TransService.AccTransfer.Bean;

import com.boomhope.Bill.Util.FieldSort;
import com.boomhope.Bill.Util.Number;

/**
 * 交易信息查询
 * @author hao
 *
 */
@Number(id = 1)
public class TransferSelectBean {
	//交易渠道（4）||委托日期（8）||交易任务号（24）||交易描述（60）||定时发送日期（8）||定时发送时间（6）||实际发送日期（8）||实际发送时间(6)||交易发送状态（2）
	//||付款人账号（转出卡号）（66）||付款人户名||收款人账号（转入卡号）（66）||收款人户名||交易金额（16，2）||受理员(5)||前台授权员（5）
	//||支付交易序号（8）（系统跟踪号（6））|| 收款人开户行名\n
	@FieldSort(NO = 0)
	private String transMethod;
	@FieldSort(NO = 1)
	private String wtDate;
	@FieldSort(NO = 2)
	private String transRWCode;
	@FieldSort(NO = 3)
	private String transDetail;
	@FieldSort(NO = 4)
	private String dsSendDate;
	@FieldSort(NO = 5)
	private String dsSendTime;
	@FieldSort(NO = 6)
	private String trueSendDate;
	@FieldSort(NO = 7)
	private String trueSendTime;
	@FieldSort(NO = 8)
	private String sendStatu;
	@FieldSort(NO = 9)
	private String zcNo;
	@FieldSort(NO = 10)
	private String zcName;
	@FieldSort(NO = 11)
	private String zrNo;
	@FieldSort(NO = 12)
	private String zrName;
	@FieldSort(NO = 13)
	private String transMoney;
	@FieldSort(NO = 14)
	private String slteller;
	@FieldSort(NO = 15)
	private String qtteller;
	@FieldSort(NO = 16)
	private String payCode;
	@FieldSort(NO = 17)
	private String payeeHbrName;
	
	private String CancelResult;//撤销结果
	private String FailCause;//撤销失败原因
	private String selectFlag;//选中标记
	private boolean flag = false; 
	private String cancelId;//撤销流水
	public String getCancelId() {
		return cancelId;
	}
	public void setCancelId(String cancelId) {
		this.cancelId = cancelId;
	}
	public String getCancelDate() {
		return cancelDate;
	}
	public void setCancelDate(String cancelDate) {
		this.cancelDate = cancelDate;
	}
	private String cancelDate ;//撤销日期
	public String getTransMethod() {
		return transMethod;
	}
	public void setTransMethod(String transMethod) {
		this.transMethod = transMethod;
	}
	public String getWtDate() {
		return wtDate;
	}
	public void setWtDate(String wtDate) {
		this.wtDate = wtDate;
	}
	public String getTransRWCode() {
		return transRWCode;
	}
	public void setTransRWCode(String transRWCode) {
		this.transRWCode = transRWCode;
	}
	public String getTransDetail() {
		return transDetail;
	}
	public void setTransDetail(String transDetail) {
		this.transDetail = transDetail;
	}
	public String getDsSendDate() {
		return dsSendDate;
	}
	public void setDsSendDate(String dsSendDate) {
		this.dsSendDate = dsSendDate;
	}
	public String getDsSendTime() {
		return dsSendTime;
	}
	public void setDsSendTime(String dsSendTime) {
		this.dsSendTime = dsSendTime;
	}
	public String getTrueSendDate() {
		return trueSendDate;
	}
	public void setTrueSendDate(String trueSendDate) {
		this.trueSendDate = trueSendDate;
	}
	public String getTrueSendTime() {
		return trueSendTime;
	}
	public void setTrueSendTime(String trueSendTime) {
		this.trueSendTime = trueSendTime;
	}
	public String getSendStatu() {
		return sendStatu;
	}
	public void setSendStatu(String sendStatu) {
		this.sendStatu = sendStatu;
	}
	public String getZcNo() {
		return zcNo;
	}
	public void setZcNo(String zcNo) {
		this.zcNo = zcNo;
	}
	public String getZcName() {
		return zcName;
	}
	public void setZcName(String zcName) {
		this.zcName = zcName;
	}
	public String getZrNo() {
		return zrNo;
	}
	public void setZrNo(String zrNo) {
		this.zrNo = zrNo;
	}
	public String getZrName() {
		return zrName;
	}
	public void setZrName(String zrName) {
		this.zrName = zrName;
	}
	public String getTransMoney() {
		return transMoney;
	}
	public void setTransMoney(String transMoney) {
		this.transMoney = transMoney;
	}
	public String getSlteller() {
		return slteller;
	}
	public void setSlteller(String slteller) {
		this.slteller = slteller;
	}
	public String getQtteller() {
		return qtteller;
	}
	public void setQtteller(String qtteller) {
		this.qtteller = qtteller;
	}
	public String getPayCode() {
		return payCode;
	}
	public void setPayCode(String payCode) {
		this.payCode = payCode;
	}
	public String getCancelResult() {
		return CancelResult;
	}
	public void setCancelResult(String cancelResult) {
		CancelResult = cancelResult;
	}
	public String getFailCause() {
		return FailCause;
	}
	public void setFailCause(String failCause) {
		FailCause = failCause;
	}
	
	public String getSelectFlag() {
		return selectFlag;
	}
	public void setSelectFlag(String selectFlag) {
		this.selectFlag = selectFlag;
	}
	public String getPayeeHbrName() {
		return payeeHbrName;
	}
	public void setPayeeHbrName(String payeeHbrName) {
		this.payeeHbrName = payeeHbrName;
	}
	
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	@Override
	public String toString() {
		return "TransferSelectBean [transMethod=" + transMethod + ", wtDate="
				+ wtDate + ", transRWCode=" + transRWCode + ", transDetail="
				+ transDetail + ", dsSendDate=" + dsSendDate + ", dsSendTime="
				+ dsSendTime + ", trueSendDate=" + trueSendDate
				+ ", trueSendTime=" + trueSendTime + ", sendStatu=" + sendStatu
				+ ", zcNo=" + zcNo + ", zcName=" + zcName + ", zrNo=" + zrNo
				+ ", zrName=" + zrName + ", transMoney=" + transMoney
				+ ", slteller=" + slteller + ", qtteller=" + qtteller
				+ ", payCode=" + payCode + ", payeeHbrName=" + payeeHbrName
				+ ", CancelResult=" + CancelResult + ", FailCause=" + FailCause
				+ ", selectFlag=" + selectFlag + ", flag=" + flag + "]";
	}
	
}
