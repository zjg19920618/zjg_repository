package com.boomhope.tms.transaction;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.boomhope.tms.entity.AuthenticFlow;
import com.boomhope.tms.message.XStreamNameCoder;
import com.boomhope.tms.message.in.InReqHeadBean;
import com.boomhope.tms.message.in.InResHeadBean;
import com.boomhope.tms.message.in.tms.Tms0007ReqBean;
import com.boomhope.tms.message.in.tms.Tms0007ReqBodyBean;
import com.boomhope.tms.message.in.tms.Tms0007ResBean;
import com.boomhope.tms.message.in.tms.Tms0007ResBodyBean;
import com.boomhope.tms.service.IAuthenticFlowService;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * 
 * @Description: 凭证号信息修改
 * @author zjg   
 * @date 2016年11月16日 下午12:17:32
 */
public class TransTms0007 extends BaseTransaction{
	Logger logger = Logger.getLogger(TransTms0007.class);
	private Tms0007ReqBean reqBean;	// 渠道请求Bean
	private Tms0007ResBean resBean;	// 渠道响应Bean
	/***
	 * 交易处理
	 * @param inReqMsg
	 * @return
	 */
	public String handle(String inReqMsg){
		
		/**
		 * 1. 解析渠道请求报文
		 * 2. 修改凭证信息
		 * 3. 生成渠道响应报文
		 */
		IAuthenticFlowService authenticFlowService = null; 
		try {
			authenticFlowService  = (IAuthenticFlowService)TransTms0007.getBean("AuthenticFlowImpl");
		} catch (Exception e) {
			logger.error("调用凭证信息service失败",e);
			return makeFailMsg("999999", "调用凭证信息Service失败:" + e.getMessage());
		}
		try {
			/* 解析请求报文并生成请求报文Bean */
			XStream reqXs = new XStream(new DomDriver("UTF-8"));
			reqXs.alias("Root", Tms0007ReqBean.class);
			reqXs.alias("Head", InReqHeadBean.class);
			reqXs.alias("Body", Tms0007ReqBodyBean.class);
			reqBean = (Tms0007ReqBean)reqXs.fromXML(inReqMsg);
			
			try {
				/* 新增鉴伪流水号 */
				AuthenticFlow flow=new AuthenticFlow();
				flow.setBranchNo(reqBean.getBodyBean().getBranchNo());
				flow.setMachineNo(reqBean.getBodyBean().getMachineNo());
				flow.setStatus(reqBean.getBodyBean().getStatus());
				SimpleDateFormat sdf=new SimpleDateFormat("YYYYMMddhhmmss");
				String str=sdf.format(new Date());
				flow.setFlowDate(str.substring(0,8));
				flow.setFlowTime(str.substring(8));
				authenticFlowService.addAuthenticFlow(flow);
			} catch (Exception e) {
				logger.error("连接数据库异常", e);
				return makeFailMsg("999999", "连接数据库异常");
			}
			
			/* 生成渠道响应报文Bean */
			resBean = new Tms0007ResBean(); 
			resBean.getHeadBean().setResCode("000000");
			resBean.getHeadBean().setResMsg("交易成功");
			
			/* 生成渠道响应报文*/
			XStream resXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			
			resXs.alias("Root", Tms0007ResBean.class);
			resXs.alias("Head", InResHeadBean.class);
			resXs.alias("Body", Tms0007ResBodyBean.class);
			return resXs.toXML(resBean);
		} catch (Exception e) {
			logger.error("生成响应报文异常", e);
			return makeFailMsg("999999", "生成响应报文异常");
		}
	}
}
