package com.boomhope.Bill.TransService.AccOpen.View.ServicePage.Account.ProtocolDeposit;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.PublicControl.OutputBankCardPanel;
import com.boomhope.Bill.TransService.AccOpen.View.ServicePage.Account.AccChooseBusiness;
import com.boomhope.Bill.TransService.AccOpen.View.ServicePage.Account.ProInformationInputPanel.AccAxcxlProPanel;
import com.boomhope.Bill.TransService.AccOpen.View.ServicePage.Account.ProInformationInputPanel.AccYxcxlProPanel;
import com.boomhope.Bill.TransService.AccOpen.controller.AccountTransFlow;
import com.boomhope.Bill.Util.UtilButton;
/**
 * 协议存款产品第一页
 * @author zjg 
 *
 */
public class AccProtocolDepPanel extends BaseTransPanelNew {
	
	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(AccProtocolDepPanel.class);
	private boolean on_off=true;
	
	public AccProtocolDepPanel( final Map<Object,Object> params) {
		logger.info("进入协议产品第一页面");
		
		//将当前页面传入流程控制进行操作
		final Component comp=this;
		
		/* 显示时间倒计时 */
		this.showTimeText(delaySecondShortTime);
		delayTimer = new Timer(delaySecondShortTime * 1000, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {  
            	logger.info("协议产品第一页面倒计时结束 ");
            	/* 倒计时结束退出交易 */ 
            	clearTimeText();
            	serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！","","");
            }      
        });            
		delayTimer.start(); 
		
		//请选择您要办理的业务
		JLabel msg=new JLabel("请选择您要办理的业务");
		msg.setFont(new Font("微软雅黑", Font.BOLD, 40));
		msg.setForeground(Color.decode("#412174"));
		msg.setHorizontalAlignment(0);
		msg.setBounds(0, 60, 1009, 60);
		this.showJpanel.add(msg);
		//安享存（机具版）
		UtilButton anButton = new UtilButton("pic/newPic/AXC.png","pic/newPic/AXC.png");
		anButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击安享存按钮");
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				openAXC(comp, params);
			}
			
		});
		anButton.setSize(200, 300);
		anButton.setLocation(127,174);
		anButton.setIcon(new ImageIcon("pic/newPic/AXC.png"));
		this.showJpanel.add(anButton);
		
		//约享存
		UtilButton yxButton = new UtilButton("pic/newPic/YXC.png","pic/newPic/YXC.png");
		yxButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击约享存按钮");
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				openXYC(comp,params);
			}

		});
		yxButton.setSize(200, 300);
		yxButton.setLocation(405, 174);
		yxButton.setIcon(new ImageIcon("pic/newPic/YXC.png"));
		this.showJpanel.add(yxButton);
		
		//幸福1+1
		UtilButton xfButton = new UtilButton("pic/newPic/XF.png","pic/newPic/XF.png");
		xfButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击幸福1+1按钮");
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				openXF(comp,params);
			}

		});
		xfButton.setSize(200, 300);
		xfButton.setLocation(683,174);
		xfButton.setIcon(new ImageIcon("pic/newPic/XF.png"));
		this.showJpanel.add(xfButton);
		
		//下方圆点
		JLabel points=new JLabel("<HTML><font color='#412174'>.</font>         <font color='#ffffff'>.</font>          "
				+ "<font color='#ffffff'>.</font></HTML>");
		points.setBounds(454, 503, 100, 60);
		points.setFont(new Font("微软雅黑", Font.BOLD, 60));
		this.showJpanel.add(points);
		// 下一页
		UtilButton backButton = new UtilButton("pic/newPic/right.png", "pic/newPic/right.png");
		backButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击下一页按钮");
				nextPage(params);
			}

		});
		backButton.setBounds(922,265 ,57, 98);
		backButton.setIcon(new ImageIcon("pic/newPic/right.png"));
		this.showJpanel.add(backButton);

		// 上一步
		JLabel submitBtn = new JLabel(new ImageIcon("pic/preStep.png"));
		submitBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击上一步按钮");
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				backTrans(params);
			}
		});
		submitBtn.setSize(150, 50);
		submitBtn.setLocation(1075, 770);
		add(submitBtn);
		
		//退出 
		JLabel backOutButton = new JLabel(new ImageIcon("pic/newPic/exit.png"));
		backOutButton.setBounds(1258, 770, 150, 50);
		backOutButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击退出按钮");
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				clearTimeText();
				openPanel(new OutputBankCardPanel());
			}
		});
		add(backOutButton);
	}
	
	/** 协议产品下一页*/
	public void nextPage(final Map<Object,Object> params){
		clearTimeText();//清空倒计时
		logger.info("进入协议产品第二页...");
		openPanel(new AccProtocolDepPanel2(params));
	}

	/** 返回上一步*/
	public void backTrans(final Map<Object,Object> params) {
		clearTimeText();//清空倒计时
		logger.info("返回业务选择页...");
		openPanel(new AccChooseBusiness(params));
	}
	
	/** 幸福1+1产品系列页*/
	public void openXF(final Component comp,final Map<Object,Object> params){
		logger.info("进入幸福1+1产品的方法");
		clearTimeText();//清空倒计时
		//跳转
		params.put("PRODUCT_CODE", "XF");//产品标识
		params.put("transCode", "0016");
		AccountTransFlow.startTransFlow(comp, params);
	}	
	
	/**安享存产品页*/
	public void openAXC(final Component comp,final Map<Object,Object> params){
		clearTimeText();//清空倒计时
		logger.info("进入安享存产品页面...");
		AccAxcxlProPanel acc =new AccAxcxlProPanel(params);
		openPanel(acc);
	}			
	
	/**约享存产品页*/
	public void openXYC(final Component comp,final Map<Object,Object> params){
		clearTimeText();//清空倒计时
		logger.info("进入约享存产品页面...");
		AccYxcxlProPanel acc =new AccYxcxlProPanel(params);
		openPanel(acc);
	}	
}
