package com.boomhope.Bill.TransService.AllTransPublicPanel.Author;


import java.awt.AWTException;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Public.GlobalParameter;
import com.boomhope.Bill.Util.Property;
import com.boomhope.Bill.Util.ScreenshotUtil;
import com.boomhope.Bill.Util.SignatureUtil;


/***
 * 签字板
 * @author hk
 *
 */
@SuppressWarnings({ "rawtypes", "static-access" })
public class AllPublicSignaturePanel extends BaseTransPanelNew {
	
	static Logger logger = Logger.getLogger(AllPublicSignaturePanel.class);
	/*倒计时时间*/
	JLabel lblNewLabel;
	JLabel promptLabel;
	private static final long serialVersionUID = 1L;
	SignatureUtil panel;
	private boolean on_off=true;
	
	public AllPublicSignaturePanel(){
		setLayout(null);
		
		baseTransBean.setThisComponent(this);
		
		/* 显示时间倒计时 */
		this.showTimeText(delaySecondLongTime);
		delayTimer = new Timer(delaySecondLongTime * 1000, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {  
            	/* 倒计时结束退出交易 */ 
            	clearTimeText();
            	//流程
            	serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！","","");
            }      
        });            
		delayTimer.start(); 
		
		try {
			panel = new SignatureUtil();
		} catch (Exception e) {
			logger.error("调用签字板时异常："+e);
			serverStop("生成签字页面失败，请联系大堂经理", "","调用签字板方法异常");
		}
		panel.setBounds(112, 127, 789, 356);
		
		this.showJpanel.add(panel);
		
		JLabel btnNewButton = new JLabel();
		btnNewButton.setIcon(new ImageIcon("pic/reSign.png"));
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				panel.getSave().clear();
				panel.repaint();
			}
		});
		btnNewButton.setBounds(695, 510, 200, 50);
		this.showJpanel.add(btnNewButton);
		
		//确认
		JLabel button = new JLabel();
		button.setIcon(new ImageIcon("pic/newPic/confirm.png"));
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击确认按钮");
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				okNext();
			}
		});
		button.setBounds(890, 770, 150, 50);
		add(button);	
		
		// 上一步
		JLabel submitBtn = new JLabel(new ImageIcon("pic/preStep.png"));
		submitBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击上一步");
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				/* 处理上一页 */
				scanBill1();
			}
		});
		submitBtn.setSize(150, 50);
		submitBtn.setLocation(1075, 770);
		add(submitBtn);
		
		// 返回
		JLabel backButton = new JLabel(new ImageIcon("pic/newPic/exit.png"));
		backButton.setBounds(1258, 770, 150, 50);
		backButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击退出");
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				clearTimeText();
				stopTimer(voiceTimer);//关闭语音
		    	closeVoice();
		    	accCancelExit();
		    	
			}
		});
		add(backButton);
		
		lblNewLabel = new JLabel("请签字");
		lblNewLabel.setBounds(0, 60, GlobalParameter.TRANS_WIDTH-50, 40);
		lblNewLabel.setHorizontalAlignment(JLabel.CENTER);
		lblNewLabel.setFont(new Font("微软雅黑", Font.BOLD, 36));
		this.showJpanel.add(lblNewLabel);
		
		promptLabel = new JLabel("请签字后再确认");
		promptLabel.setBounds(0, 510, GlobalParameter.TRANS_WIDTH-50, 40);
		promptLabel.setHorizontalAlignment(JLabel.CENTER);
		promptLabel.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		promptLabel.setForeground(Color.red);
		promptLabel.setVisible(false);
		this.showJpanel.add(promptLabel);
		
	}
	/**
	 * 确认后下一步
	 */
	
	private void okNext(){
		LinkedList list=panel.getSave();
		if(list.size()==0){
			promptLabel.setVisible(true);
			on_off=true;
			return;
		}
		ScreenshotUtil screen = new ScreenshotUtil();
		try {
			screen.Screenshot(515, 280, panel.getWidth()-6, panel.getHeight()-6, Property.SIGNATURE_PATH);
		} catch (AWTException e) {
			logger.error("签字失败："+e);
			serverStop("签字失败,请联系大堂经理", "","截取签字图片异常");
			return;
		} catch (IOException e) {
			logger.error("签字失败："+e);
			serverStop("签字失败,请联系大堂经理", "","保存签字图片异常");
			return;
		}
		nextStep();
	}
	
	/**
	 * 返回上一步
	 * 
	 */
	public void scanBill1(){
		baseTransBean.setAllPubIsSign("");
		clearTimeText();
		allPubTransFlow.transFlow();
	}
	
	
	/**
	 * 下一步
	 */
	public void nextStep(){
		baseTransBean.setAllPubIsSign("1");
		clearTimeText();
		allPubTransFlow.transFlow();
	}
	
}
