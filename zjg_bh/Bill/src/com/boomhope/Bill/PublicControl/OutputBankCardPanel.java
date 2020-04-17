package com.boomhope.Bill.PublicControl;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseContentPanel;
import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Public.GlobalParameter;

/***
 * 银行卡退出界面Panel
 * @author hao
 *
 */
public class OutputBankCardPanel extends BaseTransPanelNew{
	static Logger logger = Logger.getLogger(OutputBankCardPanel.class);
	private static final long serialVersionUID = 1L;
	
	/***
	 * 初始化
	 */
	public OutputBankCardPanel(){
		logger.info("进入退出银行卡页面");
		this.showTimeText(delaySecondShortTime);
		delayTimer = new Timer(delaySecondShortTime * 1000, new ActionListener() {          
	        public void actionPerformed(ActionEvent e) {  
	        	/* 倒计时结束退出交易 */ 
            	clearTimeText();
            	returnHome();
	        }      
	    });            
		delayTimer.start(); 
		

		/* 倒计时打开语音 */
		voiceTimer = new Timer(voiceSecond, new ActionListener() {          
	        public void actionPerformed(ActionEvent e) {  
	        	stopTimer(voiceTimer);//关闭语音
	        	closeVoice();
	        	try {
					closeLed("2");
				} catch (Exception e1) {
					logger.error("LED灯关闭失败"+e);
				}//关银行卡led灯
	        	excuteVoice("voice/quit.wav");//执行语音
	        }      
	    });            
		voiceTimer.start();
		
		
		/* 标题信息 */
		JLabel titleLabel = new JLabel("银行卡已退出，请妥善保管");
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 40));
		titleLabel.setBounds(0, 100, GlobalParameter.TRANS_WIDTH-50, 40);
		this.showJpanel.add(titleLabel);
		
		/* 加载凭证动画 */
		JLabel billImage = new JLabel();   
		billImage.setIcon(new ImageIcon("pic/output_bank.gif"));
		billImage.setIconTextGap(6);
		billImage.setBounds(325, 190, 362, 320);
		this.showJpanel.add(billImage);
		
		//返回广告页
		JButton backButton = new JButton();
		backButton.setIcon(new ImageIcon("pic/newPic/exit.png"));
		backButton.setBounds(1258, 770, 150, 50);
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clearTimeText();
				stopTimer(voiceTimer);//关闭语音
				returnHome();
			}
		});
		add(backButton);				
		//退卡
		try {
			quitBankCard();
			//成功退卡 后可执行子业务跳转
			GlobalParameter.OFF_ON=true;
			//变更为无卡状态
			GlobalParameter.CARD_STATUS=false;
		} catch (Exception e) {
			logger.error("读卡器异常，退卡失败"+e);
			serverStop("非常抱歉，读卡器出问题了，退卡失败，请联系工作人员解决。","","");//退卡失败页面
		}
	}
}
