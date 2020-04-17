package com.boomhope.Bill.Util;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;

/***
 * 封装Button类
 * @author shaopeng
 *
 */
public class UtilButton extends JButton{
	
	private static final long serialVersionUID = 1L;
	private String imageFile1;
	private String imageFile2;

	/***
	 * 初始化
	 */
	public UtilButton(String imageFile1, String imageFile2){
		this.imageFile1 = imageFile1;
		this.imageFile2 = imageFile2;

		this.setBackground(new Color(0, 0, 255));
		this.setOpaque(false);
		this.setBorderPainted(false);

		this.setIcon(new ImageIcon(this.imageFile1));
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				setIcon2();
			}
			@Override
			public void mouseExited(MouseEvent e) {
				setIcon1();
			}
		});
	}
	
	/***
	 * 设置按键图标
	 */
	private void setIcon1(){
		this.setIcon(new ImageIcon(this.imageFile1));
	}
	
	/***
	 * 设置按键按下后图标
	 */
	private void setIcon2(){
		this.setIcon(new ImageIcon(this.imageFile2));
	}

}
