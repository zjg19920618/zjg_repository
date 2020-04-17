package com.boomhope.Bill.TransService.BillChOpen.BillChangeOpen.chenckInfo;

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
import com.boomhope.Bill.TransService.BillChOpen.BillChangeOpen.HaveAcc.BillChangeOpenCheckBillPanel;
import com.boomhope.Bill.Util.UtilButton;

/**
 * 代理人选择页
 * @author wxm
 *
 */
public class BillChangeOpenDeputySelectionPanel extends BaseTransPanelNew{
	static Logger logger = Logger.getLogger(BillChangeOpenDeputySelectionPanel.class);
	private static final long serialVersionUID = 1L;
	private Component thisComp;
	private boolean on_off=true;//用于控制页面跳转的开关
	public BillChangeOpenDeputySelectionPanel(){
		logger.info("进入代理人选择页面");
		thisComp = this;
		
		//将当前页面传入流程控制进行操作
		final Component comp=this;
		/* 倒计时打开语音 */
		voiceTimer = new Timer(voiceSecond, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {  
            	stopTimer(voiceTimer);//关语音
            	closeVoice();//关语音流
            	excuteVoice("voice/others.wav");
            	
            }      
        });            
		voiceTimer.start(); 
		
		this.showTimeText(delaySecondShortTime);
		delayTimer = new Timer(delaySecondShortTime * 1000, new ActionListener() {          
            public void actionPerformed(ActionEvent e) { 
            	logger.info("代理人选择页面倒计时结束 ");
            	/* 倒计时结束退出交易 */ 
            	clearTimeText();//清空倒计时
            	serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！","","");
            }      
        }); 
		delayTimer.start(); 
		/* 标题信息 */
		JLabel titleLabel = new JLabel("您是否代理他人办理业务");
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		titleLabel.setForeground(Color.decode("#412174"));
		titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 36));
		titleLabel.setBounds(0, 80, 1009, 40);
		this.showJpanel.add(titleLabel);
		
		/* 是 */
		UtilButton trueButton = new UtilButton("pic/shi.png","pic/shi.png");
		trueButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("选择有代理人");
				agent(comp);
			}

		});
		trueButton.setBounds(160, 200, 200, 60);
		this.showJpanel.add(trueButton);
		
		/* 否 */
		UtilButton falseButton = new UtilButton("pic/fou.png", "pic/fou.png");
		falseButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("选择无代理人");
				me(comp);
			}

		});
		falseButton.setBounds(640, 200, 200, 60);
		this.showJpanel.add(falseButton);
		
		// 上一步
		JLabel submitBtn = new JLabel(new ImageIcon("pic/preStep.png"));
		submitBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击上一步按钮");
				/* 处理上一页 */
				preStep();
			}
		});
		submitBtn.setSize(150, 50);
		submitBtn.setLocation(1075, 770);
		add(submitBtn);

		// 退出
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
				logger.info("点击退出方法");
				clearTimeText();
				closeVoice();
				stopTimer(voiceTimer);
				//退出
				billHKExit();
			}
		});
		add(backButton);
		
	}
	/**
	 * 代理人
	 */
	public void agent(final Component comp){
		if(!on_off){
			logger.info("开关当前状态"+on_off);
			return;
		}
		logger.info("开关当前状态"+on_off);
		on_off=false;
		logger.info("进入有代理人方法");
		clearTimeText();
		closeVoice();
		stopTimer(voiceTimer);
		bcOpenBean.setHavAgentFlag("1");//有代理人
		//代理人身份验证页面
		openPanel(new BillChangeOpenInputIdCardPanel());
		
	}
	
	/**
	 * 本人
	 */
	public void me(final Component comp){
		if(!on_off){
			logger.info("开关当前状态"+on_off);
			return;
		}
		logger.info("开关当前状态"+on_off);
		on_off=false;
		logger.info("进入无代理人方法 ");
		closeVoice();
		clearTimeText();
		stopTimer(voiceTimer);
		bcOpenBean.setHavAgentFlag("0");//无代理人
		openPanel(new BillChangeOpenInputIdCardPanel());
	}
	
	//返回上一步
	public void preStep(){
		if(!on_off){
			logger.info("开关当前状态"+on_off);
			return;
		}
		logger.info("开关当前状态"+on_off);
		on_off=false;
		logger.info("返回插入本人身份证页面");
		clearTimeText();
		closeVoice();
		stopTimer(voiceTimer);
		
		

			
		//返回卡下子账户信息页
		openPanel(new BillChangeOpenCheckBillPanel());
			
		
			
		
	};
	
}
