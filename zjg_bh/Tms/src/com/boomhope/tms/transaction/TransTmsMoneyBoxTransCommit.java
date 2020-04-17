package com.boomhope.tms.transaction;

import org.apache.log4j.Logger;

import com.boomhope.tms.message.XStreamNameCoder;
import com.boomhope.tms.message.moneybox.MoneyBoxTransCommitReqBean;
import com.boomhope.tms.message.moneybox.MoneyBoxTransCommitReqResponseBean;
import com.boomhope.tms.socket.MoneyBoxSocketClient;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * 业务提交
 */
public class TransTmsMoneyBoxTransCommit{
	
	Logger logger = Logger.getLogger(TransTmsMoneyBoxTransCommit.class);
	
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
		MoneyBoxTransCommitReqBean boxTransCommitReqBean = new MoneyBoxTransCommitReqBean();
		MoneyBoxTransCommitReqResponseBean  transCommitReqResponseBean= new MoneyBoxTransCommitReqResponseBean();
		transCommitReqResponseBean.setRetCode("44444");
		transCommitReqResponseBean.setErrorMessage(msg);
		boxTransCommitReqBean.setResponse(transCommitReqResponseBean);
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", MoneyBoxTransCommitReqBean.class);
		return reqXs.toXML(boxTransCommitReqBean);
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
