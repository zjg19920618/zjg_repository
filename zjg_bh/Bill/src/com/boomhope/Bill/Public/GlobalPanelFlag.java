package com.boomhope.Bill.Public;

/***
 * 交易容器标签
 * 
 * @author shaopeng
 *
 */
public class GlobalPanelFlag {

	public static int CurrentFlag; // 当前容器标签

	public final static int STOP_SERVICE = 999; // 服务终止页
	public final static int ERROR_MSG = 777; // 错误页
	public final static int PRINT_STOP_SERVICE = 555; // 子账户服务终止页
	public final static int ACCOUNT_ERROR_MSG = 333; // 错误页(开户)
	public final static int ADVERT = 0; // 默认广告页
	public final static int INPUTBILL_PANEL = 1; // 存单放入
	public final static int CHECKBILL_PANEL = 2; // 存单信息
	public final static int CHECKBILL_ING = 3; // 存单核查过度页
	public final static int INPUT_IDCARD = 4; // 插入本人身份证
	public final static int CHECK_IDCARD_ING = 5; // 身份证核查过度页
	public final static int CHECK_IDCARD_FAIL = 6; // 检查失败界面
	public final static int OUTPUT_IDCARD = 7; // 证件退出
	public final static int AGENT_SELECT = 8; // 代理人选择页面
	public final static int INPUT_AGENT_IDCARD = 9; // 代理人身份证
	public final static int CHECK_AGENT_IDCARD_ING = 10; // 代理人身份中核查过度页
	public final static int CHECK_AGENT_IDCARD_FAIL = 11; // 代理人检查失败界面
	public final static int POLICE_CHECK = 12; // 联网核查
	public final static int OUTPUT_AGENT_IDCARD = 13; // 代理人证件退出
	public final static int CANEL_FOR_SELECT = 14; // 销户转卡、转存单选择
	public final static int BANKNO_INPUT_SELECT = 15; // 银行卡号录入选择
	public final static int INPUT_BANK_CARD = 16; // 插入银行卡
	public final static int CHECK_BANK_CARD_ING = 17; // 银行卡信息读取中
	public final static int OUTPUT_BANK_CARD = 18; // 银行卡退出
	public final static int CANEL_BILL_TO_BANK_CARD = 19; // 销户至银行卡确认
	public final static int INPUT_BILL_PASS = 20; // 录入存单密码
	public final static int CHECK_INPUT_BILL_PASS_FAIL = 21; // 存单密码失败页
	public final static int CANCEL_BILL_SUCCESS = 22; // 销户成功界面（不使用）
	public final static int CHECK_BILL_FAIL = 23; // 存单核查失败
	public final static int POLICE_CHECK_FAIL = 24; // 联网核查失败
	public final static int SYSTEM_PROCESS_ING = 25; // 系统处理中
	public final static int SUP_TELLER_PASS = 26; // 授权密码
	public final static int CAMERA_PANEL = 27; // 拍照界面
	public final static int FACE_CHECK = 28; // 人脸识别
	public final static int NEGATION_PROCURATOR = 100; // 不存在代理人
	public final static int EXIST_PROCURATOR = 101; // 存在代理人
	public final static int ADDITION_NUMBER = 102; // 录入授权号
	public final static int ADDITION_FINGERPRINT = 103; // 录入指纹
	public final static int REGISTER_LOGIN = 104; // 登录
	public final static int FIRST_CARD_NO = 105; // 第一次录入银行卡号
	public final static int AGAIN_CARD_NO = 106; // 在次录入银行卡号
	public final static int POLICY_PASSWORD = 107; // 录入存单密码（不使用）
	public final static int ACCOUNT_CANCELLATION = 108; // 销户成功
	public final static int MENU_PANEL = 109; // 菜单界面
	public final static int ADDITION_NUMBER_FAILURE = 110; // 授权柜员号失败页
	public final static int SUP_TELLER_PASS_FAILURE = 111; // 授权密码失败页
	public final static int OUTPUT_IDCARD_FAILURE = 112; // 身份证校验失败 证件退出
	public final static int OUTPUT_IDCARD_FAILURES = 113; // 服务终止存单退出
	public final static int BILL_CORE_CHECK_FAIL = 9001; // 存单核心验证失败
	public final static int BILL_CREATE_GRAYSCALE_FAIL = 9002; // 存单生成灰度图失败
	public final static int BILL_CHECK_FAIL = 9003; // 存单鉴别真伪失败
	public final static int OCR_CHECK_FAIL = 9004; // 存单OCR识别失败
	public final static int BACKTRANS_AFFIRM_PANEL = 114;// 确认存单是否压箱页面
	public final static int CHECK_E_BANK_CARD_ING = 115;// 电子子账户查询中
	// 新增凭证打印
	public final static int PRINT_CHECK_SERVICE = 30; // 选择要办理的业务
	public final static int PRINT_CHECK_WAY = 31; // 选择办理人
	public final static int PRINT_INPUT_IDCARD = 32; // 插入本人身份证
	public final static int PRINT_CHECK_IDCARD_ING = 33; // 本人身份中核查过度页
	public final static int PRINT_CHECK_IDCARD_FAIL = 34; // 本人身份证信息读取失败
	public final static int PRINT_OUTPUT_IDCARD = 35; // 本人证件已退出
	public final static int PRINT_INPUT_AGENT_IDCARD = 36; // 插入代理人身份证
	public final static int PRINT_CHECK_AGENT_IDCARD_ING = 37; // 代理人身份中核查过度页
	public final static int PRINT_CHECK_AGENT_IDCARD_FAIL = 38; // 代理人身份证信息读取失败
	public final static int PRINT_OUTPUT_AGENT_IDCARD = 39; // 代理人证件退出
	public final static int PRINT_NEGATION_PROCURATOR = 40; // 不存在代理人，人脸识别失败，授权页
	public final static int PRINT_EXIST_PROCURATOR = 41; // 存在代理人
	public final static int PRINT_POLICE_CHECK = 42; // 联网核查
	public final static int PRINT_POLICE_CHECK_FAIL = 43; // 联网核查失败
	public final static int PRINT_CAMERA_PANEL = 44; // 拍照界面
	public final static int PRINT_FACE_CHECK = 45; // 人脸识别
	public final static int PRINT_FACE_CHECK_FAIL = 46; // 人脸识别失败
	public final static int PRINT_CHECK_PIC = 47; // 核查图片信息
	public final static int PRINT_ADDITION_NUMBER = 48; // 录入授权号
	public final static int PRINT_ADDITION_NUMBER_FAILURE = 49; // 授权柜员号失败页
	public final static int PRINT_SUP_TELLER_PASS = 50; // 录入授权密码
	public final static int PRINT_SUP_TELLER_PASS_FAILURE = 51; // 授权密码失败页
	public final static int PRINT_ADDITION_FINGERPRINT = 52; // 录入指纹
	public final static int PRINT_ADDITION_FINGERPRINT_FAILURE = 53; // 录入指纹失败页
	public final static int PRINT_CHECK_BANK = 54;//选择打印存单的银行卡
	public final static int PRINT_CARD_PASSWORD = 55; // 输入银行卡密码
	public final static int PRINT_CHOOSE_BILL = 56; // 选择打印的存单
	public final static int PRINT_CONFIRM_BILLMSG = 57; // 确认存单信息
	public final static int TRANS_PROCESS_ING = 58; // 系统处理中
	public final static int PRINT_BILL_SUCCESS = 59; // 存单打印成功
	public final static int PRINT_ERROR_MSG = 60; // 统一错误页
	public final static int PRINT_INPUT_CARD = 61; // 插入被代理人对应身份证页
	public final static int PRINT_STOP = 62; // 结束交易
	public final static int PRINT_NO_BANK_INFO = 63; // 无银行卡信息PRINT_ADMIN_LOGIN
	public final static int PRINT_CHECK_BANK_INFO = 64; // 选择要打印的银行卡(最新)
	public final static int CHECK_BANK_PASS_ING = 65; //银行卡密码校验页
	public final static int CHECK_BANK_INFO_ING = 66; //查询银行卡信息中
	public final static int CKECK_SERVICE_PATH= 67;//选择子账户打印办理路径（身份证，银行卡）
	public final static int CHECK_PRINT_OR_STATE = 68 ;//选择打印或者状态变更
	public final static int PRINT_BILL_STATE_CHAGE = 69 ;//状态变更页
	public final static int STATE_CHAGE_ING = 80 ;//状态变更过度页
	
	public final static int PRINT_INPUT_BANK_CARD = 81; // 插入银行卡
	public final static int PRINT_OUTPUT_BANK_CARD = 82; // 银行卡退出
	public final static int PRINT_BANK_CARD_ING = 83; // 插入的银行卡查询中
	public final static int PRINT_BANK_CARD_PRINT= 84; // 银行卡查询打印页面
	public final static int PRINT_BANK_CARD_STATE = 85; // 银行卡查询状态变更页面
	public final static int BANK_PASS_FAIL = 86; // 银行卡密码失败页
	
	
	public final static int PRINT_ADMIN_LOGIN = 70; // 管理员登录
	public final static int PRINT_CHECK_ADMIN_LOGIN = 71; // 管理员登录密码校验页
	public final static int PRINT_ADMIN_MANAGE = 72; // 终端管理页
	public final static int PRINT_ADMIN_PASS_FAIL = 73; // 管理员登录密码失败
	public final static int PRINT_PAPER_OUT = 74; //设置凭证信息
	public final static int PRINT_OK_PAPER_OUT = 75; //确认凭证信息
	public final static int PRINT_CHANGE_PASS = 76; //修改密码
	public final static int PAW_UPDATE_SUCCESS = 77; //修改密码成功页
	

	public final static int MIMEOGRAPHS = 117; // 打印页面
	public final static int POLICY_IDENTIFY = 118; // 手动输入存单信息
	public final static int POLICY_INQUIRE_PANEL = 119; // 加载手动输入存单信息
	public final static int INPUTBILL_PANELS = 120; // 再次存单放入
	
	public final static int STATE_CHANGE = 121 ;//状态变更页成功页面
	
	
	//开户
	public final static int SERVICE_OPTION_PANEL= 122 ;//业务选择页(整存整取 协议存款 私行快线)
	public final static int INSERT_THE_CARD_PANEL = 123;//开户插入银行卡
	public final static int BANK_CARD_PASSWORD_INPUT = 124;//输入银行卡密码(第1次)
	public final static int INPUT_DEPINFO_PANEL  = 125;//录入存单信息
	public final static int OK_INPUT_DEPINFO_PANEL = 126;//确认存单信息
	public final static int AGREEMENT_PANEL = 127;//协议存款产品页面
	public final static int AGREEMENT_NEXT_PANEL = 128;//协议存款产品第2页面
	public final static int DEP_CHECING_PANEL = 129;//交易处理中
	public final static int SUCCESS_DEP_PANEL = 130;//交易成功页面
	public final static int AGAIN_BANK_CARD_PASSWORD_INPUT = 131;//输入银行卡密码(第2次)
	public final static int WITH_CLOSE_TOPAYPANEL = 132;//输入存单付密码(第1次)
	public final static int AGAIN_WITH_CLOSE_TOPAYPANEL = 133;//输入存单密码(第2次)
	public final static int CATALOG_QXXL_PANEL = 134;//千禧系列页面(分页)
	public final static int CATALOG_YXCXL_PANEL = 135;//约享存系列页面
	public final static int CATALOG_LDCXL_PANEL = 136;//立得存系列页面
	public final static int CATALOG_RYCXL_PANEL = 137;//如意存，系列页面(分页)
	public final static int CATALOG_YXCFY_PANEL = 152;//约享存，系列页面(分页)
	public final static int CATALOG_LDCXL_ZXTX_PANEL = 141;//立得存自享，他享系列页面(分页)
	public final static int PROMPT_PAGES_PANEL = 143;//提示页面
	public final static int INFORMATION_SEARCHPANEL = 144;//信息查询中页面
	public final static int CATALOG_JXCRYCXL_PANEL = 145;//积享存系列页面(分页)
	public final static int CATALOG_RYCJFY_PANEL = 153;//如意存+系列页面(分页)
	public final static int AGREEMENT_PAGE = 146;//产品协议书页面
	public final static int CATALOG_XFYJYXL_PANEL = 147;//幸福1+1系列页面(分页)
	public final static int ENTERING_RYC_PANEL = 148;//如意存,立得存,约享存录入页面
	public final static int ENTERING_QXXL_PANEL = 149;//千禧 ,幸福1+1录入页面 
	public final static int ENTERING_JXC_PANEL = 150;//积享存 录入页面 
	public final static int ENTERING_RYCJ_PANEL = 151;// 如意存+录入页面 
	
	public final static int CHECK_CANCEL_ACCOUNT = 180; // 选择存单销户种类
	public final static int SUB_ACCNO_CANCEL_PROCESSING= 181;//子账户销户处理
	public final static int SUB_ACCNO_CANCEL_SUCCESS= 182;//子账户销户成功
	public final static int SUB_CANEL_BILL_TO_BANK_CARD= 183;//子账户销户转卡确认页
	
	public final static int BILL_CHECK_FAIL1= 184;//初始化凭证检查失败页
	public final static int BILL_UPDATE= 185;//初始化凭证修改页
	
	public final static int CHECK_BILL_PRINT= 186;//判断是否凭条打印页
	public final static int BILL_PRINT_PROCESSING= 187;//普通账户凭条打印中
	public final static int BILL_PRINT_SUCCESS= 188;//凭条打印成功页
	
	public final static int SERVICE_TERMINATION = 154;// 服务终止页面 
	public final static int AGAIN_RED_PACKET_PANEL = 155;// 红包页面 
	public final static int INPUT_BANK_CARD_PANEL = 156;// 录入收益人卡号页面(自享，他享) 
	public final static int SYR_PAGE = 157;// 收益人信息页面(自享，他享) 
	public final static int OK_JXC_INFO_PANEL = 158;// 信息确认页页面(积享存)
	public final static int JXC_SUCCESS_PAGE = 159;// 积享存存入成功页
	public final static int TERMINATION_OF_SERVICE = 160;// 开户服务终止（主动退卡）
	public final static int SUB_BILL_PRINT_PROCESSING= 189;//子账户凭条打印中
	public final static int OPEN_SUCCESS= 190;//开户成功页面
	public final static int CATALOG_AXCXL_PANEL= 191;//安享存系列
	public final static int PRINT_INFO_PANEL= 192;//检测打印机和凭证号错误页
	
	//私行快线
	public final static int PRIVATE_LINE_PRODUCTS= 300;//私行快线产品页面
	public final static int CATALOG_SHYXCFY_PANEL= 301;//私行快线约享存，系列页面(分页)
	public final static int PRIVATE_LINE_NEXT= 302;//私行快线产品页面(第二页)
	
    //现金开户
	public final static int ENTRY_MODE= 400;//信息录入方式页面
	public final static int ORDER_NUMBER= 401;//输入订号
	public final static int MONEYBOX_PASSWORD= 468;//输入密码
	public final static int MONEYBOX_SCANNING_PANEL= 469;//二维码扫描
	
	public final static int DEPUTY_SELECTION= 402;//现金开户代理人选择
	public final static int PERSONAL_IDENTIFICATION_CARD= 403;//插入本人身份证
//	public final static int MONEYBOX_POLICE_CHECK= 404;//现金开户联网核查
	public final static int MONEYBOX_OUTPUT_IDCARD =405; // 证件退出
	public final static int MONEYBOX_CHECK_IDCARD_ING =406; // 现金开户本人联网核查    （现金开户身份证核查过度页需要加联网核查接口）
	public final static int MONEYBOX_CHECK_IDCARD_ING_AGENT =470; // 现金开户代理人联网核查 
	
	public final static int MONEYBOX_INPUT_AGENT_IDCARD =407; // 现金开户代理人身份证
	public final static int MONEYBOX_CUSTOMER_INFORMATION =408; // 录入客户信息
	public final static int MONEYBOX_OUTPUT_AGENT_IDCARD =409; // 代理人证件退出
	public final static int BUSINESS_SELECTION =410; // 业务选择（银行卡开户 现金开户）
	public final static int MONEYBOX_MISTAKE =411; // 统一错误页
	public final static int MONEYBOX_TERMINATION_OF_SERVICE =412; // 统一服务终止页面
	public final static int MONEYBOX_PAGE_SELECTL =413;//业务选择页(整存整取 协议存款 私行快线)
	public final static int MONEYBOX_INPUTDEP_INFO =414;//整存整取
	public final static int MONEYBOX_ADDITION_NUMBER =415;//输入柜员号
	public final static int MONEYBOX_SYSTEM_PROCESSING_PANEL =417;//处理中
	public final static int MONEYBOX_PRINT_SUP_TELLER_PASS =418;// 录入授权密码
	public final static int MONEYBOX_ADDITION_FINGERPRINT =419;// 录入指纹
	public final static int MONEYBOX_OK_INPUT_DEPINFO_PANEL =420;//确认存单信息
	public final static int MONEYBOX_JELLY_BEAN =421;//请选择糖豆兑换方式（不使用）
	public final static int MONEYBOX_VERIFICATION_CODE =422;//请输入手机验证码（不使用）
	public final static int MONEYBOX_WITH_CLOSE_TOPAYPANEL =423;//输入存单付密码(第1次)
	public final static int MONEYBOX_AGAIN_WITH_CLOSE_TOPAYPANEL =424;//输入存单付密码(第2次)
	public final static int MONEYBOX_AGREEMENT_PANEL =425;//协议存款产品页面
	public final static int MONEYBOX_AGREEMENT_NEXT_PANEL =426;//协议存款产品第2页面
	public final static int MONEYBOX_PRIVATE_LINE_PRODUCTS =427;//私行快线产品页面
	public final static int MONEYBOX_PRIVATE_LINE_PRODUCTS_NEXT_PAGE =467;//私行快线产品页面第2页面
	public final static int MONEYBOX_NEGATION_PROCURATOR =428;//请授权不存在代理人页面
	public final static int MONEYBOX_EXIST_PROCURATOR =429;//请授权存在代理人页面
	public final static int MONEYBOX_AGAIN_RED_PACKET_PANEL = 430;// 红包页面 
	public final static int MONEYBOX_SUCCESS_DEP_PANEL = 431;// 开户成功页面  
	public final static int MONEYBOX_DEP_CHECING_PANEL = 432;// 交易处理中	
	public final static int MONEYBOX_CATALOG_YXCTP_PANEL = 433;// 约享存图片页面
	public final static int MONEYBOX_CATALOG_SHYXCTP_PANEL = 441;// 私行约享存图片页面
	public final static int MONEYBOX_CATALOG_LDCTP_PANEL = 434;// 立得存图片页面	
	public final static int MONEYBOX_CATALOG_SHLDCTP_PANEL = 443;// 私行立得存图片页面	
	public final static int MONEYBOX_CATALOG_AXCXL_PANEL = 464;// 安享存页面	
	public final static int MONEYBOX_CATALOG_SHAXCXL_PANEL = 474;// 私行安享存页面	
	public final static int MONEYBOX_AGREEMENT_PAGE = 475;// 协议书	
	
	public final static int MONEYBOX_QXXL_PANEL = 435;//千禧系列页面(分页)
	public final static int MONEYBOX_SHQXXL_PANEL = 436;//私行千禧系列页面(分页)
	public final static int MONEYBOX_RYCXL_PANEL = 437;//如意存系列页面(分页)
	public final static int MONEYBOX_SHRYCXL_PANEL = 438;//私行如意存系列页面(分页)
	public final static int MONEYBOX_CATALOG_YXCFY_PANEL = 439;//约享存系列页面(分页)
	public final static int MONEYBOX_CATALOG_SHYXCFY_PANEL = 440;//私行约享存系列页面(分页)
	public final static int MONEYBOX_CATALOG_XFYJYXL_PANEL = 442;//幸福1+1存系列页面(分页)
	public final static int MONEYBOX_CATALOG_SHXFYJYXL_PANEL = 473;//私行幸福1+1存系列页面(分页)
	public final static int MONEYBOX_CATALOG_LDCXL_ZXTX_PANEL = 444;//立得存存系列页面(分页)
	public final static int MONEYBOX_CATALOG_SHLDCXL_ZXTX_PANEL = 445;//私行立得存存系列页面(分页)	
	public final static int MONEYBOX_CATALOG_JXCRYCXL_PANEL = 446;//积享存系列页面(分页)
	public final static int MONEYBOX_CATALOG_SHJXCRYCXL_PANEL = 447;//私行积享存系列页面(分页)
	public final static int MONEYBOX_CATALOG_RYCJFY_PANEL = 448;//如意+系列页面(分页)
	public final static int MONEYBOX_CATALOG_SHRYCJFY_PANEL = 449;//私行如意+系列页面(分页)
	public final static int MONEYBOX_CATALOG_AXCFY_PANEL = 465;//安享存系列页面(分页)
	public final static int MONEYBOX_CATALOG_SHAXCFY_PANEL = 466;//私行安享存系列页面(分页)
	
	public final static int MONEYBOX_ENTERING_QXXL_PANEL = 450;//千禧录入页面
	public final static int MONEYBOX_ENTERING_SHQXXL_PANEL = 451;//私行千禧录入页面
	public final static int MONEYBOX_ENTERING_XFYJYXL_PANEL = 452;//幸福1+1录入页面  
	public final static int MONEYBOX_ENTERING_SHXFYJYXL_PANEL = 473;//私行幸福1+1录入页面  
	public final static int MONEYBOX_ENTERING_RYC_PANEL = 453;//如意存录入页面  
	public final static int MONEYBOX_ENTERING_SHRYC_PANEL = 454;//私行如意存录入页面  
	public final static int MONEYBOX_ENTERING_LDC_PANEL = 455;//立得存录入页面
	public final static int MONEYBOX_ENTERING_SHLDC_PANEL = 456;//私行立得存录入页面
	public final static int MONEYBOX_ENTERING_YXC_PANEL = 457;//约享存录入页面
	public final static int MONEYBOX_ENTERING_SHYXC_PANEL = 458;//私行约享存录入页面	
	public final static int MONEYBOX_ENTERING_JXC_PANEL = 459;//积享存 录入页面 
	public final static int MONEYBOX_ENTERING_SHJXC_PANEL = 460;//私行积享存 录入页面 
	public final static int MONEYBOX_ENTERING_RYCJ_PANEL = 461;//如意加 录入页面 
	public final static int MONEYBOX_ENTERING_SHRYCJ_PANEL = 462;//私行如意加 录入页面 
	public final static int MONEYBOX_ENTERING_AXC_PANEL = 471;//安享存录入页面 
	public final static int MONEYBOX_ENTERING_SHAXC_PANEL = 472;//私行安享存录入页面 
	
	public final static int MONEYBOX_JXC_SUCCESS_PAGE = 463;//积享存存入成功页面
	public final static int MONEYBOX_INPUT_BANK_CARD_PANEL = 476;//现金开户录入收益人卡号页面(自享，他享) 
	public final static int MONEYBOX_SYR_PAGE = 477;// 现金开收益人信息页面(自享，他享) 
	public final static int MONEYBOX_OK_JXC_INFO_PANEL = 478;// 信息确认页页面(积享存)
	public final static int MONEYBOX_PROMPT_PAGES_PANEL = 479;// 钱柜温馨提示页面
	public final static int MONEYBOX_ADDITION_NUMBER_FAILURE = 480;// 钱柜授权柜员号失败页
	public final static int MONEYBOX_SUP_TELLER_PASS_FAILURE = 481;// 钱柜授权密码失败页
}
