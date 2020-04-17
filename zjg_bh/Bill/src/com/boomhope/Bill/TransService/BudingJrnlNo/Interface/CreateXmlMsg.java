package com.boomhope.Bill.TransService.BudingJrnlNo.Interface;

import java.util.Map;

import com.boomhope.Bill.Public.GlobalParameter;
import com.boomhope.tms.message.XStreamNameCoder;
import com.boomhope.tms.message.account.in.BCK03845ReqBean;
import com.boomhope.tms.message.account.in.BCK03845ReqBodyBean;
import com.boomhope.tms.message.in.InReqHeadBean;
import com.boomhope.tms.message.in.bck.BCK02906ReqBean;
import com.boomhope.tms.message.in.bck.BCK02906ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK03531ReqBean;
import com.boomhope.tms.message.in.bck.BCK03531ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK03532ReqBean;
import com.boomhope.tms.message.in.bck.BCK03532ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK03533ReqBean;
import com.boomhope.tms.message.in.bck.BCK03533ReqBodyBean;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XppDriver;

public class CreateXmlMsg {
	/**
	 * 请求头信息 
	 * @param tranCoede
	 * @return
	 */
	public static InReqHeadBean getInReqHeadBean(String tranCoede){
		InReqHeadBean inReqHeadBean = new InReqHeadBean();
		inReqHeadBean.setTradeCode(tranCoede);//前置交易码
		inReqHeadBean.setMachineNo(GlobalParameter.machineNo);// 设备编号
		inReqHeadBean.setBranchNo(GlobalParameter.branchNo);// 机构号
		inReqHeadBean.setMachineType(GlobalParameter.MACHINE_TYPE);// 机构号
		inReqHeadBean.setSupTellerNo(GlobalParameter.supTellerNo);// 授权柜员号
		return inReqHeadBean;
		
	}
	
	/**
	 * 卡系统-交易辅助登记
	 * @param map
	 * @return
	 */
	public static String BCK_03531(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK03531ReqBean bck03531ReqBean = new BCK03531ReqBean();
		//请求报文体
		BCK03531ReqBodyBean bck03531ReqBodyBean = new BCK03531ReqBodyBean();
		bck03531ReqBodyBean.setJRNL_NO(map.get("JRNL_NO"));//工号
		bck03531ReqBodyBean.setWORK_NO(map.get("WORK_NO"));//交易流水号
		bck03531ReqBean.setBody(bck03531ReqBodyBean);
		//请求报文头
		bck03531ReqBean.setHeadBean(getInReqHeadBean("BCK_03531"));
		
		xstream.alias("Root", BCK03531ReqBean.class);
		xstream.alias("Body", BCK03531ReqBodyBean.class);
		xstream.alias("Head", InReqHeadBean.class);
		return xstream.toXML(bck03531ReqBean);
	}
	
	
	
	/**
	 * 推荐人奖励领取【17030】前置-03532
	 * @param map
	 * @return
	 */
	public static String BCK_03532(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK03532ReqBean bck03532ReqBean = new BCK03532ReqBean();
		//请求报文体
		BCK03532ReqBodyBean bck03532ReqBodyBean = new BCK03532ReqBodyBean();
		bck03532ReqBodyBean.setACCESS_CODE(map.get("ACCESS_CODE"));//领取码
		bck03532ReqBodyBean.setCUST_NO(map.get("CUST_NO"));//客户号
		
		bck03532ReqBean.setBody(bck03532ReqBodyBean);
		//请求报文头
		bck03532ReqBean.setHeadBean(getInReqHeadBean("BCK_03532"));
		
		xstream.alias("Root", BCK03532ReqBean.class);
		xstream.alias("Body", BCK03532ReqBodyBean.class);
		xstream.alias("Head", InReqHeadBean.class);
		return xstream.toXML(bck03532ReqBean);
	}
	
	/**
	 * 推荐信息录入【17034】前置-03533
	 * @param map
	 * @return
	 */
	public static String BCK_03533(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK03533ReqBean bck03533ReqBean = new BCK03533ReqBean();
		//请求报文体
		BCK03533ReqBodyBean bck03533ReqBodyBean = new BCK03533ReqBodyBean();
		bck03533ReqBodyBean.setNAME(map.get("NAME"));//被推荐人姓名
		bck03533ReqBodyBean.setREC_NAME(map.get("REC_NAME"));//推荐人姓名
		bck03533ReqBodyBean.setREC_TEL_NO(map.get("REC_TEL_NO"));//推荐人手机号
		bck03533ReqBodyBean.setTEL_NO(map.get("TEL_NO"));//被推荐人手机号
		
		bck03533ReqBean.setBody(bck03533ReqBodyBean);
		//请求报文头
		bck03533ReqBean.setHeadBean(getInReqHeadBean("BCK_03533"));
		
		xstream.alias("Root", BCK03533ReqBean.class);
		xstream.alias("Body", BCK03533ReqBodyBean.class);
		xstream.alias("Head", InReqHeadBean.class);
		return xstream.toXML(bck03533ReqBean);
	}
	
	/**
	 * 客户账户基本信息维护【17024】前置-02906
	 * @param map
	 * @return
	 */
	public static String BCK_02906(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK02906ReqBean bck02906ReqBean = new BCK02906ReqBean();
		//请求报文体
		BCK02906ReqBodyBean bck02906ReqBodyBean = new BCK02906ReqBodyBean();
		bck02906ReqBodyBean.setQRY_TYPE(map.get("QRY_TYPE"));//查询条件
		
		bck02906ReqBean.setBody(bck02906ReqBodyBean);
		//请求报文头
		bck02906ReqBean.setHeadBean(getInReqHeadBean("BCK_02906"));
		
		xstream.alias("Root", BCK02906ReqBean.class);
		xstream.alias("Body", BCK02906ReqBodyBean.class);
		xstream.alias("Head", InReqHeadBean.class);
		return xstream.toXML(bck02906ReqBean);
	}
	/**
	 * 卡信息查询
	 * @param map
	 * @return
	 */
	public static String BCK_03845(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK03845ReqBean bck03845ReqBean = new BCK03845ReqBean();
		//请求报文体
		BCK03845ReqBodyBean bck03845ReqBodyBean = new BCK03845ReqBodyBean();
		bck03845ReqBodyBean.setCARD_NO(map.get("CARD_NO"));//卡号
		bck03845ReqBodyBean.setISPIN(map.get("ISPIN"));//是否验密
		bck03845ReqBodyBean.setPASSWD(map.get("PASSWD"));//密码
		bck03845ReqBodyBean.setSUB_ACCT_NO(map.get("SUB_ACCT_NO"));//子帐号
		bck03845ReqBean.setBody(bck03845ReqBodyBean);
		//请求报文头
		bck03845ReqBean.setHeadBean(getInReqHeadBean("BCK_03845"));
		
		xstream.alias("Root", BCK03845ReqBean.class);
		xstream.alias("Body", BCK03845ReqBodyBean.class);
		xstream.alias("Head", InReqHeadBean.class);
		return xstream.toXML(bck03845ReqBean);
	}
	
	
	
}
