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
import com.boomhope.Bill.TransService.AccOpen.controller.AccountTradeCodeAction;
import com.boomhope.Bill.TransService.AccOpen.controller.AccountTransFlow;
import com.boomhope.Bill.Util.UtilButton;

/**
 * 协议存款产品第三页
 * 
 * @author zjg
 *
 */
public class AccProtocolDepPanel3 extends BaseTransPanelNew {

	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(AccProtocolDepPanel3.class);
	private boolean on_off=true;

	public AccProtocolDepPanel3(final Map<Object, Object> params) {
		logger.info("进入协议存款产品第三页");
		
		// 将当前页面传入流程控制进行操作
		final Component comp = this;

		/* 显示时间倒计时 */
		this.showTimeText(delaySecondShortTime);
		delayTimer = new Timer(delaySecondShortTime * 1000,
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						logger.info("协议存款产品第三页倒计时结束 ");
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
		// 积享存
		UtilButton jxButton = new UtilButton("pic/newPic/JXC.png",
				"pic/newPic/JXC.png");
		jxButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击积享存按钮");
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				openJXC(comp, params);

			}

		});
		jxButton.setSize(200, 300);
		jxButton.setLocation(127, 174);
		jxButton.setIcon(new ImageIcon("pic/newPic/JXC.png"));
		this.showJpanel.add(jxButton);

		// 如意存+
		UtilButton rycjiaButton = new UtilButton("pic/newPic/RYC+.png", "pic/newPic/RYC+.png");
		rycjiaButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击如意存+按钮");
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				openRYCJ(comp, params);

			}

		});
		rycjiaButton.setSize(200, 300);
		rycjiaButton.setLocation(405, 174);
		rycjiaButton.setIcon(new ImageIcon("pic/newPic/RYC+.png"));
		this.showJpanel.add(rycjiaButton);

		// 下方圆点
		JLabel points = new JLabel(
				"<HTML><font color='#ffffff'>.</font>          "
						+ "<font color='#ffffff'>.</font>         <font color='#412174'>.</font></HTML>");
		points.setBounds(454, 503, 100, 60);
		points.setFont(new Font("微软雅黑", Font.BOLD, 60));
		this.showJpanel.add(points);
		// 上一页
		UtilButton backButton = new UtilButton("pic/newPic/left.png",
				"pic/newPic/left.png");
		backButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击上一页按钮");
				stepPage(params);
			}

		});
		backButton.setBounds(30,265 ,57, 98);
		backButton.setIcon(new ImageIcon("pic/newPic/left.png"));
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

	/** 协议产品上一页 */
	public void stepPage(final Map<Object, Object> params) {
		clearTimeText();// 清空倒计时
		logger.info("进入协议产品第二页...");
		AccProtocolDepPanel2 acc = new AccProtocolDepPanel2(params);
		openPanel(acc);
	}

	/** 返回上一步 */
	public void backTrans(final Map<Object, Object> params) {
		clearTimeText();// 清空倒计时
		logger.info("返回业务选择页...");
		AccChooseBusiness acc = new AccChooseBusiness(params);
		openPanel(acc);
	}

	

	/** 积享存产品系列页 */
	public void openJXC(final Component comp, final Map<Object, Object> params) {
		logger.info("进入积享存产品系列页面的方法");
		clearTimeText();// 清空倒计时
		// 跳转
		params.put("PRODUCT_CODE", "JX");// 产品标识
		params.put("transCode", "0017");// 交易处理码
		AccountTransFlow.startTransFlow(comp, params);
		if("1".equals(AccountTradeCodeAction.transBean.getHaveRelaAcc())){
			on_off=true;
		}
	}

	/** 如意存+产品系列页 */
	public void openRYCJ(final Component comp, final Map<Object, Object> params) {
		logger.info("进入如意存+产品系列页面的方法");
		clearTimeText();// 清空倒计时
		// 跳转
		params.put("PRODUCT_CODE", "RJ");// 产品标识
		params.put("transCode", "0017");// 交易处理码
		AccountTransFlow.startTransFlow(comp, params);
		if("1".equals(AccountTradeCodeAction.transBean.getHaveRelaAcc())){
			on_off=true;
		}
	}
}
