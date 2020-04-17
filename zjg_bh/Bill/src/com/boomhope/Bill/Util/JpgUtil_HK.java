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

import com.sun.corba.se.impl.orbutil.concurrent.Sync;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/*
 * 生成JPG工具类
 */
public class JpgUtil_HK {
	
	BufferedImage image;
	
	static Logger logger = Logger.getLogger(JpgUtil_HK.class);
	
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
		logger.info("开始生成存单换开事后监管图片。");
		int imageWidth = 0;
		int imageHeight = 0;
			//有存单
			if("1".equals(map.get("agentFlag"))){
				// 存在代理人标识
				imageWidth = 1200;// 图片的宽度
				imageHeight = 2570;// 图片的高度
			}else if("0".equals(map.get("agentFlag"))){
				// 不存在代理人标识
				imageWidth = 1200;// 图片的宽度
				imageHeight = 2120;// 图片的高度
			}else{
				imageWidth = 1200;// 图片的宽度
				imageHeight = 1370;// 图片的高度
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
		int stringWidth = graphics.getFontMetrics(new Font("楷体", Font.BOLD, 40)).stringWidth("存单换开业务确认书");
		graphics.drawString("存单换开业务确认书", (imageWidth-stringWidth)/2, 50);
		
		graphics.setFont(new Font("楷体", Font.PLAIN, 18));
		
		
		logger.info("向graphics对象中插入存单信息------");

		graphics.drawString("交易类型: " + map.get("transType"), 275, 81);
		graphics.drawString("交易时间: " + map.get("ZMBillDTransDate"), 275, 108);
		graphics.drawString("机构号: " + map.get("branchNo"), 275, 135);
		graphics.drawString("柜员号: " + map.get("tellerNo"), 275, 162);
		graphics.drawString("交易渠道: " + "存单回收机", 275, 190);
		graphics.drawString("终端号: " + map.get("macNo"), 275, 216);
		graphics.drawString("存单换开流水号 : " + map.get("HKSvrJrnlNo"), 275, 246);
		graphics.drawString("授权柜员1（鉴别真伪）: " + map.get("supTellerNo1"), 275, 273);
		graphics.drawString("授权柜员2（鉴别真伪）: " + map.get("supTellerNo2"), 275, 300);
		graphics.drawString("授权员（业务确认）: " + map.get("supTellerNo"), 275, 327);

		
		graphics.drawString("户名 : " + map.get("accName"), 675, 81);
		graphics.drawString("存单账号: " + map.get("AccNo"), 675, 108);
		graphics.drawString("金额 :"+map.get("amount"), 675, 135);
		graphics.drawString("存单凭证号: " + map.get("billNo"), 675, 162);
		graphics.drawString("新存单凭证号:"+map.get("newbillNo"),675,190);
		graphics.drawString("本人名称: " + map.get("idCardName"), 675, 216);
		graphics.drawString("本人证件号码: " + map.get("idCardNo"), 675, 246);
		graphics.drawString("本人证件类型: " + "身份证", 675, 273);
		if("1".equals(map.get("agentFlag"))){
		graphics.drawString("代理人名称: " + map.get("agentIdName"), 675, 300);
		graphics.drawString("代理人证件号: " + map.get("agentIdCardNo"), 675, 327);
		graphics.drawString("代理人证件类型: " + map.get("agentIdType"), 675, 355);		
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
		int start = 400;
		if("1".equals(map.get("agentFlag"))){
			// 存在代理人标识
			graphics.drawString("本人联网核查 " , 150, start+11);
			graphics.drawString("本人身份证 " , 150, start+151);
			graphics.drawString("代理人联网核查 " , 150, start+432);
			graphics.drawString("代理人身份证 " , 150, start+572);
			graphics.drawString("人脸现场拍照 " , 150, start+837);
			graphics.drawString("电子签名" , 150, start+1117);			
			graphics.drawString("存单图片 " , 150, start+1487);
			
		}else if("0".equals(map.get("agentFlag"))){
			// 不存在代理人标识
			graphics.drawString("本人联网核查 " , 150, start+11);
			graphics.drawString("本人身份证 " , 150, start+151);
			graphics.drawString("人脸现场拍照 " , 150, start+432);
			graphics.drawString("电子签名" , 150, start+717);			
			graphics.drawString("存单图片 " , 150, start+1082);
						
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
			graphics.drawString("▏姓名               " + map.get("agentIdName")+"                                                ", 275, start+432);
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
				
			if("true".equals(map.get("yz"))){
			  //电子印章图片
			  String property3 = Property.getProperties().getProperty("dzyz_ml");
			  if(!"".equals(property3) && property3!=null){
			 	bimg10 = javax.imageio.ImageIO.read(new java.io.File(property3));
			  }
			  //电子印章图片
		    	graphics.drawImage(bimg10, imageWidth-250, imageHeight-250, 200,200, null);
			}
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
			
			if("true".equals(map.get("yz"))){
			  //电子印章图片
			  String property3 = Property.getProperties().getProperty("dzyz_ml");
			  if(!"".equals(property3) && property3!=null){
				bimg10 = javax.imageio.ImageIO.read(new java.io.File(property3));
			   }
		    	//电子印章图片
			   graphics.drawImage(bimg10, imageWidth-250, imageHeight-250, 200,200, null);
			}
		}
	
		graphics.dispose();
		//图片名为日期+流水号
		String nowDate = DateUtil.getNowDate("yyyyMMdd");
		logger.info("获取今天日期-----》"+nowDate);
		// 8位日期+存单换开流水号
		String fileName = nowDate+map.get("HKSvrJrnlNo").trim();
		logger.info("获取存单换开日期和存单换开流水的fileName值---->" + fileName);
		
		String filepath = Property.FTP_LOCAL_PATH+fileName+".jpg";
		
		logger.info("获取本地生成的图片路径--->"+filepath);
		createImage(filepath);
		return filepath;
		
	}



	public static void main(String[] args) throws Exception {
		Property.initProperty();
		Map <String,String> map = new HashMap<String,String>();
		map.put("agentFlag", "1");// 1-存在代理人  0-不存在代理人
		map.put("haveBillAcc", "0");// 0-有 存单1-无存单
		map.put("HKSvrJrnlNo", "1234");//  换开核心流水号
		map.put("proName","存单换开");//产品名称
		map.put("canelBillDTransDate", "20171208");//  换开日期
		map.put("accName", "赵宇");//  户名
		map.put("branchNo", "C0010");//  机构号
		map.put("transType", "普通存单账户");//  交易类型3、电子子账户  0银行卡子账户 其他 普通存单账户
		map.put("amount", "100000.00");//  金额
		map.put("accNo", "052000125001000346330");//  存单账号
		map.put("cancleType", "003");//  账号
		map.put("billNo", "03826833");//  凭证号
		map.put("newbillNo", "11825111");//  新凭证号
		map.put("supTellerNo", "A0043");// 授权号
		map.put("supTellerNo1", "A0043");// 授权号1
		map.put("supTellerNo2", "A0043");// 授权号2
		map.put("supTellerNo3", "A0043");// 授权号3
		map.put("tellerNo", "C0025");//职业agentFlag
		map.put("macNo", "000C001");// 终端号
		map.put("bill_face","d:\\bill.jpg");//存单正面照
		map.put("bill_verso","d:\\verso.jpg");//存单反面照
		map.put("idtype","身份证");
		map.put("idCardName", "张三");//  本人姓名
		map.put("idCardNo", "220203198501125416");// 本人身份证号
		map.put("agentIdName", "李四");// 代理人姓名
		map.put("agentIdCardNo", "11111111111111111");// 代理人卡号
		map.put("agentIdType", "身份证");// 代理人证件类型
		map.put("openDate", "20170801");// 开户日
		map.put("idtype", "身份证");//本人证件类型
		map.put("qfjg", "湖南");//本人签发机关
		map.put("agentqfjg", "北京");//代理人
		JpgUtil_HK cg = new JpgUtil_HK();
		cg.graphicsGeneration(map);
		
	}
	
}
