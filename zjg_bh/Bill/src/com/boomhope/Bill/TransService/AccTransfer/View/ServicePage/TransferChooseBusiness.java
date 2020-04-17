package com.boomhope.Bill.TransService.AccTransfer.View.ServicePage;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.TransService.AccTransfer.Bean.PublicAccTransferBean;
import com.boomhope.Bill.TransService.AccTransfer.View.ServicePage.CardConfirm.TransferInputBankCardPanel;



/**
 * 首页进入业务选择页面
 * @author hk
 *
 */

public class TransferChooseBusiness extends BaseTransPanelNew{
	static Logger logger = Logger.getLogger(TransferChooseBusiness.class);
	private static final long serialVersionUID = 1L;
	private boolean on_off=true;//用于控制页面跳转人开关
	
	public TransferChooseBusiness(final PublicAccTransferBean transferBean){
		logger.info("进行汇款业务选择的页面：");
		final Component comp = this;

		//设置倒计时
		this.showTimeText(100);
		delayTimer = new Timer(100 * 1000, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {  
            	/* 倒计时结束退出交易 */ 
            	clearTimeText();
            	serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！","","");
            }      
        });            
		delayTimer.start(); 
		/* 倒计时打开语音 */
		voiceTimer = new Timer(voiceSecond, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {  
            	stopTimer(voiceTimer);
            	excuteVoice("voice/OpenAccType.wav");
            	
            }      
        });            
		voiceTimer.start(); 
		/* 标题信息 */
		JLabel titleLabel = new JLabel("转账类型");
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		titleLabel.setForeground(Color.decode("#412174"));
		titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 40));
		titleLabel.setBounds(0, 60, 1009, 60);
		this.showJpanel.add(titleLabel);
		//个人汇划
		JLabel jlabel1 = new JLabel();
		jlabel1.setBounds(220, 174, 200, 300);
		jlabel1.setIcon(new ImageIcon("pic/newPic/personal.png"));
		jlabel1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击个人汇划业务");
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				//页面跳转前清空倒计时
				clearTimeText();
				//跳转页面的方法
				transferBean.setIsCardBin("0");//选择个人转账
				transferBean.getReqMCM001().setTranscode("11031");
				openPanel(new TransferInputBankCardPanel(new HashMap(),transferBean));
			}
		});
		this.showJpanel.add(jlabel1);
		
		//单位汇划
		JLabel jlabel2 = new JLabel();
		jlabel2.setIcon(new ImageIcon("pic/newPic/companyAccount.png"));
		jlabel2.setBounds(585, 174, 200, 300);
		jlabel2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击单位汇划按钮");
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				//页面跳转前清空倒计时
				clearTimeText();
				//跳转页面的方法
				transferBean.setIsCardBin("1");//选择单位转账
				transferBean.getReqMCM001().setTranscode("11032");
				openPanel(new TransferInputBankCardPanel(new HashMap(),transferBean));
			}
		});
		this.showJpanel.add(jlabel2);
		
		//返回
		JLabel button = new JLabel();
		button.setIcon(new ImageIcon("pic/newPic/exit.png"));
		button.setBounds(1258, 770, 150, 50);
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击退出按钮");
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				//页面跳转前清空倒计时
				clearTimeText();
				//跳转页面的方法
				returnHome();
			}
		});
		add(button);
		
			
		
	}

}
