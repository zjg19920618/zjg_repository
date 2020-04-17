package com.boomhope.Bill.TransService.CashOpen.Interface;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Socket.SocketClient;

/***
 * socket
 * @author 
 *
 */
public class CDJSocketClient {
	Logger logger = Logger.getLogger(CDJSocketClient.class);
	
	public String sendMsg(String str) throws Exception{
		
		String retMsg = "";
		SocketClient sc = new SocketClient();
		Socket socket=null;
		InputStream is=null;
		OutputStream os=null;
		try {
			 socket = sc.createSocket();
            //构建IO  
             is = socket.getInputStream();  
             os = socket.getOutputStream();

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));  
            //向服务器端发送一条消息  
//           bw.write(sc.BCK_0014(map) + "\n");  
            bw.write(str + "\n"); 
            bw.flush();  
            //读取服务器返回的消息  
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));  
			String value = null;
			while ((value = br.readLine()) != null) {
				retMsg += value + "\n";
				if ("</Root>".equals(value)){
					break;
				}
			}
			bw.close();
			br.close();
			logger.info(retMsg);
			return retMsg;
		} catch (UnknownHostException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		}finally{
			try {
				if(os!=null){
					os.close();
				}
				if(is!=null){
					is.close();
				}
				if(socket!=null){
					socket.close();
				}
			} catch (Exception e2) {
				logger.info(e2);
			}
		}
		return null;
	}
	public static void main(String[] args) {
		CDJSocketClient cdjSocketClient = new CDJSocketClient();
		try {
			cdjSocketClient.sendMsg("");
		} catch (Exception e) {
		}
	}
}
