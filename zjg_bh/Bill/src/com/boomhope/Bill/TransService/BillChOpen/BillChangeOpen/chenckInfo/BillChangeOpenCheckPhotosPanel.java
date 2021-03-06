package com.boomhope.Bill.TransService.BillChOpen.BillChangeOpen.chenckInfo;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.TransService.BillChOpen.BillChangeOpen.BJ.BillChangeOpenAuthorNoPanel;
import com.boomhope.Bill.TransService.BillChOpen.BillChangeOpen.HaveAcc.BillChangeOpenDepositAffirmSignaPanel;
import com.boomhope.Bill.Util.BmpUtil;
import com.boomhope.Bill.Util.Property;
import com.boomhope.Bill.Util.UtilImage;
import com.boomhope.Bill.Util.UtilImages;

/***
 * 检查照片
 * 
 * @author hao
 * 
 */
public class BillChangeOpenCheckPhotosPanel extends BaseTransPanelNew {

	static Logger logger = Logger.getLogger(BillChangeOpenCheckPhotosPanel.class);
	private static final long serialVersionUID = 1L;
	JLabel lblNewLabel = null;// 错误提示
	JLabel lblNewLabel_1 = null;// 错误提示
	JLabel lblNewLabel_2 = null;// 错误提示
	JLabel lblNewLabel_3 = null;// 错误提示
	JLabel lblNewLabel_4 = null;// 错误提示
	JLabel lblNewLabel_5 = null;// 错误提示
	JLabel lblNewLabel_6 = null;// 错误提示
	JLabel lblNewLabel_6_c = null;// 错误提示
	JLabel lblNewLabel_8 = null;// 人脸比对核对结果
	JLabel lblNewLabel_5_c=null;//联网核查结果
	JPanel panel = null;
	JPanel panel2 = null;
	boolean flag;// 用于判断是打开第1还是第2个JPanel
	private boolean on_off=true;//用于控制页面跳转的开关
	
	/***
	 * 初始化
	 */
	public BillChangeOpenCheckPhotosPanel() {
		logger.info("进入无代理人核查照片页面");
		
		//将当前页面传入流程控制进行操作
		final Component comp=this;
		/* 显示时间倒计时 */
		if(delayTimer!=null){
			clearTimeText();
		}
		this.showTimeText(delaySecondLongTime);
		delayTimer = new Timer(delaySecondLongTime * 1000,new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logger.info("无代理人核查照片页面倒计时结束 ");
				/* 倒计时结束退出交易 */ 
				clearTimeText();//清空倒计时
            	serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！","","");
			}
		});
		delayTimer.start();
		/* 倒计时打开语音 */
		voiceTimer = new Timer(voiceSecond, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stopTimer(voiceTimer);//关语音
            	closeVoice();//关语音流
				excuteVoice("voice/checkIdMsg.wav");

			}
		});
		voiceTimer.start();
		/* 标题信息 */
		JLabel titleLabel = new JLabel("请核查本人身份照片");
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 36));
		titleLabel.setBounds(0, 60, 1009, 40);
		this.showJpanel.add(titleLabel);

		// 图片转换
		/*本人身份证照片正面*/
		File tempFile1 = new File(Property.BILL_ID_SELF_JUST);
		String fileName1 = tempFile1.getName();
		BmpUtil.bmpTojpg(tempFile1.getPath(), "pic/" + fileName1);
		ImageIcon image23 = new ImageIcon("pic/" + fileName1);
		image23.setImage(image23.getImage().getScaledInstance(270, 150,
				Image.SCALE_DEFAULT));
		/*本人照片*/
		ImageIcon image25 = new ImageIcon(Property.ID_CARD_SELF);
		image25.setImage(image25.getImage().getScaledInstance(200, 265,
				Image.SCALE_DEFAULT));
		/*本人身份证照片反面*/
		// 图片转换
		File tempFile = new File(Property.BILL_ID_SELF_AGAINST);
		String fileName = tempFile.getName();
		BmpUtil.bmpTojpg(tempFile.getPath(), "pic/" + fileName);
		ImageIcon image26 = new ImageIcon("pic/" + fileName);
		image26.setImage(image26.getImage().getScaledInstance(270, 150,
				Image.SCALE_DEFAULT));

		panel2 = new JPanel();
		panel2.setBounds(0, 111, 1000, 435);
		panel2.setLayout(null);
		panel2.setBackground(null);
		panel2.setOpaque(false);
		// 第一页的panel
		panel = new JPanel();
		panel.setBounds(0, 0, 1000, 435);
		panel.setLayout(null);
		panel.setBackground(null);
		panel.setOpaque(false);
		//拍摄照片
		ImageIcon image31 = new ImageIcon(Property.CAMERA_PATH);
		image31.setImage(image31.getImage().getScaledInstance(200, 265,
				Image.SCALE_DEFAULT));

		panel2.add(panel);
		this.showJpanel.add(panel2);
		setVisible(true);// 可见
		final UtilImage image1 = new UtilImage("pic/" + fileName1);
		image1.setBounds(163, 60, 270, 150);
		image1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblNewLabel_2.setVisible(false);
			}
		});
		panel.add(image1);
		image1.setIcon(image23);
		final UtilImage image4 = new UtilImage("pic/" + fileName);
		image4.setBounds(163, 260, 270, 150);
		image4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblNewLabel_4.setVisible(false);
			}
		});
		panel.add(image4);
		image4.setIcon(image26);
		lblNewLabel_2 = new JLabel("提示；没有查看，请点击查看");
		lblNewLabel_2.setBounds(215, 217, 218, 15);
		panel.add(lblNewLabel_2);
		lblNewLabel_2.setVisible(false);
		lblNewLabel_2.setForeground(Color.RED);
		lblNewLabel_2.setFont(new Font("微软雅黑", Font.PLAIN, 12));

		lblNewLabel_4 = new JLabel("提示；没有查看，请点击查看");
		lblNewLabel_4.setBounds(215, 420, 218, 15);
		panel.add(lblNewLabel_4);
		lblNewLabel_4.setVisible(false);
		lblNewLabel_4.setForeground(Color.RED);
		lblNewLabel_4.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		panel.setLayout(null);
		lblNewLabel_5 = new JLabel("提示；没有查看，请点击查看");
		lblNewLabel_5.setBounds(611, 420, 218, 15);
		panel.add(lblNewLabel_5);
		lblNewLabel_5.setVisible(false);
		lblNewLabel_5.setForeground(Color.RED);
		lblNewLabel_5.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		
		lblNewLabel_5_c = new JLabel();
		lblNewLabel_5_c.setBounds(611, 420, 218, 15);
		panel.add(lblNewLabel_5_c);
		lblNewLabel_5_c.setVisible(false);
		lblNewLabel_5_c.setForeground(Color.RED);
		lblNewLabel_5_c.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		
		lblNewLabel_6 = new JLabel("提示；没有查看，请点击查看");
		lblNewLabel_6.setBounds(611, 220, 218, 15);
		panel.add(lblNewLabel_6);
		lblNewLabel_6.setVisible(false);
		lblNewLabel_6.setForeground(Color.RED);
		lblNewLabel_6.setFont(new Font("微软雅黑", Font.PLAIN, 12));

		lblNewLabel_6_c = new JLabel();
		lblNewLabel_6_c.setBounds(579, 217, 290, 35);
		panel.add(lblNewLabel_6_c);
		lblNewLabel_6_c.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		lblNewLabel_6_c.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_c.setHorizontalAlignment(SwingConstants.LEFT);
		setDesc("核查结果：" + bcOpenBean.getInspect(), lblNewLabel_6_c);
		// 本人身份信息
		UtilImages image13 = new UtilImages("pic/tghtj.png");
		image13.setSize(124, 32);
		image13.setLocation(236, 10);
		image13.setIcon(new ImageIcon("pic/tghtj.png"));
		panel.add(image13);

		// 图标
		// 联网核查信息
		UtilImages image11 = new UtilImages("pic/fggh.png");
		image11.setBounds(623, 10, 124, 32);
		panel.add(image11);
		image11.setIcon(new ImageIcon("pic/fggh.png"));

		final UtilImage image3 = new UtilImage(Property.ID_CARD_SELF);
		image3.setBounds(589, 60, 270, 150);
		panel.add(image3);
		image3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblNewLabel_6.setVisible(false);
			}
		});
		image3.setIcon(image25);

		final UtilImage image5 = new UtilImage(Property.CAMERA_PATH);
		image5.setBounds(589, 260, 270, 150);
		panel.add(image5);
		image5.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblNewLabel_5.setVisible(false);
			}
		});
		image5.setIcon(image31);

		/* 提示信息 */
//		JLabel promptLabel = new JLabel("温馨提示：请逐一点击图片查验");
//		promptLabel.setHorizontalAlignment(JLabel.CENTER);
//		promptLabel.setFont(new Font("微软雅黑", Font.PLAIN, 25));
//		promptLabel.setBounds(0, 548, 1009, 30);
//		this.showJpanel.add(promptLabel);


		//重新拍照
		JLabel label2 = new JLabel(new ImageIcon("pic/newPic/cxpz.png"));
		label2.setBounds(1075, 770, 150, 50);
		add(label2);
		label2.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				logger.info("点击返回上一步按钮");
				backstep();
			}
		});
				
		//确认
		JLabel label = new JLabel(new ImageIcon("pic/newPic/confirm.png"));
		label.setBounds(890, 770, 150, 50);
		add(label);
		label.addMouseListener(new MouseAdapter(){
			public void mouseReleased(MouseEvent e){
				
				nextstep();
			}
		});		
		
		//退出
		JLabel label_1 = new JLabel(new ImageIcon("pic/newPic/exit.png"));
		label_1.setBounds(1258, 770, 150, 50);
		add(label_1);
		label_1.addMouseListener(new MouseAdapter(){
			public void mouseReleased(MouseEvent e){
				//表示已经进入授权检查
            	bcOpenBean.setTellerIsChecked("1");
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				logger.info("点击退出按钮");
				closeVoice();
				clearTimeText();
				stopTimer(voiceTimer);
				openPanel(new BillChangeOpenAuthorNoPanel());
			}
		});
			
	}

	/***
	 * 下一步
	 */
	private void nextstep() {
		if(!on_off){
			logger.info("开关当前状态"+on_off);
			return;
		}
		logger.info("开关当前状态"+on_off);
		on_off=false;
		closeVoice();
		clearTimeText();
		stopTimer(voiceTimer);
		
		//跳入下一页
		openPanel(new BillChangeOpenDepositAffirmSignaPanel());
	}

	/***
	 * 上一步
	 */
	private void backstep() {
		if(!on_off){
			logger.info("开关当前状态"+on_off);
			return;
		}
		logger.info("开关当前状态"+on_off);
		on_off=false;
		closeVoice();
		clearTimeText();
		stopTimer(voiceTimer);
		bcOpenBean.setReCamera("0");
		openPanel(new BillChangeOpenCameraPanel());
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
