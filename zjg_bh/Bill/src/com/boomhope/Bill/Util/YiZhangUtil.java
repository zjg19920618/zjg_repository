package com.boomhope.Bill.Util;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import sun.misc.BASE64Decoder;

import com.boomhope.Bill.Public.GlobalParameter;

import net.sf.json.JSONObject;

public class YiZhangUtil {
	
	static Logger logger  = Logger.getLogger(YiZhangUtil.class);
	/**
	 * 生成事后电子印章
	 * @throws Exception
	 */
	public boolean getYiZhangPhoto(Map map)throws Exception{
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("channel", "0035");//渠道号
		jsonObject.put("fserialno", (String)map.get("fileName"));//日期+流水号
		jsonObject.put("instno", GlobalParameter.branchNo);//机构号
		jsonObject.put("tellerno", GlobalParameter.tellerNo);//柜员号
		jsonObject.put("chl_tran_code", "");//外围系统交易码
		jsonObject.put("chl_tran_name", "");//外围系统交易名称
		jsonObject.put("vouchername", "事后监督图片");//凭证名称
		jsonObject.put("sealtype", "0001");//电子印章种类（业务专用章）
		
		//拼接tradeinfo参数值
		jsonObject.put("tradeinfo", JSONObject.fromObject(map).toString());		
		
		//电子印章请求
		HttpClientUtil util=new HttpClientUtil();
		String result = util.httpPost(Property.dzyz_url, jsonObject);
		if(result==null || "-1".equals(result)){
			return false;
		}
		//电子印章base64编码转换图片
		boolean flag = GenerateImage(result, Property.dzyz_ml);
		return flag;
	}
	
	/**
	 * base64编码转换图片
	 * @param imgStr 	//base64编码的字符串
	 * @param rootPath	//保存的路径（图片完整路径）
	 * @return
	 */
	private boolean GenerateImage(String imgStr,String rootPath) {
		if (imgStr == null){// 图像数据为空
			logger.error("图像数据为空");
			return false;
		} 
			
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			// Base64解码
			byte[] b = decoder.decodeBuffer(imgStr);
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {// 调整异常数据
					b[i] += 256;
				}
			}
			OutputStream out = new FileOutputStream(rootPath);
			out.write(b);
			out.flush();
			out.close();
			return true;
		} catch (Exception e) {
			logger.error("base64编码转换图片失败"+e);
			return false;
		}
	}
}
