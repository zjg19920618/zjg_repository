package com.boomhope.tms.transaction;

import org.apache.log4j.Logger;

import com.boomhope.tms.message.XStreamNameCoder;
import com.boomhope.tms.message.moneybox.MoneyBoxTransApplyReqBean;
import com.boomhope.tms.message.moneybox.MoneyBoxTransApplyReqResponseBean;
import com.boomhope.tms.socket.MoneyBoxSocketClient;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * 业务申请
 */
public class TransTmsMoneyBoxTransApply{
	
	Logger logger = Logger.getLogger(TransTmsMoneyBoxTransApply.class);
	
	public String handle(String msg) {
		
		try {
			//生成钱柜请求报文 
			byte[] outReqMsg = makeOutReqMsg(msg);
			//发送钱柜交易请求
			String outResMsg = new MoneyBoxSocketClient().sendMsg(outReqMsg);
			
			//解析钱柜返回的报文
			outResMsg = makeOutResMsg(outResMsg);
			
			return outResMsg;
		} catch (Exception e) {
			return makeResErrorMsg(e.getMessage(),null);
		}
	}
	
	private String makeResErrorMsg(String msg,String service) {
		MoneyBoxTransApplyReqBean boxTransApplyReqBean = new MoneyBoxTransApplyReqBean();
		MoneyBoxTransApplyReqResponseBean  transApplyReqResponseBean= new MoneyBoxTransApplyReqResponseBean();
		transApplyReqResponseBean.setRetCode("44444");
		transApplyReqResponseBean.setErrorMessage(msg);
		boxTransApplyReqBean.setResponse(transApplyReqResponseBean);
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", MoneyBoxTransApplyReqBean.class);
		return reqXs.toXML(boxTransApplyReqBean);
	}
	private String makeOutResMsg(String result)throws Exception {
		try {
			logger.info("钱柜返回报文："+result);
			result = result.substring(result.indexOf("<xBankBass>"));
			result = result.replaceAll("xBankBass", "Root");
			return result;
		} catch (Exception e) {
			logger.error("解析钱柜请求报文异常:"+e.getMessage(),e);
			throw new Exception("解析钱柜请求报文异常", e);
		}
		
	}

	private byte[] makeOutReqMsg(String msg) throws Exception{
		try {
			msg = msg.replaceAll("Root", "xBankBass");
			msg = "<?xml version=\"1.0\" encoding=\"GBK\" ?>\n"+msg;
			String head = msg.substring(0, msg.indexOf("<Head>"));
			head += msg.substring(msg.indexOf("</Head>") + 7);
			logger.info("请求钱柜报文：" + head);
			byte[] reqMsg = head.getBytes("GBK");
			return reqMsg;
		} catch (Exception e) {
			logger.error("生成钱柜请求报文异常:"+e.getMessage(),e);
			throw new Exception("生成钱柜请求报文异常", e);
		}
	}
	
}
