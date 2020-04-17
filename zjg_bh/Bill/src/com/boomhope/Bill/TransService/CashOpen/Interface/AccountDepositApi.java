package com.boomhope.Bill.TransService.CashOpen.Interface;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.boomhope.Bill.Public.GlobalParameter;
import com.boomhope.Bill.TransService.CashOpen.Bean.QXRateInfosBean;
import com.boomhope.Bill.Util.FtpFileUtils;
import com.boomhope.Bill.Util.Property;
import com.boomhope.Bill.Util.TextFileReader;
import com.boomhope.tms.message.XStreamNameCoder;
import com.boomhope.tms.message.account.in.BCK02808ReqBean;
import com.boomhope.tms.message.account.in.BCK02808ReqBodyBean;
import com.boomhope.tms.message.account.in.BCK02808ResBean;
import com.boomhope.tms.message.account.in.BCK02864ReqBean;
import com.boomhope.tms.message.account.in.BCK02864ReqBodyBean;
import com.boomhope.tms.message.account.in.BCK02864ResBean;
import com.boomhope.tms.message.account.in.BCK02867ReqBean;
import com.boomhope.tms.message.account.in.BCK02867ReqBodyBean;
import com.boomhope.tms.message.account.in.BCK02867ResBean;
import com.boomhope.tms.message.account.in.BCK03510ReqBean;
import com.boomhope.tms.message.account.in.BCK03510ReqBodyBean;
import com.boomhope.tms.message.account.in.BCK03510ResBean;
import com.boomhope.tms.message.account.in.BCK03511ReqBean;
import com.boomhope.tms.message.account.in.BCK03511ReqBodyBean;
import com.boomhope.tms.message.account.in.BCK03511ResBean;
import com.boomhope.tms.message.account.in.BCK03512ReqBean;
import com.boomhope.tms.message.account.in.BCK03512ReqBodyBean;
import com.boomhope.tms.message.account.in.BCK03512ResBean;
import com.boomhope.tms.message.account.in.BCK03845ReqBean;
import com.boomhope.tms.message.account.in.BCK03845ReqBodyBean;
import com.boomhope.tms.message.account.in.BCK03845ResBean;
import com.boomhope.tms.message.account.in.BCK03870ReqBean;
import com.boomhope.tms.message.account.in.BCK03870ReqBodyBean;
import com.boomhope.tms.message.account.in.BCK03870ResBean;
import com.boomhope.tms.message.account.in.BCK07505AcctInfo;
import com.boomhope.tms.message.account.in.BCK07505ReqBean;
import com.boomhope.tms.message.account.in.BCK07505ReqBodyBean;
import com.boomhope.tms.message.account.in.BCK07505ResBean;
import com.boomhope.tms.message.account.in.BCK07506ReqAgentInfBean;
import com.boomhope.tms.message.account.in.BCK07506ReqBean;
import com.boomhope.tms.message.account.in.BCK07506ReqBodyBean;
import com.boomhope.tms.message.account.in.BCK07506ResBean;
import com.boomhope.tms.message.account.in.tms.TmsAccountSaveOrderReqBean;
import com.boomhope.tms.message.account.in.tms.TmsAccountSaveOrderReqBodyBean;
import com.boomhope.tms.message.account.in.tms.TmsAccountSaveOrderResBean;
import com.boomhope.tms.message.in.InReqHeadBean;
import com.boomhope.tms.message.in.bck.BCK07663ReqAgentInfBean;
import com.boomhope.tms.message.in.bck.BCK07663ReqBean;
import com.boomhope.tms.message.in.bck.BCK07663ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK07663ResBean;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XppDriver;

public class AccountDepositApi {
	protected static Logger log = Logger.getLogger(AccountDepositApi.class);
	
	public static String KEY_PRODUCT_RATES="PRODUCT_RATES";
	public static String RESULT="RESULT";
	public static String PRODUCT_INFOS="PRODUCT_INFOS";

	/**
	 * 获取请求P端的Head
	 * @return
	 */
	public static InReqHeadBean getInReqHeadBean(){
		InReqHeadBean inReqHeadBean = new InReqHeadBean();
		inReqHeadBean.setMachineNo(GlobalParameter.machineNo);
		inReqHeadBean.setBranchNo(GlobalParameter.branchNo);
		inReqHeadBean.setMachineType(GlobalParameter.MACHINE_TYPE);
		return inReqHeadBean;
	}
	/**
	 * 卡信息查询--03845
	 * @param map 
	 * @return
	 */
	public BCK03845ResBean searchCard(Map<String, Object> map){
		try {
			log.info("开始调用  卡信息查询--03845 接口");
			BCK03845ReqBean reqBean = new BCK03845ReqBean();
			InReqHeadBean inReqHeadBean = getInReqHeadBean();
			inReqHeadBean.setTradeCode("BCK_03845");//
			reqBean.setHeadBean(inReqHeadBean);
			BCK03845ReqBodyBean bck03845ReqBodyBean = new BCK03845ReqBodyBean();
			bck03845ReqBodyBean.setCARD_NO(String.valueOf(map.get("CARD_NO")));
			bck03845ReqBodyBean.setISPIN(String.valueOf(map.get("ISPIN")));
			bck03845ReqBodyBean.setPASSWD(String.valueOf(map.get("PASSWD")));
			/*if("1".equals(String.valueOf(map.get("ISPIN")))){
				String passwd = ;
				String tranPwd=EncryptUtils.tranPin310("CODM."+GlobalParameter.machineNo+".zpk", Property.encrypt_mac_keyflag, String.valueOf(map.get("CARD_NO")), passwd);
				account03845ReqBodyBean.setPASSWD(tranPwd);
			}else{
				
			}*/
			bck03845ReqBodyBean.setSUB_ACCT_NO(String.valueOf(map.get("SUB_ACCT_NO")));
			reqBean.setBody(bck03845ReqBodyBean);
			
			XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
			xstream.autodetectAnnotations(true);
			xstream.alias("ROOT", BCK03845ReqBean.class);
			xstream.alias("HEAD", InReqHeadBean.class);
			xstream.alias("BODY", BCK03845ReqBodyBean.class);
			String xml = xstream.toXML(reqBean);
			log.info("发送前置报文：\n"+xml);
			//发送前置交易请求 
			String outResMsg = new CDJSocketClient().sendMsg(xml);
			log.info("前置返回报文：\n"+outResMsg);
			if(StringUtils.isNotBlank(outResMsg)){
				XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
				reqXs.alias("Root", BCK03845ResBean.class);
				
				BCK03845ResBean bck03845ResBean = (BCK03845ResBean)reqXs.fromXML(outResMsg);
				return bck03845ResBean;
			}
		} catch (Exception e) {
			log.error("调用03845接口异常"+e.getMessage(), e);
		}
		return null;
	}
	
	
	/**
	 * 产品利率信息查询--02867
	 * @param map
	 * @return
	 */
	public BCK02867ResBean searchProduct(Map<String, Object> map){
		try {
			log.info("开始调用  产品利率信息查询--02867 接口");
			BCK02867ReqBean reqBean = new BCK02867ReqBean();
			InReqHeadBean inReqHeadBean = getInReqHeadBean();
			inReqHeadBean.setTradeCode("BCK_02867");//
			reqBean.setHeadBean(inReqHeadBean);
			BCK02867ReqBodyBean account02867ReqBodyBean = new BCK02867ReqBodyBean();
			account02867ReqBodyBean.setCUST_NO((String) map.get("CUST_NO"));
			account02867ReqBodyBean.setPRODUCT_CODE((String) map.get("PRODUCT_CODE"));
			account02867ReqBodyBean.setQRY_TYPE((String) map.get("QRY_TYPE"));
			account02867ReqBodyBean.setDEP_AMT((String) map.get("DEP_AMT"));
			account02867ReqBodyBean.setMAX_AMT((String) map.get("MAX_AMT"));	
			Object object = map.get("CHL_ID");
			if(null != object){//该字段不传默认为1，为协议存款，，传3为私行快线
				account02867ReqBodyBean.setCHL_ID((String) object);
			}
			reqBean.setBody(account02867ReqBodyBean);
			XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
			xstream.autodetectAnnotations(true);
			xstream.alias("ROOT", BCK02867ReqBean.class);
			xstream.alias("HEAD", InReqHeadBean.class);
			xstream.alias("BODY", BCK02867ReqBodyBean.class);
			String xml = xstream.toXML(reqBean);
			log.info("发送前置报文：\n"+xml);
			//发送前置交易请求 
			String outResMsg = new CDJSocketClient().sendMsg(xml);
			log.info("前置返回报文：\n"+outResMsg);
			map.put(RESULT, outResMsg);
			if(StringUtils.isNotBlank(outResMsg)){
				XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
				reqXs.alias("Root", BCK02867ResBean.class);
				BCK02867ResBean bck02867ResBean = (BCK02867ResBean)reqXs.fromXML(outResMsg);
				if(!"000000".equals(bck02867ResBean.getHeadBean().getResCode()) || "44444".equals(bck02867ResBean.getHeadBean().getResCode())){
					return bck02867ResBean;
				}
				String fileName = bck02867ResBean.getBody().getPRODUCT_FI_NM();
				FtpFileUtils.downloadFileByFTPClient(Property.FTP_IP_DOWN, Integer.parseInt(Property.FTP_PORT_DOWN), Property.FTP_USER_DOWN, Property.FTP_PWD_DOWN, fileName, Property.FTP_LOCAL_PATH + fileName);
				if("1".equals(map.get("QRY_TYPE"))) {
					List<ProductRateInfo1> list = TextFileReader.paddingList(Property.FTP_LOCAL_PATH + fileName, ProductRateInfo1.class);
					map.put(KEY_PRODUCT_RATES, list);
				}else if("2".equals(map.get("QRY_TYPE"))||"3".equals(map.get("QRY_TYPE"))) {
					List<ProductRateInfo2> list = TextFileReader.paddingList(Property.FTP_LOCAL_PATH + fileName, ProductRateInfo2.class);
					map.put(KEY_PRODUCT_RATES, list);
				}else if("4".equals(map.get("QRY_TYPE"))) {
					List<ProductRateInfo> list = TextFileReader.paddingList(Property.FTP_LOCAL_PATH + fileName, ProductRateInfo.class);
					map.put(KEY_PRODUCT_RATES, list);
				}
				return bck02867ResBean;
			}
		} catch (Exception e) {
			log.error("调用02867接口异常"+e.getMessage(), e);
		}
		return null;
	}
	/**
	 * 个人存款开户【02022】--前置02808
	 * @param map
	 * @return
	 */
	public BCK02808ResBean openAccount(Map<String, Object> map){
		try {
			log.info("开始调用  个人存款开户02022--前置02808 接口");
			BCK02808ReqBean reqBean = new BCK02808ReqBean();
			InReqHeadBean inReqHeadBean = getInReqHeadBean();
			inReqHeadBean.setTradeCode("BCK_02808");//
			reqBean.setHeadBean(inReqHeadBean);
			BCK02808ReqBodyBean bck02808ReqBodyBean = new BCK02808ReqBodyBean();
			bck02808ReqBodyBean.setSTART_INT_DATE(String.valueOf(map.get("START_INT_DATE")));
			
			bck02808ReqBodyBean.setID_TYPE(String.valueOf(map.get("ID_TYPE")));
			bck02808ReqBodyBean.setID_NO(String.valueOf(map.get("ID_NO")));
			bck02808ReqBodyBean.setCUST_NO(String.valueOf(map.get("CUST_NO")));
			//存单密码加密
			bck02808ReqBodyBean.setLIMIT(String.valueOf(map.get("LIMIT")));
			bck02808ReqBodyBean.setPASSWORD(String.valueOf(map.get("PASSWORD")));
			//修改为由后端TMS加密
			/*if(StringUtils.isNotBlank(password)){
				String cardNo="0000000000000000";//CODM.000C0011.zpk
				String tranPwd=EncryptUtils.tranPin310("CODM."+GlobalParameter.machineNo+".zpk", Property.encrypt_mac_keyflag, cardNo, password);
				account02808ReqBodyBean.setPASSWORD(tranPwd);
				account02808ReqBodyBean.setLIMIT("1");
			}else{
				account02808ReqBodyBean.setLIMIT("0");
				account02808ReqBodyBean.setPASSWORD(password);
			}*/
			bck02808ReqBodyBean.setCURRENCY("00");
			bck02808ReqBodyBean.setACCT_TYPE("0");
			bck02808ReqBodyBean.setSETT_TYPE(String.valueOf(map.get("SETT_TYPE")));
			bck02808ReqBodyBean.setHAV_AGENT_FLAG("0");
			
			bck02808ReqBodyBean.setUNIT_FLAG(String.valueOf(map.get("UNIT_FLAG")));
			bck02808ReqBodyBean.setDEP_TERM(String.valueOf(map.get("DEP_TERM")));
			bck02808ReqBodyBean.setOPPO_ACCT_NO(String.valueOf(map.get("OPPO_ACCT_NO")));
			bck02808ReqBodyBean.setOPPO_DRAW_COND(String.valueOf(map.get("OPPO_DRAW_COND")));
			//银行卡密码加密
			//String tranPwd=EncryptUtils.tranPin310("CODM."+GlobalParameter.machineNo+".zpk", Property.encrypt_mac_keyflag, OPPO_ACCT_NO, DRAW_PASSWORD);
			bck02808ReqBodyBean.setDRAW_PASSWORD(String.valueOf(map.get("DRAW_PASSWORD")));
			bck02808ReqBodyBean.setAMT(String.valueOf(map.get("AMT")));
			bck02808ReqBodyBean.setNEW_CERT_NO(String.valueOf(map.get("NEW_CERT_NO")));
			bck02808ReqBodyBean.setEXCH_FLAG(String.valueOf(map.get("EXCH_FLAG")));
			bck02808ReqBodyBean.setPROD_CODE(String.valueOf(map.get("PROD_CODE")));
			bck02808ReqBodyBean.setRULE_NO(String.valueOf(map.get("RULE_NO")));//收益方式规则编号
			if(map.get("PUT_INT_ACCT")!=null){
				bck02808ReqBodyBean.setPUT_INT_ACCT(String.valueOf(map.get("PUT_INT_ACCT")));
			}
			if(map.get("SUB_ACCT_NO1")!=null){
				bck02808ReqBodyBean.setSUB_ACCT_NO1(String.valueOf(map.get("SUB_ACCT_NO1")));
			}
			Object object = map.get("CHL_ID");
			if(null != object){//该字段不传默认为1，为协议存款，，传3为私行快线
				bck02808ReqBodyBean.setCHL_ID(String.valueOf(object));
			}
			reqBean.setBody(bck02808ReqBodyBean);
			
			XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
			xstream.autodetectAnnotations(true);
			xstream.alias("ROOT", BCK02808ReqBean.class);
			xstream.alias("HEAD", InReqHeadBean.class);
			xstream.alias("BODY", BCK02808ReqBodyBean.class);
			String xml = xstream.toXML(reqBean);
			log.info("发送前置报文：\n"+xml);
			//发送前置交易请求 
			String outResMsg = new CDJSocketClient().sendMsg(xml);
			log.info("前置返回报文：\n"+outResMsg);
			map.put(RESULT, outResMsg);
			if(StringUtils.isNotBlank(outResMsg)){
				XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
				reqXs.alias("Root", BCK02808ResBean.class);
				
				BCK02808ResBean bck02808ResBean = (BCK02808ResBean)reqXs.fromXML(outResMsg);
				if(StringUtils.isNotBlank(bck02808ResBean.getBody().getFILE_NAME_RET())){
					String fileName = bck02808ResBean.getBody().getFILE_NAME_RET();
					FtpFileUtils.downloadFileByFTPClient(Property.FTP_IP_DOWN, Integer.parseInt(Property.FTP_PORT_DOWN), Property.FTP_USER_DOWN, Property.FTP_PWD_DOWN, fileName, Property.FTP_LOCAL_PATH + fileName);										
						List<OpenJxJInfo> list = TextFileReader.paddingList(Property.FTP_LOCAL_PATH + fileName, OpenJxJInfo.class);
						map.put(KEY_PRODUCT_RATES, list);					
				}
				return bck02808ResBean;
			}
		} catch (Exception e) {
			log.error("调用02808接口异常"+e.getMessage(),e);
		}
		return null;
	}
	/**
	 * 入库方法
	 * @param map
	 * @param rep02808  开户报文
	 * @param rep07506 派发规则查询(交易码07506)	
	 * @param rep07505 唐豆兑付--07505
	 * @return
	 */
	public TmsAccountSaveOrderResBean saveAccountOrder(Map<String, Object> map,String rep02808,String rep07506,String rep07505){
		try {
			log.info("开始调用 开户入库 接口");
			TmsAccountSaveOrderReqBean accountSaveOrderReqBean = new TmsAccountSaveOrderReqBean();
			InReqHeadBean inReqHeadBean = getInReqHeadBean();
			inReqHeadBean.setTradeCode("ACCOUNT_0001");//
			accountSaveOrderReqBean.setHeadBean(inReqHeadBean);
			
			TmsAccountSaveOrderReqBodyBean accountSaveOrderReqBodyBean = new TmsAccountSaveOrderReqBodyBean();
			accountSaveOrderReqBodyBean.setCARD_NO(map.get("CARD_NO").toString());//卡号
			accountSaveOrderReqBodyBean.setCERT_NO(map.get("CERT_NO").toString());//凭证号
			accountSaveOrderReqBodyBean.setCUSTOMER_NAME(map.get("CUSTOMER_NAME").toString());//户名
			accountSaveOrderReqBodyBean.setDEPOSIT_AMT(map.get("DEPOSIT_AMT").toString());//存款金额
			accountSaveOrderReqBodyBean.setDEPOSIT_PASSWORD_ENABLED(map.get("DEPOSIT_PASSWORD_ENABLED").toString());//是否加密
			accountSaveOrderReqBodyBean.setDEPOSIT_PERIOD(map.get("DEPOSIT_PERIOD").toString());//大写存期
			accountSaveOrderReqBodyBean.setDEPOSIT_RESAVE_ENABLED("1".equals(map.get("DEPOSIT_RESAVE_ENABLED").toString())?"1":"2");//是否转存  1：是。0：否
			accountSaveOrderReqBodyBean.setPRODUCT_CODE(map.get("PRODUCT_CODE").toString());//产品编号
			accountSaveOrderReqBodyBean.setPRODUCT_NAME(map.get("PRODUCT_NAME").toString());//产品名称
			accountSaveOrderReqBodyBean.setRATE(map.get("RATE").toString());//利率
			accountSaveOrderReqBodyBean.setREP_02808(rep02808);
			accountSaveOrderReqBodyBean.setREP_07505(rep07505);
			accountSaveOrderReqBodyBean.setREP_07506(rep07506);
			
			accountSaveOrderReqBodyBean.setSUB_ACCOUNT_NO(map.get("SUB_ACCOUNT_NO").toString());//存单账号
			accountSaveOrderReqBodyBean.setTERMINAL_CODE(map.get("TERMINAL_CODE").toString());//机器出厂编号
			accountSaveOrderReqBodyBean.setUNIT_CODE(map.get("UNIT_CODE").toString());//机构编号
			accountSaveOrderReqBodyBean.setUNIT_NAME(map.get("UNIT_NAME").toString());//机构名称
			accountSaveOrderReqBodyBean.setPAY_TYPE(map.get("PAY_TYPE").toString());//开户支付类型
			accountSaveOrderReqBean.setBody(accountSaveOrderReqBodyBean);
			
			XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
			xstream.autodetectAnnotations(true);
			xstream.alias("ROOT", TmsAccountSaveOrderReqBean.class);
			xstream.alias("HEAD", InReqHeadBean.class);
			xstream.alias("BODY", TmsAccountSaveOrderReqBodyBean.class);
			String xml = xstream.toXML(accountSaveOrderReqBean);
			log.info("发送前置报文：\n"+xml);
			//发送前置交易请求 
			String outResMsg = new CDJSocketClient().sendMsg(xml);
			log.info("前置返回报文：\n"+outResMsg);
			map.put(RESULT, outResMsg);
			if(StringUtils.isNotBlank(outResMsg)){
				XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
				reqXs.alias("Root", TmsAccountSaveOrderResBean.class);
				
				TmsAccountSaveOrderResBean tmsAccountSaveOrderResBean = (TmsAccountSaveOrderResBean)reqXs.fromXML(outResMsg);
				return tmsAccountSaveOrderResBean;
			}
			
		} catch (Exception e) {
			log.error("调用 开户入库 接口异常"+e.getMessage(),e);
		}
		return null;
	}
	/**
	 * 糖豆查询接口07506
	 * @param map
	 * @return
	 */
	public BCK07506ResBean searchTangDouRule(Map<String, Object> map){
		try {
			log.info("开始调用  糖豆查询接口07506 接口");
			BCK07506ReqBean reqBean = new BCK07506ReqBean();
			InReqHeadBean inReqHeadBean = getInReqHeadBean();
			inReqHeadBean.setTradeCode("BCK_07506");//
			reqBean.setHeadBean(inReqHeadBean);
			BCK07506ReqBodyBean bck07506ReqBodyBean = new BCK07506ReqBodyBean();
			bck07506ReqBodyBean.setPRODUCT_CODE(String.valueOf(map.get("PRODUCT_CODE")));
			bck07506ReqBodyBean.setAMT(String.valueOf(map.get("AMT")));
			bck07506ReqBodyBean.setDEP_TERM(String.valueOf(map.get("DEP_TERM")));
			bck07506ReqBodyBean.setACCEPT_DATE(String.valueOf(map.get("ACCEPT_DATE")));
			bck07506ReqBodyBean.setIN_INST_NO(String.valueOf(map.get("IN_INST_NO")));
			bck07506ReqBodyBean.setACT_CHNL(String.valueOf(map.get("ACT_CHNL")));
			bck07506ReqBodyBean.setDETAIL_NUM(String.valueOf(map.get("DETAIL_NUM")));
			
			BCK07506ReqAgentInfBean bck07506ReqAgentInfBean = new BCK07506ReqAgentInfBean();
			bck07506ReqAgentInfBean.setACCT_BAL(String.valueOf(map.get("ACCT_BAL")));
			bck07506ReqAgentInfBean.setSUB_ACCT_NO(String.valueOf(map.get("SUB_ACCT_NO")));
			bck07506ReqAgentInfBean.setACCT_NO(String.valueOf(map.get("ACCT_NO")));
			bck07506ReqBodyBean.setACCT_INFO(bck07506ReqAgentInfBean);
			
			reqBean.setBody(bck07506ReqBodyBean);
			XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
			xstream.autodetectAnnotations(true);
			xstream.alias("ROOT", BCK07506ReqBean.class);
			xstream.alias("HEAD", InReqHeadBean.class);
			xstream.alias("BODY", BCK07506ReqBodyBean.class);
			String xml = xstream.toXML(reqBean);
			log.info("发送前置报文：\n"+xml);
			//发送前置交易请求 
			String outResMsg = new CDJSocketClient().sendMsg(xml);
			log.info("前置返回报文：\n"+outResMsg);
			map.put(RESULT, outResMsg);
			if(StringUtils.isNotBlank(outResMsg)){
				XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
				reqXs.alias("Root", BCK07506ResBean.class);
				
				BCK07506ResBean bck07506ResBean = (BCK07506ResBean)reqXs.fromXML(outResMsg);
				return bck07506ResBean;
			}
		} catch (Exception e) {
			log.info("调用07506接口异常"+e.getMessage(),e);
		}
		return null;
	}
	/**
	 * 唐豆兑付--07505
	 * @param map
	 * @return
	 */
	public BCK07505ResBean exchangeTangDou(Map<String, Object> map){
		try {
			log.info("开始调用  唐豆兑付--07505 接口");
			BCK07505ReqBean reqBean = new BCK07505ReqBean();
			InReqHeadBean inReqHeadBean = getInReqHeadBean();
			inReqHeadBean.setTradeCode("BCK_07505");//
			reqBean.setHeadBean(inReqHeadBean);
			BCK07505ReqBodyBean bck07505ReqBodyBean = new BCK07505ReqBodyBean();
			BCK07505AcctInfo bck07505AcctInfo = new BCK07505AcctInfo();
			bck07505AcctInfo.setACCT_BAL(String.valueOf(map.get("ACCT_BAL")));
			bck07505AcctInfo.setACCT_NO(String.valueOf(map.get("ACCT_NO")));
			bck07505AcctInfo.setSUB_ACCT_NO(String.valueOf(map.get("SUB_ACCT_NO")));
			bck07505ReqBodyBean.setACCT_INFO(bck07505AcctInfo);
			
			bck07505ReqBodyBean.setACCEPT_DATE(String.valueOf(map.get("ACCEPT_DATE")));
			bck07505ReqBodyBean.setCOUNT(String.valueOf(map.get("COUNT")));
			bck07505ReqBodyBean.setCUSTOM_MANAGER_NAME(String.valueOf(map.get("CUSTOM_MANAGER_NAME")));
			bck07505ReqBodyBean.setCUSTOM_MANAGER_NO(String.valueOf(map.get("CUSTOM_MANAGER_NO")));
			bck07505ReqBodyBean.setDEAL_TYPE(String.valueOf(map.get("DEAL_TYPE")));
			bck07505ReqBodyBean.setDEP_TERM(String.valueOf(map.get("DEP_TERM")));
			bck07505ReqBodyBean.setEXCHANGE_AMT(String.valueOf(map.get("EXCHANGE_AMT")));
			bck07505ReqBodyBean.setEXCHANGE_MODE(String.valueOf(map.get("EXCHANGE_MODE")));
			bck07505ReqBodyBean.setEXCHANGE_PROP(String.valueOf(map.get("EXCHANGE_PROP")));
			bck07505ReqBodyBean.setOPPO_ACCT_NO(String.valueOf(map.get("OPPO_ACCT_NO")));
			bck07505ReqBodyBean.setPRODUCT_CODE(String.valueOf(map.get("PRODUCT_CODE")));
			bck07505ReqBodyBean.setTERM_CODE(String.valueOf(map.get("TERM_CODE")));
			bck07505ReqBodyBean.setTOTAL_BAL(String.valueOf(map.get("TOTAL_BAL")));
			
			reqBean.setBody(bck07505ReqBodyBean);
			
			XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
			xstream.autodetectAnnotations(true);
			xstream.alias("ROOT", BCK07505ReqBean.class);
			xstream.alias("HEAD", InReqHeadBean.class);
			xstream.alias("BODY", BCK07505ReqBodyBean.class);
			String xml = xstream.toXML(reqBean);
			log.info("发送前置报文：\n"+xml);
			//发送前置交易请求 
			String outResMsg = new CDJSocketClient().sendMsg(xml);
			log.info("前置返回报文：\n"+outResMsg);
			map.put(RESULT, outResMsg);
			if(StringUtils.isNotBlank(outResMsg)){
				XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
				reqXs.alias("Root", BCK07505ResBean.class);
				
				BCK07505ResBean bck07505ResBean = (BCK07505ResBean)reqXs.fromXML(outResMsg);
				return bck07505ResBean;
			}
		} catch (Exception e) {
			log.error("调用07505接口异常"+e.getMessage(),e);
		}
		return null;
	}
	/**
	 * 钱柜唐豆兑付--07663
	 * @param map
	 * @return
	 */
	public BCK07663ResBean exchangeMoneyBoxTangDou(Map<String, Object> map){
		try {
			log.info("开始调用  唐豆兑付--07663 接口");
			BCK07663ReqBean reqBean = new BCK07663ReqBean();
			InReqHeadBean inReqHeadBean = getInReqHeadBean();
			inReqHeadBean.setTradeCode("BCK_07663");//
			reqBean.setHeadBean(inReqHeadBean);
			BCK07663ReqBodyBean bck07663ReqBodyBean = new BCK07663ReqBodyBean();
			BCK07663ReqAgentInfBean bck07663AcctInfo = new BCK07663ReqAgentInfBean();
			bck07663AcctInfo.setACCT_BAL(String.valueOf(map.get("ACCT_BAL")));
			bck07663AcctInfo.setACCT_NO(String.valueOf(map.get("ACCT_NO")));
			bck07663AcctInfo.setSUB_ACCT_NO(String.valueOf(map.get("SUB_ACCT_NO")));
			bck07663ReqBodyBean.setACCT_INFO(bck07663AcctInfo);
			
			bck07663ReqBodyBean.setACCEPT_DATE(String.valueOf(map.get("ACCEPT_DATE")));
			bck07663ReqBodyBean.setCOUNT(String.valueOf(map.get("COUNT")));
			bck07663ReqBodyBean.setCUSTOM_MANAGER_NAME(String.valueOf(map.get("CUSTOM_MANAGER_NAME")));
			bck07663ReqBodyBean.setCUSTOM_MANAGER_NO(String.valueOf(map.get("CUSTOM_MANAGER_NO")));
			bck07663ReqBodyBean.setDEAL_TYPE(String.valueOf(map.get("DEAL_TYPE")));
			bck07663ReqBodyBean.setDEP_TERM(String.valueOf(map.get("DEP_TERM")));
			bck07663ReqBodyBean.setEXCHANGE_AMT(String.valueOf(map.get("EXCHANGE_AMT")));
			bck07663ReqBodyBean.setEXCHANGE_MODE(String.valueOf(map.get("EXCHANGE_MODE")));
			bck07663ReqBodyBean.setEXCHANGE_PROP(String.valueOf(map.get("EXCHANGE_PROP")));
			bck07663ReqBodyBean.setOPPO_ACCT_NO(String.valueOf(map.get("OPPO_ACCT_NO")));
			bck07663ReqBodyBean.setPRODUCT_CODE(String.valueOf(map.get("PRODUCT_CODE")));
			bck07663ReqBodyBean.setTERM_CODE(String.valueOf(map.get("TERM_CODE")));
			bck07663ReqBodyBean.setTOTAL_BAL(String.valueOf(map.get("TOTAL_BAL")));
			bck07663ReqBodyBean.setACT_CHNL(String.valueOf(map.get("ACT_CHNL")));
			bck07663ReqBodyBean.setAMT_CHL_FLAG(String.valueOf(map.get("AMT_CHL_FLAG")));
			
			reqBean.setBody(bck07663ReqBodyBean);
			
			XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
			xstream.autodetectAnnotations(true);
			xstream.alias("ROOT", BCK07663ReqBean.class);
			xstream.alias("HEAD", InReqHeadBean.class);
			xstream.alias("BODY", BCK07663ReqBodyBean.class);
			String xml = xstream.toXML(reqBean);
			log.info("发送前置报文：\n"+xml);
			//发送前置交易请求 
			String outResMsg = new CDJSocketClient().sendMsg(xml);
			log.info("前置返回报文：\n"+outResMsg);
			map.put(RESULT, outResMsg);
			if(StringUtils.isNotBlank(outResMsg)){
				XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
				reqXs.alias("Root", BCK07663ResBean.class);
				
				BCK07663ResBean bck07663ResBean = (BCK07663ResBean)reqXs.fromXML(outResMsg);
				return bck07663ResBean;
			}
		} catch (Exception e) {
			log.error("调用07663接口异常"+e.getMessage(),e);
		}
		return null;
	}
	/**
	 * 产品预计利息(24小时)-03510
	 * @param map
	 * @return
	 */
	public BCK03510ResBean searchInteInfo(Map<String, Object> map){
		try {
			log.info("开始调用  产品预计利息(24小时)-03510 接口");
			BCK03510ReqBean reqBean = new BCK03510ReqBean();
			InReqHeadBean inReqHeadBean = getInReqHeadBean();
			inReqHeadBean.setTradeCode("BCK_03510");//
			reqBean.setHeadBean(inReqHeadBean);
			BCK03510ReqBodyBean bck03510ReqBodyBean = new BCK03510ReqBodyBean();
			bck03510ReqBodyBean.setPROD_CODE(map.get("PROD_CODE").toString());//产品代码
			bck03510ReqBodyBean.setAMT(map.get("AMT").toString());//金额
			bck03510ReqBodyBean.setACCT_NO(map.get("ACCT_NO").toString());//账号
			bck03510ReqBodyBean.setSUB_ACCT_NO(map.get("SUB_ACCT_NO").toString());//子账号
			
			String openDate = map.get("OPEN_DATE").toString();//开户日期
			bck03510ReqBodyBean.setOPEN_DATE(openDate);
			Calendar now = Calendar.getInstance();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
			Date date = dateFormat.parse(openDate);
			now.setTime(date);
			
			String monthsUpper=map.get("MonthsUpper").toString().toUpperCase();
			Integer n=Integer.parseInt(monthsUpper.replaceAll("\\D",""));
			if(monthsUpper.indexOf("D")!=-1){
				now.add(Calendar.DATE, n);	//一天
			}else if(monthsUpper.indexOf("M")!=-1){
				now.add(Calendar.MONTH,n);	//一天
			}else if(monthsUpper.indexOf("Y")!=-1){
				now.add(Calendar.YEAR, n);	//一年
			}
			bck03510ReqBodyBean.setDRAW_AMT_DATE(dateFormat.format(now.getTime()));//到期日
			bck03510ReqBodyBean.setCUST_NO(map.get("CUST_NO").toString());//客户号
			Object object = map.get("CHL_ID");
			if(null != object){//该字段不传默认为1，为协议存款，，传3为私行快线
				bck03510ReqBodyBean.setCHL_ID(String.valueOf(object));
			}
			reqBean.setBody(bck03510ReqBodyBean);
			
			XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
			xstream.autodetectAnnotations(true);
			xstream.alias("ROOT", BCK03510ReqBean.class);
			xstream.alias("HEAD", InReqHeadBean.class);
			xstream.alias("BODY", BCK03510ReqBodyBean.class);
			String xml = xstream.toXML(reqBean);
			log.info("发送前置报文：\n"+xml);
			//发送前置交易请求 
			String outResMsg = new CDJSocketClient().sendMsg(xml);
			log.info("前置返回报文：\n"+outResMsg);
			map.put(RESULT, outResMsg);
			if(StringUtils.isNotBlank(outResMsg)){
				XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
				reqXs.alias("Root", BCK03510ResBean.class);
				
				BCK03510ResBean bck03510ResBean = (BCK03510ResBean)reqXs.fromXML(outResMsg);
				if(!"000000".equals(bck03510ResBean.getHeadBean().getResCode()) || "44444".equals(bck03510ResBean.getHeadBean().getResCode())){
					return bck03510ResBean;
				}
				String fileName = bck03510ResBean.getBody().getFILE_NAME();
				FtpFileUtils.downloadFileByFTPClient(Property.FTP_IP_DOWN, Integer.parseInt(Property.FTP_PORT_DOWN), Property.FTP_USER_DOWN, Property.FTP_PWD_DOWN, fileName, Property.FTP_LOCAL_PATH + fileName);
				List<SearchInteInfo> list = TextFileReader.paddingList(Property.FTP_LOCAL_PATH + fileName, SearchInteInfo.class);
				map.put(KEY_PRODUCT_RATES, list);
				
				String awtFileName = bck03510ResBean.getBody().getACT_RWD_FILE_NAME();
				FtpFileUtils.downloadFileByFTPClient(Property.FTP_IP_DOWN, Integer.parseInt(Property.FTP_PORT_DOWN), Property.FTP_USER_DOWN, Property.FTP_PWD_DOWN, awtFileName, Property.FTP_LOCAL_PATH + awtFileName);
				List<QXRateInfosBean> list1 = TextFileReader.paddingList(Property.FTP_LOCAL_PATH + awtFileName, QXRateInfosBean.class);
				map.put("ACT_RWD_FILE_NAME", list1);
				
				return bck03510ResBean;
			}
		} catch (Exception e) {
			log.error("调用03510接口异常"+e.getMessage(),e);
		}
		return null;
	}
	/**
	 * 产品可开立额度信息查询-03511
	 * @param map
	 * @return
	 */
	public BCK03511ResBean searchProducLimit(Map<String, Object> map){
		try {
			log.info("开始调用  产品可开立额度信息查询-03511 接口");
			BCK03511ReqBean reqBean = new BCK03511ReqBean();
			InReqHeadBean inReqHeadBean = getInReqHeadBean();
			inReqHeadBean.setTradeCode("BCK_03511");//
			BCK03511ReqBodyBean bck03511ReqBodyBean = new BCK03511ReqBodyBean();
			bck03511ReqBodyBean.setCUST_NO(map.get("CUST_NO").toString());//客户号
			bck03511ReqBodyBean.setPROD_CODE(map.get("PROD_CODE").toString());
			reqBean.setBody(bck03511ReqBodyBean);
			reqBean.setHeadBean(inReqHeadBean);
			
			XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
			xstream.autodetectAnnotations(true);
			xstream.alias("ROOT", BCK03511ReqBean.class);
			xstream.alias("HEAD", InReqHeadBean.class);
			xstream.alias("BODY", BCK03511ReqBodyBean.class);
			String xml = xstream.toXML(reqBean);
			log.info("发送前置报文：\n"+xml);
			//发送前置交易请求 
			String outResMsg = new CDJSocketClient().sendMsg(xml);
			log.info("前置返回报文：\n"+outResMsg);
			map.put(RESULT, outResMsg);
			if(StringUtils.isNotBlank(outResMsg)){
				XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
				reqXs.alias("Root", BCK03511ResBean.class);
				
				BCK03511ResBean bck03511ResBean = (BCK03511ResBean)reqXs.fromXML(outResMsg);
				return bck03511ResBean;
			}
		} catch (Exception e) {
			log.error("调用03511接口异常"+e.getMessage(),e);
		}
		return null;
	}
	/**
	 * 产品利率信息查询—02864
	 * @param map
	 * @return
	 */
	public BCK02864ResBean searchProducRateInfo(Map<String, Object> map){
		try {
			log.info("开始调用  产品利率信息查询—02864 接口");
			BCK02864ReqBean reqBean = new BCK02864ReqBean();
			InReqHeadBean inReqHeadBean = getInReqHeadBean();
			inReqHeadBean.setTradeCode("BCK_02864");//
			BCK02864ReqBodyBean bck02864ReqBodyBean = new BCK02864ReqBodyBean();
			bck02864ReqBodyBean.setPROD_CODE(map.get("PROD_CODE").toString());//产品编码
			bck02864ReqBodyBean.setRATE_DATE(map.get("RATE_DATE").toString());//起息日期
			reqBean.setBody(bck02864ReqBodyBean);
			reqBean.setHeadBean(inReqHeadBean);
			
			XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
			xstream.autodetectAnnotations(true);
			xstream.alias("ROOT", BCK02864ReqBean.class);
			xstream.alias("HEAD", InReqHeadBean.class);
			xstream.alias("BODY", BCK02864ReqBodyBean.class);
			String xml = xstream.toXML(reqBean);
			log.info("发送前置报文：\n"+xml);
			//发送前置交易请求 
			String outResMsg = new CDJSocketClient().sendMsg(xml);
			log.info("前置返回报文：\n"+outResMsg);
			map.put(RESULT, outResMsg);
			if(StringUtils.isNotBlank(outResMsg)){
				XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
				reqXs.alias("Root", BCK02864ResBean.class);
				
				BCK02864ResBean bck02864ResBean = (BCK02864ResBean)reqXs.fromXML(outResMsg);
				if(!"000000".equals(bck02864ResBean.getHeadBean().getResCode()) || "44444".equals(bck02864ResBean.getHeadBean().getResCode())){
					return bck02864ResBean;
				}
				String fileName = bck02864ResBean.getBody().getFILE_NAME();
				FtpFileUtils.downloadFileByFTPClient(Property.FTP_IP_DOWN, Integer.parseInt(Property.FTP_PORT_DOWN), Property.FTP_USER_DOWN, Property.FTP_PWD_DOWN, fileName, Property.FTP_LOCAL_PATH + fileName);
				List<SearchProducRateInfo> list = TextFileReader.paddingList(Property.FTP_LOCAL_PATH + fileName, SearchProducRateInfo.class);
				map.put(KEY_PRODUCT_RATES, list);
				return bck02864ResBean;
			}
		} catch (Exception e) {
			log.error("调用02864接口异常"+e.getMessage(),e);
		}
		return null;
	}
	/**
	 * 如意存明细查询【55020】--前置03512
	 * @param map
	 * @return
	 */
	public BCK03512ResBean searchRYCDetail(Map<String, Object> map){
		try {
			log.info("开始调用  如意存明细查询--前置03512 接口");
			BCK03512ReqBean reqBean = new BCK03512ReqBean();
			InReqHeadBean inReqHeadBean = getInReqHeadBean();
			inReqHeadBean.setTradeCode("BCK_03512");//
			reqBean.setHeadBean(inReqHeadBean);
			BCK03512ReqBodyBean bck03512ReqBodyBean = new BCK03512ReqBodyBean();
			bck03512ReqBodyBean.setCustNo(map.get("CustNo").toString());//客户号
			reqBean.setBody(bck03512ReqBodyBean);
			
			XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
			xstream.autodetectAnnotations(true);
			xstream.alias("ROOT", BCK03512ReqBean.class);
			xstream.alias("HEAD", InReqHeadBean.class);
			xstream.alias("BODY", BCK03512ReqBodyBean.class);
			String xml = xstream.toXML(reqBean);
			log.info("发送前置报文：\n"+xml);
			//发送前置交易请求 
			String outResMsg = new CDJSocketClient().sendMsg(xml);
			log.info("前置返回报文：\n"+outResMsg);
			map.put(RESULT, outResMsg);
			if(StringUtils.isNotBlank(outResMsg)){
				XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
				reqXs.alias("Root", BCK03512ResBean.class);
				
				BCK03512ResBean bck03512ResBean = (BCK03512ResBean)reqXs.fromXML(outResMsg);
				if(!"000000".equals(bck03512ResBean.getHeadBean().getResCode()) || "44444".equals(bck03512ResBean.getHeadBean().getResCode())){
					return bck03512ResBean;
				}
				String fileName = bck03512ResBean.getBody().getFILE_NAME();
				FtpFileUtils.downloadFileByFTPClient(Property.FTP_IP_DOWN, Integer.parseInt(Property.FTP_PORT_DOWN), Property.FTP_USER_DOWN, Property.FTP_PWD_DOWN, fileName, Property.FTP_LOCAL_PATH + fileName);
				List<SearchRYCDetail> list = TextFileReader.paddingList(Property.FTP_LOCAL_PATH + fileName, SearchRYCDetail.class);
				map.put(KEY_PRODUCT_RATES, list);
				return bck03512ResBean;
			}
		} catch (Exception e) {
			log.error("调用03512接口异常"+e.getMessage(),e);
		}
		return null;
	}
	/**
	 * 个人存款开户【75100】前置【03870】
	 * @param map
	 * @return
	 */
	public BCK03870ResBean openJXCAccount(Map<String, Object> map){
		try {
			log.info("开始调用  个人存款开户 积享存 前置03870 接口");
			BCK03870ReqBean reqBean = new BCK03870ReqBean();
			InReqHeadBean inReqHeadBean = getInReqHeadBean();
			inReqHeadBean.setTradeCode("BCK_03870");//
			reqBean.setHeadBean(inReqHeadBean);
			BCK03870ReqBodyBean bck03870ReqBodyBean = new BCK03870ReqBodyBean();
			bck03870ReqBodyBean.setID_NO(map.get("ID_NO").toString());//证件号码
			bck03870ReqBodyBean.setID_TYPE(map.get("ID_TYPE").toString());// 证件类型
			bck03870ReqBodyBean.setOPPO_ACCT_NO(map.get("OPPO_ACCT_NO").toString());//卡号
			String cardNo = map.get("CARD_NO").toString();
			bck03870ReqBodyBean.setCARD_NO(cardNo);//卡号
			String passWord = map.get("PASSWORD").toString();
			bck03870ReqBodyBean.setPASSWORD(passWord);//密码
			/*if(StringUtils.isNotBlank(passWord)){
				String tranPwd=EncryptUtils.tranPin310("CODM."+GlobalParameter.machineNo+".zpk", Property.encrypt_mac_keyflag, cardNo, passWord);
				account03870ReqBodyBean.setPASSWORD(tranPwd);//密码
			}else{
				account03870ReqBodyBean.setPASSWORD(passWord);//密码
			}*/
			bck03870ReqBodyBean.setCUST_NO(map.get("CUST_NO").toString());//客户号
			bck03870ReqBodyBean.setPRO_CODE(map.get("PRO_CODE").toString());//产品号
			bck03870ReqBodyBean.setDEP_UNIT(map.get("DEP_UNIT").toString());//M Y
			bck03870ReqBodyBean.setDEP_TERM(map.get("DEP_TERM").toString());//存期数字
			bck03870ReqBodyBean.setAUTO_REDP_FLAG(map.get("AUTO_REDP_FLAG").toString());//自动转存标志
			bck03870ReqBodyBean.setLOAD_BAL(map.get("LOAD_BAL").toString());//存款金额
			bck03870ReqBodyBean.setRELA_ACCT_NO(map.get("RELA_ACCT_NO").toString());//关联账号/收益账号
			Object object1 = map.get("SUB_RELA_ACCT_NO");
			if(null != object1){//该字段不传默认为空串，当为电子版时有值
				bck03870ReqBodyBean.setSUB_RELA_ACCT_NO(map.get("SUB_RELA_ACCT_NO").toString());//关联子账号
			}else{
				bck03870ReqBodyBean.setSUB_RELA_ACCT_NO("");//关联子账号
			}
			
			bck03870ReqBodyBean.setTRAN_CHANNEL("08");//渠道号
			bck03870ReqBodyBean.setCURRENCY("00");//币种
			bck03870ReqBodyBean.setHAV_AGENT_FLAG("1");//是否由代理人标志
			bck03870ReqBodyBean.setCAL_MODE("1");//结算方式
			bck03870ReqBodyBean.setCASH_ANALY_NO("0");//现金分析号
			Object object = map.get("CHL_ID");
			if(null != object){//该字段不传默认为1，为协议存款，，传3为私行快线
				bck03870ReqBodyBean.setCHL_ID(String.valueOf(object));
			}
			reqBean.setBody(bck03870ReqBodyBean);
			
			XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
			xstream.autodetectAnnotations(true);
			xstream.alias("ROOT", BCK03870ReqBean.class);
			xstream.alias("HEAD", InReqHeadBean.class);
			xstream.alias("BODY", BCK03870ReqBodyBean.class);
			String xml = xstream.toXML(reqBean);
			log.info("发送前置报文：\n"+xml);
			//发送前置交易请求 
			String outResMsg = new CDJSocketClient().sendMsg(xml);
			log.info("前置返回报文：\n"+outResMsg);
			map.put(RESULT, outResMsg);
			if(StringUtils.isNotBlank(outResMsg)){
				XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
				reqXs.alias("Root", BCK03870ResBean.class);
				
				BCK03870ResBean bck03870ResBean = (BCK03870ResBean)reqXs.fromXML(outResMsg);
				if(StringUtils.isNotBlank(bck03870ResBean.getBody().getFILE_NAME_RET())){
					String fileName = bck03870ResBean.getBody().getFILE_NAME_RET();
					FtpFileUtils.downloadFileByFTPClient(Property.FTP_IP_DOWN, Integer.parseInt(Property.FTP_PORT_DOWN), Property.FTP_USER_DOWN, Property.FTP_PWD_DOWN, fileName, Property.FTP_LOCAL_PATH + fileName);										
						List<OpenJxJInfo> list = TextFileReader.paddingList(Property.FTP_LOCAL_PATH + fileName, OpenJxJInfo.class);
						map.put(KEY_PRODUCT_RATES, list);	

				}
				return bck03870ResBean;
			}
		} catch (Exception e) {
			log.error("调用03870接口异常"+e.getMessage(),e);
		}
		return null;
	}
	
	
	public static void main(String[] args) {
		AccountDepositApi a=new AccountDepositApi();
		Map<String, Object> openAccountMap = new HashMap<String, Object>();
		openAccountMap.put("START_INT_DATE", "20170214");
		openAccountMap.put("ID_TYPE", "1");
		openAccountMap.put("ID_NO", "142730199002052235");
		openAccountMap.put("CUST_NO", "123456789098765432");
		openAccountMap.put("PASSWORD", "派出所");
		openAccountMap.put("UNIT_FLAG", "D");
		openAccountMap.put("DEP_TERM", "1");
		openAccountMap.put("PUT_INT_ACCT", "1234567890987654321");	
		openAccountMap.put("OPPO_ACCT_NO", "6221671111051000129");			
		openAccountMap.put("OPPO_DRAW_COND", "1");
		openAccountMap.put("DRAW_PASSWORD", "111111");
		openAccountMap.put("SETT_TYPE", "1");
		openAccountMap.put("AMT", "100.00");
		openAccountMap.put("NEW_CERT_NO", "0457123");
		openAccountMap.put("EXCH_FLAG", "2");
		openAccountMap.put("PROD_CODE", "0001");
		openAccountMap.put("PUT_INT_ACCT", "121211212121212122");//关联如意存账号
		openAccountMap.put("SUB_ACCT_NO1", "111111111111111111");//关联子账号
		a.openAccount(openAccountMap);
	}
}
