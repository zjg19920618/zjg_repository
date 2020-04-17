package com.boomhope.Bill.TransService.BudingJrnlNo.Interface;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.boomhope.Bill.TransService.AccTransfer.TransferUtil.SocketClient;
import com.boomhope.Bill.TransService.BudingJrnlNo.bean.BulidingJrnNoBean;
import com.boomhope.tms.message.XStreamNameCoder;
import com.boomhope.tms.message.account.in.BCK03845ResBean;
import com.boomhope.tms.message.in.bck.BCK02906ResBean;
import com.boomhope.tms.message.in.bck.BCK03531ResBean;
import com.boomhope.tms.message.in.bck.BCK03532ResBean;
import com.boomhope.tms.message.in.bck.BCK03533ResBean;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class InterfaceSendMsg {
static Logger logger = Logger.getLogger(InterfaceSendMsg.class);
	
	//报文返回成功
	public static String SUCCESS = "000000";
	public static String KEY_PRODUCT_RATES="KEY_PRODUCT_RATES";
	public static String PRODUCT_INFOS="PRODUCT_INFOS";
	public static String TEL_MSG = "TEL_MSG";
	public static String TRANSFER_CANCEL_MSG="TRANSFER_CANCEL_MSG";
	public static String RESULT="RESULT";
	
	 
	
	/**
	 * 卡系统-交易辅助登记
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map inter03531(BulidingJrnNoBean bean)throws Exception{
		//获取数据
		Map<String, String> map = new HashMap<String, String>();
		map.put("WORK_NO", bean.getWorkNo());//工号
		map.put("JRNL_NO", bean.getJrnlNo());//流水号
		
		Map params = new HashMap();
		String reqMsg=CreateXmlMsg.BCK_03531(map);
		SocketClient socketClient =new SocketClient();
		socketClient.createSocket();
		logger.info("请求报文："+reqMsg);
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK03531ResBean.class);
		BCK03531ResBean bck03531ResBean = (BCK03531ResBean)reqXs.fromXML(resMsg);
		if(bck03531ResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","调用接口03531失败");
			return params;
		}
		//获取返回报文错误码和错误信息
		String resCode = bck03531ResBean.getHeadBean().getResCode();
		String errMsg = bck03531ResBean.getHeadBean().getResMsg();
		logger.info("卡系统-交易辅助登记resCode:"+resCode+"resMsg:"+errMsg);
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		params.put("RETURN_CODE",  bck03531ResBean.getBody().getRETURN_CODE().trim());
		params.put("resCode",SUCCESS);
		return params;
	} 
	
	/**
	 * 推荐人奖励领取【17030】前置-03532
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map inter03532(BulidingJrnNoBean bean)throws Exception{
		//获取数据
		Map<String, String> map = new HashMap<String, String>();
		map.put("ACCESS_CODE", bean.getAccessCode());//领取码
		map.put("CUST_NO", bean.getCustNos());//客户号
		
		Map params = new HashMap();
		String reqMsg=CreateXmlMsg.BCK_03532(map);
		SocketClient socketClient =new SocketClient();
		socketClient.createSocket();
		logger.info("请求报文："+reqMsg);
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK03532ResBean.class);
		BCK03532ResBean bck03532ResBean = (BCK03532ResBean)reqXs.fromXML(resMsg);
		if(bck03532ResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","调用接口03532失败");
			return params;
		}
		//获取返回报文错误码和错误信息
		String resCode = bck03532ResBean.getHeadBean().getResCode();
		String errMsg = bck03532ResBean.getHeadBean().getResMsg();
		logger.info("推荐人奖励领取resCode:"+resCode+"resMsg:"+errMsg);
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		params.put("resCode",SUCCESS);
		return params;
	} 
	
	/**
	 * 推荐信息录入【17034】前置-03533
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map inter03533(BulidingJrnNoBean bean)throws Exception{
		//获取数据
		Map<String, String> map = new HashMap<String, String>();
		map.put("REC_NAME", bean.getRecName());//推荐人姓名
		map.put("REC_TEL_NO", bean.getRecTelNo());//推荐人手机号
		map.put("NAME", bean.getName());//被推荐人姓名
		map.put("TEL_NO", bean.getTelNos());//被推荐人手机号
		
		Map params = new HashMap();
		String reqMsg=CreateXmlMsg.BCK_03533(map);
		SocketClient socketClient =new SocketClient();
		socketClient.createSocket();
		logger.info("请求报文："+reqMsg);
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK03533ResBean.class);
		BCK03533ResBean bck03533ResBean = (BCK03533ResBean)reqXs.fromXML(resMsg);
		if(bck03533ResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","调用接口03533失败");
			return params;
		}
		//获取返回报文错误码和错误信息
		String resCode = bck03533ResBean.getHeadBean().getResCode();
		String errMsg = bck03533ResBean.getHeadBean().getResMsg();
		logger.info("推荐信息录入resCode:"+resCode+"resMsg:"+errMsg);
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		params.put("resCode",SUCCESS);
		return params;
	} 
	
	/**
	 * 客户账户基本信息维护【17024】前置-02906
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map inter02906(BulidingJrnNoBean bean)throws Exception{
		//获取数据
		Map<String, String> map = new HashMap<String, String>();
		map.put("QRY_TYPE", bean.getCustNos());//查询条件
		
		Map params = new HashMap();
		String reqMsg=CreateXmlMsg.BCK_02906(map);
		SocketClient socketClient =new SocketClient();
		socketClient.createSocket();
		logger.info("请求报文："+reqMsg);
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK02906ResBean.class);
		BCK02906ResBean bck02906ResBean = (BCK02906ResBean)reqXs.fromXML(resMsg);
		if(bck02906ResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","调用接口02906失败");
			return params;
		}
		//获取返回报文错误码和错误信息
		String resCode = bck02906ResBean.getHeadBean().getResCode();
		String errMsg = bck02906ResBean.getHeadBean().getResMsg();
		logger.info("推荐信息录入resCode:"+resCode+"resMsg:"+errMsg);
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		params.put("resCode",SUCCESS);
		params.put("CUST_NAME",bck02906ResBean.getBody().getCUST_NAME().trim());
		params.put("TEL_NO", bck02906ResBean.getBody().getTEL_NO().trim());
		params.put("CUST_NO", bck02906ResBean.getBody().getCUST_NO().trim());
		return params;
	} 
	/**
	 * 卡信息查询
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map inter03845(BulidingJrnNoBean bean)throws Exception{
		//获取数据
		Map<String, String> map = new HashMap<String, String>();
		map.put("CARD_NO", bean.getQryType());//卡号
		map.put("SUB_ACCT_NO", "");//子账号
		map.put("PASSWD", "");//卡密码
		map.put("ISPIN", "0");//是否验密值为1时则必须进行验密
		
		Map params = new HashMap();
		String reqMsg=CreateXmlMsg.BCK_03845(map);
		SocketClient socketClient =new SocketClient();
		socketClient.createSocket();
		logger.info("请求报文："+reqMsg);
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK03845ResBean.class);
		BCK03845ResBean bck03845ResBean = (BCK03845ResBean)reqXs.fromXML(resMsg);
		if(bck03845ResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","调用接口03845失败");
			return params;
		}
		//获取返回报文错误码和错误信息
		String resCode = bck03845ResBean.getHeadBean().getResCode();
		String errMsg = bck03845ResBean.getHeadBean().getResMsg();
		logger.info("卡信息查询resCode:"+resCode+"resMsg:"+errMsg);
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		if("N".equals(bck03845ResBean.getBody().getCARD_STAT())){
			logger.info("账户未激活 ");
			params.put("resCode","5555");
			params.put("errMsg","您的银行卡未激活，不能办理此业务");
			return params;
		}
		params.put("resCode",SUCCESS);
		params.put("CUST_NO", bck03845ResBean.getBody().getCUST_NO());
		return params;
	}
	
	
}
