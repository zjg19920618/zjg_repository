package com.boomhope.Bill.peripheral.action;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.boomhope.Bill.Public.GlobalDriver;
import com.boomhope.Bill.peripheral.bean.ICBankCheckStatusBean;
import com.boomhope.Bill.peripheral.bean.ICBankQuitBean;
import com.boomhope.Bill.peripheral.bean.ICBankReadBean;
import com.boomhope.Bill.peripheral.bean.ICBankReadBean55;

/**
 * 
 * Title:银行卡读卡器
 * Description:
 * @author mouchunyue
 * @date 2016年9月20日 上午9:31:08
 */
public class ICBank {
	static Logger logger = Logger.getLogger(ICBank.class);
	//由于socket是堵塞机制，故支持杀线程
	public static Socket socket = null;

	/**
	 * 读银行卡
	 * send:设备号|序列号|6|优先级|超时时间|标签1&标签2&标签3&……&
	 * result:错误码|序列号|6|错误信息
	 * 这里默认是    等效二磁数据 57
	 * @param code 序列号
	 * @return
	 */
	public static ICBankReadBean ICBankRead(String code) throws Exception{
		OutputStream os = null; 
		InputStream is =null;  
		String msg="202|"+code+"|6|0|60|5A&";
		logger.info("发送信息："+msg);
		try {
			socket = new Socket(GlobalDriver.Driver_IP, GlobalDriver.Driver_PORT);
			socket.setKeepAlive(true);
			os = socket.getOutputStream(); 
			os.write(msg.getBytes());
			is = socket.getInputStream();  
			byte[] buffer = new byte[1024];
			is.read(buffer);
			String result = new String(buffer, "GBK");
			logger.info("接受信息："+result);
			if(StringUtils.isNotBlank(result.trim())){
				String[] data = result.split("\\|");
				ICBankReadBean icBankReadBean = new ICBankReadBean();
				icBankReadBean.setStatus(data[0]);
				icBankReadBean.setCode(data[1]);
				icBankReadBean.setNum(data[2]);
				if(data[0].equals("0")){
					String idCode = data[3].substring(0, data[3].length()-2);
					icBankReadBean.setIcBankCode(idCode);
				}
				return icBankReadBean;
			}
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
		return null;
	}
	
	/**
	 * 读银行卡55域数据 
	 * send:设备号|序列号|10|优先级|超时时间|55域用法&交易类型&授权金额&其他金额&接口设备序列号&终端应用版本号&终端国家代码&交易序列计数器&发卡行认证数据&发卡行脚本1&发卡行脚本2&
	 * result:成功： 0|序列号|10|银行卡号&二磁数据&卡有效期(YYMMDD) &卡序列号&本地交易日
	 * 		      失败:	错误码|序列号|10|错误信息
	 * 这里默认是    等效二磁数据 57
	 * @param code 序列号
	 * @return
	 */
	public static ICBankReadBean55 ICBankRead55() throws Exception{
		OutputStream os = null; 
		InputStream is =null;  
		String msg="202|13|10|0|60|1&99&&&&&0156&&&&&";
		logger.info("发送信息："+msg);
		try {
			socket = new Socket(GlobalDriver.Driver_IP, GlobalDriver.Driver_PORT);
			socket.setKeepAlive(true);
			os = socket.getOutputStream(); 
			os.write(msg.getBytes());
			is = socket.getInputStream();  
			byte[] buffer = new byte[1024];
			is.read(buffer);
			String result = new String(buffer, "GBK");
			logger.info("接受信息："+result);
			if(StringUtils.isNotBlank(result.trim())){
				String[] data = result.split("\\|");
				ICBankReadBean55 icBankReadBean55 = new ICBankReadBean55();
				icBankReadBean55.setStatus(data[0]);
				icBankReadBean55.setCode(data[1]);
				icBankReadBean55.setNum(data[2]);
				if(data[0].equals("0")){
					String[] ICBankInfo = data[3].split("&");
					icBankReadBean55.setIcBankCode(ICBankInfo[0]);
					icBankReadBean55.setErCiData(ICBankInfo[1]);
					icBankReadBean55.setCardValidity(ICBankInfo[2]);
					icBankReadBean55.setCardNo(ICBankInfo[3]);
					icBankReadBean55.setTransDate(ICBankInfo[4]);
					icBankReadBean55.setTransTime(ICBankInfo[5]);
					icBankReadBean55.setData55(ICBankInfo[6]);
					
				}else{
					icBankReadBean55.setStatusDesc(data[3]);
				}
				return icBankReadBean55;
			}
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
		return null;
	}
	
	/**
	 * 检查设备状态
	 * send:设备号|序列号|999|优先级|超时时间|
	 * result:0|序列号|999|接触式卡座状态&非接触式卡座状态&SAM卡座状态&刷卡器状态&
	 * @param code 序列号
	 */
	public static ICBankCheckStatusBean checkStatus(String code) throws Exception{
		String result = SocketClient.Msg("202|"+code+"|999|0|30|").trim();
		String[] data = result.split("\\|");
		ICBankCheckStatusBean icBankCheckStatusBean = new ICBankCheckStatusBean();
		icBankCheckStatusBean.setStatus(data[0]);
		icBankCheckStatusBean.setCode(data[1]);
		icBankCheckStatusBean.setNum(data[2]);
		if(data[0].equals("0")){
			String[] status = data[3].split("&");
			icBankCheckStatusBean.setTouchStatus(status[0]);
			icBankCheckStatusBean.setNotTouchStatus(status[1]);
			icBankCheckStatusBean.setSamStatus(status[2]);
			icBankCheckStatusBean.setPosStatus(status[3]);
		}else {
			icBankCheckStatusBean.setStatusDesc(data[4]);
		}
		return icBankCheckStatusBean;
	}
	
	/**
	 * 同时取消读卡和拔卡通知
	 * send:设备号|序列号|3|优先级|超时时间|
	 * result:0|序列号|3|
	 * @param code 序列号
	 */
	public static void cancelReadBankCard(String code) throws Exception{
		String result = SocketClient.Msg("202|"+code+"|3|0|30|").trim();
		String[] data = result.split("\\|");
		if(!data[0].equals("0")){
			throw new Exception("取消操作失败！");
		}
	}
	
	/**
	 * 退出银行卡
	 * send:
	 * result:
	 * @param code 序列号
	 * @param time 吞卡时间
	 */
	public static ICBankQuitBean ICBankQuit(String code,String time) throws Exception{
		String result = SocketClient.Msg("202|"+code+"|9|0|120|"+time).trim();
		String[] data = result.split("\\|");
		ICBankQuitBean icBankQuitBean = new ICBankQuitBean();
		icBankQuitBean.setStatus(data[0]);
		icBankQuitBean.setCode(data[1]);
		icBankQuitBean.setNum(data[2]);
		icBankQuitBean.setStatusDesc(data[3]);
		return icBankQuitBean;
	}
	public static void main(String[] args) throws Exception {
		ICBank bank = new ICBank();
		ICBankReadBean icBankRead = bank.ICBankRead("39");
		logger.debug(icBankRead.getStatus());
		logger.debug(icBankRead.getCode());
		logger.debug(icBankRead.getNum());
		logger.debug(icBankRead.getIcBankCode());
	}
}
