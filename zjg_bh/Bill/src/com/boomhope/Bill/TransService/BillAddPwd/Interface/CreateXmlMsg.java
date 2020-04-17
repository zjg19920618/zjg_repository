package com.boomhope.Bill.TransService.BillAddPwd.Interface;

import java.util.Map;

import com.boomhope.Bill.Public.GlobalParameter;
import com.boomhope.tms.message.XStreamNameCoder;
import com.boomhope.tms.message.in.InReqHeadBean;
import com.boomhope.tms.message.in.bck.BCK01325ReqBean;
import com.boomhope.tms.message.in.bck.BCK01325ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK01597ReqBean;
import com.boomhope.tms.message.in.bck.BCK01597ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK02791ReqBean;
import com.boomhope.tms.message.in.bck.BCK02791ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK07517ReqBean;
import com.boomhope.tms.message.in.bck.BCK07517ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK07601ReqBean;
import com.boomhope.tms.message.in.bck.BCK07601ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK07659ReqBean;
import com.boomhope.tms.message.in.bck.BCK07659ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK07660ReqBean;
import com.boomhope.tms.message.in.bck.BCK07660ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK07670ReqBean;
import com.boomhope.tms.message.in.bck.BCK07670ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK77017ReqBean;
import com.boomhope.tms.message.in.bck.BCK77017ReqBodyBean;
import com.boomhope.tms.message.in.tms.Tms0007ReqBean;
import com.boomhope.tms.message.in.tms.Tms0007ReqBodyBean;
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
		inReqHeadBean.setMachineNo(GlobalParameter.machineNo);// 机器号
		inReqHeadBean.setBranchNo(GlobalParameter.branchNo);// 机构号
		inReqHeadBean.setMachineType(GlobalParameter.MACHINE_TYPE);// 机构号
		return inReqHeadBean;
		
	}
	
	/**
	 * 存单账户信息综合查询07601
	 */
	public static String BCK_07601(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK07601ReqBean bCK07601ReqBean = new BCK07601ReqBean();
		//请求报文体
		BCK07601ReqBodyBean bCK07601ReqBodyBean = new BCK07601ReqBodyBean();
		bCK07601ReqBodyBean.setAcctNo(map.get("ACCT_NO"));//账号
		bCK07601ReqBodyBean.setSubAcctNo(map.get("SUB_ACCT_NO"));//SUB_ACCT_NO
		bCK07601ReqBodyBean.setChkPin(map.get("CHK_PIN"));//是否验密
		bCK07601ReqBodyBean.setPasswd(map.get("PASSWD"));//密码
		bCK07601ReqBodyBean.setCERT_TYPE(map.get("CERT_TYPE"));//凭证种类
		bCK07601ReqBodyBean.setCERT_NO_ADD(map.get("CERT_NO_ADD"));//凭证号
		bCK07601ReqBean.setBody(bCK07601ReqBodyBean);
		//请求报文头
		bCK07601ReqBean.setHeadBean(getInReqHeadBean("BCK_07601"));
		
		xstream.alias("Root", BCK07601ReqBean.class);
		xstream.alias("Body", BCK07601ReqBodyBean.class);
		xstream.alias("Head", InReqHeadBean.class);
		return xstream.toXML(bCK07601ReqBean);
	}
	/**
	 * 存单账户凭证号信息综合查询02791
	 */
	public static String BCK_02791(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK02791ReqBean bCK02791ReqBean = new BCK02791ReqBean();
		//请求报文体
		BCK02791ReqBodyBean bCK02791ReqBodyBean = new BCK02791ReqBodyBean();
		
		bCK02791ReqBodyBean.setCERT_TYPE(map.get("CERT_TYPE"));//凭证种类
		bCK02791ReqBodyBean.setCERT_NO(map.get("CERT_NO"));//凭证号
		bCK02791ReqBean.setBody(bCK02791ReqBodyBean);
		//请求报文头
		bCK02791ReqBean.setHeadBean(getInReqHeadBean("BCK_02791"));
		
		xstream.alias("Root", BCK02791ReqBean.class);
		xstream.alias("Body", BCK02791ReqBodyBean.class);
		xstream.alias("Head", InReqHeadBean.class);
		return xstream.toXML(bCK02791ReqBean);
	}
	/**
	 * 鉴伪结果上送
	 */
	public static String Tms0007(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		
		Tms0007ReqBean tms0007ReqBean = new Tms0007ReqBean();
		Tms0007ReqBodyBean tms0007ReqBodyBean = new Tms0007ReqBodyBean();
		tms0007ReqBodyBean.setStatus(map.get("status"));
		tms0007ReqBodyBean.setBranchNo(GlobalParameter.branchNo);
		tms0007ReqBodyBean.setMachineNo(GlobalParameter.machineNo);
		
		tms0007ReqBean.setBodyBean(tms0007ReqBodyBean);		
		//请求报文头
		tms0007ReqBean.setHeadBean(getInReqHeadBean("TMS_0007"));
		xstream.alias("Root", Tms0007ReqBean.class);
		return xstream.toXML(tms0007ReqBean);
	}
	/**
	 * 柜员认证方式查询
	 * @param map
	 * @return
	 */
	public static String BCK_07659(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		BCK07659ReqBean bck07659ReqBean = new BCK07659ReqBean();
		bck07659ReqBean.setHeadBean(getInReqHeadBean("BCK_07659"));
		BCK07659ReqBodyBean bck07659ReqBodyBean = new BCK07659ReqBodyBean();
		bck07659ReqBodyBean.setQRY_TELLER_NO(map.get("QRY_TELLER_NO")); //设置柜员号
		bck07659ReqBean.setBody(bck07659ReqBodyBean);
		xstream.alias("Root", BCK07659ReqBean.class);
		return xstream.toXML(bck07659ReqBean);
	}
	/**
	 * 按交易授权前置77017
	 * @param map
	 * @return
	 */
	public static String BCK_77017(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK77017ReqBean bck77017ReqBean = new BCK77017ReqBean();
		//请求报文体
		BCK77017ReqBodyBean bck77017ReqBodyBean = new BCK77017ReqBodyBean();
		bck77017ReqBodyBean.setAUTH_TRAN_CODE(map.get("AUTH_TRAN_CODE"));
		bck77017ReqBodyBean.setFIN_PRI_LEN(map.get("FIN_PRI_LEN"));
		bck77017ReqBodyBean.setFIN_PRI_VAL(map.get("FIN_PRI_VAL"));
		bck77017ReqBodyBean.setSUP_TELLER_NO(map.get("SUP_TELLER_NO"));
		bck77017ReqBodyBean.setSUP_TELLER_PWD(map.get("SUP_TELLER_PWD"));
		bck77017ReqBean.setBody(bck77017ReqBodyBean);
		//请求报文头
		bck77017ReqBean.setHeadBean(getInReqHeadBean("BCK_77017"));
		
		xstream.alias("Root", BCK77017ReqBean.class);
		xstream.alias("Head", InReqHeadBean.class);
		xstream.alias("Body", BCK77017ReqBodyBean.class);
		return xstream.toXML(bck77017ReqBean);
	}
	/**
	 * 黑灰名单查询01597
	 * @param map
	 * @return
	 */
	public static String BCK_01597(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK01597ReqBean bck01597ReqBean = new BCK01597ReqBean();
		//请求报文体
		BCK01597ReqBodyBean bck01597ReqBodyBean = new BCK01597ReqBodyBean();
		bck01597ReqBodyBean.setACC_NAME1(map.get("ACC_NAME1"));//出账户名
		bck01597ReqBodyBean.setACC_NAME2(map.get("ACC_NAME2"));//入账户名
		bck01597ReqBodyBean.setACC_NO1(map.get("ACC_NO1"));//出账账号
		bck01597ReqBodyBean.setACC_NO2(map.get("ACC_NO2"));//入账账号
		bck01597ReqBodyBean.setBANK_ID1(map.get("BANK_ID1"));//出账支付行号
		bck01597ReqBodyBean.setBANK_ID2(map.get("BANK_ID2"));//入账支付行号
		bck01597ReqBodyBean.setCARD_NO1(map.get("CARD_NO1"));
		bck01597ReqBodyBean.setCARD_NO2(map.get("CARD_NO2"));//入账卡号
		bck01597ReqBodyBean.setCCY(map.get("CCY"));//币种
		bck01597ReqBodyBean.setID_NAME1(map.get("ID_NAME1"));//出账客户证件姓名
		bck01597ReqBodyBean.setID_NAME2(map.get("ID_NAME2"));//入账客户证件姓名
		bck01597ReqBodyBean.setID_NUMBER1(map.get("ID_NUMBER1"));//出账客户证件号码
		bck01597ReqBodyBean.setID_NUMBER2(map.get("ID_NUMBER2"));//入账客户证件号码
		bck01597ReqBodyBean.setID_TYPE1(map.get("ID_TYPE1"));//出账客户证件类型
		bck01597ReqBodyBean.setID_TYPE2(map.get("ID_TYPE2"));//入账客户证件类型
		bck01597ReqBodyBean.setMEMO1(map.get("MEMO1"));//预留
		bck01597ReqBodyBean.setMEMO2(map.get("MEMO2"));//预留
		bck01597ReqBodyBean.setMEMO3(map.get("MEMO3"));//预留
		bck01597ReqBodyBean.setMEMO4(map.get("MEMO4"));//预留
		bck01597ReqBodyBean.setTRAN_AMT(map.get("TRAN_AMT"));//交易金额
		bck01597ReqBodyBean.setPROGRAM_FLAG(map.get("PROGRAM_FLAG"));//程序标识 单元素100501/双元素100503
		bck01597ReqBean.setBody(bck01597ReqBodyBean);
		//请求报文头
		bck01597ReqBean.setHeadBean(getInReqHeadBean("BCK_01597"));
		
		xstream.alias("Root", BCK01597ReqBean.class);
		xstream.alias("Body", BCK01597ReqBodyBean.class);
		xstream.alias("Head", InReqHeadBean.class);
		return xstream.toXML(bck01597ReqBean);
	}
	/**
	 * 身份联网核查07670
	 */
	public static String BCK_07670(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK07670ReqBean bCK07670ReqBean = new BCK07670ReqBean();
		//请求报文体
		BCK07670ReqBodyBean bCK07670ReqBodyBean = new BCK07670ReqBodyBean();
		bCK07670ReqBodyBean.setID(map.get("ID"));
		bCK07670ReqBodyBean.setNAME(map.get("NAME"));
		bCK07670ReqBean.setBody(bCK07670ReqBodyBean);
		//请求报文头
		bCK07670ReqBean.setHeadBean(getInReqHeadBean("BCK_07670"));
		
		xstream.alias("Root", BCK07670ReqBean.class);
		xstream.alias("Body", BCK07670ReqBodyBean.class);
		xstream.alias("Head", InReqHeadBean.class);
		return xstream.toXML(bCK07670ReqBean);
	}
	/**
	 * 弱密码较验
	 * @param map
	 * @return
	 */
	public static String BCK_01325(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK01325ReqBean bck01325ReqBean = new BCK01325ReqBean();
		
		//请求报文体
		BCK01325ReqBodyBean bck01325ReqBodyBean = new BCK01325ReqBodyBean();
		bck01325ReqBodyBean.setNEW_PASS_WORD(map.get("NEW_PASS_WORD"));
		bck01325ReqBodyBean.setACCT_NO1(map.get("ACCT_NO1"));
		bck01325ReqBean.setBody(bck01325ReqBodyBean);
		//请求报文头
		bck01325ReqBean.setHeadBean(getInReqHeadBean("BCK_01325"));
		
		xstream.alias("Root", BCK01325ReqBean.class);
		xstream.alias("Body", BCK01325ReqBodyBean.class);
		xstream.alias("Head", InReqHeadBean.class);
		return xstream.toXML(bck01325ReqBean);
	}

	/**
	 * 柜员授权
	 * @param map
	 * @return
	 */
	public static String BCK_07660(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK07660ReqBean bck07660ReqBean = new BCK07660ReqBean();
		//请求报文体
		BCK07660ReqBodyBean bck07660ReqBodyBean = new BCK07660ReqBodyBean();
		bck07660ReqBodyBean.setFIN_PRI_LEN(map.get("FIN_PRI_LEN"));
		bck07660ReqBodyBean.setFIN_PRI_VAL(map.get("FIN_PRI_VAL"));
		bck07660ReqBodyBean.setSUP_TELLER_NO(map.get("SUP_TELLER_NO"));
		bck07660ReqBodyBean.setSUP_TELLER_PWD(map.get("SUP_TELLER_PWD"));
		bck07660ReqBean.setBody(bck07660ReqBodyBean);
		//请求报文头
		bck07660ReqBean.setHeadBean(getInReqHeadBean("BCK_07660"));
		
		xstream.alias("Root", BCK07660ReqBean.class);
		xstream.alias("Head", InReqHeadBean.class);
		xstream.alias("Body", BCK07660ReqBodyBean.class);
		return xstream.toXML(bck07660ReqBean);
	}
	

	
	 /**
	    * 修改支付条件【02805】-前置07517
	    * @param map
	    * @return
	    */
	 public static String BCK_07517(Map<String, String> map) {
	  XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
	  xstream.autodetectAnnotations(true);
	  BCK07517ReqBean bck07517ReqBean = new BCK07517ReqBean();
	  //请求报文体
	  BCK07517ReqBodyBean bck07517ReqBodyBean = new BCK07517ReqBodyBean();
	  bck07517ReqBodyBean.setACCT_NO(map.get("ACC_NO"));
	  bck07517ReqBodyBean.setCUST_NO(map.get("CUST_NO"));
	  bck07517ReqBodyBean.setCUST_NAME(map.get("CUST_NAME"));
	  bck07517ReqBodyBean.setCERT_NO(map.get("CERT_NO"));
	  bck07517ReqBodyBean.setOLD_PAY_COND(map.get("OLD_PAY_COND"));
	  bck07517ReqBodyBean.setNEW_PAY_COND(map.get("NEW_PAY_COND"));
	  bck07517ReqBodyBean.setCHANGE_REASON(map.get("CHANGE_REASON"));
	  bck07517ReqBodyBean.setOLD_PASSWORD(map.get("OLD_PASSWORD"));
	  bck07517ReqBodyBean.setNEW_PASSWORD(map.get("NEW_PASSWORD"));
	  bck07517ReqBodyBean.setID_NO(map.get("ID_NO"));
	  bck07517ReqBodyBean.setID_TYPE(map.get("ID_TYPE"));
	  bck07517ReqBean.setBody(bck07517ReqBodyBean);
	  //请求报文头
	  bck07517ReqBean.setHeadBean(getInReqHeadBean("BCK_07517"));
	    
	  xstream.alias("Root", BCK07517ReqBean.class);
	  xstream.alias("Body", BCK07517ReqBodyBean.class);
	  xstream.alias("Head", InReqHeadBean.class);
	  return xstream.toXML(bck07517ReqBean);
	  
	 }

}
