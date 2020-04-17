package com.boomhope.tms.transaction;

import org.apache.log4j.Logger;

import com.boomhope.tms.entity.BusBillMan;
import com.boomhope.tms.message.XStreamNameCoder;
import com.boomhope.tms.message.in.InReqHeadBean;
import com.boomhope.tms.message.in.InResHeadBean;
import com.boomhope.tms.message.in.tms.Tms0005ReqBean;
import com.boomhope.tms.message.in.tms.Tms0005ReqBodyBean;
import com.boomhope.tms.message.in.tms.Tms0005ResBean;
import com.boomhope.tms.message.in.tms.Tms0005ResBodyBean;
import com.boomhope.tms.service.IFlowService;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * 
 * @Description: 凭证号信息查询
 * @author zjg   
 * @date 2016年11月16日 下午12:17:32
 */
public class TransTms0005 extends BaseTransaction{
	Logger logger = Logger.getLogger(TransTms0005.class);
	private Tms0005ReqBean reqBean;	// 渠道请求Bean
	private Tms0005ResBean resBean;	// 渠道响应Bean
	/***
	 * 交易处理
	 * @param inReqMsg
	 * @return
	 */
	public String handle(String inReqMsg){
		
		/**
		 * 1. 解析渠道请求报文
		 * 2. 查询凭证信息
		 * 3. 生成渠道响应报文
		 */
		IFlowService busBillManService = null; 
		try {
			busBillManService  = (IFlowService)TransTms0005.getBean("flowService");
		} catch (Exception e) {
			logger.error("调用凭证信息service失败",e);
			return makeFailMsg("999999", "调用凭证信息Service失败:" + e.getMessage());
		}
		try {
			/* 解析请求报文并生成请求报文Bean */
			XStream reqXs = new XStream(new DomDriver("UTF-8"));
			reqXs.alias("Root", Tms0005ReqBean.class);
			reqXs.alias("Head", InReqHeadBean.class);
			reqXs.alias("Body", Tms0005ReqBodyBean.class);
			reqBean = (Tms0005ReqBean)reqXs.fromXML(inReqMsg);
			
			/* 查询凭证信息 */
			  BusBillMan busBill = busBillManService.findOneBusBill(reqBean.getHeadBean().getMachineNo());
				
			if(busBill==null){
				
				return makeFailMsg("999998", "凭证信息不存在");
			}
			
			/* 生成渠道响应报文Bean */
			resBean = new Tms0005ResBean(); 
			resBean.getHeadBean().setResCode("000000");
			resBean.getHeadBean().setResMsg("交易成功");
			Tms0005ResBodyBean tms0005ResBodyBean = new Tms0005ResBodyBean();
			tms0005ResBodyBean.setID(String.valueOf(busBill.getId()));
			tms0005ResBodyBean.setMACHINE_NO(busBill.getDeployMachine().getMachineNo());
			tms0005ResBodyBean.setSTART_NO(busBill.getStartBno());
			tms0005ResBodyBean.setEND_NO(busBill.getEndBno());
			tms0005ResBodyBean.setNOW_BO(busBill.getNowBno());
			tms0005ResBodyBean.setCREATE_DATE(busBill.getCreateDate());
			tms0005ResBodyBean.setUPDATE_DATE(busBill.getUpdateDate());
			resBean.setBody(tms0005ResBodyBean);
			/* 生成渠道响应报文*/
			XStream resXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			
			resXs.alias("Root", Tms0005ResBean.class);
			resXs.alias("Head", InResHeadBean.class);
			resXs.alias("Body", Tms0005ResBodyBean.class);
			return resXs.toXML(resBean);
		} catch (Exception e) {
			logger.error("生成响应报文异常", e);
			return makeFailMsg("999999", "生成响应报文异常");
		}
	}
}
