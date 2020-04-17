package com.boomhope.Bill.TransService.CashOpen.moneybox;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Public.GlobalPanelFlag;
import com.boomhope.Bill.TransService.CashOpen.Bean.PublicCashOpenBean;
import com.boomhope.Bill.TransService.CashOpen.Interface.AccountDepositApi;
import com.boomhope.Bill.TransService.CashOpen.Interface.ProductRateInfo1;
import com.boomhope.Bill.Util.UtilImages1;
import com.boomhope.Bill.monitor.sendInterface.MakeMonitorInfo;
import com.boomhope.tms.message.account.in.BCK02867ResBean;

/***
 * 立得存图片页面
 * @author gyw
 *
 */
public class MoneyBoxCatalogLDCTPPanel extends BaseTransPanelNew{

	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(MoneyBoxCatalogLDCTPPanel.class);
	
	private boolean on_off=true;//用于控制页面跳转的开关
	
	public MoneyBoxCatalogLDCTPPanel(final PublicCashOpenBean transBean){
		this.cashBean = transBean;
		
		/* 显示时间倒计时 */
		showTimeText(delaySecondLongTime);
		delayTimer = new Timer(delaySecondLongTime * 1000, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {  
            	on_off=false;
            	/* 倒计时结束退出交易 */ 
            	clearTimeText();
				serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！","","");
            }      
        });            
		delayTimer.start(); 
		/* 倒计时打开语音 */
		//立得存1
		if(transBean.getMoney()<500000){//协议1-30万
			if("1".equals(transBean.getJijvOrPuhui())){//普惠版
				// 立得存1
				JLabel lblNewLabel_1 = new JLabel("产品名称 : 立得存-自享(普惠版)");
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
				JLabel lblNewLabel_4 = new JLabel("产品额度 : 1-30万");
				lblNewLabel_4.setFont(new Font("微软雅黑", Font.PLAIN, 18));
				lblNewLabel_4.setForeground(Color.decode("#ffffff"));
				lblNewLabel_4.setHorizontalAlignment(SwingConstants.LEFT);
				lblNewLabel_4.setBounds(
						148, 347, 340, 20);
				this.showJpanel.add(lblNewLabel_4);
				JLabel lblNewLabel_5 = new JLabel("存款收益帐号绑定 : 制定本人名下");
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
						if(!on_off){
							logger.info("开关当前状态"+on_off);
							return;
						}
						logger.info("开关当前状态"+on_off);
						on_off=false;
						logger.info("点击立得存1按钮");
						// /立得存1
						nextPage1(transBean);
					}
				});
				this.showJpanel.add(image);

				// 立得存2
				JLabel lblNewLabel_8 = new JLabel("产品名称 : 立得存-他享(普惠版)");
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
						if(!on_off){
							logger.info("开关当前状态"+on_off);
							return;
						}
						logger.info("开关当前状态"+on_off);
						on_off=false;
						logger.info("点击立得存2按钮");
						// 立得存2
						nextPage2( transBean);
					}
				});
				this.showJpanel.add(image1);
				
			}else{
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
				JLabel lblNewLabel_4 = new JLabel("产品额度 : 1-30万");
				lblNewLabel_4.setFont(new Font("微软雅黑", Font.PLAIN, 18));
				lblNewLabel_4.setForeground(Color.decode("#ffffff"));
				lblNewLabel_4.setHorizontalAlignment(SwingConstants.LEFT);
				lblNewLabel_4.setBounds(
						148, 347, 340, 20);
				this.showJpanel.add(lblNewLabel_4);
				JLabel lblNewLabel_5 = new JLabel("存款收益帐号绑定 : 制定本人名下");
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
						if(!on_off){
							logger.info("开关当前状态"+on_off);
							return;
						}
						logger.info("开关当前状态"+on_off);
						on_off=false;
						logger.info("点击立得存1按钮");
						// /立得存1
						nextPage1(transBean);
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
						if(!on_off){
							logger.info("开关当前状态"+on_off);
							return;
						}
						logger.info("开关当前状态"+on_off);
						on_off=false;
						logger.info("点击立得存2按钮");
						// 立得存2
						nextPage2( transBean);
					}
				});
				this.showJpanel.add(image1);
			}
			
		}else{//私行50-1000万
			if("1".equals(transBean.getJijvOrPuhui())){//普惠版
				
				// 立得存1
				JLabel lblNewLabel_1 = new JLabel("产品名称 : 立得存-自享(普惠版)");
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
				JLabel lblNewLabel_4 = new JLabel("产品额度 : 50-1000万");
				lblNewLabel_4.setFont(new Font("微软雅黑", Font.PLAIN, 18));
				lblNewLabel_4.setForeground(Color.decode("#ffffff"));
				lblNewLabel_4.setHorizontalAlignment(SwingConstants.LEFT);
				lblNewLabel_4.setBounds(
						148, 347, 340, 20);
				this.showJpanel.add(lblNewLabel_4);
				JLabel lblNewLabel_5 = new JLabel("存款收益帐号绑定 : 制定本人名下");
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
						if(!on_off){
							logger.info("开关当前状态"+on_off);
							return;
						}
						logger.info("开关当前状态"+on_off);
						on_off=false;
						logger.info("点击立得存1按钮");
						// /立得存1
						nextPage1(transBean);
					}
				});
				this.showJpanel.add(image);

				// 立得存2
				JLabel lblNewLabel_8 = new JLabel("产品名称 : 立得存-他享(普惠版)");
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
				JLabel lblNewLabel_11 = new JLabel("产品额度 : 50-1000万");
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
						if(!on_off){
							logger.info("开关当前状态"+on_off);
							return;
						}
						logger.info("开关当前状态"+on_off);
						on_off=false;
						logger.info("点击立得存2按钮");
						// 立得存2
						nextPage2( transBean);
					}
				});
				this.showJpanel.add(image1);
			}else{//机具版
				
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
				JLabel lblNewLabel_4 = new JLabel("产品额度 : 50-1000万");
				lblNewLabel_4.setFont(new Font("微软雅黑", Font.PLAIN, 18));
				lblNewLabel_4.setForeground(Color.decode("#ffffff"));
				lblNewLabel_4.setHorizontalAlignment(SwingConstants.LEFT);
				lblNewLabel_4.setBounds(
						148, 347, 340, 20);
				this.showJpanel.add(lblNewLabel_4);
				JLabel lblNewLabel_5 = new JLabel("存款收益帐号绑定 : 制定本人名下");
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
						if(!on_off){
							logger.info("开关当前状态"+on_off);
							return;
						}
						logger.info("开关当前状态"+on_off);
						on_off=false;
						logger.info("点击立得存1按钮");
						// /立得存1
						nextPage1(transBean);
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
				JLabel lblNewLabel_11 = new JLabel("产品额度 : 50-1000万");
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
						if(!on_off){
							logger.info("开关当前状态"+on_off);
							return;
						}
						logger.info("开关当前状态"+on_off);
						on_off=false;
						logger.info("点击立得存2按钮");
						// 立得存2
						nextPage2( transBean);
					}
				});
				this.showJpanel.add(image1);
			}
		}
		
		

		//上一步
		JLabel label = new JLabel(new ImageIcon("pic/preStep.png"));
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				logger.info("点击上一步按钮");				
				backTrans(transBean);
			}

		});
		label.setBounds(1075, 770, 150, 50);
		this.add(label);
				//退出
		JLabel label_1 = new JLabel(new ImageIcon("pic/newPic/exit.png"));
		label_1.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						if(!on_off){
							logger.info("开关当前状态"+on_off);
							return;
						}
						logger.info("开关当前状态"+on_off);
						on_off=false;
						logger.info("点击退出按钮");				
						returnHome();
					}

				});
		label_1.setBounds(1258, 770, 150, 50);
		this.add(label_1);
	}
	
	
	
	/**
	 * 返回
	 * */
	public void backTrans(PublicCashOpenBean transBean) {
			clearTimeText();
			openPanel(new MoneyBoxAgreementPanel(transBean));
				
		}
	
	
	/**
	 * 立得存1
	 * */
	public void nextPage1(PublicCashOpenBean transBean) {
		//调用查询子产品信息
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("CUST_NO", transBean.getCustNo());
		map.put("PRODUCT_CODE","LZ");
		map.put("QRY_TYPE", "1");
		if(transBean.getMoney()<500000){
			map.put("DEP_AMT", "0");// 最低起存
			map.put("MAX_AMT", "500000");// 最高起存
			
		}else{
			map.put("DEP_AMT", "500000");// 最低起存
			map.put("MAX_AMT", "");// 最高起存
			map.put("CHL_ID", "3");// 私行版:渠道模块标识为3
		}
		if("1".equals(transBean.getJijvOrPuhui())){
			map.put("CHL_ID", "5");// 普惠版:渠道模块标识为5
		}
		
		transBean.getReqMCM001().setReqBefor("02867");//接口调用前流水信息记录
		
		AccountDepositApi accountDepositApi = new AccountDepositApi();
		BCK02867ResBean searchProduct=accountDepositApi.searchProduct(map);
		List<ProductRateInfo1> list = (List<ProductRateInfo1>) map.get(AccountDepositApi.KEY_PRODUCT_RATES);
		if(null == searchProduct || !"000000".equals(searchProduct.getHeadBean().getResCode()) || list.size() == 0 ||"44444".equals(searchProduct.getHeadBean().getResCode())){
			//接口调用后流水信息记录
			if(null == searchProduct){
				transBean.getReqMCM001().setIntereturnmsg("查询子产品失败：调用02867接口异常");
			}else{
				transBean.getReqMCM001().setReqAfter(searchProduct.getHeadBean().getResCode(), searchProduct.getHeadBean().getResMsg());
			}
			MakeMonitorInfo.makeInfos(transBean.getReqMCM001());//接口调用失败，上送流水信息
			serverStop("请联系大堂经理","利率查询失败:子产品查询，"+searchProduct.getHeadBean().getResMsg(), "");
			return;
		}
		
		//接口调用成功后记录流水信息
		transBean.getReqMCM001().setReqAfter(searchProduct.getHeadBean().getResCode(), searchProduct.getHeadBean().getResMsg());
		
		transBean.getAccountList().put(AccountDepositApi.KEY_PRODUCT_RATES, list);
		transBean.setLdcType("0");
		transBean.setKhldn(0);
		clearTimeText();
		openPanel(new MoneyBoxCatalogLDCXLZXTXPanel(transBean));
		
	}
	
	/**
	 * 立得存2
	 * */
	public void nextPage2(PublicCashOpenBean transBean) {
		
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("CUST_NO", transBean.getCustNo());
			map.put("PRODUCT_CODE","LT");
			map.put("QRY_TYPE", "1");
			if(transBean.getMoney()<500000){
				map.put("DEP_AMT", "0");// 最低起存
				map.put("MAX_AMT", "500000");// 最高起存
				
			}else{
				map.put("DEP_AMT", "500000");// 最低起存
				map.put("MAX_AMT", "");// 最高起存
				map.put("CHL_ID", "3");// 私行版:渠道模块标识为3
			}
			if("1".equals(transBean.getJijvOrPuhui())){
				map.put("CHL_ID", "5");// 普惠版:渠道模块标识为5
			}
			
			transBean.getReqMCM001().setReqBefor("02867");//接口调用前流水信息记录
			
			AccountDepositApi accountDepositApi = new AccountDepositApi();
			BCK02867ResBean searchProduct=accountDepositApi.searchProduct(map);
			List<ProductRateInfo1> list = (List<ProductRateInfo1>) map.get(AccountDepositApi.KEY_PRODUCT_RATES);
			if(null == searchProduct || !"000000".equals(searchProduct.getHeadBean().getResCode()) || "44444".equals(searchProduct.getHeadBean().getResCode())){
				//接口调用后流水信息记录
				if(null == searchProduct){
					transBean.getReqMCM001().setIntereturnmsg("查询子产品失败：调用02867接口异常");
				}else{
					transBean.getReqMCM001().setReqAfter(searchProduct.getHeadBean().getResCode(), searchProduct.getHeadBean().getResMsg());
				}
				MakeMonitorInfo.makeInfos(transBean.getReqMCM001());//接口调用失败，上送流水信息
				serverStop("请联系大堂经理","利率查询失败:子产品查询，"+searchProduct.getHeadBean().getResMsg(), "");
				return;
			}
			
			//接口调用成功后记录流水信息
			transBean.getReqMCM001().setReqAfter(searchProduct.getHeadBean().getResCode(), searchProduct.getHeadBean().getResMsg());
			
			transBean.getAccountList().put(AccountDepositApi.KEY_PRODUCT_RATES, list);
			transBean.setLdcType("1");
			transBean.setKhldn(0);
		clearTimeText();
		openPanel(new MoneyBoxCatalogLDCXLZXTXPanel(transBean));
			
	}
	

}
