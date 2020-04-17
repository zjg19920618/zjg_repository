package com.boomhope.Bill.Util;



import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.boomhope.Bill.TransService.AccTransfer.Bean.TransferSelectBean;
import com.jcraft.jsch.ChannelSftp;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/*
 * 生成JPG工具类
 */
public class JpgUtil_HYCancel {
	
	BufferedImage image;
	
	static Logger logger = Logger.getLogger(JpgUtil_HYCancel.class);
	
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
	 * cancelId 撤销流水
	 * cancelDate 撤销日期
	 *transMoney 汇款金额
	 *wtDate 委托日期
	 *zrNo 转入帐号
	 *zrName 转入户名
	 *payeeHbrName 收款人开户行名
	 *CancelResult 撤销结果
	 *zcName		付款人户名
	 *zcNo 付款人卡号
	 *failCause 失败原因
	 * @param map
	 * @throws IOException 
	 */
	public String graphicsGeneration(Map map) throws IOException {
		logger.info("开始生成事后监管图片。");
		int	imageWidth = 1200;// 图片的宽度
		int	imageHeight = 1500;// 图片的高度
		
		image = new BufferedImage(imageWidth, imageHeight,BufferedImage.TYPE_INT_RGB);
		logger.info("创建graphics对象-------------------");
		Graphics graphics = image.getGraphics();
		//设置背景颜色
		logger.info("设置背景颜色！");
		graphics.setColor(Color.WHITE);
		graphics.setFont(new Font("楷体", Font.PLAIN, 18));
		graphics.fillRect(0, 0, imageWidth, imageHeight);
		graphics.setColor(Color.BLACK);
		graphics.drawString("撤销记录 " , 150, 100);
		graphics.drawString("客户签字" , 150, 780);	
		
		logger.info("向graphics对象中交易信息------");
		List<TransferSelectBean> list=(List<TransferSelectBean>) map.get("list");
		for (int i=0;i<list.size();i++) {
			
			graphics.drawString("撤销流水号 : " + list.get(i).getCancelId(), 275, 27+216*i);
			//操作日期
			String date1=list.get(i).getWtDate();
			if(date1!=null&&!"".equals(date1)){
				try {
					date1=DateUtil.getDate(date1, "yyyyMMdd");
				} catch (ParseException e1) {
					logger.error(e1);
				}
			}else{
				date1="";
			}
			graphics.drawString("撤销日期 : " +date1, 675, 27+216*i);
			graphics.drawString("转出户名 : " + list.get(i).getZcName(), 275, 54+216*i);
			graphics.drawString("转入户名: " + list.get(i).getZrName(), 275, 81+216*i);
			graphics.drawString("转出账号: " + list.get(i).getZcNo(), 275,108+216*i);
			graphics.drawString("转入帐号: " + list.get(i).getZrNo(), 675, 108+216*i);
			//操作日期
			String date=list.get(i).getWtDate();
			if(date!=null&&!"".equals(date)){
				try {
					date=DateUtil.getDate(date, "yyyyMMdd");
				} catch (ParseException e1) {
					logger.error(e1);
				}
			}else{
				date="";
			}
			graphics.drawString("委托日期: " +date , 275, 135+216*i);
			graphics.drawString("转入开户行: " + list.get(i).getPayeeHbrName(), 275, 162+216*i);
			graphics.drawString("转入金额: " + list.get(i).getTransMoney(), 275, 189+216*i);
			graphics.drawString("撤销结果: " + list.get(i).getCancelResult(), 675, 135+216*i);
			String cause=list.get(i).getFailCause();
			if(cause!=null&&!"".equals(cause)){
				
			}else{
				cause="无";
			}
			graphics.drawString("失败原因: " +cause , 675, 189+216*i);
			graphics.drawString("-------------------------------------------------------------------" , 275, 197+216*i);
		}
		
		// 改成这样:
		BufferedImage bimg4 = null;
		logger.info("开始读取各种照片---------------");
		if(!"".equals(Property.getProperties().getProperty("SIGNATURE_PATH_CANCEL")) && null != Property.getProperties().getProperty("SIGNATURE_PATH_CANCEL")){
			logger.info("客户签字");
			//客户签字
			bimg4 = javax.imageio.ImageIO.read(new java.io.File(Property.getProperties().getProperty("SIGNATURE_PATH_CANCEL")));
			//客户签字
			graphics.drawImage(bimg4, 270, 807, 780,350, null);	
		}	
		
		logger.info("读取各种照片结束-------");
		logger.info("开始插入各种照片-------");
		logger.info("插入各种照片结束-----------");
		graphics.dispose();
		String filepath=(String) map.get("path");
		createImage(filepath);
		return filepath;
	}



	public static void main(String[] args) throws IOException {
		Property.initProperty();
		List<TransferSelectBean> infos=null;
		infos=new ArrayList<TransferSelectBean>();
		TransferSelectBean bean1=new TransferSelectBean();
		bean1.setZrNo("6227000291010216965");
		bean1.setZrName("郝华静");
		bean1.setWtDate("20170504");
		bean1.setPayeeHbrName("山西省阳泉市学院路支行");
		bean1.setTransMoney("200.00");
		bean1.setCancelDate("20170508");
		bean1.setCancelResult("成功");
		bean1.setFailCause("");
		bean1.setCancelId("0015");
		TransferSelectBean bean2=new TransferSelectBean();
		bean2.setZrNo("6227000291010216966");
		bean2.setZrName("黄凯");
		bean2.setWtDate("20170504");
		bean2.setPayeeHbrName("中国建设银行");
		bean2.setTransMoney("200.00");
		bean2.setCancelDate("20170508");
		bean1.setCancelResult("成功");
		bean1.setFailCause("");
		bean1.setCancelId("0015");
		TransferSelectBean bean3=new TransferSelectBean();
		bean3.setZrNo("6227000291010216965");
		bean3.setZrName("王晓明");
		bean3.setWtDate("20170504");
		bean3.setPayeeHbrName("中国农业银行");
		bean3.setTransMoney("200.00");
		bean3.setCancelDate("20170508");
		bean1.setCancelResult("成功");
		bean1.setFailCause("");
		bean1.setCancelId("0015");
		infos.add(bean1);
		infos.add(bean2);
		infos.add(bean3);
		ChannelSftp sftp = null;
		Map map=new HashMap();
		map.put("list", infos);
		map.put("path", "D://bill/201705120012.jpg");
		String nowDate = DateUtil.getNowDate("yyyyMMdd");
		String ftpPath = "/home/abc/"+nowDate;
		JpgUtil_HYCancel jp=new JpgUtil_HYCancel();
		jp.graphicsGeneration(map);
	
	}
	
}
