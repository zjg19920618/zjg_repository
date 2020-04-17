package com.boomhope.Bill.Framework;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TimerTask;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.boomhope.Bill.Driver.KeypadDriver;
import com.boomhope.Bill.Public.GlobalParameter;
import com.boomhope.Bill.Socket.SocketClient;
import com.boomhope.Bill.TransService.Update.UpdateFile;
import com.boomhope.Bill.TransService.Update.Util.PropertiesUtil;
import com.boomhope.Bill.Util.DateTool;
import com.boomhope.Bill.Util.DateUtil;
import com.boomhope.Bill.Util.Property;
import com.boomhope.Bill.Util.SFTPUtil;
import com.boomhope.Bill.peripheral.action.BillPrint;
import com.boomhope.Bill.peripheral.action.FingerPrint;
import com.boomhope.Bill.peripheral.action.ICBank;
import com.boomhope.Bill.peripheral.action.IdCard;
import com.boomhope.Bill.peripheral.action.PaperCutter;
import com.boomhope.Bill.peripheral.action.PrintMachine;
import com.boomhope.Bill.peripheral.bean.BillCheckStateBean;
import com.boomhope.Bill.peripheral.bean.FingerPrintCheckStatusBean;
import com.boomhope.Bill.peripheral.bean.ICBankCheckStatusBean;
import com.boomhope.Bill.peripheral.bean.ICBankQuitBean;
import com.boomhope.Bill.peripheral.bean.IdCartCheckStatusBean;
import com.boomhope.Bill.peripheral.bean.PaperCutterCheckStatusBean;
import com.boomhope.Bill.peripheral.bean.PrintMachineCheckStateBean;
import com.boomhope.tms.message.XStreamNameCoder;
import com.boomhope.tms.message.in.tms.Tms0001ResBean;
import com.boomhope.tms.message.in.tms.Tms0002ResBean;
import com.boomhope.tms.peripheral.action.Dimension;
import com.boomhope.tms.peripheral.bean.DimensionCheckStatusBean;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * 登录界面
 * 
 * @author zy
 *
 */
public class BaseLoginFrame extends JFrame {
	
	Map<String,String> primap = new HashMap<String,String>();
	
	private static final long serialVersionUID = 1L;
	
	static Logger logger = Logger.getLogger(BaseLoginFrame.class);
	
	public static ActiveXComponent activeX;//ActiveX控件对象  
	
	String macNo;
	
    public static Dispatch dispath;//MS级别的调度对象  
    
    static boolean isupload = true;//上传图片判断

	public static ActiveXComponent activeX1;

	public static Dispatch dispath1;
    
    static  
    {  
        //创建ActiveX控件对象  
        //可以使用CLSID,也可以使用ProgID  
        activeX = new ActiveXComponent("CLSID:E74D9FB8-3B27-4DE6-8C7A-73982F81FD0D");  
        //获得调度对象  
        dispath = activeX.getObject();  
        activeX1 = new ActiveXComponent("SOFTKEYBOARD.softKeyBoardCtrl.1");
	    dispath1 = activeX1.getObject();
    } 
   
	     
	Timer checkTimer = null;

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// 打开窗体
					BaseLoginFrame frame = new BaseLoginFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public BaseLoginFrame() {
		
		JLabel lblNewLabel_1 = new JLabel("正在加载中，请稍等。。。");
		lblNewLabel_1.setBounds(556, 532, 381, 43);
		lblNewLabel_1.setFont(new Font("微软雅黑", Font.BOLD, 25));
		getContentPane().add(lblNewLabel_1);
		
		/* 加载凭证动画 */
		JLabel billImage = new JLabel();   
		billImage.setIcon(new ImageIcon("pic/346546.gif"));
		billImage.setIconTextGap(6);
		billImage.setBounds(585, 280, 340, 250);
		getContentPane().add(billImage);
		
		
		/* 白色图片 */
		JLabel billImage1 = new JLabel();   
		billImage1.setIcon(new ImageIcon("pic/65768.png"));
		billImage1.setIconTextGap(6);
		billImage1.setBounds(349, 134, 740, 550);
		getContentPane().add(billImage1);
		
		// 加载日志
		PropertyConfigurator.configure( "config\\log4j.properties" );
		// 加载配置
		Property.initProperty();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(0, 0, 1440, 900);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 窗体关闭时的操作 退出程序
		this.setUndecorated(true);
		getContentPane().setLayout(null);
		
		ImageIcon img = new ImageIcon("pic/87634.png");//这是背景图片     
		img.setImage(img.getImage().getScaledInstance(1440, 900, Image.SCALE_DEFAULT));
		
//		UtilImages image = new UtilImages("pic/87634.png");
		
		JLabel lblNewLabel = new JLabel(img);
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setBounds(0, 0, 1440, 900);
		lblNewLabel.setFont(new Font("微软雅黑", Font.PLAIN, 20));	
		getContentPane().add(lblNewLabel);
		
		final JFrame loginFrame=this;
		/* 登录 */
		checkTimer = new Timer(1000, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {  
            	checkTimer.stop();
            	updateFile(loginFrame);
            	
            }      
        });            
		checkTimer.start();
		// 删除历史数据
		delF();
		//删除本地保存7天的事后申请书图片
		delete7Day();
	}
	
	/**
	 * 登录
	 */
	public void login(){
		// 获取本地IP
		String ip = null;
		try {
			ip = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			logger.error(e);
		}
		Map<String,String> map = new HashMap<String,String>();
		map.put("ip", ip);
		// 调用登录接口
		Tms0001ResBean resBean = getTms0001(map);
		if(null == resBean){
			fail("网络连接中断");
			return;
		}
		String resCode = resBean.getHeadBean().getResCode();
		String resMsg = resBean.getHeadBean().getResMsg();
		if(!"000000".equals(resCode)){
			fail(resMsg);
			return;
		}
		macNo = resBean.getBodyBean().getMachineNo();
		String branNo = resBean.getBodyBean().getBranchNo();
		String workkeys = resBean.getBodyBean().getKeys();
		String tellerNo = resBean.getBodyBean().getTellerNo();
		String macFacNum = resBean.getBodyBean().getMacFacNum();
		String unitName = resBean.getBodyBean().getUnitName();
		logger.info("获取的工作密钥："+workkeys);
		
		
		// 检查设备状态
//		boolean flag = checkMacStatus();
//		if(flag == false){
//			macSendStatus("2");
//			fail(primap.get("failMsg"));
//			return;
//		}
		
//		boolean checkBillState = checkBillState();
//		if(checkBillState == false){
//			macSendStatus("2");
//			fail("凭条打印机异常!");
//			return;
//		}
		boolean checkPrintMachineState = checkPrintMachineState();
		if(checkPrintMachineState == false){
			macSendStatus("2");
			fail(primap.get("failMsg"));
			return;
		}
//		boolean checkPaperCutterState = checkPaperCutterState();
//		if(checkPaperCutterState == false){
//			macSendStatus("2");
//			fail(primap.get("failMsg"));
//			return;
//		}
		boolean checkIdCardState = checkIdCardState();
		if(checkIdCardState == false){
			macSendStatus("2");
			fail(primap.get("failMsg"));
			return;
		}
		boolean checkICBankState = checkICBankState();
		if(checkICBankState == false){
			macSendStatus("2");
			fail(primap.get("failMsg"));
			return;
		}
		boolean checkKeypadState = checkKeypadState();
		if(checkKeypadState == false){
			macSendStatus("2");
			fail(primap.get("failMsg"));
			return;
		}
		boolean checkFingerPrintState = checkFingerPrintState();
		if(checkFingerPrintState == false){
			macSendStatus("2");
			fail(primap.get("failMsg"));
			return;
		}
		
		//装载工作密钥
		Map<String, String> keymap = new HashMap<String, String>();
		keymap.put("MainKeyPos", "0");
		keymap.put("WorkKeyPos", "0");
		keymap.put("EncText", workkeys);
		keymap.put("DecType", "2");
		try {
			KeypadDriver key = new KeypadDriver();
			Map<String , String> keyresult = key.loadWorkKey(keymap);
			String code = keyresult.get("ResCode");
			if(!code.equals("S")){
				fail("获取工作密钥失败!");
				return;
			}
		} catch (UnknownHostException e) {
			logger.error(e);
			fail("加载工作密钥失败!");
			return;
		} catch (IOException e) {
			logger.error(e);
			fail("加载工作密钥失败!");
			return;
		}
		// 登录成功
		success(macNo,branNo,tellerNo,macFacNum,unitName);
		
		//上传日志
		logTimer();
		
		new Thread(){
			public void run(){
				macSendStatus("1");
			}
		}.start();	
	}
	
	/**
	 * 成功
	 */
	public void success(String macNo,String branNo,String tellerNo,String macFacNum,String unitName){
		GlobalParameter.branchNo = branNo;
		GlobalParameter.machineNo = macNo;
		GlobalParameter.tellerNo = tellerNo;
		GlobalParameter.macFacNum = macFacNum;
		GlobalParameter.unitName = unitName;
		MainFrame m = new MainFrame();
		m.setVisible(true);
		dispose();
	}
	
	/**
	 * 失败
	 */
	public void fail(String errmsg){
		BaseSytemFailFrame m = new BaseSytemFailFrame(errmsg);
		m.setVisible(true);
		dispose();
	}
	
	/**
	 * 调用登录接口
	 */
	public Tms0001ResBean getTms0001(Map<String,String> map){
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
            bw.write(sc.Tms0001(map) + "\n");  
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
			System.out.println(retMsg);
			XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			reqXs.alias("Root", Tms0001ResBean.class);
			Tms0001ResBean bck0002ResBean = (Tms0001ResBean)reqXs.fromXML(retMsg);
			System.out.println(bck0002ResBean);
			System.out.println("CLIENT retMsg:" + retMsg);
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
	 * 检查凭条打印机状态
	 */
	public boolean checkBillState(){
		//检查凭条打印机状态
		boolean flag = true;
		BillCheckStateBean billPrintCheckStatus = null;
		try {
			billPrintCheckStatus = BillPrint.billPrintCheckStatus();
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
			logger.error("凭条打印机连接异常");
			primap.put("PRI0007", "2");
			primap.put("failMsg", "凭条打印机连接异常");
		}
		
		String id2 = billPrintCheckStatus.getId();
		String billPrinStatus = billPrintCheckStatus.getStatus();
		if(billPrinStatus ==null){
			billPrinStatus = "获取失败";
		}
		if(!"0".equals(id2)){
			flag = false;
			logger.error("凭条打印机错误状态"+billPrinStatus);
			primap.put("PRI0007", "2");
			primap.put("failMsg", "凭条打印机错误状态"+billPrinStatus);
		}else{
			if(!"0".equals(billPrinStatus)){
				flag = false;
				logger.error("凭条打印机异常状态"+billPrinStatus);
				primap.put("PRI0007", "2");
				primap.put("failMsg", "凭条打印机异常状态"+billPrinStatus);
			}
		}
		return flag;	
	}
	
	/**
	 * 检查存单打印机状态
	 */
	public boolean checkPrintMachineState(){
		//检查存单打印机状态
		boolean flag = true;
		PrintMachine printMachine = new PrintMachine();
		PrintMachineCheckStateBean printMachineCheckStatus =null;
		try {
			printMachineCheckStatus = printMachine.printMachineCheckStatus();
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
			logger.error("存单打印机连接异常");
			primap.put("PRI0006", "2");
			primap.put("failMsg", "存单打印机连接异常");
		}
		
		String id = printMachineCheckStatus.getId();
		String printStatus = printMachineCheckStatus.getStatus();
		String status1 = printMachineCheckStatus.getStatus1();//8纸将尽
		String status2 = printMachineCheckStatus.getStatus2();//7卡纸 
		String status3 = printMachineCheckStatus.getStatus3();//6切刀错误 
		logger.info("返回状态值"+id);
		logger.info("返回状态值对应信息"+printStatus);
		
		if("0".equals(id)){
			GlobalParameter.printStatus = "0";
			if("7".equals(status2)){
				flag = false;
				logger.error("存单打印机状态7卡纸");
				primap.put("PRI0006", "2");
				primap.put("failMsg", "存单打印机状态7卡纸");
			}
			if("6".equals(status3)){
				flag = false;
				logger.error("存单打印机状态6切刀错误");
				primap.put("PRI0006", "2");
				primap.put("failMsg", "存单打印机状态6切刀错误");
			}
		}else if("4".equals(id)){
			logger.info("打印机未安装!");
			GlobalParameter.printStatus = "4";
		}else {
			flag = false;
			logger.error("存单打印机未知异常");
			primap.put("PRI0006", "2");
			primap.put("failMsg", "存单打印机未知异常");
		}	
		return flag;
	}
	
	/**
	 * 检查裁纸器状态
	 */
	public boolean checkPaperCutterState(){
		//检查裁纸器状态
		boolean flag = true;
		PaperCutterCheckStatusBean paperCutterCheckStatusBean = null;
		try {
			paperCutterCheckStatusBean = PaperCutter.checkStatus("5");
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
			logger.error("裁纸器状态连接异常");
			primap.put("PRI0005", "2");
			primap.put("failMsg", "裁纸器状态连接异常");
			
		}
		String paperStatus = paperCutterCheckStatusBean.getStatus();
		if(paperStatus == null){
			paperStatus = "获取失败";
		}
		if(!"0".equals(paperStatus)){
			flag = false;
			logger.error("裁纸器状态"+paperStatus);
			primap.put("PRI0005", "2");
			primap.put("failMsg", "裁纸器状态"+paperStatus);
		}
		return flag;
	}
	
	/**
	 * 检查身份证阅读器状态
	 */
	public boolean checkIdCardState(){
		// 检查身份证阅读器状态
		boolean flag = true;
		IdCartCheckStatusBean idcard = null;
		try {
			idcard = IdCard.checkStatus("1");
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
			logger.error("身份证阅读器连接异常");
			primap.put("PRI0003", "2");
			primap.put("failMsg", "身份证阅读器连接异常");
		}
		String status_idcard = idcard.getStatus();
		if(status_idcard == null){
			status_idcard = "获取失败";
		}
		if(!"0".equals(status_idcard)){
			flag = false;
			logger.error("身份证阅读器状态"+status_idcard);
			primap.put("PRI0003", "2");
			primap.put("failMsg", "身份证阅读器状态"+status_idcard);
		}
		return flag;
	}
	
	/**
	 * 检查银行卡读卡器状态
	 */
	public boolean checkICBankState(){
		// 检查银行卡状读卡器状态
		boolean flag = true;
		ICBankCheckStatusBean bank = null;
		try {
			bank = ICBank.checkStatus("2");
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
			logger.error("银行卡读卡器连接异常");
			primap.put("PRI0004", "2");
			primap.put("failMsg", "银行卡读卡器连接异常");
		}
		String status_bank = bank.getStatus();
		if(status_bank == null){
			status_bank = "获取失败";
		}
		if("0".equals(status_bank)){
			String touchStatus = bank.getTouchStatus();//接触式
			if("1".equals(touchStatus)){
				//卡座正常且卡座当前有卡
				ICBankQuitBean icBankQuit = null;
				try {
					icBankQuit = ICBank.ICBankQuit("1", "30");
				} catch (Exception e) {
					e.printStackTrace();
					flag = false;
					logger.error("银行卡读卡器退卡异常");
					primap.put("PRI0004", "2");
					primap.put("failMsg", "银行卡读卡器退卡异常");
				}
				String status = icBankQuit.getStatus();
				if("0".equals(status)){
					//成功
				}else{
					flag = false;
					logger.error("银行卡读卡器状态"+status_bank);
					primap.put("PRI0004", "2");
					primap.put("failMsg", "银行卡读卡器状态"+status_bank);
				}
				//执行退卡
			}else if("2".equals(touchStatus)){
				//卡座正常且卡座当前无卡
				//正常运行
			}else{
				flag = false;
				logger.error("银行卡读卡器状态"+status_bank);
				primap.put("PRI0004", "2");
				primap.put("failMsg", "银行卡读卡器状态"+status_bank);
			}
		}else{
			flag = false;
			logger.error("银行卡读卡器状态"+status_bank);
			primap.put("PRI0004", "2");
			primap.put("failMsg", "银行卡读卡器状态"+status_bank);
		}		
		return flag;
	}
	
	/**
	 * 检查密码键盘状态
	 */
	public boolean checkKeypadState(){
		// 检查密码键盘状态
		boolean flag = true;
		KeypadDriver driver = new KeypadDriver();
		Map<String, String> driverMap = null;
		try {
			driverMap = driver.checkKeypadStatus();
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
			logger.error("密码键盘连接异常");
			primap.put("PRI0001", "2");
			primap.put("failMsg", "密码键盘连接异常");
		}
		String resCode = driverMap.get("ResCode");
		if(resCode == null){
			resCode = "获取失败";
		}
		if(!"S".equals(resCode)){
			flag = false;
			logger.error("密码键盘状态"+resCode);
			primap.put("PRI0001", "2");
			primap.put("failMsg", "密码键盘状态"+resCode);
		}	
		return flag;
	}
	
	/**
	 * 检查指纹状态
	 */
	public boolean checkFingerPrintState(){
		// 检查指纹状态
		boolean flag = true;
		FingerPrint print = new FingerPrint();
		FingerPrintCheckStatusBean bean = null;
		try {
			bean = print.checkStatus("4");
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
			logger.error("指纹仪连接异常");
			primap.put("PRI0002", "2");
			primap.put("failMsg", "指纹仪连接异常");
		}
		String print_status = bean.getStatus();
		if(print_status == null){
			print_status = "获取失败";
		}
		if(!"0".equals(print_status)){
			flag = false;
			logger.error("指纹仪"+print_status);
			primap.put("PRI0002", "2");
			primap.put("failMsg", "指纹仪"+print_status);
		}	
		return flag;
	}	
	
	/**
	 * 检查二维码扫描仪状态
	 */
	public boolean checkDimensionState(){
		// 检查二维码扫描仪状态
		boolean flag = true;
		DimensionCheckStatusBean dcr = null;
		try {
			dcr = Dimension.checkStatus("2");
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
			logger.error("二维码扫描仪连接异常");
			primap.put("PRI0008", "2");
			primap.put("failMsg", "二维码扫描仪连接异常");
			
		}
		String status = dcr.getStatus();
		if(status == null){
			status = "获取失败";
		}
		if(!"0".equals(status)){
			flag = false;
			logger.error("二维码扫描仪状态"+status);
			primap.put("PRI0008", "2");
			primap.put("failMsg", "二维码扫描仪状态"+status);
		}
		return flag;
	}
	
//	/**
//	 * 设备状态
//	 */
//	public boolean  checkMacStatus(){
//		boolean flag = true;
//		try {
			
//			//存单打印机状态检测
//			PrintMachine printMachine = new PrintMachine();
// 			PrintMachineCheckStateBean printMachineCheckStatus = printMachine.printMachineCheckStatus();
// 			String id = printMachineCheckStatus.getId();
// 			String printStatus = printMachineCheckStatus.getStatus();
// 			String status1 = printMachineCheckStatus.getStatus1();//8纸将尽
// 			String status2 = printMachineCheckStatus.getStatus2();//7卡纸 
// 			String status3 = printMachineCheckStatus.getStatus3();//6切刀错误 
// 			logger.info(printMachineCheckStatus);
// 			System.out.println("返回状态值"+id);
// 			System.out.println("返回状态值对应信息"+printStatus);
// 			
// 			if("0".equals(id)){
// 				GlobalParameter.printStatus = "0";
// 				if("7".equals(status2)){
// 					logger.error("存单打印机状态：7卡纸");
// 	 				flag = false;
// 					primap.put("PRI0006", "2");
// 					primap.put("failMsg", "存单打印机状态：7卡纸");
// 				}
// 				if("6".equals(status3)){
// 					logger.error("存单打印机状态：6切刀错误");
// 					flag = false;
// 					primap.put("PRI0006", "2");
// 					primap.put("failMsg", "存单打印机状态：6切刀错误");
// 				}
// 			}else if("4".equals(id)){
// 				logger.info("打印机未安装!");
// 				GlobalParameter.printStatus = "4";
// 			}else {
// 				logger.error("存单打印机错误：未知异常");
// 				flag = false;
//				primap.put("PRI0006", "2");
//				primap.put("failMsg", "存单打印机错误：未知异常");
// 			}
			// 检查裁纸器状态
//			PaperCutterCheckStatusBean paperCutterCheckStatusBean = PaperCutter.checkStatus("5");
//			String paperStatus = paperCutterCheckStatusBean.getStatus();
//			if(!"0".equals(paperStatus)){
//				logger.error("裁纸器状态："+paperStatus);
//				flag = false;
//				primap.put("PRI0005", "2");
//				primap.put("failMsg", "裁纸器状态："+paperStatus);
//			}
			// 检查身份证读卡器状态
//			IdCartCheckStatusBean idcard = IdCard.checkStatus("1");
//			String status_idcard = idcard.getStatus();
//			if(!"0".equals(status_idcard)){
//				logger.error("身份证阅读器状态："+status_idcard);
//				flag = false;
//				primap.put("PRI0003", "2");
//				primap.put("failMsg", "身份证阅读器状态："+status_idcard);
//			}
			// 检查银行卡状读卡器状态
//			ICBankCheckStatusBean bank = ICBank.checkStatus("2");
//			String status_bank = bank.getStatus();
//			if("0".equals(status_bank)){
//				String touchStatus = bank.getTouchStatus();//接触式
//				if("1".equals(touchStatus)){
//					//卡座正常且卡座当前有卡
//					ICBankQuitBean icBankQuit = ICBank.ICBankQuit("1", "30");
//					String status = icBankQuit.getStatus();
//					if("0".equals(status)){
//						//成功
//					}else{
//						logger.error("银行卡读卡器状态："+status_bank);
//						flag = false;
//						primap.put("PRI0004", "2");
//						primap.put("failMsg", "银行卡读卡器状态："+status_bank);
//					}
//					//执行退卡
//				}else if("2".equals(touchStatus)){
//					//卡座正常且卡座当前无卡
//					//正常运行
//				}else{
//					logger.error("银行卡读卡器状态："+status_bank);
//					flag = false;
//					primap.put("PRI0004", "2");
//					primap.put("failMsg", "银行卡读卡器状态："+status_bank);
//				}
//			}else{
//				logger.error("银行卡读卡器状态："+status_bank);
//				flag = false;
//				primap.put("PRI0004", "2");
//				primap.put("failMsg", "银行卡读卡器状态："+status_bank);
//			}
//			// 检查密码键盘状态
//			KeypadDriver driver = new KeypadDriver();
//			Map<String,String> driverMap =  driver.checkKeypadStatus();
//			String resCode = driverMap.get("ResCode");
//			if(!"S".equals(resCode)){
//				logger.error("密码键盘："+resCode);
//				flag = false;
//				primap.put("PRI0001", "2");
//				primap.put("failMsg", "密码键盘："+resCode);
//			}
			// 指纹状态
//			FingerPrint print = new FingerPrint();
//			FingerPrintCheckStatusBean bean = print.checkStatus("4");
//			String print_status = bean.getStatus();
//			if(!"0".equals(print_status)){
//				logger.error("指纹识别："+print_status);
//				flag = false;
//				primap.put("PRI0002", "2");
//				primap.put("failMsg", "指纹识别："+print_status);
//			}
//			DimensionCheckStatusBean dcr=Dimension.checkStatus("2");
//			if(!"0".equals(dcr.getStatus())){
//				logger.error("二维码扫描仪："+dcr.getStatus());
//				flag = false;
//				primap.put("PRI0008", "2");
//				primap.put("failMsg", "二维码扫描仪："+dcr.getStatus());
//			}
//		} catch (Exception e) {
//			logger.error(e);
//			flag = false;
//			primap.put("failMsg", "外设未知异常");
//		}
//		return flag;
//	}
	
	
	 /**
     * 设备外设状态上报
     */
	public void  macSendStatus(String status){
		primap.put("macNo", macNo);
		primap.put("status", status);
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
            bw.write(sc.Tms0002(primap) + "\n");  
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
			System.out.println(retMsg);
			XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			reqXs.alias("Root", Tms0002ResBean.class);
			Tms0002ResBean bck0002ResBean = (Tms0002ResBean)reqXs.fromXML(retMsg);
			System.out.println(bck0002ResBean);
			System.out.println("CLIENT retMsg:" + retMsg);
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
	}
	
	/**定时上传日志文件到服务器*/
	private void logTimer() {
		java.util.Timer timer2 = new java.util.Timer();
		timer2.scheduleAtFixedRate(new TimerTask() {  
            public void run() {
            	logger.info("开始上传log日志......");
            	SFTPUtil sf = new SFTPUtil();
            	ChannelSftp sftp = null;
            	Session sshSession = null;
            	JSch jsch = new JSch();
            	try {
            		//连接SFTP
            		sshSession = jsch.getSession(Property.FTP_USER, Property.FTP_IP, Integer.parseInt(Property.FTP_PORT));
            		logger.debug("Session created.");
            		sshSession.setPassword(Property.FTP_PWD);
            		Properties sshConfig = new Properties();
            		sshConfig.put("StrictHostKeyChecking", "no");
            		sshSession.setConfig(sshConfig);
            		sshSession.connect();
            		logger.debug("Session connected.");
            		logger.debug("Opening Channel.");
            		Channel channel = sshSession.openChannel("sftp");
            		channel.connect();
            		sftp = (ChannelSftp) channel;
            		logger.debug("Connected to " + Property.FTP_IP + ".");
            		//获取本地文件
            		File file = new File(Property.FTP_LOG_LOCAL_PATH);
            		if(file.isDirectory()){
            			File[] listFiles = file.listFiles();
            			//先进入服务器指定目录
            			String nowDate = DateUtil.getNowDate("yyyyMMdd");
            			String ftpPath = Property.FTP_LOG_PATH + GlobalParameter.machineNo+"/"+ nowDate;
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
                		logger.info("进入指定目录-->"+paths);
            			//获得该文件夹下面以hdj_开头，以.log结尾的文件，来上传
            			for (File file2 : listFiles) {
    						if(file2.isFile()){
    							if(file2.getName().startsWith("hdj_") && file2.getName().endsWith(".log")){
    	                    		// 上传的目录
                            		boolean result = sf.upload(ftpPath, file2.getPath(), sftp);
                            		if(!result){
                            			logger.error("log日志文件上传失败--->"+file2.getName());
                            		}else{
                            			logger.info("log日志文件上传成功--->"+file2.getName());
                            		}
    							}
    						}
    					}
            			
            		}
				} catch (Exception e) {
					logger.error("log日志文件上传程序异常");
				}finally{
					if (sftp!= null && sftp.isConnected()){
        				sftp.disconnect();
        			}
        			if (sshSession!= null && sshSession.isConnected()){
        				sshSession.disconnect();
        			}
				}
            	logger.info("结束上传log日志......");
            }
        }, 1000*60,1000*60*30 );  
	}		
	
	/**
	 * 上送事后图片、上传历史数据、清理日志、临时文件、接口返回文件
	 */
	public void delF() {
		// 更新本地所有图片
		Thread thread = new Thread("") {
			public void run() {
				
				//上传未成功的事后监督图片
				uploadSh();
				//清除历史数据
				clearFile(Property.FTP_LOCAL_PATH);//清理接口返回文件
				clearFile(Property.BH_TMP);//清理临时文件
				clearFile(Property.FTP_LOG_LOCAL_PATH);	//清理日志		
			}
		};
		thread.start();
	}
	
	/**
	 * 上传未成功的事后监督图片
	 * @param path(d:\\bill\\)
	 * @return
	 */
	public void uploadSh(){

		SFTPUtil sf = new SFTPUtil();
		ChannelSftp sftp = null;
		Session sshSession = null;
    	JSch jsch = new JSch();
    	
    	try {
    		//连接SFTP
    		sshSession = jsch.getSession(Property.FTP_USER, Property.FTP_IP, Integer.parseInt(Property.FTP_PORT));
    		logger.info("Session created.");
    		sshSession.setPassword(Property.FTP_PWD);
    		Properties sshConfig = new Properties();
    		sshConfig.put("StrictHostKeyChecking", "no");
    		sshSession.setConfig(sshConfig);
    		sshSession.connect();
    		logger.info("Session connected.");
    		logger.info("Opening Channel.");
    		Channel channel = sshSession.openChannel("sftp");
    		channel.connect();
    		sftp = (ChannelSftp) channel;
    		logger.info("Connected to " + Property.FTP_IP + ".");
    		
    		//上传未成功的事后监督图片
    		List<String> filename = getFileName(Property.FTP_LOCAL_PATH.replace("\\", "\\\\"));
    		if(filename == null || filename.size() == 0){
    			logger.info("没有未成功的事后监督图片");
    			return;
    		}
    		//创建上传目录
    		String nowDate = DateUtil.getNowDate("yyyyMMdd");
    		String ftpPath1 = Property.FTP_SER_PATH + nowDate;
    		String[] ftpList1 = ftpPath1.split("/");
    		String paths1 = "";
    		for (String path : ftpList1) {
    			if(StringUtils.isNotBlank(path)){
    				paths1 += "/" + path;
    				try {
            			Vector content = sftp.ls(paths1);
            			if (content == null) {
            				sftp.mkdir(paths1);
            			}
    				} catch (Exception e) {
    					sftp.mkdir(paths1);
    				}
    				sftp.cd(paths1);
    			}
    		}
    		for (String files : filename) {
    			// 上传图片
    			boolean result3 = sf.upload(ftpPath1, files, sftp);
    			if(!result3){
    				logger.error("未传成功事后监督图片上传失败-->" + files);
    			}else{
    				logger.info("未传成功事后监督图片上传成功-->" + files);
    			}
    		}
		} catch (Exception e) {
			logger.error("sftp上传文件失败，进入目录失败"+e);
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
	 * 查找指定文件
	 * @param path(d:\\bill\\)
	 * @return
	 */
	public List<String> getFileName(String path){
		File file = new File(path);
		File[] files = file.listFiles();
		List<String> str = new ArrayList<String>(); 
		
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()){
					String foldername = files[i].getAbsolutePath();
					File files2 = new File(foldername);
					File[] listFiles = files2.listFiles();
					
					for (int j = 0; j < listFiles.length; j++) {
						String filename = listFiles[j].getAbsolutePath();
						if (filename.endsWith(".jpg") && !"me_id_card.jpg".equals(filename) && !"me_agent_id_card.jpg".equals(filename)){
							str.add(filename);
						}
					}
				}else{
					String filename2 = files[i].getAbsolutePath();
					if (filename2.endsWith(".jpg") && !"me_id_card.jpg".equals(filename2) && !"me_agent_id_card.jpg".equals(filename2)){
						str.add(filename2);
					}
				}
			}
			return str;
		}	
	
	/**
	 * 开始升级升级程序
	 */
	public void updateFile(final JFrame loginFrame) {
		new Thread("启动升级程序的升级"){
			@Override
			public void run() {
				try {
					logger.debug("更新前线程睡1s等待升级程序的关闭");
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					logger.error(e);
				}
				logger.debug("启动升级程序的升级");
				UpdateFile updateFile = new UpdateFile();
				updateFile.uploadFile();
				if(updateFile.isUpdate()){
					loginFrame.dispose();
					openUpdateTest();
				}else{
					login();
				}
			}
		}.start();
	}
	/**
	 * 打开升级程序进行升级检测
	 */
	private void openUpdateTest(){
		try {
			Runtime rn = Runtime.getRuntime();
			String updatePath=PropertiesUtil.getPropertyByKey("updatePath");
			logger.debug("打开程序"+updatePath);
			rn.exec(updatePath);
		} catch (Exception e) {
			logger.error("打开程序失败"+e);
		}
	}
	/**
	 * 定时删除本地文件(7天)
	 */
	public static void delete7Day(){
		Property.initProperty();
		File file = new File(Property.bill_bd_path);
		File[] files=file.listFiles();
		if(!file.exists()|| !file.isDirectory()){
			 logger.info("此文件不存在"+Property.bill_bd_path);
			 return;
		}else{
		  for(int i=0; i<files.length;i++){
		    	File f=files[i];
			   if(!f.isDirectory()){
				   logger.info("此文件不是文件夹："+f.getName());				
			    }else{
			         SimpleDateFormat form = new SimpleDateFormat("yyyyMMdd");
					 Date d;
					try {
						d = form.parse(f.getName());//文件名转换为日期+7天
						Calendar ca = Calendar.getInstance();
						ca.setTime(d);
						ca.add(Calendar.DATE, 7);
						d = ca.getTime();
						if(d.before(new Date())){//日期+7天在当前日期之前则删除
							logger.info(f.getName()+"文件保留7天  时间已到："+form.format(new Date()));
						    deleteDirectory(f.getPath());
						}else{
							logger.info(f.getName()+"文件保留7天  时间未到："+form.format(new Date()));
						}
					} catch (ParseException e) {	
						logger.error("删除本地文件失败："+e);
					}
			    }
		    }
		}
	}

	/**
	 * 删除目录里的子文件
	 * 
	 * @param sPath
	 * @return
	 */
	private static boolean deleteDirectory(String sPath) {
		File dirFile = new File(sPath);
		boolean flag = true;
		// 删除文件夹下所有文件
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 删除子文件
			if (files[i].isFile()) {
				flag = deleteFile(files[i].getAbsolutePath());
				if (!flag)
					break;
			} else {
				// 删除子目录
				flag = deleteDirectory(files[i].getAbsolutePath());
				if (!flag)
					break;
			}
		}
		logger.info("删除文件夹：" + dirFile.getName());
		return dirFile.delete();
	}

	/**
	 * 清理资源，删除文件
	 * @param filePaths
	 * @return
	 */
	private static boolean deleteFile(String filePaths) {
		File filel = new File(filePaths);
		if (filel.isFile()) {// 如果是文件
			System.gc();// 垃圾回收,主要是为了释放上传时被占用的资源图片
			boolean result = filel.delete();
			if (!result) {// 判断是否全部删除
				filel.delete();
			}
			logger.info("删除成功" + filel);
		}
		return true;
	}
	
	/**
	 * 清理历史数据
	 */
	public static void clearFile(String path){
		
		File getfile = new File(path);
		File[] listFiles = getfile.listFiles();
		
		logger.info("初始路径："+getfile.getName());
		
		if("bill".equals(getfile.getName())){
			
			logger.info("清理接口返回文件");
			for (File file : listFiles) {
				if(file.isDirectory()){
					
					logger.info("删除文件夹："+file.getAbsolutePath());
					String delDir = delDir(file.getAbsolutePath());
					logger.info(delDir);
					
				}else{
					
					logger.info("删除文件:"+file.getAbsolutePath());
					String delFile = delFile(file.getAbsolutePath());
					logger.info(delFile);
				}
			}
		}else if("bh_tmp".equals(getfile.getName())){
			
			logger.info("清理临时图片");
			for (File file : listFiles) {
				if(file.isDirectory()){
					Date fileDate = null;
					Date nowDate = null;
					try {
						SimpleDateFormat spf = new SimpleDateFormat("yyyyMMdd");
						fileDate = spf.parse(file.getName());
						nowDate = spf.parse(spf.format(new Date()));
						logger.info("文件夹日期:"+file.getName());
						logger.info("当前日期:"+spf.format(new Date()));
						int days = DateTool.differentsDays(fileDate, nowDate);
						logger.info("相差天数:"+days);
						if(days > 7){//日期+7天在当前日期之前则删除
							logger.info("7天以外，删除");
							String delFile = delDir(file.getAbsolutePath());
							logger.info(delFile);
						}else{
							logger.info("7天以内 ，不删除");
						}
					} catch (Throwable e) {
						logger.error(file.getName()+"日期处理失败:"+e.getMessage());
						String delDir = delDir(file.getAbsolutePath());
						logger.info(delDir);
					}
					
				}else{
					
					logger.info("删除文件:"+file.getAbsolutePath());
					String delFile = delFile(file.getAbsolutePath());
					logger.info(delFile);
				}
			}
		}else if("log4j".equals(getfile.getName())){
			
			logger.info("清理日志");
			for (File file : listFiles) {
				if(file.isDirectory()){
					
					logger.info("删除文件夹:"+file.getAbsolutePath());
					String delDir = delDir(file.getAbsolutePath());
					logger.info(delDir);	
					
				}else{
					
					Date fileDate = null;
					Date nowDate = null;
					try {
						String subDate = file.getName().substring(file.getName().lastIndexOf(".")+1, file.getName().length()).replace("-", "");
						SimpleDateFormat spf = new SimpleDateFormat("yyyyMMdd");
						fileDate = spf.parse(subDate);
						nowDate = spf.parse(spf.format(new Date()));
						logger.info("文件日期:"+subDate);
						logger.info("当前日期:"+spf.format(new Date()));
						int days = DateTool.differentsDays(fileDate, nowDate);
						logger.info("相差天数:"+days);
						if(days > 7){//日期+7天在当前日期之前则删除
							logger.info("7天以外，删除");
							String delFile = delFile(file.getAbsolutePath());
							logger.info(delFile);
						}else{
							logger.info("7天以内 ，不删除");
						}
					} catch (Throwable e) {
						logger.error(file.getName()+"日期处理失败:"+e.getMessage());
//						String delFile = delFile(file.getAbsolutePath());
//						logger.info(delFile);
					}
				}
			}
		}else{
			
			logger.info("清理其他文件");
			for (File file : listFiles) {
				if(file.isDirectory()){
					
					logger.info("删除文件夹："+file.getAbsolutePath());
					String delDir = delDir(file.getAbsolutePath());
					logger.info(delDir);
					
				}else{
					
					logger.info("删除文件:"+file.getAbsolutePath());
					String delFile = delFile(file.getAbsolutePath());
					logger.info(delFile);
				}
			}
			
		}
	}
    
    /**
     * 删除文件夹
     */
    public static String delDir(String dirPath){
    	
    	File tagFile = new File(dirPath);
    	if(tagFile.exists()){
    		try {
				String cmd = "cmd /c rd "+dirPath+" /s/q";
				Runtime rt = Runtime.getRuntime(); //获取运行时系统
				Process process= rt.exec(cmd);
				InputStream inputStream = process.getInputStream();
				InputStreamReader in = new InputStreamReader(inputStream,"gbk");
				BufferedReader buf = new BufferedReader(in);
				String line = null;
				while (( line = buf.readLine())!=null) {
					logger.info("删除文件夹："+line);
				}
			} catch (Throwable e) {
				return e.getMessage();
			}
    		return "删除成功";
    	}else{
    		return "目录不存在";
    	}
    }
    
    /**
     * 删除文件
     */
    public static String delFile(String filePath){
    	
    	File tagFile = new File(filePath);
    	if(tagFile.exists()){
    		try {
    			String cmd = "cmd /c del "+filePath;
    			Runtime rt = Runtime.getRuntime(); //获取运行时系统
				Process process= rt.exec(cmd);
				InputStream inputStream = process.getInputStream();
				InputStreamReader in = new InputStreamReader(inputStream,"gbk");
				BufferedReader buf = new BufferedReader(in);
				String line = null;
				while (( line = buf.readLine())!=null) {
					logger.info("删除文件："+line);
				}
    			
			} catch (Throwable e) {
				return e.getMessage();
			}
    		return "删除成功";
    	}else{
    		return "文件不存在";
    				
    	}
    }
}
