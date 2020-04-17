package com.boomhope.Bill.TransService.BillChOpen.Interface;

import java.util.Map;

import com.boomhope.Bill.Public.GlobalParameter;
import com.boomhope.tms.message.XStreamNameCoder;
import com.boomhope.tms.message.account.in.BCK02864ReqBean;
import com.boomhope.tms.message.account.in.BCK02864ReqBodyBean;
import com.boomhope.tms.message.account.in.BCK03510ReqBean;
import com.boomhope.tms.message.account.in.BCK03510ReqBodyBean;
import com.boomhope.tms.message.account.in.BCK03845ReqBean;
import com.boomhope.tms.message.account.in.BCK03845ReqBodyBean;
import com.boomhope.tms.message.in.InReqHeadBean;
import com.boomhope.tms.message.in.bck.BCK01597ReqBean;
import com.boomhope.tms.message.in.bck.BCK01597ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK02791ReqBean;
import com.boomhope.tms.message.in.bck.BCK02791ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK03519ReqBean;
import com.boomhope.tms.message.in.bck.BCK03519ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK03521ReqBean;
import com.boomhope.tms.message.in.bck.BCK03521ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK07516ReqBean;
import com.boomhope.tms.message.in.bck.BCK07516ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK07524ReqBean;
import com.boomhope.tms.message.in.bck.BCK07524ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK07527ReqBean;
import com.boomhope.tms.message.in.bck.BCK07527ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK07601ReqBean;
import com.boomhope.tms.message.in.bck.BCK07601ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK07602ReqBean;
import com.boomhope.tms.message.in.bck.BCK07602ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK07659ReqBean;
import com.boomhope.tms.message.in.bck.BCK07659ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK07660ReqBean;
import com.boomhope.tms.message.in.bck.BCK07660ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK07670ReqBean;
import com.boomhope.tms.message.in.bck.BCK07670ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK07819ReqBean;
import com.boomhope.tms.message.in.bck.BCK07819ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK08021ReqBean;
import com.boomhope.tms.message.in.bck.BCK08021ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK77017ReqBean;
import com.boomhope.tms.message.in.bck.BCK77017ReqBodyBean;
import com.boomhope.tms.message.in.tms.Tms0005ReqBean;
import com.boomhope.tms.message.in.tms.Tms0005ReqBodyBean;
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
	 * 流水信息综合查询07601
	 */
	public static String CMM_07601(Map<String,String> map){
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
	 *个人IC卡验证(卡75048)	-前置【07602】
	 * @param map
	 * @return
	 */
	public static String BCK_07602(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK07602ReqBean bck07602ReqBean = new BCK07602ReqBean();
		//请求报文体
		BCK07602ReqBodyBean bck07602ReqBodyBean = new BCK07602ReqBodyBean();
		bck07602ReqBodyBean.setIC_CHL(map.get("IC_CHL"));//渠道号
		bck07602ReqBodyBean.setCARD_NO(map.get("CARD_NO"));//卡号
		bck07602ReqBodyBean.setAID(map.get("AID"));//AID
		bck07602ReqBodyBean.setARQC(map.get("ARQC"));//ARQC
		bck07602ReqBodyBean.setARQC_SRC_DATA(map.get("ARQC_SRC_DATA"));//ARQC生成数据
		bck07602ReqBodyBean.setTERM_CHK_VALUE(map.get("TERM_CHK_VALUE"));//终端验证结果
		bck07602ReqBodyBean.setAPP_ACCT_SEQ(map.get("APP_ACCT_SEQ"));//应用主账户序列号
		bck07602ReqBodyBean.setISS_APP_DATA(map.get("ISS_APP_DATA"));//发卡行应用数据
		bck07602ReqBodyBean.setCARD_POV(map.get("CARD_POV"));//卡片有效期
		
		bck07602ReqBean.setBody(bck07602ReqBodyBean);
		//请求报文头
		bck07602ReqBean.setHeadBean(getInReqHeadBean("BCK_07602"));
		
		xstream.alias("Root", BCK07602ReqBean.class);
		xstream.alias("Body", BCK07602ReqBodyBean.class);
		xstream.alias("Head", InReqHeadBean.class);
		return xstream.toXML(bck07602ReqBean);
	}
	
	/**
	    * 个人定期存单换开【02813】-前置07516
	    * @param map
	    * @return
	    */
	 public static String BCK_07516(Map<String, String> map) {
	  XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
	  xstream.autodetectAnnotations(true);
	  BCK07516ReqBean bck07516ReqBean = new BCK07516ReqBean();
	  //请求报文体
	  BCK07516ReqBodyBean bck07516ReqBodyBean = new BCK07516ReqBodyBean();
	  bck07516ReqBodyBean.setACCT_NO(map.get("ACC_NO"));//账号
	  bck07516ReqBodyBean.setSUB_ACCT_NO(map.get("SUB_ACCT_NO"));//子账号
	  bck07516ReqBodyBean.setCUST_NO(map.get("CUST_NO"));//客户号
	  bck07516ReqBodyBean.setCUST_NAME(map.get("CUST_NAME"));//客户名称
	  
	  bck07516ReqBodyBean.setORIG_CERT_NO(map.get("ORIG_CERT_NO"));//原凭证号
	  bck07516ReqBodyBean.setPAY_COND(map.get("PAY_COND"));//原支付条件
	  bck07516ReqBodyBean.setPASSWORD(map.get("PASSWORD"));//换开密码
	  bck07516ReqBodyBean.setCHANGE_REASON(map.get("CHANGE_REASON"));//换开原因
	  bck07516ReqBodyBean.setNEW_CERT_NO(map.get("NEW_CERT_NO"));//新凭证号
	  bck07516ReqBean.setBody(bck07516ReqBodyBean);
	  
	  
	  //请求报文头
	  bck07516ReqBean.setHeadBean(getInReqHeadBean("BCK_07516"));
	    
	  xstream.alias("Root", BCK07516ReqBean.class);
	  xstream.alias("Body", BCK07516ReqBodyBean.class);
	  xstream.alias("Head", InReqHeadBean.class);
	  return xstream.toXML(bck07516ReqBean);
	  
	 }
	 /**
	 * 代理人身份信息核对08021
	 */
	 public static String BCK_08021(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK08021ReqBean bCK08021ReqBean = new BCK08021ReqBean();
		//请求报文体
		BCK08021ReqBodyBean bCK08021ReqBodyBean = new BCK08021ReqBodyBean();
		bCK08021ReqBodyBean.setNAME(map.get("NAME"));//姓名
		bCK08021ReqBodyBean.setID_NO(map.get("ID_NO"));//证件号码
		bCK08021ReqBean.setBody(bCK08021ReqBodyBean);
		//请求报文头
		bCK08021ReqBean.setHeadBean(getInReqHeadBean("BCK_08021"));
		
		xstream.alias("Root", BCK08021ReqBean.class);
		xstream.alias("Body", BCK08021ReqBodyBean.class);
		xstream.alias("Head", InReqHeadBean.class);
		return xstream.toXML(bCK08021ReqBean);
	}

	 
		/**
		 * 卡信息查询03845
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
		 /**
		 * 开卡明细联动查询【70028】-07524
		 */
		public static String BCK_07524(Map<String,String> map){
			XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
			xstream.autodetectAnnotations(true);
			BCK07524ReqBean bck07524ReqBean = new BCK07524ReqBean();
			//请求报文体
			BCK07524ReqBodyBean bck07524ReqBodyBean = new BCK07524ReqBodyBean();
			bck07524ReqBodyBean.setID_NO(map.get("ID_NO"));//证件号
			bck07524ReqBodyBean.setID_TYPE(map.get("ID_TYPE"));//证件类型
			
			bck07524ReqBean.setBody(bck07524ReqBodyBean);
			//请求报文头
			bck07524ReqBean.setHeadBean(getInReqHeadBean("BCK_07524"));
			
			xstream.alias("Root", BCK07524ReqBean.class);
			xstream.alias("Body", BCK07524ReqBodyBean.class);
			xstream.alias("Head", InReqHeadBean.class);
			return xstream.toXML(bck07524ReqBean);
		}
		/**
		 * 卡下子账户列表查询03519
		 */
		public static String BCK_03519(Map<String,String> map){
			XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
			xstream.autodetectAnnotations(true);
			BCK03519ReqBean bck03519ReqBean = new BCK03519ReqBean();
			//请求报文体
			BCK03519ReqBodyBean bck03519ReqBodyBean = new BCK03519ReqBodyBean();
			bck03519ReqBodyBean.setCARD_NO(map.get("CARD_NO"));//卡号
			bck03519ReqBean.setBody(bck03519ReqBodyBean);
			//请求报文头
			bck03519ReqBean.setHeadBean(getInReqHeadBean("BCK_03519"));
			
			xstream.alias("Root", BCK03519ReqBean.class);
			xstream.alias("Body", BCK03519ReqBodyBean.class);
			xstream.alias("Head", InReqHeadBean.class);
			return xstream.toXML(bck03519ReqBean);
		}

		/**
		 * 电子子账户列表查询07819
		 */
		public static String BCK_07819(Map<String,String> map){
			XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
			xstream.autodetectAnnotations(true);
			BCK07819ReqBean bck07819ReqBean = new BCK07819ReqBean();
			//请求报文体
			BCK07819ReqBodyBean bck07819ReqBodyBean = new BCK07819ReqBodyBean();
			bck07819ReqBodyBean.setCARD_NO(map.get("CARD_NO"));//电子卡号
			bck07819ReqBodyBean.setCUST_NO(map.get("CUST_NO"));//客户号
			bck07819ReqBean.setBody(bck07819ReqBodyBean);
			//请求报文头
			bck07819ReqBean.setHeadBean(getInReqHeadBean("BCK_07819"));
			
			xstream.alias("Root", BCK07819ReqBean.class);
			xstream.alias("Body", BCK07819ReqBodyBean.class);
			xstream.alias("Head", InReqHeadBean.class);
			return xstream.toXML(bck07819ReqBean);
		}
		/**
		   * 按客户查询账户信息【 20103】-前置07527
		   */
		  public static String BCK_07527(Map<String,String> map){
		   XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		   xstream.autodetectAnnotations(true);
		   BCK07527ReqBean bck07527ReqBean = new BCK07527ReqBean();
		   //请求报文体
		   BCK07527ReqBodyBean bck07527ReqBodyBean = new BCK07527ReqBodyBean();
		   bck07527ReqBodyBean.setCUST_NO(map.get("CUST_NO"));//客户号
		   bck07527ReqBodyBean.setCUST_TYPE(map.get("CUST_TYPE"));//客户类型
		   bck07527ReqBodyBean.setCUST_NAME(map.get("CUST_NAME"));//客户名称
		   bck07527ReqBodyBean.setID_NO(map.get("ID_NO"));//证件号
		   bck07527ReqBodyBean.setID_TYPE(map.get("ID_TYPE"));//证件类型
		   bck07527ReqBodyBean.setQRY_TYPE(map.get("QRY_TYPE"));//查询类型
		   
		   bck07527ReqBean.setBody(bck07527ReqBodyBean);
		   //请求报文头
		   bck07527ReqBean.setHeadBean(getInReqHeadBean("BCK_07527"));
		   
		   xstream.alias("Root", BCK07527ReqBean.class);
		   xstream.alias("Body", BCK07527ReqBodyBean.class);
		   xstream.alias("Head", InReqHeadBean.class);
		   return xstream.toXML(bck07527ReqBean);
		  }
	 /**
	 * 查询凭证号信息
	 */
	 public static String Tms0005(){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));

		xstream.autodetectAnnotations(true);
		Tms0005ReqBean tms0005ReqBean = new Tms0005ReqBean();
		Tms0005ReqBodyBean tms0005ReqBodyBean = new Tms0005ReqBodyBean();
		tms0005ReqBodyBean.setISNo(""); //标识
		tms0005ReqBodyBean.setID(""); //主键
		tms0005ReqBodyBean.setMACHINE_NO(GlobalParameter.machineNo); //机器编号
		tms0005ReqBodyBean.setSTART_NO(""); //起始号
		tms0005ReqBodyBean.setEND_NO(""); //结尾号
		tms0005ReqBodyBean.setNOW_BO(""); //当前号
		tms0005ReqBodyBean.setCREATE_DATE(""); //创建时间
		tms0005ReqBodyBean.setUPDATE_DATE(""); //修改时间
		tms0005ReqBean.setBodyBean(tms0005ReqBodyBean);
		InReqHeadBean inReqHeadBean = new InReqHeadBean();
		inReqHeadBean.setTradeCode("TMS_0005");
		inReqHeadBean.setMachineNo(GlobalParameter.machineNo);
		inReqHeadBean.setBranchNo(GlobalParameter.branchNo);
		inReqHeadBean.setMachineType(GlobalParameter.MACHINE_TYPE);
		tms0005ReqBean.setHeadBean(inReqHeadBean);
		xstream.alias("Root", Tms0005ReqBean.class);
		return xstream.toXML(tms0005ReqBean);
	}
	/**
	 * 更改凭证信息
	 */
	public static String Tms0006(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));

		xstream.autodetectAnnotations(true);
		Tms0005ReqBean tms0005ReqBean = new Tms0005ReqBean();
		Tms0005ReqBodyBean tms0005ReqBodyBean = new Tms0005ReqBodyBean();
		tms0005ReqBodyBean.setISNo(map.get("isNo")); //标识
		tms0005ReqBodyBean.setID(map.get("id")); //主键
		tms0005ReqBodyBean.setMACHINE_NO(GlobalParameter.machineNo); //机器编号
		tms0005ReqBodyBean.setSTART_NO(map.get("startNo")); //起始号
		tms0005ReqBodyBean.setEND_NO(map.get("endNo")); //结尾号
		tms0005ReqBodyBean.setNOW_BO(map.get("nowNo")); //当前号
		tms0005ReqBodyBean.setCREATE_DATE(map.get("createDate")); //创建时间
		tms0005ReqBodyBean.setUPDATE_DATE(map.get("updateDate")); //修改时间
		tms0005ReqBean.setBodyBean(tms0005ReqBodyBean);
		InReqHeadBean inReqHeadBean = new InReqHeadBean();
		inReqHeadBean.setTradeCode("TMS_0006");
		inReqHeadBean.setMachineNo(GlobalParameter.machineNo);
		inReqHeadBean.setBranchNo(GlobalParameter.branchNo);
		inReqHeadBean.setMachineType(GlobalParameter.MACHINE_TYPE);
		tms0005ReqBean.setHeadBean(inReqHeadBean);
		xstream.alias("Root", Tms0005ReqBean.class);
		return xstream.toXML(tms0005ReqBean);
	}
	/**
	 * 账户信息查询及密码验证03521
	 */
	public static String BCK_03521(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK03521ReqBean bck03521ReqBean = new BCK03521ReqBean();
		//请求报文体
		BCK03521ReqBodyBean bck03521ReqBodyBean = new BCK03521ReqBodyBean();
		bck03521ReqBodyBean.setACCT_NO(map.get("ACCT_NO"));//账号
		bck03521ReqBodyBean.setPASSWORD(map.get("PASSWORD"));//密码
		bck03521ReqBodyBean.setPIN_VAL_FLAG(map.get("PIN_VAL_FLAG"));//验密标志
		bck03521ReqBean.setBody(bck03521ReqBodyBean);
		//请求报文头
		bck03521ReqBean.setHeadBean(getInReqHeadBean("BCK_03521"));
		
		xstream.alias("Root", BCK03521ReqBean.class);
		xstream.alias("Body", BCK03521ReqBodyBean.class);
		xstream.alias("Head", InReqHeadBean.class);
		return xstream.toXML(bck03521ReqBean);
	}
	/**
	 * 4.9	产品利率信息查询
	 * @param map
	 * @return
	 */
	public static String BCK_02864(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		BCK02864ReqBean bck02864ReqBean = new BCK02864ReqBean();
		bck02864ReqBean.setHeadBean(getInReqHeadBean("BCK_02864"));
		BCK02864ReqBodyBean bck02864ReqBodyBean = new BCK02864ReqBodyBean();
		bck02864ReqBodyBean.setPROD_CODE(map.get("PROD_CODE")); //产品代码
		bck02864ReqBodyBean.setRATE_DATE(map.get("RATE_DATE"));//利率日期
		bck02864ReqBodyBean.setFLOAT_FLAG(map.get("FLOAT_FLAG"));//浮动标志
		bck02864ReqBodyBean.setCUST_NO(map.get("CUST_NO"));//客户号
		bck02864ReqBodyBean.setCHL_ID(map.get("CHL_ID"));//渠道模块标识
		bck02864ReqBodyBean.setCUST_LEVEL(map.get("CUST_LEVEL"));//客户评级
		bck02864ReqBodyBean.setACCT_NO(map.get("ACC_NO"));
		bck02864ReqBodyBean.setSUB_ACCT_NO(map.get("SubAccNo"));
		bck02864ReqBean.setBody(bck02864ReqBodyBean);
		xstream.alias("Root", BCK02864ReqBean.class);
		return xstream.toXML(bck02864ReqBean);
	}
	/**
	 * 4.7	产品预计利息(24小时)
	 * @param map
	 * @return
	 */
	public static String BCK_03510(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		BCK03510ReqBean bck03510ReqBean = new BCK03510ReqBean();
		bck03510ReqBean.setHeadBean(getInReqHeadBean("BCK_03510"));
		BCK03510ReqBodyBean bck03510ReqBodyBean = new BCK03510ReqBodyBean();
		bck03510ReqBodyBean.setACCT_NO(map.get("ACCT_NO")); //设置卡号
		bck03510ReqBodyBean.setAMT(map.get("AMT"));//设置金额
		bck03510ReqBodyBean.setCHL_ID(map.get("CHL_ID"));//设置渠道模块标识
	    bck03510ReqBodyBean.setCUST_NO(map.get("CUST_NO"));//设置客户号
	    bck03510ReqBodyBean.setDRAW_AMT_DATE(map.get("DRAW_AMT_DATE"));//设置取款日期
	    bck03510ReqBodyBean.setOPEN_CHL(map.get("OPEN_CHL"));//设置开户渠道
	    bck03510ReqBodyBean.setOPEN_DATE(map.get("OPEN_DATE"));//设置开户日期
	    bck03510ReqBodyBean.setPROD_CODE(map.get("PROD_CODE"));//设置产品代码
	    bck03510ReqBodyBean.setSUB_ACCT_NO(map.get("SUB_ACCT_NO"));//设置子账号
	    bck03510ReqBodyBean.setCUST_LEVEL(map.get("CUST_LEVEL"));//客户评级
		bck03510ReqBean.setBody(bck03510ReqBodyBean);
		xstream.alias("Root", BCK03510ReqBean.class);
		return xstream.toXML(bck03510ReqBean);
	}
}
