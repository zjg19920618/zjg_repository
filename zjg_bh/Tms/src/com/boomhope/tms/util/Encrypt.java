package com.boomhope.tms.util;

import java.io.IOException;
import org.apache.log4j.Logger;
import com.boomhope.tms.transaction.ConfigReader;
import union.counter.api.UnionEncPin;
import UnionTech.JavaEsscAPI.UnionAPI;

/**
 * 
 * @author zhang.m
 * 消息加密
 */
public class Encrypt {
	protected static Logger log=Logger.getLogger(Encrypt.class);
	protected static String KEY_MAC_IP="encrypt.mac.ip";
	protected static String KEY_MAC_PORT="encrypt.mac.port";
	protected static String KEY_MAC_TYPE="encrypt.mac.type";
	protected static String KEY_PIN_VKINDEX="encrypt.pin.vkIndex";
	protected static String KEY_PUBLIC_KEY="encrypt.public.key";
	protected static String KEY_PIN_ZEK2NAME="encrypt.pin.zek2Name";
	
	//获得加密对象
	public static  UnionAPI getUnionAPI() throws IOException{
		String MacIP = ConfigReader.getConfig(KEY_MAC_IP);
		int MacPort = Integer.parseInt(ConfigReader.getConfig(KEY_MAC_PORT));
		String MacType = ConfigReader.getConfig(KEY_MAC_TYPE);
		 UnionAPI essc_api =new UnionAPI(MacIP,MacPort,2,MacType);
		 return essc_api;
	}
	
	//将明文加密成密文
	public static  String  getPinValue(String  pinData) throws IOException{
			//获取密钥
			UnionAPI essc_api =getUnionAPI();
			UnionEncPin encPin = new UnionEncPin();
			String pin = pinData;
			String derPK=ConfigReader.getConfig(KEY_PUBLIC_KEY);
			String pinDataAndDesKeyByPK = encPin.Encrypt(pin,derPK);
			String zek1ByPk = pinDataAndDesKeyByPK.substring(32,pinDataAndDesKeyByPK.length());
			String encData = pinDataAndDesKeyByPK.substring(0,32);
			String vkIndex = ConfigReader.getConfig(KEY_PIN_VKINDEX);
			String zek2Name = ConfigReader.getConfig(KEY_PIN_ZEK2NAME);
			String checkValue = encPin.getZekCheckVal();
			try {
				pinData = essc_api.UnionTransEncDataByZekName(zek1ByPk, checkValue, encData, vkIndex, zek2Name);
			}
   			catch (Exception e00)
    			{e00.printStackTrace();}
		return pinData;
	}
}
