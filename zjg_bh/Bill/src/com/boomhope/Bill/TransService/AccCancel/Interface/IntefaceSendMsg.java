package com.boomhope.Bill.TransService.AccCancel.Interface;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.boomhope.Bill.TransService.AccCancel.Bean.AccCancelProFileBean;
import com.boomhope.Bill.TransService.AccCancel.Bean.AccCancelTBMsgBean;
import com.boomhope.Bill.TransService.AccCancel.Bean.AccTdMsgBean;
import com.boomhope.Bill.TransService.AccCancel.Bean.EAccInfoBean;
import com.boomhope.Bill.TransService.AccCancel.Bean.PublicAccCancelBean;
import com.boomhope.Bill.TransService.AccCancel.Bean.SubAccInfoBean;
import com.boomhope.Bill.TransService.AccTransfer.TransferUtil.SocketClient;
import com.boomhope.Bill.Util.DateUtil;
import com.boomhope.Bill.Util.FileUtil;
import com.boomhope.Bill.Util.FtpFileUtils;
import com.boomhope.Bill.Util.Property;
import com.boomhope.Bill.Util.TextFileReader;
import com.boomhope.Bill.Util.UtilPreFile;
import com.boomhope.tms.message.XStreamNameCoder;
import com.boomhope.tms.message.account.in.BCK03845ResBean;
import com.boomhope.tms.message.in.bck.BCK01597ResBean;
import com.boomhope.tms.message.in.bck.BCK03445ResBean;
import com.boomhope.tms.message.in.bck.BCK03517ResBean;
import com.boomhope.tms.message.in.bck.BCK03519ResBean;
import com.boomhope.tms.message.in.bck.BCK03521ResBean;
import com.boomhope.tms.message.in.bck.BCK03522ResBean;
import com.boomhope.tms.message.in.bck.BCK07498ResBean;
import com.boomhope.tms.message.in.bck.BCK07499ResBean;
import com.boomhope.tms.message.in.bck.BCK07515ResBean;
import com.boomhope.tms.message.in.bck.BCK07601ResBean;
import com.boomhope.tms.message.in.bck.BCK07602ResBean;
import com.boomhope.tms.message.in.bck.BCK07622ResBean;
import com.boomhope.tms.message.in.bck.BCK07624ResBean;
import com.boomhope.tms.message.in.bck.BCK07659ResBean;
import com.boomhope.tms.message.in.bck.BCK07660ResBean;
import com.boomhope.tms.message.in.bck.BCK07665ResBean;
import com.boomhope.tms.message.in.bck.BCK07670ResBean;
import com.boomhope.tms.message.in.bck.BCK07696ResBean;
import com.boomhope.tms.message.in.bck.BCK07819ResBean;
import com.boomhope.tms.message.in.bck.BCK08021ResBean;
import com.boomhope.tms.message.in.bck.BCK77017ResBean;
import com.boomhope.tms.message.in.tms.Tms0007ResBean;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * 接口发送类
 * @author Wang.xm
 *
 */
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
	 * 本人身份证查询黑灰名单
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static Map me01597(PublicAccCancelBean bean)throws Exception{
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
	 * 代理人身份证查询黑灰名单
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static Map agent01597(PublicAccCancelBean bean)throws Exception{
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
	 * 卡查询灰名单
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static Map card01597(PublicAccCancelBean bean)throws Exception{
		//获取数据
		Map<String,String> map = new HashMap<String,String>();
		map.put("PROGRAM_FLAG", "");//程序标识
		map.put("ACC_NO1", bean.getCardNo());//出账账号
		map.put("CARD_NO1", bean.getCardNo());//出账卡号
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
	 * 账号信息综合查询【02880】-前置07601
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static Map inter07601(PublicAccCancelBean bean)throws Exception{
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
		logger.info("销户账号信息综合查询resCode:"+resCode);
		logger.info("销户账号信息综合查询resMsg:"+errMsg);
		
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
		bean.setTotalAmt((bck07601ResBean.getBody().getTotalAmt()!=null && !"".equals(bck07601ResBean.getBody().getTotalAmt().trim()))?bck07601ResBean.getBody().getTotalAmt().trim():"");//存折余额销户接口用
		bean.setProCode((bck07601ResBean.getBody().getProdCode()!=null && !"".equals(bck07601ResBean.getBody().getProdCode().trim()))?bck07601ResBean.getBody().getProdCode().trim():"");//产品代码
		bean.setProName((bck07601ResBean.getBody().getProdName()!=null && !"".equals(bck07601ResBean.getBody().getProdName().trim()))?bck07601ResBean.getBody().getProdName().trim():"");//产品名称
		bean.setExchflag((bck07601ResBean.getBody().getExchFlag()!=null && !"".equals(bck07601ResBean.getBody().getExchFlag().trim()))?bck07601ResBean.getBody().getExchFlag().trim():"");//自动转存标志(0-否，1-是)
		bean.setSvrDate((bck07601ResBean.getBody().getSvrDate()!=null && !"".equals(bck07601ResBean.getBody().getSvrDate().trim()))?bck07601ResBean.getBody().getSvrDate().trim():"");//核心日期
		bean.setStartDate((bck07601ResBean.getBody().getStartIntDate()!=null && !"".equals(bck07601ResBean.getBody().getStartIntDate().trim()))?bck07601ResBean.getBody().getStartIntDate().trim():"");//起息日
		bean.setOpenDate((bck07601ResBean.getBody().getOpenDate()!=null && !"".equals(bck07601ResBean.getBody().getOpenDate().trim()))?bck07601ResBean.getBody().getOpenDate().trim():"");//开户日
		bean.setDueDate((bck07601ResBean.getBody().getEndDate()!=null && !"".equals(bck07601ResBean.getBody().getEndDate().trim()))?bck07601ResBean.getBody().getEndDate().trim():"");//到期日
		bean.setEndDate((bck07601ResBean.getBody().getEndDate()!=null && !"".equals(bck07601ResBean.getBody().getEndDate().trim()))?bck07601ResBean.getBody().getEndDate().trim():"");//到期日
		bean.setEndDate((bck07601ResBean.getBody().getEndDate()!=null && !"".equals(bck07601ResBean.getBody().getEndDate().trim()))?bck07601ResBean.getBody().getEndDate().trim():"");//到期日
		bean.setPreDate((bck07601ResBean.getBody().getPreDate()!=null && !"".equals(bck07601ResBean.getBody().getPreDate().trim()))?bck07601ResBean.getBody().getPreDate().trim():"");//预约日期
		
		if(bck07601ResBean.getBody().getDepTerm()!=null && !"".equals(bck07601ResBean.getBody().getDepTerm().trim())){
			String str = bck07601ResBean.getBody().getDepTerm().trim().toUpperCase();
			Integer n=Integer.parseInt(str.replaceAll("\\D",""));
			if(str.indexOf("D")!=-1){
				bean.setCancelDepTerm(n+"天");
			}else if(str.indexOf("M")!=-1){
				bean.setCancelDepTerm(n+"个月");
			}else if(str.indexOf("Y")!=-1){
				bean.setCancelDepTerm(n+"年");
			}else{
				bean.setCancelDepTerm(str+"天");
			}
		}
		
		if("0".equals(bean.getSubAccNoCancel())){
			if(bck07601ResBean.getBody().getOrigAcctNo()!=null && !"".equals(bck07601ResBean.getBody().getOrigAcctNo().trim())){
				bean.setYuanAccNo(bean.getAccNo());
				String accNo = bck07601ResBean.getBody().getOrigAcctNo().trim();
				String[] split = accNo.split("-");
				bean.setAccNo(accNo);//账号-子账号
				bean.setSubCardNo(split[0]);//账号
				bean.setSubAccNo(split[1]);//子账号
				bean.setCardNo(split[0]);//转入卡号
			}
		}
		params.put("resCode",resCode);
		return params;
	}
	/**
	 * 证件信息查询
	 */
	@SuppressWarnings("rawtypes")
	public static Map inter03445(PublicAccCancelBean bean)throws Exception{
		//获取数据
		Map<String,String> map = new HashMap<String,String>();
		
		//校验银行卡时上送
		map.put("CUST_NAME", bean.getReadIdName());//证件名称
		map.put("ID_NO", bean.getReadIdNo());//证件号
		map.put("ID_TYPE", "10100");//证件类型
		
		Map<String,String> params = new HashMap<String,String>();
		String reqMsg=CreateXmlMsg.BCK_03445(map);
		logger.info("请求报文："+reqMsg);
		
		SocketClient socketClient =new SocketClient();
		socketClient.createSocket();
		
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK03445ResBean.class);
		BCK03445ResBean bck03445ResBean = (BCK03445ResBean)reqXs.fromXML(resMsg);
		
		if(bck03445ResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","调用接口03445失败");
			return params;
		}
		String resCode = bck03445ResBean.getHeadBean().getResCode();
		String errMsg = bck03445ResBean.getHeadBean().getResMsg();
		logger.info("证件信息查询resCode:"+resCode);
		logger.info("证件信息查询resMsg:"+errMsg);
		
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		
		String fileName = bck03445ResBean.getBODY().getFILE_NAME();
		// 下载文件
		FtpFileUtils.downloadFileByFTPClient(Property.FTP_IP_DOWN, Integer.parseInt(Property.FTP_PORT_DOWN), Property.FTP_USER_DOWN, Property.FTP_PWD_DOWN, fileName, Property.FTP_LOCAL_PATH + fileName);
		List<String> custInfoList = null;
		try {
			custInfoList = UtilPreFile.preCustInfo(Property.FTP_LOCAL_PATH+fileName);
		} catch (Exception e) {
			logger.error("身份证文件解析失败"+e);
			params.put("resCode","4444");
			params.put("errMsg","身份证文件解析失败");
			return params;
		}

		bean.setCustNo(custInfoList.get(0).trim());//客户号
		
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
	public static Map inter03845(PublicAccCancelBean bean)throws Exception{
		//获取数据
		Map<String, String> map = new HashMap<String, String>();
		map.put("CARD_NO", bean.getCardNo());//卡号
		map.put("SUB_ACCT_NO", "");//子账号
		if("0".equals(bean.getCardIsPin())){//为0 不验密
			map.put("ISPIN",bean.getCardIsPin());			
			map.put("PASSWD","");//卡密码
		}else{//否则必须验密
			map.put("ISPIN", "1");
			map.put("PASSWD",bean.getCardPwd());//卡密码
		}
		
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
		bean.setcNo(bck03845ResBean.getBody().getCARD_NO()!=null?bck03845ResBean.getBody().getCARD_NO().trim():"");//卡号
		bean.setCustNo(bck03845ResBean.getBody().getCUST_NO().trim());//客户号
		String cardName="*"+bck03845ResBean.getBody().getACCT_NAME().substring(1);
		bean.setCardNames(cardName);
		
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
	public static Map inter03521(PublicAccCancelBean bean)throws Exception{
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
	public static Map card07601(PublicAccCancelBean bean)throws Exception{
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
	 * 子账户列表查询-【75109】前置03519
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map inter03519(PublicAccCancelBean bean)throws Exception{
		//获取数据
		Map<String, String> map = new HashMap<String, String>();
		map.put("CARD_NO",bean.getCardNo());//卡号
		
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
					if("0".equals(subBean.getMark()) && !subBean.getProductCode().startsWith("TB") && ("0".equals(subBean.getPrintState()) || "1".equals(subBean.getPrintState()))){
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
		bean.setSubPages(list1.size());
		bean.getImportMap().put("SUB_ACC_MSG", list1);
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
	public static Map inter07819(PublicAccCancelBean bean)throws Exception{
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
					if("0".equals(eBean.geteMark()) && ("0".equals(eBean.getePrintState()) || "1".equals(eBean.getePrintState()))){
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
		bean.getImportMap().put("E_ACC_MSG", list1);
		params.put("resCode",SUCCESS);
		return params;
	} 
	
	/**
	 * 本人身份联网核查
	 */
	@SuppressWarnings("rawtypes")
	public static Map me07670(PublicAccCancelBean bean)throws Exception{
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
	 * 代理人身份联网核查
	 */
	@SuppressWarnings("rawtypes")
	public static Map agent07670(PublicAccCancelBean bean)throws Exception{
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
	 * 代理人身份信息核对
	 */
	@SuppressWarnings("rawtypes")
	public static Map inter08021(PublicAccCancelBean bean)throws Exception{
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
	 * 普通预计利息查询
	 */
	@SuppressWarnings("rawtypes")
	public static Map inter07696(PublicAccCancelBean bean)throws Exception{
		//获取数据
		Map<String,String> map = new HashMap<String,String>();
		
		map.put("ACCT_NO", bean.getAccNo());//存单账号
		
		Map<String,String> params = new HashMap<String,String>();
		String reqMsg=CreateXmlMsg.BCK_07696(map);
		logger.info("请求报文："+reqMsg);
		
		SocketClient socketClient =new SocketClient();
		socketClient.createSocket();
		
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK07696ResBean.class);
		BCK07696ResBean bck07696ResBean = (BCK07696ResBean)reqXs.fromXML(resMsg);
		
		if(bck07696ResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","调用接口07696失败");
			return params;
		}
		String resCode = bck07696ResBean.getHeadBean().getResCode();
		String errMsg = bck07696ResBean.getHeadBean().getResMsg();
		logger.info("普通预计利息查询resCode:"+resCode);
		logger.info("普通预计利息查询resMsg:"+errMsg);
		
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		
		// 利息
		bean.setYjlx(bck07696ResBean.getBody().getInte().trim());//获取预计利息
		bean.setAdvnInit(bck07696ResBean.getBody().getADVN_INIT().trim());//已付收益
		if(bck07696ResBean.getBody().getPAYOFF_INTEREST()!=null && !"".equals(bck07696ResBean.getBody().getPAYOFF_INTEREST())){
			//如果已支付利息有值，则预计利息=预计利息+已支付利息
			BigDecimal bigDecimal = new BigDecimal(bck07696ResBean.getBody().getPAYOFF_INTEREST().trim());//已支付利息
			BigDecimal bigDecimal2 = new BigDecimal(bck07696ResBean.getBody().getInte().trim());//预计利息
			bean.setYjlx(bigDecimal.add(bigDecimal2).toString());//获取预计利息
		}else{
		}			
		params.put("resCode",resCode);
		return params;
	}
	
	/**
	 * 唐豆收回查询
	 */
	@SuppressWarnings("rawtypes")
	public static Map inter07622(PublicAccCancelBean bean)throws Exception{
		//获取数据
		Map<String,String> map = new HashMap<String,String>();
		
		map.put("ACCT_NO", bean.getAccNo());//账号
		map.put("SUB_ACCT_NO", "");//子账户
		map.put("PAY_DATE", bean.getSvrDate());//核心日期
		map.put("PAY_JRNL", bean.getXHSvrJrnlNo());//销户流水号
		
		Map<String,String> params = new HashMap<String,String>();
		String reqMsg=CreateXmlMsg.BCK_07622(map);
		logger.info("请求报文："+reqMsg);
		
		SocketClient socketClient =new SocketClient();
		socketClient.createSocket();
		
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK07622ResBean.class);
		BCK07622ResBean bck07622ResBean = (BCK07622ResBean)reqXs.fromXML(resMsg);
		
		if(bck07622ResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","调用接口07622失败");
			return params;
		}
		String resCode = bck07622ResBean.getHeadBean().getResCode();
		String errMsg = bck07622ResBean.getHeadBean().getResMsg();
		logger.info("唐豆收回查询resCode:"+resCode);
		logger.info("唐豆收回查询resMsg:"+errMsg);
		
		if(!SUCCESS.equals(resCode)){//唐豆规则异常信息
			
			if(errMsg.indexOf("7091") != -1 || errMsg.indexOf("D156") != -1 || errMsg.indexOf("D999") != -1 || errMsg.indexOf("1909") != -1){
				//唐豆规则查询正常，不调用唐豆收回接口
				bean.setShtdy("0.00");
				params.put("resCode",SUCCESS);
				return params;

			}
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		Date endDate = DateUtil.getDate(bean.getDueDate());//到期日
		Date svrdate = DateUtil.getDate(bean.getSvrDate());//核心日
		Date startDate = DateUtil.getDate(bean.getOpenDate());//开户日期
		//获取存期
		String demTerm = bck07622ResBean.getBody().getDepTerm();
		//获取开户日期之后的一年
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDate);
		int year = calendar.get(Calendar.YEAR);
		calendar.set(Calendar.YEAR, year+1);
		//一年后的开户日
		startDate = calendar.getTime();
		
		//第一种情况，当唐豆金额为0.00元，不调用唐豆收回接口
		if(bck07622ResBean.getBody().getBack_Exchange_Amt()==null){
			
			bean.setShtdy("0.00");
			
		}else if("0.00".equals(bck07622ResBean.getBody().getBack_Exchange_Amt().trim()) || "".equals(bck07622ResBean.getBody().getBack_Exchange_Amt().trim())){
			
			bean.setShtdy("0.00");
		}
		//第二种情况，当存单到期，不调用唐豆收回接口
		else if(endDate.compareTo(svrdate) <= 0){
			bean.setShtdy("0.00");
		}
		//第三种情况，判断存期，当存期为两年，超过1年再支取的，不调用唐豆收回接口
		else if(demTerm.contains("Y") && Integer.parseInt(demTerm.replace("Y", "")) == 2 && startDate.compareTo(svrdate) <= 0){
			bean.setShtdy("0.00");
		}
		else{
		//调用唐豆收回接口
			bean.setTdPayAmt(bck07622ResBean.getBody().getPayAmt().trim());//支取金额
			bean.setShtds(bck07622ResBean.getBody().getBack_Count().trim());//唐豆数量
			bean.setShtdy(bck07622ResBean.getBody().getBack_Exchange_Amt().trim());//唐豆金额
		}
		params.put("resCode",resCode);
		return params;
	}
	
	/**
	 * 唐豆收回
	 */
	@SuppressWarnings("rawtypes")
	public static Map inter07665(PublicAccCancelBean bean)throws Exception{
		//获取数据
		Map<String,String> map = new HashMap<String,String>();
		
		map.put("ACCT_NO", bean.getAccNo());
		map.put("SUB_ACCT_NO", "");
		map.put("PAY_DATE", bean.getSvrDate());
		map.put("PAY_JRNL", bean.getXHSvrJrnlNo().trim());
		map.put("PAY_AMT", bean.getTdPayAmt());//支取金额
		map.put("ORG_EXCHANGE_MODE", "");
		map.put("ORG_EXCHANGE_PROP", "");
		map.put("BACK_COUNT", bean.getShtds());//唐豆数量
		map.put("BACK_PROP", "");
		map.put("BACK_EXCHANGE_AMT", bean.getShtdy());//唐豆金额
		map.put("RECOV_TYPE", "1");
		map.put("OPPO_ACCT_NO",bean.getCardNo().trim());
		map.put("PASSWORD", "");
		
		Map<String,String> params = new HashMap<String,String>();
		String reqMsg=CreateXmlMsg.BCK_07665(map);
		logger.info("请求报文："+reqMsg);
		
		SocketClient socketClient =new SocketClient();
		socketClient.createSocket();
		
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK07665ResBean.class);
		BCK07665ResBean bck07665ResBean = (BCK07665ResBean)reqXs.fromXML(resMsg);
		
		if(bck07665ResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","调用接口07665失败");
			return params;
		}
		String resCode = bck07665ResBean.getHeadBean().getResCode();
		String errMsg = bck07665ResBean.getHeadBean().getResMsg();
		logger.info("唐豆收回resCode:"+resCode);
		logger.info("唐豆收回resMsg:"+errMsg);
		
		if(!SUCCESS.equals(resCode)){
			
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
			
		}
		bean.setTdSvrJrnlNo(bck07665ResBean.getBody().getSVR_JRNL_NO());//唐豆收回流水
		params.put("resCode",resCode);
		return params;
	}
	
	/**
	 * 个人账户销户
	 */
	@SuppressWarnings("rawtypes")
	public static Map inter07624(PublicAccCancelBean bean)throws Exception{
		//获取数据
		Map<String,String> map = new HashMap<String,String>();
		
		map.put("ACCT_NO", bean.getAccNo());//账号
		map.put("CERT_NO", bean.getBillNo());//凭证号
		map.put("CUST_NO", bean.getCustNo());//客户号
		map.put("CUST_NAME", "");//客户名称
		map.put("CURRENCY_TYPE", "00");//币种
		map.put("DRAW_COND", bean.getDrawCoun());//支付条件
		map.put("PASSWORD", bean.getBillPass());//存单密码
		map.put("CURRENCY", "CNY");//货币号
		map.put("PROD_CODE", "");//产品代码
		map.put("PROD_NAME", "");//产品名称
		map.put("BALANCE", bean.getTotalAmt());//余额
		map.put("DEP_TERM", "");//存期
		map.put("START_INT_DAT", "");//起息日
		map.put("OPEN_RATE", "");//开户利率
		map.put("CYC_AMT", "");//周期金额
		map.put("CYC", "");//周期
		map.put("TIMES", "");//次数
		map.put("BES_AMT", "");//预约金额
		map.put("BES_DATE", bean.getPreDate().replace("/", ""));//预约日期
		map.put("DRAW_CURRENCY", "00");//支取币种
		map.put("PRI_INTE_FLAG", "0");//本息分开（必输，0否、1是）
		map.put("CANCEL_AMT", bean.getTotalAmt());//销户金额
		map.put("PRI_INTE_WAY", "1");//本息走向
		map.put("OPPO_ACCT_NO", bean.getCardNo());//对方账号（本息转账，有）
		map.put("SUB_ACCT_NO", "0");//子账号（必输项，对方账号为一本通使显示，默认为0基本账户可修改）
		map.put("PRI_WAY", "");//本金走向
		map.put("OPPO_ACCT_NO1", "");//对方账号（本金转账，本金、利息分开时，对方账户不允许为同一账户）
		map.put("SUB_ACCT_NO1", "0");//子账号（必输项，对方账号为一本通使显示，默认为0基本账户可修改）
		map.put("INTE_WAY", "");//利息走向
		map.put("OPPO_ACCT_NO2", "");//对方账号（利息转账，有）
		map.put("SUB_ACCT_NO2", "0");//子账号（必输项，对方账号为一本通使显示，默认为0基本账户可修改）
		map.put("ID_TYPE", "10100");//证件类型
		map.put("ID_NO", bean.getIdNo());//证件号码
		map.put("PROVE_FLAG", "");//证明标志
		map.put("CASH_ANALY_NO", "");//现金分析号
		map.put("HAV_AGENT_FLAG", bean.getHavAgentFlag());//代理人标志 0-否 1-是
		//代理人信息
		map.put("AGENT_CUST_NAME", bean.getReadAgentIdName());//客户姓名
		map.put("AGENT_SEX", bean.getAgentsex());//性别
		map.put("AGENT_ID_TYPE", "10100");//证件类别
		map.put("AGENT_ID_NO", bean.getReadAgentIdNo());//证件号码
		map.put("AGENT_DUE_DATE", "");//到期日期
		map.put("AGENT_ISSUE_DATE", "");//签发日期
		map.put("AGENT_ISSUE_INST", bean.getAgentqfjg());//签发机关
		map.put("AGENT_NATION", "");//国籍
		map.put("AGENT_OCCUPATION", "公务员");//职业
		map.put("AGENT_REGIS_PER_RES", bean.getAgentaddress());//户口所在地
		map.put("AGENT_J_C_ADD", "");//经常居住地
		map.put("AGENT_TELEPHONE", "");//固定电话
		map.put("AGENT_MOB_PHONE", "");//移动电话
		// 业务流水-P端使用字段
		map.put("proName", bean.getProName());//产品名称
		map.put("proCode", bean.getProCode());//产品代码
		map.put("cardName", bean.getCardName());//户名
		if("002".equals(bean.getAccCancelType())){
			
			map.put("canelType", "2");//销户转电子账户
			
		}else if("003".equals(bean.getAccCancelType())){
			
			map.put("canelType", "3");//销户转唐行宝
			
		}else{
			map.put("canelType", "1");//销户转卡
			
		}
		map.put("checkStatus", bean.getCheckStatus());//标记存单是否自动识别，1自动识别，2手动输入，3无存单
		
		Map<String,String> params = new HashMap<String,String>();
		String reqMsg=CreateXmlMsg.BCK_07624(map);
		logger.info("请求报文："+reqMsg);
		
		SocketClient socketClient =new SocketClient();
		socketClient.createSocket();
		
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK07624ResBean.class);
		BCK07624ResBean bck07624ResBean = (BCK07624ResBean)reqXs.fromXML(resMsg);
		
		if(bck07624ResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","调用接口07624失败");
			return params;
		}
		String resCode = bck07624ResBean.getHeadBean().getResCode();
		String errMsg = bck07624ResBean.getHeadBean().getResMsg();
		logger.info("个人账户销户resCode:"+resCode);
		logger.info("个人账户销户resMsg:"+errMsg);
		
		if(!SUCCESS.equals(resCode)){
			
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
			
		}
		
		bean.setXHSvrJrnlNo(bck07624ResBean.getBody().getSVR_JRNL_NO().trim());//销户流水
		bean.setSvrDate(bck07624ResBean.getBody().getSVR_DATE());//核心日期
		bean.setRealPri(bck07624ResBean.getBody().getREAL_PRI().trim());//实付本金
		bean.setInteAmt(bck07624ResBean.getBody().getINTE_AMT().trim());//应付利息
		bean.setRealAmt(bck07624ResBean.getBody().getREAL_INTE().trim());//实付利息
		bean.setRate((bck07624ResBean.getBody().getRATE()!=null && !"".equals(bck07624ResBean.getBody().getRATE().trim()))?bck07624ResBean.getBody().getRATE().trim():"");//销户利率
		Double alreAmtDouble = Double.valueOf(bck07624ResBean.getBody().getINTE_AMT().trim())-Double.valueOf(bck07624ResBean.getBody().getREAL_INTE().trim());//已支付利息
		if(alreAmtDouble==0){
			bean.setAlreAmt("0.00");// 已支付利息
		}else{
			bean.setAlreAmt(String.valueOf(alreAmtDouble));// 已支付利息
		}
		bean.setAdvnInit(bck07624ResBean.getBody().getADVN_INIT().trim());//幸运豆
		if(bck07624ResBean.getBody().getXFD_COUNT()!=null&& bck07624ResBean.getBody().getXFD_COUNT()!=""){
			bean.setXFD_COUNT(bck07624ResBean.getBody().getXFD_COUNT().trim());//扣回消费豆数量
		}
		if(bck07624ResBean.getBody().getXFD_AMT()!=null&& bck07624ResBean.getBody().getXFD_AMT()!=""){
			bean.setXfdAmt(bck07624ResBean.getBody().getXFD_AMT().trim());//扣回消费豆金额
		}
		
		//下载衍生品文件
		String fileName = bck07624ResBean.getBody().getPRO_FILE();
		if(fileName == null || "".equals(fileName.trim())){
			logger.info("销户衍生品文件下载路径为空");
		}
		List<AccCancelProFileBean> list  = null;
		try {
			// 下载文件
			FtpFileUtils.downloadFileByFTPClient(Property.FTP_IP_DOWN, Integer.parseInt(Property.FTP_PORT_DOWN), Property.FTP_USER_DOWN, Property.FTP_PWD_DOWN, fileName, Property.FTP_LOCAL_PATH + fileName);
			list = TextFileReader.paddingList(Property.FTP_LOCAL_PATH + fileName, AccCancelProFileBean.class);
		} catch (Exception e) {
			logger.error("销户衍生品文件下载失败"+e);
		}
		if(list == null){
			
			logger.info("销户衍生品信息获取失败");
			
		}else if(list.size() == 0){
			
			logger.info("销户衍生品返回文件信息为空");
		}
		bean.getImportMap().put("cancelProFile", list);
		params.put("resCode",resCode);
		return params;
	}
	
	/**
	 * 卡下子账户销户
	 */
	@SuppressWarnings("rawtypes")
	public static Map inter03517(PublicAccCancelBean bean)throws Exception{
		//获取数据
		Map<String,String> map = new HashMap<String,String>();
		
		map.put("TRAN_CHANNEL", "08");//渠道号 00-网银 50-手机银行
		map.put("CARD_NO", bean.getCardNo());//卡号
		map.put("SUB_ACCT_NO", bean.getSubAccNo());//子账户
		map.put("CAL_MODE", "1");//结算方式  0-现金 1-转账
		map.put("CASH_ANALY_NO", "");//现金分析号
		map.put("OPPO_ACCT_NO", "");//对方账号
		map.put("ID_TYPE", "10100");//证件类型
		map.put("ID_NO", bean.getIdNo());//证件号码
		map.put("CERT_NO", bean.getBillNo());//凭证号
		map.put("PIN_VAL_FLAG", "");//验密标志(N-不验密，其他都验密)
		map.put("PASSWORD", bean.getCardPwd());//存单密码
		if("0".equals(bean.getHavAgentFlag())){
			map.put("HAV_AGENT_FLAG", "1");//代理人标志 0-是 1-否
		}else{
			map.put("HAV_AGENT_FLAG", "0");//代理人标志 0-是 1-否
		}
		//代理人信息
		map.put("Agent_CUST_NAME", bean.getReadAgentIdName());//客户姓名
		map.put("Agent_DUE_DATE", "");//到期日期
		map.put("Agent_ID_NO", bean.getReadAgentIdNo());//证件号码
		map.put("Agent_ID_TYPE", "10100");//证件类别
		map.put("Agent_ISSUE_DATE", "");//签发日期
		map.put("Agent_ISSUE_INST", bean.getAgentqfjg());//签发机关
		map.put("Agent_J_C_ADD", "");//经常居住地
		map.put("Agent_MOB_PHONE", "");//移动电话
		map.put("Agent_NATION", "");//国籍
		map.put("Agent_OCCUPATION", "");//职业
		map.put("Agent_GIS_PER_RES", bean.getAgentaddress());//户口所在地
		map.put("Agent_SEX", bean.getAgentsex());//性别
		map.put("Agent_TELEPHONE", "");//固定电话
		// 业务流水-P端使用字段
		map.put("accNo1", bean.getAccNo());//账号-子账号
		map.put("amount", bean.getAmount());//金额
		map.put("cardNo", bean.getCardNo());//银行卡号
		map.put("proName", bean.getProName());//产品名称
		map.put("proCode", bean.getProCode());//产品代码
		map.put("cardName", bean.getCardName());//户名
		if("002".equals(bean.getAccCancelType())){
			
			map.put("canelType", "2");//销户转电子账户
			
		}else if("003".equals(bean.getAccCancelType())){
			
			map.put("canelType", "3");//销户转唐行宝
			
		}else{
			map.put("canelType", "1");//销户转卡
			
		}
		map.put("checkStatus", bean.getCheckStatus());//标记存单是否自动识别，1自动识别，2手动输入，3无存单
		
		Map<String,String> params = new HashMap<String,String>();
		String reqMsg=CreateXmlMsg.BCK_03517(map);
		logger.info("请求报文："+reqMsg);
		
		SocketClient socketClient =new SocketClient();
		socketClient.createSocket();
		
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK03517ResBean.class);
		BCK03517ResBean bck03517ResBean = (BCK03517ResBean)reqXs.fromXML(resMsg);
		
		if(bck03517ResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","调用接口03517失败");
			return params;
		}
		String resCode = bck03517ResBean.getHeadBean().getResCode();
		String errMsg = bck03517ResBean.getHeadBean().getResMsg();
		logger.info("卡下子账户销户resCode:"+resCode);
		logger.info("卡下子账户销户resMsg:"+errMsg);
		
		if(!SUCCESS.equals(resCode)){
			
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
			
		}
		
		bean.setXHSvrJrnlNo(bck03517ResBean.getBody().getSVR_JRNL_NO().trim());//销户流水
		bean.setSvrDate(bck03517ResBean.getBody().getSVR_DATE());//核心日期
		bean.setInteAmt(bck03517ResBean.getBody().getRED_INTEREST().trim());//应付利息
		bean.setRealAmt(bck03517ResBean.getBody().getPAID_INTEREST().trim());//实付利息
		bean.setAdvnInit(bck03517ResBean.getBody().getADVN_INIT().trim());//幸运豆
		bean.setAlreAmt(bck03517ResBean.getBody().getPAYOFF_INTEREST().trim());//已支付利息
		bean.setRealPri(bck03517ResBean.getBody().getPAID_PRINCIPAL().trim());//实付本金
		bean.setRate((bck03517ResBean.getBody().getRATE()!=null && !"".equals(bck03517ResBean.getBody().getRATE().trim()))?bck03517ResBean.getBody().getRATE().trim():"");//销户利率
		if(bck03517ResBean.getBody().getXFD_COUNT()!=null&& bck03517ResBean.getBody().getXFD_COUNT()!=""){
			bean.setXFD_COUNT(bck03517ResBean.getBody().getXFD_COUNT().trim());//扣回消费豆数量
		}
		if(bck03517ResBean.getBody().getXFD_AMT()!=null&& bck03517ResBean.getBody().getXFD_AMT()!=""){
			bean.setXfdAmt(bck03517ResBean.getBody().getXFD_AMT().trim());//扣回消费豆金额
		}
		
		//下载衍生品文件
		String fileName = bck03517ResBean.getBody().getPRO_FILE();
		if(fileName == null || "".equals(fileName.trim())){
			logger.info("销户衍生品文件下载路径为空");
		}
		List<AccCancelProFileBean> list  = null;
		try {
			// 下载文件
			FtpFileUtils.downloadFileByFTPClient(Property.FTP_IP_DOWN, Integer.parseInt(Property.FTP_PORT_DOWN), Property.FTP_USER_DOWN, Property.FTP_PWD_DOWN, fileName, Property.FTP_LOCAL_PATH + fileName);
			list = TextFileReader.paddingList(Property.FTP_LOCAL_PATH + fileName, AccCancelProFileBean.class);
		} catch (Exception e) {
			logger.error("销户衍生品文件下载失败"+e);
		}
		if(list == null){
			
			logger.info("销户衍生品信息获取失败");
			
		}else if(list.size() == 0){
			
			logger.info("销户衍生品返回文件信息为空");
		}
		bean.getImportMap().put("cancelProFile", list);	
		params.put("resCode",resCode);
		return params;
	}
	
	/**
	 * 电子子账户销户
	 */
	@SuppressWarnings("rawtypes")
	public static Map inter03522(PublicAccCancelBean bean)throws Exception{
		//获取数据
		Map<String,String> map = new HashMap<String,String>();
		
		map.put("TRAN_CHANNEL", "08");//渠道号 00-网银 50-手机银行
		map.put("CARD_NO", bean.getCardNo());//电子账号
		map.put("SUB_ACCT_NO", bean.getSubAccNo());//子账户
		map.put("CAL_MODE", "1");//结算方式  0-现金 1-转账
		map.put("CASH_ANALY_NO", "");//现金分析号
		map.put("OPPO_ACCT_NO", "");//对方账号
		map.put("ID_TYPE", "10100");//证件类型
		map.put("ID_NO", bean.getIdNo());//证件号码
		map.put("CERT_NO", bean.getBillNo());//凭证号
		map.put("PIN_VAL_FLAG", "");//验密标志(N-不验密，其他都验密)
		map.put("PASSWORD", bean.getBillPass());//存单密码
		if("0".equals(bean.getHavAgentFlag())){
			map.put("HAV_AGENT_FLAG", "1");//代理人标志 0-是 1-否
		}else{
			map.put("HAV_AGENT_FLAG", "0");//代理人标志 0-是 1-否
		}
		//代理人信息
		map.put("Agent_CUST_NAME", bean.getReadAgentIdName());//客户姓名
		map.put("Agent_DUE_DATE", "");//到期日期
		map.put("Agent_ID_NO", bean.getReadAgentIdNo());//证件号码
		map.put("Agent_ID_TYPE", "10100");//证件类别
		map.put("Agent_ISSUE_DATE", "");//签发日期
		map.put("Agent_ISSUE_INST", bean.getAgentqfjg());//签发机关
		map.put("Agent_J_C_ADD", "");//经常居住地
		map.put("Agent_MOB_PHONE", "");//移动电话
		map.put("Agent_NATION", "");//国籍
		map.put("Agent_OCCUPATION", "");//职业
		map.put("Agent_GIS_PER_RES", bean.getAgentaddress());//户口所在地
		map.put("Agent_SEX", bean.getAgentsex());//性别
		map.put("Agent_TELEPHONE", "");//固定电话
		// 业务流水-P端使用字段
		map.put("accNo1", bean.getAccNo());//账号-子账号
		map.put("amount", bean.getAmount());//金额
		map.put("cardNo", bean.getCardNo());//银行卡号
		map.put("proName", bean.getProName());//产品名称
		map.put("proCode", bean.getProCode());//产品代码
		map.put("cardName", bean.getCardName());//户名
		if("002".equals(bean.getAccCancelType())){
			
			map.put("canelType", "2");//销户转电子账户
			
		}else if("003".equals(bean.getAccCancelType())){
			
			map.put("canelType", "3");//销户转唐行宝
			
		}else{
			map.put("canelType", "1");//销户转卡
			
		}
		map.put("checkStatus", bean.getCheckStatus());//标记存单是否自动识别，1自动识别，2手动输入，3无存单
		
		Map<String,String> params = new HashMap<String,String>();
		String reqMsg=CreateXmlMsg.BCK_03522(map);
		logger.info("请求报文："+reqMsg);
		
		SocketClient socketClient =new SocketClient();
		socketClient.createSocket();
		
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK03522ResBean.class);
		BCK03522ResBean bck03522ResBean = (BCK03522ResBean)reqXs.fromXML(resMsg);
		
		if(bck03522ResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","调用接口03522失败");
			return params;
		}
		String resCode = bck03522ResBean.getHeadBean().getResCode();
		String errMsg = bck03522ResBean.getHeadBean().getResMsg();
		logger.info("电子子账户销户resCode:"+resCode);
		logger.info("电子子账户销户resMsg:"+errMsg);
		
		if(!SUCCESS.equals(resCode)){
			
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
			
		}
		
		bean.setXHSvrJrnlNo(bck03522ResBean.getBody().getSVR_JRNL_NO().trim());//销户流水
		bean.setSvrDate(bck03522ResBean.getBody().getSVR_DATE());//核心日期
		bean.setInteAmt(bck03522ResBean.getBody().getRED_INTEREST().trim());//应付利息
		bean.setRealAmt(bck03522ResBean.getBody().getPAID_INTEREST().trim());//实付利息
		bean.setAdvnInit(bck03522ResBean.getBody().getADVN_INIT().trim());//幸运豆
		bean.setAlreAmt(bck03522ResBean.getBody().getPAYOFF_INTEREST().trim());//已支付利息
		bean.setRealPri(bck03522ResBean.getBody().getPAID_PRINCIPAL().trim());//实付本金
		bean.setRate((bck03522ResBean.getBody().getRATE()!=null && !"".equals(bck03522ResBean.getBody().getRATE().trim()))?bck03522ResBean.getBody().getRATE().trim():"");//销户利率
		if(bck03522ResBean.getBody().getXFD_COUNT()!=null&& bck03522ResBean.getBody().getXFD_COUNT()!=""){
			bean.setXFD_COUNT(bck03522ResBean.getBody().getXFD_COUNT().trim());//扣回消费豆数量
		}
		
		//下载衍生品文件
		String fileName = bck03522ResBean.getBody().getPRO_FILE();
		if(fileName == null || "".equals(fileName.trim())){
			logger.info("销户衍生品文件下载路径为空");
		}
		List<AccCancelProFileBean> list  = null;
		try {
			// 下载文件
			FtpFileUtils.downloadFileByFTPClient(Property.FTP_IP_DOWN, Integer.parseInt(Property.FTP_PORT_DOWN), Property.FTP_USER_DOWN, Property.FTP_PWD_DOWN, fileName, Property.FTP_LOCAL_PATH + fileName);
			list = TextFileReader.paddingList(Property.FTP_LOCAL_PATH + fileName, AccCancelProFileBean.class);
		} catch (Exception e) {
			logger.error("销户衍生品文件下载失败"+e);
		}
		if(list == null){
			
			logger.info("销户衍生品信息获取失败");
			
		}else if(list.size() == 0){
			
			logger.info("销户衍生品返回文件信息为空");
		}
		bean.getImportMap().put("cancelProFile", list);		
		params.put("resCode",resCode);
		return params;
	}
	
	/**
	 * 转入唐宝账户查询【55060】-前置07498
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map inter07498(PublicAccCancelBean bean)throws Exception{
		//获取数据
		Map<String, String> map = new HashMap<String, String>();
		map.put("OPER_CHOOSE","2");//操作选择(1-查收益较高账户2-查账户列表)
		map.put("ID_TYPE", "");//证件类别
		map.put("ID_NO", "");//证件号码
		map.put("CARD_NO", bean.getCardNo());//卡号
		map.put("SUB_ACCT_NO", "");//子账号
		map.put("CUST_NO", "");//客户号
		map.put("PROD_CODE", "");//产品代码
		map.put("TRAN_AMT", "0");//转入金额
		
		Map params = new HashMap();
		
		//连接socket
		String reqMsg=CreateXmlMsg.BCK_07498(map);
		SocketClient socketClient =new SocketClient();
		socketClient.createSocket();
		
		//发送请求报文、接收响应报文
		logger.info("请求报文："+reqMsg);
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		
		//解析xml
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK07498ResBean.class);
		BCK07498ResBean bck07498ResBean = (BCK07498ResBean)reqXs.fromXML(resMsg);
		
		//获取返回报文错误码和错误信息
		String resCode = bck07498ResBean.getHeadBean().getResCode();
		String errMsg = bck07498ResBean.getHeadBean().getResMsg();
		logger.info("转入唐宝账户查询resCode:"+resCode+"resMsg:"+resMsg);
		if(bck07498ResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","唐行宝账户查询接口调用失败");
			return params;
		}
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		String fileName = bck07498ResBean.getBody().getFILE_NAME();
		List<AccCancelTBMsgBean> list  = null;
		try {
			// 下载文件
			FtpFileUtils.downloadFileByFTPClient(Property.FTP_IP_DOWN, Integer.parseInt(Property.FTP_PORT_DOWN), Property.FTP_USER_DOWN, Property.FTP_PWD_DOWN, fileName, Property.FTP_LOCAL_PATH + fileName);
			list = TextFileReader.paddingList(Property.FTP_LOCAL_PATH + fileName, AccCancelTBMsgBean.class);
		} catch (Exception e) {
			logger.error("获取唐行宝账户信息失败"+e);
			params.put("resCode","4444");
			params.put("errMsg","获取唐行宝账户信息失败");
			return params;
		}
		if(list!=null && list.size()!=0){
			
			params.put(TB_MSG, list);
			
		}else{
			
			params.put("resCode","4444");
			params.put("errMsg","您未开通唐行宝账户");
			return params;
		}
		params.put("resCode",SUCCESS);
		return params;
	} 
	
	/**
	 * 唐宝x号转入【79100】-前置07499
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map inter07499(PublicAccCancelBean bean)throws Exception{
		//获取数据
		Map<String, String> map = new HashMap<String, String>();
		map.put("PASSWORD",bean.getCardPwd());//密码
		map.put("ID_TYPE", "10100");//证件类别
		map.put("ID_NO", bean.getIdNo());//证件号码
		map.put("CARD_NO", bean.getCardNo());//卡号
		map.put("SUB_ACCT_NO", bean.getTxbSubAccNo());//子账号
		map.put("CUST_NO", bean.getCustNo());//客户号
		map.put("PROD_CODE", bean.getTxbProCode());//产品代码
		map.put("TRAN_AMT", bean.getTxbInputAmt());//转入金额
		
		Map params = new HashMap();
		
		//连接socket
		String reqMsg=CreateXmlMsg.BCK_07499(map);
		SocketClient socketClient =new SocketClient();
		socketClient.createSocket();
		
		//发送请求报文、接收响应报文
		logger.info("请求报文："+reqMsg);
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		
		//解析xml
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK07499ResBean.class);
		BCK07499ResBean bck07499ResBean = (BCK07499ResBean)reqXs.fromXML(resMsg);
		
		//获取返回报文错误码和错误信息
		String resCode = bck07499ResBean.getHeadBean().getResCode();
		String errMsg = bck07499ResBean.getHeadBean().getResMsg();
		logger.info("唐宝x号转入resCode:"+resCode+"resMsg:"+errMsg);
		if(bck07499ResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","转入唐行宝接口调用失败");
			return params;
		}
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		bean.setTxbSvrJrnlNo((bck07499ResBean.getBody().getSVR_JRNL_NO()!=null && !"".equals(bck07499ResBean.getBody().getSVR_JRNL_NO().trim()))?bck07499ResBean.getBody().getSVR_JRNL_NO().trim():"");;
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
	public static Map inter07602(PublicAccCancelBean bean)throws Exception{
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
		
		params.put("resCode",resCode);
		return params;
	} 
	
	/**
	 * 鉴伪结果上送
	 */
	public static Map tms0007(PublicAccCancelBean bean)throws Exception{
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
	public static Map inter07659(PublicAccCancelBean bean)throws Exception{
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
	 * 柜员授权
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map inter07660(PublicAccCancelBean bean)throws Exception{
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
	 * 唐豆信息查询【02217】-前置07515
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map inter07515(PublicAccCancelBean bean)throws Exception{
		//获取数据
		Map<String, String> map = new HashMap<String, String>();
		map.put("ACCT_NO", bean.getAccNo());//账号
		map.put("CUST_NO", "");//客户号
		map.put("ID_TYPE", "");//证件类别
		map.put("ID_NO", "");//证件号码
		
		Map params = new HashMap();
		String reqMsg=CreateXmlMsg.BCK_07515(map);
		
		SocketClient socketClient =new SocketClient();
		socketClient.createSocket();
		logger.info("请求报文："+reqMsg);
		
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK07515ResBean.class);
		BCK07515ResBean bck07515ResBean = (BCK07515ResBean)reqXs.fromXML(resMsg);
		//获取返回报文错误码和错误信息
		String resCode = bck07515ResBean.getHeadBean().getResCode();
		String errMsg = bck07515ResBean.getHeadBean().getResMsg();
		logger.info(" 唐豆信息查询resCode："+resCode+" 唐豆信息查询resMsg:"+errMsg);
		if(bck07515ResBean == null){
			params.put("resCode",resCode);
			params.put("errMsg","调用接口07515失败");
			return params;
		}
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		
		String fileName = bck07515ResBean.getBody().getFILE_NAME();
		List<AccTdMsgBean> list  = null;
		try {
			// 下载文件
			FtpFileUtils.downloadFileByFTPClient(Property.FTP_IP_DOWN, Integer.parseInt(Property.FTP_PORT_DOWN), Property.FTP_USER_DOWN, Property.FTP_PWD_DOWN, fileName, Property.FTP_LOCAL_PATH + fileName);
			list = TextFileReader.paddingList(Property.FTP_LOCAL_PATH + fileName, AccTdMsgBean.class);
		} catch (Exception e) {
			logger.error("唐豆信息文件下载失败"+e);
			params.put("resCode","4444");
			params.put("errMsg","唐豆信息文件下载失败");
			return params;
		}
		bean.getImportMap().put("TdMsg", list);
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
	public static Map inter77017(PublicAccCancelBean bean)throws Exception{
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
}
