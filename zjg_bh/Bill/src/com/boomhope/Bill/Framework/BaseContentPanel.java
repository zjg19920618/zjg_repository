package com.boomhope.Bill.Framework;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.boomhope.Bill.Driver.KeypadDriver;
import com.boomhope.Bill.Framework.ChildBusiness.AccCardAndCashOpenPanel;
import com.boomhope.Bill.Framework.ChildBusiness.HuiHTransferPanel;
import com.boomhope.Bill.Framework.ChildBusiness.ManagerOtherPanel;
import com.boomhope.Bill.Framework.ChildBusiness.OtherPanel;
import com.boomhope.Bill.Public.GlobalParameter;
import com.boomhope.Bill.TransService.AccCancel.AccCacncelAction.NoAccAction;
import com.boomhope.Bill.TransService.AccCancel.AccountCancel.PublicUse.AccCancelDempMethodPanel;
import com.boomhope.Bill.TransService.AccCancel.Bean.PublicAccCancelBean;
import com.boomhope.Bill.TransService.AccTransfer.Action.TransferAction;
import com.boomhope.Bill.TransService.AccTransfer.Bean.PublicAccTransferBean;
import com.boomhope.Bill.TransService.AccTransfer.View.ServicePage.TransferChooseBusiness;
import com.boomhope.Bill.TransService.AccTransfer.View.ServicePage.TransferCancel.TransferCancelInputBankCardPanel;
import com.boomhope.Bill.TransService.BillPrint.TransPrintCheckTransPathPanel;
import com.boomhope.Bill.TransService.BillPrint.Bean.BillPrintBean;
import com.boomhope.Bill.Util.CheckMacStatuesUtil;
import com.boomhope.Bill.Util.Property;
import com.boomhope.Bill.monitor.bean.ReqMCM002;
import com.boomhope.Bill.monitor.bean.ReqMCM004;
import com.boomhope.Bill.monitor.sendInterface.SendInterfaceMsg;
import com.boomhope.Bill.peripheral.action.FingerPrint;
import com.boomhope.Bill.peripheral.action.ICBank;
import com.boomhope.Bill.peripheral.action.IdCard;
import com.boomhope.Bill.peripheral.bean.FingerPrintCheckStatusBean;
import com.boomhope.Bill.peripheral.bean.ICBankCheckStatusBean;
import com.boomhope.Bill.peripheral.bean.IdCartCheckStatusBean;

/***
 * 基础内容容器
 * 
 * @author shaopeng
 * 
 */
public class BaseContentPanel extends JLabel {

	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(BaseContentPanel.class);

	public JLabel managerButten = null;
	Map<String, String> primap = new HashMap<String, String>();
	java.util.Timer timer1 = null;
	public String user;
	public String paw;
	public static BaseContentPanel contentPanel;
	final BaseTransPanelNew base = new BaseTransPanelNew();
	
	//监控平台检测数据
	private int statuesCheckTime = Property.channel_monitor_check_time;//设备状态自检周期默认时间（s）
	public boolean monitor_on_off=true;//监控平台外设检测状态开关
	public CheckMacStatuesUtil cmsUtil;//检测外设组件

	// logo图标
	JLabel logoBt;
	// 存单开户
	JLabel accountBt;
	// 存单销户
	JLabel destroyBt;
	// 子账户打印
	JLabel printBt;
	// 汇划业务
	JLabel huiHuaBt;
	//汇划转账
	JLabel huiBt;
	//汇划撤销
	JLabel huiCancelBt;
	//存单销户2
	JLabel destroyBt2;
	//其它
	JLabel otherBt;

	/***
	 * 更改背景
	 */
	public void paintComponent(Graphics g) {
		g.drawImage(new ImageIcon("pic/newPic/firstPage.jpg").getImage(), 0, 0,
				GlobalParameter.WINDOW_WIDTH, GlobalParameter.WINDOW_HEIGHT,
				this);
	}

	/***
	 * 界面初始化
	 */
	public BaseContentPanel() {
		contentPanel = this;
		this.setSize(GlobalParameter.WINDOW_WIDTH,
				GlobalParameter.WINDOW_HEIGHT);
		this.setLocation(0, 0);
		this.setOpaque(false);

		/* 初始化Panel */
		initPanel();
		new Thread(
			new Runnable() {
				public void run() {
					
					timer();
				}
			}
		).start();
	}

	/***
	 * 初始化Panel
	 */
	public void initPanel() {
		/* 底部信息 */
		final String bottomInfo = "网点:" + GlobalParameter.branchNo + "    机器号:"
				+ GlobalParameter.machineNo;
		final JLabel bottomLabel = new JLabel();
		bottomLabel.setHorizontalAlignment(JLabel.CENTER);
		SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
		long timemillis = System.currentTimeMillis();
		bottomLabel.setText(bottomInfo + "     "
				+ df.format(new Date(timemillis)));

		Timer timeAction = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				long timemillis = System.currentTimeMillis();
				// 转换日期显示格式
				SimpleDateFormat df = new SimpleDateFormat(
						"yyyy年MM月dd日 HH:mm:ss");
				bottomLabel.setText(bottomInfo + "     "
						+ df.format(new Date(timemillis)));
			}
		});
		timeAction.start();
		bottomLabel.setBounds(230, 35, GlobalParameter.WINDOW_WIDTH, 20);
		bottomLabel.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		bottomLabel.setForeground(Color.white);
		this.add(bottomLabel);
		// logo图标
		JLabel logoImg = new JLabel(new ImageIcon("pic/newPic/logo.png"));
		logoImg.setBounds(5, 4, 240, 80);
		this.add(logoImg);
		// 电话图标图标
		JLabel photoImg = new JLabel(new ImageIcon("pic/newPic/photo.png"));
		photoImg.setBounds(GlobalParameter.WINDOW_WIDTH - 240, 60, 80, 80);
		this.add(photoImg);
		// 电话号码图标
		JLabel photoNO = new JLabel("0315-96368");
		photoNO.setBounds(GlobalParameter.WINDOW_WIDTH - 160, 60, 220, 80);
		photoNO.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		photoNO.setForeground(Color.YELLOW);
		this.add(photoNO);
		// 服务提示语
		JLabel lb = new JLabel("来自市民，服务市民");
		lb.setSize(270, 70);
		lb.setLocation(90, GlobalParameter.WINDOW_HEIGHT - 70);
		lb.setFont(new Font("楷体", Font.PLAIN, 25));
		lb.setVisible(true);
		lb.setForeground(Color.white);
		this.add(lb);
		
//		PublicServerStop a = new PublicServerStop("或者游戏虽然回来", " 来自市民，服务市民来自市民，服务市民来自市民，服务市民来自市民，服务市民来自市民，服务市民来自市民，服务市民来自市民，服务市民来自市民，服务市民", " 来自市民，服务市民");
//		this.add(a);
		addFirstImage();
		this.addManagerButten();
	}

	/**
	 * 添加首页图片
	 */
	private void addFirstImage() {
		logger.info("加载业务图片（花瓣图片）");
		// logo图标
		logoBt = new JLabel();
		logoBt.setBounds(GlobalParameter.WINDOW_WIDTH / 2 - 96,
				GlobalParameter.WINDOW_HEIGHT / 2 - 96, 192, 192);
		logoBt.setIcon(new ImageIcon("pic/newPic/logoImg.png"));
		this.add(logoBt);
		
		// 存单开户
		accountBt = new JLabel();
		accountBt.setBounds(GlobalParameter.WINDOW_WIDTH / 2 - 267,
				GlobalParameter.WINDOW_HEIGHT / 2 - 267, 267, 267);
		accountBt.setIcon(new ImageIcon("pic/newPic/account2.png"));
		this.add(accountBt);
		accountBt.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				logger.info("点击存单开户的按钮");
				accountClick();
			}
		});	
		
		//汇划转账
		huiBt = new JLabel();
		huiBt.setBounds(GlobalParameter.WINDOW_WIDTH / 2 - 267,
				GlobalParameter.WINDOW_HEIGHT / 2 - 267, 267, 267);
		huiBt.setIcon(new ImageIcon("pic/newPic/huiTrans.png"));
		this.add(huiBt);
		huiBt.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击汇划转账的按钮");
				huiClick();
			}
		});
		
		// 存单销户
		destroyBt = new JLabel();
		destroyBt.setBounds(GlobalParameter.WINDOW_WIDTH / 2,
				GlobalParameter.WINDOW_HEIGHT / 2, 267, 267);
		destroyBt.setIcon(new ImageIcon("pic/newPic/positDestroy.png"));
		this.add(destroyBt);
		destroyBt.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				logger.info("点击存单销户的按钮");
				destroyClick();
			}
		});
		//其它
		otherBt = new JLabel();
		otherBt.setBounds(GlobalParameter.WINDOW_WIDTH / 2,
				GlobalParameter.WINDOW_HEIGHT / 2, 267, 267);
		otherBt.setIcon(new ImageIcon("pic/newPic/otherTrans.png"));
		this.add(otherBt);
		otherBt.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击其它业务按钮");
				otherClick();
			}
		});
		
		// 子账户打印
		printBt = new JLabel();
		printBt.setBounds(GlobalParameter.WINDOW_WIDTH / 2 - 267,
				GlobalParameter.WINDOW_HEIGHT / 2, 267, 267);
		printBt.setIcon(new ImageIcon("pic/newPic/printPic.png"));
		this.add(printBt);
		printBt.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				logger.info("点击现金开户按钮");
				cashClick();
			}
		});
		
		//存单销户2
		destroyBt2 = new JLabel();
		destroyBt2.setBounds(GlobalParameter.WINDOW_WIDTH / 2 - 267,
				GlobalParameter.WINDOW_HEIGHT / 2, 267, 267);
		destroyBt2.setIcon(new ImageIcon("pic/newPic/positDestroy2.png"));
		this.add(destroyBt2);
		destroyBt2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击存单销户的按钮");
				destroyClick();
			}
		});
		
		
		// 汇划业务
		huiHuaBt = new JLabel();
		huiHuaBt.setBounds(GlobalParameter.WINDOW_WIDTH / 2,
				GlobalParameter.WINDOW_HEIGHT / 2 - 267, 267, 267);
		huiHuaBt.setIcon(new ImageIcon("pic/newPic/huiHua.png"));
		this.add(huiHuaBt);
		huiHuaBt.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				logger.info("点击汇划业务按钮");
				huiHuaClick();
			}
		});
		
		//汇划撤销
		huiCancelBt = new JLabel();
		huiCancelBt.setBounds(GlobalParameter.WINDOW_WIDTH / 2,
				GlobalParameter.WINDOW_HEIGHT / 2 - 267, 267, 267);
		huiCancelBt.setIcon(new ImageIcon("pic/newPic/huiCancel.png"));
		this.add(huiCancelBt);
		huiCancelBt.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击汇划撤销按钮");
				huiCancelClick();
			}
		});
		initShow();
	}

	//根据打印机状态选择显示不同的业务
	public void initShow(){
		logger.info("进入根据打印机状态显示首页业务图片的方法");
		if("0".equals(GlobalParameter.printStatus)){
			logger.info("有打印机");
			GlobalParameter.TRANS_NOS=10;
			accountBt.setVisible(true);
			huiHuaBt.setVisible(true);
			printBt.setVisible(false);
			destroyBt.setVisible(false);
			huiBt.setVisible(false);
			huiCancelBt.setVisible(false);
			otherBt.setVisible(true);
			destroyBt2.setVisible(true);
		}else{
			logger.info("无打印机");
			GlobalParameter.TRANS_NOS=8;
			accountBt.setVisible(false);
			destroyBt.setVisible(false);
			printBt.setVisible(false);
			huiHuaBt.setVisible(false);
			huiBt.setVisible(true);
			otherBt.setVisible(true);
			destroyBt2.setVisible(true);
			huiCancelBt.setVisible(true);
		}
	}
	

	/*-------按钮被点击--------*/
	// 存单开户
	private void accountClick() {
		if(monitor_on_off==false){
			base.openMistakeDialog("正在检测设备状态，请稍后操作。");
			return;
		}
		logger.info("进入存单开户的方法");
		GlobalParameter.ACC_NO="99";
		hideFirstImg();
		this.repaint();
		this.add(new AccCardAndCashOpenPanel());
	}

	// 存单销户
	private void destroyClick() {
		if(monitor_on_off==false){
			base.openMistakeDialog("正在检测设备状态，请稍后操作。");
			return;
		}
		logger.info("进入存单销户的方法");
		hideFirstImg();
		this.repaint();
		GlobalParameter.ACC_NO="1";
		BaseTransPanelNew.accCancelBean = new PublicAccCancelBean();
		BaseTransPanelNew.noAccAction = new NoAccAction();
		this.add(new AccCancelDempMethodPanel());
	}

	// 子账户打印
	private void cashClick() {
		if(monitor_on_off==false){
			base.openMistakeDialog("正在检测设备状态，请稍后操作。");
			return;
		}
		logger.info("进入子账户打印的方法");
		hideFirstImg();
		this.repaint();
		GlobalParameter.ACC_NO="6";
		BaseTransPanelNew.billPrintBean = new BillPrintBean();
		this.add(new TransPrintCheckTransPathPanel(BaseTransPanelNew.billPrintBean));
		
	}

	// 汇划业务
	private void huiHuaClick() {
		if(monitor_on_off==false){
			base.openMistakeDialog("正在检测设备状态，请稍后操作。");
			return;
		}
		logger.info("进入汇划业务的方法");
		GlobalParameter.ACC_NO="98";
		hideFirstImg();
		this.repaint();
		this.add(new HuiHTransferPanel());
	}
	
	//汇划转账
	private void huiClick(){
		if(monitor_on_off==false){
			base.openMistakeDialog("正在检测设备状态，请稍后操作。");
			return;
		}
		logger.info("进入汇划转账的方法");
		hideFirstImg();
		this.repaint();
		GlobalParameter.ACC_NO="3";
		BaseTransPanelNew.transferAction = new TransferAction();
		BaseTransPanelNew.transferBean = new PublicAccTransferBean();
		this.add(new TransferChooseBusiness(BaseTransPanelNew.transferBean));
	}
	
	//汇划撤销
	private void huiCancelClick(){
		if(monitor_on_off==false){
			base.openMistakeDialog("正在检测设备状态，请稍后操作。");
			return;
		}
		if("4".equals(GlobalParameter.ACC_NO)){
			return;
		}
		logger.info("进入汇划撤销的方法");
		hideFirstImg();
		this.repaint();
		GlobalParameter.ACC_NO="4";
		BaseTransPanelNew.transferAction = new TransferAction();
		BaseTransPanelNew.transferBean = new PublicAccTransferBean();
		Map<String,String> map = new HashMap<String, String>();
		this.add(new TransferCancelInputBankCardPanel(map,BaseTransPanelNew.transferBean));
	}
	
	// 其它业务
	private void otherClick() {
		logger.info("进入其它业务的方法");
		if(monitor_on_off==false){
			base.openMistakeDialog("正在检测设备状态，请稍后操作。");
			return;
		}
		GlobalParameter.ACC_NO="100";
		hideFirstImg();
		this.repaint();
		this.add(new OtherPanel());
	}
	/**
	 * 隐藏首页按钮
	 */
	private void hideFirstImg() {
		logger.info("隐藏首页按钮图片的方法");
		logoBt.setVisible(false);
		destroyBt.setVisible(false);
		printBt.setVisible(false);
		huiHuaBt.setVisible(false);
		huiBt.setVisible(false);
		otherBt.setVisible(false);
		destroyBt2.setVisible(false);
		huiCancelBt.setVisible(false);
		accountBt.setVisible(false);

	}


	/**
	 * 添加管理员按键
	 */
	public void addManagerButten() {
		managerButten = new JLabel(new ImageIcon("pic/newPic/manager.png"));
		managerButten.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("static-access")
			@Override
			public void mouseReleased(MouseEvent arg0) {
				logger.info("点击管理员按键");
				if(monitor_on_off==false){
					base.openMistakeDialog("正在检测设备状态，请稍后操作。");
					return;
				}
				if("0".equals(GlobalParameter.ACC_NO) || "20".equals(GlobalParameter.ACC_NO)){
					return;
				}
				if(!GlobalParameter.OFF_ON){
					logger.info("开关为关闭状态，不能进行页面跳转");
					base.openMistakeDialog("抱歉，当前业务未完成，不能进行其他操作。");
					return;
				}
				if("".equals(GlobalParameter.ACC_NO) || GlobalParameter.ACC_NO==null){
					manageTrans();
				}else{
					base.openConfirmDialog("是否确认退出当前业务。是：退出当前业务，否：继续当前操作。");
					base.confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent e) {
							base.confirmDialog.disposeDialog();
							manageTrans();
						}
					});
					base.confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent e) {
							base.confirmDialog.disposeDialog();
							return;
						}
					});
				}

			}
		});
		managerButten.setSize(70, 70);
		managerButten.setLocation(20, GlobalParameter.WINDOW_HEIGHT - 70);
		managerButten.setVisible(true);
		this.add(managerButten);
	}


	public JLabel getManagerButton() {
		return managerButten;
	}

	/**
	 * 进入管理员界面
	 */
	public void manageTrans() {
		logger.info("进入管理员界面的方法");
		this.removeAll();
		initPanel();
		hideFirstImg();
		this.repaint();
		getManagerButton().setVisible(false);
		GlobalParameter.ACC_NO="97";
		this.add(new ManagerOtherPanel());
	}


	/**
	 * 定时上送设备状态
	 */
	public void timer() {
		logger.info("进入定时上送设备状态方法");
		//先获取设备检测周期时间
		getCheckStatuesTimes();
		monitor_on_off=false;
		cmsUtil = new CheckMacStatuesUtil();
		timer1 = new java.util.Timer();
		timer1.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				if(GlobalParameter.ACC_NO!=null && !"".equals(GlobalParameter.ACC_NO.trim())){
					logger.info("正在办理业务，业务编号："+GlobalParameter.ACC_NO);
					monitor_on_off=true;
					return;
				}
				logger.info("设置状态上送开始......");
				macSendStatues();
				logger.info("设置状态上送结束......");
			}
		}, 1000, statuesCheckTime*1000);
	}
	
	/**
	 * 查询状态检测周期时间
	 */
	public void getCheckStatuesTimes(){
		ReqMCM004 reqMCM004Bean = new ReqMCM004();//状态自检周期查询接口【MCM004】
		reqMCM004Bean.setVersion(Property.channel_monitor_req_version);
		reqMCM004Bean.setType("MCM004");
		reqMCM004Bean.setDeviceno(GlobalParameter.machineNo);
		reqMCM004Bean.setDevicetype("11");
		String dateTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		reqMCM004Bean.setSenddate(dateTime.substring(0,8));
		reqMCM004Bean.setSendtime(dateTime.substring(8));
		
		try {
			String reqInfos = JSONObject.fromObject(reqMCM004Bean).toString();
			String length = String.valueOf(reqInfos.getBytes().length);
			String jsonStr = StringUtils.leftPad(length, 16, " ")+reqInfos;
			logger.debug("请求报文："+jsonStr);
			
			Map resultMap = SendInterfaceMsg.sendMsg(jsonStr);
			String retMsg = (String)resultMap.get("resMsg");
			logger.info("返回报文："+retMsg);
			
			if(resultMap.get("resCode")==null || !"000000".equals(resultMap.get("resCode"))){
				logger.error("获取状态检测周期时间失败");
			}else{
				logger.info("获取状态检测周期时间成功:"+retMsg);
				Map<String,String> resMCM004Map = (Map<String,String>)JSONObject.fromObject(retMsg.substring(16));
				
				//获取检测周期时间
				statuesCheckTime = Integer.valueOf(resMCM004Map.get("check_time"));
				logger.info("状态检测周期时间为："+statuesCheckTime+"s");
			}
			
		} catch (Exception e) {
			logger.error("获取设备检测周期时间失败："+e);
		}
	}
	
	//上送设备状态
	public void macSendStatues(){
		try {
			logger.info("进入设备外设状态上报方法");
			ReqMCM002 reqMCM002Bean = new ReqMCM002();//设备状态上送请求报文
			checkMacStatus(reqMCM002Bean);
			monitor_on_off=true;
			if(base.mistakeDialog!=null && base.mistakeDialog.isVisible()){
				base.closeDialog(base.mistakeDialog);
			}
			
			String reqInfos = JSONObject.fromObject(reqMCM002Bean).toString();
			String length = String.valueOf(reqInfos.getBytes().length);
			String jsonStr = StringUtils.leftPad(length, 16, " ")+reqInfos;
			logger.debug("请求报文："+jsonStr);
			
			Map resultMap = SendInterfaceMsg.sendMsg(jsonStr);
			String retMsg = (String)resultMap.get("resMsg");
			logger.info("返回报文："+retMsg);
			
			if(resultMap.get("resCode")==null || !"000000".equals(resultMap.get("resCode"))){
				logger.error("上送检测状态失败");
			}else{
				logger.info("上送外设检测状态成功:"+retMsg);
			}
		} catch (Exception e) {
			logger.error("上送设备状态失败："+e);
		}
		
	}

	/**
	 * 检查设备状态
	 */
	public void checkMacStatus(ReqMCM002 reqMCM002Bean) {
		logger.info("进入设备状态检查方法");
		try {
			reqMCM002Bean.setVersion(Property.channel_monitor_req_version);
			reqMCM002Bean.setType("MCM002");
			reqMCM002Bean.setDeviceno(GlobalParameter.machineNo);
			reqMCM002Bean.setDevicetype("11");
			String dateTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			reqMCM002Bean.setSenddate(dateTime.substring(0,8));
			reqMCM002Bean.setSendtime(dateTime.substring(8));
			
			reqMCM002Bean.setServicesta("ok");
			
			//检测所有设备状态
			// 条码打印架
			cmsUtil.checkBillState();
			if(cmsUtil.getPrimap().get("PRI0007")!=null && "2".equals(cmsUtil.getPrimap().get("PRI0007"))){
				logger.error(cmsUtil.getPrimap().get("PRI0007failMsg"));
				reqMCM002Bean.setReceipt("error|ok");
			}else if(cmsUtil.getPrimap().get("PRI0007")!=null && "0".equals(cmsUtil.getPrimap().get("PRI0007"))){
				logger.error(cmsUtil.getPrimap().get("PRI0007failMsg"));
				reqMCM002Bean.setReceipt("ok|ok");
			}else{
				logger.info(cmsUtil.getPrimap().get("PRI0007failMsg"));
				reqMCM002Bean.setReceipt("unkown|ok");
			}
			// 存单打印机
			cmsUtil.checkPrintMachineState();
			if(cmsUtil.getPrimap().get("PRI0006")!=null && "2".equals(cmsUtil.getPrimap().get("PRI0006"))){
				logger.error(cmsUtil.getPrimap().get("PRI0006failMsg"));
				reqMCM002Bean.setJounal("error|ok");
			}else if(cmsUtil.getPrimap().get("PRI0006")!=null && "0".equals(cmsUtil.getPrimap().get("PRI0006"))){
				logger.error(cmsUtil.getPrimap().get("PRI0006failMsg"));
				reqMCM002Bean.setJounal("ok|ok");
			}else{
				logger.info(cmsUtil.getPrimap().get("PRI0006failMsg"));
				reqMCM002Bean.setJounal("unkown|ok");
			}	
			//身份证阅读器
			cmsUtil.checkIdCardState();
			if(cmsUtil.getPrimap().get("PRI0003")!=null && "2".equals(cmsUtil.getPrimap().get("PRI0003"))){
				logger.error(cmsUtil.getPrimap().get("PRI0003failMsg"));
				reqMCM002Bean.setIdcard("error|ok");
			}else if(cmsUtil.getPrimap().get("PRI0003")!=null && "0".equals(cmsUtil.getPrimap().get("PRI0003"))){
				logger.error(cmsUtil.getPrimap().get("PRI0003failMsg"));
				reqMCM002Bean.setIdcard("ok|ok");
			}else{
				logger.info(cmsUtil.getPrimap().get("PRI0003failMsg"));
				reqMCM002Bean.setIdcard("unkown|ok");
			}
			//银行卡读卡器
			cmsUtil.checkICBankState();
			if(cmsUtil.getPrimap().get("PRI0004")!=null && "2".equals(cmsUtil.getPrimap().get("PRI0004"))){
				logger.error(cmsUtil.getPrimap().get("PRI0004failMsg"));
				reqMCM002Bean.setReader("error|0|ok");
			}else if(cmsUtil.getPrimap().get("PRI0004")!=null && "0".equals(cmsUtil.getPrimap().get("PRI0004"))){
				logger.error(cmsUtil.getPrimap().get("PRI0004failMsg"));
				reqMCM002Bean.setReader("ok|0|ok");
			}else{
				logger.info(cmsUtil.getPrimap().get("PRI0004failMsg"));
				reqMCM002Bean.setReader("unkown|0|ok");
			}
			//密码键盘
			cmsUtil.checkKeypadState();
			if(cmsUtil.getPrimap().get("PRI0001")!=null && "2".equals(cmsUtil.getPrimap().get("PRI0001"))){
				logger.error(cmsUtil.getPrimap().get("PRI0001failMsg"));
				reqMCM002Bean.setPinpad("error|ok");
			}else if(cmsUtil.getPrimap().get("PRI0001")!=null && "0".equals(cmsUtil.getPrimap().get("PRI0001"))){
				logger.error(cmsUtil.getPrimap().get("PRI0001failMsg"));
				reqMCM002Bean.setPinpad("ok|ok");
			}else{
				logger.info(cmsUtil.getPrimap().get("PRI0001failMsg"));
				reqMCM002Bean.setPinpad("unkown|ok");
			}
			//指纹仪
			cmsUtil.checkFingerPrintState();
			if(cmsUtil.getPrimap().get("PRI0002")!=null && "2".equals(cmsUtil.getPrimap().get("PRI0002"))){
				logger.error(cmsUtil.getPrimap().get("PRI0002failMsg"));
				reqMCM002Bean.setFingerprint("error");
			}else if(cmsUtil.getPrimap().get("PRI0002")!=null && "0".equals(cmsUtil.getPrimap().get("PRI0002"))){
				logger.error(cmsUtil.getPrimap().get("PRI0002failMsg"));
				reqMCM002Bean.setFingerprint("ok");
			}else{
				logger.info(cmsUtil.getPrimap().get("PRI0002failMsg"));
				reqMCM002Bean.setFingerprint("unkown");
			}
			//二维码扫描仪
			cmsUtil.checkDimensionState();
			if(cmsUtil.getPrimap().get("PRI0008")!=null && "2".equals(cmsUtil.getPrimap().get("PRI0008"))){
				logger.error(cmsUtil.getPrimap().get("PRI0008failMsg"));
				reqMCM002Bean.setBarcodereader("error");
			}else if(cmsUtil.getPrimap().get("PRI0008")!=null && "0".equals(cmsUtil.getPrimap().get("PRI0008"))){
				logger.error(cmsUtil.getPrimap().get("PRI0008failMsg"));
				reqMCM002Bean.setBarcodereader("ok");
			}else{
				logger.info(cmsUtil.getPrimap().get("PRI0008failMsg"));
				reqMCM002Bean.setBarcodereader("unkown");
			}
			
			
			// 检查银行卡读卡器状态
//			IdCartCheckStatusBean idcard = IdCard.checkStatus("1");
//			String status_idcard = idcard.getStatus();
//			if (!"0".equals(status_idcard)) {
//				logger.error("银行卡状态：" + status_idcard);
//				reqMCM002Bean.setReader("error|0|ok");
//			}else{
//				reqMCM002Bean.setReader("ok|0|ok");
//			}
//			// 检查身份证状读卡器状态
//			ICBankCheckStatusBean bank = ICBank.checkStatus("2");
//			String status_bank = bank.getStatus();
//			if (!"0".equals(status_bank)) {
//				logger.error("身份证状态：" + status_idcard);
//				reqMCM002Bean.setIdcard("error|ok");
//			}else{
//				reqMCM002Bean.setIdcard("ok|ok");
//			}
//			// 检查密码键盘状态
//			KeypadDriver driver = new KeypadDriver();
//			Map<String, String> driverMap = driver.checkKeypadStatus();
//			String resCode = driverMap.get("ResCode");
//			if (!"S".equals(resCode)) {
//				logger.error("密码键盘：" + status_idcard);
//				reqMCM002Bean.setPinpad("error|ok");
//			}else{
//				reqMCM002Bean.setPinpad("ok|ok");
//			}
//			// 指纹状态
//			FingerPrint print = new FingerPrint();
//			FingerPrintCheckStatusBean bean = print.checkStatus("4");
//			String print_status = bean.getStatus();
//			if (!"0".equals(print_status)) {
//				logger.error("指纹识别：" + print_status);
//				reqMCM002Bean.setFingerprint("error");
//			}else{
//				reqMCM002Bean.setFingerprint("ok");
//			}
		} catch (Exception e) {
			logger.error("检测设备状态失败");
			logger.error(e);
		}
	}

	/***
	 * 关闭外设状态监控
	 */
	public void stopTerminor() {
		logger.info("进入关闭外设状态监控方法");
		timer1.cancel();
	}

	/**
	 * 密码键盘明文输入银行卡号，调用关闭密码键盘
	 */
	public String closePwd() throws Exception {
		logger.info("进入关闭密码键盘方法");
		if (KeypadDriver.socket != null
				&& !KeypadDriver.socket.isInputShutdown()) {
			KeypadDriver.socket.shutdownInput();
		}
		if (KeypadDriver.socket != null
				&& !KeypadDriver.socket.isOutputShutdown()) {
			KeypadDriver.socket.shutdownOutput();
		}
		Thread.sleep(100);
		String res = new KeypadDriver().closePwd("6");
		return res;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPaw() {
		return paw;
	}

	public void setPaw(String paw) {
		this.paw = paw;
	}
}
