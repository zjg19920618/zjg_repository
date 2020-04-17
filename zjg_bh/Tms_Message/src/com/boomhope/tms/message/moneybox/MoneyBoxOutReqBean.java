package com.boomhope.tms.message.moneybox;

import com.boomhope.tms.message.in.InReqHeadBean;

/***
 * 请求报文Bean
 * @author shaopeng
 *
 */
public class MoneyBoxOutReqBean {
	private MoneyBoxOutHeadBean MessageHead;	// 报文头
	private InReqHeadBean Head;	// 报文头
	
	public MoneyBoxOutReqBean(){
		MessageHead = new MoneyBoxOutHeadBean();
	}

	public MoneyBoxOutHeadBean getMessageHead() {
		return MessageHead;
	}

	public void setMessageHead(MoneyBoxOutHeadBean messageHead) {
		MessageHead = messageHead;
	}

	public InReqHeadBean getHead() {
		return Head;
	}

	public void setHead(InReqHeadBean head) {
		Head = head;
	}
	
}
