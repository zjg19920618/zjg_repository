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
 * 立得存系列页面
 * 
 * @author zjg
 * 
 */
public class AccLdcxlProPanel extends BaseTransPanelNew {
	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(AccLdcxlProPanel.class);

	Map<Object, Object> parames = null;
	private boolean on_off=true;

	public AccLdcxlProPanel(final Map<Object, Object> parames) {
		logger.info("进入立得存系列页面");
		
		// 将当前页面传入流程控制进行操作
		final Component comp = this;
		this.parames = parames;
		
		/* 显示时间倒计时 */
		this.showTimeText(delaySecondShortTime);
		delayTimer = new Timer(delaySecondShortTime * 1000, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {  
            	logger.info("立得存页面倒计时结束");
            	/* 倒计时结束退出交易 */ 
            	clearTimeText();
            	serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！","","");
            }      
        });            
		delayTimer.start(); 

		// 标题
		JLabel depoLum = new JLabel("立 得 存");
		depoLum.setFont(new Font("微软雅黑", Font.BOLD, 40));
		depoLum.setHorizontalAlignment(0);
		depoLum.setForeground(Color.decode("#412174"));
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

			// 立得存1
			JLabel lblNewLabel_1 = new JLabel("产品名称 : 立得存-自享(机具版)");
			lblNewLabel_1.setForeground(Color.decode("#ffffff"));
			lblNewLabel_1.setFont(new Font("微软雅黑", Font.PLAIN, 18));
			lblNewLabel_1.setBounds(
					148, 179, 340, 20);
			lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
			this.showJpanel.add(lblNewLabel_1);
			JLabel lblNewLabel_2 = new JLabel("存期 : 40-60个月");
			lblNewLabel_2.setForeground(Color.decode("#ffffff"));
			lblNewLabel_2.setFont(new Font("微软雅黑", Font.PLAIN, 18));
			lblNewLabel_2.setBounds(
					148, 305, 340, 20);
			lblNewLabel_2.setHorizontalAlignment(SwingConstants.LEFT);
			this.showJpanel.add(lblNewLabel_2);
			JLabel lblNewLabel_3 = new JLabel("收益类型 : 每日、每周、每月、每季、");
			lblNewLabel_3.setForeground(Color.decode("#ffffff"));
			lblNewLabel_3.setHorizontalAlignment(SwingConstants.LEFT);
			lblNewLabel_3.setFont(new Font("微软雅黑", Font.PLAIN, 18));
			lblNewLabel_3.setBounds(
					148, 221, 340, 20);
			this.showJpanel.add(lblNewLabel_3);
			JLabel lblNewLabel_4 = new JLabel(productAmt);
			lblNewLabel_4.setFont(new Font("微软雅黑", Font.PLAIN, 18));
			lblNewLabel_4.setForeground(Color.decode("#ffffff"));
			lblNewLabel_4.setHorizontalAlignment(SwingConstants.LEFT);
			lblNewLabel_4.setBounds(
					148, 347, 340, 20);
			this.showJpanel.add(lblNewLabel_4);
			JLabel lblNewLabel_5 = new JLabel("存款收益帐号绑定 : 指定本人名下");
			lblNewLabel_5.setForeground(Color.decode("#ffffff"));
			lblNewLabel_5.setHorizontalAlignment(SwingConstants.LEFT);
			lblNewLabel_5.setFont(new Font("微软雅黑", Font.PLAIN, 18));
			lblNewLabel_5.setBounds(
					148, 389, 340, 20);
			this.showJpanel.add(lblNewLabel_5);

			JLabel lblNewLabel_6 = new JLabel("每半年、每年");
			lblNewLabel_6.setForeground(Color.decode("#ffffff"));
			lblNewLabel_6.setHorizontalAlignment(SwingConstants.LEFT);
			lblNewLabel_6.setFont(new Font("微软雅黑", Font.PLAIN, 18));
			lblNewLabel_6.setBounds(
					148, 263, 340, 20);
			this.showJpanel.add(lblNewLabel_6);
			JLabel lblNewLabel_7 = new JLabel("的一张唐山银行的银行卡账户为收");
			lblNewLabel_7.setForeground(Color.decode("#ffffff"));
			lblNewLabel_7.setHorizontalAlignment(SwingConstants.LEFT);
			lblNewLabel_7.setFont(new Font("微软雅黑", Font.PLAIN, 18));
			lblNewLabel_7.setBounds(
					148, 431, 340, 20);
			this.showJpanel.add(lblNewLabel_7);
			JLabel lblNewLabel_7_1 = new JLabel("益账户");
			lblNewLabel_7_1.setForeground(Color.decode("#ffffff"));
			lblNewLabel_7_1.setHorizontalAlignment(SwingConstants.LEFT);
			lblNewLabel_7_1.setFont(new Font("微软雅黑", Font.PLAIN, 18));
			lblNewLabel_7_1.setBounds(
					148, 473, 340, 20);
			this.showJpanel.add(lblNewLabel_7_1);
			JLabel image = new JLabel(new ImageIcon("pic/newPic/LDCF.png"));
			image.setHorizontalAlignment(SwingConstants.LEFT);
			image.setHorizontalTextPosition(SwingConstants.CENTER);
			image.setSize(340, 360);
			image.setLocation(118, 157);
			image.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					logger.info("点击立得存1按钮");
					if(!on_off){
						logger.info("开关当前状态"+on_off);
						return;
					}
					logger.info("开关当前状态"+on_off);
					on_off=false;
					// /立得存1
					nextPage1(comp, parames);
				}
			});
			this.showJpanel.add(image);

			// 立得存2
			JLabel lblNewLabel_8 = new JLabel("产品名称 : 立得存-他享(机具版)");
			lblNewLabel_8.setForeground(Color.decode("#ffffff"));
			lblNewLabel_8.setFont(new Font("微软雅黑", Font.PLAIN, 18));
			lblNewLabel_8.setBounds(556, 179,
					340, 20);
			lblNewLabel_8.setHorizontalAlignment(SwingConstants.LEFT);
			this.showJpanel.add(lblNewLabel_8);
			JLabel lblNewLabel_9 = new JLabel("存期 : 40-60个月");
			lblNewLabel_9.setForeground(Color.decode("#ffffff"));
			lblNewLabel_9.setFont(new Font("微软雅黑", Font.PLAIN, 18));
			lblNewLabel_9.setBounds(556, 305,
					340, 20);
			lblNewLabel_9.setHorizontalAlignment(SwingConstants.LEFT);
			this.showJpanel.add(lblNewLabel_9);
			JLabel lblNewLabel_10 = new JLabel("收益类型 : 每日、每周、每月、每季、");
			lblNewLabel_10.setForeground(Color.decode("#ffffff"));
			lblNewLabel_10.setHorizontalAlignment(SwingConstants.LEFT);
			lblNewLabel_10.setFont(new Font("微软雅黑", Font.PLAIN, 18));
			lblNewLabel_10.setBounds(556, 221,
					340, 20);
			this.showJpanel.add(lblNewLabel_10);
			JLabel lblNewLabel_11 = new JLabel(productAmt);
			lblNewLabel_11.setFont(new Font("微软雅黑", Font.PLAIN, 18));
			lblNewLabel_11.setForeground(Color.decode("#ffffff"));
			lblNewLabel_11.setHorizontalAlignment(SwingConstants.LEFT);
			lblNewLabel_11.setBounds(556, 347,
					340, 20);
			this.showJpanel.add(lblNewLabel_11);
			JLabel lblNewLabel_12 = new JLabel("存款收益帐号绑定 : 指定除本人以");
			lblNewLabel_12.setForeground(Color.decode("#ffffff"));
			lblNewLabel_12.setHorizontalAlignment(SwingConstants.LEFT);
			lblNewLabel_12.setFont(new Font("微软雅黑", Font.PLAIN, 18));
			lblNewLabel_12.setBounds(556, 389,
					330, 20);
			this.showJpanel.add(lblNewLabel_12);

			JLabel lblNewLabel_13 = new JLabel("每半年、每年");
			lblNewLabel_13.setForeground(Color.decode("#ffffff"));
			lblNewLabel_13.setHorizontalAlignment(SwingConstants.LEFT);
			lblNewLabel_13.setFont(new Font("微软雅黑", Font.PLAIN, 18));
			lblNewLabel_13.setBounds(556, 263,
					340, 20);
			this.showJpanel.add(lblNewLabel_13);
			JLabel lblNewLabel_14 = new JLabel("外的第三人名下的一张唐山银行的");
			lblNewLabel_14.setForeground(Color.decode("#ffffff"));
			lblNewLabel_14.setHorizontalAlignment(SwingConstants.LEFT);
			lblNewLabel_14.setFont(new Font("微软雅黑", Font.PLAIN, 18));
			lblNewLabel_14.setBounds(556, 431,
					326, 20);
			this.showJpanel.add(lblNewLabel_14);
			JLabel lblNewLabel_15 = new JLabel("银行卡账户为收益账户");
			lblNewLabel_15.setForeground(Color.decode("#ffffff"));
			lblNewLabel_15.setHorizontalAlignment(SwingConstants.LEFT);
			lblNewLabel_15.setFont(new Font("微软雅黑", Font.PLAIN, 18));
			lblNewLabel_15.setBounds(556, 473,
					340, 20);
			this.showJpanel.add(lblNewLabel_15);
			JLabel image1 = new JLabel(new ImageIcon("pic/newPic/LDCF.png"));
			image1.setHorizontalAlignment(SwingConstants.LEFT);
			image1.setSize(340, 360);
			image1.setHorizontalTextPosition(SwingConstants.CENTER);
			image1.setLocation(526, 157);
			image1.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					logger.info("点击立得存2按钮");
					if(!on_off){
						logger.info("开关当前状态"+on_off);
						return;
					}
					logger.info("开关当前状态"+on_off);
					on_off=false;
					// 立得存2
					nextPage2(comp, parames);
				}
			});
			this.showJpanel.add(image1);
		} else {
			// 立得存1
			JLabel lblNewLabel_1 = new JLabel("产品名称 : 立得存-自享(机具版)");
			lblNewLabel_1.setForeground(Color.decode("#ffffff"));
			lblNewLabel_1.setFont(new Font("微软雅黑", Font.PLAIN, 18));
			lblNewLabel_1.setBounds(
					148, 179, 340, 20);
			lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
			this.showJpanel.add(lblNewLabel_1);
			JLabel lblNewLabel_2 = new JLabel("存期 : 40-60个月");
			lblNewLabel_2.setForeground(Color.decode("#ffffff"));
			lblNewLabel_2.setFont(new Font("微软雅黑", Font.PLAIN, 18));
			lblNewLabel_2.setBounds(
					148, 305, 340, 20);
			lblNewLabel_2.setHorizontalAlignment(SwingConstants.LEFT);
			this.showJpanel.add(lblNewLabel_2);
			JLabel lblNewLabel_3 = new JLabel("收益类型 : 每日、每周、每月、每季、");
			lblNewLabel_3.setForeground(Color.decode("#ffffff"));
			lblNewLabel_3.setHorizontalAlignment(SwingConstants.LEFT);
			lblNewLabel_3.setFont(new Font("微软雅黑", Font.PLAIN, 18));
			lblNewLabel_3.setBounds(
					148, 221, 340, 20);
			this.showJpanel.add(lblNewLabel_3);
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
			lblNewLabel_4.setBounds(
					148, 347, 340, 20);
			this.showJpanel.add(lblNewLabel_4);
			JLabel lblNewLabel_5 = new JLabel("存款收益帐号绑定 : 指定本人名下");
			lblNewLabel_5.setForeground(Color.decode("#ffffff"));
			lblNewLabel_5.setHorizontalAlignment(SwingConstants.LEFT);
			lblNewLabel_5.setFont(new Font("微软雅黑", Font.PLAIN, 18));
			lblNewLabel_5.setBounds(
					148, 389, 340, 20);
			this.showJpanel.add(lblNewLabel_5);

			JLabel lblNewLabel_6 = new JLabel("每半年、每年");
			lblNewLabel_6.setForeground(Color.decode("#ffffff"));
			lblNewLabel_6.setHorizontalAlignment(SwingConstants.LEFT);
			lblNewLabel_6.setFont(new Font("微软雅黑", Font.PLAIN, 18));
			lblNewLabel_6.setBounds(
					148, 263, 340, 20);
			this.showJpanel.add(lblNewLabel_6);
			JLabel lblNewLabel_7 = new JLabel("的一张唐山银行的银行卡账户为收");
			lblNewLabel_7.setForeground(Color.decode("#ffffff"));
			lblNewLabel_7.setHorizontalAlignment(SwingConstants.LEFT);
			lblNewLabel_7.setFont(new Font("微软雅黑", Font.PLAIN, 18));
			lblNewLabel_7.setBounds(
					148, 431, 340, 20);
			this.showJpanel.add(lblNewLabel_7);
			JLabel lblNewLabel_7_1 = new JLabel("益账户");
			lblNewLabel_7_1.setForeground(Color.decode("#ffffff"));
			lblNewLabel_7_1.setHorizontalAlignment(SwingConstants.LEFT);
			lblNewLabel_7_1.setFont(new Font("微软雅黑", Font.PLAIN, 18));
			lblNewLabel_7_1.setBounds(
					148, 473, 340, 20);
			this.showJpanel.add(lblNewLabel_7_1);
			JLabel image = new JLabel(new ImageIcon("pic/newPic/LDCF.png"));
			image.setHorizontalAlignment(SwingConstants.LEFT);
			image.setHorizontalTextPosition(SwingConstants.CENTER);
			image.setSize(340, 360);
			image.setLocation(118, 157);
			image.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					logger.info("点击立得存1按钮");
					if(!on_off){
						logger.info("开关当前状态"+on_off);
						return;
					}
					logger.info("开关当前状态"+on_off);
					on_off=false;
					// /立得存1
					nextPage1(comp, parames);
				}
			});
			this.showJpanel.add(image);

			// 立得存2
			JLabel lblNewLabel_8 = new JLabel("产品名称 : 立得存-他享(机具版)");
			lblNewLabel_8.setForeground(Color.decode("#ffffff"));
			lblNewLabel_8.setFont(new Font("微软雅黑", Font.PLAIN, 18));
			lblNewLabel_8.setBounds(556, 179,
					340, 20);
			lblNewLabel_8.setHorizontalAlignment(SwingConstants.LEFT);
			this.showJpanel.add(lblNewLabel_8);
			JLabel lblNewLabel_9 = new JLabel("存期 : 40-60个月");
			lblNewLabel_9.setForeground(Color.decode("#ffffff"));
			lblNewLabel_9.setFont(new Font("微软雅黑", Font.PLAIN, 18));
			lblNewLabel_9.setBounds(556, 305,
					340, 20);
			lblNewLabel_9.setHorizontalAlignment(SwingConstants.LEFT);
			this.showJpanel.add(lblNewLabel_9);
			JLabel lblNewLabel_10 = new JLabel("收益类型 : 每日、每周、每月、每季、");
			lblNewLabel_10.setForeground(Color.decode("#ffffff"));
			lblNewLabel_10.setHorizontalAlignment(SwingConstants.LEFT);
			lblNewLabel_10.setFont(new Font("微软雅黑", Font.PLAIN, 18));
			lblNewLabel_10.setBounds(556, 221,
					340, 20);
			this.showJpanel.add(lblNewLabel_10);
			JLabel lblNewLabel_11 = new JLabel("产品额度 : 1-30万");
			lblNewLabel_11.setFont(new Font("微软雅黑", Font.PLAIN, 18));
			lblNewLabel_11.setForeground(Color.decode("#ffffff"));
			lblNewLabel_11.setHorizontalAlignment(SwingConstants.LEFT);
			lblNewLabel_11.setBounds(556, 347,
					340, 20);
			this.showJpanel.add(lblNewLabel_11);
			JLabel lblNewLabel_12 = new JLabel("存款收益帐号绑定 : 指定除本人以");
			lblNewLabel_12.setForeground(Color.decode("#ffffff"));
			lblNewLabel_12.setHorizontalAlignment(SwingConstants.LEFT);
			lblNewLabel_12.setFont(new Font("微软雅黑", Font.PLAIN, 18));
			lblNewLabel_12.setBounds(556, 389,
					330, 20);
			this.showJpanel.add(lblNewLabel_12);

			JLabel lblNewLabel_13 = new JLabel("每半年、每年");
			lblNewLabel_13.setForeground(Color.decode("#ffffff"));
			lblNewLabel_13.setHorizontalAlignment(SwingConstants.LEFT);
			lblNewLabel_13.setFont(new Font("微软雅黑", Font.PLAIN, 18));
			lblNewLabel_13.setBounds(556, 263,
					340, 20);
			this.showJpanel.add(lblNewLabel_13);
			JLabel lblNewLabel_14 = new JLabel("外的第三人名下的一张唐山银行的");
			lblNewLabel_14.setForeground(Color.decode("#ffffff"));
			lblNewLabel_14.setHorizontalAlignment(SwingConstants.LEFT);
			lblNewLabel_14.setFont(new Font("微软雅黑", Font.PLAIN, 18));
			lblNewLabel_14.setBounds(556, 431,
					326, 20);
			this.showJpanel.add(lblNewLabel_14);
			JLabel lblNewLabel_15 = new JLabel("银行卡账户为收益账户");
			lblNewLabel_15.setForeground(Color.decode("#ffffff"));
			lblNewLabel_15.setHorizontalAlignment(SwingConstants.LEFT);
			lblNewLabel_15.setFont(new Font("微软雅黑", Font.PLAIN, 18));
			lblNewLabel_15.setBounds(556, 473,
					340, 20);
			this.showJpanel.add(lblNewLabel_15);
			JLabel image1 = new JLabel(new ImageIcon("pic/newPic/LDCF.png"));
			image1.setHorizontalAlignment(SwingConstants.LEFT);
			image1.setSize(340, 360);
			image1.setHorizontalTextPosition(SwingConstants.CENTER);
			image1.setLocation(526, 157);
			image1.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					logger.info("点击立得存2按钮");
					if(!on_off){
						logger.info("开关当前状态"+on_off);
						return;
					}
					logger.info("开关当前状态"+on_off);
					on_off=false;
					// 立得存2
					nextPage2(comp, parames);
				}
			});
			this.showJpanel.add(image1);
		}
		
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
				/* 处理上一页 */
				backTrans();
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
		add(backButton);
	}

	/**
	 * 上一步
	 */
	public void backTrans() {
		logger.info("进入上一步方法");
		clearTimeText();//清空倒计时
		// 取标识判断上一步跳转协议还是私行
		if ("1".equals(parames.get("CHL_ID")) || "5".equals(parames.get("CHL_ID"))) {// 协议
			AccProtocolDepPanel acc = new AccProtocolDepPanel(parames);
			openPanel(acc);
		} else if ("2".equals(parames.get("CHL_ID"))) {// 私行
			AccPrivateLinePanel acc = new AccPrivateLinePanel(parames);
			openPanel(acc);
		}		
	}

	

	/**
	 * 立得存1
	 * */
	public void nextPage1(final Component comp, final Map<Object, Object> params) {
		logger.info("进入立得存1的方法");
		clearTimeText();//清空倒计时
		// 调用查询自享产品信息
		params.put("PRODUCT_CODE", "LZ");// 产品标识
		params.put("transCode", "0016");
		AccountTransFlow.startTransFlow(comp, params);
	}

	/**
	 * 立得存2
	 * */
	public void nextPage2(final Component comp, final Map<Object, Object> params) {
		logger.info("进入立得存2的方法");
		clearTimeText();//清空倒计时
		// 调用查询他享产品信息
		params.put("PRODUCT_CODE", "LT");// 产品标识
		params.put("transCode", "0016");
		AccountTransFlow.startTransFlow(comp, params);
	}
}
