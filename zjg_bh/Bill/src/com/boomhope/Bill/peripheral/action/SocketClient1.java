package com.boomhope.Bill.peripheral.action;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketClient1 {

	@SuppressWarnings("resource")
	public static String Msg(String msg) throws UnknownHostException, IOException{

		Socket socket = new Socket("127.0.0.1", 8999);
			 //构建IO  
		OutputStream os = socket.getOutputStream(); 
		os.write(msg.getBytes("GBK"));
		InputStream is = socket.getInputStream();  
		byte[] buffer = new byte[1024];
		is.read(buffer);
		String result = new String(buffer, "GBK");
		socket.close();
		os.close();
		is.close();
		return result;

	}
}
