package com.boomhope.Bill.Util;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import net.sf.json.JSONObject;

import com.boomhope.Bill.TransService.AccOpen.Util.Base64Util;
import com.boomhope.Bill.TransService.AccOpen.Util.HttpClientUtil;

/**
 * 人脸识别
 * @author hp
 *
 */
@SuppressWarnings({"rawtypes","unchecked"})
public class CompareFaces {
	private static Logger logger = Logger.getLogger(CompareFaces.class);
	
	public static Map compares(String idFacePath, String cameraPath) throws Exception{
		Map map = new HashMap();
		
		JSONObject json = new JSONObject();
		json.put("img1", Base64Util.GetImageStr(idFacePath));
		json.put("img1Type", "1");
		json.put("img2", Base64Util.GetImageStr(cameraPath));
		json.put("img2Type", "1");
		HttpClientUtil util = new HttpClientUtil();
		logger.debug("开始调用人脸识别接口");
		String result = util.post(Property.NEW_FACE_CHECK_SYS_PATH,json);
		logger.debug("执行结果：" + result);
		JSONObject obj = JSONObject.fromObject(result);
		String r = String.valueOf(obj.get("result"));
		// 人脸识别失败
		if (r.equals("0")) {
			String exCode = String.valueOf(obj.get("exCode"));
			String exMsg = String.valueOf(obj.get("exMsg"));
			logger.info("人脸识别失败原因："+exCode+"--"+exMsg);
			map.put("exCode", exCode);
			map.put("exMsg", exMsg);
			return map;
		}
		
		double sim = Double.parseDouble(String.valueOf(obj.get("sim")));
		double defaultSim = Double.parseDouble(String.valueOf(obj.get("defaultSim")));
		logger.info("阈值："+defaultSim+"||||"+"相似度:"+sim);
		if (sim >= defaultSim) {//sim >= defaultSim
			//当人脸识别成功之后则调用接口
			String sims = String.format("%.2f",Double.parseDouble(obj.getString("sim")));
			map.put("exCode", "000000");
			map.put("sims", sims);
			return map;
		}
		map.put("exCode", "999999");
		map.put("sims", sim);
		map.put("exMsg", "人脸识别相识度过低，无法通过。");
		return map;
	}
	
}
