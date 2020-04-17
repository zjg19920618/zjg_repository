package com.boomhope.Bill.peripheral.action;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Public.GlobalDriver;


public class SocketClient {

	static Logger logger = Logger.getLogger(SocketClient.class);
	public static String Msg(String msg) throws UnknownHostException, IOException{

		Socket socket = null;
		OutputStream os = null; 
		InputStream is =null;  
		logger.info("发送信息："+msg);
		try {
			socket = new Socket(GlobalDriver.Driver_IP, GlobalDriver.Driver_PORT);
			os = socket.getOutputStream(); 
			os.write(msg.getBytes());
			is = socket.getInputStream();  
			byte[] buffer = new byte[1024];
			is.read(buffer);
			String result = new String(buffer, "GBK");
			logger.info("接受信息："+result);
			return result;
		} catch (UnknownHostException e) {
			logger.error(e);
			throw new UnknownHostException();
		}catch (IOException e) {
			logger.error(e);
			throw new IOException();
		}finally{
			if(is!=null){
				is.close();
			}
			if(os!=null){
				os.close();
			}
			if(socket!=null){
				socket.close();
			}
		}

	}
}
