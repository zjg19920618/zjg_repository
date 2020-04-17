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

import com.boomhope.Bill.Util.FtpFileUtils;
import com.boomhope.tms.message.XStreamNameCoder;
import com.boomhope.tms.message.in.bck.BCK0004ResBean;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class Test0004 {

	/**
	 * 销户
	 * @param args
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public static void main(String[] args) throws UnknownHostException, IOException {
		Map<String,String> map = new HashMap();
		map.put("accNo", "051000129000600141441");// 账号
		map.put("billNo", "03826830");//存单号
		map.put("custNo", "113207805");//客户号
		map.put("amount", "300000.00");//本金
		map.put("idCardNo", "13028119871002204X");// 身份证号
		map.put("agentFlag", "1");//代理人标识
		map.put("agentIdCardNo", "220203198501125416");//代理人身份证
		map.put("agentIdCardName", "赵宇");//代理人名称
		map.put("agentaddress", "吉林");//代理人地址
		map.put("agentqfjg", "吉林");//代理人签发机关
		map.put("agentsex", "男");//代理人签发机关
		SocketClient sc = new SocketClient();
		try {
			Socket socket = sc.createSocket();
            //构建IO  
            InputStream is = socket.getInputStream();  
            OutputStream os = socket.getOutputStream();  
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));  
            //向服务器端发送一条消息  
            bw.write(sc.BCK_0004(map) + "\n");  
            bw.flush();  
            //读取服务器返回的消息  
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));  
			
			String retMsg = "";
			String value = null;
			while ((value = br.readLine()) != null) {
				retMsg += value + "\n";
				if ("</Root>".equals(value)){
					break;
				}
			}
			System.out.println(retMsg);
			XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			reqXs.alias("Root", BCK0004ResBean.class);
			BCK0004ResBean bck0002ResBean = (BCK0004ResBean)reqXs.fromXML(retMsg);
			System.out.println(bck0002ResBean);
			System.out.println("CLIENT retMsg:" + retMsg);
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
}
