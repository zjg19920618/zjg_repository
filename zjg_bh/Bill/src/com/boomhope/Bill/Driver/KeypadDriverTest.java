package com.boomhope.Bill.Driver;


/***
 * 密码键盘驱动测试类
 * @author shaopeng
 *
 */
public class KeypadDriverTest {

	public static void main(String[] args) throws Exception{
		
		/* 测试获取版本信息 */
//	//	new KeypadDriverTest().doGetVersionInfo();
//		
//		/* 输入密码 */
////		new KeypadDriverTest().doInputPassword();
///*		Map<String, String> map = new HashMap<String, String>();
//		map.put("MainKeyPos", "0");
//		map.put("WorkKeyPos", "0");
//		map.put("EncText", "220AFED59CF516391DED6F02365ECD7E");
//		map.put("DecType", "2");
//		System.out.println(new KeypadDriver().loadWorkKey(map));*/
//		System.out.println(111);
//		new Thread(){
//			public void run() {
//				BackTransInputBillPassPanel panel = null;
//				String knowArticle = null;
//				try {
//					knowArticle = new KeypadDriver().getKnowArticle(panel, "4", "20");
//				} catch (UnknownHostException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				System.out.println(knowArticle);
//				
//			};
//		}.start();
//		new Thread(){
//			public void run() {
//				try {
//					System.out.println(KeypadDriver.socket.isInputShutdown());
//					System.out.println(KeypadDriver.socket.isOutputShutdown());
//					KeypadDriver.socket.shutdownInput();
//					KeypadDriver.socket.shutdownOutput();
//					
//					KeypadDriver driver = new KeypadDriver();
//					String pwd = driver.closePwd("6");
//					System.out.println(pwd);
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			};
//		}.start();
//		/*KeypadDriver driver = new KeypadDriver();
//		String pwd = driver.closePwd("6");
//		System.out.println(pwd);*/
//	}
//	
//	private void doInputPassword(){
//		try {
//			
//			Map<String, String> reqMap = new HashMap<String, String>();
//			reqMap.put("PassLength", "6");	// 密码长度
//			reqMap.put("EncType", "1");	// 加密方式
//			reqMap.put("PinType", "3");	// pin类型
//			reqMap.put("PinFill", "4");	// pin补位方式
//			reqMap.put("CardNo", "");	// 卡号
//			
//			Map<String, String> resMap = new KeypadDriver().inputPassword(null, reqMap);
//			
//					
//			System.out.println("密码:" + resMap.get("Password"));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	private void doGetVersionInfo(){
//		try {
//			Map<String, String> map = new KeypadDriver().getVersionInfo();
//			System.out.println("逻辑件版本号:" + map.get("VerNo"));
//			System.out.println("设备类型:" + map.get("DeviceType"));
//			System.out.println("设备型号:" + map.get("DeviceNo"));
//			System.out.println("设备固件版本号:" + map.get("DeviceVerNo"));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}


}
