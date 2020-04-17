package com.boomhope.Bill.TransService.AccOpen.View.ServicePage.Account.ProInformationInputPanel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.PublicControl.OutputBankCardPanel;
import com.boomhope.Bill.TransService.AccOpen.Bean.AccPublicBean;
import com.boomhope.Bill.TransService.AccOpen.View.ServicePage.Account.AccSuccessDepPanel;
import com.boomhope.Bill.TransService.AccOpen.controller.AccountTradeCodeAction;

/**
 * 红包页面
 * 
 * @author hk
 *
 */
public class AccRedPacketPanel extends BaseTransPanelNew {

	static Logger logger = Logger.getLogger(AccRedPacketPanel.class);
	private static final long serialVersionUID = 1L;
	JLabel lblNewLabel = null;
	ImageIcon image = null;
	ImageIcon image1 = null;
	JLabel jlb = null;
	JLabel jlb1 = null;
	JButton okButton = null;
	JLabel lblNewLabel_1 = null;
	JLabel lblNewLabel_2 = null;
	JLabel lblNewLabel_3 = null;
	Component com = null;
	AccPublicBean accBean;
	private boolean on_off=true;

	public AccRedPacketPanel(final Map<String, Object> params) {
		logger.info("进入红包页面");
		
		this.com = this;
		this.accBean = AccountTradeCodeAction.transBean;

		/* 显示时间倒计时 */
		this.showTimeText(delaySecondMinTime);
		delayTimer = new Timer(delaySecondMinTime * 1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logger.info("红包页面倒计时结束 ");
				/* 倒计时结束退出交易 */
				clearTimeText();// 清空倒计时
				nextPage(com, params);
			}
		});
		delayTimer.start();

		/* 标题信息 */
		jlb = new JLabel();
		jlb.setLocation(258, 0);
		image = new ImageIcon("pic/01.gif");
		image.setImage(image.getImage().getScaledInstance(496, 606,
				Image.SCALE_DEFAULT));
		jlb.setIcon(image);
		jlb.setSize(496, 606);
		jlb.setVisible(true);
		jlb.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击图片变化按钮");
				/* 点击后变换图片 */
				backTrans();
			}
		});
		this.showJpanel.add(jlb);

		lblNewLabel_1 = new JLabel("￥");
		lblNewLabel_1.setForeground(new Color(250, 204, 0));
		lblNewLabel_1.setBackground(Color.BLACK);
		lblNewLabel_1.setFont(new Font("微软雅黑", Font.BOLD, 28));
		lblNewLabel_1.setBounds(405, 405, 33, 30);
		lblNewLabel_1.setVisible(false);
		this.showJpanel.add(lblNewLabel_1);

		// lblNewLabel_2 = new JLabel("1000.00");
		lblNewLabel_2 = new JLabel(accBean.getIntMoney());
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setForeground(new Color(255, 204, 0));
		lblNewLabel_2.setBackground(Color.BLACK);
		lblNewLabel_2.setFont(new Font("微软雅黑", Font.BOLD, 28));
		lblNewLabel_2.setBounds(422, 400, 178, 40);
		lblNewLabel_2.setVisible(false);
		this.showJpanel.add(lblNewLabel_2);
		//
		lblNewLabel_3 = new JLabel("元");
		lblNewLabel_3.setForeground(new Color(255, 204, 0));
		lblNewLabel_3.setBackground(Color.BLACK);
		lblNewLabel_3.setFont(new Font("微软雅黑", Font.BOLD, 28));
		lblNewLabel_3.setBounds(570, 405, 49, 30);
		lblNewLabel_3.setVisible(false);
		this.showJpanel.add(lblNewLabel_3);

		/* 获得红包图片 */
		jlb1 = new JLabel();
		jlb1.setLocation(258, 0);
		image1 = new ImageIcon("pic/02.png");
		image1.setImage(image1.getImage().getScaledInstance(496, 606,
				Image.SCALE_DEFAULT));
		jlb1.setIcon(image1);
		jlb1.setSize(496, 606);
		jlb1.setVisible(false);
		jlb1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击获取红包按钮");
				/* 点击后变换图片 */
				backTrans();
			}
		});
		
		/* 确认 */
		okButton = new JButton(new ImageIcon("pic/04.png"));

		okButton.setHorizontalTextPosition(SwingConstants.CENTER);
		okButton.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		okButton.setOpaque(false);// 设置控件是否透明，true为不透明，false为透明
		okButton.setContentAreaFilled(false);// 设置图片填满按钮所在的区域
		okButton.setFocusPainted(true);// 设置这个按钮是不是获得焦点
		okButton.setBorderPainted(false);// 设置是否绘制边框
		okButton.setBorder(null);// 设置边框
		okButton.setVisible(false);
		okButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				/* 处理下一页 */
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						logger.info("点击红包的确认按钮");
						if(!on_off){
							logger.info("开关当前状态"+on_off);
							return;
						}
						logger.info("开关当前状态"+on_off);
						on_off=false;
						// 跳转处理成功页面
						nextPage(com, params);
					}

				});
			}

		});
		okButton.setSize(120, 40);
		okButton.setLocation(455, 450);
		this.showJpanel.add(okButton);
		this.showJpanel.add(jlb1);

		JLabel label = new JLabel(
				"<HTML><p>温馨提示:</p><p>如您提前支取,</p><p>幸运豆全额退回!</P><HTML>");
		label.setForeground(Color.RED);
		label.setHorizontalAlignment(SwingConstants.LEFT);
		label.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		label.setBounds(776, 336, 203, 148);
		this.showJpanel.add(label);
		openRed();
		
	}

	private void openRed() {
		logger.info("进入打开红包方法");
		Thread thread = new Thread("openRed") {
			public void run() {
				try {
					Thread.sleep(10000);
					backTrans();
				} catch (InterruptedException e) {
					logger.error(e);
				}

			}
		};
		thread.start();
	}

	/**
	 * 点击后图片变化
	 * */
	public void backTrans() {
		logger.info("进入图片变化方法");
		jlb.setVisible(false);
		jlb1.setVisible(true);
		okButton.setVisible(true);
		lblNewLabel_1.setVisible(true);
		lblNewLabel_2.setVisible(true);
		lblNewLabel_3.setVisible(true);
		return;
	}

	/**
	 * 跳转成功页/
	 */
	private void nextPage(final Component com, final Map<String, Object> params) {
		logger.info("进入页面跳转的方法");
		clearTimeText();// 清空倒计时
		// 跳转是否打印存单
		if ("JX".equals((String) params.get("PRODUCT_CODE"))) {
			logger.info("跳转至积享存存入成功页面");
			// 积享不打印存单，跳转积享存存入成功页面
			openPanel(new AccJXCSuccessPanel(AccountTradeCodeAction.transBean,params));
		} else {
			logger.info("进入存单打印成功页面");
			// 进入存单打印成功页面
			openPanel(new AccSuccessDepPanel(AccountTradeCodeAction.transBean,params));
		}
	}
}
