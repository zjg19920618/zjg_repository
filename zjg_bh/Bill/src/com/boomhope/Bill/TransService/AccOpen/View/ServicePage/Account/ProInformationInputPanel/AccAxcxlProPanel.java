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
import com.boomhope.Bill.Public.GlobalParameter;
import com.boomhope.Bill.PublicControl.OutputBankCardPanel;
import com.boomhope.Bill.TransService.AccOpen.View.ServicePage.Account.PrivateLine.AccPrivateLinePanel;
import com.boomhope.Bill.TransService.AccOpen.View.ServicePage.Account.ProtocolDeposit.AccProtocolDepPanel;
import com.boomhope.Bill.TransService.AccOpen.controller.AccountTradeCodeAction;
import com.boomhope.Bill.TransService.AccOpen.controller.AccountTransFlow;

/***
 * 安享存系列页面
 * 
 * @author zjg
 *
 */
public class AccAxcxlProPanel extends BaseTransPanelNew {
	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(AccAxcxlProPanel.class);
	Map<Object, Object> parames = null;
	private boolean on_off=true;

	public AccAxcxlProPanel(final Map<Object, Object> parames) {
		logger.info("进入安享存系列页面");

		// 将当前页面传入流程控制进行操作
		final Component comp = this;
		this.parames = parames;

		/* 显示时间倒计时 */
		this.showTimeText(delaySecondShortTime);
		delayTimer = new Timer(delaySecondShortTime * 1000, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {  
            	logger.info("安享存系列页面倒计时结束 ");
            	/* 倒计时结束退出交易 */ 
            	clearTimeText();
            	serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！","","");
            }      
        });            
		delayTimer.start(); 
		// 标题
		JLabel depoLum = new JLabel("安 享 存");
		depoLum.setFont(new Font("微软雅黑", Font.BOLD, 40));
		depoLum.setForeground(Color.decode("#412174"));
		depoLum.setBounds((GlobalParameter.TRANS_WIDTH-150)/2, 50, 150, 80);
		this.showJpanel.add(depoLum);

		if ("2".equals(parames.get("CHL_ID"))) {
			// 私行安享存
			JLabel lblNewLabel_1 = new JLabel("产品名称 : 安享存(机具版)");
			lblNewLabel_1.setForeground(Color.decode("#ffffff"));
			lblNewLabel_1.setFont(new Font("微软雅黑", Font.PLAIN, 18));
			lblNewLabel_1.setBounds(
					389, 202, 340, 20);
			lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
			this.showJpanel.add(lblNewLabel_1);
			JLabel lblNewLabel_2 = new JLabel("存期 : 3年");
			lblNewLabel_2.setForeground(Color.decode("#ffffff"));
			lblNewLabel_2.setFont(new Font("微软雅黑", Font.PLAIN, 18));
			lblNewLabel_2.setBounds(389, 286, 340, 20);
			lblNewLabel_2.setHorizontalAlignment(SwingConstants.LEFT);
			this.showJpanel.add(lblNewLabel_2);
			JLabel lblNewLabel_3 = new JLabel("收益类型 : 每年");
			lblNewLabel_3.setForeground(Color.decode("#ffffff"));
			lblNewLabel_3.setHorizontalAlignment(SwingConstants.LEFT);
			lblNewLabel_3.setFont(new Font("微软雅黑", Font.PLAIN, 18));
			lblNewLabel_3.setBounds(
					389, 244, 340, 20);
			this.showJpanel.add(lblNewLabel_3);

			//根据不同的客户级别显示不同的开户额度
			String productAmt = "";
			if("1".equals(AccountTradeCodeAction.transBean.getCustLevel().trim()) 
					|| "2".equals(AccountTradeCodeAction.transBean.getCustLevel().trim())
					|| "3".equals(AccountTradeCodeAction.transBean.getCustLevel().trim())){
					productAmt = "产品额度 :1-1000万";
				}else{
					productAmt = "产品额度 :50-1000万";
				}
			JLabel lblNewLabel_4 = new JLabel(productAmt);
			lblNewLabel_4.setFont(new Font("微软雅黑", Font.PLAIN, 18));
			lblNewLabel_4.setForeground(Color.decode("#ffffff"));
			lblNewLabel_4.setHorizontalAlignment(SwingConstants.LEFT);
			lblNewLabel_4.setBounds(389, 327, 340, 20);
			this.showJpanel.add(lblNewLabel_4);
			JLabel lblNewLabel_5 = new JLabel("存款收益帐号绑定 : 指定本人名下");
			lblNewLabel_5.setForeground(Color.decode("#ffffff"));
			lblNewLabel_5.setHorizontalAlignment(SwingConstants.LEFT);
			lblNewLabel_5.setFont(new Font("微软雅黑", Font.PLAIN, 18));
			lblNewLabel_5.setBounds(389, 370, 340, 20);
			this.showJpanel.add(lblNewLabel_5);

			JLabel lblNewLabel_7 = new JLabel("的一张唐山银行的银行卡账户为收");
			lblNewLabel_7.setForeground(Color.decode("#ffffff"));
			lblNewLabel_7.setHorizontalAlignment(SwingConstants.LEFT);
			lblNewLabel_7.setFont(new Font("微软雅黑", Font.PLAIN, 18));
			lblNewLabel_7.setBounds(389, 412, 340, 20);
			this.showJpanel.add(lblNewLabel_7);
			JLabel lblNewLabel_7_1 = new JLabel("益账户");
			lblNewLabel_7_1.setForeground(Color.decode("#ffffff"));
			lblNewLabel_7_1.setHorizontalAlignment(SwingConstants.LEFT);
			lblNewLabel_7_1.setFont(new Font("微软雅黑", Font.PLAIN, 18));
			lblNewLabel_7_1.setBounds(389, 454, 340, 20);
			this.showJpanel.add(lblNewLabel_7_1);
			JLabel image = new JLabel(new ImageIcon("pic/newPic/AXCF.png"));
			image.setHorizontalAlignment(SwingConstants.LEFT);
			image.setHorizontalTextPosition(SwingConstants.CENTER);
			image.setSize(340, 360);
			image.setLocation(359, 157);
			image.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					logger.info("点击进入私行安享存按钮");
					if(!on_off){
						logger.info("开关当前状态"+on_off);
						return;
					}
					logger.info("开关当前状态"+on_off);
					on_off=false;
					// 私行安享存
					nextPage1(comp, parames);
				}
			});
			this.showJpanel.add(image);
		}
		if ("1".equals(parames.get("CHL_ID")) || "5".equals(parames.get("CHL_ID"))) {
			String typeInfo = "";
			if("1".equals(parames.get("CHL_ID"))){
				typeInfo="(机具版)";
			}else{
				typeInfo="(普惠版)";
			}
			// 协议安享存
			JLabel lblNewLabel_1 = new JLabel("产品名称 : 安享存"+typeInfo);
			lblNewLabel_1.setForeground(Color.decode("#ffffff"));
			lblNewLabel_1.setFont(new Font("微软雅黑", Font.PLAIN, 18));
			lblNewLabel_1.setBounds(
					389, 202, 340, 20);
			lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
			this.showJpanel.add(lblNewLabel_1);
			JLabel lblNewLabel_2 = new JLabel("存期 : 3年");
			lblNewLabel_2.setForeground(Color.decode("#ffffff"));
			lblNewLabel_2.setFont(new Font("微软雅黑", Font.PLAIN, 18));
			lblNewLabel_2.setBounds(389, 244, 340, 20);
			lblNewLabel_2.setHorizontalAlignment(SwingConstants.LEFT);
			this.showJpanel.add(lblNewLabel_2);
			JLabel lblNewLabel_3 = new JLabel("收益类型 : 每年");
			lblNewLabel_3.setForeground(Color.decode("#ffffff"));
			lblNewLabel_3.setHorizontalAlignment(SwingConstants.LEFT);
			lblNewLabel_3.setFont(new Font("微软雅黑", Font.PLAIN, 18));
			lblNewLabel_3.setBounds(
					389, 286, 340, 20);
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
			lblNewLabel_4.setBounds(389, 327, 340, 20);
			this.showJpanel.add(lblNewLabel_4);
			JLabel lblNewLabel_5 = new JLabel("存款收益帐号绑定 : 指定本人名下");
			lblNewLabel_5.setForeground(Color.decode("#ffffff"));
			lblNewLabel_5.setHorizontalAlignment(SwingConstants.LEFT);
			lblNewLabel_5.setFont(new Font("微软雅黑", Font.PLAIN, 18));
			lblNewLabel_5.setBounds(389, 370, 340, 20);
			this.showJpanel.add(lblNewLabel_5);

			JLabel lblNewLabel_7 = new JLabel("的一张唐山银行的银行卡账户为收");
			lblNewLabel_7.setForeground(Color.decode("#ffffff"));
			lblNewLabel_7.setHorizontalAlignment(SwingConstants.LEFT);
			lblNewLabel_7.setFont(new Font("微软雅黑", Font.PLAIN, 18));
			lblNewLabel_7.setBounds(389, 412, 340, 20);
			this.showJpanel.add(lblNewLabel_7);
			JLabel lblNewLabel_7_1 = new JLabel("益账户");
			lblNewLabel_7_1.setForeground(Color.decode("#ffffff"));
			lblNewLabel_7_1.setHorizontalAlignment(SwingConstants.LEFT);
			lblNewLabel_7_1.setFont(new Font("微软雅黑", Font.PLAIN, 18));
			lblNewLabel_7_1.setBounds(389,454, 340, 20);
			this.showJpanel.add(lblNewLabel_7_1);
			JLabel image = new JLabel(new ImageIcon("pic/newPic/AXCF.png"));
			image.setHorizontalAlignment(SwingConstants.LEFT);
			image.setHorizontalTextPosition(SwingConstants.CENTER);
			image.setSize(340, 360);
			image.setLocation(359, 157);
			image.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					logger.info("点击进入私行安享存按钮");
					if(!on_off){
						logger.info("开关当前状态"+on_off);
						return;
					}
					logger.info("开关当前状态"+on_off);
					on_off=false;
					// 协议安享和普惠
					nextPage1(comp, parames);
				}
			});
			this.showJpanel.add(image);
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
		if ("1".equals(parames.get("CHL_ID")) || "5".equals(parames.get("CHL_ID"))) {// 协议
			logger.info("进入协议上一页"+parames.get("CHL_ID"));
			AccProtocolDepPanel acc = new AccProtocolDepPanel(parames);
			openPanel(acc);
		} else if ("2".equals(parames.get("CHL_ID"))) {// 私行
			logger.info("进入私行上一页"+parames.get("CHL_ID"));
			AccPrivateLinePanel acc = new AccPrivateLinePanel(parames);
			openPanel(acc);
		}
	}

	

	/**
	 * 安享存
	 * */
	public void nextPage1(final Component comp, final Map<Object, Object> params) {
		logger.info("进入安享存的方法");
		clearTimeText();// 清空倒计时
		// 调用查询子产品信息
		params.put("PRODUCT_CODE", "AX");// 产品标识
		params.put("transCode", "0016");
		AccountTransFlow.startTransFlow(comp, params);
	}
}
