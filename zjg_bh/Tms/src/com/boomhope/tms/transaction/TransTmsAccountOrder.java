package com.boomhope.tms.transaction;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.boomhope.tms.entity.BaseUnit;
import com.boomhope.tms.entity.DeployMachine;
import com.boomhope.tms.entity.orcl.CdjOrder;
import com.boomhope.tms.message.XStreamNameCoder;
import com.boomhope.tms.message.account.in.tms.TmsAccountSaveOrderReqBean;
import com.boomhope.tms.message.account.in.tms.TmsAccountSaveOrderReqBodyBean;
import com.boomhope.tms.message.account.in.tms.TmsAccountSaveOrderResBean;
import com.boomhope.tms.message.in.InReqHeadBean;
import com.boomhope.tms.service.IFlowService;
import com.boomhope.tms.util.DateUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XppDriver;

/***
 * 开户入库交易
 */
public class TransTmsAccountOrder extends BaseTransaction implements ServletContextListener{

	Logger logger = Logger.getLogger(TransTmsAccountOrder.class);

	/***
	 * 交易处理
	 * @param reqMsg 请求报文
	 * @return  响应报文
	 */
	public String handle(String reqMsg){
		
		/***
		 * 1. 解析请求报文
		 */
		
		// 部署机器Service
		IFlowService flowService = null;
		try {
			flowService = (IFlowService) TransTmsAccountOrder.getBean("flowService");
		} catch (Exception e) {
			logger.error("调用开户流水Service失败",e);
			return makeResMsg("999999", "调用开户Service失败:" + e.getMessage());
		}
		try {
			/* 解析请求报文 */
			XStream reqXs = new XStream(new DomDriver("UTF-8"));
			reqXs.alias("Root", TmsAccountSaveOrderReqBean.class);
			reqXs.alias("Head", InReqHeadBean.class);
			reqXs.alias("Body", TmsAccountSaveOrderReqBodyBean.class);
		
			/* 请求报文Bean */
			TmsAccountSaveOrderReqBean reqBean = (TmsAccountSaveOrderReqBean)reqXs.fromXML(reqMsg);
			TmsAccountSaveOrderReqBodyBean orderReqBodyBean = reqBean.getBody();
			CdjOrder cdjOrder = new CdjOrder();
			//机构编号
			BaseUnit baseUnit = new BaseUnit();
			baseUnit.setUnitCode(orderReqBodyBean.getUNIT_CODE());
			cdjOrder.setBaseUnit(baseUnit);
			
			cdjOrder.setUnitName(orderReqBodyBean.getUNIT_NAME());
			
			//卡号
			cdjOrder.setCardNo(orderReqBodyBean.getCARD_NO());
			//凭证号
			cdjOrder.setCertNo(orderReqBodyBean.getCERT_NO());
			cdjOrder.setCreateDate(DateUtil.getDateTime("yyyyMMddHHmmss"));//创建时间
			cdjOrder.setCustomerName(orderReqBodyBean.getCUSTOMER_NAME());//户名
			//机器出厂编号
			DeployMachine deployMachine = new DeployMachine();
			deployMachine.setMacFacNum(orderReqBodyBean.getTERMINAL_CODE());
			cdjOrder.setDeployMachine(deployMachine);
			//存款金额
			cdjOrder.setDepositAmt(orderReqBodyBean.getDEPOSIT_AMT());
			//是否加密
			cdjOrder.setDepositPasswordEnabled(orderReqBodyBean.getDEPOSIT_PASSWORD_ENABLED());
			//大写存期
			cdjOrder.setDepositPeriod(orderReqBodyBean.getDEPOSIT_PERIOD());
			//是否转存
			cdjOrder.setDepositResaveEnabled(orderReqBodyBean.getDEPOSIT_RESAVE_ENABLED());
			//
			//cdjOrder.setInterest(orderReqBodyBean.getINTEREST());
			//
			//cdjOrder.setOrderNo(orderReqBodyBean.getORDER_NO());
			//
			//cdjOrder.setPoint(orderReqBodyBean.getPOINT());
			//产品编号
			cdjOrder.setProductCode(orderReqBodyBean.getPRODUCT_CODE());
			//产品名称
			cdjOrder.setProductName(orderReqBodyBean.getPRODUCT_NAME());
			//利率
			cdjOrder.setRate(orderReqBodyBean.getRATE());
			//
			cdjOrder.setRemark(orderReqBodyBean.getREMARK());
			//接口02808前置响应
			cdjOrder.setRep_02808(orderReqBodyBean.getREP_02808());
			cdjOrder.setReq_02808(orderReqBodyBean.getREQ_02808());
			cdjOrder.setRep_07505(orderReqBodyBean.getREP_07505());
			cdjOrder.setReq_07505(orderReqBodyBean.getREQ_07505());
			cdjOrder.setRep_07506(orderReqBodyBean.getREP_07506());
			cdjOrder.setReq_07506(orderReqBodyBean.getREQ_07506());
			//状态
			cdjOrder.setStatus("0");
			//存单账号
			cdjOrder.setSubAccountNo(orderReqBodyBean.getSUB_ACCOUNT_NO());
			cdjOrder.setPayType(orderReqBodyBean.getPAY_TYPE());
			flowService.addOrderCDJ(cdjOrder);
			XStream resXs = new XStream(new XppDriver(new XStreamNameCoder()));
			resXs.autodetectAnnotations(true);

			TmsAccountSaveOrderResBean resBean = new TmsAccountSaveOrderResBean();
			resBean.getHeadBean().setResCode("000000");
			resBean.getHeadBean().setResMsg("成功");
			
			return resXs.toXML(resBean);
		} catch (Exception e) {
			logger.error("开户流水入库失败:"+e.getMessage(), e);
			return makeResMsg("999999", "开户流水入库失败:"+e.getMessage());
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

		TmsAccountSaveOrderResBean resBean = new TmsAccountSaveOrderResBean();
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
