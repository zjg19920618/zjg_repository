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
import java.util.Properties;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Public.GlobalPanelFlag;
import com.boomhope.Bill.Public.GlobalParameter;
import com.boomhope.Bill.Socket.SocketClient;
import com.boomhope.Bill.TransService.CashOpen.Bean.PublicCashOpenBean;
import com.boomhope.Bill.Util.DateUtil;
import com.boomhope.Bill.Util.JpgUtil;
import com.boomhope.Bill.Util.Property;
import com.boomhope.Bill.Util.SFTPUtil;
import com.boomhope.Bill.monitor.sendInterface.MakeMonitorInfo;
import com.boomhope.tms.message.XStreamNameCoder;
import com.boomhope.tms.message.in.InReqHeadBean;
import com.boomhope.tms.message.in.bck.BCK0009ResBean;
import com.boomhope.tms.message.in.bck.BCK0010ResBean;
import com.boomhope.tms.message.in.bck.BCK03446ResBean;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
/**
 * 处理中
 * @author gyw
 *
 */
public class MoneyBoxSystemProcessingPanel extends BaseTransPanelNew{
	private static final long serialVersionUID = 1L;
	
	static Logger logger = Logger.getLogger(MoneyBoxSystemProcessingPanel.class);
	// 核心流水号
	private  String seq = "";
	// 核心流水号
	private String svr_date = "";
	Timer checkTimer = null;
	String errmsg = "";
	public MoneyBoxSystemProcessingPanel(final PublicCashOpenBean transBean) {

		this.cashBean = transBean;
		/* 警告图标 */
		JLabel billImage = new JLabel();
		billImage.setIcon(new ImageIcon("pic/processing.gif"));
		billImage.setIconTextGap(6);
		billImage.setBounds(281, 228, 200, 200);
		this.showJpanel.add(billImage);
		

		JLabel label = new JLabel("正在处理中，请稍候...");
		label.setHorizontalAlignment(SwingConstants.LEFT);
		label.setForeground(Color.decode("#412174"));
		label.setFont(new Font("微软雅黑", Font.BOLD, 30));
		label.setBounds(502, 317, 505, 30);
		this.showJpanel.add(label);
		
		/* 倒计时进入接口调用 */
		checkTimer = new Timer(500, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {  
            	// 获取授权方式
            	process(transBean);
            	checkTimer.stop();
            	
            }      
        });            
		checkTimer.start(); 

	}
	/**
	 * 业务处理
	 * @return
	 */
	String  rescode= null;
	String resmsg = null;
	public void process(PublicCashOpenBean transBean){
		String identifying = transBean.getImportMap().get("identifying");
		if(identifying.equals("2")){
			
			transBean.getReqMCM001().setReqBefor("07659");//接口调用前流水信息记录
			
			BCK0009ResBean bck0009ResBean = getsqfs(transBean);
			if(bck0009ResBean == null){
				transBean.getReqMCM001().setIntereturnmsg("查询授权方式失败：调用07659接口异常");
				MakeMonitorInfo.makeInfos(transBean.getReqMCM001());//接口调用失败，上送流水信息
				serverStop("请重新授权","","查询授权方式失败");
				return;
			}
			String resCode = bck0009ResBean.getHeadBean().getResCode();
			String resMsg = bck0009ResBean.getHeadBean().getResMsg();
			
			//接口调用成功后记录流水信息
			transBean.getReqMCM001().setReqAfter(resCode,resMsg);
			
			logger.info("授权方式查询rescode："+resCode);
			logger.info("授权方式查询resmsg："+resMsg);
			if(!"000000".equals(resCode)){
				if(resCode.contains("700012")){
					openPanel(new MoneyBoxAdditionNumberFailure(transBean));
					return;
				}else{
					MakeMonitorInfo.makeInfos(transBean.getReqMCM001());//接口调用失败，上送流水信息
					serverStop("请联系大堂经理",resMsg, "");	
					return;
				}
			}
			String check_mode = bck0009ResBean.getBody().getCHECK_MOD();
			// 查询授权模式
			if(check_mode.equals("1")){
				transBean.getReqMCM001().setAuthmethod1("1");
				// 1-口令
				openPanel(new MoneyBoxPrintSupTellerPassPanel(transBean));
			}else if(check_mode.equals("2")){
				// 2-指纹
				transBean.getReqMCM001().setAuthmethod1("3");
				openPanel(new MoneyBoxAdditionFingerprintPanel(transBean));
			}else if(check_mode.equals("3")){
				// 3-指口令+指纹
				transBean.getReqMCM001().setAuthmethod1("3");
				openPanel(new MoneyBoxPrintSupTellerPassPanel(transBean));
			}else{
				transBean.getReqMCM001().setIntereturnmsg("查询授权方式失败：调用07659接口异常");
				MakeMonitorInfo.makeInfos(transBean.getReqMCM001());//接口调用失败，上送流水信息
				serverStop("请稍后重新授权","","系统异常，调用柜员认证方式查询失败");
			}

		}else if(identifying.equals("6")){
			transBean.getReqMCM001().setReqBefor("07660");//接口调用前流水信息记录
			
			BCK0010ResBean resBean = checkSupTelPass(transBean);
			if(resBean == null){
				transBean.getReqMCM001().setIntereturnmsg("密码授权失败：调用07660接口异常");
				MakeMonitorInfo.makeInfos(transBean.getReqMCM001());//接口调用失败，上送流水信息
				serverStop("请稍后重新操作","","系统异常");
				return;
			}
			String resCode = resBean.getHeadBean().getResCode();
			String resMsg = resBean.getHeadBean().getResMsg();
			
			//接口调用成功后记录流水信息
			transBean.getReqMCM001().setReqAfter(resCode,resMsg);
			
			logger.info("授权密码rescode："+resCode);
			logger.info("授权密码resmsg："+resMsg);
			if(!"000000".equals(resCode)){
				// 密码错误
				if(resCode.contains("700043")){
					transBean.setErrmsg(resMsg);
					transBean.getImportMap().put("backStep1", GlobalPanelFlag.MONEYBOX_PRINT_SUP_TELLER_PASS+"");
					openPanel(new MoneyBoxSupTellerPasswordFailuer(transBean));
				 		
					return;
				}else if(resCode.contains("700018")){
					transBean.setErrmsg(resMsg);
					transBean.getImportMap().put("backStep1", GlobalPanelFlag.MONEYBOX_PRINT_SUP_TELLER_PASS+"");
					openPanel(new MoneyBoxMistakePanel(transBean));
					return;
				}else{
					logger.info(resMsg);
					MakeMonitorInfo.makeInfos(transBean.getReqMCM001());//接口调用失败，上送流水信息
					serverStop("请联系大堂经理",resMsg,"");
					return;
				}
			}else{
				if ("yes".equals(transBean.getImportMap().get("customer_identification"))){
					//创建用户信息
					transBean.getReqMCM001().setReqBefor("03446");//接口调用前流水信息记录
					BCK03446ResBean bck03446ResBean = getCreateUser(transBean);
					if (bck03446ResBean == null) {
						transBean.getReqMCM001().setIntereturnmsg("创建用户信息失败：调用03446接口异常");
						MakeMonitorInfo.makeInfos(transBean.getReqMCM001());//接口调用失败，上送流水信息
						serverStop("请稍后重试","系统异常","");
						return;
					}
					String resCode1 = bck03446ResBean.getHeadBean().getResCode();
					String resMsg1 = bck03446ResBean.getHeadBean().getResMsg();
					//接口调用成功后记录流水信息
					transBean.getReqMCM001().setReqAfter(resCode1,resMsg1);
					logger.info("创建用户信息rescode："+resCode1);
					logger.info("创建用户信息resmsg："+resMsg1);
					if(!"0000000".equals(resCode1)){
						MakeMonitorInfo.makeInfos(transBean.getReqMCM001());//接口调用失败，上送流水信息
						serverStop("请重新操作业务","创建用户信息失败：" + resMsg1,"");
						return;
					}
					//保存客户号  保存核心时间
					transBean.setCustNo(bck03446ResBean.getBody().getCUST_NO().trim());
					transBean.setSvrDate(bck03446ResBean.getBody().getSVR_DATE().trim());
					transBean.setCustSvrNo(bck03446ResBean.getBody().getSVR_JRNL_NO().trim());
					transBean.getImportMap().put("customer_identification","no");
					createCustInfoJpg(transBean);
					transBean.setJijvOrPuhui("");//清空普惠标示
					openPanel(new MoneyBoxPageSelectlPanel(transBean));
					return ;
				}
			}
			//确认存单信息页面
			clearTimeText();
			openPanel(new MoneyBoxOkInputDepinfoPanel(transBean));
		}else if("8".equals(identifying)){//指纹授权成功
			if ("yes".equals(transBean.getImportMap().get("customer_identification"))){
				//创建用户信息
				transBean.getReqMCM001().setReqBefor("03446");//接口调用前流水信息记录
				BCK03446ResBean bck03446ResBean = getCreateUser(transBean);
				if (bck03446ResBean == null) {
					transBean.getReqMCM001().setIntereturnmsg("创建用户信息失败：调用03446接口异常");
					MakeMonitorInfo.makeInfos(transBean.getReqMCM001());//接口调用失败，上送流水信息
					serverStop("请稍后重新操作","","系统异常");
					return;
				}
				String resCode1 = bck03446ResBean.getHeadBean().getResCode();
				String resMsg1 = bck03446ResBean.getHeadBean().getResMsg();
				
				//接口调用成功后记录流水信息
				transBean.getReqMCM001().setReqAfter(resCode1,resMsg1);
				
				logger.info("创建用户信息rescode："+resCode1);
				logger.info("创建用户信息resmsg："+resMsg1);
				if(!"000000".equals(resCode1)){
					MakeMonitorInfo.makeInfos(transBean.getReqMCM001());//接口调用失败，上送流水信息
					serverStop("请重新操作业务","创建用户信息失败：" + resMsg1,"");
					return;
				}
				//保存客户号  保存核心时间
				transBean.setCustNo(bck03446ResBean.getBody().getCUST_NO().trim());
				transBean.setSvrDate(bck03446ResBean.getBody().getSVR_DATE().trim());
				transBean.setCustSvrNo(bck03446ResBean.getBody().getSVR_JRNL_NO().trim());
				transBean.getImportMap().put("customer_identification","no");
				createCustInfoJpg(transBean);
				transBean.setJijvOrPuhui("");//清空普惠标示
				openPanel(new MoneyBoxPageSelectlPanel(transBean));
				return ;
			}
			clearTimeText();
			openPanel(new MoneyBoxOkInputDepinfoPanel(transBean));
		}
	}
	/**
	 * 授权方式查询
	 */
	public BCK0009ResBean getsqfs(PublicCashOpenBean transBean){
		Map<String,String> map = new HashMap();
		map.put("tllerNo", transBean.getSupTellerNo());
		
		SocketClient sc = new SocketClient();
		Socket socket = null;
		InputStream is = null;
		OutputStream os = null;
		BufferedWriter bw = null;
		BufferedReader br = null;
		try {
			socket = sc.createSocket();
            //构建IO  
            is = socket.getInputStream();  
            os = socket.getOutputStream();  
            bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));  
            //向服务器端发送一条消息  
            bw.write(sc.BCK_0009(map) + "\n");  
            bw.flush();  
            //读取服务器返回的消息  
            br = new BufferedReader(new InputStreamReader(is, "UTF-8"));  
			
			String retMsg = "";
			String value = null;
			while ((value = br.readLine()) != null) {
				retMsg += value + "\n";
				if ("</Root>".equals(value)){
					break;
				}
			}
			logger.info(retMsg);
			XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			reqXs.alias("Root", BCK0009ResBean.class);
			BCK0009ResBean bck0009ResBean = (BCK0009ResBean)reqXs.fromXML(retMsg);
			logger.info(bck0009ResBean);
			logger.info("CLIENT retMsg:" + retMsg);

			return bck0009ResBean;
		} catch (UnknownHostException e) {
			logger.error(e);
			return null;
		} catch (IOException e) {
			logger.error(e);
			return null;
		}finally{
			if(socket != null){
				try {
					br.close();
					bw.close();
					is.close();
					os.close();
					socket.close();
				} catch (IOException e) {
					logger.error(e);
				}
			}
		}		
	}
	/**
	 * 校验授权密码上送
	 */
	public BCK0010ResBean checkSupTelPass(PublicCashOpenBean transBean){
		Map<String,String> map = new HashMap();
		String tllerNo = transBean.getSupTellerNo();
		map.put("tllerNo", tllerNo);
		String pass =transBean.getSupPass();
		map.put("suPass", pass);
		SocketClient sc = new SocketClient();
		Socket socket = null;
		InputStream is = null;
		OutputStream os = null;
		BufferedWriter bw = null;
		BufferedReader br = null;
		try {
			socket = sc.createSocket();
			
            //构建IO  
            is = socket.getInputStream();  
            os = socket.getOutputStream();  

            bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));  
            //向服务器端发送一条消息  

            bw.write(sc.BCK_0010(map) + "\n");  

            bw.flush();  

            //读取服务器返回的消息  
            br = new BufferedReader(new InputStreamReader(is, "UTF-8"));  
			
			String retMsg = "";
			String value = null;
			while ((value = br.readLine()) != null) {
				retMsg += value + "\n";
				if ("</Root>".equals(value)){
					break;
				}
			}
			logger.info(retMsg);
			XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			
			reqXs.alias("Root", BCK0010ResBean.class);
			reqXs.alias("Head", InReqHeadBean.class);
			
			BCK0010ResBean bck0010ResBean = (BCK0010ResBean)reqXs.fromXML(retMsg);
			logger.info(bck0010ResBean);
			logger.info("CLIENT retMsg:" + retMsg);
            return bck0010ResBean;
		}catch (UnknownHostException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		}finally{
			if(socket != null){
				try {
					br.close();
					bw.close();
					is.close();
					os.close();
					socket.close();
				} catch (IOException e) {
					logger.error(e);
				}
			}
		}	
		return null;
	}
	
	/**
	 * 创建用户信息
	 */
	public BCK03446ResBean getCreateUser(PublicCashOpenBean transBean){
		Map<String,String> map = new HashMap<String, String>();
		map.put("tel", transBean.getTel());//手机号
		map.put("CUST_NAME", transBean.getIdCardName());//用户名
		map.put("DOMICILE_PLACE", transBean.getAddress());//地址
		map.put("CUST_SEX", transBean.getSex().trim().equals("男")? "1":"2");//性别
		map.put("ID_TYPE", "10100");//证件类型
		map.put("ID_NO", transBean.getReadIdcard());//证件号码
		map.put("ISSUE_INST", transBean.getQfjg());//签发机关
		map.put("CUST_TYPE", "1");//客户类型
		map.put("BUS_STATUS", transBean.getJobs());//职业
		
		SocketClient sc = new SocketClient();
		Socket socket = null;
		InputStream is = null;
		OutputStream os = null;
		BufferedWriter bw = null;
		BufferedReader br = null;
		try {
			socket = sc.createSocket();
            //构建IO  
            is = socket.getInputStream();  
            os = socket.getOutputStream();  
            bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));  
            //向服务器端发送一条消息  
            bw.write(sc.BCK_03446(map) + "\n");  
            bw.flush();  
            //读取服务器返回的消息  
            br = new BufferedReader(new InputStreamReader(is, "UTF-8"));  
			
			String retMsg = "";
			String value = null;
			while ((value = br.readLine()) != null) {
				retMsg += value + "\n";
				if ("</Root>".equals(value)){
					break;
				}
			}
			logger.info(retMsg);
			XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			reqXs.alias("Root", BCK03446ResBean.class);
			BCK03446ResBean bck03446ResBean = (BCK03446ResBean)reqXs.fromXML(retMsg);
			logger.info(bck03446ResBean);
			logger.info("CLIENT retMsg:" + retMsg);

			return bck03446ResBean;
		} catch (UnknownHostException e) {
			logger.error(e);
			return null;
		} catch (IOException e) {
			logger.error(e);
			return null;
		}finally{
			if(socket != null){
				try {
					br.close();
					bw.close();
					is.close();
					os.close();
					socket.close();
				} catch (IOException e) {
					logger.error(e);
				}
			}
		}		
	}
	
	
	
	
	/**
	 * 创建客户信息事后监管图片
	 */
	private void createCustInfoJpg(PublicCashOpenBean transBean){
		Map<String, String> map = new HashMap<String, String>();
		String flag = transBean.getImportMap().get("agent_persion");
		if (flag != null && "yes".equals(flag)) {
			map.put("agentFlag", "1");// 1-存在代理人 2-不存在代理人
		} else {
			map.put("agentFlag", "2");
		}
		map.put("canelBillId",transBean.getCustSvrNo().trim());// 核心流水号
		map.put("canelBillDTransDate", transBean.getSvrDate().trim());// 开户日期
		map.put("accName", transBean.getCardName().trim());// 户名
		map.put("branchNo", GlobalParameter.branchNo);// 机构号
		// 授权号
		String supTellerNo = transBean.getSupTellerNo();
		//手动授权号
		String handSupTellerNo = transBean.getHandSupTellerNo();
		if (supTellerNo == null) {
			supTellerNo = "无";
		}
		map.put("supTellerNo", supTellerNo);// 授权号

		if (handSupTellerNo == null) {
			handSupTellerNo = "无";
		}
		map.put("handSupTellerNo", handSupTellerNo);//手动授权号
		map.put("macNo", GlobalParameter.machineNo);// 终端号
		map.put("idCardName", transBean.getCardName());// 本人姓名
		map.put("idCardNo", transBean.getIdCardNo());// 本人身份证号
		map.put("qfjg", transBean.getQfjg());// 签发机关
		map.put("teller", GlobalParameter.tellerNo);// 操作员
		map.put("agentIdCardName", transBean.getAgentIdCardName());// 代理人姓名
		map.put("agentIdCardNo", transBean.getAgentIdCardNo());// 代理人卡号
		map.put("agentqfjg", transBean.getAgentqfjg());// 代理人签发机关
		map.put("bill_face", transBean.getBillPath_fc());
		map.put("bill_verso", transBean.getBillPath_rc());
		map.put("customer_identification", "yes");//是否新建客户 
		map.put("tel", transBean.getTel());//手机号
		map.put("CUST_NAME", transBean.getIdCardName());//用户名
		map.put("DOMICILE_PLACE", transBean.getAddress());//地址
		map.put("CUST_SEX", transBean.getSex().trim().equals("男")? "1":"2");//性别
		map.put("ID_NO", transBean.getReadIdcard());//证件号码
		map.put("ISSUE_INST", transBean.getQfjg());//签发机关
		map.put("BUS_STATUS", transBean.getJobs());//职业
		map.put("custSvrNo", transBean.getCustSvrNo());//交易流水号
		logger.info("生成事后监督内容："+map);
		JpgUtil cg = new JpgUtil();
		String filePath1 = "";
		try {
			filePath1 = cg.createCustomerJpg(map);
		} catch (IOException e2) {
			logger.error("事后监管程序，生成事后监管图片异常！"+ e2);
			logger.info("事后监管程序，生成事后监管图片异常！");
			return;
		}
		SFTPUtil sf = new SFTPUtil();
		ChannelSftp sftp = null;
		Session sshSession = null;
    	JSch jsch = new JSch();
    	try {
    		//连接SFTP
    		sshSession = jsch.getSession(Property.FTP_USER_KH, Property.FTP_IP_KH, Integer.parseInt(Property.FTP_PORT_KH));
    		logger.info("Session created.");
    		sshSession.setPassword(Property.FTP_PWD_KH);
    		Properties sshConfig = new Properties();
    		sshConfig.put("StrictHostKeyChecking", "no");
    		sshSession.setConfig(sshConfig);
    		sshSession.connect();
//    		logger.info("Session connected.");
//    		logger.info("Opening Channel.");
    		Channel channel = sshSession.openChannel("sftp");
    		channel.connect();
    		sftp = (ChannelSftp) channel;
    		logger.info("Connected to " + Property.FTP_IP_KH + ".");
    		
    		String nowDate = DateUtil.getNowDate("yyyyMMdd");
    		// 上传的目录
    		String ftpPath = Property.FTP_SER_PATH_KH +"/"+ nowDate;
    		String[] ftpList = ftpPath.split("/");
    		String paths = "";
    		for (String path : ftpList) {
    			if(StringUtils.isNotBlank(path)){
    				paths += "/" + path;
    				try {
            			Vector content = sftp.ls(paths);
            			if (content == null) {
            				sftp.mkdir(paths);
            			}
    				} catch (Exception e) {
    					sftp.mkdir(paths);
    				}
    				sftp.cd(paths);
    			}
    		}
    		File file1 = new File(filePath1);
    		boolean result1 = sf.upload(ftpPath, filePath1, sftp);
    		if(!result1){
    			logger.error("新建信息事后监管上传图片失败-->" + file1.getName());
    		}else{
    			logger.info("新建信息事后监管上传图片成功-->" + file1.getName());
    			//删除图片
//    			file.delete();
    			deleteFile(file1);
    		}
		} catch (Exception e) {
			logger.error("事后监督上传图片，进入目录失败");
		}finally{
			if (sftp!= null && sftp.isConnected()){
				sftp.disconnect();
			}
			if (sshSession!= null && sshSession.isConnected()){
				sshSession.disconnect();
			}
		}
	}
	/**
	 * 成功上传事后监督图片后删除本地图片的方法
	 * */
	private void deleteFile(File file) {
		if (file.isFile()) {// 如果是文件
			// logger.info(f);
			System.gc();// 垃圾回收,主要是为了释放上传时被占用的资源图片
			boolean result = file.delete();
			if (!result) {// 判断是否全部删除
				file.delete();
			}
			logger.info("删除成功" + file);
		}
	}
}
