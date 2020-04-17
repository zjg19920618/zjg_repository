package com.boomhope.Bill.TransService.AccOpen.Util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.log4j.Logger;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 图片与base64之间转换
 * @author zy
 *
 */
public class Base64Util {
	
	static Logger logger  = Logger.getLogger(Base64Util.class);
	
	public static void main(String[] args) throws Exception {
		String file = "D:\\agent.bmp";
		String strImg = GetImageStr(file);
		System.out.println(strImg);
		GenerateImage(strImg, strImg);
	}

	/**
	 * 图片转化成base64字符串 
	 * @param file
	 * @return
	 * @throws Exception 
	 */
	public static String GetImageStr(String file) throws Exception {
		// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
		String imgFile = file;// 待处理的图片
		InputStream in = null;
		byte[] data = null;
		// 读取图片字节数组
		try {
			in = new FileInputStream(imgFile);
			data = new byte[in.available()];
			in.read(data);
		} catch (IOException e) {
			logger.error(e);
			throw new Exception(e);
		}finally{
			if(in != null){
				try {
					in.close();
				} catch (IOException e) {
					logger.error(e);
				}
			}
		}
		// 对字节数组Base64编码
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(data);
		// 返回Base64编码过的字节数组字符串
	}

	// base64字符串转化成图片
	public static boolean GenerateImage(String imgStr,String imgFilePath) { // 对字节数组字符串进行Base64解码并生成图片
		if (imgStr == null) // 图像数据为空
			return false;
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			// Base64解码
			byte[] b = decoder.decodeBuffer(imgStr);
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {// 调整异常数据
					b[i] += 256;
				}
			}
			// 生成jpeg图片
			OutputStream out = new FileOutputStream(imgFilePath);
			out.write(b);
			out.flush();
			out.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}