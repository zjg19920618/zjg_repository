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

import org.apache.log4j.Logger;

import com.jcraft.jsch.ChannelSftp;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
public class JpgUtils {

	
	BufferedImage image;
	
	static Logger logger = Logger.getLogger(JpgUtils.class);
	
	void createImage(String fileLocation) {
		
		try {
			File file = new File(fileLocation);
			if (!file.getParentFile().exists()){
				file.getParentFile().mkdirs();
			}
			if (file.exists()) {
				file.delete();
			}
			//file.createNewFile();
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
	 * agentFlag 1-存在代理人  2-不存在代理人
	 * svrJrnlNo 流水号
	 * proName 产品名称
	 * accName 户名
	 * branchNo 机构号
	 * transType 交易类型
	 * amount 交易金额
	 * accNo 账号
	 * billNo 凭证号
	 * supTellerNo 授权号
	 * macNo 终端号
	 * idCardName 本人姓名
	 * idCardNo 本人身份证号
	 * checkResult 本人核查结果
	 * qfjg 签发机关
	 * teller 操作员
	 * busType 业务类型
	 * agentIdCardName 代理人姓名
	 * agentIdCardNo 代理人卡号
	 * agentqfjg 代理人签发机关
	 * 
	 * @param map
	 * @throws IOException 
	 */
	public String graphicsGeneration(Map<String,String> map) throws IOException {
		logger.info("开始生成事后监管图片。");
		int imageWidth = 0;
		int imageHeight = 0;
		if("1".equals(map.get("agentFlag"))){
			// 存在代理人标识
			imageWidth = 1200;// 图片的宽度
//			imageHeight = 1900;// 图片的高度
			imageHeight = 1350;// 图片的高度
		}else if("2".equals(map.get("agentFlag"))){
			// 不存在代理人标识
			imageWidth = 1200;// 图片的宽度
//			imageHeight = 1520;// 图片的高度
			imageHeight = 1000;// 图片的高度
		}

		image = new BufferedImage(imageWidth, imageHeight,BufferedImage.TYPE_INT_RGB);
		logger.info("创建graphics对象-------------------");
		Graphics graphics = image.getGraphics();
		//设置背景颜色
		logger.info("设置背景颜色！");
		graphics.setColor(Color.WHITE);
		graphics.setFont(new Font("楷体", Font.PLAIN, 18));
		graphics.fillRect(0, 0, imageWidth, imageHeight);
		graphics.setColor(Color.BLACK);
		graphics.drawString("本人联网核查 " , 150, 170);
		graphics.drawString("本人身份证 " , 150, 310);
		logger.info("判断是否为代理人办理，1为代理人，2为不是代理人，agentFlag---》"+map.get("agentFlag"));
		if("1".equals(map.get("agentFlag"))){
			graphics.drawString("代理人联网核查 " , 150, 594);
			graphics.drawString("代理人身份证 " , 150, 704);
//			graphics.drawString("存单 " , 150, 959);
			graphics.drawString("现场拍照 " , 150, 959);
			graphics.drawString("人脸识别结果：通过" , 800, 1159);
		}else if("2".equals(map.get("agentFlag"))){
//			graphics.drawString("存单" , 150, 570);
			graphics.drawString("现场拍照" , 150, 570);
			graphics.drawString("人脸识别结果：通过" , 800, 770);
		}
		logger.info("向graphics对象中插入存单信息------");
//		graphics.drawString("存单" , 150, 959);
		graphics.drawString("流水号 : " + map.get("svrJrnlNo"), 275, 27);
		graphics.drawString("产品名称 : " + map.get("proName"), 675, 27);
		graphics.drawString("户名 : " + map.get("accName"), 275, 54);
		graphics.drawString("机构号: " + map.get("branchNo"), 675, 54);
		graphics.drawString("交易类型: " + map.get("transType"), 275, 81);
		graphics.drawString("终端号: " + map.get("macNo"), 675, 81);
		graphics.drawString("账号: " + map.get("accNo"), 275, 108);
		graphics.drawString("凭证号: " + map.get("billNo"), 675, 108);
//		graphics.drawString("终端号: " + map.get("macNo"), 275, 135);
//		graphics.drawString("终端号: " + map.get("macNo"), 675, 135);
		logger.info("向graphics对象中插入身份证相关信息");
		graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, 166);
		graphics.drawString(" ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", 268, 159);
		graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, 192);
		graphics.drawString("▏姓名               " + map.get("idCardName")+"                                                ", 275, 170);
		graphics.drawString(" ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", 268, 180);
		graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, 180);
		graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, 272);
		graphics.drawString("▏身份证号           " + map.get("idCardNo")+"                                                ", 275, 190);
		graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, 212);
		graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, 190);
		graphics.drawString(" ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", 268, 200);
		graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, 200);
		graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, 222);
		graphics.drawString("▏核查结果           公民身份号码与姓名一致，且存在照片                                                ", 275, 220);
		graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, 232);
		graphics.drawString(" ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", 268, 240);
		graphics.drawString("▏签发机关           " + map.get("qfjg")+"                                                ", 275, 260);
		graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, 249);
		graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, 265);
		graphics.drawString(" ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", 268, 280);
		graphics.drawString("操作员: " + map.get("teller"), 275, 290);
		graphics.drawString("业务类型: " + map.get("busType"), 575, 290);
		Date date=new Date();
		  DateFormat format=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		  String time=format.format(date);
		graphics.drawString("办理日期: " + time, 800, 290);
		
		if("1".equals(map.get("agentFlag"))){
			logger.info("向graphics对象中插入，如果为代理人办理，继续插入身份证相关信息-------");
			graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, 580);
			graphics.drawString(" ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", 268, 573);
			graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, 606);
			graphics.drawString("▏姓名               " + map.get("agentIdCardName")+"                                                ", 275, 584);
			graphics.drawString(" ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", 268, 594);
			graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, 594);
			graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, 686);
			graphics.drawString("▏身份证号           " + map.get("agentIdCardNo")+"                                                ", 275, 604);
			graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, 626);
			graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, 604);
			graphics.drawString(" ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", 268, 614);
			graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, 614);
			graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, 636);
			graphics.drawString("▏核查结果           公民身份号码与姓名一致，且存在照片                                                ", 275, 634);
			graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, 646);
			graphics.drawString(" ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", 268, 654);
			graphics.drawString("▏签发机关           " + map.get("agentqfjg")+"                                                ", 275, 674);
			graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, 663);
			graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, 679);
			graphics.drawString(" ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", 268, 694);
		}else 
			if("2".equals(map.get("agentFlag"))){
		}

		// 改成这样:
		BufferedImage bimg = null;
		BufferedImage bimg1 = null;
		BufferedImage bimg2 = null;
		BufferedImage bimg3 = null;
		BufferedImage bimg4 = null;
		BufferedImage bimg5 = null;
		BufferedImage bimg6 = null;
		BufferedImage bimg7 = null;
		BufferedImage bimg8 = null;
//		BufferedImage bimg9 = null;
			logger.info("开始读取各种照片---------------");
		
			if(!Property.getProperties().getProperty("bill_id_self_just").equals("") || Property.getProperties().getProperty("bill_id_self_just").equals(null)){
				logger.info("本人身份证正面处理");
				//本人身份证正面
				bimg = javax.imageio.ImageIO.read(new java.io.File(Property.getProperties().getProperty("bill_id_self_just")));
			}if(!Property.getProperties().getProperty("id_card_self").equals("") || Property.getProperties().getProperty("id_card_self").equals(null)){
				logger.info("本人照本处理");
				//本人照片
				bimg2 = javax.imageio.ImageIO.read(new java.io.File(Property.getProperties().getProperty("id_card_self")));
			}if(!Property.getProperties().getProperty("bill_id_self_against").equals("") || Property.getProperties().getProperty("bill_id_self_against").equals(null)){
				logger.info("本人身份证反面处理");
				//本人身份证反面
				bimg1 = javax.imageio.ImageIO.read(new java.io.File(Property.getProperties().getProperty("bill_id_self_against")));
			}
			
			
//			String bill_face = map.get("bill_face");
//			if(bill_face != null){
//				logger.info("存单正面处理"+bill_face);
//				//存单正面
//				bimg3 = javax.imageio.ImageIO.read(new java.io.File(bill_face));
//			}
			
		
			if("1".equals(map.get("agentFlag"))){
				if(!Property.getProperties().getProperty("id_card_agent").equals("") || Property.getProperties().getProperty("id_card_agent").equals(null)){
					logger.info("代理人照片处理");
					//代理人照片
					bimg4 = javax.imageio.ImageIO.read(new java.io.File(Property.getProperties().getProperty("id_card_agent")));
				}
				if(!Property.getProperties().getProperty("bill_id_agent_just").equals("") || Property.getProperties().getProperty("bill_id_agent_just").equals(null)){
					logger.info("代理人身份证正面处理");
					//代理人身份证正面
					bimg5 = javax.imageio.ImageIO.read(new java.io.File(Property.getProperties().getProperty("bill_id_agent_just")));
				}if(!Property.getProperties().getProperty("bill_id_agent_against").equals("") || Property.getProperties().getProperty("bill_id_agent_against").equals(null)){
					//代理人身份证反面
					bimg6 = javax.imageio.ImageIO.read(new java.io.File(Property.getProperties().getProperty("bill_id_agent_against")));
				}
			}
			
			if(!Property.getProperties().getProperty("camera_path").equals("") || Property.getProperties().getProperty("camera_path").equals(null)){
				logger.info("办理人现场照处理");
				//本人现场照
				bimg8 = javax.imageio.ImageIO.read(new java.io.File(Property.getProperties().getProperty("camera_path")));
			}
			logger.info("读取各种照片结束-------");
			logger.info("开始插入各种照片-------");
			
		if (bimg != null)
			//本人身份证
		graphics.drawImage(bimg, 270, 300, 400,250, null);		
		graphics.drawImage(bimg1, 675, 300, 400,250, null);
		//照片
		graphics.drawImage(bimg2, 950, 155, 100,115, null);
		//代理人照片
		if("1".equals(map.get("agentFlag"))){
			graphics.drawImage(bimg4, 950, 570, 100,115, null);
		}else 
			if("2".equals(map.get("agentFlag"))){
		}

		//代理人身份证
		if("1".equals(map.get("agentFlag"))){
			graphics.drawImage(bimg5, 270, 694, 400,250, null);
			graphics.drawImage(bimg6, 675, 694, 400,250, null);
		}else 
			if("2".equals(map.get("agentFlag"))){
		}
		
		
        //拍摄照片
		if("1".equals(map.get("agentFlag"))){
			graphics.drawImage(bimg8, 270, 949, 400,400, null);
		}else 
			if("2".equals(map.get("agentFlag"))){
			graphics.drawImage(bimg8, 270, 560, 400,400, null);
		}
		logger.info("插入各种照片结束-----------");
		graphics.dispose();
		//图片名为身份证加业务类型
		String nowDate = DateUtil.getNowDate("yyyyMMdd");
		logger.info("获取今天日期-----》"+nowDate);
		
		// 8位日期+流水号
		String fileName = nowDate+map.get("svrJrnlNo").trim();
		logger.info("获取日期和canelBillId的fileName值---->" + fileName);
		String filepath = Property.FTP_LOCAL_PATH+fileName+".jpg";
		logger.info("获取本地生成的图片路径--->"+filepath);
		createImage(filepath);
		return filepath;
	}



	public static void main(String[] args) throws IOException {
		Property.initProperty();
		Map <String,String> map = new HashMap<String,String>();
		map.put("agentFlag", "2");// 1-存在代理人  2-不存在代理人
		map.put("svrJrnlNo", "1234");//  流水号
		map.put("canelBillDTransDate", "20160923");//  销户日期
		map.put("accName", "赵宇");//  户名
		map.put("branchNo", "C0010");//  机构号
		map.put("transType", "存单销户");//  交易类型
		map.put("amount", "100.00");//  交易金额
		map.put("accNo", "052000125001000346330");//  账号
		map.put("billNo", "03826833");//  凭证号
		map.put("supTellerNo", "A0043");// 授权号
		map.put("macNo", "000C001");// 终端号
		map.put("idCardName", "张三");//  本人姓名
		map.put("idCardNo", "220203198501125416");// 本人身份证号
		map.put("qfjg", "吉林");// 签发机关
		map.put("teller", "100100");// 操作员
		map.put("busType", "存单销户");// 业务类型
		map.put("agentIdCardName", "李四");// 代理人姓名
		map.put("agentIdCardNo", "11111111111111111");// 代理人卡号
		map.put("agentqfjg", "北京");// 代理人签发机关
		ChannelSftp sftp = null;
		String nowDate = DateUtil.getNowDate("yyyyMMdd");
		String ftpPath = "/home/abc/"+nowDate;
			JpgUtils cg = new JpgUtils();
			cg.graphicsGeneration(map);
//			SFTPUtil sf = new SFTPUtil();
//			//ChannelSftp sftp = sf.connect(Property.FTP_IP, Integer.parseInt(Property.FTP_PORT),Property.FTP_USER, Property.FTP_PWD);
//			try {
//				sftp = sf.connect("198.1.245.93", 22,"root","rootroot");
//			} catch (JSchException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//			// sf.download(directory, downloadFile, saveFile, sftp);
//			// sf.delete(directory, deleteFile, sftp);
//			
//
//			
//			Vector content = null;
//			try {
//				content = sftp.ls(ftpPath);
//			} catch (SftpException e) {
//				e.printStackTrace();
//			} 
//		    if(content == null) { 
//		    	try {
//					sftp.mkdir(ftpPath);
//					sftp.cd(ftpPath);
//				} catch (SftpException e) {
//					try {
//						sftp.mkdir(ftpPath);
//						sftp.cd(ftpPath);
//					} catch (SftpException e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					}
//				} 
//		    } 
//		    
//		    sf.upload("/home/abc/20160925", "d:\\bill\\201609231234.jpg", sftp);
//		   
//		    sftp.disconnect();
		
	
	}
	
	


}
