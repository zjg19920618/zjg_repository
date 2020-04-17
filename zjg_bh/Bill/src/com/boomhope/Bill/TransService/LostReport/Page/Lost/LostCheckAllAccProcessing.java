package com.boomhope.Bill.TransService.LostReport.Page.Lost;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;

/**
 * @Description:查询账户信息处理中
 * @author zjg
 * @date 2018年3月21日 下午17:41:51
 */
@SuppressWarnings({"static-access","unused"})
public class LostCheckAllAccProcessing extends BaseTransPanelNew {
	
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(LostShowAccNoPage.class);
	
	public LostCheckAllAccProcessing(){
		
		baseTransBean.setThisComponent(this);
		
		/*显示时间倒计时*/
		this.showTimeText(BaseTransPanelNew.delaySecondLongestTime);
		delayTimer = new Timer(BaseTransPanelNew.delaySecondLongestTime * 1000,new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				/*倒计时结束退出交易*/
				clearTimeText();
				serverStop("温馨提示：服务已超时，请结束您的交易！","","");
			}
		});
		delayTimer.start();
		
		/* 倒计时打开语音 */
		voiceTimer = new Timer(BaseTransPanelNew.voiceSecond, new ActionListener() {          
            public void actionPerformed(ActionEvent e) { 
            	stopTimer(voiceTimer);//关闭语音
            	closeVoice();//关闭语音流
            	excuteVoice("voice/checkAcc.wav");
            	checkAllAcc();
            }      
        });            
		voiceTimer.start();
		
		// 标题
		JLabel depoLum = new JLabel("账户信息查询中,请稍候......");
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
	 * 查询账户信息
	 */
	public void checkAllAcc(){
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				//忘记密码直接查询符合条件的账号信息
				lostAction.chenkAcc();
			}
		}).start();
	}
}
