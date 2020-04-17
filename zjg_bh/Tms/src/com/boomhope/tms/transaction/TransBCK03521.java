package com.boomhope.tms.transaction;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.boomhope.tms.entity.DeployMachine;
import com.boomhope.tms.entity.TransFlow;
import com.boomhope.tms.message.XStreamNameCoder;
import com.boomhope.tms.message.in.InReqHeadBean;
import com.boomhope.tms.message.in.InResHeadBean;
import com.boomhope.tms.message.in.bck.BCK03521ReqBean;
import com.boomhope.tms.message.in.bck.BCK03521ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK03521ResBean;
import com.boomhope.tms.message.in.bck.BCK03521ResBodyBean;
import com.boomhope.tms.message.out.Out03521ReqBean;
import com.boomhope.tms.message.out.Out03521ReqBodyBean;
import com.boomhope.tms.message.out.Out03521ResBean;
import com.boomhope.tms.message.out.Out03521ResBodyBean;
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
/**
 *账户信息查询及密码验证-前置03521																																																																											
 * 
 * @throws Exception
 */
public class TransBCK03521 extends BaseTransaction implements ServletContextListener{
	Logger logger = Logger.getLogger(TransBCK03521.class);
	ITransFlowService transFlowService;	// 交易流水服务
	IDeployMachineService deployMachineService = null;
	BCK03521ReqBean inReqBean;	// 渠道请求Bean
	BCK03521ResBean inResBean;	// 渠道响应Bean
	Out03521ReqBean	outReqBean;	// 前置请求Bean
	Out03521ResBean outResBean;	// 前置响应Bean
	TransFlow transFlow;	// 交易流水Bean
	DeployMachine mac = null;
	public String handle(String msg) {
		try {
			transFlowService = (ITransFlowService) TransBCK03521.getBean("TransFlowService");
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
			logger.info("向前置发送03521请求"+outReqMsg);
			
			//发送前置交易请求 
			String outResMsg = new SocketClient().sendMsg(outReqMsg);
			logger.info("接收前置03521响应"+outResMsg);
			
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
			transFlow.setTransCode("BCK_03521");
			transFlow.setTransName("回单机-账户信息查询及密码验证");
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
				
		inResBean = new BCK03521ResBean();
		InResHeadBean inResHeadBean = new InResHeadBean();
		inResHeadBean.setResCode("44444");
		inResHeadBean.setResMsg(msg);
		inResBean.setHeadBean(inResHeadBean);
		
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK03521ResBean.class);
		return reqXs.toXML(inResBean);
	}

	private String makeResMsg() throws Exception{
		try {
			inResBean = new BCK03521ResBean();
			XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			
			BCK03521ResBodyBean bck03521ResBodyBean = new BCK03521ResBodyBean();
			
			bck03521ResBodyBean.setCARD_NO(outResBean.getBODY().getCARD_NO());//账号
			bck03521ResBodyBean.setHBR_INST_NO(outResBean.getBODY().getHBR_INST_NO());//开户机构
			bck03521ResBodyBean.setCUST_NO(outResBean.getBODY().getCUST_NO());//客户号
			bck03521ResBodyBean.setCERT_NO(outResBean.getBODY().getCERT_NO());//凭证号
			bck03521ResBodyBean.setDRAW_COND(outResBean.getBODY().getDRAW_COND());//支付条件
			bck03521ResBodyBean.setDEP_TERM(outResBean.getBODY().getDEP_TERM());//存期
			bck03521ResBodyBean.setTYPE(outResBean.getBODY().getTYPE());//类别
			bck03521ResBodyBean.setACCT_TYPE(outResBean.getBODY().getACCT_TYPE());//账户种类
			bck03521ResBodyBean.setDB_CR_FLAG(outResBean.getBODY().getDB_CR_FLAG());//余额方向
			bck03521ResBodyBean.setD_BAL(outResBean.getBODY().getD_BAL());//借余额
			bck03521ResBodyBean.setC_BAL(outResBean.getBODY().getC_BAL());//贷余额
			bck03521ResBodyBean.setBALANCE(outResBean.getBODY().getBALANCE());//余额
			bck03521ResBodyBean.setSTOP_AMT(outResBean.getBODY().getSTOP_AMT());//止付金额
			bck03521ResBodyBean.setACCT_STAT(outResBean.getBODY().getACCT_STAT());//账户状态
			bck03521ResBodyBean.setLOST_STAT(outResBean.getBODY().getLOST_STAT());//挂失状态
			bck03521ResBodyBean.setHOLD_STAT(outResBean.getBODY().getHOLD_STAT());//冻结状态
			bck03521ResBodyBean.setSTOP_STAT(outResBean.getBODY().getSTOP_STAT());//止付状态
			bck03521ResBodyBean.setACCT_NAME(outResBean.getBODY().getACCT_NAME());//名称
			bck03521ResBodyBean.setSAVE_TYPE(outResBean.getBODY().getSAVE_TYPE());//储种
			bck03521ResBodyBean.setADDRESS(outResBean.getBODY().getADDRESS());//地址
			bck03521ResBodyBean.setITEM(outResBean.getBODY().getITEM());//科目
			bck03521ResBodyBean.setCLEAR_FLAG(outResBean.getBODY().getCLEAR_FLAG());//结算户标志
			//接口改变后新增的字段
			bck03521ResBodyBean.setCURRENCY(outResBean.getBODY().getCURRENCY());//币种
			bck03521ResBodyBean.setSVR_ORGAACCNM(outResBean.getBODY().getSVR_ORGAACCNM());//单位结算卡完整户名
			bck03521ResBodyBean.setSVR_ADD_TYPE(outResBean.getBODY().getSVR_ADD_TYPE());//关联账户性质
			bck03521ResBodyBean.setSVR_ACC_STAT(outResBean.getBODY().getSVR_ACC_STAT());//关联账户状态
			inResBean.setBody(bck03521ResBodyBean);
			
			InResHeadBean inResHeadBean = new InResHeadBean();
			inResHeadBean.setResCode(outResBean.getResponseBean().getRetCode());
			inResHeadBean.setResMsg(outResBean.getResponseBean().getRetMsg());
			inResBean.setHeadBean(inResHeadBean);
			reqXs.alias("Root", BCK03521ResBean.class);
			return reqXs.toXML(inResBean);
		} catch (Exception e) {
			logger.error("生成渠道响应报文异常", e);
			throw new Exception("生成渠道响应报文异常", e);
		}
		
	}

	private Out03521ResBean makeOutResBean(String outResMsg) throws Exception{
		try {
			logger.info("前置返回报文：\n"+outResMsg);
			outResMsg = outResMsg.substring(outResMsg.indexOf("<ROOT>"));
			/* 解析前置响应报文*/
			XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			
			reqXs.alias("ROOT", Out03521ResBean.class);
			reqXs.alias("HEAD", OutHeadBean.class);
			reqXs.alias("RESPONSE", OutResponseBean.class);
			reqXs.alias("BODY", Out03521ResBodyBean.class);
			
			return (Out03521ResBean)reqXs.fromXML(outResMsg);
		} catch (Exception e) {
			logger.error("解析前置返回报文异常", e);
			throw new Exception("解析前置返回报文异常", e);
		}
		
	}

	private byte[] makeOutReqMsg() throws Exception{
		try {
			outReqBean = new Out03521ReqBean();
			Out03521ReqBodyBean out03521ReqBodyBean = new Out03521ReqBodyBean();
			out03521ReqBodyBean.setACCT_NO(inReqBean.getBody().getACCT_NO()); //账号
			out03521ReqBodyBean.setPIN_VAL_FLAG(inReqBean.getBody().getPIN_VAL_FLAG()); //验密标志
			if(inReqBean.getBody().getPIN_VAL_FLAG().equals("1")){
				/*凭证密码加密处理*/
				String pwd = inReqBean.getBody().getPASSWORD();//获取终端密文密码
//				logger.debug("*******终端传送密文："+pwd);
				String acctNo = inReqBean.getBody().getACCT_NO();//获取存单账号
//				logger.debug("*******存单账号："+acctNo);
				logger.info("加密前：   "+pwd);
				String tranPwd=EncryptUtils.tranPin310(mac.getWorkkeyflag(), mac.getKeyflag(), acctNo, pwd);
				logger.info("加密后：   "+tranPwd);
				logger.debug("*******转加密密文："+tranPwd);
				out03521ReqBodyBean.setPASSWORD(tranPwd);
			}else{
				out03521ReqBodyBean.setPASSWORD(inReqBean.getBody().getPASSWORD());
			}
			
			outReqBean.setBODY(out03521ReqBodyBean);
			OutHeadBean outHeadBean = new OutHeadBean();
			outHeadBean.setTRAN_CODE("03521");
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
			
			xs.alias("ROOT", Out03521ReqBean.class);
			xs.alias("HEAD", OutHeadBean.class);
			xs.alias("BODY", Out03521ReqBodyBean.class);
			String xml = "<?xml version=\"1.0\" encoding=\"GBK\" ?>\n"+xs.toXML(outReqBean);
			logger.info("发送前置报文：\n"+xml);
			byte[] bbb = xml.getBytes("GBK");
			return MACProtocolUtils.toRequest(mac.getMackeyflag(), bbb);
		} catch (Exception e) {
			logger.error("生成前置请求报文异常",e);
			throw new Exception("生成前置请求报文异常", e);
		}
		
	}

	private BCK03521ReqBean makeInReqBean(String msg) throws Exception{
		/* 解析渠道请求报文*/
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		
		reqXs.alias("Root", BCK03521ReqBean.class);
		reqXs.alias("Head", InReqHeadBean.class);
		reqXs.alias("Body", BCK03521ReqBodyBean.class);
		
		return (BCK03521ReqBean)reqXs.fromXML(msg);
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


