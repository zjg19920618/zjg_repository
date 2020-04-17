package com.boomhope.Bill.Util;



import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/*
 * 生成JPG工具类
 */
public class JpgUtil_XH {
	
	BufferedImage image;
	
	static Logger logger = Logger.getLogger(JpgUtil_XH.class);
	
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
	 * @param map
	 * @throws IOException 
	 */
	public String graphicsGeneration(Map<String,String> map) throws Exception {
		logger.info("开始生成销户事后监管图片。");
		int imageWidth = 0;
		int imageHeight = 0;
		
		//获取list数据，设置生存图片的高度
		List<String> list = new ArrayList<String>();
		
		//获取唐行宝信息
		if(null != map.get("txbSvrJrnlNo") && !"".equals(map.get("txbSvrJrnlNo"))){
			list.add("转入唐行宝金额 : " + map.get("txbInputAmt").trim()+"元");
			list.add("转入唐行宝流水号: " + map.get("txbSvrJrnlNo").trim());
		}
		//获取唐豆信息
		if(null != map.get("tdAmt") && !"".equals(map.get("tdAmt").trim()) && !"0.00".equals(map.get("tdAmt").trim())){
			list.add("唐豆收回金额 : " + map.get("tdAmt").trim()+"元");
			list.add("唐豆收回流水号 : " + map.get("tdSvrJrnlNo").trim());
		}
		//获取幸运豆信息
		if(null != map.get("xydAmt") && !"".equals(map.get("xydAmt").trim()) && 0 != Double.valueOf(map.get("xydAmt").trim())){
			list.add("幸运豆收回金额 : " + map.get("xydAmt").trim()+"元");
		}
		//获取衍生品信息
		if(Integer.valueOf(map.get("proNum")) != 0){
			for (int i = 0; i < Integer.valueOf(map.get("proNum")); i++) {
				
				list.add(map.get("proMsg"+String.valueOf(i)));
			}
		}
		
		
		imageWidth = 1200;// 图片的宽度
		if("0".equals(map.get("haveBillAcc"))){
			int y = 1370;
			//有存单
			if("1".equals(map.get("agentFlag"))){
				// 存在代理人标识
				if(list.size()>0){
					imageHeight = y+750+450+list.size()*27;// 图片的高度
				}else{
					imageHeight = y+750+450;// 图片的高度
				}
				
			}else if("0".equals(map.get("agentFlag"))){
				// 不存在代理人标识
				if(list.size()>0){
					imageHeight = y+750+list.size()*27;// 图片的高度
				}else{
					imageHeight = y+750;// 图片的高度
				}
				
			}else{
				if(list.size()>0){
					imageHeight = y+list.size()*27;// 图片的高度
				}else{
					imageHeight = y;// 图片的高度
				}
			}
		}else if("1".equals(map.get("haveBillAcc"))){
			int y = 870;
			//无存单
			if("1".equals(map.get("agentFlag"))){
				// 存在代理人标识
				if(list.size()>0){
					imageHeight = y+750+450+list.size()*27;// 图片的高度
				}else{
					imageHeight = y+750+450;// 图片的高度
				}
				
			}else if("0".equals(map.get("agentFlag"))){
				// 不存在代理人标识
				if(list.size()>0){
					imageHeight = y+750+list.size()*27;// 图片的高度
				}else{
					imageHeight = y+750;// 图片的高度
				}
				
			}else{
				if(list.size()>0){
					imageHeight = y+list.size()*27;// 图片的高度
				}else{
					imageHeight = y;// 图片的高度
				}
			}
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
		int stringWidth = graphics.getFontMetrics(new Font("楷体", Font.BOLD, 40)).stringWidth("存单销户业务确认书");
		graphics.drawString("存单销户业务确认书", (imageWidth-stringWidth)/2, 50);
		
		graphics.setFont(new Font("楷体", Font.PLAIN, 18));
		
		
		logger.info("向graphics对象中插入存单信息------");
		
		graphics.drawString("交易类型: " + map.get("transType"), 275, 81);
		graphics.drawString("交易时间: " + map.get("canelBillDTransDate"), 275, 108);
		graphics.drawString("产品名称: " + map.get("proName"), 275, 135);
		graphics.drawString("开户日期: " + map.get("openDate"), 275, 162);
		graphics.drawString("存期: " + map.get("cancelDepterm"), 275, 190);
		graphics.drawString("利率: " + map.get("rate")+"%", 275, 216);
		graphics.drawString("机构号: " + map.get("branchNo"), 275, 243);
		graphics.drawString("柜员号: " + map.get("tellerNo"), 275, 270);
		graphics.drawString("操作员1（鉴别真伪）: " + map.get("supTellerNo1"), 275, 297);
		graphics.drawString("操作员2（鉴别真伪）: " + map.get("supTellerNo2"), 275, 324);
		graphics.drawString("授权员（业务确认）: " + map.get("supTellerNo3"), 275, 351);
		if("0".equals(map.get("haveBillAcc"))){
			graphics.drawString("是否有存单: " + "有存单", 275, 378);
		}else{
			graphics.drawString("是否有存单: " + "无存单", 275, 378);
		}
		graphics.drawString("是否到期: " + map.get("judgeState"), 275, 405);
		
		
		graphics.drawString("户名 : " + map.get("accName"), 675, 81);
		graphics.drawString("存单原账号: " + map.get("yuanAccNo"), 675, 108);
		graphics.drawString("存单账号: " + map.get("accNo"), 675, 135);
		graphics.drawString("存单凭证号: " + map.get("billNo"), 675, 162);
		graphics.drawString("转存标志: " + map.get("exchflag"), 675, 190);
		graphics.drawString("转入方式: " + map.get("transferMethed"), 675, 216);
		if("001".equals(map.get("cancleType"))){
			graphics.drawString("转入卡号: " + map.get("transferAcc"), 675, 243);
		}else if("002".equals(map.get("cancleType"))){
			graphics.drawString("转入电子账号: " + map.get("transferAcc"), 675, 243);
		}else if("003".equals(map.get("cancleType"))){
			graphics.drawString("转入唐行宝账号: " + map.get("transferAcc"), 675, 243);
		}
		graphics.drawString("销户金额: " + map.get("amount")+"元", 675, 270);
		graphics.drawString("实付利息: " + map.get("yflx")+"元", 675, 297);
		graphics.drawString("销户流水号 : " + map.get("canelBillId"), 675, 324);
		
		int y = 324;
		//遍历显示唐行宝转入信息、唐豆收回信息、幸运豆收回信息、衍生品收回信息等
		for (int i = 0; i < list.size(); i++) {
			y = y + 27 ;
			graphics.drawString(list.get(i), 675, y);
		}
		int start = 432;
		if(y>432){
			start = y+27;
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
		if("1".equals(map.get("agentFlag"))){
			// 存在代理人标识
			graphics.drawString("本人联网核查 " , 150, start+11);
			graphics.drawString("本人身份证 " , 150, start+151);
			graphics.drawString("代理人联网核查 " , 150, start+432);
			graphics.drawString("代理人身份证 " , 150, start+572);
			graphics.drawString("人脸现场拍照 " , 150, start+837);
			graphics.drawString("电子签名" , 150, start+1117);
			if("0".equals(map.get("haveBillAcc"))){
				graphics.drawString("存单 " , 150, start+1487);
			}
		}else if("0".equals(map.get("agentFlag"))){
			// 不存在代理人标识
			graphics.drawString("本人联网核查 " , 150, start+11);
			graphics.drawString("本人身份证 " , 150, start+151);
			graphics.drawString("人脸现场拍照 " , 150, start+432);
			graphics.drawString("电子签名" , 150, start+717);
			if("0".equals(map.get("haveBillAcc"))){
				graphics.drawString("存单 " , 150, start+1082);
			}
			
		}else{
			graphics.drawString("电子签名" , 150, start+11);
			if("0".equals(map.get("haveBillAcc"))){
				graphics.drawString("存单 " , 150, start+381);
			}
		}
		
		// 存在代理人标识
		if("1".equals(map.get("agentFlag"))){
			
			//本人联网信息
			graphics.drawString(" ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", 268, start);
			graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, start+7);
			graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, start+33);
			graphics.drawString("▏姓名               " + map.get("idCardName")+"                                                ", 275, start+11);
			graphics.drawString(" ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", 268, start+21);
			graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, start+21);
			graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, start+113);
			graphics.drawString("▏身份证号           " + map.get("idCardNo")+"                                                ", 275, start+31);
			graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, start+53);
			graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, start+31);
			graphics.drawString(" ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", 268, start+41);
			graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, start+41);
			graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, start+63);
			graphics.drawString("▏核查结果           公民身份号码与姓名一致，且存在照片                                                ", 275, start+61);
			graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, start+73);
			graphics.drawString(" ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", 268, start+81);
			graphics.drawString("▏签发机关           " + map.get("qfjg")+"                                                ", 275, start+101);
			graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, start+90);
			graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, start+106);
			graphics.drawString(" ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", 268, start+121);
			
			//代理人联网信息
			graphics.drawString(" ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", 268, start+421);
			graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, start+428);
			graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, start+454);
			graphics.drawString("▏姓名               " + map.get("agentIdCardName")+"                                                ", 275, start+432);
			graphics.drawString(" ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", 268, start+442);
			graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, start+442);
			graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, start+534);
			graphics.drawString("▏身份证号           " + map.get("agentIdCardNo")+"                                                ", 275, start+452);
			graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, start+474);
			graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, start+452);
			graphics.drawString(" ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", 268, start+462);
			graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, start+462);
			graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, start+484);
			graphics.drawString("▏核查结果           公民身份号码与姓名一致，且存在照片                                                ", 275, start+482);
			graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, start+494);
			graphics.drawString(" ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", 268, start+502);
			graphics.drawString("▏签发机关           " + map.get("agentqfjg")+"                                                ", 275, start+522);
			graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, start+511);
			graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, start+527);
			graphics.drawString(" ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", 268, start+542);
			
			//本人证件照
			if(!"".equals(Property.getProperties().getProperty("bill_id_self_just")) && Property.getProperties().getProperty("bill_id_self_just")!=null){
				logger.info("本人身份证正面处理");
				//本人身份证正面
				bimg = javax.imageio.ImageIO.read(new java.io.File(Property.getProperties().getProperty("bill_id_self_just")));
			}if(!"".equals(Property.getProperties().getProperty("id_card_self")) && Property.getProperties().getProperty("id_card_self")!=null){
				logger.info("本人照本处理");
				//本人照片
				bimg2 = javax.imageio.ImageIO.read(new java.io.File(Property.getProperties().getProperty("id_card_self")));
			}if(!"".equals(Property.getProperties().getProperty("bill_id_self_against")) && Property.getProperties().getProperty("bill_id_self_against")!=null){
				logger.info("本人身份证反面处理");
				//本人身份证反面
				bimg1 = javax.imageio.ImageIO.read(new java.io.File(Property.getProperties().getProperty("bill_id_self_against")));
			}
			
			//代理人证件照
			if(!"".equals(Property.getProperties().getProperty("id_card_agent")) && Property.getProperties().getProperty("id_card_agent")!=null){
				logger.info("代理人照片处理");
				//代理人照片
				bimg4 = javax.imageio.ImageIO.read(new java.io.File(Property.getProperties().getProperty("id_card_agent")));
			}
			if(!"".equals(Property.getProperties().getProperty("bill_id_agent_just")) && Property.getProperties().getProperty("bill_id_agent_just")!=null){
				logger.info("代理人身份证正面处理");
				//代理人身份证正面
				bimg5 = javax.imageio.ImageIO.read(new java.io.File(Property.getProperties().getProperty("bill_id_agent_just")));
			}if(!"".equals(Property.getProperties().getProperty("bill_id_agent_against")) && Property.getProperties().getProperty("bill_id_agent_against")!=null){
				//代理人身份证反面
				bimg6 = javax.imageio.ImageIO.read(new java.io.File(Property.getProperties().getProperty("bill_id_agent_against")));
			}
			
			//现场拍照及电子签名
			String property = Property.getProperties().getProperty("camera_path");
			if(!"".equals(property) && property!=null){
				//现场拍照
				bimg8 = javax.imageio.ImageIO.read(new java.io.File(property));
			}
			String property2 = Property.getProperties().getProperty("ACCCANCEL_SIGNATRUE_PATH");
			if(!"".equals(property2) && property2!=null){
				//签字图片
				bimg9 = javax.imageio.ImageIO.read(new java.io.File(property2));
			}
			
			//本人照片
			graphics.drawImage(bimg2, 950, start-3, 100,115, null);
			//本人身份证
			graphics.drawImage(bimg, 270, start+137, 400,250, null);		
			graphics.drawImage(bimg1, 675, start+137, 400,250, null);
			
			//代理人照片
			graphics.drawImage(bimg4, 950, start+417, 100,115, null);
			//代理人身份证
			graphics.drawImage(bimg5, 270, start+557, 400,250, null);
			graphics.drawImage(bimg6, 675, start+557, 400,250, null);
			
			//现场照
			graphics.drawImage(bimg8, 270, start+817, 440,280, null);
			//电子签名图片
			graphics.drawImage(bimg9, 270, start+1102, 800,350, null);
			
			if("0".equals(map.get("haveBillAcc"))){
				//存单图片
				String bill_face = map.get("bill_face");
				if(bill_face != null){
					logger.info("存单正面处理"+bill_face);
					//存单正面
					bimg3 = javax.imageio.ImageIO.read(new java.io.File(bill_face));
				}
				String bill_verso = map.get("bill_verso");
				if(bill_verso != null){
					logger.info("存单返面处理");
					//存单反面
					bimg7 = javax.imageio.ImageIO.read(new java.io.File(bill_verso));
				}
				//存单
				graphics.drawImage(bimg3, 270, start+1472, 800,250, null);
				graphics.drawImage(bimg7, 270, start+1727, 800,250, null);
				
			}
			
			//电子印章图片
			String property3 = Property.getProperties().getProperty("dzyz_ml");
			if(!"".equals(property3) && property3!=null){
				bimg10 = javax.imageio.ImageIO.read(new java.io.File(property3));
			}
			//电子印章图片
			graphics.drawImage(bimg10, imageWidth-250, imageHeight-250, 200,200, null);
			
		// 不存在代理人标识
		}else if("0".equals(map.get("agentFlag"))){
			
			//本人联网信息
			graphics.drawString(" ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", 268, start);
			graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, start+7);
			graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, start+33);
			graphics.drawString("▏姓名               " + map.get("idCardName")+"                                                ", 275, start+11);
			graphics.drawString(" ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", 268, start+21);
			graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, start+21);
			graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, start+113);
			graphics.drawString("▏身份证号           " + map.get("idCardNo")+"                                                ", 275, start+31);
			graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, start+53);
			graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, start+31);
			graphics.drawString(" ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", 268, start+41);
			graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, start+41);
			graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, start+63);
			graphics.drawString("▏核查结果           公民身份号码与姓名一致，且存在照片                                                ", 275, start+61);
			graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, start+73);
			graphics.drawString(" ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", 268, start+81);
			graphics.drawString("▏签发机关           " + map.get("qfjg")+"                                                ", 275, start+101);
			graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, start+90);
			graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, start+106);
			graphics.drawString(" ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", 268, start+121);
			
			//本人证件照
			if(!"".equals(Property.getProperties().getProperty("bill_id_self_just")) && Property.getProperties().getProperty("bill_id_self_just")!=null){
				logger.info("本人身份证正面处理");
				//本人身份证正面
				bimg = javax.imageio.ImageIO.read(new java.io.File(Property.getProperties().getProperty("bill_id_self_just")));
			}if(!"".equals(Property.getProperties().getProperty("id_card_self")) && Property.getProperties().getProperty("id_card_self")!=null){
				logger.info("本人照本处理");
				//本人照片
				bimg2 = javax.imageio.ImageIO.read(new java.io.File(Property.getProperties().getProperty("id_card_self")));
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
			String property2 = Property.getProperties().getProperty("ACCCANCEL_SIGNATRUE_PATH");
			if(!"".equals(property2) && property2!=null){
				//签字图片
				bimg9 = javax.imageio.ImageIO.read(new java.io.File(property2));
			}
			
			//本人照片
			graphics.drawImage(bimg2, 950, start-3, 100,115, null);
			//本人身份证
			graphics.drawImage(bimg, 270, start+137, 400,250, null);		
			graphics.drawImage(bimg1, 675, start+137, 400,250, null);
			
			//现场照
			graphics.drawImage(bimg8, 270, start+412, 440,280, null);
			//电子签名图片
			graphics.drawImage(bimg9, 270, start+702, 800,350, null);
			
			if("0".equals(map.get("haveBillAcc"))){
				//存单图片
				String bill_face = map.get("bill_face");
				if(bill_face != null){
					logger.info("存单正面处理"+bill_face);
					//存单正面
					bimg3 = javax.imageio.ImageIO.read(new java.io.File(bill_face));
				}
				String bill_verso = map.get("bill_verso");
				if(bill_verso != null){
					logger.info("存单返面处理");
					//存单反面
					bimg7 = javax.imageio.ImageIO.read(new java.io.File(bill_verso));
				}
				//存单
				graphics.drawImage(bimg3, 270, start+1062, 800,250, null);
				graphics.drawImage(bimg7, 270, start+1317, 800,250, null);
			}
			
			//电子印章图片
			String property3 = Property.getProperties().getProperty("dzyz_ml");
			if(!"".equals(property3) && property3!=null){
				bimg10 = javax.imageio.ImageIO.read(new java.io.File(property3));
			}
			//电子印章图片
			graphics.drawImage(bimg10, imageWidth-250, imageHeight-250, 200,200, null);
		}else{
			//签字图片
			String property2 = Property.getProperties().getProperty("ACCCANCEL_SIGNATRUE_PATH");
			if(!"".equals(property2) && property2!=null){
				bimg9 = javax.imageio.ImageIO.read(new java.io.File(property2));
			}
			//电子签名图片
			graphics.drawImage(bimg9, 270, start-3, 800,350, null);
			
			
			if("0".equals(map.get("haveBillAcc"))){
				//存单图片
				String bill_face = map.get("bill_face");
				if(bill_face != null){
					logger.info("存单正面处理"+bill_face);
					//存单正面
					bimg3 = javax.imageio.ImageIO.read(new java.io.File(bill_face));
				}
				String bill_verso = map.get("bill_verso");
				if(bill_verso != null){
					logger.info("存单返面处理");
					//存单反面
					bimg7 = javax.imageio.ImageIO.read(new java.io.File(bill_verso));
				}
				//存单
				graphics.drawImage(bimg3, 270, start+367, 800,250, null);
				graphics.drawImage(bimg7, 270, start+622, 800,250, null);
			}
			
			//电子印章图片
			String property3 = Property.getProperties().getProperty("dzyz_ml");
			if(!"".equals(property3) && property3!=null){
				bimg10 = javax.imageio.ImageIO.read(new java.io.File(property3));
			}
			//电子印章图片
			graphics.drawImage(bimg10, imageWidth-250, imageHeight-250, 200,200, null);
		}
	
		graphics.dispose();
		//图片名为日期+流水号
		String nowDate = DateUtil.getNowDate("yyyyMMdd");
		logger.info("获取今天日期-----》"+nowDate);
		// 8位日期+销户流水号
		String fileName = nowDate+map.get("canelBillId");
		logger.info("获取销户日期和销户流水的fileName值---->" + fileName);
		
		String filepath = "";
		
		if(null==map.get("tdAmt") || "".equals(map.get("tdAmt")) || "0.00".equals(map.get("tdAmt"))){
			
			filepath = Property.FTP_LOCAL_PATH+fileName+".jpg";
			
		}else if(null!=map.get("tdSvrJrnlNo")){
			
			String fileName1 = map.get("tdSvrJrnlNo").trim();
			filepath = Property.FTP_LOCAL_PATH+fileName+"_"+fileName1+".jpg";
			
		}else{
			
			filepath = Property.FTP_LOCAL_PATH+fileName+".jpg";
		}
		logger.info("获取本地生成的图片路径--->"+filepath);
		createImage(filepath);
		return filepath;
		
	}



	public static void main(String[] args) throws Exception {
		Property.initProperty();
		Map <String,String> map = new HashMap<String,String>();
		map.put("agentFlag", "0");// 1-存在代理人  0-不存在代理人
		map.put("haveBillAcc", "1");// 0-有 存单1-无存单
		map.put("canelBillId", "1234");//  销户核心流水号
		map.put("canelBillDTransDate", "20160923");//  销户日期
		map.put("accName", "赵宇");//  户名
		map.put("branchNo", "C0010");//  机构号
		map.put("transType", "存单销户");//  交易类型
		map.put("amount", "100.00");//  交易金额
		map.put("yuanAccNo", "05200012501468683");//  原存单账号
		map.put("accNo", "052000125001000346330");//  存单账号
		map.put("cancleType", "003");//  账号
		map.put("transferAcc", "623193001000000064");//  转入账号
		map.put("billNo", "03826833");//  凭证号
		map.put("supTellerNo", "A0043");// 授权号
		map.put("supTellerNo1", "A0043");// 授权号
		map.put("supTellerNo2", "A0043");// 授权号
		map.put("supTellerNo3", "A0043");// 授权号
		map.put("tellerNo", "C0025");//职业agentFlag
		map.put("judgeState", "到期支取");//职业agentFlag
		map.put("transferMethed", "转唐行宝");//职业agentFlag
		map.put("macNo", "000C001");// 终端号
		map.put("bill_face","d:\\bill.jpg");//存单正面照
		map.put("bill_verso","d:\\verso.jpg");//存单反面照
		
		map.put("idCardName", "张三");//  本人姓名
		map.put("idCardNo", "220203198501125416");// 本人身份证号
		map.put("qfjg", "吉林");// 签发机关
		
		map.put("agentIdCardName", "李四");// 代理人姓名
		map.put("agentIdCardNo", "11111111111111111");// 代理人卡号
		map.put("agentqfjg", "北京");// 代理人签发机关
		
		map.put("yflx", "150");// 实付利息
		map.put("tdAmt", "55");// 唐豆金额
		map.put("tdSvrJrnlNo", "615685");// 唐豆流水
		map.put("xydAmt", "5");// 幸运豆金额
		map.put("openDate", "20170801");// 开户日
		map.put("cancelDepterm", "2年");// 存期
		map.put("exchflag", "0");// 转存标志
		map.put("txbInputAmt", "5000");// 转入唐行宝金额
		map.put("txbSvrJrnlNo", "18184");// 转入唐行宝流水号
		map.put("proMsg0", "21515");// 衍生品1
		map.put("proMsg1", "21515");// 衍生品2
		map.put("proMsg2", "21515");// 衍生品3
		map.put("proMsg3", "21515");// 衍生品4
		map.put("proNum", "4");// 衍生品数量
		JpgUtil_XH cg = new JpgUtil_XH();
		cg.graphicsGeneration(map);
//		SFTPUtil sf = new SFTPUtil();
//			ChannelSftp sftp = sf.connect(Property.FTP_IP, Integer.parseInt(Property.FTP_PORT),Property.FTP_USER, Property.FTP_PWD);
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
