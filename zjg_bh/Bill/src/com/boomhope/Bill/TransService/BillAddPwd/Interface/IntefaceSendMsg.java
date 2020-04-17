package com.boomhope.Bill.TransService.BillAddPwd.Interface;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.boomhope.Bill.TransService.AccTransfer.TransferUtil.SocketClient;
import com.boomhope.Bill.TransService.BillAddPwd.Bean.AddPasswordBean;

import com.boomhope.Bill.Util.DateUtil;
import com.boomhope.Bill.Util.FileUtil;
import com.boomhope.Bill.Util.FtpFileUtils;
import com.boomhope.Bill.Util.Property;
import com.boomhope.Bill.Util.UtilPreFile;
import com.boomhope.tms.message.XStreamNameCoder;
import com.boomhope.tms.message.in.bck.BCK01325ResBean;
import com.boomhope.tms.message.in.bck.BCK01597ResBean;
import com.boomhope.tms.message.in.bck.BCK02791ResBean;
import com.boomhope.tms.message.in.bck.BCK07601ResBean;
import com.boomhope.tms.message.in.bck.BCK07659ResBean;
import com.boomhope.tms.message.in.bck.BCK07660ResBean;
import com.boomhope.tms.message.in.bck.BCK07670ResBean;
import com.boomhope.tms.message.in.bck.BCK77017ResBean;
import com.boomhope.tms.message.in.bck.BCK07517ResBean;
import com.boomhope.tms.message.in.tms.Tms0007ResBean;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class IntefaceSendMsg {
	
	
static Logger logger = Logger.getLogger(IntefaceSendMsg.class);
		//报文返回成功
	public static String SUCCESS = "000000";
	public static String KEY_PRODUCT_RATES="KEY_PRODUCT_RATES";
	public static String PRODUCT_INFOS="PRODUCT_INFOS";
	public static String RESULT="RESULT";
	public static String TB_MSG = "TB_MSG";
	public static String SUB_ACC_MSG = "SUB_ACC_MSG";
	public static String E_ACC_MSG ="E_ACC_MSG";
	
	/**
	 * 账号信息综合查询【02880】-前置07601
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static Map inter07601(AddPasswordBean bean)throws Exception{
		//获取数据
		Map<String,String> map = new HashMap<String,String>();
		
		//校验银行卡时上送
		map.put("ACCT_NO", bean.getAccNo());//账号/卡号-子账号
		map.put("SUB_ACCT_NO", "");//
		map.put("CHK_PIN", bean.getIsCheckPass());//是否验密(0：不验密。1：验密)
		if("1".equals(bean.getIsCheckPass())){
			map.put("PASSWD", bean.getBillPass());//密码
		}else{
			map.put("PASSWD", "");//密码
		}
		map.put("CERT_TYPE", bean.getBillType());//凭证种类
		map.put("CERT_NO_ADD", bean.getBillNo());//凭证号
		
		Map<String,String> params = new HashMap<String,String>();
		String reqMsg=CreateXmlMsg.BCK_07601(map);
		logger.info("请求报文："+reqMsg);
		
		SocketClient socketClient =new SocketClient();
		socketClient.createSocket();
		
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK07601ResBean.class);
		BCK07601ResBean bck07601ResBean = (BCK07601ResBean)reqXs.fromXML(resMsg);
		
		if(bck07601ResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","调用接口07601失败");
			return params;
		}
		String resCode = bck07601ResBean.getHeadBean().getResCode();
		String errMsg = bck07601ResBean.getHeadBean().getResMsg();
		logger.info("增设密码账号信息综合查询resCode:"+resCode);
		logger.info("增设密码账号信息综合查询resMsg:"+errMsg);
		
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		
		//获取存单账户信息
		bean.setBackAccNo((bck07601ResBean.getBody().getAcctNo()!=null && !"".equals(bck07601ResBean.getBody().getAcctNo().trim()))?bck07601ResBean.getBody().getAcctNo().trim():"");//存单账号
		bean.setDrawCoun((bck07601ResBean.getBody().getDrawCond()!=null && !"".equals(bck07601ResBean.getBody().getDrawCond().trim()))?bck07601ResBean.getBody().getDrawCond().trim():"");//存单是否有密码(0：无密。1：有密码)
		bean.setOpenInstNo((bck07601ResBean.getBody().getOpenInst_No()!=null && !"".equals(bck07601ResBean.getBody().getOpenInst_No().trim()))?bck07601ResBean.getBody().getOpenInst_No().trim():"");//存单开户机构
		bean.setAccState((bck07601ResBean.getBody().getAcctStat()!=null && !"".equals(bck07601ResBean.getBody().getAcctStat().trim()))?bck07601ResBean.getBody().getAcctStat().trim():"");//存单状态
		bean.setOpenQlt((bck07601ResBean.getBody().getOpenQlt()!=null && !"".equals(bck07601ResBean.getBody().getOpenQlt().trim()))?bck07601ResBean.getBody().getOpenQlt().trim():"");//存单开户性质
		bean.setCertNo((bck07601ResBean.getBody().getCertNo()!=null && !"".equals(bck07601ResBean.getBody().getCertNo().trim()))?bck07601ResBean.getBody().getCertNo().trim():"");//返回的存单凭证号
		bean.setIdNo((bck07601ResBean.getBody().getIdNo()!=null && !"".equals(bck07601ResBean.getBody().getIdNo().trim()))?bck07601ResBean.getBody().getIdNo().trim():"");//存单证件号
		bean.setIdName((bck07601ResBean.getBody().getCustName()!=null && !"".equals(bck07601ResBean.getBody().getCustName().trim()))?bck07601ResBean.getBody().getCustName().trim():"");//存单证件姓名
		bean.setBillIdNo((bck07601ResBean.getBody().getIdNo()!=null && !"".equals(bck07601ResBean.getBody().getIdNo().trim()))?bck07601ResBean.getBody().getIdNo().trim():"");//存单证件号
		bean.setBillIdName((bck07601ResBean.getBody().getCustName()!=null && !"".equals(bck07601ResBean.getBody().getCustName().trim()))?bck07601ResBean.getBody().getCustName().trim():"");//存单证件姓名
		bean.setAccName((bck07601ResBean.getBody().getCustName()!=null && !"".equals(bck07601ResBean.getBody().getCustName().trim()))?bck07601ResBean.getBody().getCustName().trim():"");//存单证件姓名
		bean.setAmount((bck07601ResBean.getBody().getEndAmt()!=null && !"".equals(bck07601ResBean.getBody().getEndAmt().trim()))?bck07601ResBean.getBody().getEndAmt().trim():"");//结存额
		bean.setTotalAmt((bck07601ResBean.getBody().getTotalAmt()!=null && !"".equals(bck07601ResBean.getBody().getTotalAmt().trim()))?bck07601ResBean.getBody().getTotalAmt().trim():"");//存折余额
		bean.setProCode((bck07601ResBean.getBody().getProdCode()!=null && !"".equals(bck07601ResBean.getBody().getProdCode().trim()))?bck07601ResBean.getBody().getProdCode().trim():"");//产品代码
		bean.setProName((bck07601ResBean.getBody().getProdName()!=null && !"".equals(bck07601ResBean.getBody().getProdName().trim()))?bck07601ResBean.getBody().getProdName().trim():"");//产品名称
		bean.setSvrDate((bck07601ResBean.getBody().getSvrDate()!=null && !"".equals(bck07601ResBean.getBody().getSvrDate().trim()))?bck07601ResBean.getBody().getSvrDate().trim():"");//核心日期
		bean.setStartDate((bck07601ResBean.getBody().getStartIntDate()!=null && !"".equals(bck07601ResBean.getBody().getStartIntDate().trim()))?bck07601ResBean.getBody().getStartIntDate().trim():"");//起息日
		bean.setOpenDate((bck07601ResBean.getBody().getOpenDate()!=null && !"".equals(bck07601ResBean.getBody().getOpenDate().trim()))?bck07601ResBean.getBody().getOpenDate().trim():"");//开户日
		bean.setDueDate((bck07601ResBean.getBody().getEndDate()!=null && !"".equals(bck07601ResBean.getBody().getEndDate().trim()))?bck07601ResBean.getBody().getEndDate().trim():"");//到期日
	    bean.setCustNo((bck07601ResBean.getBody().getCustNo()!=null && !"".equals(bck07601ResBean.getBody().getCustNo().trim()))?bck07601ResBean.getBody().getCustNo().trim():"");//客户号
		bean.setIdType(bck07601ResBean.getBody().getIdType()!=null && !"".equals(bck07601ResBean.getBody().getIdType().trim())?bck07601ResBean.getBody().getIdType().trim():"");//证件类型
		params.put("resCode",resCode);
		return params;
	}
	/**
	 * 凭证信息综合查询【02882】-前置02791
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static Map inter02791(AddPasswordBean bean)throws Exception{
		//获取数据
		Map<String,String> map = new HashMap<String,String>();
		
		map.put("CERT_TYPE", bean.getBillType());//凭证种类
		map.put("CERT_NO", bean.getBillNo());//凭证号
		
		Map<String,String> params = new HashMap<String,String>();
		String reqMsg=CreateXmlMsg.BCK_02791(map);
		logger.info("请求报文："+reqMsg);
		
		SocketClient socketClient =new SocketClient();
		socketClient.createSocket();
		
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK02791ResBean.class);
		BCK02791ResBean bck02791ResBean = (BCK02791ResBean)reqXs.fromXML(resMsg);
		
		if(bck02791ResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","调用接口02791失败");
			return params;
		}
		String resCode = bck02791ResBean.getHeadBean().getResCode();
		String errMsg = bck02791ResBean.getHeadBean().getResMsg();
		logger.info("存单增设密码凭证号信息综合查询resCode:"+resCode);
		logger.info("存单增设密码凭证号信息综合查询resMsg:"+errMsg);
		
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
	
		//获取存单账户信息
		bean.setAccNo((bck02791ResBean.getBody().getAcctNo()!=null && !"".equals(bck02791ResBean.getBody().getAcctNo().trim()))?bck02791ResBean.getBody().getAcctNo().trim():"");//存单账号
		bean.setDrawCoun((bck02791ResBean.getBody().getDrawCond()!=null && !"".equals(bck02791ResBean.getBody().getDrawCond().trim()))?bck02791ResBean.getBody().getDrawCond().trim():"");//存单是否有密码(0：无密。1：有密码)
		bean.setOpenInstNo((bck02791ResBean.getBody().getOpenInst_No()!=null && !"".equals(bck02791ResBean.getBody().getOpenInst_No().trim()))?bck02791ResBean.getBody().getOpenInst_No().trim():"");//存单开户机构
		bean.setAccState((bck02791ResBean.getBody().getAcctStat()!=null && !"".equals(bck02791ResBean.getBody().getAcctStat().trim()))?bck02791ResBean.getBody().getAcctStat().trim():"");//存单状态
		bean.setOpenQlt((bck02791ResBean.getBody().getOpenQlt()!=null && !"".equals(bck02791ResBean.getBody().getOpenQlt().trim()))?bck02791ResBean.getBody().getOpenQlt().trim():"");//存单开户性质
		bean.setCertNo((bck02791ResBean.getBody().getCertNo()!=null && !"".equals(bck02791ResBean.getBody().getCertNo().trim()))?bck02791ResBean.getBody().getCertNo().trim():"");//返回的存单凭证号
		bean.setIdNo((bck02791ResBean.getBody().getIdNo()!=null && !"".equals(bck02791ResBean.getBody().getIdNo().trim()))?bck02791ResBean.getBody().getIdNo().trim():"");//存单证件号
		bean.setIdName((bck02791ResBean.getBody().getCustName()!=null && !"".equals(bck02791ResBean.getBody().getCustName().trim()))?bck02791ResBean.getBody().getCustName().trim():"");//存单证件姓名
		bean.setBillIdNo((bck02791ResBean.getBody().getIdNo()!=null && !"".equals(bck02791ResBean.getBody().getIdNo().trim()))?bck02791ResBean.getBody().getIdNo().trim():"");//存单证件号
		bean.setBillIdName((bck02791ResBean.getBody().getCustName()!=null && !"".equals(bck02791ResBean.getBody().getCustName().trim()))?bck02791ResBean.getBody().getCustName().trim():"");//存单证件姓名
		bean.setAccName((bck02791ResBean.getBody().getCustName()!=null && !"".equals(bck02791ResBean.getBody().getCustName().trim()))?bck02791ResBean.getBody().getCustName().trim():"");//存单证件姓名
		bean.setAmount((bck02791ResBean.getBody().getEndAmt()!=null && !"".equals(bck02791ResBean.getBody().getEndAmt().trim()))?bck02791ResBean.getBody().getEndAmt().trim():"");//结存额
		bean.setTotalAmt((bck02791ResBean.getBody().getTotalAmt()!=null && !"".equals(bck02791ResBean.getBody().getTotalAmt().trim()))?bck02791ResBean.getBody().getTotalAmt().trim():"");//存折余额
		bean.setProCode((bck02791ResBean.getBody().getProdCode()!=null && !"".equals(bck02791ResBean.getBody().getProdCode().trim()))?bck02791ResBean.getBody().getProdCode().trim():"");//产品代码
		bean.setProName((bck02791ResBean.getBody().getProdName()!=null && !"".equals(bck02791ResBean.getBody().getProdName().trim()))?bck02791ResBean.getBody().getProdName().trim():"");//产品名称
		bean.setSvrDate((bck02791ResBean.getBody().getSvrDate()!=null && !"".equals(bck02791ResBean.getBody().getSvrDate().trim()))?bck02791ResBean.getBody().getSvrDate().trim():"");//核心日期
		bean.setStartDate((bck02791ResBean.getBody().getStartIntDate()!=null && !"".equals(bck02791ResBean.getBody().getStartIntDate().trim()))?bck02791ResBean.getBody().getStartIntDate().trim():"");//起息日
		bean.setOpenDate((bck02791ResBean.getBody().getOpenDate()!=null && !"".equals(bck02791ResBean.getBody().getOpenDate().trim()))?bck02791ResBean.getBody().getOpenDate().trim():"");//开户日
		bean.setDueDate((bck02791ResBean.getBody().getEndDate()!=null && !"".equals(bck02791ResBean.getBody().getEndDate().trim()))?bck02791ResBean.getBody().getEndDate().trim():"");//到期日
				
		params.put("resCode",resCode);
		return params;
	}
	/**
	 * 账号信息综合查询【02880】-前置07601
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static Map inte07601(AddPasswordBean bean)throws Exception{
		//获取数据
		Map<String,String> map = new HashMap<String,String>();
		 
			  
	    map.put("ACCT_NO", "");//账号		
	    map.put("SUB_ACCT_NO","");//子账号
		
		map.put("CHK_PIN", bean.getIsCheckPass());//是否验密(0：不验密。1：验密)
		
		if("1".equals(bean.getIsCheckPass())){
			map.put("PASSWD", bean.getBillPass());//密码
		}else{
			map.put("PASSWD", "");//密码
		}
		map.put("CERT_TYPE", bean.getBillType());//凭证种类
		map.put("CERT_NO_ADD", bean.getBillNo());//凭证号
		
		Map<String,String> params = new HashMap<String,String>();
		String reqMsg=CreateXmlMsg.BCK_07601(map);
		logger.info("请求报文："+reqMsg);
		
		SocketClient socketClient =new SocketClient();
		socketClient.createSocket();
		
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK07601ResBean.class);
		BCK07601ResBean bck07601ResBean = (BCK07601ResBean)reqXs.fromXML(resMsg);
		
		if(bck07601ResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","调用接口07601失败");
			return params;
		}
		String resCode = bck07601ResBean.getHeadBean().getResCode();
		String errMsg = bck07601ResBean.getHeadBean().getResMsg();
		logger.info("存单增设密码凭证号信息综合查询resCode:"+resCode);
		logger.info("存单增设密码凭证号信息综合查询resMsg:"+errMsg);
		
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		
		//获取存单账户信息
		bean.setAccNo((bck07601ResBean.getBody().getOrigAcctNo()!=null && !"".equals(bck07601ResBean.getBody().getOrigAcctNo().trim()))?bck07601ResBean.getBody().getOrigAcctNo().trim():"");//存单账号
		bean.setDrawCoun((bck07601ResBean.getBody().getDrawCond()!=null && !"".equals(bck07601ResBean.getBody().getDrawCond().trim()))?bck07601ResBean.getBody().getDrawCond().trim():"");//存单是否有密码(0：无密。1：有密码)
		bean.setOpenInstNo((bck07601ResBean.getBody().getOpenInst_No()!=null && !"".equals(bck07601ResBean.getBody().getOpenInst_No().trim()))?bck07601ResBean.getBody().getOpenInst_No().trim():"");//存单开户机构
		bean.setAccState((bck07601ResBean.getBody().getAcctStat()!=null && !"".equals(bck07601ResBean.getBody().getAcctStat().trim()))?bck07601ResBean.getBody().getAcctStat().trim():"");//存单状态
		bean.setOpenQlt((bck07601ResBean.getBody().getOpenQlt()!=null && !"".equals(bck07601ResBean.getBody().getOpenQlt().trim()))?bck07601ResBean.getBody().getOpenQlt().trim():"");//存单开户性质
		bean.setCertNo((bck07601ResBean.getBody().getCertNo()!=null && !"".equals(bck07601ResBean.getBody().getCertNo().trim()))?bck07601ResBean.getBody().getCertNo().trim():"");//返回的存单凭证号
		bean.setIdNo((bck07601ResBean.getBody().getIdNo()!=null && !"".equals(bck07601ResBean.getBody().getIdNo().trim()))?bck07601ResBean.getBody().getIdNo().trim():"");//存单证件号
		bean.setIdName((bck07601ResBean.getBody().getCustName()!=null && !"".equals(bck07601ResBean.getBody().getCustName().trim()))?bck07601ResBean.getBody().getCustName().trim():"");//存单证件姓名
		bean.setBillIdNo((bck07601ResBean.getBody().getIdNo()!=null && !"".equals(bck07601ResBean.getBody().getIdNo().trim()))?bck07601ResBean.getBody().getIdNo().trim():"");//存单证件号
		bean.setBillIdName((bck07601ResBean.getBody().getCustName()!=null && !"".equals(bck07601ResBean.getBody().getCustName().trim()))?bck07601ResBean.getBody().getCustName().trim():"");//存单证件姓名
		bean.setAccName((bck07601ResBean.getBody().getCustName()!=null && !"".equals(bck07601ResBean.getBody().getCustName().trim()))?bck07601ResBean.getBody().getCustName().trim():"");//存单证件姓名
		bean.setAmount((bck07601ResBean.getBody().getEndAmt()!=null && !"".equals(bck07601ResBean.getBody().getEndAmt().trim()))?bck07601ResBean.getBody().getEndAmt().trim():"");//结存额
		bean.setTotalAmt((bck07601ResBean.getBody().getTotalAmt()!=null && !"".equals(bck07601ResBean.getBody().getTotalAmt().trim()))?bck07601ResBean.getBody().getTotalAmt().trim():"");//存折余额
		bean.setProCode((bck07601ResBean.getBody().getProdCode()!=null && !"".equals(bck07601ResBean.getBody().getProdCode().trim()))?bck07601ResBean.getBody().getProdCode().trim():"");//产品代码
		bean.setProName((bck07601ResBean.getBody().getProdName()!=null && !"".equals(bck07601ResBean.getBody().getProdName().trim()))?bck07601ResBean.getBody().getProdName().trim():"");//产品名称
		bean.setSvrDate((bck07601ResBean.getBody().getSvrDate()!=null && !"".equals(bck07601ResBean.getBody().getSvrDate().trim()))?bck07601ResBean.getBody().getSvrDate().trim():"");//核心日期
		bean.setStartDate((bck07601ResBean.getBody().getStartIntDate()!=null && !"".equals(bck07601ResBean.getBody().getStartIntDate().trim()))?bck07601ResBean.getBody().getStartIntDate().trim():"");//起息日
		bean.setOpenDate((bck07601ResBean.getBody().getOpenDate()!=null && !"".equals(bck07601ResBean.getBody().getOpenDate().trim()))?bck07601ResBean.getBody().getOpenDate().trim():"");//开户日
		bean.setDueDate((bck07601ResBean.getBody().getEndDate()!=null && !"".equals(bck07601ResBean.getBody().getEndDate().trim()))?bck07601ResBean.getBody().getEndDate().trim():"");//到期日

		params.put("resCode",resCode);
		return params;
	}
	/**
	 * 鉴伪结果上送
	 */
	public static Map tms0007(AddPasswordBean bean)throws Exception{
		//获取数据
		Map<String,String> map = new HashMap<String,String>();
		map.put("status", bean.getJwResult());
		
		Map<String,String> params = new HashMap<String,String>();
		
		String reqMsg=CreateXmlMsg.Tms0007(map);
		
		SocketClient socketClient =new SocketClient();
		socketClient.createSocket();
		
		logger.info("请求报文："+reqMsg);
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", Tms0007ResBean.class);
		Tms0007ResBean tms0007ResBean = (Tms0007ResBean)reqXs.fromXML(resMsg);
		if(tms0007ResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","鉴伪调用失败");
			return params;
		}
		String resCode = tms0007ResBean.getHeadBean().getResCode();
		String errMsg = tms0007ResBean.getHeadBean().getResMsg();
		logger.info("鉴伪结果上送-resCode："+resCode+"鉴伪结果上送-resMsg："+resMsg);
		
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		
		params.put("resCode",resCode);
		return params;		
	}
	/**
	 * 柜员认证方式查询
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map inter07659(AddPasswordBean bean)throws Exception{
		//获取数据
		Map<String, String> map = new HashMap<String, String>();
		map.put("QRY_TELLER_NO", bean.getSupTellerNo());//QRY_TELLER_NO	查询柜员号
		Map params = new HashMap();
		String reqMsg=CreateXmlMsg.BCK_07659(map);
		SocketClient socketClient =new SocketClient();
		socketClient.createSocket();
		logger.info("请求报文："+reqMsg);
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK07659ResBean.class);
		BCK07659ResBean bck07659ResBean = (BCK07659ResBean)reqXs.fromXML(resMsg);
		//获取返回报文错误码和错误信息
		String resCode = bck07659ResBean.getHeadBean().getResCode();
		String errMsg = bck07659ResBean.getHeadBean().getResMsg();
		logger.info("柜员认证方式查询resCode:"+resCode+"resMsg:"+errMsg);
		if(bck07659ResBean == null){
			params.put("resCode",resCode);
			params.put("errMsg","调用接口失败");
			return params;
		}
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		bean.setCheckMod(bck07659ResBean.getBody().getCHECK_MOD());
		logger.info("查询成功后信息："+bean);
		params.put("resCode",SUCCESS);
		return params;
	} 
	/**
	 * 按交易授权前置77017
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map inter77017(AddPasswordBean bean)throws Exception{
		//获取数据
		Map<String, String> map = new HashMap<String, String>();
		map.put("AUTH_TRAN_CODE", bean.getAuthNoCode());//AUTH_TRAN_CODE	授权主交易码
		map.put("SUP_TELLER_NO", bean.getSupTellerNo());//SUP_TELLER_NO	查询柜员号
		map.put("SUP_TELLER_PWD", bean.getSupPass());//SUP_TELLER_PWD	授权密码
		map.put("FIN_PRI_LEN", bean.getFinPriLen());//FIN_PRI_LEN	指纹长度
		map.put("FIN_PRI_VAL", bean.getFinPriVal());//FIN_PRI_VAL	指纹值
		Map params = new HashMap();
		String reqMsg=CreateXmlMsg.BCK_77017(map);
		SocketClient socketClient =new SocketClient();
		socketClient.createSocket();
		logger.info("请求报文："+reqMsg);
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK77017ResBean.class);
		BCK77017ResBean bck77017ResBean = (BCK77017ResBean)reqXs.fromXML(resMsg);
		//获取返回报文错误码和错误信息
		String resCode = bck77017ResBean.getHeadBean().getResCode();
		String errMsg = bck77017ResBean.getHeadBean().getResMsg();
		logger.info("按交易授权resCode："+resCode+"resMsg:"+errMsg);
		if(bck77017ResBean == null){
			params.put("resCode",resCode);
			params.put("errMsg","调用接口077017失败");
			return params;
		}
		if(!SUCCESS.equals(resCode)){
			//700043密码错误，700099指纹错误，700018授权员权限不够
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		logger.info("操作成功后信息："+bean);
		params.put("resCode",SUCCESS);
		return params;
	} 
	/**
	 * 本人身份证查询黑灰名单
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static Map me01597(AddPasswordBean bean)throws Exception{
		//获取数据
		Map<String,String> map = new HashMap<String,String>();
		map.put("PROGRAM_FLAG", "");//程序标识
		map.put("ACC_NO1", "");//出账账号
		map.put("CARD_NO1", "");//出账卡号
		map.put("ACC_NAME1", "");//出账户名
		map.put("BANK_ID1", "");//出账支付行号
		map.put("ID_TYPE1", "");//出账客户证件类型
		map.put("ID_NUMBER1","");//证件号码
		map.put("ID_NAME1", "");//证件姓名
		map.put("ACC_NO2", "");//入账账号
		map.put("CARD_NO2", "");//入账卡号
		map.put("ACC_NAME2", "");//入账户名
		map.put("BANK_ID2", "");//入账支付行号
		map.put("ID_TYPE2", "");//入账客户证件类型
		map.put("ID_NUMBER2", "");//入账客户证件号码
		map.put("ID_NAME2", "");//入账客户证件姓名
		map.put("CCY", "");//币种
		map.put("TRAN_AMT", "");//交易金额
		map.put("MEMO1", "");//预留
		map.put("MEMO2", "");//预留
		map.put("MEMO3", "");//预留
		map.put("MEMO4", "");//预留
		
		//本人黑灰名单查询
		map.put("ID_TYPE1", "1");//本人证件类型
		map.put("ID_NUMBER1", bean.getReadIdNo());//本人证件号码
		map.put("ID_NAME1", bean.getReadIdName());//本人证件姓名
			
		Map<String,String> params = new HashMap<String,String>();
		String reqMsg=CreateXmlMsg.BCK_01597(map);
		logger.info("请求报文："+reqMsg);
		
		SocketClient socketClient =new SocketClient();
		socketClient.createSocket();
		
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK01597ResBean.class);
		BCK01597ResBean bck01597ResBean = (BCK01597ResBean)reqXs.fromXML(resMsg);
		if(bck01597ResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","调用接口01597失败");
			return params;
		}
		String resCode = bck01597ResBean.getHeadBean().getResCode();
		String errMsg = bck01597ResBean.getHeadBean().getResMsg();
		logger.info("查询白名单resCode:"+resCode);
		logger.info("查询白名单resMsg:"+errMsg);
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		//获取检查状态
		String svrno1 = bck01597ResBean.getBody().getESB_LIST().getSTTN();
		for (int i = 0; i < 4; i++) {
			if("1".equals(String.valueOf(svrno1.charAt(i)))){
				params.put("resCode","0010");
				params.put("errMsg","该客户为涉案客户，禁止交易");
				return params;
			}if("2".equals(String.valueOf(svrno1.charAt(i)))){//检查为灰名单的时候
				params.put("resCode","0020");
				params.put("errMsg","该客户可疑，谨防诈骗，请到我行柜台办理");
				return params;
			}	
		}
		params.put("resCode",resCode);
		return params;
	}
	/**
	 * 本人身份联网核查
	 */
	@SuppressWarnings("rawtypes")
	public static Map me07670(AddPasswordBean bean)throws Exception{
		//获取数据
		Map<String,String> map = new HashMap<String,String>();
		
		//本人联网核查
		map.put("ID", bean.getReadIdNo());//本人证件号
		map.put("NAME", bean.getReadIdName());//本人证件名称	
		
		Map<String,String> params = new HashMap<String,String>();
		String reqMsg=CreateXmlMsg.BCK_07670(map);
		logger.info("请求报文："+reqMsg);
		
		SocketClient socketClient =new SocketClient();
		socketClient.createSocket();
		
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK07670ResBean.class);
		BCK07670ResBean bck07670ResBean = (BCK07670ResBean)reqXs.fromXML(resMsg);
		
		if(bck07670ResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","调用接口07670失败");
			return params;
		}
		String resCode = bck07670ResBean.getHeadBean().getResCode();
		String errMsg = bck07670ResBean.getHeadBean().getResMsg();
		logger.info("身份联网核查resCode:"+resCode);
		logger.info("身份联网核查resMsg:"+errMsg);
		
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		
		//本人联网返回信息
		try{
			bean.setInspect(bck07670ResBean.getBody().getCORE_RET_MSG());
			String fileName = bck07670ResBean.getBody().getFILE_NAME();
			FtpFileUtils.downloadFileByFTPClient(Property.FTP_IP_DOWN, Integer.parseInt(Property.FTP_PORT_DOWN), Property.FTP_USER_DOWN, Property.FTP_PWD_DOWN, fileName, Property.FTP_LOCAL_PATH + fileName);
			UtilPreFile.getIdCardImage(Property.FTP_LOCAL_PATH+fileName,Property.ID_CARD_SELF);
		} catch (Exception e) {
			logger.error("获取联网核查结果异常"+e);
			params.put("resCode","4444");
			params.put("errMsg","获取联网核查结果异常");
			return params;
		}
		
		try {
			// 本人联网核查照片查看
			String tmp = Property.BH_TMP + "\\"+DateUtil.getNowDate("yyyyMMdd")+"\\" +bean.getFid()+"\\";
			// 拷贝临时图片--------------------
			File from_f = new File(Property.ID_CARD_SELF);
			// 目标目录
			File to_f = new File(tmp+bean.getReadIdNo()+"_mePoic.jpg");
			logger.info("copy核查图片："+to_f);
			FileUtil.copyFileUsingJava7Files(from_f, to_f);
		} catch (IOException e) {
			logger.error("拷贝临时图片异常"+e);
		}
		bean.setInspect(bck07670ResBean.getBody().getCORE_RET_MSG());	
		
		params.put("resCode",resCode);
		return params;
	}
	/**
	 * 弱密码较验01325
	 */
	public static Map inter01325(AddPasswordBean bean)throws Exception{
		//获取数据
		Map<String, String> map = new HashMap<String, String>();
		map.put("NEW_PASS_WORD",bean.getFristPassword());//密码
		if("1".equals(bean.getSubAccNoCancel())){
			map.put("ACCT_NO1", bean.getAccNo());//账号
		}else{
		   map.put("ACCT_NO1", bean.getSubCardNo());//账号
		}
		Map params = new HashMap();
		String reqMsg=CreateXmlMsg.BCK_01325(map);
		SocketClient socketClient =new SocketClient();
		socketClient.createSocket();
		logger.info("请求报文："+reqMsg);
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK01325ResBean.class);
		BCK01325ResBean bck01325ResBean = (BCK01325ResBean)reqXs.fromXML(resMsg);
		if(bck01325ResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","调用接口01325失败");
			return params;
		}
		//获取返回报文错误码和错误信息
		String resCode = bck01325ResBean.getHeadBean().getResCode();
		String errMsg = bck01325ResBean.getHeadBean().getResMsg();
		logger.info("较验弱密码resCode:"+resCode+"resMsg"+errMsg);
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		
		logger.info("查询成功后信息："+bean);
		params.put("resCode",SUCCESS);
		params.put("errMsg",errMsg);
		return params;
	}

	/**
	 * 柜员授权
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map inter07660(AddPasswordBean bean)throws Exception{
		//获取数据
		Map<String, String> map = new HashMap<String, String>();
		map.put("SUP_TELLER_NO", bean.getSupTellerNo());//SUP_TELLER_NO	查询柜员号
		map.put("SUP_TELLER_PWD", bean.getSupPass());//SUP_TELLER_PWD	授权密码
		map.put("FIN_PRI_LEN", bean.getFinPriLen());//FIN_PRI_LEN	指纹长度
		map.put("FIN_PRI_VAL", bean.getFinPriVal());//FIN_PRI_VAL	指纹值
		Map params = new HashMap();
		String reqMsg=CreateXmlMsg.BCK_07660(map);
		SocketClient socketClient =new SocketClient();
		socketClient.createSocket();
		logger.info("请求报文："+reqMsg);
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK07660ResBean.class);
		BCK07660ResBean bck07660ResBean = (BCK07660ResBean)reqXs.fromXML(resMsg);
		//获取返回报文错误码和错误信息
		String resCode = bck07660ResBean.getHeadBean().getResCode();
		String errMsg = bck07660ResBean.getHeadBean().getResMsg();
		logger.info("柜员授权resCode："+resCode+"resMsg:"+errMsg);
		if(bck07660ResBean == null){
			params.put("resCode",resCode);
			params.put("errMsg","调用接口07660失败");
			return params;
		}
		if(!SUCCESS.equals(resCode)){
			//700043密码错误，700099指纹错误，700018授权员权限不够
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		logger.info("操作成功后信息："+bean);
		params.put("resCode",SUCCESS);
		return params;
	} 

	
	/**
	  * 修改支付条件【02805】-前置07517
	  * 
	  * @param bean
	  * @return
	  * @throws Exception
	  */
	 public static Map inter07517(AddPasswordBean bean)throws Exception{
	  //获取数据
	  Map<String, String> map = new HashMap<String, String>();
	  map.put("ACC_NO", bean.getAccNo());//账号
	  map.put("CUST_NO",""); //客户号
	  map.put("CUST_NAME","");//客户名称
	  map.put("CERT_NO", bean.getCertNo());//凭证号
	  map.put("OLD_PAY_COND",bean.getDrawCoun());//原支付条件 (0、无 1、凭密码 2、凭证件 3、凭印鉴 4、凭印鉴和密码)
	  map.put("NEW_PAY_COND","1");//新支付条件(0、无 1、凭密码 2、凭证件 3、凭印鉴 4、凭印鉴和密码)
	  map.put("CHANGE_REASON","2");//更改原因
	  map.put("LOST_APPL_NO","");//挂失申请号
	  map.put("OLD_PASSWORD","");//原密码
	  map.put("NEW_PASSWORD",bean.getPassword());//新密码
	  map.put("ID_YTPE","");//证件类型
	  map.put("ID_NO", "");//证件号码   
	     
	     Map params = new HashMap();
	  String reqMsg=CreateXmlMsg.BCK_07517(map);
	  SocketClient socketClient =new SocketClient();
	  socketClient.createSocket();
	  logger.info("请求报文："+reqMsg);
	  String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
	  logger.info("响应报文："+resMsg);
	  XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
	  reqXs.alias("Root", BCK07517ResBean.class);
	  BCK07517ResBean bck07517ResBean = (BCK07517ResBean)reqXs.fromXML(resMsg);
	  if(bck07517ResBean == null){
	   params.put("resCode","4444");
	   params.put("errMsg","调用接口07517失败");
	   return params;
	  }
	  //获取返回报文错误码和错误信息
	  String resCode = bck07517ResBean.getHeadBean().getResCode();
	  String errMsg = bck07517ResBean.getHeadBean().getResMsg();
	  logger.info("查询信息resCode:"+resCode+"resMsg"+errMsg);
	  if(!SUCCESS.equals(resCode)){
	   params.put("resCode",resCode);
	   params.put("errMsg",errMsg);
	   return params;
	  }
	  
	  logger.info("查询成功后信息："+bean);
	  params.put("resCode",SUCCESS);
	  params.put("errMsg",errMsg);
	  
	  bean.setZMSvrJrnlNo(bck07517ResBean.getBody().getSVR_JRNL_NO());//增密流水号
	  bean.setSvrDate(bck07517ResBean.getBody().getSVR_DATE());//核心日期
	  return params;
	 }
}
