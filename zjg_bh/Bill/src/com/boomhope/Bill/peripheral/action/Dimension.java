package com.boomhope.Bill.peripheral.action;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Public.GlobalDriver;
import com.boomhope.Bill.peripheral.bean.DimensionCheckStatusBean;
import com.boomhope.Bill.peripheral.bean.DimensionReadBean;


/**
 * 
 * Title:二维码扫描仪
 * Description:
 * @author mouchunyue
 * @date 2016年9月19日 下午5:31:07
 */
public class Dimension {
	
	static Logger logger = Logger.getLogger(Dimension.class);
	//由于socket是堵塞机制，故支持杀线程
	public static Socket socket = null;
	/**
	 * 状态检查
	 * send:设备号|序列号|4|优先级|超时时间|
	 * result:0|序列号|4|设备状态值|
	 * @param code 序列号
	 * @return
	 */
	public static  DimensionCheckStatusBean checkStatus(String code) throws Exception{
		String result = SocketClient.Msg("209|"+code+"|999|0|30|").trim();
		String[] data = result.split("\\|");
		DimensionCheckStatusBean checkIdCartStatus = new DimensionCheckStatusBean();
		checkIdCartStatus.setId(data[0]);
		checkIdCartStatus.setCode(data[1]);
		checkIdCartStatus.setNum(data[2]);
		checkIdCartStatus.setStatus(data[3]);
		return checkIdCartStatus;
	}
	/**
	 * 扫描二维码信息
	 * send:设备号|序列号|1|优先级|超时时间|
	 * result:
	 * @param code 序列号
	 */
	public static DimensionReadBean dimensionRead(String code) throws Exception{
		String msg="209|"+code+"|1|0|60|";
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
			String[] data = result.split("\\|");
			DimensionReadBean cardBean = new DimensionReadBean();
			cardBean.setStatus(data[0]);
			cardBean.setCode(data[1]);
			cardBean.setNum(data[2]);
			if(data[0].equals("0")){
				cardBean.setInfo(data[3]);
			}else{
				cardBean.setStatusDesc(data[3]);
			}
			return cardBean;
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
	/**
	 * 取消扫描二维码信息
	 * send:设备号|序列号|2|优先级|超时时间|
	 * result:0|序列号|2
	 * @param num 序列号
	 */
	public static void dimensionCancelRead(String code) throws Exception{
		String result = SocketClient.Msg("209|"+code+"|2|0|120|").trim();
		String[] data = result.split("\\|");
		if(!"0".equals(data[0])){
			throw new Exception("扫描仪关闭失败");
		}
	}
	public static void main(String[] args) throws Exception {
		Dimension code = new Dimension();
//		DimensionCancelReadBean cancelReadIdCard = code.dimensionCancelRead("30");
//		System.out.println(cancelReadIdCard);
	}
}
