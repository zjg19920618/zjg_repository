package com.boomhope.Bill.TransService.BillAddPwd.ServicePanel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.TransService.BillAddPwd.Bean.AddPasswordBean;
import com.boomhope.Bill.TransService.BillAddPwd.BillAddPwdAction.AddPwdAction;
import com.boomhope.Bill.TransService.BillAddPwd.Interface.IntefaceSendMsg;

public class SetPwdCheckMePanel extends BaseTransPanelNew{
	static Logger logger = Logger.getLogger(SetPwdCheckMePanel.class);
	private static final long serialVersionUID = 1L;
	private Component comp;
	public SetPwdCheckMePanel() {
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
            	closeVoice();//关闭语音流
            	excuteVoice("voice/webCheck.wav");
            	mecheck();
            }      
        });            
		voiceTimer.start();
		// 标题
		JLabel depoLum = new JLabel("本人身份核查中,请稍候......");
		depoLum.setFont(new Font("微软雅黑", Font.BOLD, 40));
		depoLum.setHorizontalAlignment(0);
		depoLum.setForeground(Color.decode("#412174"));
		depoLum.setBounds(0, 60, 1009, 60);
		this.showJpanel.add(depoLum);
		/* 加载核查动画 */
		JLabel billImage = new JLabel();
		billImage.setIcon(new ImageIcon("pic/checking.gif"));
		billImage.setIconTextGap(6);
		billImage.setBounds(404, 170, 354, 253);
		this.showJpanel.add(billImage);
		
	}
	
	/**
	 * 本人身份信息查询
	 */
	public void mecheck(){
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				addPwdAction.mePolicecheck1(comp,addPwdBean);
		
			}
		}).start();
	}
}
