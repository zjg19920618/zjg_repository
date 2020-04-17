package com.boomhope.Bill.TransService.CashOpen.moneybox;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import sun.audio.AudioStream;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.TransService.CashOpen.Bean.PublicCashOpenBean;

/***
 * 现金开户收益人信息页面
 * @author gyw
 *
 */
public class MoneyBoxSYRPage extends BaseTransPanelNew{
	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(MoneyBoxSYRPage.class);
	private boolean on_off=true;//开关控制
	public MoneyBoxSYRPage(final PublicCashOpenBean transBean){
		this.cashBean= transBean;
		/* 显示时间倒计时 */
		this.showTimeText(delaySecondLongTime);
		delayTimer = new Timer(delaySecondLongTime * 1000, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {  
            	on_off=false;
            	/* 倒计时结束退出交易 */ 
            	clearTimeText();
				serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！","","");
            }      
        });
		JLabel lblNewLabel = new JLabel("收益人信息");
		lblNewLabel.setFont(new Font("微软雅黑", Font.BOLD, 40));
		lblNewLabel.setForeground(Color.decode("#412174"));
		lblNewLabel.setHorizontalAlignment(0);
		lblNewLabel.setBounds(0, 60, 1009, 60);
		this.showJpanel.add(lblNewLabel);
		
		JLabel label = new JLabel("收益人卡号：");
		label.setFont(new Font("微软雅黑", Font.PLAIN, 30));
		label.setBounds(200, 260, 200, 30);
		this.showJpanel.add(label);
		
		JLabel label_1 = new JLabel("收益人：");
		label_1.setFont(new Font("微软雅黑", Font.PLAIN, 30));
		label_1.setBounds(200, 340, 200, 30);
		this.showJpanel.add(label_1);
		
		JLabel lblNewLabel_1 = new JLabel(transBean.getInputNo());
		lblNewLabel_1.setFont(new Font("微软雅黑", Font.PLAIN, 30));
		lblNewLabel_1.setBounds(420, 254, 500, 45);
		this.showJpanel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel();
		lblNewLabel_2.setFont(new Font("微软雅黑", Font.PLAIN, 30));
		lblNewLabel_2.setBounds(420, 340, 191, 34);
		this.showJpanel.add(lblNewLabel_2);
		if(transBean.getLdcType().equals("0")){
			String str1=transBean.getCardName();
			lblNewLabel_2.setText(str1.replace(str1.substring(0, 1),"*"));
		}else if(transBean.getLdcType().equals("1")){
			String a=transBean.getLdcTXacctName();
			lblNewLabel_2.setText(a.replace(a.substring(0, 1),"*"));
		}else if(transBean.getLdcType().equals("2")){
			String a=transBean.getCardName();
			lblNewLabel_2.setText(a.replace(a.substring(0, 1),"*"));
				}

		//确认
		JLabel lblNewLabel1 = new JLabel(new ImageIcon("pic/newPic/confirm.png"));
		lblNewLabel1.setBounds(890, 770, 150, 50);
		lblNewLabel1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				/* 处理下一页 */
				logger.info("点击确认按钮");
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				okTrans(transBean);
			}

		});
		this.add(lblNewLabel1);
		//上一步
		JLabel label1 = new JLabel(new ImageIcon("pic/preStep.png"));
		label1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击上一步按钮");	
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				backTrans(transBean);
			}

		});
		label1.setBounds(1075, 770, 150, 50);
		this.add(label1);
		//退出
		JLabel label_11 = new JLabel(new ImageIcon("pic/newPic/exit.png"));
		label_11.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击退出按钮");	
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				returnHome();
			}

		});
		label_11.setBounds(1258, 770, 150, 50);
		this.add(label_11);
	
	}
	/**
	 * 返回
	 * */
	public void backTrans(PublicCashOpenBean transBean) {
		clearTimeText();
		openPanel(new MoneyBoxInputBankCardPanel(transBean));
		
	}
	
	/**
	 * 确认
	 * */
	public void okTrans(PublicCashOpenBean transBean) {
		clearTimeText();
		//如果是新建客户或者金额大于等于5万走授权流程
		String customer_identification = transBean.getImportMap().get("customer_identification");
//		int money = Integer.parseInt(transBean.getOrderAmount().replace(".00", ""));
		if(transBean.getMoney()>=50000.00){
			//判断跳转如果是等于yes就是代理人代理 跳转代理人页面
			if("yes".equals(transBean.getImportMap().get("agent_persion"))){
				//跳转代理人授权页面
				openPanel(new MoneyBoxExistProcuratorPanel(transBean));
				
			}else if ("no".equals(transBean.getImportMap().get("agent_persion"))){
				//跳转本人授权页面
				openPanel(new MoneyBoxNegationProcuratorPanel(transBean));
			}							
		}
		//判断 金额如果小于5万走简单流程 
		if(transBean.getMoney()<50000.00){
			//确认存单信息页面
			openPanel(new MoneyBoxOkInputDepinfoPanel(transBean));
			
		}
	}
	}
