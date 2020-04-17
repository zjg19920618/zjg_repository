package com.boomhope.Bill.Public;

/***
 * 公共变量
 * @author shaopeng
 *
 */
public class GlobalParameter {
	
	public static String machineNo = "000C0011";	// 机器号
	public static String branchNo = "010001";	// 机构号
	public static String keyboard_work_keyflag = "CODM.00000001.zpk";// 密码键盘工作密钥
	public static String tellerNo = "";// 柜员号
	public static String macFacNum = "";// 出厂编号
	public static String unitName = "";// 机构名称
	public static String supTellerNo = "";//授权柜员号
	
	public static String MACHINE_TYPE = "BCK";	// 机构号
	
	public static int WINDOW_WIDTH = 1440;		// 主界面宽度
	public static int WINDOW_HEIGHT = 900;	// 主界面高度
		
	public static int CONTENT_WIDTH = 1440;	
	public static int CONTENT_HEIGHT = 740;	// 去掉上下框界面高度
	
	public static int TRANS_WIDTH = 1059;	// 交易容器宽度
	public static int TRANS_HEIGHT = 750;	// 交易容器高度
	
	public static String printStatus = "0";	// 存单打印机状态
	public static String printRateStatus="";//打印存单利率开关状态 0-打印利率 1-不打印利率
	
	//0管理员，1存单销户，2现金开户，3汇划转账，4汇划撤销,5银行卡开户,6子账户打印,20流水绑定,21推荐有礼,100其他
	//99开户，98汇划业务,97管理员按钮
	public static String ACC_NO = ""; 
	
	public static int TRANS_NOS=1;//能显示的业务数量
	public static boolean OFF_ON=true;//业务操作中是否可以跳转到其它子业务的开关  true：可以跳转  false:不能跳转
	
	public static boolean ACC_STATUS=false;//设备内是否有存单 	false:无	 	true:有
	public static boolean CARD_STATUS=false;//设备内是否有银行卡  	false:无 		true:有
	
}
