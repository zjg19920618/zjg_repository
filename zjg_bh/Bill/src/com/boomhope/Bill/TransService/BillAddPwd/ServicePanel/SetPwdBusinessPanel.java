package com.boomhope.Bill.TransService.BillAddPwd.ServicePanel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
/**
 * 业务处理中
 * @author hao
 *
 */
public class SetPwdBusinessPanel extends BaseTransPanelNew {
	static Logger logger = Logger.getLogger(SetPwdBusinessPanel.class);
	private static final long serialVersionUID = 1L;
	private Component comp;
	
	public SetPwdBusinessPanel() {
		logger.info("进入增设密码");
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
            	closeVoice();//关语音流
            	excuteVoice("voice/zsmmclz.wav");
            	//增设密码处理
            	ZSDeal(comp);
            	
            }      
        });            
		voiceTimer.start();
		// 标题
		JLabel depoLum = new JLabel("增设密码处理中，请稍候......");
		depoLum.setFont(new Font("微软雅黑", Font.BOLD, 40));
		depoLum.setHorizontalAlignment(0);
		depoLum.setForeground(Color.decode("#412174"));
		depoLum.setBounds(0, 210, 1009, 60);
		this.showJpanel.add(depoLum);
		
	}	
	
	/**
	 * 增设密码
	 */
	public void ZSDeal(final Component comp){
		openProssDialog();
		new Thread(new Runnable() {	
			@Override
			public void run() {
				
				//清空倒计时
				clearTimeText();
				stopTimer(voiceTimer);
				closeVoice();
				//增设密码处理
				addPwdAction.ZSPassWord(comp,addPwdBean);
			}
		}).start();
	}
}


