package com.boomhope.tms.transaction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import org.junit.Test;

import com.boomhope.tms.message.XStreamNameCoder;
import com.boomhope.tms.message.in.bck.BCK0004ReqBean;
import com.boomhope.tms.message.out.Out01118ReqBean;
import com.boomhope.tms.message.out.Out01118ReqBodyBean;
import com.boomhope.tms.message.out.Out01596ReqBean;
import com.boomhope.tms.message.out.Out01596ReqBodyBean;
import com.boomhope.tms.message.out.Out01597ReqBean;
import com.boomhope.tms.message.out.Out01597ReqBodyBean;
import com.boomhope.tms.message.out.Out02013ReqBean;
import com.boomhope.tms.message.out.Out02013ReqBodyBean;
import com.boomhope.tms.message.out.Out02026ReqAgentInfBean;
import com.boomhope.tms.message.out.Out02026ReqBean;
import com.boomhope.tms.message.out.Out02026ReqBodyBean;
import com.boomhope.tms.message.out.Out02209ReqBean;
import com.boomhope.tms.message.out.Out02209ReqBodyBean;
import com.boomhope.tms.message.out.Out02210ReqBean;
import com.boomhope.tms.message.out.Out02210ReqBodyBean;
import com.boomhope.tms.message.out.Out02224ReqBean;
import com.boomhope.tms.message.out.Out02224ReqBodyBean;
import com.boomhope.tms.message.out.Out02224ReqBodyDetailBean;
import com.boomhope.tms.message.out.Out02600ReqBean;
import com.boomhope.tms.message.out.Out02600ReqBodyBean;
import com.boomhope.tms.message.out.Out02600ReqBodyDetailBean;
import com.boomhope.tms.message.out.Out02781ReqBean;
import com.boomhope.tms.message.out.Out02781ReqBodyBean;
import com.boomhope.tms.message.out.Out02880ReqBean;
import com.boomhope.tms.message.out.Out02880ReqBodyBean;
import com.boomhope.tms.message.out.Out02882ReqBean;
import com.boomhope.tms.message.out.Out02882ReqBodyBean;
import com.boomhope.tms.message.out.Out02944ReqBean;
import com.boomhope.tms.message.out.Out02944ReqBodyBean;
import com.boomhope.tms.message.out.Out02954ReqBean;
import com.boomhope.tms.message.out.Out02954ReqBodyBean;
import com.boomhope.tms.message.out.Out03448ReqBean;
import com.boomhope.tms.message.out.Out03448ReqBodyBean;
import com.boomhope.tms.message.out.Out03453ReqBean;
import com.boomhope.tms.message.out.Out03453ReqBodyBean;
import com.boomhope.tms.message.out.Out03514ReqBean;
import com.boomhope.tms.message.out.Out03514ReqBodyBean;
import com.boomhope.tms.message.out.Out03517ReqAgentInfBean;
import com.boomhope.tms.message.out.Out03517ReqBean;
import com.boomhope.tms.message.out.Out03517ReqBodyBean;
import com.boomhope.tms.message.out.Out03518ReqBean;
import com.boomhope.tms.message.out.Out03518ReqBodyBean;
import com.boomhope.tms.message.out.Out03518ResBean;
import com.boomhope.tms.message.out.Out03519ReqBean;
import com.boomhope.tms.message.out.Out03519ReqBodyBean;
import com.boomhope.tms.message.out.Out03520ReqBean;
import com.boomhope.tms.message.out.Out03520ReqBodyBean;
import com.boomhope.tms.message.out.Out03521ReqBean;
import com.boomhope.tms.message.out.Out03521ReqBodyBean;
import com.boomhope.tms.message.out.Out03522ReqAgentInfBean;
import com.boomhope.tms.message.out.Out03522ReqBean;
import com.boomhope.tms.message.out.Out03522ReqBodyBean;
import com.boomhope.tms.message.out.Out03740ReqBean;
import com.boomhope.tms.message.out.Out03740ReqBodyBean;
import com.boomhope.tms.message.out.Out07190ReqBean;
import com.boomhope.tms.message.out.Out07190ReqBodyBean;
import com.boomhope.tms.message.out.Out07491ReqBean;
import com.boomhope.tms.message.out.Out07491ReqBodyBean;
import com.boomhope.tms.message.out.Out07492ReqBean;
import com.boomhope.tms.message.out.Out07492ReqBodyBean;
import com.boomhope.tms.message.out.Out07494ReqBean;
import com.boomhope.tms.message.out.Out07494ReqBodyBean;
import com.boomhope.tms.message.out.Out07495ReqBean;
import com.boomhope.tms.message.out.Out07495ReqBodyBean;
import com.boomhope.tms.message.out.Out07498ReqBean;
import com.boomhope.tms.message.out.Out07498ReqBodyBean;
import com.boomhope.tms.message.out.Out07499ReqBean;
import com.boomhope.tms.message.out.Out07499ReqBodyBean;
import com.boomhope.tms.message.out.Out07515ReqBean;
import com.boomhope.tms.message.out.Out07515ReqBodyBean;
import com.boomhope.tms.message.out.Out07602ReqBean;
import com.boomhope.tms.message.out.Out07602ReqBodyBean;
import com.boomhope.tms.message.out.Out07659ReqBean;
import com.boomhope.tms.message.out.Out07659ReqBodyBean;
import com.boomhope.tms.message.out.Out07660ReqBean;
import com.boomhope.tms.message.out.Out07660ReqBodyBean;
import com.boomhope.tms.message.out.Out07819ReqBean;
import com.boomhope.tms.message.out.Out07819ReqBodyBean;
import com.boomhope.tms.message.out.Out08021ReqBean;
import com.boomhope.tms.message.out.Out08021ReqBodyBean;
import com.boomhope.tms.message.out.Out75010ReqBean;
import com.boomhope.tms.message.out.Out75010ReqBodyBean;
import com.boomhope.tms.message.out.Out86022ReqBean;
import com.boomhope.tms.message.out.Out86022ReqBodyBean;
import com.boomhope.tms.message.out.OutCM021ReqBean;
import com.boomhope.tms.message.out.OutCM021ReqBodyBean;
import com.boomhope.tms.message.out.OutHeadBean;
import com.boomhope.tms.util.DateUtil;
import com.boomhope.tms.util.Encrypt;
import com.boomhope.tms.util.EncryptUtils;
import com.boomhope.tms.util.FtpFileUtils;
import com.boomhope.tms.util.MACProtocolUtils;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * 前置接口测试
 * @author zhang.m
 *
 */
public class PreTest {
	
	protected String ENCRYPT_MAC_ZAK2NAME="encrypt.mac.zak2name";
	protected String ENCRYPT_PIN_KEYNAME="encrypt.pin.KeyName";
	protected String ENCRYTP_PIN_TRANKEYFULLNAME="encrypt.pin.tranKeyFullName";
	
	/**
	 * 证件信息查询【86022】-前置 03445
	 * @throws Exception 
	 */
	@Test
	public void Test001() throws Exception{
		
		/* 解析请求报文 */
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		
		//拼接输出的body
		Out86022ReqBodyBean outBody = new Out86022ReqBodyBean();
		outBody.setCUST_NAME("张建钢");//客户姓名
		outBody.setID_TYPE("10100");//证件类型（必输项1-身份证 2-户口本 3-军人证 4-护照   5-旅行通行证 6-港澳台回乡探亲证  7-警官证 8-其他证明）
		outBody.setID_NO("140624199206180015");//证件号码
		//拼接输出的head
		OutHeadBean outHeadBean = new OutHeadBean();
		outHeadBean.setTRAN_CODE("03445");//前置交易码
		outHeadBean.setCARD_ACCP_TERM_ID("000C0011");//机器编码
		outHeadBean.setMER_NO("123456789012345");//商户号
		outHeadBean.setINST_NO("053600150");//机构号
		outHeadBean.setTELLER_NO("C0011");//柜员号
		outHeadBean.setCHANNEL("0035");//渠道号
		outHeadBean.setCHL_TRAN_CODE("");//渠道交易码
		outHeadBean.setSUP_TELLER_NO("");//授权柜员号
		outHeadBean.setTERM_JRNL_NO("");//报文流水号
		outHeadBean.setTRAN_DATE(DateUtil.getDateTime("yyyyMMdd"));//交易日期
		outHeadBean.setTRAN_TIME(DateUtil.getDateTime("HH:mm:ss"));//交易时间
		//拼接root类
		Out86022ReqBean out86022ReqBean = new Out86022ReqBean();
		out86022ReqBean.setBODY(outBody);
		out86022ReqBean.setHeadBean(outHeadBean);
		
		reqXs.alias("ROOT", Out86022ReqBean.class);
		reqXs.alias("HEAD", OutHeadBean.class);
		reqXs.alias("BODY", Out86022ReqBodyBean.class);
		
		System.out.println("*******请求报文：");
		
		String xml = reqXs.toXML(out86022ReqBean);
		xml =  "<?xml version=\"1.0\" encoding=\"GBK\" ?>\n"+xml;
		byte[] bbb = xml.getBytes("GBK");
		String mac = ConfigReader.getConfig(ENCRYPT_MAC_ZAK2NAME);
		byte[] re = MACProtocolUtils.toRequest(mac, bbb);
		
		System.out.println(xml);
		System.out.println("*******返回报文：");

		try {
			Socket socket = new Socket("198.1.246.206", 9104);
			boolean closed = socket.isClosed();
//			System.out.println(closed);
//			System.out.println(socket.isConnected());
//			System.out.println(socket.isBound());
			InputStream inputStream = socket.getInputStream();
			OutputStream outputStream = socket.getOutputStream();
			outputStream.write(re);
			
			byte[] res = MACProtocolUtils.toResponse(inputStream);
			String response = new String(res,"GBK");
			System.out.println(response);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	/**
	 * 账号信息综合查询【02880】-前置07601
	 * @throws Exception
	 */
	@Test
	public void Test002() throws Exception{
		/* 解析请求报文 */
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		
		//拼接输出的body
		Out02880ReqBodyBean outBody = new Out02880ReqBodyBean();
		outBody.setACCT_NO("6231930008000170277-13");//凭证账号
		outBody.setSUB_ACCT_NO("");//子账号
		outBody.setCERT_TYPE("");//凭证种类
		outBody.setCERT_NO_ADD("");//凭证号
		outBody.setCHK_PIN("0");//是否验密：0--否；1--是
		/*凭证密码加密*/
		if(outBody.getCHK_PIN().equals("1")){
			String pinKeyName=ConfigReader.getConfig(ENCRYPT_PIN_KEYNAME);//键盘工作密钥标记
			String acctNo="6231930000000610659";//存单账号
			String pin="123456";//存单密码
			String password=EncryptUtils.encryPin433(pinKeyName,pin,acctNo);
//			System.out.println("+++++++++password:"+password);
			String tranKeyFullName=ConfigReader.getConfig(ENCRYTP_PIN_TRANKEYFULLNAME);//转加密密钥标记
			String tranPwd=EncryptUtils.tranPin310(pinKeyName, tranKeyFullName, acctNo, password);
//			System.out.println("+++++++++tranPwd:"+tranPwd);
			outBody.setPASSWD(tranPwd);//密码(凭密码时必输，无密码不显示此输入项)
		}else{
			outBody.setPASSWD("");//密码(凭密码时必输，无密码不显示此输入项)
		}
		//拼接输出的head
		OutHeadBean outHeadBean = new OutHeadBean();
		outHeadBean.setTRAN_CODE("07601");//前置交易码
		outHeadBean.setCARD_ACCP_TERM_ID("000C0011");//机器编码
		outHeadBean.setMER_NO("123456789012345");//商户号
		outHeadBean.setINST_NO("053600150");//机构号
		outHeadBean.setTELLER_NO("C0011");//柜员号
		outHeadBean.setCHANNEL("0035");//渠道号
		outHeadBean.setCHL_TRAN_CODE("");//渠道交易码
		outHeadBean.setSUP_TELLER_NO("");//授权柜员号
		outHeadBean.setTERM_JRNL_NO("");//报文流水号
		outHeadBean.setTRAN_DATE(DateUtil.getDateTime("yyyyMMdd"));//交易日期
		outHeadBean.setTRAN_TIME(DateUtil.getDateTime("HH:mm:ss"));//交易时间
		
		//拼接root类
		Out02880ReqBean out02880ReqBean = new Out02880ReqBean();
		out02880ReqBean.setBODY(outBody);
		out02880ReqBean.setHeadBean(outHeadBean);
		
		reqXs.alias("ROOT", Out02880ReqBean.class);
		reqXs.alias("HEAD", OutHeadBean.class);
		reqXs.alias("BODY", Out02880ReqBodyBean.class);
		
		System.out.println("*******请求报文：");
		String xml = reqXs.toXML(out02880ReqBean);
		xml =  "<?xml version=\"1.0\" encoding=\"GBK\" ?>\n"+xml;
		byte[] bbb = xml.getBytes("GBK");
		String mac = ConfigReader.getConfig(ENCRYPT_MAC_ZAK2NAME);
		
		byte[] re = MACProtocolUtils.toRequest(mac, bbb);
		
		System.out.println(xml);
		System.out.println("*******返回报文：");
		try {
			Socket socket = new Socket("198.1.246.206", 9104);
			boolean closed = socket.isClosed();
//			System.out.println(closed);
//			System.out.println(socket.isConnected());
//			System.out.println(socket.isBound());
			InputStream inputStream = socket.getInputStream();
			OutputStream outputStream = socket.getOutputStream();
			outputStream.write(re);
			
			byte[] res = MACProtocolUtils.toResponse(inputStream);
			String response = new String(res,"GBK");
			System.out.println(response);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	/**
	 * 普通定期存单预计利息【02944】-前置07696
	 * @throws Exception
	 */
	@Test
	public void Test003() throws Exception{
		/* 解析请求报文 */
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		
		//拼接输出的body
		Out02944ReqBodyBean outBody = new Out02944ReqBodyBean();
		outBody.setACCT_NO("6231931300000000011-24");//账号-子账号
		
		//拼接输出的head
		OutHeadBean outHeadBean = new OutHeadBean();
		outHeadBean.setTRAN_CODE("07696");//前置交易码
		outHeadBean.setCARD_ACCP_TERM_ID("000C0036");//机器编码
		outHeadBean.setMER_NO("123456789012345");//商户号
		outHeadBean.setINST_NO("053600150");//机构号
		outHeadBean.setTELLER_NO("C0036");//柜员号
		outHeadBean.setCHANNEL("0035");//渠道号
		outHeadBean.setCHL_TRAN_CODE("");//渠道交易码
		outHeadBean.setSUP_TELLER_NO("");//授权柜员号
		outHeadBean.setTERM_JRNL_NO("");//报文流水号
		outHeadBean.setTRAN_DATE(DateUtil.getDateTime("yyyyMMdd"));//交易日期
		outHeadBean.setTRAN_TIME(DateUtil.getDateTime("HH:mm:ss"));//交易时间
		
		//拼接root类
		Out02944ReqBean out02944ReqBean = new Out02944ReqBean();
		out02944ReqBean.setBODY(outBody);
		out02944ReqBean.setHeadBean(outHeadBean);
		
		reqXs.alias("ROOT", Out02944ReqBean.class);
		reqXs.alias("HEAD", OutHeadBean.class);
		reqXs.alias("BODY", Out02944ReqBodyBean.class);
		
		System.out.println("*******请求报文：");
		String xml = reqXs.toXML(out02944ReqBean);
		xml =  "<?xml version=\"1.0\" encoding=\"GBK\" ?>\n"+xml;
		byte[] bbb = xml.getBytes("GBK");
		String mac = ConfigReader.getConfig(ENCRYPT_MAC_ZAK2NAME);
		
		byte[] re = MACProtocolUtils.toRequest(mac, bbb);
		
		System.out.println(xml);
		System.out.println("*******返回报文：");
		try {
			Socket socket = new Socket("198.1.246.247", 9104);
			boolean closed = socket.isClosed();
//			System.out.println(closed);
//			System.out.println(socket.isConnected());
//			System.out.println(socket.isBound());
			InputStream inputStream = socket.getInputStream();
			OutputStream outputStream = socket.getOutputStream();
			outputStream.write(re);
			
			byte[] res = MACProtocolUtils.toResponse(inputStream);
			String response = new String(res,"GBK");
			System.out.println(response);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	
	
	/**
	 * 个人账户销户【02026】-前置07624
	 * @throws Exception
	 */
//	@Test
	public void Test004() throws Exception{
		/* 解析请求报文 */
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		
		//拼接输出的body
		Out02026ReqBodyBean outBody = new Out02026ReqBodyBean();
		BCK0004ReqBean inReqBean = new BCK0004ReqBean();
		outBody.setACCT_NO("052000125000600035599");//账号
		outBody.setCERT_NO("03754072");//凭证号
		outBody.setCUST_NO("113207805");//客户号 
		outBody.setCUST_NAME("");//客户名称
		outBody.setDRAW_COND("1");//支付条件(0-无;1-凭密码;2-凭证件;3-凭印鉴;4_凭印鉴和密码;3、4需要电子验印)
		/*凭证密码加密*/
		if("1".equals(outBody.getDRAW_COND())){
			String pinKeyName=ConfigReader.getConfig(ENCRYPT_PIN_KEYNAME);//键盘工作密钥标记
			String acctNo="052000125000600035599";//存单账号
			String pin="123456";//存单密码
			String password=EncryptUtils.encryPin433(pinKeyName,pin,acctNo);
//			System.out.println("+++++++++password:"+password);
			String tranKeyFullName=ConfigReader.getConfig(ENCRYTP_PIN_TRANKEYFULLNAME);//转加密密钥标记
			String tranPwd=EncryptUtils.tranPin310(pinKeyName, tranKeyFullName, acctNo, password);
//			System.out.println("+++++++++tranPwd:"+tranPwd);
			outBody.setPASSWORD(tranPwd);//密码(凭密码时必输，无密码不显示此输入项)
		}else{
			outBody.setPASSWORD("");//密码(凭密码时必输，无密码不显示此输入项)
		}
		outBody.setCURRENCY("CNY");//货币号
		outBody.setPROD_CODE("");//产品代码
		outBody.setPROD_NAME("");//产品名称
		outBody.setBALANCE("300000.00");//余额
		outBody.setDEP_TERM("");//存期
		outBody.setSTART_INT_DATE("");//起息日
		outBody.setOPEN_RATE("");//开户利率
		outBody.setCYC_AMT("");//周期金额
		outBody.setCYC("");//周期
		outBody.setTIMES("");//次数
		outBody.setBES_AMT("");//预约金额
		outBody.setBES_DATE("");//预约日期
		outBody.setDRAW_CURRENCY("00");//支取币种
		outBody.setPRI_INTE_FLAG("0");//本息分开（必输，0否、1是）
		outBody.setCANCEL_AMT("300000.00");//销户金额
		outBody.setPRI_INTE_WAY("1");//本息走向
		outBody.setOPPO_ACCT_NO("6231930000000902361");//对方账号（本息转账，有）
		outBody.setSUB_ACCT_NO("0");//子账号（必输项，对方账号为一本通使显示，默认为0基本账户可修改）
		outBody.setPRI_WAY("");//本金走向
		outBody.setOPPO_ACCT_NO1("");//对方账号（本金转账，本金、利息分开时，对方账户不允许为同一账户）
		outBody.setSUB_ACCT_NO1("0");//子账号（必输项，对方账号为一本通使显示，默认为0基本账户可修改）
		outBody.setINTE_WAY("");//利息走向
		outBody.setOPPO_ACCT_NO2("");//对方账号（利息转账，有）
		outBody.setSUB_ACCT_NO2("0");//子账号（必输项，对方账号为一本通使显示，默认为0基本账户可修改）
		outBody.setID_TYPE("");//证件类型
		outBody.setID_NO("13028119871002204X");//证件号码
		outBody.setPROVE_FLAG("");//证明标志
		outBody.setCASH_ANALY_NO("");//现金分析号
		outBody.setHAV_AGENT_FLAG("0");//是否有代理人标志
		if(outBody.getHAV_AGENT_FLAG().equals("1")){
			//拼接输出的AGENT_INF
			Out02026ReqAgentInfBean outAgentInf = new Out02026ReqAgentInfBean();
			outAgentInf.setCUST_NAME("张蒙");//客户姓名
			outAgentInf.setDUE_DATE("");//到期日期
			outAgentInf.setID_NO("130682199109264075");//证件号码
			outAgentInf.setID_TYPE("1");//证件类别
			outAgentInf.setISSUE_DATE("");//签发日期
			outAgentInf.setISSUE_INST("定州市公安局");//签发机关
			outAgentInf.setJ_C_ADD("");//经常居住地
			outAgentInf.setMOB_PHONE("");//移动电话
			outAgentInf.setNATION("");//国籍
			outAgentInf.setOCCUPATION("公务员");//职业
			outAgentInf.setREGIS_PER_RES("河北省定州市周村镇前屯村67号");//户口所在地
			outAgentInf.setSEX("男");//性别
			outAgentInf.setTELEPHONE("");//固定电话
			
			outBody.setAGENT_INF(outAgentInf);
		}
		//拼接输出的head
		OutHeadBean outHeadBean = new OutHeadBean();
		outHeadBean.setTRAN_CODE("07624");//前置交易码
		outHeadBean.setCARD_ACCP_TERM_ID("000C0011");//机器编码
		outHeadBean.setMER_NO("123456789012345");//商户号
		outHeadBean.setINST_NO("051000129");//机构号
		outHeadBean.setTELLER_NO("C0011");//柜员号
		outHeadBean.setCHANNEL("0035");//渠道号
		outHeadBean.setCHL_TRAN_CODE("");//渠道交易码
		outHeadBean.setSUP_TELLER_NO("");//授权柜员号
		outHeadBean.setTERM_JRNL_NO("");//报文流水号
		outHeadBean.setTRAN_DATE(DateUtil.getDateTime("yyyyMMdd"));//交易日期
		outHeadBean.setTRAN_TIME(DateUtil.getDateTime("HH:mm:ss"));//交易时间
		
		//拼接root类
		Out02026ReqBean out02026ReqBean = new Out02026ReqBean();
		out02026ReqBean.setBODY(outBody);
		out02026ReqBean.setHeadBean(outHeadBean);
		
		reqXs.alias("ROOT", Out02026ReqBean.class);
		reqXs.alias("HEAD", OutHeadBean.class);
		reqXs.alias("BODY", Out02026ReqBodyBean.class);
		
		System.out.println("*******请求报文：");
		String xml = reqXs.toXML(out02026ReqBean);
		xml =  "<?xml version=\"1.0\" encoding=\"GBK\" ?>\n"+xml;
		byte[] bbb = xml.getBytes("GBK");
		String mac = ConfigReader.getConfig(ENCRYPT_MAC_ZAK2NAME);
		byte[] re = MACProtocolUtils.toRequest(mac, bbb);
		
		System.out.println(xml);
		System.out.println("*******返回报文：");
		try {
			Socket socket = new Socket("198.1.246.206", 9104);
			boolean closed = socket.isClosed();
//			System.out.println(closed);
//			System.out.println(socket.isConnected());
//			System.out.println(socket.isBound());
			InputStream inputStream = socket.getInputStream();
			OutputStream outputStream = socket.getOutputStream();
			outputStream.write(re);
			
			byte[] res = MACProtocolUtils.toResponse(inputStream);
			String response = new String(res,"GBK");
			System.out.println(response);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	/**
	 * 唐豆收回查询【02210】-前置07622
	 * @throws Exception
	 */
//	@Test
	public void Test005() throws Exception{
		/* 解析请求报文 */
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		
		//拼接输出的body
		Out02210ReqBodyBean outBody = new Out02210ReqBodyBean();
		outBody.setACCT_NO("051000129001001199005");//账号
		outBody.setSUB_ACCT_NO("");//子账号（一本通时必输）
		outBody.setPAY_DATE(DateUtil.getDateTime("yyyyMMdd"));//日期
		outBody.setPAY_JRNL("2970");//流水号
		
		//拼接输出的head
		OutHeadBean outHeadBean = new OutHeadBean();
		outHeadBean.setTRAN_CODE("07622");//前置交易码
		outHeadBean.setCARD_ACCP_TERM_ID("000C0011");//机器编码
		outHeadBean.setMER_NO("123456789012345");//商户号
		outHeadBean.setINST_NO("051000129");//机构号
		outHeadBean.setTELLER_NO("C0011");//柜员号
		outHeadBean.setCHANNEL("0035");//渠道号
		outHeadBean.setCHL_TRAN_CODE("");//渠道交易码
		outHeadBean.setSUP_TELLER_NO("");//授权柜员号
		outHeadBean.setTERM_JRNL_NO("");//报文流水号
		outHeadBean.setTRAN_DATE(DateUtil.getDateTime("yyyyMMdd"));//交易日期
		outHeadBean.setTRAN_TIME(DateUtil.getDateTime("HH:mm:ss"));//交易时间
		
		//拼接root类
		Out02210ReqBean out02210ReqBean = new Out02210ReqBean();
		out02210ReqBean.setBODY(outBody);
		out02210ReqBean.setHeadBean(outHeadBean);
		
		reqXs.alias("ROOT", Out02210ReqBean.class);
		reqXs.alias("HEAD", OutHeadBean.class);
		reqXs.alias("BODY", Out02210ReqBodyBean.class);
		
		System.out.println("*******请求报文：");
		String xml = reqXs.toXML(out02210ReqBean);
		xml =  "<?xml version=\"1.0\" encoding=\"GBK\" ?>\n"+xml;
		byte[] bbb = xml.getBytes("GBK");
		String mac = ConfigReader.getConfig(ENCRYPT_MAC_ZAK2NAME);//MAC密钥标记
		
		byte[] re = MACProtocolUtils.toRequest(mac, bbb);
		
		System.out.println(xml);
		System.out.println("*******返回报文：");
		try {
			Socket socket = new Socket("198.1.246.206", 9104);
			boolean closed = socket.isClosed();
//			System.out.println(closed);
//			System.out.println(socket.isConnected());
//			System.out.println(socket.isBound());
			InputStream inputStream = socket.getInputStream();
			OutputStream outputStream = socket.getOutputStream();
			outputStream.write(re);
			
			byte[] res = MACProtocolUtils.toResponse(inputStream);
			String response = new String(res,"GBK");
			System.out.println(response);
		}catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	/**
	 * 唐豆收回【02209】-前置07665
	 * @throws Exception
	 */
//	@Test
	public void Test006() throws Exception{
		/* 解析请求报文 */
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		
		//拼接输出的body
		Out02209ReqBodyBean outBody = new Out02209ReqBodyBean();
		outBody.setACCT_NO("051000129001001199005");//账号
		outBody.setBACK_COUNT("47500");//收回唐豆数量
		outBody.setBACK_EXCHANGE_AMT("95");//收回兑现金额
		outBody.setBACK_PROP("");//收回比例
		outBody.setOPPO_ACCT_NO("");//对方账号
		outBody.setORG_EXCHANGE_MODE("");//原唐豆兑换方式(0-现金，1-转账，2-兑物)
		outBody.setORG_EXCHANGE_PROP("");//原唐豆兑现比例
		outBody.setPASSWORD("");//对方账号密码
		outBody.setPAY_AMT("20000.00");//支取金额
		outBody.setPAY_DATE(DateUtil.getDateTime("yyyyMMdd"));//支取日期
		outBody.setPAY_JRNL("2970");//支取流水
		outBody.setRECOV_TYPE("0");//唐豆收回方式(0-现金 1-转账 2-兑物)
		outBody.setSUB_ACCT_NO("");//子账号
		
		//拼接输出的head
		OutHeadBean outHeadBean = new OutHeadBean();
		outHeadBean.setTRAN_CODE("07665");//前置交易码
		outHeadBean.setCARD_ACCP_TERM_ID("000C0011");//机器编码
		outHeadBean.setMER_NO("123456789012345");//商户号
		outHeadBean.setINST_NO("051000129");//机构号
		outHeadBean.setTELLER_NO("C0011");//柜员号
		outHeadBean.setCHANNEL("0035");//渠道号
		outHeadBean.setCHL_TRAN_CODE("");//渠道交易码
		outHeadBean.setSUP_TELLER_NO("");//授权柜员号
		outHeadBean.setTERM_JRNL_NO("");//报文流水号
		outHeadBean.setTRAN_DATE(DateUtil.getDateTime("yyyyMMdd"));//交易日期
		outHeadBean.setTRAN_TIME(DateUtil.getDateTime("HH:mm:ss"));//交易时间
		
		//拼接root类
		Out02209ReqBean out02209ReqBean = new Out02209ReqBean();
		out02209ReqBean.setBODY(outBody);
		out02209ReqBean.setHeadBean(outHeadBean);
		
		reqXs.alias("ROOT", Out02209ReqBean.class);
		reqXs.alias("HEAD", OutHeadBean.class);
		reqXs.alias("BODY", Out02209ReqBodyBean.class);
		
		System.out.println("*******请求报文：");
		String xml = reqXs.toXML(out02209ReqBean);
		xml =  "<?xml version=\"1.0\" encoding=\"GBK\" ?>\n"+xml;
		byte[] bbb = xml.getBytes("GBK");
		String mac = ConfigReader.getConfig(ENCRYPT_MAC_ZAK2NAME);//MAC密钥标记
		
		byte[] re = MACProtocolUtils.toRequest(mac, bbb);
		
		System.out.println(xml);
		System.out.println("*******返回报文：");
		try {
			Socket socket = new Socket("198.1.246.206", 9104);
			boolean closed = socket.isClosed();
//			System.out.println(closed);
//			System.out.println(socket.isConnected());
//			System.out.println(socket.isBound());
			InputStream inputStream = socket.getInputStream();
			OutputStream outputStream = socket.getOutputStream();
			outputStream.write(re);
			
			byte[] res = MACProtocolUtils.toResponse(inputStream);
			String response = new String(res,"GBK");
			System.out.println(response);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	/**
	 * 卡信息查询【75010】-前置03845
	 * @throws Exception
	 */
	@Test
	public void Test007() throws Exception{
		/* 解析请求报文 */
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		
		//拼接输出的body
		Out75010ReqBodyBean outBody = new Out75010ReqBodyBean();
		outBody.setCARD_NO("6231930000002000156");//卡号
		outBody.setISPIN("0");//是否验密：0--否；1--是
		outBody.setPASSWD("");//密码
		outBody.setSUB_ACCT_NO("");//子账号
		
		//拼接输出的head
		OutHeadBean outHeadBean = new OutHeadBean();
		outHeadBean.setTRAN_CODE("03845");//前置交易码
		outHeadBean.setCARD_ACCP_TERM_ID("000C0011");//机器编码
		outHeadBean.setMER_NO("123456789012345");//商户号
		outHeadBean.setINST_NO("053600150");//机构号
		outHeadBean.setTELLER_NO("C0011");//柜员号
		outHeadBean.setCHANNEL("0035");//渠道号
		outHeadBean.setCHL_TRAN_CODE("");//渠道交易码
		outHeadBean.setSUP_TELLER_NO("");//授权柜员号
		outHeadBean.setTERM_JRNL_NO("");//报文流水号
		outHeadBean.setTRAN_DATE(DateUtil.getDateTime("yyyyMMdd"));//交易日期
		outHeadBean.setTRAN_TIME(DateUtil.getDateTime("HH:mm:ss"));//交易时间
		
		//拼接root类
		Out75010ReqBean out75010ReqBean = new Out75010ReqBean();
		out75010ReqBean.setBODY(outBody);
		out75010ReqBean.setHeadBean(outHeadBean);
		
		reqXs.alias("ROOT", Out75010ReqBean.class);
		reqXs.alias("HEAD", OutHeadBean.class);
		reqXs.alias("BODY", Out75010ReqBodyBean.class);
		
		System.out.println("*******请求报文：");
		String xml = reqXs.toXML(out75010ReqBean);
		xml =  "<?xml version=\"1.0\" encoding=\"GBK\" ?>\n"+xml;
		byte[] bbb = xml.getBytes("GBK");
		String mac = ConfigReader.getConfig(ENCRYPT_MAC_ZAK2NAME);//MAC密钥标记
		
		byte[] re = MACProtocolUtils.toRequest(mac, bbb);
		String ss  = new String(re,"GBK");
		System.out.println("请求、、、、"+ss);
		System.out.println("*******返回报文：");
		try {
			Socket socket = new Socket("198.1.246.206", 9104);
			boolean closed = socket.isClosed();
//			System.out.println(closed);
//			System.out.println(socket.isConnected());
//			System.out.println(socket.isBound());
			InputStream inputStream = socket.getInputStream();
			OutputStream outputStream = socket.getOutputStream();
			outputStream.write(re);
			
			byte[] res = MACProtocolUtils.toResponse(inputStream);
			String response = new String(res,"GBK");
			System.out.println(response);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	/**
	 * 身份核查【前置交易码-03448】
	 * @throws Exception
	 */
//	@Test
	public void Test008() throws Exception{
		/* 解析请求报文 */
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		
		//拼接输出的body
		Out03448ReqBodyBean outBody = new Out03448ReqBodyBean();
		outBody.setID("130682199109264075");//证件号码
		outBody.setNAME("张蒙");//姓名
		
		//拼接输出的head
		OutHeadBean outHeadBean = new OutHeadBean();
		outHeadBean.setTRAN_CODE("03448");//前置交易码
		outHeadBean.setCARD_ACCP_TERM_ID("000C0001");//机器编码
		outHeadBean.setMER_NO("123456789012345");//商户号
		outHeadBean.setINST_NO("051000129");//机构号
		outHeadBean.setTELLER_NO("C0010");//柜员号
		outHeadBean.setCHANNEL("0035");//渠道号
		outHeadBean.setCHL_TRAN_CODE("");//渠道交易码
		outHeadBean.setSUP_TELLER_NO("");//授权柜员号
		outHeadBean.setTERM_JRNL_NO("");//报文流水号
		outHeadBean.setTRAN_DATE(DateUtil.getDateTime("yyyyMMdd"));//交易日期
		outHeadBean.setTRAN_TIME(DateUtil.getDateTime("HH:mm:ss"));//交易时间
		
		//拼接root类
		Out03448ReqBean out03448ReqBean = new Out03448ReqBean();
		out03448ReqBean.setBODY(outBody);
		out03448ReqBean.setHeadBean(outHeadBean);
		
		reqXs.alias("ROOT", Out03448ReqBean.class);
		reqXs.alias("HEAD", OutHeadBean.class);
		reqXs.alias("BODY", Out03448ReqBodyBean.class);
		
		System.out.println("*******请求报文：");
		String xml = reqXs.toXML(out03448ReqBean);
		xml =  "<?xml version=\"1.0\" encoding=\"GBK\" ?>\n"+xml;
		byte[] bbb = xml.getBytes("GBK");
		String mac = ConfigReader.getConfig(ENCRYPT_MAC_ZAK2NAME);//MAC密钥标记
		
		byte[] re = MACProtocolUtils.toRequest(mac, bbb);
		
		System.out.println(xml);
		System.out.println("*******返回报文：");
		try {
			Socket socket = new Socket("198.1.246.206", 9104);
			boolean closed = socket.isClosed();
//			System.out.println(closed);
//			System.out.println(socket.isConnected());
//			System.out.println(socket.isBound());
			InputStream inputStream = socket.getInputStream();
			OutputStream outputStream = socket.getOutputStream();
			outputStream.write(re);
			
			byte[] res = MACProtocolUtils.toResponse(inputStream);
			String response = new String(res,"GBK");
			System.out.println(response);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	/**
	 * 柜员认证方式查询—前置【07659】
	 * @throws Exception
	 */
//	@Test
	public void Test009() throws Exception{
		/* 解析请求报文 */
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		
		//拼接输出的body
		Out07659ReqBodyBean outBody = new Out07659ReqBodyBean();
		outBody.setQRY_TELLER_NO("A0043");//查询柜员号
		
		//拼接输出的head
		OutHeadBean outHeadBean = new OutHeadBean();
		outHeadBean.setTRAN_CODE("07659");//前置交易码
		outHeadBean.setCARD_ACCP_TERM_ID("000C0011");//机器编码
		outHeadBean.setMER_NO("123456789012345");//商户号
		outHeadBean.setINST_NO("051000129");//机构号
		outHeadBean.setTELLER_NO("C0011");//柜员号
		outHeadBean.setCHANNEL("0035");//渠道号
		outHeadBean.setCHL_TRAN_CODE("");//渠道交易码
		outHeadBean.setSUP_TELLER_NO("");//授权柜员号
		outHeadBean.setTERM_JRNL_NO("");//报文流水号
		outHeadBean.setTRAN_DATE(DateUtil.getDateTime("yyyyMMdd"));//交易日期
		outHeadBean.setTRAN_TIME(DateUtil.getDateTime("HH:mm:ss"));//交易时间
		
		//拼接root类
		Out07659ReqBean out07659ReqBean = new Out07659ReqBean();
		out07659ReqBean.setBODY(outBody);
		out07659ReqBean.setHeadBean(outHeadBean);
		
		reqXs.alias("ROOT", Out07659ReqBean.class);
		reqXs.alias("HEAD", OutHeadBean.class);
		reqXs.alias("BODY", Out07659ReqBodyBean.class);
		
		System.out.println("*******请求报文：");
		String xml = reqXs.toXML(out07659ReqBean);
		xml =  "<?xml version=\"1.0\" encoding=\"GBK\" ?>\n"+xml;
		byte[] bbb = xml.getBytes("GBK");
		String mac = ConfigReader.getConfig(ENCRYPT_MAC_ZAK2NAME);//MAC密钥标记
		
		byte[] re = MACProtocolUtils.toRequest(mac, bbb);
		
		System.out.println(xml);
		System.out.println("*******返回报文：");
		try {
			Socket socket = new Socket("198.1.246.206", 9104);
			boolean closed = socket.isClosed();
//			System.out.println(closed);
//			System.out.println(socket.isConnected());
//			System.out.println(socket.isBound());
			InputStream inputStream = socket.getInputStream();
			OutputStream outputStream = socket.getOutputStream();
			outputStream.write(re);
			
			byte[] res = MACProtocolUtils.toResponse(inputStream);
			String response = new String(res,"GBK");
			System.out.println(response);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	/**
	 * 柜员授权—前置【07660】
	 * @throws Exception
	 */
//	@Test
	public void Test010() throws Exception{
		/* 解析请求报文 */
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		
		//拼接输出的body
		Out07660ReqBodyBean outBody = new Out07660ReqBodyBean();
		outBody.setSUP_TELLER_NO("A0043");//授权柜员号
		outBody.setFIN_PRI_LEN("");//指纹长度
		outBody.setFIN_PRI_VAL("");//指纹值
		
		/*对授权柜员输入的密码进行加密处理*/
		String tranPwd = "a1111111";
		String subTellerPwd = Encrypt.getPinValue(tranPwd);
		System.out.println("********授权加密密码："+subTellerPwd);
		outBody.setSUP_TELLER_PWD(subTellerPwd);//授权密码
//		outBody.setSUP_TELLER_PWD("");//无授权密码
		//拼接输出的head
		OutHeadBean outHeadBean = new OutHeadBean();
		outHeadBean.setTRAN_CODE("07660");//前置交易码
		outHeadBean.setCARD_ACCP_TERM_ID("000C0011");//机器编码
		outHeadBean.setMER_NO("123456789012345");//商户号
		outHeadBean.setINST_NO("051000129");//机构号
		outHeadBean.setTELLER_NO("C0011");//柜员号
		outHeadBean.setCHANNEL("0035");//渠道号
		outHeadBean.setCHL_TRAN_CODE("");//渠道交易码
		outHeadBean.setSUP_TELLER_NO("A0043");//授权柜员号
		outHeadBean.setTERM_JRNL_NO("");//报文流水号
		outHeadBean.setTRAN_DATE(DateUtil.getDateTime("yyyyMMdd"));//交易日期
		outHeadBean.setTRAN_TIME(DateUtil.getDateTime("HH:mm:ss"));//交易时间
		
		//拼接root类
		Out07660ReqBean out07660ReqBean = new Out07660ReqBean();
		out07660ReqBean.setBODY(outBody);
		out07660ReqBean.setHeadBean(outHeadBean);
		
		reqXs.alias("ROOT", Out07660ReqBean.class);
		reqXs.alias("HEAD", OutHeadBean.class);
		reqXs.alias("BODY", Out07660ReqBodyBean.class);
		
		System.out.println("*******请求报文：");
		String xml = reqXs.toXML(out07660ReqBean);
		xml =  "<?xml version=\"1.0\" encoding=\"GBK\" ?>\n"+xml;
		byte[] bbb = xml.getBytes("GBK");
		String mac = ConfigReader.getConfig(ENCRYPT_MAC_ZAK2NAME);//MAC密钥标记
		byte[] re = MACProtocolUtils.toRequest(mac, bbb);
		System.out.println(xml);
		System.out.println("*******返回报文：");
		try {
			Socket socket = new Socket("198.1.246.206", 9104);
			boolean closed = socket.isClosed();
//			System.out.println(closed);
//			System.out.println(socket.isConnected());
//			System.out.println(socket.isBound());
			InputStream inputStream = socket.getInputStream();
			OutputStream outputStream = socket.getOutputStream();
			outputStream.write(re);
			
			byte[] res = MACProtocolUtils.toResponse(inputStream);
			String response = new String(res,"GBK");
			System.out.println(response);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	/**
	 * 代理人身份信息核对 【前置-08021】
	 * @throws Exception
	 */
//	@Test
	public void Test011() throws Exception{
		/* 解析请求报文 */
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		
		//拼接输出的body
		Out08021ReqBodyBean outBody = new Out08021ReqBodyBean();
		outBody.setID_NO("130682199109264075");//证件号码
		outBody.setNAME("张蒙");//姓名
		
		//拼接输出的head
		OutHeadBean outHeadBean = new OutHeadBean();
		outHeadBean.setTRAN_CODE("08021");//前置交易码
		outHeadBean.setCARD_ACCP_TERM_ID("000C0011");//机器编码
		outHeadBean.setMER_NO("123456789012345");//商户号
		outHeadBean.setINST_NO("051000129");//机构号
		outHeadBean.setTELLER_NO("C0011");//柜员号
		outHeadBean.setCHANNEL("0035");//渠道号
		outHeadBean.setCHL_TRAN_CODE("");//渠道交易码
		outHeadBean.setSUP_TELLER_NO("");//授权柜员号
		outHeadBean.setTERM_JRNL_NO("");//报文流水号
		outHeadBean.setTRAN_DATE(DateUtil.getDateTime("yyyyMMdd"));//交易日期
		outHeadBean.setTRAN_TIME(DateUtil.getDateTime("HH:mm:ss"));//交易时间
		
		//拼接root类
		Out08021ReqBean out08021ReqBean = new Out08021ReqBean();
		out08021ReqBean.setBODY(outBody);
		out08021ReqBean.setHeadBean(outHeadBean);
		
		reqXs.alias("ROOT", Out08021ReqBean.class);
		reqXs.alias("HEAD", OutHeadBean.class);
		reqXs.alias("BODY", Out08021ReqBodyBean.class);
		
		System.out.println("*******请求报文：");
		String xml = reqXs.toXML(out08021ReqBean);
		xml =  "<?xml version=\"1.0\" encoding=\"GBK\" ?>\n"+xml;
		byte[] bbb = xml.getBytes("GBK");
		String mac = ConfigReader.getConfig(ENCRYPT_MAC_ZAK2NAME);//MAC密钥标记
		
		byte[] re = MACProtocolUtils.toRequest(mac, bbb);
		
		System.out.println(xml);
		System.out.println("*******返回报文：");
		try {
			Socket socket = new Socket("198.1.246.206", 9104);
			boolean closed = socket.isClosed();
//			System.out.println(closed);
//			System.out.println(socket.isConnected());
//			System.out.println(socket.isBound());
			InputStream inputStream = socket.getInputStream();
			OutputStream outputStream = socket.getOutputStream();
			outputStream.write(re);
			
			byte[] res = MACProtocolUtils.toResponse(inputStream);
			String response = new String(res,"GBK");
			System.out.println(response);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	/**
	 * 获取工作密钥
	 * @throws Exception
	 */
//	@Test
	public void Test012() throws Exception{
		String mainKeyName="CODM.000C0011.zmk";
		String workKeyName="CODM.000C0011.zpk";
		String[] items = EncryptUtils.fetchNewWorkKey(mainKeyName, workKeyName);
		System.out.println("key:"+items[0]);
		System.out.println("code:"+items[1]);
	}
	
	
	
	
	/**
	 * pin密码键盘加密
	 * @throws Exception
	 */
	@Test
	public void Test013() throws Exception{
		String pinKeyName="CODM.000C0002.zpk";//键盘工作密钥标记
		String acctNo="052000125001000346731";
		String pin="258258";
		String password=EncryptUtils.encryPin433(pinKeyName,pin,acctNo);
		System.out.println("pin加密:"+password);
		String tranKeyFullName="ZZQZ.00000001.zpk";//转加密密钥标记
		String tranPwd=EncryptUtils.tranPin310(pinKeyName, tranKeyFullName, acctNo, password);
		System.out.println("转加密:"+tranPwd);
	}
	
	
	
	
	
	/**
	 * 授权柜员密码加密
	 * @throws Exception
	 */
//	@Test
	public void Test014() throws Exception{
		String tranPwd = "a1111111";
		String subTellerPwd = Encrypt.getPinValue(tranPwd);
		System.out.println("授权密码加密："+subTellerPwd);
	}

	
	
	
	/**
	 * 客户手机绑定信息查询【前置-07491】
	 * @throws Exception
	 */
	@Test
	public void Test015() throws Exception{
		/* 解析请求报文 */
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		
		//拼接输出的body
		Out07491ReqBodyBean outBody = new Out07491ReqBodyBean();
		outBody.setACCT_NO("6231930000000902361");//卡号
		
		//拼接输出的head
		OutHeadBean outHeadBean = new OutHeadBean();
		outHeadBean.setTRAN_CODE("07491");//前置交易码
		outHeadBean.setCARD_ACCP_TERM_ID("000C0011");//机器编码
		outHeadBean.setMER_NO("123456789012345");//商户号
		outHeadBean.setINST_NO("051000129");//机构号
		outHeadBean.setTELLER_NO("C0011");//柜员号
		outHeadBean.setCHANNEL("0035");//渠道号
		outHeadBean.setCHL_TRAN_CODE("");//渠道交易码
		outHeadBean.setSUP_TELLER_NO("");//授权柜员号
		outHeadBean.setTERM_JRNL_NO("");//报文流水号
		outHeadBean.setTRAN_DATE(DateUtil.getDateTime("yyyyMMdd"));//交易日期
		outHeadBean.setTRAN_TIME(DateUtil.getDateTime("HH:mm:ss"));//交易时间
		
		//拼接root类
		Out07491ReqBean out07491ReqBean = new Out07491ReqBean();
		out07491ReqBean.setBODY(outBody);
		out07491ReqBean.setHeadBean(outHeadBean);
		
		reqXs.alias("ROOT", Out07491ReqBean.class);
		reqXs.alias("HEAD", OutHeadBean.class);
		reqXs.alias("BODY", Out07491ReqBodyBean.class);
		
		System.out.println("*******请求报文：");
		String xml = reqXs.toXML(out07491ReqBean);
		xml =  "<?xml version=\"1.0\" encoding=\"GBK\" ?>\n"+xml;
		byte[] bbb = xml.getBytes("GBK");
		String mac = ConfigReader.getConfig(ENCRYPT_MAC_ZAK2NAME);//MAC密钥标记
		
		byte[] re = MACProtocolUtils.toRequest(mac, bbb);
		
		System.out.println(xml);
		System.out.println("*******返回报文：");
		try {
			Socket socket = new Socket("198.1.246.206", 9104);
			boolean closed = socket.isClosed();
//			System.out.println(closed);
//			System.out.println(socket.isConnected());
//			System.out.println(socket.isBound());
			InputStream inputStream = socket.getInputStream();
			OutputStream outputStream = socket.getOutputStream();
			outputStream.write(re);
			
			byte[] res = MACProtocolUtils.toResponse(inputStream);
			String response = new String(res,"GBK");
			System.out.println(response);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	
	
	/**
	 * 单条短信发送 【前置-07190】
	 * @throws Exception
	 */
//	@Test
	public void Test016() throws Exception{
		/* 解析请求报文 */
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		
		//拼接输出的body
		Out07190ReqBodyBean outBody = new Out07190ReqBodyBean();
		outBody.setTEL_NO("13910914954");//手机号
		outBody.setCONTENT("您的存单账号为052000125001000346731已于2016年10月20日销户成功，收回唐豆0.00元，如有疑义，请拨打客服电话96368。");//短信内容
		outBody.setMATCH_CODE("");//模板编号
		outBody.setSEND_TIME("");//发送时间————实时发送，传空值
		
		//拼接输出的head
		OutHeadBean outHeadBean = new OutHeadBean();
		outHeadBean.setTRAN_CODE("07190");//前置交易码
		outHeadBean.setCARD_ACCP_TERM_ID("000C0011");//机器编码
		outHeadBean.setMER_NO("123456789012345");//商户号
		outHeadBean.setINST_NO("051000129");//机构号
		outHeadBean.setTELLER_NO("C0011");//柜员号
		outHeadBean.setCHANNEL("0035");//渠道号
		outHeadBean.setCHL_TRAN_CODE("");//渠道交易码
		outHeadBean.setSUP_TELLER_NO("");//授权柜员号
		outHeadBean.setTERM_JRNL_NO("");//报文流水号
		outHeadBean.setTRAN_DATE(DateUtil.getDateTime("yyyyMMdd"));//交易日期
		outHeadBean.setTRAN_TIME(DateUtil.getDateTime("HH:mm:ss"));//交易时间
		
		//拼接root类
		Out07190ReqBean out07190ReqBean = new Out07190ReqBean();
		out07190ReqBean.setBODY(outBody);
		out07190ReqBean.setHeadBean(outHeadBean);
		
		reqXs.alias("ROOT", Out07190ReqBean.class);
		reqXs.alias("HEAD", OutHeadBean.class);
		reqXs.alias("BODY", Out07190ReqBodyBean.class);
		
		System.out.println("*******请求报文：");
		String xml = reqXs.toXML(out07190ReqBean);
		xml =  "<?xml version=\"1.0\" encoding=\"GBK\" ?>\n"+xml;
		byte[] bbb = xml.getBytes("GBK");
		String mac = ConfigReader.getConfig(ENCRYPT_MAC_ZAK2NAME);//MAC密钥标记
		
		byte[] re = MACProtocolUtils.toRequest(mac, bbb);
		
		System.out.println(xml);
		System.out.println("*******返回报文：");
		try {
			Socket socket = new Socket("198.1.246.206", 9104);
			boolean closed = socket.isClosed();
//			System.out.println(closed);
//			System.out.println(socket.isConnected());
//			System.out.println(socket.isBound());
			InputStream inputStream = socket.getInputStream();
			OutputStream outputStream = socket.getOutputStream();
			outputStream.write(re);
			
			byte[] res = MACProtocolUtils.toResponse(inputStream);
			String response = new String(res,"GBK");
			System.out.println(response);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 4.12待打印存单查询【02948】前置03518																																																																																
	 * 
	 * @throws Exception
	 */
	@Test
	public void Test017() throws Exception {
		/* 解析请求报文 */
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));

		// 拼接输出的body
		Out03518ReqBodyBean outBody = new Out03518ReqBodyBean();
		outBody.setID_TYPE("10100"); //证件类型
		outBody.setID_NO("140624199206180015"); //证件号
		outBody.setOPER_CHOOSE("0"); //查--未达印账户
//		outBody.setOPER_CHOOSE("1"); //查--可变更账户

		// 拼接输出的head
		OutHeadBean outHeadBean = new OutHeadBean();
		outHeadBean.setTRAN_CODE("03518");// 前置交易码
		outHeadBean.setCARD_ACCP_TERM_ID("000C0011");// 机器编码
		outHeadBean.setMER_NO("123456789012345");// 商户号
		outHeadBean.setINST_NO("053600150");// 机构号
		outHeadBean.setTELLER_NO("C0011");// 柜员号
		outHeadBean.setCHANNEL("0035");// 渠道号
		outHeadBean.setCHL_TRAN_CODE("");// 渠道交易码
		outHeadBean.setSUP_TELLER_NO("");// 授权柜员号
		outHeadBean.setTERM_JRNL_NO("");// 报文流水号
		outHeadBean.setTRAN_DATE(DateUtil.getDateTime("yyyyMMdd"));// 交易日期
		outHeadBean.setTRAN_TIME(DateUtil.getDateTime("HH:mm:ss"));// 交易时间

		// 拼接root类
		Out03518ReqBean out03518ReqBean = new Out03518ReqBean();
		out03518ReqBean.setBODY(outBody);
		out03518ReqBean.setHeadBean(outHeadBean);

		reqXs.alias("ROOT", Out03518ReqBean.class);
		reqXs.alias("HEAD", OutHeadBean.class);
		reqXs.alias("BODY", Out03518ReqBodyBean.class);

		System.out.println("*******请求报文：");
		String xml = reqXs.toXML(out03518ReqBean);
		xml = "<?xml version=\"1.0\" encoding=\"GBK\" ?>\n" + xml;
		byte[] bbb = xml.getBytes("GBK");
		String mac = ConfigReader.getConfig(ENCRYPT_MAC_ZAK2NAME);// MAC密钥标记

		byte[] re = MACProtocolUtils.toRequest(mac, bbb);

		System.out.println(xml);
		System.out.println("*******返回报文：");
		try {
			String retMsg = "";
			Socket socket = new Socket("198.1.246.206", 9104);
			boolean closed = socket.isClosed();
			InputStream inputStream = socket.getInputStream();
			OutputStream outputStream = socket.getOutputStream();
			outputStream.write(re);

			byte[] res = MACProtocolUtils.toResponse(inputStream);
			String response = new String(res, "GBK");
			System.out.println(response);
			
			 //读取服务器返回的消息  
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));  
			
			String value = null;
			while ((value = br.readLine()) != null) {
				retMsg += value + "\n";
				if ("</ROOT>".equals(value)){
					break;
				}
			}
			System.out.println(retMsg);
			XStream ss = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			reqXs.alias("ROOT", Out03518ResBean.class);
			Out03518ResBean out03518ResBean = (Out03518ResBean)ss .fromXML(retMsg);
			System.out.println(out03518ResBean);
			System.out.println("CLIENT retMsg:" + retMsg);
			
			String FTP_IP="198.1.246.206";
			String FTP_USER="ftp1";
			String FTP_PAS="ftp1";
			String LOCAL_FOLDER_ADDRESS="C://Users//Administrator//Desktop//ftp1";
			String fileName=out03518ResBean.getBODY().getFILE_NAME();
			FtpFileUtils.downloadFileByFTPClient(FTP_IP, 21, FTP_USER, FTP_PAS, fileName, LOCAL_FOLDER_ADDRESS);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 存单打印【02949】前置03514																																																																														
	 * 
	 * @throws Exception
	 */
	@Test
	public void Test018() throws Exception {
		/* 解析请求报文 */
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));

		// 拼接输出的body
		Out03514ReqBodyBean outBody = new Out03514ReqBodyBean();
		outBody.setACCT_NO("6231930008000000201"); //卡号
		outBody.setSUB_ACCT_NO("9");//子账号
		outBody.setCERT_NO_ADD("04000974"); //凭证号

		// 拼接输出的head
		OutHeadBean outHeadBean = new OutHeadBean();
		outHeadBean.setTRAN_CODE("03514");// 前置交易码
		outHeadBean.setCARD_ACCP_TERM_ID("000C0011");// 机器编码
		outHeadBean.setMER_NO("123456789012345");// 商户号
		outHeadBean.setINST_NO("051000129");// 机构号
		outHeadBean.setTELLER_NO("C0011");// 柜员号
		outHeadBean.setCHANNEL("0035");// 渠道号
		outHeadBean.setCHL_TRAN_CODE("");// 渠道交易码
		outHeadBean.setSUP_TELLER_NO("");// 授权柜员号
		outHeadBean.setTERM_JRNL_NO("");// 报文流水号
		outHeadBean.setTRAN_DATE(DateUtil.getDateTime("yyyyMMdd"));// 交易日期
		outHeadBean.setTRAN_TIME(DateUtil.getDateTime("HH:mm:ss"));// 交易时间

		// 拼接root类
		Out03514ReqBean out03514ReqBean = new Out03514ReqBean();
		out03514ReqBean.setBODY(outBody);
		out03514ReqBean.setHeadBean(outHeadBean);

		reqXs.alias("ROOT", Out03514ReqBean.class);
		reqXs.alias("HEAD", OutHeadBean.class);
		reqXs.alias("BODY", Out03514ReqBodyBean.class);

		System.out.println("*******请求报文：");
		String xml = reqXs.toXML(out03514ReqBean);
		xml = "<?xml version=\"1.0\" encoding=\"GBK\" ?>\n" + xml;
		byte[] bbb = xml.getBytes("GBK");
		String mac = ConfigReader.getConfig(ENCRYPT_MAC_ZAK2NAME);// MAC密钥标记

		byte[] re = MACProtocolUtils.toRequest(mac, bbb);

		System.out.println(xml);
		System.out.println("*******返回报文：");
		try {
			Socket socket = new Socket("198.1.246.206", 9104);
			boolean closed = socket.isClosed();
			InputStream inputStream = socket.getInputStream();
			OutputStream outputStream = socket.getOutputStream();
			outputStream.write(re);

			byte[] res = MACProtocolUtils.toResponse(inputStream);
			String response = new String(res, "GBK");
			System.out.println(response);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 存单打印状态变更【02950】前置03520																																																																												
	 * 
	 * @throws Exception
	 */
	@Test
	public void Test019() throws Exception {
		/* 解析请求报文 */
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));

		// 拼接输出的body
		Out03520ReqBodyBean outBody = new Out03520ReqBodyBean();
		outBody.setACCT_NO("6231930000000900092"); //卡号
		outBody.setSUB_ACCT_NO("67");//子账号
		outBody.setOPER_CHOOSE("0"); //操作选择

		// 拼接输出的head
		OutHeadBean outHeadBean = new OutHeadBean();
		outHeadBean.setTRAN_CODE("03520");// 前置交易码
		outHeadBean.setCARD_ACCP_TERM_ID("000C0011");// 机器编码
		outHeadBean.setMER_NO("123456789012345");// 商户号
		outHeadBean.setINST_NO("051000129");// 机构号
		outHeadBean.setTELLER_NO("C0011");// 柜员号
		outHeadBean.setCHANNEL("0035");// 渠道号
		outHeadBean.setCHL_TRAN_CODE("");// 渠道交易码
		outHeadBean.setSUP_TELLER_NO("");// 授权柜员号
		outHeadBean.setTERM_JRNL_NO("");// 报文流水号
		outHeadBean.setTRAN_DATE(DateUtil.getDateTime("yyyyMMdd"));// 交易日期
		outHeadBean.setTRAN_TIME(DateUtil.getDateTime("HH:mm:ss"));// 交易时间

		// 拼接root类
		Out03520ReqBean out03520ReqBean = new Out03520ReqBean();
		out03520ReqBean.setBODY(outBody);
		out03520ReqBean.setHeadBean(outHeadBean);

		reqXs.alias("ROOT", Out03520ReqBean.class);
		reqXs.alias("HEAD", OutHeadBean.class);
		reqXs.alias("BODY", Out03520ReqBodyBean.class);

		System.out.println("*******请求报文：");
		String xml = reqXs.toXML(out03520ReqBean);
		xml = "<?xml version=\"1.0\" encoding=\"GBK\" ?>\n" + xml;
		byte[] bbb = xml.getBytes("GBK");
		String mac = ConfigReader.getConfig(ENCRYPT_MAC_ZAK2NAME);// MAC密钥标记

		byte[] re = MACProtocolUtils.toRequest(mac, bbb);

		System.out.println(xml);
		System.out.println("*******返回报文：");
		try {
			Socket socket = new Socket("198.1.246.206", 9104);
			boolean closed = socket.isClosed();
			InputStream inputStream = socket.getInputStream();
			OutputStream outputStream = socket.getOutputStream();
			outputStream.write(re);

			byte[] res = MACProtocolUtils.toResponse(inputStream);
			String response = new String(res, "GBK");
			System.out.println(response);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 *子账户列表查询-【75109】前置03519																																																																													
	 * 
	 * @throws Exception
	 */
	@Test
	public void Test020() throws Exception {
		/* 解析请求报文 */
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));

		// 拼接输出的body
		Out03519ReqBodyBean outBody = new Out03519ReqBodyBean();
		outBody.setCARD_NO("6231930000000902353"); //卡号6231930008000170285

		// 拼接输出的head
		OutHeadBean outHeadBean = new OutHeadBean();
		outHeadBean.setTRAN_CODE("03519");// 前置交易码
		outHeadBean.setCARD_ACCP_TERM_ID("000C0011");// 机器编码
		outHeadBean.setMER_NO("123456789012345");// 商户号
		outHeadBean.setINST_NO("051000129");// 机构号
		outHeadBean.setTELLER_NO("C0011");// 柜员号
		outHeadBean.setCHANNEL("0035");// 渠道号
		outHeadBean.setCHL_TRAN_CODE("");// 渠道交易码
		outHeadBean.setSUP_TELLER_NO("");// 授权柜员号
		outHeadBean.setTERM_JRNL_NO("");// 报文流水号
		outHeadBean.setTRAN_DATE(DateUtil.getDateTime("yyyyMMdd"));// 交易日期
		outHeadBean.setTRAN_TIME(DateUtil.getDateTime("HH:mm:ss"));// 交易时间

		// 拼接root类
		Out03519ReqBean out03519ReqBean = new Out03519ReqBean();
		out03519ReqBean.setBODY(outBody);
		out03519ReqBean.setHeadBean(outHeadBean);

		reqXs.alias("ROOT", Out03519ReqBean.class);
		reqXs.alias("HEAD", OutHeadBean.class);
		reqXs.alias("BODY", Out03519ReqBodyBean.class);

		System.out.println("*******请求报文：");
		String xml = reqXs.toXML(out03519ReqBean);
		xml = "<?xml version=\"1.0\" encoding=\"GBK\" ?>\n" + xml;
		byte[] bbb = xml.getBytes("GBK");
		String mac = ConfigReader.getConfig(ENCRYPT_MAC_ZAK2NAME);// MAC密钥标记

		byte[] re = MACProtocolUtils.toRequest(mac, bbb);

		System.out.println(xml);
		System.out.println("*******返回报文：");
		try {
			Socket socket = new Socket("198.1.246.206", 9104);
			boolean closed = socket.isClosed();
			InputStream inputStream = socket.getInputStream();
			OutputStream outputStream = socket.getOutputStream();
			outputStream.write(re);

			byte[] res = MACProtocolUtils.toResponse(inputStream);
			String response = new String(res, "GBK");
			System.out.println(response);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 *账户信息查询及密码验证-前置03521																																																																											
	 * 
	 * @throws Exception
	 */
	@Test
	public void Test021() throws Exception {
		/* 解析请求报文 */
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));

		// 拼接输出的body
		Out03521ReqBodyBean outBody = new Out03521ReqBodyBean();
		outBody.setACCT_NO("053600150000600246910");
		outBody.setPIN_VAL_FLAG("0");
		/*凭证密码加密*/
		if("1".equals(outBody.getPIN_VAL_FLAG())){
			String pinKeyName=ConfigReader.getConfig(ENCRYPT_PIN_KEYNAME);//键盘工作密钥标记
			String acctNo="6231930000002000099";//卡号
			String pin="123456";//银行卡密码
			String password=EncryptUtils.encryPin433(pinKeyName,pin,acctNo);
//			System.out.println("+++++++++password:"+password);
			String tranKeyFullName=ConfigReader.getConfig(ENCRYTP_PIN_TRANKEYFULLNAME);//转加密密钥标记
			String tranPwd=EncryptUtils.tranPin310(pinKeyName, tranKeyFullName, acctNo, password);
//			System.out.println("+++++++++tranPwd:"+tranPwd);
			outBody.setPASSWORD(tranPwd);//密码(凭密码时必输，无密码不显示此输入项)
		}else{
			outBody.setPASSWORD("");//密码(凭密码时必输，无密码不显示此输入项)
		}

		// 拼接输出的head
		OutHeadBean outHeadBean = new OutHeadBean();
		outHeadBean.setTRAN_CODE("03521");// 前置交易码
		outHeadBean.setCARD_ACCP_TERM_ID("000C0036");// 机器编码
		outHeadBean.setMER_NO("123456789012345");// 商户号
		outHeadBean.setINST_NO("053600150");// 机构号
		outHeadBean.setTELLER_NO("C0036");// 柜员号
		outHeadBean.setCHANNEL("0035");// 渠道号
		outHeadBean.setCHL_TRAN_CODE("");// 渠道交易码
		outHeadBean.setSUP_TELLER_NO("");// 授权柜员号
		outHeadBean.setTERM_JRNL_NO("");// 报文流水号
		outHeadBean.setTRAN_DATE(DateUtil.getDateTime("yyyyMMdd"));// 交易日期
		outHeadBean.setTRAN_TIME(DateUtil.getDateTime("HH:mm:ss"));// 交易时间

		// 拼接root类
		Out03521ReqBean out03521ReqBean = new Out03521ReqBean();
		out03521ReqBean.setBODY(outBody);
		out03521ReqBean.setHeadBean(outHeadBean);

		reqXs.alias("ROOT", Out03521ReqBean.class);
		reqXs.alias("HEAD", OutHeadBean.class);
		reqXs.alias("BODY", Out03521ReqBodyBean.class);

		System.out.println("*******请求报文：");
		String xml = reqXs.toXML(out03521ReqBean);
		xml = "<?xml version=\"1.0\" encoding=\"GBK\" ?>\n" + xml;
		byte[] bbb = xml.getBytes("GBK");
		String mac = ConfigReader.getConfig(ENCRYPT_MAC_ZAK2NAME);// MAC密钥标记

		byte[] re = MACProtocolUtils.toRequest(mac, bbb);

		System.out.println(xml);
		System.out.println("*******返回报文：");
		try {
			Socket socket = new Socket("198.1.246.247", 9104);
			boolean closed = socket.isClosed();
			InputStream inputStream = socket.getInputStream();
			OutputStream outputStream = socket.getOutputStream();
			outputStream.write(re);

			byte[] res = MACProtocolUtils.toResponse(inputStream);
			String response = new String(res, "GBK");
			System.out.println(response);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 *凭证信息综合查询【02882】-前置02791																																																																						
	 * 
	 */
	@Test
	public void Test022() throws Exception {
		/* 解析请求报文 */
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));

		// 拼接输出的body
		Out02882ReqBodyBean outBody = new Out02882ReqBodyBean();
		outBody.setCERT_NO("04047676");
		outBody.setCERT_TYPE("102");
		
		// 拼接输出的head
		OutHeadBean outHeadBean = new OutHeadBean();
		outHeadBean.setTRAN_CODE("02791");// 前置交易码
		outHeadBean.setCARD_ACCP_TERM_ID("000C0011");// 机器编码
		outHeadBean.setMER_NO("123456789012345");// 商户号
		outHeadBean.setINST_NO("051000129");// 机构号
		outHeadBean.setTELLER_NO("C0011");// 柜员号
		outHeadBean.setCHANNEL("0035");// 渠道号
		outHeadBean.setCHL_TRAN_CODE("");// 渠道交易码
		outHeadBean.setSUP_TELLER_NO("");// 授权柜员号
		outHeadBean.setTERM_JRNL_NO("");// 报文流水号
		outHeadBean.setTRAN_DATE(DateUtil.getDateTime("yyyyMMdd"));// 交易日期
		outHeadBean.setTRAN_TIME(DateUtil.getDateTime("HH:mm:ss"));// 交易时间

		// 拼接root类
		Out02882ReqBean out02882ReqBean = new Out02882ReqBean();
		out02882ReqBean.setBODY(outBody);
		out02882ReqBean.setHeadBean(outHeadBean);

		reqXs.alias("ROOT", Out02882ReqBean.class);
		reqXs.alias("HEAD", OutHeadBean.class);
		reqXs.alias("BODY", Out02882ReqBodyBean.class);

		System.out.println("*******请求报文：");
		String xml = reqXs.toXML(out02882ReqBean);
		xml = "<?xml version=\"1.0\" encoding=\"GBK\" ?>\n" + xml;
		byte[] bbb = xml.getBytes("GBK");
		String mac = ConfigReader.getConfig(ENCRYPT_MAC_ZAK2NAME);// MAC密钥标记

		byte[] re = MACProtocolUtils.toRequest(mac, bbb);

		System.out.println(xml);
		System.out.println("*******返回报文：");
		try {
			Socket socket = new Socket("198.1.246.206", 9104);
			boolean closed = socket.isClosed();
			InputStream inputStream = socket.getInputStream();
			OutputStream outputStream = socket.getOutputStream();
			outputStream.write(re);

			byte[] res = MACProtocolUtils.toResponse(inputStream);
			String response = new String(res, "GBK");
			System.out.println(response);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 卡系统 子账户销户【75104】前置03517
	 * @throws Exception
	 */
	@Test
	public void Test023() throws Exception{
		/* 解析请求报文 */
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		
		//拼接输出的body
		Out03517ReqBodyBean outBody = new Out03517ReqBodyBean();
		outBody.setTRAN_CHANNEL("00"); //渠道号 00-网银 50-手机银行
		outBody.setCARD_NO("6231930000000610659"); //卡号
		outBody.setSUB_ACCT_NO2("9"); //子账户
		outBody.setCAL_MODE("1"); //结算方式  0-现金 1-转账
		outBody.setCASH_ANALY_NO(""); //现金分析号
		outBody.setOPPO_ACCT_NO(""); //对方账号
		outBody.setCERT_NO("04047675"); //凭证号
		outBody.setID_TYPE("1"); //证件类型
		outBody.setID_NO("130682199109264075"); //证件号码
		outBody.setHAV_AGENT_FLAG("1"); //代理人标志 0-是 1-否
		outBody.setPIN_VAL_FLAG("1"); //验密标志       N-不验密，其他都验密，内部使用，不对外。

		/*凭证密码加密*/
		if(!"N".equals(outBody.getPIN_VAL_FLAG())){
			String pinKeyName=ConfigReader.getConfig(ENCRYPT_PIN_KEYNAME);//键盘工作密钥标记
			String acctNo="6231930000000610659";//子账号所属卡号
			String pin="123456";//存单密码
			String password=EncryptUtils.encryPin433(pinKeyName,pin,acctNo);
//			System.out.println("+++++++++password:"+password);
			String tranKeyFullName=ConfigReader.getConfig(ENCRYTP_PIN_TRANKEYFULLNAME);//转加密密钥标记
			String tranPwd=EncryptUtils.tranPin310(pinKeyName, tranKeyFullName, acctNo, password);
//			System.out.println("+++++++++tranPwd:"+tranPwd);
			outBody.setPASSWORD(tranPwd);//密码(凭密码时必输，无密码不显示此输入项)
		}else{
			outBody.setPASSWORD("");//密码(凭密码时必输，无密码不显示此输入项)
		}
		
		if(outBody.getHAV_AGENT_FLAG().equals("0")){
			//拼接输出的AGENT_INF
			Out03517ReqAgentInfBean outAgentInf = new Out03517ReqAgentInfBean();
			outAgentInf.setCUST_NAME("张蒙");//客户姓名
			outAgentInf.setDUE_DATE("");//到期日期
			outAgentInf.setID_NO("130682199109264075");//证件号码
			outAgentInf.setID_TYPE("1");//证件类别
			outAgentInf.setISSUE_DATE("");//签发日期
			outAgentInf.setISSUE_INST("定州市公安局");//签发机关
			outAgentInf.setJ_C_ADD("");//经常居住地
			outAgentInf.setMOB_PHONE("");//移动电话
			outAgentInf.setNATION("");//国籍
			outAgentInf.setOCCUPATION("公务员");//职业
			outAgentInf.setREGIS_PER_RES("河北省定州市周村镇前屯村67号");//户口所在地
			outAgentInf.setSEX("男");//性别
			outAgentInf.setTELEPHONE("");//固定电话
			
			outBody.setAGENT_INF(outAgentInf);
		}
		//拼接输出的head
		OutHeadBean outHeadBean = new OutHeadBean();
		outHeadBean.setTRAN_CODE("03517");//前置交易码
		outHeadBean.setCARD_ACCP_TERM_ID("000C0011");//机器编码
		outHeadBean.setMER_NO("123456789012345");//商户号
		outHeadBean.setINST_NO("051000129");//机构号
		outHeadBean.setTELLER_NO("C0011");//柜员号
		outHeadBean.setCHANNEL("0035");//渠道号
		outHeadBean.setCHL_TRAN_CODE("");//渠道交易码
		outHeadBean.setSUP_TELLER_NO("");//授权柜员号
		outHeadBean.setTERM_JRNL_NO("");//报文流水号
		outHeadBean.setTRAN_DATE(DateUtil.getDateTime("yyyyMMdd"));//交易日期
		outHeadBean.setTRAN_TIME(DateUtil.getDateTime("HH:mm:ss"));//交易时间
		
		//拼接root类
		Out03517ReqBean out03517ReqBean = new Out03517ReqBean();
		out03517ReqBean.setBODY(outBody);
		out03517ReqBean.setHeadBean(outHeadBean);
		
		reqXs.alias("ROOT", Out03517ReqBean.class);
		reqXs.alias("HEAD", OutHeadBean.class);
		reqXs.alias("BODY", Out03517ReqBodyBean.class);
		
		System.out.println("*******请求报文：");
		String xml = reqXs.toXML(out03517ReqBean);
		xml =  "<?xml version=\"1.0\" encoding=\"GBK\" ?>\n"+xml;
		byte[] bbb = xml.getBytes("GBK");
		String mac = ConfigReader.getConfig(ENCRYPT_MAC_ZAK2NAME);
		byte[] re = MACProtocolUtils.toRequest(mac, bbb);
		
		System.out.println(xml);
		System.out.println("*******返回报文：");
		try {
			Socket socket = new Socket("198.1.246.206", 9104);
			boolean closed = socket.isClosed();
			InputStream inputStream = socket.getInputStream();
			OutputStream outputStream = socket.getOutputStream();
			outputStream.write(re);
			
			byte[] res = MACProtocolUtils.toResponse(inputStream);
			String response = new String(res,"GBK");
			System.out.println(response);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 电子账户 子账户销户【35104】-前置03522
	 * @throws Exception
	 */
	@Test
	public void Test024() throws Exception{
		/* 解析请求报文 */
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		
		//拼接输出的body
		Out03522ReqBodyBean outBody = new Out03522ReqBodyBean();
		outBody.setTRAN_CHANNEL("00"); //渠道号 00-网银 50-手机银行N
		outBody.setCARD_NO("6231930000000610659"); //卡号
		outBody.setSUB_ACCT_NO("9"); //子账户
		outBody.setCAL_MODE("1"); //结算方式  0-现金 1-转账
		outBody.setCASH_ANALY_NO(""); //现金分析号
		outBody.setOPPO_ACCT_NO(""); //对方账号
		outBody.setCERT_NO("04047675"); //凭证号
		outBody.setID_TYPE("1"); //证件类型
		outBody.setID_NO("130682199109264075"); //证件号码
		outBody.setHAV_AGENT_FLAG("1"); //代理人标志 0-是 1-否
		outBody.setPIN_VAL_FLAG("N"); //验密标志       N-不验密，其他都验密，内部使用，不对外。

		/*凭证密码加密*/
		if(!"N".equals(outBody.getPIN_VAL_FLAG())){
			String pinKeyName=ConfigReader.getConfig(ENCRYPT_PIN_KEYNAME);//键盘工作密钥标记
			String acctNo="6231930000000610659";//子账号所属卡号
			String pin="123456";//存单密码
			String password=EncryptUtils.encryPin433(pinKeyName,pin,acctNo);
//			System.out.println("+++++++++password:"+password);
			String tranKeyFullName=ConfigReader.getConfig(ENCRYTP_PIN_TRANKEYFULLNAME);//转加密密钥标记
			String tranPwd=EncryptUtils.tranPin310(pinKeyName, tranKeyFullName, acctNo, password);
//			System.out.println("+++++++++tranPwd:"+tranPwd);
			outBody.setPASSWORD(tranPwd);//密码(凭密码时必输，无密码不显示此输入项)
		}else{
			outBody.setPASSWORD("");//密码(凭密码时必输，无密码不显示此输入项)
		}
		
		if(outBody.getHAV_AGENT_FLAG().equals("0")){
			//拼接输出的AGENT_INF
			Out03522ReqAgentInfBean outAgentInf = new Out03522ReqAgentInfBean();
			outAgentInf.setCUST_NAME("张蒙");//客户姓名
			outAgentInf.setDUE_DATE("");//到期日期
			outAgentInf.setID_NO("130682199109264075");//证件号码
			outAgentInf.setID_TYPE("1");//证件类别
			outAgentInf.setISSUE_DATE("");//签发日期
			outAgentInf.setISSUE_INST("定州市公安局");//签发机关
			outAgentInf.setJ_C_ADD("");//经常居住地
			outAgentInf.setMOB_PHONE("");//移动电话
			outAgentInf.setNATION("");//国籍
			outAgentInf.setOCCUPATION("公务员");//职业
			outAgentInf.setREGIS_PER_RES("河北省定州市周村镇前屯村67号");//户口所在地
			outAgentInf.setSEX("男");//性别
			outAgentInf.setTELEPHONE("");//固定电话
			
			outBody.setAGENT_INF(outAgentInf);
		}
		//拼接输出的head
		OutHeadBean outHeadBean = new OutHeadBean();
		outHeadBean.setTRAN_CODE("03522");//前置交易码
		outHeadBean.setCARD_ACCP_TERM_ID("000C0011");//机器编码
		outHeadBean.setMER_NO("123456789012345");//商户号
		outHeadBean.setINST_NO("051000129");//机构号
		outHeadBean.setTELLER_NO("C0011");//柜员号
		outHeadBean.setCHANNEL("0035");//渠道号
		outHeadBean.setCHL_TRAN_CODE("");//渠道交易码
		outHeadBean.setSUP_TELLER_NO("");//授权柜员号
		outHeadBean.setTERM_JRNL_NO("");//报文流水号
		outHeadBean.setTRAN_DATE(DateUtil.getDateTime("yyyyMMdd"));//交易日期
		outHeadBean.setTRAN_TIME(DateUtil.getDateTime("HH:mm:ss"));//交易时间
		
		//拼接root类
		Out03522ReqBean out03522ReqBean = new Out03522ReqBean();
		out03522ReqBean.setBODY(outBody);
		out03522ReqBean.setHeadBean(outHeadBean);
		
		reqXs.alias("ROOT", Out03522ReqBean.class);
		reqXs.alias("HEAD", OutHeadBean.class);
		reqXs.alias("BODY", Out03522ReqBodyBean.class);
		
		System.out.println("*******请求报文：");
		String xml = reqXs.toXML(out03522ReqBean);
		xml =  "<?xml version=\"1.0\" encoding=\"GBK\" ?>\n"+xml;
		byte[] bbb = xml.getBytes("GBK");
		String mac = ConfigReader.getConfig(ENCRYPT_MAC_ZAK2NAME);
		byte[] re = MACProtocolUtils.toRequest(mac, bbb);
		
		System.out.println(xml);
		System.out.println("*******返回报文：");
		try {
			Socket socket = new Socket("198.1.246.206", 9104);
			boolean closed = socket.isClosed();
			InputStream inputStream = socket.getInputStream();
			OutputStream outputStream = socket.getOutputStream();
			outputStream.write(re);
			
			byte[] res = MACProtocolUtils.toResponse(inputStream);
			String response = new String(res,"GBK");
			System.out.println(response);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 黑灰名单电信诈骗接口
	 * @throws Exception
	 */
	@Test
	public void Test025() throws Exception{
		
		/* 解析请求报文 */
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		
		//拼接输出的body
		Out01597ReqBodyBean outBody = new Out01597ReqBodyBean();
		outBody.setACC_NAME1("");//
		outBody.setACC_NAME2("");//
		outBody.setACC_NO1("");//出账账号371203199106060032
		outBody.setACC_NO2("6231931200000000070");//入账账号
		outBody.setBANK_ID1("");//
		outBody.setBANK_ID2("");//
		outBody.setCCY("");//
		outBody.setID_NAME1("");//出账客户证件姓名  牟春越 0.0
		outBody.setID_NAME2("黄楷");//入账客户证件姓名
		outBody.setID_NUMBER1("");//出账客户证件号码   130535199304293111  0.0
		outBody.setID_NUMBER2("");//入账客户证件号码371203199106060032
		outBody.setID_TYPE1("1");//出账客户证件类型
		outBody.setID_TYPE2("");//入账客户证件类型
		outBody.setCARD_NO1("6231931200000000070");//出账卡号371203199106060032
		outBody.setCARD_NO2("");//入账卡号
		outBody.setMEMO1("");//
		outBody.setMEMO2("");//
		outBody.setMEMO4("");//
		outBody.setMEMO3("");//
		outBody.setPROGRAM_FLAG("100501");//程序标识
		outBody.setTRAN_AMT("");//
		//拼接输出的head
		OutHeadBean outHeadBean = new OutHeadBean();
		outHeadBean.setTRAN_CODE("01597");//前置交易码
		outHeadBean.setCARD_ACCP_TERM_ID("000C0011");//机器编码
		outHeadBean.setMER_NO("123456789012345");//商户号
		outHeadBean.setINST_NO("053600150");//机构号
		outHeadBean.setTELLER_NO("C0011");//柜员号
		outHeadBean.setCHANNEL("0035");//渠道号
		outHeadBean.setCHL_TRAN_CODE("");//渠道交易码
		outHeadBean.setSUP_TELLER_NO("");//授权柜员号
		outHeadBean.setTERM_JRNL_NO("");//报文流水号
		outHeadBean.setTRAN_DATE(DateUtil.getDateTime("yyyyMMdd"));//交易日期
		outHeadBean.setTRAN_TIME(DateUtil.getDateTime("HH:mm:ss"));//交易时间
		//拼接root类
		Out01597ReqBean out01597ReqBean = new Out01597ReqBean();
		out01597ReqBean.setBODY(outBody);
		out01597ReqBean.setHeadBean(outHeadBean);
		
		reqXs.alias("ROOT", Out01597ReqBean.class);
		reqXs.alias("HEAD", OutHeadBean.class);
		reqXs.alias("BODY", Out01597ReqBodyBean.class);
		
		System.out.println("*******请求报文：");
		
		String xml = reqXs.toXML(out01597ReqBean);
		xml =  "<?xml version=\"1.0\" encoding=\"GBK\" ?>\n"+xml;
		byte[] bbb = xml.getBytes("GBK");
		String mac = ConfigReader.getConfig(ENCRYPT_MAC_ZAK2NAME);
		byte[] re = MACProtocolUtils.toRequest(mac, bbb);
		
		System.out.println(xml);
		System.out.println("*******返回报文：");

		try {
			Socket socket = new Socket("198.1.246.206", 9104);
			boolean closed = socket.isClosed();
//			System.out.println(closed);
//			System.out.println(socket.isConnected());
//			System.out.println(socket.isBound());
			InputStream inputStream = socket.getInputStream();
			OutputStream outputStream = socket.getOutputStream();
			outputStream.write(re);
			
			byte[] res = MACProtocolUtils.toResponse(inputStream);
			String response = new String(res,"GBK");
			System.out.println(response);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * 大小额系统参数查询CM021
	 * @throws hk
	 */
	@Test
	public void Test026() throws Exception{
		
		/***
		 * 1. 解析请求报文
		 * 2. 生成响应报文
		 */
		
		/* 解析请求报文 */
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		
		//拼接输出的body
		OutCM021ReqBodyBean outBody = new OutCM021ReqBodyBean();
		outBody.setSYSCODE("HVPS");//系统代码
		outBody.setPARCODE("CUR_SYS_STAT");;//参数代码
		//拼接输出的head
		OutHeadBean outHeadBean = new OutHeadBean();
		outHeadBean.setTRAN_CODE("CM021");//前置交易码
		outHeadBean.setCARD_ACCP_TERM_ID("000C0011");//机器编码
		outHeadBean.setMER_NO("123456789012345");//商户号
		outHeadBean.setINST_NO("053600150");//机构号
		outHeadBean.setTELLER_NO("C0011");//柜员号
		outHeadBean.setCHANNEL("0035");//渠道号
		outHeadBean.setCHL_TRAN_CODE("");//渠道交易码
		outHeadBean.setSUP_TELLER_NO("");//授权柜员号
		outHeadBean.setTERM_JRNL_NO("");//报文流水号
		outHeadBean.setTRAN_DATE(DateUtil.getDateTime("yyyyMMdd"));//交易日期
		outHeadBean.setTRAN_TIME(DateUtil.getDateTime("HHmmss"));//交易时间
		
		//拼接root类
		OutCM021ReqBean outCM021ReqBean = new OutCM021ReqBean();
		outCM021ReqBean.setBODY(outBody);
		outCM021ReqBean.setHeadBean(outHeadBean);
		
		reqXs.alias("ROOT", OutCM021ReqBean.class);
		reqXs.alias("HEAD", OutHeadBean.class);
		reqXs.alias("BODY", OutCM021ReqBodyBean.class);
		
		System.out.println("我要开始调用前置了");
		String xml = reqXs.toXML(outCM021ReqBean);
		xml =  "<?xml version=\"1.0\" encoding=\"GBK\" ?>\n"+xml;
		byte[] bbb = xml.getBytes("GBK");
		String mac = "ZZQZ.00000001.zak";
		
		byte[] re = MACProtocolUtils.toRequest(mac, bbb);
		
		System.out.println(xml);
		try {
			Socket socket = new Socket("198.1.246.206", 9915);//ip为国结ip,端口号与以往不一致，为9105
			boolean closed = socket.isClosed();
			InputStream inputStream = socket.getInputStream();
			OutputStream outputStream = socket.getOutputStream();
			outputStream.write(re);
			
			byte[] res = MACProtocolUtils.toResponse(inputStream);
			String response = new String(res,"GBK");
			System.out.println(response);
			//解析接受的数据
			//暂时不解析了
			outputStream.close();
			inputStream.close();
			socket.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 机构关系查询交易【20102】--前置01569
	 * @throws hk
	 */
	@Test
	public void Test027() throws Exception{
		
		/***
		 * 1. 解析请求报文
		 * 2. 生成响应报文
		 */
		
		/* 解析请求报文 */
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		
		//拼接输出的body
		Out01596ReqBodyBean outBody = new Out01596ReqBodyBean();
		outBody.setACCT_NO("");//账号
		outBody.setCHL_NO("0035");//渠道号
		outBody.setINST_CODE("050400121");//机构号
		outBody.setSVR_INST_NO("");//交易机构号
		//拼接输出的head
		OutHeadBean outHeadBean = new OutHeadBean();
		outHeadBean.setTRAN_CODE("01569");//前置交易码
		outHeadBean.setCARD_ACCP_TERM_ID("000C0011");//机器编码
		outHeadBean.setMER_NO("123456789012345");//商户号
		outHeadBean.setINST_NO("053600150");//机构号
		outHeadBean.setTELLER_NO("C0011");//柜员号
		outHeadBean.setCHANNEL("0035");//渠道号
		outHeadBean.setCHL_TRAN_CODE("");//渠道交易码
		outHeadBean.setSUP_TELLER_NO("");//授权柜员号
		outHeadBean.setTERM_JRNL_NO("");//报文流水号
		outHeadBean.setTRAN_DATE(DateUtil.getDateTime("yyyyMMdd"));//交易日期
		outHeadBean.setTRAN_TIME(DateUtil.getDateTime("HHmmss"));//交易时间
		
		//拼接root类
		Out01596ReqBean out01596ReqBean = new Out01596ReqBean();
		out01596ReqBean.setBody(outBody);
		out01596ReqBean.setHeadBean(outHeadBean);
		
		reqXs.alias("ROOT", Out01596ReqBean.class);
		reqXs.alias("HEAD", OutHeadBean.class);
		reqXs.alias("BODY", Out01596ReqBodyBean.class);
		
		System.out.println("我要开始调用前置了");
		String xml = reqXs.toXML(out01596ReqBean);
		xml =  "<?xml version=\"1.0\" encoding=\"GBK\" ?>\n"+xml;
		byte[] bbb = xml.getBytes("GBK");
		String mac = "ZZQZ.00000001.zak";
		
		byte[] re = MACProtocolUtils.toRequest(mac, bbb);
		
		System.out.println(xml);
		try {
			Socket socket = new Socket("198.1.246.206", 9915);//ip为国结ip,端口号与以往不一致，为9915
			boolean closed = socket.isClosed();
			InputStream inputStream = socket.getInputStream();
			OutputStream outputStream = socket.getOutputStream();
			outputStream.write(re);
			
			byte[] res = MACProtocolUtils.toResponse(inputStream);
			String response = new String(res,"GBK");
			System.out.println(response);
			//解析接受的数据
			//暂时不解析了
			outputStream.close();
			inputStream.close();
			socket.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	//转账客户列表信息查询
	@Test
	public void Test028() throws Exception{
		
		/* 解析请求报文 */
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		
		//拼接输出的body
		Out07492ReqBodyBean outBody = new Out07492ReqBodyBean();
		outBody.setACCT_NAME("张建钢");//付款人名称
		outBody.setACCT_NO("6231931300000000078");//付款卡号
		outBody.setQRY_CHNL("0035");//查询渠道
		//拼接输出的head
		OutHeadBean outHeadBean = new OutHeadBean();
		outHeadBean.setTRAN_CODE("07492");//前置交易码
		outHeadBean.setCARD_ACCP_TERM_ID("000C0011");//机器编码
		outHeadBean.setMER_NO("123456789012345");//商户号
		outHeadBean.setINST_NO("053600150");//机构号
		outHeadBean.setTELLER_NO("C0011");//柜员号
		outHeadBean.setCHANNEL("0035");//渠道号
		outHeadBean.setCHL_TRAN_CODE("");//渠道交易码
		outHeadBean.setSUP_TELLER_NO("");//授权柜员号
		outHeadBean.setTERM_JRNL_NO("");//报文流水号
		outHeadBean.setTRAN_DATE(DateUtil.getDateTime("yyyyMMdd"));//交易日期
		outHeadBean.setTRAN_TIME(DateUtil.getDateTime("HHmmss"));//交易时间
		//拼接root类
		Out07492ReqBean out07492ReqBean = new Out07492ReqBean();
		out07492ReqBean.setBODY(outBody);
		out07492ReqBean.setHeadBean(outHeadBean);
		
		reqXs.alias("ROOT", Out07492ReqBean.class);
		reqXs.alias("HEAD", OutHeadBean.class);
		reqXs.alias("BODY", Out07492ReqBodyBean.class);
		
		System.out.println("*******请求报文：");
		
		String xml = reqXs.toXML(out07492ReqBean);
		xml =  "<?xml version=\"1.0\" encoding=\"GBK\" ?>\n"+xml;
		byte[] bbb = xml.getBytes("GBK");
		String mac = ConfigReader.getConfig(ENCRYPT_MAC_ZAK2NAME);
		byte[] re = MACProtocolUtils.toRequest(mac, bbb);
		
		System.out.println(xml);
		System.out.println("*******返回报文：");

		try {
			Socket socket = new Socket("198.1.246.206", 9915);//ip为国结ip,端口号与以往不一致，为9915
			boolean closed = socket.isClosed();
//			System.out.println(closed);
//			System.out.println(socket.isConnected());
//			System.out.println(socket.isBound());
			InputStream inputStream = socket.getInputStream();
			OutputStream outputStream = socket.getOutputStream();
			outputStream.write(re);
			
			byte[] res = MACProtocolUtils.toResponse(inputStream);
			String response = new String(res,"GBK");
			System.out.println(response);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	//小额普通贷记往帐录入
	@Test
	public void Test029() throws Exception{
		
		/* 解析请求报文 */
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		
		//拼接输出的body
		Out02224ReqBodyBean outBody = new Out02224ReqBodyBean();
		outBody.setBUSI_OPTION("0");//业务选择
		outBody.setBUSI_CLASS("A100");//业务类型
		outBody.setCURRENCY("00");//币种
		outBody.setBOOK_CARD("1");//折卡选择
		outBody.setPAY_ACCT_NO("");//付款人帐号
		outBody.setPAY_CARD_NO("6231931200000000070");//付款人卡号
		outBody.setPAY_NAME("史玉敏");//付款人名称
		outBody.setPAY_ADDR("");//付款人地址
		outBody.setPAY_HBR_NO("");//付款人开户行号
		outBody.setPAY_HBR_NAME("");//付款人开户行名
		outBody.setPAY_HBR_INST_NO("053600150");//付款人开户机构号
		outBody.setDRAW_COND("0");//支付条件
		outBody.setBOOK_NO("");//存折号码
		outBody.setPASSWORD("");//密码
		outBody.setBALANCE("");//余额
		outBody.setCERT_TYPE("");//凭证种类
		outBody.setCERT_NO("");//凭证号码
		outBody.setSUMM_CODE("");//摘要
		outBody.setSUMM_TEXT("");//摘要内容
		outBody.setREMIT_AMT("1234.56");//汇款金额
		outBody.setFEE_TYPE("0");//手续费类型
		outBody.setFEE("0");//手续费
		outBody.setPOST_FEE("0");//邮电费
		outBody.setBUSI_TYPE("02102");//业务种类
		outBody.setITEM_NUM("1");//笔数
		outBody.setTRAN_AMT("1234.56");//交易金额
		outBody.setPAY_ACCT_TYPE("2");//付款类型
		outBody.setPAYEE_ACCT_TYPE("2");//收款类型
		outBody.setSETT_TYPE("1");//业务模式
		outBody.setTASK_ID("ZHFW17041505360015000010");//任务号 任务号规则：ZHFW+YYMMDD+付款人开户机构号+5位序号
		outBody.setCORE_PRJ_NO("ZHFWBEPS");//核心项目编号
		outBody.setCORE_PRO_CODE("ZHFWBEPS");//核心产品代码
		outBody.setREMARK1("");//备注1
		outBody.setREMARK2("");//备注1
		outBody.setTRANSFER_FLAG("");//转账标志
		outBody.setNEXT_DAY_SEND_FLAG("");//次日发送标志
		outBody.setTIMED_SEND_TIME("");//定时发送时间
		
		Out02224ReqBodyDetailBean out02224Detail = new Out02224ReqBodyDetailBean();
		out02224Detail.setPAYEE_BANK_NO("308100005086");//收款人行号
		out02224Detail.setPAYEE_BANK_NAME("招商银行股份有限公司北京双榆树支行");//收款人行名
		out02224Detail.setPAYEE_HBR_NO("308100005086");//收款开户行号
		out02224Detail.setPAYEE_HBR_NAME("招商银行股份有限公司北京双榆树支行");//收款开户行名
		out02224Detail.setPAYEE_ACCT_NO("6214830150998226");//收款人帐号
		out02224Detail.setPAYEE_NAME("张蒙");//收款人户名
		out02224Detail.setPAYEE_ADDR("");//收款人地址
		out02224Detail.setPAY_AMT("1234.56");//支付金额
		out02224Detail.setAPPD_TEXT("");//附言
		
		outBody.setDETAIL(out02224Detail);
		//拼接输出的head
		OutHeadBean outHeadBean = new OutHeadBean();
		outHeadBean.setTRAN_CODE("02224");//前置交易码
		outHeadBean.setCARD_ACCP_TERM_ID("000C0011");//机器编码
		outHeadBean.setMER_NO("123456789012345");//商户号
		outHeadBean.setINST_NO("053600150");//机构号
		outHeadBean.setTELLER_NO("C0011");//柜员号
		outHeadBean.setCHANNEL("0035");//渠道号
		outHeadBean.setCHL_TRAN_CODE("");//渠道交易码
		outHeadBean.setSUP_TELLER_NO("");//授权柜员号
		outHeadBean.setTERM_JRNL_NO("");//报文流水号
		outHeadBean.setTRAN_DATE(DateUtil.getDateTime("yyyyMMdd"));//交易日期
		outHeadBean.setTRAN_TIME(DateUtil.getDateTime("HHmmss"));//交易时间
		//拼接root类
		Out02224ReqBean out02224ReqBean = new Out02224ReqBean();
		out02224ReqBean.setBODY(outBody);
		out02224ReqBean.setHeadBean(outHeadBean);
		
		reqXs.alias("ROOT", Out02224ReqBean.class);
		reqXs.alias("HEAD", OutHeadBean.class);
		reqXs.alias("BODY", Out02224ReqBodyBean.class);
		
		System.out.println("*******请求报文：");
		
		String xml = reqXs.toXML(out02224ReqBean);
		xml =  "<?xml version=\"1.0\" encoding=\"GBK\" ?>\n"+xml;
		byte[] bbb = xml.getBytes("GBK");
		String mac = ConfigReader.getConfig(ENCRYPT_MAC_ZAK2NAME);
		byte[] re = MACProtocolUtils.toRequest(mac, bbb);
		
		System.out.println(xml);
		System.out.println("*******返回报文：");
		

		try {
			Socket socket = new Socket("198.1.246.206", 9915);//ip为国结ip,端口号与以往不一致，为9915
			boolean closed = socket.isClosed();
//			System.out.println(closed);
//			System.out.println(socket.isConnected());
//			System.out.println(socket.isBound());
			InputStream inputStream = socket.getInputStream();
			OutputStream outputStream = socket.getOutputStream();
			outputStream.write(re);
			
			byte[] res = MACProtocolUtils.toResponse(inputStream);
			String response = new String(res,"GBK");
			System.out.println(response);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	//核心节假日查询
	@Test
	public void Test031() throws Exception{
		
		/* 解析请求报文 */
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		
		//拼接输出的body
		Out07495ReqBodyBean outBody = new Out07495ReqBodyBean();
		outBody.setQRY_DATE("20170414");//查询日期
		
		//拼接输出的head
		OutHeadBean outHeadBean = new OutHeadBean();
		outHeadBean.setTRAN_CODE("07495");//前置交易码
		outHeadBean.setCARD_ACCP_TERM_ID("000C0011");//机器编码
		outHeadBean.setMER_NO("123456789012345");//商户号
		outHeadBean.setINST_NO("053600150");//机构号
		outHeadBean.setTELLER_NO("C0011");//柜员号
		outHeadBean.setCHANNEL("0035");//渠道号
		outHeadBean.setCHL_TRAN_CODE("");//渠道交易码
		outHeadBean.setSUP_TELLER_NO("");//授权柜员号
		outHeadBean.setTERM_JRNL_NO("");//报文流水号
		outHeadBean.setTRAN_DATE(DateUtil.getDateTime("yyyyMMdd"));//交易日期
		outHeadBean.setTRAN_TIME(DateUtil.getDateTime("HHmmss"));//交易时间
		//拼接root类
		Out07495ReqBean out07495ReqBean = new Out07495ReqBean();
		out07495ReqBean.setBODY(outBody);
		out07495ReqBean.setHeadBean(outHeadBean);
		
		reqXs.alias("ROOT", Out07495ReqBean.class);
		reqXs.alias("HEAD", OutHeadBean.class);
		reqXs.alias("BODY", Out07495ReqBodyBean.class);
		
		System.out.println("*******请求报文：");
		
		String xml = reqXs.toXML(out07495ReqBean);
		xml =  "<?xml version=\"1.0\" encoding=\"GBK\" ?>\n"+xml;
		byte[] bbb = xml.getBytes("GBK");
		String mac = ConfigReader.getConfig(ENCRYPT_MAC_ZAK2NAME);
		byte[] re = MACProtocolUtils.toRequest(mac, bbb);
		
		System.out.println(xml);
		System.out.println("*******返回报文：");

		try {
			Socket socket = new Socket("198.1.246.206", 9915);//ip为国结ip,端口号与以往不一致，为9915
			boolean closed = socket.isClosed();
//					System.out.println(closed);
//					System.out.println(socket.isConnected());
//					System.out.println(socket.isBound());
			InputStream inputStream = socket.getInputStream();
			OutputStream outputStream = socket.getOutputStream();
			outputStream.write(re);
			
			byte[] res = MACProtocolUtils.toResponse(inputStream);
			String response = new String(res,"GBK");
			System.out.println(response);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//大额普通贷记往帐录入
	@Test
	public void Test032() throws Exception{
		
		/* 解析请求报文 */
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		
		//拼接输出的body
		Out02013ReqBodyBean outBody = new Out02013ReqBodyBean();
		outBody.setMSG_TYPE("100");
		outBody.setCURRENCY("00");//币种
		outBody.setBOOK_CARD("1");//折卡选择
		outBody.setPAY_ACCT_NO("");//付款人帐号
		outBody.setPAY_CARD_NO("6231931200000000070");//付款人卡号
		outBody.setPAY_NAME("史玉敏");//付款人名称
		outBody.setPAY_ADDR("");//付款人地址
		outBody.setBALANCE("");//余额
		outBody.setSETT_TYPE("1");//结算方式
		outBody.setDRAW_COND("0");//支付条件
		outBody.setPASSWORD("");//密码
		outBody.setCERT_TYPE("");//凭证种类
		outBody.setCERT_NO("");//凭证号码
		outBody.setSUMM_CODE("6");//摘要
		outBody.setSUMM_TEXT("");//摘要内容
		outBody.setREMIT_AMT("1234.56");//汇款金额
		outBody.setFEE_TYPE("0");//手续费类型
		outBody.setFEE("0");//手续费
		outBody.setPOST_FEE("0");//邮电费
		outBody.setBUSI_TYPE("02102");//业务种类
		outBody.setRECV_BANK_NO("308100005086");
		outBody.setRECV_BANK_NAME("招商银行股份有限公司北京双榆树支行");
		outBody.setRECV_CLR_BANK_NO("308584000013");
		outBody.setRECV_CLR_BANK_NAME("招商银行股份有限公司北京双榆树支行");
		outBody.setPAYEE_HBR_NO("308100005086");//收款开户行号
		outBody.setPAYEE_HBR_NAME("招商银行股份有限公司北京双榆树支行");//收款开户行名
		outBody.setPAYEE_ACCT_NO("6214830150998226");//收款人帐号
		outBody.setPAYEE_NAME("张蒙");//收款人户名
		outBody.setPAYEE_ADDR("");//收款人地址
		outBody.setORIG_SEND_DATE("");//原发报日期
		outBody.setORIG_TRAN_NO("");//原交易序号
		outBody.setORIG_TRUST_DATE("");//原委托日期
		outBody.setORIG_CMT_NO("");//原CMT号码
		outBody.setORIG_AMT("");//原金额
		outBody.setORIG_PAY_ACCT_NO("");//原付款人帐号
		outBody.setORIG_PAY_NAME("");//原付款人名称
		outBody.setORIG_PAYEE_ACCT_NO("");//原收款人帐号
		outBody.setORIG_PAYEE_NAME("");//原收款人名称
		outBody.setORIG_APPD_TEXT("");//原附言
		outBody.setAPPD_TEXT("");//附言
		outBody.setBUSI_CLASS("A100");//业务类型
		outBody.setBUSI_PRIORITY("NORM");//业务优先级
		outBody.setPAY_HBR_NO("");//付款人开户行号
		outBody.setPAY_HBR_NAME("");//付款人开户行名
		outBody.setPAY_HBR_INST_NO("053600150");//付款人开户机构号
		outBody.setREMARK("");//备注
		outBody.setREMARK2("");//备注2
		outBody.setAPPD_TEXT2("");//附言2
		outBody.setRETURN_TEXT("");//退汇原因
		outBody.setTASK_ID("ZHFW170415053600150A0009");//任务号 任务号规则：ZHFW+YYMMDD+付款人开户机构号+5位序号
		outBody.setOPER_DATE("20170414");//操作日期
		outBody.setCORE_PRJ_NO("ZHFWHVPS");//核心项目编号
		outBody.setCORE_PRO_CODE("ZHFWHVPS");//核心产品代码
		outBody.setCNAPS_MSG_TYPE("hvps.111.001.01");//二代报文种类
		outBody.setPP_No("");//平盘单号
		outBody.setTRANSFER_FLAG("");//转账标志
		outBody.setNEXT_DAY_SEND_FLAG("");//次日发送标志
		outBody.setTIMED_SEND_TIME("");//定时发送时间
		
		
		//拼接输出的head
		OutHeadBean outHeadBean = new OutHeadBean();
		outHeadBean.setTRAN_CODE("02013");//前置交易码
		outHeadBean.setCARD_ACCP_TERM_ID("000C0011");//机器编码
		outHeadBean.setMER_NO("123456789012345");//商户号
		outHeadBean.setINST_NO("053600150");//机构号
		outHeadBean.setTELLER_NO("C0011");//柜员号
		outHeadBean.setCHANNEL("0035");//渠道号
		outHeadBean.setCHL_TRAN_CODE("");//渠道交易码
		outHeadBean.setSUP_TELLER_NO("");//授权柜员号
		outHeadBean.setTERM_JRNL_NO("");//报文流水号
		outHeadBean.setTRAN_DATE(DateUtil.getDateTime("yyyyMMdd"));//交易日期
		outHeadBean.setTRAN_TIME(DateUtil.getDateTime("HHmmss"));//交易时间
		//拼接root类
		Out02013ReqBean out02013ReqBean = new Out02013ReqBean();
		out02013ReqBean.setBODY(outBody);
		out02013ReqBean.setHeadBean(outHeadBean);
		
		reqXs.alias("ROOT", Out02013ReqBean.class);
		reqXs.alias("HEAD", OutHeadBean.class);
		reqXs.alias("BODY", Out02013ReqBodyBean.class);
		
		System.out.println("*******请求报文：");
		
		String xml = reqXs.toXML(out02013ReqBean);
		xml =  "<?xml version=\"1.0\" encoding=\"GBK\" ?>\n"+xml;
		byte[] bbb = xml.getBytes("GBK");
		String mac = ConfigReader.getConfig(ENCRYPT_MAC_ZAK2NAME);
		byte[] re = MACProtocolUtils.toRequest(mac, bbb);
		
		System.out.println(xml);
		System.out.println("*******返回报文：");

		try {
			Socket socket = new Socket("198.1.246.206", 9915);//ip为国结ip,端口号与以往不一致，为9915
			boolean closed = socket.isClosed();
//				System.out.println(closed);
//				System.out.println(socket.isConnected());
//				System.out.println(socket.isBound());
			InputStream inputStream = socket.getInputStream();
			OutputStream outputStream = socket.getOutputStream();
			outputStream.write(re);
			
			byte[] res = MACProtocolUtils.toResponse(inputStream);
			String response = new String(res,"GBK");
			System.out.println(response);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//转账信息查询
		@Test
		public void Test030() throws Exception{
			
			/* 解析请求报文 */
			XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			
			//拼接输出的body
			Out03521ReqBodyBean outBody = new Out03521ReqBodyBean();
			outBody.setACCT_NO("6231931200000000070");//账号
			outBody.setPIN_VAL_FLAG("0");//验密标志
			outBody.setPASSWORD("");//密码
			
			
			//拼接输出的head
			OutHeadBean outHeadBean = new OutHeadBean();
			outHeadBean.setTRAN_CODE("03521");//前置交易码
			outHeadBean.setCARD_ACCP_TERM_ID("000C0011");//机器编码
			outHeadBean.setMER_NO("123456789012345");//商户号
			outHeadBean.setINST_NO("053600150");//机构号
			outHeadBean.setTELLER_NO("C0011");//柜员号
			outHeadBean.setCHANNEL("0035");//渠道号
			outHeadBean.setCHL_TRAN_CODE("");//渠道交易码
			outHeadBean.setSUP_TELLER_NO("");//授权柜员号
			outHeadBean.setTERM_JRNL_NO("");//报文流水号
			outHeadBean.setTRAN_DATE(DateUtil.getDateTime("yyyyMMdd"));//交易日期
			outHeadBean.setTRAN_TIME(DateUtil.getDateTime("HHmmss"));//交易时间
			//拼接root类
			Out03521ReqBean out03521ReqBean = new Out03521ReqBean();
			out03521ReqBean.setBODY(outBody);
			out03521ReqBean.setHeadBean(outHeadBean);
			
			reqXs.alias("ROOT", Out03521ReqBean.class);
			reqXs.alias("HEAD", OutHeadBean.class);
			reqXs.alias("BODY", Out03521ReqBodyBean.class);
			
			System.out.println("*******请求报文：");
			
			String xml = reqXs.toXML(out03521ReqBean);
			xml =  "<?xml version=\"1.0\" encoding=\"GBK\" ?>\n"+xml;
			byte[] bbb = xml.getBytes("GBK");
			String mac = ConfigReader.getConfig(ENCRYPT_MAC_ZAK2NAME);
			byte[] re = MACProtocolUtils.toRequest(mac, bbb);
			
			System.out.println(xml);
			System.out.println("*******返回报文：");
	
			try {
				Socket socket = new Socket("198.1.246.206", 9104);//ip为国结ip,端口号与以往不一致，为9915
				boolean closed = socket.isClosed();
	//				System.out.println(closed);
	//				System.out.println(socket.isConnected());
	//				System.out.println(socket.isBound());
				InputStream inputStream = socket.getInputStream();
				OutputStream outputStream = socket.getOutputStream();
				outputStream.write(re);
				
				byte[] res = MACProtocolUtils.toResponse(inputStream);
				String response = new String(res,"GBK");
				System.out.println(response);
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}




	//单日累计划转金额查询-前置07494
	@Test
	public void Test033() throws Exception{
		
		/* 解析请求报文 */
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		
		//拼接输出的body
		Out07494ReqBodyBean outBody = new Out07494ReqBodyBean();
		outBody.setACCT_NO("6231930000000001099");
		
		//拼接输出的head
		OutHeadBean outHeadBean = new OutHeadBean();
		outHeadBean.setTRAN_CODE("07494");//前置交易码
		outHeadBean.setCARD_ACCP_TERM_ID("000C0011");//机器编码
		outHeadBean.setMER_NO("123456789012345");//商户号
		outHeadBean.setINST_NO("053600150");//机构号
		outHeadBean.setTELLER_NO("C0011");//柜员号
		outHeadBean.setCHANNEL("0035");//渠道号
		outHeadBean.setCHL_TRAN_CODE("");//渠道交易码
		outHeadBean.setSUP_TELLER_NO("");//授权柜员号
		outHeadBean.setTERM_JRNL_NO("");//报文流水号
		outHeadBean.setTRAN_DATE(DateUtil.getDateTime("yyyyMMdd"));//交易日期
		outHeadBean.setTRAN_TIME(DateUtil.getDateTime("HHmmss"));//交易时间
		//拼接root类
		Out07494ReqBean out07494ReqBean = new Out07494ReqBean();
		out07494ReqBean.setBody(outBody);
		out07494ReqBean.setHeadBean(outHeadBean);
		
		reqXs.alias("ROOT", Out07494ReqBean.class);
		reqXs.alias("HEAD", OutHeadBean.class);
		reqXs.alias("BODY", Out07494ReqBodyBean.class);
		
		System.out.println("*******请求报文：");
		
		String xml = reqXs.toXML(out07494ReqBean);
		xml =  "<?xml version=\"1.0\" encoding=\"GBK\" ?>\n"+xml;
		byte[] bbb = xml.getBytes("GBK");
		String mac = ConfigReader.getConfig(ENCRYPT_MAC_ZAK2NAME);
		byte[] re = MACProtocolUtils.toRequest(mac, bbb);
		
		System.out.println(xml);
		System.out.println("*******返回报文：");

		try {
			Socket socket = new Socket("198.1.246.206", 9915);//ip为国结ip,端口号与以往不一致，为9915
			boolean closed = socket.isClosed();
//						System.out.println(closed);
//						System.out.println(socket.isConnected());
//						System.out.println(socket.isBound());
			InputStream inputStream = socket.getInputStream();
			OutputStream outputStream = socket.getOutputStream();
			outputStream.write(re);
			
			byte[] res = MACProtocolUtils.toResponse(inputStream);
			String response = new String(res,"GBK");
			System.out.println(response);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	//行内汇划
	@Test
	public void Test034() throws Exception{
		
		/* 解析请求报文 */
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		
		//拼接输出的body
		Out02600ReqBodyBean outBody = new Out02600ReqBodyBean();
		outBody.setBUSI_TYPE("2");//业务类型
		outBody.setDB_CR_FLAG("2");//借贷标志
		outBody.setAGENT_INST_NO("");//被代理机构号
		outBody.setSEND_BANK_NO("313124000341");//提出行行号
		outBody.setSEND_BANK_NAME("唐山银行股份有限公司裕华支行");//提出行名称
		outBody.setPAY_ACCT_NO("6231930000002000099");//付款人账号
		outBody.setPAY_NAME("张建钢");//付款人名称
		outBody.setDRAW_COND("0");//支取条件
		outBody.setRECV_BANK_NO("313124000448");//提入行行号
		outBody.setRECV_BANK_NAME("唐山银行股份有限公司古冶支行");//提入行名称
		outBody.setPAYEE_ACCT_NO("6231930000002008043");//收款人账号
		outBody.setPAYEE_NAME("张媛媛");//收款人名称
		outBody.setBILL_TYPE("308");//票据种类
		outBody.setBILL_NO("");//票据号码
		outBody.setPIN("");//支付密码
		outBody.setAVAL_BAL("29577860.90");//可用余额
		outBody.setCUEERNCY("00");//币种
		outBody.setAMT("1234.56");//金    额
		outBody.setBILL_DATE("");//出票日期
		outBody.setNOTE_DATE("");//提示付款日期
		outBody.setENDOR_NUM("");//背书次数
		outBody.setREMARK("");//备注
		outBody.setPURPOS("工资");//用途
		outBody.setTRN_REASON("");//转账原因
		outBody.setORIG_CERT_TYPE("");//原始凭证种类
		outBody.setATTACH_NUM("");//张数
		outBody.setAPPD_TEXT("");//附加信息
		outBody.setSUMM_CODE("6");//摘要代码
		outBody.setSUMM_TEXT("行内汇划");//摘要代码
		outBody.setNEXT_DAY_SEND_FLAG("0");//摘要内容次日发送标志
		outBody.setTIMED_SEND_TIME("");//定时发送时间
		outBody.setTASK_IDTRNS("ZHFW17050405310014699997");
		
		Out02600ReqBodyDetailBean out02600detail = new Out02600ReqBodyDetailBean();
		out02600detail.setENDOR_NAME("");//
		outBody.setDETAIL(out02600detail);//
		
		//拼接输出的head
		OutHeadBean outHeadBean = new OutHeadBean();
		outHeadBean.setTRAN_CODE("02600");//前置交易码
		outHeadBean.setCARD_ACCP_TERM_ID("000C0011");//机器编码
		outHeadBean.setMER_NO("123456789012345");//商户号
		outHeadBean.setINST_NO("053600150");//机构号
		outHeadBean.setTELLER_NO("C0011");//柜员号
		outHeadBean.setCHANNEL("0035");//渠道号
		outHeadBean.setCHL_TRAN_CODE("");//渠道交易码
		outHeadBean.setSUP_TELLER_NO("");//授权柜员号
		outHeadBean.setTERM_JRNL_NO("");//报文流水号
		outHeadBean.setTRAN_DATE(DateUtil.getDateTime("yyyyMMdd"));//交易日期
		outHeadBean.setTRAN_TIME(DateUtil.getDateTime("HHmmss"));//交易时间
		//拼接root类
		Out02600ReqBean out02600ReqBean = new Out02600ReqBean();
		out02600ReqBean.setBODY(outBody);
		out02600ReqBean.setHeadBean(outHeadBean);
		
		reqXs.alias("ROOT", Out02600ReqBean.class);
		reqXs.alias("HEAD", OutHeadBean.class);
		reqXs.alias("BODY", Out02600ReqBodyBean.class);
		
		System.out.println("*******请求报文：");
		
		String xml = reqXs.toXML(out02600ReqBean);
		xml =  "<?xml version=\"1.0\" encoding=\"GBK\" ?>\n"+xml;
		byte[] bbb = xml.getBytes("GBK");
		String mac = ConfigReader.getConfig(ENCRYPT_MAC_ZAK2NAME);
		byte[] re = MACProtocolUtils.toRequest(mac, bbb);
		
		System.out.println(xml);
		System.out.println("*******返回报文：");

		try {
			Socket socket = new Socket("198.1.246.206", 9915);//ip为国结ip,端口号与以往不一致，为9915
			boolean closed = socket.isClosed();
			InputStream inputStream = socket.getInputStream();
			OutputStream outputStream = socket.getOutputStream();
			outputStream.write(re);
			
			byte[] res = MACProtocolUtils.toResponse(inputStream);
			String response = new String(res,"GBK");
			System.out.println(response);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	//
	@Test
	public void Test035() throws Exception{
		
		/* 解析请求报文 */
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		
		//拼接输出的body
		Out01118ReqBodyBean outBody = new Out01118ReqBodyBean();
		outBody.setSCH_INST_NO("054600162");//机构 号
		
		//拼接输出的head
		OutHeadBean outHeadBean = new OutHeadBean();
		outHeadBean.setTRAN_CODE("01118");//前置交易码
		outHeadBean.setCARD_ACCP_TERM_ID("000C0011");//机器编码
		outHeadBean.setMER_NO("123456789012345");//商户号
		outHeadBean.setINST_NO("053600150");//机构号
		outHeadBean.setTELLER_NO("C0011");//柜员号
		outHeadBean.setCHANNEL("0035");//渠道号
		outHeadBean.setCHL_TRAN_CODE("");//渠道交易码
		outHeadBean.setSUP_TELLER_NO("");//授权柜员号
		outHeadBean.setTERM_JRNL_NO("");//报文流水号
		outHeadBean.setTRAN_DATE(DateUtil.getDateTime("yyyyMMdd"));//交易日期
		outHeadBean.setTRAN_TIME(DateUtil.getDateTime("HHmmss"));//交易时间
		//拼接root类
		Out01118ReqBean out01118ReqBean = new Out01118ReqBean();
		out01118ReqBean.setBODY(outBody);
		out01118ReqBean.setHeadBean(outHeadBean);
		
		reqXs.alias("ROOT", Out01118ReqBean.class);
		reqXs.alias("HEAD", OutHeadBean.class);
		reqXs.alias("BODY", Out01118ReqBodyBean.class);
		
		System.out.println("*******请求报文：");
		
		String xml = reqXs.toXML(out01118ReqBean);
		xml =  "<?xml version=\"1.0\" encoding=\"GBK\" ?>\n"+xml;
		byte[] bbb = xml.getBytes("GBK");
		String mac = ConfigReader.getConfig(ENCRYPT_MAC_ZAK2NAME);
		byte[] re = MACProtocolUtils.toRequest(mac, bbb);
		
		System.out.println(xml);
		System.out.println("*******返回报文：");

		try {
			Socket socket = new Socket("198.1.246.206", 9915);//ip为国结ip,端口号与以往不一致，为9915
			boolean closed = socket.isClosed();
//						System.out.println(closed);
//						System.out.println(socket.isConnected());
//						System.out.println(socket.isBound());
			InputStream inputStream = socket.getInputStream();
			OutputStream outputStream = socket.getOutputStream();
			outputStream.write(re);
			
			byte[] res = MACProtocolUtils.toResponse(inputStream);
			String response = new String(res,"GBK");
			System.out.println(response);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//单位卡IC卡验证（核心03453）-前置03453
	@Test
	public void Test036() throws Exception{
		
		/* 解析请求报文 */
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		String pinKeyName=ConfigReader.getConfig(ENCRYPT_PIN_KEYNAME);//键盘工作密钥标记
		String acctNo="6232655800000000590";//卡号
		String pin="258258";//银行卡密码
		String password=EncryptUtils.encryPin433(pinKeyName,pin,acctNo);
//		System.out.println("+++++++++password:"+password);
		String tranKeyFullName=ConfigReader.getConfig(ENCRYTP_PIN_TRANKEYFULLNAME);//转加密密钥标记
		String tranPwd=EncryptUtils.tranPin310(pinKeyName, tranKeyFullName, acctNo, password);
		
		//拼接输出的body
		Out03453ReqBodyBean outBody = new Out03453ReqBodyBean();
//		outBody.setCARD_NO("6232655800000000582");
//		outBody.setPASSWD(tranPwd);
//		outBody.setTRACK2_INFO("6232655800000000582=26122200000000000");
//		outBody.setFALL_BACK_FLAG("1");
//		outBody.setARQC("");
//		outBody.setARQC_SRC_DATA("");
//		outBody.setTERM_RESULT("");
//		outBody.setISSUER_APP_DATA("");
//		outBody.setICCARD_DATA("");
//		outBody.setDATE_EXPR("");
//		outBody.setARQC("A41A971ED00C827E");
//		outBody.setARQC_SRC_DATA("00000000000400000000000001568020040840015617050517E0D6A5297C0000BB03A02000");
//		outBody.setTERM_RESULT("8020040840");
//		outBody.setISSUER_APP_DATA("07010103A02000010A0100000000007EFA1587");
//		outBody.setICCARD_DATA("01");
//		outBody.setDATE_EXPR("261231");
		
//		outBody.setCARD_NO("6232655800000000608");
//		outBody.setPASSWD(tranPwd);
//		outBody.setTRACK2_INFO("6232655800000000608=26122200000000000");
//		outBody.setFALL_BACK_FLAG("1");
//		outBody.setARQC("0F53B512B7ED07C1");
//		outBody.setARQC_SRC_DATA("000000000004000000000000015680200408400156170505176E89AAE47C00019403A02000");
//		outBody.setTERM_RESULT("8020040840");
//		outBody.setISSUER_APP_DATA("07010103A02000010A010000000000E8F6D576");
//		outBody.setICCARD_DATA("01");
//		outBody.setDATE_EXPR("261231");
		
		outBody.setCARD_NO("6232655800000000590");
		outBody.setPASSWD(tranPwd);
		outBody.setTRACK2_INFO("6232655800000000590=26122200000000000");
		outBody.setFALL_BACK_FLAG("1");
		outBody.setARQC("6566C6C6DDD607D1");
		outBody.setARQC_SRC_DATA("000000000004000000000000015680200408400156170505171A7026317C0000B903A02800");
		outBody.setTERM_RESULT("8020040840");
		outBody.setISSUER_APP_DATA("07010103A02800010A0100000000005935BF9F");
		outBody.setICCARD_DATA("01");
		outBody.setDATE_EXPR("261231");
		
		//拼接输出的head
		OutHeadBean outHeadBean = new OutHeadBean();
		outHeadBean.setTRAN_CODE("03453");//前置交易码
		outHeadBean.setCARD_ACCP_TERM_ID("000C0011");//机器编码
		outHeadBean.setMER_NO("123456789012345");//商户号
		outHeadBean.setINST_NO("053600150");//机构号
		outHeadBean.setTELLER_NO("C0011");//柜员号
		outHeadBean.setCHANNEL("0035");//渠道号
		outHeadBean.setCHL_TRAN_CODE("");//渠道交易码
		outHeadBean.setSUP_TELLER_NO("");//授权柜员号
		outHeadBean.setTERM_JRNL_NO("");//报文流水号
		outHeadBean.setTRAN_DATE(DateUtil.getDateTime("yyyyMMdd"));//交易日期
		outHeadBean.setTRAN_TIME(DateUtil.getDateTime("HHmmss"));//交易时间
		//拼接root类
		Out03453ReqBean out03453ReqBean = new Out03453ReqBean();
		out03453ReqBean.setBody(outBody);
		out03453ReqBean.setHeadBean(outHeadBean);
		
		reqXs.alias("ROOT", Out03453ReqBean.class);
		reqXs.alias("HEAD", OutHeadBean.class);
		reqXs.alias("BODY", Out03453ReqBodyBean.class);
		
		System.out.println("*******请求报文：");
		
		String xml = reqXs.toXML(out03453ReqBean);
		xml =  "<?xml version=\"1.0\" encoding=\"GBK\" ?>\n"+xml;
		byte[] bbb = xml.getBytes("GBK");
		String mac = ConfigReader.getConfig(ENCRYPT_MAC_ZAK2NAME);
		byte[] re = MACProtocolUtils.toRequest(mac, bbb);
		
		System.out.println(xml);
		System.out.println("*******返回报文：");

		try {
			Socket socket = new Socket("198.1.246.206", 9915);//ip为国结ip,端口号与以往不一致，为9915
			boolean closed = socket.isClosed();
			InputStream inputStream = socket.getInputStream();
			OutputStream outputStream = socket.getOutputStream();
			outputStream.write(re);
			
			byte[] res = MACProtocolUtils.toResponse(inputStream);
			String response = new String(res,"GBK");
			System.out.println(response);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			Socket socket = new Socket("198.1.246.206", 9915);//ip为国结ip,端口号与以往不一致，为9915
			boolean closed = socket.isClosed();
			InputStream inputStream = socket.getInputStream();
			OutputStream outputStream = socket.getOutputStream();
			outputStream.write(re);
			
			byte[] res = MACProtocolUtils.toResponse(inputStream);
			String response = new String(res,"GBK");
			System.out.println(response);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void Test07602() throws Exception{
		
		/* 解析请求报文 */
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		
		//拼接输出的body
		Out07602ReqBodyBean outBody = new Out07602ReqBodyBean();
//		outBody.setAID("A000000333010101");
//		outBody.setAPP_ACCT_SEQ("01");
//		outBody.setARQC("3518D038C8E4F756");
//		outBody.setARQC_SRC_DATA("000000000004000000000000015680200408400156170503177A57B02C7C00042103A0A000");
//		outBody.setCARD_NO("6231931200000000096");
//		outBody.setCARD_POV("241231");
//		outBody.setIC_CHL("");
//		outBody.setISS_APP_DATA("07010103A0A000010A010000030000B688563F");
//		outBody.setTERM_CHK_VALUE("8020040840");
		
//		outBody.setAID("A000000333010101");
//		outBody.setAPP_ACCT_SEQ("01");
//		outBody.setARQC("C9A973B9712BE763");
//		outBody.setARQC_SRC_DATA("000000000004000000000000015680000408400156170505179BCBA9D07C00018603A0A000");
//		outBody.setCARD_NO("6231930000002000099");
//		outBody.setCARD_POV("241231");
//		outBody.setIC_CHL("");
//		outBody.setISS_APP_DATA("07010103A0A000010A010000000000F6263245");
//		outBody.setTERM_CHK_VALUE("8000040840");
		
		outBody.setAID("A000000333010101");
		outBody.setAPP_ACCT_SEQ("01");
		outBody.setARQC("47438054E50B487C");
		outBody.setARQC_SRC_DATA("00000000000400000000000001568000040840015617050517D15032807C00010C03A0A010");
		outBody.setCARD_NO("6231930000000001099");
		outBody.setCARD_POV("241231");
		outBody.setIC_CHL("");
		outBody.setISS_APP_DATA("07010103A0A010010A01000001511066667B40");
		outBody.setTERM_CHK_VALUE("8000040840");
		//拼接输出的head
		OutHeadBean outHeadBean = new OutHeadBean();
		outHeadBean.setTRAN_CODE("07602");//前置交易码
		outHeadBean.setCARD_ACCP_TERM_ID("000C0011");//机器编码
		outHeadBean.setMER_NO("123456789012345");//商户号
		outHeadBean.setINST_NO("053600150");//机构号
		outHeadBean.setTELLER_NO("C0011");//柜员号
		outHeadBean.setCHANNEL("0035");//渠道号
		outHeadBean.setCHL_TRAN_CODE("");//渠道交易码
		outHeadBean.setSUP_TELLER_NO("");//授权柜员号
		outHeadBean.setTERM_JRNL_NO("");//报文流水号
		outHeadBean.setTRAN_DATE(DateUtil.getDateTime("yyyyMMdd"));//交易日期
		outHeadBean.setTRAN_TIME(DateUtil.getDateTime("HHmmss"));//交易时间
		//拼接root类
		Out07602ReqBean out07602ReqBean = new Out07602ReqBean();
		out07602ReqBean.setBODY(outBody);
		out07602ReqBean.setHeadBean(outHeadBean);
		
		reqXs.alias("ROOT", Out07602ReqBean.class);
		reqXs.alias("HEAD", OutHeadBean.class);
		reqXs.alias("BODY", Out07602ReqBodyBean.class);
		
		System.out.println("*******请求报文：");
		
		String xml = reqXs.toXML(out07602ReqBean);
		xml =  "<?xml version=\"1.0\" encoding=\"GBK\" ?>\n"+xml;
		byte[] bbb = xml.getBytes("GBK");
		String mac = ConfigReader.getConfig(ENCRYPT_MAC_ZAK2NAME);
		byte[] re = MACProtocolUtils.toRequest(mac, bbb);
		
		System.out.println(xml);
		System.out.println("*******返回报文：");

		try {
			Socket socket = new Socket("198.1.246.206", 9915);//ip为国结ip,端口号与以往不一致，为9915
			boolean closed = socket.isClosed();
//						System.out.println(closed);
//						System.out.println(socket.isConnected());
//						System.out.println(socket.isBound());
			InputStream inputStream = socket.getInputStream();
			OutputStream outputStream = socket.getOutputStream();
			outputStream.write(re);
			
			byte[] res = MACProtocolUtils.toResponse(inputStream);
			String response = new String(res,"GBK");
			System.out.println(response);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 查询绑定手机号
	 * @throws Exception
	 */
	@Test
	public void Test03740() throws Exception{
		
		/* 解析请求报文 */
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		
		//拼接输出的body
		Out03740ReqBodyBean outBody = new Out03740ReqBodyBean();
		outBody.setQRY_OPTION("0");
		outBody.setCARD_NO("6231930000000601500");
		outBody.setMP_NO("");
		//拼接输出的head
		OutHeadBean outHeadBean = new OutHeadBean();
		outHeadBean.setTRAN_CODE("03740");//前置交易码
		outHeadBean.setCARD_ACCP_TERM_ID("000C0011");//机器编码
		outHeadBean.setMER_NO("123456789012345");//商户号
		outHeadBean.setINST_NO("053600150");//机构号
		outHeadBean.setTELLER_NO("C0011");//柜员号
		outHeadBean.setCHANNEL("0035");//渠道号
		outHeadBean.setCHL_TRAN_CODE("");//渠道交易码
		outHeadBean.setSUP_TELLER_NO("");//授权柜员号
		outHeadBean.setTERM_JRNL_NO("");//报文流水号
		outHeadBean.setTRAN_DATE(DateUtil.getDateTime("yyyyMMdd"));//交易日期
		outHeadBean.setTRAN_TIME(DateUtil.getDateTime("HHmmss"));//交易时间
		//拼接root类
		Out03740ReqBean out03740ReqBean = new Out03740ReqBean();
		out03740ReqBean.setBODY(outBody);
		out03740ReqBean.setHeadBean(outHeadBean);
		
		reqXs.alias("ROOT", Out03740ReqBean.class);
		reqXs.alias("HEAD", OutHeadBean.class);
		reqXs.alias("BODY", Out03740ReqBodyBean.class);
		
		System.out.println("*******请求报文：");
		
		String xml = reqXs.toXML(out03740ReqBean);
		xml =  "<?xml version=\"1.0\" encoding=\"GBK\" ?>\n"+xml;
		byte[] bbb = xml.getBytes("GBK");
		String mac = ConfigReader.getConfig(ENCRYPT_MAC_ZAK2NAME);
		byte[] re = MACProtocolUtils.toRequest(mac, bbb);
		
		System.out.println(xml);
		System.out.println("*******返回报文：");

		try {
			Socket socket = new Socket("198.1.246.206", 9915);//ip为国结ip,端口号与以往不一致，为9915
			boolean closed = socket.isClosed();
			InputStream inputStream = socket.getInputStream();
			OutputStream outputStream = socket.getOutputStream();
			outputStream.write(re);
			
			byte[] res = MACProtocolUtils.toResponse(inputStream);
			String response = new String(res,"GBK");
			System.out.println(response);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void Test037() throws Exception{
		
		/* 解析请求报文 */
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		
		//拼接输出的body
		Out02954ReqBodyBean outBody = new Out02954ReqBodyBean();
		outBody.setCARD_NO("6232655800000000665");//卡号
		outBody.setOPER_CHOOSE("1");//操作选择
		outBody.setOTHER_ACCT_NO("6232655800000000616");//对方账号
		
		//拼接输出的head
		OutHeadBean outHeadBean = new OutHeadBean();
		outHeadBean.setTRAN_CODE("02954");//前置交易码
		outHeadBean.setCARD_ACCP_TERM_ID("000C0011");//机器编码
		outHeadBean.setMER_NO("123456789012345");//商户号
		outHeadBean.setINST_NO("053600150");//机构号
		outHeadBean.setTELLER_NO("C0011");//柜员号
		outHeadBean.setCHANNEL("0035");//渠道号
		outHeadBean.setCHL_TRAN_CODE("");//渠道交易码
		outHeadBean.setSUP_TELLER_NO("");//授权柜员号
		outHeadBean.setTERM_JRNL_NO("");//报文流水号
		outHeadBean.setTRAN_DATE(DateUtil.getDateTime("yyyyMMdd"));//交易日期
		outHeadBean.setTRAN_TIME(DateUtil.getDateTime("HHmmss"));//交易时间
		//拼接root类
		Out02954ReqBean out02954ReqBean = new Out02954ReqBean();
		out02954ReqBean.setBODY(outBody);
		out02954ReqBean.setHeadBean(outHeadBean);
		
		reqXs.alias("ROOT", Out02954ReqBean.class);
		reqXs.alias("HEAD", OutHeadBean.class);
		reqXs.alias("BODY", Out02954ReqBodyBean.class);
		
		System.out.println("*******请求报文：");
		
		String xml = reqXs.toXML(out02954ReqBean);
		xml =  "<?xml version=\"1.0\" encoding=\"GBK\" ?>\n"+xml;
		byte[] bbb = xml.getBytes("GBK");
		String mac = ConfigReader.getConfig(ENCRYPT_MAC_ZAK2NAME);
		byte[] re = MACProtocolUtils.toRequest(mac, bbb);
		
		System.out.println(xml);
		System.out.println("*******返回报文：");

		try {
			Socket socket = new Socket("198.1.246.206", 9915);//ip为国结ip,端口号与以往不一致，为9915
			boolean closed = socket.isClosed();
//						System.out.println(closed);
//						System.out.println(socket.isConnected());
//						System.out.println(socket.isBound());
			InputStream inputStream = socket.getInputStream();
			OutputStream outputStream = socket.getOutputStream();
			outputStream.write(re);
			
			byte[] res = MACProtocolUtils.toResponse(inputStream);
			String response = new String(res,"GBK");
			System.out.println(response);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 二类账户限额查询-前置02781
	 * @throws Exception
	 */
	@Test
	public void Test038() throws Exception{
		/* 解析请求报文 */
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		
		//拼接输出的body
		Out02781ReqBodyBean outBody = new Out02781ReqBodyBean();
		outBody.setACCT_NO("6231931200000000100");//账号
		outBody.setDB_CR_FLAG("1");
		outBody.setOPPO_ACCT_NO("6231931200000000062");
		outBody.setTRAN_AMT("200");
		//拼接输出的head
		OutHeadBean outHeadBean = new OutHeadBean();
		outHeadBean.setTRAN_CODE("02781");//前置交易码
		outHeadBean.setCARD_ACCP_TERM_ID("000C0001");//机器编码
		outHeadBean.setMER_NO("123456789012345");//商户号
		outHeadBean.setINST_NO("051000129");//机构号
		outHeadBean.setTELLER_NO("C0010");//柜员号
		outHeadBean.setCHANNEL("0035");//渠道号
		outHeadBean.setCHL_TRAN_CODE("");//渠道交易码
		outHeadBean.setSUP_TELLER_NO("");//授权柜员号
		outHeadBean.setTERM_JRNL_NO("");//报文流水号
		outHeadBean.setTRAN_DATE(DateUtil.getDateTime("yyyyMMdd"));//交易日期
		outHeadBean.setTRAN_TIME(DateUtil.getDateTime("HH:mm:ss"));//交易时间
		
		//拼接root类
		Out02781ReqBean out02781ReqBean = new Out02781ReqBean();
		out02781ReqBean.setBODY(outBody);
		out02781ReqBean.setHeadBean(outHeadBean);
		
		reqXs.alias("ROOT", Out02781ReqBean.class);
		reqXs.alias("HEAD", OutHeadBean.class);
		reqXs.alias("BODY", Out02781ReqBodyBean.class);
		
		System.out.println("*******请求报文：");
		String xml = reqXs.toXML(out02781ReqBean);
		xml =  "<?xml version=\"1.0\" encoding=\"GBK\" ?>\n"+xml;
		byte[] bbb = xml.getBytes("GBK");
		String mac = ConfigReader.getConfig(ENCRYPT_MAC_ZAK2NAME);
		
		byte[] re = MACProtocolUtils.toRequest(mac, bbb);
		
		System.out.println(xml);
		System.out.println("*******返回报文：");
		try {
			Socket socket = new Socket("198.1.246.206", 9915);
			boolean closed = socket.isClosed();
//			System.out.println(closed);
//			System.out.println(socket.isConnected());
//			System.out.println(socket.isBound());
			InputStream inputStream = socket.getInputStream();
			OutputStream outputStream = socket.getOutputStream();
			outputStream.write(re);
			
			byte[] res = MACProtocolUtils.toResponse(inputStream);
			String response = new String(res,"GBK");
			System.out.println(response);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 唐豆信息查询【02217】-前置07515
	 * @throws Exception
	 */
	@Test
	public void Test07515() throws Exception{
		/* 解析请求报文 */
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		
		//拼接输出的body
		Out07515ReqBodyBean outBody = new Out07515ReqBodyBean();
		outBody.setACCT_NO("053600150000600252297");//账号
		//拼接输出的head
		OutHeadBean outHeadBean = new OutHeadBean();
		outHeadBean.setTRAN_CODE("07515");//前置交易码
		outHeadBean.setCARD_ACCP_TERM_ID("000C0001");//机器编码
		outHeadBean.setMER_NO("123456789012345");//商户号
		outHeadBean.setINST_NO("053600150");//机构号
		outHeadBean.setTELLER_NO("C0011");//柜员号
		outHeadBean.setCHANNEL("0035");//渠道号
		outHeadBean.setCHL_TRAN_CODE("");//渠道交易码
		outHeadBean.setSUP_TELLER_NO("");//授权柜员号
		outHeadBean.setTERM_JRNL_NO("");//报文流水号
		outHeadBean.setTRAN_DATE(DateUtil.getDateTime("yyyyMMdd"));//交易日期
		outHeadBean.setTRAN_TIME(DateUtil.getDateTime("HH:mm:ss"));//交易时间
		
		//拼接root类
		Out07515ReqBean out07515ReqBean = new Out07515ReqBean();
		out07515ReqBean.setBody(outBody);
		out07515ReqBean.setHeadBean(outHeadBean);
		
		reqXs.alias("ROOT", Out07515ReqBean.class);
		reqXs.alias("HEAD", OutHeadBean.class);
		reqXs.alias("BODY", Out07515ReqBodyBean.class);
		
		System.out.println("*******请求报文：");
		String xml = reqXs.toXML(out07515ReqBean);
		xml =  "<?xml version=\"1.0\" encoding=\"GBK\" ?>\n"+xml;
		byte[] bbb = xml.getBytes("GBK");
		String mac = ConfigReader.getConfig(ENCRYPT_MAC_ZAK2NAME);
		
		byte[] re = MACProtocolUtils.toRequest(mac, bbb);
		
		System.out.println(xml);
		System.out.println("*******返回报文：");
		try {
			Socket socket = new Socket("198.1.246.206", 9915);
			boolean closed = socket.isClosed();
//			System.out.println(closed);
//			System.out.println(socket.isConnected());
//			System.out.println(socket.isBound());
			InputStream inputStream = socket.getInputStream();
			OutputStream outputStream = socket.getOutputStream();
			outputStream.write(re);
			
			byte[] res = MACProtocolUtils.toResponse(inputStream);
			String response = new String(res,"GBK");
			System.out.println(response);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 电子账户子账户列表查询【35109】（直连电子账户）-前置07819
	 * @throws Exception
	 */
	@Test
	public void Test07819() throws Exception{
		/* 解析请求报文 */
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		
		//拼接输出的body
		Out07819ReqBodyBean outBody = new Out07819ReqBodyBean();
		outBody.setCARD_NO("");//卡号
		outBody.setCUST_NO("114521936");//客户号
		//拼接输出的head
		OutHeadBean outHeadBean = new OutHeadBean();
		outHeadBean.setTRAN_CODE("07819");//前置交易码
		outHeadBean.setCARD_ACCP_TERM_ID("000C0011");//机器编码
		outHeadBean.setMER_NO("123456789012345");//商户号
		outHeadBean.setINST_NO("053600150");//机构号
		outHeadBean.setTELLER_NO("C0011");//柜员号
		outHeadBean.setCHANNEL("0035");//渠道号
		outHeadBean.setCHL_TRAN_CODE("");//渠道交易码
		outHeadBean.setSUP_TELLER_NO("");//授权柜员号
		outHeadBean.setTERM_JRNL_NO("");//报文流水号
		outHeadBean.setTRAN_DATE(DateUtil.getDateTime("yyyyMMdd"));//交易日期
		outHeadBean.setTRAN_TIME(DateUtil.getDateTime("HH:mm:ss"));//交易时间
		
		//拼接root类
		Out07819ReqBean out07819ReqBean = new Out07819ReqBean();
		out07819ReqBean.setBody(outBody);
		out07819ReqBean.setHeadBean(outHeadBean);
		
		reqXs.alias("ROOT", Out07819ReqBean.class);
		reqXs.alias("HEAD", OutHeadBean.class);
		reqXs.alias("BODY", Out07819ReqBodyBean.class);
		
		System.out.println("*******请求报文：");
		String xml = reqXs.toXML(out07819ReqBean);
		xml =  "<?xml version=\"1.0\" encoding=\"GBK\" ?>\n"+xml;
		byte[] bbb = xml.getBytes("GBK");
		String mac = ConfigReader.getConfig(ENCRYPT_MAC_ZAK2NAME);
		
		byte[] re = MACProtocolUtils.toRequest(mac, bbb);
		
		System.out.println(xml);
		System.out.println("*******返回报文：");
		try {
			Socket socket = new Socket("198.1.246.206", 9915);
			boolean closed = socket.isClosed();
//			System.out.println(closed);
//			System.out.println(socket.isConnected());
//			System.out.println(socket.isBound());
			InputStream inputStream = socket.getInputStream();
			OutputStream outputStream = socket.getOutputStream();
			outputStream.write(re);
			
			byte[] res = MACProtocolUtils.toResponse(inputStream);
			String response = new String(res,"GBK");
			System.out.println(response);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 电子账户子账户列表查询【35109】（直连电子账户）-前置07819
	 * @throws Exception
	 */
	@Test
	public void Test03510() throws Exception{
		/* 解析请求报文 */
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		
		//拼接输出的body
		Out07819ReqBodyBean outBody = new Out07819ReqBodyBean();
		outBody.setCARD_NO("6231930008000170277");//卡号
		//拼接输出的head
		OutHeadBean outHeadBean = new OutHeadBean();
		outHeadBean.setTRAN_CODE("07819");//前置交易码
		outHeadBean.setCARD_ACCP_TERM_ID("000C0011");//机器编码
		outHeadBean.setMER_NO("123456789012345");//商户号
		outHeadBean.setINST_NO("053600150");//机构号
		outHeadBean.setTELLER_NO("C0011");//柜员号
		outHeadBean.setCHANNEL("0035");//渠道号
		outHeadBean.setCHL_TRAN_CODE("");//渠道交易码
		outHeadBean.setSUP_TELLER_NO("");//授权柜员号
		outHeadBean.setTERM_JRNL_NO("");//报文流水号
		outHeadBean.setTRAN_DATE(DateUtil.getDateTime("yyyyMMdd"));//交易日期
		outHeadBean.setTRAN_TIME(DateUtil.getDateTime("HH:mm:ss"));//交易时间
		
		//拼接root类
		Out07819ReqBean out07819ReqBean = new Out07819ReqBean();
		out07819ReqBean.setBody(outBody);
		out07819ReqBean.setHeadBean(outHeadBean);
		
		reqXs.alias("ROOT", Out07819ReqBean.class);
		reqXs.alias("HEAD", OutHeadBean.class);
		reqXs.alias("BODY", Out07819ReqBodyBean.class);
		
		System.out.println("*******请求报文：");
		String xml = reqXs.toXML(out07819ReqBean);
		xml =  "<?xml version=\"1.0\" encoding=\"GBK\" ?>\n"+xml;
		byte[] bbb = xml.getBytes("GBK");
		String mac = ConfigReader.getConfig(ENCRYPT_MAC_ZAK2NAME);
		
		byte[] re = MACProtocolUtils.toRequest(mac, bbb);
		
		System.out.println(xml);
		System.out.println("*******返回报文：");
		try {
			Socket socket = new Socket("198.1.246.206", 9915);
			boolean closed = socket.isClosed();
//			System.out.println(closed);
//			System.out.println(socket.isConnected());
//			System.out.println(socket.isBound());
			InputStream inputStream = socket.getInputStream();
			OutputStream outputStream = socket.getOutputStream();
			outputStream.write(re);
			
			byte[] res = MACProtocolUtils.toResponse(inputStream);
			String response = new String(res,"GBK");
			System.out.println(response);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 转入唐宝账户查询【55060】-前置07498
	 * @throws Exception
	 */
	@Test
	public void Test07498() throws Exception{
		/* 解析请求报文 */
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		
		//拼接输出的body
		Out07498ReqBodyBean outBody = new Out07498ReqBodyBean();
		outBody.setOPER_CHOOSE("1");//操作选择
		outBody.setID_TYPE("");//证件类型
		outBody.setID_NO("");//证件号码
		outBody.setCARD_NO("6231930000002000156");//卡号
		outBody.setSUB_ACCT_NO("");//子账号
		outBody.setCUST_NO("");//客户号
		outBody.setPROD_CODE("");//产品代码
		outBody.setTRAN_AMT("1000");//转入金额
		//拼接输出的head
		OutHeadBean outHeadBean = new OutHeadBean();
		outHeadBean.setTRAN_CODE("07498");//前置交易码
		outHeadBean.setCARD_ACCP_TERM_ID("000C0011");//机器编码
		outHeadBean.setMER_NO("123456789012345");//商户号
		outHeadBean.setINST_NO("053600150");//机构号
		outHeadBean.setTELLER_NO("C0011");//柜员号
		outHeadBean.setCHANNEL("0035");//渠道号
		outHeadBean.setCHL_TRAN_CODE("");//渠道交易码
		outHeadBean.setSUP_TELLER_NO("");//授权柜员号
		outHeadBean.setTERM_JRNL_NO("");//报文流水号
		outHeadBean.setTRAN_DATE(DateUtil.getDateTime("yyyyMMdd"));//交易日期
		outHeadBean.setTRAN_TIME(DateUtil.getDateTime("HH:mm:ss"));//交易时间
		
		//拼接root类
		Out07498ReqBean out07498ReqBean = new Out07498ReqBean();
		out07498ReqBean.setBody(outBody);
		out07498ReqBean.setHeadBean(outHeadBean);
		
		reqXs.alias("ROOT", Out07498ReqBean.class);
		reqXs.alias("HEAD", OutHeadBean.class);
		reqXs.alias("BODY", Out07498ReqBodyBean.class);
		
		System.out.println("*******请求报文：");
		String xml = reqXs.toXML(out07498ReqBean);
		xml =  "<?xml version=\"1.0\" encoding=\"GBK\" ?>\n"+xml;
		byte[] bbb = xml.getBytes("GBK");
		String mac = ConfigReader.getConfig(ENCRYPT_MAC_ZAK2NAME);
		
		byte[] re = MACProtocolUtils.toRequest(mac, bbb);
		
		System.out.println(xml);
		System.out.println("*******返回报文：");
		try {
			Socket socket = new Socket("198.1.246.206", 9915);
			boolean closed = socket.isClosed();
			InputStream inputStream = socket.getInputStream();
			OutputStream outputStream = socket.getOutputStream();
			outputStream.write(re);
			
			byte[] res = MACProtocolUtils.toResponse(inputStream);
			String response = new String(res,"GBK");
			System.out.println(response);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 唐宝x号转入【79100】-前置07499
	 * @throws Exception
	 */
	@Test
	public void Test07499() throws Exception{
		/* 解析请求报文 */
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		
		//拼接输出的body
		Out07499ReqBodyBean outBody = new Out07499ReqBodyBean();
		outBody.setCUST_NO("");//客户号
		outBody.setID_TYPE("10100");//证件类别
		outBody.setID_NO("140624199206180015");//证件号码
		outBody.setPROD_CODE("");//产品代码
		outBody.setCARD_NO("6231930000000902353");//卡号
		outBody.setPASSWORD("");//密码
		outBody.setSUB_ACCT_NO("");//子账号
		outBody.setTRAN_AMT("");//转入金额
		//拼接输出的head
		OutHeadBean outHeadBean = new OutHeadBean();
		outHeadBean.setTRAN_CODE("07499");//前置交易码
		outHeadBean.setCARD_ACCP_TERM_ID("000C0011");//机器编码
		outHeadBean.setMER_NO("123456789012345");//商户号
		outHeadBean.setINST_NO("053600150");//机构号
		outHeadBean.setTELLER_NO("C0011");//柜员号
		outHeadBean.setCHANNEL("0035");//渠道号
		outHeadBean.setCHL_TRAN_CODE("");//渠道交易码
		outHeadBean.setSUP_TELLER_NO("");//授权柜员号
		outHeadBean.setTERM_JRNL_NO("");//报文流水号
		outHeadBean.setTRAN_DATE(DateUtil.getDateTime("yyyyMMdd"));//交易日期
		outHeadBean.setTRAN_TIME(DateUtil.getDateTime("HH:mm:ss"));//交易时间
		
		//拼接root类
		Out07499ReqBean out07499ReqBean = new Out07499ReqBean();
		out07499ReqBean.setBody(outBody);
		out07499ReqBean.setHeadBean(outHeadBean);
		
		reqXs.alias("ROOT", Out07499ReqBean.class);
		reqXs.alias("HEAD", OutHeadBean.class);
		reqXs.alias("BODY", Out07499ReqBodyBean.class);
		
		System.out.println("*******请求报文：");
		String xml = reqXs.toXML(out07499ReqBean);
		xml =  "<?xml version=\"1.0\" encoding=\"GBK\" ?>\n"+xml;
		byte[] bbb = xml.getBytes("GBK");
		String mac = ConfigReader.getConfig(ENCRYPT_MAC_ZAK2NAME);
		
		byte[] re = MACProtocolUtils.toRequest(mac, bbb);
		
		System.out.println(xml);
		System.out.println("*******返回报文：");
		try {
			Socket socket = new Socket("198.1.246.206", 9915);
			boolean closed = socket.isClosed();
//			System.out.println(closed);
//			System.out.println(socket.isConnected());
//			System.out.println(socket.isBound());
			InputStream inputStream = socket.getInputStream();
			OutputStream outputStream = socket.getOutputStream();
			outputStream.write(re);
			
			byte[] res = MACProtocolUtils.toResponse(inputStream);
			String response = new String(res,"GBK");
			System.out.println(response);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}



