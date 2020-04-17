package com.boomhope.Bill.TransService.AccOpen.View.ServicePage.Account.ProInformationInputPanel;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.PublicControl.OutputBankCardPanel;
import com.boomhope.Bill.TransService.AccOpen.Bean.AccPublicBean;
import com.boomhope.Bill.TransService.AccOpen.View.ServicePage.Account.Author.AccCheckAgentPhotos;
import com.boomhope.Bill.TransService.AccOpen.View.ServicePage.Account.Author.AccCheckPhotos;
import com.boomhope.Bill.TransService.AccOpen.controller.AccountTradeCodeAction;


/***
 * 收益人信息确认页面
 * @author hk
 *
 */
public class AccSyrPagesPanel extends BaseTransPanelNew{
	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(AccSyrPagesPanel.class);
	
	AccPublicBean accBean;
	private Map<Object,Object> params;
	private boolean on_off=true;
	
	public AccSyrPagesPanel(Map<Object,Object> params){
		logger.info("进入收益人信息确认页面");
		
		this.params =params;
		accBean = AccountTradeCodeAction.transBean;
		
		/* 显示时间倒计时 */
		if(delayTimer!=null){
			clearTimeText();
		}
		this.showTimeText(delaySecondShortTime);
		delayTimer = new Timer(delaySecondShortTime * 1000, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {  
            	logger.info("收益人信息确认页面倒计时结束 ");
            	/* 倒计时结束退出交易 */ 
            	clearTimeText();
            	serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！","","");
            }      
        });            
		delayTimer.start(); 
		
		JLabel lblNewLabel = new JLabel("收益人信息");
		lblNewLabel.setFont(new Font("微软雅黑", Font.BOLD, 40));
		lblNewLabel.setForeground(Color.decode("#412174"));
		lblNewLabel.setHorizontalAlignment(0);
		lblNewLabel.setBounds(0, 60, 1009, 60);
		this.showJpanel.add(lblNewLabel);
		
		JLabel label = new JLabel("收益人卡号：");
		label.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		label.setBounds(314, 256, 152, 40);
		this.showJpanel.add(label);
		
		JLabel label_1 = new JLabel("收益人：");
		label_1.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		label_1.setBounds(359, 340, 96, 38);
		this.showJpanel.add(label_1);
		JLabel lblNewLabel_1 = new JLabel(accBean.getInputNo());
		lblNewLabel_1.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		lblNewLabel_1.setBounds(476, 256, 347, 40);
		this.showJpanel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel();
		lblNewLabel_2.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		lblNewLabel_2.setBounds(465, 340, 191, 34);
		this.showJpanel.add(lblNewLabel_2);
		
		//显示收益人姓名
		String str1=accBean.getInputName();
		lblNewLabel_2.setText(str1.replace(str1.substring(0, 1),"*"));
		logger.info("收益人姓名："+str1);
		
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
				backTrans();
			}
		});
		submitBtn.setSize(150, 50);
		submitBtn.setLocation(1075, 770);
		add(submitBtn);

		// 确认
		JLabel confirm = new JLabel(new ImageIcon("pic/newPic/confirm.png"));
		confirm.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击确认按钮");
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				/* 处理下一页 */
				okTrans();
			}
		});
		confirm.setSize( 150, 50);
		confirm.setLocation(890, 770);
		add(confirm);
		
		// 返回
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
				clearTimeText();
				stopTimer(voiceTimer);//关闭语音
		    	closeVoice();
				openPanel(new OutputBankCardPanel());
			}
		});
		add(backButton);
	}
	/**
	 * 返回
	 * */
	public void backTrans() {
		logger.info("进入返回上一步的方法");
		clearTimeText();//清空倒计时
		stopTimer(voiceTimer);//关语音
		closeVoice();//关语音流
		
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
				if("1".equals(accBean.getIsCheckedPic())||"2".equals(accBean.getIsCheckedPic())){
					if("0".equals(AccountTradeCodeAction.transBean.getHaveAgentFlag())){
						openPanel(new AccCheckAgentPhotos(params));
					}else{
						openPanel(new AccCheckPhotos(params));
					}
				}else{
					openPanel(new AccInputBankMsgPanel(params));
				}
			}
		});
	}
	
	/**
	 * 确认
	 * */
	public void okTrans() {
		logger.info("进入确认的方法");
		clearTimeText();//清空倒计时
		stopTimer(voiceTimer);//关语音
		closeVoice();//关语音流
		
		openPanel(new AccOkInputDepInfoPanel(params));
		
	}
	
}

