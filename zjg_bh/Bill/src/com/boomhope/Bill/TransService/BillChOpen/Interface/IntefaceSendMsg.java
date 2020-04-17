package com.boomhope.Bill.TransService.BillChOpen.Interface;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;










import com.boomhope.Bill.TransService.BillChOpen.Interface.CreateXmlMsg;
import com.boomhope.Bill.TransService.AccTransfer.TransferUtil.SocketClient;
import com.boomhope.Bill.TransService.BillChOpen.Interface.IntefaceSendMsg;
import com.boomhope.Bill.TransService.BillChOpen.Bean.BackPrintBillBean;
import com.boomhope.Bill.TransService.BillChOpen.Bean.BillChangeInfoBean;
import com.boomhope.Bill.TransService.BillChOpen.Bean.BillChangeOpenInfoBean;
import com.boomhope.Bill.TransService.BillChOpen.Bean.EAccInfoBean;
import com.boomhope.Bill.TransService.BillChOpen.Bean.PublicBillChangeOpenBean;
import com.boomhope.Bill.TransService.BillChOpen.Bean.SearchInteInfo;
import com.boomhope.Bill.TransService.BillChOpen.Bean.SearchProducRateInfo;
import com.boomhope.Bill.TransService.BillChOpen.Bean.SubAccInfoBean;
import com.boomhope.Bill.Util.DateUtil;
import com.boomhope.Bill.Util.FileUtil;
import com.boomhope.Bill.Util.FtpFileUtils;
import com.boomhope.Bill.Util.Property;
import com.boomhope.Bill.Util.TextFileReader;
import com.boomhope.Bill.Util.UtilPreFile;
import com.boomhope.tms.message.XStreamNameCoder;
import com.boomhope.tms.message.account.in.BCK02864ResBean;
import com.boomhope.tms.message.account.in.BCK03510ReqBean;
import com.boomhope.tms.message.account.in.BCK03510ReqBodyBean;
import com.boomhope.tms.message.account.in.BCK03510ResBean;
import com.boomhope.tms.message.account.in.BCK03845ResBean;
import com.boomhope.tms.message.in.InReqHeadBean;
import com.boomhope.tms.message.in.InResBean;
import com.boomhope.tms.message.in.bck.BCK01597ResBean;
import com.boomhope.tms.message.in.bck.BCK02791ResBean;
import com.boomhope.tms.message.in.bck.BCK03519ResBean;
import com.boomhope.tms.message.in.bck.BCK03521ResBean;
import com.boomhope.tms.message.in.bck.BCK07516ResBean;
import com.boomhope.tms.message.in.bck.BCK07524ResBean;
import com.boomhope.tms.message.in.bck.BCK07527ResBean;
import com.boomhope.tms.message.in.bck.BCK07601ResBean;
import com.boomhope.tms.message.in.bck.BCK07602ResBean;
import com.boomhope.tms.message.in.bck.BCK07659ResBean;
import com.boomhope.tms.message.in.bck.BCK07660ResBean;
import com.boomhope.tms.message.in.bck.BCK07670ResBean;
import com.boomhope.tms.message.in.bck.BCK07819ResBean;
import com.boomhope.tms.message.in.bck.BCK08021ResBean;
import com.boomhope.tms.message.in.bck.BCK77017ResBean;
import com.boomhope.tms.message.in.tms.Tms0005ResBean;
import com.boomhope.tms.message.in.tms.Tms0005ResBodyBean;
import com.boomhope.tms.message.in.tms.Tms0007ResBean;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XppDriver;

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
	public static Map inter07601(PublicBillChangeOpenBean bean)throws Exception{
		//获取数据
		Map<String,String> map = new HashMap<String,String>();
		 //校验密码时上送
		if("1".equals(bean.getXYpass())){
			if("1".equals(bean.getSubAccNoCancel())){
				 map.put("ACCT_NO", bean.getAccNo());//账号
			}else{
			 map.put("ACCT_NO", bean.getSubCardNo());//账号		
			map.put("SUB_ACCT_NO",bean.getSubAccNo());//子账号
			}
		}else{		  
	        map.put("ACCT_NO", bean.getAccNo());//账号		
	    	map.put("SUB_ACCT_NO","");//子账号
		}
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
		logger.info("存单换开账号信息综合查询resCode:"+resCode);
		logger.info("存单换开账号信息综合查询resMsg:"+errMsg);
		
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		if(!"1".equals(bean.getXYpass())){
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
		bean.setOpenchanl((bck07601ResBean.getBody().getOpenChnal()!=null && !"".equals(bck07601ResBean.getBody().getOpenChnal().trim()))?bck07601ResBean.getBody().getOpenChnal().trim():"");//开户渠道号
		bean.setMonthsUpper((bck07601ResBean.getBody().getDepTerm()!=null && !"".equals(bck07601ResBean.getBody().getDepTerm().trim()))?bck07601ResBean.getBody().getDepTerm().trim():"");//存期
		bean.setRate((bck07601ResBean.getBody().getRate()!=null && !"".equals(bck07601ResBean.getBody().getRate().trim()))?bck07601ResBean.getBody().getRate().trim():"");//利率
		bean.setExchFlag((bck07601ResBean.getBody().getExchFlag()!=null && !"".equals(bck07601ResBean.getBody().getExchFlag().trim()))?bck07601ResBean.getBody().getExchFlag().trim():"");//自动转存标志
		bean.setEndAmt((bck07601ResBean.getBody().getEndAmt()!=null && !"".equals(bck07601ResBean.getBody().getEndAmt().trim()))?bck07601ResBean.getBody().getEndAmt().trim():"");//结存额
		}
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
	public static Map inter02791(PublicBillChangeOpenBean bean)throws Exception{
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
		logger.info("存单换开凭证号信息综合查询resCode:"+resCode);
		logger.info("存单换开凭证号信息综合查询resMsg:"+errMsg);
		
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
		bean.setOpenchanl((bck02791ResBean.getBody().getOpenChnal()!=null && !"".equals(bck02791ResBean.getBody().getOpenChnal().trim()))?bck02791ResBean.getBody().getOpenChnal().trim():"");//开户渠道号
		bean.setMonthsUpper((bck02791ResBean.getBody().getDepTerm()!=null && !"".equals(bck02791ResBean.getBody().getDepTerm().trim()))?bck02791ResBean.getBody().getDepTerm().trim():"");//存期
		bean.setRate((bck02791ResBean.getBody().getRate()!=null && !"".equals(bck02791ResBean.getBody().getRate().trim()))?bck02791ResBean.getBody().getRate().trim():"");//利率
		bean.setExchFlag((bck02791ResBean.getBody().getExchFlag()!=null && !"".equals(bck02791ResBean.getBody().getExchFlag().trim()))?bck02791ResBean.getBody().getExchFlag().trim():"");//自动转存标志
		bean.setEndAmt((bck02791ResBean.getBody().getEndAmt()!=null && !"".equals(bck02791ResBean.getBody().getEndAmt().trim()))?bck02791ResBean.getBody().getEndAmt().trim():"");//结存额
		
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
	public static Map inte07601(PublicBillChangeOpenBean bean)throws Exception{
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
		logger.info("存单换开凭证号信息综合查询resCode:"+resCode);
		logger.info("存单换开凭证号信息综合查询resMsg:"+errMsg);
		
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
		bean.setOpenchanl((bck07601ResBean.getBody().getOpenChnal()!=null && !"".equals(bck07601ResBean.getBody().getOpenChnal().trim()))?bck07601ResBean.getBody().getOpenChnal().trim():"");//开户渠道号
		bean.setMonthsUpper((bck07601ResBean.getBody().getDepTerm()!=null && !"".equals(bck07601ResBean.getBody().getDepTerm().trim()))?bck07601ResBean.getBody().getDepTerm().trim():"");//存期
		bean.setRate((bck07601ResBean.getBody().getRate()!=null && !"".equals(bck07601ResBean.getBody().getRate().trim()))?bck07601ResBean.getBody().getRate().trim():"");//利率
		bean.setExchFlag((bck07601ResBean.getBody().getExchFlag()!=null && !"".equals(bck07601ResBean.getBody().getExchFlag().trim()))?bck07601ResBean.getBody().getExchFlag().trim():"");//自动转存标志
		bean.setEndAmt((bck07601ResBean.getBody().getEndAmt()!=null && !"".equals(bck07601ResBean.getBody().getEndAmt().trim()))?bck07601ResBean.getBody().getEndAmt().trim():"");//结存额
		
		params.put("resCode",resCode);
		return params;
	}
	/**
	 * 鉴伪结果上送
	 */
	public static Map tms0007(PublicBillChangeOpenBean bean)throws Exception{
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
	 * 本人身份证查询黑灰名单
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static Map me01597(PublicBillChangeOpenBean bean)throws Exception{
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
	public static Map me07670(PublicBillChangeOpenBean bean)throws Exception{
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
	 * 柜员认证方式查询
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map inter07659(PublicBillChangeOpenBean bean)throws Exception{
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
	public static Map inter77017(PublicBillChangeOpenBean bean)throws Exception{
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
	 * 柜员授权
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map inter07660(PublicBillChangeOpenBean bean)throws Exception{
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
	 * 个人IC卡验证(卡75048)	-前置【07602】
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static Map inter07602(PublicBillChangeOpenBean bean)throws Exception{
		//获取数据
		Map<String,String> map = new HashMap<String,String>();
		map.put("IC_CHL", "");
		map.put("CARD_NO",bean.getCardNo());//卡号
		map.put("AID", "A000000333010101");
		map.put("ARQC", bean.getArqc());
		map.put("ARQC_SRC_DATA", bean.getArqcSrcData());
		map.put("TERM_CHK_VALUE", bean.getTermChkValue());
		map.put("APP_ACCT_SEQ", bean.getAppAcctSeq());
		map.put("ISS_APP_DATA", bean.getIssAppData());
		map.put("CARD_POV", bean.getCardPov());
		
		Map<String,String> params = new HashMap<String,String>();
		
		String reqMsg=CreateXmlMsg.BCK_07602(map);
		
		SocketClient socketClient =new SocketClient();
		socketClient.createSocket();
		
		logger.info("请求报文："+reqMsg);
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK07602ResBean.class);
		BCK07602ResBean bck07602ResBean = (BCK07602ResBean)reqXs.fromXML(resMsg);
		if(bck07602ResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","调用接口07602失败");
			return params;
		}
		String resCode = bck07602ResBean.getHeadBean().getResCode();
		String errMsg = bck07602ResBean.getHeadBean().getResMsg();
		logger.info("个人IC卡验证-resCode："+resCode+"个人IC卡验证-resMsg："+errMsg);
		
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		bean.setSvrDate((bck07602ResBean.getBody().getSVR_DATE()!=null && !"".equals(bck07602ResBean.getBody().getSVR_DATE().trim()))?bck07602ResBean.getBody().getSVR_DATE().trim():"");//核心日期
		params.put("resCode",resCode);
		return params;
	}
	
	/**
	  * 个人定期存单换开【02813】-前置07516
	  * 
	  * @param bean
	  * @return
	  * @throws Exception
	  */
	 public static Map inter07516(PublicBillChangeOpenBean bean)throws Exception{
	  //获取数据
	  Map<String, String> map = new HashMap<String, String>();
	  if("3".equals(bean.getSubAccNoCancel())){
			
		  map.put("ACC_NO",bean.getSubCardNo());	//电子账号
		
	  }else if("0".equals(bean.getSubAccNoCancel())){
		
		map.put("ACC_NO",bean.getSubCardNo());	//卡下子账户-账号
		
	  }else{
		
		map.put("ACC_NO",bean.getAccNo());	//存单账号
	  }
	  map.put("SUB_ACCT_NO",bean.getSubAccNo()); //子账号
	  map.put("CUST_NO",bean.getCustNo());//客户号
	  map.put("CUST_NAME", bean.getAccName());//客户名称
	  map.put("ORIG_CERT_NO",bean.getCertNo());//原凭证号
	  map.put("PAY_COND","1");//支付条件
	  map.put("PASSWORD",bean.getBillPass());//密码
	  map.put("CHANGE_REASON","2");//换开原因
	  map.put("NEW_CERT_NO", bean.getNowNo());//新凭证号
					
	     
	  Map params = new HashMap();
	  String reqMsg=CreateXmlMsg.BCK_07516(map);
	  SocketClient socketClient =new SocketClient();
	  socketClient.createSocket();
	  logger.info("请求报文："+reqMsg);
	  String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
	  logger.info("响应报文："+resMsg);
	  XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
	  reqXs.alias("Root", BCK07516ResBean.class);
	  BCK07516ResBean bck07516ResBean = (BCK07516ResBean)reqXs.fromXML(resMsg);
	  if(bck07516ResBean == null){
	   params.put("resCode","4444");
	   params.put("errMsg","调用接口07516失败");
	   return params;
	  }
	  //获取返回报文错误码和错误信息
	  String resCode = bck07516ResBean.getHeadBean().getResCode();
	  String errMsg = bck07516ResBean.getHeadBean().getResMsg();
	  logger.info("查询信息resCode:"+resCode+"resMsg"+errMsg);
	  if(!SUCCESS.equals(resCode)){
	   params.put("resCode",resCode);
	   params.put("errMsg",errMsg);
	   return params;
	  }
	  String fileName = bck07516ResBean.getBody().getFILE_NAME().trim();
		List<BackPrintBillBean> list  = null;
		
		try {
			// 下载文件
			FtpFileUtils.downloadFileByFTPClient(Property.FTP_IP_DOWN, Integer.parseInt(Property.FTP_PORT_DOWN), Property.FTP_USER_DOWN, Property.FTP_PWD_DOWN, fileName, Property.FTP_LOCAL_PATH + fileName);
			list = TextFileReader.paddingList(Property.FTP_LOCAL_PATH + fileName, BackPrintBillBean.class);
			if(list.size()>0 && list!=null){
				BackPrintBillBean back=list.get(0);
				bean.setAccNo(back.getAcc_no());	
				bean.setAccName(back.getAcc_name());
				bean.setEndAmt(back.getEndAmt());
				String opendata=back.getOpen_Date().replaceAll("/","").trim();
				bean.setOpenDate(opendata);
				String startdata=back.getStart_Data().replaceAll("/","").trim();
				bean.setStartDate(startdata);
				String enddata=back.getEnd_Data().replaceAll("/","").trim();
				bean.setEndDate(enddata);
				bean.setMonthsUpper(back.getDeptime());
				bean.setRate(back.getRate());
				bean.setRouting(back.getRouting());
				bean.setExchFlag(back.getExchFlag());
			}
		} catch (Exception e) {
			logger.error("开卡明细联动查询列表下载失败"+e);
			params.put("resCode","4444");
			params.put("errMsg","开卡明细联动查询列表下载失败");
			return params;
		}
	  
	  logger.info("查询成功后信息："+bean);
	  
	  params.put("resCode",SUCCESS);
	  params.put("errMsg",errMsg);
	  
	  bean.setHKSvrJrnlNo(bck07516ResBean.getBody().getSVR_JRNL_NO());//流水号
	  bean.setSvrDate(bck07516ResBean.getBody().getSVR_DATE());//核心日期
	  return params;
	 }
	 
	 /**
	 * 代理人身份信息核对
	 */
	 @SuppressWarnings("rawtypes")
	 public static Map inter08021(PublicBillChangeOpenBean bean)throws Exception{
		//获取数据
		Map<String,String> map = new HashMap<String,String>();
		
		//代理人身份信息
		map.put("ID_NO", bean.getReadAgentIdNo());//代理人证件号
		map.put("NAME", bean.getReadAgentIdName());//代理人证件名称
		
		Map<String,String> params = new HashMap<String,String>();
		String reqMsg=CreateXmlMsg.BCK_08021(map);
		logger.info("请求报文："+reqMsg);
		
		SocketClient socketClient =new SocketClient();
		socketClient.createSocket();
		
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK08021ResBean.class);
		BCK08021ResBean bck08021ResBean = (BCK08021ResBean)reqXs.fromXML(resMsg);
		
		if(bck08021ResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","调用接口08021失败");
			return params;
		}
		String resCode = bck08021ResBean.getHeadBean().getResCode();
		String errMsg = bck08021ResBean.getHeadBean().getResMsg();
		logger.info("代理人身份信息核查resCode:"+resCode);
		logger.info("代理人身份信息核查resMsg:"+errMsg);
		
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		
		bean.setCheckResult(bck08021ResBean.getBody().getCHECK_RESULT());//(0-我行员工   1-非我行员工)
		params.put("resCode",resCode);
		return params;
	 }
		
	/**
	 * 代理人身份联网核查
	 */
	 @SuppressWarnings("rawtypes")
	 public static Map agent07670(PublicBillChangeOpenBean bean)throws Exception{
		//获取数据
		Map<String,String> map = new HashMap<String,String>();
		
		//代理人联网核查
		map.put("ID", bean.getReadAgentIdNo());//代理人证件号
		map.put("NAME", bean.getReadAgentIdName());//代理人证件名称
		
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
		
		//代理人联网返回信息
		try{
			bean.setAgentInspect(bck07670ResBean.getBody().getCORE_RET_MSG());
			String fileName = bck07670ResBean.getBody().getFILE_NAME();
			FtpFileUtils.downloadFileByFTPClient(Property.FTP_IP_DOWN, Integer.parseInt(Property.FTP_PORT_DOWN), Property.FTP_USER_DOWN, Property.FTP_PWD_DOWN, fileName, Property.FTP_LOCAL_PATH + fileName);
			UtilPreFile.getIdCardImage(Property.FTP_LOCAL_PATH+fileName,Property.ID_CARD_AGENT);
		} catch (Exception e) {
			logger.error("获取联网核查结果异常"+e);
			params.put("resCode","4444");
			params.put("errMsg","获取联网核查结果异常");
			return params;
		}
		
		try {
			// 代理人联网核查照片查看
			String tmp1 = Property.BH_TMP + "\\"+DateUtil.getNowDate("yyyyMMdd")+"\\" +bean.getFid()+"\\";
			// 拷贝临时图片--------------------
			File from = new File(Property.ID_CARD_AGENT);
			// 目标目录
			File to = new File(tmp1+bean.getReadAgentIdNo()+"_agentPoic.jpg");
			logger.info("copy代理人核查图片："+to);
			FileUtil.copyFileUsingJava7Files(from, to);
		} catch (IOException e) {
			logger.error("拷贝临时图片异常"+e);
		}
		bean.setAgentInspect(bck07670ResBean.getBody().getCORE_RET_MSG());	
		
		params.put("resCode",resCode);
		return params;
	 }
	 /**
	 * 代理人身份证查询黑灰名单
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	 @SuppressWarnings("rawtypes")
	 public static Map agent01597(PublicBillChangeOpenBean bean)throws Exception{
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
		
		//代理人黑灰名单查询
		map.put("ID_TYPE1", "1");//代理人证件类型
		map.put("ID_NUMBER1", bean.getReadAgentIdNo());//代理人证件号码
		map.put("ID_NAME1", bean.getReadAgentIdName());//代理人证件姓名
			
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
		 *信息综合查询【02880】-前置07601
		 * @param bean
		 * @return
		 * @throws Exception
		 */
		@SuppressWarnings("rawtypes")
		public static Map CMM07601(PublicBillChangeOpenBean bean)throws Exception{
			//获取数据
			Map<String,String> map = new HashMap<String,String>();
	
				map.put("ACCT_NO", bean.getAccNo());//卡号-子账号
				map.put("SUB_ACCT_NO", "");//子账号

			map.put("CHK_PIN", "0");//是否验密(0：不验密。1：验密)
			map.put("PASSWD", "");//密码			
			map.put("CERT_TYPE", "");//凭证种类
			map.put("CERT_NO_ADD", "");//凭证号
			
			Map<String,String> params = new HashMap<String,String>();
			String reqMsg=CreateXmlMsg.CMM_07601(map);
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
			logger.info("信息综合查询resCode:"+resCode);
			logger.info("信息综合查询resMsg:"+errMsg);
			
			if(!SUCCESS.equals(resCode)){
				params.put("resCode",resCode);
				params.put("errMsg",errMsg);
				return params;
			}
			bean.setCustNo((bck07601ResBean.getBody().getCustNo()!=null && !"".equals(bck07601ResBean.getBody().getCustNo().trim()))?bck07601ResBean.getBody().getCustNo().trim():"");
		  
			params.put("resCode",resCode);
			return params;
		}
		/**
		 * 卡信息查询03845
		 * @param bean
		 * @return
		 * @throws Exception
		 */
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public static Map inter03845(PublicBillChangeOpenBean  bean)throws Exception{
			//获取数据
			Map<String, String> map = new HashMap<String, String>();
			map.put("CARD_NO", bean.getCardNo());//卡号
			map.put("SUB_ACCT_NO", "");//子账号			
			map.put("ISPIN","1");			
			map.put("PASSWD",bean.getCardPwd());//卡密码
			
			
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
			if(bck03845ResBean.getBody().getCARD_STAT()!=null && !"".equals(bck03845ResBean.getBody().getCARD_STAT().trim())){
				
				if("N".equals(bck03845ResBean.getBody().getCARD_STAT())){
					logger.info("银行卡未激活 ");
					params.put("resCode","5555");
					params.put("errMsg","您的银行卡未激活，不能办理此业务");
					return params;
				}else if(!"0".equals(bck03845ResBean.getBody().getCARD_STAT())){
					logger.info("卡状态异常");
					params.put("resCode","5555");
					params.put("errMsg","您的银行卡状态异常，不能办理此业务");
					return params;
				}
			}
			
			if(bck03845ResBean.getBody().getACCT_STAT()!=null && !"".equals(bck03845ResBean.getBody().getACCT_STAT().trim())){
				
				String utilStat = bck03845ResBean.getBody().getACCT_STAT().trim();
				
				if("1".equals(String.valueOf(utilStat.charAt(1)))){
					logger.info("卡为封闭冻结状态");
					params.put("resCode","5555");
					params.put("errMsg","您的银行卡为封闭冻结状态，不能办理此业务");
					return params;
				}
				else if("1".equals(String.valueOf(utilStat.charAt(3)))){
					logger.info("卡为只收不付状态");
					params.put("resCode","5555");
					params.put("errMsg","您的银行卡为柜面只收不付状态，不能办理此业务");
					return params;
				}
				else if("2".equals(String.valueOf(utilStat.charAt(3)))){
					logger.info("卡为电信诈骗全额止付状态");
					params.put("resCode","5555");
					params.put("errMsg","您的银行卡为电信诈骗全额止付状态，不能办理此业务");
					return params;
				}else if("3".equals(String.valueOf(utilStat.charAt(3)))){
					logger.info("卡为电信诈骗全额冻结状态");
					params.put("resCode","5555");
					params.put("errMsg","您的银行卡为电信诈骗全额冻结状态，不能办理此业务");
					return params;
				}
				else if("1".equals(String.valueOf(utilStat.charAt(4))) || "2".equals(String.valueOf(utilStat.charAt(4)))){
					logger.info("卡为挂失状态");
					params.put("resCode","5555");
					params.put("errMsg","您的银行卡为挂失状态，不能办理此业务");
					return params;
				}else if("1".equals(String.valueOf(utilStat.charAt(11))) || "2".equals(String.valueOf(utilStat.charAt(11)))){
					logger.info("卡为挂失状态");
					params.put("resCode","5555");
					params.put("errMsg","您的银行卡为挂失状态，不能办理此业务");
					return params;
				}
				try {
					if("2".equals(String.valueOf(utilStat.charAt(21)))){
						logger.info("卡为全额止付状态");
						params.put("resCode","5555");
						params.put("errMsg","您的银行卡为全额止付状态，不能办理此业务");
						return params;
					}
				} catch (Exception e) {
					logger.error("卡状态校验失败");
				}
			}
			
			
			logger.info("账户可用余额:"+bck03845ResBean.getBody().getAVAL_BAL().trim());
			bean.setSvrDate(bck03845ResBean.getBody().getSVR_DATE());//核心日期
			bean.setBindIdNo(bck03845ResBean.getBody().getBIND_ID()!=null?bck03845ResBean.getBody().getBIND_ID().trim():"");//绑定账户
			bean.setIdNo(bck03845ResBean.getBody().getID_NO()!=null?bck03845ResBean.getBody().getID_NO().trim():"");//身份证号
			bean.setIdName(bck03845ResBean.getBody().getACCT_NAME()!=null?bck03845ResBean.getBody().getACCT_NAME().trim():"");//户名
			bean.setCardName(bck03845ResBean.getBody().getACCT_NAME()!=null?bck03845ResBean.getBody().getACCT_NAME().trim():"");//户名
			bean.setBackAccNo(bck03845ResBean.getBody().getCARD_NO()!=null?bck03845ResBean.getBody().getCARD_NO().trim():"");//卡号
			bean.setCustNo(bck03845ResBean.getBody().getCUST_NO().trim());//客户号
			String cardName="*"+bck03845ResBean.getBody().getACCT_NAME().substring(1);
			bean.setCardNames(cardName);
			
			params.put("resCode",SUCCESS);
			return params;
		} 
		/**
		 * 开卡明细联动查询【70028】-07524
		 * @param 
		 * @return
		 * @throws Exception
		 */
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public static Map inter07524(PublicBillChangeOpenBean bean)throws Exception{
			//获取数据
			Map<String, String> map = new HashMap<String, String>();				    	
			map.put("ID_TYPE", "10100");//证件类型
			map.put("ID_NO", bean.getIdNo());//证件号
			
			Map params = new HashMap();
			
			//连接socket
			String reqMsg=CreateXmlMsg.BCK_07524(map);
			SocketClient socketClient =new SocketClient();
			socketClient.createSocket();
			
			//发送请求报文、接收响应报文
			logger.info("请求报文："+reqMsg);
			String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
			logger.info("响应报文："+resMsg);
			
			//解析xml
			XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			reqXs.alias("Root", BCK07524ResBean.class);
			BCK07524ResBean bck07524ResBean = (BCK07524ResBean)reqXs.fromXML(resMsg);
			
			//获取返回报文错误码和错误信息
			String resCode = bck07524ResBean.getHeadBean().getResCode();
			String errMsg = bck07524ResBean.getHeadBean().getResMsg();
			logger.info("开卡明细联动查询resCode:"+resCode+"resMsg:"+errMsg);
			if(bck07524ResBean == null){
				params.put("resCode","4444");
				params.put("errMsg","开卡明细联动查询接口失败");
				return params;
			}
			if(!SUCCESS.equals(resCode)){
				params.put("resCode",resCode);
				params.put("errMsg",errMsg);
				return params;
			}
			String fileName = bck07524ResBean.getBody().getFILE_NAME().trim();
			List<BillChangeOpenInfoBean> list  = null;
			
			try {
				// 下载文件
				FtpFileUtils.downloadFileByFTPClient(Property.FTP_IP_DOWN, Integer.parseInt(Property.FTP_PORT_DOWN), Property.FTP_USER_DOWN, Property.FTP_PWD_DOWN, fileName, Property.FTP_LOCAL_PATH + fileName);
				list = TextFileReader.paddingList(Property.FTP_LOCAL_PATH + fileName, BillChangeOpenInfoBean.class);
				
			} catch (Exception e) {
				logger.error("开卡明细联动查询列表下载失败"+e);
				params.put("resCode","4444");
				params.put("errMsg","开卡明细联动查询列表下载失败");
				return params;
			}
			bean.setOpen_card_num(bck07524ResBean.getBody().getOPEN_CARD_NUM()!=null?bck07524ResBean.getBody().getOPEN_CARD_NUM().trim():"");//开卡张数
            params.put(E_ACC_MSG, list);
			params.put("resCode",SUCCESS);
			return params;
		} 
		/**
		 * 子账户列表查询-【75109】前置03519
		 * @param bean
		 * @return
		 * @throws Exception
		 */
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public static Map inter03519(PublicBillChangeOpenBean bean)throws Exception{
			//获取数据
			Map<String, String> map = new HashMap<String, String>();
			map.put("CARD_NO",bean.getOpen_card());//卡号
			
			Map params = new HashMap();
			
			//连接socket
			String reqMsg=CreateXmlMsg.BCK_03519(map);
			SocketClient socketClient =new SocketClient();
			socketClient.createSocket();
			
			//发送请求报文、接收响应报文
			logger.info("请求报文："+reqMsg);
			String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
			logger.info("响应报文："+resMsg);
			
			//解析xml
			XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			reqXs.alias("Root", BCK03519ResBean.class);
			BCK03519ResBean bck03519ResBean = (BCK03519ResBean)reqXs.fromXML(resMsg);
			
			//获取返回报文错误码和错误信息
			String resCode = bck03519ResBean.getHeadBean().getResCode();
			String errMsg = bck03519ResBean.getHeadBean().getResMsg();
			logger.info("子账户列表查询resCode:"+resCode+"resMsg:"+errMsg);
			if(bck03519ResBean == null){
				params.put("resCode","4444");
				params.put("errMsg","子账户列表查询接口失败");
				return params;
			}
			if(!SUCCESS.equals(resCode)){
				params.put("resCode",resCode);
				params.put("errMsg",errMsg);
				return params;
			}
			String fileName = bck03519ResBean.getBody().getFILE_NAME();
			List<SubAccInfoBean> list  = null;
			List<SubAccInfoBean> list1 = new ArrayList<SubAccInfoBean>();
			try {
				// 下载文件
				FtpFileUtils.downloadFileByFTPClient(Property.FTP_IP_DOWN, Integer.parseInt(Property.FTP_PORT_DOWN), Property.FTP_USER_DOWN, Property.FTP_PWD_DOWN, fileName, Property.FTP_LOCAL_PATH + fileName);
				list = TextFileReader.paddingList(Property.FTP_LOCAL_PATH + fileName, SubAccInfoBean.class);
				if(list.size()>0){
					for(SubAccInfoBean subBean:list){
						String ss=subBean.getOpenDate();
						String sss=ss.replaceAll("/","").trim();
						if("0".equals(subBean.getMark()) && !subBean.getProductCode().startsWith("TB") && "2".equals(subBean.getPrintState()) && bean.getSvrDate().equals(sss)){							
							list1.add(subBean);
						}
					}
				}
			} catch (Exception e) {
				logger.error("子账户列表信息下载失败"+e);
				params.put("resCode","4444");
				params.put("errMsg","子账户列表信息下载失败");
				return params;
			}
			bean.getSubMap().put("SubMsgList", list1);
			params.put("resCode",SUCCESS);
			return params;
		} 
		/**
		 * 电子账户子账户列表查询【35109】（直连电子账户）-前置07819
		 * @param bean
		 * @return
		 * @throws Exception
		 */
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public static Map inter07819(PublicBillChangeOpenBean bean)throws Exception{
			//获取数据
			Map<String, String> map = new HashMap<String, String>();
			map.put("CARD_NO","");//卡号
			map.put("CUST_NO",bean.getCustNo());//客户号
			
			Map params = new HashMap();
			
			//连接socket
			String reqMsg=CreateXmlMsg.BCK_07819(map);
			SocketClient socketClient =new SocketClient();
			socketClient.createSocket();
			
			//发送请求报文、接收响应报文
			logger.info("请求报文："+reqMsg);
			String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
			logger.info("响应报文："+resMsg);
			
			//解析xml
			XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			reqXs.alias("Root", BCK07819ResBean.class);
			BCK07819ResBean bck07819ResBean = (BCK07819ResBean)reqXs.fromXML(resMsg);
			
			//获取返回报文错误码和错误信息
			String resCode = bck07819ResBean.getHeadBean().getResCode();
			String errMsg = bck07819ResBean.getHeadBean().getResMsg();
			logger.info("电子子账户列表信息查询resCode:"+resCode+"resMsg:"+errMsg);
			if(bck07819ResBean == null){
				params.put("resCode","4444");
				params.put("errMsg","电子子账户列表信息查询接口失败");
				return params;
			}
			if(!SUCCESS.equals(resCode)){
				params.put("resCode",resCode);
				params.put("errMsg",errMsg);
				return params;
			}
			String fileName = bck07819ResBean.getBody().getFILE_NAME();
			List<EAccInfoBean> list  = null;
			List<EAccInfoBean> list1 = new ArrayList<EAccInfoBean>(); 
			try {
				// 下载文件
				FtpFileUtils.downloadFileByFTPClient(Property.FTP_IP_DOWN, Integer.parseInt(Property.FTP_PORT_DOWN), Property.FTP_USER_DOWN, Property.FTP_PWD_DOWN, fileName, Property.FTP_LOCAL_PATH + fileName);
				list = TextFileReader.paddingList(Property.FTP_LOCAL_PATH + fileName, EAccInfoBean.class);
				if(list.size()>0){
					
					for(EAccInfoBean eBean:list){	
						String str=eBean.geteOpenDate();
						String s=str.replaceAll("/","").trim();
						if("0".equals(eBean.geteMark()) &&  "2".equals(eBean.getePrintState()) && bean.getSvrDate().equals(s)){						
	 						list1.add(eBean);
						}
					}
				}
			} catch (Exception e) {
				logger.error("电子子账户列表信息下载失败"+e);
				params.put("resCode","4444");
				params.put("errMsg","电子子账户列表信息下载失败");
				return params;
			}
			bean.setePages(list1.size());
			bean.getDZAccMap().put("DZMsgList", list1);
			params.put("resCode",SUCCESS);
			return params;
		}
		/**
		   * 按客户号查询账户信息【 20103】-前置07527
		   * @param 
		   * @return
		   * @throws Exception
		   */
		  @SuppressWarnings({ "rawtypes", "unchecked" })
		  public static Map inter07527(PublicBillChangeOpenBean bean)throws Exception{
		   //获取数据
		   Map<String, String> map = new HashMap<String, String>();         
		   map.put("CUST_NO", bean.getCustNo());//客户号
		   map.put("CUST_TYPE", "1");//客户类型
		   map.put("CUST_NAME", bean.getIdName());//客户名称
		   map.put("ID_TYPE", "");//证件类型
		   map.put("ID_NO", "");//证件号码
		   map.put("QRY_TYPE", "2");//查询类型
		   
		   Map params = new HashMap();
		   
		   //连接socket
		   String reqMsg=CreateXmlMsg.BCK_07527(map);
		   SocketClient socketClient =new SocketClient();
		   socketClient.createSocket();
		   
		   //发送请求报文、接收响应报文
		   logger.info("请求报文："+reqMsg);
		   String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		   logger.info("响应报文："+resMsg);
		   
		   //解析xml
		   XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		   reqXs.alias("Root", BCK07527ResBean.class);
		   BCK07527ResBean bck07527ResBean = (BCK07527ResBean)reqXs.fromXML(resMsg);
		   
		   //获取返回报文错误码和错误信息
		   String resCode = bck07527ResBean.getHeadBean().getResCode();
		   String errMsg = bck07527ResBean.getHeadBean().getResMsg();
		   logger.info("按客户号查询普通账户信息resCode:"+resCode+"resMsg:"+errMsg);
		   if(bck07527ResBean == null){
		    params.put("resCode","4444");
		    params.put("errMsg","按客户号查询普通账户信息接口失败");
		    return params;
		   }
		   if(!SUCCESS.equals(resCode)){
		    params.put("resCode",resCode);
		    params.put("errMsg",errMsg);
		    return params;
		   }
		   String fileName = bck07527ResBean.getBody().getFILE_NAME().trim();
		   List<BillChangeInfoBean> list2  = null;  
		   List<BillChangeInfoBean> list1 = new ArrayList<BillChangeInfoBean>(); 
		   try {
		    // 下载文件
		    FtpFileUtils.downloadFileByFTPClient(Property.FTP_IP_DOWN, Integer.parseInt(Property.FTP_PORT_DOWN), Property.FTP_USER_DOWN, Property.FTP_PWD_DOWN, fileName, Property.FTP_LOCAL_PATH + fileName);
		    list2 = TextFileReader.paddingList(Property.FTP_LOCAL_PATH + fileName, BillChangeInfoBean.class);
		    if(list2.size()>0){
				for(BillChangeInfoBean eBean:list2){
					String str=eBean.getOpen_date();
					String ss=str.replaceAll("/","").trim();
					if(!"Q".equals(String.valueOf(eBean.getAcct_stat().charAt(0)))&& !"1".equals(String.valueOf(eBean.getAcct_stat().charAt(7))) && !"2".equals(String.valueOf(eBean.getAcct_stat().charAt(7)))&& bean.getSvrDate().equals(ss)){
						list1.add(eBean);
					}
				}
			}
		   } catch (Exception e) {
		    logger.error("按客户号查询普通账户信息查询列表下载失败"+e);
		    params.put("resCode","4444");
		    params.put("errMsg","按客户号查询普通账户信息查询列表下载失败");
		    return params;
		   }
		  
		   bean.getAccMap().put("AccMsgList", list1);
		   params.put("resCode",SUCCESS);
		   return params;
		  } 
	 	 /**查询凭证信息
	 * @throws Exception */
	 public static Map<String,String> Tms0005() throws Exception{
		
		SocketClient sc = null;
		sc = new SocketClient();
		sc.createSocket();
		String tms0005 = CreateXmlMsg.Tms0005();
		Map<String,String> params = new HashMap<String,String>();
		// 发送请求  响应
		String ropMsg = sc.sendMesg(tms0005,"UTF-8");
		if(null == ropMsg ){
			logger.error("查询凭证号失败");
			params.put("resCode","4444");
			params.put("errMsg","查询凭证号失败");
			return params;
		}
		logger.info("凭证查询结果:" + ropMsg);
			
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", Tms0005ResBean.class);
		reqXs.alias("Head", InResBean.class);
		reqXs.alias("Body", Tms0005ResBodyBean.class);
		Tms0005ResBean tms0005ResBean = (Tms0005ResBean)reqXs.fromXML(ropMsg);
		String resCode = tms0005ResBean.getHeadBean().getResCode();
		String errMsg = tms0005ResBean.getHeadBean().getResMsg();
		logger.info("查询凭证信息resCode:"+resCode+"resMsg:"+errMsg);
		if(!SUCCESS.equals(resCode)){
			logger.error("查询凭证号失败");
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		params.put("nowNo",tms0005ResBean.getBody().getNOW_BO());//当前凭证号
		params.put("startNo",tms0005ResBean.getBody().getSTART_NO());//起始凭证号
		params.put("endNo",tms0005ResBean.getBody().getEND_NO());//结尾凭证号
		params.put("nowId",tms0005ResBean.getBody().getID());//凭证ID
		params.put("createDate",tms0005ResBean.getBody().getCREATE_DATE());//创建日期
		params.put("resCode",SUCCESS);
		logger.info("查询成功："+tms0005ResBean);
		return params;
	}
	 /**更新凭证信息
	 * @throws IOException 
	 * @throws UnknownHostException 
	 * @throws Exception */
	 public static Map<String,String> Tms0006(Map<String,String> map) throws Exception {
		
		SocketClient sc = null;
		sc = new SocketClient();
		sc.createSocket();
		String tms0006 = CreateXmlMsg.Tms0006(map);
		Map<String,String> params = new HashMap<String,String>();
		// 发送请求  响应
		String ropMsg = sc.sendMesg(tms0006,"UTF-8");
		if(null == ropMsg ){
			logger.error("更新凭证号失败");
			params.put("resCode","4444");
			params.put("errMsg","更新凭证号失败");
			return params;
		}
		logger.info("凭证更新结果:" + ropMsg);
		
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", Tms0005ResBean.class);
		reqXs.alias("Head", InResBean.class);
		reqXs.alias("Body", Tms0005ResBodyBean.class);
		Tms0005ResBean tms0005ResBean = (Tms0005ResBean)reqXs.fromXML(ropMsg);
		String resCode = tms0005ResBean.getHeadBean().getResCode();
		String errMsg = tms0005ResBean.getHeadBean().getResMsg();
		logger.info("更新凭证信息resCode:"+resCode+"resMsg:"+errMsg);
		if(!SUCCESS.equals(resCode)){
			logger.error("更新凭证号失败");
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		logger.info("成功后信息："+tms0005ResBean);
		params.put("resCode",SUCCESS);
		return params;	
		
	}
	 /**
	 * 卡账户信息查询1-前置03521
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	 public static Map inter03521(PublicBillChangeOpenBean bean)throws Exception{
		//获取数据
		Map<String, String> map = new HashMap<String, String>();
		map.put("ACCT_NO", bean.getCardNo());//账号
		map.put("PASSWORD", "");//密码
		map.put("PIN_VAL_FLAG", "0");//验密标志(1-有密码 ，0-无密码)
		
		Map params = new HashMap();
		
		//连接socket
		String reqMsg=CreateXmlMsg.BCK_03521(map);
		SocketClient socketClient =new SocketClient();
		socketClient.createSocket();
		
		//发送请求报文、接收响应报文
		logger.info("请求报文："+reqMsg);
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		
		//解析xml
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK03521ResBean.class);
		BCK03521ResBean bck03521ResBean = (BCK03521ResBean)reqXs.fromXML(resMsg);
		//获取返回报文错误码和错误信息
		String resCode = bck03521ResBean.getHeadBean().getResCode();
		String errMsg = bck03521ResBean.getHeadBean().getResMsg();
		logger.info("账户信息查询及密码验证resCode："+resCode+"resMsg:"+errMsg);
		if(bck03521ResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","账户信息查询及密码验证接口失败");
			return params;
		}
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		if("Q".equals(bck03521ResBean.getBody().getACCT_STAT())){//销户
			logger.info("银行卡销户 ");
			params.put("resCode","5555");
			params.put("errMsg","您的账户为销户状态，不能办理此业务");
			return params;
		}else if("T".equals(bck03521ResBean.getBody().getACCT_STAT())){
			logger.info("银行卡停用 ");
			params.put("resCode","5555");
			params.put("errMsg","您的账户为停用状态，不能办理此业务");
			return params;
		}else if("L".equals(bck03521ResBean.getBody().getACCT_STAT())){
			logger.info("银行卡证件到期中止 ");
			params.put("resCode","5555");
			params.put("errMsg","您的账户证件到期中止，不能办理此业务");
			return params;
		}else if("M".equals(bck03521ResBean.getBody().getACCT_STAT())){
			logger.info("银行卡中止且停用 ");
			params.put("resCode","5555");
			params.put("errMsg","您的账户中止且停用，不能办理此业务");
			return params;
		}else if("N".equals(bck03521ResBean.getBody().getACCT_STAT())){
			logger.info("银行卡中止且非柜面停用 ");
			params.put("resCode","5555");
			params.put("errMsg","您的账户中止且非柜面停用，不能办理此业务");
			return params;
		}
		
		if("G".equals(bck03521ResBean.getBody().getLOST_STAT())){//挂失
			logger.info("银行卡挂失 ");
			params.put("resCode","5555");
			params.put("errMsg","您的银行卡为挂失状态，不能办理此业务");
			return params;
		}
		if("D".equals(bck03521ResBean.getBody().getHOLD_STAT())){//冻结
			logger.info("银行卡封闭冻结 ");
			params.put("resCode","5555");
			params.put("errMsg","您的银行卡为封闭冻结状态，不能办理此业务");
			return params;
			
		}else if("S".equals(bck03521ResBean.getBody().getHOLD_STAT())){
			logger.info("银行卡只收不付 ");
			params.put("resCode","5555");
			params.put("errMsg","您的银行卡为只收不付状态，不能办理此业务");
			return params;
		}else if("Z".equals(bck03521ResBean.getBody().getHOLD_STAT())){
			logger.info("银行卡全额止付");
			params.put("resCode","5555");
			params.put("errMsg","您的银行卡为全额止付状态，不能办理此业务");
			return params;
		}
		
		params.put("resCode",SUCCESS);
		return params;		
	}
	
	/**
	 * 卡账户信息查询2-前置07601
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	 public static Map card07601(PublicBillChangeOpenBean bean)throws Exception{
		//获取数据
		Map<String,String> map = new HashMap<String,String>();
		
		//校验银行卡时上送
		map.put("ACCT_NO", bean.getCardNo());//卡号
		map.put("SUB_ACCT_NO", "");//
		map.put("CHK_PIN", "0");//是否验密(0：不验密。1：验密)
		map.put("PASSWD", "");//
		map.put("CERT_TYPE", "");//凭证种类
		map.put("CERT_NO_ADD", "");//凭证号		
		
		Map params = new HashMap();
		
		//连接socket
		String reqMsg=CreateXmlMsg.BCK_07601(map);
		SocketClient socketClient =new SocketClient();
		socketClient.createSocket();
		
		//发送请求报文、接收响应报文
		logger.info("请求报文："+reqMsg);
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		
		//解析xml
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK07601ResBean.class);
		BCK07601ResBean bck07601ResBean = (BCK07601ResBean)reqXs.fromXML(resMsg);
		//获取返回报文错误码和错误信息
		String resCode = bck07601ResBean.getHeadBean().getResCode();
		String errMsg = bck07601ResBean.getHeadBean().getResMsg();
		logger.info("账号信息综合查询resCode："+resCode+"resMsg:"+errMsg);
		if(bck07601ResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","账号信息综合查询接口失败");
			return params;
		}
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		
		if(bck07601ResBean.getBody().getAcctStat()!=null && !"".equals(bck07601ResBean.getBody().getAcctStat().trim())){
			
			String utilStat = bck07601ResBean.getBody().getAcctStat().trim();
			
			if("D".equals(String.valueOf(utilStat.charAt(0)))){
				logger.info("卡渠道限制");
				params.put("resCode","5555");
				params.put("errMsg","您的银行卡有渠道限制，不能办理此业务");
				return params;
			}	
		}
		
		params.put("resCode",SUCCESS);
		return params;		
	}
	 
		
		/**
		 * 产品利率信息查询
		 * @param bcOpenBean
		 * @return
		 * @throws Exception
		 */
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public static Map inter02864(PublicBillChangeOpenBean bcOpenBean)throws Exception{
			//获取数据
			Map<String,String> map = new HashMap<String,String>();
			map.put("CUST_NO", bcOpenBean.getCustNo());
			map.put("FLOAT_FLAG","1");//非必输1-浮动，非1-不浮动
			map.put("CHL_ID","");//渠道模块标识
			map.put("PROD_CODE", bcOpenBean.getProCode());// 产品名称
			
			String opendata= bcOpenBean.getOpenDate().replaceAll("/","").trim();
			
			map.put("RATE_DATE",opendata);// 利率日期
			map.put("CUST_LEVEL", "");//客户评级
			if(!"1".equals(bcOpenBean.getSubAccNoCancel())){
				map.put("ACC_NO",bcOpenBean.getSubCardNo());
			    map.put("SubAccNo", bcOpenBean.getSubAccNo());
			}else{				
				map.put("ACC_NO", bcOpenBean.getAccNo());
				map.put("SubAccNo", "");
			}
			Map params = new HashMap();
			String reqMsg=CreateXmlMsg.BCK_02864(map);
			SocketClient socketClient =new SocketClient();
			socketClient.createSocket();
			logger.info("请求报文："+reqMsg);
			String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
			logger.info("响应报文："+resMsg);
			XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			reqXs.alias("Root", BCK02864ResBean.class);
			BCK02864ResBean bck02864ResBean = (BCK02864ResBean)reqXs.fromXML(resMsg);
			String resCode02864 = bck02864ResBean.getHeadBean().getResCode();
			String resMsg02864 = bck02864ResBean.getHeadBean().getResMsg();
			logger.info("产品利率信息查询：resCode02864:"+resCode02864+"resMsg02864"+resMsg02864);
			if(!"000000".equals(resCode02864)){
				params.put("resCode",resCode02864);
				params.put("errMsg",resMsg02864);
				return params;
			}
			String fileName = bck02864ResBean.getBody().getFILE_NAME();
			FtpFileUtils.downloadFileByFTPClient(Property.FTP_IP_DOWN, Integer.parseInt(Property.FTP_PORT_DOWN), Property.FTP_USER_DOWN, Property.FTP_PWD_DOWN, fileName, Property.FTP_LOCAL_PATH + fileName);
			List<SearchProducRateInfo> list = TextFileReader.paddingList(Property.FTP_LOCAL_PATH + fileName, SearchProducRateInfo.class);
			logger.info(list);
			params.put(KEY_PRODUCT_RATES, list);
			params.put("float", bck02864ResBean.getBody().getFLOAT_SUM());//浮动
			params.put("resCode", SUCCESS);
			bcOpenBean.setFloatSum(bck02864ResBean.getBody().getFLOAT_SUM());
			return params;
		} 
}
