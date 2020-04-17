package com.boomhope.Bill.TransService.AccCancel.AccountCancel.checkInfo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.media.Buffer;
import javax.media.CaptureDeviceInfo;
import javax.media.CaptureDeviceManager;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.Player;
import javax.media.control.FrameGrabbingControl;
import javax.media.format.VideoFormat;
import javax.media.util.BufferToImage;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import sun.audio.AudioStream;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Util.Property;
import com.boomhope.Bill.Util.UtilButton;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * 
 * title:拍照界面
 * 
 * @author hao 
 */
public class AccCancelCameraPanel extends BaseTransPanelNew {
	static Logger logger = Logger.getLogger(AccCancelCameraPanel.class);
	private static final long serialVersionUID = 1L;
	UtilButton pz_button = null;
	UtilButton cp_button = null;
	public static Player player = null;
	private CaptureDeviceInfo deviceInfo = null;
	private MediaLocator mediaLocator = null;
	private Component component = null;
	private JPanel vedioPanel = null;
	private Buffer buffer = null;
	private Image image = null;
	String str1 = "vfw:Logitech   USB   Video   Camera:0"; // 获取USB摄像头的字符串
	String str2 = "vfw:Microsoft WDM Image Capture (Win32):0"; // 获取本地摄像头的字符串
	AudioStream as = null;
	private JLabel confirm=null;
	private boolean on_off=true;//页面跳转开关
	private boolean cam_on_off=true;//拍照开关
	private boolean reCam_on_off=true;//重新开关

	public AccCancelCameraPanel() {
		logger.info("进入拍照页面");
		final Component comp=this;
		/* 标题信息 */
		JLabel titleLabel = new JLabel("请办理人对准摄像头拍照");
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 40));
		titleLabel.setBounds(0, 60, 1009, 40);
		this.showJpanel.add(titleLabel);
		
		//判断摄像头是否已经开启，若已经开启，先执行一次关闭
		if(player!=null && player.getTargetState()==600){
			player.close();
		}
		
		if(accCancelBean.getCameraCount()==null ||"".equals(accCancelBean.getCameraCount())){
			accCancelBean.setCameraCount("0");
		}
		
		this.showTimeText(delaySecondShortTime);
		delayTimer = new Timer(delaySecondShortTime * 1000,
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						/* 倒计时结束退出交易 */
						clearTimeText();
						player.close();
						vedioPanel.removeAll();
						vedioPanel.repaint();
						serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！", "","");
					}
				});
		delayTimer.start();
		/* 倒计时打开语音 */
		voiceTimer = new Timer(voiceSecond, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stopTimer(voiceTimer);
				closeVoice();//关语音流
				excuteVoice("voice/pzstart.wav");

			}
		});
		voiceTimer.start();
		// 上一步
		JLabel submitBtn = new JLabel(new ImageIcon("pic/preStep.png"));
		submitBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				accCancelBean.setCameraCount("");
				//清空倒计时和语音
				closeVoice();
				clearTimeText();
				stopTimer(voiceTimer);
				player.close();
				//跳转回上一页面
				
				if("1".equals(accCancelBean.getTellerIsChecked())){
					if("1".equals(accCancelBean.getHavAgentFlag())){//有代理人
						
						logger.info("代理人核查图片页");
						openPanel(new AccCancelCheckAgentPhotosPanel());
						
					}else{//本人
						logger.info("本人核查图片页");
						openPanel(new AccCancelCheckPhotosPanel());
					}
				}else if("1".equals(accCancelBean.getHavAgentFlag())){//有代理人
					
					logger.info("代理人选择");
					openPanel(new AccCancelDeputySelectionPanel());
					
				}else{//本人
					logger.info("代理人选择");
					openPanel(new AccCancelDeputySelectionPanel());
				}
					
			}
		});
		submitBtn.setSize(150, 50);
		submitBtn.setLocation(1075, 770);
		add(submitBtn);

		// 确认
		confirm = new JLabel(new ImageIcon("pic/newPic/confirm.png"));
		confirm.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				nextStep();
			}
		});
		confirm.setSize( 150, 50);
		confirm.setLocation(890, 770);
		confirm.setVisible(false);
		add(confirm);
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
				//清空倒计时和语音
				closeVoice();
				clearTimeText();
				stopTimer(voiceTimer);
				player.close();
				accCancelExit();
			}
		});
		add(backButton);
		
		vedioPanel = new JPanel();
		vedioPanel.setBounds(185, 125, 640, 400);
		vedioPanel.setLayout(null);
		vedioPanel.setBackground(Color.BLACK);
		this.showJpanel.add(vedioPanel);

		// 拍照
		pz_button = new UtilButton("pic/pz.png", "pic/pz.png");
		pz_button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(!cam_on_off){
					return;
				}
				reCam_on_off=true;
				cam_on_off=false;
				// 先把语音关掉
				closeVoice();
				// 关闭语音
				stopTimer(voiceTimer);
				pz();
				// 拍照声音
				excuteVoice("voice/pz.wav");
				pz_button.setVisible(false);
				cp_button.setVisible(true);
				confirm.setVisible(true);
			}
		});
		pz_button.setBounds(405, 539, 200, 50);
		this.showJpanel.add(pz_button);
		// 重拍
		cp_button = new UtilButton("pic/cp.png", "pic/cp.png");
		cp_button.setVisible(false);
		cp_button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(!reCam_on_off){
					return;
				}
				reCam_on_off=false;
				cam_on_off=true;
				vedioPanel.removeAll();
				open();
				pz_button.setVisible(true);
				cp_button.setVisible(false);
				confirm.setVisible(false);
			}

		});
		cp_button.setBounds(620, 539, 200, 50);
		this.showJpanel.add(cp_button);
		// 打开摄像头
		open();
	}

	/**
	 * 打开摄像头
	 */
	public void open() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				openProssDialog();
				logger.info("进入打开摄像头方法");
				deviceInfo = CaptureDeviceManager.getDevice(str2); // 根据字符串获取采集设备（摄像头）的引用
				mediaLocator = deviceInfo.getLocator(); // 获取采集设备的定位器的引用，需要根据此引用来创建视频播放器
				try {
					player = Manager.createRealizedPlayer(mediaLocator);// 利用mediaLocator
					component = player.getVisualComponent();
					if (component != null) {
						vedioPanel.add(component, BorderLayout.NORTH);
						player.start();
					}
					closeDialog(prossDialog);
				} catch (Exception e) {
					closeDialog(prossDialog);
					logger.error("摄像头打开失败:"+e);
					serverStop("摄像头打开失败，请联系大堂经理进行处理。", "","");
				}
				
			}
		}).start();
	}

	/**
	 * 拍照
	 */
	public void pz() {
		try {
			logger.info("进入拍照方法");
			FrameGrabbingControl fgc = (FrameGrabbingControl) player
					.getControl("javax.media.control.FrameGrabbingControl");
			buffer = fgc.grabFrame();
			BufferToImage bufferToImage = new BufferToImage(
					(VideoFormat) buffer.getFormat());
			image = bufferToImage.createImage(buffer);
			player.close();
			vedioPanel.removeAll();
			ImagePanel ppp = new ImagePanel();
			ppp.setImage(image);
			vedioPanel.add(ppp);
		} catch (Exception e) {
			clearTimeText();
			logger.error("拍照失败："+e);
			player.close();
			openPanel(new AccCancelFaceCheckFail("拍照失败，请联系大堂经理", ""));
		}		
	}

	// 保存图片
	public void saveImage(Image image, String path) {
		BufferedImage bi = new BufferedImage(image.getWidth(null),
				image.getHeight(null), BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = bi.createGraphics();
		g2.drawImage(image, null, null);
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(path);
		} catch (FileNotFoundException e) {
			logger.error("图片生成失败" + e);
			player.close();
			openPanel(new AccCancelFaceCheckFail("拍照失败，请联系大堂经理", ""));
		}
		JPEGImageEncoder je = JPEGCodec.createJPEGEncoder(fos);
		JPEGEncodeParam jp = je.getDefaultJPEGEncodeParam(bi);
		jp.setQuality(0.5f, false);
		je.setJPEGEncodeParam(jp);
		try {
			je.encode(bi);
			fos.close();
		} catch (IOException e) {
			logger.error("图片保存失败" + e);
			player.close();
			openPanel(new AccCancelFaceCheckFail("拍照失败，请联系大堂经理", ""));
		}
	}

	class ImagePanel extends Panel {
		public Image myimg = null;

		public ImagePanel() {
			setLayout(null);
			setSize(640, 400);
		}

		public void setImage(Image img) {
			this.myimg = img;
			repaint();
		}

		public void paint(Graphics g) {
			if (myimg != null) {
				g.drawImage(myimg, 0, 0, this);
			}
		}
	}
	
	//下一步
	public void nextStep(){
		//清空倒计时
		if(!on_off){
			logger.info("开关当前状态"+on_off);
			return;
		}
		logger.info("开关当前状态"+on_off);
		on_off=false;
		closeVoice();
		clearTimeText();
		stopTimer(voiceTimer);
		player.close();
		if("0".equals(accCancelBean.getReCamera())){
			//重新拍照跳转过来
			saveImage(image, Property.RE_CAMERA_PATH);
			
		}else{
			saveImage(image, Property.CAMERA_PATH);
		}
		//页面跳转
		openPanel(new AccCancelFaceCheckPanel());
	}

}
