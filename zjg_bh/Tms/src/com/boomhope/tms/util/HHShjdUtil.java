package com.boomhope.tms.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageDecoder;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * 
 * @author Administrator
 *
 */
public class HHShjdUtil {
	Logger logger = Logger.getLogger(HHShjdUtil.class);
	/**
	 * 查询次日转账状态
	 */
	public static Map<String,String> checkState(){
		return new HashMap<>();
	}
	
	/**
	 * 修改图片内容,到另一个路径
	 * @param svrJrnlNo  流水号
	 */
	public void updatePhotos(String oldPath,String newPath,String svrJrnlNo){
			InputStream input = null;
			OutputStream output = null;
		try {
			//源图片
			input = new FileInputStream(new File(oldPath));
			//通过JPEG图像流创建JPEG数据流解码器
			JPEGImageDecoder jpegDecoder =JPEGCodec.createJPEGDecoder(input);
			//解码当前JPEG数据流，返回BufferedImage
			BufferedImage buffImg = jpegDecoder.decodeAsBufferedImage();
			
			//得到画笔对象
			Graphics g = buffImg.getGraphics();
			g.setColor(Color.BLACK);
			g.setFont(new Font("楷体", Font.PLAIN, 18));
			//输出文字
			g.drawString(svrJrnlNo, 355, 28);
			
			//输出数据流
			output = new FileOutputStream(newPath);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(output);
			encoder.encode(buffImg);
			
		} catch (Exception e) {
			
			throw new RuntimeException(e);
			
		}finally{
			try {
				if(input!=null){
					input.close();
				}
				if(output!=null){
					output.close();
				}
			} catch (IOException e) {
				
				throw new RuntimeException(e);
				
			}
		}
	}
	
	public static void main(String[] args) {
		List<Object> list = new ArrayList<Object>();
		list.add("ZHFW21120105040012100204");
		list.add("ZHFW21120105040012100205");
		list.add("ZHFW21120105040012100207");
		list.add("ZHFW21120105040012100208");
		String	dateTime = DateUtil.getDateTime("yyyyMMdd");
		for (int i = 0; i < list.size(); i++) {
			String name = (String)list.get(i);
			String substring = dateTime.substring(0, 2);
			String substring2 = name.substring(4,10);
			String oldpath = "D:\\shjd\\tsyh\\" + substring + substring2+ "\\000004\\"+name+".jpg";
			String newpath = "D:\\ss\\"+name+".jpg";
			HHShjdUtil hp =new HHShjdUtil();
			hp.updatePhotos(oldpath, newpath, name);
		}
	}
}
