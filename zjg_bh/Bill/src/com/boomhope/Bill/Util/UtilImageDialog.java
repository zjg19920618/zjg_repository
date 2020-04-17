package com.boomhope.Bill.Util;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;

/***
 * 放大显示图片
 * @author shaopeng
 *
 */
public class UtilImageDialog extends JDialog{
	
	private static final long serialVersionUID = 1L;
	private JLabel image;
	private int width = 900;
	private int height = 560;

	public UtilImageDialog(String imageFile){
		
		this.setSize(width, height);
		this.setLayout(null);
		this.setUndecorated(true);
		this.setLocation(460,175);
		

		image = new JLabel();
		image.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				/* 点击后退出 */
				closePanel();
			}
		});
		ImageIcon imageIcon = new ImageIcon(imageFile);
		imageIcon.getImage().flush();
		ImageIcon image1 = new ImageIcon(imageFile);
		image.setIcon(new UtilIcon(image1));
		image.setSize(930, 560);
		image.setLocation(-5, 0);
		add(image);
	}
	
	/***
	 * 关闭对话框
	 */
	private void closePanel(){
		this.dispose();
	}

}

