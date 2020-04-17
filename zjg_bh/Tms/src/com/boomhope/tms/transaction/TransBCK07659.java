package com.boomhope.tms.transaction;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.boomhope.tms.entity.DeployMachine;
import com.boomhope.tms.entity.TransFlow;
import com.boomhope.tms.message.XStreamNameCoder;
import com.boomhope.tms.message.in.InReqHeadBean;
import com.boomhope.tms.message.in.InResHeadBean;
import com.boomhope.tms.message.in.bck.BCK07659ReqBean;
import com.boomhope.tms.message.in.bck.BCK07659ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK07659ResBean;
import com.boomhope.tms.message.in.bck.BCK07659ResBodyBean;
import com.boomhope.tms.message.out.Out07659ReqBean;
import com.boomhope.tms.message.out.Out07659ReqBodyBean;
import com.boomhope.tms.message.out.Out07659ResBean;
import com.boomhope.tms.message.out.Out07659ResBodyBean;
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


	/**
	 * 柜员查询方式认证
	 * @author Yangtao
	 *
	 */
public class TransBCK07659 extends BaseTransaction implements ServletContextListener{
	Logger logger = Logger.getLogger(TransBCK07659.class);
	ITransFlowService transFlowService;	// 交易流水服务
	IDeployMachineService deployMachineService = null;
	BCK07659ReqBean inReqBean;	// 渠道请求Bean
	BCK07659ResBean inResBean;	// 渠道响应Bean
	Out07659ReqBean	outReqBean;	// 前置请求Bean
	Out07659ResBean	outResBean;	// 前置响应Bean
	DeployMachine mac = null;
	TransFlow transFlow;	// 交易流水Bean
	/**
	 * 生成响应信息
	 * @param msg
	 * @return
	 */
	public String handle(String msg) {
		try {
			transFlowService = (ITransFlowService) TransBCK07659.getBean("TransFlowService");
			deployMachineService = (IDeployMachineService) TransTms0001.getBean("deployMachineService");
		} catch (Exception e) {
			logger.error("调用交易流水Service失败", e);
			return makeResErrorMsg("调用交易流水Service失败","service");
		}
		
		try {
			try {
				logger.info("终端发送的请求报文："+msg);
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
			logger.info("向前置发送07659请求"+outReqMsg);
			
			//发送前置交易请求 
			String outResMsg = new SocketClient().sendMsg(outReqMsg);
			logger.info("接刷前置响应07659请求"+outReqMsg);
			
			//解析前置响应报文 
			outResBean = makeOutResBean(outResMsg);
			
			/* 更新流水信息 */
			updateTransFlow();
			
			//然后将Account07506ResBean类中的数据放入BCK07506ResBean中，返回
			//返回信息
			String resMsg=makeResMsg();
			logger.info("终端返回报文："+resMsg);
			return resMsg;
		} catch (Exception e) {
			String resErrorMsg=makeResErrorMsg(e.getMessage(),null);
			logger.info("终端返回报文："+resErrorMsg);
			logger.error(e);
			return resErrorMsg ;
		}
		
	}
	
    /**
     * 更新交易流水
     * @throws Exception
     */
	private void updateTransFlow() throws Exception {
		try {
			transFlow.setBusinessStatus(outResBean.getResponseBean().getRetCode());
			transFlow.setTransStatus("S");
			transFlow.setTransStatusDesc("交易成功");
			transFlowService.updateTransFlow(transFlow);//更新交易流水
		} catch (Exception e) {
			logger.error("前置返回交易入库失败", e);
			throw new Exception("前置返回交易入库失败", e);
		}
	}
    /**
     * 添加交易流水Bean
     * @throws Exception
     */
	private void addTransFlow() throws Exception {
		try {
			transFlow = new TransFlow();
			transFlow.setTransCode("BCK_07506");//设置交易代码
			transFlow.setTransName("回单机-派发规则查询");//设置交易名称
			transFlow.setProductCode("BCK-回单机");//设置产品编号
			transFlow.setMachineType(inReqBean.getHeadBean().getMachineType());//设置交易机器类型
			transFlow.setBranchNo(inReqBean.getHeadBean().getBranchNo());//设置交易机构号
			transFlow.setMachineId(inReqBean.getHeadBean().getMachineNo());//设置交易机器号
			transFlow.setBusinessStatus("0");//设置业务状态
			transFlow.setTransStatus("1");//设置交易状态
			transFlow.setTransStatusDesc("接收交易请求");//设置交易状态描述
			transFlow.setTransDate(DateUtil.getDateTime("yyyyMMdd"));//交易日期
			transFlow.setTransTime(DateUtil.getDateTime("HHmmss"));//交易时间
			
			transFlowService.addTransFlow(transFlow);
		} catch (Exception e) {
			logger.error("接收交易请求入库失败", e);
			throw new Exception("接收交易请求入库失败", e);
		}
	}
	/**
	 * 交易失败时生成的响应信息
	 * @param msg
	 * @param service
	 * @return
	 */
	private String makeResErrorMsg(String msg,String service) {
		if(null == service){
			//修改入库失败操作
			transFlow.setTransStatus("E");
			transFlow.setTransStatusDesc("交易失败");
			transFlowService.updateTransFlow(transFlow);
		}
		
		inResBean = new BCK07659ResBean();
		BCK07659ResBodyBean BCK07659ResBodyBean = new BCK07659ResBodyBean();
		
		inResBean.setBody(BCK07659ResBodyBean);
		InResHeadBean inResHeadBean = new InResHeadBean();
		inResHeadBean.setResCode("44444");//设置返回代码
		inResHeadBean.setResMsg(msg);//设置返回信息
		inResBean.setHeadBean(inResHeadBean);//添加渠道信息（报文头）
		
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", Out07659ResBean.class);
		return reqXs.toXML(inResBean);
	}
	/**
	 * 生成xml格式的渠道响应报文
	 * @return
	 * @throws Exception
	 */
	private String makeResMsg() throws Exception{
		try {
			inResBean = new BCK07659ResBean();//内联响应报文
			BCK07659ResBodyBean BCK07659ResBodyBean = new BCK07659ResBodyBean();//内联响应报文体
			BCK07659ResBodyBean.setCHECK_MOD(outResBean.getBODY().getCHECK_MOD());//设置认证方式
			inResBean.setBody(BCK07659ResBodyBean);//内联响应报文添加报文体
			InResHeadBean inResHeadBean = new InResHeadBean();//内联响应报文头
			inResHeadBean.setResCode(outResBean.getResponseBean().getRetCode());//设置返回代码
			inResHeadBean.setResMsg(outResBean.getResponseBean().getRetMsg());//设置返回信息
			inResBean.setHeadBean(inResHeadBean);//设置响应报文头
			XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			reqXs.alias("Root", BCK07659ResBean.class);
			return reqXs.toXML(inResBean);
		} catch (Exception e) {
			logger.error("生成渠道响应报文异常", e);
			throw new Exception("生成渠道响应报文异常", e);
		}
		
	}
	/**
	 * 将xml格式的前置响应信息转换成Java对象(柜员认知方式响应报文)
	 * @param outResMsg
	 * @return
	 * @throws Exception
	 */
	private Out07659ResBean makeOutResBean(String outResMsg) throws Exception{
		try {
			logger.info("前置返回报文：\n"+outResMsg);
			outResMsg = outResMsg.substring(outResMsg.indexOf("<ROOT>"));
			/* 解析前置响应报文*/
			XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			
			reqXs.alias("ROOT", Out07659ResBean.class);
			reqXs.alias("HEAD", OutHeadBean.class);
			reqXs.alias("RESPONSE", OutResponseBean.class);
			reqXs.alias("BODY", Out07659ResBodyBean.class);
			
			return (Out07659ResBean)reqXs.fromXML(outResMsg);
		} catch (Exception e) {
			logger.error("解析前置返回报文异常", e);
			throw new Exception("解析前置返回报文异常", e);
		}
		
	}
	/**
	 * 以字节码数组的形式生成前置请求信息
	 * @return
	 * @throws Exception
	 */
	private byte[] makeOutReqMsg() throws Exception{
		try {
			outReqBean = new Out07659ReqBean();  //前置请求报文
			Out07659ReqBodyBean accountBody = new Out07659ReqBodyBean(); //前置请求报文体
			accountBody.setQRY_TELLER_NO(inReqBean.getBody().getQRY_TELLER_NO());//设置柜员号
			outReqBean.setBODY(accountBody);//添加前置请求报文体
			OutHeadBean headBean = new OutHeadBean();//获取请求报文头
			headBean.setTRAN_CODE("07659");//设置交易号
			headBean.setTRAN_DATE(DateUtil.getDateTime("yyyyMMdd"));//设置交易日期
			headBean.setTRAN_TIME(DateUtil.getDateTime("HH:mm:ss"));//设置交易时间
			headBean.setCARD_ACCP_TERM_ID(inReqBean.getHeadBean().getMachineNo());//自助设备编号
			headBean.setMER_NO(mac.getMerNo());//商户号
			headBean.setINST_NO(inReqBean.getHeadBean().getBranchNo());//机构号
			headBean.setTELLER_NO(mac.getTellerNo());// 柜员号
			headBean.setCHANNEL(mac.getChannel());// 渠道号
			outReqBean.setHeadBean(headBean);//添加请求报文头
			//转换xml
			XStream xs = new XStream(new XppDriver(new XStreamNameCoder()));
			xs.autodetectAnnotations(true);
			      
			xs.alias("ROOT", Out07659ReqBean.class);
			xs.alias("HEAD", OutHeadBean.class);
			xs.alias("BODY", Out07659ReqBodyBean.class);
			String xml = "<?xml version=\"1.0\" encoding=\"GBK\" ?>\n"+xs.toXML(outReqBean);
			logger.info("发送前置报文：\n"+xml);
			byte[] bbb = xml.getBytes("GBK");
			//String mac = "ZZQZ.00000001.zak";
			return MACProtocolUtils.toRequest(mac.getMackeyflag(), bbb);
		} catch (Exception e) {
			logger.error("生成前置请求报文异常",e);
			throw new Exception("生成前置请求报文异常", e);
		}
		
	}
    /**
     * 将xml格式的内联请求转换成java对象内联请求报文Bean(柜员请求认证方式)
     * @param msg
     * @return
     * @throws Exception
     */
	private BCK07659ReqBean makeInReqBean(String msg) throws Exception{
		/* 解析渠道请求报文*/
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		
		reqXs.alias("Root", BCK07659ReqBean.class);
		reqXs.alias("Head", InReqHeadBean.class);
		reqXs.alias("Body", BCK07659ReqBodyBean.class);
		
		return (BCK07659ReqBean)reqXs.fromXML(msg);
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
