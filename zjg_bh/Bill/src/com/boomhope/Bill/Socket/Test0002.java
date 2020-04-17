package com.boomhope.Bill.Socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import com.boomhope.tms.message.XStreamNameCoder;
import com.boomhope.tms.message.in.InResBean;
import com.boomhope.tms.message.in.bck.BCK0002ResBean;
import com.boomhope.tms.message.in.bck.BCK0002ResBodyBean;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class Test0002 {

	/**
	 * 账户信息查询
	 * @param args
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public static void main(String[] args) throws UnknownHostException, IOException {
		Map<String,String> map = new HashMap();
		map.put("accNo", "6231930000000902361");
		map.put("transCode", "BCK_0002");
		map.put("MacNo", "M001");
		
		String retMsg = "";
		SocketClient sc = new SocketClient();
		try {
			Socket socket = sc.createSocket();
			
            //构建IO  
            InputStream is = socket.getInputStream();  
            OutputStream os = socket.getOutputStream();  

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));  
            //向服务器端发送一条消息  
            bw.write(sc.BCK_0002(map) + "\n");  
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
			System.out.println(retMsg);
			XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			reqXs.alias("Root", BCK0002ResBean.class);
			BCK0002ResBean bck0002ResBean = (BCK0002ResBean)reqXs.fromXML(retMsg);
			System.out.println(bck0002ResBean);
			System.out.println("CLIENT retMsg:" + retMsg);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
