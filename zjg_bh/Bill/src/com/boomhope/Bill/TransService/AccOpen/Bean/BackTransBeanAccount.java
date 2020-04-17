package com.boomhope.Bill.TransService.AccOpen.Bean;
/***
 * 销户Bean
 * 
 *
 */
public class BackTransBeanAccount {
	private String money;	// 金额
	private String payableInterest;	// 应付利息
	private String paymentInterest;	// 支付利息
	private String nterestPaid;	// 实付利息
	private String reminderMessage;// 提示信息
	private String jellyBean;	// 糖豆
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getPayableInterest() {
		return payableInterest;
	}
	public void setPayableInterest(String payableInterest) {
		this.payableInterest = payableInterest;
	}
	public String getPaymentInterest() {
		return paymentInterest;
	}
	public void setPaymentInterest(String paymentInterest) {
		this.paymentInterest = paymentInterest;
	}
	public String getNterestPaid() {
		return nterestPaid;
	}
	public void setNterestPaid(String nterestPaid) {
		this.nterestPaid = nterestPaid;
	}
	public String getReminderMessage() {
		return reminderMessage;
	}
	public void setReminderMessage(String reminderMessage) {
		this.reminderMessage = reminderMessage;
	}
	public String getJellyBean() {
		return jellyBean;
	}
	public void setJellyBean(String jellyBean) {
		this.jellyBean = jellyBean;
	}
	
	
	
}
