package com.boomhope.Bill.Util;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * 配置文件读取
 * @author zy
 *
 */
public class Property {
	/**
	 * 外设代码
	 */
	public static String CZQ_CODE;//裁纸器编号
	public static String DKQ_CODE;//读卡器代码
	public static String SFZDKQ_CODE;//身份证读卡器
	public static String MMJP_CODE;//密码键盘
	public static String ZWY_CODE;//指纹仪
	public static String EWM_CODE;//二维码
	public static String DYJ_CODE;//打印机
	
	/*博宏新P的IP*/
	public static String BP_NEW_IP = "";
	/*博宏新P的端口*/
	public static int  BP_NEW_PORT = 0;
	/*博宏P的IP*/
	public static String BP_IP = "";
	/*博宏P的端口*/
	public static int  BP_PORT = 0;
	/*超时间间*/
	public static int  TIME_OUT = 0;
	/*存单正面*/
	public static String BILL_FACE = "";
	/**
	 * 销户
	 */
	/*FTP IP*/
	public static String FTP_IP = "";
	/*FTP端口*/
	public static String FTP_PORT = "";
	/*FTP用户*/
	public static String FTP_USER = "";
	/*FTP密码*/
	public static String FTP_PWD = "";
	/*本地路径*/
	public static String FTP_LOCAL_PATH = "";
	/*上传路径*/
	public static String FTP_SER_PATH = "";
	/*汇划上传路径*/
	public static String FTP_HUIHUA_SER_PATH = "";
	/*LOG上传路径*/
	public static String FTP_LOG_PATH = "";
	/*LOG本地路径*/
	public static String FTP_LOG_LOCAL_PATH = "";
	/**
	 * 开户
	 */
	/*FTP IP*/
	public static String FTP_IP_KH = "";
	/*FTP端口*/
	public static String FTP_PORT_KH = "";
	/*FTP用户*/
	public static String FTP_USER_KH = "";
	/*FTP密码*/
	public static String FTP_PWD_KH = "";
	/*上传路径*/
	public static String FTP_SER_PATH_KH = "";
	/*LOG上传路径*/
	public static String FTP_LOG_PATH_KH = "";
	/*LOG本地路径*/
	public static String FTP_LOG_LOCAL_PATH_KH = "";
	/*银行卡开户上传路径*/
	public static String FTP_CARDOPEN_SER_PATH = "";
	
	/*增设密码上传路径*/
	public static  String FTP_SER_PATH_ZM = "";
	public static  String acc_zm_amt="";

	/*存单换开上传路径*/
	public static  String FTP_SER_PATH_HK = "";
	public static  String acc_hk_amt="";
	
	/*存单反面照*/
	public static String BILL_VERSO = "";
	/*本人身份证照片正面*/
	public static String BILL_ID_SELF_JUST = "";
	/*本人身份证照片反面*/
	public static String BILL_ID_SELF_AGAINST = "";
	/*本人身份证头像照片*/
	public static String BILL_ID_SELF_FACE = "";
	
	/*代理人身份证照片正面*/
	public static String BILL_ID_AGENT_JUST = "";
	/*代理人身份证照片反面*/
	public static String BILL_ID_AGENT_AGAINST = "";
	/*代理人身份证头像照片*/
	public static String BILL_ID_AGENT_FACE = "";
	
	/*本人照片*/
	public static String ID_CARD_SELF = "";
	/*代理人照片*/
	public static String ID_CARD_AGENT = "";
	/*pdf文件保存路径*/
	public static String BILL_PDF = "";
	/*pdf文件IP地址*/
	public static String BILL_HOST = "";
	/*pdf端口号*/
	public static int BILL_PORT = 0;
	/*pdf用户名*/
	public static String BILL_USER_NAME = "";
	/*pdf密码*/
	public static String BILL_PASSWORD = "";
	/*pdf基础目录*/
	public static String BILL_BASE_PATH = "";
	/*pdf存放目录*/
	public static String BILL_FILE_PATH = "";
	/*pdf文件名称*/
	public static String BILL_FILE_NAME = "";
	/*pdf文件流*/
	public static String BILL_INPUT = "";
	/*jpg文件存放路径*/
	public static String BILL_JPG = "";
	/*前置联网核查下载IP*/
	public static String FTP_IP_DOWN = "";
	/*前置联网核查下载端口*/
	public static String FTP_PORT_DOWN = "";
	/*前置联网核查下载用户*/
	public static String FTP_USER_DOWN = "";
	/*前置联网核查下载密码*/
	public static String FTP_PWD_DOWN = "";
	/*OCR识别模板*/
	public static String OCR_MODE_PATH = "";
	/*OCR核心包路径*/
	public static String OCR_SDK_PATH = "";
	/*人脸识别系统路径*/
	public static String FACE_CHECK_SYS_PATH = "";	
	/*人脸识别次数*/
	public static String CAMEAR_COUNT="";
	/*新人脸识别系统路径*/
	public static String NEW_FACE_CHECK_SYS_PATH = "";	
	/*现场拍照*/
	public static String  CAMERA_PATH = "";
	/*现场重新拍照*/
	public static String  RE_CAMERA_PATH = "";
	/*临时图片文件*/
	public static String  BH_TMP = "";
	/*电子印章url*/
	public static String dzyz_url = "";	
	/*电子印章保存路径*/
	public static String  dzyz_ml = "";
	/*电子印章保存路径*/
	public static String  dzyz_template = "";
	/*客户签字图片路径*/
	public static String SIGNATURE_PATH="";
	/*读取省市县的文件路径*/
	public static String CITY_PATH="";
	/*凭证管理员账户*/
	public static String  BILL_MANAGER= "";
	/*(汇划撤销)客户签字图片路径*/
	public static String SIGNATURE_PATH_CANCEL="";
	/*汇划当日限制最大金额*/
	public static String MAX_AMT=""; 
	/*汇划当日累计最大交易金额*/
	public static String ALL_DAY_AMT="";
	/*汇划工作日单笔最大交易金额*/
	public static String WORK_DAY_ONCE_AMT="";
	/*汇划节假日单笔最大交易金额*/
	public static String REST_DAY_ONCE_AMT="";
	/*汇划单笔需要二次授权的交易金额限制*/
	public static String AUTH_TWO_ONCE_AMT="";
	
	/*以下为测试信息*/
	/*账户号*/
	public static String ACC_NO = "";
	/*凭证号*/
	public static String CER_NO = "";
	/*柜员号*/
	public static String TLLER_NO = "";
	/*代理人身份证号*/
	public static String AGENT_CARD_ID = "";
	/*代理人身份姓名*/
	public static String AGENT_CARD_NAME = "";
	/*本人人身份证号*/
	public static String ID_CARD = "";
	/*本人身份姓名*/
	public static String ID_CARD_NAME = "";
	//对方账户
	public static String OPPO_ACCT_NO="";
	//上级支行
	public static String parent_unit_no="";
	
	/*发送短信验证码*/
	//操作请求
	public static String command="";
	//用户号
	public static String spid="";
	//密码
	public static String sppassword="";
	//短信平台路径
	public static String sUrl="";
	
	//挂失解挂申请书路径
	public static String Lost_Path="";
	//挂失解挂申请书事后监督图片word路径
	public static String Lost_Shjd_Path="";
	//挂失解挂pdf和jpf文件保存路径
	public static String Lost_Pdf_Path="";
	//挂失解挂事后监督图片上传路径
	public static String FTP_SER_PATH_LOST="";
	//挂失解挂事后申请书图片保存本地路径
	public static String bill_bd_path="";
	//挂失解挂事后申请书图片上传失败保存本地路径
	public static String bill_sb_path="";
	//挂失解挂事后图片上传IP
	public static String FTP_SH_IP="";
	//挂失解挂事后上传端口
	public static String FTP_SH_PORT="";
	//挂失解挂事后图片上传用户名
	public static String FTP_SH_USER="";
	//挂失解挂事后图片上传密码
	public static String FTP_SH_PWD="";
	//挂失解挂事后图片上传路径
	public static String FTP_SH_PATH="";
	//挂失解挂打印申请书失败保存路径
	public static String bill_print_path="";
	
	//打印协议路径
	public static String agreementPath="";
	//控制协议书是否打印
	public static String IS_PRINT_AGREEMENT="";
	//打印协议书的第一个协议书版本号
	public static String PRINT_AGREEMENT_NUMBER="";
	//销户电子签名图片路径
	public static String ACCCANCEL_SIGNATRUE_PATH="";
	//选择对勾符号路径
	public static String choice_ok_path="";
	//销户鉴伪超限金额
	public static String acc_cancel_amt="";
	//销户联网核查超限金额
	public static String acc_cancel_ckeckIdAmt="";
	//销户授权超限金额
	public static String acc_cancel_tellNoAmt="";
	//开户联网核查超限金额
	public static String acc_open_ckeckIdAmt="";
	//开户授权超限金额
	public static String acc_open_tellNoAmt="";
	//开户唐豆规则路径
	public static String acc_td_rules_path="";
	//开户增益豆扣回规则路径
	public static String acc_zyd_rules_path="";
	//挂失解挂限制金额
	public static String lost_solveLost ="";
	
	//多渠道监控平台
	//监控平台IP
	public static String channel_monitor_ip="";
	//监控平台端口号
	public static String channel_monitor_port="";
	//监控平台请求报文版本号
	public static String channel_monitor_req_version="";
	//监控平台请求超时时间
	public static int channel_monitor_time_out=0;
	//监控凭条设备检测默认周期
	public static int channel_monitor_check_time=0;
	
	/**
	 * 初始化
	 */
    public static void initProperty() { 
    	Properties prop =  getProperties();
    	
    	CZQ_CODE = (String) prop.get("CZQ_CODE");
    	DKQ_CODE = (String) prop.get("DKQ_CODE");
    	SFZDKQ_CODE = (String) prop.get("SFZDKQ_CODE");
    	MMJP_CODE = (String) prop.get("MMJP_CODE");
    	ZWY_CODE = (String) prop.get("ZWY_CODE");
    	EWM_CODE = (String) prop.get("EWM_CODE");
    	DYJ_CODE = (String) prop.get("DYJ_CODE");
    	
    	BP_NEW_IP = (String) prop.get("bp_new_ip");
    	BP_NEW_PORT = Integer.parseInt(prop.get("bp_new_port")+"");
        BP_IP = (String) prop.get("bp_ip");
        BP_PORT = Integer.parseInt(prop.get("bp_port")+"");
        BILL_FACE = (String) prop.get("bill_face");
        /*
         * 销户
         */
        FTP_IP = (String) prop.get("FTP_IP");
        FTP_PORT = (String) prop.get("FTP_PORT");
        FTP_USER = (String) prop.get("FTP_USER");
        FTP_PWD = (String) prop.get("FTP_PWD");
        FTP_LOCAL_PATH = (String) prop.get("FTP_LOCAL_PATH");
        BILL_VERSO= (String) prop.get("bill_verso");
        BILL_ID_SELF_JUST= (String) prop.get("bill_id_self_just");
        BILL_ID_SELF_AGAINST= (String) prop.get("bill_id_self_against");
        BILL_ID_SELF_FACE= (String) prop.get("bill_id_self_face");
        FTP_SER_PATH = (String) prop.get("FTP_SER_PATH");
        FTP_LOG_PATH = (String) prop.get("FTP_LOG_PATH");
        FTP_LOG_LOCAL_PATH = (String) prop.get("FTP_LOG_LOCAL_PATH");
        /*
         * 开户
         */
        FTP_IP_KH = (String) prop.get("FTP_IP_KH");
        FTP_PORT_KH = (String) prop.get("FTP_PORT_KH");
        FTP_USER_KH = (String) prop.get("FTP_USER_KH");
        FTP_PWD_KH = (String) prop.get("FTP_PWD_KH");
        FTP_SER_PATH_KH = (String) prop.get("FTP_SER_PATH_KH");
        FTP_CARDOPEN_SER_PATH = (String)prop.get("FTP_CARDOPEN_SER_PATH");
        
        /*
         * 增设密码
         */
        FTP_SER_PATH_ZM=(String)prop.get("FTP_SER_PATH_ZM");
        /*增设密码密码超限金额*/
        acc_zm_amt=(String)prop.get("acc_zm_amt");
        
        /*
         *	存单换开
         */
	    FTP_SER_PATH_HK=(String)prop.get("FTP_SER_PATH_HK");
        acc_hk_amt=(String)prop.get("acc_hk_amt");
        
        BILL_ID_AGENT_JUST= (String) prop.get("bill_id_agent_just");
        BILL_ID_AGENT_AGAINST= (String) prop.get("bill_id_agent_against");
        BILL_ID_AGENT_FACE= (String) prop.get("bill_id_agent_face");
        
        ID_CARD_SELF= (String) prop.get("id_card_self");
        ID_CARD_AGENT= (String) prop.get("id_card_agent");
        BILL_PDF= (String) prop.get("bill_pdf");
        BILL_HOST= (String) prop.get("bill_host");
        BILL_PORT= Integer.parseInt(prop.get("bill_port")+"");
        BILL_USER_NAME= (String) prop.get("bill_user_name");
        BILL_PASSWORD= (String) prop.get("bill_password");
        BILL_BASE_PATH= (String) prop.get("bill_base_path");
        BILL_FILE_PATH= (String) prop.get("bill_file_path");
        BILL_FILE_NAME= (String) prop.get("bill_file_name");
        BILL_INPUT= (String) prop.get("bill_input");
        BILL_JPG= (String) prop.get("bill_jpg");
        FTP_IP_DOWN= (String) prop.get("FTP_IP_DOWN");
        FTP_PORT_DOWN= (String) prop.get("FTP_PORT_DOWN");
        FTP_USER_DOWN= (String) prop.get("FTP_USER_DOWN");
        FTP_PWD_DOWN= (String) prop.get("FTP_PWD_DOWN");
        CITY_PATH=(String) prop.get("CITY_PATH");
        TIME_OUT = Integer.parseInt(prop.get("time_out")+"");
        OCR_MODE_PATH= (String) prop.get("OCR_MODE_PATH");
        OCR_SDK_PATH= (String) prop.get("OCR_SDK_PATH");
        CAMERA_PATH= (String) prop.get("camera_path");
        RE_CAMERA_PATH= (String) prop.get("re_camera_path");
        FACE_CHECK_SYS_PATH= (String) prop.get("face_check_sys_path");
        BH_TMP= (String) prop.get("bh_tmp");
        dzyz_url= (String) prop.get("dzyz_url");
        dzyz_ml= (String) prop.get("dzyz_ml");
        dzyz_template= (String) prop.get("dzyz_template");
        
        BILL_MANAGER=(String)prop.get("bill_manager");
        NEW_FACE_CHECK_SYS_PATH=(String)prop.get("new_face_compare_path");
        FTP_HUIHUA_SER_PATH = (String)prop.get("FTP_HUIHUA_SER_PATH");//汇划事后p端上传路径
        
        // 以下为测试类信息
        ACC_NO = (String) prop.get("accNo");
        CER_NO = (String) prop.get("certNo");
        TLLER_NO = (String) prop.get("tllerNo");
        AGENT_CARD_ID = (String) prop.get("agentIdCardNo");
        AGENT_CARD_NAME = (String) prop.get("agentIdCardName");
        ID_CARD = (String) prop.get("IdCardNo");
		ID_CARD_NAME = (String) prop.get("IdCardName");
		OPPO_ACCT_NO=(String)prop.get("OPPO_ACCT_NO");
		parent_unit_no=(String)prop.get("parent_unit_no");
		command=(String)prop.get("command");
		spid=(String)prop.get("spid");
		sppassword=(String)prop.get("sppassword");
		sUrl=(String)prop.get("sUrl");
		
		//以下字段为汇款类信息
		SIGNATURE_PATH = (String)prop.get("SIGNATURE_PATH");
		SIGNATURE_PATH_CANCEL=(String)prop.get("SIGNATURE_PATH_CANCEL");
		CAMEAR_COUNT=(String)prop.get("camera_count");
		MAX_AMT=(String)prop.get("max_amt");
		ALL_DAY_AMT=(String)prop.get("all_day_amt");
		WORK_DAY_ONCE_AMT=(String)prop.get("work_day_once_amt");
		REST_DAY_ONCE_AMT=(String)prop.get("rest_day_once_amt");
		AUTH_TWO_ONCE_AMT=(String)prop.get("auth_two_once_amt");
		//获取协议路径
		agreementPath=(String)prop.get("Agreement_Path");
		PRINT_AGREEMENT_NUMBER=(String)prop.get("print_Agreement_Number");
		//销户电子签名图片路径
		ACCCANCEL_SIGNATRUE_PATH = (String)prop.get("ACCCANCEL_SIGNATRUE_PATH");
		//选择对勾符号
		choice_ok_path = (String)prop.get("Choice_Ok_Path");
		//控制协议书是否打印
		IS_PRINT_AGREEMENT = (String)prop.get("is_print_agreement");
		//销户鉴伪不过超限金额
		acc_cancel_amt = (String)prop.get("acc_cancel_amt");
		//销户联网核查超限金额
		acc_cancel_ckeckIdAmt = (String)prop.get("acc_cancel_ckeckIdAmt");
		//销户授权超限金额
		acc_cancel_tellNoAmt = (String)prop.get("acc_cancel_tellNoAmt");
		//开户联网核查超限金额
		acc_open_ckeckIdAmt = (String)prop.get("acc_open_ckeckIdAmt");
		//开户授权超限金额
		acc_open_tellNoAmt = (String)prop.get("acc_open_tellNoAmt");
		//开户唐豆规则路径
		acc_td_rules_path=(String)prop.get("acc_td_rules_path");
		//开户增益豆扣回规则路径
		acc_zyd_rules_path=(String)prop.get("acc_zyd_rules_path");
		//挂失解挂申请书
		Lost_Path=(String)prop.get("Lost_Path");
		//挂失解挂申请书事后监督图片
		Lost_Shjd_Path=(String)prop.get("Lost_Shjd_Path");
		//挂失解挂pdf和jpg文件保存路径
		Lost_Pdf_Path=(String)prop.get("Lost_Pdf_Path");
		//挂失解挂事后监督图片上传路径
		FTP_SER_PATH_LOST=(String)prop.get("FTP_SER_PATH_LOST");
		//挂失解挂限制金额
		lost_solveLost = (String)prop.get("lost_solveLost");
		//挂失解挂事后申请书图片保存本地路径
		bill_bd_path=(String)prop.get("bill_bd_path");
		//挂失解挂事后申请书图片上传失败保存本地路径
		bill_sb_path=(String)prop.get("bill_sb_path");
		//挂失解挂事后申请书图片上传IP
		FTP_SH_IP=(String)prop.get("FTP_SH_IP");
		//挂失解挂事后申请书图片上传端口
		FTP_SH_PORT =(String)prop.get("FTP_SH_PORT");
		//挂失解挂事后申请书图片上传用户名
		FTP_SH_USER =(String)prop.get("FTP_SH_USER");
		//挂失解挂事后申请书图片上传密码
		FTP_SH_PWD =(String)prop.get("FTP_SH_PWD");
		//挂失解挂事后申请书图片上传路径
		FTP_SH_PATH=(String)prop.get("FTP_SH_PATH");
		//挂失解挂打印申请书失败保存路径
		bill_print_path=(String)prop.get("bill_print_path");		
		
		//监控平台
		channel_monitor_ip=(String)prop.get("channel_monitor_ip");
		channel_monitor_port=(String)prop.get("channel_monitor_port");
		channel_monitor_req_version=(String)prop.get("channel_monitor_req_version");
		channel_monitor_time_out = Integer.parseInt(prop.get("channel_monitor_time_out")+"");
		channel_monitor_check_time = Integer.parseInt(prop.get("channel_monitor_check_time")+"");
    } 
    
    /**
     * 加载配置文件数据
     * @return
     */
    public static Properties getProperties(){
    	InputStream in = null;
        Properties prop = new Properties();   
        BufferedReader br= null;
        try{
        	in = new FileInputStream("config\\config.properties");
            //in = new BufferedInputStream (new FileInputStream("config\\config.properties"));
        	br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        	prop.load(br); 
           
        }
        catch(Exception e){
            e.printStackTrace();
        }finally{
        	try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        return prop;
    }
    
    /**
     * 加载配置文件数据
     * @return
     */
    public static Properties getPropertiesVersion(){
    	InputStream in = null;
        Properties prop = new Properties();   
        BufferedReader br= null;
        try{
        	in = new FileInputStream("config\\version.properties");
            //in = new BufferedInputStream (new FileInputStream("config\\config.properties"));
        	br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        	prop.load(br); 
           
        }
        catch(Exception e){
            e.printStackTrace();
        }finally{
        	try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        return prop;
    }
}