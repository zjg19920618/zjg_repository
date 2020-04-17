package com.boomhope.Bill.TransService.CashOpen.moneybox;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
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
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Public.GlobalPanelFlag;
import com.boomhope.Bill.Socket.SocketClient;
import com.boomhope.Bill.TransService.CashOpen.Bean.PublicCashOpenBean;
import com.boomhope.Bill.Util.DateUtil;
import com.boomhope.Bill.Util.FileUtil;
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
 * 现金开户校验身代理人份证
 * @author gyw
 *
 */
public class MoneyBoxCheckIdcardIngAgent extends BaseTransPanelNew{
	private static final long serialVersionUID = 1L;
	Timer checkTimer = null;
	static Logger logger = Logger.getLogger(MoneyBoxCheckIdcardIngAgent.class);
	public MoneyBoxCheckIdcardIngAgent(final PublicCashOpenBean transBean){
		this.cashBean = transBean;
	
		/* 标题信息 */
		JLabel titleLabel = new JLabel("代理人信息查询中，请稍后。。。");
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
		
		
		/* 执行身份检查 */
		checkTimer = new Timer(1000, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {  
            	stopTimer(checkTimer);
            	checkIdCard(transBean);
            }      
        });            
		checkTimer.start(); 
	
	}
	
	/**
	 * 身份信息核查
	 */
	public void checkIdCard(PublicCashOpenBean transBean){
		//校验证件为账户本人身份证，请插入代理人身份证
		if(transBean.getReadIdcard().equals(transBean.getAgentIdCardNo())){
			transBean.getReqMCM001().setIntereturnmsg("插入的代理人身份证和本人身份证相同");
			fail(transBean, "证件为账户本人身份证，请插入代理人身份证");
			return;
		}
		// 上送核心检查
		Map<String,String> map1 = new HashMap<String,String>();
		map1.put("id", transBean.getAgentIdCardNo());
		logger.info("代理人身份证号"+transBean.getAgentIdCardNo());
		map1.put("idtype", "1");
		map1.put("name", transBean.getAgentIdCardName());
		logger.info("代理人名字"+transBean.getAgentIdCardName());
		map1.put("cardNo", "");
		
		transBean.getReqMCM001().setReqBefor("01597");//接口调用前流水信息记录
		
		// 查询白名单
		BCK01597ResBean resBean1 = getBillInfo1(map1);
		if(resBean1 == null){
			transBean.getReqMCM001().setIntereturnmsg("查询白名单失败：调用01597接口异常");
			MakeMonitorInfo.makeInfos(transBean.getReqMCM001());//接口调用失败，上送流水信息
			serverStop("请联系大堂经理","","调用接口失败");
			return;
		}
		String resCode1 = resBean1.getHeadBean().getResCode();
		String resMsg1 = resBean1.getHeadBean().getResMsg();
		transBean.getReqMCM001().setReqAfter(resCode1,resMsg1);//接口调用成功后记录流水信息
		logger.info("查询白名单resCode"+resCode1);
		logger.info("查询白名单resMsg"+resMsg1);
		if(!"000000".equals(resCode1)){
			MakeMonitorInfo.makeInfos(transBean.getReqMCM001());//接口调用失败，上送流水信息
			serverStop("请联系大堂经理",resMsg1, "");
			return;
		}
		String svrno1 = resBean1.getBody().getESB_LIST().getSTTN();
		if(!"0000".equals(svrno1)){
			if("0010".equals(svrno1)){
				String desc = resBean1.getBody().getESB_LIST().getSTTN();
				transBean.getReqMCM001().setIntereturnmsg("该客户为涉案客户，禁止交易");
				MakeMonitorInfo.makeInfos(transBean.getReqMCM001());//接口调用失败，上送流水信息
				serverStop("请联系大堂经理", "","该客户为涉案客户，禁止交易。");
				return;
			}if("0020".equals(svrno1)){
				transBean.getReqMCM001().setIntereturnmsg("该客户可疑");
				MakeMonitorInfo.makeInfos(transBean.getReqMCM001());//接口调用失败，上送流水信息
				serverStop("请联系大堂经理","","该客户可疑，谨防诈骗，请到我行柜台办理。" );
				return;
			}
		}
		// 上送核心检查
		Map<String,String> map = new HashMap<String,String>();
		map.put("agentIdNo",transBean.getAgentIdCardNo());
		map.put("agentName",transBean.getAgentIdCardName());
		
		transBean.getReqMCM001().setReqBefor("08021");//接口调用前流水信息记录
		// 获取存单返回信息
		BCK0011ResBean resBean = getAgentCheck(map);
		if(resBean != null){
			String resCode = resBean.getHeadBean().getResCode();
			String resMsg = resBean.getHeadBean().getResMsg();
			transBean.getReqMCM001().setReqAfter(resCode,resMsg);//接口调用成功后记录流水信息
			logger.info("代理人身份信息核对resCode:"+resCode);
			logger.info("代理人身份信息核对resMsg:"+resMsg);
			if(!"000000".equals(resCode)){
				MakeMonitorInfo.makeInfos(transBean.getReqMCM001());//业务失败，上送流水信息
				fail(transBean, "代理人核查失败："+resMsg);
				logger.error(resMsg);
				return;
			}
			String result = resBean.getBody().getCHECK_RESULT();
			if("0".equals(result)){
				transBean.getReqMCM001().setIntereturnmsg("本行员工不能代理");
				fail(transBean, "本行员工不得代理");
				return;
			}
		}else{
			transBean.getReqMCM001().setIntereturnmsg("查询代理人信息失败：调用08021接口异常");
			MakeMonitorInfo.makeInfos(transBean.getReqMCM001());//业务失败，上送流水信息
			fail(transBean, "代理人核查失败,前置接口调用失败");
			return;
		}
		//先进行联网核查，
		Map<String,String> agentmap = new HashMap<String,String>();
		agentmap.put("id", transBean.getAgentIdCardNo());
		agentmap.put("name", transBean.getAgentIdCardName());

		transBean.getReqMCM001().setReqBefor("03448");//接口调用前流水信息记录
		
		BCK0008ResBean resBeanAgent = getPoiceInfo(agentmap);
		String resCodeAgent = resBeanAgent.getHeadBean().getResCode();
		String resMsgAgent = resBeanAgent.getHeadBean().getResMsg();
		logger.error("代理人身份核查resCode："+resCodeAgent);
		logger.error("代理人身份核查resMsg："+resMsgAgent);
		transBean.getReqMCM001().setReqAfter(resCodeAgent,resMsgAgent);//接口调用成功后记录流水信息
		if(!"000000".equals(resCodeAgent)){
			MakeMonitorInfo.makeInfos(transBean.getReqMCM001());//业务失败，上送流水信息
			fail(transBean, resMsgAgent);
			return;
		}
		transBean.setAgentinspect(resBeanAgent.getBody().getCORE_RET_MSG());
		try {
			String fileName = resBeanAgent.getBody().getFILE_NAME();
			FtpFileUtils.downloadFileByFTPClient(Property.FTP_IP_DOWN, Integer.parseInt(Property.FTP_PORT_DOWN), Property.FTP_USER_DOWN, Property.FTP_PWD_DOWN, fileName, Property.FTP_LOCAL_PATH + fileName);
			UtilPreFile.getIdCardImage(Property.FTP_LOCAL_PATH+fileName,Property.ID_CARD_AGENT);
			// 代理人联网核查照片查看
			String tmp1 = Property.BH_TMP + "\\"+DateUtil.getNowDate("yyyyMMdd")+"\\" +transBean.getFid()+"\\";
			// 拷贝临时图片--------------------
			File from = new File(Property.ID_CARD_AGENT);
			// 目标目录
			File to = new File(tmp1+transBean.getIdCardNo()+"_agentPoic.jpg");
			FileUtil.copyFileUsingJava7Files(from, to);
		} catch (Exception e) {
			transBean.getReqMCM001().setIntereturnmsg("获取联网核查结果图片失败：调用03448接口异常");
			MakeMonitorInfo.makeInfos(transBean.getReqMCM001());//接口调用失败，上送流水信息
			serverStop("请联系大堂经理","","获取联网核查结果图片失败" );
			logger.error(e);
			return;
		}
		//全部成功
		success(transBean);
	}
	
	/**
	 * 代理人身份信息核对
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public BCK0011ResBean getAgentCheck(Map<String,String> map)  {
		
		String retMsg = "";
		SocketClient sc = new SocketClient();
		Socket socket=null;
		try {
			socket = sc.createSocket();
            //构建IO
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            //向服务器端发送一条消息  
            bw.write(sc.BCK_0011(map) + "\n");  
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
			reqXs.alias("Root", BCK0011ResBean.class);
			BCK0011ResBean bck0002ResBean = (BCK0011ResBean)reqXs.fromXML(retMsg);
			logger.info(bck0002ResBean);
			logger.info("CLIENT retMsg:" + retMsg);
			return bck0002ResBean;
		} catch (UnknownHostException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		}finally{
			try {
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
		InputStream is =null;
		OutputStream os=null;
		try {
			 socket =sc.createSocket();
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
	 * 联网核查
	 */
	public BCK0008ResBean getPoiceInfo(Map<String,String> map){
		
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
            bw.write(sc.BCK_0008(map) + "\n");
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
			reqXs.alias("Root", BCK0008ResBean.class);
			BCK0008ResBean bck0002ResBean = (BCK0008ResBean)reqXs.fromXML(retMsg);
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
	 * 
	 * 失败后处理
	 */
	private void fail(PublicCashOpenBean transBean,String errmsg){
		transBean.setErrmsg(errmsg);
		transBean.getImportMap().put("CashProcurator", GlobalPanelFlag.DEPUTY_SELECTION+"");
		clearTimeText();
		openPanel(new MoneyBoxMistakePanel(transBean));			
		
	 	
	}
	
	/**
	 * 成功
	 */
	private void success(PublicCashOpenBean transBean){
		
		String idCard_check_result = transBean.getImportMap().get("agent_persion_result");
		if("yes".equals(idCard_check_result)){
			//yes代表这个被代理人没有创建用户信息
			openPanel(new MoneyBoxNotCompleteCustPromPanel(transBean));
		}else{
			transBean.setJijvOrPuhui("");//清空普惠标示
			openPanel(new MoneyBoxPageSelectlPanel(transBean));
		}
	}
}
