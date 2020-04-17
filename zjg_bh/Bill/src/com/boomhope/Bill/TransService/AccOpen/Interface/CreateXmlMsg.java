package com.boomhope.Bill.TransService.AccOpen.Interface;

import java.util.Map;

import com.boomhope.Bill.Public.GlobalParameter;
import com.boomhope.tms.message.XStreamNameCoder;
import com.boomhope.tms.message.account.in.BCK02864ReqBean;
import com.boomhope.tms.message.account.in.BCK02864ReqBodyBean;
import com.boomhope.tms.message.account.in.BCK02867ReqBean;
import com.boomhope.tms.message.account.in.BCK02867ReqBodyBean;
import com.boomhope.tms.message.account.in.BCK03510ReqBean;
import com.boomhope.tms.message.account.in.BCK03510ReqBodyBean;
import com.boomhope.tms.message.account.in.BCK03512ReqBean;
import com.boomhope.tms.message.account.in.BCK03512ReqBodyBean;
import com.boomhope.tms.message.account.in.BCK03845ReqBean;
import com.boomhope.tms.message.account.in.BCK03845ReqBodyBean;
import com.boomhope.tms.message.account.in.BCK03870ReqAgentInfBean;
import com.boomhope.tms.message.account.in.BCK03870ReqBean;
import com.boomhope.tms.message.account.in.BCK03870ReqBodyBean;
import com.boomhope.tms.message.account.in.BCK07505AcctInfo;
import com.boomhope.tms.message.account.in.BCK07505ReqBean;
import com.boomhope.tms.message.account.in.BCK07505ReqBodyBean;
import com.boomhope.tms.message.account.in.BCK07506ReqAgentInfBean;
import com.boomhope.tms.message.account.in.BCK07506ReqBean;
import com.boomhope.tms.message.account.in.BCK07506ReqBodyBean;
import com.boomhope.tms.message.account.in.tms.TmsAccountSaveOrderReqBean;
import com.boomhope.tms.message.account.in.tms.TmsAccountSaveOrderReqBodyBean;
import com.boomhope.tms.message.cdjmac.CdjOpenAccReqBean;
import com.boomhope.tms.message.cdjmac.CdjOpenAccReqBodyBean;
import com.boomhope.tms.message.in.InReqHeadBean;
import com.boomhope.tms.message.in.bck.BCK01597ReqBean;
import com.boomhope.tms.message.in.bck.BCK01597ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK03445ReqBean;
import com.boomhope.tms.message.in.bck.BCK03445ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK03514ReqBean;
import com.boomhope.tms.message.in.bck.BCK03514ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK03521ReqBean;
import com.boomhope.tms.message.in.bck.BCK03521ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK03524ReqBean;
import com.boomhope.tms.message.in.bck.BCK03524ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK04422ReqBean;
import com.boomhope.tms.message.in.bck.BCK04422ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK07601ReqBean;
import com.boomhope.tms.message.in.bck.BCK07601ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK07659ReqBean;
import com.boomhope.tms.message.in.bck.BCK07659ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK07660ReqBean;
import com.boomhope.tms.message.in.bck.BCK07660ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK07670ReqBean;
import com.boomhope.tms.message.in.bck.BCK07670ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK08021ReqBean;
import com.boomhope.tms.message.in.bck.BCK08021ReqBodyBean;
import com.boomhope.tms.message.in.tms.Tms0005ReqBean;
import com.boomhope.tms.message.in.tms.Tms0005ReqBodyBean;
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
	 * 查询灰名单
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
	 * 证件信息查询
	 * @param map
	 * @return
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
	 * 证件信息查询
	 * @param map
	 * @return
	 */
	public static String BCK_03512(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK03512ReqBean bck03512ReqBean = new BCK03512ReqBean();
		//请求报文体
		BCK03512ReqBodyBean bck03512ReqBodyBean = new BCK03512ReqBodyBean();
		bck03512ReqBodyBean.setCustNo(map.get("CUST_NO"));
		//请求报文头
		bck03512ReqBean.setHeadBean(getInReqHeadBean("BCK_03512"));
		bck03512ReqBean.setBody(bck03512ReqBodyBean);
		
		xstream.alias("Root", BCK03445ReqBean.class);
		xstream.alias("Body", BCK03445ReqBodyBean.class);
		xstream.alias("Head", InReqHeadBean.class);
		return xstream.toXML(bck03512ReqBean);
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
	
	/**
	 * 账户信息查询及密码验证-前置03521
	 */
	public static String BCK_03521(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK03521ReqBean bck03521ReqBean = new BCK03521ReqBean();
		bck03521ReqBean.setHeadBean(getInReqHeadBean("BCK_03521"));
		
		BCK03521ReqBodyBean bck03521ReqBodyBean = new BCK03521ReqBodyBean();
		bck03521ReqBodyBean.setACCT_NO(map.get("ACCT_NO"));//账号
		bck03521ReqBodyBean.setPASSWORD(map.get("PASSWORD"));//密码
		bck03521ReqBodyBean.setPIN_VAL_FLAG(map.get("PIN_VAL_FLAG"));//验密标志
		
		bck03521ReqBean.setBody(bck03521ReqBodyBean);
		
		xstream.alias("Root", BCK03521ReqBean.class);
		xstream.alias("Body", BCK03521ReqBodyBean.class);
		xstream.alias("Head", InReqHeadBean.class);
		return xstream.toXML(bck03521ReqBean);
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
	 * 派发规则查询
	 * @param map
	 * @return
	 */
	public static String BCK_07506(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK07506ReqBean bck07506ReqBean = new BCK07506ReqBean();
						
		//请求报文头
		bck07506ReqBean.setHeadBean(getInReqHeadBean("BCK_07506"));//交易代码
		
		//请求报文体
		BCK07506ReqAgentInfBean bck07506ReqAgentInfBean = new BCK07506ReqAgentInfBean();//创建用户信息
		bck07506ReqAgentInfBean.setACCT_NO(map.get("ACCT_NO"));//账号
		bck07506ReqAgentInfBean.setSUB_ACCT_NO(map.get("SUB_ACCT_NO"));//子账号
		bck07506ReqAgentInfBean.setACCT_BAL(map.get("ACCT_BAL"));//余额		
		BCK07506ReqBodyBean bck07506ReqBodyBean = new BCK07506ReqBodyBean();
		bck07506ReqBodyBean.setACCT_INFO(bck07506ReqAgentInfBean);//用户信息
		bck07506ReqBodyBean.setPRODUCT_CODE(map.get("PRODUCT_CODE"));//产品代码
		bck07506ReqBodyBean.setDEP_TERM(map.get("DEP_TERM"));//存期
		bck07506ReqBodyBean.setAMT(map.get("AMT"));//金额
		bck07506ReqBodyBean.setACCEPT_DATE(map.get("ACCEPT_DATE"));//兑付日期
		bck07506ReqBodyBean.setIN_INST_NO(map.get("IN_INST_NO"));//查询机构 
		bck07506ReqBodyBean.setACT_CHNL(map.get("ACT_CHNL"));//活动渠道
		bck07506ReqBodyBean.setDETAIL_NUM(map.get("DETAIL_NUM"));//循环数
		bck07506ReqBean.setBody(bck07506ReqBodyBean);
		xstream.alias("ROOT", BCK07506ReqBean.class);
		xstream.alias("HEAD", InReqHeadBean.class);
		xstream.alias("BODY", BCK07506ReqBodyBean.class);
		return xstream.toXML(bck07506ReqBean);
	}
	/**
	 * 唐豆兑付
	 * @param map
	 * @return
	 */
	public static String BCK_07505(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);		
		BCK07505ReqBean bck07505ReqBean = new BCK07505ReqBean();
		//请求报文头
		bck07505ReqBean.setHeadBean(getInReqHeadBean("BCK_07505"));//交易代码
		
		//请求报文体
		BCK07505AcctInfo bck07505AcctInfo = new BCK07505AcctInfo();//用户信息
		bck07505AcctInfo.setACCT_NO(map.get("ACCT_NO"));//账号
		bck07505AcctInfo.setSUB_ACCT_NO(map.get("SUB_ACCT_NO"));//子账号
		bck07505AcctInfo.setACCT_BAL(map.get("ACCT_BAL"));//余额	
		BCK07505ReqBodyBean bck07505ReqBodyBean = new BCK07505ReqBodyBean();
		bck07505ReqBodyBean.setACCT_INFO(bck07505AcctInfo);
		bck07505ReqBodyBean.setPRODUCT_CODE(map.get("PRODUCT_CODE"));//产品代码
		bck07505ReqBodyBean.setDEP_TERM(map.get("DEP_TERM"));//存期
		bck07505ReqBodyBean.setACCEPT_DATE(map.get("ACCEPT_DATE"));//兑付日
		bck07505ReqBodyBean.setTOTAL_BAL(map.get("TOTAL_BAL"));//账户总余额
		bck07505ReqBodyBean.setTERM_CODE(map.get("TERM_CODE"));//唐豆期次代码
		bck07505ReqBodyBean.setCOUNT(map.get("COUNT"));//唐豆数量
		bck07505ReqBodyBean.setEXCHANGE_MODE(map.get("EXCHANGE_MODE"));//唐豆兑换方式
		bck07505ReqBodyBean.setEXCHANGE_PROP(map.get("EXCHANGE_PROP"));//唐豆兑换比例
		bck07505ReqBodyBean.setEXCHANGE_AMT(map.get("EXCHANGE_AMT"));//兑现金额
		bck07505ReqBodyBean.setCUSTOM_MANAGER_NO(map.get("CUSTOM_MANAGER_NO"));//客户经理号
		bck07505ReqBodyBean.setCUSTOM_MANAGER_NAME(map.get("CUSTOM_MANAGER_NAME"));//客户经理名称
		bck07505ReqBodyBean.setOPPO_ACCT_NO(map.get("OPPO_ACCT_NO"));//对方账户
		bck07505ReqBodyBean.setACT_CHNL(map.get("ACT_CHNL"));//活动渠道
		bck07505ReqBodyBean.setDEAL_TYPE(map.get("DEAL_TYPE"));//历史唐豆处理标志
		bck07505ReqBean.setBody(bck07505ReqBodyBean);
		xstream.alias("ROOT", BCK07505ReqBean.class);
		xstream.alias("HEAD", InReqHeadBean.class);
		xstream.alias("BODY", BCK07505ReqBodyBean.class);
		return xstream.toXML(bck07505ReqBean);
	}
	/**
	 * 个人存款开户
	 * @param
	 * @return
	 */
	public static String BCK_03870(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);		
		BCK03870ReqBean bck03870ReqBean = new BCK03870ReqBean();
		//请求报文头
		bck03870ReqBean.setHeadBean(getInReqHeadBean("BCK_03870"));//交易代码
		//请求报文体
		BCK03870ReqBodyBean bck03870ReqBodyBean = new BCK03870ReqBodyBean();
		bck03870ReqBodyBean.setTRAN_CHANNEL(map.get("TRAN_CHANNEL"));//渠道号
		bck03870ReqBodyBean.setCURRENCY(map.get("CURRENCY"));//币种
		bck03870ReqBodyBean.setCAL_MODE(map.get("CAL_MODE"));//结算方式
		bck03870ReqBodyBean.setHAV_AGENT_FLAG(map.get("HAV_AGENT_FLAG"));//是否有代理人标志
		bck03870ReqBodyBean.setID_TYPE(map.get("ID_TYPE"));//证件类型
		bck03870ReqBodyBean.setCASH_ANALY_NO(map.get("CASH_ANALY_NO"));//现金分析号
		
		bck03870ReqBodyBean.setCARD_NO(map.get("CARD_NO"));//卡号
		bck03870ReqBodyBean.setPASSWORD(map.get("PASSWORD"));//密码
		bck03870ReqBodyBean.setID_NO(map.get("ID_NO"));//证件号码
		bck03870ReqBodyBean.setCUST_NO(map.get("CUST_NO"));//客户号
		bck03870ReqBodyBean.setPRO_CODE(map.get("PRO_CODE"));//产品代码
		bck03870ReqBodyBean.setDEP_UNIT(map.get("DEP_UNIT"));//存期单位
		bck03870ReqBodyBean.setDEP_TERM(map.get("DEP_TERM"));//存期数字
		bck03870ReqBodyBean.setOPPO_ACCT_NO(map.get("OPPO_ACCT_NO"));//对方账号
		bck03870ReqBodyBean.setAUTO_REDP_FLAG(map.get("AUTO_REDP_FLAG"));//自动转存标志
		bck03870ReqBodyBean.setLOAD_BAL(map.get("LOAD_BAL"));//存款金额
		bck03870ReqBodyBean.setCUST_LEVEL(map.get("CUST_LEVEL"));//客户评级
		bck03870ReqBodyBean.setRULE_NO(map.get("RULE_NO"));//千禧收益方式编号
		
		if(map.get("RELA_ACCT_NO")==null){
			bck03870ReqBodyBean.setRELA_ACCT_NO("");//收益账号
		}else{
			bck03870ReqBodyBean.setRELA_ACCT_NO(map.get("RELA_ACCT_NO"));//收益账号
		}
		bck03870ReqBodyBean.setCERT_PRINT(map.get("CERT_PRINT"));//是否打印存单0不打印，1打印
		Object object1 = map.get("SUB_RELA_ACCT_NO");
		if(null != object1){//该字段不传默认为空串，当为电子版时有值
			bck03870ReqBodyBean.setSUB_RELA_ACCT_NO(map.get("SUB_RELA_ACCT_NO").toString());//关联子账号
		}else{
			bck03870ReqBodyBean.setSUB_RELA_ACCT_NO("");//关联子账号
		}
		String chlid = map.get("CHL_ID");
		if(null != chlid && !"".equals(chlid)){//该字段不传默认为1，1为协议存款，，传3为私行快线
			bck03870ReqBodyBean.setCHL_ID(chlid);
		}else{
			bck03870ReqBodyBean.setCHL_ID("1");
		}
		
		//判断是否代理人
		if("0".equals(map.get("HAV_AGENT_FLAG"))){
			BCK03870ReqAgentInfBean bck03870ReqAgentInfBean = new BCK03870ReqAgentInfBean();//创建用户信息
			bck03870ReqAgentInfBean.setCUST_NAME(map.get("AGENT_CUST_NAME"));//客户姓名
			bck03870ReqAgentInfBean.setSEX(map.get("AGENT_SEX"));//性别
			bck03870ReqAgentInfBean.setID_TYPE(map.get("AGENT_ID_TYPE"));//证件类型
			bck03870ReqAgentInfBean.setID_NO(map.get("AGENT_ID_NO"));//证件号码
			bck03870ReqAgentInfBean.setISSUE_DATE(map.get("AGENT_ISSUE_DATE"));//签发日期
			bck03870ReqAgentInfBean.setDUE_DATE(map.get("AGENT_DUE_DATE"));//到期日期
			bck03870ReqAgentInfBean.setISSUE_INST(map.get("AGENT_ISSUE_INST"));//签发机关
			bck03870ReqAgentInfBean.setNATION(map.get("AGENT_NATION"));//国籍
			bck03870ReqAgentInfBean.setOCCUPATION(map.get("AGENT_OCCUPATION"));//职业 
			bck03870ReqAgentInfBean.setREGIS_PER_RES(map.get("AGENT_REGIS_PER_RES"));//户口所在地
			bck03870ReqAgentInfBean.setJ_C_ADD(map.get("AGENT_J_C_ADD"));//经常居住地
			bck03870ReqAgentInfBean.setTELEPHONE(map.get("AGENT_TELEPHONE"));//固定电话
			bck03870ReqAgentInfBean.setMOB_PHONE(map.get("AGENT_MOB_PHONE"));//移动电话
			bck03870ReqBodyBean.setAGENT_INF(bck03870ReqAgentInfBean);
		}
		
		bck03870ReqBean.setBody(bck03870ReqBodyBean);
		xstream.alias("ROOT", BCK03870ReqBean.class);
		xstream.alias("HEAD", InReqHeadBean.class);
		xstream.alias("BODY", BCK03870ReqBodyBean.class);
		return xstream.toXML(bck03870ReqBean);
	}
	/**
	 * 存单打印 
	 * @param map
	 * @return
	 */
	public static String BCK_03514(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		BCK03514ReqBean bck03514ReqBean = new BCK03514ReqBean();
		
		//请求报文头
		bck03514ReqBean.setHeadBean(getInReqHeadBean("BCK_03514"));//交易代码
		//请求报文体
		BCK03514ReqBodyBean bck03514ReqBodyBean = new BCK03514ReqBodyBean();
		bck03514ReqBodyBean.setACCT_NO(map.get("ACCT_NO")); //卡号
		bck03514ReqBodyBean.setSUB_ACCT_NO(map.get("SUB_ACCT_NO"));//子账号
		bck03514ReqBodyBean.setCERT_NO_ADD(map.get("CERT_NO_ADD"));//凭证号
		bck03514ReqBean.setBody(bck03514ReqBodyBean);
		xstream.alias("Root", BCK03514ReqBean.class);
		return xstream.toXML(bck03514ReqBean);
	}
	
	/**
	 * 产品利率信息
	 * @param map
	 * @return
	 */
	public static String BCK_02867(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		BCK02867ReqBean bck02867ReqBean = new BCK02867ReqBean();
		bck02867ReqBean.setHeadBean(getInReqHeadBean("BCK_02867"));
		BCK02867ReqBodyBean bck02867ReqBodyBean = new BCK02867ReqBodyBean();
		bck02867ReqBodyBean.setCHL_ID(map.get("CHL_ID")); //设置渠道模块标识
		bck02867ReqBodyBean.setCUST_NO(map.get("CUST_NO"));//设置客户号
		bck02867ReqBodyBean.setDEP_AMT(map.get("DEP_AMT"));//设置最低起存
		bck02867ReqBodyBean.setMAX_AMT(map.get("MAX_AMT"));//设置最高起存
		bck02867ReqBodyBean.setPRODUCT_CODE(map.get("PRODUCT_CODE"));//设置产品代码
		bck02867ReqBodyBean.setQRY_TYPE(map.get("QRY_TYPE"));//设置查询类型
		bck02867ReqBodyBean.setCUST_LEVEL(map.get("CUST_LEVEL"));//客户评级
		bck02867ReqBean.setBody(bck02867ReqBodyBean);
		xstream.alias("Root", BCK02867ReqBean.class);
		return xstream.toXML(bck02867ReqBean);
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
	 * 身份核查
	 * @return
	 */
	public static String BCK_07670(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK07670ReqBean bCK07670ReqBean = new BCK07670ReqBean();
		InReqHeadBean inReqHeadBean = new InReqHeadBean();
		inReqHeadBean.setTradeCode("BCK_07670");	
		inReqHeadBean.setMachineNo(GlobalParameter.machineNo);
		inReqHeadBean.setBranchNo(GlobalParameter.branchNo);
		inReqHeadBean.setMachineType(GlobalParameter.MACHINE_TYPE);
		bCK07670ReqBean.setHeadBean(inReqHeadBean);		
		BCK07670ReqBodyBean bCK07670ReqBodyBean = new BCK07670ReqBodyBean();
		bCK07670ReqBodyBean.setID(map.get("ID"));
		bCK07670ReqBodyBean.setNAME(map.get("NAME"));
		bCK07670ReqBean.setBody(bCK07670ReqBodyBean);
		xstream.alias("ROOT", BCK07670ReqBean.class);
		xstream.alias("HEAD", InReqHeadBean.class);
		xstream.alias("BODY", BCK07670ReqBodyBean.class);
		return xstream.toXML(bCK07670ReqBean);
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
	 * 代理人身份信息查询
	 * @return
	 */
	public static String BCK_08021(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK08021ReqBean bCK08021ReqBean = new BCK08021ReqBean();
		InReqHeadBean inReqHeadBean = new InReqHeadBean();
		inReqHeadBean.setTradeCode("BCK_08021");	
		inReqHeadBean.setMachineNo(GlobalParameter.machineNo);
		inReqHeadBean.setBranchNo(GlobalParameter.branchNo);
		inReqHeadBean.setMachineType(GlobalParameter.MACHINE_TYPE);
		bCK08021ReqBean.setHeadBean(inReqHeadBean);		
		BCK08021ReqBodyBean bCK08021ReqBodyBean = new BCK08021ReqBodyBean();
		bCK08021ReqBodyBean.setID_NO(map.get("ID_NO"));
		bCK08021ReqBodyBean.setNAME(map.get("NAME"));
		bCK08021ReqBean.setBody(bCK08021ReqBodyBean);
		xstream.alias("ROOT", BCK08021ReqBean.class);
		xstream.alias("HEAD", InReqHeadBean.class);
		xstream.alias("BODY", BCK08021ReqBodyBean.class);
		return xstream.toXML(bCK08021ReqBean);
	}
	
	/**
	 * 客户基本信息查询 04422
	 * @return
	 */
	public static String BCK_04422(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK04422ReqBean bCK04422ReqBean = new BCK04422ReqBean();
		InReqHeadBean inReqHeadBean = new InReqHeadBean();
		inReqHeadBean.setTradeCode("BCK_04422");	
		inReqHeadBean.setMachineNo(GlobalParameter.machineNo);
		inReqHeadBean.setBranchNo(GlobalParameter.branchNo);
		inReqHeadBean.setMachineType(GlobalParameter.MACHINE_TYPE);
		bCK04422ReqBean.setHeadBean(inReqHeadBean);		
		BCK04422ReqBodyBean bCK04422ReqBodyBean = new BCK04422ReqBodyBean();
		bCK04422ReqBodyBean.setRESOLVE_TYPE(map.get("RESOLVE_TYPE"));
		bCK04422ReqBodyBean.setECIF_CUST_NO(map.get("ECIF_CUST_NO"));
		bCK04422ReqBodyBean.setPARTY_NAME(map.get("PARTY_NAME"));
		bCK04422ReqBodyBean.setCERT_TYPE(map.get("CERT_TYPE"));
		bCK04422ReqBodyBean.setCERT_NO(map.get("CERT_NO"));
		bCK04422ReqBodyBean.setACCT_NO(map.get("ACCT_NO"));
		bCK04422ReqBean.setBody(bCK04422ReqBodyBean);
		xstream.alias("ROOT", BCK04422ReqBean.class);
		xstream.alias("HEAD", InReqHeadBean.class);
		xstream.alias("BODY", BCK04422ReqBodyBean.class);
		return xstream.toXML(bCK04422ReqBean);
	}
	
	/**
	 * 客户评级查询03524
	 * @return
	 */
	public static String BCK_03524(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK03524ReqBean bCK03524ReqBean = new BCK03524ReqBean();
		InReqHeadBean inReqHeadBean = new InReqHeadBean();
		inReqHeadBean.setTradeCode("BCK_03524");	
		inReqHeadBean.setMachineNo(GlobalParameter.machineNo);
		inReqHeadBean.setBranchNo(GlobalParameter.branchNo);
		inReqHeadBean.setMachineType(GlobalParameter.MACHINE_TYPE);
		bCK03524ReqBean.setHeadBean(inReqHeadBean);		
		BCK03524ReqBodyBean bCK03524ReqBodyBean = new BCK03524ReqBodyBean();
		bCK03524ReqBodyBean.setACCT_NO(map.get("ACCT_NO"));
		bCK03524ReqBodyBean.setCUST_NO(map.get("CUST_NO"));
		bCK03524ReqBean.setBody(bCK03524ReqBodyBean);
		xstream.alias("ROOT", BCK03524ReqBean.class);
		xstream.alias("HEAD", InReqHeadBean.class);
		xstream.alias("BODY", BCK03524ReqBodyBean.class);
		return xstream.toXML(bCK03524ReqBean);
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
	 * 银行卡开户入库
	 */
	public static String saveOpenAccXml(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		TmsAccountSaveOrderReqBean accountSaveOrderReqBean = new TmsAccountSaveOrderReqBean();
		InReqHeadBean inReqHeadBean = getInReqHeadBean("ACCOUNT_0001");
		accountSaveOrderReqBean.setHeadBean(inReqHeadBean);
		
		TmsAccountSaveOrderReqBodyBean accountSaveOrderReqBodyBean = new TmsAccountSaveOrderReqBodyBean();
		accountSaveOrderReqBodyBean.setCARD_NO(map.get("CARD_NO"));//卡号
		accountSaveOrderReqBodyBean.setCERT_NO(map.get("CERT_NO"));//凭证号
		accountSaveOrderReqBodyBean.setCUSTOMER_NAME(map.get("CUSTOMER_NAME"));//户名
		accountSaveOrderReqBodyBean.setDEPOSIT_AMT(map.get("DEPOSIT_AMT"));//存款金额
		accountSaveOrderReqBodyBean.setDEPOSIT_PASSWORD_ENABLED(map.get("DEPOSIT_PASSWORD_ENABLED"));//是否加密
		accountSaveOrderReqBodyBean.setDEPOSIT_PERIOD(map.get("DEPOSIT_PERIOD"));//大写存期
		accountSaveOrderReqBodyBean.setDEPOSIT_RESAVE_ENABLED(map.get("DEPOSIT_RESAVE_ENABLED"));//是否转存  1：是。0：否
		accountSaveOrderReqBodyBean.setPRODUCT_CODE(map.get("PRODUCT_CODE"));//产品编号
		accountSaveOrderReqBodyBean.setPRODUCT_NAME(map.get("PRODUCT_NAME"));//产品名称
		accountSaveOrderReqBodyBean.setRATE(map.get("RATE"));//利率
		accountSaveOrderReqBodyBean.setREP_02808(map.get("Res03870"));//开户返回报文
		accountSaveOrderReqBodyBean.setREP_07505(map.get("Res07505"));//唐豆派发查询返回报文
		accountSaveOrderReqBodyBean.setREP_07506(map.get("Res07506"));//唐豆兑付返回报文
		
		accountSaveOrderReqBodyBean.setSUB_ACCOUNT_NO(map.get("SUB_ACCOUNT_NO"));//存单账号
		accountSaveOrderReqBodyBean.setTERMINAL_CODE(map.get("TERMINAL_CODE"));//机器出厂编号
		accountSaveOrderReqBodyBean.setUNIT_CODE(map.get("UNIT_CODE"));//机构编号
		accountSaveOrderReqBodyBean.setUNIT_NAME(map.get("UNIT_NAME"));//机构名称
		accountSaveOrderReqBodyBean.setPAY_TYPE(map.get("PAY_TYPE"));//开户支付类型
		accountSaveOrderReqBean.setBody(accountSaveOrderReqBodyBean);
		
		xstream.alias("Root", TmsAccountSaveOrderReqBean.class);
		xstream.alias("Head", InReqHeadBean.class);
		xstream.alias("Body", TmsAccountSaveOrderReqBodyBean.class);
		return xstream.toXML(accountSaveOrderReqBean);
	}
	
	/**
	 * 查询图片上传路径
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public static String checkUploadPathXml(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		CdjOpenAccReqBean cdjOpenAccReqBean = new CdjOpenAccReqBean();
		com.boomhope.tms.message.cdjmac.InReqHeadBean inReqHeadBean = new com.boomhope.tms.message.cdjmac.InReqHeadBean();
		inReqHeadBean.setTradeCode("openAccMsg");
		cdjOpenAccReqBean.setHeadBean(inReqHeadBean);
		
		CdjOpenAccReqBodyBean cdjOpenAccReqBodyBean = new CdjOpenAccReqBodyBean();
		cdjOpenAccReqBodyBean.setOpenAccNo(map.get("openAccNo"));//开户账户
		cdjOpenAccReqBodyBean.setOpenAccDate(map.get("openAccDate"));//开户日期
		
		cdjOpenAccReqBean.setBody(cdjOpenAccReqBodyBean);
		
		xstream.alias("Root", CdjOpenAccReqBean.class);
		xstream.alias("Head", com.boomhope.tms.message.cdjmac.InReqHeadBean.class);
		xstream.alias("Body", CdjOpenAccReqBodyBean.class);
		return xstream.toXML(cdjOpenAccReqBean);
	} 
}
