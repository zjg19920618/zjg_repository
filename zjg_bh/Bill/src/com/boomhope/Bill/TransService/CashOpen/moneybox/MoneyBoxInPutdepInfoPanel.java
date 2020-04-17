package com.boomhope.Bill.TransService.CashOpen.moneybox;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Public.GlobalPanelFlag;
import com.boomhope.Bill.TransService.CashOpen.Bean.PublicCashOpenBean;
import com.boomhope.Bill.TransService.CashOpen.Interface.AccountDepositApi;
import com.boomhope.Bill.TransService.CashOpen.Interface.ProductRateInfo;
import com.boomhope.Bill.Util.MoneyUtils;
import com.boomhope.Bill.Util.NumberZH;
import com.boomhope.Bill.Util.UtilVoice;

/***
 * 录入存单信息
 * 
 * @author gyw
 *
 */
public class MoneyBoxInPutdepInfoPanel extends BaseTransPanelNew{
	

	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(MoneyBoxInPutdepInfoPanel.class);
	JLabel promptLabel;
	UtilVoice utilVoice = null;
	SourceDataLine line = null;
	/* 标记是否点击了 */
	private boolean isbutton_1 = true;// 标记3个月存期的
	private boolean isopenButton = true;// 标记6个月存期的
	private boolean isoneYearButton = true;// 标记一年存期的
	private boolean istwoYearButton = true;// 标记二年存期的
	private boolean isthreeYearButton = true;// 标记三年存期的
	private boolean isfiveYearButton = true;// 标记五年存期的
	private boolean ispaykey = true;// 标记是凭密支付
	private boolean nopaykey = true;// 标记不是频密支付
	private boolean isauto = true;// 标记是自动存
	private boolean noauto = true;// 标记不是自动转存

    JButton onemillButton=null;
    JButton twomillButtoon =null;
    JButton threemillButtoon=null;
    JButton fourmillButtoon =null;
    JButton fivemillButtoon =null;
    JLabel lblNewLabel_4;
    
    private boolean on_off=true;//开关控制
	public MoneyBoxInPutdepInfoPanel(final PublicCashOpenBean  transBean) {
		this.cashBean = transBean;

		// 流程
		JLabel billImage1 = new JLabel();
		billImage1.setIcon(new ImageIcon("pic/business.png"));
		billImage1.setIconTextGap(6);
		billImage1.setBounds(104, 76, 848, 61);
		this.showJpanel.add(billImage1);
		JLabel label = new JLabel("整存整取");
		label.setForeground(Color.decode("#412174"));
		label.setHorizontalAlignment(0);
		label.setFont(new Font("微软雅黑", Font.PLAIN, 40));
		label.setBounds(0, 60,1009, 60);
		this.showJpanel.add(label);
		/* 用户信息 */
		JLabel label1 = new JLabel("用户信息");
		label1.setForeground(Color.decode("#412174"));
		label1.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		label1.setBounds(115, 110, 490, 40);
		this.showJpanel.add(label1);
		// 户名
		JLabel lblNewLabel = new JLabel("户       名 :");
		lblNewLabel.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		lblNewLabel.setBounds(113, 168, 127, 40);
		this.showJpanel.add(lblNewLabel);
		/*// 银行卡金额
		JLabel label_1 = new JLabel("银行卡金额 :");
		label_1.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		label_1.setBounds(506, 178, 174, 50);
		add(label_1);*/
		// 获取用户名
		JLabel label_5 = new JLabel(transBean.getIdCardName());
		label_5.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		label_5.setBounds(250, 168, 134, 40);
		this.showJpanel.add(label_5);
		/*// 获取银行卡金额
		JLabel label_6 = new JLabel(transBean.getBalanceStr());
		label_6.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		label_6.setBounds(651, 178, 348, 50);
		add(label_6);*/
		// 录入存款信息
		JLabel lblNewLabel_1 = new JLabel("录入存款信息");
		lblNewLabel_1.setForeground(Color.decode("#412174"));
		lblNewLabel_1.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		lblNewLabel_1.setBounds(115, 218, 246, 50);
		this.showJpanel.add(lblNewLabel_1);
		// 存期
		JLabel lblNewLabel_2 = new JLabel("存       期 :");
		lblNewLabel_2.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		lblNewLabel_2.setBounds(115, 278, 125, 40);
		this.showJpanel.add(lblNewLabel_2);
		/* 存期时间 */
		final JLabel button = new JLabel("三个月", new ImageIcon(
				"pic/newPic/inputinfo.png"),0);
		button.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		button.setHorizontalTextPosition(SwingConstants.CENTER);
		button.setBounds(250, 280, 90, 40);
		button.setOpaque(false);// 设置控件是否透明，true为不透明，false为透明
		button.setBorder(null);// 设置边框
		this.showJpanel.add(button);
		// 6个月的按钮
		final JLabel button_1 = new JLabel("六个月", new ImageIcon(
				"pic/newPic/inputinfo.png"),0);
		button_1.setHorizontalTextPosition(SwingConstants.CENTER);
		button_1.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		button_1.setBounds(360, 280, 90, 40);
		button_1.setOpaque(false);// 设置控件是否透明，true为不透明，false为透明
		button_1.setBorder(null);// 设置边框
		this.showJpanel.add(button_1);
		// 一年的存期按钮
		final JLabel oneYearButton = new JLabel("一年", new ImageIcon(
				"pic/newPic/inputinfo.png"),0);
		oneYearButton.setBounds(470, 280, 90, 40);
		oneYearButton.setHorizontalTextPosition(SwingConstants.CENTER);
		oneYearButton.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		oneYearButton.setOpaque(false);// 设置控件是否透明，true为不透明，false为透明
		oneYearButton.setBorder(null);// 设置边框
		this.showJpanel.add(oneYearButton);
		// 两年的存期按钮
		final JLabel twoYearButton = new JLabel("二年", new ImageIcon(
				"pic/newPic/inputinfo.png"),0);
		twoYearButton.setHorizontalTextPosition(SwingConstants.CENTER);
		twoYearButton.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		twoYearButton.setOpaque(false);// 设置控件是否透明，true为不透明，false为透明
		twoYearButton.setBorder(null);// 设置边框
		twoYearButton.setBounds(580, 280, 90, 40);
		this.showJpanel.add(twoYearButton);
		// 三年存期的按钮
		final JLabel threeYearButton = new JLabel("三年", new ImageIcon(
				"pic/newPic/inputinfo.png"),0);

		threeYearButton.setBounds(690, 280, 90, 40);
		threeYearButton.setHorizontalTextPosition(SwingConstants.CENTER);
		threeYearButton.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		threeYearButton.setOpaque(false);// 设置控件是否透明，true为不透明，false为透明
		threeYearButton.setBorder(null);// 设置边框
		this.showJpanel.add(threeYearButton);
		// 五年存期的按钮
		final JLabel fiveYearButton = new JLabel("五年", new ImageIcon(
				"pic/newPic/inputinfo.png"),0);

		fiveYearButton.setBounds(800, 280, 90, 40);
		fiveYearButton.setHorizontalTextPosition(SwingConstants.CENTER);
		fiveYearButton.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		fiveYearButton.setOpaque(false);// 设置控件是否透明，true为不透明，false为透明
		fiveYearButton.setBorder(null);// 设置边框
		this.showJpanel.add(fiveYearButton);
		// 监听存期为3个月的按钮
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				isopenButton = true;
				button.setIcon(new ImageIcon("pic/newPic/inputinfo_1.png"));
				button.setForeground(Color.white);
				button_1.setIcon(new ImageIcon("pic/newPic/inputinfo.png"));
				button_1.setForeground(Color.black);
				isbutton_1 = false;
				oneYearButton.setIcon(new ImageIcon("pic/newPic/inputinfo.png"));// 未点击的按钮背景还原
				oneYearButton.setForeground(Color.black);// 未点击的按钮字体还原
				isoneYearButton = false;
				twoYearButton.setIcon(new ImageIcon("pic/newPic/inputinfo.png"));// 未点击的按钮背景还原
				twoYearButton.setForeground(Color.black);// 未点击的按钮字体还原
				istwoYearButton = false;
				threeYearButton.setIcon(new ImageIcon("pic/newPic/inputinfo.png"));// 未点击的按钮背景还原
				threeYearButton.setForeground(Color.black);// 未点击的按钮字体还原
				isthreeYearButton = false;
				fiveYearButton.setIcon(new ImageIcon("pic/newPic/inputinfo.png"));// 未点击的按钮背景还原
				fiveYearButton.setForeground(Color.black);// 未点击的按钮字体还原
				isfiveYearButton = false;
				transBean.setMonthsUpper("3");
			}
		});
		// 监听存期为6个月的按钮
		button_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				isbutton_1 = true;
				button_1.setIcon(new ImageIcon("pic/newPic/inputinfo_1.png"));
				button_1.setForeground(Color.white);
				button.setIcon(new ImageIcon("pic/newPic/inputinfo.png"));
				button.setForeground(Color.black);
				isopenButton = false;
				oneYearButton.setIcon(new ImageIcon("pic/newPic/inputinfo.png"));// 未点击的按钮背景还原
				oneYearButton.setForeground(Color.black);// 未点击的按钮字体还原
				isoneYearButton = false;
				twoYearButton.setIcon(new ImageIcon("pic/newPic/inputinfo.png"));// 未点击的按钮背景还原
				twoYearButton.setForeground(Color.black);// 未点击的按钮字体还原
				istwoYearButton = false;
				threeYearButton.setIcon(new ImageIcon("pic/newPic/inputinfo.png"));// 未点击的按钮背景还原
				threeYearButton.setForeground(Color.black);// 未点击的按钮字体还原
				isthreeYearButton = false;
				fiveYearButton.setIcon(new ImageIcon("pic/newPic/inputinfo.png"));// 未点击的按钮背景还原
				fiveYearButton.setForeground(Color.black);// 未点击的按钮字体还原
				isfiveYearButton = false;
				transBean.setMonthsUpper("6");
			}
		});
		// 监听存期为一年的按钮
		oneYearButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				isoneYearButton = true;
				oneYearButton.setIcon(new ImageIcon("pic/newPic/inputinfo_1.png"));// 点击背景变颜色
				oneYearButton.setForeground(Color.white);// 被点击的按钮的字体显示为白色
				button.setIcon(new ImageIcon("pic/newPic/inputinfo.png"));// 未点击的按钮背景还原
				button.setForeground(Color.black);// 未点击的按钮字体还原
				isopenButton = false;
				button_1.setIcon(new ImageIcon("pic/newPic/inputinfo.png"));// 未点击的按钮背景还原
				button_1.setForeground(Color.black);// 未点击的按钮字体还原
				isbutton_1 = false;
				twoYearButton.setIcon(new ImageIcon("pic/newPic/inputinfo.png"));// 未点击的按钮背景还原
				twoYearButton.setForeground(Color.black);// 未点击的按钮字体还原
				istwoYearButton = false;
				threeYearButton.setIcon(new ImageIcon("pic/newPic/inputinfo.png"));// 未点击的按钮背景还原
				threeYearButton.setForeground(Color.black);// 未点击的按钮字体还原
				isthreeYearButton = false;
				fiveYearButton.setIcon(new ImageIcon("pic/newPic/inputinfo.png"));// 未点击的按钮背景还原
				fiveYearButton.setForeground(Color.black);// 未点击的按钮字体还原
				isfiveYearButton = false;
				transBean.setMonthsUpper("12");
			}
		});
		// 监听存期为二年的按钮
		twoYearButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				istwoYearButton = true;
				twoYearButton.setIcon(new ImageIcon("pic/newPic/inputinfo_1.png"));// 点击背景变颜色
				twoYearButton.setForeground(Color.white);// 被点击的按钮的字体显示为白色
				button.setIcon(new ImageIcon("pic/newPic/inputinfo.png"));// 未点击的按钮背景还原
				button.setForeground(Color.black);// 未点击的按钮字体还原
				isopenButton = false;
				button_1.setIcon(new ImageIcon("pic/newPic/inputinfo.png"));// 未点击的按钮背景还原
				button_1.setForeground(Color.black);// 未点击的按钮字体还原
				isbutton_1 = false;
				oneYearButton.setIcon(new ImageIcon("pic/newPic/inputinfo.png"));// 未点击的按钮背景还原
				oneYearButton.setForeground(Color.black);// 未点击的按钮字体还原
				isoneYearButton = false;
				threeYearButton.setIcon(new ImageIcon("pic/newPic/inputinfo.png"));// 未点击的按钮背景还原
				threeYearButton.setForeground(Color.black);// 未点击的按钮字体还原
				isthreeYearButton = false;
				fiveYearButton.setIcon(new ImageIcon("pic/newPic/inputinfo.png"));// 未点击的按钮背景还原
				fiveYearButton.setForeground(Color.black);// 未点击的按钮字体还原
				isfiveYearButton = false;
				transBean.setMonthsUpper("24");
			}
		});
		// 监听存期为三年的按钮
		threeYearButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				isthreeYearButton = true;
				threeYearButton.setIcon(new ImageIcon("pic/newPic/inputinfo_1.png"));// 点击背景变颜色
				threeYearButton.setForeground(Color.white);// 被点击的按钮的字体显示为白色
				button.setIcon(new ImageIcon("pic/newPic/inputinfo.png"));// 未点击的按钮背景还原
				button.setForeground(Color.black);// 未点击的按钮字体还原
				isopenButton = false;
				button_1.setIcon(new ImageIcon("pic/newPic/inputinfo.png"));// 未点击的按钮背景还原
				button_1.setForeground(Color.black);// 未点击的按钮字体还原
				isbutton_1 = false;
				oneYearButton.setIcon(new ImageIcon("pic/newPic/inputinfo.png"));// 未点击的按钮背景还原
				oneYearButton.setForeground(Color.black);// 未点击的按钮字体还原
				isoneYearButton = false;
				twoYearButton.setIcon(new ImageIcon("pic/newPic/inputinfo.png"));// 未点击的按钮背景还原
				twoYearButton.setForeground(Color.black);// 未点击的按钮字体还原
				istwoYearButton = false;
				fiveYearButton.setIcon(new ImageIcon("pic/newPic/inputinfo.png"));// 未点击的按钮背景还原
				fiveYearButton.setForeground(Color.black);// 未点击的按钮字体还原
				isfiveYearButton = false;
				transBean.setMonthsUpper("36");
			}
		});
		// 监听存期为五年的按钮
		fiveYearButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				isfiveYearButton = true;
				fiveYearButton.setIcon(new ImageIcon("pic/newPic/inputinfo_1.png"));// 点击背景变颜色
				fiveYearButton.setForeground(Color.white);// 被点击的按钮的字体显示为白色
				button.setIcon(new ImageIcon("pic/newPic/inputinfo.png"));// 未点击的按钮背景还原
				button.setForeground(Color.black);// 未点击的按钮字体还原
				isopenButton = false;
				button_1.setIcon(new ImageIcon("pic/newPic/inputinfo.png"));// 未点击的按钮背景还原
				button_1.setForeground(Color.black);// 未点击的按钮字体还原
				isbutton_1 = false;
				oneYearButton.setIcon(new ImageIcon("pic/newPic/inputinfo.png"));// 未点击的按钮背景还原
				oneYearButton.setForeground(Color.black);// 未点击的按钮字体还原
				isoneYearButton = false;
				twoYearButton.setIcon(new ImageIcon("pic/newPic/inputinfo.png"));// 未点击的按钮背景还原
				twoYearButton.setForeground(Color.black);// 未点击的按钮字体还原
				istwoYearButton = false;
				threeYearButton.setIcon(new ImageIcon("pic/newPic/inputinfo.png"));// 未点击的按钮背景还原
				threeYearButton.setForeground(Color.black);// 未点击的按钮字体还原
				isthreeYearButton = false;
				transBean.setMonthsUpper("60");
			}
		});
		// 存入
		JLabel label_2 = new JLabel("存入金额 :");
		label_2.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		label_2.setBounds(115, 355, 117, 40);
		this.showJpanel.add(label_2);
		
		lblNewLabel_4 = new JLabel(String.valueOf(transBean.getMoney())+"元");
		lblNewLabel_4.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		lblNewLabel_4.setBounds(250, 355, 300, 40);
		this.showJpanel.add(lblNewLabel_4);
		
		// 凭密支付
		JLabel label_3 = new JLabel("凭密支付 :");
		label_3.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		label_3.setBounds(115, 430, 117, 40);
		this.showJpanel.add(label_3);

		// 是否凭密支付
		final JLabel button_4 = new JLabel("是", new ImageIcon(
				"pic/newPic/inputinfo.png"),0);
		button_4.setBounds(250, 430, 90, 40);
		button_4.setHorizontalTextPosition(SwingConstants.CENTER);
		button_4.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		button_4.setOpaque(false);// 设置控件是否透明，true为不透明，false为透明
		button_4.setBorder(null);// 设置边框
		this.showJpanel.add(button_4);

		final JLabel button_5 = new JLabel("否", new ImageIcon(
				"pic/newPic/inputinfo.png"),0);
		button_5.setBounds(370, 430, 90, 40);
		button_5.setHorizontalTextPosition(SwingConstants.CENTER);
		button_5.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		button_5.setOpaque(false);// 设置控件是否透明，true为不透明，false为透明
		button_5.setBorder(null);// 设置边框
		this.showJpanel.add(button_5);
		// 监听是否凭密支付的监听按钮
		button_4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				ispaykey =false;
				nopaykey = true;
				button_4.setIcon(new ImageIcon("pic/newPic/inputinfo_1.png"));// 点击背景变颜色
				button_4.setForeground(Color.white);// 被点击的按钮的字体显示为白色
				button_5.setIcon(new ImageIcon("pic/newPic/inputinfo.png"));// 未点击的按钮背景还原
				button_5.setForeground(Color.black);// 未点击的按钮字体还原

			}
		});
		button_5.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				nopaykey = false;
				ispaykey = true;
				button_5.setIcon(new ImageIcon("pic/newPic/inputinfo_1.png"));// 点击背景变颜色
				button_5.setForeground(Color.white);// 被点击的按钮的字体显示为白色
				button_4.setIcon(new ImageIcon("pic/newPic/inputinfo.png"));// 未点击的按钮背景还原
				button_4.setForeground(Color.black);// 未点击的按钮字体还原
			}
		});
		// 自动转存
		JLabel lblNewLabel_3 = new JLabel("自动转存 :");
		lblNewLabel_3.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		lblNewLabel_3.setBounds(115, 505, 117, 40);
		this.showJpanel.add(lblNewLabel_3);
		// 是否自动转存
		final JLabel button_6 = new JLabel("是", new ImageIcon(
				"pic/newPic/inputinfo.png"),0);
		button_6.setBounds(250, 505, 90, 40);
		button_6.setHorizontalTextPosition(SwingConstants.CENTER);
		button_6.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		button_6.setOpaque(false);// 设置控件是否透明，true为不透明，false为透明
		button_6.setBorder(null);// 设置边框
		this.showJpanel.add(button_6);

		final JLabel button_7 = new JLabel("否", new ImageIcon(
				"pic/newPic/inputinfo.png"),0);
		button_7.setBounds(370, 505, 90, 40);
		button_7.setHorizontalTextPosition(SwingConstants.CENTER);
		button_7.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		button_7.setOpaque(false);// 设置控件是否透明，true为不透明，false为透明
		button_7.setBorder(null);// 设置边框
		this.showJpanel.add(button_7);
		// 监听是否自动转存的按钮
		button_6.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				isauto = true;
				noauto = false;
				button_6.setIcon(new ImageIcon("pic/newPic/inputinfo_1.png"));// 点击背景变颜色
				button_6.setForeground(Color.white);// 被点击的按钮的字体显示为白色
				button_7.setIcon(new ImageIcon("pic/newPic/inputinfo.png"));// 未点击的按钮背景还原
				button_7.setForeground(Color.black);// 未点击的按钮字体还原
				transBean.setAutoSaving("1");
			}
		});
		button_7.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				noauto = true;
				isauto = false;
				button_7.setIcon(new ImageIcon("pic/newPic/inputinfo_1.png"));// 点击背景变颜色
				button_7.setForeground(Color.white);// 被点击的按钮的字体显示为白色
				button_6.setIcon(new ImageIcon("pic/newPic/inputinfo.png"));// 未点击的按钮背景还原
				button_6.setForeground(Color.black);// 未点击的按钮字体还原
				transBean.setAutoSaving("0");
			}
		});


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
		JLabel label11 = new JLabel(new ImageIcon("pic/preStep.png"));
		label11.addMouseListener(new MouseAdapter() {
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
		label11.setBounds(1075, 770, 150, 50);
		this.add(label11);
		//退出
		JLabel label_1 = new JLabel(new ImageIcon("pic/newPic/exit.png"));
		label_1.addMouseListener(new MouseAdapter() {
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
		label_1.setBounds(1258, 770, 150, 50);
		this.add(label_1);

		/* 倒计时打开语音 */
		voiceTimer = new Timer(voiceSecond, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stopTimer(voiceTimer);
				excuteVoice("voice/inputdepinfo.wav");

			}
		});
		voiceTimer.start();
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

		/* 提示信息 */
		promptLabel = new JLabel("");
		promptLabel.setHorizontalAlignment(JLabel.CENTER);
		promptLabel.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		promptLabel.setBounds(0, 566, 1009, 30);
		this.showJpanel.add(promptLabel);

		


	}

	

	/**
	 * 返回
	 * */
	public void backTrans(PublicCashOpenBean transBean) {
		clearTimeText();
		transBean.setJijvOrPuhui("");//清空普惠标示
		openPanel(new MoneyBoxPageSelectlPanel(transBean));
		
		
	}


	/*
	 * 判断存期
	 * */
	public boolean isOption(){
		//判断存期是否选中
		if(isopenButton==false||isbutton_1==false||isoneYearButton==false||istwoYearButton==false||isthreeYearButton==false||isfiveYearButton==false){
			return true;
		}else{
			//加入提示音
			inputday();
			//加入提示语
			promptLabel.setText("请选择存期");
			promptLabel.setForeground(Color.red);
			return false;
		}
	}
	

	//判断凭密支付是否选中
	public  boolean ispaypass(){
		if(ispaykey==false||nopaykey==false){
			//如果选择凭密支付确认时要弹出输入支付密码的框
			if(ispaykey==false){
				//确认时要弹出密码框
//				openBean.setPopUpBox(1);
				
				}
			return true;
		}else{
			//语音提示
			optinputdep();
			//加入提示语
			promptLabel.setText("请选择是否凭密支取");
			promptLabel.setForeground(Color.red);
		return false;
		}
	}
	
	//判断自动转存是否选中
	public  boolean isautos(){
		if(isauto==false||noauto==false){
			return true;
		}else{
			//语音提示
			optatuodep();
			//加入提示语
			promptLabel.setText("请选择是否自动转存");
			promptLabel.setForeground(Color.red);
		return false;
		}
	}
	/* 未选择存期时提示音 */
	public void inputday(){

		//加载音频
		utilVoice = new UtilVoice();
		try {
			 line = utilVoice.voice("voice/optionday.wav");
		} catch (UnsupportedAudioFileException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		} catch (LineUnavailableException e) {
			logger.error(e);
		}
	
	}
	/* 未选择存入金额时提示音 */
	public void inputmoney(){
		//加载音频
		utilVoice = new UtilVoice();
		try {
			 line = utilVoice.voice("voice/optinputmoney.wav");
		} catch (UnsupportedAudioFileException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		} catch (LineUnavailableException e) {
			logger.error(e);
		}
	}
	/* 未选择凭密支付时提示音 */
	public void optinputdep(){
		//加载音频
		utilVoice = new UtilVoice();
		try {
			 line = utilVoice.voice("voice/optdeppass.wav");
		} catch (UnsupportedAudioFileException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		} catch (LineUnavailableException e) {
			logger.error(e);
		}
	}
	/* 未选择自动转存时提示音 */
	public void optatuodep(){
		//加载音频
		utilVoice = new UtilVoice();
		try {
			 line = utilVoice.voice("voice/optatuodep.wav");
		} catch (UnsupportedAudioFileException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		} catch (LineUnavailableException e) {
			logger.error(e);
		}
	}
	/**
	 * 确认
	 * */
	public void okTrans(PublicCashOpenBean transBean){
		/*
		 * 当用户全部选择之后进入下一个页面
		 * */
		if(isOption()==true&&ispaypass()==true&&isautos()==true){
			
			//根据选取的存期，获取利率
			List<ProductRateInfo> list = (List<ProductRateInfo>)transBean.getAccountList().get(AccountDepositApi.KEY_PRODUCT_RATES);
			String monthsUpper = transBean.getMonthsUpper();
			int months = Integer.parseInt(monthsUpper);
			transBean.setMonthsUpperStr(MoneyUtils.intUppercase(months));
			if(months<12){
				monthsUpper = "0"+months+"M";
			}else{
				int m = months/12;
				monthsUpper = "0"+m+"Y";
			}
			transBean.setMonthsUpper(monthsUpper);
			for (ProductRateInfo productRateInfo : list) {
				if (productRateInfo.getSavingCount().equals(transBean.getMonthsUpper())) {
					transBean.setRate(productRateInfo.getInterestRate());
					break;
				}
			}
			Calendar now = Calendar.getInstance();
			SimpleDateFormat date = new SimpleDateFormat("yyyyMMdd");
			String svrDate = transBean.getSvrDate();
			transBean.setCreateTime(svrDate);//开始时间
			Date parse = new Date();
			try {
				parse = date.parse(svrDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			now.setTime(parse);
			now.add(Calendar.MONTH, months);
			transBean.setEndTime(date.format(now.getTime()));//结束时间
			//金额格式化转换
//			 String ch = textField_1.getText();
//			 double d1 = 0;
//       	 String ss="";
//       	 String b="";
//       	 String a="";
//       	 if(!ch.equals("")){
//				try {
//				d1 = new DecimalFormat().parse(ch).doubleValue();
//			} catch (ParseException e) {
//				logger.info("去掉金额格式化"+e);
//			} //这里使用的是parse，不是format
//				ss=String.valueOf(d1);
//				 char[] password11=ss.toCharArray();
//				 a = new String(password11);
//			b=	a.substring(0, a.length()-2); 				 				
//      	 logger.debug("转换金额"+b); 
//       	 } 
//       	 int i=Integer.parseInt(b);
       	 
//			int money = Integer.parseInt(textField_1.getText().replace(".00", ""));
//       	int money = Integer.parseInt(b.replace(".00", ""));
			//需要赋值钱柜面对应的金额(目前写死测试)
//			int money = 250;
			transBean.setMoneyUpper(NumberZH.change(Double.parseDouble(String.valueOf(transBean.getMoney()))));
			
//			int money = Integer.parseInt(textField_1.getText().replace(".00", ""));
//			transBean.setMoney(money);
			transBean.setMoneyUpper(NumberZH.change(Double.parseDouble(String.valueOf(transBean.getMoney()))));
			//计算利息
			transBean.setInte(String.format("%.2f", Float.parseFloat(transBean.getRate()) * transBean.getMoney() * 0.01 * months / 12));

				//如果选择凭密支付确认时要弹出输入支付密码的框
				if(ispaykey==false){
					transBean.setLimit("1");
					clearTimeText();
					openPanel(new MoneyBoxWithCloseTopayPanel(transBean));
									
					}else{
						transBean.setLimit("0");
						transBean.setSubPwd("");
						//如果是新建客户或者金额大于等于5万走授权流程
						String customer_identification = transBean.getImportMap().get("customer_identification");
						if(transBean.getMoney()>=50000.00){
							//判断跳转如果是等于yes就是代理人代理 跳转代理人页面
							if("yes".equals(transBean.getImportMap().get("agent_persion"))){
								clearTimeText();
								//跳转代理人授权页面
								openPanel(new MoneyBoxExistProcuratorPanel(transBean));
								
							}else if ("no".equals(transBean.getImportMap().get("agent_persion"))){
								clearTimeText();
								//跳转本人授权页面
								openPanel(new MoneyBoxNegationProcuratorPanel(transBean));
								
							}							
						}
						//判断 金额如果小于5万走简单流程 
						if(transBean.getMoney()<50000.00){
							clearTimeText();
							//确认存单信息页面
							openPanel(new MoneyBoxOkInputDepinfoPanel(transBean));
							
						}

					}
			
		}else{
			on_off=true;
		}
	}
}
