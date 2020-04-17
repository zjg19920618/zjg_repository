package com.boomhope.Bill.peripheral.action;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.boomhope.Bill.Public.GlobalDriver;
import com.boomhope.Bill.peripheral.bean.IdCardCancelReadBean;
import com.boomhope.Bill.peripheral.bean.IdCardReadBean;
import com.boomhope.Bill.peripheral.bean.IdCartCheckStatusBean;

/**
 * 
 * Title:身份证读卡器
 * Description:
 * @author mouchunyue
 * @date 2016年9月19日 下午5:31:07
 */
public class IdCard {
	//由于socket是堵塞机制，故支持杀线程
	public static Socket socket = null;
	static Logger logger = Logger.getLogger(IdCard.class);
	/**
	 * 状态检查
	 * send:设备号|序列号|4|优先级|超时时间|
	 * result:0|序列号|4|设备状态值|
	 * @param code 序列号
	 * @return
	 */
	public static  IdCartCheckStatusBean checkStatus(String code) throws Exception{
		String result = SocketClient.Msg("201|"+code+"|999|0|30|").trim();
		String[] data = result.split("\\|");
		IdCartCheckStatusBean checkIdCartStatus = new IdCartCheckStatusBean();
		checkIdCartStatus.setId(data[0]);
		checkIdCartStatus.setCode(data[1]);
		checkIdCartStatus.setNum(data[2]);
		checkIdCartStatus.setStatus(data[3]);
		return checkIdCartStatus;
	}
	/**
	 * 读取身份证信息
	 * send:设备号|序列号|1|优先级|超时时间|
	 * result:0|序列号|1|姓名&#性别&#民族&#生日&#地址&#身份证号&#发证机关&#有效期开始&#有效期结束|照片绝对路径|正面扫描图绝对路径|反面扫描图绝对路径|
	 * @param code 序列号
	 */
	public static IdCardReadBean IdCardRead(String code) throws Exception{
		OutputStream os = null;
		InputStream is = null;
		try {
			socket = new Socket(GlobalDriver.Driver_IP, GlobalDriver.Driver_PORT);
			socket.setKeepAlive(true);
			os = socket.getOutputStream(); 
			String reqMsg="201|"+code+"|1|0|60|";
			os.write(reqMsg.getBytes());
			is = socket.getInputStream();
			byte[] buffer = new byte[1024];
			is.read(buffer);
			String result = new String(buffer, "GBK").trim();
			if(StringUtils.isNotBlank(result.trim())){
				String[] data = result.split("\\|");
				IdCardReadBean cardBean = new IdCardReadBean();
				cardBean.setStatus(data[0]);
				cardBean.setCode(data[1]);
				cardBean.setNum(data[2]);
				if(data[0].equals("0")){
					cardBean.setPhotoPath(data[4]);
					cardBean.setFrontFace(data[5]);
					cardBean.setBackFace(data[6]);
					String person = data[3];
					String []people = person.split("&#");
					cardBean.setName(people[0]);
					cardBean.setSex(people[1]);
					cardBean.setNationality(people[2]);
					cardBean.setBirthday(people[3]);
					cardBean.setAddress(people[4]);
					cardBean.setIdCode(people[5]);
					cardBean.setIssuingUnit(people[6]);
					cardBean.setStartDate(people[7]);
					cardBean.setEndDate(people[8]);
				}else{
					cardBean.setStatusDesc(data[3]);
				}
				return cardBean;
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
	 * 取消读取身份证
	 * send:设备号|序列号|2|优先级|超时时间|
	 * result:0|序列号|2
	 * @param num 序列号
	 */
	public static IdCardCancelReadBean idCardCancelRead(String code) throws Exception{
		String result = SocketClient.Msg("201|"+code+"|2|0|120|").trim();
		String[] data = result.split("\\|");
		IdCardCancelReadBean cancelReadIdCardBean = new IdCardCancelReadBean();
		cancelReadIdCardBean.setStatus(data[0]);
		cancelReadIdCardBean.setCode(data[1]);
		cancelReadIdCardBean.setNum(data[2]);
		cancelReadIdCardBean.setStatusDesc(data[3]);
		return cancelReadIdCardBean;
	}
	public static void main(String[] args) throws Exception {
		IdCard code = new IdCard();
		IdCardCancelReadBean cancelReadIdCard = code.idCardCancelRead("30");
		logger.debug(cancelReadIdCard);
	}
}
