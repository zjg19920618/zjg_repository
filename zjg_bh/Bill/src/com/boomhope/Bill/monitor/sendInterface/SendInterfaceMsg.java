package com.boomhope.Bill.monitor.sendInterface;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Socket.SocketClient;
import com.boomhope.Bill.Util.Property;

@SuppressWarnings({"rawtypes","unchecked"})
public class SendInterfaceMsg {
	static Logger logger = Logger.getLogger(SendInterfaceMsg.class);
	
	
	public static Map sendMsg(String str){
		Map resultMap = new HashMap();
		SocketClient sc = new SocketClient();
		Socket socket = null;
		InputStream is = null;
		OutputStream os = null;
		String retMsg = "";
		
		
		try {
			socket = sc.createSocket(Property.channel_monitor_ip,Integer.valueOf(Property.channel_monitor_port));
			socket.setSoTimeout(Property.channel_monitor_time_out);
			// 构建IO
			is = socket.getInputStream();
			os = socket.getOutputStream();
			
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os,"GBK"));
			logger.debug("请求报文："+str);
			// 向服务器端发送一条消息
			bw.write(str);
			bw.flush();
			socket.shutdownOutput();
			
			// 读取服务器返回的消息
			BufferedReader br = new BufferedReader(new InputStreamReader(is,"GBK"));
			
			String value = null;
			while ((value = br.readLine()) != null) {
				retMsg += value ;
			}
			bw.close();
			br.close();
			
		} catch (Exception e) {
			logger.error("发送信息失败："+e);
		}finally{
			if(os!=null){
				
				try {
					os.close();
				} catch (IOException e) {
					logger.error("关闭输出流失败："+e);
				}
			}
			if(is!=null){
				try {
					is.close();
				} catch (IOException e) {
					logger.error("关闭读入流失败："+e);
				}
							
			}	
			if(socket!=null){
				try {
					socket.close();
				} catch (IOException e) {
					logger.error("关闭socket失败："+e);
				}
			}
		}
		logger.info("返回报文："+retMsg);
		if(retMsg!=null && !"".equals(retMsg.trim())){
			logger.info("上送流水完成:"+retMsg);
			resultMap.put("resCode", "000000");
			resultMap.put("resMsg", retMsg);
		}else{
			logger.error("上送流水失败");
			resultMap.put("resCode", "4444");
			resultMap.put("resMsg", "上送流水失败");
			return resultMap;
		}
		return resultMap;
	}
}
