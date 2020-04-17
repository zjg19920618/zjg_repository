package com.boomhope.Bill.Util;



import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/*
 * 生成JPG工具类
 */
public class JpgUtil_HY {
	
	BufferedImage image;
	
	static Logger logger = Logger.getLogger(JpgUtil_HY.class);
	
	void createImage(String fileLocation) {
		
		try {
			File file = new File(fileLocation);
			if (!file.getParentFile().exists()){
				file.getParentFile().mkdirs();
			}
			if (file.exists()) {
				file.delete();
			}
			FileOutputStream fos = new FileOutputStream(file);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(bos);
			encoder.encode(image);
			bos.close();
		} catch (Exception e) {
			logger.error(e);
		}
	}

	/**
	 * money 汇款金额
	 * billId 核心流水号
	 * transDate 核心日期
	 * chuName 出账户名
	 * chuNo 出账账号
	 * chuBank 出账开户行
	 * ruName 入账户名
	 * ruNo 入账帐号
	 * ruBank 入账开户行
	 * idCardName 本人姓名
	 * idCardNo 本人身份证号
	 * tellerNo 授权柜员
	 * checkResult 本人核查结果
	 * qfjg 签发机关
	 * use 汇款用途
	 * branchNo 机构号
	 * macNo 终端号
	 * 
	 * @param map
	 * @throws IOException 
	 */
	public String graphicsGeneration(Map<String,String> map) throws IOException {
		logger.info("开始生成事后监管图片。");
		int	imageWidth = 1200;// 图片的宽度
		int	imageHeight = 1500;// 图片的高度
		
		int y = 27;
		
		image = new BufferedImage(imageWidth, imageHeight,BufferedImage.TYPE_INT_RGB);
		logger.info("创建graphics对象-------------------");
		Graphics graphics = image.getGraphics();
		
		
		//设置背景颜色
		logger.info("设置背景颜色！");
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, imageWidth, imageHeight);
		graphics.setColor(Color.BLACK);
		graphics.setFont(new Font("楷体", Font.BOLD, 40));
		int stringWidth = graphics.getFontMetrics(new Font("楷体", Font.BOLD, 40)).stringWidth("汇划转账确认书");
		graphics.drawString("汇划转账确认书",  (imageWidth-stringWidth)/2, 50);
		graphics.setFont(new Font("楷体", Font.PLAIN, 18));
		graphics.drawString("交易信息 " , 150, 150);
		graphics.drawString("本人联网核查 " , 150, 354);
		graphics.drawString("本人身份证 " , 150, 480);
		graphics.drawString("现场拍照" , 150, 830);
		graphics.drawString("人脸识别结果："+map.get("sec") , 800, 830);
		graphics.drawString("客户签字" , 150, 1250);	
		
		logger.info("向graphics对象中交易信息------");
		graphics.drawString("流水号 : " + map.get("billId"), 275, 50+y);
		graphics.drawString("交易时间 : " + map.get("transDate"), 675, 50+y);
		graphics.drawString("转出户名 : " + map.get("chuName"), 275, 50+2*y);
		graphics.drawString("转入户名: " + map.get("ruName"), 675, 50+2*y);
		graphics.drawString("转出账号: " + map.get("chuNo"), 275, 50+3*y);
		graphics.drawString("转入帐号: " + map.get("ruNo"), 675, 50+3*y);
		graphics.drawString("机构号: " + map.get("branchNo"), 275, 50+4*y);
		graphics.drawString("终端号: " + map.get("macNo"), 675, 50+4*y);
		graphics.drawString("汇款用途: " + map.get("use"), 275, 50+5*y);
		graphics.drawString("汇款金额: " + map.get("money"), 675, 50+5*y);
		graphics.drawString("授权员一: " + map.get("tellerNo"), 275, 50+6*y);
		if(map.get("tellerNo2")!=null && !"".equals(map.get("tellerNo2"))){
			graphics.drawString("授权员二: " + map.get("tellerNo2"), 675, 50+6*y);
		}
		graphics.drawString("到账方式: " + map.get("transMethod"), 275, 50+7*y);
		graphics.drawString("转出开户行: " + map.get("chuBank"), 275, 50+8*y);
		graphics.drawString("转入开户行: " + map.get("ruBank"), 275, 50+9*y);
		logger.info("向graphics对象中插入身份证相关信息");
		Date date=new Date();
		DateFormat format=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String time=format.format(date);
		graphics.drawString("核查时间: " + time, 800, 321);
		graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, 341);
		graphics.drawString(" ━━━━━━━━━━━━━━━━━━━━━━━━━O━━━━━━━━━━━━━━━━━━━", 268, 334);
		graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, 367);
		graphics.drawString("▏姓名               " + map.get("idCardName")+"                                                ", 275, 345);
		graphics.drawString(" ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", 268, 355);
		graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, 355);
		graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, 447);
		graphics.drawString("▏身份证号           " + map.get("idCardNo")+"                                                ", 275, 366);
		graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, 377);
		graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, 355);
		graphics.drawString(" ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", 268, 375);
		graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, 375);
		graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, 397);
		graphics.drawString("▏核查结果           公民身份号码与姓名一致，且存在照片                                                ", 275, 388);
		graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, 387);
		graphics.drawString(" ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", 268, 405);
		graphics.drawString("▏签发机关           " + map.get("qfjg")+"                                                ", 275, 435);
		graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, 425);
		graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, 440);
		graphics.drawString(" ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", 268, 455);
			
		
		
		// 改成这样:
		BufferedImage bimg = null;
		BufferedImage bimg1 = null;
		BufferedImage bimg2 = null;
		BufferedImage bimg3 = null;
		BufferedImage bimg4 = null;
		logger.info("开始读取各种照片---------------");
	
		if(!"".equals(Property.getProperties().getProperty("bill_id_self_just")) || Property.getProperties().getProperty("bill_id_self_just")!=null){
			logger.info("本人身份证正面处理");
			//本人身份证正面
			bimg = javax.imageio.ImageIO.read(new java.io.File(Property.getProperties().getProperty("bill_id_self_just")));
		}if(!"".equals(Property.getProperties().getProperty("bill_id_self_face")) || Property.getProperties().getProperty("bill_id_self_face")!=null){
			logger.info("本人照片处理");
			//本人照片
			bimg2 = javax.imageio.ImageIO.read(new java.io.File(Property.getProperties().getProperty("id_card_self")));
		}if(!Property.getProperties().getProperty("bill_id_self_against").equals("") || Property.getProperties().getProperty("bill_id_self_against").equals(null)){
			logger.info("本人身份证反面处理");
			//本人身份证反面
			bimg1 = javax.imageio.ImageIO.read(new java.io.File(Property.getProperties().getProperty("bill_id_self_against")));
		}
		if(!Property.getProperties().getProperty("camera_path").equals("") || Property.getProperties().getProperty("camera_path").equals(null)){
			logger.info("本人现场照处理");
			//本人现场照
			bimg3 = javax.imageio.ImageIO.read(new java.io.File(Property.getProperties().getProperty("camera_path")));
		}
		if(!Property.getProperties().getProperty("SIGNATURE_PATH").equals("") || Property.getProperties().getProperty("SIGNATURE_PATH").equals(null)){
			logger.info("客户签字");
			//客户签字
			bimg4 = javax.imageio.ImageIO.read(new java.io.File(Property.getProperties().getProperty("SIGNATURE_PATH")));
		}	
		
		logger.info("读取各种照片结束-------");
		logger.info("开始插入各种照片-------");
		
		//本人身份证
		graphics.drawImage(bimg, 270, 451, 400,250, null);		
		graphics.drawImage(bimg1, 675, 451, 400,250, null);
		//照片
		graphics.drawImage(bimg2, 950, 331, 100,115, null);
		//现场拍照
		graphics.drawImage(bimg3, 270, 751, 400,400, null);
		//客户签字
		graphics.drawImage(bimg4, 270, 1104, 780,350, null);	
		
		logger.info("插入各种照片结束-----------");
		graphics.dispose();
		//图片名为身份证加业务类型
		logger.info("获取本地生成的图片路径--->"+map.get("jpgName"));
		createImage(map.get("jpgName"));
		return map.get("jpgName");
	}



	public static void main(String[] args) throws IOException {
		Property.initProperty();
		Map <String,String> map = new HashMap<String,String>();
		
		map.put("money", "50000");//交易金额
		map.put("billId", "1234");// 核心流水号
		map.put("transDate", "20160923");//  日期
		map.put("branchNo", "C0010");//  机构号
		map.put("chuNo", "052000125001000346330");//  账号
		map.put("ruNo", "052000125001000346330");//  账号
		map.put("tellerNo", "A0043");// 授权号
		map.put("macNo", "000C001");// 终端号
		map.put("idCardName", "张三");//  本人姓名
		map.put("idCardNo", "220203198501125416");// 本人身份证号
		map.put("qfjg", "吉林");// 签发机关
		map.put("sec", "人脸相似度为98.26%");
		map.put("transMethod", "次日到账");
		map.put("jpgName", "D://bill/20170512.jpg");
		ChannelSftp sftp = null;
		String nowDate = DateUtil.getNowDate("yyyyMMdd");
		String ftpPath = "/home/abc/"+nowDate;
		JpgUtil_HY jp=new JpgUtil_HY();
		jp.graphicsGeneration(map);
	
	}
	
}
