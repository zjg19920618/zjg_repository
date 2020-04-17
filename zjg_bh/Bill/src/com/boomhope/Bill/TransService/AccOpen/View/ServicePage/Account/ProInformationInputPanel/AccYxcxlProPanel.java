package com.boomhope.Bill.TransService.AccOpen.View.ServicePage.Account.ProInformationInputPanel;

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
import javax.swing.SwingConstants;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.PublicControl.OutputBankCardPanel;
import com.boomhope.Bill.TransService.AccOpen.View.ServicePage.Account.PrivateLine.AccPrivateLinePanel;
import com.boomhope.Bill.TransService.AccOpen.View.ServicePage.Account.ProtocolDeposit.AccProtocolDepPanel;
import com.boomhope.Bill.TransService.AccOpen.controller.AccountTradeCodeAction;
import com.boomhope.Bill.TransService.AccOpen.controller.AccountTransFlow;

/***
 * 约享存系列页面
 * 
 * @author zjg
 *
 */
public class AccYxcxlProPanel extends BaseTransPanelNew {
	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(AccYxcxlProPanel.class);
	Map<Object, Object> parames = null;
	private boolean on_off=true;

	public AccYxcxlProPanel(final Map<Object, Object> parames) {
		logger.info("进入约享存系列页面");
		
		// 将当前页面传入流程控制进行操作
		final Component comp = this;
		this.parames = parames;

		/* 显示时间倒计时 */
		this.showTimeText(delaySecondShortTime);
		delayTimer = new Timer(delaySecondShortTime * 1000,
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						logger.info("约享存系列页面倒计时结束 ");
						/* 倒计时结束退出交易 */
						clearTimeText();
		            	serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！","","");
					}
				});
		delayTimer.start();
		// 标题
		JLabel depoLum = new JLabel("约 享 存");
		depoLum.setFont(new Font("微软雅黑", Font.BOLD, 40));
		depoLum.setForeground(Color.decode("#412174"));
		depoLum.setHorizontalAlignment(0);
		depoLum.setBounds(0, 60, 1009, 60);
		this.showJpanel.add(depoLum);

		// 判断是否为私行快线
		if ("2".equals(parames.get("CHL_ID"))) {
			//根据不同的客户级别显示不同的开户额度
			String productAmt = "";
			if("1".equals(AccountTradeCodeAction.transBean.getCustLevel().trim()) 
					|| "2".equals(AccountTradeCodeAction.transBean.getCustLevel().trim())
					|| "3".equals(AccountTradeCodeAction.transBean.getCustLevel().trim())){
					productAmt = "产品额度 :1-1000万";
				}else{
					productAmt = "产品额度 :50-1000万";
				}
			// 约享存1
			JLabel lblNewLabel_1 = new JLabel("产品名称 : 约享存A(机具版)");
			lblNewLabel_1.setForeground(Color.decode("#ffffff"));
			lblNewLabel_1.setFont(new Font("微软雅黑", Font.PLAIN, 18));
			lblNewLabel_1.setBounds(
					145, 193, 340, 20);
			lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
			this.showJpanel.add(lblNewLabel_1);
			JLabel lblNewLabel_2 = new JLabel("存期 : 40个月");
			lblNewLabel_2.setForeground(Color.decode("#ffffff"));
			lblNewLabel_2.setFont(new Font("微软雅黑", Font.PLAIN, 18));
			lblNewLabel_2.setBounds(145, 243, 340, 20);
			lblNewLabel_2.setHorizontalAlignment(SwingConstants.LEFT);
			this.showJpanel.add(lblNewLabel_2);
			JLabel lblNewLabel_4 = new JLabel(productAmt);
			lblNewLabel_4.setFont(new Font("微软雅黑", Font.PLAIN, 18));
			lblNewLabel_4.setForeground(Color.decode("#ffffff"));
			lblNewLabel_4.setHorizontalAlignment(SwingConstants.LEFT);
			lblNewLabel_4.setBounds(145, 293, 340, 20);
			this.showJpanel.add(lblNewLabel_4);
			JLabel image = new JLabel(new ImageIcon("pic/newPic/YXCF.png"));
			image.setHorizontalAlignment(SwingConstants.LEFT);
			image.setHorizontalTextPosition(SwingConstants.CENTER);
			image.setSize(340, 160);
			image.setLocation(125, 172);
			image.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					logger.info("点击私行约享存1按钮");
					if(!on_off){
						logger.info("开关当前状态"+on_off);
						return;
					}
					logger.info("开关当前状态"+on_off);
					on_off=false;
					// 约享存1
					nextPage1(comp, parames);
				}
			});
			this.showJpanel.add(image);
			// 约享存2
			JLabel lblNewLabel_3 = new JLabel("产品名称 : 约享存B(机具版)");
			lblNewLabel_3.setForeground(Color.decode("#ffffff"));
			lblNewLabel_3.setFont(new Font("微软雅黑", Font.PLAIN, 18));
			lblNewLabel_3.setBounds(
					563, 193, 340, 20);
			lblNewLabel_3.setHorizontalAlignment(SwingConstants.LEFT);
			this.showJpanel.add(lblNewLabel_3);
			JLabel lblNewLabel_5 = new JLabel("存期 : 50个月");
			lblNewLabel_5.setForeground(Color.decode("#ffffff"));
			lblNewLabel_5.setFont(new Font("微软雅黑", Font.PLAIN, 18));
			lblNewLabel_5.setBounds(563, 243, 340, 20);
			lblNewLabel_5.setHorizontalAlignment(SwingConstants.LEFT);
			this.showJpanel.add(lblNewLabel_5);
			JLabel lblNewLabel_6 = new JLabel(productAmt);
			lblNewLabel_6.setFont(new Font("微软雅黑", Font.PLAIN, 18));
			lblNewLabel_6.setForeground(Color.decode("#ffffff"));
			lblNewLabel_6.setHorizontalAlignment(SwingConstants.LEFT);
			lblNewLabel_6.setBounds(563, 293, 340, 20);
			this.showJpanel.add(lblNewLabel_6);
			JLabel image2 = new JLabel(new ImageIcon("pic/newPic/YXCF.png"));
			image2.setHorizontalAlignment(SwingConstants.LEFT);
			image2.setHorizontalTextPosition(SwingConstants.CENTER);
			image2.setSize(340, 160);
			image2.setLocation(543, 172);
			image2.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					logger.info("点击私行约享存2按钮");
					if(!on_off){
						logger.info("开关当前状态"+on_off);
						return;
					}
					logger.info("开关当前状态"+on_off);
					on_off=false;
					// 约享存2
					nextPage2(comp, parames);
				}
			});
			this.showJpanel.add(image2);
			// 约享存3
			JLabel lblNewLabel_7 = new JLabel("产品名称 : 约享存C(机具版)");
			lblNewLabel_7.setForeground(Color.decode("#ffffff"));
			lblNewLabel_7.setFont(new Font("微软雅黑", Font.PLAIN, 18));
			lblNewLabel_7.setBounds(
					354, 387, 340, 20);
			lblNewLabel_7.setHorizontalAlignment(SwingConstants.LEFT);
			this.showJpanel.add(lblNewLabel_7);
			JLabel lblNewLabel_8 = new JLabel("存期 : 60个月");
			lblNewLabel_8.setForeground(Color.decode("#ffffff"));
			lblNewLabel_8.setFont(new Font("微软雅黑", Font.PLAIN, 18));
			lblNewLabel_8.setBounds(354, 437, 340, 20);
			lblNewLabel_8.setHorizontalAlignment(SwingConstants.LEFT);
			this.showJpanel.add(lblNewLabel_8);
			JLabel lblNewLabel_9 = new JLabel(productAmt);
			lblNewLabel_9.setFont(new Font("微软雅黑", Font.PLAIN, 18));
			lblNewLabel_9.setForeground(Color.decode("#ffffff"));
			lblNewLabel_9.setHorizontalAlignment(SwingConstants.LEFT);
			lblNewLabel_9.setBounds(354, 487, 340, 20);
			this.showJpanel.add(lblNewLabel_9);
			JLabel image3 = new JLabel(new ImageIcon("pic/newPic/YXCF.png"));
			image3.setHorizontalAlignment(SwingConstants.LEFT);
			image3.setHorizontalTextPosition(SwingConstants.CENTER);
			image3.setSize(340, 160);
			image3.setLocation(334, 366);
			image3.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					logger.info("点击私行约享存3按钮");
					if(!on_off){
						logger.info("开关当前状态"+on_off);
						return;
					}
					logger.info("开关当前状态"+on_off);
					on_off=false;
					// 约享存3
					nextPage3(comp, parames);
				}
			});
			this.showJpanel.add(image3);
		} else {
			String typeInfo = "";
			if("1".equals(parames.get("CHL_ID"))){
				typeInfo="(机具版)";
			}else{
				typeInfo="(普惠版)";
			}
			JLabel lblNewLabel_1 = new JLabel("产品名称 : 约享存A"+typeInfo);
			lblNewLabel_1.setForeground(Color.decode("#ffffff"));
			lblNewLabel_1.setFont(new Font("微软雅黑", Font.PLAIN, 18));
			lblNewLabel_1.setBounds(
					145,193, 340, 20);
			lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
			this.showJpanel.add(lblNewLabel_1);
			JLabel lblNewLabel_2 = new JLabel("存期 : 40个月");
			lblNewLabel_2.setForeground(Color.decode("#ffffff"));
			lblNewLabel_2.setFont(new Font("微软雅黑", Font.PLAIN, 18));
			lblNewLabel_2.setBounds(145,243, 340, 20);
			lblNewLabel_2.setHorizontalAlignment(SwingConstants.LEFT);
			this.showJpanel.add(lblNewLabel_2);
			String amtLine = "";
			if("1".equals(parames.get("CHL_ID"))){
				amtLine = "产品额度 : 1-30万";
			}else{
				amtLine = "启存金额 : 1万";
			}
			JLabel lblNewLabel_4 = new JLabel(amtLine);
			lblNewLabel_4.setFont(new Font("微软雅黑", Font.PLAIN, 18));
			lblNewLabel_4.setForeground(Color.decode("#ffffff"));
			lblNewLabel_4.setHorizontalAlignment(SwingConstants.LEFT);
			lblNewLabel_4.setBounds(145,293, 340, 20);
			this.showJpanel.add(lblNewLabel_4);
			JLabel image = new JLabel(new ImageIcon("pic/newPic/YXCF.png"));
			image.setHorizontalAlignment(SwingConstants.LEFT);
			image.setHorizontalTextPosition(SwingConstants.CENTER);
			image.setSize(340, 160);
			image.setLocation(125,172);
			image.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					logger.info("点击约享存1按钮");
					if(!on_off){
						logger.info("开关当前状态"+on_off);
						return;
					}
					logger.info("开关当前状态"+on_off);
					on_off=false;
					// 约享存1
					nextPage1(comp, parames);
				}
			});
			this.showJpanel.add(image);
			// 约享存2
			JLabel lblNewLabel_3 = new JLabel("产品名称 : 约享存B"+typeInfo);
			lblNewLabel_3.setForeground(Color.decode("#ffffff"));
			lblNewLabel_3.setFont(new Font("微软雅黑", Font.PLAIN, 18));
			lblNewLabel_3.setBounds(
					563,193, 340, 20);
			lblNewLabel_3.setHorizontalAlignment(SwingConstants.LEFT);
			this.showJpanel.add(lblNewLabel_3);
			JLabel lblNewLabel_5 = new JLabel("存期 : 50个月");
			lblNewLabel_5.setForeground(Color.decode("#ffffff"));
			lblNewLabel_5.setFont(new Font("微软雅黑", Font.PLAIN, 18));
			lblNewLabel_5.setBounds(563,243, 340, 20);
			lblNewLabel_5.setHorizontalAlignment(SwingConstants.LEFT);
			this.showJpanel.add(lblNewLabel_5);
			JLabel lblNewLabel_6 = new JLabel("产品额度 : 1-30万");
			lblNewLabel_6.setFont(new Font("微软雅黑", Font.PLAIN, 18));
			lblNewLabel_6.setForeground(Color.decode("#ffffff"));
			lblNewLabel_6.setHorizontalAlignment(SwingConstants.LEFT);
			lblNewLabel_6.setBounds(563,293, 340, 20);
			this.showJpanel.add(lblNewLabel_6);
			JLabel image2 = new JLabel(new ImageIcon("pic/newPic/YXCF.png"));
			image2.setHorizontalAlignment(SwingConstants.LEFT);
			image2.setHorizontalTextPosition(SwingConstants.CENTER);
			image2.setSize(340, 160);
			image2.setLocation(543,172);
			image2.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					logger.info("点击约享存2按钮");
					if(!on_off){
						logger.info("开关当前状态"+on_off);
						return;
					}
					logger.info("开关当前状态"+on_off);
					on_off=false;
					// 约享存2
					nextPage2(comp, parames);
				}
			});
			this.showJpanel.add(image2);
			// 约享存3
			JLabel lblNewLabel_7 = new JLabel("产品名称 : 约享存C"+typeInfo);
			lblNewLabel_7.setForeground(Color.decode("#ffffff"));
			lblNewLabel_7.setFont(new Font("微软雅黑", Font.PLAIN, 18));
			lblNewLabel_7.setBounds(
					354,387, 340, 20);
			lblNewLabel_7.setHorizontalAlignment(SwingConstants.LEFT);
			this.showJpanel.add(lblNewLabel_7);
			JLabel lblNewLabel_8 = new JLabel("存期 : 60个月");
			lblNewLabel_8.setForeground(Color.decode("#ffffff"));
			lblNewLabel_8.setFont(new Font("微软雅黑", Font.PLAIN, 18));
			lblNewLabel_8.setBounds(354,437, 340, 20);
			lblNewLabel_8.setHorizontalAlignment(SwingConstants.LEFT);
			this.showJpanel.add(lblNewLabel_8);
			JLabel lblNewLabel_9 = new JLabel("产品额度 : 1-30万");
			lblNewLabel_9.setFont(new Font("微软雅黑", Font.PLAIN, 18));
			lblNewLabel_9.setForeground(Color.decode("#ffffff"));
			lblNewLabel_9.setHorizontalAlignment(SwingConstants.LEFT);
			lblNewLabel_9.setBounds(354,487, 340, 20);
			this.showJpanel.add(lblNewLabel_9);
			JLabel image3 = new JLabel(new ImageIcon("pic/newPic/YXCF.png"));
			image3.setHorizontalAlignment(SwingConstants.LEFT);
			image3.setHorizontalTextPosition(SwingConstants.CENTER);
			image3.setSize(340, 160);
			image3.setLocation(334,366);
			image3.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					logger.info("点击约享存3按钮");
					if(!on_off){
						logger.info("开关当前状态"+on_off);
						return;
					}
					logger.info("开关当前状态"+on_off);
					on_off=false;
					// 约享存3
					nextPage3(comp, parames);
				}
			});
			this.showJpanel.add(image3);
		}

		/* 返回上一步 */
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
				/* 上一步 */
				backTrans();
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

	/**
	 * 上一步
	 */
	public void backTrans() {
		logger.info("进入上一步的方法");
		clearTimeText();// 清空倒计时
		// 取标识判断上一步跳转协议还是私行
		if ("1".equals(parames.get("CHL_ID")) ||"5".equals(parames.get("CHL_ID"))) {// 协议
			logger.info("进入协议的上一页"+parames.get("CHL_ID"));
			AccProtocolDepPanel acc = new AccProtocolDepPanel(parames);
			openPanel(acc);
		} else if ("2".equals(parames.get("CHL_ID"))) {// 私行
			logger.info("进入私行的上一页"+parames.get("CHL_ID"));
			AccPrivateLinePanel acc = new AccPrivateLinePanel(parames);
			openPanel(acc);
		}
	}

	

	/**
	 * 约享存1
	 * */
	public void nextPage1(final Component comp, final Map<Object, Object> params) {
		logger.info("进入约享存1的方法");
		clearTimeText();// 清空倒计时
		// 调接口查询所有约享存A
		params.put("PRODUCT_CODE", "YA");// 产品标识
		params.put("transCode", "0016");
		AccountTransFlow.startTransFlow(comp, params);
	}

	/**
	 * 约享存2
	 * */
	public void nextPage2(final Component comp, final Map<Object, Object> params) {
		logger.info("进入约享存2的方法");
		clearTimeText();// 清空倒计时
		// 调接口查询所有约享存B
		params.put("PRODUCT_CODE", "YB");// 产品标识
		params.put("transCode", "0016");
		AccountTransFlow.startTransFlow(comp, params);
	}

	/**
	 * 约享存3
	 * */
	public void nextPage3(final Component comp, final Map<Object, Object> params) {
		logger.info("进入约享存3的方法");
		clearTimeText();// 清空倒计时
		// 调接口查询所有约享存C
		params.put("PRODUCT_CODE", "YC");// 产品标识
		params.put("transCode", "0016");
		AccountTransFlow.startTransFlow(comp, params);
	}
}
