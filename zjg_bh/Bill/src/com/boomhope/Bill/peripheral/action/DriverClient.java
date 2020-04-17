package com.boomhope.Bill.peripheral.action;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;


/***
 * 驱动请求客户端
 * @author shaopeng
 *
 */
public class DriverClient {
	static Logger logger = Logger.getLogger(DriverClient.class);
	/***
	 * 密码键盘通用处理
	 * @param reqMsg
	 * @return
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public String doKeypad(String reqMsg) throws UnknownHostException, IOException{
		
		logger.info("reqMsg:" + reqMsg);

		//构建IO  
		Socket socket = null;
		OutputStream os =null;
		InputStream is = null;
		try {
			socket = new Socket(KeypadDriver.Driver_IP, KeypadDriver.Driver_PORT);
			os = socket.getOutputStream(); 
			os.write(reqMsg.getBytes());
			is = socket.getInputStream();  
			byte[] buffer = new byte[1024];
			is.read(buffer);
			String result = new String(buffer, "GBK");
			logger.info("resMsg:" + result);
			return result;

		}catch (UnknownHostException e) {
			logger.error(e);
			throw new UnknownHostException();
		}catch(IOException e1){
			logger.error(e1);
			throw new IOException();
		}finally{
			if(os!=null){
				os.close();
			}
			if(is!=null){
				is.close();
			}
			if(socket!=null){
				socket.close();
			}
		}
	}
}
