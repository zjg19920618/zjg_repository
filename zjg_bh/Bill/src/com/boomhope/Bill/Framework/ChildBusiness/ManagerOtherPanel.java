package com.boomhope.Bill.Framework.ChildBusiness;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseContentPanel;
import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Public.GlobalParameter;
import com.boomhope.Bill.PublicControl.adminTrans.TransPrintAdminLogin;
import com.boomhope.Bill.TransService.BudingJrnlNo.TransBuldingJrnlNo;
import com.boomhope.Bill.TransService.BudingJrnlNo.bean.BulidingJrnNoBean;
import com.boomhope.Bill.TransService.LostReport.Page.LostJpg.LostSelectPrintOrFtpJpg;

public class ManagerOtherPanel extends BaseTransPanelNew{
	Logger logger = Logger.getLogger(ManagerOtherPanel.class);

	private JLabel SvrDing;//流水号绑定
	private JLabel managerLabel;//管理员登录
	private JLabel TPshangchuan;//事后上传
	private boolean on_off=true;//跳转的开关控制
	
	/**
	 * 更改背景
	 */
	public void paintComponent(Graphics g){
		logger.info("加载其它子业务选择页的背景");
		g.drawImage(new ImageIcon("pic/newPic/ground.png").getImage(),220,150,GlobalParameter.TRANS_WIDTH-50,GlobalParameter.TRANS_HEIGHT-140,this);
	}
	/**
	 * 界面初始化
	 */
	public ManagerOtherPanel() {
		logger.info("进入其它子业务选择页面");
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
				logger.info("其它子业务选择页倒计时结束");
				clearTimeText();
			}
			
		});
		delayTimer.start();
		
		
		//界面初始化参数配置
		this.setSize(GlobalParameter.WINDOW_WIDTH,830);
		this.setLocation(0, 0);
		this.setOpaque(false);
		this.setLayout(null);
		
		//流水号绑定
		SvrDing = new JLabel(new ImageIcon("pic/newPic/SvrBd.png"));
		SvrDing.setBounds(320, 300, 200,300);
		add(SvrDing);
		SvrDing.addMouseListener(new MouseAdapter(){
			public void mouseReleased(MouseEvent arg0){
				SwingUtilities.invokeLater(new Runnable() {
					
					@Override
					public void run() {
						if(!on_off){
							return;
						}
						on_off=false;
						logger.info("点击流水号绑定按钮");
						svrBd();
					}
				});
			}
		});
		
		//管理员登录
		managerLabel = new JLabel(new ImageIcon("pic/newPic/managerLogin.png"));
		managerLabel.setBounds(630, 300, 200, 300);
		add(managerLabel);
		managerLabel.addMouseListener(new MouseAdapter(){
			public void mouseReleased(MouseEvent arg0){
				SwingUtilities.invokeLater(new Runnable() {
					
					@Override
					public void run() {
						if(!on_off){
							return;
						}
						on_off=false;
						logger.info("点击管理员登录按钮");
						managerLogin();
					}
				});
			}
		});
		// 事后图片上传
		TPshangchuan = new JLabel(new ImageIcon("pic/newPic/JpgFtp.jpg"));
		TPshangchuan.setBounds(940, 300, 200, 300);
		add(TPshangchuan);
		TPshangchuan.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent arg0) {
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						if (!on_off) {
							return;
						}
						on_off = false;
						logger.info("点击事后图片上传按钮");
						FtpJpg();
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
	
	
	//流水号绑定
	public void svrBd(){
		logger.info("进流水号绑定的方法");
		final Component thisComp = this;
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
				if(thisComp.getParent()==null){
					return;
				}else{
					contentPanel = (BaseContentPanel) thisComp.getParent();
					contentPanel.remove(thisComp);
				}
				GlobalParameter.ACC_NO="20";
				contentPanel.repaint();
				jrnNoBean = new BulidingJrnNoBean();
				contentPanel.add(new TransBuldingJrnlNo(jrnNoBean));
			}
		});
	}
	
	
	//管理员登录
	public void managerLogin(){
		logger.info("进入管理员登录的方法");
		final Component thisComp = this;
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
				if(thisComp.getParent()==null){
					return;
				}else{
					contentPanel = (BaseContentPanel) thisComp.getParent();
					contentPanel.remove(thisComp);
				}
				GlobalParameter.ACC_NO="0";
				contentPanel.repaint();
				contentPanel.add(new TransPrintAdminLogin());
			}
		});
	}
	//事后图片上传
	public void FtpJpg(){
		logger.info("进入事后图片上传的方法");
		final Component thisComp = this;
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
				if(thisComp.getParent()==null){
					return;
				}else{
					contentPanel = (BaseContentPanel) thisComp.getParent();
					contentPanel.remove(thisComp);
				}
				GlobalParameter.ACC_NO="8";
				contentPanel.repaint();
        		contentPanel.add(new LostSelectPrintOrFtpJpg());
			}
		});
	}
}
