package com.boomhope.Bill.TransService.CashOpen.moneybox;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import sun.audio.AudioStream;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.TransService.CashOpen.Bean.PublicCashOpenBean;

/***
 * 温馨提示页面
 * @author gyw
 *
 */
public class MoneyBoxPromptPagesPanel extends BaseTransPanelNew{
	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(MoneyBoxPromptPagesPanel.class);
	private boolean on_off=true;//开关控制
	public MoneyBoxPromptPagesPanel(final PublicCashOpenBean transBean){
		this.cashBean=transBean;
		
		/* 显示时间倒计时 */
		this.showTimeText(delaySecondLongTime);
		delayTimer = new Timer(delaySecondLongTime * 1000, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {  
            	on_off=false;
            	/* 倒计时结束退出交易 */ 
            	clearTimeText();
            	serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！","","");
            }      
        });
		JLabel billImage = new JLabel();   
		billImage.setIcon(new ImageIcon("pic/ico_ok.png"));
		billImage.setIconTextGap(6);
		billImage.setBounds(312, 245, 98, 99);
		this.showJpanel.add(billImage);
		
		JLabel label = null;
		if("1".equals(transBean.getPromptPages())){
			label = new JLabel("购买积享存之前请先购买如意存产品");
		}if("2".equals(transBean.getPromptPages())){
			label = new JLabel("购买如意存+之前先够买如意存产品");
		}if("3".equals(transBean.getPromptPages())){
			label = new JLabel("不满50周岁无购买权限");
		}
		
		label.setHorizontalAlignment(SwingConstants.LEFT);
		label.setFont(new Font("微软雅黑", Font.BOLD, 30));
		label.setBounds(312, 422, 540, 30);
		this.showJpanel.add(label);
		
		/* 上一页 */
		JButton backButton = new JButton(new ImageIcon("pic/back_step.png"));
		backButton.setHorizontalTextPosition(SwingConstants.CENTER);
		backButton.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		backButton.setOpaque(false);// 设置控件是否透明，true为不透明，false为透明
		backButton.setContentAreaFilled(false);// 设置图片填满按钮所在的区域
		// backButton.setMargin(new Insets(0, 0, 0, 0));//设置按钮边框和标签文字之间的距离
		// backButton.setIcon(new ImageIcon("pic/inputinfo_1.png"));
		backButton.setFocusPainted(true);// 设置这个按钮是不是获得焦点
		backButton.setBorderPainted(false);// 设置是否绘制边框
		backButton.setBorder(null);// 设置边框
		backButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				/* 处理上一页 */
				backTrans(transBean);
			}
		});
		backButton.setSize(200, 50);
		backButton.setLocation(780, 638);
		// backButton.setIcon(new ImageIcon("pic/exit_bankcard.png"));
		this.showJpanel.add(backButton);

//		/* 下一页 */
//		JButton okButton = new JButton(new ImageIcon("pic/tk.png"));
//
//		okButton.setHorizontalTextPosition(SwingConstants.CENTER);
//		okButton.setFont(new Font("微软雅黑", Font.PLAIN, 20));
//		okButton.setOpaque(false);// 设置控件是否透明，true为不透明，false为透明
//		okButton.setContentAreaFilled(false);// 设置图片填满按钮所在的区域
//		// okButton.setMargin(new Insets(0, 0, 0, 0));//设置按钮边框和标签文字之间的距离
//		// okButton.setIcon(new ImageIcon("pic/inputinfo_1.png"));
//		okButton.setFocusPainted(true);// 设置这个按钮是不是获得焦点
//		okButton.setBorderPainted(false);// 设置是否绘制边框
//		okButton.setBorder(null);// 设置边框
//		okButton.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseReleased(MouseEvent e) {
//				delayTimer.stop();
//				transBean.setErrmsg("");
//				quitIdBank();
//			}
//
//		});
//		okButton.setSize(200, 50);
//		okButton.setLocation(780, 638);
//		this.add(okButton);
		
		JLabel lblNewLabel = new JLabel("温馨提示！");
		lblNewLabel.setFont(new Font("微软雅黑", Font.BOLD, 30));
		lblNewLabel.setBounds(483, 275, 173, 50);
		this.showJpanel.add(lblNewLabel);
	}
	/**
	 * 返回
	 * */
	public void backTrans(PublicCashOpenBean transBean) {
		
		clearTimeText();
		openPanel(new MoneyBoxAgreementPanel(transBean));
					
				
	}
	
	/**
	 * 确认
	 * */
	public void okTrans() {
		delayTimer.stop();
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
//				openPanel(GlobalPanelFlag.INPUT_DEPINFO_PANEL);
			}
			
		});
	}
	}

