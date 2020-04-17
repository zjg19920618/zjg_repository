package com.boomhope.tms.socket;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.apache.log4j.Logger;

import com.boomhope.tms.transaction.ConfigReader;
import com.boomhope.tms.util.MACProtocolUtils;

/***
 * socketͨѶ��
 * @author shaopeng
 *
 */
public class SocketClient {
	Logger logger = Logger.getLogger(SocketClient.class);
	
	/***
	 * ���ͽ�������
	 * @param reqMsg ������
	 * @return ��Ӧ����
	 * @throws Exception 
	 */
	public String sendMsg(byte[] reqMsg) throws Exception{
		Socket socket = null;
		InputStream is =null;
		OutputStream os = null;
		try {
			logger.info("开始连接前置交易接口");
			logger.info("SOCKET"+ConfigReader.getConfig("SocketIp")+"开始连接");
			socket = new Socket(ConfigReader.getConfig("SocketIp"),Integer.valueOf(ConfigReader.getConfig("SocketPort")));
			logger.info("SOCKET--ip："+ConfigReader.getConfig("SocketIp")+"连接成功");
			logger.info("SOCKET--port："+ConfigReader.getConfig("SocketPort")+"连接成功");
			socket.setSoTimeout(Integer.valueOf(ConfigReader.getConfig("SocketTimeOut")));
			
			 //向前置发送报文
            is = socket.getInputStream();  
            os = socket.getOutputStream();  
            
            os.write(reqMsg);
            byte[] res = MACProtocolUtils.toResponse(is);
            String result = new String(res,"GBK");
			return result;
		} catch (Exception e) {
			logger.error("socket连接前置异常", e);
			throw new Exception("socket连接前置异常", e);
		}finally{
			 socket.close();
	            os.close();
	            is.close();
		}
	}
	
	/**
	 * 连接前置综合服务端端口号，向前置发送报文
	 * @param reqMsg
	 * @return
	 * @throws Exception
	 */
	public String sendNewServiceMsg(byte[] reqMsg) throws Exception{
		Socket socket = null;
		InputStream is =null;
		OutputStream os = null;
		try {
			logger.info("开始连接综合服务端接口");
			logger.info("SOCKET"+ConfigReader.getConfig("SocketIp")+"开始连接");
			socket = new Socket(ConfigReader.getConfig("SocketIp"),Integer.valueOf(ConfigReader.getConfig("ServiceSocketPort")));
			
			logger.info("SOCKET--ip："+ConfigReader.getConfig("SocketIp")+"连接成功");
			logger.info("SOCKET--port："+ConfigReader.getConfig("ServiceSocketPort")+"连接成功");
			socket.setSoTimeout(Integer.valueOf(ConfigReader.getConfig("SocketTimeOut")));
			
            //向前置发送报文
            is = socket.getInputStream();  
            os = socket.getOutputStream();  
            
            os.write(reqMsg);
            byte[] res = MACProtocolUtils.toResponse(is);
            String result = new String(res,"GBK");
			return result;
		} catch (Exception e) {
			logger.error("socket连接综合服务端前置异常", e);
			throw new Exception("socket连接综合服务端前置异常", e);
		}finally{
			 socket.close();
	         os.close();
	         is.close();
		}
	}
}
