package com.boomhope.Bill.Util;

import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/***
 * 封装图片类
 * @author shaopeng
 *
 */
public class UtilImage extends JLabel{
	

	private static final long serialVersionUID = 1L;

	String imageFile;
	public String flag = "";
	
	public UtilImage(String imageFile){
		this.imageFile = imageFile;
		ImageIcon image = new ImageIcon(imageFile);
		this.setIcon(image);
		image.setImage(image.getImage().getScaledInstance(420, 257, Image.SCALE_DEFAULT));
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				flag = "Y";
				/* 点击后显示放大图片 */
				showImage();
			}
		});
	}
	
	/***
	 * 显示放大图片
	 */
	private void showImage(){
		UtilImageDialog imageDialog = new UtilImageDialog(imageFile);
		imageDialog.setModal(true);
		imageDialog.setVisible(true);
		
	}

}
