package com.boomhope.Bill.TransService.AccCancel.AccountCancel.HaveAcc;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;

/**
 * 查询银行卡信息
 * @author zy
 *
 */
@SuppressWarnings("serial")
public class AccCancelCheckingBankcardPanel extends BaseTransPanelNew{
	
	static Logger logger = Logger.getLogger(AccCancelCheckingBankcardPanel.class);
	private Component comp;
	
	public AccCancelCheckingBankcardPanel(){
		
		comp = this;
		/*显示时间倒计时*/
		this.showTimeText(BaseTransPanelNew.delaySecondShortTime);
		delayTimer = new Timer(BaseTransPanelNew.delaySecondShortTime*1000,new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				clearTimeText();
				serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！","","");
			}
			
		});
		delayTimer.start();
		/* 倒计时打开语音 */
		voiceTimer = new Timer(BaseTransPanelNew.voiceSecond, new ActionListener() {          
            public void actionPerformed(ActionEvent e) { 
            	stopTimer(voiceTimer);//关闭语音
            	// 检查
            	check();
            }      
        });            
		voiceTimer.start();
		
		// 标题
		JLabel depoLum = new JLabel("正在校验账户信息，请稍候......");
		depoLum.setFont(new Font("微软雅黑", Font.BOLD, 40));
		depoLum.setHorizontalAlignment(0);
		depoLum.setForeground(Color.decode("#412174"));
		depoLum.setBounds(0, 60, 1009, 60);
		this.showJpanel.add(depoLum);
		/* 加载凭证动画 */
		JLabel billImage = new JLabel();   
		billImage.setIcon(new ImageIcon("pic/checking.gif"));
		billImage.setIconTextGap(6);
		billImage.setBounds(404,170, 340, 253);
		this.showJpanel.add(billImage);		
		
	}
	
	/**
	 * 查询转入账户信息
	 */
	public void check(){
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				clearTimeText();
				//进入流程查询转入账户信息
				noAccAction.checkCard(comp);
			}
		}).start();
	}
}
