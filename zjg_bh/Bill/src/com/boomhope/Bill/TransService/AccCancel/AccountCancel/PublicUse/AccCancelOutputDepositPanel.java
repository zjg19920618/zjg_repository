package com.boomhope.Bill.TransService.AccCancel.AccountCancel.PublicUse;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseLoginFrame;
import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Public.GlobalParameter;
import com.boomhope.Bill.PublicControl.OutputBankCardPanel;
import com.boomhope.Bill.Util.UtilVoice;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

/**
 * 存单退出
 * 
 * @author wang.sk
 * 
 */
public class AccCancelOutputDepositPanel extends BaseTransPanelNew {
	final int voiceSecond = 500;
	UtilVoice utilVoice = null;
	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(AccCancelOutputDepositPanel.class);

	public AccCancelOutputDepositPanel() {
		/*显示时间倒计时*/
		this.showTimeText(BaseTransPanelNew.delaySecondShortTime);
		delayTimer = new Timer(BaseTransPanelNew.delaySecondShortTime*1000,new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				logger.info("退出存单页面倒计时结束");
				clearTimeText();
				//退出
				accCancelExit();
			}
			
		});
		delayTimer.start();
		/* 倒计时打开语音 */
		voiceTimer = new Timer(voiceSecond, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stopTimer(voiceTimer);//关语音
            	closeVoice();//关语音流
				excuteVoice("voice/billOut.wav");

			}
		});
		voiceTimer.start();
	
		/* 标题信息 */
		JLabel t = new JLabel("存单已退出，请妥善保管");
		t.setHorizontalAlignment(JLabel.CENTER);
		t.setFont(new Font("微软雅黑", Font.BOLD, 40));
		t.setBounds(0, 250, GlobalParameter.TRANS_WIDTH - 50, 40);
		this.showJpanel.add(t);
	
		//退出
		JLabel label_1 = new JLabel(new ImageIcon("pic/newPic/exit.png"));
		label_1.setBounds(1258, 770, 150, 50);
		add(label_1);
		label_1.addMouseListener(new MouseAdapter(){
			public void mouseReleased(MouseEvent e){
				logger.info("点击退出按钮");
				clearTimeText();
				//退出
				accCancelExit();
			}
		});
		
		//退出存单
		try {
			outBill();
			
			//成功退存单、退卡 后可执行子业务跳转
			if(!GlobalParameter.ACC_STATUS && !GlobalParameter.CARD_STATUS){
				GlobalParameter.OFF_ON=true;
			}
			
			//存单退出，变更存单状态
			GlobalParameter.ACC_STATUS=false;
		} catch (Exception e1) {
			logger.error("回单模块异常，退存单失败"+e1);
			serverStop("存单未退出，请联系工作人员解决","","");//退存单失败页面
		}
		
	}
	
}
