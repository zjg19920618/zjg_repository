package com.boomhope.Bill.TransService.AccOpen.View.ServicePage.Account.ProtocolDeposit;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.PublicControl.OutputBankCardPanel;
import com.boomhope.Bill.TransService.AccOpen.View.ServicePage.Account.AccChooseBusiness;
import com.boomhope.Bill.TransService.AccOpen.View.ServicePage.Account.ProInformationInputPanel.AccLdcxlProPanel;
import com.boomhope.Bill.TransService.AccOpen.controller.AccountTransFlow;
import com.boomhope.Bill.Util.UtilButton;

/**
 * 协议存款产品第二页
 * 
 * @author zjg
 *
 */
public class AccProtocolDepPanel2 extends BaseTransPanelNew {

	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(AccProtocolDepPanel2.class);
	private boolean on_off=true;

	public AccProtocolDepPanel2(final Map<Object, Object> params) {
		logger.info("进入协议存款产品第二页");
		
		// 将当前页面传入流程控制进行操作
		final Component comp = this;

		/* 显示时间倒计时 */
		this.showTimeText(delaySecondShortTime);
		delayTimer = new Timer(delaySecondShortTime * 1000,
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						logger.info("协议存款产品第二页倒计时结束 ");
						/* 倒计时结束退出交易 */
						clearTimeText();
		            	serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！","","");
					}
				});
		delayTimer.start();
		// 请选择您要办理的业务
		JLabel msg = new JLabel("请选择您要办理的业务");
		msg.setFont(new Font("微软雅黑", Font.BOLD, 40));
		msg.setForeground(Color.decode("#412174"));
		msg.setHorizontalAlignment(0);
		msg.setBounds(0, 60, 1009, 60);
		this.showJpanel.add(msg);
		// 协议千禧
		UtilButton qxButton = new UtilButton("pic/newPic/QX.png",
				"pic/newPic/QX.png");
		qxButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击协议千禧按钮");
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				openQx(comp, params);
			}

		});
		qxButton.setSize(200, 300);
		qxButton.setLocation(127, 174);
		qxButton.setIcon(new ImageIcon("pic/newPic/QX.png"));
		this.showJpanel.add(qxButton);

		// 如意存
		UtilButton ryButton = new UtilButton("pic/newPic/RYC.png",
				"pic/newPic/RYC.png");
		ryButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击如意存按钮");
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				openRYC(comp, params);
			}

		});
		ryButton.setSize(200, 300);
		ryButton.setLocation(405, 174);
		ryButton.setIcon(new ImageIcon("pic/newPic/RYC.png"));
		this.showJpanel.add(ryButton);

		// 立得存
		UtilButton ldButton = new UtilButton("pic/newPic/LDC.png",
				"pic/newPic/LDC.png");
		ldButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击立得存按钮");
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				openLDC(comp, params);
			}

		});
		ldButton.setSize(200, 300);
		ldButton.setLocation(683, 174);
		ldButton.setIcon(new ImageIcon("pic/newPic/LDC.png"));
		this.showJpanel.add(ldButton);
		// 下方圆点
		JLabel points = new JLabel(
				"<HTML><font color='#ffffff'>.</font>          <font color='#412174'>.</font>          "
						+ "<font color='#ffffff'>.</font></HTML>");
		points.setBounds(454, 503, 100, 60);
		points.setFont(new Font("微软雅黑", Font.BOLD, 60));
		this.showJpanel.add(points);

		// 下一页
		UtilButton backButton = new UtilButton("pic/newPic/right.png",
				"pic/newPic/right.png");
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

		// 上一页
		UtilButton backButton1 = new UtilButton("pic/newPic/left.png",
				"pic/newPic/left.png");
		backButton1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击上一页按钮");
				stepPage(params);
			}

		});
		backButton1.setBounds(30,265 ,57, 98);
		backButton1.setIcon(new ImageIcon("pic/newPic/left.png"));
		this.showJpanel.add(backButton1);
		
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

	/** 协议产品上一页 */
	public void stepPage(final Map<Object, Object> params) {
		clearTimeText();// 清空倒计时
		logger.info("进入协议产品第一页...");
		AccProtocolDepPanel acc = new AccProtocolDepPanel(params);
		openPanel(acc);
	}

	/** 协议产品下一页 */
	public void nextPage(final Map<Object, Object> params) {
		clearTimeText();// 清空倒计时
		logger.info("进入协议产品第三页...");
		openPanel(new AccProtocolDepPanel3(params));
	}

	/** 返回上一步 */
	public void backTrans(final Map<Object, Object> params) {
		clearTimeText();// 清空倒计时
		logger.info("返回业务选择页...");
		openPanel(new AccChooseBusiness(params));
	}

	

	/** 千禧产品系列页 */
	public void openQx(final Component comp, final Map<Object, Object> params) {
		logger.info("进入千禧产品页面的方法");
		clearTimeText();// 清空倒计时
		params.put("PRODUCT_CODE", "QX");// 产品标识
		params.put("transCode", "0016");
		AccountTransFlow.startTransFlow(comp, params);
	}

	

	/** 如意存产品系列页 */
	public void openRYC(final Component comp, final Map<Object, Object> params) {
		logger.info("进入如意存产品页面的方法");
		clearTimeText();// 清空倒计时
		// 跳转
		params.put("PRODUCT_CODE", "RY");// 产品标识
		params.put("transCode", "0016");
		AccountTransFlow.startTransFlow(comp, params);
	}

	/** 立得存产品页 */
	public void openLDC(final Component comp, final Map<Object, Object> params) {
		clearTimeText();// 清空倒计时
		logger.info("进入立得存产品页面...");
		AccLdcxlProPanel acc = new AccLdcxlProPanel(params);
		openPanel(acc);

	}

	

	
}
