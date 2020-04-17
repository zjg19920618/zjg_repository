package com.boomhope.Bill.TransService.CashOpen.moneybox;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.sound.sampled.SourceDataLine;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.TransService.CashOpen.Bean.PublicCashOpenBean;

/**
 * 红包页面
 * @author gyw
 *
 */
public class MoneyBoxAgainRedPacketPanel extends BaseTransPanelNew{

	static Logger logger = Logger.getLogger(MoneyBoxAgainRedPacketPanel.class);
	private static final long serialVersionUID = 1L;
	JLabel lblNewLabel = null;
	ImageIcon image =null;
	ImageIcon image1 =null;
	JLabel jlb = null;
	JLabel jlb1 = null;
	JLabel okButton =null;
	JLabel lblNewLabel_1=null;
	JLabel lblNewLabel_2=null;
	JLabel lblNewLabel_3=null;
	boolean flag=false;//标记是否点击了确定按钮
	public MoneyBoxAgainRedPacketPanel(final PublicCashOpenBean transBean) {

		this.cashBean = transBean;
		/* 显示时间倒计时 */
		showTimeText(delaySecondMinTime);
		delayTimer = new Timer(delaySecondMinTime * 1000, new ActionListener() {          
            public void actionPerformed(ActionEvent e) { 
            	flag=true;
            	/* 倒计时结束退出交易 */ 
        		if(transBean.getProductCode().startsWith("JX")){
            		nextPage(transBean);
            	}else{
            		nextStep(transBean);
            	}
        	}           
        });            
		delayTimer.start();
		/* 倒计时打开语音 */
		voiceTimer = new Timer(voiceSecond, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {  
            	stopTimer(voiceTimer);
            	excuteVoice("voice/redPacket.wav");
            	
            }      
        });            
		voiceTimer.start(); 
		/* 标题信息 */
//		JLabel titleLabel = new JLabel("恭喜您获得幸运豆");
//		titleLabel.setHorizontalAlignment(JLabel.CENTER);
//		titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 36));
//		titleLabel.setBounds(10, 150, GlobalParameter.TRANS_WIDTH, 40);
//		this.add(titleLabel);
		/* 红包动画 */
//	    image = new UtilImages("pic/gif_click_jx.gif");
//		image.setSize(466, 257);
//		image.setLocation(330, 300);
//		image.setVisible(true);
//		image.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseReleased(MouseEvent e) {
//				/* 点击后变换图片 */
//				backTrans();
//			}
//		});
//		add(image);
		jlb = new JLabel();
		jlb.setLocation(258, 0);
		image = new ImageIcon("pic/01.gif");
		image.setImage(image.getImage().getScaledInstance(496,606,Image.SCALE_DEFAULT ));
		jlb.setIcon(image);
		jlb.setSize(496,606);
		jlb.setVisible(true);
		jlb.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				/* 点击后变换图片 */
				backTrans();
			}
		});
		this.showJpanel.add(jlb);
		
		lblNewLabel_1 = new JLabel("￥");
		lblNewLabel_1.setForeground(new Color(250, 204, 0));
		lblNewLabel_1.setBackground(Color.BLACK);
		lblNewLabel_1.setFont(new Font("微软雅黑", Font.BOLD, 28));
		lblNewLabel_1.setBounds(431, 450, 301, 79);
		lblNewLabel_1.setVisible(false);
		this.showJpanel.add(lblNewLabel_1);
		
//		lblNewLabel_2 = new JLabel("1000.00");
		lblNewLabel_2 = new JLabel(transBean.getInterestCount());
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setForeground(new Color(255, 204, 0));
		lblNewLabel_2.setBackground(Color.BLACK);
		lblNewLabel_2.setFont(new Font("微软雅黑", Font.BOLD, 28));
		lblNewLabel_2.setBounds(350, 450, 320, 79);
		lblNewLabel_2.setVisible(false);
		this.showJpanel.add(lblNewLabel_2);
//		
		lblNewLabel_3 = new JLabel("元");
		lblNewLabel_3.setForeground(new Color(255, 204, 0));
		lblNewLabel_3.setBackground(Color.BLACK);
		lblNewLabel_3.setFont(new Font("微软雅黑", Font.BOLD, 28));
		lblNewLabel_3.setBounds(570, 450, 301, 79);
		lblNewLabel_3.setVisible(false);
		this.showJpanel.add(lblNewLabel_3);
		
		/* 获得红包图片 */
//		image1 = new UtilImages("pic/jx_bg.png");
//		image1.setSize(420, 257);
//		image1.setLocation(330, 252);
//		image1.setVisible(false);
//		image1.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseReleased(MouseEvent e) {
//				/* 点击后变换图片 */
////				backTrans();
//			}
//		});
//		add(image1);
		jlb1 = new JLabel();
		jlb1.setLocation(258, 0);
		image1 = new ImageIcon("pic/02.png");
		image1.setImage(image1.getImage().getScaledInstance(496,606,Image.SCALE_DEFAULT ));
		jlb1.setIcon(image1);
		jlb1.setSize(496,606);
		jlb1.setVisible(false);
		jlb1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				/* 点击后变换图片 */
				
					backTrans();
					
				}
			
		});
		/* 确认 */
		okButton = new JLabel(new ImageIcon("pic/04.png"));
		okButton.setHorizontalTextPosition(SwingConstants.CENTER);
		okButton.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		okButton.setOpaque(false);// 设置控件是否透明，true为不透明，false为透明
		okButton.setVisible(false);
		okButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				//暂时跳转处理中页面
				logger.info("红包页面点击确定按钮");
				if(flag){
					return;
				}
				flag=true;
				nextStep(transBean);
			}
		});
		okButton.setSize(120, 40);
		okButton.setLocation(455, 535);
		this.showJpanel.add(okButton);
		this.showJpanel.add(jlb1);
		
		
		JLabel label = new JLabel(
				"<HTML><p>温馨提示:</p><p>如您提前支取,</p><p>幸运豆全额退回!</p><HTML>");
		label.setForeground(Color.RED);
		label.setHorizontalAlignment(SwingConstants.LEFT);
		label.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		label.setBounds(776, 336, 203, 148);
		this.showJpanel.add(label);
		openRed();
	}
	
	
	private void openRed(){
		Thread thread = new Thread("openRed"){
		public void run(){
			try {
				Thread.sleep(10000);
				backTrans();
			} catch (InterruptedException e) {
				logger.error(e);
			}
			
		}
	};thread.start();
	}
	
	/**
	 * 点击后图片变化
	 * */
	public void backTrans() {
		jlb.setVisible(false);
		jlb1.setVisible(true);
		okButton.setVisible(true);
		lblNewLabel_1.setVisible(true);
		lblNewLabel_2.setVisible(true);
		lblNewLabel_3.setVisible(true);
		return;
	}
	
	/**
	 * 跳转积享存成功页
	 */
	private void nextPage(PublicCashOpenBean transBean){
		clearTimeText();
		openPanel(new MoneyBoxJXCSuccessPage(transBean));
	}
	/**
	 * 跳转成功页
	 */
	private void nextStep(PublicCashOpenBean transBean){
		//退卡
		clearTimeText();
		logger.info("下一步进入存入成功页面");
		openPanel(new MoneyBoxSuccessDepPanel(transBean));
	}


}
