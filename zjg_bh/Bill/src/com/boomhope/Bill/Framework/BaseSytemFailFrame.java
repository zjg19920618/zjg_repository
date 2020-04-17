package com.boomhope.Bill.Framework;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Util.UtilButton;

/**
 * 登录界面
 * 
 * @author zy
 *
 */
public class BaseSytemFailFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;

//	static Logger logger = Logger.getLogger(BackTransChecingBillPanel.class);
	
	public BaseSytemFailFrame(String errmsg){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(0, 0, 1440, 900);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 窗体关闭时的操作 退出程序
		this.setUndecorated(true);
		getContentPane().setLayout(null);
		
		//点击退出按钮
		UtilButton backButton1 = new UtilButton("pic/5675671.png","pic/5675671.png");
		backButton1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				scanBill1(); 
			}

		});
		
		backButton1.setSize(200, 50);
		backButton1.setBounds(785, 513, 200, 50);
		backButton1.setIcon(new ImageIcon("pic/5675671.png"));
		getContentPane().add(backButton1);	
		
		
		/* 文字 */
		
		JLabel bill = new JLabel("异常:"+errmsg);
		bill.setFont(new Font("宋体", Font.BOLD, 30));
		bill.setBounds(559, 403, 515, 100);
		getContentPane().add(bill);
		JLabel billImage1 = new JLabel();   
		billImage1.setIcon(new ImageIcon("pic/5768765.png"));
		billImage1.setIconTextGap(6);
		billImage1.setBounds(411, 159, 740, 550);
		getContentPane().add(billImage1);
		
		/* 白色图片 */
		JLabel billImage2 = new JLabel();   
		billImage2.setIcon(new ImageIcon("pic/65768.png"));
		billImage2.setIconTextGap(6);
		billImage2.setBounds(349, 134, 740, 550);
		getContentPane().add(billImage2);
		
		ImageIcon img = new ImageIcon("pic/2343.png");//这是背景图片     
		img.setImage(img.getImage().getScaledInstance(1440, 900, Image.SCALE_DEFAULT));
		JLabel lblNewLabel = new JLabel(img);
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setBounds(0, 0, 1440, 900);
		lblNewLabel.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		getContentPane().add(lblNewLabel);
	}

	/***
	 * 退出
	 */
	private void scanBill1(){
		System.exit(1);
	}
}
