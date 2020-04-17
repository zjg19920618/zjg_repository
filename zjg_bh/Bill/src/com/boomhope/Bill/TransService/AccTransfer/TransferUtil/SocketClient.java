package com.boomhope.Bill.TransService.AccTransfer.TransferUtil;
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

import com.boomhope.Bill.Util.Property;


/**
 * Socket管理工具类
 * @author wang.xm
 *
 */
public class SocketClient {
	
	static Logger logger = Logger.getLogger(SocketClient.class);
	
	private Socket socket;
	private InputStream is=null;
	private OutputStream os=null;
	/**
	 * 创建Soctet
	 * @return
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public void createSocket() throws UnknownHostException, IOException{
		logger.info("连接ip："+Property.BP_IP);
		socket =  new Socket(Property.BP_IP, Property.BP_PORT);
		logger.info("连接ip："+Property.BP_IP+"成功！");
		//设置超时时间
        socket.setSoTimeout(Property.TIME_OUT); 
	}
	
	/**
	 * 发送报文同时获得响应报文
	 * @param requestMsg
	 * @param encoding
	 * @return responseMsg
	 * @throws IOException 
	 * @throws UnknownHostException
	 */
	public String sendMesg(String requestMsg,String encoding)throws UnknownHostException, IOException{
		BufferedWriter bw=null;
		BufferedReader br=null;
		try {
			//构建IO  
	        is = socket.getInputStream();  
	        os = socket.getOutputStream();
            bw = new BufferedWriter(new OutputStreamWriter(os, encoding));  
            logger.info("Send request Message:"+requestMsg);
            //向服务器端发送一条消息  
            bw.write(requestMsg + "\n");  
            //清楚缓存
            bw.flush();  
            //读取服务器返回的消息  
            br = new BufferedReader(new InputStreamReader(is, encoding));  
			
			String retMsg = "";
			String value = null;
			//读取信息
			while ((value = br.readLine()) != null) {
				retMsg += value + "\n";
				if ("</Root>".equals(value)){
					break;
				}
			}
			logger.info("Receive response Message:"+retMsg);
			return retMsg;
		}catch (UnknownHostException e) {
			logger.error(e);
			throw new UnknownHostException(e.getMessage());
		} catch (IOException e) {
			logger.error(e);
			throw new IOException(e);
		}finally{
			//一定要及时关闭各种流释放资源
			try {
				if(bw!=null){
					bw.close();
				}
				if(br!=null){
					br.close();
				}
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
				logger.error(e2);
			}
		}
	}
	
}