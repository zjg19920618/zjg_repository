package com.boomhope.Bill.TransService.AccCancel.Interface;

import java.util.Map;

import com.boomhope.Bill.Public.GlobalParameter;
import com.boomhope.tms.message.XStreamNameCoder;
import com.boomhope.tms.message.account.in.BCK03845ReqBean;
import com.boomhope.tms.message.account.in.BCK03845ReqBodyBean;
import com.boomhope.tms.message.in.InReqHeadBean;
import com.boomhope.tms.message.in.bck.BCK01597ReqBean;
import com.boomhope.tms.message.in.bck.BCK01597ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK03445ReqBean;
import com.boomhope.tms.message.in.bck.BCK03445ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK03517ReqAgentInfBean;
import com.boomhope.tms.message.in.bck.BCK03517ReqBean;
import com.boomhope.tms.message.in.bck.BCK03517ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK03519ReqBean;
import com.boomhope.tms.message.in.bck.BCK03519ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK03521ReqBean;
import com.boomhope.tms.message.in.bck.BCK03521ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK03522ReqAgentInfBean;
import com.boomhope.tms.message.in.bck.BCK03522ReqBean;
import com.boomhope.tms.message.in.bck.BCK03522ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK07498ReqBean;
import com.boomhope.tms.message.in.bck.BCK07498ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK07499ReqBean;
import com.boomhope.tms.message.in.bck.BCK07499ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK07515ReqBean;
import com.boomhope.tms.message.in.bck.BCK07515ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK07601ReqBean;
import com.boomhope.tms.message.in.bck.BCK07601ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK07602ReqBean;
import com.boomhope.tms.message.in.bck.BCK07602ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK07622ReqBean;
import com.boomhope.tms.message.in.bck.BCK07622ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK07624ReqAgentInfBean;
import com.boomhope.tms.message.in.bck.BCK07624ReqBean;
import com.boomhope.tms.message.in.bck.BCK07624ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK07659ReqBean;
import com.boomhope.tms.message.in.bck.BCK07659ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK07660ReqBean;
import com.boomhope.tms.message.in.bck.BCK07660ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK07665ReqBean;
import com.boomhope.tms.message.in.bck.BCK07665ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK07670ReqBean;
import com.boomhope.tms.message.in.bck.BCK07670ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK07696ReqBean;
import com.boomhope.tms.message.in.bck.BCK07696ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK07819ReqBean;
import com.boomhope.tms.message.in.bck.BCK07819ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK08021ReqBean;
import com.boomhope.tms.message.in.bck.BCK08021ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK77017ReqBean;
import com.boomhope.tms.message.in.bck.BCK77017ReqBodyBean;
import com.boomhope.tms.message.in.tms.Tms0007ReqBean;
import com.boomhope.tms.message.in.tms.Tms0007ReqBodyBean;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XppDriver;

/**
 * 创建XML报文信息
 * @author Wang.xm
 *
 */

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
	 * 证件信息查询03445
	 */
	public static String BCK_03445(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK03445ReqBean bck03445ReqBean = new BCK03445ReqBean();
		//请求报文体
		BCK03445ReqBodyBean bck03445ReqBodyBean = new BCK03445ReqBodyBean();
		bck03445ReqBodyBean.setCUST_NAME(map.get("CUST_NAME"));
		bck03445ReqBodyBean.setID_NO(map.get("ID_NO"));
		bck03445ReqBodyBean.setID_TYPE(map.get("ID_TYPE"));
		bck03445ReqBean.setBODY(bck03445ReqBodyBean);
		//请求报文头
		bck03445ReqBean.setHeadBean(getInReqHeadBean("BCK_03445"));
		
		xstream.alias("Root", BCK03445ReqBean.class);
		xstream.alias("Body", BCK03445ReqBodyBean.class);
		xstream.alias("Head", InReqHeadBean.class);
		return xstream.toXML(bck03445ReqBean);
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
	 * 卡信息查询及密码验证03845
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
	 * 普通预计利息查询07696
	 */
	public static String BCK_07696(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK07696ReqBean bck07696ReqBean = new BCK07696ReqBean();
		//请求报文体
		BCK07696ReqBodyBean bck07696ReqBodyBean = new BCK07696ReqBodyBean();
		bck07696ReqBodyBean.setAcctNO(map.get("ACCT_NO"));
		bck07696ReqBean.setBody(bck07696ReqBodyBean);
		//请求报文头
		bck07696ReqBean.setHeadBean(getInReqHeadBean("BCK_07696"));
		
		xstream.alias("Root", BCK07696ReqBean.class);
		xstream.alias("Body", BCK07696ReqBodyBean.class);
		xstream.alias("Head", InReqHeadBean.class);
		return xstream.toXML(bck07696ReqBean);
	}
	/**
	 * 唐豆回收查询07622
	 */
	public static String BCK_07622(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK07622ReqBean bck07622ReqBean = new BCK07622ReqBean();
		//请求报文体
		BCK07622ReqBodyBean bck07622ReqBodyBean = new BCK07622ReqBodyBean();
		bck07622ReqBodyBean.setAcctNo(map.get("ACCT_NO"));//账号
		bck07622ReqBodyBean.setSubAcctNo(map.get("SUB_ACCT_NO"));//子账户
		bck07622ReqBodyBean.setPayDate(map.get("PAY_DATE"));//日期
		bck07622ReqBodyBean.setPayJrnl(map.get("PAY_JRNL"));//流水号
		bck07622ReqBean.setBody(bck07622ReqBodyBean);
		//请求报文头
		bck07622ReqBean.setHeadBean(getInReqHeadBean("BCK_07622"));
		
		xstream.alias("Root", BCK07622ReqBean.class);
		xstream.alias("Body", BCK07622ReqBodyBean.class);
		xstream.alias("Head", InReqHeadBean.class);
		return xstream.toXML(bck07622ReqBean);
	}
	/**
	 * 唐豆回收07665
	 */
	public static String BCK_07665(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK07665ReqBean bck07665ReqBean = new BCK07665ReqBean();
		//请求报文体
		BCK07665ReqBodyBean bck07665ReqBodyBean = new BCK07665ReqBodyBean();
		bck07665ReqBodyBean.setACCT_NO(map.get("ACCT_NO"));//账号
		bck07665ReqBodyBean.setSUB_ACCT_NO(map.get("SUB_ACCT_NO"));//子账号
		bck07665ReqBodyBean.setPAY_DATE(map.get("PAY_DATE"));//支取日期
		bck07665ReqBodyBean.setPAY_JRNL(map.get("PAY_JRNL"));//支取流水
		bck07665ReqBodyBean.setPAY_AMT(map.get("PAY_AMT"));//支取金额
		bck07665ReqBodyBean.setORG_EXCHANGE_MODE(map.get("ORG_EXCHANGE_MODE"));//原唐豆兑换方式
		bck07665ReqBodyBean.setORG_EXCHANGE_PROP(map.get("ORG_EXCHANGE_PROP"));//原唐豆兑现比例
		bck07665ReqBodyBean.setBACK_COUNT(map.get("BACK_COUNT"));//收回唐豆数量
		bck07665ReqBodyBean.setBACK_PROP(map.get("BACK_PROP"));//收回比例
		bck07665ReqBodyBean.setBACK_EXCHANGE_AMT(map.get("BACK_EXCHANGE_AMT"));//收回兑现金额
		bck07665ReqBodyBean.setRECOV_TYPE(map.get("RECOV_TYPE"));//唐豆收回方式
		bck07665ReqBodyBean.setOPPO_ACCT_NO(map.get("OPPO_ACCT_NO"));//对方账号
		bck07665ReqBodyBean.setPASSWORD(map.get("PASSWORD"));//对方账号密码
		bck07665ReqBean.setBody(bck07665ReqBodyBean);
		//请求报文头
		bck07665ReqBean.setHeadBean(getInReqHeadBean("BCK_07665"));
		
		xstream.alias("Root", BCK07665ReqBean.class);
		xstream.alias("Body", BCK07665ReqBodyBean.class);
		xstream.alias("Head", InReqHeadBean.class);
		return xstream.toXML(bck07665ReqBean);
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
	 * 普通账户销户07624 
	 */
	public static String BCK_07624(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK07624ReqBean bck07624ReqBean = new BCK07624ReqBean();
		//请求报文体
		BCK07624ReqBodyBean bck07624ReqBodyBean = new BCK07624ReqBodyBean();
		bck07624ReqBodyBean.setACCT_NO(map.get("ACCT_NO"));//账号
		bck07624ReqBodyBean.setCERT_NO(map.get("CERT_NO"));//凭证号
		bck07624ReqBodyBean.setCUST_NO(map.get("CUST_NO"));//客户号
		bck07624ReqBodyBean.setCUST_NAME(map.get("CUST_NAME"));//客户名称
		bck07624ReqBodyBean.setCURRENCY_TYPE(map.get("CURRENCY_TYPE"));//币种
		bck07624ReqBodyBean.setDRAW_COND(map.get("DRAW_COND"));//支付条件
		if("1".equals(map.get("DRAW_COND").toString())){
			bck07624ReqBodyBean.setPASSWORD(map.get("PASSWORD"));//存单密码
		}else {
			bck07624ReqBodyBean.setPASSWORD("");//存单密码
		}
		bck07624ReqBodyBean.setCURRENCY(map.get("CURRENCY"));//货币号
		bck07624ReqBodyBean.setPROD_CODE(map.get("PROD_CODE"));//产品代码
		bck07624ReqBodyBean.setPROD_NAME(map.get("PROD_NAME"));//产品名称
		bck07624ReqBodyBean.setBALANCE(map.get("BALANCE"));//余额
		bck07624ReqBodyBean.setDEP_TERM(map.get("DEP_TERM"));//存期
		bck07624ReqBodyBean.setSTART_INT_DATE(map.get("START_INT_DAT"));//起息日
		bck07624ReqBodyBean.setOPEN_RATE(map.get("OPEN_RATE"));//开户利率
		bck07624ReqBodyBean.setCYC_AMT(map.get("CYC_AMT"));//周期金额
		bck07624ReqBodyBean.setCYC(map.get("CYC"));//周期
		bck07624ReqBodyBean.setTIMES(map.get("TIMES"));//次数
		bck07624ReqBodyBean.setBES_AMT(map.get("BES_AMT"));//预约金额
		bck07624ReqBodyBean.setBES_DATE(map.get("BES_DATE"));//预约日期
		bck07624ReqBodyBean.setDRAW_CURRENCY(map.get("DRAW_CURRENCY"));//支取币种
		bck07624ReqBodyBean.setPRI_INTE_FLAG(map.get("PRI_INTE_FLAG"));//本息分开（必输，0否、1是）
		bck07624ReqBodyBean.setCANCEL_AMT(map.get("CANCEL_AMT"));//销户金额
		bck07624ReqBodyBean.setPRI_INTE_WAY(map.get("PRI_INTE_WAY"));//本息走向
		bck07624ReqBodyBean.setOPPO_ACCT_NO(map.get("OPPO_ACCT_NO"));//对方账号（本息转账，有）
		bck07624ReqBodyBean.setSUB_ACCT_NO(map.get("SUB_ACCT_NO"));//子账号（必输项，对方账号为一本通使显示，默认为0基本账户可修改）
		bck07624ReqBodyBean.setPRI_WAY(map.get("PRI_WAY"));//本金走向
		bck07624ReqBodyBean.setOPPO_ACCT_NO1(map.get("OPPO_ACCT_NO1"));//对方账号（本金转账，本金、利息分开时，对方账户不允许为同一账户）
		bck07624ReqBodyBean.setSUB_ACCT_NO1(map.get("SUB_ACCT_NO1"));//子账号（必输项，对方账号为一本通使显示，默认为0基本账户可修改）
		bck07624ReqBodyBean.setINTE_WAY(map.get("INTE_WAY"));//利息走向
		bck07624ReqBodyBean.setOPPO_ACCT_NO2(map.get("OPPO_ACCT_NO2"));//对方账号（利息转账，有）
		bck07624ReqBodyBean.setSUB_ACCT_NO2(map.get("SUB_ACCT_NO2"));//子账号（必输项，对方账号为一本通使显示，默认为0基本账户可修改）
		bck07624ReqBodyBean.setID_TYPE(map.get("ID_TYPE"));//证件类型
		bck07624ReqBodyBean.setID_NO(map.get("ID_NO"));//证件号码
		bck07624ReqBodyBean.setPROVE_FLAG(map.get("PROVE_FLAG"));//证明标志
		bck07624ReqBodyBean.setCASH_ANALY_NO(map.get("CASH_ANALY_NO"));//现金分析号
		bck07624ReqBodyBean.setHAV_AGENT_FLAG(map.get("HAV_AGENT_FLAG"));//是否有代理人标志
		//拼接输出的AGENT_INF
		BCK07624ReqAgentInfBean reqAgentInfBean = new BCK07624ReqAgentInfBean();
		reqAgentInfBean.setCUST_NAME(map.get("AGENT_CUST_NAME"));//客户姓名
		reqAgentInfBean.setSEX(map.get("AGENT_SEX"));//性别
		reqAgentInfBean.setID_TYPE(map.get("AGENT_ID_TYPE"));//证件类别
		reqAgentInfBean.setID_NO(map.get("AGENT_ID_NO"));//证件号码
		reqAgentInfBean.setDUE_DATE(map.get("AGENT_DUE_DATE"));//到期日期
		reqAgentInfBean.setISSUE_DATE(map.get("AGENT_ISSUE_DATE"));//签发日期
		reqAgentInfBean.setISSUE_INST(map.get("AGENT_ISSUE_INST"));//签发机关
		reqAgentInfBean.setNATION(map.get("AGENT_NATION"));//国籍
		reqAgentInfBean.setOCCUPATION(map.get("AGENT_OCCUPATION"));//职业
		reqAgentInfBean.setREGIS_PER_RES(map.get("AGENT_REGIS_PER_RES"));//户口所在地
		reqAgentInfBean.setJ_C_ADD(map.get("AGENT_J_C_ADD"));//经常居住地
		reqAgentInfBean.setTELEPHONE(map.get("AGENT_TELEPHONE"));//固定电话
		reqAgentInfBean.setMOB_PHONE(map.get("AGENT_MOB_PHONE"));//移动电话
		
		// 业务流水-P端使用字段
		bck07624ReqBodyBean.setPROD_NAME(map.get("proName"));
		bck07624ReqBodyBean.setPROD_CODE(map.get("proCode"));
		bck07624ReqBodyBean.setOPPO_NAME(map.get("cardName"));
		bck07624ReqBodyBean.setCANEL_TYPE(map.get("canelType"));
		bck07624ReqBodyBean.setCHECKSTATUS(map.get("checkStatus"));
		
		bck07624ReqBodyBean.setAGENT_INF(reqAgentInfBean);
		bck07624ReqBean.setBody(bck07624ReqBodyBean);
		//请求报文头
		bck07624ReqBean.setHeadBean(getInReqHeadBean("BCK_07624"));
		
		xstream.alias("Root", BCK07624ReqBean.class);
		xstream.alias("Body", BCK07624ReqBodyBean.class);
		xstream.alias("Head", InReqHeadBean.class);
		return xstream.toXML(bck07624ReqBean);
	}
	/**
	 * 卡下子账户销户03517
	 */
	public static String BCK_03517(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK03517ReqBean bck03517ReqBean = new BCK03517ReqBean();
		//请求报文体
		BCK03517ReqBodyBean bck03517ReqBodyBean = new BCK03517ReqBodyBean();
		bck03517ReqBodyBean.setTRAN_CHANNEL(map.get("TRAN_CHANNEL")); //渠道号 00-网银 50-手机银行
		bck03517ReqBodyBean.setCARD_NO(map.get("CARD_NO")); //卡号
		bck03517ReqBodyBean.setSUB_ACCT_NO2(map.get("SUB_ACCT_NO")); //子账户
		bck03517ReqBodyBean.setCAL_MODE(map.get("CAL_MODE")); //结算方式  0-现金 1-转账
		bck03517ReqBodyBean.setCASH_ANALY_NO(map.get("CASH_ANALY_NO")); //现金分析号
		bck03517ReqBodyBean.setOPPO_ACCT_NO(map.get("OPPO_ACCT_NO")); //对方账号
		bck03517ReqBodyBean.setID_TYPE(map.get("ID_TYPE")); //证件类型
		bck03517ReqBodyBean.setID_NO(map.get("ID_NO")); //证件号码
		bck03517ReqBodyBean.setCERT_NO(map.get("CERT_NO")); //凭证号
		
		bck03517ReqBodyBean.setPIN_VAL_FLAG(map.get("PIN_VAL_FLAG")); //验密标志
		if(!"N".equals(map.get("PIN_VAL_FLAG").toString())){
			bck03517ReqBodyBean.setPASSWORD(map.get("PASSWORD"));//存单密码
		}else {
			bck03517ReqBodyBean.setPASSWORD("");//存单密码
		}
		bck03517ReqBodyBean.setHAV_AGENT_FLAG(map.get("HAV_AGENT_FLAG")); //代理人标志 0-是 1-否
		
		//拼接输出的AGENT_INF
		BCK03517ReqAgentInfBean reqAgentInfBean = new BCK03517ReqAgentInfBean();
		reqAgentInfBean.setCUST_NAME(map.get("Agent_CUST_NAME"));//客户姓名
		reqAgentInfBean.setDUE_DATE(map.get("Agent_DUE_DATE"));//到期日期
		reqAgentInfBean.setID_NO(map.get("Agent_ID_NO"));//证件号码
		reqAgentInfBean.setID_TYPE(map.get("Agent_ID_TYPE"));//证件类别
		reqAgentInfBean.setISSUE_DATE(map.get("Agent_ISSUE_DATE"));//签发日期
		reqAgentInfBean.setISSUE_INST(map.get("Agent_ISSUE_INST"));//签发机关
		reqAgentInfBean.setJ_C_ADD(map.get("Agent_J_C_ADD"));//经常居住地
		reqAgentInfBean.setMOB_PHONE(map.get("Agent_MOB_PHONE"));//移动电话
		reqAgentInfBean.setNATION(map.get("Agent_NATION"));//国籍
		reqAgentInfBean.setOCCUPATION(map.get("Agent_OCCUPATION"));//职业
		reqAgentInfBean.setREGIS_PER_RES(map.get("Agent_GIS_PER_RES"));//户口所在地
		reqAgentInfBean.setSEX(map.get("Agent_SEX"));//性别
		reqAgentInfBean.setTELEPHONE(map.get("Agent_TELEPHONE"));//固定电话
		
		// 业务流水-P端使用字段
		bck03517ReqBodyBean.setACCT_NO(map.get("accNo1"));
		bck03517ReqBodyBean.setAMOUNT(map.get("amount"));
		bck03517ReqBodyBean.setCARDNO(map.get("cardNo"));
		bck03517ReqBodyBean.setPRONAME(map.get("proName"));
		bck03517ReqBodyBean.setPROCODE(map.get("proCode"));
		bck03517ReqBodyBean.setCARDNAME(map.get("cardName"));
		bck03517ReqBodyBean.setCANEL_TYPE(map.get("canelType"));
		bck03517ReqBodyBean.setCHECKSTATUS(map.get("checkStatus"));
		
		bck03517ReqBodyBean.setAGENT_INF(reqAgentInfBean);
		bck03517ReqBean.setBody(bck03517ReqBodyBean);
		//请求报文头
		bck03517ReqBean.setHeadBean(getInReqHeadBean("BCK_03517"));
		
		xstream.alias("Root", BCK03517ReqBean.class);
		xstream.alias("Body", BCK03517ReqBodyBean.class);
		xstream.alias("Head", InReqHeadBean.class);
		return xstream.toXML(bck03517ReqBean);
	}
	/**
	 * 电子子账户销户03522
	 */
	public static String BCK_03522(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK03522ReqBean bck03522ReqBean = new BCK03522ReqBean();
		//请求报文体
		BCK03522ReqBodyBean bck03522ReqBodyBean = new BCK03522ReqBodyBean();
		bck03522ReqBodyBean.setTRAN_CHANNEL(map.get("TRAN_CHANNEL")); //渠道号 00-网银 50-手机银行
		bck03522ReqBodyBean.setCARD_NO(map.get("CARD_NO")); //卡号
		bck03522ReqBodyBean.setSUB_ACCT_NO(map.get("SUB_ACCT_NO")); //子账户
		bck03522ReqBodyBean.setCAL_MODE(map.get("CAL_MODE")); //结算方式  0-现金 1-转账
		bck03522ReqBodyBean.setCASH_ANALY_NO(map.get("CASH_ANALY_NO")); //现金分析号
		bck03522ReqBodyBean.setOPPO_ACCT_NO(map.get("OPPO_ACCT_NO")); //对方账号
		bck03522ReqBodyBean.setID_TYPE(map.get("ID_TYPE")); //证件类型
		bck03522ReqBodyBean.setID_NO(map.get("ID_NO")); //证件号码
		bck03522ReqBodyBean.setCERT_NO(map.get("CERT_NO")); //凭证号
		
		bck03522ReqBodyBean.setPIN_VAL_FLAG(map.get("PIN_VAL_FLAG")); //验密标志
		if(!"N".equals(map.get("PIN_VAL_FLAG").toString())){
			bck03522ReqBodyBean.setPASSWORD(map.get("PASSWORD"));//存单密码
		}else {
			bck03522ReqBodyBean.setPASSWORD("");//存单密码
		}
		bck03522ReqBodyBean.setHAV_AGENT_FLAG(map.get("HAV_AGENT_FLAG")); //代理人标志 0-是 1-否
		
		//拼接输出的AGENT_INF
		BCK03522ReqAgentInfBean reqAgentInfBean = new BCK03522ReqAgentInfBean();
		reqAgentInfBean.setCUST_NAME(map.get("Agent_CUST_NAME"));//客户姓名
		reqAgentInfBean.setDUE_DATE(map.get("Agent_DUE_DATE"));//到期日期
		reqAgentInfBean.setID_NO(map.get("Agent_ID_NO"));//证件号码
		reqAgentInfBean.setID_TYPE(map.get("Agent_ID_TYPE"));//证件类别
		reqAgentInfBean.setISSUE_DATE(map.get("Agent_ISSUE_DATE"));//签发日期
		reqAgentInfBean.setISSUE_INST(map.get("Agent_ISSUE_INST"));//签发机关
		reqAgentInfBean.setJ_C_ADD(map.get("JAgent__C_ADD"));//经常居住地
		reqAgentInfBean.setMOB_PHONE(map.get("Agent_MOB_PHONE"));//移动电话
		reqAgentInfBean.setNATION(map.get("Agent_NATION"));//国籍
		reqAgentInfBean.setOCCUPATION(map.get("Agent_OCCUPATION"));//职业
		reqAgentInfBean.setREGIS_PER_RES(map.get("Agent_REGIS_PER_RES"));//户口所在地
		reqAgentInfBean.setSEX(map.get("Agent_SEX"));//性别
		reqAgentInfBean.setTELEPHONE(map.get("Agent_TELEPHONE"));//固定电话
		
		// 业务流水-P端使用字段
		bck03522ReqBodyBean.setACCT_NO(map.get("accNo1"));
		bck03522ReqBodyBean.setAMOUNT(map.get("amount"));
		bck03522ReqBodyBean.setCARDNO(map.get("cardNo"));
		bck03522ReqBodyBean.setPRONAME(map.get("proName"));
		bck03522ReqBodyBean.setPROCODE(map.get("proCode"));
		bck03522ReqBodyBean.setCARDNAME(map.get("cardName"));
		bck03522ReqBodyBean.setCANEL_TYPE(map.get("canelType"));
		bck03522ReqBodyBean.setCHECKSTATUS(map.get("checkStatus"));	
					
		bck03522ReqBodyBean.setAGENT_INF(reqAgentInfBean);
		bck03522ReqBean.setBody(bck03522ReqBodyBean);
		//请求报文头
		bck03522ReqBean.setHeadBean(getInReqHeadBean("BCK_03522"));
		
		xstream.alias("Root", BCK03522ReqBean.class);
		xstream.alias("Body", BCK03522ReqBodyBean.class);
		xstream.alias("Head", InReqHeadBean.class);
		return xstream.toXML(bck03522ReqBean);
	}
	
	/**
	 * 转入唐宝账户查询【55060】-前置07498
	 * hk
	 */
	public static String BCK_07498(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK07498ReqBean bck07498ReqBean = new BCK07498ReqBean();
		//请求报文体
		BCK07498ReqBodyBean bck07498ReqBodyBean = new BCK07498ReqBodyBean();
		bck07498ReqBodyBean.setCARD_NO(map.get("CARD_NO"));//卡号
		bck07498ReqBodyBean.setCUST_NO(map.get("CUST_NO"));//客户号
		bck07498ReqBodyBean.setID_NO(map.get("ID_NO"));//证件号码
		bck07498ReqBodyBean.setID_TYPE(map.get("ID_TYPE"));//证件类别
		bck07498ReqBodyBean.setOPER_CHOOSE(map.get("OPER_CHOOSE"));//操作选择
		bck07498ReqBodyBean.setPROD_CODE(map.get("PROD_CODE"));//产品代码
		bck07498ReqBodyBean.setSUB_ACCT_NO(map.get("SUB_ACCT_NO"));//子账号
		bck07498ReqBodyBean.setTRAN_AMT(map.get("TRAN_AMT"));//转入金额
		
		bck07498ReqBean.setBody(bck07498ReqBodyBean);
		bck07498ReqBean.setHeadBean(getInReqHeadBean("BCK_07498"));
		xstream.alias("Root", BCK01597ReqBean.class);
		xstream.alias("Body", BCK01597ReqBodyBean.class);
		xstream.alias("Head", InReqHeadBean.class);
		return xstream.toXML(bck07498ReqBean);
	}
	
	/**
	 * 唐宝x号转入【79100】-前置07499
	 * hk
	 */
	public static String BCK_07499(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK07499ReqBean bck07499ReqBean = new BCK07499ReqBean();
		//请求报文体
		BCK07499ReqBodyBean bck07499ReqBodyBean = new BCK07499ReqBodyBean();
		bck07499ReqBodyBean.setCARD_NO(map.get("CARD_NO"));//卡号
		bck07499ReqBodyBean.setCUST_NO(map.get("CUST_NO"));//客户号
		bck07499ReqBodyBean.setID_NO(map.get("ID_NO"));//证件号码
		bck07499ReqBodyBean.setID_TYPE(map.get("ID_TYPE"));//证件类别
		bck07499ReqBodyBean.setPASSWORD(map.get("PASSWORD"));//密码
		bck07499ReqBodyBean.setPROD_CODE(map.get("PROD_CODE"));//产品代码
		bck07499ReqBodyBean.setSUB_ACCT_NO(map.get("SUB_ACCT_NO"));//子账号
		bck07499ReqBodyBean.setTRAN_AMT(map.get("TRAN_AMT"));//转入金额
		
		bck07499ReqBean.setBody(bck07499ReqBodyBean);
		bck07499ReqBean.setHeadBean(getInReqHeadBean("BCK_07499"));
		xstream.alias("Root", BCK01597ReqBean.class);
		xstream.alias("Body", BCK01597ReqBodyBean.class);
		xstream.alias("Head", InReqHeadBean.class);
		return xstream.toXML(bck07499ReqBean);
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
	 *	唐豆信息查询【02217】-前置07515
	 */
	public static String BCK_07515(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		BCK07515ReqBean bckReqBean = new BCK07515ReqBean();
		bckReqBean.setHeadBean(getInReqHeadBean("BCK_07515"));
		BCK07515ReqBodyBean bck07515ReqBodyBean = new BCK07515ReqBodyBean();
		bck07515ReqBodyBean.setACCT_NO(map.get("ACCT_NO")); //账号
		bck07515ReqBodyBean.setCUST_NO(map.get("CUST_NO")); //客户号
		bck07515ReqBodyBean.setID_TYPE(map.get("ID_TYPE")); //证件类型
		bck07515ReqBodyBean.setID_NO(map.get("ID_NO")); //证件号
		bckReqBean.setBody(bck07515ReqBodyBean);
		xstream.alias("Root", BCK07515ReqBean.class);
		return xstream.toXML(bckReqBean);
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
	
}
