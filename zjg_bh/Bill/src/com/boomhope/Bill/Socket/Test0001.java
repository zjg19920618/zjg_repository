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
import com.boomhope.tms.message.in.bck.BCK0001ResBean;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class Test0001 {

	/**
	 * 预计授权认证查询
	 * @param args
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public static void main(String[] args) throws UnknownHostException, IOException {
		Map<String,String> map = new HashMap();
		map.put("idCardNo", "130221197012016311");
		map.put("custName", "金继存");
		SocketClient sc = new SocketClient();
		try {
			Socket socket = sc.createSocket();
            //构建IO  
            InputStream is = socket.getInputStream();  
            OutputStream os = socket.getOutputStream();  
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));  
            //向服务器端发送一条消息  
            bw.write(sc.BCK_0001(map) + "\n");  
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
			reqXs.alias("Root", BCK0001ResBean.class);
			BCK0001ResBean bck0002ResBean = (BCK0001ResBean)reqXs.fromXML(retMsg);
			System.out.println(bck0002ResBean);
			System.out.println("CLIENT retMsg:" + retMsg);
			
			String KEY_FTP_IP="198.1.246.206";
			String KEY_FTP_PORT="21";
			String KEY_FTP_USER="ftp1";
			String KEY_FTP_PWD="ftp1";
			String KEY_FTP_REMOTE_PATH="";
			String KEY_FTP_LOCAL_PATH="d://abc";
			String fileName = bck0002ResBean.getBody().getFILE_NAME();
			FtpFileUtils.downloadFileByFTPClient(KEY_FTP_IP, Integer.parseInt(KEY_FTP_PORT), KEY_FTP_USER, KEY_FTP_PWD, KEY_FTP_REMOTE_PATH + fileName, KEY_FTP_LOCAL_PATH + fileName);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
}
