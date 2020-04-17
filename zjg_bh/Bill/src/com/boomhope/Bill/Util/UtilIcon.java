package com.boomhope.Bill.Util;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.Icon;

/***
 * 封装可缩放icon类
 * @author shaopeng
 *
 */
public class UtilIcon implements Icon{ 
    private Icon icon = null; 

    public UtilIcon(Icon icon){ 
    	this.icon = icon; 
    } 

    public int getIconHeight(){ 
    	return icon.getIconHeight(); 
	} 

	public int getIconWidth(){ 
		return icon.getIconWidth(); 
	} 

	@Override
	public void paintIcon(Component c, Graphics g, int x, int y) {
		float wid = c.getWidth(); 
		float hei = c.getHeight(); 
		int iconWid = icon.getIconWidth(); 
        int iconHei = icon.getIconHeight(); 

        Graphics2D g2d = (Graphics2D) g; 
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR); 
        g2d.scale(wid/iconWid,   hei/iconHei); 
        icon.paintIcon(c, g2d, 0, 0); 
	} 
} 