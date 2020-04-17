package com.boomhope.Bill.Framework.ChildBusiness;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseContentPanel;
import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Public.GlobalParameter;
import com.boomhope.Bill.PublicControl.OutputBankCardPanel;
import com.boomhope.Bill.TransService.AccOpen.Bean.AccPublicBean;
import com.boomhope.Bill.TransService.AccOpen.View.ServicePage.Account.PublicProPanel.AccInputBankCardPanel;
import com.boomhope.Bill.TransService.AccOpen.controller.AccountTradeCodeAction;
import com.boomhope.Bill.TransService.CashOpen.Bean.PublicCashOpenBean;
import com.boomhope.Bill.TransService.CashOpen.moneybox.MoneyBoxEntryModePanel;
import com.boomhope.Bill.Util.OnOffSetUp;

public class AccCardAndCashOpenPanel extends BaseTransPanelNew{
	Logger logger = Logger.getLogger(AccCardAndCashOpenPanel.class);

	private JLabel CardOpen;
	private JLabel CashOpen;
	private boolean on_off=true;//跳转的开关控制
	
	/**
	 * 更改背景
	 */
	public void paintComponent(Graphics g){
		logger.info("加载开户子业务选择页的背景");
		g.drawImage(new ImageIcon("pic/newPic/ground.png").getImage(),220,150,GlobalParameter.TRANS_WIDTH-50,GlobalParameter.TRANS_HEIGHT-140,this);
	}
	/**
	 * 界面初始化
	 */
	public AccCardAndCashOpenPanel() {
		logger.info("进入开户子业务选择页面");
		this.removeAll();
		repaint();
		
		/*显示时间倒计时*/
		if(delayTimer != null){
			clearTimeText();
		}
		this.showTimeText(delaySecondShortTime);
		delayTimer = new Timer(delaySecondShortTime*1000,new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				logger.info("开户子业务选择页倒计时结束");
				clearTimeText();
				returnHome();
			}
			
		});
		delayTimer.start();
		
		
		//界面初始化参数配置
		this.setSize(GlobalParameter.WINDOW_WIDTH,830);
		this.setLocation(0, 0);
		this.setOpaque(false);
		this.setLayout(null);
		
		
		//现金开户
		CashOpen = new JLabel(new ImageIcon("pic/newPic/cashOpen.png"));
		CashOpen.setBounds(820, 300, 200,300);
		add(CashOpen);
		CashOpen.addMouseListener(new MouseAdapter(){
			public void mouseReleased(MouseEvent arg0){
				SwingUtilities.invokeLater(new Runnable() {
					
					@Override
					public void run() {
						if(!on_off){
							return;
						}
						on_off=false;
						logger.info("点击现金开户按钮");
						cashClick();
						
					}
				});
			}
		});
		//银行卡开户
		CardOpen = new JLabel(new ImageIcon("pic/newPic/cardOpen.png"));
		CardOpen.setBounds(420, 300, 200, 300);
		add(CardOpen);
		CardOpen.addMouseListener(new MouseAdapter(){
			public void mouseReleased(MouseEvent e){
				SwingUtilities.invokeLater(new Runnable() {
					
					@Override
					public void run() {
						if(!on_off){
							return;
						}
						on_off=false;
						logger.info("点击银行卡开户按钮");
						cardClick();
						
					}
				});
			}
		});
		
		// 返回
		JLabel backButton = new JLabel(new ImageIcon("pic/newPic/exit.png"));
		backButton.setBounds(1079, 770, 150, 50);
		backButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击退出按钮");
				clearTimeText();
				returnHome();
			}
		});
		add(backButton);
		
	}
	
	//现金开户
	public void cashClick(){
		logger.info("进入现金开户的方法");
		final Component thisComp=this;
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
				if(thisComp.getParent()==null){
					return;
				}else{
					contentPanel = (BaseContentPanel) thisComp.getParent();
					contentPanel.remove(thisComp);
				}
				GlobalParameter.ACC_NO="2";
				cashBean = new PublicCashOpenBean();
				contentPanel.repaint();
				contentPanel.add(new MoneyBoxEntryModePanel(cashBean));
				
			}
		});
	}
	
	//银行卡开户
	public void cardClick(){
		logger.info("进入银行卡开户的方法");
		final Component thisComp=this;
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
				if(thisComp.getParent()==null){
					return;
				}else{
					contentPanel = (BaseContentPanel) thisComp.getParent();
					contentPanel.remove(thisComp);
				}
				GlobalParameter.ACC_NO="5";
				Map map = new HashMap();
				map.put("transCode", "0001");
				AccountTradeCodeAction.transBean=new AccPublicBean();
				contentPanel.repaint();
				contentPanel.add(new AccInputBankCardPanel(map));
				
			}
		});
	}
}
