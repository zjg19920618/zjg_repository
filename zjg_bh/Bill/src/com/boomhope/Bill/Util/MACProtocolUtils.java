package com.boomhope.Bill.Util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;


public class MACProtocolUtils {
	protected static Logger log = Logger.getLogger(MACProtocolUtils.class);
	
	/***
	 * 格式化数据
	 *
	 * @param macKeyName 
	 * @param msg
	 * @return
	 * @throws IOException
	 */
	public static byte[] toRequest(String macKeyName, byte[] macData) throws IOException {
		// 16位MAC
		String mac = "";
		
		try{
			mac = EncryptUtils.encryMAC302(macKeyName, macData);
		}
		catch(Exception e){
			log.error("", e);
		}
		
		byte[] macBytes=mac.getBytes();
		if (macBytes.length < 10){
			macBytes = new byte[16];
		}
		
		int length = macBytes.length + macData.length;
		byte[] lengthBytes = String.format("%04d", length).getBytes();
				
		return combine(lengthBytes, macBytes, macData);
	};

	protected static byte[] combine(byte[] length, byte[] mac, byte[] msg) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		// 格式拼接
		out.write(length);
		out.write(mac);
		out.write(msg);
		
		byte[] result = out.toByteArray();
		log.debug("combine:"+new String(result));
		out.close();
		return result;
	}

	/***
	 * socket接收数据
	 * 
	 * @param in
	 * @return
	 * @throws IOException
	 */
	public static byte[] toResponse(InputStream in) throws IOException {
		byte[] buffer = new byte[4];
		in.read(buffer);
		int length = Integer.valueOf(new String(buffer));
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		out.write(buffer);
		while (length-- > 0) {
			out.write(in.read());
		}
		byte[] result = out.toByteArray();
		log.debug("toResponse:"+new String(result));
		out.close();
		return result;
	}
	
	

}
