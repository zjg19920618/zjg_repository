package com.boomhope.Bill.TransService.AccCancel.AccountCancel.PublicUse;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
/**
 * 凭条打印中
 * @author zjg
 *
 */
public class AccCancelPtPrintProcessingPanel extends BaseTransPanelNew {
	static Logger logger = Logger.getLogger(AccCancelPtPrintProcessingPanel.class);
	private static final long serialVersionUID = 1L;
	private Component comp;
	
	public AccCancelPtPrintProcessingPanel() {
		logger.info("进入凭条打印处理页面");
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
            	PtPrint();
            	
            }      
        });            
		voiceTimer.start();
		// 标题
		JLabel depoLum = new JLabel("凭条正在打印中，请稍候......");
		depoLum.setFont(new Font("微软雅黑", Font.BOLD, 40));
		depoLum.setHorizontalAlignment(0);
		depoLum.setForeground(Color.decode("#412174"));
		depoLum.setBounds(0, 220, 1009, 60);
		this.showJpanel.add(depoLum);
		
	}	
	
	/**
	 * 凭条打印处理
	 */
	public void PtPrint(){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				clearTimeText();
				//进入流程处理凭条打印
				noAccAction.xhPrint(comp);
			}
		}).start();
	}
}


