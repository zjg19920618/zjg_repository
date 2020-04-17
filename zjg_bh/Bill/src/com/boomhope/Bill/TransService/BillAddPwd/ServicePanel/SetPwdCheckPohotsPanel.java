package com.boomhope.Bill.TransService.BillAddPwd.ServicePanel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Util.BmpUtil;
import com.boomhope.Bill.Util.Property;
import com.boomhope.Bill.Util.UtilImage;
import com.boomhope.Bill.Util.UtilImages;
/**
 * 检查照片
 */
public class SetPwdCheckPohotsPanel extends BaseTransPanelNew{

	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(SetPwdCheckPohotsPanel.class);
	JLabel lblNewLabel = null;// 错误提示
	JLabel lblNewLabel_2 = null;// 错误提示
	JLabel lblNewLabel_4 = null;// 错误提示
	JLabel lblNewLabel_3 = null;// 错误提示
	JLabel lblNewLabel_5 = null;// 错误提示
	JLabel lblNewLabel_6 = null;// 错误提示
	JLabel lblNewLabel_6_c = null;// 错误提示
	JLabel lblNewLabel_5_c=null;//联网核查结果
	JLabel lblNewLabel_7_c=null;//联网核查结果
	UtilImage image1 = null;
	UtilImage image2 = null;
	UtilImage image3 = null;
	UtilImage image4 = null;
	UtilImages image5 = null;
	UtilImages image6 = null;
	UtilImages image7 = null;
	UtilImages image8 = null;
	boolean flag;// 用于判断是打开第1还是第2个JPanel
	/***
	 * 初始化
	 */
	public SetPwdCheckPohotsPanel() {
		/*显示时间倒计时*/
		this.showTimeText(BaseTransPanelNew.delaySecondShortTime);
		delayTimer = new Timer(BaseTransPanelNew.delaySecondShortTime*1000,new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				clearTimeText();
				serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！","","");
			}
			
		});
		delayTimer.start();
		/* 倒计时打开语音 */
		voiceTimer = new Timer(BaseTransPanelNew.voiceSecond, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stopTimer(voiceTimer);//关语音
            	closeVoice();//关语音流
				excuteVoice("voice/checkIdMsg.wav");

			}
		});
		voiceTimer.start();
		        /* 标题信息 */
		        JLabel titleLabel = new JLabel("请核对身份信息");
		        titleLabel.setHorizontalAlignment(JLabel.CENTER);
		        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 36));
		        titleLabel.setForeground(Color.decode("#412174"));
		        titleLabel.setBounds(0, 30, 1009, 40);
		        this.showJpanel.add(titleLabel);
		        // 图片转换
				/*本人身份证照片正面*/
				File face = new File(Property.BILL_ID_SELF_JUST);
				String face1 = face.getName();
				BmpUtil.bmpTojpg(face.getPath(), "pic/" + face1);
				ImageIcon image20 = new ImageIcon("pic/" + face1);
				image20.setImage(image20.getImage().getScaledInstance(270, 150,Image.SCALE_DEFAULT));
				
				/*本人身份证照片反面*/
				// 图片转换
				File back = new File(Property.BILL_ID_SELF_AGAINST);
				String back1 = back.getName();
				BmpUtil.bmpTojpg(back.getPath(), "pic/" + back1);
				ImageIcon image21 = new ImageIcon("pic/" + back1);
				image21.setImage(image21.getImage().getScaledInstance(270, 150,Image.SCALE_DEFAULT));
				
				/*本人身份证人脸照片*/
				// 图片转换
				ImageIcon image22 = new ImageIcon(Property.BILL_ID_SELF_FACE);
				image22.setImage(image22.getImage().getScaledInstance(132, 163,Image.SCALE_DEFAULT));
				
				/*本人拍照照片*/
				// 图片转换
				ImageIcon image23 = new ImageIcon(Property.CAMERA_PATH);
				image23.setImage(image23.getImage().getScaledInstance(200, 265,Image.SCALE_DEFAULT));

				/*
				 * 核查证件照片和拍照照片
				 */
				//本人身份证正面照片
				image1 = new UtilImage("pic/" + face1);
				image1.setBounds(175, 360, 270, 150);
				image1.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						lblNewLabel_2.setVisible(false);
					}
				});
				this.showJpanel.add(image1);
				image1.setIcon(image20);
				
				//本人身份证反面照片
				image2 = new UtilImage("pic/" + back1);
				image2.setBounds(565, 360, 270, 150);
				image2.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						lblNewLabel_4.setVisible(false);
					}
				});
				this.showJpanel.add(image2);
				image2.setIcon(image21);
				
				//身份证脸部照片
				image3 = new UtilImage(Property.BILL_ID_SELF_FACE);
				image3.setBounds(245, 130, 270, 150);
				image3.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						lblNewLabel_3.setVisible(false);
					}
				});
				this.showJpanel.add(image3);
				image3.setIcon(image22);
				
				//拍照照片
				image4 = new UtilImage(Property.CAMERA_PATH);
				image4.setBounds(600, 130, 270, 150);
				image4.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						lblNewLabel_5.setVisible(false);
					}
				});
				this.showJpanel.add(image4);
				image4.setIcon(image23);
				
				image5 = new UtilImages("pic/newPic/mehead.png");		
				image5.setBounds(248, 90, 124, 32);
				image5.setIcon(new ImageIcon("pic/newPic/mehead.png"));
				this.showJpanel.add(image5);
				
				image6 = new UtilImages("pic/newPic/camer.png");
				image6.setBounds(635, 90, 124, 32);
				image6.setIcon(new ImageIcon("pic/newPic/camer.png"));
				this.showJpanel.add(image6);
				
				image7 = new UtilImages("pic/newPic/meface.png");
				image7.setBounds(248, 320, 124, 32);
				image7.setIcon(new ImageIcon("pic/newPic/meface.png"));
				this.showJpanel.add(image7);
				
				image8 = new UtilImages("pic/newPic/meback.png");
				image8.setBounds(635, 320, 124, 32);
				image8.setIcon(new ImageIcon("pic/newPic/meback.png"));
				this.showJpanel.add(image8);
				
//				
//				lblNewLabel_6_c = new JLabel("核查结果：" + addPwdBean.getFaceCompareVal());
//				lblNewLabel_6_c.setBounds(0, 530, 1009, 40);
//				this.showJpanel.add(lblNewLabel_6_c);
//				lblNewLabel_6_c.setFont(new Font("微软雅黑", Font.BOLD, 12));
//				lblNewLabel_6_c.setHorizontalAlignment(SwingConstants.CENTER);
//
//				/* 提示信息 */
//				JLabel promptLabel = new JLabel("温馨提示：请逐一点击图片查验");
//				promptLabel.setHorizontalAlignment(JLabel.CENTER);
//				promptLabel.setFont(new Font("微软雅黑", Font.PLAIN, 25));
//				promptLabel.setBounds(0, 570, 1009, 30);
//				this.showJpanel.add(promptLabel);

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
						//已检查，更改状态
						addPwdBean.setTellerIsChecked("1");
						logger.info("点击退出按钮");
						closeVoice();
						clearTimeText();
						stopTimer(voiceTimer);
						//退出
						//跳入输入授权柜员号
						openPanel(new SetPwdAuthorNoPanel());
					}
				});
					
			}

			/***
			 * 下一步
			 */
			private void nextstep() {
				closeVoice();
				clearTimeText();
				stopTimer(voiceTimer);
				
				//跳入下一页
				openPanel(new SetPwdCheckBillQZPanel());
				
			}

			/***
			 * 重新拍照
			 */
			private void backstep() {
				closeVoice();
				clearTimeText();
				stopTimer(voiceTimer);
				addPwdBean.setRacameraCout("1");//检查身份信息后重新拍照
				openPanel(new SetPwdCameraPanel());
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
