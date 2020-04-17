package com.boomhope.tms.transaction;

import org.apache.log4j.Logger;

import com.boomhope.tms.entity.OnOffState;
import com.boomhope.tms.message.XStreamNameCoder;
import com.boomhope.tms.message.in.InReqHeadBean;
import com.boomhope.tms.message.in.InResHeadBean;
import com.boomhope.tms.message.in.tms.Tms0009ReqBean;
import com.boomhope.tms.message.in.tms.Tms0009ReqBodyBean;
import com.boomhope.tms.message.in.tms.Tms0009ResBean;
import com.boomhope.tms.message.in.tms.Tms0009ResBodyBean;
import com.boomhope.tms.service.IOnOffStateService;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * 
 * @Description: 开关设置状态查询
 * @author zjg   
 * @date 2016年6月66日 下午12:17:32
 */
public class TransTms0009 extends BaseTransaction{
	Logger logger = Logger.getLogger(TransTms0009.class);
	private Tms0009ReqBean reqBean;	// 渠道请求Bean
	private Tms0009ResBean resBean;	// 渠道响应Bean
	/***
	 * 交易处理
	 * @param inReqMsg
	 * @return
	 */
	public String handle(String inReqMsg){
		
		/**
		 * 1. 解析渠道请求报文
		 * 2. 开关开闭状态查询
		 * 3. 生成渠道响应报文
		 */
		// 部署机器Service
		IOnOffStateService onOffStateService = null;
		try {
			onOffStateService = (IOnOffStateService) TransTms0009.getBean("onOffStateService");
		} catch (Exception e) {
			logger.error("调用开关状态查询Service失败", e);
			return makeFailMsg("999999", "调用开关状态查询Service失败:" + e.getMessage());
		}
		try {
			/* 解析请求报文并生成请求报文Bean */
			XStream reqXs = new XStream(new DomDriver("UTF-8"));
			reqXs.alias("Root", Tms0009ReqBean.class);
			reqXs.alias("Head", InReqHeadBean.class);
			reqXs.alias("Body", Tms0009ReqBodyBean.class);
			reqBean = (Tms0009ReqBean)reqXs.fromXML(inReqMsg);
			
			/* 查询开关状态信息 */
			OnOffState onOffState = onOffStateService.findOneState(reqBean.getBodyBean().getOnOffName());//开关名称
			if (onOffState == null){
				return makeFailMsg("999999", "开关状态查询失败");
			}
			
			/* 生成渠道响应报文Bean */
			resBean = new Tms0009ResBean(); 
			resBean.getHeadBean().setResCode("000000");
			resBean.getHeadBean().setResMsg("交易成功");
			Tms0009ResBodyBean tms0009ResBodyBean = new Tms0009ResBodyBean();
			tms0009ResBodyBean.setOnOffState(onOffState.getOnOffState());//开关状态  （0：启用，1关闭）
			resBean.setBodyBean(tms0009ResBodyBean);
			/* 生成渠道响应报文*/
			XStream resXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			
			resXs.alias("Root", Tms0009ResBean.class);
			resXs.alias("Head", InResHeadBean.class);
			resXs.alias("Body", Tms0009ResBodyBean.class);
			return resXs.toXML(resBean);
		} catch (Exception e) {
			logger.error("生成响应报文异常", e);
			return makeFailMsg("999999", "生成响应报文异常");
		}
	}
}
