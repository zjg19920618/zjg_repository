package com.boomhope.Bill.TransService.BillPrint;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Public.GlobalPanelFlag;
import com.boomhope.Bill.Socket.SocketClient;
import com.boomhope.Bill.TransService.BillPrint.Bean.BillPrintBean;
import com.boomhope.Bill.Util.FtpFileUtils;
import com.boomhope.Bill.Util.Property;
import com.boomhope.Bill.Util.UtilPreFile;
import com.boomhope.Bill.monitor.sendInterface.MakeMonitorInfo;
import com.boomhope.tms.message.XStreamNameCoder;
import com.boomhope.tms.message.in.bck.BCK0008ResBean;
import com.boomhope.tms.message.in.bck.BCK0011ResBean;
import com.boomhope.tms.message.in.bck.BCK01597ResBean;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * 
 * title:身份证联网核查
 * @author ly
 * 2016年11月7日下午10:00:15
 */
public class TransPrintPoliceCheckPanel extends BaseTransPanelNew {

	static Logger logger = Logger.getLogger(TransPrintPoliceCheckPanel.class);

	private static final long serialVersionUID = 1L;

	Timer checkTimer = null;

	public TransPrintPoliceCheckPanel(final BillPrintBean transBean) {
		logger.info("进入身份证联网核查页面");
		this.billPrintBean = transBean;
		GlobalPanelFlag.CurrentFlag = GlobalPanelFlag.PRINT_POLICE_CHECK;

		/* 显示时间倒计时 */
		this.showTimeText(delaySecondShortTime);
		delayTimer = new Timer(delaySecondShortTime * 1000, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {  
            	/* 倒计时结束退出交易 */ 
            	clearTimeText();
            	serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！","","");
            }      
        });            
		delayTimer.start(); 
		
		/* 标题信息 */
		JLabel titleLabel = new JLabel("正在进行联网核查，请稍候...");
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		titleLabel.setForeground(Color.decode("#412174"));
		titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 40));
		titleLabel.setBounds(0, 60, 1009, 60);
		this.showJpanel.add(titleLabel);

		/* 加载核查动画 */
		JLabel billImage = new JLabel();
		billImage.setIcon(new ImageIcon("pic/checking.gif"));
		billImage.setIconTextGap(6);
		billImage.setBounds(450, 349, 220, 159);
		this.showJpanel.add(billImage);

		/* 倒计时进入联网核查 */
		checkTimer = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stopTimer(checkTimer);
				poiceCheck(transBean);
			}
		});
		checkTimer.start();

	}

	/**
	 * 调用接口核查
	 */
	public void poiceCheck(BillPrintBean transBean) {
		String me = transBean.getImportMap().get("me");
		if(me != null && me.equals("true")){
			// 本人身份核查
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", transBean.getReadIdcard());
			logger.info("身份证号"+transBean.getReadIdcard());
			map.put("idtype", "1");
			map.put("name", transBean.getIdCardName());
			logger.info("名字"+transBean.getIdCardName());
			map.put("cardNo", "");
			// 查询白名单
			transBean.getReqMCM001().setReqBefor("01597");
			BCK01597ResBean resBean1 = getBillInfo1(map);
			if(resBean1 == null){
				transBean.getReqMCM001().setIntereturnmsg("调用01597接口异常");
				MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
				serverStop("请联系大堂经理","","调用接口失败");
				return;
			}
			String resCode1 = resBean1.getHeadBean().getResCode();
			String resMsg1 = resBean1.getHeadBean().getResMsg();
			logger.info("查询白名单resCode"+resCode1);
			logger.info("查询白名单resMsg"+resMsg1);
			transBean.getReqMCM001().setReqAfter(resCode1, resMsg1);
			if(!"000000".equals(resCode1)){
				MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
				serverStop("请联系大堂经理", resMsg1,"");
				return;
			}
			String svrno1 = resBean1.getBody().getESB_LIST().getSTTN();
			if(!"0000".equals(svrno1)){
				if("0010".equals(svrno1)){
					String desc = resBean1.getBody().getESB_LIST().getSTTN();
					serverStop("请联系大堂经理", "","该客户为涉案客户，禁止交易。");
					return;
				}if("0020".equals(svrno1)){
					serverStop("请联系大堂经理", "","该客户可疑，谨防诈骗，请到我行柜台办理。");
					return;
				}
			}	
			//	本人联网核查
			transBean.getReqMCM001().setReqBefor("07670");
			BCK0008ResBean resBean = getPoiceInfo(map);
			if(resBean == null){
				logger.info("调用联网核查接口失败");
				transBean.getReqMCM001().setIntereturnmsg("调用07670接口异常");
				MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
				serverStop("请联系大堂经理","","调用接口失败");
				return;
			}
			String resCode = resBean.getHeadBean().getResCode();
			String resMsg = resBean.getHeadBean().getResMsg();
			logger.error("本人身份核查resCode：" + resCode);
			logger.error("本人身份核查resMsg：" + resMsg);
			transBean.getReqMCM001().setReqAfter(resCode1, resMsg1);
			if (!"000000".equals(resCode)) {
				MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
				serverStop("请联系大堂经理","联网核查失败:"+resMsg, "");
				return;
			}
			try {
				String fileName = resBean.getBody().getFILE_NAME();
				FtpFileUtils.downloadFileByFTPClient(Property.FTP_IP_DOWN,Integer.parseInt(Property.FTP_PORT_DOWN),Property.FTP_USER_DOWN, Property.FTP_PWD_DOWN, fileName,Property.FTP_LOCAL_PATH + fileName);
				UtilPreFile.getIdCardImage(Property.FTP_LOCAL_PATH + fileName,Property.ID_CARD_SELF);
			} catch (Exception e) {
				serverStop("请联系大堂经理", "","获取联网核查结果图片失败");
				logger.error(e);
				return;
			}
			transBean.setInspect(resBean.getBody().getCORE_RET_MSG());
		}
		
		
		
		// 代理人身份核查
		String agent =transBean.getImportMap().get("agent");
		if (agent != null && "true".equals(agent)) {
			Map<String, String> agentmap = new HashMap<String, String>();
			agentmap.put("id", transBean.getAgentIdCardNo());
			logger.info("身份证号"+transBean.getAgentIdCardNo());
			agentmap.put("idtype", "1");
			agentmap.put("name", transBean.getAgentIdCardName());
			logger.info("名字"+transBean.getAgentIdCardName());
			agentmap.put("cardNo", "");
			// 查询白名单
			transBean.getReqMCM001().setReqBefor("01597");
			BCK01597ResBean resBean2 = getBillInfo1(agentmap);
			if(resBean2 == null){
				transBean.getReqMCM001().setIntereturnmsg("调用01597接口异常");
				MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
				serverStop("请联系大堂经理","","调用接口失败");
				return;
			}
			String resCode2 = resBean2.getHeadBean().getResCode();
			String resMsg2= resBean2.getHeadBean().getResMsg();
			logger.info("查询白名单resCode"+resCode2);
			logger.info("查询白名单resMsg"+resMsg2);
			transBean.getReqMCM001().setReqAfter(resCode2, resMsg2);
			if(!"000000".equals(resCode2)){
				MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
				serverStop("请联系大堂经理",resMsg2, "");
				return;
			}
			String svrno2 = resBean2.getBody().getESB_LIST().getSTTN();
			if(!"0000".equals(svrno2)){
				if("0010".equals(svrno2)){
					String desc = resBean2.getBody().getESB_LIST().getSTTN();
					serverStop("请联系大堂经理", "","该客户为涉案客户，禁止交易。");
					return;
				}if("0020".equals(svrno2)){
					serverStop("请联系大堂经理","","该客户可疑，谨防诈骗，请到我行柜台办理。" );
					return;
				}
			}	
            //	代理人联网核查
			transBean.getReqMCM001().setReqBefor("07670");
			BCK0008ResBean resBeanAgent = getPoiceInfo(agentmap);
			if(resBeanAgent == null){
				logger.info("调用联网核查接口失败");
				transBean.getReqMCM001().setIntereturnmsg("调用07670接口异常");
				MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
				serverStop("请联系大堂经理","","调用接口失败");
				return;
			}
			String resCodeAgent = resBeanAgent.getHeadBean().getResCode();
			String resMsgAgent = resBeanAgent.getHeadBean().getResMsg();
			logger.error("代理人身份核查resCode：" + resCodeAgent);
			logger.error("代理人身份核查resMsg：" + resMsgAgent);
			transBean.getReqMCM001().setReqAfter(resCodeAgent, resMsgAgent);
			if (!"000000".equals(resCodeAgent)) {
				MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
				fail(transBean, resMsgAgent);
				return;
			}
			try {
				String fileName = resBeanAgent.getBody().getFILE_NAME();
				FtpFileUtils.downloadFileByFTPClient(Property.FTP_IP_DOWN,Integer.parseInt(Property.FTP_PORT_DOWN),Property.FTP_USER_DOWN, Property.FTP_PWD_DOWN,fileName, Property.FTP_LOCAL_PATH + fileName);
				UtilPreFile.getIdCardImage(Property.FTP_LOCAL_PATH + fileName,Property.ID_CARD_AGENT);
			} catch (Exception e) {
				serverStop("请联系大堂经理","","获取联网核查结果异常" );
				logger.error(e);
				return;
			}
			transBean.setAgentinspect(resBeanAgent.getBody().getCORE_RET_MSG());
			
			// 代理人身份信息核对
			agentmap.put("agentIdNo", transBean.getAgentIdCardNo());
			agentmap.put("agentName", transBean.getAgentIdCardName());
			transBean.getReqMCM001().setReqBefor("08021");
			BCK0011ResBean agentCheck = getAgentCheck(agentmap);
			if(agentCheck == null){
				logger.info("调用代理人核对接口失败");
				transBean.getReqMCM001().setIntereturnmsg("调用08021接口异常");
				MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
				serverStop("请联系大堂经理","","调用接口失败");
				return;
			}
			String resCode = agentCheck.getHeadBean().getResCode();
			String resMsg = agentCheck.getHeadBean().getResMsg();
			transBean.getReqMCM001().setReqAfter(resCode, resMsg);
			if(!"000000".equals(resCode)){
				MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
				fail(transBean, resMsg);
				logger.error(resMsg);
				return;
			}
			if("0".equals(agentCheck.getBody().getCHECK_RESULT())){
				fail(transBean, "代理人不允许为本行员工！");
				logger.error("代理人不允许为本行员工！");
				return;
			}
		}
		// 全部核查成功
		if(agent !=null && me!=null && "true".equals(agent) && "true".equals(me)){
			openPanel(new TransPrintOrStateChage(transBean));
		}else{
			success(transBean);
		}
	}
	
	/**
	 * 联网核查
	 */
	public BCK0008ResBean getPoiceInfo(Map<String, String> map) {

		String retMsg = "";
		SocketClient sc = new SocketClient();
		Socket socket=null;
		InputStream is=null;
		OutputStream os=null;
		try {
			 socket = sc.createSocket();
            //构建IO  
             is = socket.getInputStream();  
             os = socket.getOutputStream();  
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os,
					"UTF-8"));
			// 向服务器端发送一条消息
			bw.write(sc.BCK_07670(map) + "\n");
			bw.flush();
			// 读取服务器返回的消息
			BufferedReader br = new BufferedReader(new InputStreamReader(is,
					"UTF-8"));
			String value = null;
			while ((value = br.readLine()) != null) {
				retMsg += value + "\n";
				if ("</Root>".equals(value)) {
					break;
				}
			}
			logger.info(retMsg);
			XStream reqXs = new XStream(new DomDriver("UTF-8",
					new XStreamNameCoder()));
			reqXs.alias("Root", BCK0008ResBean.class);
			BCK0008ResBean bck0002ResBean = (BCK0008ResBean) reqXs
					.fromXML(retMsg);
			logger.info(bck0002ResBean);
			logger.info("CLIENT retMsg:" + retMsg);
			return bck0002ResBean;
		} catch (UnknownHostException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		}finally{
			try {
				if(os!=null){
					os.close();
				}
				if(is!=null){
					is.close();
				}
				if(socket!=null){
					socket.close();
				}
			} catch (Exception e2) {
				logger.info(e2);
			}
		}
		return null;
	}

	/**
	 * 代理人身份信息核对
	 */
	public BCK0011ResBean getAgentCheck(Map<String, String> map) {

		String retMsg = "";
		SocketClient sc = new SocketClient();
		Socket socket=null;
		InputStream is=null;
		OutputStream os=null;
		try {
			 socket = sc.createSocket();
            //构建IO  
             is = socket.getInputStream();  
             os = socket.getOutputStream();  
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os,
					"UTF-8"));
			// 向服务器端发送一条消息
			bw.write(sc.BCK_0011(map) + "\n");
			bw.flush();
			// 读取服务器返回的消息
			BufferedReader br = new BufferedReader(new InputStreamReader(is,
					"UTF-8"));
			String value = null;
			while ((value = br.readLine()) != null) {
				retMsg += value + "\n";
				if ("</Root>".equals(value)) {
					break;
				}
			}
			logger.info(retMsg);
			XStream reqXs = new XStream(new DomDriver("UTF-8",
					new XStreamNameCoder()));
			reqXs.alias("Root", BCK0011ResBean.class);
			BCK0011ResBean bck0011ResBean = (BCK0011ResBean) reqXs
					.fromXML(retMsg);
			logger.info(bck0011ResBean);
			logger.info("CLIENT retMsg:" + retMsg);
			return bck0011ResBean;
		} catch (UnknownHostException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		}finally{
			try {
				if(os!=null){
					os.close();
				}
				if(is!=null){
					is.close();
				}
				if(socket!=null){
					socket.close();
				}
			} catch (Exception e2) {
				logger.info(e2);
			}
		}
		return null;
	}
	
	/**
	 * 调用接口是否白名单
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public BCK01597ResBean getBillInfo1(Map<String,String> map1)  {
		String retMsg = "";
		SocketClient sc = new SocketClient();
		Socket socket=null;
		InputStream is=null;
		OutputStream os=null;
		try {
			 socket = sc.createSocket();
            //构建IO  
             is = socket.getInputStream();  
             os = socket.getOutputStream();  
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));  
            //向服务器端发送一条消息  
            bw.write(sc.BCK_01597(map1) + "\n");  
            bw.flush();  
            //读取服务器返回的消息  
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));  
			
			String value = null;
			while ((value = br.readLine()) != null) {
				retMsg += value + "\n";
				if ("</Root>".equals(value)){
					break;
				}
			}
			logger.info(retMsg);
			XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			reqXs.alias("Root", BCK01597ResBean.class);
			BCK01597ResBean bck01597ResBean = (BCK01597ResBean)reqXs.fromXML(retMsg);
			logger.info(bck01597ResBean);
			logger.info("CLIENT retMsg:" + retMsg);
			return bck01597ResBean;
		} catch (UnknownHostException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		}finally{
			try {
				if(os!=null){
					os.close();
				}
				if(is!=null){
					is.close();
				}
				if(socket!=null){
					socket.close();
				}
			} catch (Exception e2) {
				logger.info(e2);
			}
		}
		return null;

	}
	
	/**
	 * 成功
	 */
	public void success(BillPrintBean transBean) {
		openPanel(new TransferPrintCameraPanel(transBean));
		
		
	}

	/**
	 * 失败
	 */
	public void fail(BillPrintBean transBean,String errmsg) {
		String me = transBean.getImportMap().get("me");
		if(me != null && me.equals("true")){
			// 本人核查失败
			transBean.getImportMap().put("idCard_check", "fail");
		}
		String agent = transBean.getImportMap().get("agent");
		if (agent != null && "true".equals(agent)) {
			// 代理人核查失败
			transBean.getImportMap().put("agent_idCard_check", "fail");
		}
	
		// 失败原因
		transBean.setErrmsg(errmsg);
		openPanel(new TransPrintPoliceCheckFailPanel(transBean));
	}


}
