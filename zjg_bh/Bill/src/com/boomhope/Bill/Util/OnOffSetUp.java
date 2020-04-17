package com.boomhope.Bill.Util;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Public.GlobalParameter;
import com.boomhope.Bill.TransService.AccCancel.Interface.IntefaceSendMsg;
import com.boomhope.Bill.TransService.AccTransfer.TransferUtil.SocketClient;
import com.boomhope.tms.message.XStreamNameCoder;
import com.boomhope.tms.message.in.InReqHeadBean;
import com.boomhope.tms.message.in.tms.Tms0009ReqBean;
import com.boomhope.tms.message.in.tms.Tms0009ReqBodyBean;
import com.boomhope.tms.message.in.tms.Tms0009ResBean;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XppDriver;

/**
 * 开关
 * @author Administrator
 *
 */
public class OnOffSetUp {
	
	static Logger logger = Logger.getLogger(IntefaceSendMsg.class);
	
	/**
	 * 请求头信息 
	 * @param tranCoede
	 * @return
	 */
	public static InReqHeadBean getInReqHeadBean(String tranCoede){
		InReqHeadBean inReqHeadBean = new InReqHeadBean();
		inReqHeadBean.setTradeCode(tranCoede);//交易码
		inReqHeadBean.setMachineNo(GlobalParameter.machineNo);// 机器号
		inReqHeadBean.setBranchNo(GlobalParameter.branchNo);// 机构号
		inReqHeadBean.setMachineType(GlobalParameter.MACHINE_TYPE);// 机构号
		return inReqHeadBean;
		
	}
	
	/**
	 * 开关查询
	 * params onOffName：查询的开关名称
	 */
	@SuppressWarnings("rawtypes")
	public static Map checkOnOffState(String onOffName)throws Exception{
		//获取数据
		Map<String,String> params = new HashMap<String,String>();
		
		//生成报文
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		Tms0009ReqBean tms0009ReqBean = new Tms0009ReqBean();
		//请求报文体
		Tms0009ReqBodyBean tms0009ReqBodyBean = new Tms0009ReqBodyBean();
		tms0009ReqBodyBean.setOnOffName(onOffName);
		tms0009ReqBean.setBodyBean(tms0009ReqBodyBean);
		//请求报文头
		tms0009ReqBean.setHeadBean(getInReqHeadBean("TMS_0009"));
		
		xstream.alias("Root", Tms0009ReqBean.class);
		xstream.alias("Body", Tms0009ReqBodyBean.class);
		xstream.alias("Head", InReqHeadBean.class);
		String reqMsg=xstream.toXML(tms0009ReqBean);
		logger.info("请求报文："+reqMsg);
		
		SocketClient socketClient =new SocketClient();
		socketClient.createSocket();
		
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", Tms0009ResBean.class);
		Tms0009ResBean tms0009ResBean = (Tms0009ResBean)reqXs.fromXML(resMsg);
		
		if(tms0009ResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","开关状态查询失败");
			return params;
		}
		String resCode = tms0009ResBean.getHeadBean().getResCode();
		String errMsg = tms0009ResBean.getHeadBean().getResMsg();
		logger.info("开关状态查询resCode:"+resCode);
		logger.info("开关状态查询resMsg:"+errMsg);
		
		if(!"000000".equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		
		params.put("resCode",resCode);
		params.put("STATE",tms0009ResBean.getBodyBean().getOnOffState());//状态值（0：启用；1：关闭）
		return params;
	}
}
