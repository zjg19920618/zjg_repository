package com.boomhope.Bill.Test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Panel;
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
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class CameraUtil extends JFrame {

	public static Player player = null;
	private CaptureDeviceInfo deviceInfo = null;
	private MediaLocator mediaLocator = null;
	private Component component = null;
	private JPanel vedioPanel = null;
	private Buffer buffer = null;
	private JPanel image_panel1;
	BufferedImage bufferedimage = null;
	String str1 = "vfw:Logitech   USB   Video   Camera:0"; // 获取USB摄像头的字符串
	String str2 = "vfw:Microsoft WDM Image Capture (Win32):0"; // 获取本地摄像头的字符串

	public CameraUtil() {
		getContentPane().setLayout(null);
		this.setSize(1059, 750);
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createLineBorder(Color.red));// 设置面板边框颜色
		panel.setBounds(0, 0, 1043, 712);
		getContentPane().add(panel);
		panel.setLayout(null);

		vedioPanel = new JPanel();
		vedioPanel.setBounds(130, 195, 262, 293);
		vedioPanel.setBorder(BorderFactory.createLineBorder(Color.red));// 设置面板边框颜色
		panel.add(vedioPanel);

		JLabel label = new JLabel("请对准摄像头拍照");
		label.setBounds(457, 104, 178, 35);
		panel.add(label);

		image_panel1 = new JPanel();
		image_panel1.setBorder(BorderFactory.createLineBorder(Color.red));// 设置面板边框颜色
		image_panel1.setBounds(651, 195, 262, 293);
		panel.add(image_panel1);
		image_panel1.setLayout(null);

		JButton btnNewButton = new JButton("拍照");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				photograph();
			}

		});
		btnNewButton.setBounds(651, 522, 93, 23);
		panel.add(btnNewButton);
		open();
	}

	/**
	 * 打开摄像头
	 */
	public void open() {
		deviceInfo = CaptureDeviceManager.getDevice(str2); // 根据字符串获取采集设备（摄像头）的引用
		mediaLocator = deviceInfo.getLocator(); // 获取采集设备的定位器的引用，需要根据此引用来创建视频播放器
		try {
			player = Manager.createRealizedPlayer(mediaLocator);// 利用mediaLocator
			component = player.getVisualComponent();
			if (component != null) {
				vedioPanel.add(component, BorderLayout.NORTH);
				player.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 拍照
	 */
	public void photograph() {
		FrameGrabbingControl fgc = (FrameGrabbingControl) player.getControl("javax.media.control.FrameGrabbingControl");
		buffer = fgc.grabFrame();
		BufferToImage bufferToImage = new BufferToImage((VideoFormat) buffer.getFormat());
		Image image = bufferToImage.createImage(buffer);
		Image smallImage = image.getScaledInstance(262,293,Image.SCALE_FAST);
		ImagePanel ppp = new ImagePanel();
		ppp.setImage(smallImage);
		image_panel1.add(ppp);
		saveImage(smallImage, "c:/test11111111.jpg");
	}

	public static void saveImage(Image image, String path) {
		BufferedImage bi = new BufferedImage(image.getWidth(null),image.getHeight(null), BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = bi.createGraphics();
		g2.drawImage(image, null, null);
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(path);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		JPEGImageEncoder je = JPEGCodec.createJPEGEncoder(fos);
		JPEGEncodeParam jp = je.getDefaultJPEGEncodeParam(bi);
		jp.setQuality(0.5f, false);
		je.setJPEGEncodeParam(jp);
		try {
			je.encode(bi);
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	class ImagePanel extends Panel {
		public Image myimg = null;

		public ImagePanel() {
			setLayout(null);
			setSize(320, 240);
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

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// 打开窗体
					CameraUtil frame = new CameraUtil();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
