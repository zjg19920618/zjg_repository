package com.boomhope.Bill.Util;



import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/*
 * 生成JPG工具类
 */
public class JpgUtil_CardKH {
	
	BufferedImage image;
	
	static Logger logger = Logger.getLogger(JpgUtil_CardKH.class);
	
	void createImage(String fileLocation) {
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		try {
			File file = new File(fileLocation);
			if (!file.getParentFile().exists()){
				file.getParentFile().mkdirs();
			}
			if (file.exists()) {
				file.delete();
			}
			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(bos);
			encoder.encode(image);
			bos.close();
		} catch (Exception e) {
			logger.error(e);
			throw new RuntimeException(e);
		}finally{
			if(fos!=null){
				
				try {
					fos.close();
				} catch (IOException e) {
					logger.error(e);
				}
			}
			if(bos!=null){
				try {
					bos.close();
				} catch (IOException e) {
					logger.error(e);
				}
			}
		}
	}

	/**
	 * 生成事后监督图片
	 * @param map
	 * @throws IOException 
	 */
	public String graphicsGeneration(Map<String,String> map) throws Exception {
		logger.info("开始生成销户事后监管图片。");
		int imageWidth = 0;
		int imageHeight = 0;
		int y=0;//各个元素的纵坐标
		int lastY=0;//基本信息最后一个字段的纵坐标
		if("0".equals(map.get("haveAgentFlag"))){
			// 存在代理人标识
			imageWidth = 1200;// 图片的宽度
			imageHeight = 2300;// 图片的高度
		}else if("1".equals(map.get("haveAgentFlag"))){
			// 不存在代理人标识
			imageWidth = 1200;// 图片的宽度
			imageHeight = 1850;// 图片的高度
		}else{
			imageWidth = 1200;// 图片的宽度
			imageHeight = 1000;// 图片的高度
		}

		image = new BufferedImage(imageWidth, imageHeight,BufferedImage.TYPE_INT_RGB);
		logger.info("创建graphics对象-------------------");
		Graphics graphics = image.getGraphics();
		//设置背景颜色
		logger.info("设置背景颜色！");
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, imageWidth, imageHeight);
		graphics.setColor(Color.BLACK);
		graphics.setFont(new Font("楷体", Font.BOLD, 40));
		int stringWidth = graphics.getFontMetrics(new Font("楷体", Font.BOLD, 40)).stringWidth("银行卡-子账户开户确认书");
		graphics.drawString("银行卡-子账户开户确认书",  (imageWidth-stringWidth)/2, 50);
		
		graphics.setFont(new Font("楷体", Font.PLAIN, 18));
		
		logger.info("向graphics对象中插入存单信息------");
		y=77;
		graphics.drawString("交易类型: " + map.get("transType"), 275, y);
		graphics.drawString("交易时间: " + map.get("accSvrDate"), 275,y+=27);
		graphics.drawString("凭证号: " + map.get("billNo"), 275,y+=27);
		graphics.drawString("转存标志: " + map.get("exchFlag"), 275,y+=27);
		graphics.drawString("存期: " + map.get("depTerm"), 275,y+=27);
		graphics.drawString("机构号: " + map.get("branchNo"), 275, y+=27);
		graphics.drawString("柜员号: " + map.get("tellerNo"), 275, y+=27);
		graphics.drawString("终端号: " + map.get("macNo"), 275, y+=27);
		graphics.drawString("授权员: " + map.get("supTellerNo"), 275, y+=27);
		if(!"".equals(map.get("inputName").trim())){
			graphics.drawString(map.get("inputName"), 275, y+=27);
			graphics.drawString(map.get("inputNo"), 275, y+=27);
		}else{
			
		}
		lastY=y;
		
		y=77;
		graphics.drawString("户名 : " + map.get("accName"), 675, y);
		graphics.drawString("账号: " + map.get("accCardNo"), 675, y+=27);
		graphics.drawString("产品名称: " + map.get("productName"), 675, y+=27);
		graphics.drawString("产品代码: " + map.get("productCode"), 675, y+=27);
		graphics.drawString("起息日: " + map.get("startDate"), 675, y+=27);
		graphics.drawString("到期日: " + map.get("endDate"), 675, y+=27);
		graphics.drawString("开户利率: " + map.get("openRate"), 675, y+=27);
		graphics.drawString("开户金额: " + map.get("amount").trim()+"元", 675, y+=27);
		graphics.drawString("开户流水号 : " + map.get("accSvrNo"), 675, y+=27);
		if(map.get("productCode").startsWith("JX") ||map.get("productCode").startsWith("RJ")){
			graphics.drawString("关联账号 : " + map.get("realAccNo"), 275, y+=27);
		}
		
		if(map.get("tangDDFSvrNo")==null || "".equals(map.get("tangDDFSvrNo"))){
			if(map.get("intMoney")!=null && !"".equals(map.get("intMoney"))){
				
				graphics.drawString("幸运豆兑付金额:"+map.get("intMoney").trim()+"元", 675, y+=27);
			}else{
				
			}
		}else{
			if(map.get("intMoney")!=null && !"".equals(map.get("intMoney"))){
				
				graphics.drawString("唐豆兑付金额："+map.get("tangDDFAmt").trim()+"元", 675, y+=27);
				graphics.drawString("唐豆兑付流水号："+map.get("tangDDFSvrNo"), 675, y+=27);
				graphics.drawString("幸运豆兑付金额:"+map.get("intMoney").trim()+"元", 675, y+=27);
			}else{
				graphics.drawString("唐豆兑付金额："+map.get("tangDDFAmt").trim()+"元", 675, y+=27);
				graphics.drawString("唐豆兑付流水号："+map.get("tangDDFSvrNo"), 675, y+=27);
			}
		}
		if(map.get("zydCount")!=null && !"".equals(map.get("zydCount"))){
			graphics.drawString("增益豆金额："+map.get("zydCount").trim()+"元", 675, y+=27);
		}
		if(map.get("xfdCount")!=null && !"".equals(map.get("xfdCount"))){
			graphics.drawString("消费豆数量："+map.get("xfdCount").trim()+"个", 675, y+=27);
		}
		
		//判断两列基础信息的长度，重新定位纵坐标。
		if(y<lastY){
			y=lastY;
		}
		
		BufferedImage bimg = null;
		BufferedImage bimg1 = null;
		BufferedImage bimg2 = null;
		BufferedImage bimg3 = null;
		BufferedImage bimg4 = null;
		BufferedImage bimg5 = null;
		BufferedImage bimg6 = null;
		BufferedImage bimg7 = null;
		BufferedImage bimg8 = null;
		BufferedImage bimg9 = null;
		BufferedImage bimg10 = null;
		
		// 存在代理人标识
		if("0".equals(map.get("haveAgentFlag"))){
			//本人联网信息
			graphics.drawString("本人身份信息: ", 275, y+=24);//398
			graphics.drawString(" ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", 268, y+20);//418
			graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275,y+27);//425
			graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, y+53);//451
			graphics.drawString("▏姓名               " + map.get("idCardName")+"                                                ", 275, y+31);//429
			graphics.drawString(" ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", 268, y+41);//439
			graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, y+41);//439
			graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, y+133);//531
			graphics.drawString("▏身份证号           " + map.get("idCardNo")+"                                                ", 275, y+51);//449
			graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, y+73);//471
			graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, y+51);//449
			graphics.drawString(" ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", 268, y+61);//459
			graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, y+61);//459
			graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, y+83);//481
			graphics.drawString("▏核查结果           公民身份号码与姓名一致，且存在照片                                                ", 275, y+81);//479
			graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, y+93);//491
			graphics.drawString(" ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", 268, y+101);//499
			graphics.drawString("▏签发机关           " + map.get("qfjg")+"                                                ", 275, y+121);//519
			graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, y+110);//508
			graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, y+126);//524
			graphics.drawString(" ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", 268, y+141);//539
			
			//本人证件照
			if(!"".equals(Property.getProperties().getProperty("bill_id_self_just")) && Property.getProperties().getProperty("bill_id_self_just")!=null){
				logger.info("本人身份证正面处理");
				//本人身份证正面
				bimg = javax.imageio.ImageIO.read(new java.io.File(Property.getProperties().getProperty("bill_id_self_just")));
			}if(!"".equals(map.get("selfPhotoPath")) && map.get("selfPhotoPath")!=null){
				logger.info("本人照本处理");
				//本人照片
				bimg2 = javax.imageio.ImageIO.read(new java.io.File(map.get("selfPhotoPath")));
			}if(!"".equals(Property.getProperties().getProperty("bill_id_self_against")) && Property.getProperties().getProperty("bill_id_self_against")!=null){
				logger.info("本人身份证反面处理");
				//本人身份证反面
				bimg1 = javax.imageio.ImageIO.read(new java.io.File(Property.getProperties().getProperty("bill_id_self_against")));
			}
			//本人照片
			graphics.drawImage(bimg2, 950, y+17, 100,115, null);//415
			//本人身份证
			graphics.drawImage(bimg, 270, y+157, 400,250, null);//555		
			graphics.drawImage(bimg1, 675, y+157, 400,250, null);//555
			
			//代理人联网信息
			graphics.drawString("代理人身份信息: ", 275, y+=451);//849
			graphics.drawString(" ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", 268, y+20);//869
			graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, y+27);//876
			graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, y+53);//902
			graphics.drawString("▏姓名               " + map.get("agentIdCardName")+"                                                ", 275, y+31);//880
			graphics.drawString(" ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", 268, y+41);//890
			graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, y+41);//890
			graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, y+133);//982
			graphics.drawString("▏身份证号           " + map.get("agentIdCardNo")+"                                                ", 275, y+51);//900
			graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, y+73);//922
			graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, y+51);//900
			graphics.drawString(" ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", 268, y+61);//910
			graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, y+61);//910
			graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, y+83);//932
			graphics.drawString("▏核查结果           公民身份号码与姓名一致，且存在照片                                                ", 275, y+81);//930
			graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, y+82);//941
			graphics.drawString(" ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", 268, y+101);//950
			graphics.drawString("▏签发机关           " + map.get("agentqfjg")+"                                                ", 275, y+121);//970
			graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, y+110);//959
			graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, y+126);//975
			graphics.drawString(" ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", 268, y+141);//
			
			
			//代理人证件照
			if(!"".equals(map.get("agentPhotoPath")) && map.get("agentPhotoPath")!=null){
				logger.info("代理人照片处理");
				//代理人照片
				bimg4 = javax.imageio.ImageIO.read(new java.io.File(map.get("agentPhotoPath")));
			}
			if(!"".equals(Property.getProperties().getProperty("bill_id_agent_just")) && Property.getProperties().getProperty("bill_id_agent_just")!=null){
				logger.info("代理人身份证正面处理");
				//代理人身份证正面
				bimg5 = javax.imageio.ImageIO.read(new java.io.File(Property.getProperties().getProperty("bill_id_agent_just")));
			}if(!"".equals(Property.getProperties().getProperty("bill_id_agent_against")) && Property.getProperties().getProperty("bill_id_agent_against")!=null){
				//代理人身份证反面
				bimg6 = javax.imageio.ImageIO.read(new java.io.File(Property.getProperties().getProperty("bill_id_agent_against")));
			}
			//代理人照片
			graphics.drawImage(bimg4, 950, y+17, 100,115, null);//866
			//代理人身份证
			graphics.drawImage(bimg5, 270, y+157, 400,250, null);//1006
			graphics.drawImage(bimg6, 675, y+157, 400,250, null);//1006
			
			//现场拍照及电子签名
			String property = Property.getProperties().getProperty("camera_path");
			if(!"".equals(property) && property!=null){
				//现场拍照
				bimg8 = javax.imageio.ImageIO.read(new java.io.File(property));
			}
			String property2 = Property.getProperties().getProperty("SIGNATURE_PATH");
			if(!"".equals(property2) && property2!=null){
				//签字图片
				bimg9 = javax.imageio.ImageIO.read(new java.io.File(property2));
			}
			String property3 = Property.getProperties().getProperty("dzyz_ml");
			if(!"".equals(property3) && property3!=null){
				//电子印章图片
				bimg10 = javax.imageio.ImageIO.read(new java.io.File(property3));
			}
			
			
			
			//现场照
			graphics.drawString("现场拍照: ", 275, y+=451);//1285
			graphics.drawImage(bimg8, 270, y+10, 440,280, null);//1295
			if(!"0010".equals(map.get("productCode"))){
				graphics.drawString("客户声明：本人已经阅知并同意遵守存款产品协议书的相关约定，确认业务办理内容与申请内容一致。",  275, y+=320);
				graphics.drawString("电子签名和电子印章: ", 275, y+30);//1600
				//电子签名图片
				graphics.drawImage(bimg9, 270, y+43, 600,250, null);//1613
				//电子印章图片
				graphics.drawImage(bimg10, 875, y+65, 200,200, null);//1635
			}else{
				graphics.drawString("电子签名和电子印章: ", 275, y+=315);//1600
				//电子签名图片
				graphics.drawImage(bimg9, 270, y+13, 600,250, null);//1613
				//电子印章图片
				graphics.drawImage(bimg10, 875, y+35, 200,200, null);//1635
			}
			
		// 不存在代理人标识
		}else if("1".equals(map.get("haveAgentFlag"))){
			//本人联网信息
			graphics.drawString("本人身份信息: ", 275, y+=24);//398
			graphics.drawString(" ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", 268, y+20);//418
			graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275,y+27);//425
			graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, y+53);//451
			graphics.drawString("▏姓名               " + map.get("idCardName")+"                                                ", 275, y+31);//429
			graphics.drawString(" ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", 268, y+41);//439
			graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, y+41);//439
			graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, y+133);//531
			graphics.drawString("▏身份证号           " + map.get("idCardNo")+"                                                ", 275, y+51);//449
			graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, y+73);//471
			graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, y+51);//449
			graphics.drawString(" ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", 268, y+61);//459
			graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, y+61);//459
			graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, y+83);//481
			graphics.drawString("▏核查结果           公民身份号码与姓名一致，且存在照片                                                ", 275, y+81);//479
			graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, y+93);//491
			graphics.drawString(" ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", 268, y+101);//499
			graphics.drawString("▏签发机关           " + map.get("qfjg")+"                                                ", 275, y+121);//519
			graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, y+110);//508
			graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, y+126);//524
			graphics.drawString(" ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", 268, y+141);//539
			
			//本人证件照
			if(!"".equals(Property.getProperties().getProperty("bill_id_self_just")) && Property.getProperties().getProperty("bill_id_self_just")!=null){
				logger.info("本人身份证正面处理");
				//本人身份证正面
				bimg = javax.imageio.ImageIO.read(new java.io.File(Property.getProperties().getProperty("bill_id_self_just")));
			}if(!"".equals(map.get("selfPhotoPath")) && map.get("selfPhotoPath")!=null){
				logger.info("本人照本处理");
				//本人照片
				bimg2 = javax.imageio.ImageIO.read(new java.io.File(map.get("selfPhotoPath")));
			}if(!"".equals(Property.getProperties().getProperty("bill_id_self_against")) && Property.getProperties().getProperty("bill_id_self_against")!=null){
				logger.info("本人身份证反面处理");
				//本人身份证反面
				bimg1 = javax.imageio.ImageIO.read(new java.io.File(Property.getProperties().getProperty("bill_id_self_against")));
			}
			
			//现场拍照及电子签名
			String property = Property.getProperties().getProperty("camera_path");
			if(!"".equals(property) && property!=null){
				//现场拍照
				bimg8 = javax.imageio.ImageIO.read(new java.io.File(property));
			}
			String property2 = Property.getProperties().getProperty("SIGNATURE_PATH");
			if(!"".equals(property2) && property2!=null){
				//签字图片
				bimg9 = javax.imageio.ImageIO.read(new java.io.File(property2));
			}
			String property3 = Property.getProperties().getProperty("dzyz_ml");
			if(!"".equals(property3) && property3!=null){
				//电子印章图片
				bimg10 = javax.imageio.ImageIO.read(new java.io.File(property3));
			}
			
			
			//本人照片
			graphics.drawImage(bimg2, 950, y+17, 100,115, null);
			//本人身份证
			graphics.drawImage(bimg, 270, y+157, 400,250, null);		
			graphics.drawImage(bimg1, 675, y+157, 400,250, null);
			
			//现场照
			graphics.drawString("现场拍照: ", 275, y+=452);//850
			graphics.drawImage(bimg8, 270, y+10, 440,280, null);
			if(!"0010".equals(map.get("productCode"))){
				graphics.drawString("客户声明：本人已经阅知并同意遵守存款产品协议书的相关约定，确认业务办理内容与申请内容一致。",  275, y+=320);
				graphics.drawString("电子签名和电子印章: ", 275, y+30);//1170
				//电子签名图片
				graphics.drawImage(bimg9, 270, y+40, 600,250, null);
				//电子印章图片
				graphics.drawImage(bimg10, 875, y+55, 200,200, null);
			}else{
				graphics.drawString("电子签名和电子印章: ", 275, y+=320);//1170
				//电子签名图片
				graphics.drawImage(bimg9, 270, y+10, 600,250, null);
				//电子印章图片
				graphics.drawImage(bimg10, 875, y+35, 200,200, null);
			}
		}else{
			String property2 = Property.getProperties().getProperty("SIGNATURE_PATH");
			if(!"".equals(property2) && property2!=null){
				//签字图片
				bimg9 = javax.imageio.ImageIO.read(new java.io.File(property2));
			}
			
			String property3 = Property.getProperties().getProperty("dzyz_ml");
			if(!"".equals(property3) && property3!=null){
				//电子印章图片
				bimg10 = javax.imageio.ImageIO.read(new java.io.File(property3));
			}
			if(!"0010".equals(map.get("productCode"))){
				graphics.drawString("客户声明：本人已经阅知并同意遵守存款产品协议书的相关约定，确认业务办理内容与申请内容一致。",  275, y+=27);
				graphics.drawString("电子签名和电子印章: ", 275, y+=30);//405
				//电子签名图片
				graphics.drawImage(bimg9, 270, y+20, 600,250, null);
				//电子印章图片
				graphics.drawImage(bimg10, 875, y+45, 200,200, null);
			}else{
				graphics.drawString("电子签名和电子印章: ", 275, y+=30);//405
				//电子签名图片
				graphics.drawImage(bimg9, 270, y+20, 600,250, null);
				//电子印章图片
				graphics.drawImage(bimg10, 875, y+45, 200,200, null);
			}
			
		}
	
		graphics.dispose();
		//图片名为日期+流水号
		String nowDate = DateUtil.getNowDate("yyyyMMdd");
		logger.info("获取今天日期-----》"+nowDate);
		// 8位日期+销户流水号
		String fileName = nowDate+map.get("accSvrNo");
		logger.info("获取开户日期和开户流水的fileName值---->" + fileName);
		
		String filepath = "";
		if(map.get("tangDDFSvrNo")==null || "".equals(map.get("tangDDFSvrNo"))){
			
			filepath = Property.FTP_LOCAL_PATH+fileName+".jpg";
		}else{
			String fileName1 = map.get("tangDDFSvrNo").trim();
			filepath = Property.FTP_LOCAL_PATH+fileName+"_"+fileName1+".jpg";
		}
		logger.info("获取本地生成的图片路径--->"+filepath);
		createImage(filepath);
		return filepath;
		
	}



	public static void main(String[] args) throws Exception {
		Property.initProperty();
		Map <String,String> map = new HashMap<String,String>();
		map.put("haveAgentFlag","1");//是否有代理人
		map.put("accSvrNo","123456");// 核心流水号
		map.put("accSvrDate", "20170822");// 开户日期
		map.put("accName", "赵宇");// 户名
		map.put("branchNo", "053600150");// 机构号
		map.put("transType", "银行卡开户");// 交易类型
		map.put("amount", "100000.00");// 交易金额
		map.put("accCardNo", "6231930000000900118");// 账号
		map.put("productName", "整存整取");//产品名称
		map.put("productCode","0xd");//产品代码
		map.put("supTellerNo", "11825");// 授权号
		map.put("macNo","000C0010");// 终端号
		map.put("idCardName", "赵宇");// 本人姓名
		map.put("idCardNo", "220203198501125416");// 本人身份证号
		map.put("qfjg", "吉林");// 签发机关
		map.put("tellerNo", "C0010");// 操作员
		map.put("agentIdCardName", "李四");// 代理人姓名
		map.put("agentIdCardNo", "11111111111111111");// 代理人卡号
		map.put("agentqfjg", "北京");// 代理人签发机关
		map.put("inputName", "收益人姓名：123");//收益人姓名
		map.put("inputNo", "收益人卡名：567");//收益人账号
		map.put("tangDDFAmt", "");//唐豆金额
		map.put("tangDDFSvrNo", "");//唐豆流水号
		map.put("intMoney", "45");//幸运豆金额
		map.put("billNo", "无");//存单凭证号
		map.put("startDate", "20175255");//起息日期
		map.put("endDate", "20175255");//到期日期
		map.put("openRate", 0.35+"%");//开户利率
		map.put("depTerm", "1152天");//存期
		map.put("exchFlag", "非自动转存");//转存标志
		
			JpgUtil_CardKH cg = new JpgUtil_CardKH();
			cg.graphicsGeneration(map);
////		SFTPUtil sf = new SFTPUtil();
//			//ChannelSftp sftp = sf.connect(Property.FTP_IP, Integer.parseInt(Property.FTP_PORT),Property.FTP_USER, Property.FTP_PWD);
//			try {
//				sftp = sf.connect("198.1.245.93", 22,"root","rootroot");
//			} catch (JSchException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
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
