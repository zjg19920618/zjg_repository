package com.boomhope.Bill.TransService.CashOpen.moneybox;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Public.GlobalPanelFlag;
import com.boomhope.Bill.TransService.CashOpen.Bean.PublicCashOpenBean;
import com.boomhope.Bill.Util.BmpUtil;
import com.boomhope.Bill.Util.Property;
import com.boomhope.Bill.Util.UtilButton;
import com.boomhope.Bill.Util.UtilImage;
import com.boomhope.Bill.Util.UtilImages;

/***
 * 不存在代理人界面
 * 
 * @author gyw
 *
 */
public class MoneyBoxNegationProcuratorPanel extends BaseTransPanelNew {

	static Logger logger = Logger.getLogger(MoneyBoxNegationProcuratorPanel.class);
	private static final long serialVersionUID = 1L;
	private static final String EXIT_ON_CLOSE = null;
	
	JLabel lblNewLabel_8 = null;// 现场拍照核对结果
	JPanel panel_1 = null;
	JPanel panel = null;
	JPanel panel2 = null;
	boolean flag;// 用于判断是打开第1还是第2个JPanel
	
	private boolean on_off=true;//开关控制

	/***
	 * 初始化
	 */
	public MoneyBoxNegationProcuratorPanel(final PublicCashOpenBean transBean) {

		this.cashBean = transBean;

		/* 显示时间倒计时 */
		this.showTimeText(delaySecondMaxTime);
		delayTimer = new Timer(delaySecondMaxTime * 1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				on_off=false;
				/* 倒计时结束退出交易 */
				clearTimeText();
				serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！","","");
			}
		});
		delayTimer.start();
		/* 倒计时打开语音 */
		voiceTimer = new Timer(voiceSecond, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				voiceTimer.stop();
				excuteVoice("voice/examine.wav");

			}
		});
		voiceTimer.start();
		/* 标题信息 */
		JLabel titleLabel = new JLabel("请授权");
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		titleLabel.setForeground((Color.decode("#412174")));
		titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 40));
		titleLabel.setBounds(0, 60, 1009, 60);
		this.showJpanel.add(titleLabel);

		/* 授权图片 */
		ImageIcon image22 = new ImageIcon(transBean.getBillPath_fc());
		image22.setImage(image22.getImage().getScaledInstance(270, 150,
				Image.SCALE_DEFAULT));

		// 图片转换
		File tempFile1 = new File(Property.BILL_ID_SELF_JUST);
		String fileName1 = tempFile1.getName();
		BmpUtil.bmpTojpg(tempFile1.getPath(), "pic/" + fileName1);
		logger.debug("pic/" + fileName1);
		ImageIcon image23 = new ImageIcon("pic/" + fileName1);
		image23.setImage(image23.getImage().getScaledInstance(270, 150,
				Image.SCALE_DEFAULT));
		ImageIcon image24 = new ImageIcon(transBean.getBillPath_rc());
		image24.setImage(image24.getImage().getScaledInstance(270, 150,
				Image.SCALE_DEFAULT));
		ImageIcon image25 = new ImageIcon(Property.ID_CARD_SELF);
		image25.setImage(image25.getImage().getScaledInstance(200, 265,
				Image.SCALE_DEFAULT));

		// 图片转换
		File tempFile = new File(Property.BILL_ID_SELF_AGAINST);
		String fileName = tempFile.getName();
		BmpUtil.bmpTojpg(tempFile.getPath(), "pic/" + fileName);
		logger.debug("pic/" + fileName);
		ImageIcon image26 = new ImageIcon("pic/" + fileName);
		image26.setImage(image26.getImage().getScaledInstance(270, 150,
				Image.SCALE_DEFAULT));

		panel2 = new JPanel();
		panel2.setBounds(32, 125, 1000, 427);
		panel2.setLayout(null);
		panel2.setBackground(null);
		panel2.setOpaque(false);
		// 第一页的panel
		panel = new JPanel();
		panel.setBounds(0, 0, 1000, 427);
		panel.setLayout(null);
		panel.setBackground(null);
		panel.setOpaque(false);

		// 下一页的panel
		panel_1 = new JPanel();

		panel_1.setBounds(0, 0, 1000, 427);
		panel_1.setLayout(null);
		panel_1.setBackground(null);
		panel_1.setOpaque(false);
		ImageIcon image31 = new ImageIcon(Property.CAMERA_PATH);
		image31.setImage(image31.getImage().getScaledInstance(200, 265,
				Image.SCALE_DEFAULT));

		panel2.add(panel);
		this.showJpanel.add(panel2);
		setVisible(true);// 可见
		final UtilImage image1 = new UtilImage("pic/" + fileName1);
		image1.setBounds(163, 50, 270, 150);
		
		panel.add(image1);
		image1.setIcon(image23);
		final UtilImage image4 = new UtilImage("pic/" + fileName);
		image4.setBounds(163, 232, 270, 150);
		
		panel.add(image4);
		image4.setIcon(image26);
		

		// lblNewLabel_7 = new JLabel();
		// lblNewLabel_7.setBounds(569, 397, 260, 15);
		// panel.add(lblNewLabel_7);
		// lblNewLabel_7.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		// lblNewLabel_7.setVerticalAlignment(SwingConstants.TOP);
		// lblNewLabel_7.setHorizontalAlignment(SwingConstants.LEFT);
		// setDesc("核查结果："+this.transBean.getInspect(),lblNewLabel_7);
		// 本人身份信息
		UtilImages image13 = new UtilImages("pic/tghtj.png");
		image13.setSize(124, 32);
		image13.setLocation(236, 0);
		image13.setIcon(new ImageIcon("pic/tghtj.png"));
		panel.add(image13);

		// 图标
		// 联网核查信息
		UtilImages image11 = new UtilImages("pic/fggh.png");
		image11.setBounds(623, 0, 124, 32);
		panel.add(image11);
		image11.setIcon(new ImageIcon("pic/fggh.png"));

		final UtilImage image3 = new UtilImage(Property.ID_CARD_SELF);
		image3.setBounds(569, 87, 200, 265);
		panel.add(image3);
		
		image3.setIcon(image25);

		/* 提示信息 */
		JLabel promptLabel = new JLabel("温馨提示：请逐一点击图片查验");
		promptLabel.setHorizontalAlignment(JLabel.CENTER);
		promptLabel.setFont(new Font("微软雅黑", Font.PLAIN, 25));
		promptLabel.setBounds(0, 563, 1059, 30);
		this.showJpanel.add(promptLabel);

		JButton testButton = new JButton("测试按键");
		testButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				/* 处理开户交易 */
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				scanBill(transBean);
			}

		});
		testButton.setSize(150, 30);
		testButton.setLocation(500, 700);
		Property property = new Property();
		if (property.getProperties().getProperty("push_button").equals("false")) {
			testButton.setVisible(false);
		} else if (property.getProperties().getProperty("push_button")
				.equals("true")) {
			testButton.setVisible(true);
		}
		this.showJpanel.add(testButton);


		//确认
		JLabel lblNewLabel = new JLabel(new ImageIcon("pic/newPic/confirm.png"));
				lblNewLabel.setBounds(890, 770, 150, 50);
		lblNewLabel.addMouseListener(new MouseAdapter() {
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
				scanBill(transBean);
			}

		});
		this.add(lblNewLabel);
		//上一步
		JLabel label = new JLabel(new ImageIcon("pic/preStep.png"));
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击上一步按钮");	
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				scanBill1(transBean);
			}

		});
		label.setBounds(1075, 770, 150, 50);
		this.add(label);
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
	}

	/***
	 * 下一步
	 */
	private void scanBill(PublicCashOpenBean transBean) {

		clearTimeText();
		// 跳授权号
		openPanel(new MoneyBoxAdditionNumberPanel(transBean));
	}

	/***
	 * 上一步
	 */
	private void scanBill1(PublicCashOpenBean transBean) {

		clearTimeText();
		// 其他产品返回上一步需要对应判断

		if ("0010".equals(transBean.getProductCode())) {
			openPanel(new MoneyBoxInPutdepInfoPanel(transBean));
		} else if ("QX".equals(transBean.getProductCode().substring(0, 2))) {
			openPanel(new MoneyBoxEnteringQXXLPanel(transBean));
		} else if ("XF".equals(transBean.getProductCode().substring(0, 2))) {
			openPanel(new MoneyBoxEnteringXFYJYXLPanel(transBean));
		} else if ("YX".equals(transBean.getProductCode().substring(0, 2))) {
			openPanel(new MoneyBoxEnteringYXCPanel(transBean));
		} else if ("RY".equals(transBean.getProductCode().substring(0, 2))) {
			openPanel(new MoneyBoxEnteringRYCPanel(transBean));
		} else if ("RJ".equals(transBean.getProductCode().substring(0, 2))) {
			openPanel(new MoneyBoxEnteringRYCJPanel(transBean));
		} else if ("JJ".equals(transBean.getProductCode().substring(0, 2))) {
			openPanel(new MoneyBoxEnteringAXCPanel(transBean));
		} else if ("LD".equals(transBean.getProductCode().substring(0, 2))) {
			openPanel(new MoneyBoxEnteringLDCPanel(transBean));
		}else{
			on_off=true;
		}

	}

	/**
	 * 设置
	 */
	public void setDesc(String s, JLabel label) {
		StringBuffer sbf = new StringBuffer();

		if (s == null) {
			return;
		}
		// 循环
		int cpCount = s.codePointCount(0, s.length());
		for (int i = 0; i < cpCount; i++) {
			int index = s.offsetByCodePoints(0, i);
			int cp = s.codePointAt(index);
			if (!Character.isSupplementaryCodePoint(cp)) {
				if (i == 0) {
					sbf.append("<html><p>");
				}
				sbf.append((char) cp);
				if ((i + 1) % 16 == 0) {
					sbf.append("</p><p>");
				}
				if (cpCount == (i + 1)) {
					sbf.append("</p></html>");
				}
			} else {
				if (i == 0) {
					sbf.append("<html><p>");
				}
				sbf.append((char) cp);
				if ((i + 1) % 10 == 0) {
					sbf.append("</p><p>");
				}
				if (cpCount == (i + 1)) {
					sbf.append("</p></html>");
				}
			}
		}
		label.setText(sbf.toString());
	}

}
