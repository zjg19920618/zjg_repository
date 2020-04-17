package com.boomhope.Bill.monitor.sendInterface;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.boomhope.Bill.Util.JsonUtil;
import com.boomhope.Bill.monitor.bean.MonitorResBean;
import com.boomhope.Bill.monitor.bean.ReqMCM001;

/**
 * 发送流水信息请求
 * @author hk
 *
 */

@SuppressWarnings("rawtypes")
public class MakeMonitorInfo {
	static Logger logger = Logger.getLogger(MakeMonitorInfo.class);
	private static MonitorResBean resBean;

	public static MonitorResBean makeInfos(ReqMCM001 reqInfos){
		final ReqMCM001 req = reqInfos;
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				try {
					//将请求报文转换成json字符串
					String dateTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
					req.setSenddate(dateTime.substring(0,8));//发送日期
					req.setSendtime(dateTime.substring(8));//发送时间
					String reqInfos = JsonUtil.toStr(req);
					logger.info("转换成的json字符串信息："+reqInfos);
					
					//生成数字前缀
					String sumStr = StringUtils.leftPad(String.valueOf(reqInfos.getBytes().length), 16, " ");
					logger.info("生成的长度字符串："+sumStr);
					
					//上送流水信息
					Map resultMap = SendInterfaceMsg.sendMsg(sumStr+reqInfos.trim());
					
					//返回的结果信息
					String resCode = (String)resultMap.get("resCode");
					logger.info("resCode:"+resCode);
					if(resCode!=null && "000000".equals(resCode)){
						
						String resMsg = (String)resultMap.get("resMsg");
						logger.info("上送流水结果信息："+resMsg);
						
						
						String jsonMsg = resMsg.substring(resMsg.indexOf("{"));
						logger.info("json信息："+jsonMsg);
						
						//将json字符串转换成返回信息实体类
						JSONObject jsonObject = JSONObject.fromObject(jsonMsg);
						resBean = (MonitorResBean)JSONObject.toBean(jsonObject,MonitorResBean.class);
						System.out.println("5454545||"+resBean.getDeviceno());
					}else{
						logger.info("上送失败");
					}
				} catch (Exception e) {
					logger.error("上送流水信息异常："+e);
				}
				// TODO Auto-generated method stub
				
			}
		}).start();
		return resBean;
		
	}
}
