package com.boomhope.Bill.Comm;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import sun.misc.BASE64Decoder;

import com.boomhope.Bill.Public.GlobalParameter;
import com.boomhope.Bill.TransService.BillPrint.Bean.BillPrintBean;
import com.boomhope.Bill.TransService.BillPrint.Interface.ICSubAccNo;
import com.boomhope.Bill.Util.HttpClientUtil;
import com.boomhope.Bill.Util.NumberZH;
import com.boomhope.Bill.Util.Property;
import com.boomhope.Bill.peripheral.action.BillPrint;
import com.boomhope.tms.message.in.bck.BCK0016ResBodyBean;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

/**
 * 
 * Title:子账户打印存单
 * Description:
 * @author mouchunyue
 * @date 2016年11月7日 下午2:48:08
 */
public class PrintBill {

	private static Logger logger = Logger.getLogger(PrintBill.class);
	private static Dispatch doc=null;
	private static Dispatch docs = null;
	private static Dispatch selection = null;
	private static ActiveXComponent word = null;
	
//	static{
//        word=new ActiveXComponent("Word.Application");
//	}
	/**
	 * 初始化Doc
	 */
	private void getDocInit(){
		logger.info("开始加载word模板");
		word=new ActiveXComponent("Word.Application");
		Dispatch.put(word, "Visible", new Variant(false));
		docs=word.getProperty("Documents").toDispatch();
		doc=Dispatch.call(docs, "Open", Property.dzyz_template).toDispatch();
		selection = Dispatch.get(word, "Selection").toDispatch();
	}
	
	public boolean PrintBills(BillPrintBean transBean,ICSubAccNo accNo,BCK0016ResBodyBean bck0016ResBodyBean,String type){
		boolean flag = false;
		try {
			//拼接调用电子印章所需要的参数
			JSONObject jsonObject = getJson(transBean,accNo,type,bck0016ResBodyBean);
			// 调用电子印章http接口（base64编码）
			String dzyz = getDZYZ(Property.dzyz_url, jsonObject);
			//将base64编码转换成图片
			if(dzyz!=null){
				//将base64编码转换成图片
				flag = GenerateImage(dzyz, Property.dzyz_ml);
				//获取电子印章
				if(!flag){
					return flag;
				}
			}else{
				logger.error("获取电子印章失败");
			}
			ComThread.InitSTA();
			getDocInit();
			//如果包含，则执行替换字符串
			/**
			 * L11	产品名称
			 * L21	存单单号
			 * L22	姓名
			 * L31	存款大写金额
			 * L32	存款小写金额
			 * T11	存入日
			 * T12	起息日
			 * T13	到期日
			 * T14	存期
			 * T15	利率
			 * T16	到期利息
			 * T17	支取方式
			 * T18	柜员号
			 * L51	是否自动转存
			 * L52	本行提示：非到期日支取的，请提供存款人有效身份证件
			 * L53	存单机开户
			 */
			if(find("L11")){
				Dispatch.put(selection, "Text", accNo.getProductName());
		        Dispatch.call(selection, "MoveRight"); 
			}
			if(find("L21")){
				Dispatch.put(selection, "Text", transBean.getAccNo()+"-"+accNo.getSubAccNo());
		        Dispatch.call(selection, "MoveRight"); 
			}
			
			if(find("L22")){
				Dispatch.put(selection, "Text", transBean.getCardName());
		        Dispatch.call(selection, "MoveRight"); 
			}
			if(find("L31")){
				Dispatch.put(selection, "Text", "人民币"+NumberZH.change(Double.parseDouble(accNo.getATM())));//大写金额
				Dispatch.call(selection, "MoveRight"); 
			}
			if(find("L32")){
				Dispatch.put(selection, "Text", "CNY"+new DecimalFormat("###,###,###,###,###.00").format(Double.parseDouble(accNo.getATM())));//小写金额
		        Dispatch.call(selection, "MoveRight"); 
			}
			if(find("T11")){
				
				Dispatch.put(selection, "Text", accNo.getOpenDate()); //存入日
		        Dispatch.call(selection, "MoveRight"); 
			}
			if(find("T12")){
				Dispatch.put(selection, "Text", accNo.getStartIntDate());//起息日
		        Dispatch.call(selection, "MoveRight"); 
			}
			if(find("T13")){
				Dispatch.put(selection, "Text", accNo.getEndIntDate());//到期日
		        Dispatch.call(selection, "MoveRight"); 
			}
			if(find("T14")){          
				Dispatch.put(selection, "Text",accNo.getSavingCountMinStr());//存期
				Dispatch.call(selection, "MoveRight");
			}
			if(find("T15")){
				if("0010".equals(bck0016ResBodyBean.getPRO_CODE().trim())){
					Dispatch.put(selection, "Text", accNo.getOpenRate().trim()+"%");// 利率
					Dispatch.call(selection, "MoveRight");
				}else if(bck0016ResBodyBean.getPRO_CODE().trim().startsWith("A")){
					Dispatch.put(selection, "Text", "--");// 利率
					Dispatch.call(selection, "MoveRight");
				}else{
					if("1".equals(GlobalParameter.printRateStatus)){
						Dispatch.put(selection, "Text", "--");// 利率
						Dispatch.call(selection, "MoveRight");
					}else{
						Dispatch.put(selection, "Text", accNo.getOpenRate().trim()+"%");// 利率
						Dispatch.call(selection, "MoveRight");
					}
				} 
			}
			if(find("T16")){
				if("0010".equals(bck0016ResBodyBean.getPRO_CODE().trim())){
					Dispatch.put(selection, "Text", bck0016ResBodyBean.getINTE());// 利息
					Dispatch.call(selection, "MoveRight");
				}else{
					if("1".equals(GlobalParameter.printRateStatus)){
						Dispatch.put(selection, "Text", "--");// 利息
						Dispatch.call(selection, "MoveRight");
					}else{
						Dispatch.put(selection, "Text", bck0016ResBodyBean.getINTE());// 利息
						Dispatch.call(selection, "MoveRight");
					}
				} 
			}
			if(find("T17")){
				if(bck0016ResBodyBean.getPAY_COND().trim().equals("0")){//支取方式
					Dispatch.put(selection, "Text", "无");  //无
			        Dispatch.call(selection, "MoveRight"); 
				}else if(bck0016ResBodyBean.getPAY_COND().trim().equals("1")){
					Dispatch.put(selection, "Text", "密码");  //凭密支取
			        Dispatch.call(selection, "MoveRight"); 
				}
			}
			if(find("T18")){
				Dispatch.put(selection, "Text", GlobalParameter.tellerNo); //柜员号
		        Dispatch.call(selection, "MoveRight");
			}
			
			if(find("L51")){
				if("0010".equals(bck0016ResBodyBean.getPRO_CODE().trim())){//整存整取
					Dispatch.put(selection, "Text", accNo.getExchFlag());
					Dispatch.call(selection, "MoveRight");
				}else if(bck0016ResBodyBean.getPRO_CODE().trim().startsWith("Y")){//约享存窗口期
					Dispatch.put(selection, "Text", accNo.getPrinterL51Str());
					Dispatch.call(selection, "MoveRight");
				}else if(bck0016ResBodyBean.getPRO_CODE().trim().startsWith("F")){//聚享存窗口期
					Dispatch.put(selection, "Text", accNo.getPrinterL51Str());
					Dispatch.call(selection, "MoveRight");
				}else if(bck0016ResBodyBean.getPRO_CODE().trim().startsWith("A")){//安享存窗口期
					Dispatch.put(selection, "Text", accNo.getPrinterL51Str());
					Dispatch.call(selection, "MoveRight");
				}else{
					Dispatch.put(selection, "Text", "");
					Dispatch.call(selection, "MoveRight");
				}
			}
			if(find("L53")){
				if(type.trim().equals("7")){
					Dispatch.put(selection, "Text", "银行卡账户-子账户"); 
					Dispatch.call(selection, "MoveRight"); 
				}
				if(type.trim().equals("11")){
					Dispatch.put(selection, "Text", "电子账户-子账户"); 
					Dispatch.call(selection, "MoveRight"); 
				}
			}
			
			//打印电子印章
			Dispatch picture = Dispatch.call(Dispatch.get(selection, "InLineShapes").toDispatch(),
	                "AddPicture", Property.dzyz_ml).toDispatch();
			Dispatch.call(picture, "Select"); // 选中图片
			Dispatch.put(picture, "Width", new Variant(104));
			Dispatch ShapeRange = Dispatch.call(picture, "ConvertToShape").toDispatch(); // 取得图片区域
			Dispatch WrapFormat = Dispatch.get(ShapeRange, "WrapFormat").toDispatch(); // 取得图片的格式对象
			Dispatch.put(WrapFormat, "Type", 5);
			Dispatch shapeRange = Dispatch.get(selection, "ShapeRange").toDispatch();  
	        Dispatch.put(shapeRange, "Left", new Variant(-30));
	        Dispatch.put(shapeRange, "Top", new Variant(-130));
	        Dispatch.call(doc, "PrintOut");//打印
		} catch (Exception e) {
			logger.error("打印存单程序异常"+e);
			return false;
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
				logger.error(e);
			}
			
		}
		return true;
	}
	//扫描Doc文件，判断文件中是否包含该字符串，如果包含则返回true
	private boolean find(String text){
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
	private String getDZYZ(String url,JSONObject paramMap){
		HttpClientUtil httpClientUtil = new HttpClientUtil();
		String result = httpClientUtil.httpPost(url, paramMap);
		return result;
	}
	/**
	 * 拼接调用电子印章接口所需要的参数
	 * @param transBean
	 * @param globalParameter
	 * @return
	 */
	private JSONObject getJson(BillPrintBean transBean,ICSubAccNo accNo,String type,BCK0016ResBodyBean bck0016ResBodyBean){
		logger.info("开始拼接调用电子印章接口所需要的参数");
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("channel", "0035");//渠道号
		jsonObject.put("fserialno", accNo.getFserialno());//日期+流水号
		jsonObject.put("instno", GlobalParameter.branchNo);//机构号
		jsonObject.put("tellerno", GlobalParameter.tellerNo);//柜员号
		jsonObject.put("chl_tran_code", "");//外围系统交易码
		jsonObject.put("chl_tran_name", "");//外围系统交易名称
		jsonObject.put("vouchername", "存单");//凭证名称
		jsonObject.put("sealtype", "0001");//电子印章种类（业务专用章）
		
		//拼接tradeinfo参数值
		Map<String,String> map = new HashMap<String, String>();
		map.put("产品代码", accNo.getProductCode());
		map.put("储种",accNo.getProductName());
		map.put("账号",transBean.getAccNo()+"-"+accNo.getSubAccNo());
		map.put("户名",transBean.getCardName());
		map.put("金额(大写)","人民币"+NumberZH.change(Double.parseDouble(accNo.getATM())));
		map.put("金额(小写)","CNY"+new DecimalFormat("###,###,###,###,###.00").format(Double.parseDouble(accNo.getATM())));
		map.put("存入日",accNo.getOpenDate());
		map.put("起息日",accNo.getStartIntDate());
		map.put("到期日",accNo.getEndIntDate());
		
		map.put("存期",accNo.getSavingCountMinStr());
		map.put("利率",accNo.getOpenRate()+"%");
		map.put("到期利息",bck0016ResBodyBean.getINTE());
		
		if(bck0016ResBodyBean.getPAY_COND().trim().equals("0")){//支取方式
			map.put("支取方式","无"); //无
		}else if(bck0016ResBodyBean.getPAY_COND().trim().equals("1")){
			map.put("支取方式","密码"); //凭密支取
		}
		
		map.put("是否提前支取","");
		String type2 = "";
		if(type.trim().equals("7")){
			type2 = "银行卡账户-子账户";
		}
		if(type.trim().equals("11")){
			type2 = "电子账户-子账户";
		}
		map.put("开户渠道",type2);
		jsonObject.put("tradeinfo", JSONObject.fromObject(map).toString());
		logger.info("拼接调用电子印章接口所需要的参数结束---结果为-->"+jsonObject.toString());
		return jsonObject;
	}
	/**
	 * base64编码转换图片
	 * @param imgStr 	//base64编码的字符串
	 * @param rootPath	//保存的路径（图片完整路径）
	 * @return
	 */
	private boolean GenerateImage(String imgStr,String rootPath) {
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
}
