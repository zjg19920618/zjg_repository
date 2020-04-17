package com.boomhope.Bill.Comm;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.print.DocFlavor;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintJobAttributeSet;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import sun.misc.BASE64Decoder;

import com.boomhope.Bill.Public.GlobalParameter;
import com.boomhope.Bill.TransService.BillPrint.Bean.BillPrintBean;
import com.boomhope.Bill.TransService.BillPrint.Bean.BillPrintSubAccInfoBean;
import com.boomhope.Bill.TransService.BillPrint.Interface.InterfaceSendMsg;
import com.boomhope.Bill.TransService.BillPrint.Interface.SearchProducRateInfo;
import com.boomhope.Bill.Util.DateTool;
import com.boomhope.Bill.Util.DateUtil;
import com.boomhope.Bill.Util.HttpClientUtil;
import com.boomhope.Bill.Util.Property;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

/**
 * 
 * Title:协议书打印
 * Description:
 * @author hk
 * @date 20170810
 * 
 */
public class PrintAgreements {

	private static Logger logger = Logger.getLogger(PrintAgreements.class);
	private static Dispatch doc=null;
	private static Dispatch docs = null;
	private static Dispatch selection = null;
	private static ActiveXComponent word = null;
	private static String printName =null;//打印机名字
	
	private static String c1;//利率1
	private static String c2;//利率2
	private static String c3;//利率3
	private static String c4;//利率4
	private static String c5;//利率5
	private static String c6;//利率6
	private static String c7;//利率7
	private static String c8;//利率2
	private static String choicePath = Property.choice_ok_path;
	private static BillPrintBean billBean;//打印协议
	private static BillPrintSubAccInfoBean transBean;//打印的子账户
	private static Map channelMap;//开户渠道 
	private static String OpenDate;//开户日期
	
	
	public static void main(String[] args) {
		try {
			SimpleDateFormat sdf1=new SimpleDateFormat("yyyy/MM/dd");
			Date date1 = sdf1.parse("2017/09/29");//开户日
			Date date2 = sdf1.parse("2017/09/30");//到期日
			long time1 = date1.getTime();
			long time2 = date2.getTime();
			long betTime = time2-time1;
		} catch (Exception e) {
			logger.error("计算存期失败："+e);
		}
	}
	
	/**
	 * 设置渠道号对应的渠道信息
	 */
	private static void setChannelMap(){
		channelMap=new HashMap<>();
		channelMap.put("00", "柜台");
		channelMap.put("01", "本行ATM");
		channelMap.put("08", "机具");
		channelMap.put("50", "随身银行");
		channelMap.put("90", "网银");
		channelMap.put("0001", "AB系统");
		channelMap.put("0002", "帐务集中");
		channelMap.put("0005", "本行ATM");
		channelMap.put("0014", "网银");
		channelMap.put("0028", "手机银行系统");
		channelMap.put("0035", "机具");
		channelMap.put("0041", "随身银行");
		channelMap.put("0003", "信贷系统");
		channelMap.put("0007", "POS系统");
		channelMap.put("0008", "柜面通本代他");
		channelMap.put("0009", "银联");
		channelMap.put("0010", "TIPS系统");
		channelMap.put("0011", "非税系统");
		channelMap.put("0012", "支付密码系统");
		channelMap.put("0015", "综合理财");
		channelMap.put("0016", "NC");
		channelMap.put("0017", "银校通系统");
		channelMap.put("0018", "钱柜");
		channelMap.put("0019", "事后监督系统");
		channelMap.put("0020", "款箱系统");
		channelMap.put("0021", "核心系统");
		channelMap.put("0022", "卡系统");
		channelMap.put("0024", "黑名单系统");
		channelMap.put("0025", "互联网支付平台");
		channelMap.put("0026", "积分系统");
		channelMap.put("0027", "动态密码锁系统");
		channelMap.put("0029", "人力资源系统");
		channelMap.put("0031", "对账系统");
		channelMap.put("0032", "财政电子化支付系统");
		channelMap.put("0033", "回单机系统");
		channelMap.put("0034", "发卡机系统");
		channelMap.put("0036", "数据仓库");
		channelMap.put("0038", "股金系统");
		channelMap.put("0039", "基金系统");
		channelMap.put("0040", "电子账户系统");
		channelMap.put("0042", "移动发卡机系统");
		channelMap.put("0043", "金税系统");
		channelMap.put("0044", "电信诈骗系统");
		channelMap.put("0046", "快柜系统");
		channelMap.put("9999", "前置内部渠道");
		channelMap.put("VS01", "帐务集中定时查询");
		
		
	}
	
	/**
	 * 分配协议
	 * @Param Agreement 协议类型
	 */
	public static String print(String Agreement,String name){
		 
		String fileName=null;
		Property.initProperty();
		String agreement=Property.agreementPath;
		logger.info(agreement);
		switch(Agreement){
		case "JX":
			if(name.contains("A")){
				fileName="JXC_a_Agreement.docx";break;
			}else if(name.contains("B")){
				fileName="JXC_b_Agreement.docx";break;
			}else if(name.contains("C")){
				fileName="JXC_c_Agreement.docx";break;
			}
		case "RY":
			if(name.contains("A")){
				fileName="RYC_a_Agreement.docx";break;
			}else if(name.contains("B")){
				fileName="RYC_b_Agreement.docx";break;
			}else if(name.contains("C")){
				fileName="RYC_c_Agreement.docx";break;
			}
		case "RJ":
			if(name.contains("A")){
				fileName="RYC+_a_Agreement.docx";break;
			}else if(name.contains("B")){
				fileName="RYC+_b_Agreement.docx";break;
			}else if(name.contains("C")){
				fileName="RYC+_c_Agreement.docx";break;
			}
		case "AX":
			if(name.contains("A")){
				fileName="AXC_a_Agreement.docx";break;
			}else if(name.contains("B")){
				fileName="AXC_b_Agreement.docx";break;
			}else if(name.contains("C")){
				fileName="AXC_c_Agreement.docx";break;
			}
		case "LZ":fileName="LDC_Agreement.docx";break;
		case "LT":fileName="LDC_Agreement.docx";break;
		case "YA":
			if(name.contains("a")){
				fileName="YXC_A_a_Agreement.docx";break;
			}else if(name.contains("b")){
				fileName="YXC_A_b_Agreement.docx";break;
			}else if(name.contains("c")){
				fileName="YXC_A_c_Agreement.docx";break;
			}
		case "YB":
			if(name.contains("a")){
				fileName="YXC_B_a_Agreement.docx";break;
			}else if(name.contains("b")){
				fileName="YXC_B_b_Agreement.docx";break;
			}else if(name.contains("c")){
				fileName="YXC_B_c_Agreement.docx";break;
			}
		case "YC":
			if(name.contains("a")){
				fileName="YXC_C_a_Agreement.docx";break;
			}else if(name.contains("b")){
				fileName="YXC_C_b_Agreement.docx";break;
			}else if(name.contains("c")){
				fileName="YXC_C_c_Agreement.docx";break;
			}
		case "XF":fileName="XF_Agreement.docx";break;
		case "QX":fileName="QXC_Agreement.docx";break;
		case "CC":fileName="HLC_Agreement.docx";break;
		case "FL":
			if(name.contains("活动专属")){
				fileName="juXC_HD_Agreement.docx";break;
			}else{
				fileName="JuXC_Agreement.docx";break;
			}
		case "FM":
			if(name.contains("活动专属")){
				fileName="juXC_HD_Agreement.docx";break;
			}else{
				fileName="JuXC_Agreement.docx";break;
			}
		}
		String filePath=agreement+"\\"+transBean.getAgreementEdition()+"\\"+fileName;
		logger.info(filePath);
		return filePath;
	}
	
	
	/**
	 * 初始化Doc
	 */
	private static boolean getDocInit(String str){
		logger.info("开始加载word模板");
		try {
			//获取可用打印机
			getPrinterName();
			word=new ActiveXComponent("Word.Application");
			Dispatch.put(word, "Visible", new Variant(false));
			docs=word.getProperty("Documents").toDispatch();
			//设置打印机
			word.setProperty("ActivePrinter", new Variant(printName));
			Property.initProperty();
			doc=Dispatch.call(docs, "Open", str).toDispatch();
			selection = Dispatch.get(word, "Selection").toDispatch();
			return true;
		} catch (Exception e) {
			logger.error("打印机初始化失败："+e);
			return false;
		}
	}
	
	/**
	 * 获取打印机名称
	 * @param productedCode
	 * @param transBean 
	 * @return
	 */
	private static void getPrinterName(){
		HashPrintJobAttributeSet hpjas = new HashPrintJobAttributeSet();
		DocFlavor df = DocFlavor.INPUT_STREAM.AUTOSENSE;
		PrintService[] ps = PrintServiceLookup.lookupPrintServices(df, hpjas);
		
		for(int i=0;i<ps.length;i++){
			if(ps[i].getName().contains("FX DocuPrint P228 db")){
				printName="FX DocuPrint P228 db";
			}else if(ps[i].getName().contains("Brother HL-2240 series")){
				printName="Brother HL-2240 series";
			}else if (ps[i].getName().contains("Brother HL-2260 Printer")) {
				   printName = "Brother HL-2260 Printer";
				 
			   }
		}
		
	}
	
	public static boolean PrintBills(final String productedCode,final BillPrintBean bean){
		logger.info("调用打印机准备");
		transBean=bean.getSubBean();
		billBean=bean;
		setChannelMap();
		boolean flag = false;
		String openDateStr = transBean.getOpenDate().trim();
		Pattern p=Pattern.compile("^[0-9]*$");
		Matcher ma=p.matcher(openDateStr);
		boolean pMa= ma.matches();
		if(pMa && openDateStr.length()==8){
			OpenDate=openDateStr.trim();
		}else{
			OpenDate=openDateStr.substring(0,4)+openDateStr.substring(5,7)+openDateStr.substring(8);
		}
		try {
			String dzyz="";//电子印章
			if("JX".equals(productedCode)||"RJ".equals(productedCode)){
				if(!getDetermDate()){
					return flag;
				}
			}
			if(transBean.isHavePic()==false){
				//拼接调用电子印章所需要的参数
				JSONObject jsonObject = getJsons(transBean);
				// 调用电子印章http接口（base64编码）
				dzyz = getDZYZ(Property.dzyz_url, jsonObject);
				logger.info("电子印章："+dzyz);
				if(dzyz!=null){
					//将base64编码转换成图片
					flag = GenerateImage(dzyz, Property.dzyz_ml);
					//获取电子印章
					if(!flag){
						return flag;
					}
				}else{
					logger.error("获取电子印章失败");
					return flag;
				}
				
			}
			
			String path = print(productedCode,transBean.getProductName());
			ComThread.InitSTA();
			if(!getDocInit(path)){
				return flag=false;
			}
			
			switch(productedCode){
			case "JX":
				flag=jxPrint(transBean);
				break;
			case "RY":
				flag=ryPrint(transBean);
				break;
			case "RJ":
				flag=ryjPrint(transBean);
				break;
			case "AX":
				flag=axPrint(transBean);
				break;
			case "LZ":
				flag=ldPrint(productedCode,transBean);
				break;
			case "LT":
				flag=ldPrint(productedCode,transBean);
				break;
			case "YA":
				flag=yxPrint(transBean);
				break;
			case "YB":
				flag=yxPrint(transBean);
				break;
			case "YC":
				flag=yxPrint(transBean);
				break;
			case "XF":
				flag=qxPrint(transBean);
				break;
			case "QX":
				flag=qxPrint(transBean);
				break;
			case "FL":
				flag=juxcPrint(transBean);
			case "FM":
				flag=juxcPrint(transBean);
			case "CC":
				flag=hlcPrint(transBean);
			}
			
			if(!flag){
				return flag;
			}
			
			if(transBean.isHavePic()){
				Dispatch picture =null;
				try {
					//打印电子签名
					if(transBean.getOldAccNo()!=null){
						
						picture = Dispatch.call(Dispatch.get(selection, "InLineShapes").toDispatch(),
								"AddPicture","D:\\"+transBean.getOldAccNo().trim()+"-"+transBean.getSubAccNo().trim()+"_DZQM.png").toDispatch();
						
					}else{
						
						picture = Dispatch.call(Dispatch.get(selection, "InLineShapes").toDispatch(),
								"AddPicture","D:\\"+transBean.getAccNo().trim()+"-"+transBean.getSubAccNo().trim()+"_DZQM.png").toDispatch();
					}
					Dispatch.call(picture, "Select"); // 选中图片
					Dispatch.put(picture, "Width", new Variant(200));
					Dispatch.put(picture, "Height",new Variant(90));
					Dispatch ShapeRange = Dispatch.call(picture, "ConvertToShape").toDispatch(); // 取得图片区域
					Dispatch WrapFormat = Dispatch.get(ShapeRange, "WrapFormat").toDispatch(); // 取得图片的格式对象
					Dispatch.put(WrapFormat, "Type", 5);
					Dispatch shapeRange = Dispatch.get(selection, "ShapeRange").toDispatch();  
			        Dispatch.put(shapeRange, "Left", new Variant(30));
			        Dispatch.put(shapeRange, "Top", new Variant(-120));
			        flag=true;
				} catch (Exception e) {
					logger.error("打印签名失败："+e);
					return flag=false;
				}
				
				try {
					//打印电子印章
					if(transBean.getOldAccNo()!=null){
						
						picture = Dispatch.call(Dispatch.get(selection, "InLineShapes").toDispatch(),
								"AddPicture", "D:\\"+transBean.getOldAccNo().trim()+"-"+transBean.getSubAccNo().trim()+"_DZYZ.png").toDispatch();
					}else{
						
					    picture = Dispatch.call(Dispatch.get(selection, "InLineShapes").toDispatch(),
								"AddPicture", "D:\\"+transBean.getAccNo().trim()+"-"+transBean.getSubAccNo().trim()+"_DZYZ.png").toDispatch();
					}
					Dispatch.call(picture, "Select"); // 选中图片
					Dispatch.put(picture, "Width", new Variant(104));
					Dispatch.put(picture, "Height",new Variant(104));
					Dispatch ShapeRange = Dispatch.call(picture, "ConvertToShape").toDispatch(); // 取得图片区域
					Dispatch WrapFormat = Dispatch.get(ShapeRange, "WrapFormat").toDispatch(); // 取得图片的格式对象
					Dispatch.put(WrapFormat, "Type", 5);
					Dispatch shapeRange = Dispatch.get(selection, "ShapeRange").toDispatch();  
					Dispatch.put(shapeRange, "Left", new Variant(340));
					Dispatch.put(shapeRange, "Top", new Variant(-130));
					flag = true;
				} catch (Exception e) {
					logger.error("电子印章打印失败"+e);
					return flag=false;
				}
				
			}else{
				if(dzyz != null && !"-1".equals(dzyz)){
					try {
						//打印电子印章
						Dispatch picture = Dispatch.call(Dispatch.get(selection, "InLineShapes").toDispatch(),
								"AddPicture", Property.dzyz_ml).toDispatch();
						Dispatch.call(picture, "Select"); // 选中图片
						Dispatch.put(picture, "Width", new Variant(104));
						Dispatch.put(picture, "Height",new Variant(104));
						Dispatch ShapeRange = Dispatch.call(picture, "ConvertToShape").toDispatch(); // 取得图片区域
						Dispatch WrapFormat = Dispatch.get(ShapeRange, "WrapFormat").toDispatch(); // 取得图片的格式对象
						Dispatch.put(WrapFormat, "Type", 5);
						Dispatch shapeRange = Dispatch.get(selection, "ShapeRange").toDispatch();  
						Dispatch.put(shapeRange, "Left", new Variant(340));
						Dispatch.put(shapeRange, "Top", new Variant(-130));
						flag = true;
					} catch (Exception e) {
						logger.error("电子印章打印失败"+e);
						return flag=false;
					}
					
				}else{
					logger.error("获取电子印章失败");
					flag=false;
				}
			}
			
	        Dispatch.call(doc, "PrintOut");//打印
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("打印存单程序异常"+e);
			return flag=false;
		}finally{
			try {
				if(doc!=null){
	                Dispatch.call(doc, "Close",new Variant(0));
	                Dispatch.call(word, "Quit");
	                ComThread.Release();
	                doc = null;
	                docs = null;
	                selection = null;
	            }
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		return flag;
	}
	
	
	
	//扫描Doc文件，判断文件中是否包含该字符串，如果包含则返回true
	private static boolean find(String text){
		Dispatch find = word.call(selection, "Find").toDispatch(); 
        // 设置要查找的内容 
        Dispatch.put(find, "Text", text); 
        // 向前查找 
        Dispatch.put(find, "Forward", "True"); 
        // 设置格式 
        Dispatch.put(find, "Format", "True"); 
        // 大小写匹配 
        Dispatch.put(find, "MatchCase", "True"); 
        // 全字匹配 
        Dispatch.put(find, "MatchWholeWord", "True"); 
        // 查找并选中 
        boolean b = Dispatch.call(find, "Execute").getBoolean();
        return b;
	}
	/**
	 * 调用电子印章接口
	 * @param url
	 * @param paramMap
	 * @return
	 */
	public static String getDZYZ(String url,JSONObject paramMap){
		HttpClientUtil httpClientUtil = new HttpClientUtil();
		String result = httpClientUtil.httpPost(url, paramMap);
		return result;
	}
	
	/**
	 * 拼接调用电子印章接口所需要的参数
	 * 银行卡开户
	 * @param transBean
	 * @param globalParameter
	 * @return
	 */
	public static JSONObject getJsons(BillPrintSubAccInfoBean transBean) {
		logger.info("开始拼接调用电子印章接口所需要的参数");
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("channel", "0035");// 渠道号
		jsonObject.put("fserialno", OpenDate+transBean.getCreateSvrNo().trim());// 开始日期+流水号
		jsonObject.put("instno", GlobalParameter.branchNo);// 机构号
		jsonObject.put("tellerno", GlobalParameter.tellerNo);// 柜员号
		jsonObject.put("chl_tran_code", "");// 外围系统交易码
		jsonObject.put("chl_tran_name", "");// 外围系统交易名称
		jsonObject.put("vouchername", "协议书");// 凭证名称
		jsonObject.put("sealtype", "0001");// 电子印章种类（业务专用章）

		// 拼接tradeinfo参数值
		Map<String, String> map = new HashMap<String, String>();
		map.put("产品代码",transBean.getProductCode());// 产品代码
		map.put("储种",transBean.getProductName());// 产品名称
		map.put("账号",transBean.getAccNo());// 设置账号
		map.put("子账号", transBean.getSubAccNo());//子账号
		map.put("户名", billBean.getCardName());// 设置带*号的卡名
		map.put("金额(小写)", "CNY" + transBean.getOpenATM());// 设置存款金额（小写）
		map.put("存入日",transBean.getOpenDate());// 设置开始日期
		map.put("到期日", transBean.getEndIntDate());
		map.put("存期", transBean.getDepTerm());// 设置存期
		map.put("利率", transBean.getOpenRate() + "%");// 设置定期利率

		map.put("柜员号",GlobalParameter.tellerNo);
		map.put("是否提前支取", "");
		map.put("开户渠道", (String)channelMap.get(transBean.getChannel()));
		jsonObject.put("tradeinfo", JSONObject.fromObject(map).toString());
		logger.info("拼接调用电子印章接口所需要的参数结束---结果为-->" + jsonObject.toString());
		return jsonObject;
	}
	
	
	
	/**
	 * base64编码转换图片
	 * @param imgStr 	//base64编码的字符串
	 * @param rootPath	//保存的路径（图片完整路径）
	 * @return
	 */
	public static boolean GenerateImage(String imgStr,String rootPath) {
		if (imgStr == null) // 图像数据为空
			return false;
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			// Base64解码
			byte[] b = decoder.decodeBuffer(imgStr);
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {// 调整异常数据
					b[i] += 256;
				}
			}
			OutputStream out = new FileOutputStream(rootPath);
			out.write(b);
			out.flush();
			out.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * 利率获取
	 */
	public static boolean getRateInfo(BillPrintSubAccInfoBean transBean){
		Map<String,String> map = new HashMap<String, String>();
		BigDecimal fRate = null;
		try {
			billBean.getReqMCM001().setReqBefor("02864");
			Map map02864 = InterfaceSendMsg.inter02864(transBean);
			if (!"000000".equals(map02864.get("resCode"))) {
				logger.error("利率查询异常：" + map02864.get("errMsg"));
				return false;
			}else{
				fRate =new BigDecimal(((String)map02864.get("float")).trim());
				List<SearchProducRateInfo> list = (List<SearchProducRateInfo>) map02864.get("KEY_PRODUCT_RATES");
				StringBuffer str = new StringBuffer();
				if(transBean.getProductCode().startsWith("FL") || transBean.getProductCode().startsWith("FM")){
					float floatRet = Float.parseFloat((String)map02864.get("float"));
					String s = "";
					
					SearchProducRateInfo producRateInfo=list.get(0);
					//获得存单的封闭期	
					String saveDate = producRateInfo.getSavingCount();//存期
					String closeDate="";
					if(saveDate.indexOf("D")!=-1){
						 closeDate = saveDate.substring(0,saveDate.indexOf("D")+1).toUpperCase();
					}else{
						closeDate = saveDate.substring(0,3).toUpperCase();
					}
					String rate = producRateInfo.getRate();//封闭期利率
					double b = Double.parseDouble(rate);
					BigDecimal a1 = new BigDecimal(floatRet);
					BigDecimal b2 = new BigDecimal(b);
					DecimalFormat df1 = new DecimalFormat("0.0000");
					String rate2 = df1.format(a1.add(b2).doubleValue());
					String startDate = producRateInfo.getLockStarDate();
					String endDate = producRateInfo.getLockEndDate();
					if(closeDate.indexOf("D")!=-1){
						s = "锁定期："+startDate+"-"+endDate+",锁定期利率："+rate2+"%";
					}else if(closeDate.indexOf("M")!=-1){
						s = "锁定期："+startDate+"-"+endDate+",锁定期利率："+rate2+"%";
					}
						
					if(s.toString().trim().length()!=0){
						s = "提示："+s;
						s.replaceAll("%(?![0-9a-fA-F]{2})", "%25");
					}
					transBean.setPrintL52Str(s);
				}else{
					for (int i = 1; i <= list.size(); i++) {
						
						SearchProducRateInfo producRateInfo = list.get(i-1);
						producRateInfo.setInteDate(OpenDate);
						System.out.println("第"+i+"个利率信息："+producRateInfo.toString());
						BigDecimal b2 = new BigDecimal(producRateInfo.getRate().trim());
						BigDecimal b3 = b2.add(fRate);
						System.out.println("利率："+b3);
						if(i==1){
							if(transBean.getProductCode().startsWith("AX")){
								map.put("c"+i, b3+"%");
							}else{
								map.put("c"+i, b2+"%");
							}
						}else{
							map.put("c"+i, b3+"%");
						}
						if(producRateInfo.getDrawMonth() == null
								|| producRateInfo.getDrawMonth().trim().length() == 0){
							continue;
						}else{
							float rate = Float.parseFloat((String)map02864.get("float"));
							StringBuffer l51Str = producRateInfo.getL51Str(rate);
							if (l51Str.toString().trim().length() != 0) {
								if (str.toString().length() == 0) {
									str.append("提前支取选择期：").append(l51Str);
								} else {
									str.append("；").append(l51Str);
								}
							}
						}
						
					}
					transBean.setPrintL52Str(str.toString());
				}
			}
			
		} catch (Exception e) {
			logger.error("调用利率查询接口（02864）异常"+e);
			return false;
		}
		c1=map.get("c1");
		c2=map.get("c2");
		c3=map.get("c3");
		c4=map.get("c4");
		c5=map.get("c5");
		c6=map.get("c6");
		c7=map.get("c7");
		c8=map.get("c8");
		logger.info("利率："+map.toString());
		return true;
	}
	
	/**
	 * 积享存协议模板
	 */
	public static boolean jxPrint(BillPrintSubAccInfoBean transBean){
		//获取利率
		if(!getRateInfo(transBean)){
			return false;
		}
		String rateInfo = "";
		if(transBean.getProductName().contains("A")){
			rateInfo = "提前支取选择期：0-3个月："+c1+";3(含)-6个月:"+c2+";6(含)-12个月:"+c3+";12(含)-24个月:"+c4
						+";24(含)-36个月:"+c5+";36(含)-48个月:"+c6+";48(含)个月-5年:"+c7+";5(含)年:"+c8;
		}else if(transBean.getProductName().contains("B")){
			rateInfo = "提前支取选择期：0-3个月："+c1+";3(含)-6个月:"+c2+";6(含)-12个月:"+c3+";12(含)-24个月:"+c4
					+";24(含)-36个月:"+c5+";36(含)-50个月:"+c6+";50(含)个月:"+c7;
		}else if(transBean.getProductName().contains("C")){
			rateInfo = "提前支取选择期：0-3个月："+c1+";3(含)-6个月:"+c2+";6(含)-12个月:"+c3+";12(含)-24个月:"+c4
					+";24(含)-40个月:"+c5+";40(含)个月:"+c6;
		}
		//如果包含，则执行替换字符串
		//日期流水号
		if(find("A1")){
			Dispatch.put(selection, "Text", "第"+OpenDate+transBean.getCreateSvrNo()+"号");
	        Dispatch.call(selection, "MoveRight"); 
		}
		//客户姓名
		if(find("B1")){
			Dispatch.put(selection, "Text", billBean.getCardName());
	        Dispatch.call(selection, "MoveRight"); 
		}
		//证件类型
		if(find("B2")){
			Dispatch.put(selection, "Text", "身份证");
	        Dispatch.call(selection, "MoveRight"); 
		}
		//证件号码
		if(find("B3")){
			Dispatch.put(selection, "Text", billBean.getReadIdcard());
			Dispatch.call(selection, "MoveRight"); 
		}
		//起息日期
		if(find("B4")){
			Dispatch.put(selection, "Text",transBean.getOpenDate());
	        Dispatch.call(selection, "MoveRight"); 
		}
		//开户渠道
		if(find("B5")){
			Dispatch.put(selection, "Text", channelMap.get(transBean.getChannel()));
	        Dispatch.call(selection, "MoveRight"); 
		}
		//产品名称
		if(find("B6")){
			Dispatch.put(selection, "Text",transBean.getProductName());
	        Dispatch.call(selection, "MoveRight"); 
		}
		//到期日
		if(find("B7")){
			Dispatch.put(selection, "Text", transBean.getEndIntDate());
	        Dispatch.call(selection, "MoveRight"); 
		}
		//计算存期天数
//		String depTerm=null;
//		try {
//			if(transBean.getOpenDate().contains("/") && transBean.getOpenDate().length()==10){
//				transBean.setOpenDate(transBean.getOpenDate().replace("/", ""));
//			}else if((Pattern.compile("^[0-9]*$").matcher(transBean.getOpenDate())).matches()&&transBean.getOpenDate().length()==8){
//				
//			}
//			if(transBean.getEndIntDate().contains("/")&&transBean.getEndIntDate().length()==10){
//				transBean.setEndIntDate(transBean.getEndIntDate().replace("/", ""));
//			}else if((Pattern.compile("^[0-9]*$").matcher(transBean.getEndIntDate())).matches()&&transBean.getEndIntDate().length()==8){
//				
//			}
//			SimpleDateFormat sdf1=new SimpleDateFormat("yyyyMMdd");
//			Date date1 = sdf1.parse(transBean.getOpenDate());//开户日
//			Date date2 = sdf1.parse(transBean.getEndIntDate());//到期日
//			long time1 = date1.getTime();
//			long time2 = date2.getTime();
//			long betTime = time2-time1;
//			long days=betTime/1000/60/60/24;
//			depTerm=String.valueOf(days);
//		} catch (Exception e) {
//			logger.error("计算存期失败："+e);
//			return false;
//		}
		
		//存期
		if(find("B8")){          
			Dispatch.put(selection, "Text",transBean.getDepTerm());
			Dispatch.call(selection, "MoveRight");
		}
		//对方账号
		if(find("B9")){
			Dispatch.put(selection, "Text",transBean.getKoukuanNo());
	        Dispatch.call(selection, "MoveRight"); 
		}
		//账号
		if(find("B10")){
			Dispatch.put(selection, "Text", transBean.getAccNo().trim()+"-".trim()+transBean.getSubAccNo().trim());
	        Dispatch.call(selection, "MoveRight"); 
		}
		//利率
		if(find("B11")){
			Dispatch.put(selection, "Text", transBean.getOpenRate()+"%");
	        Dispatch.call(selection, "MoveRight"); 
		}
		if(find("C1")){
			Dispatch.put(selection, "Text", rateInfo); 
	        Dispatch.call(selection, "MoveRight"); 
		}
		//积享存每月存入的金额
		if(find("D1")){
			Dispatch.put(selection, "Text", transBean.getOpenATM());
	        Dispatch.call(selection, "MoveRight"); 
		}
		//关联的如意存账号
		if(find("D2")){
			Dispatch.put(selection, "Text", transBean.getCognateNo()); 
	        Dispatch.call(selection, "MoveRight"); 
		}
		//是否有代理人标志
		if(find("E1")){
			String idCard_check_result = billBean.getImportMap().get("agent_persion");
			if("yes".equals(idCard_check_result)){
				Dispatch.put(selection, "Text", "(代)：");
			}else{
				Dispatch.put(selection, "Text", "：");
			}
	        Dispatch.call(selection, "MoveRight"); 
		}
		//柜员号
		if(find("F2")){
			Dispatch.put(selection, "Text","打印柜员号："+GlobalParameter.tellerNo);
	        Dispatch.call(selection, "MoveRight"); 
		}
		//日期
		String Date=DateUtil.getNowDate("yyyy年MM月dd日 HH:mm:ss");
		if(find("F1")){
			Dispatch.put(selection, "Text","打印时间："+Date);
			Dispatch.call(selection, "MoveRight"); 
		}
	
		return true;
	}
	
	/**
	 * 如意存协议模板
	 */
	public static boolean ryPrint(BillPrintSubAccInfoBean transBean){
		//获取利率
		if(!getRateInfo(transBean)){
			return false;
		}
		String rateInfo = "";
		if(transBean.getProductName().contains("A")){
			rateInfo = "提前支取选择期：0-3个月："+c1+";3(含)-6个月:"+c2+";6(含)-12个月:"+c3+";12(含)-24个月:"+c4
						+";24(含)-36个月:"+c5+";36(含)-48个月:"+c6+";48(含)个月-5年:"+c7+";5(含)年:"+c8;
		}else if(transBean.getProductName().contains("B")){
			rateInfo = "提前支取选择期：0-3个月："+c1+";3(含)-6个月:"+c2+";6(含)-12个月:"+c3+";12(含)-24个月:"+c4
					+";24(含)-36个月:"+c5+";36(含)-50个月:"+c6+";50(含)个月:"+c7;
		}else if(transBean.getProductName().contains("C")){
			rateInfo = "提前支取选择期：0-3个月："+c1+";3(含)-6个月:"+c2+";6(含)-12个月:"+c3+";12(含)-24个月:"+c4
					+";24(含)-40个月:"+c5+";40(含)个月:"+c6;
		}
		//如果包含，则执行替换字符串
		//日期流水号
		if(find("A1")){
			Dispatch.put(selection, "Text", "第"+OpenDate+transBean.getCreateSvrNo()+"号");
	        Dispatch.call(selection, "MoveRight"); 
		}
		//客户姓名
		if(find("B1")){
			Dispatch.put(selection, "Text", billBean.getCardName());
	        Dispatch.call(selection, "MoveRight"); 
		}
		//证件类型
		if(find("B2")){
			Dispatch.put(selection, "Text", "身份证");
	        Dispatch.call(selection, "MoveRight"); 
		}
		//证件号码
		if(find("B3")){
			Dispatch.put(selection, "Text", billBean.getReadIdcard());
			Dispatch.call(selection, "MoveRight"); 
		}
		//起息日期
		if(find("B4")){
			Dispatch.put(selection, "Text",transBean.getOpenDate());
	        Dispatch.call(selection, "MoveRight"); 
		}
		//开户渠道
		if(find("B5")){
			Dispatch.put(selection, "Text", channelMap.get(transBean.getChannel()));
	        Dispatch.call(selection, "MoveRight"); 
		}
		//产品名称
		if(find("B6")){
			Dispatch.put(selection, "Text",transBean.getProductName());
	        Dispatch.call(selection, "MoveRight"); 
		}
		//到期日
		if(find("B7")){
			Dispatch.put(selection, "Text", transBean.getEndIntDate());
	        Dispatch.call(selection, "MoveRight"); 
		}
		//存期
		if(find("B8")){          
			Dispatch.put(selection, "Text",transBean.getDepTerm());
			Dispatch.call(selection, "MoveRight");
		}
		//金额
		if(find("B9")){
			Dispatch.put(selection, "Text",transBean.getOpenATM());
	        Dispatch.call(selection, "MoveRight"); 
		}
		//账号
		if(find("B10")){
			Dispatch.put(selection, "Text", transBean.getAccNo().trim()+"-".trim()+transBean.getSubAccNo().trim());
	        Dispatch.call(selection, "MoveRight"); 
		}
		//利率
		if(find("B11")){
			Dispatch.put(selection, "Text", transBean.getOpenRate()+"%");
	        Dispatch.call(selection, "MoveRight"); 
		}
		if(find("C1")){
			Dispatch.put(selection, "Text", rateInfo); 
	        Dispatch.call(selection, "MoveRight"); 
		}
		//是否有代理人标志
		if(find("E1")){
			String idCard_check_result = billBean.getImportMap().get("agent_persion");
			if("yes".equals(idCard_check_result)){
				Dispatch.put(selection, "Text", "(代)：");
			}else{
				Dispatch.put(selection, "Text", "：");
			}
	        Dispatch.call(selection, "MoveRight"); 
		}
		//柜员号
		if(find("F2")){
			Dispatch.put(selection, "Text","打印柜员号："+GlobalParameter.tellerNo);
	        Dispatch.call(selection, "MoveRight"); 
		}
		//日期
		String Date=DateUtil.getNowDate("yyyy年MM月dd日 HH:mm:ss");
		if(find("F1")){
			Dispatch.put(selection, "Text","打印时间："+Date);
			Dispatch.call(selection, "MoveRight"); 
		}
		
		try {
			Dispatch picture = Dispatch.call(Dispatch.get(selection, "InLineShapes").toDispatch(),
	                "AddPicture",choicePath).toDispatch();
			Dispatch.call(picture, "Select"); // 选中图片
			Dispatch.put(picture, "Width", new Variant(5.3));
			Dispatch.put(picture, "Height",new Variant(5.3));
			Dispatch ShapeRange = Dispatch.call(picture, "ConvertToShape").toDispatch(); // 取得图片区域
			Dispatch WrapFormat = Dispatch.get(ShapeRange, "WrapFormat").toDispatch(); // 取得图片的格式对象
			Dispatch.put(WrapFormat, "Type", 5);
			Dispatch shapeRange = Dispatch.get(selection, "ShapeRange").toDispatch();  
			if(transBean.getProductName().contains("A")){
				Dispatch.put(shapeRange, "Left", new Variant(49));
		        Dispatch.put(shapeRange, "Top", new Variant(-452));
			}else if(transBean.getProductName().contains("B")){
				Dispatch.put(shapeRange, "Left", new Variant(49));
		        Dispatch.put(shapeRange, "Top", new Variant(-438));
			}else if(transBean.getProductName().contains("C")){
				Dispatch.put(shapeRange, "Left", new Variant(49));
		        Dispatch.put(shapeRange, "Top", new Variant(-406));
			}
		} catch (Exception e) {
			logger.error("打印选择符号失败"+e);
			return false;
		}
		
		return true;
	}
	
	/**
	 * 安享存协议模板
	 */
	public static boolean axPrint(BillPrintSubAccInfoBean transBean){
		//获取利率
		if(!getRateInfo(transBean)){
			return false;
		}
		String rateInfo = "";
		if(transBean.getProductName().contains("A")){
			rateInfo = "提前支取选择期：1年："+c1+";2年:"+c2+";3年:"+c3;
		}else if(transBean.getProductName().contains("B")){
			rateInfo = "提前支取选择期：1年："+c1+";2年:"+c2+";3年:"+c3+";4年:"+c4;
		}else if(transBean.getProductName().contains("C")){
			rateInfo = "提前支取选择期：1年："+c1+";2年:"+c2+";3年:"+c3+";4年:"+c4+";5年:"+c5;
		}
		
		//如果包含，则执行替换字符串
		//日期流水号
		if(find("A1")){
			Dispatch.put(selection, "Text", "第"+OpenDate+transBean.getCreateSvrNo()+"号");
	        Dispatch.call(selection, "MoveRight"); 
		}
		//客户姓名
		if(find("B1")){
			Dispatch.put(selection, "Text", billBean.getCardName());
	        Dispatch.call(selection, "MoveRight"); 
		}
		//证件类型
		if(find("B2")){
			Dispatch.put(selection, "Text", "身份证");
	        Dispatch.call(selection, "MoveRight"); 
		}
		//证件号码
		if(find("B3")){
			Dispatch.put(selection, "Text", billBean.getReadIdcard());
			Dispatch.call(selection, "MoveRight"); 
		}
		//起息日期
		if(find("B4")){
			Dispatch.put(selection, "Text",transBean.getOpenDate());
	        Dispatch.call(selection, "MoveRight"); 
		}
		//开户渠道
		if(find("B5")){
			Dispatch.put(selection, "Text", channelMap.get(transBean.getChannel()));
	        Dispatch.call(selection, "MoveRight"); 
		}
		//产品名称
		if(find("B6")){
			Dispatch.put(selection, "Text",transBean.getProductName());
	        Dispatch.call(selection, "MoveRight"); 
		}
		//到期日
		if(find("B7")){
			Dispatch.put(selection, "Text", transBean.getEndIntDate());
	        Dispatch.call(selection, "MoveRight"); 
		}
		//存期
		if(find("B8")){          
			Dispatch.put(selection, "Text",transBean.getDepTerm());
			Dispatch.call(selection, "MoveRight");
		}
		//金额
		if(find("B9")){
			Dispatch.put(selection, "Text",transBean.getOpenATM());
	        Dispatch.call(selection, "MoveRight"); 
		}
		//账号
		if(find("B10")){
			Dispatch.put(selection, "Text", transBean.getAccNo().trim()+"-".trim()+transBean.getSubAccNo().trim());
	        Dispatch.call(selection, "MoveRight"); 
		}
		//利率
		if(find("B11")){
			Dispatch.put(selection, "Text", "- -");
	        Dispatch.call(selection, "MoveRight"); 
		}
		//窗口期利率提示
		if(find("C1")){
			Dispatch.put(selection, "Text", rateInfo); 
	        Dispatch.call(selection, "MoveRight"); 
		}
		//账号
		if(find("D1")){
			Dispatch.put(selection, "Text", transBean.getGetNo());
	        Dispatch.call(selection, "MoveRight"); 
		}
		//是否有代理人标志
		if(find("E1")){
			String idCard_check_result = billBean.getImportMap().get("agent_persion");
			if("yes".equals(idCard_check_result)){
				Dispatch.put(selection, "Text", "(代)：");
			}else{
				Dispatch.put(selection, "Text", "：");
			}
	        Dispatch.call(selection, "MoveRight"); 
		}
		//柜员号
		if(find("F2")){
			Dispatch.put(selection, "Text","打印柜员号："+GlobalParameter.tellerNo);
	        Dispatch.call(selection, "MoveRight"); 
		}
		//日期
		String Date=DateUtil.getNowDate("yyyy年MM月dd日 HH:mm:ss");
		if(find("F1")){
			Dispatch.put(selection, "Text","打印时间："+Date);
			Dispatch.call(selection, "MoveRight"); 
		}
		
		try {
			Dispatch picture = Dispatch.call(Dispatch.get(selection, "InLineShapes").toDispatch(),
	                "AddPicture",choicePath).toDispatch();
			Dispatch.call(picture, "Select"); // 选中图片
			Dispatch.put(picture, "Width", new Variant(5.5));
			Dispatch.put(picture, "Height",new Variant(5.5));
			Dispatch ShapeRange = Dispatch.call(picture, "ConvertToShape").toDispatch(); // 取得图片区域
			Dispatch WrapFormat = Dispatch.get(ShapeRange, "WrapFormat").toDispatch(); // 取得图片的格式对象
			Dispatch.put(WrapFormat, "Type", 5);
			Dispatch shapeRange = Dispatch.get(selection, "ShapeRange").toDispatch();  
			Dispatch.put(shapeRange, "Left", new Variant(86.5));
	        Dispatch.put(shapeRange, "Top", new Variant(-348));
		} catch (Exception e) {
			logger.error("打印选择符号失败"+e);
			return false;
		}
		
		return true;
	}
	
	/**
	 * 立得存协议
	 */
	public static boolean ldPrint(String productedCode,BillPrintSubAccInfoBean transBean){
		//如果包含，则执行替换字符串
		//日期流水号
		if(find("A1")){
			Dispatch.put(selection, "Text", "第"+OpenDate+transBean.getCreateSvrNo()+"号");
	        Dispatch.call(selection, "MoveRight"); 
		}
		//客户姓名
		if(find("B1")){
			Dispatch.put(selection, "Text", billBean.getCardName());
	        Dispatch.call(selection, "MoveRight"); 
		}
		//证件类型
		if(find("B2")){
			Dispatch.put(selection, "Text", "身份证");
	        Dispatch.call(selection, "MoveRight"); 
		}
		//证件号码
		if(find("B3")){
			Dispatch.put(selection, "Text", billBean.getReadIdcard());
			Dispatch.call(selection, "MoveRight"); 
		}
		//起息日期
		if(find("B4")){
			Dispatch.put(selection, "Text",transBean.getOpenDate());
	        Dispatch.call(selection, "MoveRight"); 
		}
		//开户渠道
		if(find("B5")){
			Dispatch.put(selection, "Text", channelMap.get(transBean.getChannel()));
	        Dispatch.call(selection, "MoveRight"); 
		}
		//产品名称
		if(find("B6")){
			Dispatch.put(selection, "Text",transBean.getProductName());
	        Dispatch.call(selection, "MoveRight"); 
		}
		//到期日
		if(find("B7")){
			Dispatch.put(selection, "Text", transBean.getEndIntDate());
	        Dispatch.call(selection, "MoveRight"); 
		}
		//存期
		if(find("B8")){          
			Dispatch.put(selection, "Text",transBean.getDepTerm());
			Dispatch.call(selection, "MoveRight");
		}
		//金额
		if(find("B9")){
			Dispatch.put(selection, "Text",transBean.getOpenATM());
	        Dispatch.call(selection, "MoveRight"); 
		}
		//账号
		if(find("B10")){
			Dispatch.put(selection, "Text", transBean.getAccNo().trim()+"-".trim()+transBean.getSubAccNo().trim());
	        Dispatch.call(selection, "MoveRight"); 
		}
		//利率
		if(find("B11")){
			Dispatch.put(selection, "Text", transBean.getOpenRate()+"%");
	        Dispatch.call(selection, "MoveRight"); 
		}
		//收益人账号
		if("LZ".equals(productedCode)){
			if(find("D1")){
				Dispatch.put(selection, "Text", transBean.getGetNo());
				Dispatch.call(selection, "MoveRight"); 
			}
			if(find("D2")){
				Dispatch.put(selection, "Text",""); 
				Dispatch.call(selection, "MoveRight"); 
			}
		}else if("LT".equals(productedCode)){
			if(find("D1")){
				Dispatch.put(selection, "Text", "");
				Dispatch.call(selection, "MoveRight"); 
			}
			if(find("D2")){
				Dispatch.put(selection, "Text",transBean.getGetNo()); 
				Dispatch.call(selection, "MoveRight"); 
			}
		}
		//是否有代理人标志
		if(find("E1")){
			String idCard_check_result = billBean.getImportMap().get("agent_persion");
			if("yes".equals(idCard_check_result)){
				Dispatch.put(selection, "Text", "(代)：");
			}else{
				Dispatch.put(selection, "Text", "：");
			}
	        Dispatch.call(selection, "MoveRight"); 
		}
		//柜员号
		if(find("F2")){
			Dispatch.put(selection, "Text","打印柜员号："+GlobalParameter.tellerNo);
	        Dispatch.call(selection, "MoveRight"); 
		}
		//日期
		String Date=DateUtil.getNowDate("yyyy年MM月dd日 HH:mm:ss");
		if(find("F1")){
			Dispatch.put(selection, "Text","打印时间："+Date);
			Dispatch.call(selection, "MoveRight"); 
		}
		
		//选择自享/他享账号
		try {
			Dispatch picture = Dispatch.call(Dispatch.get(selection, "InLineShapes").toDispatch(),
	                "AddPicture",choicePath).toDispatch();
			Dispatch.call(picture, "Select"); // 选中图片
			Dispatch.put(picture, "Width", new Variant(5.3));
			Dispatch.put(picture, "Height",new Variant(5.3));
			Dispatch ShapeRange = Dispatch.call(picture, "ConvertToShape").toDispatch(); // 取得图片区域
			Dispatch WrapFormat = Dispatch.get(ShapeRange, "WrapFormat").toDispatch(); // 取得图片的格式对象
			Dispatch.put(WrapFormat, "Type", 5);
			Dispatch shapeRange = Dispatch.get(selection, "ShapeRange").toDispatch();  
			if("LZ".equals(productedCode)){
				Dispatch.put(shapeRange, "Left", new Variant(49));
				Dispatch.put(shapeRange, "Top", new Variant(-409));
			}else{
				Dispatch.put(shapeRange, "Left", new Variant(49));
				Dispatch.put(shapeRange, "Top", new Variant(-393));
			}
		} catch (Exception e) {
			logger.error("打印选择符号失败："+e);
			return false;
		}
		
		try {
			Dispatch picture = Dispatch.call(Dispatch.get(selection, "InLineShapes").toDispatch(),
	                "AddPicture",choicePath).toDispatch();
			Dispatch.call(picture, "Select"); // 选中图片
			Dispatch.put(picture, "Width", new Variant(5.3));
			Dispatch.put(picture, "Height",new Variant(5.3));
			Dispatch ShapeRange = Dispatch.call(picture, "ConvertToShape").toDispatch(); // 取得图片区域
			Dispatch WrapFormat = Dispatch.get(ShapeRange, "WrapFormat").toDispatch(); // 取得图片的格式对象
			Dispatch.put(WrapFormat, "Type", 5);
			Dispatch shapeRange = Dispatch.get(selection, "ShapeRange").toDispatch();  
			if(transBean.getProductName().contains("A")){
				Dispatch.put(shapeRange, "Left", new Variant(52));
		        Dispatch.put(shapeRange, "Top", new Variant(-364));
			}else if(transBean.getProductName().contains("B")){
				Dispatch.put(shapeRange, "Left", new Variant(52));
		        Dispatch.put(shapeRange, "Top", new Variant(-353));
			}else if(transBean.getProductName().contains("C")){
				Dispatch.put(shapeRange, "Left", new Variant(52));
		        Dispatch.put(shapeRange, "Top", new Variant(-342));
			}else if(transBean.getProductName().contains("D")){
				Dispatch.put(shapeRange, "Left", new Variant(52));
		        Dispatch.put(shapeRange, "Top", new Variant(-331));
			}else if(transBean.getProductName().contains("E")){
				Dispatch.put(shapeRange, "Left", new Variant(52));
		        Dispatch.put(shapeRange, "Top", new Variant(-320));
			}else if(transBean.getProductName().contains("F")){
				Dispatch.put(shapeRange, "Left", new Variant(52));
		        Dispatch.put(shapeRange, "Top", new Variant(-309));
			}
		} catch (Exception e) {
			logger.error("打印选择符号失败："+e);
			return false;
		}
		return true;
	}
	
	/**
	 * 如意存+协议模板
	 */
	public static boolean ryjPrint(BillPrintSubAccInfoBean transBean){
		//获取利率
		if(!getRateInfo(transBean)){
			return false;
		}
		String rateInfo = "";
		if(transBean.getProductName().contains("A")){
			rateInfo = "提前支取选择期：0-3个月："+c1+";3(含)-6个月:"+c2+";6(含)-12个月:"+c3+";12(含)-24个月:"+c4
						+";24(含)-36个月:"+c5+";36(含)-48个月:"+c6+";48(含)个月-5年:"+c7+";5(含)年:"+c8;
		}else if(transBean.getProductName().contains("B")){
			rateInfo = "提前支取选择期：0-3个月："+c1+";3(含)-6个月:"+c2+";6(含)-12个月:"+c3+";12(含)-24个月:"+c4
					+";24(含)-36个月:"+c5+";36(含)-50个月:"+c6+";50(含)个月:"+c7;
		}else if(transBean.getProductName().contains("C")){
			rateInfo = "提前支取选择期：0-3个月："+c1+";3(含)-6个月:"+c2+";6(含)-12个月:"+c3+";12(含)-24个月:"+c4
					+";24(含)-40个月:"+c5+";40(含)个月:"+c6;
		}
		//如果包含，则执行替换字符串
		//日期流水号
		if(find("A1")){
			Dispatch.put(selection, "Text", "第"+OpenDate+transBean.getCreateSvrNo()+"号");
	        Dispatch.call(selection, "MoveRight"); 
		}
		//客户姓名
		if(find("B1")){
			Dispatch.put(selection, "Text", billBean.getCardName());
	        Dispatch.call(selection, "MoveRight"); 
		}
		//证件类型
		if(find("B2")){
			Dispatch.put(selection, "Text", "身份证");
	        Dispatch.call(selection, "MoveRight"); 
		}
		//证件号码
		if(find("B3")){
			Dispatch.put(selection, "Text", billBean.getReadIdcard());
			Dispatch.call(selection, "MoveRight"); 
		}
		//起息日期
		if(find("B4")){
			Dispatch.put(selection, "Text",transBean.getOpenDate());
	        Dispatch.call(selection, "MoveRight"); 
		}
		//开户渠道
		if(find("B5")){
			Dispatch.put(selection, "Text", channelMap.get(transBean.getChannel()));
	        Dispatch.call(selection, "MoveRight"); 
		}
		//产品名称
		if(find("B6")){
			Dispatch.put(selection, "Text",transBean.getProductName());
	        Dispatch.call(selection, "MoveRight"); 
		}
		//到期日
		if(find("B7")){
			Dispatch.put(selection, "Text", transBean.getEndIntDate());
	        Dispatch.call(selection, "MoveRight"); 
		}
//		String depTerm=null;
//		try {
//			if(transBean.getOpenDate().contains("/") && transBean.getOpenDate().length()==10){
//				transBean.setOpenDate(transBean.getOpenDate().replace("/", ""));
//			}else if((Pattern.compile("^[0-9]*$").matcher(transBean.getOpenDate())).matches()&&transBean.getOpenDate().length()==8){
//				
//			}
//			if(transBean.getEndIntDate().contains("/")&&transBean.getEndIntDate().length()==10){
//				transBean.setEndIntDate(transBean.getEndIntDate().replace("/", ""));
//			}else if((Pattern.compile("^[0-9]*$").matcher(transBean.getEndIntDate())).matches()&&transBean.getEndIntDate().length()==8){
//				
//			}
//			SimpleDateFormat sdf1=new SimpleDateFormat("yyyyMMdd");
//			Date date1 = sdf1.parse(transBean.getOpenDate());//开户日
//			Date date2 = sdf1.parse(transBean.getEndIntDate());//到期日
//			long time1 = date1.getTime();
//			long time2 = date2.getTime();
//			long betTime = time2-time1;
//			long days=betTime/1000/60/60/24;
//			depTerm=String.valueOf(days);
//		} catch (Exception e) {
//			logger.error("计算存期失败："+e);
//			return false;
//		}
		//存期
		if(find("B8")){          
			Dispatch.put(selection, "Text",transBean.getDepTerm());
			Dispatch.call(selection, "MoveRight");
		}
		//金额
		if(find("B9")){
			Dispatch.put(selection, "Text",transBean.getOpenATM());
	        Dispatch.call(selection, "MoveRight"); 
		}
		//账号
		if(find("B10")){
			Dispatch.put(selection, "Text", transBean.getAccNo().trim()+"-".trim()+transBean.getSubAccNo().trim());
	        Dispatch.call(selection, "MoveRight"); 
		}
		//利率
		if(find("B11")){
			Dispatch.put(selection, "Text", transBean.getOpenRate()+"%");
	        Dispatch.call(selection, "MoveRight"); 
		}
		//关联卡号
		if(find("B12")){
			String realAccNo = transBean.getCognateNo().trim();
			Dispatch.put(selection, "Text",realAccNo);
	        Dispatch.call(selection, "MoveRight"); 
		}
		if(find("C1")){
			Dispatch.put(selection, "Text", rateInfo); 
	        Dispatch.call(selection, "MoveRight"); 
		}
		//积享存每月存入的金额
		if(find("D1")){
			Dispatch.put(selection, "Text", transBean.getOpenATM());
	        Dispatch.call(selection, "MoveRight"); 
		}
		//关联的如意存账号
		if(find("D2")){
			Dispatch.put(selection, "Text", transBean.getCognateNo()); 
	        Dispatch.call(selection, "MoveRight"); 
		}
		//是否有代理人标志
		if(find("E1")){
			String idCard_check_result = billBean.getImportMap().get("agent_persion");
			if("yes".equals(idCard_check_result)){
				Dispatch.put(selection, "Text", "(代)：");
			}else{
				Dispatch.put(selection, "Text", "：");
			}
	        Dispatch.call(selection, "MoveRight"); 
		}
		//柜员号
		if(find("F2")){
			Dispatch.put(selection, "Text","打印柜员号："+GlobalParameter.tellerNo);
	        Dispatch.call(selection, "MoveRight"); 
		}
		//日期
		String Date=DateUtil.getNowDate("yyyy年MM月dd日 HH:mm:ss");
		if(find("F1")){
			Dispatch.put(selection, "Text","打印时间："+Date);
			Dispatch.call(selection, "MoveRight"); 
		}
		return true;
	}
	
	/**
	 * 约享存协议模板
	 */
	public static boolean yxPrint(BillPrintSubAccInfoBean transBean){
		//获取利率
		if(!getRateInfo(transBean)){
			return false;
		}
		//如果包含，则执行替换字符串
		//日期流水号
		if(find("A1")){
			Dispatch.put(selection, "Text", "第"+OpenDate+transBean.getCreateSvrNo()+"号");
	        Dispatch.call(selection, "MoveRight"); 
		}
		//客户姓名
		if(find("B1")){
			Dispatch.put(selection, "Text", billBean.getCardName());
	        Dispatch.call(selection, "MoveRight"); 
		}
		//证件类型
		if(find("B2")){
			Dispatch.put(selection, "Text", "身份证");
	        Dispatch.call(selection, "MoveRight"); 
		}
		//证件号码
		if(find("B3")){
			Dispatch.put(selection, "Text", billBean.getReadIdcard());
			Dispatch.call(selection, "MoveRight"); 
		}
		//起息日期
		if(find("B4")){
			Dispatch.put(selection, "Text",transBean.getOpenDate());
	        Dispatch.call(selection, "MoveRight"); 
		}
		//开户渠道
		if(find("B5")){
			Dispatch.put(selection, "Text", channelMap.get(transBean.getChannel()));
	        Dispatch.call(selection, "MoveRight"); 
		}
		//产品名称
		if(find("B6")){
			Dispatch.put(selection, "Text",transBean.getProductName());
	        Dispatch.call(selection, "MoveRight"); 
		}
		//到期日
		if(find("B7")){
			Dispatch.put(selection, "Text", transBean.getEndIntDate());
	        Dispatch.call(selection, "MoveRight"); 
		}
		//存期
		if(find("B8")){          
			Dispatch.put(selection, "Text",transBean.getDepTerm());
			Dispatch.call(selection, "MoveRight");
		}
		//金额
		if(find("B9")){
			Dispatch.put(selection, "Text",transBean.getOpenATM());
	        Dispatch.call(selection, "MoveRight"); 
		}
		//账号
		if(find("B10")){
			Dispatch.put(selection, "Text", transBean.getAccNo().trim()+"-".trim()+transBean.getSubAccNo().trim());
	        Dispatch.call(selection, "MoveRight"); 
		}
		//利率
		if(find("B11")){
			Dispatch.put(selection, "Text", transBean.getOpenRate()+"%");
	        Dispatch.call(selection, "MoveRight"); 
		}
		if(find("C1")){
			Dispatch.put(selection, "Text", transBean.getPrintL52Str()); 
	        Dispatch.call(selection, "MoveRight"); 
		}
		//是否有代理人标志
		if(find("E1")){
			String idCard_check_result = billBean.getImportMap().get("agent_persion");
			if("yes".equals(idCard_check_result)){
				Dispatch.put(selection, "Text", "(代)：");
			}else{
				Dispatch.put(selection, "Text", "：");
			}
	        Dispatch.call(selection, "MoveRight"); 
		}
		//柜员号
		if(find("F2")){
			Dispatch.put(selection, "Text","打印柜员号："+GlobalParameter.tellerNo);
			Dispatch.call(selection, "MoveRight"); 
		}
		//日期
		String Date=DateUtil.getNowDate("yyyy年MM月dd日 HH:mm:ss");
		if(find("F1")){
			Dispatch.put(selection, "Text","打印时间："+Date);
	        Dispatch.call(selection, "MoveRight"); 
		}
		
		try {
			Dispatch picture = Dispatch.call(Dispatch.get(selection, "InLineShapes").toDispatch(),
	                "AddPicture",choicePath).toDispatch();
			Dispatch.call(picture, "Select"); // 选中图片
			Dispatch.put(picture, "Width", new Variant(5.3));
			Dispatch.put(picture, "Height",new Variant(5.3));
			Dispatch ShapeRange = Dispatch.call(picture, "ConvertToShape").toDispatch(); // 取得图片区域
			Dispatch WrapFormat = Dispatch.get(ShapeRange, "WrapFormat").toDispatch(); // 取得图片的格式对象
			Dispatch.put(WrapFormat, "Type", 5);
			Dispatch shapeRange = Dispatch.get(selection, "ShapeRange").toDispatch();  
			String productedName = transBean.getProductName().trim();
			if(productedName.contains("A")){
				if(productedName.contains("a")){
					Dispatch.put(shapeRange, "Left", new Variant(45));
			        Dispatch.put(shapeRange, "Top", new Variant(-411));
				}else if(productedName.contains("b")){
					Dispatch.put(shapeRange, "Left", new Variant(45));
			        Dispatch.put(shapeRange, "Top", new Variant(-379));
				}else if(productedName.contains("c")){
					Dispatch.put(shapeRange, "Left", new Variant(45));
			        Dispatch.put(shapeRange, "Top", new Variant(-347));
				}
			}else if(productedName.contains("B")){
				if(productedName.contains("a")){
					Dispatch.put(shapeRange, "Left", new Variant(47));
			        Dispatch.put(shapeRange, "Top", new Variant(-411));
				}else if(productedName.contains("b")){
					Dispatch.put(shapeRange, "Left", new Variant(47));
			        Dispatch.put(shapeRange, "Top", new Variant(-378));
				}else if(productedName.contains("c")){
					Dispatch.put(shapeRange, "Left", new Variant(47));
			        Dispatch.put(shapeRange, "Top", new Variant(-345));
				}
			}else if(productedName.contains("C")){
				if(productedName.contains("a")){
					Dispatch.put(shapeRange, "Left", new Variant(43));
			        Dispatch.put(shapeRange, "Top", new Variant(-411));
				}else if(productedName.contains("b")){
					Dispatch.put(shapeRange, "Left", new Variant(43));
			        Dispatch.put(shapeRange, "Top", new Variant(-379));
				}else if(productedName.contains("c")){
					Dispatch.put(shapeRange, "Left", new Variant(43));
			        Dispatch.put(shapeRange, "Top", new Variant(-347));
				}
			}
			
		} catch (Exception e) {
			logger.error("打印选择符号失败："+e);
			return false;
		}
		
		return true;
	}
	
	/**
	 * 幸福1+1和千禧产品
	 */
	public static boolean qxPrint(BillPrintSubAccInfoBean transBean){
		//如果包含，则执行替换字符串
		//日期流水号
		if(find("A1")){
			Dispatch.put(selection, "Text", "第"+OpenDate+transBean.getCreateSvrNo()+"号");
	        Dispatch.call(selection, "MoveRight"); 
		}
		//客户姓名
		if(find("B1")){
			Dispatch.put(selection, "Text", billBean.getCardName());
	        Dispatch.call(selection, "MoveRight"); 
		}
		//证件类型
		if(find("B2")){
			Dispatch.put(selection, "Text", "身份证");
	        Dispatch.call(selection, "MoveRight"); 
		}
		//证件号码
		if(find("B3")){
			Dispatch.put(selection, "Text", billBean.getReadIdcard());
			Dispatch.call(selection, "MoveRight"); 
		}
		//起息日期
		if(find("B4")){
			Dispatch.put(selection, "Text",transBean.getOpenDate());
	        Dispatch.call(selection, "MoveRight"); 
		}
		//开户渠道
		if(find("B5")){
			Dispatch.put(selection, "Text", channelMap.get(transBean.getChannel()));
	        Dispatch.call(selection, "MoveRight"); 
		}
		//产品名称
		if(find("B6")){
			Dispatch.put(selection, "Text",transBean.getProductName());
	        Dispatch.call(selection, "MoveRight"); 
		}
		//到期日
		if(find("B7")){
			Dispatch.put(selection, "Text", transBean.getEndIntDate());
	        Dispatch.call(selection, "MoveRight"); 
		}
		//存期
		if(find("B8")){          
			Dispatch.put(selection, "Text",transBean.getDepTerm());
			Dispatch.call(selection, "MoveRight");
		}
		//金额
		if(find("B9")){
			Dispatch.put(selection, "Text",transBean.getOpenATM());
	        Dispatch.call(selection, "MoveRight"); 
		}
		//账号
		if(find("B10")){
			Dispatch.put(selection, "Text", transBean.getAccNo().trim()+"-".trim()+transBean.getSubAccNo().trim());
	        Dispatch.call(selection, "MoveRight"); 
		}
		//利率
		if(find("B11")){
			Dispatch.put(selection, "Text", transBean.getOpenRate()+"%");
	        Dispatch.call(selection, "MoveRight"); 
		}
		//是否有代理人标志
		if(find("E1")){
			String idCard_check_result = billBean.getImportMap().get("agent_persion");
			if("yes".equals(idCard_check_result)){
				Dispatch.put(selection, "Text", "(代)：");
			}else{
				Dispatch.put(selection, "Text", "：");
			}
	        Dispatch.call(selection, "MoveRight"); 
		}
		//柜员号
		if(find("F2")){
			Dispatch.put(selection, "Text","打印柜员号："+GlobalParameter.tellerNo);
	        Dispatch.call(selection, "MoveRight"); 
		}
		//日期
		String Date=DateUtil.getNowDate("yyyy年MM月dd日 HH:mm:ss");
		if(find("F1")){
			Dispatch.put(selection, "Text","打印时间："+Date);
			Dispatch.call(selection, "MoveRight"); 
		}
		return true;
	}
	
	
	/**
	 * 活力存产品
	 */
	public static boolean hlcPrint(BillPrintSubAccInfoBean transBean){
		//如果包含，则执行替换字符串
		//日期流水号
		if(find("A1")){
			Dispatch.put(selection, "Text", "第"+OpenDate+transBean.getCreateSvrNo()+"号");
	        Dispatch.call(selection, "MoveRight"); 
		}
		//客户姓名
		if(find("B1")){
			Dispatch.put(selection, "Text", billBean.getCardName());
	        Dispatch.call(selection, "MoveRight"); 
		}
		//证件类型
		if(find("B2")){
			Dispatch.put(selection, "Text", "身份证");
	        Dispatch.call(selection, "MoveRight"); 
		}
		//证件号码
		if(find("B3")){
			Dispatch.put(selection, "Text", billBean.getReadIdcard());
			Dispatch.call(selection, "MoveRight"); 
		}
		//起息日期
		if(find("B4")){
			Dispatch.put(selection, "Text",transBean.getOpenDate());
	        Dispatch.call(selection, "MoveRight"); 
		}
		//开户渠道
		if(find("B5")){
			Dispatch.put(selection, "Text", channelMap.get(transBean.getChannel()));
	        Dispatch.call(selection, "MoveRight"); 
		}
		//产品名称
		if(find("B6")){
			Dispatch.put(selection, "Text",transBean.getProductName());
	        Dispatch.call(selection, "MoveRight"); 
		}
		//到期日
		if(find("B7")){
			Dispatch.put(selection, "Text", transBean.getEndIntDate());
	        Dispatch.call(selection, "MoveRight"); 
		}
		//存期
		if(find("B8")){          
			Dispatch.put(selection, "Text",transBean.getDepTerm());
			Dispatch.call(selection, "MoveRight");
		}
		//金额
		if(find("B9")){
			Dispatch.put(selection, "Text",transBean.getOpenATM());
	        Dispatch.call(selection, "MoveRight"); 
		}
		//账号
		if(find("B10")){
			Dispatch.put(selection, "Text", transBean.getAccNo().trim()+"-".trim()+transBean.getSubAccNo().trim());
	        Dispatch.call(selection, "MoveRight"); 
		}
		//利率
		if(find("B11")){
			Dispatch.put(selection, "Text", transBean.getOpenRate()+"%");
	        Dispatch.call(selection, "MoveRight"); 
		}
		if(find("C1")){
			Dispatch.put(selection, "Text", ""); 
	        Dispatch.call(selection, "MoveRight"); 
		}
		//是否有代理人标志
		if(find("E1")){
			String idCard_check_result = billBean.getImportMap().get("agent_persion");
			if("yes".equals(idCard_check_result)){
				Dispatch.put(selection, "Text", "(代)：");
			}else{
				Dispatch.put(selection, "Text", "：");
			}
	        Dispatch.call(selection, "MoveRight"); 
		}
		//柜员号
		if(find("F2")){
			Dispatch.put(selection, "Text","打印柜员号："+GlobalParameter.tellerNo);
	        Dispatch.call(selection, "MoveRight"); 
		}
		//日期
		String Date=DateUtil.getNowDate("yyyy年MM月dd日 HH:mm:ss");
		if(find("F1")){
			Dispatch.put(selection, "Text","打印时间："+Date);
			Dispatch.call(selection, "MoveRight"); 
		}
		return true;
	}
	
	/**
	 * 聚享存和聚享存活动专属产品
	 */
	public static boolean juxcPrint(BillPrintSubAccInfoBean transBean){
		//获取利率
		if(!getRateInfo(transBean)){
			return false;
		}
		//如果包含，则执行替换字符串
		//日期流水号
		if(find("A1")){
			Dispatch.put(selection, "Text", "第"+OpenDate+transBean.getCreateSvrNo()+"号");
	        Dispatch.call(selection, "MoveRight"); 
		}
		//客户姓名
		if(find("B1")){
			Dispatch.put(selection, "Text", billBean.getCardName());
	        Dispatch.call(selection, "MoveRight"); 
		}
		//证件类型
		if(find("B2")){
			Dispatch.put(selection, "Text", "身份证");
	        Dispatch.call(selection, "MoveRight"); 
		}
		//证件号码
		if(find("B3")){
			Dispatch.put(selection, "Text", billBean.getReadIdcard());
			Dispatch.call(selection, "MoveRight"); 
		}
		//起息日期
		if(find("B4")){
			Dispatch.put(selection, "Text",transBean.getOpenDate());
	        Dispatch.call(selection, "MoveRight"); 
		}
		//开户渠道
		if(find("B5")){
			Dispatch.put(selection, "Text", channelMap.get(transBean.getChannel()));
	        Dispatch.call(selection, "MoveRight"); 
		}
		//产品名称
		if(find("B6")){
			Dispatch.put(selection, "Text",transBean.getProductName());
	        Dispatch.call(selection, "MoveRight"); 
		}
		//到期日
		if(find("B7")){
			Dispatch.put(selection, "Text", transBean.getEndIntDate());
	        Dispatch.call(selection, "MoveRight"); 
		}
		//存期
		if(find("B8")){          
			Dispatch.put(selection, "Text",transBean.getDepTerm());
			Dispatch.call(selection, "MoveRight");
		}
		//金额
		if(find("B9")){
			Dispatch.put(selection, "Text",transBean.getOpenATM());
	        Dispatch.call(selection, "MoveRight"); 
		}
		//账号
		if(find("B10")){
			Dispatch.put(selection, "Text", transBean.getAccNo().trim()+"-".trim()+transBean.getSubAccNo().trim());
	        Dispatch.call(selection, "MoveRight"); 
		}
		//利率
		if(find("B11")){
			Dispatch.put(selection, "Text", transBean.getOpenRate()+"%");
	        Dispatch.call(selection, "MoveRight"); 
		}
		if(find("C1")){
			Dispatch.put(selection, "Text", transBean.getPrintL52Str()); 
	        Dispatch.call(selection, "MoveRight"); 
		}
		//是否有代理人标志
		if(find("E1")){
			String idCard_check_result = billBean.getImportMap().get("agent_persion");
			if("yes".equals(idCard_check_result)){
				Dispatch.put(selection, "Text", "(代)：");
			}else{
				Dispatch.put(selection, "Text", "：");
			}
	        Dispatch.call(selection, "MoveRight"); 
		}
		//柜员号
		if(find("F2")){
			Dispatch.put(selection, "Text","打印柜员号："+GlobalParameter.tellerNo);
	        Dispatch.call(selection, "MoveRight"); 
		}
		//日期
		String Date=DateUtil.getNowDate("yyyy年MM月dd日 HH:mm:ss");
		if(find("F1")){
			Dispatch.put(selection, "Text","打印时间："+Date);
			Dispatch.call(selection, "MoveRight"); 
		}
		return true;
	}

	//计算积享存和如意存到期日期
	public static boolean getDetermDate(){
		//计算存期天数
		String depTerm=null;
		try {
			if(transBean.getOpenDate().contains("/") && transBean.getOpenDate().length()==10){
				transBean.setOpenDate(transBean.getOpenDate().replace("/", ""));
			}else if((Pattern.compile("^[0-9]*$").matcher(transBean.getOpenDate())).matches()&&transBean.getOpenDate().length()==8){
				
			}
			if(transBean.getEndIntDate().contains("/")&&transBean.getEndIntDate().length()==10){
				transBean.setEndIntDate(transBean.getEndIntDate().replace("/", ""));
			}else if((Pattern.compile("^[0-9]*$").matcher(transBean.getEndIntDate())).matches()&&transBean.getEndIntDate().length()==8){
				
			}
			SimpleDateFormat sdf1=new SimpleDateFormat("yyyyMMdd");
			Date date1 = sdf1.parse(transBean.getOpenDate());//开户日
			Date date2 = sdf1.parse(transBean.getEndIntDate());//到期日
			long time1 = date1.getTime();
			long time2 = date2.getTime();
			long betTime = time2-time1;
			long days=betTime/1000/60/60/24;
			depTerm=String.valueOf(days);
			transBean.setDepTerm(depTerm+"天");
			return true;
		} catch (Exception e) {
			logger.error("计算存期失败："+e);
			return false;
		}
	}
}
