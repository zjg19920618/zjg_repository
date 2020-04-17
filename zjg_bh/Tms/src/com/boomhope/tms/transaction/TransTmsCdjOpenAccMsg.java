package com.boomhope.tms.transaction;

import org.apache.log4j.Logger;

import com.boomhope.tms.message.XStreamNameCoder;
import com.boomhope.tms.message.cdjmac.CdjOpenAccReqBean;
import com.boomhope.tms.message.cdjmac.CdjOpenAccReqBodyBean;
import com.boomhope.tms.message.cdjmac.CdjOpenAccResBean;
import com.boomhope.tms.message.cdjmac.CdjOpenAccResBodyBean;
import com.boomhope.tms.message.cdjmac.InReqHeadBean;
import com.boomhope.tms.message.cdjmac.InResHeadBean;
import com.boomhope.tms.message.cdjmac.InResponseBean;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * 开户查询上传路径
 *
 */
public class TransTmsCdjOpenAccMsg{
	
	Logger logger = Logger.getLogger(TransTmsCdjOpenAccMsg.class);
	private CdjOpenAccReqBean req;
	private CdjOpenAccResBean res;
	private InResponseBean response;
	
	public String handle(String msg) {
		
		try {
			//生成存单机请求报文 
			req = makeInReqBean(msg);
			
			//响应存单机报文
			return makeInResMsg(req);
			
		} catch (Exception e) {
			return makeResErrorMsg(e.getMessage(),null);
		}
	}
	
	private String makeResErrorMsg(String msg,String service) {
		
		res = new CdjOpenAccResBean();
		CdjOpenAccResBodyBean cdjOpenAccResBodyBean = new CdjOpenAccResBodyBean();
		res.setBody(cdjOpenAccResBodyBean);
		
		InResHeadBean inResHeadBean = new InResHeadBean();
		res.setHeadBean(inResHeadBean);
		
		res.setResponse(response);
		response.setResCode("44444");
		response.setResMsg(msg);
		
		
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", CdjOpenAccResBean.class);
		return reqXs.toXML(res);
	}

	
	
	private String makeInResMsg(CdjOpenAccReqBean reqBean)throws Exception {
		try {
			res = new CdjOpenAccResBean();
			CdjOpenAccResBodyBean cdjOpenAccResBodyBean = new CdjOpenAccResBodyBean();
			cdjOpenAccResBodyBean.setUploadPath(ConfigReader.getConfig("agreementPath"));//开户图片信息上传路径
			res.setBody(cdjOpenAccResBodyBean);
			
			InResHeadBean inResHeadBean = new InResHeadBean();
			res.setHeadBean(inResHeadBean);
			
			response = new InResponseBean();
			res.setResponse(response);
			response.setResCode("000000");
			response.setResMsg("成功");
			XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			reqXs.alias("Root", CdjOpenAccResBean.class);
			return reqXs.toXML(res);
		} catch (Exception e) {
			logger.error("生成渠道响应报文异常", e);
			throw new Exception("生成渠道响应报文异常", e);
		}
		
	}

	private CdjOpenAccReqBean makeInReqBean(String msg) throws Exception{
		/* 解析渠道请求报文*/
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		
		reqXs.alias("Root", CdjOpenAccReqBean.class);
		reqXs.alias("Head", InReqHeadBean.class);
		reqXs.alias("Body", CdjOpenAccReqBodyBean.class);
		
		return (CdjOpenAccReqBean)reqXs.fromXML(msg);
	}
	
}
