package com.boomhope.tms.transaction;


import org.apache.log4j.Logger;

import com.boomhope.tms.entity.BaseUser;
import com.boomhope.tms.message.XStreamNameCoder;
import com.boomhope.tms.message.in.InReqHeadBean;
import com.boomhope.tms.message.in.InResHeadBean;
import com.boomhope.tms.message.in.tms.Tms0004ReqBean;
import com.boomhope.tms.message.in.tms.Tms0004ReqBodyBean;
import com.boomhope.tms.message.in.tms.Tms0004ResBean;
import com.boomhope.tms.service.ILoginService;
import com.boomhope.tms.util.MD5Util;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;


/**
 * 
 * @Description: 管理员密码校验
 * @author zjg   
 * @date 2016年11月16日 下午12:17:32
 */
public class TransTms0004 extends BaseTransaction{
	Logger logger = Logger.getLogger(TransTms0004.class);
	private Tms0004ReqBean reqBean;	// 渠道请求Bean
	private Tms0004ResBean resBean;	// 渠道响应Bean
	/***
	 * 交易处理
	 * @param inReqMsg
	 * @return
	 */
	public String handle(String inReqMsg){
		
		/**
		 * 1. 解析渠道请求报文
		 * 2. 查询管理员信息(username,password)
		 * 3. 生成渠道响应报文
		 */
		ILoginService loginService = null; 
		try {
			loginService  = (ILoginService)TransTms0004.getBean("loginService");
		} catch (Exception e) {
			logger.error("调用管理员校验service失败",e);
			return makeFailMsg("999999", "调用管理员校验Service失败:" + e.getMessage());
		}
		try {
			/* 解析请求报文并生成请求报文Bean */
			XStream reqXs = new XStream(new DomDriver("UTF-8"));
			reqXs.alias("Root", Tms0004ReqBean.class);
			reqXs.alias("Head", InReqHeadBean.class);
			reqXs.alias("Body", Tms0004ReqBodyBean.class);
			reqBean = (Tms0004ReqBean)reqXs.fromXML(inReqMsg);
			
			/* 查询管理员信息 */
			if("0".equals(reqBean.getBodyBean().getIsXiu())){
				BaseUser user = loginService.getUser(reqBean.getBodyBean().getUsername(),reqBean.getBodyBean().getPassword());
				if (user!=null){
					/* 生成渠道响应报文Bean */
					resBean = new Tms0004ResBean(); 
					resBean.getHeadBean().setResCode("000000");
					resBean.getHeadBean().setResMsg("交易成功");
				}else {
					return makeFailMsg("999999", "管理员不存在或者密码错误");
				}
			}else {
				//修改管理员密码
				BaseUser baseUser = new BaseUser();
				String nepwd2 = MD5Util.string2MD5(reqBean.getBodyBean().getPassword());
				baseUser.setUsername(reqBean.getBodyBean().getUsername());
				baseUser.setPwd(nepwd2);
				
				try {
					loginService.editPwd(baseUser);
				} catch (Exception e) {
					return makeFailMsg("999999", "密码修改失败");
				}
				
				/* 生成渠道响应报文Bean */
				resBean = new Tms0004ResBean(); 
				resBean.getHeadBean().setResCode("000000");
				resBean.getHeadBean().setResMsg("交易成功");
			}
			
			
			/* 生成渠道响应报文*/
			XStream resXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			
			resXs.alias("Root", Tms0004ResBean.class);
			resXs.alias("Head", InResHeadBean.class);
			return resXs.toXML(resBean);
		} catch (Exception e) {
			logger.error("生成响应报文异常", e);
			return makeFailMsg("999999", "生成响应报文异常");
		}
	}
}
