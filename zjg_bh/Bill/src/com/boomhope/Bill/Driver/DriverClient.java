package com.boomhope.Bill.Driver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.boomhope.Bill.Public.GlobalDriver;

/***
 * 驱动请求客户端
 * @author shaopeng
 *
 */
public class DriverClient {
	
	static Logger logger = Logger.getLogger(DriverClient.class);
	/***
	 * 密码键盘通用处理
	 * @param reqMsg
	 * @return
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public String doKeypad(String reqMsg) throws UnknownHostException, IOException{
		logger.info("reqMsg:" + reqMsg);
		Socket socket = null;
		OutputStream os = null;
		InputStream is = null;
		try {
			socket = new Socket(GlobalDriver.Driver_IP, GlobalDriver.Driver_PORT);
			//构建IO  
			os = socket.getOutputStream(); 
			os.write(reqMsg.getBytes());
			is = socket.getInputStream();  
			byte[] buffer = new byte[1024];
			is.read(buffer);
			String result = new String(buffer, "GBK");
			logger.info("resMsg:" + result);
			return result;
		} catch (UnknownHostException e) {
			logger.error(e);
			throw new UnknownHostException();
		}catch (IOException e1) {
			logger.error(e1);
			throw new IOException();
		}finally{
			if(os!=null){
				os.close();
			}
			if(is!=null){
				is.close();
			}
			if(socket!=null){
				socket.close();
			}
		}
	}
	
//	/***
//	 * 已废弃，不在使用
//	 * 输入密码处理函数
//	 * @param reqMsg
//	 * @return
//	 * @throws UnknownHostException
//	 * @throws IOException
//	 */
//	public String doInputPassword(BackTransBasePanel panels, String reqMsg) throws UnknownHostException, IOException{
//		//银行卡密码校验
//		TransInputBankCardPassPanel bankCardPassPanel = null;
//		//存单密码校验
//		BackTransInputBillPassPanel backTransInputBillPassPanel = null;
//		//存单开户中的银行卡密码校验
//		AccountBankCardPasswordInput accountBankCardPasswordInput = null;
//		//存单开户中的银行卡密码校验第二次
//		AccountAgainBankCardPasswordInput accountAgainBankCardPasswordInput = null;
//		//存单机开户中的存单密码校验（第一次输入）
//		AccountWithCloseToPayPanel accountWithCloseToPayPanel = null;
//		//存单机开户中的存单密码校验（第二次输入）
//		AccountAgainWithCloseToPayPanel accountAgainWithCloseToPayPanel = null;
//		System.out.println("reqMsg:" + reqMsg);
//		
//		String resMsg = "";
//
//		Socket socket = new Socket(GlobalDriver.Driver_IP, GlobalDriver.Driver_PORT);
//		System.out.println("aaaaaaa");
//		
//		socket.setKeepAlive(true);
//		System.out.println("bbbbbbb");
//		if(panels instanceof TransInputBankCardPassPanel){
//			bankCardPassPanel = (TransInputBankCardPassPanel)panels;
//		}
//		if(panels instanceof BackTransInputBillPassPanel){
//			backTransInputBillPassPanel = (BackTransInputBillPassPanel)panels;
//		}
//		if(panels instanceof AccountBankCardPasswordInput){
//			accountBankCardPasswordInput = (AccountBankCardPasswordInput)panels;
//		}
//		if(panels instanceof AccountAgainBankCardPasswordInput){
//			accountAgainBankCardPasswordInput = (AccountAgainBankCardPasswordInput)panels;
//		}
//		if(panels instanceof AccountWithCloseToPayPanel){
//			accountWithCloseToPayPanel = (AccountWithCloseToPayPanel)panels;
//		}
//		if(panels instanceof AccountAgainWithCloseToPayPanel){
//			accountAgainWithCloseToPayPanel = (AccountAgainWithCloseToPayPanel)panels;
//		}
//		//构建IO  
//		OutputStream os = socket.getOutputStream(); 
//		os.write(reqMsg.getBytes());
//		System.out.println("ccccccc");
//		InputStream is=null;
//		while (true){
//			is = socket.getInputStream();  
//			byte[] buffer = new byte[1024];
//			is.read(buffer);
//			resMsg = new String(buffer, "GBK");
//			System.out.println("resMsg:" + resMsg);
//			String[] res = resMsg.split("\\|");
//			
//			if (res[3].equals("*")){
//				// 输入密码字符，调用回调函数在界面上同步显示增加一个"*"
//				if(bankCardPassPanel != null){
//					String text = bankCardPassPanel.passwordField.getText() + "*";
//					bankCardPassPanel.passwordField.setText(text);
//				}if(backTransInputBillPassPanel != null){
//					String text = backTransInputBillPassPanel.passwordField.getText() + "*";
//					backTransInputBillPassPanel.passwordField.setText(text);
//				}
//				if(accountBankCardPasswordInput != null){
//					String text = accountBankCardPasswordInput.passwordField.getText() + "*";
//					accountBankCardPasswordInput.passwordField.setText(text);
//				}
//				if(accountAgainBankCardPasswordInput != null){
//					String text = accountAgainBankCardPasswordInput.passwordField.getText() + "*";
//					accountAgainBankCardPasswordInput.passwordField.setText(text);
//				}
//				if(accountWithCloseToPayPanel != null){
//					String text = accountWithCloseToPayPanel.passwordField.getText() + "*";
//					accountWithCloseToPayPanel.passwordField.setText(text);
//				}
//				if(accountAgainWithCloseToPayPanel != null){
//					String text = accountAgainWithCloseToPayPanel.passwordField.getText() + "*";
//					accountAgainWithCloseToPayPanel.passwordField.setText(text);
//				}
//			}else if (res[3].equals("1B")){
//				/* 退出输入，密码返回为空 */
//				if(bankCardPassPanel != null){
//					bankCardPassPanel.passwordField.setText("");
//				}
//				if(backTransInputBillPassPanel != null){
//					backTransInputBillPassPanel.passwordField.setText("");
//				}
//				if(accountBankCardPasswordInput != null){
//					accountBankCardPasswordInput.passwordField.setText("");
//				}
//				if(accountAgainBankCardPasswordInput != null){
//					accountAgainBankCardPasswordInput.passwordField.setText("");
//				}
//				if(accountWithCloseToPayPanel != null){
//					accountWithCloseToPayPanel.passwordField.setText("");
//				}
//				if(accountAgainWithCloseToPayPanel != null){
//					accountAgainWithCloseToPayPanel.passwordField.setText("");
//				}
//			}else if (res[3].equals("FF")){
//				/* 用户点击退格，删除最后一个密码字符 */
//				if(bankCardPassPanel != null){
//					String text = bankCardPassPanel.passwordField.getText();
//					if(StringUtils.isNotBlank(text)){
//						text = text.substring(0, text.length()-1);
//						bankCardPassPanel.passwordField.setText(text);
//					}
//				}
//				if(backTransInputBillPassPanel != null){
//					String text = backTransInputBillPassPanel.passwordField.getText();
//					if(StringUtils.isNotBlank(text)){
//						text = text.substring(0, text.length()-1);
//						backTransInputBillPassPanel.passwordField.setText(text);
//					}
//				}
//				if(accountBankCardPasswordInput != null){
//					String text = accountBankCardPasswordInput.passwordField.getText();
//					if(StringUtils.isNotBlank(text)){
//						text = text.substring(0, text.length()-1);
//						accountBankCardPasswordInput.passwordField.setText(text);
//					}
//				}
//				if(accountAgainBankCardPasswordInput != null){
//					String text = accountAgainBankCardPasswordInput.passwordField.getText();
//					if(StringUtils.isNotBlank(text)){
//						text = text.substring(0, text.length()-1);
//						accountAgainBankCardPasswordInput.passwordField.setText(text);
//					}
//				}
//				if(accountWithCloseToPayPanel != null){
//					String text = accountWithCloseToPayPanel.passwordField.getText();
//					if(StringUtils.isNotBlank(text)){
//						text = text.substring(0, text.length()-1);
//						accountWithCloseToPayPanel.passwordField.setText(text);
//					}
//				}
//				if(accountAgainWithCloseToPayPanel != null){
//					String text = accountAgainWithCloseToPayPanel.passwordField.getText();
//					if(StringUtils.isNotBlank(text)){
//						text = text.substring(0, text.length()-1);
//						accountAgainWithCloseToPayPanel.passwordField.setText(text);
//					}
//				}
//			}else if (res[3].equals("0D")){
//				/* 用户输入完成，点击"确认" */
//				
//			}else{
//				/* 非以上情况，则返回内容为密码密文 */
//				if(bankCardPassPanel != null){
//					bankCardPassPanel.nextStep(res[3]);
//				}
//				if(backTransInputBillPassPanel != null){
//					backTransInputBillPassPanel.nextStep(res[3]);
//				}
//				if(accountBankCardPasswordInput != null){
//					accountBankCardPasswordInput.nextStep(res[3]);
//				}
//				if(accountAgainBankCardPasswordInput != null){
//					accountAgainBankCardPasswordInput.nextStep(res[3]);
//				}
//				if(accountWithCloseToPayPanel != null){
//					accountWithCloseToPayPanel.nextStep(res[3]);
//				}
//				if(accountAgainWithCloseToPayPanel != null){
//					accountAgainWithCloseToPayPanel.nextStep(res[3]);
//				}
//				break;
//			}
//		}
//		socket.getInputStream().close();
//		socket.close();
//		return resMsg;
//	}
}
