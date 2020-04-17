package com.boomhope.Bill.TransService.CashOpen.moneybox;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.IOException;
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
 * 约享存图片页面
 * 
 * @author gyw
 *
 */
public class MoneyBoxCatalogYXCTPPanel extends BaseTransPanelNew{

	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(MoneyBoxCatalogYXCTPPanel.class);
	
	private boolean on_off=true;//开关控制
	
	public MoneyBoxCatalogYXCTPPanel(final PublicCashOpenBean transBean) {
		this.cashBean = transBean;

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
		delayTimer.start();
		if(transBean.getMoney()<500000){//协议1-30万
			if("1".equals(transBean.getJijvOrPuhui())){//普惠版
				JLabel lblNewLabel_1 = new JLabel("产品名称 : 约享存A(普惠版)");
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
				JLabel lblNewLabel_4 = new JLabel("产品额度 : 1-30万");
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
						if(!on_off){
							logger.info("开关当前状态"+on_off);
							return;
						}
						logger.info("开关当前状态"+on_off);
						on_off=false;
						logger.info("点击约享存1按钮");
						// 约享存1
						nextPage1(transBean);
					}
				});
				this.showJpanel.add(image);
				// 约享存2
				JLabel lblNewLabel_3 = new JLabel("产品名称 : 约享存B(普惠版)");
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
						if(!on_off){
							logger.info("开关当前状态"+on_off);
							return;
						}
						logger.info("开关当前状态"+on_off);
						on_off=false;
						logger.info("点击约享存2按钮");
						// 约享存2
						nextPage2(transBean);
					}
				});
				this.showJpanel.add(image2);
				// 约享存3
				JLabel lblNewLabel_7 = new JLabel("产品名称 : 约享存C(普惠版)");
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
						if(!on_off){
							logger.info("开关当前状态"+on_off);
							return;
						}
						logger.info("开关当前状态"+on_off);
						on_off=false;
						logger.info("点击约享存3按钮");
						// 约享存3
						nextPage3(transBean);
					}
				});
				this.showJpanel.add(image3);
				
			}else{
				
				JLabel lblNewLabel_1 = new JLabel("产品名称 : 约享存A(机具版)");
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
				JLabel lblNewLabel_4 = new JLabel("产品额度 : 1-30万");
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
						if(!on_off){
							logger.info("开关当前状态"+on_off);
							return;
						}
						logger.info("开关当前状态"+on_off);
						on_off=false;
						logger.info("点击约享存1按钮");
						// 约享存1
						nextPage1(transBean);
					}
				});
				this.showJpanel.add(image);
				// 约享存2
				JLabel lblNewLabel_3 = new JLabel("产品名称 : 约享存B(机具版)");
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
						if(!on_off){
							logger.info("开关当前状态"+on_off);
							return;
						}
						logger.info("开关当前状态"+on_off);
						on_off=false;
						logger.info("点击约享存2按钮");
						// 约享存2
						nextPage2(transBean);
					}
				});
				this.showJpanel.add(image2);
				// 约享存3
				JLabel lblNewLabel_7 = new JLabel("产品名称 : 约享存C(机具版)");
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
						if(!on_off){
							logger.info("开关当前状态"+on_off);
							return;
						}
						logger.info("开关当前状态"+on_off);
						on_off=false;
						logger.info("点击约享存3按钮");
						// 约享存3
						nextPage3(transBean);
					}
				});
				this.showJpanel.add(image3);
			}
			
		}else{
			if("1".equals(transBean.getJijvOrPuhui())){//普惠版
				JLabel lblNewLabel_1 = new JLabel("产品名称 : 约享存A(普惠版)");
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
				JLabel lblNewLabel_4 = new JLabel("产品额度 : 50-1000万");
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
						if(!on_off){
							logger.info("开关当前状态"+on_off);
							return;
						}
						logger.info("开关当前状态"+on_off);
						on_off=false;
						logger.info("点击约享存1按钮");
						// 约享存1
						nextPage1(transBean);
					}
				});
				this.showJpanel.add(image);
				// 约享存2
				JLabel lblNewLabel_3 = new JLabel("产品名称 : 约享存B(普惠版)");
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
				JLabel lblNewLabel_6 = new JLabel("产品额度 : 50-1000万");
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
						if(!on_off){
							logger.info("开关当前状态"+on_off);
							return;
						}
						logger.info("开关当前状态"+on_off);
						on_off=false;
						logger.info("点击约享存2按钮");
						// 约享存2
						nextPage2(transBean);
					}
				});
				this.showJpanel.add(image2);
				// 约享存3
				JLabel lblNewLabel_7 = new JLabel("产品名称 : 约享存C(普惠版)");
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
				JLabel lblNewLabel_9 = new JLabel("产品额度 : 50-1000万");
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
						if(!on_off){
							logger.info("开关当前状态"+on_off);
							return;
						}
						logger.info("开关当前状态"+on_off);
						on_off=false;
						logger.info("点击约享存3按钮");
						// 约享存3
						nextPage3(transBean);
					}
				});
				this.showJpanel.add(image3);
				
			}else{
				JLabel lblNewLabel_1 = new JLabel("产品名称 : 约享存A(机具版)");
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
				JLabel lblNewLabel_4 = new JLabel("产品额度 : 50-1000万");
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
						if(!on_off){
							logger.info("开关当前状态"+on_off);
							return;
						}
						logger.info("开关当前状态"+on_off);
						on_off=false;
						logger.info("点击约享存1按钮");
						// 约享存1
						nextPage1(transBean);
					}
				});
				this.showJpanel.add(image);
				// 约享存2
				JLabel lblNewLabel_3 = new JLabel("产品名称 : 约享存B(机具版)");
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
				JLabel lblNewLabel_6 = new JLabel("产品额度 : 50-1000万");
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
						if(!on_off){
							logger.info("开关当前状态"+on_off);
							return;
						}
						logger.info("开关当前状态"+on_off);
						on_off=false;
						logger.info("点击约享存2按钮");
						// 约享存2
						nextPage2(transBean);
					}
				});
				this.showJpanel.add(image2);
				// 约享存3
				JLabel lblNewLabel_7 = new JLabel("产品名称 : 约享存C(机具版)");
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
				JLabel lblNewLabel_9 = new JLabel("产品额度 : 50-1000万");
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
						if(!on_off){
							logger.info("开关当前状态"+on_off);
							return;
						}
						logger.info("开关当前状态"+on_off);
						on_off=false;
						logger.info("点击约享存3按钮");
						// 约享存3
						nextPage3(transBean);
					}
				});
				this.showJpanel.add(image3);
				
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
	 * 约享存1
	 * */
	public void nextPage1(PublicCashOpenBean transBean) {
		//调接口查询所有约享存A
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("CUST_NO", transBean.getCustNo());
		map.put("PRODUCT_CODE","YA");
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
		if(null == searchProduct || !"000000".equals(searchProduct.getHeadBean().getResCode()) || list.size() == 0){
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
		transBean.setKhyxn(0);
		clearTimeText();
		openPanel(new MoneyBoxCatalogYXCFYPanel(transBean));
			
		}
		
	/**
	 *约享存2
	 * */
	public void nextPage2(PublicCashOpenBean transBean) {
		//调接口查询所有约享存B
				
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("CUST_NO", transBean.getCustNo());
		map.put("PRODUCT_CODE","YB");
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
		if(null == searchProduct || !"000000".equals(searchProduct.getHeadBean().getResCode()) || list.size() == 0){
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
		transBean.setKhyxn(0);
		clearTimeText();
		openPanel(new MoneyBoxCatalogYXCFYPanel(transBean));
					
	}
	/**
	 * 约享存3
	 * */
	public void nextPage3(PublicCashOpenBean transBean) {
		//调接口查询所有约享存C
				
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("CUST_NO", transBean.getCustNo());
		map.put("PRODUCT_CODE","YC");
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
		if(null == searchProduct || !"000000".equals(searchProduct.getHeadBean().getResCode()) || list.size() == 0){
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
		transBean.setKhyxn(0);
		clearTimeText();
		openPanel(new MoneyBoxCatalogYXCFYPanel(transBean));
					
	}
	
}
