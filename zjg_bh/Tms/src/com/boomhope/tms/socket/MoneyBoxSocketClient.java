package com.boomhope.tms.socket;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.apache.log4j.Logger;

import com.boomhope.tms.transaction.ConfigReader;
import com.boomhope.tms.util.Property;

/**
 * 请求钱柜的socket
 *
 */
public class MoneyBoxSocketClient {
	Logger logger = Logger.getLogger(MoneyBoxSocketClient.class);
	
	public String sendMsg(byte[] reqMsg) throws Exception{
		Socket socket = null;
		InputStream is =null;
		OutputStream os = null;
		ByteArrayOutputStream out=null;
		try {
			logger.info("socket:"+ConfigReader.getConfig("moneyBoxSocketIp")+",开始链接");
			socket = new Socket(ConfigReader.getConfig("moneyBoxSocketIp"),Integer.valueOf(ConfigReader.getConfig("moneyBoxSocketPort")));
			socket.setSoTimeout(Integer.valueOf(ConfigReader.getConfig("moneyBoxSocketTimeOut")));
//			Socket socket = new Socket("198.1.1.139", 10002);
			logger.info("socket:"+ConfigReader.getConfig("moneyBoxSocketIp")+",链接成功");
            //����IO  
            is = socket.getInputStream();
            os = socket.getOutputStream();  
//            logger.info("钱柜发送日志*********"+reqMsg);
            os.write(reqMsg);
            out = new ByteArrayOutputStream();
    		while (!out.toString().endsWith("</xBankBass>")) {
    			out.write(is.read());
    		}
    		byte[] bytes = out.toByteArray();
    		String result = new String(bytes,"GBK");
//    		 logger.info("钱柜返回日志*********"+result);
    		return result;
		} catch (Exception e) {
			logger.error("socket连接钱柜异常", e);
			throw new Exception("socket连接钱柜异常", e);
		}finally{
			out.close();
    		os.close();
    		is.close();
    		socket.close();
		}
	}
	public static void main(String[] args)throws Exception {
		new MoneyBoxSocketClient().sendMsg(null);
	}
}
