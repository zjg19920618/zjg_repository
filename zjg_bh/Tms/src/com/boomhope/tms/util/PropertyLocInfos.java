package com.boomhope.tms.util;
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
public class PropertyLocInfos {
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
	/*银行信息文件(生产)的路径*/
	public static String BANKINFO_PATH_SC="";
	/*银行信息文件(测试)的路径*/
	public static String BANKINFO_PATH_CS="";
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
	/*新人脸识别系统路径*/
	public static String NEW_FACE_CHECK_SYS_PATH = "";	
	/*现场拍照*/
	public static String  CAMERA_PATH = "";
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
	
	/*凭证管理员账户*/
	public static String  BILL_MANAGER= "";
	
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
        BANKINFO_PATH_SC=(String)prop.getProperty("BANKINFO_PATH_SC");
        BANKINFO_PATH_CS=(String)prop.getProperty("BANKINFO_PATH_CS");
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
        TIME_OUT = Integer.parseInt(prop.get("time_out")+"");
        OCR_MODE_PATH= (String) prop.get("OCR_MODE_PATH");
        OCR_SDK_PATH= (String) prop.get("OCR_SDK_PATH");
        CAMERA_PATH= (String) prop.get("camera_path");
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