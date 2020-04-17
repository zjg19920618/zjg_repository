package com.boomhope.Bill.TransService.AccOpen.View.ServicePage.Account.ProInformationInputPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import sun.audio.AudioStream;

import com.boomhope.Bill.Comm.CDJPrintBill;
import com.boomhope.Bill.Comm.CDJPrintPt;
import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Public.GlobalParameter;
import com.boomhope.Bill.PublicControl.OutputBankCardPanel;
import com.boomhope.Bill.TransService.AccOpen.Bean.AccPublicBean;
import com.boomhope.Bill.TransService.AccOpen.Interface.IntefaceSendMsg;
import com.boomhope.Bill.TransService.AccOpen.View.ServicePage.Account.AccChooseBusiness;
import com.boomhope.Bill.TransService.AccOpen.controller.AccountTradeCodeAction;
import com.boomhope.Bill.Util.AccZYDRulePanel;
import com.boomhope.Bill.Util.DateUtil;
import com.boomhope.Bill.Util.FileUtil;
import com.boomhope.Bill.Util.JpgUtil_CardKH;
import com.boomhope.Bill.Util.Property;
import com.boomhope.Bill.Util.SFTPUtil;
import com.boomhope.Bill.monitor.sendInterface.MakeMonitorInfo;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

/***
 * 积享存存入成功页面
 * @author gyw
 *
 */
public class AccJXCSuccessPanel extends BaseTransPanelNew {
	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(AccJXCSuccessPanel.class);

	final int voiceSecond = 500;
	FileInputStream fileau = null;
	AudioStream as = null;
	
	private JTable table_2;
	private JTable table_3;
	JLabel confirm;
	AccPublicBean transBean = null;
	private boolean on_off=true;
	
	private AccZYDRulePanel zydrp = null;//增益豆规则
	
	private JLabel billImage;//动画图片
	private JLabel promptLabel;//存入成功提示
	private JLabel accImage;//图片
	private JLabel lblNewLabel_1;//提示语
	private JPanel panel;//表格面板
	private JPanel panel1;//第二行表格面板
	private JLabel svrLabel;//流水号显示
	private JLabel zydrComfirm;//增益豆规则显示确认按钮
	private JLabel tdTotalCount;//增益豆赠送数量
	private JLabel tdinfoCount;//唐豆明细
	private JLabel rlueLabel;//规则提示
	private JLabel rlueButton;//规则按钮
	private JLabel labelTitle;//扣回规则标题
	private int y=350;
	
	public AccJXCSuccessPanel(final AccPublicBean transBean,final Map<String,Object> map){
		logger.info("进入积享存成功页面");
		
		this.transBean = transBean;
		/* 显示时间倒计时 */
		this.showTimeText(delaySecondShortTime);
		delayTimer = new Timer(delaySecondShortTime * 1000, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {  
            	logger.info("积享存成功页面倒计时结束 ");
            	/* 倒计时结束退出交易 */ 
            	clearTimeText();// 清空倒计时
            	serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！","","");
            }      
        });            
		delayTimer.start(); 
		
		/* 倒计时打开语音 */
		if("0".equals(transBean.getAgreementPrint())){
			voiceTimer = new Timer(voiceSecond, new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					stopTimer(voiceTimer);
					closeVoice();
					excuteVoice("voice/successAgreement.wav");
					
				}
			});
			voiceTimer.start();
		}            
		
		/* 加载凭证动画 */
		billImage = new JLabel("");   
		billImage.setIcon(new ImageIcon("pic/accomplish.png"));
		billImage.setIconTextGap(6);
		billImage.setBounds(320, 62, 50, 50);
		this.showJpanel.add(billImage);
		
		/* 提示信息 */
		promptLabel = new JLabel("存入成功");
		promptLabel.setHorizontalAlignment(JLabel.CENTER);
		promptLabel.setFont(new Font("微软雅黑", Font.PLAIN, 40));
		promptLabel.setForeground(Color.decode("#412174"));
		promptLabel.setBounds(0, 60, 1009, 49);
		this.showJpanel.add(promptLabel);
		//图片
		accImage = new JLabel(); 
		accImage.setIcon(new ImageIcon("pic/ico_wxts.png"));
		accImage.setIconTextGap(6);
		accImage.setBounds(158, 133, 36, 36);
		this.showJpanel.add(accImage);
		
		lblNewLabel_1 = new JLabel("温馨提示：如需纸质存款协议说明，您可到回单机上自助进行打印。");
		lblNewLabel_1.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		lblNewLabel_1.setHorizontalAlignment(0);
		lblNewLabel_1.setBounds(0, 129, 1009, 50);
		this.showJpanel.add(lblNewLabel_1);
		
		
		String[] columnNames = { "关联子账户","存入日", "起息日", "到期日", "存期", "利率", "柜员号" };
		String[] columnNames1 = { "子账号", "户名", "金额", "产品名称", "关联产品名称"};
		Object[][] data = { { transBean.getSubRelaAcctNo(),transBean.getCreateTime(), transBean.getCreateTime(), transBean.getEndTime(), transBean.getJxRyjDepTem()+"天", transBean.getRate()+"%", GlobalParameter.tellerNo}};
		Object[][] data1 = { { transBean.getSubAcctNo(), transBean.getIdCardName(), transBean.getMoney(), transBean.getProductName().substring(0, transBean.getProductName().length()-5), transBean.getSubProductName().substring(0, transBean.getSubProductName().length()-5)}};
		panel = new JPanel();
		panel.setBounds(65, 300, 883, 73);
		panel.setLayout(null);
		this.showJpanel.add(panel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 883, 73);
		panel.add(scrollPane);
		
		table_2 = new JTable(data, columnNames);
		JTableHeader header = table_2.getTableHeader();
		header.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		header.setPreferredSize(new Dimension(header.getWidth(),35));
		header.setReorderingAllowed(false);
		header.setResizingAllowed(false);
		table_2.setFont(new Font("微软雅黑", Font.PLAIN, 20));//改动
		table_2.setRowHeight(50);//高度
		table_2.setEnabled(false);
		DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
		tcr.setHorizontalAlignment(JLabel.CENTER);
		table_2.setDefaultRenderer(Object.class, tcr);
		table_2.getColumnModel().getColumn(0).setPreferredWidth(85);//设置款度
		scrollPane.setViewportView(table_2);
		
		
		
		//第2行
		panel1 = new JPanel();
		panel1.setBounds(65, 200, 883, 73);
		panel1.setLayout(null);
		this.showJpanel.add(panel1);
		
		JScrollPane scrollPane1 = new JScrollPane();
		scrollPane1.setBounds(0, 0, 883, 73);
		panel1.add(scrollPane1);
		
		table_3 = new JTable(data1, columnNames1);
		JTableHeader header1 = table_3.getTableHeader();
		header1.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		header1.setPreferredSize(new Dimension(header.getWidth(),27));
		header1.setReorderingAllowed(false);
		header1.setResizingAllowed(false);
		table_3.setFont(new Font("微软雅黑", Font.PLAIN, 20));//改动
		table_3.setRowHeight(43);//高度
		table_3.setEnabled(false);
		DefaultTableCellRenderer tcr1 = new DefaultTableCellRenderer();
		tcr1.setHorizontalAlignment(JLabel.CENTER);
		table_3.setDefaultRenderer(Object.class, tcr1);
		table_3.getColumnModel().getColumn(0).setPreferredWidth(78);//设置款度
		table_3.getColumnModel().getColumn(4).setPreferredWidth(120);//设置款度
		scrollPane1.setViewportView(table_3);
		
		//展示增益豆信息
		showZYDInfo();
		
		svrLabel = new JLabel("开户流水号："+transBean.getSvrJrnlNo());
		svrLabel.setBounds(0,y+=50 , 1009, 30);
		svrLabel.setFont(new Font("微软雅黑",Font.PLAIN,30));
		svrLabel.setHorizontalAlignment(SwingUtilities.CENTER);
		this.showJpanel.add(svrLabel);
		
		/* 继续交易 */
		confirm = new JLabel(new ImageIcon("pic/jxjy.png"));
		confirm.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击继续交易按钮");
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				//继续交易
				backTrans(map);
			}
		});
		confirm.setSize( 150, 50);
		confirm.setLocation(1075, 770);
		add(confirm);
		confirm.setVisible(false);
		
		/* 退出 */
		JLabel backButton = new JLabel(new ImageIcon("pic/newPic/exit.png"));
		backButton.setBounds(1258, 770, 150, 50);
		backButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击退出按钮");
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				//清空倒计时和语音
				clearTimeText();
				openPanel(new OutputBankCardPanel());
			}
		});
		add(backButton);
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				prossDialog.showDialog();
				//调用事后监督
				upLoad();
				//银行卡开户入库
				saveOpenAcc();
				
				//是否打印凭条
				isPrintPt(transBean);
				
				//上送流水信息
				AccountTradeCodeAction.transBean.getReqMCM001().setTransresult("0");;
				AccountTradeCodeAction.transBean.getReqMCM001().setIntereturnmsg("开户成功");
				MakeMonitorInfo.makeInfos(AccountTradeCodeAction.transBean.getReqMCM001());
				
				//睡眠
				try {
					Thread.sleep(5000);
				} catch (Exception e) {
					logger.error("等待异常"+e);
				}
				logger.info("打印凭条结束");
				
				prossDialog.disposeDialog();
				confirm.setVisible(true);
			}
		}).start();
	}
	
	/**
	 * 继续交易
	 */
	public void backTrans(final Map<String,Object> map) {
		logger.info("进入继续交易方法");
		clearTimeText();//清空倒计时
		stopTimer(voiceTimer);//关语音
		goOnTrans(map);
	}
	
	/**
	 * 继续交易
	 */
	public void goOnTrans(final Map<String,Object> map){
		AccPublicBean accBean = AccountTradeCodeAction.transBean;
		AccountTradeCodeAction.transBean=null;
		AccountTradeCodeAction.transBean=new AccPublicBean();
		AccountTradeCodeAction.transBean.setCardNo(accBean.getCardNo());
		AccountTradeCodeAction.transBean.setErCiInfo(accBean.getErCiInfo());
		AccountTradeCodeAction.transBean.setAppAcctSeq(accBean.getAppAcctSeq());
		AccountTradeCodeAction.transBean.setCardPov(accBean.getCardPov());
		AccountTradeCodeAction.transBean.setArqc(accBean.getArqc());
		AccountTradeCodeAction.transBean.setTermChkValue(accBean.getTermChkValue());
		AccountTradeCodeAction.transBean.setIssAppData(accBean.getIssAppData());
		AccountTradeCodeAction.transBean.setArqcSrcData(accBean.getArqcSrcData());
		AccountTradeCodeAction.transBean.setCardIsPin(accBean.getCardIsPin());
		AccountTradeCodeAction.transBean.setCardPwd(accBean.getCardPwd());
		AccountTradeCodeAction.transBean.setIdCardNo(accBean.getIdCardNo());
		AccountTradeCodeAction.transBean.setIdCardName(accBean.getIdCardName());
		AccountTradeCodeAction.transBean.setSvrDate(accBean.getSvrDate());
		AccountTradeCodeAction.transBean.setCustNo(accBean.getCustNo());
		AccountTradeCodeAction.transBean.setCertNoAdd(accBean.getCertNoAdd());
		AccountTradeCodeAction.transBean.setCertNoId(accBean.getCertNoId());
		AccountTradeCodeAction.transBean.setCertEndNo(accBean.getCertEndNo());
		AccountTradeCodeAction.transBean.setCertStartNo(accBean.getCertStartNo());
		AccountTradeCodeAction.transBean.setCreatTime(accBean.getCreatTime());
		AccountTradeCodeAction.transBean.setHaveAgentFlag(accBean.getHaveAgentFlag());
		AccountTradeCodeAction.transBean.setZzAmt(accBean.getZzAmt());
		AccountTradeCodeAction.transBean.setCameraCount(accBean.getCameraCount());
		AccountTradeCodeAction.transBean.setBeiZzAmt(accBean.getZzAmt());
		if("1".equals(accBean.getTransChangeNo().trim())){
			AccountTradeCodeAction.transBean.setTransChangeNo("2");
		}else{
			AccountTradeCodeAction.transBean.setTransChangeNo(accBean.getTransChangeNo());
		}
		
		AccountTradeCodeAction.transBean.setIdCardNo(accBean.getIdCardNo());
		AccountTradeCodeAction.transBean.setIdCardName(accBean.getIdCardName());
		AccountTradeCodeAction.transBean.setQfjg(accBean.getQfjg());
		AccountTradeCodeAction.transBean.setAddress(accBean.getAddress());
		AccountTradeCodeAction.transBean.setSex(accBean.getSex());
		AccountTradeCodeAction.transBean.setIdCardtitle(accBean.getIdCardtitle());
		AccountTradeCodeAction.transBean.setIdCardback(accBean.getIdCardback());
		AccountTradeCodeAction.transBean.setIdCardface(accBean.getIdCardface());
		AccountTradeCodeAction.transBean.setBirthday(accBean.getBirthday());
		AccountTradeCodeAction.transBean.setEndDate(accBean.getEndDate());
		AccountTradeCodeAction.transBean.setPhotoPath(accBean.getPhotoPath());
		AccountTradeCodeAction.transBean.setInspect(accBean.getInspect());
		
		AccountTradeCodeAction.transBean.setAgentIdCardNo(accBean.getAgentIdCardNo());
		AccountTradeCodeAction.transBean.setAgentIdCardName(accBean.getAgentIdCardName());
		AccountTradeCodeAction.transBean.setAgentqfjg(accBean.getAgentqfjg());
		AccountTradeCodeAction.transBean.setAgentaddress(accBean.getAgentaddress());
		AccountTradeCodeAction.transBean.setAgentsex(accBean.getSex());
		AccountTradeCodeAction.transBean.setAgentIdCardtitle(accBean.getAgentIdCardtitle());
		AccountTradeCodeAction.transBean.setAgentIdCardback(accBean.getIdCardback());
		AccountTradeCodeAction.transBean.setAgentIdCardface(accBean.getIdCardface());
		AccountTradeCodeAction.transBean.setAgentBirthday(accBean.getAgentBirthday());
		AccountTradeCodeAction.transBean.setAgentEndDate(accBean.getEndDate());
		AccountTradeCodeAction.transBean.setAgentPhotoPath(accBean.getAgentPhotoPath());
		AccountTradeCodeAction.transBean.setAgentinspect(accBean.getAgentinspect());
		
		AccountTradeCodeAction.transBean.setGoOnTrans("1");
		openPanel(new AccChooseBusiness(map));// 返回业务选择页
	}
	
	/**
	 * 是否打印凭条
	 */
	private void isPrintPt(final AccPublicBean transBean) {
		logger.info("进入是否打印凭条的方法");
		openConfirmDialog("是否打印凭条 是：打印凭条，否：不打印凭条。");
		confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("选择否不打印凭条的按钮");
				confirmDialog.disposeDialog();
			}

		});
		confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("选择是打印凭条的按钮");
				/* 退出 */
				confirmDialog.disposeDialog();
				voiceTimer = new Timer(voiceSecond, new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						stopTimer(voiceTimer);//关语音
						closeVoice();//关语音流
						excuteVoice("voice/printPt.wav");
						
					}
				});
				voiceTimer.start();
				billPrint();
			}	
		});
	}
	public void billPrint(){
		logger.info("进入打印凭条的方法");
		try {
			openLed("0");
			logger.info("凭条Led灯打开");
		} catch (Exception e2) {
			logger.error("凭条Led灯打开失败");
		}
		try{
			CDJPrintPt pt = new CDJPrintPt();
			if(!pt.print(transBean)){
				openMistakeDialog(AccountTradeCodeAction.transBean.getBillMsg());
				mistakeDialog.ExitButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						closeDialog(mistakeDialog);
					}
				});
			}
		}catch(Exception e3){
			logger.error("打印凭条失败"+e3);
			openMistakeDialog("凭条打印失败");
			mistakeDialog.ExitButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					closeDialog(mistakeDialog);
				}
			});
			try {
				closeLed("0");
				logger.info("凭条Led灯关闭");
			} catch (Exception e1) {
				logger.info("凭条Led灯关闭");
			}
		}
		
		try {
			closeLed("0");
			logger.info("凭条Led灯关闭");
		} catch (Exception e1) {
			logger.info("凭条Led灯关闭");
		}
	}
	
	
	/**
	 * 调用事后监督
	 */
	public void upLoad(){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				uploadPhoto();
				upLoadPic();
			}
		}).start();
	}
	
	
	/**
	 * 上传事后监督
	 */
	public void uploadPhoto(){
		Map <String,String> map = new HashMap<String,String>();
		map.put("accSvrNo",transBean.getSvrJrnlNo().trim());// 核心流水号
		map.put("accSvrDate", transBean.getSvrDate().trim()+" "+DateUtil.getDateTime("HH:mm:ss"));// 开户日期
		map.put("accName", transBean.getIdCardName().trim());// 户名
		map.put("branchNo", GlobalParameter.branchNo);// 机构号
		map.put("transType", "银行卡子账户开户");// 交易类型
		String money = transBean.getMoney().trim();// 交易金额
		if(money.contains(".")){
			map.put("amount", money);
		}else{
			map.put("amount", money+".00");
		}
//		map.put("amount", transBean.getMoney().trim());// 交易金额
		map.put("accCardNo", transBean.getCardNo().trim()+"-"+transBean.getSubAcctNo().trim());// 账号
		map.put("productName", transBean.getProductName());//产品名称
		map.put("productCode", transBean.getProductCode());//产品代码
		if(transBean.getProductCode().startsWith("AX") || transBean.getProductCode().startsWith("LT") || transBean.getProductCode().startsWith("LZ")){
			map.put("inputName", "收益人姓名 ： "+transBean.getInputName());//收益人姓名
			map.put("inputNo", "收益人账号 ： "+transBean.getInputNo());//收益人账号
		}else{
			map.put("inputName", "");//收益人姓名
			map.put("inputNo", "");//收益人账号
		}
		map.put("selfPhotoPath", transBean.getPhotoPath());//本人证件照路径
		map.put("agentPhotoPath", transBean.getAgentPhotoPath());//代理人证件照路径
		// 授权号
		String supTellerNo = transBean.getSupTellerNo();
		if (supTellerNo == null) {
			supTellerNo = "无";
		}
		map.put("supTellerNo", supTellerNo);// 授权号
		map.put("macNo", GlobalParameter.machineNo);// 终端号
		map.put("idCardName", transBean.getIdCardName().trim());// 本人姓名
		map.put("idCardNo", transBean.getIdCardNo().trim());// 本人身份证号
		map.put("tellerNo", GlobalParameter.tellerNo);// 操作员
		//开户金额
		Float money1 = Float.valueOf(transBean.getMoney());
						
		//开户联网金额超限大于5万
		Float ckeckIdAmt = Float.valueOf(Property.getProperties().getProperty("acc_open_ckeckIdAmt"));
				
		if(money1>=ckeckIdAmt){//判断累计金额是否大于5万
			map.put("haveAgentFlag", transBean.getHaveAgentFlag());//是否有代理人
			map.put("qfjg", transBean.getQfjg().trim());// 签发机关
		}else{
			map.put("haveAgentFlag","无");//是否有代理人
		}
		if("0".equals(transBean.getHaveAgentFlag())){
			map.put("agentIdCardName", transBean.getAgentIdCardName().trim());// 代理人姓名
			map.put("agentIdCardNo", transBean.getAgentIdCardNo().trim());// 代理人身份证号
			map.put("agentqfjg", transBean.getAgentqfjg().trim());// 代理人签发机关
		}
		map.put("intMoney", transBean.getIntMoney());//幸运豆金额
		if(transBean.getZydCount()!=null && !"".equals(transBean.getZydCount())){
			map.put("zydCount", transBean.getZydCount());//增益豆金额
		}
		if(transBean.getXfdCount()!=null && !"".equals(transBean.getXfdCount())){
			map.put("xfdCount", transBean.getXfdCount());//消费豆金额
		}
		map.put("billNo", "无");//存单凭证号
		map.put("startDate", transBean.getCreateTime());//起息日期
		map.put("endDate", transBean.getEndTime());//到期日期
		map.put("openRate", transBean.getRate().trim()+"%");//开户利率
		map.put("depTerm", transBean.getJxRyjDepTem()+"天");//存期
		map.put("exchFlag", "非自动转存");//转存标志
		if(transBean.getProductCode().startsWith("JX")||transBean.getProductCode().startsWith("RJ")){
			map.put("realAccNo", transBean.getRelaAcctNo()+"-"+transBean.getSubRelaAcctNo());//如意存或者积享存关联账号
		}
		
		
		try {
			if(("0".equals(transBean.getCertPrint()))){
				CDJPrintBill pa = new CDJPrintBill();
				//拼接调用电子印章所需要的参数
				JSONObject jsonObject = pa.getJsons(transBean);
				// 调用电子印章http接口（base64编码）
				String dzyz = pa.getDZYZ(Property.dzyz_url, jsonObject);
				logger.info("电子印章："+dzyz);
				if(dzyz!=null){
					//将base64编码转换成图片
					//获取电子印章
					if(!pa.GenerateImage(dzyz, Property.dzyz_ml)){
						logger.info("电子印章转换成图片失败。");
					}
				}else{
					logger.info("获取电子印章失败");
				}
			}
		} catch (Exception e) {
			logger.error("生成电子印章图片异常："+e);
		}
		
		JpgUtil_CardKH cg = new JpgUtil_CardKH();
		String filePath = "";
		try {
			filePath = cg.graphicsGeneration(map);
		} catch (Exception e2) {
			logger.error("事后监管程序，生成事后监管图片异常！"+ e2);
		}
		
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
    		
    		String nowDate = DateUtil.getNowDate("yyyyMMdd");
    		// 上传的目录
    		String ftpPath = Property.FTP_SER_PATH_KH + nowDate+"/000006/";
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
    		File file = new File(filePath);
    		boolean result = sf.upload(ftpPath, filePath, sftp);
    		if(!result){
    			logger.error("事后监管上传图片失败-->" + file.getName());
    		}else{
    			logger.info("事后监管上传图片成功-->" + file.getName());
    			//删除图片
    			deleteFile(file);
    		}
    		
		} catch (Exception e) {
			logger.error("事后监督上传图片，进入目录失败"+e);
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
			System.gc();// 垃圾回收,主要是为了释放上传时被占用的资源图片
			boolean result = file.delete();
		if (!result) {// 判断是否全部删除
			file.delete();
		}
		logger.info("删除成功" + file);
		}
	}
	
	/**
	 * 银行卡开户入库
	 */
	private void saveOpenAcc(){
		
		logger.info("银行卡开户入库记录");
		try {
			Map openAcc = IntefaceSendMsg.saveOpenAcc(transBean); 
			if(!"000000".equals(openAcc.get("resCode"))){
				
				logger.info("银行卡开户有入库失败："+openAcc.get("errMsg"));
			}
		} catch (Exception e) {
			logger.error("银行卡开户有入库失败："+e);
		}
		
		logger.info("银行卡开户入库成功");
	}
	
	//开户成功后根据是否有增益豆信息显示增益豆信息提示
	public void showZYDInfo(){
		java.text.DecimalFormat  df1= new java.text.DecimalFormat("0.0"); 
		if(transBean.getZydCount()!=null && !"".equals(transBean.getZydCount())){
			if(Double.valueOf(transBean.getZydCount())==0){
				logger.info("增益豆数量为0");
			}else{
				
				//加载增益豆规则
				zydrp = new AccZYDRulePanel(showJpanel,Property.acc_td_rules_path);
				zydrp.getJsp().setVisible(false);
				
				tdTotalCount = new JLabel("<HTML><font color='#333333'>您购买此产品共获赠</font><font color='#ff0000'>"
						//+ transBean.getTdTotalCount()
						+ df1.format(Double.valueOf(transBean.getTdTotalCount()))
						+ "</font><font color='#333333'>" + "个唐豆,"
						+ "</font></HTML>");
				tdTotalCount.setHorizontalAlignment(SwingConstants.CENTER);
				tdTotalCount.setFont(new Font("微软雅黑", Font.PLAIN, 30));
				tdTotalCount.setForeground(Color.decode("#333333"));
				tdTotalCount.setBounds(0, y+=50, 1009, 30);
				this.showJpanel.add(tdTotalCount);
				
				StringBuffer tdStr =new StringBuffer("含");
				if(transBean.getIntMoney()!=null && !"".equals(transBean.getIntMoney()) && Double.valueOf(transBean.getIntMoney())>0){
					//tdStr.append("幸运豆"+transBean.getIntMoney()+"元、");
					tdStr.append("幸运豆"+df1.format(Double.valueOf(transBean.getIntMoney()))+"元、");
				}
				if(transBean.getZydCount()!=null && !"".equals(transBean.getZydCount()) && Double.valueOf(transBean.getZydCount())>0){
					//tdStr.append("增益豆"+transBean.getZydCount()+"元、");
					tdStr.append("增益豆"+df1.format(Double.valueOf(transBean.getZydCount()))+"元、");
				}
				if(transBean.getXfdCount()!=null && !"".equals(transBean.getXfdCount()) && Double.valueOf(transBean.getXfdCount())>0){
					//tdStr.append("消费豆"+transBean.getXfdCount()+"个、");
					tdStr.append("消费豆"+df1.format(Double.valueOf(transBean.getXfdCount()))+"个、");
				}
				tdStr.deleteCharAt(tdStr.length()-1);
				tdStr.append(",");
				
				tdinfoCount =new JLabel(tdStr.toString());
				tdinfoCount.setBounds(0, y+=50, 1009, 30);
				tdinfoCount.setFont(new Font("微软雅黑",Font.PLAIN,30));
				tdinfoCount.setHorizontalAlignment(SwingUtilities.CENTER);
				this.showJpanel.add(tdinfoCount);
				
				labelTitle = new JLabel("");
				labelTitle.setBounds((showJpanel.getWidth()-250)/2, 20, 250, 40);
				labelTitle.setFont(new Font("微软雅黑",Font.BOLD,30));
				labelTitle.setHorizontalAlignment(SwingUtilities.CENTER);
				labelTitle.setVisible(false);
				this.showJpanel.add(labelTitle);
				
				rlueLabel =new JLabel("若该笔存款提前支取将扣回唐豆，具体标准见  ");
				rlueLabel.setBounds(0, y+=50, 690, 30);
				rlueLabel.setFont(new Font("微软雅黑",Font.PLAIN,30));
				rlueLabel.setHorizontalAlignment(SwingUtilities.RIGHT);
				this.showJpanel.add(rlueLabel);
				
				rlueButton = new JLabel("  唐豆扣回规则(链接)");
				rlueButton.setBounds(690, y, 300, 30);
				rlueButton.setFont(new Font("微软雅黑",Font.BOLD,30));
				rlueButton.setHorizontalAlignment(SwingUtilities.LEFT);
				rlueButton.setForeground(Color.red);
				rlueButton.setBackground(Color.BLUE);
				this.showJpanel.add(rlueButton);
				
				rlueButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						logger.info("点击扣回规则按钮");
						zydrp.getJsp().setVisible(true);
						showRule();
					};
				});
				
				/* 确认 */
				zydrComfirm = new JLabel(new ImageIcon("pic/affirm.png"));
				zydrComfirm.setBounds((showJpanel.getWidth()-200)/2, showJpanel.getHeight()-70, 200, 50);
				zydrComfirm.setVisible(false);
				this.showJpanel.add(zydrComfirm);
				zydrComfirm.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						logger.info("点击规则确认按钮");
						zydrp.getJsp().setVisible(false);
						notShowRule();
					}
				});
			}
		}
	}
	
	/**
	 * 获取上传电子签名和电子印章的路径
	 */
	public boolean getUpLoadQMAndYZPicPath(){
		try {
			Map map = IntefaceSendMsg.checkUploadPath(transBean);
			if(!"000000".equals(map.get("resCode"))){
				
				logger.info("银行卡开户有入库失败："+map.get("errMsg"));
				return false;
			}
		} catch (Exception e) {
			logger.error("调用查询保存图片路径失败："+e);
			return false;
		}
		logger.info("获取电子签名和电子印章图片成功");
		return true;
	}
	
	/**
	 * 上传电子签名和电子印章图片
	 */
	public void upLoadPic(){
		if(!getUpLoadQMAndYZPicPath()){
			logger.info("获取上传路径失败");
			return;
		}
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
    		
    		String nowDate = DateUtil.getNowDate("yyyyMMdd");
    		// 上传的目录
    		String ftpPath = transBean.getUploadPath();
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
    		for(int i=0;i<2;i++){
    			String filePath="";
    			String nameStr="";
    			if(i==0){
    				filePath=Property.SIGNATURE_PATH;
    				nameStr="DZQM";
    			}else{
    				filePath=Property.dzyz_ml;
    				nameStr="DZYZ";
    			}
    			File file =  new File(filePath);
    			//临时图片名称（用于上传）命名方式和路径
    			//D:\\bh_tmp\\LSTP\\卡号-子账号_(DZQM/DZYZ).png
    			String linSPath=Property.BH_TMP+"\\LSTP\\"+transBean.getCardNo().trim()+"-"+transBean.getSubAcctNo().trim()+
    					"_"+nameStr.trim()+".png";
    			File newFile = new File(linSPath);
    			FileUtil.copyFileUsingJava7Files(file, newFile);
    			boolean result = sf.upload(ftpPath, linSPath, sftp);
    			if(!result){
    				logger.error("上传图片失败-->" + file.getName());
    			}else{
    				logger.info("上传图片成功-->" + file.getName());
    				deleteFile(newFile);
    			}
    		}
    	} catch (Exception e) {
			logger.error("上传图片，进入目录失败"+e);
		}finally{
			if (sftp!= null && sftp.isConnected()){
				sftp.disconnect();
			}
			if (sshSession!= null && sshSession.isConnected()){
				sshSession.disconnect();
			}
		}
	}
	
	//点击扣回规则显示规则明细
	public void showRule(){
		zydrComfirm.setVisible(true);
		labelTitle.setVisible(true);
		
		billImage.setVisible(false);
		promptLabel.setVisible(false);
		accImage.setVisible(false);
		lblNewLabel_1.setVisible(false);
		tdinfoCount.setVisible(false);
		panel.setVisible(false);
		panel1.setVisible(false);
		svrLabel.setVisible(false);
		tdTotalCount.setVisible(false);
		rlueLabel.setVisible(false);
		rlueButton.setVisible(false);
	}
	
	//点击规则确认按钮退出规则明细
	public void notShowRule(){
		zydrComfirm.setVisible(false);
		labelTitle.setVisible(false);
		
		tdinfoCount.setVisible(true);
		billImage.setVisible(true);
		promptLabel.setVisible(true);
		accImage.setVisible(true);
		lblNewLabel_1.setVisible(true);
		panel.setVisible(true);
		panel1.setVisible(true);
		svrLabel.setVisible(true);
		tdTotalCount.setVisible(true);
		rlueLabel.setVisible(true);
		rlueButton.setVisible(true);
	}
	
}

