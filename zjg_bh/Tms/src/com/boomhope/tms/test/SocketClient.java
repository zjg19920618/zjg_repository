package com.boomhope.tms.test;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.boomhope.tms.entity.BusFlow;
import com.boomhope.tms.message.XStreamNameCoder;
import com.boomhope.tms.message.in.InReqHeadBean;
import com.boomhope.tms.message.in.bck.BCK0001ReqBean;
import com.boomhope.tms.message.in.bck.BCK0001ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK0002ReqBean;
import com.boomhope.tms.message.in.bck.BCK0002ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK0003ReqBean;
import com.boomhope.tms.message.in.bck.BCK0003ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK0004ReqAgentInfBean;
import com.boomhope.tms.message.in.bck.BCK0004ReqBean;
import com.boomhope.tms.message.in.bck.BCK0004ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK0005ReqBean;
import com.boomhope.tms.message.in.bck.BCK0005ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK0006ReqBean;
import com.boomhope.tms.message.in.bck.BCK0006ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK0007ReqBean;
import com.boomhope.tms.message.in.bck.BCK0007ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK0008ReqBean;
import com.boomhope.tms.message.in.bck.BCK0008ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK0009ReqBean;
import com.boomhope.tms.message.in.bck.BCK0009ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK0010ReqBean;
import com.boomhope.tms.message.in.bck.BCK0010ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK0010ResBean;
import com.boomhope.tms.message.in.bck.BCK0011ReqBean;
import com.boomhope.tms.message.in.bck.BCK0011ReqBodyBean;
import com.boomhope.tms.message.in.tms.Tms0001ReqBean;
import com.boomhope.tms.message.in.tms.Tms0001ReqBodyBean;
import com.boomhope.tms.message.in.tms.Tms0002PeriBean;
import com.boomhope.tms.message.in.tms.Tms0002ReqBean;
import com.boomhope.tms.message.in.tms.Tms0002ReqBodyBean;
import com.boomhope.tms.transaction.ConfigReader;
import com.boomhope.tms.util.DateUtil;
import com.boomhope.tms.util.Encrypt;
import com.boomhope.tms.util.EncryptUtils;
import com.boomhope.tms.util.MACProtocolUtils;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XppDriver;

public class SocketClient {

	/**
	 * @param args
	 */

	@SuppressWarnings("resource")
	public static void main(String []args) {
		BusFlow busFlow = new BusFlow();
		busFlow.getDeployMachine();
		System.out.println(busFlow);
		
//		System.out.println("CLIENT retMsg:" + get07660());
		/*try {
			Socket socket = new Socket("127.0.0.1", 9999);
			
            //构建IO  
            InputStream is = socket.getInputStream();  
            OutputStream os = socket.getOutputStream();  

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));  
            //向服务器端发送一条消息  

            bw.write(get07660() + "\n");  

            bw.flush();  

            //读取服务器返回的消息  
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));  
			
			String retMsg = "";
			String value = null;
			while ((value = br.readLine()) != null) {
				retMsg += value + "\n";
				if ("</Root>".equals(value)){
					break;
				}
			}
			System.out.println(retMsg);
			XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));

			reqXs.alias("ROOT", BCK0010ResBean.class);
			reqXs.alias("HEAD", InReqHeadBean.class);
			reqXs.alias("BODY", BCK0010ReqBodyBean.class);
			
			BCK0010ResBean bck0010ResBean = (BCK0010ResBean)reqXs.fromXML(retMsg);
			System.out.println(bck0010ResBean);
			System.out.println("CLIENT retMsg:" + retMsg);
           
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
	}
	
	@Test
	public void get0018() throws Exception{
		String str = "<?xml version=\"1.0\" encoding=\"GBK\" ?>"
				+ "<ROOT>"
				+ "<HEAD>"
				+ "<TRAN_CODE>03519</TRAN_CODE>"
				+ "<CARD_ACCP_TERM_ID>000C0011</CARD_ACCP_TERM_ID>"
				+ "<MER_NO>123456789012345</MER_NO>"
				+ "<INST_NO>051000129</INST_NO>"
				+ "<TELLER_NO>C0011</TELLER_NO>"
				+ "<SUP_TELLER_NO></SUP_TELLER_NO>"
				+ "<CHANNEL>0035</CHANNEL>"
				+ "<SUP_TELLER_NO></SUP_TELLER_NO>"
				+ "<TERM_JRNL_NO></TERM_JRNL_NO>"
				+ "<CHL_TRAN_CODE></CHL_TRAN_CODE>"
				+ "<TRAN_DATE>"+DateUtil.getDateTime("yyyyMMdd")+"</TRAN_DATE>"
				+ "<TRAN_TIME>"+DateUtil.getDateTime("HHmmss")+"</TRAN_TIME>"
				+ "</HEAD>"
				+ "<BODY>"
				+ "<CARD_NO>6231931100000000106</CARD_NO>"
				+ "</BODY>"
				+ "</ROOT>";
		System.out.println(str);
		byte[] bbb = str.getBytes("GBK");
		String mac = ConfigReader.getConfig("encrypt.mac.zak2name");
		byte[] request = MACProtocolUtils.toRequest(mac, bbb);
		String outResMsg = new com.boomhope.tms.socket.SocketClient().sendMsg(request);
		System.out.println(outResMsg);
	}
	@Test
	public void get03446() throws Exception{
		String str = "<?xml version=\"1.0\" encoding=\"GBK\" ?>"
				+ "<ROOT>"
				+ "<HEAD>"
				+ "<TRAN_CODE>03446</TRAN_CODE>"
				+ "<CARD_ACCP_TERM_ID>000C0010</CARD_ACCP_TERM_ID>"
				+ "<MER_NO>123456789012345</MER_NO>"
				//+ "<INST_NO>051000129</INST_NO>"
				+ "<INST_NO>052000125</INST_NO>"
				+ "<TELLER_NO>C0010</TELLER_NO>"
				+ "<SUP_TELLER_NO></SUP_TELLER_NO>"
				+ "<CHANNEL>0035</CHANNEL>"
				+ "<SUP_TELLER_NO></SUP_TELLER_NO>"
				+ "<TERM_JRNL_NO></TERM_JRNL_NO>"
				+ "<CHL_TRAN_CODE></CHL_TRAN_CODE>"
				+ "<TRAN_DATE>"+DateUtil.getDateTime("yyyyMMdd")+"</TRAN_DATE>"
				+ "<TRAN_TIME>"+DateUtil.getDateTime("HHmmss")+"</TRAN_TIME>"
				+ "</HEAD>"
				+ "<BODY>"
				+ "<CUSTOM_MANAGER_NO></CUSTOM_MANAGER_NO>"
				+ "<CUST_NAME>王聪</CUST_NAME>"
				+ "<CUST_SHORT></CUST_SHORT>"
				+ "<EN_NAME_1></EN_NAME_1>"
				+ "<EN_NAME_2></EN_NAME_2>"
				+ "<CUST_SEX>1</CUST_SEX>"
				+ "<ID_TYPE>1</ID_TYPE>"
				+ "<ID_NO>23010419940305341X</ID_NO>"
				+ "<ISSUE_DATE></ISSUE_DATE>"
				+ "<DUE_DATE></DUE_DATE>"
				+ "<ISSUE_INST>河北</ISSUE_INST>"
				+ "<BIRTH_RES></BIRTH_RES>"
				+ "<BIRTH_DATE></BIRTH_DATE>"
				+ "<COUNTRY>1</COUNTRY>"
				+ "<NATION>1</NATION>"
				+ "<EDU_GRADE></EDU_GRADE>"
				+ "<MAR_STATUS></MAR_STATUS>"
				+ "<HEA_STATUS></HEA_STATUS>"
				+ "<CUST_TYPE>1</CUST_TYPE>"
				+ "<BANK_SER_LEV></BANK_SER_LEV>"
				+ "<BANK_STAFF></BANK_STAFF>"
				+ "<BANK_PART></BANK_PART>"
				+ "<BUS_STATUS>08</BUS_STATUS>"
				+ "<J_C_ADD>河北唐山</J_C_ADD>"
				+ "<DOMICILE_PLACE>河北唐山</DOMICILE_PLACE>"
				+ "<TEL_NO>18310784080</TEL_NO>"
				+ "</BODY>"
				+ "</ROOT>";
		System.out.println(str);
		byte[] bbb = str.getBytes("GBK");
		String mac = ConfigReader.getConfig("encrypt.mac.zak2name");
		byte[] request = MACProtocolUtils.toRequest(mac, bbb);
		String outResMsg = new com.boomhope.tms.socket.SocketClient().sendMsg(request);
		System.out.println(outResMsg);
	}
	//打印
	@Test
	public void get0019() throws Exception{
		String str = "<?xml version=\"1.0\" encoding=\"GBK\" ?>"
				+ "<ROOT>"
				+ "<HEAD>"
				+ "<TRAN_CODE>03514</TRAN_CODE>"
				+ "<CARD_ACCP_TERM_ID>000C0010</CARD_ACCP_TERM_ID>"
				+ "<MER_NO>123456789012345</MER_NO>"
				+ "<INST_NO>052000125</INST_NO>"
				+ "<TELLER_NO>C0010</TELLER_NO>"
				+ "<SUP_TELLER_NO></SUP_TELLER_NO>"
				+ "<CHANNEL>0035</CHANNEL>"
				+ "<SUP_TELLER_NO></SUP_TELLER_NO>"
				+ "<TERM_JRNL_NO></TERM_JRNL_NO>"
				+ "<CHL_TRAN_CODE></CHL_TRAN_CODE>"
				+ "<TRAN_DATE>"+DateUtil.getDateTime("yyyyMMdd")+"</TRAN_DATE>"
				+ "<TRAN_TIME>"+DateUtil.getDateTime("HHmmss")+"</TRAN_TIME>"
				+ "</HEAD>"
				+ "<BODY>"
				+ "<ACCT_NO>6231931100000000106</ACCT_NO>"
				+ "<SUB_ACCT_NO>92</SUB_ACCT_NO>"
				+ "<CERT_NO_ADD>04047954</CERT_NO_ADD>"
				+ "</BODY>"
				+ "</ROOT>";
		System.out.println(str);
		byte[] bbb = str.getBytes("GBK");
		String mac = ConfigReader.getConfig("encrypt.mac.zak2name");
		byte[] request = MACProtocolUtils.toRequest(mac, bbb);
		String outResMsg = new com.boomhope.tms.socket.SocketClient().sendMsg(request);
		System.out.println(outResMsg);
	}
	//打印
	@Test
	public void get0020() throws Exception{
		String str = "<?xml version=\"1.0\" encoding=\"GBK\" ?>"
				+ "<ROOT>"
				+ "<HEAD>"
				+ "<TRAN_CODE>03520</TRAN_CODE>"
				+ "<CARD_ACCP_TERM_ID>000C0010</CARD_ACCP_TERM_ID>"
				+ "<MER_NO>123456789012345</MER_NO>"
				+ "<INST_NO>052000125</INST_NO>"
				+ "<TELLER_NO>C0010</TELLER_NO>"
				+ "<SUP_TELLER_NO></SUP_TELLER_NO>"
				+ "<CHANNEL>0035</CHANNEL>"
				+ "<SUP_TELLER_NO></SUP_TELLER_NO>"
				+ "<TERM_JRNL_NO></TERM_JRNL_NO>"
				+ "<CHL_TRAN_CODE></CHL_TRAN_CODE>"
				+ "<TERM_JRNL_NO></TERM_JRNL_NO>"
				+ "<TRAN_DATE>"+DateUtil.getDateTime("yyyyMMdd")+"</TRAN_DATE>"
				+ "<TRAN_TIME>"+DateUtil.getDateTime("HHmmss")+"</TRAN_TIME>"
				+ "</HEAD>"
				+ "<BODY>"
				+ "<ACCT_NO>6231931100000000106</ACCT_NO>"
				+ "<SUB_ACCT_NO>3</SUB_ACCT_NO>"
				+ "<OPER_CHOOSE>0</OPER_CHOOSE>"
				+ "</BODY>"
				+ "</ROOT>";
		System.out.println(str);
		byte[] bbb = str.getBytes("GBK");
		String mac = ConfigReader.getConfig("encrypt.mac.zak2name");
		byte[] request = MACProtocolUtils.toRequest(mac, bbb);
		String outResMsg = new com.boomhope.tms.socket.SocketClient().sendMsg(request);
		System.out.println(outResMsg);
		}
	//打印
	@Test
	public void get03510() throws Exception{
		String str = "<?xml version=\"1.0\" encoding=\"GBK\" ?>"
				+ "<ROOT>"
				+ "<HEAD>"
				+ "<TRAN_CODE>03510</TRAN_CODE>"
				+ "<CARD_ACCP_TERM_ID>000C0010</CARD_ACCP_TERM_ID>"
				+ "<MER_NO>123456789012345</MER_NO>"
				+ "<INST_NO>052000125</INST_NO>"
				+ "<TELLER_NO>C0010</TELLER_NO>"
				+ "<SUP_TELLER_NO></SUP_TELLER_NO>"
				+ "<CHANNEL>0035</CHANNEL>"
				+ "<SUP_TELLER_NO></SUP_TELLER_NO>"
				+ "<TERM_JRNL_NO></TERM_JRNL_NO>"
				+ "<CHL_TRAN_CODE></CHL_TRAN_CODE>"
				+ "<TERM_JRNL_NO></TERM_JRNL_NO>"
				+ "<TRAN_DATE>"+DateUtil.getDateTime("yyyyMMdd")+"</TRAN_DATE>"
				+ "<TRAN_TIME>"+DateUtil.getDateTime("HHmmss")+"</TRAN_TIME>"
				+ "</HEAD>"
				+ "<BODY>"
				+ "<ACCT_NO></ACCT_NO>"
				+ "<PROD_CODE>RYA20006</PROD_CODE>"
				+ "<AMT>150000</AMT>"
				+ "<OPEN_DATE>20161201</OPEN_DATE>"
				+ "<DRAW_AMT_DATE>20170301</DRAW_AMT_DATE>"
				+ "<CUST_NO>113207830</CUST_NO>"
				+ "<OPEN_CHL>0035</OPEN_CHL>"
				+ "</BODY>"
				+ "</ROOT>";
		System.out.println(str);
		byte[] bbb = str.getBytes("GBK");
		String mac = ConfigReader.getConfig("encrypt.mac.zak2name");
		byte[] request = MACProtocolUtils.toRequest(mac, bbb);
		String outResMsg = new com.boomhope.tms.socket.SocketClient().sendMsg(request);
		System.out.println(outResMsg);
	}
	/**
	 * 设备登录报文
	 */
	private static String getTms0001(){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));

		xstream.autodetectAnnotations(true);
		Tms0001ReqBean tms0001ReqBean = new Tms0001ReqBean();
		Tms0001ReqBodyBean tms0001ReqBodyBean = new Tms0001ReqBodyBean();
		tms0001ReqBodyBean.setMachineIp("192.168.1.231");
		tms0001ReqBean.setBodyBean(tms0001ReqBodyBean);
		InReqHeadBean inReqHeadBean = new InReqHeadBean();
		inReqHeadBean.setTradeCode("TMS_0001");
		inReqHeadBean.setMachineType("BCK-存单回收机");
		tms0001ReqBean.setHeadBean(inReqHeadBean);
		xstream.alias("Root", BCK0011ReqBean.class);
		return xstream.toXML(tms0001ReqBean);
	}
	
	/***
	 * 生成测试报文
	 * @return
	 */
	private static String getMsg(){
		
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));

		xstream.autodetectAnnotations(true);
		
		/* TMS_0001报文测试 */
		List<Tms0002PeriBean> periList = new ArrayList<Tms0002PeriBean>();
		
		/* 外设信息 */
		Tms0002PeriBean periBean = new Tms0002PeriBean();
		periBean.setPeriId("peri001");
		periBean.setPeriStatus("1");
		periList.add(periBean);
		
		Tms0002PeriBean periBean2 = new Tms0002PeriBean();
		periBean2.setPeriId("peri002");
		periBean2.setPeriStatus("2");
		periList.add(periBean2);
		
		/* 请求报文体 */
		Tms0002ReqBodyBean bodyBean = new Tms0002ReqBodyBean();
		bodyBean.setMachineStatus("1");
		bodyBean.setPeriList(periList);
		
		/* 请求报文头 */
		InReqHeadBean headBean = new InReqHeadBean();
		headBean.setTradeCode("TMS_0002");
		headBean.setMachineNo("TS000001");
		
		/* 请求报文 */
		Tms0002ReqBean reqBean = new Tms0002ReqBean();
		reqBean.setHeadBean(headBean);
		reqBean.setBodyBean(bodyBean);

		xstream.alias("ROOT", Tms0002ReqBean.class);
		xstream.alias("HEAD", InReqHeadBean.class);
		xstream.alias("BODY", Tms0002ReqBodyBean.class);
		
		return xstream.toXML(reqBean);
	}
	/**
	 * 测试证件信息报文
	 * @return
	 */
	private static String IdCode(){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		/**
		 * 请求报文头
		 */
		InReqHeadBean headBean = new InReqHeadBean();
		headBean.setTradeCode("BCK_0001");
		headBean.setBranchNo("BranchNo");
		headBean.setMachineNo("BranchNo");
		headBean.setMachineType("1111");
		
		/**
		 * 请求报文体
		 */
		BCK0001ReqBodyBean bck0001ReqBodyBean = new BCK0001ReqBodyBean();
		bck0001ReqBodyBean.setIdType("1");;
		bck0001ReqBodyBean.setIdNo("130221197012016311");
		bck0001ReqBodyBean.setCustomName("金继存");
		
		BCK0001ReqBean bck0001ReqBean = new BCK0001ReqBean();
		bck0001ReqBean.setBody(bck0001ReqBodyBean);
		bck0001ReqBean.setHeadBean(headBean);
		
		xstream.alias("ROOT", BCK0001ReqBean.class);
		xstream.alias("HEAD", InReqHeadBean.class);
		xstream.alias("BODY", BCK0001ReqBodyBean.class);
		return xstream.toXML(bck0001ReqBean);
	}
	/**
	 * 账号信息综合查询
	 * @return
	 */
	private static String getId02880(){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		
		InReqHeadBean inReqHeadBean = new InReqHeadBean();
		inReqHeadBean.setTradeCode("BCK_0002");
		inReqHeadBean.setBranchNo("BranchNo");
		inReqHeadBean.setMachineNo("BranchNo");
		inReqHeadBean.setMachineType("1111");
		
		BCK0002ReqBean bck0002ReqBean = new BCK0002ReqBean();
		bck0002ReqBean.setHeadBean(inReqHeadBean);
		
		BCK0002ReqBodyBean bck0002ReqBodyBean = new BCK0002ReqBodyBean();
		bck0002ReqBodyBean.setAcctNo("051000129001001200246");
		bck0002ReqBodyBean.setChkPin("0");
		bck0002ReqBodyBean.setPasswd("");
		bck0002ReqBodyBean.setSubAcctNo("");
		bck0002ReqBean.setBody(bck0002ReqBodyBean);
		xstream.alias("ROOT", BCK0002ReqBean.class);
		xstream.alias("HEAD", InReqHeadBean.class);
		xstream.alias("BODY", BCK0002ReqBodyBean.class);
		return xstream.toXML(bck0002ReqBean);
	}
	/**
	 * 预计利息查询
	 * @return
	 */
	private static String get02944(){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		/**
		 * 请求报文头
		 */
		InReqHeadBean headBean = new InReqHeadBean();
		headBean.setTradeCode("BCK_0003");
		headBean.setBranchNo("BranchNo");
		headBean.setMachineNo("BranchNo");
		headBean.setMachineType("1111");
		
		/**
		 * 请求报文体
		 */
		BCK0003ReqBodyBean bck0003ReqBodyBean = new BCK0003ReqBodyBean();
		bck0003ReqBodyBean.setAcctNO("051000129001001070581");
		
		/**
		 * 组装Root
		 */
		BCK0003ReqBean bck0003ReqBean = new BCK0003ReqBean();
		bck0003ReqBean.setBody(bck0003ReqBodyBean);
		bck0003ReqBean.setHeadBean(headBean);
		
		xstream.alias("ROOT", BCK0003ReqBean.class);
		xstream.alias("HEAD", InReqHeadBean.class);
		xstream.alias("BODY", BCK0003ReqBodyBean.class);
		return xstream.toXML(bck0003ReqBean);
	}
	/**
	 * 个人账户销户
	 */
	private static String get02026(){
		/* 解析请求报文 */
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		
		//拼接输出的body
		BCK0004ReqBodyBean reqBodyBean = new BCK0004ReqBodyBean();
		reqBodyBean.setACCT_NO("051000129001001200238");//账号
		reqBodyBean.setCERT_NO("03826861");//凭证号
		reqBodyBean.setCUST_NO("113207805");//客户号
		reqBodyBean.setCUST_NAME("金继存");//客户名称
		reqBodyBean.setDRAW_COND("1");//支付条件(0-无;1-凭密码;2-凭证件;3-凭印鉴;4_凭印鉴和密码;3、4需要电子验印)
		if(reqBodyBean.getDRAW_COND().equals("1")){
			/*凭证密码加密*/
			String pinKeyName="CODM.00000001.zpk";
			String pin="123456";
			String acctNo = reqBodyBean.getACCT_NO();
			System.out.println("-----------acctNo:"+acctNo);
			String password = EncryptUtils.encryPin433(pinKeyName, pin,acctNo);
			reqBodyBean.setPASSWORD(password);//密码
			System.out.println("密码pin加密结果："+reqBodyBean.getPASSWORD());
		}else{
			reqBodyBean.setPASSWORD("");//密码
		}
		reqBodyBean.setCURRENCY("CNY");//货币号
		reqBodyBean.setPROD_CODE("");//产品代码
		reqBodyBean.setPROD_NAME("");//产品名称
		reqBodyBean.setBALANCE("20000.00");//余额
		reqBodyBean.setDEP_TERM("");//存期
		reqBodyBean.setSTART_INT_DATE("");//起息日
		reqBodyBean.setOPEN_RATE("");//开户利率
		reqBodyBean.setCYC_AMT("");//周期金额
		reqBodyBean.setCYC("");//周期
		reqBodyBean.setTIMES("");//次数
		reqBodyBean.setBES_AMT("");//预约金额
		reqBodyBean.setBES_DATE("");//预约日期
		reqBodyBean.setDRAW_CURRENCY("00");//支取币种
		reqBodyBean.setPRI_INTE_FLAG("1");//本息分开（必输，0否、1是）
		reqBodyBean.setCANCEL_AMT("20000.00");//销户金额
		reqBodyBean.setPRI_INTE_WAY("");//本息走向
		reqBodyBean.setOPPO_ACCT_NO("");//对方账号（本息转账，有）
		reqBodyBean.setSUB_ACCT_NO("0");//子账号（必输项，对方账号为一本通使显示，默认为0基本账户可修改）
		reqBodyBean.setPRI_WAY("");//本金走向
		reqBodyBean.setOPPO_ACCT_NO1("");//对方账号（本金转账，本金、利息分开时，对方账户不允许为同一账户）
		reqBodyBean.setSUB_ACCT_NO1("0");//子账号（必输项，对方账号为一本通使显示，默认为0基本账户可修改）
		reqBodyBean.setINTE_WAY("");//利息走向
		reqBodyBean.setOPPO_ACCT_NO2("");//对方账号（利息转账，有）
		reqBodyBean.setSUB_ACCT_NO2("0");//子账号（必输项，对方账号为一本通使显示，默认为0基本账户可修改）
		reqBodyBean.setID_TYPE("");//证件类型
		reqBodyBean.setID_NO("13028119871002204X");//证件号码
		reqBodyBean.setPROVE_FLAG("");//证明标志
		reqBodyBean.setCASH_ANALY_NO("");//现金分析号
		reqBodyBean.setHAV_AGENT_FLAG("1");//是否有代理人标志
		if(reqBodyBean.getHAV_AGENT_FLAG().equals("1")){
			//拼接输出的AGENT_INF
			BCK0004ReqAgentInfBean reqAgentInfBean = new BCK0004ReqAgentInfBean();
			reqAgentInfBean.setCUST_NAME("张蒙");//客户姓名
			reqAgentInfBean.setDUE_DATE("");//到期日期
			reqAgentInfBean.setID_NO("130682199109264075");//证件号码
			reqAgentInfBean.setID_TYPE("1");//证件类别
			reqAgentInfBean.setISSUE_DATE("");//签发日期
			reqAgentInfBean.setISSUE_INST("定州市公安局");//签发机关
			reqAgentInfBean.setJ_C_ADD("");//经常居住地
			reqAgentInfBean.setMOB_PHONE("");//移动电话
			reqAgentInfBean.setNATION("");//国籍
			reqAgentInfBean.setOCCUPATION("公务员");//职业
			reqAgentInfBean.setREGIS_PER_RES("河北省定州市周村镇前屯村67号");//户口所在地
			reqAgentInfBean.setSEX("男");//性别
			reqAgentInfBean.setTELEPHONE("");//固定电话
			
			reqBodyBean.setAGENT_INF(reqAgentInfBean);
		}
		//拼接输出的head
		InReqHeadBean inReqHeadBean = new InReqHeadBean();;
		inReqHeadBean.setTradeCode("BCK_0004");
		inReqHeadBean.setBranchNo("BranchNo");
		inReqHeadBean.setMachineNo("BranchNo");
		inReqHeadBean.setMachineType("1111");
		
		//拼接root类
		BCK0004ReqBean bck0004ReqBean = new BCK0004ReqBean();
		bck0004ReqBean.setBody(reqBodyBean);
		bck0004ReqBean.setHeadBean(inReqHeadBean);
		
		reqXs.alias("Root", BCK0004ReqBean.class);
		return reqXs.toXML(bck0004ReqBean);
	}
	/**
	 * 唐豆收回查询
	 */
	private static String get02210(){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		BCK0005ReqBean bck0005ReqBean = new BCK0005ReqBean();
		BCK0005ReqBodyBean bck0005ReqBodyBean = new BCK0005ReqBodyBean();
		bck0005ReqBodyBean.setAcctNo("051000129001001199984");
		bck0005ReqBodyBean.setPayDate("20160921");
		bck0005ReqBodyBean.setPayJrnl("3530");
		bck0005ReqBodyBean.setSubAcctNo("");
		bck0005ReqBean.setBody(bck0005ReqBodyBean);
		InReqHeadBean inReqHeadBean = new InReqHeadBean();
		inReqHeadBean.setTradeCode("BCK_0005");
		inReqHeadBean.setBranchNo("BranchNo");
		inReqHeadBean.setMachineNo("BranchNo");
		inReqHeadBean.setMachineType("1111");
		bck0005ReqBean.setHeadBean(inReqHeadBean);
		xstream.alias("Root", BCK0005ReqBean.class);
		return xstream.toXML(bck0005ReqBean);
	}
	/**
	 * 唐豆收回
	 */
	private static String get02209(){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		BCK0006ReqBean bck0006ReqBean = new BCK0006ReqBean();
		BCK0006ReqBodyBean bck0006ReqBodyBean = new BCK0006ReqBodyBean();
		bck0006ReqBodyBean.setACCT_NO("051000129001001199005");
		bck0006ReqBodyBean.setBACK_COUNT("47500");
		bck0006ReqBodyBean.setBACK_EXCHANGE_AMT("95");
		bck0006ReqBodyBean.setBACK_PROP("");//收回比例
		bck0006ReqBodyBean.setOPPO_ACCT_NO("");//对方账号
		bck0006ReqBodyBean.setORG_EXCHANGE_MODE("");//原唐豆兑换方式(0-现金，1-转账，2-兑物)
		bck0006ReqBodyBean.setORG_EXCHANGE_PROP("");//原唐豆兑现比例
		bck0006ReqBodyBean.setPASSWORD("");//对方账号密码
		bck0006ReqBodyBean.setPAY_AMT("20000.00");//支取金额
		bck0006ReqBodyBean.setPAY_DATE(DateUtil.getDateTime("yyyyMMdd"));//支取日期
		bck0006ReqBodyBean.setPAY_JRNL("2970");//支取流水
		bck0006ReqBodyBean.setRECOV_TYPE("0");//唐豆收回方式(0-现金 1-转账 2-兑物)
		bck0006ReqBodyBean.setSUB_ACCT_NO("");//子账号
		bck0006ReqBean.setBody(bck0006ReqBodyBean);
		InReqHeadBean inReqHeadBean = new InReqHeadBean();
		inReqHeadBean.setTradeCode("BCK_0006");
		inReqHeadBean.setBranchNo("BranchNo");
		inReqHeadBean.setMachineNo("BranchNo");
		inReqHeadBean.setMachineType("1111");
		bck0006ReqBean.setHeadBean(inReqHeadBean);
		xstream.alias("Root", BCK0006ReqBean.class);
		return xstream.toXML(bck0006ReqBean);
	}
	/**
	 * 卡信息查询
	 */
	private static String get75010(){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		BCK0007ReqBean bck0007ReqBean = new BCK0007ReqBean();
		BCK0007ReqBodyBean bck0007ReqBodyBean = new BCK0007ReqBodyBean();
		
		bck0007ReqBodyBean.setCARD_NO("6231930000000902361");
		bck0007ReqBodyBean.setISPIN("0");
		bck0007ReqBodyBean.setPASSWD("");
		bck0007ReqBodyBean.setSUB_ACCT_NO("");
		bck0007ReqBean.setBody(bck0007ReqBodyBean);
		InReqHeadBean inReqHeadBean = new InReqHeadBean();
		inReqHeadBean.setTradeCode("BCK_0007");
		inReqHeadBean.setBranchNo("BranchNo");
		inReqHeadBean.setMachineNo("BranchNo");
		inReqHeadBean.setMachineType("1111");
		bck0007ReqBean.setHeadBean(inReqHeadBean);
		xstream.alias("Root", BCK0007ReqBean.class);
	//	xstream.alias("Head", InReqBean.class);
	//	xstream.alias("Body", BCK0007ReqBodyBean.class);
		return xstream.toXML(bck0007ReqBean);
	}
	/**
	 * 身份核查
	 * @return
	 */
	private static String get03448(){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK0008ReqBean bck0008ReqBean = new BCK0008ReqBean();
		BCK0008ReqBodyBean bck0008ReqBodyBean = new BCK0008ReqBodyBean();
		bck0008ReqBodyBean.setID("130682199109264075");
		bck0008ReqBodyBean.setNAME("张蒙");
		bck0008ReqBean.setBody(bck0008ReqBodyBean);
		InReqHeadBean headBean = new InReqHeadBean();
		headBean.setTradeCode("BCK_0008");
		headBean.setBranchNo("BranchNo");
		headBean.setMachineNo("BranchNo");
		headBean.setMachineType("1111");
		bck0008ReqBean.setHeadBean(headBean);
		xstream.alias("ROOT", BCK0008ReqBean.class);
		xstream.alias("HEAD", InReqHeadBean.class);
		xstream.alias("BODY", BCK0008ReqBodyBean.class);
		return xstream.toXML(bck0008ReqBean);
	}
	/**
	 * 柜员认证方式查询
	 */
	private static String get07659(){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK0009ReqBean bck0009ReqBean = new BCK0009ReqBean();
		BCK0009ReqBodyBean bck0009ReqBodyBean = new BCK0009ReqBodyBean();
		bck0009ReqBodyBean.setQRY_TELLER_NO("A0030");
		bck0009ReqBean.setBody(bck0009ReqBodyBean);
		InReqHeadBean inReqHeadBean = new InReqHeadBean();
		inReqHeadBean.setTradeCode("BCK_0009");
		inReqHeadBean.setBranchNo("BranchNo");
		inReqHeadBean.setMachineNo("BranchNo");
		inReqHeadBean.setMachineType("1111");
		bck0009ReqBean.setHeadBean(inReqHeadBean);
		xstream.alias("ROOT", BCK0009ReqBean.class);
		xstream.alias("HEAD", InReqHeadBean.class);
		xstream.alias("BODY", BCK0009ReqBodyBean.class);
		return xstream.toXML(bck0009ReqBean);
	}
	
	/**
	 * 柜员授权
	 */
	private static String get07660(){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		BCK0010ReqBean bck0010ReqBean = new BCK0010ReqBean();
		BCK0010ReqBodyBean bck0010ReqBodyBean = new BCK0010ReqBodyBean();
		bck0010ReqBodyBean.setSUP_TELLER_NO("A0043");//授权柜员号
		/*授权柜员密码加密处理*/
		String tranPwd = "a1111111";
		bck0010ReqBodyBean.setSUP_TELLER_PWD(tranPwd);
		bck0010ReqBodyBean.setFIN_PRI_LEN("");//指纹长度
		bck0010ReqBodyBean.setFIN_PRI_VAL("");//指纹值
		bck0010ReqBean.setBody(bck0010ReqBodyBean);
		InReqHeadBean inReqHeadBean = new InReqHeadBean();
		inReqHeadBean.setTradeCode("BCK_0010");

		bck0010ReqBean.setHeadBean(inReqHeadBean);

		inReqHeadBean.setBranchNo("BranchNo");
		inReqHeadBean.setMachineNo("BranchNo");
		inReqHeadBean.setMachineType("1111");
		
		xstream.alias("Root", BCK0010ReqBean.class);
//		xstream.alias("HEAD", InReqHeadBean.class);
//		xstream.alias("BODY", BCK0010ReqBodyBean.class);
		return xstream.toXML(bck0010ReqBean);
	}
	
	/**
	 * 代理人身份信息核对
	 */
	private static String get08021(){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		BCK0011ReqBean bck0011ReqBean = new BCK0011ReqBean();
		BCK0011ReqBodyBean bck0011ReqBodyBean = new BCK0011ReqBodyBean();
		bck0011ReqBodyBean.setID_NO("130682199109264075");
		bck0011ReqBodyBean.setNAME("张蒙");
		bck0011ReqBean.setBody(bck0011ReqBodyBean);
		InReqHeadBean inReqHeadBean = new InReqHeadBean();
		inReqHeadBean.setTradeCode("BCK_0011");
		inReqHeadBean.setBranchNo("BranchNo");
		inReqHeadBean.setMachineNo("BranchNo");
		inReqHeadBean.setMachineType("1111");
		bck0011ReqBean.setHeadBean(inReqHeadBean);
		xstream.alias("Root", BCK0011ReqBean.class);
		return xstream.toXML(bck0011ReqBean);
	}
	
	
}