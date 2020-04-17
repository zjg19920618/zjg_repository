package com.boomhope.Bill.TransService.AccOpen.View.ServicePage.Account;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.PublicControl.OutputBankCardPanel;
import com.boomhope.Bill.TransService.AccOpen.controller.AccountTradeCodeAction;
import com.boomhope.Bill.TransService.AccOpen.controller.AccountTransFlow;
import com.boomhope.Bill.Util.UtilButton;

/**
 * 业务选择页面
 * 
 * @author yangtao
 *
 */
public class AccChooseBusiness extends BaseTransPanelNew {

	static Logger logger = Logger.getLogger(AccChooseBusiness.class);
	private static final long serialVersionUID = 1L;

	Map<Object, Object> parames = null;
	
	private JLabel leftLabel;
	private JLabel rightLabel;
	private JPanel panel1;
	private JPanel panel2;
	private int page = 1;
	private boolean on_off=true;

	/***
	 * 初始化
	 */
	public AccChooseBusiness(final Map params) {
		logger.info("进入业务选择页面");
		
		// 将当前页面传入流程控制进行操作
		final Component comp = this;
		this.parames = params;
		if(delayTimer!=null){
			clearTimeText();
		}
		/* 显示时间倒计时 */
		this.showTimeText(delaySecondShortTime);
		delayTimer = new Timer(delaySecondShortTime * 1000, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {  
            	logger.info("业务选择页面倒计时结束 ");
            	/* 倒计时结束退出交易 */ 
            	clearTimeText();
            	serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！","","");
            }      
        });            
		delayTimer.start(); 

		/* 倒计时打开语音 */
		voiceTimer = new Timer(voiceSecond,
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						stopTimer(voiceTimer);// 关闭语音
						closeVoice();
						excuteVoice("voice/select.wav");

					}
				});
		voiceTimer.start();
		
		// 请选择您要办理的业务
		JLabel msg = new JLabel("请选择您要办理的业务");
		msg.setFont(new Font("微软雅黑", Font.BOLD, 40));
		msg.setForeground(Color.decode("#412174"));
		msg.setBounds(306, 41, 464, 60);
		this.showJpanel.add(msg);
		
		//上一页按钮
		leftLabel = new JLabel(new ImageIcon("pic/newPic/left.png"));
		leftLabel.setBounds(30, 265, 57, 98);
		this.showJpanel.add(leftLabel);
		leftLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				upPage();
			}
		});
		//如果为第一页，则不显示上一页按钮
		if(page==1){
			leftLabel.setVisible(false);
		}
		
		//下一页按钮
		rightLabel = new JLabel(new ImageIcon("pic/newPic/right.png"));
		rightLabel.setBounds(922, 265, 57, 98);
		rightLabel.setVisible(false);
		this.showJpanel.add(rightLabel);
		rightLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				downPage();
			}
		});
		
		//第一页的产品
		panel1 = new JPanel();
		panel1.setBounds(87, 110, 835, 500);
		panel1.setOpaque(false);
		panel1.setLayout(null);
		this.showJpanel.add(panel1);
		
		//第二页
		panel2 = new JPanel();
		panel2.setBounds(87, 110, 835, 500);
		panel2.setOpaque(false);
		panel2.setVisible(false);
		panel2.setLayout(null);
		this.showJpanel.add(panel2);
		
		// 整存整取
		UtilButton openButton = new UtilButton("pic/wholeupdated.png",
				"pic/wholeupdated.png");
		openButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击整存整取按钮");
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				// 整存整取
				OpenDeposit(comp, params);
			}

		});
		openButton.setSize(227, 337);
		openButton.setLocation(30, 64);
		openButton.setIcon(new ImageIcon("pic/wholeupdated.png"));
		panel1.add(openButton);

		// 协议存款
		UtilButton backButton = new UtilButton("pic/agreementupdated.png",
				"pic/agreementupdated.png");
		backButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击协议存款按钮");
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				// 协议存款
				ProtocolDeposit(comp, params);
			}
		});
		backButton.setSize(227, 337);
		backButton.setLocation(305, 64);
		backButton.setIcon(new ImageIcon("pic/agreementupdated.png"));
		panel1.add(backButton);

		// 私行快线
		UtilButton backButton11 = new UtilButton("pic/shkxupdated.png",
				"pic/shkxupdated.png");
		backButton11.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击私行快线按钮");
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				// 私行快线
				PrivateDeposit(comp, params);
			}
		});
		backButton11.setSize(227, 337);
		backButton11.setLocation(568, 64);
		backButton11.setIcon(new ImageIcon("pic/shkxupdated.png"));
		panel1.add(backButton11);
		
		/*// 普惠产品(因业务需要暂时屏蔽该产品)
		UtilButton puHuiButton = new UtilButton("pic/PHCP.png",
				"pic/PHCP.png");
		puHuiButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击整存整取按钮");
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				//普惠产品
				puHuiDeposit(comp,params);
			}

		});
		puHuiButton.setSize(227, 337);
		puHuiButton.setLocation(30, 64);
		puHuiButton.setIcon(new ImageIcon("pic/PHCP.png"));
		panel2.add(puHuiButton);*/
		
		//退出
		JLabel backOutButton = new JLabel();
		backOutButton.setIcon(new ImageIcon("pic/newPic/exit.png"));
		backOutButton.setBounds(1258, 770, 150, 50);
		backOutButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击退出按钮");
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				//清空倒计时
				clearTimeText();
				stopTimer(voiceTimer);// 关闭语音
				closeVoice();
				//返回时跳转页面
				openPanel(new OutputBankCardPanel());
			}
		});		
		add(backOutButton);	
	}
	
	/**
	 * 整存整取
	 */
	private void OpenDeposit(final Component comp,final Map<Object, Object> params) {
		logger.info("进入跳转整存整取页面的方法");
		//调用凭证号查询
		if(!checkCertNo(AccountTradeCodeAction.transBean)){
			openConfirmDialog("凭证号不足，无法打印存单，请联系大堂经理添加凭证号，是否继续操作？是：继续完成开户，但不打印存单，否：退出.");
			confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					clearTimeText();// 清空倒计时
					stopTimer(voiceTimer);// 关闭语音
					closeVoice();
					logger.info("选择是按钮，不打印存单");
					AccountTradeCodeAction.transBean.setCertPrint("0");//不需要打印存单
					
					// 跳转
					params.put("resultCode", "0000");
					params.put("transCode", "0003");
					params.put("productType", "ZCZQ");
					AccountTradeCodeAction.transBean.setProductType("4");
					AccountTradeCodeAction.transBean.setProductCode("0010");
					AccountTradeCodeAction.transBean.getReqMCM001().setTranscode("11017");
					AccountTradeCodeAction.transBean.setProductName("整存整取储蓄存款");
					AccountTransFlow.startTransFlow(comp, params);
					
				}
			});
			confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					logger.info("选择否按钮，退出业务");
					clearTimeText();// 清空倒计时
					stopTimer(voiceTimer);// 关闭语音
					closeVoice();
					openPanel(new OutputBankCardPanel());
				}
			});
			return;
		}
		
		clearTimeText();// 清空倒计时
		stopTimer(voiceTimer);// 关闭语音
		closeVoice();
		// 跳转
		params.put("resultCode", "0000");
		params.put("transCode", "0003");
		params.put("productType", "ZCZQ");
		AccountTradeCodeAction.transBean.setProductType("4");
		AccountTradeCodeAction.transBean.setProductCode("0010");
		AccountTradeCodeAction.transBean.setProductName("整存整取储蓄存款");
		AccountTradeCodeAction.transBean.getReqMCM001().setTranscode("11017");
		AccountTransFlow.startTransFlow(comp, params);
	}

	/*
	 * 上一页
	 */
	public void upPage(){
		page -= 1;
		if(page==1){
			panel1.setVisible(true);
			panel2.setVisible(false);
			leftLabel.setVisible(false);
			rightLabel.setVisible(true);
		}
	}
	
	/*
	 * 下一页
	 */
	public void downPage(){
		page += 1;
		if(page==2){
			panel2.setVisible(true);
			panel1.setVisible(false);
			rightLabel.setVisible(false);
			leftLabel.setVisible(true);
		}
	}
	
	
	/**
	 * 普惠产品
	 */
	private void puHuiDeposit(final Component comp,
			final Map<Object, Object> params){
		logger.info("进入普惠产品页面的方法");
		
		//调用凭证号查询
		if(!checkCertNo(AccountTradeCodeAction.transBean)){
			openConfirmDialog("凭证号不足，无法打印存单，请联系大堂经理添加凭证号，是否继续操作？是：继续完成开户，但不打印存单，否：退出.");
			confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					clearTimeText();// 清空倒计时
					stopTimer(voiceTimer);// 关闭语音
					closeVoice();
					logger.info("选择是按钮，不打印存单");
					AccountTradeCodeAction.transBean.setCertPrint("0");//不需要打印存单
					
					// 跳转
					params.put("resultCode", "0000");
					params.put("transCode", "0003");
					params.put("CHL_ID", "5");//协议存款
					AccountTradeCodeAction.transBean.setProductType("");//清空整存整取金额大于5万的标识
					AccountTradeCodeAction.transBean.setChlId("5");
					AccountTradeCodeAction.transBean.getReqMCM001().setTranscode("11020");
					AccountTransFlow.startTransFlow(comp, params);
					
				}
			});
			confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					logger.info("选择否按钮，退出业务");
					clearTimeText();// 清空倒计时
					stopTimer(voiceTimer);// 关闭语音
					closeVoice();
					AccountTradeCodeAction.transBean.getReqMCM001().setTranscode("11020");
					openPanel(new OutputBankCardPanel());
				}
			});
			return;
		}
		
		clearTimeText();// 清空倒计时
		stopTimer(voiceTimer);// 关闭语音
		closeVoice();
		params.put("resultCode", "0000");
		params.put("transCode", "0003");
		params.put("CHL_ID", "5");//协议存款
		AccountTradeCodeAction.transBean.setProductType("");//清空整存整取金额大于5万的标识
		AccountTradeCodeAction.transBean.setChlId("5");
		AccountTradeCodeAction.transBean.getReqMCM001().setTranscode("11020");
		AccountTransFlow.startTransFlow(comp, params);	
	}
	
	/**
	 * 协议存款
	 */
	private void ProtocolDeposit(final Component comp,
			final Map<Object, Object> params) {
		logger.info("进入协议存款页面的方法");
		
		//调用凭证号查询
		if(!checkCertNo(AccountTradeCodeAction.transBean)){
			openConfirmDialog("凭证号不足，无法打印存单，请联系大堂经理添加凭证号，是否继续操作？是：继续完成开户，但不打印存单，否：退出.");
			confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					clearTimeText();// 清空倒计时
					stopTimer(voiceTimer);// 关闭语音
					closeVoice();
					logger.info("选择是按钮，不打印存单");
					AccountTradeCodeAction.transBean.setCertPrint("0");//不需要打印存单
					
					// 跳转
					params.put("resultCode", "0000");
					params.put("transCode", "0003");
					params.put("CHL_ID", "1");//协议存款
					AccountTradeCodeAction.transBean.setProductType("");//清空整存整取金额大于5万的标识
					AccountTradeCodeAction.transBean.setChlId("1");
					AccountTradeCodeAction.transBean.getReqMCM001().setTranscode("11018");
					AccountTransFlow.startTransFlow(comp, params);
					
				}
			});
			confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					logger.info("选择否按钮，退出业务");
					clearTimeText();// 清空倒计时
					stopTimer(voiceTimer);// 关闭语音
					closeVoice();
					AccountTradeCodeAction.transBean.getReqMCM001().setTranscode("11018");
					openPanel(new OutputBankCardPanel());
				}
			});
			return;
		}
		
		
		clearTimeText();// 清空倒计时
		stopTimer(voiceTimer);// 关闭语音
		closeVoice();
		// 跳转
		params.put("resultCode", "0000");
		params.put("transCode", "0003");
		params.put("CHL_ID", "1");//协议存款
		AccountTradeCodeAction.transBean.setProductType("");//清空整存整取金额大于5万的标识
		AccountTradeCodeAction.transBean.getReqMCM001().setTranscode("11018");
		AccountTradeCodeAction.transBean.setChlId("1");
		AccountTransFlow.startTransFlow(comp, params);		
	}

	/**
	 * 私行快线
	 */
	private void PrivateDeposit(final Component comp,
			final Map<Object, Object> params) {
		logger.info("进入跳转到私行快线页面的方法");
		//调用凭证号查询
		if(!checkCertNo(AccountTradeCodeAction.transBean)){
			openConfirmDialog("凭证号不足，无法打印存单，请联系大堂经理添加凭证号，是否继续操作？是：继续完成开户，但不打印存单，否：退出.");
			confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					clearTimeText();// 清空倒计时
					stopTimer(voiceTimer);// 关闭语音
					closeVoice();
					logger.info("选择是按钮，不打印存单");
					AccountTradeCodeAction.transBean.setCertPrint("0");//不需要打印存单
					
					// 跳转
					params.put("resultCode", "0000");
					params.put("transCode", "0003");
					params.put("CHL_ID", "2");//协议存款
					AccountTradeCodeAction.transBean.setProductType("");//清空整存整取金额大于5万的标识
					AccountTradeCodeAction.transBean.getReqMCM001().setTranscode("11019");
					AccountTransFlow.startTransFlow(comp, params);
					
				}
			});
			confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					logger.info("选择否按钮，退出业务");
					clearTimeText();// 清空倒计时
					stopTimer(voiceTimer);// 关闭语音
					closeVoice();
					AccountTradeCodeAction.transBean.getReqMCM001().setTranscode("11019");
					openPanel(new OutputBankCardPanel());
				}
			});
			return;
		}
		
		clearTimeText();// 清空倒计时
		stopTimer(voiceTimer);// 关闭语音
		closeVoice();
		// 跳转
		params.put("resultCode", "0000");
		params.put("transCode", "0003");
		params.put("CHL_ID", "2");//私行标识
		AccountTradeCodeAction.transBean.setProductType("");//清空整存整取金额大于5万的标识
		AccountTradeCodeAction.transBean.getReqMCM001().setTranscode("11019");
		AccountTransFlow.startTransFlow(comp, params);		
	}

	
}