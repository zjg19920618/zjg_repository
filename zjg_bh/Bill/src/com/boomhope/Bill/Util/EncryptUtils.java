package com.boomhope.Bill.Util;

import java.util.Date;

import org.apache.log4j.Logger;

public class EncryptUtils {
	protected static Logger log=Logger.getLogger(EncryptUtils.class);
	protected static String KEY_PIN_IP="encrypt.pin.ip";
	protected static String KEY_PIN_PORT="encrypt.pin.port";
	protected static String KEY_PIN_TYPE="encrypt.pin.type";
	protected static String KEY_PINVK_INDEX="encrypt.pinvk.index";
	protected static String KEY_PUBLIC_KEY="encrypt.public.key";
	protected static String KEY_MAC_IP="encrypt.mac.ip";
	protected static String KEY_MAC_PORT="encrypt.mac.port";
	protected static String KEY_MAC_TYPE="encrypt.mac.type";
	protected static String KEY_CARD_NO_CUT_TAIL="encrypt.card.no.cut.tail";
	
	protected static UnionAPIEX getPinAPIEx(){
		String ip = "198.1.245.102";
		int port = 28101;
		String type = "CD";
		UnionAPIEX api = new UnionAPIEX(ip, port, 2, type);
		return api;
	}

	/**
	 * 指定的密钥加密的PIN明文，得到相关的密文
	 * @param pin 密码明文
	 * @param accNo 卡号
	 * @param pinKeyName
	 * @return
	 */
	public static String encryPin433(String pinKeyName, String pin, String accNo) {
		Date start = new Date();
		String pinBlock = "";
		try {
			UnionAPIEX api=getPinAPIEx();
			pinBlock = api.UnionEncryptPin(pinKeyName, pin, accNo);
		} catch (Exception e) {
			log.error("", e);
		}
		 Date end = new Date();
		 log.debug("433 pin=[" + pin + "]共用时间：" + (end.getTime() - start.getTime()) + "毫秒");
		return pinBlock;
	}

	/**
	 * 转加密
	 * @param pinKeyName1 密钥名称1
	 * @param pinKeyName2 密钥名称2
	 * @param cardNo 卡号
	 * @param pinBlock1 密码密文
	 * @return
	 */
	public static String tranPin310(String pinKeyName1, String pinKeyName2, String cardNo, String pinBlock1){
		try {
			String accNo2 = "0000000000000000\0";
			UnionAPIEX api = getPinAPIEx();
			
			log.debug("310-----------1"+ (pinKeyName1.length() == 0));
			log.debug("310-----------2"+ (pinKeyName2.length() == 0));
			log.debug("310-----------3" + (pinBlock1.length() == 0));
			log.debug("310-----------4" + (cardNo.length() == 0));
			log.debug("310-----------5" + (accNo2.length() == 0));
			log.debug("310-----------6"+ (pinBlock1.length() != 16) );
			log.debug("310-----------6"+ (pinBlock1.length()) );
			log.debug("310-----------"+ ((pinKeyName1.length() == 0) || (pinKeyName2.length() == 0) || (pinBlock1.length() == 0) || (cardNo.length() == 0) || (accNo2.length() == 0) || (pinBlock1.length() != 16)));
			
			int cutTail= 0;
			if(cutTail==1){
				cardNo=cardNo.substring(0, cardNo.length()-1)+"\0";//截取不要卡号的最后一位
			}
			else {
				cardNo=cardNo+"\0";
			}
			log.debug("310---------7"+cardNo);
			String pinBlock2 = api.UnionTranslatePinWith2AccNo(pinKeyName1, pinKeyName2, pinBlock1, cardNo, cardNo);
			return pinBlock2;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public static int generateNewWorkKey285(String workKeyName){
		try {
			UnionAPIEX api = getPinAPIEx();

			int code = api.UnionGenerateKeyMerely(workKeyName);
			return code;
		} catch (Exception e) {
			log.error("", e);
		}
		return -1;
	}
	
	public static String[] readNewWorkKey288(String mainKeyName, String workKeyName){
		try {
			UnionAPIEX api = getPinAPIEx();

			byte workKeyBytes[] = new byte[32];
			byte checkValueBytes[] = new byte[32];
			int code = api.UnionReadKeyBySpecZmk(workKeyName, mainKeyName, workKeyBytes, checkValueBytes);// 工作密钥
			if (code == 0) {
				String workKey = new String(workKeyBytes);
				String checkValue = new String(checkValueBytes);
				return new String[]{ workKey, checkValue };
			}
		} catch (Exception e) {
			log.error("", e);
		}
		return null;
	}
	
	public static String[] fetchNewWorkKey(String mainKeyName, String workKeyName){
		int code = generateNewWorkKey285(workKeyName);
		if (code == 0) {
			return readNewWorkKey288(mainKeyName, workKeyName);
		}
		return null;
	}
	
	/**
	 * 计算MAC校验值
	 * @param macKeyName 密钥名称
	 * @param macData 报文
	 * @return
	 */
	public static String encryMAC302(String macKeyName, String macData) {
		Date start = new Date();
		
		String mac = "";
		try {
			UnionAPIEX api = getMACAPIEx();
			mac = api.UnionGenerateMac(macKeyName, macData.toString().length(), macData.toString());
		} catch (Exception e) {
			log.error("MAC异常"+macKeyName, e);
		}
		Date end = new Date();
		log.debug("mac=[" + mac + "]共用时间：" + (end.getTime() - start.getTime()) + "毫秒");
		return mac;
	}
	
	protected static UnionAPIEX getMACAPIEx(){
		String ip = "198.1.245.102";
		int port = 28101;
		String type ="CD";
		UnionAPIEX api = new UnionAPIEX(ip, port, 2, type);
		return api;
	}
	

	/**
	 * 计算MAC校验值
	 * @param macKeyName 密钥名称
	 * @param macData 报文
	 * @return
	 */
	public static String encryMAC302(String macKeyName, byte[] macData) {
		Date start = new Date();
		
		String mac = "";
		try {
			UnionAPIEX api = getMACAPIEx();
			mac = api.UnionGenerateMac(macKeyName, macData.length, macData);
		} catch (Exception e) {
			log.error("MAC异常"+macKeyName, e);
		}
		Date end = new Date();
		log.info("mac=[" + mac + "]共用时间：" + (end.getTime() - start.getTime()) + "毫秒");
		return mac;
	}
}
