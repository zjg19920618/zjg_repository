package com.boomhope.Bill.TransService.AllTransPublicPanel.IDCard;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import com.boomhope.Bill.Framework.BaseTransBean;
import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Public.GlobalParameter;
import com.boomhope.Bill.Util.BmpUtil;
import com.boomhope.Bill.Util.Property;
import com.boomhope.Bill.Util.UtilButton;
import com.boomhope.Bill.Util.UtilImage;
import com.boomhope.Bill.Util.UtilImages;

/**
 * 请授权-存在代理人
 * 
 * @author Administrator
 *
 */
@SuppressWarnings("static-access")
public class AllPublicCheckAgentPhotos extends BaseTransPanelNew {
	static Logger logger = Logger.getLogger(AllPublicCheckAgentPhotos.class);
	private static final long serialVersionUID = 1L;
	Timer voiceTimer1;
	String imageFile;
	JLabel lblNewLabel = null;// 错误提示
	JLabel lblNewLabel_1 = null;// 错误提示
	JLabel lblNewLabel_2 = null;// 错误提示
	JLabel lblNewLabel_4 = null;// 错误提示
	JLabel lblNewLabel_4desc = null;
	JLabel lblNewLabel_5 = null;// 错误提示
	JLabel lblNewLabel_5desc = null;
	JLabel lblNewLabel_6 = null;// 错误提示
	JLabel lblNewLabel_6desc = null;
	JLabel lblNewLabel_3 = null;// 错误提示
	JLabel lblNewLabel_9 = null;// 错误提示
	JLabel lblNewLabel_9desc = null;
	UtilImages image13 = null;
	UtilImages image12 = null;
	UtilImages image11 = null;
	UtilImages image10 = null;
	UtilImages image9 = null;
	UtilImages image8 = null;
	UtilImage image7 = null;
	UtilImage image6 = null;
	UtilImage image5 = null;
	UtilImage image4 = null;
	UtilImage image3 = null;
	UtilImage image3_1 = null;
	UtilImage image2 = null;
	UtilImage image1 = null;
	UtilImage image = null;
	JPanel empower_panel = null;
	JPanel panel = null;
	JPanel empower_panel_1 = null;
	private boolean on_off=true;

	/***
	 * 初始化
	 */
	public AllPublicCheckAgentPhotos() {
		logger.info("进入代理人核查照片页面");
		
		//将当前页面传入流程控制进行操作
		baseTransBean.setThisComponent(this);

		/* 显示时间倒计时 */
		this.showTimeText(delaySecondLongTime);
		delayTimer = new Timer(delaySecondLongTime * 1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logger.info("代理人核查照片页面倒计时结束 ");
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
		JLabel titleLabel = new JLabel("请授权  ");
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 36));
		titleLabel.setBounds(0, 85, GlobalParameter.TRANS_WIDTH, 40);
		this.showJpanel.add(titleLabel);

		//重新拍照
		JLabel submitBtn = new JLabel(new ImageIcon("pic/newPic/cxpz.png"));
		submitBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击重新拍照");
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				/* 处理上一页 */
				scanBill1();
			}
		});
		submitBtn.setSize(150, 50);
		submitBtn.setLocation(1075, 770);
		add(submitBtn);

		// 确认
		JLabel confirm = new JLabel(new ImageIcon("pic/newPic/confirm.png"));
		confirm.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击确认按钮");
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				/* 处理下一页 */
				scanBill();

			}
		});
		confirm.setSize( 150, 50);
		confirm.setLocation(890, 770);
		add(confirm);
		
		//如果为第二笔以上授权，则不显示重新拍照按钮
		if(baseTransBean.getAllPubtransAuthorNo()!=null && "2".equals(baseTransBean.getAllPubtransAuthorNo().trim())){
			submitBtn.setVisible(false);
			confirm.setLocation(1075, 770);
		}
				
		// 返回
		JLabel backButton = new JLabel(new ImageIcon("pic/newPic/exit.png"));
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
				clearTimeText();
				stopTimer(voiceTimer);//关闭语音
		    	closeVoice();
		    	accCancelExit();
			}
		});
		add(backButton);
		
		empower();
		
	}

	/***
	 * 下一步
	 */
	private void scanBill() {
		logger.info("进入确认方法");
		clearTimeText();
		closeVoice();
		stopTimer(voiceTimer);
		if(!"1".equals(baseTransBean.getAllPubIsCheckAuthor())){//如果授权不是通过状态
			baseTransBean.setAllPubIsCheckAuthor("1");
		}
		allPubTransFlow.transFlow();
	}

	/***
	 * 重新拍照
	 */
	private void scanBill1() {
		logger.info("进入重新拍照方法");
		clearTimeText();
		closeVoice();
		stopTimer(voiceTimer);
		
		baseTransBean.setAllPubIsCheckAuthor("3");
		if(BaseTransBean.getAllPubtransAuthorNo()!=null &&"1".equals(BaseTransBean.getAllPubtransAuthorNo().trim())){//如果是第一次授权时
			baseTransBean.setAllPubReCamera("1");//重新拍照
		}
		lostPubBean.setUpStepName("ALL_PUBLIC_CHECK_AGENT_PHOTOS_PANLE");
		lostPubBean.setNextStepName("ALL_PUBLIC_PRINT_CAMERA_PANEL");
		allPubTransFlow.transFlow();
	}



	/**
	 * 执行语音
	 */
	private void excuteVoice1() {
		logger.info("进入语音执行方法");
		try {
			FileInputStream fileau = new FileInputStream("voice/verify.wav");
			as = new AudioStream(fileau);
			AudioPlayer.player.start(as);
		} catch (Exception e) {
			logger.error("语音执行异常"+e);
		}
	}

	/**
	 * 拍照
	 */
	public void empower() {
		logger.info("进入拍照方法");
		String inspect=baseTransBean.getAllPubInspect();
		String photoPath=baseTransBean.getAllPubPhotoPath();
		String agentinspect=baseTransBean.getAllPubAgentInspect();
		String agentPhotoPath=baseTransBean.getAllPubAgentPhotoPath();;
		/* 授权图片 */

		// 图片转换
		File tempFile = new File(Property.BILL_ID_SELF_JUST);
		String fileName = tempFile.getName();
		BmpUtil.bmpTojpg(Property.BILL_ID_SELF_JUST, "pic/" + fileName);
		ImageIcon image23 = new ImageIcon("pic/" + fileName);
		image23.setImage(image23.getImage().getScaledInstance(200, 110,
				Image.SCALE_DEFAULT));
		
		// 图片转换
		File tempFile3 = new File(Property.BILL_ID_SELF_AGAINST);
		String fileName3 = tempFile3.getName();
		BmpUtil.bmpTojpg(Property.BILL_ID_SELF_AGAINST, "pic/" + fileName3);
		ImageIcon image27 = new ImageIcon("pic/" + fileName3);
		image27.setImage(image27.getImage().getScaledInstance(200, 110,
				Image.SCALE_DEFAULT));		

		// 图片转换
		File tempFile1 = new File(Property.BILL_ID_AGENT_JUST.trim());
		String fileName1 = tempFile1.getName();
		BmpUtil.bmpTojpg(Property.BILL_ID_AGENT_JUST, "pic/" + fileName1);
		ImageIcon image24 = new ImageIcon("pic/" + fileName1);
		image24.setImage(image24.getImage().getScaledInstance(200, 110,
				Image.SCALE_DEFAULT));
		
		File tempFile4 = new File(Property.BILL_ID_AGENT_AGAINST.trim());
		String fileName4 = tempFile4.getName();
		BmpUtil.bmpTojpg(Property.BILL_ID_AGENT_AGAINST, "pic/" + fileName4);
		System.out.println("pic/" + fileName4);
		ImageIcon image28 = new ImageIcon("pic/" + fileName4);
		image28.setImage(image28.getImage().getScaledInstance(200, 110,
				Image.SCALE_DEFAULT));
		
		ImageIcon image25 = new ImageIcon(photoPath);
		image25.setImage(image25.getImage().getScaledInstance(200, 300,
				Image.SCALE_DEFAULT));
		final ImageIcon image31 = new ImageIcon(Property.CAMERA_PATH);
		image31.setImage(image31.getImage().getScaledInstance(200, 300,
				Image.SCALE_DEFAULT));

		ImageIcon image29 = new ImageIcon(agentPhotoPath);
		image29.setImage(image29.getImage().getScaledInstance(200, 300,
				Image.SCALE_DEFAULT));
		/**
		 * 主panel
		 * 
		 */
		panel = new JPanel();
		panel.setBounds(32, 135, 1000, 480);
		panel.setLayout(null);
		panel.setBackground(null);
		panel.setOpaque(false);
		/**
		 * 第一页的panel
		 */
		empower_panel = new JPanel();
		empower_panel.setBounds(0, 0, 1000, 427);
		empower_panel.setLayout(null);
		empower_panel.setOpaque(false);
		
		/***
		 * 第二页的panel
		 */
		empower_panel_1 = new JPanel();
		empower_panel_1.setBounds(0, 0, 1000, 480);
		empower_panel_1.setLayout(null);
		empower_panel_1.setOpaque(false);
		panel.add(empower_panel);
		this.showJpanel.add(panel);
		this.showJpanel.setVisible(true);

		image1 = new UtilImage("pic/" + fileName);
		image1.setSize(200, 110);
		image1.setLocation(200, 100);
		image1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblNewLabel_1.setVisible(false);
			}
		});
		image1.setIcon(image23);
		empower_panel.add(image1);
		
		image2 = new UtilImage("pic/" + fileName1);
		image2.setSize(200, 110);
		image2.setLocation(591, 100);
		image2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblNewLabel_2.setVisible(false);
			}
		});
		image2.setIcon(image24);
		empower_panel.add(image2);
		
		image3 = new UtilImage("pic/" + fileName3);
		image3.setSize(200, 110);
		image3.setLocation(200, 260);
		image3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblNewLabel_3.setVisible(false);
			}
		});
		image3.setIcon(image27);
		empower_panel.add(image3);
		
		image4 = new UtilImage("pic/" + fileName4);
		image4.setSize(200, 110);
		image4.setLocation(591, 260);
		image4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblNewLabel_4.setVisible(false);
			}
		});
		image4.setIcon(image28);
		empower_panel.add(image4);

		
		
		image5 = new UtilImage(photoPath);
		image5.setSize(200, 270);
		image5.setLocation(100, 100);
		image5.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblNewLabel_5.setVisible(false);
			}
		});
		image5.setIcon(image25);
		empower_panel_1.add(image5);

		image6 = new UtilImage(Property.CAMERA_PATH);
		image6.setSize(200, 270);
		image6.setLocation(700, 100);
		image6.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblNewLabel_9.setVisible(false);

			}
		});
		image6.setIcon(image31);
		empower_panel_1.add(image6);

		image7 = new UtilImage(agentPhotoPath);
		image7.setSize(200, 270);
		image7.setLocation(400, 100);
		image7.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblNewLabel_6.setVisible(false);
			}
		});
		image7.setIcon(image29);
		empower_panel_1.add(image7);
		
		
		image9 = new UtilImages("pic/tghtj.png");
		image9.setSize(124, 32);
		image9.setLocation(237, 40);
		image9.setIcon(new ImageIcon("pic/tghtj.png"));
		empower_panel.add(image9);
		
		image10 = new UtilImages("pic/fghy.png");
		image10.setSize(124, 32);
		image10.setLocation(627, 40);
		image10.setIcon(new ImageIcon("pic/fghy.png"));
		empower_panel.add(image10);

		image11 = new UtilImages("pic/me_id_check.png");
		image11.setSize(124, 32);
		image11.setLocation(130, 40);
		image11.setIcon(new ImageIcon("pic/me_id_check.png"));
		empower_panel_1.add(image11);

		image12 = new UtilImages("pic/agent_id_check.png");
		image12.setSize(124, 32);
		image12.setLocation(430, 40);
		image12.setIcon(new ImageIcon("pic/agent_id_check.png"));
		empower_panel_1.add(image12);

		image13 = new UtilImages("pic/face_check.png");
		image13.setSize(124, 32);
		image13.setLocation(730, 40);
		image13.setIcon(new ImageIcon("pic/face_check.png"));
		empower_panel_1.add(image13);

		lblNewLabel_1 = new JLabel("提示：没有查看，请点击查看");
		lblNewLabel_1.setVisible(false);
		lblNewLabel_1.setForeground(Color.RED);
		lblNewLabel_1.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		lblNewLabel_1.setBounds(225, 230, 170, 15);
		empower_panel.add(lblNewLabel_1);

		lblNewLabel_2 = new JLabel("提示：没有查看，请点击查看");
		lblNewLabel_2.setVisible(false);
		lblNewLabel_2.setForeground(Color.RED);
		lblNewLabel_2.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		lblNewLabel_2.setBounds(615, 230, 200, 15);
		empower_panel.add(lblNewLabel_2);
		
		lblNewLabel_3 = new JLabel("提示；没有查看，请点击查看");
		lblNewLabel_3.setVisible(false);
		lblNewLabel_3.setForeground(Color.RED);
		lblNewLabel_3.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		lblNewLabel_3.setBounds(225, 380, 162, 15);
		empower_panel.add(lblNewLabel_3);

		lblNewLabel_4 = new JLabel("提示；没有查看，请点击查看");
		lblNewLabel_4.setVisible(false);
		lblNewLabel_4.setForeground(Color.RED);
		lblNewLabel_4.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		lblNewLabel_4.setBounds(615, 380, 162, 15);
		empower_panel.add(lblNewLabel_4);

		
		lblNewLabel_5 = new JLabel("提示：没有查看，请点击查看");
		lblNewLabel_5.setVisible(false);
		lblNewLabel_5.setForeground(Color.RED);
		lblNewLabel_5.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		lblNewLabel_5.setBounds(100, 310, 162, 15);
		empower_panel_1.add(lblNewLabel_5);

		lblNewLabel_5desc = new JLabel();
		lblNewLabel_5desc.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		lblNewLabel_5desc.setBounds(100, 400, 200, 80);
		lblNewLabel_5desc.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_5desc.setHorizontalAlignment(SwingConstants.LEFT);
		setDesc("核查结果：" + inspect, lblNewLabel_5desc);
		empower_panel_1.add(lblNewLabel_5desc);

		lblNewLabel_6 = new JLabel("提示；没有查看，请点击查看");
		lblNewLabel_6.setVisible(false);
		lblNewLabel_6.setForeground(Color.RED);
		lblNewLabel_6.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		lblNewLabel_6.setBounds(400, 310, 162, 15);
		empower_panel_1.add(lblNewLabel_6);

		lblNewLabel_6desc = new JLabel();
		lblNewLabel_6desc.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6desc.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6desc.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		lblNewLabel_6desc.setBounds(400, 400, 200, 80);
		setDesc("核查结果：" + agentinspect, lblNewLabel_6desc);
		empower_panel_1.add(lblNewLabel_6desc);


		lblNewLabel_9 = new JLabel("提示；没有查看，请点击查看");
		lblNewLabel_9.setVisible(false);
		lblNewLabel_9.setForeground(Color.RED);
		lblNewLabel_9.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		lblNewLabel_9.setBounds(700, 310, 162, 15);
		empower_panel_1.add(lblNewLabel_9);

		lblNewLabel_9desc = new JLabel();
		lblNewLabel_9desc.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_9desc.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_9desc.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		lblNewLabel_9desc.setBounds(700, 400, 200, 80);
		lblNewLabel_9desc.setVisible(false);
		setDesc("识别结果：人脸相似度为"+baseTransBean.getAllPubSims()+"%", lblNewLabel_9desc);
		empower_panel_1.add(lblNewLabel_9desc);
		/**
		 * 第一页的按钮
		 */
		UtilButton btnNewButton_3 = new UtilButton("pic/newPic/right.png",
				"pic/newPic/right.png");
		btnNewButton_3.setBounds(875, 165, 57, 98);
		empower_panel.add(btnNewButton_3);
		

		
		/**
		 * 下一页的btn
		 */
		UtilButton btnNewButton = new UtilButton("pic/newPic/left.png",
				"pic/newPic/left.png");
		btnNewButton.setBounds(30, 164, 57, 98);
		empower_panel_1.add(btnNewButton);
		
		/***
		 * 对第一页监听
		 */
		btnNewButton_3.addActionListener(new ActionListener() {// 事件响应
					@Override
					public void actionPerformed(ActionEvent e) {
						panel.removeAll();
						panel.add(empower_panel_1);
						panel.revalidate(); // 重新验证
						panel.repaint(); // 重画
					}
				});
		/***
		 * 对下一页监听
		 */

		btnNewButton.addActionListener(new ActionListener() {// 事件响应
					@Override
					public void actionPerformed(ActionEvent e) {
						panel.removeAll();
						panel.add(empower_panel);
						panel.revalidate(); // 重新验证
						panel.repaint(); // 重画
					}
				});

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
