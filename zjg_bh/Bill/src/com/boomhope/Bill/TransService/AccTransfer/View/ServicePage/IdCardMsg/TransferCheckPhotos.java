package com.boomhope.Bill.TransService.AccTransfer.View.ServicePage.IdCardMsg;

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
import com.boomhope.Bill.PublicControl.OutputBankCardPanel;
import com.boomhope.Bill.TransService.AccTransfer.Bean.PublicAccTransferBean;
import com.boomhope.Bill.Util.BmpUtil;
import com.boomhope.Bill.Util.Property;
import com.boomhope.Bill.Util.UtilImage;
import com.boomhope.Bill.Util.UtilImages;

/***
 * 检查照片界面
 * 
 * @author hao
 * 
 */
public class TransferCheckPhotos extends BaseTransPanelNew {

	static Logger logger = Logger.getLogger(TransferCheckPhotos.class);
	private static final long serialVersionUID = 1L;
	JLabel lblNewLabel_2 = null;
	JLabel lblNewLabel_4 = null;
	JLabel lblNewLabel_5 = null;
	JLabel lblNewLabel_6 = null;
	JLabel lblNewLabel_6_c = null;
	JLabel lblNewLabel_8 = null;
	JLabel lblNewLabel_4_c = null;
	JLabel lblNewLabel_5_c = null;
	JPanel panel = null;
	JPanel panel2 = null;
	boolean flag;// 用于判断是打开第1还是第2个JPanel
	private PublicAccTransferBean transferMoneyBean;
	private boolean on_off=true;
	/***
	 * 初始化
	 */
	public TransferCheckPhotos(final PublicAccTransferBean transferBean) {
		transferMoneyBean=transferBean;
		
		// 将当前页面传入流程控制进行操作
		logger.info("进入照片核查页面");
		final Component comp=this;
		/* 显示时间倒计时 */
		this.showTimeText(delaySecondLongTime);
		delayTimer = new Timer(delaySecondLongTime * 1000,
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						/* 倒计时结束退出交易 */
						clearTimeText();
						serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！", "","");
					}
				});
		delayTimer.start();
		
		/* 标题信息 */
		JLabel titleLabel = new JLabel("身份信息核查");
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 36));
		titleLabel.setBounds(0, 60, 1009, 40);
		this.showJpanel.add(titleLabel);
		
		// 图片转换
		/* 本人身份证照片正面 */
		File tempFile1 = new File(Property.BILL_ID_SELF_JUST);
		String fileName1 = tempFile1.getName();
		BmpUtil.bmpTojpg(tempFile1.getPath(), "pic/" + fileName1);
		ImageIcon image23 = new ImageIcon("pic/" + fileName1);
		image23.setImage(image23.getImage().getScaledInstance(270, 150,
				Image.SCALE_DEFAULT));
		
		// 图片转换
		/* 本人身份证照片反面 */
		File tempFile = new File(Property.BILL_ID_SELF_AGAINST);
		String fileName = tempFile.getName();
		BmpUtil.bmpTojpg(tempFile.getPath(), "pic/" + fileName);
		ImageIcon image26 = new ImageIcon("pic/" + fileName);
		image26.setImage(image26.getImage().getScaledInstance(270, 150,
				Image.SCALE_DEFAULT));

		/* 本人照片 */
		ImageIcon image25 = new ImageIcon(Property.ID_CARD_SELF);
		image25.setImage(image25.getImage().getScaledInstance(132, 163,
				Image.SCALE_DEFAULT));
		
		// 拍摄照片
		ImageIcon image31 = new ImageIcon(Property.CAMERA_PATH);
		image31.setImage(image31.getImage().getScaledInstance(200, 265,
				Image.SCALE_DEFAULT));

		panel2 = new JPanel();
		panel2.setBounds(0, 111, 1000, 427);
		panel2.setLayout(null);
		panel2.setBackground(null);
		panel2.setOpaque(false);
		// 第一页的panel
		panel = new JPanel();
		panel.setBounds(0, 0, 1000, 427);
		panel.setLayout(null);
		panel.setBackground(null);
		panel.setOpaque(false);

		panel2.add(panel);
		this.showJpanel.add(panel2);
		setVisible(true);// 可见
		
		//本人正面照
		final UtilImage image1 = new UtilImage("pic/" + fileName1);
		image1.setBounds(163, 60, 270, 150);
		panel.add(image1);
		image1.setIcon(image23);
		
		//本人反面照
		final UtilImage image4 = new UtilImage("pic/" + fileName);
		image4.setBounds(163, 242, 270, 150);
		panel.add(image4);
		image4.setIcon(image26);
		
		//本人照片
		final UtilImage image3 = new UtilImage(Property.ID_CARD_SELF);
		image3.setBounds(603, 60, 270, 150);
		panel.add(image3);
		image3.setIcon(image25);

		//拍摄照片
		final UtilImage image5 = new UtilImage(Property.CAMERA_PATH);
		image5.setBounds(569, 242, 270, 150);
		panel.add(image5);
		image5.setIcon(image31);
		

		lblNewLabel_6_c = new JLabel();
		lblNewLabel_6_c.setBounds(788, 131, 212, 47);
		panel.add(lblNewLabel_6_c);
		lblNewLabel_6_c.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		lblNewLabel_6_c.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_c.setHorizontalAlignment(SwingConstants.LEFT);
		setDesc("核查结果：" +transferBean.getResultCheckIdCard(), lblNewLabel_6_c);
		
		// 图标
		// 本人身份信息
		UtilImages image13 = new UtilImages("pic/tghtj.png");
		image13.setSize(124, 32);
		image13.setLocation(236, 10);
		image13.setIcon(new ImageIcon("pic/tghtj.png"));
		panel.add(image13);

		// 图标
		// 联网核查信息
		UtilImages image11 = new UtilImages("pic/fggh.png");
		image11.setBounds(606, 10, 124, 32);
		panel.add(image11);
		image11.setIcon(new ImageIcon("pic/fggh.png"));

		/* 提示信息 */
		JLabel promptLabel = new JLabel("单击可放大图片");
		promptLabel.setHorizontalAlignment(JLabel.CENTER);
		promptLabel.setFont(new Font("微软雅黑", Font.PLAIN, 25));
		promptLabel.setBounds(0, 548, 1009, 30);
		this.showJpanel.add(promptLabel);
		
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
				//清空倒计时
				clearTimeText();
				closeVoice();
				stopTimer(voiceTimer);
				//设置柜员检查结果为空
				transferMoneyBean.setTellerIsChecked("");
				transferMoneyBean.setIsSign("");
				//跳转回上一页面
				openPanel(new TransferPrintCameraPanel(transferMoneyBean));
			}
		});
		submitBtn.setSize( 150, 50);
		submitBtn.setLocation(1075, 770);
		add(submitBtn);

		// 确认
		JLabel backBtn = new JLabel(new ImageIcon("pic/newPic/confirm.png"));
		backBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击确定按钮");
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				scanBill();
			}
		});
		backBtn.setSize( 150, 50);
		backBtn.setLocation(890, 770);
		add(backBtn);
		// 返回
		JLabel utilButton = new JLabel(new ImageIcon("pic/newPic/exit.png"));
		utilButton.setBounds(1258, 770, 150, 50);
		utilButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				closeVoice();
				stopTimer(voiceTimer);
				//清空倒计时
				clearTimeText();
				openPanel(new OutputBankCardPanel());
			}
		});
		add(utilButton);
		setDesc(transferMoneyBean.getResultCheckIdCard(),lblNewLabel_6_c);

	}

	/***
	 * 下一步
	 */
	private void scanBill() {
		closeVoice();
		stopTimer(voiceTimer);
		clearTimeText();
		
		//已检查，更改状态
		if(null==transferMoneyBean.getTellerIsChecked() || "".equals(transferMoneyBean.getTellerIsChecked())){
			transferMoneyBean.setTellerIsChecked("1");
		}else if("2".equals(transferMoneyBean.getTellerIsChecked()) && transferMoneyBean.getSupTellerNo()!=null && !"".equals(transferMoneyBean.getSupTellerNo())){
			transferMoneyBean.setTellerIsChecked("3");
		}
		openPanel(new TransferSignConfirmPanel(transferMoneyBean));
	}

	
	/**
	 * 设置
	 */
	public void setDesc(String s, JLabel label) {
		String stopMsg = "";
		if(""==s || null == s){
			
		}else if(s.length()<=15){
			stopMsg = "<html><p>"+s+"</p></html>";
		}else if(s.length()>15 && s.length()<=30){
			stopMsg = "<html><p>"+s.substring(0, 15)+"</p>"
					+ "<p>"+s.substring(15, s.length())+"</p></html>";
		}else if(s.length()>30 && s.length()<=45){
			stopMsg = "<html><p>"+s.substring(0, 15)+"</p>"
					+ "<p>"+s.substring(15, 30)+"</p>"
					+ "<p>"+s.substring(30, s.length())+"</p></html>";
		}else if(s.length()>45 && s.length()<=60){
			stopMsg = "<html><p>"+s.substring(0, 15)+"</p>"
					+ "<p>"+s.substring(15, 30)+"</p>"
					+ "<p>"+s.substring(30, 45)+"</p>"
					+ "<p>"+s.substring(45, s.length())+"</p></html>";
		}
		label.setText(stopMsg);
	}
}
