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
import com.boomhope.Bill.TransService.AccTransfer.Action.TransferAction;
import com.boomhope.Bill.TransService.AccTransfer.Bean.PublicAccTransferBean;
import com.boomhope.Bill.TransService.AccTransfer.View.ServicePage.TransferChooseBusiness;
import com.boomhope.Bill.TransService.AccTransfer.View.ServicePage.TransferCancel.TransferCancelInputBankCardPanel;

public class HuiHTransferPanel extends BaseTransPanelNew{
	Logger logger = Logger.getLogger(HuiHTransferPanel.class);
	//汇划撤销
	private JLabel huiHCancel;
	private JLabel huiHTransfer;
	private boolean on_off=true;//跳转的开关控制
	
	/**
	 * 更改背景
	 */
	public void paintComponent(Graphics g){
		logger.info("加载汇划业务选择页的背景");
		g.drawImage(new ImageIcon("pic/newPic/ground.png").getImage(),220,150,GlobalParameter.TRANS_WIDTH-50,GlobalParameter.TRANS_HEIGHT-140,this);
	}
	/**
	 * 界面初始化
	 */
	public HuiHTransferPanel() {
		logger.info("进入汇划业务选择页面");
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
				logger.info("汇划业务选择页倒计时结束");
				clearTimeText();
			}
			
		});
		delayTimer.start();
		
		
		//界面初始化参数配置
		this.setSize(GlobalParameter.WINDOW_WIDTH,830);
		this.setLocation(0, 0);
		this.setOpaque(false);
		this.setLayout(null);
		
		
		//汇划撤销
		huiHCancel = new JLabel(new ImageIcon("pic/newPic/huihcancel.png"));
		huiHCancel.setBounds(820, 300, 200,300);
		add(huiHCancel);
		huiHCancel.addMouseListener(new MouseAdapter(){
			public void mouseReleased(MouseEvent arg0){
				SwingUtilities.invokeLater(new Runnable() {
					
					@Override
					public void run() {
						if(!on_off){
							return;
						}
						on_off=false;
						logger.info("点击汇划撤销按钮");
						huiCancelClick();
						
					}
				});
			}
		});
		//汇划转账
		huiHTransfer = new JLabel(new ImageIcon("pic/newPic/huihtransfer.png"));
		huiHTransfer.setBounds(420, 300, 200, 300);
		add(huiHTransfer);
		huiHTransfer.addMouseListener(new MouseAdapter(){
			public void mouseReleased(MouseEvent e){
				SwingUtilities.invokeLater(new Runnable() {
					
					@Override
					public void run() {
						if(!on_off){
							return;
						}
						on_off=false;
						logger.info("点击汇划转账按钮");
						huiClick();
						
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
	
	//汇划撤销
	public void huiCancelClick(){
		logger.info("进入汇划撤销的方法");
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
				GlobalParameter.ACC_NO="4";
				transferAction = new TransferAction();
				transferBean = new PublicAccTransferBean();
				transferBean.getReqMCM001().setTranscode("11033");
				Map<String,String> map = new HashMap<String, String>();
				contentPanel.repaint();
				contentPanel.add(new TransferCancelInputBankCardPanel(map,transferBean));
				
			}
		});
	}
	
	//汇划转账
	public void huiClick(){
		logger.info("进入汇划转账的方法");
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
				GlobalParameter.ACC_NO="3";
				transferBean = new PublicAccTransferBean();
				transferAction = new TransferAction();
				contentPanel.repaint();
				contentPanel.add(new TransferChooseBusiness(transferBean));
				
			}
		});
	}
	
	
}
