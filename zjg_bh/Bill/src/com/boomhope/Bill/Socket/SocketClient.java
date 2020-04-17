package com.boomhope.Bill.Socket;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Public.GlobalParameter;
import com.boomhope.Bill.Util.DateUtil;
import com.boomhope.Bill.Util.Property;
import com.boomhope.Message.Trans.MapToXml;
import com.boomhope.tms.message.XStreamNameCoder;
import com.boomhope.tms.message.account.in.BCK02864ReqBean;
import com.boomhope.tms.message.account.in.BCK02864ReqBodyBean;
import com.boomhope.tms.message.in.InReqHeadBean;
import com.boomhope.tms.message.in.InResBean;
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
import com.boomhope.tms.message.in.bck.BCK0011ReqBean;
import com.boomhope.tms.message.in.bck.BCK0011ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK0012ReqBean;
import com.boomhope.tms.message.in.bck.BCK0012ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK0013ReqBean;
import com.boomhope.tms.message.in.bck.BCK0013ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK0014ReqBean;
import com.boomhope.tms.message.in.bck.BCK0014ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK0015ReqBean;
import com.boomhope.tms.message.in.bck.BCK0015ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK0015ResBean;
import com.boomhope.tms.message.in.bck.BCK0015ResBodyBean;
import com.boomhope.tms.message.in.bck.BCK0016ReqBean;
import com.boomhope.tms.message.in.bck.BCK0016ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK0017ReqBean;
import com.boomhope.tms.message.in.bck.BCK0017ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK0018ReqBean;
import com.boomhope.tms.message.in.bck.BCK0018ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK0019ReqBean;
import com.boomhope.tms.message.in.bck.BCK0019ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK0020ReqBean;
import com.boomhope.tms.message.in.bck.BCK0020ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK0021ReqAgentInfBean;
import com.boomhope.tms.message.in.bck.BCK0021ReqBean;
import com.boomhope.tms.message.in.bck.BCK0021ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK0022ReqAgentInfBean;
import com.boomhope.tms.message.in.bck.BCK0022ReqBean;
import com.boomhope.tms.message.in.bck.BCK0022ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK01597ReqBean;
import com.boomhope.tms.message.in.bck.BCK01597ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK03446ReqBean;
import com.boomhope.tms.message.in.bck.BCK03446ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK03519ReqBean;
import com.boomhope.tms.message.in.bck.BCK03519ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK03521ReqBean;
import com.boomhope.tms.message.in.bck.BCK03521ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK04422ReqBean;
import com.boomhope.tms.message.in.bck.BCK04422ReqBodyBean;
import com.boomhope.tms.message.in.bck.BCK07670ReqBean;
import com.boomhope.tms.message.in.bck.BCK07670ReqBodyBean;
import com.boomhope.tms.message.in.tms.Tms0001ReqBean;
import com.boomhope.tms.message.in.tms.Tms0001ReqBodyBean;
import com.boomhope.tms.message.in.tms.Tms0002PeriBean;
import com.boomhope.tms.message.in.tms.Tms0002ReqBean;
import com.boomhope.tms.message.in.tms.Tms0002ReqBodyBean;
import com.boomhope.tms.message.in.tms.Tms0004ReqBean;
import com.boomhope.tms.message.in.tms.Tms0004ReqBodyBean;
import com.boomhope.tms.message.in.tms.Tms0005ReqBean;
import com.boomhope.tms.message.in.tms.Tms0005ReqBodyBean;
import com.boomhope.tms.message.in.tms.Tms0007ReqBean;
import com.boomhope.tms.message.in.tms.Tms0007ReqBodyBean;
import com.boomhope.tms.message.moneybox.MoneyBoxOutHeadBean;
import com.boomhope.tms.message.moneybox.MoneyBoxQueryOrderReqBean;
import com.boomhope.tms.message.moneybox.MoneyBoxQueryOrderReqBodyBean;
import com.boomhope.tms.message.moneybox.MoneyBoxQueryOrderReqResponseBean;
import com.boomhope.tms.message.moneybox.MoneyBoxTransApplyReqBean;
import com.boomhope.tms.message.moneybox.MoneyBoxTransApplyReqBodyBean;
import com.boomhope.tms.message.moneybox.MoneyBoxTransApplyReqResponseBean;
import com.boomhope.tms.message.moneybox.MoneyBoxTransCommitReqBean;
import com.boomhope.tms.message.moneybox.MoneyBoxTransCommitReqBodyBean;
import com.boomhope.tms.message.moneybox.MoneyBoxTransCommitReqResponseBean;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XppDriver;

public class SocketClient {
	
	static Logger logger = Logger.getLogger(SocketClient.class);
	/**
	 * 创建
	 * @return
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public  Socket  createSocket() throws UnknownHostException, IOException{
		return createSocket(Property.BP_IP, Property.BP_PORT);
	}
	
	/**
	 * @author bikq
	 */
	public  Socket  createSocket(String ip,int port) throws UnknownHostException, IOException{
		logger.info("连接ip："+ip);
		Socket socket =  new Socket(ip,port);
//		Socket socket =  new Socket("198.1.245.252",9899);
//		socket.setKeepAlive(true);  
        socket.setSoTimeout(Property.TIME_OUT); 
		return socket;
	}
	
	/**
	 * 发送请求
	 * @param socket
	 * @param xmlRequest
	 * @throws IOException
	 */
	public  void sendRequest(Socket socket,String xmlRequest) throws IOException{
		logger.info("发送报文："+xmlRequest);
		OutputStream os = socket.getOutputStream();  
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));  
        //发送请求
        bw.write(xmlRequest + "\n");  
        bw.flush();
        socket.shutdownOutput();
	}
	/**
	 * 接收请求
	 * @param socket
	 * @return
	 * @throws IOException
	 */
	public  String  response(Socket socket) throws IOException{
        InputStream is = socket.getInputStream();  
        BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8")); 
        String retMsg = "";
		String value = null;
		while ((value = br.readLine()) != null) {
			retMsg += value + "\n";
			if ("</Root>".equals(value)){
				break;
			}
		}
		is.close();
		logger.info("接受报文："+retMsg);
		return retMsg;
	}

	/**
	 * 关闭socket
	 * @param socket
	 */
	public void closeSocket(Socket socket){
		try {
			if(socket != null){
				socket.close();
				logger.info("Socket连接关闭，IP="+socket.getInetAddress());
			}
		} catch (IOException e) {
			logger.error("Socket连接关闭异常，IP="+socket.getInetAddress());
		}
	}
	
	/**
	 * 账户综合查询
	 * @return
	 */
	public static String BCK_0002(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		
		InReqHeadBean inReqHeadBean = new InReqHeadBean();
		inReqHeadBean.setTradeCode("BCK_0002");
		inReqHeadBean.setMachineNo(GlobalParameter.machineNo);
		inReqHeadBean.setBranchNo(GlobalParameter.branchNo);
		inReqHeadBean.setMachineType(GlobalParameter.MACHINE_TYPE);
		
		
		BCK0002ReqBean bck0002ReqBean = new BCK0002ReqBean();
		bck0002ReqBean.setHeadBean(inReqHeadBean);
		
		BCK0002ReqBodyBean bck0002ReqBodyBean = new BCK0002ReqBodyBean();
		bck0002ReqBodyBean.setCERT_NO_ADD(map.get("BillNo"));
		
		bck0002ReqBodyBean.setAcctNo(map.get("accNo"));
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
	 * 身份核查
	 * @return
	 */
	public static String BCK_0008(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK0008ReqBean bCK0008ReqBean = new BCK0008ReqBean();
		InReqHeadBean inReqHeadBean = new InReqHeadBean();
		inReqHeadBean.setTradeCode("BCK_0008");	
		inReqHeadBean.setMachineNo(GlobalParameter.machineNo);
		inReqHeadBean.setBranchNo(GlobalParameter.branchNo);
		inReqHeadBean.setMachineType(GlobalParameter.MACHINE_TYPE);
		bCK0008ReqBean.setHeadBean(inReqHeadBean);		
		BCK0008ReqBodyBean bCK0008ReqBodyBean = new BCK0008ReqBodyBean();
		bCK0008ReqBodyBean.setID(map.get("id"));
		bCK0008ReqBodyBean.setNAME(map.get("name"));
		bCK0008ReqBean.setBody(bCK0008ReqBodyBean);
		xstream.alias("ROOT", BCK0008ReqBean.class);
		xstream.alias("HEAD", InReqHeadBean.class);
		xstream.alias("BODY", BCK0008ReqBodyBean.class);
		return xstream.toXML(bCK0008ReqBean);
	}
	/**
	 * 身份核查07670
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
		bCK07670ReqBodyBean.setID(map.get("id"));
		bCK07670ReqBodyBean.setNAME(map.get("name"));
		bCK07670ReqBean.setBody(bCK07670ReqBodyBean);
		xstream.alias("ROOT", BCK07670ReqBean.class);
		xstream.alias("HEAD", InReqHeadBean.class);
		xstream.alias("BODY", BCK07670ReqBodyBean.class);
		return xstream.toXML(bCK07670ReqBean);
	}
	
	/**
	 * 糖豆回收查询
	 * @return
	 */
	public static String BCK_0005(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		
		BCK0005ReqBean bck0005ReqBean = new BCK0005ReqBean();
		InReqHeadBean inReqHeadBean = new InReqHeadBean();
		inReqHeadBean.setTradeCode(map.get("TradeCode"));
		inReqHeadBean.setMachineNo(GlobalParameter.machineNo);
		inReqHeadBean.setBranchNo(GlobalParameter.branchNo);
		inReqHeadBean.setMachineType(GlobalParameter.MACHINE_TYPE);
//		inReqHeadBean.setMachineNo(map.get("machineNo"));
		bck0005ReqBean.setHeadBean(inReqHeadBean);
		BCK0005ReqBodyBean bck0005ReqBodyBean = new BCK0005ReqBodyBean();
		bck0005ReqBodyBean.setAcctNo(map.get("acctNo"));
		bck0005ReqBodyBean.setPayDate(map.get("payDate"));
		bck0005ReqBodyBean.setPayJrnl(map.get("payJrnl"));
		bck0005ReqBodyBean.setSubAcctNo(map.get("subAcctNo"));
		bck0005ReqBean.setBody(bck0005ReqBodyBean);
		xstream.alias("ROOT", BCK0005ReqBean.class);
		xstream.alias("HEAD", InReqHeadBean.class);
		xstream.alias("BODY", BCK0005ReqBodyBean.class);
		return xstream.toXML(bck0005ReqBean);
	}
	/**
	 * 糖豆回收
	 * @return
	 */
	public static String BCK_0006(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		
		BCK0006ReqBean bck0006ReqBean = new BCK0006ReqBean();
		InReqHeadBean inReqHeadBean = new InReqHeadBean();
		inReqHeadBean.setTradeCode(map.get("TradeCode"));
		inReqHeadBean.setMachineNo(GlobalParameter.machineNo);
		inReqHeadBean.setBranchNo(GlobalParameter.branchNo);
		inReqHeadBean.setMachineType(GlobalParameter.MACHINE_TYPE);
//		inReqHeadBean.setMachineNo(map.get("machineNo"));
		bck0006ReqBean.setHeadBean(inReqHeadBean);
		BCK0006ReqBodyBean bck0006ReqBodyBean = new BCK0006ReqBodyBean();
		bck0006ReqBodyBean.setACCT_NO(map.get("ACCT_NO"));
		bck0006ReqBodyBean.setBACK_COUNT(map.get("BACK_COUNT"));
		bck0006ReqBodyBean.setBACK_EXCHANGE_AMT(map.get("BACK_EXCHANGE_AMT"));
		bck0006ReqBodyBean.setBACK_PROP(map.get("BACK_PROP"));//收回比例
		bck0006ReqBodyBean.setOPPO_ACCT_NO(map.get("OPPO_ACCT_NO"));//对方账号
		bck0006ReqBodyBean.setORG_EXCHANGE_MODE(map.get("ORG_EXCHANGE_MODE"));//原唐豆兑换方式(0-现金，1-转账，2-兑物)
		bck0006ReqBodyBean.setORG_EXCHANGE_PROP(map.get("ORG_EXCHANGE_PROP"));//原唐豆兑现比例
		bck0006ReqBodyBean.setPASSWORD(map.get("PASSWORD"));//对方账号密码
		bck0006ReqBodyBean.setPAY_AMT(map.get("PAY_AMT"));//支取金额
		bck0006ReqBodyBean.setPAY_DATE(map.get("PAY_DATE"));//支取日期
		bck0006ReqBodyBean.setPAY_JRNL(map.get("PAY_JRNL"));//支取流水
		bck0006ReqBodyBean.setRECOV_TYPE(map.get("RECOV_TYPE"));//唐豆收回方式(0-现金 1-转账 2-兑物)
		bck0006ReqBodyBean.setSUB_ACCT_NO(map.get("SUB_ACCT_NO"));//子账号
		bck0006ReqBean.setBody(bck0006ReqBodyBean);
		xstream.alias("ROOT", BCK0006ReqBean.class);
		xstream.alias("HEAD", InReqHeadBean.class);
		xstream.alias("BODY", BCK0006ReqBodyBean.class);
		return xstream.toXML(bck0006ReqBean);
	}
	
	/**
	 * 测试
	 * @param args
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public static void main(String[] args) throws UnknownHostException, IOException {
		Map<String,String> map = new HashMap<String,String>();

		map.put("id", "198.1.243.161");
		
		SocketClient sc = new SocketClient();
		Socket socket = null;
		//for(int i=0;i<1000;i++){
		try {
			socket = sc.createSocket("198.1.245.93",8998);
			// 发送请求
			sc.sendRequest(socket,MapToXml.Tms0001(map));
			// 响应
			String retMsg = sc.response(socket);
			
			XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			reqXs.alias("Root", BCK0015ResBean.class);
			reqXs.alias("Head", InResBean.class);
			reqXs.alias("Body", BCK0015ResBodyBean.class);
			BCK0015ResBean tms0015ResBean = (BCK0015ResBean)reqXs.fromXML(retMsg);
			logger.debug(tms0015ResBean);
			logger.debug("CLIENT retMsg:" + retMsg);
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			sc.closeSocket(socket);
		}
		//}
	}
	
	/**
	 * 证件信息查询
	 * @param map
	 * @return
	 */
	public static String BCK_0001(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		/**
		 * 请求报文头
		 */
		InReqHeadBean headBean = new InReqHeadBean();
		headBean.setTradeCode("BCK_0001");
		headBean.setMachineNo(GlobalParameter.machineNo);
		headBean.setBranchNo(GlobalParameter.branchNo);
		headBean.setMachineType(GlobalParameter.MACHINE_TYPE);
		
		/**
		 * 请求报文体
		 */
		BCK0001ReqBodyBean bck0001ReqBodyBean = new BCK0001ReqBodyBean();
		bck0001ReqBodyBean.setIdType("10100");;
		bck0001ReqBodyBean.setIdNo(map.get("idCardNo"));;
		bck0001ReqBodyBean.setCustomName(map.get("custName"));
		
		BCK0001ReqBean bck0001ReqBean = new BCK0001ReqBean();
		bck0001ReqBean.setBody(bck0001ReqBodyBean);
		bck0001ReqBean.setHeadBean(headBean);
		
		xstream.alias("ROOT", BCK0001ReqBean.class);
		xstream.alias("HEAD", InReqHeadBean.class);
		xstream.alias("BODY", BCK0001ReqBodyBean.class);
		return xstream.toXML(bck0001ReqBean);
	
	}
	
	/**
	 * 卡信息查询
	 * @return
	 */
	public static String BCK_0007(Map<String,String> map){
		
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		BCK0007ReqBean bck0007ReqBean = new BCK0007ReqBean();
		BCK0007ReqBodyBean bck0007ReqBodyBean = new BCK0007ReqBodyBean();
		
		bck0007ReqBodyBean.setCARD_NO(map.get("cardNo"));
		bck0007ReqBodyBean.setISPIN(map.get("ISPIN"));
		if(map.get("ISPIN").equals("1")){
			bck0007ReqBodyBean.setPASSWD(map.get("pwd"));
		}else{
			bck0007ReqBodyBean.setPASSWD("");
		}
		bck0007ReqBodyBean.setSUB_ACCT_NO("");
		bck0007ReqBean.setBody(bck0007ReqBodyBean);
		InReqHeadBean inReqHeadBean = new InReqHeadBean();
		inReqHeadBean.setTradeCode("BCK_0007");
		inReqHeadBean.setMachineNo(GlobalParameter.machineNo);
		inReqHeadBean.setBranchNo(GlobalParameter.branchNo);
		inReqHeadBean.setMachineType(GlobalParameter.MACHINE_TYPE);
		bck0007ReqBean.setHeadBean(inReqHeadBean);
		xstream.alias("Root", BCK0007ReqBean.class);
	//	xstream.alias("Head", InReqBean.class);
	//	xstream.alias("Body", BCK0007ReqBodyBean.class);
		return xstream.toXML(bck0007ReqBean);
	}
	
	/**
	 * 预计利息
	 * @return
	 */
	public static String BCK_0003(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		/**
		 * 请求报文头
		 */
		InReqHeadBean headBean = new InReqHeadBean();
		headBean.setTradeCode("BCK_0003");
		headBean.setMachineNo(GlobalParameter.machineNo);
		headBean.setBranchNo(GlobalParameter.branchNo);
		headBean.setMachineType(GlobalParameter.MACHINE_TYPE);
		/**
		 * 请求报文体
		 */
		BCK0003ReqBodyBean bck0003ReqBodyBean = new BCK0003ReqBodyBean();
		bck0003ReqBodyBean.setAcctNO(map.get("accNo"));
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
	 * 预计授权认证查询
	 * @return
	 */
	public static String BCK_0009(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK0009ReqBean bck0009ReqBean = new BCK0009ReqBean();
		BCK0009ReqBodyBean bck0009ReqBodyBean = new BCK0009ReqBodyBean();
		bck0009ReqBodyBean.setQRY_TELLER_NO(map.get("tllerNo"));
		bck0009ReqBean.setBody(bck0009ReqBodyBean);
		InReqHeadBean inReqHeadBean = new InReqHeadBean();
		inReqHeadBean.setTradeCode("BCK_0009");
		inReqHeadBean.setMachineNo(GlobalParameter.machineNo);
		inReqHeadBean.setBranchNo(GlobalParameter.branchNo);
		inReqHeadBean.setMachineType(GlobalParameter.MACHINE_TYPE);
		bck0009ReqBean.setHeadBean(inReqHeadBean);
		xstream.alias("ROOT", BCK0009ReqBean.class);
		xstream.alias("HEAD", InReqHeadBean.class);
		xstream.alias("BODY", BCK0009ReqBodyBean.class);
		return xstream.toXML(bck0009ReqBean);
	}
	
	/**
	 * 代理人身份信息核对
	 * @param map
	 * @return
	 */
	public static String BCK_0011(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		BCK0011ReqBean bck0011ReqBean = new BCK0011ReqBean();
		BCK0011ReqBodyBean bck0011ReqBodyBean = new BCK0011ReqBodyBean();
		bck0011ReqBodyBean.setID_NO(map.get("agentIdNo"));
		bck0011ReqBodyBean.setNAME(map.get("agentName"));
		bck0011ReqBean.setBody(bck0011ReqBodyBean);
		InReqHeadBean inReqHeadBean = new InReqHeadBean();
		inReqHeadBean.setTradeCode("BCK_0011");
		inReqHeadBean.setMachineNo(GlobalParameter.machineNo);
		inReqHeadBean.setBranchNo(GlobalParameter.branchNo);
		inReqHeadBean.setMachineType(GlobalParameter.MACHINE_TYPE);
		bck0011ReqBean.setHeadBean(inReqHeadBean);
		xstream.alias("Root", BCK0011ReqBean.class);
		return xstream.toXML(bck0011ReqBean);
	}
	
	/**
	 * 销户
	 * @return
	 */
	public static String BCK_0004(Map<String,String> map){
		/* 解析请求报文 */
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		//拼接输出的body
		BCK0004ReqBodyBean reqBodyBean = new BCK0004ReqBodyBean();
		reqBodyBean.setACCT_NO(map.get("accNo"));//账号
		reqBodyBean.setCERT_NO(map.get("billNo"));//凭证号
		reqBodyBean.setCUST_NO(map.get("custNo"));//客户号
		reqBodyBean.setCURRENCY_TYPE(map.get("currencyType"));//币种
		reqBodyBean.setCUST_NAME("");//客户名称
		if("1".equals(map.get("drawCoun").toString())){
			reqBodyBean.setPASSWORD(map.get("billPass"));//存单密码
		}else {
			reqBodyBean.setPASSWORD("");//存单密码
		}
		reqBodyBean.setDRAW_COND(map.get("drawCoun"));//支付条件
		reqBodyBean.setCURRENCY("CNY");//货币号
		reqBodyBean.setPROD_CODE("");//产品代码
		reqBodyBean.setPROD_NAME("");//产品名称
		reqBodyBean.setBALANCE(map.get("amount"));//余额
		reqBodyBean.setDEP_TERM("");//存期
		reqBodyBean.setSTART_INT_DATE("");//起息日
		reqBodyBean.setOPEN_RATE("");//开户利率
		reqBodyBean.setCYC_AMT("");//周期金额
		reqBodyBean.setCYC("");//周期
		reqBodyBean.setTIMES("");//次数
		reqBodyBean.setBES_AMT("");//预约金额
		reqBodyBean.setBES_DATE("");//预约日期
		reqBodyBean.setDRAW_CURRENCY("00");//支取币种
		reqBodyBean.setPRI_INTE_FLAG("0");//本息分开（必输，0否、1是）
		reqBodyBean.setCANCEL_AMT(map.get("amount"));//销户金额
		reqBodyBean.setPRI_INTE_WAY("1");//本息走向
		reqBodyBean.setOPPO_ACCT_NO(map.get("cardNo"));//对方账号（本息转账，有）
		reqBodyBean.setSUB_ACCT_NO("0");//子账号（必输项，对方账号为一本通使显示，默认为0基本账户可修改）
		reqBodyBean.setPRI_WAY("");//本金走向
		reqBodyBean.setOPPO_ACCT_NO1("");//对方账号（本金转账，本金、利息分开时，对方账户不允许为同一账户）
		reqBodyBean.setSUB_ACCT_NO1("0");//子账号（必输项，对方账号为一本通使显示，默认为0基本账户可修改）
		reqBodyBean.setINTE_WAY("");//利息走向
		reqBodyBean.setOPPO_ACCT_NO2("");//对方账号（利息转账，有）
		reqBodyBean.setSUB_ACCT_NO2("0");//子账号（必输项，对方账号为一本通使显示，默认为0基本账户可修改）
		reqBodyBean.setID_TYPE("");//证件类型
		reqBodyBean.setID_NO(map.get("idCardNo"));//证件号码
		reqBodyBean.setPROVE_FLAG("");//证明标志
		reqBodyBean.setCASH_ANALY_NO("");//现金分析号
		reqBodyBean.setHAV_AGENT_FLAG(map.get("agentFlag"));//是否有代理人标志
		//拼接输出的AGENT_INF
		BCK0004ReqAgentInfBean reqAgentInfBean = new BCK0004ReqAgentInfBean();
		reqAgentInfBean.setCUST_NAME(map.get("agentIdCardName"));//客户姓名
		reqAgentInfBean.setDUE_DATE("");//到期日期
		reqAgentInfBean.setID_NO(map.get("agentIdCardNo"));//证件号码
		reqAgentInfBean.setID_TYPE("1");//证件类别
		reqAgentInfBean.setISSUE_DATE("");//签发日期
		reqAgentInfBean.setISSUE_INST(map.get("agentqfjg"));//签发机关
		reqAgentInfBean.setJ_C_ADD("");//经常居住地
		reqAgentInfBean.setMOB_PHONE("");//移动电话
		reqAgentInfBean.setNATION("");//国籍
		reqAgentInfBean.setOCCUPATION("公务员");//职业
		reqAgentInfBean.setREGIS_PER_RES(map.get("agentaddress"));//户口所在地
		reqAgentInfBean.setSEX(map.get("agentsex"));//性别
		reqAgentInfBean.setTELEPHONE("");//固定电话
		
		reqBodyBean.setAGENT_INF(reqAgentInfBean);
		
		// 业务流水-P端使用字段
		reqBodyBean.setPROD_NAME(map.get("proName"));
		reqBodyBean.setPROD_CODE(map.get("proCode"));
		reqBodyBean.setOPPO_NAME(map.get("cardName"));
		reqBodyBean.setCANEL_TYPE("1");
		if("1".equals(map.get("checkStatus"))){
			reqBodyBean.setCHECKSTATUS("1");
		}else{
			reqBodyBean.setCHECKSTATUS("2");
		}
		
		
		//拼接输出的head
		InReqHeadBean inReqHeadBean = new InReqHeadBean();;
		inReqHeadBean.setTradeCode("BCK_0004");
		inReqHeadBean.setMachineNo(GlobalParameter.machineNo);
		inReqHeadBean.setBranchNo(GlobalParameter.branchNo);
		inReqHeadBean.setMachineType(GlobalParameter.MACHINE_TYPE);
		
		//拼接root类
		BCK0004ReqBean bck0004ReqBean = new BCK0004ReqBean();
		bck0004ReqBean.setBody(reqBodyBean);
		bck0004ReqBean.setHeadBean(inReqHeadBean);
		
		
		
		
		
		reqXs.alias("Root", BCK0004ReqBean.class);
		return reqXs.toXML(bck0004ReqBean);
	}
	
	/**
	 * 设备登录报文
	 */
	public static String Tms0001(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));

		xstream.autodetectAnnotations(true);
		Tms0001ReqBean tms0001ReqBean = new Tms0001ReqBean();
		Tms0001ReqBodyBean tms0001ReqBodyBean = new Tms0001ReqBodyBean();
		tms0001ReqBodyBean.setMachineIp(map.get("ip"));
		tms0001ReqBean.setBodyBean(tms0001ReqBodyBean);
		InReqHeadBean inReqHeadBean = new InReqHeadBean();
		inReqHeadBean.setTradeCode("TMS_0001");
		inReqHeadBean.setMachineNo(GlobalParameter.machineNo);
		inReqHeadBean.setBranchNo(GlobalParameter.branchNo);
		inReqHeadBean.setMachineType(GlobalParameter.MACHINE_TYPE);
		tms0001ReqBean.setHeadBean(inReqHeadBean);
		xstream.alias("Root", BCK0011ReqBean.class);
		return xstream.toXML(tms0001ReqBean);
	}
	/**
	 * 设置状态上送报文
	 */
	public static String Tms0002(Map<String,String> map){
		
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));

		xstream.autodetectAnnotations(true);
		
		/* TMS_0001报文测试 */
		List<Tms0002PeriBean> periList = new ArrayList<Tms0002PeriBean>();
		
		if(map.get("PRI0001") != null){
			/* 外设信息 */
			Tms0002PeriBean periBean = new Tms0002PeriBean();
			periBean.setPeriId("PRI0001");
			periBean.setPeriStatus(map.get("PRI0001"));
			periBean.setPeriStatusDesc("密码键盘故障");
			periList.add(periBean);
		}
		
		if(map.get("PRI0002") != null){
			Tms0002PeriBean periBean1 = new Tms0002PeriBean();
			periBean1.setPeriId("PRI0002");
			periBean1.setPeriStatus(map.get("PRI0002"));
			periBean1.setPeriStatusDesc("指纹故障");
			periList.add(periBean1);
		}
		
		if(map.get("PRI0003") != null){
			Tms0002PeriBean periBean2 = new Tms0002PeriBean();
			periBean2.setPeriId("PRI0003");
			periBean2.setPeriStatus(map.get("PRI0003"));
			periBean2.setPeriStatusDesc("身份证读卡器故障");
			periList.add(periBean2);
		}
		
		if(map.get("PRI0004") != null){
			Tms0002PeriBean periBean3 = new Tms0002PeriBean();
			periBean3.setPeriId("PRI0004");
			periBean3.setPeriStatus(map.get("PRI0004"));
			periBean3.setPeriStatusDesc("银行卡读卡器故障");
			periList.add(periBean3);
		}
		if(map.get("PRI0005") != null){
			Tms0002PeriBean periBean4 = new Tms0002PeriBean();
			periBean4.setPeriId("PRI0005");
			periBean4.setPeriStatus(map.get("PRI0005"));
			periBean4.setPeriStatusDesc("裁纸器故障");
			periList.add(periBean4);
		}
		if(map.get("PRI0006") != null){
			Tms0002PeriBean periBean6 = new Tms0002PeriBean();
			periBean6.setPeriId("PRI0006");
			periBean6.setPeriStatus(map.get("PRI0006"));
			periBean6.setPeriStatusDesc("存单打印机故障");
			periList.add(periBean6);
		}
		if(map.get("PRI0007") != null){
			Tms0002PeriBean periBean7 = new Tms0002PeriBean();
			periBean7.setPeriId("PRI0007");
			periBean7.setPeriStatus(map.get("PRI0007"));
			periBean7.setPeriStatusDesc("存单打印机故障");
			periList.add(periBean7);
		}
		/* 请求报文体 */
		Tms0002ReqBodyBean bodyBean = new Tms0002ReqBodyBean();
		bodyBean.setMachineStatus(map.get("status"));
		bodyBean.setPeriList(periList);
		
		/* 请求报文头 */
		InReqHeadBean headBean = new InReqHeadBean();
		headBean.setTradeCode("TMS_0002");
		headBean.setMachineNo(map.get("macNo"));
		
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
	 * 管理员密码校验
	 */
	public static String Tms0004(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));

		xstream.autodetectAnnotations(true);
		Tms0004ReqBean tms0004ReqBean = new Tms0004ReqBean();
		Tms0004ReqBodyBean tms0004ReqBodyBean = new Tms0004ReqBodyBean();
		tms0004ReqBodyBean.setIsXiu(map.get("isXiu"));
		tms0004ReqBodyBean.setUsername(map.get("username"));
		tms0004ReqBodyBean.setPassword(map.get("password"));
//		tms0004ReqBodyBean.setUsername("test");
//		tms0004ReqBodyBean.setPassword("123456");
		tms0004ReqBean.setBodyBean(tms0004ReqBodyBean);
		InReqHeadBean inReqHeadBean = new InReqHeadBean();
		inReqHeadBean.setTradeCode("TMS_0004");
		inReqHeadBean.setMachineNo(GlobalParameter.machineNo);
		inReqHeadBean.setBranchNo(GlobalParameter.branchNo);
		inReqHeadBean.setMachineType(GlobalParameter.MACHINE_TYPE);
		tms0004ReqBean.setHeadBean(inReqHeadBean);
		xstream.alias("Root", Tms0004ReqBean.class);
		return xstream.toXML(tms0004ReqBean);
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
	 * 设备登录报文
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
		InReqHeadBean inReqHeadBean = new InReqHeadBean();
		inReqHeadBean.setTradeCode("TMS_0007");
		inReqHeadBean.setMachineNo(GlobalParameter.machineNo);
		inReqHeadBean.setBranchNo(GlobalParameter.branchNo);
		inReqHeadBean.setMachineType(GlobalParameter.MACHINE_TYPE);
		tms0007ReqBean.setHeadBean(inReqHeadBean);
		xstream.alias("Root", Tms0007ReqBean.class);
		return xstream.toXML(tms0007ReqBean);
	}
	
	/**
	 * 柜员授权
	 * @return
	 */
	public static String BCK_0010(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		BCK0010ReqBean bck0010ReqBean = new BCK0010ReqBean();
		BCK0010ReqBodyBean bck0010ReqBodyBean = new BCK0010ReqBodyBean();
		bck0010ReqBodyBean.setSUP_TELLER_NO(map.get("tllerNo"));//授权柜员号
		/*授权柜员密码加密处理*/
		String pwd = map.get("suPass");//密码
		if(pwd != null){
			bck0010ReqBodyBean.setSUP_TELLER_PWD(pwd);//授权密码
		}
		String zw = map.get("zw");//指纹值 
		if(zw != null){
			bck0010ReqBodyBean.setFIN_PRI_VAL(zw);//指纹值
		}
		bck0010ReqBodyBean.setFIN_PRI_LEN("");//指纹长度
		
		bck0010ReqBean.setBody(bck0010ReqBodyBean);
		InReqHeadBean inReqHeadBean = new InReqHeadBean();
		inReqHeadBean.setTradeCode("BCK_0010");
		inReqHeadBean.setMachineNo(GlobalParameter.machineNo);
		inReqHeadBean.setBranchNo(GlobalParameter.branchNo);
		inReqHeadBean.setMachineType(GlobalParameter.MACHINE_TYPE);

		bck0010ReqBean.setHeadBean(inReqHeadBean);

		xstream.alias("Root", BCK0010ReqBean.class);
		return xstream.toXML(bck0010ReqBean);
	}
	/**
	 * 客户手机绑定信息查询
	 */
	public static String BCK_0012(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		BCK0012ReqBean bck0012ReqBean = new BCK0012ReqBean();
		InReqHeadBean inReqHeadBean = new InReqHeadBean();
		inReqHeadBean.setTradeCode("BCK_0012");
		inReqHeadBean.setMachineNo(GlobalParameter.machineNo);
		inReqHeadBean.setBranchNo(GlobalParameter.branchNo);
		inReqHeadBean.setMachineType(GlobalParameter.MACHINE_TYPE);

		bck0012ReqBean.setHeadBean(inReqHeadBean);
		BCK0012ReqBodyBean bck0012ReqBodyBean = new BCK0012ReqBodyBean();
		bck0012ReqBodyBean.setACCT_NO(map.get("ACCT_NO"));
		bck0012ReqBean.setBody(bck0012ReqBodyBean);
		xstream.alias("Root", BCK0012ReqBean.class);
		return xstream.toXML(bck0012ReqBean);
	}
	/**
	 * 单条短信发送
	 */
	public static String BCK_0013(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		BCK0013ReqBean bck0013ReqBean = new BCK0013ReqBean();
		InReqHeadBean inReqHeadBean = new InReqHeadBean();
		inReqHeadBean.setTradeCode("BCK_0013");
		inReqHeadBean.setMachineNo(GlobalParameter.machineNo);
		inReqHeadBean.setBranchNo(GlobalParameter.branchNo);
		inReqHeadBean.setMachineType(GlobalParameter.MACHINE_TYPE);

		bck0013ReqBean.setHeadBean(inReqHeadBean);
		BCK0013ReqBodyBean bck0013ReqBodyBean = new BCK0013ReqBodyBean();
		String VCode = map.get("VCode");//验证码
		bck0013ReqBodyBean.setCONTENT("验证码："+VCode+"。如有疑义，请拨打客服电话96368。");
		bck0013ReqBodyBean.setMATCH_CODE("");
		bck0013ReqBodyBean.setSEND_TIME("");
		bck0013ReqBodyBean.setTEL_NO(map.get("TEL_NO"));
		bck0013ReqBean.setBody(bck0013ReqBodyBean);
		xstream.alias("Root", BCK0013ReqBean.class);
		return xstream.toXML(bck0013ReqBean);
	}
	/**
	 * 凭证信息综合查询
	 */
	public static String BCK_0014(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		BCK0014ReqBean bck0014ReqBean = new BCK0014ReqBean();
		InReqHeadBean inReqHeadBean = new InReqHeadBean();
		inReqHeadBean.setTradeCode("BCK_0014");
		inReqHeadBean.setMachineNo(GlobalParameter.machineNo);
		inReqHeadBean.setBranchNo(GlobalParameter.branchNo);
		inReqHeadBean.setMachineType(GlobalParameter.MACHINE_TYPE);

		bck0014ReqBean.setHeadBean(inReqHeadBean);
		BCK0014ReqBodyBean bck0014ReqBodyBean = new BCK0014ReqBodyBean();
		bck0014ReqBodyBean.setCertNo(map.get("BillNo"));
		bck0014ReqBodyBean.setCertType("102");
		bck0014ReqBean.setBody(bck0014ReqBodyBean);
		xstream.alias("Root", BCK0014ReqBean.class);
		return xstream.toXML(bck0014ReqBean);
	}
	/**
	 * 待打印查询
	 */
	public static String BCK_0015(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		BCK0015ReqBean bck0015ReqBean = new BCK0015ReqBean();
		InReqHeadBean inReqHeadBean = new InReqHeadBean();
		inReqHeadBean.setTradeCode("BCK_0015");
		inReqHeadBean.setMachineNo(GlobalParameter.machineNo);
		inReqHeadBean.setBranchNo(GlobalParameter.branchNo);
		inReqHeadBean.setMachineType(GlobalParameter.MACHINE_TYPE);

		bck0015ReqBean.setHeadBean(inReqHeadBean);
		BCK0015ReqBodyBean bck0015ReqBodyBean = new BCK0015ReqBodyBean();
		
		if(map.get("ID_NO")!=null && map.get("ID_NO")!=""){
			
			bck0015ReqBodyBean.setID_NO(map.get("ID_NO"));//证件号
			bck0015ReqBodyBean.setID_TYPE(map.get("ID_TYPE"));//证件类型
			
		}else if(map.get("ACCT_NO")!=null && map.get("ACCT_NO")!=""){
			
			bck0015ReqBodyBean.setACCT_NO(map.get("ACCT_NO"));//银行卡号
		}
		
		bck0015ReqBodyBean.setOPER_CHOOSE(map.get("OPER_CHOOSE"));//操作选择
		bck0015ReqBean.setBody(bck0015ReqBodyBean);
		xstream.alias("Root", BCK0015ReqBean.class);
		return xstream.toXML(bck0015ReqBean);
	}
	/**
	 * 存单打印 
	 */
	public static String BCK_0016(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		BCK0016ReqBean bck0016ReqBean = new BCK0016ReqBean();
		InReqHeadBean inReqHeadBean = new InReqHeadBean();
		inReqHeadBean.setTradeCode("BCK_0016");
		inReqHeadBean.setMachineNo(GlobalParameter.machineNo);
		inReqHeadBean.setBranchNo(GlobalParameter.branchNo);
		inReqHeadBean.setMachineType(GlobalParameter.MACHINE_TYPE);

		bck0016ReqBean.setHeadBean(inReqHeadBean);
		BCK0016ReqBodyBean bck0016ReqBodyBean = new BCK0016ReqBodyBean();
		bck0016ReqBodyBean.setACCT_NO(map.get("acctNo")); //卡号
		bck0016ReqBodyBean.setSUB_ACCT_NO(map.get("subAcctNo"));//子账号
		bck0016ReqBodyBean.setCERT_NO_ADD(map.get("certNoAdd"));//凭证号
		bck0016ReqBean.setBody(bck0016ReqBodyBean);
		xstream.alias("Root", BCK0016ReqBean.class);
		return xstream.toXML(bck0016ReqBean);
	}
	/**
	 * 存单打印状态变更
	 */
	public static String BCK_0017(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		BCK0017ReqBean bck0017ReqBean = new BCK0017ReqBean();
		InReqHeadBean inReqHeadBean = new InReqHeadBean();
		inReqHeadBean.setTradeCode("BCK_0017");
		inReqHeadBean.setMachineNo(GlobalParameter.machineNo);
		inReqHeadBean.setBranchNo(GlobalParameter.branchNo);
		inReqHeadBean.setMachineType(GlobalParameter.MACHINE_TYPE);

		bck0017ReqBean.setHeadBean(inReqHeadBean);
		BCK0017ReqBodyBean bck0017ReqBodyBean = new BCK0017ReqBodyBean();
		bck0017ReqBodyBean.setACCT_NO(map.get("acctNo")); //卡号
		bck0017ReqBodyBean.setSUB_ACCT_NO(map.get("subAcctNo"));//子账号
		bck0017ReqBodyBean.setOPER_CHOOSE(map.get("operChoose")); //操作选择
		bck0017ReqBean.setBody(bck0017ReqBodyBean);
		xstream.alias("Root", BCK0017ReqBean.class);
		return xstream.toXML(bck0017ReqBean);
	}
	/**
	 * 子账户列表查询
	 */
	public static String BCK_0018(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		BCK0018ReqBean bck0018ReqBean = new BCK0018ReqBean();
		InReqHeadBean inReqHeadBean = new InReqHeadBean();
		inReqHeadBean.setTradeCode("BCK_0018");
		inReqHeadBean.setMachineNo(GlobalParameter.machineNo);
		inReqHeadBean.setBranchNo(GlobalParameter.branchNo);
		inReqHeadBean.setMachineType(GlobalParameter.MACHINE_TYPE);

		bck0018ReqBean.setHeadBean(inReqHeadBean);
		BCK0018ReqBodyBean bck0018ReqBodyBean = new BCK0018ReqBodyBean();
		bck0018ReqBodyBean.setCARD_NO(map.get("cardNo")); //卡号
		bck0018ReqBean.setBody(bck0018ReqBodyBean);
		xstream.alias("Root", BCK0018ReqBean.class);
		return xstream.toXML(bck0018ReqBean);
	}
	/**
	 * 账户信息查询及密码验证
	 */
	public static String BCK_0019(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		BCK03521ReqBean bck0019ReqBean = new BCK03521ReqBean();
		InReqHeadBean inReqHeadBean = new InReqHeadBean();
		inReqHeadBean.setTradeCode("BCK_03521");
		inReqHeadBean.setMachineNo(GlobalParameter.machineNo);
		inReqHeadBean.setBranchNo(GlobalParameter.branchNo);
		inReqHeadBean.setMachineType(GlobalParameter.MACHINE_TYPE);

		bck0019ReqBean.setHeadBean(inReqHeadBean);
		BCK03521ReqBodyBean bck0019ReqBodyBean = new BCK03521ReqBodyBean();
		bck0019ReqBodyBean.setACCT_NO(map.get("acctNo")); //卡号
		bck0019ReqBodyBean.setPIN_VAL_FLAG((map.get("pinValFlag")));//是否验密
		if("1".equals(map.get("pinValFlag"))){
			bck0019ReqBodyBean.setPASSWORD((map.get("password")));//操作选择
		}else{
			bck0019ReqBodyBean.setPASSWORD("");
		}
		bck0019ReqBean.setBody(bck0019ReqBodyBean);
		xstream.alias("Root", BCK03521ReqBean.class);
		return xstream.toXML(bck0019ReqBean);
	}
	/**
	 * 产品利率信息查询
	 */
	public static String BCK_0020(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		BCK02864ReqBean bck0020ReqBean = new BCK02864ReqBean();
		InReqHeadBean inReqHeadBean = new InReqHeadBean();
		inReqHeadBean.setTradeCode("BCK_02864");
		inReqHeadBean.setMachineNo(GlobalParameter.machineNo);
		inReqHeadBean.setBranchNo(GlobalParameter.branchNo);
		inReqHeadBean.setMachineType(GlobalParameter.MACHINE_TYPE);

		bck0020ReqBean.setHeadBean(inReqHeadBean);
		BCK02864ReqBodyBean bck0020ReqBodyBean = new BCK02864ReqBodyBean();
		bck0020ReqBodyBean.setPROD_CODE(map.get("PROD_CODE"));
		bck0020ReqBodyBean.setRATE_DATE(map.get("RATE_DATE"));
		bck0020ReqBodyBean.setACCT_NO(map.get("ACCT_NO"));
		bck0020ReqBodyBean.setCHL_ID(map.get("CHL_ID"));
		bck0020ReqBodyBean.setCUST_NO(map.get("CUST_NO"));
		bck0020ReqBodyBean.setFLOAT_FLAG(map.get("FLOAT_FLAG"));
		bck0020ReqBodyBean.setSUB_ACCT_NO(map.get("SUB_ACCT_NO"));
		bck0020ReqBean.setBody(bck0020ReqBodyBean);
		xstream.alias("Root", BCK02864ReqBean.class);
		return xstream.toXML(bck0020ReqBean);
	}
	/**
	 * 卡系统 子账户销户
	 */
	public static String BCK_0021(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		BCK0021ReqBean bck0021ReqBean = new BCK0021ReqBean();
		InReqHeadBean inReqHeadBean = new InReqHeadBean();
		inReqHeadBean.setTradeCode("BCK_0021");
		inReqHeadBean.setMachineNo(GlobalParameter.machineNo);
		inReqHeadBean.setBranchNo(GlobalParameter.branchNo);
		inReqHeadBean.setMachineType(GlobalParameter.MACHINE_TYPE);

		bck0021ReqBean.setHeadBean(inReqHeadBean);
		BCK0021ReqBodyBean bck0021ReqBodyBean = new BCK0021ReqBodyBean();
		bck0021ReqBodyBean.setTRAN_CHANNEL("0035"); //渠道号 00-网银 50-手机银行
		bck0021ReqBodyBean.setCARD_NO(map.get("accNo")); //卡号
		bck0021ReqBodyBean.setSUB_ACCT_NO2(map.get("sub_accNo")); //子账户
		bck0021ReqBodyBean.setCAL_MODE(map.get("calMode")); //结算方式  0-现金 1-转账
		bck0021ReqBodyBean.setCASH_ANALY_NO(""); //现金分析号
		bck0021ReqBodyBean.setOPPO_ACCT_NO(""); //对方账号
		bck0021ReqBodyBean.setID_TYPE(map.get("idType")); //证件类型
		bck0021ReqBodyBean.setID_NO(map.get("idCard")); //证件号码
		bck0021ReqBodyBean.setCERT_NO(map.get("certNo")); //凭证号
		
		bck0021ReqBodyBean.setPIN_VAL_FLAG(map.get("pinValFlag")); //验密标志
		if(!"N".equals(map.get("pinValFlag").toString())){
			bck0021ReqBodyBean.setPASSWORD(map.get("password"));//存单密码
		}else {
			bck0021ReqBodyBean.setPASSWORD("");//存单密码
		}
		bck0021ReqBodyBean.setHAV_AGENT_FLAG(map.get("agentFlag")); //代理人标志 0-是 1-否
		
			//拼接输出的AGENT_INF
			BCK0021ReqAgentInfBean reqAgentInfBean = new BCK0021ReqAgentInfBean();
			reqAgentInfBean.setCUST_NAME(map.get("agentIdCardName"));//客户姓名
			reqAgentInfBean.setDUE_DATE("");//到期日期
			reqAgentInfBean.setID_NO(map.get("agentIdCardNo"));//证件号码
			reqAgentInfBean.setID_TYPE("1");//证件类别
			reqAgentInfBean.setISSUE_DATE("");//签发日期
			reqAgentInfBean.setISSUE_INST(map.get("agentqfjg"));//签发机关
			reqAgentInfBean.setJ_C_ADD("");//经常居住地
			reqAgentInfBean.setMOB_PHONE("");//移动电话
			reqAgentInfBean.setNATION("");//国籍
			reqAgentInfBean.setOCCUPATION("公务员");//职业
			reqAgentInfBean.setREGIS_PER_RES(map.get("agentaddress"));//户口所在地
			reqAgentInfBean.setSEX(map.get("agentsex"));//性别
			reqAgentInfBean.setTELEPHONE("");//固定电话
			
			// 业务流水-P端使用字段
			bck0021ReqBodyBean.setACCT_NO(map.get("accNo1"));
			bck0021ReqBodyBean.setAMOUNT(map.get("amount"));
			bck0021ReqBodyBean.setCARDNO(map.get("cardNo"));
			bck0021ReqBodyBean.setPRONAME(map.get("proName"));
			bck0021ReqBodyBean.setPROCODE(map.get("proCode"));
			bck0021ReqBodyBean.setCARDNAME(map.get("cardName"));
			bck0021ReqBodyBean.setCANEL_TYPE("2");
			if("1".equals(map.get("checkStatus"))){
				bck0021ReqBodyBean.setCHECKSTATUS("1");
			}else{
				bck0021ReqBodyBean.setCHECKSTATUS("2");
			}
		bck0021ReqBodyBean.setAGENT_INF(reqAgentInfBean);
		bck0021ReqBean.setBody(bck0021ReqBodyBean);
		xstream.alias("Root", BCK0021ReqBean.class);
		return xstream.toXML(bck0021ReqBean);
	}
	
	/**
	 * 电子账户 子账户销户
	 */
	public static String BCK_0022(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		BCK0022ReqBean bck0022ReqBean = new BCK0022ReqBean();
		InReqHeadBean inReqHeadBean = new InReqHeadBean();
		inReqHeadBean.setTradeCode("BCK_0022");
		inReqHeadBean.setMachineNo(GlobalParameter.machineNo);
		inReqHeadBean.setBranchNo(GlobalParameter.branchNo);
		inReqHeadBean.setMachineType(GlobalParameter.MACHINE_TYPE);

		bck0022ReqBean.setHeadBean(inReqHeadBean);
		BCK0022ReqBodyBean bck0022ReqBodyBean = new BCK0022ReqBodyBean();
		bck0022ReqBodyBean.setTRAN_CHANNEL("0035"); //渠道号 00-网银 50-手机银行
		bck0022ReqBodyBean.setCARD_NO(map.get("accNo")); //卡号
		bck0022ReqBodyBean.setSUB_ACCT_NO(map.get("sub_accNo")); //子账户
		bck0022ReqBodyBean.setCAL_MODE(map.get("calMode")); //结算方式  0-现金 1-转账
		bck0022ReqBodyBean.setCASH_ANALY_NO(""); //现金分析号
		bck0022ReqBodyBean.setOPPO_ACCT_NO(""); //对方账号
		bck0022ReqBodyBean.setID_TYPE(map.get("idType")); //证件类型
		bck0022ReqBodyBean.setID_NO(map.get("idCard")); //证件号码
		bck0022ReqBodyBean.setCERT_NO(map.get("certNo")); //凭证号
		
		bck0022ReqBodyBean.setPIN_VAL_FLAG(map.get("pinValFlag")); //验密标志
		if(!"N".equals(map.get("pinValFlag").toString())){
			bck0022ReqBodyBean.setPASSWORD(map.get("password"));//存单密码
		}else {
			bck0022ReqBodyBean.setPASSWORD("");//存单密码
		}
		bck0022ReqBodyBean.setHAV_AGENT_FLAG(map.get("agentFlag")); //代理人标志 0-是 1-否
		
			//拼接输出的AGENT_INF
			BCK0022ReqAgentInfBean reqAgentInfBean = new BCK0022ReqAgentInfBean();
			reqAgentInfBean.setCUST_NAME(map.get("agentIdCardName"));//客户姓名
			reqAgentInfBean.setDUE_DATE("");//到期日期
			reqAgentInfBean.setID_NO(map.get("agentIdCardNo"));//证件号码
			reqAgentInfBean.setID_TYPE("1");//证件类别
			reqAgentInfBean.setISSUE_DATE("");//签发日期
			reqAgentInfBean.setISSUE_INST(map.get("agentqfjg"));//签发机关
			reqAgentInfBean.setJ_C_ADD("");//经常居住地
			reqAgentInfBean.setMOB_PHONE("");//移动电话
			reqAgentInfBean.setNATION("");//国籍
			reqAgentInfBean.setOCCUPATION("公务员");//职业
			reqAgentInfBean.setREGIS_PER_RES(map.get("agentaddress"));//户口所在地
			reqAgentInfBean.setSEX(map.get("agentsex"));//性别
			reqAgentInfBean.setTELEPHONE("");//固定电话
			
			// 业务流水-P端使用字段
			bck0022ReqBodyBean.setACCT_NO(map.get("accNo1"));
			bck0022ReqBodyBean.setAMOUNT(map.get("amount"));
			bck0022ReqBodyBean.setCARDNO(map.get("cardNo"));
			bck0022ReqBodyBean.setPRONAME(map.get("proName"));
			bck0022ReqBodyBean.setPROCODE(map.get("proCode"));
			bck0022ReqBodyBean.setCARDNAME(map.get("cardName"));
			bck0022ReqBodyBean.setCANEL_TYPE("3");
			if("1".equals(map.get("checkStatus"))){
				bck0022ReqBodyBean.setCHECKSTATUS("1");
			}else{
				bck0022ReqBodyBean.setCHECKSTATUS("2");
			}			
		bck0022ReqBodyBean.setAGENT_INF(reqAgentInfBean);
		bck0022ReqBean.setBody(bck0022ReqBodyBean);
		xstream.alias("Root", BCK0022ReqBean.class);
		return xstream.toXML(bck0022ReqBean);
	}
	/**
	 * 黑白名单
	 */
	public static String BCK_01597(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		BCK01597ReqBean bck01597ReqBean = new BCK01597ReqBean();
		InReqHeadBean inReqHeadBean = new InReqHeadBean();
		inReqHeadBean.setTradeCode("BCK_01597");
		inReqHeadBean.setMachineNo(GlobalParameter.machineNo);
		inReqHeadBean.setBranchNo(GlobalParameter.branchNo);
		inReqHeadBean.setMachineType(GlobalParameter.MACHINE_TYPE);

		bck01597ReqBean.setHeadBean(inReqHeadBean);
		BCK01597ReqBodyBean bck01597ReqBodyBean = new BCK01597ReqBodyBean();
		//银行卡
//		bck01597ReqBodyBean.setCARD_NO(map.get("cardNo")); //卡号
		bck01597ReqBodyBean.setACC_NO1(map.get("cardNo"));//卡号
		bck01597ReqBodyBean.setCARD_NO1(map.get("cardNo"));//卡号
		//身份证
		bck01597ReqBodyBean.setID_NAME1(map.get("name"));//名字
		bck01597ReqBodyBean.setID_NUMBER1(map.get("id"));//卡号
		bck01597ReqBodyBean.setID_TYPE1(map.get("idtype"));//类型
		bck01597ReqBean.setBody(bck01597ReqBodyBean);
		xstream.alias("Root", BCK01597ReqBean.class);
		return xstream.toXML(bck01597ReqBean);
	}
	/**
	 * 个人客户建立
	 */
	public static String BCK_03446(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		BCK03446ReqBean bck03446ReqBean = new BCK03446ReqBean();
		InReqHeadBean inReqHeadBean = new InReqHeadBean();
		inReqHeadBean.setTradeCode("BCK_03446");
		inReqHeadBean.setMachineNo(GlobalParameter.machineNo);
		inReqHeadBean.setBranchNo(GlobalParameter.branchNo);
		inReqHeadBean.setMachineType(GlobalParameter.MACHINE_TYPE);

		bck03446ReqBean.setHeadBean(inReqHeadBean);
		BCK03446ReqBodyBean bck03446ReqBodyBean = new BCK03446ReqBodyBean();
		bck03446ReqBodyBean.setCUSTOM_MANAGER_NO("");//客户经理编号
		bck03446ReqBodyBean.setCUST_NAME(map.get("CUST_NAME"));//客户姓名
		bck03446ReqBodyBean.setCUST_SHORT("");//简称
		bck03446ReqBodyBean.setEN_NAME_1("");//英文姓氏
		bck03446ReqBodyBean.setEN_NAME_2("");//英文名称
		bck03446ReqBodyBean.setCUST_SEX(map.get("CUST_SEX"));//性别
		bck03446ReqBodyBean.setID_TYPE(map.get("ID_TYPE"));//证件类别
		bck03446ReqBodyBean.setID_NO(map.get("ID_NO"));//证件号码
		bck03446ReqBodyBean.setISSUE_DATE("");//签发日期
		bck03446ReqBodyBean.setDUE_DATE("");//失效日期
		bck03446ReqBodyBean.setISSUE_INST(map.get("ISSUE_INST"));//签发机关
		bck03446ReqBodyBean.setBIRTH_RES("");//出生地点
		bck03446ReqBodyBean.setBIRTH_DATE("");//出生日期
		bck03446ReqBodyBean.setCOUNTRY("");//国籍
		bck03446ReqBodyBean.setNATION("");//民族
		bck03446ReqBodyBean.setEDU_GRADE("");//教育程度
		bck03446ReqBodyBean.setMAR_STATUS("");//婚姻状况
		bck03446ReqBodyBean.setHEA_STATUS("");//健康状况
		bck03446ReqBodyBean.setCUST_TYPE(map.get("CUST_TYPE"));//客户类型
		bck03446ReqBodyBean.setBANK_SER_LEV("");//银行服务评级
		bck03446ReqBodyBean.setBANK_STAFF("");//是否银行员工
		bck03446ReqBodyBean.setBANK_PART("");//是否银行股东
		bck03446ReqBodyBean.setBUS_STATUS(map.get("BUS_STATUS"));//职业状况
		bck03446ReqBodyBean.setJ_C_ADD(map.get("DOMICILE_PLACE"));//经常居住地
		bck03446ReqBodyBean.setDOMICILE_PLACE(map.get("DOMICILE_PLACE"));//户口所在地
		bck03446ReqBodyBean.setTEL_NO(map.get("tel"));//手机号码
		bck03446ReqBean.setBody(bck03446ReqBodyBean);
		xstream.alias("Root", BCK03446ReqBean.class);
		return xstream.toXML(bck03446ReqBean);
	}
	/**
	 * 查询钱柜订单
	 * @param map
	 * @return
	 */
	public static String moneyBoxQueryOrder(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		MoneyBoxQueryOrderReqBean moneyBoxQueryOrderReqBean = new MoneyBoxQueryOrderReqBean();
		//先放入Tms端需要的报文头
		InReqHeadBean inReqHeadBean = new InReqHeadBean();
		inReqHeadBean.setTradeCode("MoneyBox_QueryOrder");
		inReqHeadBean.setMachineNo(GlobalParameter.machineNo);
		inReqHeadBean.setBranchNo(GlobalParameter.branchNo);
		inReqHeadBean.setMachineType(GlobalParameter.MACHINE_TYPE);
		moneyBoxQueryOrderReqBean.setHead(inReqHeadBean);
		MoneyBoxQueryOrderReqBodyBean bodyBean = new MoneyBoxQueryOrderReqBodyBean();
		bodyBean.setIDNum(map.get("IDNum"));
		bodyBean.setOrderNum(map.get("OrderNum"));
		bodyBean.setPassword(map.get("Password"));
		MoneyBoxOutHeadBean boxOutHeadBean = new MoneyBoxOutHeadBean();
		boxOutHeadBean.setServiceName("orderService");
		boxOutHeadBean.setMethodName("queryOrder");
//		boxOutHeadBean.setBranchNum("000000001");
		//boxOutHeadBean.setSubBranchNum("00000000102");
		boxOutHeadBean.setBranchNum(Property.getProperties().getProperty("parent_unit_no"));
//		boxOutHeadBean.setSubBranchNum("053600150");
		boxOutHeadBean.setSubBranchNum(GlobalParameter.branchNo);
		boxOutHeadBean.setChannel("0035");
		boxOutHeadBean.setTermNum(GlobalParameter.machineNo);
		boxOutHeadBean.setTransDate(DateUtil.getNowDate("yyyyMMdd"));
		
		moneyBoxQueryOrderReqBean.setRequest(bodyBean);
		moneyBoxQueryOrderReqBean.setMessageHead(boxOutHeadBean);
		MoneyBoxQueryOrderReqResponseBean response = new MoneyBoxQueryOrderReqResponseBean();
		moneyBoxQueryOrderReqBean.setResponse(response);
		xstream.alias("Root", MoneyBoxQueryOrderReqBean.class);
		return xstream.toXML(moneyBoxQueryOrderReqBean);
	}
	/**
	 * 业务申请
	 * @param map
	 * @return
	 */
	public static String moneyBoxTransApply(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		MoneyBoxTransApplyReqBean moneyBoxTransApplyReqBean = new MoneyBoxTransApplyReqBean();
		//先放入Tms端需要的报文头
		InReqHeadBean inReqHeadBean = new InReqHeadBean();
		inReqHeadBean.setTradeCode("MoneyBox_TransApply");
		inReqHeadBean.setMachineNo(GlobalParameter.machineNo);
		inReqHeadBean.setBranchNo(GlobalParameter.branchNo);
		inReqHeadBean.setMachineType(GlobalParameter.MACHINE_TYPE);
		moneyBoxTransApplyReqBean.setHead(inReqHeadBean);
		MoneyBoxTransApplyReqBodyBean bodyBean = new MoneyBoxTransApplyReqBodyBean();
		bodyBean.setIDNum(map.get("IDNum"));
		bodyBean.setOrderNum(map.get("OrderNum"));
		bodyBean.setPassword(map.get("Password"));
		bodyBean.setAccount(map.get("Account"));
		bodyBean.setAmount(map.get("Amount"));
		bodyBean.setCartonNum(map.get("CartonNum"));
		bodyBean.setTellerNum(map.get("TellerNum"));
		bodyBean.setTermNum(map.get("TermNum"));
		bodyBean.setTransType(map.get("TransType"));
		MoneyBoxOutHeadBean boxOutHeadBean = new MoneyBoxOutHeadBean();
		boxOutHeadBean.setServiceName("orderService");
		boxOutHeadBean.setMethodName("transApply");
//		boxOutHeadBean.setBranchNum("000000001");
		//boxOutHeadBean.setSubBranchNum("00000000102");
		boxOutHeadBean.setBranchNum(Property.getProperties().getProperty("parent_unit_no"));
//		boxOutHeadBean.setSubBranchNum("053600150");
		boxOutHeadBean.setSubBranchNum(GlobalParameter.branchNo);
		boxOutHeadBean.setChannel("0035");
		boxOutHeadBean.setTermNum(GlobalParameter.machineNo);
		boxOutHeadBean.setTransDate(DateUtil.getNowDate("yyyyMMdd"));
		
		moneyBoxTransApplyReqBean.setRequest(bodyBean);
		moneyBoxTransApplyReqBean.setMessageHead(boxOutHeadBean);
		MoneyBoxTransApplyReqResponseBean response = new MoneyBoxTransApplyReqResponseBean();
		moneyBoxTransApplyReqBean.setResponse(response);
		xstream.alias("Root", MoneyBoxTransApplyReqBean.class);
		return xstream.toXML(moneyBoxTransApplyReqBean);
	}
	/**
	 * 业务提交
	 * @param map
	 * @return
	 */
	public static String moneyBoxTransCommit(Map<String,String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		MoneyBoxTransCommitReqBean moneyBoxTransCommitReqBean = new MoneyBoxTransCommitReqBean();
		//先放入Tms端需要的报文头
		InReqHeadBean inReqHeadBean = new InReqHeadBean();
		inReqHeadBean.setTradeCode("MoneyBox_TransCommit");
		inReqHeadBean.setMachineNo(GlobalParameter.machineNo);
		inReqHeadBean.setBranchNo(GlobalParameter.branchNo);
		inReqHeadBean.setMachineType(GlobalParameter.MACHINE_TYPE);
		moneyBoxTransCommitReqBean.setHead(inReqHeadBean);
		MoneyBoxTransCommitReqBodyBean bodyBean = new MoneyBoxTransCommitReqBodyBean();
		bodyBean.setIDNum(map.get("IDNum"));
		bodyBean.setOrderNum(map.get("OrderNum"));
		bodyBean.setPassword(map.get("Password"));
		bodyBean.setApplyCode(map.get("ApplyCode"));
		bodyBean.setCommitStatus(map.get("CommitStatus"));
		bodyBean.setDepositReceiptNum(map.get("DepositReceiptNum"));
		bodyBean.setCoreJournal(map.get("CoreJournal"));
		MoneyBoxOutHeadBean boxOutHeadBean = new MoneyBoxOutHeadBean();
		boxOutHeadBean.setServiceName("orderService");
		boxOutHeadBean.setMethodName("transCommit");
//		boxOutHeadBean.setBranchNum("000000001");
		//boxOutHeadBean.setSubBranchNum("00000000102");
		boxOutHeadBean.setBranchNum(Property.getProperties().getProperty("parent_unit_no"));
//		boxOutHeadBean.setSubBranchNum("053600150");
		boxOutHeadBean.setSubBranchNum(GlobalParameter.branchNo);
		boxOutHeadBean.setChannel("0035");
		boxOutHeadBean.setTermNum(GlobalParameter.machineNo);
		boxOutHeadBean.setTransDate(DateUtil.getNowDate("yyyyMMdd"));
		
		moneyBoxTransCommitReqBean.setRequest(bodyBean);
		moneyBoxTransCommitReqBean.setMessageHead(boxOutHeadBean);
		MoneyBoxTransCommitReqResponseBean response = new MoneyBoxTransCommitReqResponseBean();
		moneyBoxTransCommitReqBean.setResponse(response);
		xstream.alias("Root", MoneyBoxTransCommitReqBean.class);
		return xstream.toXML(moneyBoxTransCommitReqBean);
	}
	/**
	 * 客户基本信息查询
	 * @return
	 */
	public static String BCK_04422(Map<String, String> map2){
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
		bCK04422ReqBodyBean.setRESOLVE_TYPE(map2.get("RESOLVE_TYPE"));	//识别方式
		bCK04422ReqBodyBean.setECIF_CUST_NO(map2.get("ECIF_CUST_NO"));	//客户编号
		bCK04422ReqBodyBean.setPARTY_NAME(map2.get("PARTY_NAME"));		//客户姓名
		bCK04422ReqBodyBean.setCERT_TYPE("10100");			//证件类型
		bCK04422ReqBodyBean.setCERT_NO(map2.get("CERT_NO"));				//证件号码
		bCK04422ReqBodyBean.setACCT_NO(map2.get("ACCT_NO"));				//客户识别账号
		bCK04422ReqBean.setBody(bCK04422ReqBodyBean);
		xstream.alias("ROOT", BCK04422ReqBean.class);
		xstream.alias("HEAD", InReqHeadBean.class);
		xstream.alias("BODY", BCK04422ReqBodyBean.class);
		return xstream.toXML(bCK04422ReqBean);
	}

	 /**
	  * * 客户基本信息查询
	 * @return
	 */
	public static String BCK_03519(Map<String, String> map){
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		BCK03519ReqBean bCK03519ReqBean = new BCK03519ReqBean();
		InReqHeadBean inReqHeadBean = new InReqHeadBean();
		inReqHeadBean.setTradeCode("BCK_03519");	
		inReqHeadBean.setMachineNo(GlobalParameter.machineNo);
		inReqHeadBean.setBranchNo(GlobalParameter.branchNo);
		inReqHeadBean.setMachineType(GlobalParameter.MACHINE_TYPE);
		bCK03519ReqBean.setHeadBean(inReqHeadBean);		
		BCK03519ReqBodyBean bCK03519ReqBodyBean = new BCK03519ReqBodyBean();
		bCK03519ReqBodyBean.setCARD_NO(map.get("CARD_NO"));//卡号
		bCK03519ReqBean.setBody(bCK03519ReqBodyBean);
		xstream.alias("ROOT", BCK03519ReqBean.class);
		xstream.alias("HEAD", InReqHeadBean.class);
		xstream.alias("BODY", BCK03519ReqBodyBean.class);
		return xstream.toXML(bCK03519ReqBean);
	}

	
	
}