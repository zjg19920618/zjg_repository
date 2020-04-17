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

/*
 * 生成JPG工具类
 */
public class JpgUtil {
	
	BufferedImage image;
	
	static Logger logger = Logger.getLogger(JpgUtil.class);
	
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
	 * canelBillId 销户核心流水号
	 * canelBillDTransDate 销户日期
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
			imageHeight = 1500;// 图片的高度
//			imageHeight = 960;// 图片的高度
		}else if("2".equals(map.get("agentFlag"))){
			// 不存在代理人标识
			imageWidth = 1200;// 图片的宽度
//			imageHeight = 1520;// 图片的高度
			imageHeight = 1120;// 图片的高度
//			imageHeight = 580;// 图片的高度
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
		if(map.get("handSupTellerNo").equals("无") && map.get("supTellerNo").equals("无")){
			graphics.drawString("存单 " , 150, 190);
		}else if(!map.get("handSupTellerNo").equals("无") && map.get("supTellerNo").equals("无")){
			graphics.drawString("存单 " , 150, 190);
		}else{
			graphics.drawString("本人联网核查 " , 150, 170);
			graphics.drawString("本人身份证 " , 150, 310);
			logger.info("判断是否为代理人办理，1为代理人，2为不是代理人，agentFlag---》"+map.get("agentFlag"));
			if("1".equals(map.get("agentFlag"))){
				graphics.drawString("代理人联网核查 " , 150, 594);
				graphics.drawString("代理人身份证 " , 150, 704);
				graphics.drawString("存单 " , 150, 959);
//				graphics.drawString("现场拍照 " , 150, 1499);
//				graphics.drawString("人脸识别结果：通过" , 800, 1700);
			}else if("2".equals(map.get("agentFlag"))){
				graphics.drawString("存单" , 150, 570);
//				graphics.drawString("现场拍照" , 150, 1110);
//				graphics.drawString("人脸识别结果：通过" , 800, 1310);
			}
		}
		

		logger.info("向graphics对象中插入存单信息------");
//		graphics.drawString("存单" , 150, 959);
		graphics.drawString("流水号 : " + map.get("canelBillId"), 275, 27);
		graphics.drawString("交易时间 : " + map.get("canelBillDTransDate"), 675, 27);
		graphics.drawString("户名 : " + map.get("accName"), 275, 54);
		graphics.drawString("机构号: " + map.get("branchNo"), 675, 54);
		graphics.drawString("交易类型: " + map.get("transType"), 275, 81);
		graphics.drawString("交易金额: " + map.get("amount"), 675, 81);
		graphics.drawString("账号: " + map.get("accNo"), 275, 108);
		graphics.drawString("凭证号: " + map.get("billNo"), 675, 108);
		graphics.drawString("授权员（销户）: " + map.get("supTellerNo"), 275, 135);
		graphics.drawString("终端号: " + map.get("macNo"), 675, 135);
		logger.info("向graphics对象中插入身份证相关信息");
		if(!map.get("handSupTellerNo").equals("无") && !map.get("supTellerNo").equals("无")){
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
		}else if(map.get("handSupTellerNo").equals("无") && !map.get("supTellerNo").equals("无")){
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
		}else{}
		
		if(map.get("handSupTellerNo").equals("无") && map.get("supTellerNo").equals("无")){
			graphics.drawString("操作员: " + map.get("teller"), 275, 170);
			graphics.drawString("授权员（手输）: " + map.get("handSupTellerNo"), 455, 170);
//			graphics.drawString("业务类型: " + map.get("busType"), 455, 290);
			Date date=new Date();
			  DateFormat format=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			  String time=format.format(date);
			graphics.drawString("核查时间: " + time, 800, 170);
			logger.info("授权员（手输）为空 ，授权员为空");
			logger.debug("授权员（手输）为空 ，授权员为空");
		}else
		if(!map.get("handSupTellerNo").equals("无") && map.get("supTellerNo").equals("无")){
			graphics.drawString("操作员: " + map.get("teller"), 275, 170);
			graphics.drawString("授权员（手输）: " + map.get("handSupTellerNo"), 455, 170);
//			graphics.drawString("业务类型: " + map.get("busType"), 455, 290);
			Date date=new Date();
			  DateFormat format=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			  String time=format.format(date);
			graphics.drawString("核查时间: " + time, 800, 170);
			logger.info("授权员（手输）不为空 ，授权员为空");
			logger.debug("授权员（手输）不为空 ，授权员为空");
		}else{
			graphics.drawString("操作员: " + map.get("teller"), 275, 290);
			graphics.drawString("授权员（手输）: " + map.get("handSupTellerNo"), 455, 290);
//			graphics.drawString("业务类型: " + map.get("busType"), 455, 290);
			Date date=new Date();
			  DateFormat format=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			  String time=format.format(date);
			graphics.drawString("核查时间: " + time, 800, 290);
			logger.info("授权员（手输）不为空 ，授权员不为空 或授权员（手输）为空，授权员不为空 ");
			logger.debug("授权员（手输）不为空 ，授权员不为空 或授权员（手输）为空，授权员不为空 ");
		}
		
		if(!map.get("handSupTellerNo").equals("无") && !map.get("supTellerNo").equals("无")){
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
			
		}else if(map.get("handSupTellerNo").equals("无") && !map.get("supTellerNo").equals("无")){
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
			if(!map.get("handSupTellerNo").equals("无") && !map.get("supTellerNo").equals("无")){
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
			}else if(map.get("handSupTellerNo").equals("无") && !map.get("supTellerNo").equals("无")){
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
			}else{}

		
			
			if(!map.get("handSupTellerNo").equals("无") && !map.get("supTellerNo").equals("无")){
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
			}else if(map.get("handSupTellerNo").equals("无") && !map.get("supTellerNo").equals("无")){
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
			}else{}
			
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
//			if(!Property.getProperties().getProperty("camera_path").equals("") || Property.getProperties().getProperty("camera_path").equals(null)){
//				logger.info("本人现场照处理");
//				//本人现场照
//				bimg8 = javax.imageio.ImageIO.read(new java.io.File(Property.getProperties().getProperty("camera_path")));
//			}
//			if(!Property.getProperties().getProperty("camera_path").equals("") || Property.getProperties().getProperty("camera_path").equals(null)){
//				logger.info("代理人现场照");
//				//代理人现场照
//				bimg9 = javax.imageio.ImageIO.read(new java.io.File(Property.getProperties().getProperty("camera_path")));
//			}	
			logger.info("读取各种照片结束-------");
			logger.info("开始插入各种照片-------");
//		if (bimg != null)
			if(!map.get("handSupTellerNo").equals("无") && !map.get("supTellerNo").equals("无")){
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
				//存单
				if("1".equals(map.get("agentFlag"))){
					graphics.drawImage(bimg3, 270, 949, 800,250, null);
					graphics.drawImage(bimg7, 270, 1209, 800,250, null);
				}else 
					if("2".equals(map.get("agentFlag"))){
						graphics.drawImage(bimg3, 270, 560, 800,250, null);
						graphics.drawImage(bimg7, 270, 820, 800,250, null);
				}
			}else if(map.get("handSupTellerNo").equals("无") && !map.get("supTellerNo").equals("无")){
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
				//存单
				if("1".equals(map.get("agentFlag"))){
					graphics.drawImage(bimg3, 270, 949, 800,250, null);
					graphics.drawImage(bimg7, 270, 1209, 800,250, null);
				}else 
					if("2".equals(map.get("agentFlag"))){
						graphics.drawImage(bimg3, 270, 560, 800,250, null);
						graphics.drawImage(bimg7, 270, 820, 800,250, null);
				}
			}else if(map.get("handSupTellerNo").equals("无") && map.get("supTellerNo").equals("无")){
				
				//存单
				if("1".equals(map.get("agentFlag"))){
					graphics.drawImage(bimg3, 270, 949, 800,250, null);
					graphics.drawImage(bimg7, 270, 1209, 800,250, null);
				}else 
					if("2".equals(map.get("agentFlag"))){
						graphics.drawImage(bimg3, 270, 190, 800,250, null);
						graphics.drawImage(bimg7, 270, 450, 800,250, null);
				}
			}else
				if(!map.get("handSupTellerNo").equals("无") && map.get("supTellerNo").equals("无")){
					//存单
					if("1".equals(map.get("agentFlag"))){
						graphics.drawImage(bimg3, 270, 949, 800,250, null);
						graphics.drawImage(bimg7, 270, 1209, 800,250, null);
					}else 
						if("2".equals(map.get("agentFlag"))){
							graphics.drawImage(bimg3, 270, 190, 800,250, null);
							graphics.drawImage(bimg7, 270, 450, 800,250, null);
					}
				}


		

        //拍摄照片
//		if("1".equals(map.get("agentFlag"))){
//			graphics.drawImage(bimg8, 270, 1489, 400,400, null);
//		}else 
//			if("2".equals(map.get("agentFlag"))){
//			graphics.drawImage(bimg8, 270, 1100, 400,400, null);
//		}
		logger.info("插入各种照片结束-----------");
		graphics.dispose();
		//图片名为身份证加业务类型
		String nowDate = DateUtil.getNowDate("yyyyMMdd");
		logger.info("获取今天日期-----》"+nowDate);
		// 8位日期+销户流水号
		String fileName = nowDate+map.get("canelBillId");
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
		map.put("canelBillId", "1234");//  销户核心流水号
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
		map.put("handSupTellerNo","无");
		map.put("customer_identification", "yes");//是否新建客户 
		map.put("tel", "1000000");//手机号
		map.put("CUST_NAME","123");//用户名
		map.put("DOMICILE_PLACE","hjh");//地址
		map.put("CUST_SEX", "1");//性别
//		map.put("ID_TYPE", "1");//证件类型
		map.put("ID_NO", "32324");//证件号码
		map.put("ISSUE_INST", "232");//签发机关
//		map.put("CUST_TYPE", "1");//客户类型
		map.put("BUS_STATUS", "01");//职业agentFlag
		map.put("agentFlag", "1");//职业
		ChannelSftp sftp = null;
		String nowDate = DateUtil.getNowDate("yyyyMMdd");
		String ftpPath = "/home/abc/"+nowDate;
			JpgUtil cg = new JpgUtil();
			cg.createCustomerJpg(map);
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
	
	/**
	 * 生成开户事后监管图片
	 * agentFlag 1-存在代理人  2-不存在代理人
	 * canelBillId 销户核心流水号
	 * canelBillDTransDate 销户日期
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
	public String graphicsGeneration1(Map<String,String> map) throws IOException {
		logger.info("开始生成事后监管图片。");
		int imageWidth = 0;
		int imageHeight = 0;
		if("1".equals(map.get("agentFlag"))){
			// 存在代理人标识
			imageWidth = 1200;// 图片的宽度
			imageHeight = 1500;// 图片的高度
		}else if("2".equals(map.get("agentFlag"))){
			// 不存在代理人标识
			imageWidth = 1200;// 图片的宽度
			imageHeight = 1120;// 图片的高度
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
		graphics.drawString("本人联网核查 " , 150, 197);
		graphics.drawString("本人身份证 " , 150, 337);
		logger.info("判断是否为代理人办理，1为代理人，2为不是代理人，agentFlag---》"+map.get("agentFlag"));
		if("1".equals(map.get("agentFlag"))){
			graphics.drawString("代理人联网核查 " , 150, 651);
			graphics.drawString("代理人身份证 " , 150, 734);
			if(map.get("customer_identification").equals("yes")){
				graphics.drawString("客户信息 " , 150, 1025);
			}
		}else if("2".equals(map.get("agentFlag"))){
			if(map.get("customer_identification").equals("yes")){
				graphics.drawString("客户信息 " , 150, 636);
			}
		}
		

		logger.info("向graphics对象中插入存单信息------");
//		graphics.drawString("存单" , 150, 959);
		graphics.drawString("流水号 : " + map.get("canelBillId"), 275, 27);
		graphics.drawString("交易时间 : " + map.get("canelBillDTransDate"), 675, 27);
		graphics.drawString("户名 : " + map.get("accName"), 275, 54);
		graphics.drawString("机构号: " + map.get("branchNo"), 675, 54);
		graphics.drawString("交易类型: " + map.get("transType"), 275, 81);
		graphics.drawString("交易金额: " + map.get("amount"), 675, 81);
		graphics.drawString("账号: " + map.get("accNo"), 275, 108);
		graphics.drawString("终端号: " + map.get("macNo"), 675, 108);
		graphics.drawString("授权员（开户）: " + map.get("supTellerNo"), 275, 135);
		graphics.drawString("订单号: " + map.get("orderNo"), 275, 162);
		
		if(map.get("xydCount")!=null && !"".equals(map.get("xydCount"))){
			graphics.drawString("幸运豆金额: " + map.get("xydCount")+"元", 675, 135);
		}
		if(map.get("zydCount")!=null && !"".equals(map.get("zydCount"))){
			graphics.drawString("增益豆金额: " + map.get("zydCount")+"元", 675, 162);
		}
		if(map.get("xfdCount")!=null && !"".equals(map.get("xfdCount"))){
			graphics.drawString("消费豆数量: " + map.get("xfdCount")+"个", 675, 162);
		}	
		
		logger.info("向graphics对象中插入身份证相关信息");
		graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, 250);
		graphics.drawString(" ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", 268, 216);
		graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, 249);
		graphics.drawString("▏姓名               " + map.get("idCardName")+"                                                ", 275, 227);
		graphics.drawString(" ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", 268, 237);
		graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, 237);
		graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, 329);
		graphics.drawString("▏身份证号           " + map.get("idCardNo")+"                                                ", 275, 247);
		graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, 269);
		graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, 247);
		graphics.drawString(" ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", 268, 257);
		graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, 257);
		graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, 279);
		graphics.drawString("▏核查结果           公民身份号码与姓名一致，且存在照片                                                ", 275, 279);
		graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, 289);
		graphics.drawString(" ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", 268, 299);
		graphics.drawString("▏签发机关           " + map.get("qfjg")+"                                                ", 275, 319);
		graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, 306);
		graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, 322);
		graphics.drawString(" ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", 268, 337);
		
		if(map.get("handSupTellerNo").equals("无") && map.get("supTellerNo").equals("无")){
			graphics.drawString("操作员: " + map.get("teller"), 275, 197);
			graphics.drawString("授权员（手输）: " + map.get("handSupTellerNo"), 455, 197);
//			graphics.drawString("业务类型: " + map.get("busType"), 455, 290);
			Date date=new Date();
			  DateFormat format=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			  String time=format.format(date);
			graphics.drawString("核查时间: " + time, 800, 197);
			logger.info("授权员（手输）为空 ，授权员为空");
			logger.debug("授权员（手输）为空 ，授权员为空");
		}else
		if(!map.get("handSupTellerNo").equals("无") && map.get("supTellerNo").equals("无")){
			graphics.drawString("操作员: " + map.get("teller"), 275, 197);
			graphics.drawString("授权员（手输）: " + map.get("handSupTellerNo"), 455, 197);
//			graphics.drawString("业务类型: " + map.get("busType"), 455, 290);
			Date date=new Date();
			  DateFormat format=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			  String time=format.format(date);
			graphics.drawString("核查时间: " + time, 800, 197);
			logger.info("授权员（手输）不为空 ，授权员为空");
			logger.debug("授权员（手输）不为空 ，授权员为空");
		}else{
			graphics.drawString("操作员: " + map.get("teller"),275, 197);
			graphics.drawString("授权员（手输）: " + map.get("handSupTellerNo"), 455, 197);
//			graphics.drawString("业务类型: " + map.get("busType"), 455, 290);
			Date date=new Date();
			  DateFormat format=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			  String time=format.format(date);
			graphics.drawString("核查时间: " + time, 800, 197);
			logger.info("授权员（手输）不为空 ，授权员不为空 或授权员（手输）为空，授权员不为空 ");
			logger.debug("授权员（手输）不为空 ，授权员不为空 或授权员（手输）为空，授权员不为空 ");
		}
		
		if("1".equals(map.get("agentFlag"))){
			logger.info("向graphics对象中插入，如果为代理人办理，继续插入身份证相关信息-------");
			graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, 637);
			graphics.drawString(" ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", 268, 630);
			graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, 663);
			graphics.drawString("▏姓名               " + map.get("agentIdCardName")+"                                                ", 275, 641);
			graphics.drawString(" ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", 268, 631);
			graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, 651);
			graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, 743);
			graphics.drawString("▏身份证号           " + map.get("agentIdCardNo")+"                                                ", 275, 661);
			graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, 683);
			graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, 661);
			graphics.drawString(" ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", 268, 671);
			graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, 671);
			graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, 693);
			graphics.drawString("▏核查结果           公民身份号码与姓名一致，且存在照片                                                ", 275, 691);
			graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, 703);
			graphics.drawString(" ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", 268, 711);
			graphics.drawString("▏签发机关           " + map.get("agentqfjg")+"                                                ", 275, 731);
			graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, 720);
			graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, 736);
			graphics.drawString(" ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", 268, 751);
		}else 
			if("2".equals(map.get("agentFlag"))){
		}
		
		

		// 改成这样:
		BufferedImage bimg = null;
		BufferedImage bimg1 = null;
		BufferedImage bimg2 = null;
//		BufferedImage bimg3 = null;
		BufferedImage bimg4 = null;
		BufferedImage bimg5 = null;
		BufferedImage bimg6 = null;
//		BufferedImage bimg7 = null;
//		BufferedImage bimg8 = null;
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
			
			logger.info("读取各种照片结束-------");
			logger.info("开始插入各种照片-------");
			//本人身份证
			graphics.drawImage(bimg, 270, 357, 400,250, null);		
			graphics.drawImage(bimg1, 675, 357, 400,250, null);
			//照片
			graphics.drawImage(bimg2, 950, 212, 100,115, null);
			//代理人照片

			//代理人身份证
			if("1".equals(map.get("agentFlag"))){
				graphics.drawImage(bimg5, 270, 751, 400,250, null);
				graphics.drawImage(bimg6, 675, 751, 400,250, null);
				graphics.drawImage(bimg4, 950, 627, 100,115, null);
				if(!"0010".equals(map.get("proCode"))){
					graphics.drawString("客户声明：本人已经阅知并同意遵守存款产品协议书的相关约定，确认业务办理内容与申请内容一致。",  270, 1028);
				}
			}else{
				if(!"0010".equals(map.get("proCode"))){
					graphics.drawString("客户声明：本人已经阅知并同意遵守存款产品协议书的相关约定，确认业务办理内容与申请内容一致。",  270, 634);
				}
				
			}

			if(map.get("customer_identification").equals("yes")){
				int height=1009;
				if("1".equals(map.get("agentFlag"))){//是否代理
					height=1009;
				}else{
					height=627;
				}
				String job="";
				if(map.get("BUS_STATUS").equals("01")){
					job="公务员";
				  }else if(map.get("BUS_STATUS").equals("02")){
					  job="医生";
				  }else if(map.get("BUS_STATUS").equals("教师")){
					  job="03";
				  }else if(map.get("BUS_STATUS").equals("04")){
					  job="其他事业单位员工";
				  }else if(map.get("BUS_STATUS").equals("05")){
					  job="律师/会计师/审计师";
				  }else if(map.get("BUS_STATUS").equals("06")){
					  job="企业员工";
				  }else if(map.get("BUS_STATUS").equals("07")){
					  job="军人";
				  }else if(map.get("BUS_STATUS").equals("08")){
					  job="学生";
				  }else if(map.get("BUS_STATUS").equals("09")){
					  job="个体/自由职业者";
				  }else if(map.get("BUS_STATUS").equals("10")){
					  job="其他";
				  }
				
//				graphics.drawImage(bimg, 950, height-4, 400,250, null);
				graphics.drawImage(bimg2, 950, height-4, 100,115, null);
				graphics.drawString(" ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", 268, height);//159
				graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, height+7);
				graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, height+21);
				graphics.drawString("▏姓名               " + map.get("idCardName")+"                                                ", 275, height+11);
				graphics.drawString(" ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", 268, height+21);
				graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, height+28);
				graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, height+42);
				graphics.drawString("▏证件类型              身份证"+"                                             ", 275, height+32);
				graphics.drawString(" ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", 268, height+42);
				graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, height+49);
				graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, height+63);
				graphics.drawString("▏证件号码              " + map.get("ID_NO")+"                                                ", 275, height+53);
				graphics.drawString(" ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", 268, height+63);
				graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, height+70);
				graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, height+84);
				graphics.drawString("▏签发机关             " + map.get("ISSUE_INST")+"                                                ", 275, height+74);
				graphics.drawString(" ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", 268, height+84);
				graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, height+91);
				graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, height+105);
				graphics.drawString("▏性别              " + (map.get("CUST_SEX").equals("1")?"男":"女")+"                                                ", 275, height+95);
				graphics.drawString(" ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", 268, height+105);
				graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, height+112);
				graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, height+126);
				graphics.drawString("▏手机号               " + map.get("tel")+"                                                ", 275, height+116);
				graphics.drawString(" ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", 268, height+126);
				graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, height+133);
				graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, height+147);
				graphics.drawString("▏职业               " + job+"                                                ", 275, height+137);
				graphics.drawString(" ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", 268, height+147);
				graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, height+154);
				graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, height+168);
				graphics.drawString("▏联系地址               " + map.get("DOMICILE_PLACE")+"                                                ", 275, height+161);
				graphics.drawString(" ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", 268, height+175);
			}
		

        //拍摄照片
//		if("1".equals(map.get("agentFlag"))){
//			graphics.drawImage(bimg8, 270, 1489, 400,400, null);
//		}else 
//			if("2".equals(map.get("agentFlag"))){
//			graphics.drawImage(bimg8, 270, 1100, 400,400, null);
//		}
		logger.info("插入各种照片结束-----------");
		graphics.dispose();
		//图片名为身份证加业务类型
		String nowDate = DateUtil.getNowDate("yyyyMMdd");
		logger.info("获取今天日期-----》"+nowDate);
		// 8位日期+销户流水号
		String fileName = nowDate+map.get("canelBillId");
		logger.info("获取日期和canelBillId的fileName值---->" + fileName);
		String filepath = Property.FTP_LOCAL_PATH+fileName+".jpg";
		logger.info("获取本地生成的图片路径--->"+filepath);
		createImage(filepath);
		return filepath;
	}
	/**
	 * 生成新建客户信息图片
	 * @param map
	 * @return
	 * @throws IOException
	 */
	public String createCustomerJpg(Map<String,String> map) throws IOException {
		logger.info("开始生成事后监管图片。");
		int imageWidth = 1200;
		int imageHeight = 1120;

		image = new BufferedImage(imageWidth, imageHeight,BufferedImage.TYPE_INT_RGB);
		logger.info("创建graphics对象-------------------");
		Graphics graphics = image.getGraphics();
		//设置背景颜色
		logger.info("设置背景颜色！");
		graphics.setColor(Color.WHITE);
		graphics.setFont(new Font("楷体", Font.PLAIN, 18));
		graphics.fillRect(0, 0, imageWidth, imageHeight);
		graphics.setColor(Color.BLACK);
		graphics.drawString("本人身份证 " , 150, 177);
		graphics.drawString("客户信息 " , 150, 447);
		logger.info("向graphics对象中插入存单信息------");
		graphics.drawString("流水号 : " + map.get("custSvrNo"), 275, 27);
		graphics.drawString("交易时间 : " + map.get("canelBillDTransDate"), 675, 27);
		graphics.drawString("户名 : " + map.get("accName"), 275, 54);
		graphics.drawString("机构号: " + map.get("branchNo"), 675, 54);
		graphics.drawString("交易类型: 新建客户", 275, 81);
		graphics.drawString("终端号: " + map.get("macNo"), 675, 81);
		
		// 改成这样:
		BufferedImage bimg = null;
		BufferedImage bimg1 = null;
		BufferedImage bimg2 = null;
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
		
		logger.info("读取各种照片结束-------");
		logger.info("开始插入各种照片-------");
		if(map.get("handSupTellerNo").equals("无") && map.get("supTellerNo").equals("无")){
			graphics.drawString("操作员: " + map.get("teller"), 275, 134);
			graphics.drawString("授权员（手输）: " + map.get("handSupTellerNo"), 455, 134);
//			graphics.drawString("业务类型: " + map.get("busType"), 455, 290);
			Date date=new Date();
			  DateFormat format=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			  String time=format.format(date);
			graphics.drawString("核查时间: " + time, 800, 134);
			logger.info("授权员（手输）为空 ，授权员为空");
			logger.debug("授权员（手输）为空 ，授权员为空");
		}else
		if(!map.get("handSupTellerNo").equals("无") && map.get("supTellerNo").equals("无")){
			graphics.drawString("操作员: " + map.get("teller"), 275, 134);
			graphics.drawString("授权员（手输）: " + map.get("handSupTellerNo"), 455, 134);
//			graphics.drawString("业务类型: " + map.get("busType"), 455, 290);
			Date date=new Date();
			  DateFormat format=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			  String time=format.format(date);
			graphics.drawString("核查时间: " + time, 800, 134);
			logger.info("授权员（手输）不为空 ，授权员为空");
			logger.debug("授权员（手输）不为空 ，授权员为空");
		}else{
			graphics.drawString("操作员: " + map.get("teller"),275,134);
			graphics.drawString("授权员（手输）: " + map.get("handSupTellerNo"), 455, 134);
//			graphics.drawString("业务类型: " + map.get("busType"), 455, 290);
			Date date=new Date();
			  DateFormat format=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			  String time=format.format(date);
			graphics.drawString("核查时间: " + time, 800, 134);
			logger.info("授权员（手输）不为空 ，授权员不为空 或授权员（手输）为空，授权员不为空 ");
			logger.debug("授权员（手输）不为空 ，授权员不为空 或授权员（手输）为空，授权员不为空 ");
		}
		//本人身份证
		graphics.drawImage(bimg, 270, 153, 400,250, null);		
		graphics.drawImage(bimg1, 675, 153, 400,250, null);
		
		int height=417;
		String job="";
		if(map.get("BUS_STATUS").equals("01")){
			job="公务员";
		  }else if(map.get("BUS_STATUS").equals("02")){
			  job="医生";
		  }else if(map.get("BUS_STATUS").equals("教师")){
			  job="03";
		  }else if(map.get("BUS_STATUS").equals("04")){
			  job="其他事业单位员工";
		  }else if(map.get("BUS_STATUS").equals("05")){
			  job="律师/会计师/审计师";
		  }else if(map.get("BUS_STATUS").equals("06")){
			  job="企业员工";
		  }else if(map.get("BUS_STATUS").equals("07")){
			  job="军人";
		  }else if(map.get("BUS_STATUS").equals("08")){
			  job="学生";
		  }else if(map.get("BUS_STATUS").equals("09")){
			  job="个体/自由职业者";
		  }else if(map.get("BUS_STATUS").equals("10")){
			  job="其他";
		  }
//		graphics.drawImage(bimg, 950, height-4, 400,250, null);
		graphics.drawImage(bimg2, 950, height-4, 100,115, null);
		graphics.drawString(" ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", 268, height);//159
		graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, height+7);
		graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, height+21);
		graphics.drawString("▏姓名               " + map.get("idCardName")+"                                                ", 275, height+11);
		graphics.drawString(" ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", 268, height+21);
		graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, height+28);
		graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, height+42);
		graphics.drawString("▏证件类型              身份证"+"                                             ", 275, height+32);
		graphics.drawString(" ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", 268, height+42);
		graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, height+49);
		graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, height+63);
		graphics.drawString("▏证件号码              " + map.get("ID_NO")+"                                                ", 275, height+53);
		graphics.drawString(" ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", 268, height+63);
		graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, height+70);
		graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, height+84);
		graphics.drawString("▏签发机关             " + map.get("ISSUE_INST")+"                                                ", 275, height+74);
		graphics.drawString(" ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", 268, height+84);
		graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, height+91);
		graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, height+105);
		graphics.drawString("▏性别              " + (map.get("CUST_SEX").equals("1")?"男":"女")+"                                                ", 275, height+95);
		graphics.drawString(" ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", 268, height+105);
		graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, height+112);
		graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, height+126);
		graphics.drawString("▏手机号               " + map.get("tel")+"                                                ", 275, height+116);
		graphics.drawString(" ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", 268, height+126);
		graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, height+133);
		graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, height+147);
		graphics.drawString("▏职业               " + job+"                                                ", 275, height+137);
		graphics.drawString(" ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", 268, height+147);
		graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, height+154);
		graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, height+168);
		graphics.drawString("▏联系地址               " + map.get("DOMICILE_PLACE")+"                                                ", 275, height+161);
		graphics.drawString(" ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", 268, height+168);
		graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, height+175);
		graphics.drawString("▏                  ▏" + "  "+"                                                ▏"+"              ▏", 275, height+189);
		graphics.drawString("▏国籍                中国                                               ", 275, height+182);
		graphics.drawString(" ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", 268, height+196);
		logger.info("插入各种照片结束-----------");
		
		
		//代理人身份证
		if("1".equals(map.get("agentFlag"))){
			BufferedImage bimg5 = null;
			BufferedImage bimg6 = null;
			graphics.drawString("代理人身份证图片 " , 150, 647);
			if(!Property.getProperties().getProperty("bill_id_agent_just").equals("") || Property.getProperties().getProperty("bill_id_agent_just").equals(null)){
				logger.info("代理人身份证正面处理");
				//代理人身份证正面
				bimg5 = javax.imageio.ImageIO.read(new java.io.File(Property.getProperties().getProperty("bill_id_agent_just")));
			}if(!Property.getProperties().getProperty("bill_id_agent_against").equals("") || Property.getProperties().getProperty("bill_id_agent_against").equals(null)){
				//代理人身份证反面
				bimg6 = javax.imageio.ImageIO.read(new java.io.File(Property.getProperties().getProperty("bill_id_agent_against")));
			}
			graphics.drawImage(bimg5, 270, 631, 400,250, null);
			graphics.drawImage(bimg6, 675, 631, 400,250, null);
		}
		graphics.dispose();
		//图片名为身份证加业务类型
		String nowDate = DateUtil.getNowDate("yyyyMMdd");
		logger.info("获取今天日期-----》"+nowDate);
		// 8位日期+销户流水号
		String fileName = nowDate+map.get("custSvrNo");
		logger.info("获取日期和canelBillId的fileName值---->" + fileName);
		String filepath = Property.FTP_LOCAL_PATH+fileName+".jpg";
		logger.info("获取本地生成的图片路径--->"+filepath);
		createImage(filepath);
		return filepath;
	}
}
