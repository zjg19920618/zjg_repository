package com.boomhope.Bill.Util;

import java.awt.Image;
import java.awt.event.MouseAdapter;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class UtilImages1 extends JLabel{

	private static final long serialVersionUID = 1L;

	String imageFile;
	public String flag = "";
	
	public UtilImages1(String imageFile){
		this.imageFile = imageFile;
		ImageIcon image = new ImageIcon(imageFile);
		this.setIcon(image);
		image.setImage(image.getImage().getScaledInstance(250, 210, Image.SCALE_DEFAULT));
		this.addMouseListener(new MouseAdapter() {

		});
	}
	

}