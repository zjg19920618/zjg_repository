package com.boomhope.Bill.TransService.AccOpen.View.ServicePage.Account.Author;

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
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.PublicControl.OutputBankCardPanel;
import com.boomhope.Bill.TransService.AccOpen.View.ServicePage.Account.AccDepoLumPanel;
import com.boomhope.Bill.TransService.AccOpen.View.ServicePage.Account.ProInformationInputPanel.AccJxcInputPanel;
import com.boomhope.Bill.TransService.AccOpen.View.ServicePage.Account.ProInformationInputPanel.AccProInputPanel;
import com.boomhope.Bill.TransService.AccOpen.View.ServicePage.Account.ProInformationInputPanel.AccRyjInputPanel;
import com.boomhope.Bill.TransService.AccOpen.controller.AccountTradeCodeAction;
import com.boomhope.Bill.TransService.AccOpen.controller.AccountTransFlow;
import com.boomhope.Bill.Util.UtilButton;

/**
 * 代理人选择页
 * @author wxm
 *
 */
public class AccDeputySelectionPanel extends BaseTransPanelNew{
	static Logger logger = Logger.getLogger(AccDeputySelectionPanel.class);
	private static final long serialVersionUID = 1L;
	private Component thisComp;
	private boolean on_off=true;
	
	public AccDeputySelectionPanel(final Map map){
		logger.info("进入代理人选择页面");
		thisComp = this;
		
		//将当前页面传入流程控制进行操作
		final Component comp=this;
		/* 倒计时打开语音 */
		voiceTimer = new Timer(voiceSecond, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {  
            	stopTimer(voiceTimer);//关语音
            	closeVoice();//关语音流
            	excuteVoice("voice/others.wav");
            	
            }      
        });            
		voiceTimer.start(); 
		/* 显示时间倒计时 */
		if(delayTimer!=null){
			clearTimeText();
		}
		this.showTimeText(delaySecondShortTime);
		delayTimer = new Timer(delaySecondShortTime * 1000, new ActionListener() {          
            public void actionPerformed(ActionEvent e) { 
            	logger.info("代理人选择页面倒计时结束 ");
            	/* 倒计时结束退出交易 */ 
            	clearTimeText();//清空倒计时
            	serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！","","");
            }      
        }); 
		delayTimer.start(); 
		/* 标题信息 */
		JLabel titleLabel = new JLabel("您是否代理他人办理业务");
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		titleLabel.setForeground(Color.decode("#412174"));
		titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 36));
		titleLabel.setBounds(0, 80, 1009, 40);
		this.showJpanel.add(titleLabel);
		
		/* 是 */
		UtilButton trueButton = new UtilButton("pic/shi.png","pic/shi.png");
		trueButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("选择有代理人");
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				agent(comp, map);
			}

		});
		trueButton.setBounds(160, 200, 200, 60);
		this.showJpanel.add(trueButton);
		
		/* 否 */
		UtilButton falseButton = new UtilButton("pic/fou.png", "pic/fou.png");
		falseButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("选择无代理人");
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				me(comp, map);
			}

		});
		falseButton.setBounds(640, 200, 200, 60);
		this.showJpanel.add(falseButton);
		
		// 上一步
		JLabel submitBtn = new JLabel(new ImageIcon("pic/preStep.png"));
		submitBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击上一步按钮");
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				/* 处理上一页 */
				preStep(map);
			}
		});
		submitBtn.setSize(150, 50);
		submitBtn.setLocation(1075, 770);
		add(submitBtn);

		// 返回
		JLabel backButton = new JLabel(new ImageIcon("pic/newPic/exit.png"));
		backButton.setBounds(1258, 770, 150, 50);
		backButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击退出方法");
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				clearTimeText();
				closeVoice();
				stopTimer(voiceTimer);
				openPanel(new OutputBankCardPanel());
			}
		});
		add(backButton);
		
	}
	/**
	 * 代理人
	 */
	public void agent(final Component comp,final Map<String, String> map){
		logger.info("进入有代理人方法");
		clearTimeText();
		closeVoice();
		stopTimer(voiceTimer);
		openProssDialog();
		map.put("haveAgentFlag", "0");
		map.put("transCode", "0007");
		AccountTransFlow.startTransFlow(comp, map);
		prossDialog.disposeDialog();
	}
	
	/**
	 * 本人
	 */
	public void me(final Component comp,final Map<String, String> map){
		logger.info("进入无代理人方法 ");
		closeVoice();
		clearTimeText();
		stopTimer(voiceTimer);
		openProssDialog();
		map.put("haveAgentFlag", "1");
		map.put("transCode", "0007");
		AccountTransFlow.startTransFlow(comp, map);
		prossDialog.disposeDialog();
	}
	
	//返回上一步
	public void preStep(Map map){
		logger.info("返回存单信息录入页面");
		clearTimeText();
		closeVoice();
		stopTimer(voiceTimer);
		AccountTradeCodeAction.transBean.setZzAmt(AccountTradeCodeAction.transBean.getBeiZzAmt());
		if("1".equals(AccountTradeCodeAction.transBean.getTransChangeNo().trim())){
			AccountTradeCodeAction.transBean.setMoreThanAmt("");
			AccountTradeCodeAction.transBean.setTransChangeNo("0");
		}
		if("JX".equals(map.get("PRODUCT_CODE"))){
			openPanel(thisComp, new AccJxcInputPanel(map));
		}else if("RJ".equals(map.get("PRODUCT_CODE"))){
			openPanel(thisComp, new AccRyjInputPanel(map));
		}else if("0010".equals(AccountTradeCodeAction.transBean.getProductCode())){
			openPanel(new AccDepoLumPanel(AccountTradeCodeAction.transBean));
		}else{
			openPanel(thisComp, new AccProInputPanel(map));
		}
	};
	
}
