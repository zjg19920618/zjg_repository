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
import com.boomhope.Bill.TransService.BillChOpen.Bean.PublicBillChangeOpenBean;
import com.boomhope.Bill.Util.HttpClientUtil;
import com.boomhope.Bill.Util.OnOffSetUp;
import com.boomhope.Bill.Util.Property;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

/**
 * 
 * Title:换开打印存单
 * Description:
 */
public class HKPrintBill {

	private static Logger logger = Logger.getLogger(HKPrintBill.class);
	private static Dispatch doc=null;
	private static Dispatch docs = null;
	private static Dispatch selection = null;
	private static ActiveXComponent word = null;
	
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
	
	
	/**
	 * 换开打印存单
	 * @param 
	 * @return
	 */
	public boolean PrintBill(PublicBillChangeOpenBean bean) {
		boolean flag = false;
		try {
			if(!getPrintRateStatus()){
				return false;
			}
			
			// 拼接调用电子印章所需要的参数
			JSONObject jsonObject = getJsons(bean);
			// 调用电子印章http接口（base64编码）
			String dzyz = getDZYZ(Property.dzyz_url, jsonObject);
			if (dzyz != null) {
				// 将base64编码转换成图片
				flag = GenerateImage(dzyz, Property.dzyz_ml);
				// 获取电子印章
				if (!flag) {
					return flag;					
				}else{
					bean.setYz("true");
				}
			} else {
				logger.error("获取电子印章失败");
				
			}

			ComThread.InitSTA();
			getDocInit();

			// 如果包含，则执行替换字符串
			/**
			 * L11 产品名称 L21 存单单号 L22 姓名 L31 存款大写金额 L32 存款小写金额 T11 存入日 T12 起息日
			 * T13 到期日 T14 存期 T15 利率 T16 到期利息 T17 支取方式 T18 柜员号 L51 是否自动转存 L52
			 * 本行提示：非到期日支取的，请提供存款人有效身份证件 L53 存单机换开
			 */
			if (find("L11")) {
				Dispatch.put(selection, "Text", bean.getProName());// 设置产品名称
				Dispatch.call(selection, "MoveRight");
			}
			if (find("L21")) {
				Dispatch.put(selection, "Text", bean.getAccNo());// 获取子账号所属卡号
				Dispatch.call(selection, "MoveRight");
			}

			if (find("L22")) {
				Dispatch.put(selection, "Text", bean.getAccName());
				Dispatch.call(selection, "MoveRight");
			}
			if (find("L31")) {
				Dispatch.put(selection, "Text",
						"人民币" + bean.getMoneyUpper());// 大写金额
				Dispatch.call(selection, "MoveRight");
			}
			if (find("L32")) {
				Dispatch.put(
						selection,
						"Text",
						"CNY"
								+ new DecimalFormat(",###.00").format(Double
										.valueOf(bean.getEndAmt())));// 存入金额（小写金额）
				Dispatch.call(selection, "MoveRight");
			}
			if (find("T11")) {
				String CreateTime="";
				if(bean.getOpenDate().contains("/")){
					CreateTime=bean.getOpenDate().replace("/", "");
				}else{
					CreateTime=bean.getOpenDate();
				}
				Dispatch.put(selection, "Text", CreateTime); // 存入日
				Dispatch.call(selection, "MoveRight");
			}
			if (find("T12")) {
				String ValueDate="";
				if(bean.getStartDate().contains("/")){
					ValueDate=bean.getStartDate().replace("/", "");
				}else{
					ValueDate=bean.getStartDate();
				}
				Dispatch.put(selection, "Text", ValueDate);// 设置核心日期（起息日）
				Dispatch.call(selection, "MoveRight");
			}
			if (find("T13")) {
				String EndTime="";
				if(bean.getEndDate().contains("/")){
					EndTime=bean.getEndDate().replace("/", "");
				}else{
					EndTime=bean.getEndDate();
				}
				Dispatch.put(selection, "Text", EndTime);// 到期日
				Dispatch.call(selection, "MoveRight");
			}
			if (find("T14")) {
				if(bean.getProCode().startsWith("JX") || bean.getProCode().startsWith("RJ")){	
					Dispatch.put(selection, "Text",bean.getJxRyjDepTem()+"天");//存期
					Dispatch.call(selection, "MoveRight");
				}else{	
						
						Dispatch.put(selection, "Text",bean.getMonthsUpperStr());//存期
						Dispatch.call(selection, "MoveRight");
					
					
				}
			}
			if (find("T15")) {
				if("0010".equals(bean.getProCode())){
					Dispatch.put(selection, "Text", bean.getRate().trim()+"%");// 利率
					Dispatch.call(selection, "MoveRight");
				}else if(bean.getProCode().trim().startsWith("A")){
					Dispatch.put(selection, "Text", "--");// 利率
					Dispatch.call(selection, "MoveRight");
				}else{
					if("1".equals(GlobalParameter.printRateStatus)){
						Dispatch.put(selection, "Text", "--");// 利率
						Dispatch.call(selection, "MoveRight");
					}else{
						Dispatch.put(selection, "Text", bean.getRate().trim()+"%");// 利率
						Dispatch.call(selection, "MoveRight");
					}
				}
				
			}
			if (find("T16")) {
				Dispatch.put(selection, "Text", bean.getRouting());// 利息
				Dispatch.call(selection, "MoveRight");
				
			}
			if (find("T17")) {
				Dispatch.put(selection, "Text", "密码"); // 无
				Dispatch.call(selection, "MoveRight");
			}
			if (find("T18")) {
				Dispatch.put(selection, "Text", GlobalParameter.tellerNo); // 柜员号
				Dispatch.call(selection, "MoveRight");
			}

			if (find("L51")) {
				/*
				 * 目前只打印整存整取的
				 */
				if ("0010".equals(bean.getProCode().trim())) {// 整存整取
					if (bean.getExchFlag().equals("1")) {
						Dispatch.put(selection, "Text",
								"自动转存   本行提示:存款到期已自动转存,非到期日支取的,请提供存款人有效身份证件。");
					} else {
						Dispatch.put(selection, "Text",
								"非自动转存   本行提示:非到期日支取的,请提供存款人有效身份证件。");
					}
					Dispatch.call(selection, "MoveRight");
				}else if(bean.getProCode().trim().startsWith("Y")){//约享存窗口期
					if("1".equals(GlobalParameter.printRateStatus)){
						Dispatch.put(selection, "Text","");
						Dispatch.call(selection, "MoveRight");
					}else{
						Dispatch.put(selection, "Text",
								bean.getPrinterL51Str()+"（详见协议）");
						Dispatch.call(selection, "MoveRight");
					}
				}else if(bean.getProCode().trim().startsWith("A")){//安享存
					if("1".equals(GlobalParameter.printRateStatus)){
						Dispatch.put(selection, "Text","");
						Dispatch.call(selection, "MoveRight");
					}else{
						Dispatch.put(selection, "Text",bean.getPrinterL52Str()+"（详见协议）");
						Dispatch.call(selection, "MoveRight");
					}
				}else {
					Dispatch.put(selection, "Text", "");
					Dispatch.call(selection, "MoveRight");
				}
			}
			if (find("L53")) {	
				/*if("3".equals(bean.getSubAccNoCancel())){
					Dispatch.put(selection, "Text", "电子账户-子账户(换开)"); 
					Dispatch.call(selection, "MoveRight"); 
				}else if("0".equals(bean.getSubAccNoCancel())){
					Dispatch.put(selection, "Text", "银行卡-子账户(换开)"); 
					Dispatch.call(selection, "MoveRight"); 
				}else{
					Dispatch.put(selection, "Text", "(换开)"); 
					Dispatch.call(selection, "MoveRight"); 
				}*/
				Dispatch.put(selection, "Text", "存单换开"); 
				Dispatch.call(selection, "MoveRight"); 
			}
			if (dzyz != null && !"-1".equals(dzyz)) {
				try {
					// 打印电子印章
					Dispatch picture = Dispatch.call(Dispatch.get(selection, "InLineShapes").toDispatch(), "AddPicture",Property.dzyz_ml).toDispatch();
					Dispatch.call(picture, "Select"); // 选中图片
					Dispatch.put(picture, "Width", new Variant(104));
					Dispatch ShapeRange = Dispatch.call(picture,"ConvertToShape").toDispatch(); // 取得图片区域
					Dispatch WrapFormat = Dispatch.get(ShapeRange, "WrapFormat").toDispatch(); // 取得图片的格式对象
					Dispatch.put(WrapFormat, "Type", 5);
					Dispatch shapeRange = Dispatch.get(selection, "ShapeRange").toDispatch();
					Dispatch.put(shapeRange, "Left", new Variant(-30));
					Dispatch.put(shapeRange, "Top", new Variant(-130));
				} catch (Exception e) {
					logger.error("电子印章打印失败" + e);
				}

			} else {
				logger.error("获取电子印章失败");
			}
			Dispatch.call(doc, "PrintOut");// 打印
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("打印存单程序异常" + e);
			return false;
		} finally {
			try {
				if (doc != null) {
					Dispatch.call(doc, "Close", new Variant(0));
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
	public String getDZYZ(String url,JSONObject paramMap){
		HttpClientUtil httpClientUtil = new HttpClientUtil();
		String result = httpClientUtil.httpPost(url, paramMap);
		return result;
	}
	
	/**
	 * 拼接调用电子印章接口所需要的参数
	 * 存单换开
	 * @param transBean
	 * @param globalParameter
	 * @return
	 */
	public JSONObject getJsons(PublicBillChangeOpenBean bean) {
		logger.info("开始拼接调用电子印章接口所需要的参数");
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("channel", "0035");// 渠道号
		jsonObject.put("fserialno", bean.getCreateTime()
				+ bean.getHKSvrJrnlNo().trim());// 开始日期+流水号
		jsonObject.put("instno", GlobalParameter.branchNo);// 机构号
		jsonObject.put("tellerno", GlobalParameter.tellerNo);// 柜员号
		jsonObject.put("chl_tran_code", "");// 外围系统交易码
		jsonObject.put("chl_tran_name", "");// 外围系统交易名称
		jsonObject.put("vouchername", "存单");// 凭证名称
		jsonObject.put("sealtype", "0001");// 电子印章种类（业务专用章）

		// 拼接tradeinfo参数值
		Map<String, String> map = new HashMap<String, String>();
		map.put("产品代码", bean.getProCode());// 产品代码
		map.put("产品名称", bean.getProName());// 产品名称
		map.put("账号", bean.getAccNo());// 设置账号
		map.put("户名", bean.getAccName());// 设置带*号的卡名
		if(!"".equals(bean.getSubAccNo()) && bean.getSubAccNo()!=null){
			map.put("卡号", bean.getSubCardNo());//卡号
			map.put("子账号", bean.getSubAccNo());//子账号
		}	
		map.put("金额(大写)", "人民币" + bean.getMoneyUpper());// 设置金额大写
		map.put("金额(小写)", "CNY" + bean.getAmount());// 设置存款金额（小写）
		map.put("存入日", bean.getOpenDate());// 设置开始日期
		map.put("起息日", bean.getStartDate());// 设置核心日期（起息日）
		map.put("到期日", bean.getDueDate());

		map.put("存期", bean.getMonthsUpperStr());// 设置存期
		map.put("利率", bean.getRate() + "%");// 设置定期利率
		map.put("到期利息", bean.getRouting());// 设置到期利息

		/*
		 * 新的页面设计没有是否凭密支取的功能
		 */

		// if(transBean.getSubPwd().trim().length()<5){//支取方式
		// map.put("支取方式","无"); //无
		// }else{
		// map.put("支取方式","密码"); //凭密支取
		// }
		map.put("柜员号", GlobalParameter.tellerNo);
		map.put("是否提前支取", "");
		map.put("换开渠道", "存单回收机");
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
	public boolean GenerateImage(String imgStr,String rootPath) {
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
	
	//查询打印存单利率打印开关
	public boolean getPrintRateStatus(){
		try {
			OnOffSetUp oosu=new OnOffSetUp();
			Map map=oosu.checkOnOffState("rate");
			if(!"000000".equals(map.get("resCode"))){
				logger.info("查询存单利率打印状态失败："+map.get("errMsg"));
				return false;
			}
			GlobalParameter.printRateStatus=(String)map.get("STATE");
			return true;
		} catch (Exception e) {
			logger.error("查询存单利率打印状态失败："+e);
			return false;
		}
	}
}
