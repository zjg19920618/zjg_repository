package com.boomhope.Bill.Public;

/***
 * 全局驱动信息
 * @author shaopeng
 *
 */
public class GlobalDriver {
	
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
//	public static final String KEYPAD_CARDNO = "052000125001000346330";	// 加密卡号 默认为16个0

}
