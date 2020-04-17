package com.boomhope.Bill.Util;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
 
import javax.imageio.ImageIO;

/**
 * 截屏工具
 * @author zy
 *
 */
public class ScreenshotUtil {  
	
	/**
	 * 截图方法
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 * @param path图片保存路径
	 * @throws AWTException
	 * @throws IOException
	 */
	public static void Screenshot(int x,int y,int w,int h,String path) throws AWTException, IOException{
		int width = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();  //要截取的宽度
        int height = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();  //要截取的高度
        Robot robot = new Robot();  
        BufferedImage image = robot.createScreenCapture(new Rectangle(width,height));  
        image = image.getSubimage(x, y, w, h);
        ImageIO.write (image, "png" , new File(path));   
	}
       
    public static void main(String[] args) {
        
    }  
}  