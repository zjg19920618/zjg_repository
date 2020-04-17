package com.boomhope.Bill.TransService.BillAddPwd.ServicePanel;

import java.awt.Color;
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
import com.boomhope.Bill.Util.UtilButton;

public class SetPwdNoPassPanel extends BaseTransPanelNew{
	static Logger logger = Logger.getLogger(SetPwdNoPassPanel.class);
	private static final long serialVersionUID = 1L;
	private boolean on_off=true;//用于控制页面跳转的开关
	JLabel depoLum ;
	JLabel errLabel ;
	JLabel errLabe2 ;
	public SetPwdNoPassPanel() {
		this.showTimeText(delaySecondShortTime);
		delayTimer = new Timer(delaySecondShortTime * 1000,new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/* 倒计时结束退出交易 */ 
				clearTimeText();//清空倒计时
            	serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！","","");
			}
		});
		delayTimer.start();
		         // 标题
				depoLum = new JLabel("您设置的密码两次不一致，请核实后重新设定！");
				depoLum.setBounds(0, 30, 1009, 180);
				this.showJpanel.add(depoLum);
				depoLum.setFont(new Font("微软雅黑", Font.BOLD, 40));
				depoLum.setHorizontalAlignment(0);
				depoLum.setForeground(Color.decode("#412174"));
				depoLum.setHorizontalAlignment(JLabel.CENTER);
				
				/* 重新设置密码 */
				UtilButton trueButton = new UtilButton("pic/szxmm.png","pic/szxmm.png");
				trueButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						
						if(!on_off){
							logger.info("开关当前状态"+on_off);
							return;
						}
						logger.info("开关当前状态"+on_off);
						on_off=false;
						addPwdBean.setFristPassword("");
						clearTimeText();
						stopTimer(voiceTimer);//关闭语音
		            	closeVoice();//关闭语音流
		            	openPanel(new SetPwdinputPassPanel());
					}

				});
				trueButton.setBounds(160, 280, 200, 90);
				this.showJpanel.add(trueButton);
				errLabel = new JLabel("输入新的密码");
				errLabel.setHorizontalAlignment(JLabel.CENTER);
				errLabel.setFont(new Font("微软雅黑", Font.PLAIN,14));
				errLabel.setBounds(135, 360, 250, 40);
				errLabel.setVisible(true);
				this.showJpanel.add(errLabel);
				/* 继续设置密码*/
				UtilButton falseButton = new UtilButton("pic/jxszmima.png", "pic/jxszmima.png");
				falseButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						if(!on_off){
							logger.info("开关当前状态"+on_off);
							return;
						}
						logger.info("开关当前状态"+on_off);
						on_off=false;
						clearTimeText();
						stopTimer(voiceTimer);//关闭语音
		            	closeVoice();//关闭语音流
		            	openPanel(new SetPwdinputPassPanell());
					}

				});
				falseButton.setBounds(640, 280, 200, 90);
				this.showJpanel.add(falseButton);
				errLabe2 = new JLabel("输入与第一次相同的密码");
				errLabe2.setHorizontalAlignment(JLabel.CENTER);
				errLabe2.setFont(new Font("微软雅黑", Font.PLAIN,14));
				errLabe2.setBounds(620, 360, 250, 40);
				errLabe2.setVisible(true);
				this.showJpanel.add(errLabe2);
				// 返回
				JLabel backButton = new JLabel(new ImageIcon("pic/newPic/exit.png"));
				backButton.setBounds(1258, 770, 150, 50);
				backButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						if(!on_off){
							logger.info("开关当前状态"+on_off);
							return;
						}
						logger.info("开关当前状态"+on_off);
						on_off=false;
						//清空倒计时和语音
						closeVoice();
						clearTimeText();
						stopTimer(voiceTimer);
						
						accCancelExit();
					}
				});
				add(backButton);
	}
}
