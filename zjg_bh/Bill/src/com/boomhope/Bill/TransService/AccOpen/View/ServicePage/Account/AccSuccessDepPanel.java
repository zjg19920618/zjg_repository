package com.boomhope.Bill.TransService.AccOpen.View.ServicePage.Account;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.boomhope.Bill.Comm.CDJPrintBill;
import com.boomhope.Bill.Comm.CDJPrintPt;
import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Public.GlobalParameter;
import com.boomhope.Bill.PublicControl.OutputBankCardPanel;
import com.boomhope.Bill.TransService.AccOpen.Bean.AccPublicBean;
import com.boomhope.Bill.TransService.AccOpen.Bean.accOpenProFileBean02808And03870;
import com.boomhope.Bill.TransService.AccOpen.Interface.IntefaceSendMsg;
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

/**
 * 存单开户成功页面
 * 
 * @author gyw
 * 
 */
public class AccSuccessDepPanel extends BaseTransPanelNew {

	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(AccSuccessDepPanel.class);

	AccPublicBean transBean = null;
	private Map<String, Object> params;
	private  String path="";
	JLabel confirm;
	private boolean shjdBiao = true;//事后标识
	private boolean dayiShibai = true;//调用存单打印接口失败标识
	private boolean on_off=true;
	
	private AccZYDRulePanel zydrp = null;//增益豆规则
	
	private JLabel sign;//成功图片
	private JLabel jixiangwu;//吉祥物图片
	private JLabel success;//交易成功
	private JLabel label1;//唐豆显示框
	private JLabel svrLabel;//开户流水号显示框
	private JLabel tangLabel2;//唐豆派发流水号显示框
	private JLabel label=null;//提示框
	private JLabel label2=null;//提示框
	private JLabel zydrComfirm;//增益豆规则显示确认按钮
	private JLabel tdinfoCount;//唐豆明细
	private JLabel tdTotalCount;//增益豆赠送数量
	private JLabel rlueLabel;//规则提示
	private JLabel rlueButton;//规则按钮
	private JLabel labelTitle;//扣回规则标题
	private int y=150;//各个组件位置高度
	
	
	public AccSuccessDepPanel(final AccPublicBean transBean,final Map<String,Object> map) {
		logger.info("进入存单开户成功页面");
		this.transBean = transBean;
		this.params = map;
		
		/* 倒计时打开语音 */
		if("0010".equals(transBean.getProductCode()) && "1".equals(transBean.getCertPrint())){
			voiceTimer = new Timer(voiceSecond, new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					stopTimer(voiceTimer);//关语音
					closeVoice();//关语音流
					excuteVoice("voice/successdep.wav");
					
				}
			});
			voiceTimer.start();
		}else if("0".equals(transBean.getAgreementPrint())){
			voiceTimer = new Timer(voiceSecond, new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					stopTimer(voiceTimer);//关语音
					closeVoice();//关语音流
					excuteVoice("voice/successAgreement.wav");
					
				}
			});
			voiceTimer.start();
		}
		
		
		this.showTimeText(delaySecondMaxTime);
		delayTimer = new Timer(delaySecondMaxTime * 1000, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {
            	logger.info("存单开户成功倒计时结束 ");
            	/* 倒计时结束退出交易 */ 
            	clearTimeText();
            	serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！","","");
            }      
        });            
		delayTimer.start(); 

		
		

		// 图标
		sign = new JLabel();
		sign.setIcon(new ImageIcon("pic/newPic/success.png"));
		sign.setBounds(360, 105, 70, 70);
		this.showJpanel.add(sign);
		// 吉祥物图片
		jixiangwu = new JLabel();
		jixiangwu.setIcon(new ImageIcon("pic/newPic/mascot.png"));
		jixiangwu.setBounds(283, 148, 493, 454);
		this.showJpanel.add(jixiangwu);
		/*
		 * 交易成功
		 */
		success = new JLabel("交 易 成 功!");
		success.setForeground(Color.decode("#412174"));
		success.setFont(new Font("微软雅黑", Font.BOLD, 30));
		success.setBounds(439, 115, 210, 40);
		this.showJpanel.add(success);
		
		if((transBean.getProductCode().startsWith("0010") || transBean.getProductCode().startsWith("XF")) && transBean.getExchangeAmt()!=null && Double.parseDouble(transBean.getExchangeAmt())>0){
			label1 = new JLabel(
					"<HTML><font color='#333333'>本次存款所得价值</font><font color='#ff0000'>"
							+ transBean.getExchangeAmt() + "元"
							+ "</font><font color='#333333'>" + "唐豆已存入您的银行卡!"
							+ "</font></HTML>");
			label1.setHorizontalAlignment(SwingConstants.CENTER);
			label1.setFont(new Font("微软雅黑", Font.PLAIN, 30));
			label1.setForeground(Color.decode("#333333"));
			label1.setBounds(0, y+=50, 1009, 30);
			this.showJpanel.add(label1);
			
		}
		
		label2 = new JLabel();
		label2.setHorizontalAlignment(SwingConstants.CENTER);
		label2.setForeground(Color.decode("#ff0000"));
		label2.setFont(new Font("微软雅黑", Font.PLAIN, 30));
		label2.setBounds(0, GlobalParameter.TRANS_HEIGHT - 200, 1009, 30);
		this.showJpanel.add(label2);
		
		//展示增益豆信息
		showZYDInfo();

		svrLabel = new JLabel("开户流水号："+transBean.getSvrJrnlNo());
		svrLabel.setBounds(0, y+=50, 1009, 30);
		svrLabel.setFont(new Font("微软雅黑",Font.PLAIN,30));
		svrLabel.setHorizontalAlignment(SwingUtilities.CENTER);
		this.showJpanel.add(svrLabel);
		
		if(transBean.getTangDDFSvrNo()!=null && !"".equals(transBean.getTangDDFSvrNo())){
			tangLabel2 = new JLabel("唐豆派发流水号："+transBean.getTangDDFSvrNo());
			tangLabel2.setBounds(0, y+=50, 1009, 30);
			tangLabel2.setFont(new Font("微软雅黑",Font.PLAIN,30));
			tangLabel2.setHorizontalAlignment(SwingUtilities.CENTER);
			this.showJpanel.add(tangLabel2);
		}
		
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
		
		label = new JLabel("");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("微软雅黑", Font.PLAIN, 30));
		label.setForeground(Color.decode("#333333"));
		label.setBounds(0, GlobalParameter.TRANS_HEIGHT - 250, 1009, 30);
		label.setVisible(false);
		this.showJpanel.add(label);
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				prossDialog.showDialog();
				//银行卡开户入库
				saveOpenAcc();
				
				// 打印存单
				if ("1".equals(transBean.getCertPrint())) {
					//  提示信息
					logger.info("打印存单开始" + transBean);
					label.setText("正在打印存单，请耐心等待...");
					label2.setText("请检查印章是否打印完整，若有异议请联系大堂经理");
					label.setVisible(true);
					
					// 调存单打印接口
					if (!bill()) {
						shjdBiao = false;
						dayiShibai = false;
						//调用事后监督
						upLoad();
						return;
					}
					
					//打开led灯
					try {
						openLed("3");
					} catch (Exception e1) {
						logger.error("打开Led灯异常" + e1);
					}
					
					// 调存单打印机打印
					if(!printBill()){
						shjdBiao = false;
						//关闭led灯
						try {
							closeLed("3");
						} catch (Exception e1) {
							logger.error("关闭Led灯异常" + e1);
						}	
						//调用事后监督
						upLoad();
						return;
					};
					
					//关闭led灯
					try {
						closeLed("3");
					} catch (Exception e1) {
						logger.error("关闭Led灯异常" + e1);
					}	
					//调用事后监督
					upLoad();
					
					// 更新凭证
					updateBill();
					
					repaint();
					logger.info("打印存单结束");
					label.setText("存单打印成功");
					
					//睡眠
					try {
						Thread.sleep(5000);
					} catch (Exception e) {
						logger.error("等待异常"+e);
					}
				}else{
					
					shjdBiao = false;
					//调用事后监督
					upLoad();
				}
				
				//是否打印凭条
				isPrintPt(transBean);
				
				//上送流水信息
				if("1".equals(transBean.getCertPrint())){
					AccountTradeCodeAction.transBean.getReqMCM001().setUsevouchertype("102");
					AccountTradeCodeAction.transBean.getReqMCM001().setUsevoucherno(transBean.getCertNoAdd());
				}else{
					AccountTradeCodeAction.transBean.getReqMCM001().setUsevouchertype("");
					AccountTradeCodeAction.transBean.getReqMCM001().setUsevoucherno("");
				}
				transBean.getReqMCM001().setTransresult("0");
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
		clearTimeText();// 清空倒计时
		stopTimer(voiceTimer);// 关语音
		closeVoice();// 关语音流
		if("1".equals(AccountTradeCodeAction.transBean.getCertPrint())){
			AccountTradeCodeAction.transBean.setCertNoAdd(String.format("%08d",(Long.parseLong(AccountTradeCodeAction.transBean.getCertNoAdd())+ 1)));
		}
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

	/** 更新凭证号 */
	public void updateBill() {
		logger.info("进入更新凭证号方法");
		try {
			Map<String, String> updateMap = new HashMap<String, String>();
			updateMap.put("nowNo", String.format("%08d",
					(Long.parseLong(AccountTradeCodeAction.transBean
							.getCertNoAdd()) + 1)));// 更新当前凭证号
			updateMap.put("updateDate", DateUtil.getNowDate("yyyyMMddHHmmss"));
			updateMap.put("isNo", "");
			updateMap.put("id", transBean.getCertNoId());
			updateMap.put("startNo", transBean.getCertStartNo());
			updateMap.put("endNo", transBean.getCertEndNo());
			updateMap.put("createDate", transBean.getCreateTime());
			updateMap.put("updateDate", DateUtil.getNowDate("yyyyMMddHHmmss"));

			Map<String, String> tms0006 = IntefaceSendMsg.Tms0006(updateMap);
			if (!"000000".equals(tms0006.get("resCode"))) {// 凭证更新失败依然打印
				logger.error("更新凭证号失败：" + tms0006.get("errMsg"));
				openMistakeDialog("更新凭证号失败");
			}
		} catch (Exception e) {
			logger.error("更新凭证号失败：" + e);
			openMistakeDialog("更新凭证号失败");
		}
	}

	/** 调用存单打印接口 */
	public boolean bill() {
		logger.info("进入调用存单打印接口方法");
		boolean isBill = true;
		try {
			AccountTradeCodeAction.transBean.getReqMCM001().setReqBefor("03514");
			Map map = IntefaceSendMsg.inter03514(transBean);
			AccountTradeCodeAction.transBean.getReqMCM001().setReqAfter((String)map.get("resCode"), (String)map.get("errMsg"));
			if("4444".equals(map.get("resCode"))){
				logger.error(map.get("errMsg"));
				prossDialog.disposeDialog();
				AccountTradeCodeAction.transBean.getReqMCM001().setUsevouchertype("");
				AccountTradeCodeAction.transBean.getReqMCM001().setUsevoucherno("");
				AccountTradeCodeAction.transBean.getReqMCM001().setTransresult("2");
				MakeMonitorInfo.makeInfos(AccountTradeCodeAction.transBean.getReqMCM001());
				serverStop("开户成功,存单打印失败", (String)map.get("errMsg"),"");
				isBill = false;
			}
			if (!"000000".equals(map.get("resCode"))) {
				logger.error(map.get("errMsg"));
				prossDialog.disposeDialog();
				AccountTradeCodeAction.transBean.getReqMCM001().setUsevouchertype("");
				AccountTradeCodeAction.transBean.getReqMCM001().setUsevoucherno("");
				AccountTradeCodeAction.transBean.getReqMCM001().setTransresult("2");
				MakeMonitorInfo.makeInfos(AccountTradeCodeAction.transBean.getReqMCM001());
				serverStop("开户成功,存单打印失败", (String)map.get("errMsg"),"");
				isBill = false;
			}
		} catch (Exception e) {
			logger.error("调用存单打印接口失败：" + e);
			prossDialog.disposeDialog();
			AccountTradeCodeAction.transBean.getReqMCM001().setUsevouchertype("");
			AccountTradeCodeAction.transBean.getReqMCM001().setUsevoucherno("");
			AccountTradeCodeAction.transBean.getReqMCM001().setIntereturnmsg("调用03514接口异常");
			AccountTradeCodeAction.transBean.getReqMCM001().setTransresult("2");
			MakeMonitorInfo.makeInfos(AccountTradeCodeAction.transBean.getReqMCM001());
			serverStop("开户成功,存单打印失败","", "调用03514存单打印接口失败");
			isBill = false;
		}
		return isBill;
	}

	/** 调存单打印机 */
	public boolean printBill() {
		logger.info("进入调用存单打印机的方法");
		boolean isPrint = true;
		try {
			// 打印存单
			CDJPrintBill cdjPrintBill = new CDJPrintBill();
			cdjPrintBill.PrintBill(transBean);
		} catch (Exception e) {
			logger.error("打印机打印存单失败：" + e);
			prossDialog.disposeDialog();
			AccountTradeCodeAction.transBean.getReqMCM001().setIntereturnmsg("打印存单失败");
			AccountTradeCodeAction.transBean.getReqMCM001().setTransresult("2");
			MakeMonitorInfo.makeInfos(AccountTradeCodeAction.transBean.getReqMCM001());
			serverStop("抱歉，您的存单没有打印成功","", "调用存单打印机失败");
			isPrint = false;
		}
		return isPrint;
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
	
	//打印凭条
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
		map.put("tangDDFAmt", transBean.getExchangeAmt());//唐豆金额
		map.put("tangDDFSvrNo", transBean.getTangDDFSvrNo());//唐豆流水号
		map.put("intMoney", transBean.getIntMoney());//幸运豆金额
		if(transBean.getZydCount()!=null && !"".equals(transBean.getZydCount())){
			map.put("zydCount", transBean.getZydCount());//增益豆金额
		}
		if(transBean.getXfdCount()!=null && !"".equals(transBean.getXfdCount())){
			map.put("xfdCount", transBean.getXfdCount());//消费豆金额
		}
		
		if ("1".equals(transBean.getCertPrint())) {
			if(!dayiShibai){
				map.put("billNo", "无");//存单凭证号
			}else{
				map.put("billNo", transBean.getCertNoAdd());//存单凭证号
			}
		}else{
			map.put("billNo", "无");//存单凭证号
		}
		map.put("startDate", transBean.getCreateTime());//起息日期
		map.put("endDate", transBean.getEndTime());//到期日期
		map.put("openRate", transBean.getRate().trim()+"%");//开户利率
		if(transBean.getProductCode().startsWith("RJ")){
			map.put("depTerm", transBean.getJxRyjDepTem()+"天");//存期
		}else{
			map.put("depTerm", transBean.getMonthsUpperStr());//存期
		}
		if(transBean.getProductCode().startsWith("0010")){
			if("0".equals(transBean.getAutoRedpFlag().trim())){
				map.put("exchFlag", "非自动转存");//转存标志
			}else{
				map.put("exchFlag", "自动转存");//转存标志
			}
		}else{
			map.put("exchFlag", "非自动转存");//转存标志
		}
		if(transBean.getProductCode().startsWith("JX")||transBean.getProductCode().startsWith("RJ")){
			map.put("realAccNo", transBean.getRelaAcctNo()+"-"+transBean.getSubRelaAcctNo());//如意存或者积享存关联账号
		}
		try {
			if(shjdBiao){
				
			}else{
				if(("0".equals(transBean.getCertPrint()) )){
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
			return;
		}
		
		logger.info("银行卡开户入库成功");
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
	
	//开户成功后根据是否有增益豆信息显示增益豆信息提示
	public void showZYDInfo(){
		java.text.DecimalFormat  df1= new java.text.DecimalFormat("0.0"); 
		//java.text.DecimalFormat  df2= new java.text.DecimalFormat("0.00"); 
		if(transBean.getTdTotalCount()!=null && !"".equals(transBean.getTdTotalCount())){
			if(Double.valueOf(transBean.getTdTotalCount())==0){
				logger.info("增益豆数量为0");
			}else{
				zydrp = new AccZYDRulePanel(showJpanel,Property.acc_zyd_rules_path);
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
					//tdStr.append("幸运豆"+transBean.getIntMoney().trim()+"元、");
					tdStr.append("幸运豆"+df1.format(Double.valueOf(transBean.getIntMoney()))+"元、");
				}
				if(transBean.getZydCount()!=null && !"".equals(transBean.getZydCount()) && Double.valueOf(transBean.getZydCount())>0){
					//tdStr.append("增益豆"+transBean.getZydCount().trim()+"元、");
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
	
	//点击扣回规则显示规则明细
	public void showRule(){
		zydrComfirm.setVisible(true);
		labelTitle.setVisible(true);
		sign.setVisible(false);
		jixiangwu.setVisible(false);
		success.setVisible(false);
		if(label1!=null){
			label1.setVisible(false);
		}
		if(tangLabel2!=null){
			tangLabel2.setVisible(false);
		}
		tdinfoCount.setVisible(false);
		svrLabel.setVisible(false);
		label.setVisible(false);
		label2.setVisible(false);
		tdTotalCount.setVisible(false);
		rlueLabel.setVisible(false);
		rlueButton.setVisible(false);
	}
	
	//点击规则确认按钮退出规则明细
	public void notShowRule(){
		zydrComfirm.setVisible(false);
		labelTitle.setVisible(false);
		sign.setVisible(true);
		jixiangwu.setVisible(true);
		success.setVisible(true);
		svrLabel.setVisible(true);
		label.setVisible(true);
		label2.setVisible(true);
		tdTotalCount.setVisible(true);
		tdinfoCount.setVisible(true);
		rlueLabel.setVisible(true);
		rlueButton.setVisible(true);
		if(label1!=null){
			label1.setVisible(true);
		}
		if(tangLabel2!=null){
			tangLabel2.setVisible(true);
		}
	}
}











