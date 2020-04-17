package com.boomhope.Bill.TransService.AccCancel.AccountCancel.PublicUse;

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
 * 销户处理中
 * @author hao
 *
 */
public class AccCancelBusinessPanel extends BaseTransPanelNew {
	static Logger logger = Logger.getLogger(AccCancelBusinessPanel.class);
	private static final long serialVersionUID = 1L;
	private Component comp;
	
	public AccCancelBusinessPanel() {
		logger.info("进入销户处理页面");
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
            	//销户处理
            	XhDeal();
            	
            }      
        });            
		voiceTimer.start();
		// 标题
		JLabel depoLum = new JLabel("销户正在处理中，请稍候......");
		depoLum.setFont(new Font("微软雅黑", Font.BOLD, 40));
		depoLum.setHorizontalAlignment(0);
		depoLum.setForeground(Color.decode("#412174"));
		depoLum.setBounds(0, 220, 1009, 60);
		this.showJpanel.add(depoLum);
		
	}	
	
	/**
	 * 销户处理
	 */
	public void XhDeal(){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				clearTimeText();
				//进入流程处理销户业务
				noAccAction.xhBusinessDeal(comp);
			}
		}).start();
	}
}


