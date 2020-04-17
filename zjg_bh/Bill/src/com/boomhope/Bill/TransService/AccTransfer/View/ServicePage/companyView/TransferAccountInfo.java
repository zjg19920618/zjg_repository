package com.boomhope.Bill.TransService.AccTransfer.View.ServicePage.companyView;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.PublicControl.OutputBankCardPanel;
import com.boomhope.Bill.TransService.AccTransfer.Action.TransferAction;
import com.boomhope.Bill.TransService.AccTransfer.Bean.PublicAccTransferBean;
/**
 * 账户信息展示
 * @author hk
 *
 */

public class TransferAccountInfo extends BaseTransPanelNew{
	static Logger logger = Logger.getLogger(TransferAccountInfo.class);
	private static final long serialVersionUID = 1L;
	private boolean on_off=true;
	
	public TransferAccountInfo(final PublicAccTransferBean transferBean){
		logger.info("进入信息展示页面");
		
		//设置倒计时
		this.showTimeText(delaySecondShortTime);
		delayTimer = new Timer(delaySecondShortTime * 1000, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {  
            	/* 倒计时结束退出交易 */ 
            	clearTimeText();
            	serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！","","");
            }      
        });            
		delayTimer.start();
		//账户信息
		JLabel label = new JLabel("账户信息");
		label.setFont(new Font("微软雅黑",Font.BOLD,40));
		label.setHorizontalAlignment(0);
		label.setBounds(0,60,1009,40);
		this.showJpanel.add(label);
		
		//设置交易笔数 
		if(transferBean.getTransNo()==null || "".equals(transferBean.getTransNo())){
			transferBean.setTransNo("1");
		}
		
		//判断是个人还是单位汇款
		if("1".equals(transferBean.getIsCardBin())){
			//卡号：
			JLabel label1 = new JLabel("卡               号：");
			label1.setFont(new Font("微软雅黑",Font.BOLD,25));
			label1.setHorizontalAlignment(SwingConstants.RIGHT);
			label1.setBounds(230, 160, 210, 40);
			this.showJpanel.add(label1);
			
			String chuZhangCardNo = transferBean.getChuZhangCardNo();
			if(!"".equals(chuZhangCardNo) && null!=chuZhangCardNo ){
				chuZhangCardNo="<HTML>"+chuZhangCardNo+"</br></HTML>";
			}else{
				chuZhangCardNo="";
			}
			//卡号
			JLabel label2 = new JLabel(chuZhangCardNo);
			label2.setFont(new Font("微软雅黑",Font.PLAIN,25));
			label2.setHorizontalTextPosition(SwingConstants.LEFT);
			label2.setBounds(440, 160, 569, 40);
			this.showJpanel.add(label2);
			
			//持卡人户名：
			JLabel label3 = new JLabel("持 卡 人 户 名：");
			label3.setFont(new Font("微软雅黑",Font.BOLD,25));
			label3.setHorizontalAlignment(SwingConstants.RIGHT);
			label3.setBounds(230, 220, 210, 40);
			this.showJpanel.add(label3);
			
			String chuZhangAcctName =transferBean.getChuZhangAcctName();
			if(!"".equals(chuZhangAcctName) && null!=chuZhangAcctName ){
				chuZhangAcctName="<HTML>"+chuZhangAcctName+"</br></HTML>";
			}else{
				chuZhangAcctName="";
			}
			//持卡人户名信息
			JLabel label4 = new JLabel(chuZhangAcctName);
			label4.setFont(new Font("微软雅黑",Font.PLAIN,25));
			label4.setHorizontalTextPosition(SwingConstants.LEFT);
			label4.setBounds(440, 220, 569, 40);
			this.showJpanel.add(label4);
			
			//结算账号：
			JLabel label5 = new JLabel("结  算   账  号：");
			label5.setFont(new Font("微软雅黑",Font.BOLD,25));
			label5.setHorizontalAlignment(SwingConstants.RIGHT);
			label5.setBounds(230, 280, 210, 40);
			this.showJpanel.add(label5);
			
			String chuZhangAcctNo = transferBean.getChuZhangAcctNo();
			if(!"".equals(chuZhangAcctNo) && null!=chuZhangAcctNo ){
				chuZhangAcctNo="<HTML>"+chuZhangAcctNo+"</br></HTML>";
			}else{
				chuZhangAcctNo="";
			}
			//结算账号信息
			JLabel label6 = new JLabel(chuZhangAcctNo);
			label6.setFont(new Font("微软雅黑",Font.PLAIN,25));
			label6.setHorizontalTextPosition(SwingConstants.LEFT);
			label6.setBounds(440, 280, 569, 40);
			this.showJpanel.add(label6);
			
			String chuZhangcardName =transferBean.getChuZhangcardName();
			if(!"".equals(chuZhangcardName) && null!=chuZhangcardName ){
				chuZhangcardName="<HTML>"+chuZhangcardName+"</br></HTML>";
			}else{
				chuZhangcardName="";
			}
			//户名：
			JLabel label7 = new JLabel("户              名：");
			label7.setFont(new Font("微软雅黑",Font.BOLD,25));
			label7.setHorizontalAlignment(SwingConstants.RIGHT);
			label7.setBounds(230, 340, 210, 40);
			this.showJpanel.add(label7);
			
			//户名信息
			JLabel label8 = new JLabel(chuZhangcardName);
			label8.setFont(new Font("微软雅黑",Font.PLAIN,25));
			label8.setHorizontalTextPosition(SwingConstants.LEFT);
			label8.setBounds(440, 340, 569, 40);
			this.showJpanel.add(label8);
			
			//开户行：
			JLabel label9 = new JLabel("开      户     行：");
			label9.setFont(new Font("微软雅黑",Font.BOLD,25));
			label9.setHorizontalAlignment(SwingConstants.RIGHT);
			label9.setBounds(230, 510, 210, 60);
			this.showJpanel.add(label9);
			
			String instName =transferBean.getPayHbrInstName();
			if(!"".equals(instName) && null!=instName ){
				instName="<HTML>"+instName+"</br></HTML>";
			}else{
				instName="";
			}
			//开户行信息
			JLabel label10 = new JLabel(instName);
			label10.setFont(new Font("微软雅黑",Font.PLAIN,25));
			label10.setHorizontalTextPosition(SwingConstants.LEFT);
			label10.setBounds(440, 510, 569, 60);
			this.showJpanel.add(label10);
			
			//账户可用余额：
			JLabel label11 = new JLabel("账户可用余额：");
			label11.setFont(new Font("微软雅黑",Font.BOLD,25));
			label11.setHorizontalAlignment(SwingConstants.RIGHT);
			label11.setBounds(230, 400, 210, 40);
			this.showJpanel.add(label11);
			
			String cardAmt =transferBean.getCardAmt();
			if(!"".equals(cardAmt) && null!=cardAmt ){
				NumberFormat fmt= NumberFormat.getCurrencyInstance();
				cardAmt=fmt.format(Double.valueOf(cardAmt));
			}else{
				cardAmt="";
			}
			//账户可用余额信息
			JLabel label12 = new JLabel(cardAmt+" 元");
			label12.setFont(new Font("微软雅黑",Font.PLAIN,25));
			label12.setHorizontalTextPosition(SwingConstants.LEFT);
			label12.setBounds(440, 400, 569, 40);
			this.showJpanel.add(label12);	
			//结存额
			JLabel label19 = new JLabel("结      存     额：");
			label19.setFont(new Font("微软雅黑",Font.BOLD,25));
			label19.setHorizontalAlignment(SwingConstants.RIGHT);
			label19.setBounds(230, 460, 210, 40);
			this.showJpanel.add(label19);
			
			String balance = transferBean.getBalance();
			if(!"".equals(balance) && null!=balance ){
				NumberFormat fmt= NumberFormat.getCurrencyInstance();
				balance=fmt.format(Double.valueOf(balance));

			}else{
				balance="";
			}
			//结存额信息
			JLabel label20 = new JLabel(balance+" 元");
			label20.setFont(new Font("微软雅黑",Font.PLAIN,25));
			label20.setHorizontalTextPosition(SwingConstants.LEFT);
			label20.setBounds(440, 460, 569, 40);
			this.showJpanel.add(label20);
			
			
			
			//进行判断是否有权限查询余额，有则显示，无则不显示 
			if(!"".equals(transferBean.getIsMoneyRoot()) && null!=transferBean.getIsMoneyRoot()){
				if("YES".equals(transferBean.getIsMoneyRoot())){
					logger.info("有查询余额权限");
					label11.setVisible(true);
					label12.setVisible(true);
					label19.setVisible(true);
					label20.setVisible(true);
				}else if("NO".equals(transferBean.getIsMoneyRoot())){

					logger.info("无查询余额权限");
					label11.setVisible(false);
					label12.setVisible(false);
					label19.setVisible(false);
					label20.setVisible(false);
					label9.setLocation(230, 400);
					label10.setLocation(440,400);
				}
			}else{
				label11.setVisible(true);
				label12.setVisible(true);
				label19.setVisible(true);
				label20.setVisible(true);
			}
			
		}else{
			//卡号：
			JLabel label1 = new JLabel("卡              号：");
			label1.setFont(new Font("微软雅黑",Font.BOLD,25));
			label1.setHorizontalAlignment(SwingConstants.RIGHT);
			label1.setBounds(230, 160, 210, 40);
			this.showJpanel.add(label1);
			
			String chuZhangCardNo = transferBean.getChuZhangCardNo();
			if(!"".equals(chuZhangCardNo) && null!=chuZhangCardNo ){
				
			}else{
				chuZhangCardNo="";
			}
			//卡号
			JLabel label2 = new JLabel(chuZhangCardNo);
			label2.setFont(new Font("微软雅黑",Font.PLAIN,25));
			label2.setHorizontalTextPosition(SwingConstants.LEFT);
			label2.setBounds(440, 160, 569, 40);
			this.showJpanel.add(label2);
			
			
			//户名：
			JLabel label3 = new JLabel("户              名：");
			label3.setFont(new Font("微软雅黑",Font.BOLD,25));
			label3.setHorizontalAlignment(SwingConstants.RIGHT);
			label3.setBounds(230, 220, 210, 40);
			this.showJpanel.add(label3);
			
			String chuZhangcardName = transferBean.getChuZhangcardName();
			if(!"".equals(chuZhangcardName) && null!=chuZhangcardName ){
				chuZhangcardName="<HTML>"+chuZhangcardName+"</br></HTML>";
			}else{
				chuZhangcardName="";
			}
			//户名信息
			JLabel label4 = new JLabel(chuZhangcardName);
			label4.setFont(new Font("微软雅黑",Font.PLAIN,25));
			label4.setHorizontalTextPosition(SwingConstants.LEFT);
			label4.setBounds(440, 220, 569, 40);
			this.showJpanel.add(label4);
			
			
			//开户行：
			JLabel label5 = new JLabel("开      户     行：");
			label5.setFont(new Font("微软雅黑",Font.BOLD,25));
			label5.setHorizontalAlignment(SwingConstants.RIGHT);
			label5.setBounds(230, 390, 210, 60);
			this.showJpanel.add(label5);
			
			String instName = transferBean.getPayHbrInstName();
			if(!"".equals(instName) && null!=instName ){
				instName="<HTML>"+instName+"</br></HTML>";
			}else{
				instName="";
			}
			//开户行信息
			JLabel label6 = new JLabel(instName);
			label6.setFont(new Font("微软雅黑",Font.PLAIN,25));
			label6.setHorizontalTextPosition(SwingConstants.LEFT);
			label6.setBounds(440, 390, 569, 60);
			this.showJpanel.add(label6);
			
			//账户可用余额：
			JLabel label7 = new JLabel("账户可用余额：");
			label7.setFont(new Font("微软雅黑",Font.BOLD,25));
			label7.setHorizontalAlignment(SwingConstants.RIGHT);
			label7.setBounds(230, 280, 210, 40);
			this.showJpanel.add(label7);
			
			String cardAmt =transferBean.getCardAmt();
			if(!"".equals(cardAmt) && null!=cardAmt ){
				NumberFormat fmt= NumberFormat.getCurrencyInstance();
				cardAmt=fmt.format(Double.valueOf(cardAmt));

			}else{
				cardAmt="";
			}
			//账户可用余额信息
			JLabel label8 = new JLabel(cardAmt+" 元");
			label8.setFont(new Font("微软雅黑",Font.PLAIN,25));
			label8.setHorizontalTextPosition(SwingConstants.LEFT);
			label8.setBounds(440,280, 569, 40);
			this.showJpanel.add(label8);
			
			//结存额
			JLabel label19 = new JLabel("结      存     额：");
			label19.setFont(new Font("微软雅黑",Font.BOLD,25));
			label19.setHorizontalAlignment(SwingConstants.RIGHT);
			label19.setBounds(230, 340, 210, 40);
			this.showJpanel.add(label19);
			
			String balance = transferBean.getBalance();
			if(!"".equals(balance) && null!=balance ){
				NumberFormat fmt= NumberFormat.getCurrencyInstance();
				balance=fmt.format(Double.valueOf(balance));

			}else{
				balance="";
			}
			//结存额信息
			JLabel label20 = new JLabel(balance+" 元");
			label20.setFont(new Font("微软雅黑",Font.PLAIN,25));
			label20.setHorizontalTextPosition(SwingConstants.LEFT);
			label20.setBounds(440, 340, 569, 40);
			this.showJpanel.add(label20);
			
		}
		
		//确认
		JLabel okButton = new JLabel();
		okButton.setIcon(new ImageIcon("pic/newPic/confirm.png"));
		okButton.setBounds(1075, 770, 150, 50);
		okButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				//清空倒计时
				clearTimeText();
				//返回时跳转页面
				openPanel(new TransferRemitInfomationPanel(transferBean));
			}
		});
		add(okButton);
		
		//返回
		JLabel backButton = new JLabel();
		backButton.setIcon(new ImageIcon("pic/newPic/exit.png"));
		backButton.setBounds(1258, 770, 150, 50);
		backButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				//清空倒计时
				clearTimeText();
				//返回时跳转页面
				openPanel(new OutputBankCardPanel());
			}
		});		
		add(backButton);
		
		
	}
}
