package com.boomhope.Bill.TransService.AccCancel.AccountCancel.HaveAcc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Map;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Driver.KeypadDriver;
import com.boomhope.Bill.Driver.NewKeypadDriver;
import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Public.GlobalPanelFlag;
import com.boomhope.Bill.Public.GlobalParameter;
import com.boomhope.Bill.TransService.AccCancel.AccountCancel.PublicUse.AccCancelDempMethodPanel;
import com.boomhope.Bill.Util.SoftKeyBoardPopups1;
import com.boomhope.Bill.Util.SoftKeyBoardPopups2;
import com.boomhope.Bill.Util.UtilButton;
import com.boomhope.Bill.Util.UtilVoice;

/**
 * 输入银行卡卡号页面
 * @author hao
 *
 */

public class AccCancelHandInputBankCardPanel extends BaseTransPanelNew {
	
	static Logger logger = Logger.getLogger(AccCancelHandInputBankCardPanel.class);
	private static final long serialVersionUID = 1L;
	//密码文本框
	public JTextField textField;
	private JPanel passwordPanel;
	private SoftKeyBoardPopups2 keyPopup;
	private JLabel errInfo;// 错误提示
	private boolean on_off=true;//用于控制页面跳转的开关

	/***
	 * 初始化
	 */
	public AccCancelHandInputBankCardPanel(){
		/*显示时间倒计时*/
		this.showTimeText(BaseTransPanelNew.delaySecondLongTime);
		delayTimer = new Timer(BaseTransPanelNew.delaySecondLongTime*1000,new ActionListener(){

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
            	closeVoice();
            	if("1".equals(accCancelBean.getSecondCardNocard())){
            		//两次录入不一致语音提示
            		excuteVoice("voice/cardNoSecondCard.wav");
            	}else{
            		
            		excuteVoice("voice/once.wav");
            	}
            }      
        });            
		voiceTimer.start();
		
		/*提示信息*/
		JLabel titleLabel = new JLabel("请录入银行卡卡号");
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		titleLabel.setFont(new Font("微软雅黑", Font.BOLD,40));
		titleLabel.setForeground(Color.decode("#412174"));
		titleLabel.setBounds(0, 80, GlobalParameter.TRANS_WIDTH-50, 60);
		this.showJpanel.add(titleLabel);
	
		 //输入框
        passwordPanel = new JPanel(new BorderLayout());  
        passwordPanel.setBackground(Color.WHITE);  
        passwordPanel.setPreferredSize(new Dimension(2024, 30));  
        passwordPanel.setLayout(new BorderLayout());  
        passwordPanel.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));  
        this.showJpanel.add(passwordPanel); 
        
		textField = new JTextField();
		textField.setFont(new Font("微软雅黑", Font.PLAIN, 40));
		textField.setBounds(255, 297, 562, 50);
		textField.setEditable(false);
		textField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				keyPopup.show(passwordPanel,250,350);
					
				
			}

		});
		textField.setColumns(10);
		this.showJpanel.add(textField);
		keyPopup = new SoftKeyBoardPopups2(textField);
		
		errInfo = new JLabel("提示：请录入银行卡号!");
		errInfo.setVisible(false);
		errInfo.setForeground(Color.RED);
		errInfo.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		errInfo.setBounds(260, 357, 258, 23);
		if("1".equals(accCancelBean.getSecondCardNocard())){
			errInfo.setText("两次录入的卡号不一致，请重新录入");
			errInfo.setVisible(true);
		}
		this.showJpanel.add(errInfo);
		
		//上一步
		JLabel label = new JLabel(new ImageIcon("pic/newPic/preStep.png"));
		label.setBounds(1075, 770, 150, 50);
		label.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				backstep();
			}
		});
		add(label);
		
		//确认
		JLabel okButton = new JLabel(new ImageIcon("pic/newPic/confirm.png"));
		okButton.setBounds(890, 770, 150, 50);
		okButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				nextstep(); 
			}
		});
		add(okButton);		
		
		//退出
		JLabel label_1 = new JLabel(new ImageIcon("pic/newPic/exit.png"));
		label_1.setBounds(1258, 770, 150, 50);
		label_1.addMouseListener(new MouseAdapter(){
			public void mouseReleased(MouseEvent e){
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				closeVoice();
				clearTimeText();
				stopTimer(voiceTimer);
				accCancelExit();
			}
		});		
		add(label_1);	
		
	}
	
	/***
	 * 下一步
	 */
	private void nextstep(){
    	
    	String str = textField.getText();
		logger.debug("银行卡号第一次输入"+str);
		if(str == null || "".equals(str)){
			errInfo.setText("提示：请录入银行卡号!");
			errInfo.setVisible(true);			
			return;
		}
		if(!on_off){
			logger.info("开关当前状态"+on_off);
			return;
		}
		logger.info("开关当前状态"+on_off);
		on_off=false;
		accCancelBean.setFirstCardNo(str);
		accCancelBean.setSecondCardNocard("");//清空标识
		
		clearTimeText();//清空倒计时
		stopTimer(voiceTimer);//关闭语音
    	closeVoice();

		//跳转再次录入银行卡号
		openPanel(new AccCancelHandSecondInputBankCardPanel());
	}
	
	/**
	 * 上一步
	 * @return
	 */
	private void backstep(){
		if(!on_off){
			logger.info("开关当前状态"+on_off);
			return;
		}
		logger.info("开关当前状态"+on_off);
		on_off=false;
		clearTimeText();//清空倒计时
		stopTimer(voiceTimer);//关闭语音
    	closeVoice();

    	accCancelBean.setSecondCardNocard("");//清空标识
		//跳转录入卡号选择页
		openPanel(new AccCancelSelectMethodPanel());
	}
	
	public JTextField getTextField() {
		return textField;
	}

	public void setTextField(JTextField textField) {
		this.textField = textField;
	}
	
}