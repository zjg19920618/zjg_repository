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

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import sun.misc.BASE64Decoder;

import com.boomhope.Bill.Public.GlobalParameter;
import com.boomhope.Bill.TransService.AccOpen.Bean.AccPublicBean;
import com.boomhope.Bill.TransService.CashOpen.Bean.PublicCashOpenBean;
import com.boomhope.Bill.TransService.LostReport.Bean.AccLostAndReturnInfoBean;
import com.boomhope.Bill.TransService.LostReport.Bean.LostPubBean;
import com.boomhope.Bill.TransService.LostReport.Bean.SearchProducRateInfo02864;
import com.boomhope.Bill.TransService.LostReport.Bean.ShowAccNoMsgBean;
import com.boomhope.Bill.TransService.LostReport.Interface.InterfaceSendMsg;
import com.boomhope.Bill.Util.DateTool;
import com.boomhope.Bill.Util.HttpClientUtil;
import com.boomhope.Bill.Util.MoneyUtils;
import com.boomhope.Bill.Util.NumberZH;
import com.boomhope.Bill.Util.OnOffSetUp;
import com.boomhope.Bill.Util.Property;
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
@SuppressWarnings({ "unchecked", "unused", "rawtypes","static-access" })
public class CDJPrintBill {

	private static Logger logger = Logger.getLogger(CDJPrintBill.class);
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
	
	public boolean PrintBills(PublicCashOpenBean transBean){
		boolean flag = false;
		try {
			//拼接调用电子印章所需要的参数
			JSONObject jsonObject = getJson(transBean);
			// 调用电子印章http接口（base64编码）
			String dzyz = getDZYZ(Property.dzyz_url, jsonObject);
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
				Dispatch.put(selection, "Text", transBean.getProductName());
		        Dispatch.call(selection, "MoveRight"); 
			}
			if(find("L21")){
				Dispatch.put(selection, "Text", transBean.getSubCardNo());
		        Dispatch.call(selection, "MoveRight"); 
			}
			
			if(find("L22")){
				Dispatch.put(selection, "Text", transBean.getCardName());
		        Dispatch.call(selection, "MoveRight"); 
			}
			if(find("L31")){
				Dispatch.put(selection, "Text", "人民币"+transBean.getMoneyUpper());//大写金额
				Dispatch.call(selection, "MoveRight"); 
			}
			if(find("L32")){
				Dispatch.put(selection, "Text", "CNY"+new DecimalFormat(",###.00").format(transBean.getMoney()));//小写金额
		        Dispatch.call(selection, "MoveRight"); 
			}
			if(find("T11")){
				
				Dispatch.put(selection, "Text", transBean.getCreateTime()); //存入日
		        Dispatch.call(selection, "MoveRight"); 
			}
			if(find("T12")){
				Dispatch.put(selection, "Text", transBean.getValueDate());//起息日
		        Dispatch.call(selection, "MoveRight"); 
			}
			if(find("T13")){
				Dispatch.put(selection, "Text", transBean.getEndTime());//到期日
		        Dispatch.call(selection, "MoveRight"); 
			}
			if(find("T14")){          
				Dispatch.put(selection, "Text",transBean.getMonthsUpperStr());//存期
				Dispatch.call(selection, "MoveRight");
			}
			if(find("T15")){
				Dispatch.put(selection, "Text", transBean.getRate()+"%");//利率
		        Dispatch.call(selection, "MoveRight"); 
			}
			if(find("T16")){
				Dispatch.put(selection, "Text", transBean.getInte()); //利息
		        Dispatch.call(selection, "MoveRight"); 
			}
			if(find("T17")){
				if(transBean.getSubPwd().trim().length()<5){//支取方式
					Dispatch.put(selection, "Text", "无");  //无
			        Dispatch.call(selection, "MoveRight"); 
				}else {
					Dispatch.put(selection, "Text", "密码");  //凭密支取
			        Dispatch.call(selection, "MoveRight"); 
				}
			}
			if(find("T18")){
				Dispatch.put(selection, "Text", GlobalParameter.tellerNo); //柜员号
		        Dispatch.call(selection, "MoveRight");
			}
			
			if(find("L51")){
				if("0010".equals(transBean.getProductCode().trim())){//整存整取
					if(transBean.getAutoSaving().equals("1")){
						Dispatch.put(selection, "Text", "自动转存   本行提示:存款到期已自动转存,非到期日支取的,请提供存款人有效身份证件。");
					}else{
						Dispatch.put(selection, "Text", "非自动转存   本行提示:非到期日支取的,请提供存款人有效身份证件。");
					}
					Dispatch.call(selection, "MoveRight");
				}else if(transBean.getProductCode().trim().startsWith("Y")){//约享存窗口期
					Dispatch.put(selection, "Text", transBean.getPrinterL51Str()+"（详见协议）");
					Dispatch.call(selection, "MoveRight");
				}else if(transBean.getProductCode().trim().startsWith("J")){//约享存窗口期
					Dispatch.put(selection, "Text", transBean.getPrinterL51Str()+"（详见协议）");
					Dispatch.call(selection, "MoveRight");
				}else{
					Dispatch.put(selection, "Text", "");
					Dispatch.call(selection, "MoveRight");
				}
			}
			if(find("L53")){
				Dispatch.put(selection, "Text", "现金开户"); 
				Dispatch.call(selection, "MoveRight"); 
			}
			if(dzyz!=null&&!"-1".equals(dzyz)){
				try {
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
				} catch (Exception e) {
					logger.error("电子印章打印失败"+e);
				}
			
			}else{
				logger.error("获取电子印章失败");
			}
	        Dispatch.call(doc, "PrintOut");//打印
		} catch (Exception e) {
			e.printStackTrace();
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
				e.printStackTrace();
			}
			
		}
		return true;
	}
	
	/**
	 * 银行卡开户打印存单
	 * @param transBean 银行卡开户中的字段bean
	 * @return
	 */
	public boolean PrintBill(AccPublicBean transBean) {
		boolean flag = false;
		try {
			if(!getPrintRateStatus()){
				return false;
			}
			
			// 拼接调用电子印章所需要的参数
			JSONObject jsonObject = getJsons(transBean);
			// 调用电子印章http接口（base64编码）
			String dzyz = getDZYZ(Property.dzyz_url, jsonObject);
			if (dzyz != null) {
				// 将base64编码转换成图片
				flag = GenerateImage(dzyz, Property.dzyz_ml);
				// 获取电子印章
				if (!flag) {
					return flag;
					
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
			 * 本行提示：非到期日支取的，请提供存款人有效身份证件 L53 存单机开户
			 */
			if (find("L11")) {
				Dispatch.put(selection, "Text", transBean.getProductName());// 设置产品名称
				Dispatch.call(selection, "MoveRight");
			}
			if (find("L21")) {
				Dispatch.put(selection, "Text", transBean.getCardNo().trim()
						+ "-" + transBean.getSubAcctNo());// 获取子账号所属卡号
				Dispatch.call(selection, "MoveRight");
			}

			if (find("L22")) {
				Dispatch.put(selection, "Text", transBean.getIdCardName());
				Dispatch.call(selection, "MoveRight");
			}
			if (find("L31")) {
				Dispatch.put(selection, "Text",
						"人民币" + transBean.getMoneyUpper());// 大写金额
				Dispatch.call(selection, "MoveRight");
			}
			if (find("L32")) {
				Dispatch.put(
						selection,
						"Text",
						"CNY"
								+ new DecimalFormat(",###.00").format(Double
										.valueOf(transBean.getMoney())));// 存入金额（小写金额）
				Dispatch.call(selection, "MoveRight");
			}
			if (find("T11")) {

				Dispatch.put(selection, "Text", transBean.getCreateTime()); // 存入日
				Dispatch.call(selection, "MoveRight");
			}
			if (find("T12")) {
				Dispatch.put(selection, "Text", transBean.getSvrDate());// 设置核心日期（起息日）
				Dispatch.call(selection, "MoveRight");
			}
			if (find("T13")) {
				String EndTime="";
				if(transBean.getEndTime().contains("/")){
					EndTime=transBean.getEndTime().replace("/", "");
				}else{
					EndTime=transBean.getEndTime();
				}
				Dispatch.put(selection, "Text", EndTime);// 到期日
				Dispatch.call(selection, "MoveRight");
			}
			if (find("T14")) {
				if(transBean.getProductCode().startsWith("JX") || transBean.getProductCode().startsWith("RJ")){
					Dispatch.put(selection, "Text",transBean.getJxRyjDepTem()+"天");//存期
					Dispatch.call(selection, "MoveRight");
				}else{
					Dispatch.put(selection, "Text",transBean.getMonthsUpperStr());//存期
					Dispatch.call(selection, "MoveRight");
				}
			}
			if (find("T15")) {
				if("0010".equals(transBean.getProductCode())){
					Dispatch.put(selection, "Text", transBean.getRate().trim()+"%");// 利率
					Dispatch.call(selection, "MoveRight");
				}else if(transBean.getProductCode().trim().startsWith("A")){
					Dispatch.put(selection, "Text", "--");// 利率
					Dispatch.call(selection, "MoveRight");
				}else{
					if("1".equals(GlobalParameter.printRateStatus)){
						Dispatch.put(selection, "Text", "--");// 利率
						Dispatch.call(selection, "MoveRight");
					}else{
						Dispatch.put(selection, "Text", transBean.getRate().trim()+"%");// 利率
						Dispatch.call(selection, "MoveRight");
					}
				}
			}
			if (find("T16")) {
				if("0010".equals(transBean.getProductCode())){
					Dispatch.put(selection, "Text", transBean.getInte().trim());// 利息
					Dispatch.call(selection, "MoveRight");
				}else{
					if("1".equals(GlobalParameter.printRateStatus)){
						Dispatch.put(selection, "Text", "--");// 利息
						Dispatch.call(selection, "MoveRight");
					}else{
						Dispatch.put(selection, "Text", transBean.getInte().trim());// 利息
						Dispatch.call(selection, "MoveRight");
					}
				}
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
				if ("0010".equals(transBean.getProductCode().trim())) {// 整存整取
					if (transBean.getAutoRedpFlag().equals("1")) {
						Dispatch.put(selection, "Text",
								"自动转存   本行提示:存款到期已自动转存,非到期日支取的,请提供存款人有效身份证件。");
					} else {
						Dispatch.put(selection, "Text",
								"非自动转存   本行提示:非到期日支取的,请提供存款人有效身份证件。");
					}
					Dispatch.call(selection, "MoveRight");
					
				}else if(transBean.getProductCode().trim().startsWith("Y")){//约享存窗口期
					if("1".equals(GlobalParameter.printRateStatus)){
						Dispatch.put(selection, "Text",
								transBean.getBillPrinterL51Str()+"（详见协议）");
						Dispatch.call(selection, "MoveRight");
					}else{
						Dispatch.put(selection, "Text",
								transBean.getPrinterL51Str()+"（详见协议）");
						Dispatch.call(selection, "MoveRight");
					}
				}else if(transBean.getProductCode().trim().startsWith("A")){//安享存
					if("1".equals(GlobalParameter.printRateStatus)){
						Dispatch.put(selection, "Text","");
						Dispatch.call(selection, "MoveRight");
					}else{
						Dispatch.put(selection, "Text",transBean.getPrinterL52Str()+"（详见协议）");
						Dispatch.call(selection, "MoveRight");
					}
				}else {
					Dispatch.put(selection, "Text", "");
					Dispatch.call(selection, "MoveRight");
				}
			}
			if (find("L53")) {
				Dispatch.put(selection, "Text", "银行卡—子账户");
				Dispatch.call(selection, "MoveRight");
			}
			if (dzyz != null && !"-1".equals(dzyz)) {
				try {
					// 打印电子印章
					Dispatch picture = Dispatch.call(
							Dispatch.get(selection, "InLineShapes")
									.toDispatch(), "AddPicture",
							Property.dzyz_ml).toDispatch();
					Dispatch.call(picture, "Select"); // 选中图片
					Dispatch.put(picture, "Width", new Variant(104));
					Dispatch ShapeRange = Dispatch.call(picture,
							"ConvertToShape").toDispatch(); // 取得图片区域
					Dispatch WrapFormat = Dispatch
							.get(ShapeRange, "WrapFormat").toDispatch(); // 取得图片的格式对象
					Dispatch.put(WrapFormat, "Type", 5);
					Dispatch shapeRange = Dispatch.get(selection, "ShapeRange")
							.toDispatch();
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
	
	
	
	/**
	 * 挂失补发打印存单
	 * @Description: 挂失补发时重新打印存单
	 * @Author: hk
	 * @date 2018年4月2日 上午10:49:56
	 */
	public boolean lostPrintBill(LostPubBean transBean) {
		boolean flag = false;
		
		AccLostAndReturnInfoBean bean = (AccLostAndReturnInfoBean)transBean.getAccMap().get("resAccInfo");
		
		//判断利率显示开关
		if(!getPrintRateStatus()){
			logger.info("查询利率开关失败");
			return false;
		}
		
		try {
			
			if(!"Y".equals(transBean.getAllPubDzyz())){
				// 拼接调用电子印章所需要的参数
				JSONObject jsonObject = getLostJsons(transBean);
				// 调用电子印章http接口（base64编码）
				String dzyz = getDZYZ(Property.dzyz_url, jsonObject);
				if (dzyz != null) {
					// 将base64编码转换成图片
					flag = GenerateImage(dzyz, Property.dzyz_ml);
					// 获取电子印章
					if (!flag) {
						logger.error("获取电子印章失败");
						return flag;
					}else{
						transBean.setAllPubDzyz("Y");
					}
					
				} else {
					logger.error("获取电子印章失败");
				}
			}

			ComThread.InitSTA();
			getDocInit();

			// 如果包含，则执行替换字符串
			/**
			 * L11 产品名称 L21 存单单号 L22 姓名 L31 存款大写金额 L32 存款小写金额 T11 存入日 T12 起息日
			 * T13 到期日 T14 存期 T15 利率 T16 到期利息 T17 支取方式 T18 柜员号 L51 是否自动转存 L52
			 * 本行提示：非到期日支取的，请提供存款人有效身份证件 L53 存单机开户
			 */
     		ShowAccNoMsgBean show=(ShowAccNoMsgBean) transBean.getAccMap().get("selectMsg");//挂失种类
			if (find("L11")) {
				Dispatch.put(selection, "Text", transBean.getProName());// 设置产品名称
				Dispatch.call(selection, "MoveRight");
			}
			if (find("L21")) {
				if("1".equals(show.getCardOrAccOrAcc1())){
	     			Dispatch.put(selection, "Text", transBean.getNewAccNo());
				}else{
					Dispatch.put(selection, "Text", transBean.getAllPubAccNo());// 获取子账号所属卡号
				}
				Dispatch.call(selection, "MoveRight");
			}

			if (find("L22")) {
				Dispatch.put(selection, "Text", bean.getCustName());
				Dispatch.call(selection, "MoveRight");
			}
			if (find("L31")) {
				Dispatch.put(selection, "Text",
						"人民币" + transBean.getOpenAmtUpper());// 大写存折金额
				Dispatch.call(selection, "MoveRight");
			}
			if (find("L32")) {
				Dispatch.put(
						selection,
						"Text",
						"CNY"
								+ new DecimalFormat(",###.00").format(Double
										.valueOf(bean.getBalance())));// 存入结存额（小写金额）
				Dispatch.call(selection, "MoveRight");
			}
			if (find("T11")) {
				Dispatch.put(selection, "Text", bean.getOpenDate().replace("/",""));
				Dispatch.call(selection, "MoveRight");
			}
			if (find("T12")) {
				Dispatch.put(selection, "Text", bean.getStartRateDate().replace("/", ""));// 设置核心日期（起息日）
				Dispatch.call(selection, "MoveRight");
			}
			if (find("T13")) {
				if("0002".equals(bean.getProCode())){
					Dispatch.put(selection, "Text", "");// 到期日
					Dispatch.call(selection, "MoveRight");
				}else{
					String EndTime="";
					if(transBean.getEndDate().contains("/")){
						EndTime=transBean.getEndDate().replace("/", "");
					}else{
						EndTime=transBean.getEndDate();
					}
					Dispatch.put(selection, "Text", EndTime);// 到期日
					Dispatch.call(selection, "MoveRight");
				}
			}
			if (find("T14")) {
				String depTerm="";
				if(bean.getProCode().startsWith("RJ")){//产品为如意存+ 则转换为天数
					String startdate=bean.getStartRateDate().replace("/","");//起息日期
					String enddate=bean.getEndIntDate().replace("/","");//到期日期
					SimpleDateFormat fommter = new SimpleDateFormat("yyyyMMdd");
					Date a1 = fommter.parse(startdate);
					Date b1 = fommter.parse(enddate);
				    int	date = DateTool.differentsDays(a1, b1);
				    depTerm=String.valueOf(date)+"天";
				    Dispatch.put(selection, "Text",depTerm);//如意存+ 的存期
				}else{
					//产品为其他
					if(bean.getDepTerm().startsWith("0")){
					    depTerm=bean.getDepTerm().replace(bean.getDepTerm().substring(0,2), bean.getDepTerm().substring(1,2));
					}else{
						depTerm=bean.getDepTerm();
					}
					if(depTerm.contains("Y")){
						Dispatch.put(selection, "Text",depTerm.replace("Y", "年"));//存期
					}else if(depTerm.contains("M")){
						Dispatch.put(selection, "Text",depTerm.replace("M", "个月"));//存期
					}else if(depTerm.contains("D")){
						Dispatch.put(selection, "Text",depTerm.replace("D", "天"));//存期
					}else{
						Dispatch.put(selection, "Text",depTerm+"天");//存期
					}
				}
				Dispatch.call(selection, "MoveRight");
			}
			if (find("T15")) {
				if("0010".equals(bean.getProCode())){
					Dispatch.put(selection, "Text", bean.getOpenRate().trim()+"%");// 利率
					Dispatch.call(selection, "MoveRight");
				}else if("0002".equals(bean.getProCode())){
					Dispatch.put(selection, "Text", "");// 利率
					Dispatch.call(selection, "MoveRight");
				}else if(bean.getProCode().trim().startsWith("A") || transBean.getProCode().trim().startsWith("JJ")){
					Dispatch.put(selection, "Text", "--");// 利率
					Dispatch.call(selection, "MoveRight");
				}else{
					if("1".equals(GlobalParameter.printRateStatus)){
						Dispatch.put(selection, "Text", "--");// 利率
						Dispatch.call(selection, "MoveRight");
					}else{
						Dispatch.put(selection, "Text", bean.getOpenRate().trim()+"%");// 利率
						Dispatch.call(selection, "MoveRight");
					}
				}
			}
			if (find("T16")) {
				if("0010".equals(bean.getProCode())){
					Dispatch.put(selection, "Text", bean.getRateSum().trim());// 利息
					Dispatch.call(selection, "MoveRight");
				}else if("0002".equals(bean.getProCode())){
					Dispatch.put(selection, "Text", "");// 利息
					Dispatch.call(selection, "MoveRight");
				}else{
					if("1".equals(GlobalParameter.printRateStatus)){
						Dispatch.put(selection, "Text", "--");// 利息
						Dispatch.call(selection, "MoveRight");
					}else{
						Dispatch.put(selection, "Text", bean.getRateSum().trim());// 利息
						Dispatch.call(selection, "MoveRight");
					}
				}
			}
			if (find("T17")) {//支去方式
				if("1".equals(transBean.getDrawCond())){
					Dispatch.put(selection, "Text", "密码"); 
				}else if("2".equals(transBean.getDrawCond())){
					Dispatch.put(selection, "Text", "证件");
				}else if("3".equals(transBean.getDrawCond())){
					Dispatch.put(selection, "Text", "印鉴"); 
				}else{
					Dispatch.put(selection, "Text", "无"); 
				}
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
					if (bean.getReMark().equals("0")) {
						Dispatch.put(selection, "Text",
								"非自动转存   本行提示:非到期日支取的,请提供存款人有效身份证件。");
					} else {
						Dispatch.put(selection, "Text",
								"自动转存   本行提示:存款到期已自动转存,非到期日支取的,请提供存款人有效身份证件。");
					}
					Dispatch.call(selection, "MoveRight");
					
				}else if("0002".equals(bean.getProCode().trim())){
					if (bean.getReMark().equals("0")) {
						Dispatch.put(selection, "Text",
								"非自动转存   本行提示:非到期日支取的,请提供存款人有效身份证件。");
					} else {
						Dispatch.put(selection, "Text",
								"自动转存   本行提示:存款到期已自动转存,非到期日支取的,请提供存款人有效身份证件。");
					}
					Dispatch.call(selection, "MoveRight");
				}else if(bean.getProCode().trim().startsWith("Y")){//约享存窗口期
					if("1".equals(GlobalParameter.printRateStatus)){
						Dispatch.put(selection, "Text",
								transBean.getWindownTepterm()+"（详见协议）");
						Dispatch.call(selection, "MoveRight");
					}else{
						Dispatch.put(selection, "Text",
								transBean.getWindownTepterm()+"（详见协议）");
						Dispatch.call(selection, "MoveRight");
					}
				}else if(transBean.getProCode().trim().startsWith("A") || transBean.getProCode().startsWith("JJ")  || transBean.getProCode().startsWith("FL") || transBean.getProCode().startsWith("FM")){//安享存  聚享存
					if("1".equals(GlobalParameter.printRateStatus)){
						Dispatch.put(selection, "Text","");
						Dispatch.call(selection, "MoveRight");
					}else{
						Dispatch.put(selection, "Text",transBean.getWindownTepterm()+"（详见协议）");
						Dispatch.call(selection, "MoveRight");
					}
				}else{
					Dispatch.put(selection, "Text", "");
					Dispatch.call(selection, "MoveRight");
				}
			}
			if (find("L53")) {
				if("1".equals(show.getCardOrAccOrAcc1())){
					Dispatch.put(selection, "Text", "补发");
				}else if("1_2".equals(show.getCardOrAccOrAcc1())){
					Dispatch.put(selection, "Text", "电子账户—子账户  补发");
				}else{
					Dispatch.put(selection, "Text", "银行卡账户—子账户  补发");
				}
				Dispatch.call(selection, "MoveRight");
			}
			if ("Y".equals(transBean.getAllPubDzyz())) {
				try {
					// 打印电子印章
					Dispatch picture = Dispatch.call(
							Dispatch.get(selection, "InLineShapes")
									.toDispatch(), "AddPicture",
							Property.dzyz_ml).toDispatch();
					Dispatch.call(picture, "Select"); // 选中图片
					Dispatch.put(picture, "Width", new Variant(104));
					Dispatch ShapeRange = Dispatch.call(picture,
							"ConvertToShape").toDispatch(); // 取得图片区域
					Dispatch WrapFormat = Dispatch
							.get(ShapeRange, "WrapFormat").toDispatch(); // 取得图片的格式对象
					Dispatch.put(WrapFormat, "Type", 5);
					Dispatch shapeRange = Dispatch.get(selection, "ShapeRange")
							.toDispatch();
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
	 * 银行卡开户
	 * @param transBean
	 * @param globalParameter
	 * @return
	 */
	public JSONObject getJsons(AccPublicBean transBean) {
		logger.info("开始拼接调用电子印章接口所需要的参数");
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("channel", "0035");// 渠道号
		jsonObject.put("fserialno", transBean.getCreateTime()
				+ transBean.getSvrJrnlNo().trim());// 开始日期+流水号
		jsonObject.put("instno", GlobalParameter.branchNo);// 机构号
		jsonObject.put("tellerno", GlobalParameter.tellerNo);// 柜员号
		jsonObject.put("chl_tran_code", "");// 外围系统交易码
		jsonObject.put("chl_tran_name", "");// 外围系统交易名称
		jsonObject.put("vouchername", "存单");// 凭证名称
		jsonObject.put("sealtype", "0001");// 电子印章种类（业务专用章）

		// 拼接tradeinfo参数值
		Map<String, String> map = new HashMap<String, String>();
		map.put("产品代码", transBean.getProductCode());// 产品代码
		map.put("储种", transBean.getProductName());// 产品名称
		map.put("账号", transBean.getAccinputNo());// 设置账号
		map.put("户名", transBean.getIdCardName());// 设置带*号的卡名
		map.put("卡号", transBean.getCardNo());//卡号
		map.put("子账号", transBean.getSubAcctNo());//子账号
		map.put("金额(大写)", "人民币" + transBean.getMoneyUpper());// 设置金额大写
		map.put("金额(小写)", "CNY" + transBean.getMoney());// 设置存款金额（小写）
		map.put("存入日", transBean.getCreateTime());// 设置开始日期
		map.put("起息日", transBean.getSvrDate());// 设置核心日期（起息日）
		map.put("到期日", transBean.getEndTime());

		map.put("存期", transBean.getMonthsUpperStr());// 设置存期
		map.put("利率", transBean.getRate() + "%");// 设置定期利率
		map.put("到期利息", transBean.getInte());// 设置到期利息

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
		map.put("开户渠道", "存单回收机开户");
		jsonObject.put("tradeinfo", JSONObject.fromObject(map).toString());
		logger.info("拼接调用电子印章接口所需要的参数结束---结果为-->" + jsonObject.toString());
		return jsonObject;
	}
	
	
	/**
	 * 拼接调用电子印章接口所需要的参数
	 * @param transBean
	 * @param globalParameter
	 * @return
	 */
	private JSONObject getJson(PublicCashOpenBean transBean){
		logger.info("开始拼接调用电子印章接口所需要的参数");
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("channel", "0035");//渠道号
		jsonObject.put("fserialno", transBean.getCreateTime()+transBean.getSvrJrnlNo().trim());//日期+流水号
		jsonObject.put("instno", GlobalParameter.branchNo);//机构号
		jsonObject.put("tellerno", GlobalParameter.tellerNo);//柜员号
		jsonObject.put("chl_tran_code", "");//外围系统交易码
		jsonObject.put("chl_tran_name", "");//外围系统交易名称
		jsonObject.put("vouchername", "存单");//凭证名称
		jsonObject.put("sealtype", "0001");//电子印章种类（业务专用章）
		
		//拼接tradeinfo参数值
		Map<String,String> map = new HashMap<String, String>();
		map.put("产品代码", transBean.getProductCode());
		map.put("储种",transBean.getProductName());
		map.put("账号",transBean.getSubCardNo());
		map.put("户名",transBean.getCardName());
		map.put("金额(大写)","人民币"+transBean.getMoneyUpper());
		map.put("金额(小写)","CNY"+transBean.getMoney());
		map.put("存入日",transBean.getCreateTime());
		map.put("起息日",transBean.getValueDate());
		map.put("到期日",transBean.getEndTime());
		
		map.put("存期",transBean.getMonthsUpperStr());
		map.put("利率",transBean.getRate()+"%");
		map.put("到期利息",transBean.getInte());
		
		if(transBean.getSubPwd().trim().length()<5){//支取方式
			map.put("支取方式","无"); //无
		}else{
			map.put("支取方式","密码"); //凭密支取
		}
		map.put("柜员号",GlobalParameter.tellerNo);
		map.put("是否提前支取","");
		map.put("开户渠道","存单回收机开户");
		jsonObject.put("tradeinfo", JSONObject.fromObject(map).toString());
		logger.info("拼接调用电子印章接口所需要的参数结束---结果为-->"+jsonObject.toString());
		return jsonObject;
	}
	
	
	/**
	 * 挂失补发时打印存单拼接电子印章信息
	 * @Description: 调用电子印章信息
	 * @Author: hk
	 * @date 2018年4月2日 上午10:51:06
	 */
	public JSONObject getLostJsons(LostPubBean transBean) {
		logger.info("开始拼接调用电子印章接口所需要的参数");
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("channel", "0035");// 渠道号
		jsonObject.put("fserialno", transBean.getAllPubSvrDate()
				+ transBean.getLostJrnlNo().trim());// 开始日期+流水号
		jsonObject.put("instno", GlobalParameter.branchNo);// 机构号
		jsonObject.put("tellerno", GlobalParameter.tellerNo);// 柜员号
		jsonObject.put("chl_tran_code", "");// 外围系统交易码
		jsonObject.put("chl_tran_name", "");// 外围系统交易名称
		jsonObject.put("vouchername", "存单");// 凭证名称
		jsonObject.put("sealtype", "0001");// 电子印章种类（业务专用章）

		// 拼接tradeinfo参数值
		Map<String, String> map = new HashMap<String, String>();
		AccLostAndReturnInfoBean bean = (AccLostAndReturnInfoBean)transBean.getAccMap().get("resAccInfo");
		map.put("产品代码", transBean.getProCode());// 产品代码
		map.put("储种", transBean.getProName());// 产品名称
		map.put("账号", transBean.getAllPubAccNo());// 设置账号
		map.put("金额(大写)", "人民币" + transBean.getOpenAmtUpper());// 设置金额大写
		map.put("金额(小写)", "CNY" + transBean.getOpenAmt());// 设置存款金额（小写）
		map.put("存入日", bean.getOpenDate());// 设置开始日期
		map.put("起息日", bean.getStartRateDate());// 设置核心日期（起息日）
		map.put("到期日", bean.getEndIntDate());

		map.put("存期", bean.getDepTerm());// 设置存期
		map.put("利率", bean.getOpenRate() + "%");// 设置定期利率
		map.put("到期利息", bean.getRateSum());// 设置到期利息

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
		map.put("开户渠道", "存单回收机开户");
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
