package com.boomhope.tms.transaction;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.boomhope.tms.entity.DeployMachine;
import com.boomhope.tms.entity.TransFlow;
import com.boomhope.tms.message.XStreamNameCoder;
import com.boomhope.tms.message.in.InReqHeadBean;
import com.boomhope.tms.message.in.InResHeadBean;
import com.boomhope.tms.message.in.bck.BCK0001ReqBean;
import com.boomhope.tms.message.in.bck.BCK0001ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK0001ResBean;
import com.boomhope.tms.message.in.bck.BCK0001ResBodyBean;
import com.boomhope.tms.message.out.Out86022ReqBean;
import com.boomhope.tms.message.out.Out86022ReqBodyBean;
import com.boomhope.tms.message.out.Out86022ResBean;
import com.boomhope.tms.message.out.Out86022ResBodyBean;
import com.boomhope.tms.message.out.OutHeadBean;
import com.boomhope.tms.message.out.OutResponseBean;
import com.boomhope.tms.service.IDeployMachineService;
import com.boomhope.tms.service.ITransFlowService;
import com.boomhope.tms.socket.SocketClient;
import com.boomhope.tms.util.DateUtil;
import com.boomhope.tms.util.MACProtocolUtils;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XppDriver;

/***
 * 回单机-证件信息查询交易
 * @author shaopeng
 *
 */
public class TransBCK0001 extends BaseTransaction  implements ServletContextListener {
	Logger logger = Logger.getLogger(TransBCK0001.class);
	
	ITransFlowService transFlowService;	// 交易流水服务
	IDeployMachineService deployMachineService = null;
	BCK0001ReqBean inReqBean;	// 渠道请求Bean
	BCK0001ResBean inResBean;	// 渠道响应Bean
	Out86022ReqBean	outReqBean;	// 前置请求Bean
	Out86022ResBean	outResBean;	// 前置响应Bean
	DeployMachine mac = null;
	TransFlow transFlow;	// 交易流水Bean
	
	public String handle(String inReqMsg){
		
		// 创建交易流水服务
		try {
			transFlowService = (ITransFlowService) TransBCK0001.getBean("TransFlowService");
			deployMachineService = (IDeployMachineService) TransTms0001.getBean("deployMachineService");
		} catch (Exception e) {
			logger.error("调用交易流水Service失败", e);
			return makeResErrorMsg("调用交易流水Service失败","service");
		}
		try {
			try {
				/* 解析渠道请求报文*/
				inReqBean = makeInReqBean(inReqMsg);
			} catch (Exception e) {
				logger.error("解析渠道请求报文异常", e);
				return makeResErrorMsg("解析渠道请求报文异常","service");
			}
			// 获取机器部署信息
			mac = deployMachineService.getDeployMachineById(inReqBean.getHeadBean().getMachineNo());
			/* 记录流水信息 */
			addTransFlow();
			
			/* 生成前置请求报文 */
			byte[] outReqMsg = makeOutReqMsg();
			logger.info("向前置发送请求"+outReqMsg);
			
			/* 发送前置交易请求 */
			String outResMsg = new SocketClient().sendMsg(outReqMsg);
			logger.info("接收拉置响应"+outResMsg);
			
			/* 解析前置响应报文 */
			outResBean = makeOutResBean(outResMsg);
			
			/* 更新流水信息 */
			updateTransFlow();
			
			/* 生成并返回渠道响应报文 */
			return makeResMsg();
		} catch (Exception e) {
			return makeResErrorMsg(e.getMessage(),null);
		}
		
	}
	
	private void updateTransFlow() throws Exception {
		try {
			transFlow.setBusinessStatus(outResBean.getResponseBean().getRetCode());
			transFlow.setTransStatus("S");
			transFlow.setTransStatusDesc("交易成功");
			transFlowService.updateTransFlow(transFlow);
		} catch (Exception e) {
			logger.error("前置返回交易入库失败", e);
			throw new Exception("前置返回交易入库失败", e);
		}
	}

	private String makeResErrorMsg(String msg,String service) {
		if(null == service){
			//修改入库失败操作
			transFlow.setTransStatus("E");
			transFlow.setTransStatusDesc("交易失败");
			transFlowService.updateTransFlow(transFlow);
		}
		inResBean = new BCK0001ResBean();
		BCK0001ResBodyBean bck0001ResBodyBean = new BCK0001ResBodyBean();
		inResBean.setBody(bck0001ResBodyBean);
		InResHeadBean inResHeadBean = new InResHeadBean();
		inResHeadBean.setResCode("44444");
		inResHeadBean.setResMsg(msg);
		inResBean.setHeadBean(inResHeadBean);
		
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK0001ResBean.class);
		return reqXs.toXML(inResBean);
	}

	/***
	 * 解析前置返回报文Bean
	 * @param outResMsg 前置响应报文
	 * @return
	 */
	private Out86022ResBean makeOutResBean(String outResMsg) throws Exception{
		try {
			logger.info("前置返回报文：\n"+outResMsg);
			outResMsg = outResMsg.substring(outResMsg.indexOf("<ROOT>"));
			/* 解析前置响应报文*/
			XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			
			reqXs.alias("ROOT", Out86022ResBean.class);
			reqXs.alias("HEAD", OutHeadBean.class);
			reqXs.alias("RESPONSE", OutResponseBean.class);
			reqXs.alias("BODY", Out86022ResBodyBean.class);
			
			return (Out86022ResBean)reqXs.fromXML(outResMsg);
		} catch (Exception e) {
			logger.error("解析前置返回报文异常", e);
			throw new Exception("解析前置返回报文异常", e);
		}

	}
	
	/***
	 * 生成前置请求报文
	 * @return
	 */
	private byte[] makeOutReqMsg() throws Exception{
		try {
			outReqBean = new Out86022ReqBean();
			Out86022ReqBodyBean out86022ReqBodyBean = new Out86022ReqBodyBean();
			out86022ReqBodyBean.setID_TYPE(inReqBean.getBody().getIdType());
			out86022ReqBodyBean.setID_NO(inReqBean.getBody().getIdNo());
			out86022ReqBodyBean.setCUST_NAME(inReqBean.getBody().getCustomName());
			OutHeadBean headBean = new OutHeadBean();
			headBean.setTRAN_CODE("03445");
			headBean.setCARD_ACCP_TERM_ID(inReqBean.getHeadBean().getMachineNo());//自助设备编号
			headBean.setMER_NO(mac.getMerNo());//商户号
			headBean.setINST_NO(inReqBean.getHeadBean().getBranchNo());//机构号
			headBean.setTELLER_NO(mac.getTellerNo());// 柜员号
			headBean.setCHANNEL(mac.getChannel());// 渠道号
			headBean.setTRAN_DATE(DateUtil.getDateTime("yyyyMMdd"));
			headBean.setTRAN_TIME(DateUtil.getDateTime("HHmmss"));
			outReqBean.setBODY(out86022ReqBodyBean);
			outReqBean.setHeadBean(headBean);
			
			//转换xml
			XStream xs = new XStream(new XppDriver(new XStreamNameCoder()));
			xs.autodetectAnnotations(true);
			
			xs.alias("ROOT", Out86022ReqBean.class);
			xs.alias("HEAD", OutHeadBean.class);
			xs.alias("BODY", Out86022ReqBodyBean.class);
			String xml = "<?xml version=\"1.0\" encoding=\"GBK\" ?>\n"+xs.toXML(outReqBean);
			logger.info("发送前置报文：\n"+xml);
			byte[] bbb = xml.getBytes("GBK");
			
			byte[] re = MACProtocolUtils.toRequest(mac.getMackeyflag(), bbb);
			return re;
		} catch (Exception e) {
			logger.error("生成前置请求报文异常",e);
			throw new Exception("生成前置请求报文异常", e);
		}
		
	}
	
	/***
	 * 新增交易流水
	 * @throws Exception 
	 */
	private void addTransFlow() throws Exception{
		try {
			transFlow = new TransFlow();
			transFlow.setTransCode("BCK_0001");
			transFlow.setTransName("回单机-证件信息查询");
			transFlow.setProductCode("BCK-回单机");
			transFlow.setMachineType(inReqBean.getHeadBean().getMachineType());
			transFlow.setBranchNo(inReqBean.getHeadBean().getBranchNo());
			transFlow.setMachineId(inReqBean.getHeadBean().getMachineNo());
			transFlow.setBusinessStatus("0");
			transFlow.setTransStatus("1");
			transFlow.setTransStatusDesc("接收交易请求");
			transFlow.setTransDate(DateUtil.getDateTime("yyyyMMdd"));
			transFlow.setTransTime(DateUtil.getDateTime("HHmmss"));
			
			transFlowService.addTransFlow(transFlow);
		} catch (Exception e) {
			logger.error("接收交易请求入库失败", e);
			throw new Exception("接收交易请求入库失败", e);
		}
		
	}
	
	/***
	 * 解析渠道报文并生成请求Bean
	 * @param inReqMsg
	 * @return
	 * @throws Exception 
	 */
	private BCK0001ReqBean makeInReqBean(String inReqMsg){
		/* 解析渠道请求报文*/
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		
		reqXs.alias("Root", BCK0001ReqBean.class);
		reqXs.alias("Head", InReqHeadBean.class);
		reqXs.alias("Body", BCK0001ReqBodyBean.class);
		return (BCK0001ReqBean)reqXs.fromXML(inReqMsg);
	}
	
	/***
	 * 生成渠道响应报文
	 * @return
	 */
	private String makeResMsg() throws Exception{
		try {
			inResBean = new BCK0001ResBean();
			BCK0001ResBodyBean bck0001ResBodyBean = new BCK0001ResBodyBean();
			bck0001ResBodyBean.setFILE_NAME(outResBean.getBODY().getFILE_NAME());
			bck0001ResBodyBean.setSVR_DATE(outResBean.getBODY().getSVR_DATE());
			bck0001ResBodyBean.setSVR_JRNL_NO(outResBean.getBODY().getSVR_JRNL_NO());
			bck0001ResBodyBean.setSVR_RET_CODE(outResBean.getBODY().getSVR_RET_CODE());
			bck0001ResBodyBean.setSVR_RET_DESC(outResBean.getBODY().getSVR_RET_DESC());
			inResBean.setBody(bck0001ResBodyBean);
			InResHeadBean inResHeadBean = new InResHeadBean();
			inResHeadBean.setResCode(outResBean.getResponseBean().getRetCode());
			inResHeadBean.setResMsg(outResBean.getResponseBean().getRetMsg());
			inResBean.setHeadBean(inResHeadBean);
			
			XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			reqXs.alias("Root", BCK0001ResBean.class);
			return reqXs.toXML(inResBean);
		} catch (Exception e) {
			logger.error("生成渠道响应报文异常", e);
			throw new Exception("生成渠道响应报文异常", e);
		}
		
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
