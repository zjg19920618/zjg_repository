package com.boomhope.Bill.Driver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JTextField;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Public.GlobalDriver;

/***
 * 密码键盘驱动类
 * @author shaopeng
 *
 */
public class NewKeypadDriver {
	
	Logger logger = Logger.getLogger(NewKeypadDriver.class);
	/**
	 * 根据需求，银行卡号需能软键盘和密码键盘都能输入，
	 * 又因，开启密码键盘必须要传入输入的位数，而不能判断软键盘输入了多少位，和密码键盘输入了多少位，
	 * 例如：预先传入输入30位，用软键盘输入了20位，又用密码键盘输入了剩余10位，这时用密码键盘没有输入30位，而导致socket一直在死循环，
	 * 则，需要调用关闭密码键盘事件，由于开启密码键盘已经占用了这个端口，这时在开启一个端口，而导致堵塞，
	 * 故，创建一个socket公共属性，供明文输入，关闭密码键盘这两个方法来使用，则关闭密码键盘能杀死前一个socket，保证程序继续执行
	 */
	public static Socket socket = null;
	
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
//		reqMsg = reqMsg + reqMap.get("DecType") + "&";	// 解密类型  1-DES(主密钥DES解密工作密钥) 2-3DES(主密钥3DES解密工作密钥) 

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
	 * 激活密钥
	 * @param resMap
	 * 【请求参数MainKey： 主密钥槽位】
	 * 【请求参数WorkKeyPos: 工作密钥槽位】
	 * @return
	 * 【响应map参数ResCode：返回代码(S-成功  F-失败】
	 * 【响应map参数ResMsg：返回信息(失败时返回)】
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public Map<String, String> activateKey(Map<String, String> reqMap) throws UnknownHostException, IOException{
		
		/* 设置公共报文信息，交易代码为"2" */
		String reqMsg = getMsgHead("3");	
		
		reqMsg = reqMsg + reqMap.get("MainKeyPos") + "&";	// 主密钥槽位
		reqMsg = reqMsg + reqMap.get("WorkKeyPos") + "&";	// 工作密钥槽位

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
	 * 生成公共请求报文信息
	 * @param transCode 交易代码
	 * @return
	 */
	private String getMsgHead(String transCode){
		
		/* 生成请求报文 */
		String reqMsg = GlobalDriver.KEYPAD_DEVICE_NO + "|";// 设备号
		reqMsg = reqMsg + GlobalDriver.KEYPAD_SEQNO + "|";	// 序列号(暂时默认为"1")
		reqMsg = reqMsg + transCode + "|";	// 交易代码
		reqMsg = reqMsg + GlobalDriver.KEYPAD_PRIORITY + "|";// 优先级(预留，暂时填"0")
		reqMsg = reqMsg + GlobalDriver.KEYPAD_OVERTIME + "|";// 超时时间(默认)
		
		return reqMsg;
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
	/**
	 * 关闭密码键盘
	 * @param transCode  交易码
	 * @return
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public String closePwd(String transCode) throws UnknownHostException, IOException{
		String reqMsg = getMsgHead(transCode);
		
		System.out.println("reqMsg:" + reqMsg);

		socket = new Socket(GlobalDriver.Driver_IP, GlobalDriver.Driver_PORT);

		//构建IO  
		OutputStream os = socket.getOutputStream(); 
		os.write(reqMsg.getBytes());
		InputStream is = socket.getInputStream();  
		byte[] buffer = new byte[1024];
		is.read(buffer);
		String result = new String(buffer, "GBK");
		System.out.println("resMsg:" + result);
		os.close();
		is.close();
		socket.close();
		String[] res = result.split("\\|");
		
		return res[0];
	}
	
	/**
	 * 明文输入密码（通用）
	 * 
	 * 注：使用该方法，需要穿2个text类型的文本，来接收数据，由于是密码，则需要穿一个密码类型的文本，和一个明文的文本，
	 * 
	 * @param panel          放this页面，该页面必须继承BackTransBasePanel类
	 * @param propertyStr    存放密码：JPasswordField属性的字段全称，
	 * @param knowStr		 存放明文的字段
	 * @param transCode      //交易代码
	 * @param pwdLength		 //号码长度
	 * @param lth            //实际需要长度
	 * 由于这个密码键盘指定了位数，位数输入够了，直接自动关闭，当要修改时，
	 * 需要重新调用密码键盘，现在，将号码长度设置长一些，如果长度打到了实际需要的长度，则后面的值，直接丢弃
	 * @return
	 * @throws Exception
	 */
	public String getKnowPassword(BaseTransPanelNew panel,String propertyStr,String knowStr,String transCode,String pwdLength,String lth) throws Exception {
		Class clazz = (Class)panel.getClass();
		Field propertyStrfield = clazz.getDeclaredField(propertyStr);
		Field knowStrfield = clazz.getDeclaredField(knowStr);
		propertyStrfield.setAccessible(true);
		knowStrfield.setAccessible(true);
		if(Integer.parseInt(transCode) <= 0){
			return null;
		}
		String reqMsg = getMsgHead(transCode);
		reqMsg = reqMsg + pwdLength;

		System.out.println("reqMsg:" + reqMsg);
		
		String resMsg = "";

		socket = new Socket(GlobalDriver.Driver_IP, GlobalDriver.Driver_PORT);
		
		socket.setKeepAlive(true);

		//构建IO  
		OutputStream os = socket.getOutputStream(); 
		os.write(reqMsg.getBytes());
		
		while (true){
			InputStream is = socket.getInputStream();  
			byte[] buffer = new byte[1024];
			is.read(buffer);
			resMsg = new String(buffer, "GBK");
			System.out.println("resMsg:" + resMsg);
			if(StringUtils.isNotBlank(resMsg.trim())){
				String[] res = resMsg.split("\\|");
				if(res[3].startsWith("3")){
					Method propertyStrM = clazz.getMethod("get"+upperHeadChar(propertyStrfield.getName()));  
					Object propertyStrObject = propertyStrM.invoke(panel);//取出属性
					JTextField passwordField =(JTextField)propertyStrObject;
		            String passwordText = passwordField.getText();//取属性值
					
					Method knowStrfieldM = clazz.getMethod("get"+upperHeadChar(knowStrfield.getName()));  
					Object object = knowStrfieldM.invoke(panel);//取出属性
		            JTextField textField =(JTextField)object;
		            String text = textField.getText();//取属性值
		            if(!String.valueOf(text.length()).equals(lth)){
		            	 //密码追加值
		            	 passwordText = passwordText + "*";
		            	 passwordField.setText(passwordText);
		            	 //明文追加值
		            	 text += res[3].substring(1);//追加值
		            	 textField.setText(text);
		            }
				}else if (res[3].equals("1B")){
					os.close();
					socket.close();
					getKnowPassword(panel, propertyStr, knowStr, transCode, pwdLength, lth);
					break;
				}else if(res[3].equals("0D")){
					os.close();
					socket.close();
					getKnowPassword(panel, propertyStr, knowStr, transCode, pwdLength, lth);
					break;
				}
				else if (res[3].equals("FF")){
					/* 用户点击退格，删除最后一个密码字符 */
					Method propertyStrM = clazz.getMethod("get"+upperHeadChar(propertyStrfield.getName()));  
					Object propertyStrObject = propertyStrM.invoke(panel);//取出属性
					JTextField passwordField =(JTextField)propertyStrObject;
		            String passwordText = passwordField.getText();//取属性值
					
					Method knowStrfieldM = clazz.getMethod("get"+upperHeadChar(knowStrfield.getName()));  
					Object object = knowStrfieldM.invoke(panel);//取出属性
		            JTextField textField =(JTextField)object;
		            String text = textField.getText();//取属性值
		            if(StringUtils.isNotBlank(text)){
		            	passwordText = passwordText.substring(0, passwordText.length()-1);
		            	passwordField.setText(passwordText);
		            	
		            	text = text.substring(0, text.length()-1);
		            	textField.setText(text);
		            }
				}
			}else{
				break;
			}
		}
		socket.getInputStream().close();
		socket.close();
		return resMsg;
	}
	
	/**
	 * 明文输入号码（通用）
	 * 
	 * 注：使用该方法，JTextField属性 的字段，必须为public公共的，不能为其他类型
	 * 必须实现JTextField属性 的字段的get，set方法。
	 * 
	 * @param panel          放this页面，该页面必须继承BackTransBasePanel类
	 * @param propertyStr    存放号码：JTextField属性的字段全称，
	 * @param transCode      //交易代码
	 * @param pwdLength		 //号码长度
	 * @param lth            //实际需要长度
	 * 由于这个密码键盘指定了位数，位数输入够了，直接自动关闭，当要修改时，
	 * 需要重新调用密码键盘，现在，将号码长度设置长一些，如果长度打到了实际需要的长度，则后面的值，直接丢弃
	 * @return
	 * @throws Exception
	 */
	public String getKnowID(BaseTransPanelNew panel,String propertyStr,String transCode,String pwdLength,String lth) throws Exception {
		Class clazz = (Class)panel.getClass();
		Field field = clazz.getDeclaredField(propertyStr);
		field.setAccessible(true);
		if(Integer.parseInt(transCode) <= 0){
			return null;
		}
		String reqMsg = getMsgHead(transCode);
		reqMsg = reqMsg + pwdLength;

		System.out.println("reqMsg:" + reqMsg);
		
		String resMsg = "";

		socket = new Socket(GlobalDriver.Driver_IP, GlobalDriver.Driver_PORT);
		
		socket.setKeepAlive(true);

		//构建IO  
		OutputStream os = socket.getOutputStream(); 
		os.write(reqMsg.getBytes());
		
		while (true){
			InputStream is = socket.getInputStream();  
			byte[] buffer = new byte[1024];
			is.read(buffer);
			resMsg = new String(buffer, "GBK");
			System.out.println("resMsg:" + resMsg);
			if(StringUtils.isNotBlank(resMsg.trim())){
				String[] res = resMsg.split("\\|");
				if(res[3].startsWith("3")){
					Method m = clazz.getMethod("get"+upperHeadChar(field.getName()));//方法getDeleteDate  
					Object object = m.invoke(panel);//取出属性
		            JTextField jTextField =(JTextField)object;
		            String text = jTextField.getText();//取属性值
		            if(!String.valueOf(text.length()).equals(lth)){
		            	 text += res[3].substring(1);//追加值
				         jTextField.setText(text);
		            }
				}else if (res[3].equals("1B")){
					os.close();
					socket.close();
					getKnowID(panel, propertyStr, transCode, pwdLength, lth);
					break;
				}else if(res[3].equals("0D")){
					os.close();
					socket.close();
					getKnowID(panel, propertyStr, transCode, pwdLength, lth);
					break;
				}
				else if (res[3].equals("FF")){
					/* 用户点击退格，删除最后一个密码字符 */
					Method m = clazz.getMethod("get"+upperHeadChar(field.getName()));//方法getDeleteDate  
					Object object = m.invoke(panel);//取出属性
		            JTextField jTextField =(JTextField)object;
		            String text = jTextField.getText();//取属性值
		            if(StringUtils.isNotBlank(text)){
		            	text = text.substring(0, text.length()-1);
		            }
		            jTextField.setText(text);
				}
			}else{
				break;
			}
		}
		socket.getInputStream().close();
		socket.close();
		return resMsg;
	}
	private String upperHeadChar(String in){  
        String head=in.substring(0,1);  
        String out=head.toUpperCase()+in.substring(1,in.length());  
        return out;  
    }  
	/**
	 * 输入密码（通用）
	 * 注：使用该方法，JTextField属性 的字段，必须为public公共的，不能为其他类型
	 * 必须实现JTextField属性 的字段的get，set方法。
	 * 
	 * @param panel        放this页面，该页面必须继承BackTransBasePanel类
	 * @param propertyStr  存放号码：JTextField属性的字段全称，
	 * @param nextStep     放入输完密码自动执行的方法名
	 * @param reqMap
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> getInputPassword(BaseTransPanelNew panel,String propertyStr,String nextStep, Map<String, String> reqMap) throws Exception{
		
		/* 生成请求报文 */
		String reqMsg = getMsgHead("5");
		reqMsg = reqMsg + reqMap.get("PassLength") + "&";	// 密码长度
		
		// 加密方式加密方式: 1-工作密钥DES方式  2-工作密钥3DES方式  3-主密钥DES方式  4-主密钥3DES方式
		reqMsg = reqMsg + reqMap.get("EncType") + "&";	
		
		// PIN运算算法: 1-ISO9564-1格式0（ANSI X9.8）   2-IBM3624   3-ASCII
		reqMsg = reqMsg + reqMap.get("PinType") + "&";	
		
		// PIN补位方式: 1-左补FF   2-右补FF   3-左补00  4-右补00
		reqMsg = reqMsg + reqMap.get("PinFill") + "&";	
		
		// 卡号 Pin运算算法为1时需要
		reqMsg = reqMsg + reqMap.get("CardNo") + "&";	

		
		/* 发送请求并接收驱动平台返回 */
		String resMsg = getDoInputPassword(panel,propertyStr,nextStep, reqMsg);

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
		resMap.put("Password", res[3]);	// 加密后密文

		return resMap;
	}
	/***
	 * 输入密码处理函数
	 * @param reqMsg
	 * @param propertyStr  存放号码：JTextField属性的字段全称，
	 * @return
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public String getDoInputPassword(BaseTransPanelNew panel,String passText,String errLable, String reqMsg) throws Exception{
		Class clazz = (Class)panel.getClass();
		Field fieldText = clazz.getDeclaredField(passText);
		fieldText.setAccessible(true);
		Field fieldLable = clazz.getDeclaredField(errLable);
		fieldLable.setAccessible(true);
		logger.info("reqMsg:" + reqMsg);
		
		String resMsg = "";
		OutputStream os=null;
		InputStream is = null;
		try {
			socket = new Socket(GlobalDriver.Driver_IP, GlobalDriver.Driver_PORT);
			
			socket.setKeepAlive(true);
			//构建IO  
			os = socket.getOutputStream(); 
			os.write(reqMsg.getBytes());
			
			Method m = clazz.getMethod("get"+upperHeadChar(fieldText.getName()));//
			Object object = m.invoke(panel);//取出属性
	        JTextField jTextField =(JTextField)object;
	        Method m1 = clazz.getMethod("get"+upperHeadChar(fieldLable.getName()));//
			Object object1 = m1.invoke(panel);//取出属性
	        JLabel lable =(JLabel)object1;
	        jTextField.setText("");
	        String text = jTextField.getText();//取属性值
			while (true){
				is = socket.getInputStream();  
				byte[] buffer = new byte[1024];
				is.read(buffer);
				resMsg = new String(buffer, "GBK");
				System.out.println("resMsg:" + resMsg);
				String[] res = resMsg.split("\\|");
				if (res[3].equals("*")){
					// 输入密码字符，调用回调函数在界面上同步显示增加一个"*"
		            text = text+ "*";//追加值		           
		            jTextField.setText(text);
					
				}else if (res[3].equals("1B")){
					/* 退出输入，密码返回为空 */
					if(is!=null){
						is.close();
					}
					if(os!=null){
						os.close();
					}
					if(socket!=null){
						socket.close();
					}					
					return getDoInputPassword(panel,passText,errLable,reqMsg);
				}else if (res[3].equals("FF")){
					text="";
					jTextField.setText("");
				}else if (res[3].equals("0D")){
					/* 用户未输入完成，点击"确认" */
					if(text.length()<6){
						if(is!=null){
							is.close();
						}
						if(os!=null){
							os.close();
						}
						if(socket!=null){
							socket.close();
						}
						lable.setVisible(true);
						return getDoInputPassword(panel,passText,errLable,reqMsg);
					}
				}else if (res[3].equals("")){}else{
					/* 非以上情况，则返回内容为密码密文 */
					break;				
				}
			}
			return resMsg;
		} catch (Exception e) {
			throw new Exception(e);
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
}
