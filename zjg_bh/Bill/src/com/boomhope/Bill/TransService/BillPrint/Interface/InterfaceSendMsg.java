package com.boomhope.Bill.TransService.BillPrint.Interface;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.boomhope.Bill.TransService.AccTransfer.TransferUtil.SocketClient;
import com.boomhope.Bill.TransService.BillPrint.Bean.BillPrintBean;
import com.boomhope.Bill.TransService.BillPrint.Bean.BillPrintSubAccInfoBean;
import com.boomhope.Bill.Util.FtpFileUtils;
import com.boomhope.Bill.Util.Property;
import com.boomhope.Bill.Util.TextFileReader;
import com.boomhope.tms.message.XStreamNameCoder;
import com.boomhope.tms.message.account.in.BCK02864ResBean;
import com.boomhope.tms.message.account.in.BCK03845ResBean;
import com.boomhope.tms.message.in.bck.BCK07518ResBean;
import com.boomhope.tms.message.in.bck.BCK07519ResBean;
import com.boomhope.tms.message.in.bck.BCK07520ResBean;
import com.boomhope.tms.message.in.bck.BCK07522ResBean;
import com.boomhope.tms.message.in.bck.BCK07523ResBean;
import com.boomhope.tms.message.in.bck.BCK07601ResBean;
import com.boomhope.tms.message.in.bck.BCK07819ResBean;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class InterfaceSendMsg {
	static Logger logger = Logger.getLogger(InterfaceSendMsg.class);
	
	//报文返回成功
	public static String SUCCESS = "000000";
	public static String KEY_PRODUCT_RATES="KEY_PRODUCT_RATES";
	
	
	/**
	 * 产品利率信息查询
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map inter02864(BillPrintSubAccInfoBean bean)throws Exception{
		//获取数据
		Map<String,String> map = new HashMap<String,String>();
		String oepnDate=null;
		String openDateStr = bean.getOpenDate().trim();
		Pattern p=Pattern.compile("^[0-9]*$");
		Matcher ma=p.matcher(openDateStr);
		boolean pMa= ma.matches();
		if(pMa && openDateStr.length()==8){
			oepnDate=openDateStr.trim();
		}else{
			oepnDate=openDateStr.substring(0,4)+openDateStr.substring(5,7)+openDateStr.substring(8);
		}
		map.put("CUST_NO", "");
		map.put("FLOAT_FLAG","1");//非必输1-浮动，非1-不浮动
		map.put("CHL_ID",bean.getChannel());//渠道模块标识
		map.put("PROD_CODE", bean.getProductCode());// 产品名称
		map.put("RATE_DATE", oepnDate);// 利率日期
		if(bean.getNewAccNo()!=null){
			
			map.put("ACCT_NO", bean.getNewAccNo());//存单换开后卡号
			
		}else{
			map.put("ACCT_NO", bean.getAccNo());//卡号
			
		}
		
		map.put("SUB_ACCT_NO", bean.getSubAccNo());//子账号
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
		return params;
	} 
	
	
	/**
	 * 个人客户查询( 01020)-前置07519
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map inter07519(BillPrintBean bean)throws Exception{
		//获取数据
		Map<String,String> map = new HashMap<String,String>();
		map.put("SCH_TYPE", "2");//查询类型
		map.put("ID_TYPE","10100");//证件类型
		map.put("ID_NO",bean.getReadIdcard());//身份证号
		map.put("INFO_TYPE", "1");//信息类型
		Map params = new HashMap();
		String reqMsg=CreateXmlMsg.BCK_07519(map);
		SocketClient socketClient =new SocketClient();
		socketClient.createSocket();
		logger.info("请求报文："+reqMsg);
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK07519ResBean.class);
		BCK07519ResBean bck07519ResBean = (BCK07519ResBean)reqXs.fromXML(resMsg);
		String resCode07519 = bck07519ResBean.getHeadBean().getResCode();
		String resMsg07519 = bck07519ResBean.getHeadBean().getResMsg();
		logger.info("查询客户信息：resCode07519:"+resCode07519+"resMsg07519"+resMsg07519);
		if(!"000000".equals(resCode07519)){
			params.put("resCode",resCode07519);
			params.put("errMsg",resMsg07519);
			return params;
		}
		String fileName = bck07519ResBean.getBody().getFILE_NAME();
		FtpFileUtils.downloadFileByFTPClient(Property.FTP_IP_DOWN, Integer.parseInt(Property.FTP_PORT_DOWN), Property.FTP_USER_DOWN, Property.FTP_PWD_DOWN, fileName, Property.FTP_LOCAL_PATH + fileName);
		List<IdCardCheckInfo> list = TextFileReader.paddingList(Property.FTP_LOCAL_PATH + fileName, IdCardCheckInfo.class);
		logger.info(list);
		
		params.put("ID_INFO", list);
		params.put("resCode", SUCCESS);
		return params;
	} 
	
	
	/**
	 * 根据客户号查询所有卡号信息【90001】-前置07520
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map inter07520(BillPrintBean bean)throws Exception{
		//获取数据
		Map<String,String> map = new HashMap<String,String>();
		map.put("CUST_NO", bean.getCustNo());//客户号
		
		Map params = new HashMap();
		String reqMsg=CreateXmlMsg.BCK_07520(map);
		SocketClient socketClient =new SocketClient();
		socketClient.createSocket();
		
		logger.info("请求报文："+reqMsg);
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		
		logger.info("响应报文："+resMsg);
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		
		reqXs.alias("Root", BCK07520ResBean.class);
		BCK07520ResBean bck07520ResBean = (BCK07520ResBean)reqXs.fromXML(resMsg);
		
		String resCode07520 = bck07520ResBean.getHeadBean().getResCode();
		String resMsg07520 = bck07520ResBean.getHeadBean().getResMsg();
		logger.info("查询客户信息：resCode07520:"+resCode07520+"resMsg07520"+resMsg07520);
		if(!"000000".equals(resCode07520)){
			params.put("resCode",resCode07520);
			params.put("errMsg",resMsg07520);
			return params;
		}
		
		String fileName = bck07520ResBean.getBody().getFILE_NAME();
		FtpFileUtils.downloadFileByFTPClient(Property.FTP_IP_DOWN, Integer.parseInt(Property.FTP_PORT_DOWN), Property.FTP_USER_DOWN, Property.FTP_PWD_DOWN, fileName, Property.FTP_LOCAL_PATH + fileName);
		List<CardsInfo> list = TextFileReader.paddingList(Property.FTP_LOCAL_PATH + fileName, CardsInfo.class);
		logger.info(list);
		
		params.put("CARDS_INFO", list);
		params.put("resCode", SUCCESS);
		return params;
	} 
	/**
	 * 电子账户子账户列表查询【35109】
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map inter07819(BillPrintBean bean)throws Exception{
		//获取数据
		Map<String,String> map = new HashMap<String,String>();
		map.put("CUST_NO", bean.getCustNo());//客户号
		
		Map params = new HashMap();
		String reqMsg=CreateXmlMsg.BCK_07819(map);
		SocketClient socketClient =new SocketClient();
		socketClient.createSocket();
		
		logger.info("请求报文："+reqMsg);
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		
		logger.info("响应报文："+resMsg);
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		
		reqXs.alias("Root", BCK07819ResBean.class);
		BCK07819ResBean bck07819ResBean = (BCK07819ResBean)reqXs.fromXML(resMsg);
		
		String resCode07819 = bck07819ResBean.getHeadBean().getResCode();
		String resMsg07819 = bck07819ResBean.getHeadBean().getResMsg();
		logger.info("查询电子账户子账户列表：resCode07819:"+resCode07819+"resMsg07819"+resMsg07819);
		if(!"000000".equals(resCode07819)){
			params.put("resCode",resCode07819);
			params.put("errMsg",resMsg07819);
			return params;
		}
		
		String fileName = bck07819ResBean.getBody().getFILE_NAME();
		FtpFileUtils.downloadFileByFTPClient(Property.FTP_IP_DOWN, Integer.parseInt(Property.FTP_PORT_DOWN), Property.FTP_USER_DOWN, Property.FTP_PWD_DOWN, fileName, Property.FTP_LOCAL_PATH + fileName);
		List<EAccInfoBean> list = TextFileReader.paddingList(Property.FTP_LOCAL_PATH + fileName, EAccInfoBean.class);
		logger.info(list);
		
		params.put("CARDS_INFO", list);
		params.put("resCode", SUCCESS);
		return params;
	} 
	/**
	 * 子账号开户流水查询
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map inter07522(BillPrintSubAccInfoBean bean,BillPrintBean billPrintBean)throws Exception{
		//获取数据
		Map<String,String> map = new HashMap<String,String>();
		if(bean.getNewAccNo()!=null){
			
			map.put("ACCT_NO", bean.getNewAccNo());//存单换开后新卡号
			
		}else{
			map.put("ACCT_NO", bean.getAccNo());//卡号
			
		}
		
		map.put("SUB_ACCT_NO",bean.getSubAccNo());//子账号
		Map params = new HashMap();
		String reqMsg=CreateXmlMsg.BCK_07522(map);
		SocketClient socketClient =new SocketClient();
		socketClient.createSocket();
		logger.info("请求报文："+reqMsg);
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK07522ResBean.class);
		BCK07522ResBean bck07522ResBean = (BCK07522ResBean)reqXs.fromXML(resMsg);
		String resCode07522 = bck07522ResBean.getHeadBean().getResCode();
		String resMsg07522 = bck07522ResBean.getHeadBean().getResMsg();
		logger.info("子账户开户流水号：resCode07522:"+resCode07522+"resMsg07522"+resMsg07522);
		billPrintBean.getReqMCM001().setIntereturncode(resCode07522);
		billPrintBean.getReqMCM001().setIntereturnmsg(resMsg07522);
		if(!"000000".equals(resCode07522)){
			params.put("resCode",resCode07522);
			params.put("errMsg",resMsg07522);
			return params;
		}
		bean.setCreateSvrNo(bck07522ResBean.getBody().getORIG_SVR_JRNL_NO());
		params.put("resCode", SUCCESS);
		return params;
	} 
	
	/**
	 * 存款关联账号查询
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map inter07518(BillPrintSubAccInfoBean bean)throws Exception{
		//获取数据
		Map<String,String> map = new HashMap<String,String>();
		map.put("QRY_TYPE", bean.getQry_type());
		map.put("ACCT_NO", bean.getAccNo());
		map.put("SUB_ACCT_NO", bean.getSubAccNo());
		Map params = new HashMap();
		String reqMsg=CreateXmlMsg.BCK_07518(map);
		SocketClient socketClient =new SocketClient();
		socketClient.createSocket();
		logger.info("请求报文："+reqMsg);
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK07518ResBean.class);
		BCK07518ResBean bck07518ResBean = (BCK07518ResBean)reqXs.fromXML(resMsg);
		String resCode = bck07518ResBean.getHeadBean().getResCode();
		String errMsg = bck07518ResBean.getHeadBean().getResMsg();
		logger.info("查询关联账号失败resCode："+resCode);
		logger.info("查询关联账号失败resMsg："+errMsg);
		if(!"000000".equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		String fileName = bck07518ResBean.getBody().getFILE_NAME();
		List<AgreementAXLDGLNoInfo> list  = null;
		List<AgreementJXRJGLNoInfo> list1 = null;
		try {
			// 下载文件
			FtpFileUtils.downloadFileByFTPClient(Property.FTP_IP_DOWN, Integer.parseInt(Property.FTP_PORT_DOWN), Property.FTP_USER_DOWN, Property.FTP_PWD_DOWN, fileName, Property.FTP_LOCAL_PATH + fileName);
			if("1".equals(bean.getQry_type())||"5".equals(bean.getQry_type())){
				list = TextFileReader.paddingList(Property.FTP_LOCAL_PATH + fileName, AgreementAXLDGLNoInfo.class);
				if(list.size()>0){
					for(AgreementAXLDGLNoInfo subBean:list){
						bean.setGetNo(subBean.getGetCardNo().trim());
					}
				}
			}else if("2".equals(bean.getQry_type())||"3".equals(bean.getQry_type())){
				list1 = TextFileReader.paddingList(Property.FTP_LOCAL_PATH + fileName, AgreementJXRJGLNoInfo.class);
				if(list1.size()>0){
					for(AgreementJXRJGLNoInfo subBean:list1){
						if(subBean.getCognateSubAccNo()==null || "".equals(subBean.getCognateSubAccNo())){
							bean.setCognateNo(subBean.getCognateAccNo());
						}else{
							bean.setCognateNo(subBean.getCognateAccNo()+"-"+subBean.getCognateSubAccNo());
						}
						bean.setKoukuanNo(subBean.getPushMoneyAccNo());
					}
				}
			}
		} catch (Exception e) {
			logger.error("子账户列表信息下载失败"+e);
			params.put("resCode", "9999");
			params.put("errMsg","下载文件失败");
			return params;
		}
		
		params.put("resCode", SUCCESS);
		return params;
	} 
	
	
	/**
	 * 卡信息查询
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map inter03845(BillPrintBean bean)throws Exception{
		//获取数据
		Map<String, String> map = new HashMap<String, String>();
		map.put("CARD_NO", bean.getAccNo());//卡号
		map.put("SUB_ACCT_NO", "");//子账号
		map.put("PASSWD", "");//卡密码
		map.put("ISPIN", "0");
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
		
		if("1".equals(bean.getPrintChoice())){
			
			if(bck03845ResBean.getBody().getCARD_STAT()!=null && !"".equals(bck03845ResBean.getBody().getCARD_STAT().trim())){
				
				if("Q".equals(bck03845ResBean.getBody().getCARD_STAT())){
					logger.info("卡销户");
					params.put("resCode","5555");
					params.put("errMsg","您的银行卡已销户，不能办理此业务");
					return params;
				}
			}
			
			if(bck03845ResBean.getBody().getACCT_STAT()!=null && !"".equals(bck03845ResBean.getBody().getACCT_STAT().trim())){
				
				String utilStat = bck03845ResBean.getBody().getACCT_STAT().trim();
				
				if("1".equals(String.valueOf(utilStat.charAt(11))) || "2".equals(String.valueOf(utilStat.charAt(11)))){
					logger.info("卡为密码挂失状态");
					params.put("resCode","5555");
					params.put("errMsg","您的银行卡为密码挂失状态，不能办理此业务");
					return params;
				}
			}
			
		}else{
			
			if(bck03845ResBean.getBody().getCARD_STAT()!=null && !"".equals(bck03845ResBean.getBody().getCARD_STAT().trim())){
				
					
				if("Q".equals(bck03845ResBean.getBody().getCARD_STAT())){
					logger.info("卡销户");
					params.put("resCode","5555");
					params.put("errMsg","您的银行卡已销户，不能办理此业务");
					return params;
				}else if("N".equals(bck03845ResBean.getBody().getCARD_STAT())){
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
					params.put("errMsg","您的银行卡为密码挂失状态，不能办理此业务");
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
			String status=bck03845ResBean.getBody().getACCT_STAT();
			String stat=bck03845ResBean.getBody().getCARD_STAT();
			if(!"0".equals(stat.trim())){
				params.put("resCode","9999");
				params.put("errMsg","抱歉此卡处于非正常状态");
				logger.info("卡查询信息结果为卡为非正常状态。");
				return params;
			}
		}
		
		bean.setReadIdcard(bck03845ResBean.getBody().getID_NO()!=null?bck03845ResBean.getBody().getID_NO().trim():"");
		bean.setCardName(bck03845ResBean.getBody().getACCT_NAME()!=null?bck03845ResBean.getBody().getACCT_NAME().trim():"");
		params.put("resCode",SUCCESS);
		return params;
	} 
	
	
	/**
	 * 账号信息综合查询【02880】-前置07601
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static Map inter07601(BillPrintBean bean)throws Exception{
		//获取数据
		Map<String,String> map = new HashMap<String,String>();
		
		//校验银行卡时上送
		String accNo="";
		if("1".equals(bean.getIsCheckPass())){
			accNo=bean.getAccNo().trim();
			map.put("PASSWD", bean.getInputOrderPwd());//密码
		}else{
			map.put("PASSWD", "");//密码
			if(bean.getSubAccNo()!=null && !"".equals(bean.getSubAccNo())){
				accNo=bean.getAccNo().trim()+"-"+bean.getSubAccNo();
			}else{
				accNo=bean.getAccNo().trim();
			}
		}
		map.put("ACCT_NO", accNo.trim());//账号/卡号-子账号
		map.put("SUB_ACCT_NO", "");//
		map.put("CHK_PIN", bean.getIsCheckPass());//是否验密(0：不验密。1：验密)
		map.put("CERT_TYPE", bean.getBillType());//凭证种类
		map.put("CERT_NO_ADD", bean.getBillNo());//凭证号
		
		Map<String,Object> params = new HashMap<String,Object>();
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
		if("1".equals(bean.getPrintChoice())&&!"1".equals(bean.getIsCheckPass())){
			if("0010".equals(bck07601ResBean.getBody().getProdCode().trim())||bck07601ResBean.getBody().getProdName().contains("个人通知存款")){
				params.put("resCode","999999");
				params.put("errMsg","抱歉！此产品没有相关协议，不支持协议书打印。");
				return params;
			}
			if("0".equals(bck07601ResBean.getBody().getDrawCond()) ||bck07601ResBean.getBody().getDrawCond()==null||"".equals(bck07601ResBean.getBody().getDrawCond())){
				params.put("resCode", "999999");
				params.put("errMsg", "抱歉，您的存单无密码，暂不支持打印。");
				return params;
			}
			BillPrintSubAccInfoBean subBean = new BillPrintSubAccInfoBean();
			subBean.setAccNo(bean.getAccNo());//账号
			subBean.setSubAccNo(bean.getSubAccNo());//子账号
			subBean.setOpenDate((bck07601ResBean.getBody().getOpenDate()!=null && !"".equals(bck07601ResBean.getBody().getOpenDate().trim()))?bck07601ResBean.getBody().getOpenDate().trim():"");//开户日
			subBean.setEndIntDate((bck07601ResBean.getBody().getEndDate()!=null && !"".equals(bck07601ResBean.getBody().getEndDate().trim()))?bck07601ResBean.getBody().getEndDate().trim():"");//到期日
			subBean.setOpenATM((bck07601ResBean.getBody().getOpenAmt()!=null && !"".equals(bck07601ResBean.getBody().getOpenAmt().trim()))?bck07601ResBean.getBody().getOpenAmt().trim():"");//开户金额
			subBean.setProductCode((bck07601ResBean.getBody().getProdCode()!=null && !"".equals(bck07601ResBean.getBody().getProdCode().trim()))?bck07601ResBean.getBody().getProdCode().trim():"");//产品代码
			subBean.setProductName((bck07601ResBean.getBody().getProdName()!=null && !"".equals(bck07601ResBean.getBody().getProdName().trim()))?bck07601ResBean.getBody().getProdName().trim():"");//产品名称
			subBean.setDepTerm((bck07601ResBean.getBody().getDepTerm()!=null && !"".equals(bck07601ResBean.getBody().getDepTerm().trim()))?bck07601ResBean.getBody().getDepTerm().trim():"");//存期
			subBean.setOpenRate((bck07601ResBean.getBody().getRate()!=null && !"".equals(bck07601ResBean.getBody().getRate().trim()))?bck07601ResBean.getBody().getRate().trim():"");//开户利率
			subBean.setAccNoState((bck07601ResBean.getBody().getAcctStat()!=null && !"".equals(bck07601ResBean.getBody().getAcctStat().trim()))?bck07601ResBean.getBody().getAcctStat().trim():"");//账户状态
			subBean.setChannel((bck07601ResBean.getBody().getOpenChnal()!=null && !"".equals(bck07601ResBean.getBody().getOpenChnal().trim()))?bck07601ResBean.getBody().getOpenChnal().trim():"");//开户渠道
			subBean.setBill((bck07601ResBean.getBody().getCertNo()!=null && !"".equals(bck07601ResBean.getBody().getCertNo().trim()))?bck07601ResBean.getBody().getCertNo().trim():"");//凭证号
			
			if(bck07601ResBean.getBody().getOrigAcctNo()!=null && !"".equals(bck07601ResBean.getBody().getOrigAcctNo().trim())){
				
				subBean.setNewAccNo(bck07601ResBean.getBody().getOrigAcctNo().trim());//换开后新账号
				String  accN = bck07601ResBean.getBody().getOrigAcctNo().trim();
				if(accN.contains("-")){
					
					String[] split = accN.split("-");
					subBean.setNewAccNo(split[0]);//转入卡号
					subBean.setSubAccNo(split[1]);//子账号
				}
				
			}
			bean.setCardName((bck07601ResBean.getBody().getCustName()!=null && !"".equals(bck07601ResBean.getBody().getCustName().trim()))?bck07601ResBean.getBody().getCustName().trim():"");//存单证件姓名
			bean.setReadIdcard((bck07601ResBean.getBody().getIdNo()!=null && !"".equals(bck07601ResBean.getBody().getIdNo().trim()))?bck07601ResBean.getBody().getIdNo().trim():"");//存单证件号
			params.put("InputAccInfo", subBean);
		}
		
		params.put("resCode",resCode);
		return params;
	}
	
	/**
	 * 银行卡换开查询原卡号【08029】前置07523
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map inter07523(BillPrintBean bean)throws Exception{
		//获取数据
		Map<String,String> map = new HashMap<String,String>();
		map.put("CARD_NO", bean.getAccNo());//卡号
		
		Map params = new HashMap();
		String reqMsg=CreateXmlMsg.BCK_07523(map);
		SocketClient socketClient =new SocketClient();
		socketClient.createSocket();
		
		logger.info("请求报文："+reqMsg);
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		if(resMsg == null || "".equals(resMsg)){
			params.put("resCode","4444");
			params.put("errMsg","调用接口07523失败");
			return params;
		}
		
		logger.info("响应报文："+resMsg);
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		
		reqXs.alias("Root", BCK07523ResBean.class);
		BCK07523ResBean bck07523ResBean = (BCK07523ResBean)reqXs.fromXML(resMsg);
		
		String resCode07523 = bck07523ResBean.getHeadBean().getResCode();
		String resMsg07523 = bck07523ResBean.getHeadBean().getResMsg();
		logger.info("查询银行卡换开原卡号：resCode07523:"+resCode07523+"resMsg07523"+resMsg07523);
		if(!"000000".equals(resCode07523)){
			params.put("resCode",resCode07523);
			params.put("errMsg",resMsg07523);
			return params;
		}
		
		String fileName = bck07523ResBean.getBody().getFILE_NAME();
		logger.info("开始下载银行卡换开原卡号文件");
		List<HistoryCardNo>  list = new ArrayList<HistoryCardNo>();
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null; //用于包装InputStreamReader,提高处理性能。因为BufferedReader有缓冲的，而InputStreamReader没有。
		String str = "";
		String str1 = "";
		try {
			// 下载文件
			FtpFileUtils.downloadFileByFTPClient(Property.FTP_IP_DOWN, Integer.parseInt(Property.FTP_PORT_DOWN), Property.FTP_USER_DOWN, Property.FTP_PWD_DOWN, fileName, Property.FTP_LOCAL_PATH + fileName);
			fis = new FileInputStream(Property.FTP_LOCAL_PATH + fileName);// FileInputStream
			// 从文件系统中的某个文件中获取字节
		    isr = new InputStreamReader(fis,"GBK");// InputStreamReader 是字节流通向字符流的桥梁,
		    br = new BufferedReader(isr);// 从字符输入流中读取文件中的内容,封装了一个new InputStreamReader的对象
		    while ((str = br.readLine()) != null) {
		    	HistoryCardNo card = new HistoryCardNo();
		    	card.setCardNo(str.trim());
		    	list.add(card);
		    	System.out.println(str1);// 打印出str1 
		    	logger.info("历史卡号："+str);
		    }
		    
		} catch (FileNotFoundException e) {
				logger.error("找不到指定文件"+e);
				params.put("resCode","4444");
				params.put("errMsg","未找到换开的原卡号文件");
				return params;
		} catch (IOException e) {
				logger.error("读取换开的原卡号文件失败"+e);
				params.put("resCode","4444");
				params.put("errMsg","读取换开的原卡号文件失败");
				return params;
	    } finally {
	    	 try {
			      br.close();
			      isr.close();
			      fis.close();
			     // 关闭的时候最好按照先后顺序关闭最后开的先关闭所以先关s,再关n,最后关m
			    } catch (IOException e) {
			    	logger.error(e);   
			    }
	    }
		
		params.put("historyCardNo", list);
		params.put("resCode", SUCCESS);
		return params;
	} 
}
