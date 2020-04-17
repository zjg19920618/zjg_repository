package com.boomhope.Bill.TransService.CashOpen.moneybox;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.TransService.CashOpen.Bean.PublicCashOpenBean;
import com.boomhope.Bill.Util.SoftKeyBoardPopups;
import com.boomhope.Bill.Util.SoftKeyBoardPopups5;
import com.boomhope.Bill.Util.UtilButton;
import com.boomhope.Bill.Util.UtilVoice;

/***
 * 录入授权柜员号界面
 * @author gyw
 *
 */
public class MoneyBoxAdditionNumberPanel extends BaseTransPanelNew{

	static Logger logger = Logger.getLogger(MoneyBoxAdditionNumberPanel.class);
	private static final long serialVersionUID = 1L;
	private JTextField textField;
	JLabel errInfo = null;// 错误提示
	JLabel fingerprint = null;
	SourceDataLine line = null;
	private JPanel passwordPanel;
	private SoftKeyBoardPopups5 keyPopup;
	private boolean on_off=true;//用于控制页面跳转的开关
	/***
	 * 初始化
	 */
	public MoneyBoxAdditionNumberPanel(final PublicCashOpenBean transBean){
		
		this.cashBean = transBean;
		/* 倒计时打开语音 */
		voiceTimer = new Timer(voiceSecond, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {  
            	stopTimer(voiceTimer);
            	excuteVoice("voice/impowers.wav");
            	
            }      
        });            
		voiceTimer.start(); 
		
		/* 显示时间倒计时 */
		showTimeText(delaySecondLongTime);
		delayTimer = new Timer(delaySecondLongTime * 1000, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {  
            	/* 倒计时结束退出交易 */ 
            	clearTimeText();
            	on_off=false;
				serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！","","");
            }      
        });  
		delayTimer.start(); 
        //输入框
        passwordPanel = new JPanel(new BorderLayout());  
        passwordPanel.setBackground(Color.WHITE);  
        passwordPanel.setPreferredSize(new Dimension(2024, 30));  
        passwordPanel.setLayout(new BorderLayout());  
        passwordPanel.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));  
        this.showJpanel.add(passwordPanel); 
        
		/* 标题信息 */
		JLabel titleLabel = new JLabel("请录入授权柜员号:");
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		titleLabel.setForeground(Color.decode("#412174"));
		titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 40));
		titleLabel.setBounds(0, 200, 1009, 60);
		this.showJpanel.add(titleLabel);
		//确认
		JLabel lblNewLabel = new JLabel(new ImageIcon("pic/newPic/confirm.png"));
		lblNewLabel.setBounds(890, 770, 150, 50);
		lblNewLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				scanBill(transBean); 
			}

		});
		this.add(lblNewLabel);
		
		JLabel label = new JLabel(new ImageIcon("pic/preStep.png"));
		label.setBounds(1075, 770, 150, 50);
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				scanBill1(transBean); 
			}

		});
		this.add(label);
		//退出
		JLabel label_1 = new JLabel(new ImageIcon("pic/newPic/exit.png"));
		label_1.setBounds(1258, 770, 150, 50);
		label_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				returnHome();
			}

		});
		this.add(label_1);
		
		textField = new JTextField();
		textField.setFont(new Font("微软雅黑", Font.PLAIN, 40));
		textField.setBounds(292, 259, 424, 40);
		textField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				scanBill12();
			}

		});
		textField.setColumns(10);
		passwordPanel.add(textField, BorderLayout.CENTER);
		this.showJpanel.add(textField);
		keyPopup = new SoftKeyBoardPopups5(textField,5);
		
		errInfo = new JLabel("提示：请录入授权柜员号!");
		errInfo.setVisible(false);
		errInfo.setForeground(Color.decode("#ff0101"));
		errInfo.setBounds(292, 310, 258, 23);
		errInfo.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		this.showJpanel.add(errInfo);
		
	}
	
	/***
	 * 下一步
	 */
	private void scanBill(PublicCashOpenBean transBean){
		//关闭语音
		clearTimeText();
		//业务处理标识判断
		transBean.getImportMap().put("identifying", "2");
		String str = textField.getText();
		if(str == null || "".equals(str)){
			on_off=true;
			errInfo.setVisible(true);
			return;
		}
		//授权柜员号
		transBean.setSupTellerNo(str);
		openPanel(new MoneyBoxSystemProcessingPanel(transBean));
	}
	
	/***
	 * 上一步
	 */
	private void scanBill1(PublicCashOpenBean transBean){
		//关闭语音
		clearTimeText();
		transBean.setJijvOrPuhui("");//清空普惠标示
		openPanel(new MoneyBoxPageSelectlPanel(transBean));
		
		
	}
	
	
	
	
	/***
	 * 键盘
	 */
	private void scanBill12(){
		keyPopup.show(passwordPanel, 160, 350);
		textField.grabFocus();
	}

}
