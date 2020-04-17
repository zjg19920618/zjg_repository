package com.boomhope.tms.transaction;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.boomhope.tms.message.XStreamNameCoder;
import com.boomhope.tms.message.in.InReqHeadBean;
import com.boomhope.tms.message.in.tms.Tms0002PeriBean;
import com.boomhope.tms.message.in.tms.Tms0002ReqBean;
import com.boomhope.tms.message.in.tms.Tms0002ReqBodyBean;
import com.boomhope.tms.message.in.tms.Tms0002ResBean;
import com.boomhope.tms.service.ITms0002Service;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XppDriver;

/***
 * 交易Tms0001
 * @author shaopeng
 *
 */
public class TransTms0002 extends BaseTransaction implements ServletContextListener{

	Logger logger = Logger.getLogger(TransTms0002.class);

	/***
	 * 交易处理
	 * @param reqMsg 请求报文
	 * @return  响应报文
	 */
	public String handle(String reqMsg){
		
		/***
		 * 1. 解析请求报文
		 * 2. 判断机器状态
		 * 	2.1 机器状态为1-成功 
		 * 		a.更新部署机器表中的"监控状态"、"监控时间"、"监控描述"
		 * 		b.更新外设监控表该机器下所有外设状态为成功
		 *  2.2 机器状态为2-异常
		 *  	a.更新部署机器表中的"监控状态"和"监控时间"、"监控描述"
		 *      b.更新外设监控表中指定外设状态为异常
		 * 3. 生成响应报文
		 */
		
		// 部署机器Service
		ITms0002Service monitorService = null;
		try {
			monitorService = (ITms0002Service) TransTms0002.getBean("Tms0002Service");
		} catch (Exception e) {
			logger.error("调用外设监控Service失败",e);
			return makeResMsg("999999", "调用外设监控Service失败:" + e.getMessage());
		}
		try {
			/* 解析请求报文 */
			XStream reqXs = new XStream(new DomDriver("UTF-8"));
			reqXs.alias("Root", Tms0002ReqBean.class);
			reqXs.alias("Head", InReqHeadBean.class);
			reqXs.alias("Body", Tms0002ReqBodyBean.class);
			reqXs.alias("Peri", Tms0002PeriBean.class);
			reqXs.addImplicitCollection(Tms0002ReqBodyBean.class, "periList");	
		
			/* 请求报文Bean */
			Tms0002ReqBean reqBean = (Tms0002ReqBean)reqXs.fromXML(reqMsg);
			
			/* 判定机器状态,调用相应的处理方法 */
			String machineStatus = reqBean.getBodyBean().getMachineStatus();
			logger.debug("status:" + machineStatus);
			if ("1".equals(machineStatus)){
				logger.debug("sss");
				return monitorService.doSuccess(reqBean);
			}else{
				logger.debug("ffff");
				return monitorService.doFail(reqBean);
			}
		} catch (Exception e) {
			logger.error("机器状态入库失败:"+e.getMessage(), e);
			return makeResMsg("999999", "机器状态入库失败:"+e.getMessage());
		}
		
	}
	
	/***
	 * 生成返回报文
	 * @param resCode
	 * @param resMsg
	 * @return
	 */
	private String makeResMsg(String resCode, String resMsg){
		
		XStream resXs = new XStream(new XppDriver(new XStreamNameCoder()));
		resXs.autodetectAnnotations(true);

		Tms0002ResBean resBean = new Tms0002ResBean();
		resBean.getHeadBean().setResCode(resCode);
		resBean.getHeadBean().setResMsg(resMsg);
		
		return resXs.toXML(resBean);
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
