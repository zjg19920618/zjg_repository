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
import com.boomhope.tms.message.in.bck.BCK0002ResBean;
import com.boomhope.tms.message.in.bck.BCK0005ReqBean;
import com.boomhope.tms.message.in.bck.BCK0005ResBean;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class Test0005 {
	/**
	 * 糖豆回收查询
	 * @param args
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public static void main(String[] args) throws UnknownHostException, IOException {
		Map<String,String> map = new HashMap();
		map.put("acctNo", "051000129001001199984");
		map.put("payDate", "20160921");
		map.put("payJrnl", "3530");
		map.put("subAcctNo", "");
		map.put("TradeCode", "BCK_0005");
//		map.put("machineNo", "我是机器编码");
		String retMsg = "";
		SocketClient sc = new SocketClient();
		try {
			Socket socket = sc.createSocket();
			
            //构建IO  
            InputStream is = socket.getInputStream();  
            OutputStream os = socket.getOutputStream();  

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));  
            //向服务器端发送一条消息  
            bw.write(sc.BCK_0005(map) + "\n");  
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
			reqXs.alias("Root", BCK0005ResBean.class);
			BCK0005ResBean bCK0005ResBean = (BCK0005ResBean)reqXs.fromXML(retMsg);
			System.out.println(bCK0005ResBean);
			System.out.println("CLIENT retMsg:" + retMsg);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
