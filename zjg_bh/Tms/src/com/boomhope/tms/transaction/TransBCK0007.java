package com.boomhope.tms.transaction;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.boomhope.tms.entity.DeployMachine;
import com.boomhope.tms.entity.TransFlow;
import com.boomhope.tms.message.XStreamNameCoder;
import com.boomhope.tms.message.in.InReqHeadBean;
import com.boomhope.tms.message.in.InResHeadBean;
import com.boomhope.tms.message.in.bck.BCK0007ReqBean;
import com.boomhope.tms.message.in.bck.BCK0007ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK0007ResBean;
import com.boomhope.tms.message.in.bck.BCK0007ResBodyBean;
import com.boomhope.tms.message.out.Out75010ReqBean;
import com.boomhope.tms.message.out.Out75010ReqBodyBean;
import com.boomhope.tms.message.out.Out75010ResBean;
import com.boomhope.tms.message.out.Out75010ResBodyBean;
import com.boomhope.tms.message.out.OutHeadBean;
import com.boomhope.tms.message.out.OutResponseBean;
import com.boomhope.tms.service.IDeployMachineService;
import com.boomhope.tms.service.ITransFlowService;
import com.boomhope.tms.socket.SocketClient;
import com.boomhope.tms.util.DateUtil;
import com.boomhope.tms.util.EncryptUtils;
import com.boomhope.tms.util.MACProtocolUtils;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XppDriver;

public class TransBCK0007 extends BaseTransaction implements ServletContextListener{
	Logger logger = Logger.getLogger(TransBCK0007.class);
	ITransFlowService transFlowService;	// 交易流水服务
	IDeployMachineService deployMachineService = null;
	BCK0007ReqBean inReqBean;	// 渠道请求Bean
	BCK0007ResBean inResBean;	// 渠道响应Bean
	Out75010ReqBean	outReqBean;	// 前置请求Bean
	Out75010ResBean	outResBean;	// 前置响应Bean
	TransFlow transFlow;	// 交易流水Bean
	DeployMachine mac = null;
	public String handle(String msg) {
		try {
			transFlowService = (ITransFlowService) TransBCK0007.getBean("TransFlowService");
			deployMachineService = (IDeployMachineService) TransTms0001.getBean("deployMachineService");
		} catch (Exception e) {
			logger.error("调用交易流水Service失败", e);
			return makeResErrorMsg("调用交易流水Service失败","service");
		}
		try {
			try {
				//解析渠道报文
				inReqBean = makeInReqBean(msg);
			} catch (Exception e) {
				logger.error("解析渠道请求报文异常", e);
				return makeResErrorMsg("解析渠道请求报文异常","service");
			}
			// 获取机器部署信息
			mac = deployMachineService.getDeployMachineById(inReqBean.getHeadBean().getMachineNo());
			/* 记录流水信息 */
			addTransFlow();
			
			//生成前置请求报文 
			byte[] outReqMsg = makeOutReqMsg();
			logger.info("向前置发送03845请求"+outReqMsg);
			
			//发送前置交易请求 
			String outResMsg = new SocketClient().sendMsg(outReqMsg);
			logger.info("接收前置03845响应"+outResMsg);
			
			//解析前置响应报文 
			outResBean = makeOutResBean(outResMsg);
			/* 更新流水信息 */
			updateTransFlow();
			//返回信息
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

	private void addTransFlow() throws Exception {
		try {
			transFlow = new TransFlow();
			transFlow.setTransCode("BCK_0007");
			transFlow.setTransName("回单机-卡信息查询");
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

	private String makeResErrorMsg(String msg,String service) {
		if(null == service){
			//修改入库失败操作
			transFlow.setTransStatus("E");
			transFlow.setTransStatusDesc("交易失败");
			transFlowService.updateTransFlow(transFlow);
		}
				
		inResBean = new BCK0007ResBean();
		BCK0007ResBodyBean bck0007ResBodyBean = new BCK0007ResBodyBean();
		
		inResBean.setBody(bck0007ResBodyBean);
		InResHeadBean inResHeadBean = new InResHeadBean();
		inResHeadBean.setResCode("44444");
		inResHeadBean.setResMsg(msg);
		inResBean.setHeadBean(inResHeadBean);
		
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK0007ResBean.class);
		return reqXs.toXML(inResBean);
	}

	private String makeResMsg() throws Exception{
		try {
			inResBean = new BCK0007ResBean();
			XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			BCK0007ResBodyBean bck0007ResBodyBean = new BCK0007ResBodyBean();
			bck0007ResBodyBean.setSVR_JRNL(outResBean.getBODY().getSVR_JRNL());
			bck0007ResBodyBean.setCARD_STAT(outResBean.getBODY().getCARD_STAT());
			bck0007ResBodyBean.setACCT_STAT(outResBean.getBODY().getACCT_STAT());
			bck0007ResBodyBean.setACCT_NAME(outResBean.getBODY().getACCT_NAME());
			bck0007ResBodyBean.setID_TYPE(outResBean.getBODY().getID_TYPE());
			bck0007ResBodyBean.setID_NO(outResBean.getBODY().getID_NO());
			inResBean.setBody(bck0007ResBodyBean);
			InResHeadBean inResHeadBean = new InResHeadBean();
			inResHeadBean.setResCode(outResBean.getResponseBean().getRetCode());
			inResHeadBean.setResMsg(outResBean.getResponseBean().getRetMsg());
			inResBean.setHeadBean(inResHeadBean);
			reqXs.alias("Root", BCK0007ResBean.class);
			return reqXs.toXML(inResBean);
		} catch (Exception e) {
			logger.error("生成渠道响应报文异常", e);
			throw new Exception("生成渠道响应报文异常", e);
		}
		
	}

	private Out75010ResBean makeOutResBean(String outResMsg) throws Exception{
		try {
			logger.info("前置返回报文：\n"+outResMsg);
			outResMsg = outResMsg.substring(outResMsg.indexOf("<ROOT>"));
			/* 解析前置响应报文*/
			XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			
			reqXs.alias("ROOT", Out75010ResBean.class);
			reqXs.alias("HEAD", OutHeadBean.class);
			reqXs.alias("RESPONSE", OutResponseBean.class);
			reqXs.alias("BODY", Out75010ResBodyBean.class);
			
			return (Out75010ResBean)reqXs.fromXML(outResMsg);
		} catch (Exception e) {
			logger.error("解析前置返回报文异常", e);
			throw new Exception("解析前置返回报文异常", e);
		}
		
	}

	private byte[] makeOutReqMsg() throws Exception{
		try {
			outReqBean = new Out75010ReqBean();
			Out75010ReqBodyBean out75010ReqBodyBean = new Out75010ReqBodyBean();
			out75010ReqBodyBean.setCARD_NO(inReqBean.getBody().getCARD_NO());
			out75010ReqBodyBean.setISPIN(inReqBean.getBody().getISPIN());
			if(inReqBean.getBody().getISPIN().equals("1")){
				/*凭证密码加密处理*/
				String pwd = inReqBean.getBody().getPASSWD();//获取终端密文密码
//				logger.debug("*******终端传送密文："+pwd);
				String acctNo = inReqBean.getBody().getCARD_NO();//获取存单账号
//				logger.debug("*******存单账号："+acctNo);
				logger.info("加密前：   "+pwd);
				String tranPwd=EncryptUtils.tranPin310(mac.getWorkkeyflag(), mac.getKeyflag(), acctNo, pwd);
				logger.info("加密后：   "+tranPwd);
				logger.debug("*******转加密密文："+tranPwd);
				out75010ReqBodyBean.setPASSWD(tranPwd);
			}else{
				out75010ReqBodyBean.setPASSWD(inReqBean.getBody().getPASSWD());
			}
			out75010ReqBodyBean.setSUB_ACCT_NO(inReqBean.getBody().getSUB_ACCT_NO());
			outReqBean.setBODY(out75010ReqBodyBean);
			OutHeadBean outHeadBean = new OutHeadBean();
			outHeadBean.setTRAN_CODE("03845");
			outHeadBean.setTRAN_DATE(DateUtil.getDateTime("yyyyMMdd"));
			outHeadBean.setTRAN_TIME(DateUtil.getDateTime("HHmmss"));
			outHeadBean.setCARD_ACCP_TERM_ID(inReqBean.getHeadBean().getMachineNo());//自助设备编号
			outHeadBean.setMER_NO(mac.getMerNo());//商户号
			outHeadBean.setINST_NO(inReqBean.getHeadBean().getBranchNo());//机构号
			outHeadBean.setTELLER_NO(mac.getTellerNo());// 柜员号
			outHeadBean.setCHANNEL(mac.getChannel());// 渠道号
			outReqBean.setHeadBean(outHeadBean);
			XStream xs = new XStream(new XppDriver(new XStreamNameCoder()));
			xs.autodetectAnnotations(true);
			
			xs.alias("ROOT", Out75010ReqBean.class);
			xs.alias("HEAD", OutHeadBean.class);
			xs.alias("BODY", Out75010ReqBodyBean.class);
			String xml = "<?xml version=\"1.0\" encoding=\"GBK\" ?>\n"+xs.toXML(outReqBean);
			logger.info("发送前置报文：\n"+xml);
			byte[] bbb = xml.getBytes("GBK");
			return MACProtocolUtils.toRequest(mac.getMackeyflag(), bbb);
		} catch (Exception e) {
			logger.error("生成前置请求报文异常",e);
			throw new Exception("生成前置请求报文异常", e);
		}
		
	}

	private BCK0007ReqBean makeInReqBean(String msg) throws Exception{
		/* 解析渠道请求报文*/
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		
		reqXs.alias("Root", BCK0007ReqBean.class);
		reqXs.alias("Head", InReqHeadBean.class);
		reqXs.alias("Body", BCK0007ReqBodyBean.class);
		
		return (BCK0007ReqBean)reqXs.fromXML(msg);
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
