package com.boomhope.Bill.TransService.AccTransfer.TransferUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;



/**
 * 序列流水号以天为周期
 * @author wxm
 *
 */
public class SequenceUtil {
	
	static Logger logger = Logger.getLogger(SequenceUtil.class);
	
	private static int id=0;
	
	private static String date="";
	
	/**
	 * 获取流水号
	 * @return
	 */
	public static String getSequencId(){
		try {
			SimpleDateFormat sdf=new SimpleDateFormat("YYYYMMdd");
			Date now =new Date();
			if("".equals(date)){
				date=sdf.format(now);
			}
			if(date.equals(sdf.format(now))){
				id++;
			}else{
				date=sdf.format(now);
				id=1;
			}
			return intToString(id);
		} catch (Exception e) {
			logger.info("获取序列号异常："+e);
			return intToString((id++));
		}
	}
	
	/**
	 * 转换int成String
	 * @return
	 */
	private static String intToString(int id){
		String str="";
		int length=5-(""+id).length();
		for (int i = 0; i < length; i++) {
			str+="0";
		}
		str+=id;
		logger.info("获取的序列号："+str);
		return str;
	}
	
	public static void main(String[] args) throws InterruptedException {
//		for (int i = 0; i < 10; i++) {
//			System.out.println(SequenceUtil.getSequencId());
//		}
		
		while(true){
			System.out.println(SequenceUtil.getSequencId());
			Thread.sleep(1000);
		}
	}
}
