package com.boomhope.Bill.peripheral.action;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;



/***
 * 密码键盘驱动类
 * @author shaopeng
 *
 */
public class KeypadDriver {
	
	public static String Driver_IP = "127.0.0.1";	// 驱动平台地址
	public static int Driver_PORT = 8999;	// 驱动平台端口号

	public static final String KEYPAD_DEVICE_NO = "200";	// 密码键盘主设备号
	public static final String KEYPAD_SEQNO = "1";	// 暂时默认为"1"
	public static final String KEYPAD_PRIORITY = "0";	// 优先级(预留，暂时填"0")
	public static final String KEYPAD_OVERTIME = "120";	// 超时时间(默认为120秒)
	
	public static final String KEYPAD_PASSLENGTH = "6";	// 密码长度
	public static final String KEYPAD_ENCTYPE = "2";	// 加密方式 2-工作密钥3DES
	public static final String KEYPAD_PINTYPE = "1";	// pin运算算法 1-ISO9564-1格式0（ANSI X9.8）
	public static final String KEYPAD_PINFILL = "2";	// pin补位方式 2-右补FF
	public static final String KEYPAD_CARDNO = "052000125001000346330";	// 加密卡号 默认为16个0
	
	/***
	 * 获取版本信息
	 * @return 响应map
	 * 【响应map参数ResCode：返回代码(S-成功  F-失败)】
	 * 【响应map参数ResMsg：返回信息(失败时返回)】
	 * 【响应map参数VerNo: 逻辑件版本号】
	 * 【响应map参数DeviceType: 设备类型】
	 * 【响应map参数DeviceNo: 设备型号】
	 * 【响应map参数DeviceVerNo: 设备固件版本号】
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public Map<String , String> getVersionInfo() throws UnknownHostException, IOException{
				
		/* 生成公共报文信息，交易代码为"0" */
		String reqMsg = getMsgHead("0");
		
		/* 发送请求并接收驱动平台返回 */
		String resMsg = new DriverClient().doKeypad(reqMsg);
		
		/* 解析响应报文并写入Map返回 */
		String[] res = resMsg.split("\\|");
		Map<String, String> resMap = new HashMap<String, String>();
		
		/* 返回请求失败map */
		if (!res[0].endsWith("0")){
			return getFailMap(res);
		}
		
		/* 生成请求成功map */
		resMap.put("ResCode", "S");			// 返回代码 S-成功
		resMap.put("ResMsg", "交易成功");		// 返回信息
		resMap.put("VerNo", res[3]);		// 逻辑件版本号
		resMap.put("DeviceType", res[4]);	// 设备类型
		resMap.put("DeviceNo", res[5]);		// 设备型号
		resMap.put("DeviceVerNo", res[6]);	// 设备固件版本号
		
		return resMap;
	}
	
	/***
	 * 加载主密钥
	 * 【请求map参数MainKeyPos：主密钥槽位】
	 * 【请求map参数MainKeyText：主密钥明文】
	 * 【响应map参数ResCode：返回代码(S-成功  F-失败)】
	 * 【响应map参数ResMsg：返回信息(失败时返回)】
	 * @param reqMap 请求信息
	 * @return
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public Map<String , String> loadMainKey(Map<String, String> reqMap) throws UnknownHostException, IOException{
		
		/* 生成公共报文信息，交易代码为"1" */
		String reqMsg = getMsgHead("1");
		
		reqMsg = reqMsg + reqMap.get("MainKeyPos") + "&";	// 主密钥槽位
		reqMsg = reqMsg + reqMap.get("MainKeyText") + "&|"; // 主密钥明文
		
		/* 发送请求并接收驱动平台返回 */
		String resMsg = new DriverClient().doKeypad(reqMsg);

		/* 解析响应报文并写入Map返回 */
		String[] res = resMsg.split("\\|");
		Map<String, String> resMap = new HashMap<String, String>();
		
		/* 返回请求失败map */
		if (!res[0].endsWith("0")){
			return getFailMap(res);
		}		

		/* 生成请求成功map */
		resMap.put("ResCode", "S");		// 返回代码 S-成功
		resMap.put("ResMsg", "交易成功");	// 返回信息

		return resMap;
	}
	
	/***
	 * 装载工作密钥
	 * @param reqMap 请求信息Map
	 * 【请求参数MainKey： 主密钥槽位】
	 * 【请求参数WorkKeyPos: 工作密钥槽位】
	 * 【请求参数EncText：工作密钥密文串】
	 * 【请求参数DecType：解密类型 1-DES 2-3DES】
	 * @return 响应信息Map
	 * 【响应map参数ResCode：返回代码(S-成功  F-失败)】
	 * 【响应map参数ResMsg：返回信息(失败时返回)】
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public Map<String, String> loadWorkKey(Map<String, String> reqMap) throws UnknownHostException, IOException{
	
		/* 设置公共报文信息，交易代码为"2" */
		String reqMsg = getMsgHead("2");	
		
		reqMsg = reqMsg + reqMap.get("MainKeyPos") + "&";	// 主密钥槽位
		reqMsg = reqMsg + reqMap.get("WorkKeyPos") + "&";	// 工作密钥槽位
		reqMsg = reqMsg + reqMap.get("EncText") + "&";	// 工作密钥密文串
		reqMsg = reqMsg + reqMap.get("DecType") + "&";	// 解密类型  1-DES(主密钥DES解密工作密钥) 2-3DES(主密钥3DES解密工作密钥) 

		/* 发送请求并接收驱动平台返回 */
		String resMsg = new DriverClient().doKeypad(reqMsg);

		/* 解析响应报文并写入Map返回 */
		String[] res = resMsg.split("\\|");
		Map<String, String> resMap = new HashMap<String, String>();
		
		/* 返回请求失败map */
		if (!res[0].endsWith("0")){
			return getFailMap(res);
		}		

		/* 生成请求成功map */
		resMap.put("ResCode", "S");		// 返回代码 S-成功
		resMap.put("ResMsg", "交易成功");	// 返回信息
		
		return resMap;
	}
	
	/***
	 * 检测密码键盘状态
	 * @return 状态 0-正常 1-异常 2-检测不到
	 * 【响应map参数ResCode：返回代码(S-成功  F-失败】
	 * 【响应map参数ResMsg：返回信息(失败时返回)】
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public Map<String, String> checkKeypadStatus() throws UnknownHostException, IOException{
		
		/* 生成请求报文，交易代码为"999" */
		String reqMsg = getMsgHead("999");

		String resMsg = new DriverClient().doKeypad(reqMsg);

		/* 解析响应报文并写入Map返回 */
		String[] res = resMsg.split("\\|");

		/* 返回请求失败map */
		Map<String, String> resMap = new HashMap<String, String>();

		/* 返回请求失败map */
		if (res[3] == null || res[3].trim().equals("")){
			resMap.put("ResCode", "F");		
			resMap.put("ResMsg", "检测不到状态");	
		}else if (res[3].endsWith("0")){
			resMap.put("ResCode", "S");		
			resMap.put("ResMsg", "设备正常");	
		}else if(res[3].endsWith("1")){
			resMap.put("ResCode", "F");			
			resMap.put("Password", "设备故障");	
		}
		
		return resMap;
	}
	
	/***
	 * 生成失败返回map
	 * @param res
	 * @return
	 */
	private Map<String, String> getFailMap(String res[]){
		Map<String, String> map = new HashMap<String, String>();
		map.put("ResCode", "F");
		map.put("ResMsg", res[0] + "-" + res[3]);
		return map;
	}
	
	/***
	 * 生成公共请求报文信息
	 * @param transCode 交易代码
	 * @return
	 */
	private String getMsgHead(String transCode){
		
		/* 生成请求报文 */
		String reqMsg = KEYPAD_DEVICE_NO + "|";// 设备号
		reqMsg = reqMsg + KEYPAD_SEQNO + "|";	// 序列号(暂时默认为"1")
		reqMsg = reqMsg + transCode + "|";	// 交易代码
		reqMsg = reqMsg + KEYPAD_PRIORITY + "|";// 优先级(预留，暂时填"0")
		reqMsg = reqMsg + KEYPAD_OVERTIME + "|";// 超时时间(默认)
		
		return reqMsg;
	}
}
