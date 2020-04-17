package com.boomhope.Bill.TransService.AccCancel.AccountCancel.PublicUse;

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
/**
 * 销户转存方式选择
 * @author hao
 *
 */
public class AccCancelDempMethodPanel extends BaseTransPanelNew {
	static Logger logger = Logger.getLogger(AccCancelDempMethodPanel.class);
	private static final long serialVersionUID = 1L;
	private boolean on_off=true;//用于控制页面跳转的开关
	public AccCancelDempMethodPanel() {
		/* 显示时间倒计时 */
		this.showTimeText(delaySecondShortTime);
		delayTimer = new Timer(delaySecondShortTime * 1000, new ActionListener() {          
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
            	stopTimer(voiceTimer);//关闭语音
            	closeVoice();//关闭语音流
            	excuteVoice("voice/choose.wav");
            	
            }      
        });            
		voiceTimer.start();
		// 标题
		JLabel depoLum = new JLabel("请选择销户后转入方式");
		depoLum.setFont(new Font("微软雅黑", Font.BOLD, 40));
		depoLum.setHorizontalAlignment(0);
		depoLum.setForeground(Color.decode("#412174"));
		depoLum.setBounds(0, 60, 1009, 60);
		this.showJpanel.add(depoLum);
		//转到银行卡
		UtilButton haveAcc = new UtilButton("pic/newPic/transBank.png",
				"pic/newPic/transBank.png");
		haveAcc.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击银行卡按钮");
				accCancelBean.setAccCancelType("001");
				AccMethod();
			}

		});
		haveAcc.setSize(200, 300);
		haveAcc.setLocation(100, 174);
		haveAcc.setIcon(new ImageIcon("pic/newPic/transBank.png"));
		this.showJpanel.add(haveAcc);
		//转至电子账户
		UtilButton noAcc = new UtilButton("pic/newPic/transElecAcc.png",
				"pic/newPic/transElecAcc.png");
		noAcc.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击电子账户按钮");
				accCancelBean.setAccCancelType("002");
				AccMethod();
			}

		});
		noAcc.setSize(200, 300);
		noAcc.setLocation(400, 174);
		noAcc.setIcon(new ImageIcon("pic/newPic/transElecAcc.png"));
		this.showJpanel.add(noAcc);
		//转到唐行宝
		UtilButton transTHB = new UtilButton("pic/newPic/transferTHB.png",
				"pic/newPic/transferTHB.png");
		transTHB.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击唐行宝按钮");
				accCancelBean.setAccCancelType("003");
				AccMethod();
			
			}

		});
		transTHB.setSize(200, 300);
		transTHB.setLocation(700, 174);
		transTHB.setIcon(new ImageIcon("pic/newPic/transferTHB.png"));
		this.showJpanel.add(transTHB);

		//退出
		JLabel label_1 = new JLabel(new ImageIcon("pic/newPic/exit.png"));
		label_1.setBounds(1258, 770, 150, 50);
		add(label_1);
		label_1.addMouseListener(new MouseAdapter(){
			public void mouseReleased(MouseEvent e){
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				clearTimeText();
				returnHome();
			}
		});


	}
	
	public void AccMethod(){
		if(!on_off){
			logger.info("开关当前状态"+on_off);
			return;
		}
		logger.info("开关当前状态"+on_off);
		on_off=false;
		logger.info("进入下一页面是否有存单页面");
		clearTimeText();
		openPanel(new AccCancelMethodPanel());
	}
	
	
}
