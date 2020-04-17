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
import com.boomhope.tms.message.in.bck.BCK0005ResBean;
import com.boomhope.tms.message.in.bck.BCK0006ResBean;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class Test0006 {
	/**
	 * 糖豆回
	 * @param args
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public static void main(String[] args) throws UnknownHostException, IOException {
		Map<String,String> map = new HashMap();
		map.put("ACCT_NO", "051000129001001199984");
		map.put("BACK_COUNT", "25000");
		map.put("BACK_EXCHANGE_AMT", "50");
		map.put("BACK_PROP", "");
		map.put("OPPO_ACCT_NO", "");
		map.put("ORG_EXCHANGE_MODE", "");
		map.put("ORG_EXCHANGE_PROP", "");
		map.put("PASSWORD", "");
		map.put("PAY_AMT", "20000.00");
		map.put("PAY_DATE", "20160921");
		map.put("PAY_JRNL", "3530");
		map.put("RECOV_TYPE", "0");
		map.put("SUB_ACCT_NO", "");
		map.put("TradeCode", "BCK_0006");
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
            bw.write(sc.BCK_0006(map) + "\n");  
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
			reqXs.alias("Root", BCK0006ResBean.class);
			BCK0006ResBean bCK0006ResBean = (BCK0006ResBean)reqXs.fromXML(retMsg);
			System.out.println(bCK0006ResBean);
			System.out.println("CLIENT retMsg:" + retMsg);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
