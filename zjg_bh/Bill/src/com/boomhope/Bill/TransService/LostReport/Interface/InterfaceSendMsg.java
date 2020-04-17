package com.boomhope.Bill.TransService.LostReport.Interface;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.boomhope.Bill.TransService.LostReport.Bean.AccCancelProFileBean;
import com.boomhope.Bill.TransService.LostReport.Bean.AccLostAndReturnInfoBean;
import com.boomhope.Bill.TransService.LostReport.Bean.AccNoCheck08223;
import com.boomhope.Bill.TransService.LostReport.Bean.AccTdMsgBean;
import com.boomhope.Bill.TransService.LostReport.Bean.BillChangeInfoBean;
import com.boomhope.Bill.TransService.LostReport.Bean.BillChangeOpenInfoBean;
import com.boomhope.Bill.TransService.LostReport.Bean.EAccInfoBean;
import com.boomhope.Bill.TransService.LostReport.Bean.IdCardCheckInfo;
import com.boomhope.Bill.TransService.LostReport.Bean.LostCheckBean;
import com.boomhope.Bill.TransService.LostReport.Bean.LostPubBean;
import com.boomhope.Bill.TransService.LostReport.Bean.SearchProducRateInfo02864;
import com.boomhope.Bill.TransService.LostReport.Bean.ShowAccNoMsgBean;
import com.boomhope.Bill.TransService.LostReport.Bean.SubAccInfoBean;
import com.boomhope.Bill.Util.AllPublicTransSocketClient;
import com.boomhope.Bill.Util.DateUtil;
import com.boomhope.Bill.Util.FileUtil;
import com.boomhope.Bill.Util.FtpFileUtils;
import com.boomhope.Bill.Util.Property;
import com.boomhope.Bill.Util.TextFileReader;
import com.boomhope.Bill.Util.UtilPreFile;
import com.boomhope.tms.message.XStreamNameCoder;
import com.boomhope.tms.message.account.in.BCK02864ResBean;
import com.boomhope.tms.message.account.in.BCK03845ResBean;
import com.boomhope.tms.message.in.InResBean;
import com.boomhope.tms.message.in.bck.BCK01325ResBean;
import com.boomhope.tms.message.in.bck.BCK01597ResBean;
import com.boomhope.tms.message.in.bck.BCK02781ResBean;
import com.boomhope.tms.message.in.bck.BCK02791ResBean;
import com.boomhope.tms.message.in.bck.BCK03519ResBean;
import com.boomhope.tms.message.in.bck.BCK03521ResBean;
import com.boomhope.tms.message.in.bck.BCK03527ResBean;
import com.boomhope.tms.message.in.bck.BCK04422ResBean;
import com.boomhope.tms.message.in.bck.BCK07515ResBean;
import com.boomhope.tms.message.in.bck.BCK07517ResBean;
import com.boomhope.tms.message.in.bck.BCK07519ResBean;
import com.boomhope.tms.message.in.bck.BCK07524ResBean;
import com.boomhope.tms.message.in.bck.BCK07527ResBean;
import com.boomhope.tms.message.in.bck.BCK07601ResBean;
import com.boomhope.tms.message.in.bck.BCK07622ResBean;
import com.boomhope.tms.message.in.bck.BCK07659ResBean;
import com.boomhope.tms.message.in.bck.BCK07660ResBean;
import com.boomhope.tms.message.in.bck.BCK07665ResBean;
import com.boomhope.tms.message.in.bck.BCK07670ResBean;
import com.boomhope.tms.message.in.bck.BCK07696ResBean;
import com.boomhope.tms.message.in.bck.BCK07819ResBean;
import com.boomhope.tms.message.in.bck.BCK08021ResBean;
import com.boomhope.tms.message.in.bck.BCK08159ResBean;
import com.boomhope.tms.message.in.bck.BCK08160ResBean;
import com.boomhope.tms.message.in.bck.BCK08240ResBean;
import com.boomhope.tms.message.in.bck.BCK08169ResBean;
import com.boomhope.tms.message.in.bck.BCK08176ResBean;
import com.boomhope.tms.message.in.bck.BCK08177ResBean;
import com.boomhope.tms.message.in.bck.BCK08178ResBean;
import com.boomhope.tms.message.in.bck.BCK08179ResBean;
import com.boomhope.tms.message.in.bck.BCK08180ResBean;
import com.boomhope.tms.message.in.bck.BCK08181ResBean;
import com.boomhope.tms.message.in.bck.BCK08182ResBean;
import com.boomhope.tms.message.in.bck.BCK08186ResBean;
import com.boomhope.tms.message.in.bck.BCK08189ResBean;
import com.boomhope.tms.message.in.bck.BCK08190ResBean;
import com.boomhope.tms.message.in.bck.BCK08193ResBean;
import com.boomhope.tms.message.in.bck.BCK08194ResBean;
import com.boomhope.tms.message.in.bck.BCK08198ResBean;
import com.boomhope.tms.message.in.bck.BCK08199ResBean;
import com.boomhope.tms.message.in.bck.BCK08222ResBean;
import com.boomhope.tms.message.in.bck.BCK08223ResBean;
import com.boomhope.tms.message.in.bck.BCK08230ResBean;
import com.boomhope.tms.message.in.bck.BCK08231ResBean;
import com.boomhope.tms.message.in.bck.BCK08233ResBean;
import com.boomhope.tms.message.in.bck.BCK08236ResBean;
import com.boomhope.tms.message.in.bck.BCK08237ResBean;
import com.boomhope.tms.message.in.bck.BCK08239ResBean;
import com.boomhope.tms.message.in.bck.BCK08257ResBean;
import com.boomhope.tms.message.in.bck.BCK08258ResBean;
import com.boomhope.tms.message.in.bck.BCK08260ResBean;
import com.boomhope.tms.message.in.bck.BCK08262ResBean;
import com.boomhope.tms.message.in.tms.Tms0005ResBean;
import com.boomhope.tms.message.in.tms.Tms0005ResBodyBean;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * 接口报文请求及响应(挂失/解挂)
 * @author Administrator
 *
 */
@SuppressWarnings({"rawtypes","static-access","unchecked","unused"})
public class InterfaceSendMsg {
static Logger logger = Logger.getLogger(InterfaceSendMsg.class);
	
	//报文返回成功
	public static String SUCCESS = "000000";
	
	/**
	 * 本人联网核查
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public static Map inter07670(LostPubBean bean)throws Exception{
		//获取数据
		Map<String,String> map = new HashMap<String,String>();
		//证件号码
		map.put("ID", bean.getAllPubCheckIdCardNo());
		//证件姓名
		map.put("NAME", bean.getAllPubCheckIdCardName());
		Map<String,String> params = new HashMap<String,String>();
		String reqMsg=CreateXmlMsg.BCK_07670(map);
		AllPublicTransSocketClient socketClient =new AllPublicTransSocketClient();
		socketClient.createSocket();
		logger.info("请求报文："+reqMsg);
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK07670ResBean.class);
		BCK07670ResBean bck07670ResBean = (BCK07670ResBean)reqXs.fromXML(resMsg);
		if(bck07670ResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","调用接口07670失败");
			return params;
		}
		String resCode = bck07670ResBean.getHeadBean().getResCode();
		String errMsg = bck07670ResBean.getHeadBean().getResMsg();
		logger.info("联网核查resCode"+resCode+"联网核查"+resMsg);
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		//下载核查图片
		try {
			String fileName = bck07670ResBean.getBody().getFILE_NAME();
			FtpFileUtils.downloadFileByFTPClient(Property.FTP_IP_DOWN, Integer.parseInt(Property.FTP_PORT_DOWN), Property.FTP_USER_DOWN, Property.FTP_PWD_DOWN, fileName, Property.FTP_LOCAL_PATH + fileName);
			UtilPreFile.getIdCardImage(Property.FTP_LOCAL_PATH+fileName,Property.ID_CARD_SELF);
			// 联网核查照片查看
			String tmp = Property.BH_TMP + "\\"+DateUtil.getNowDate("yyyyMMdd")+"\\" +DateUtil.getNowDate("yyyyMMddhhmmss")+"\\";
			// 拷贝临时图片--------------------
			File from_f = new File(Property.ID_CARD_SELF);
			File to_f = new File(tmp+bean.getAllPubCheckIdCardNo()+"_mePoic.jpg");
			FileUtil.copyFileUsingJava7Files(from_f, to_f);
			params.put("filePath", tmp+bean.getAllPubCheckIdCardNo()+"_mePoic.jpg");
		} catch (Exception e) {
			logger.error("联网核查照片下载失败"+e);
			params.put("resCode","4444");
			params.put("errMsg","联网核查照片下载失败");
			return params;
		}
		params.put("CORE_RET_MSG", bck07670ResBean.getBody().getCORE_RET_MSG());
		params.put("resCode",resCode);
		return params;
	} 

	/**
	 * 查询灰名单
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public static Map inter01597(LostPubBean bean)throws Exception{
		//获取数据
		Map<String,String> map = new HashMap<String,String>();
		//证件号码
		map.put("ID_NUMBER1",bean.getAllPubCheckIdCardNo());
		//证件类型
		map.put("ID_TYPE1", "1");
		//证件姓名
		map.put("ID_NAME1", bean.getAllPubCheckIdCardName());
		//程序标识
		map.put("PROGRAM_FLAG","100501");
		//卡号
		map.put("CARD_NO1", bean.getAllPubAccNo());
		//账号
		map.put("ACC_NO1", bean.getAllPubAccNo());
		Map<String,String> params = new HashMap<String,String>();
		String reqMsg=CreateXmlMsg.BCK_01597(map);
		AllPublicTransSocketClient socketClient =new AllPublicTransSocketClient();
		socketClient.createSocket();
		logger.info("请求报文："+reqMsg);
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK01597ResBean.class);
		BCK01597ResBean bck01597ResBean = (BCK01597ResBean)reqXs.fromXML(resMsg);
		if(bck01597ResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","调用接口01597失败");
			return params;
		}
		String resCode = bck01597ResBean.getHeadBean().getResCode();
		String errMsg = bck01597ResBean.getHeadBean().getResMsg();
		logger.info("查询白名单resCode"+resCode+"查询白名单resMsg"+resMsg);
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		//获取检查状态
		String svrno1 = bck01597ResBean.getBody().getESB_LIST().getSTTN();
		for (int i = 0; i < 4; i++) {
			if("1".equals(String.valueOf(svrno1.charAt(i)))){
				params.put("resCode","0010");
				params.put("errMsg","身份涉案，禁止办理业务。");
				return params;
			}if("2".equals(String.valueOf(svrno1.charAt(i)))){//检查为灰名单的时候
				params.put("resCode","0020");
				params.put("errMsg","身份可疑，禁止办理业务。");
				return params;
			}	
		}
		params.put("resCode",resCode);
		return params;
		
	} 
	
	/**
	 * 查询销户转入卡黑灰名单
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public static Map card01597(LostPubBean bean)throws Exception{
		//获取数据
		Map<String,String> map = new HashMap<String,String>();
		//程序标识
		map.put("PROGRAM_FLAG","100501");
		//卡号
		map.put("CARD_NO1", bean.getSelectCardNo());
		//账号
		map.put("ACC_NO1", bean.getSelectCardNo());
		Map<String,String> params = new HashMap<String,String>();
		String reqMsg=CreateXmlMsg.BCK_01597(map);
		AllPublicTransSocketClient socketClient =new AllPublicTransSocketClient();
		socketClient.createSocket();
		logger.info("请求报文："+reqMsg);
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK01597ResBean.class);
		BCK01597ResBean bck01597ResBean = (BCK01597ResBean)reqXs.fromXML(resMsg);
		if(bck01597ResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","调用接口01597失败");
			return params;
		}
		String resCode = bck01597ResBean.getHeadBean().getResCode();
		String errMsg = bck01597ResBean.getHeadBean().getResMsg();
		logger.info("查询白名单resCode"+resCode+"查询白名单resMsg"+resMsg);
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		//获取检查状态
		String svrno1 = bck01597ResBean.getBody().getESB_LIST().getSTTN();
		for (int i = 0; i < 4; i++) {
			if("1".equals(String.valueOf(svrno1.charAt(i)))){
				params.put("resCode","0010");
				params.put("errMsg","账户涉案，禁止办理业务。");
				return params;
			}if("2".equals(String.valueOf(svrno1.charAt(i)))){//检查为灰名单的时候
				params.put("resCode","0020");
				params.put("errMsg","账户可疑，禁止办理业务。");
				return params;
			}	
		}
		params.put("resCode",resCode);
		return params;
		
	} 
	
	/**
	 * 个人客户查询( 01020)-前置07519
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public static Map inter07519(LostPubBean bean)throws Exception{
		//获取数据
		Map<String,String> map = new HashMap<String,String>();
		map.put("SCH_TYPE", "2");//查询类型
		map.put("ID_TYPE","10100");//证件类型
		map.put("ID_NO",bean.getAllPubIDNo());//身份证号
		map.put("INFO_TYPE", "1");//信息类型
		Map params = new HashMap();
		String reqMsg=CreateXmlMsg.BCK_07519(map);
		
		//连接socket
		AllPublicTransSocketClient socketClient =new AllPublicTransSocketClient();
		socketClient.createSocket();
		
		//发送请求报文、接收响应报文
		logger.info("请求报文："+reqMsg);
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		   
		//解析xml
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK07519ResBean.class);
		BCK07519ResBean bck07519ResBean = (BCK07519ResBean)reqXs.fromXML(resMsg);
		
		 //获取返回报文错误码和错误信息
		String resCode = bck07519ResBean.getHeadBean().getResCode();
		String errMsg = bck07519ResBean.getHeadBean().getResMsg();
		logger.info("查询客户号：resCode07519:"+resCode+"resMsg07519"+errMsg);
		
		if(bck07519ResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","查询客户号接口调用失败");
			return params;
		}
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		
		String fileName = bck07519ResBean.getBody().getFILE_NAME()!=null && !"".equals(bck07519ResBean.getBody().getFILE_NAME().trim())?bck07519ResBean.getBody().getFILE_NAME().trim():"";//文件路径
		if("".equals(fileName)){
			 logger.error("未返回客户号文件下载路径");
			 params.put("resCode","4444");
			 params.put("errMsg","查询客户号失败");
			 return params;
		}
		
		List<IdCardCheckInfo> list = null;
		try {
			
			FtpFileUtils.downloadFileByFTPClient(Property.FTP_IP_DOWN, Integer.parseInt(Property.FTP_PORT_DOWN), Property.FTP_USER_DOWN, Property.FTP_PWD_DOWN, fileName, Property.FTP_LOCAL_PATH + fileName);
			list = TextFileReader.paddingList(Property.FTP_LOCAL_PATH + fileName, IdCardCheckInfo.class);
			logger.info(list);
			
		} catch (Exception e) {
			
			 logger.error("下载客户号文件失败"+e);
			 params.put("resCode","4444");
			 params.put("errMsg","查询客户号失败");
			 return params;
		}
		
		if(list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				logger.info("客户号："+list.get(i).getCust_No());
				bean.setCustNo(list.get(i).getCust_No()!=null && !"".equals(list.get(i).getCust_No().trim()) ? list.get(i).getCust_No().trim() : "");//客户号
			}
		}
		if(bean.getCustNo() == null || "".equals(bean.getCustNo().trim())){
			
			logger.info("未获取到客户号");
			params.put("resCode","4444");
			params.put("errMsg","未获取到客户号");
			return params;
		}
				
		params.put("resCode", SUCCESS);
		return params;
	} 
	
	/**
	 * 查询所有客户号，个人客户查询( 01020)-前置07519
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public static Map check07519(LostPubBean bean)throws Exception{
		//获取数据
		Map<String,String> map = new HashMap<String,String>();
		map.put("SCH_TYPE", "2");//查询类型
		map.put("ID_TYPE","10100");//证件类型
		map.put("ID_NO",bean.getAllPubIDNo());//身份证号
		map.put("INFO_TYPE", "1");//信息类型
		Map params = new HashMap();
		String reqMsg=CreateXmlMsg.BCK_07519(map);
		
		//连接socket
		AllPublicTransSocketClient socketClient =new AllPublicTransSocketClient();
		socketClient.createSocket();
		
		//发送请求报文、接收响应报文
		logger.info("请求报文："+reqMsg);
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		   
		//解析xml
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK07519ResBean.class);
		BCK07519ResBean bck07519ResBean = (BCK07519ResBean)reqXs.fromXML(resMsg);
		
		 //获取返回报文错误码和错误信息
		String resCode = bck07519ResBean.getHeadBean().getResCode();
		String errMsg = bck07519ResBean.getHeadBean().getResMsg();
		logger.info("查询客户号：resCode07519:"+resCode+"resMsg07519"+errMsg);
		
		if(bck07519ResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","查询客户号接口调用失败");
			return params;
		}
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		
		String fileName = bck07519ResBean.getBody().getFILE_NAME()!=null && !"".equals(bck07519ResBean.getBody().getFILE_NAME().trim())?bck07519ResBean.getBody().getFILE_NAME().trim():"";//文件路径
		if("".equals(fileName)){
			 logger.error("未返回客户号文件下载路径");
			 params.put("resCode","4444");
			 params.put("errMsg","查询客户号失败");
			 return params;
		}
		
		List<IdCardCheckInfo> list = null;
		try {
			
			FtpFileUtils.downloadFileByFTPClient(Property.FTP_IP_DOWN, Integer.parseInt(Property.FTP_PORT_DOWN), Property.FTP_USER_DOWN, Property.FTP_PWD_DOWN, fileName, Property.FTP_LOCAL_PATH + fileName);
			list = TextFileReader.paddingList(Property.FTP_LOCAL_PATH + fileName, IdCardCheckInfo.class);
			logger.info(list);
			
		} catch (Exception e) {
			
			 logger.error("下载客户号文件失败"+e);
			 params.put("resCode","4444");
			 params.put("errMsg","查询客户号失败");
			 return params;
		}
		
		params.put("custNoList", list);		
		params.put("resCode", SUCCESS);
		return params;
	} 
	
	/**
	  * 按客户号查询账户信息【 20103】-前置07527
	  * @param 
	  * @return
	  * @throws Exception
	  */
	public static Map inter07527(LostPubBean bean)throws Exception{
		//获取数据
		Map<String, String> map = new HashMap<String, String>();         
		map.put("CUST_NO", bean.getCustNo());//客户号
		map.put("CUST_TYPE", "1");//客户类型
		map.put("CUST_NAME", bean.getAllPubIdCardName());//客户名称
		map.put("ID_TYPE", "");//证件类型
		map.put("ID_NO", "");//证件号码
		map.put("QRY_TYPE", "2");//查询类型
		   
		Map params = new HashMap();
		   
		//连接socket
		String reqMsg=CreateXmlMsg.BCK_07527(map);
		AllPublicTransSocketClient socketClient =new AllPublicTransSocketClient();
		socketClient.createSocket();
		   
		//发送请求报文、接收响应报文
		logger.info("请求报文："+reqMsg);
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		   
		//解析xml
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK07527ResBean.class);
		BCK07527ResBean bck07527ResBean = (BCK07527ResBean)reqXs.fromXML(resMsg);
		   
		//获取返回报文错误码和错误信息
		String resCode = bck07527ResBean.getHeadBean().getResCode();
		String errMsg = bck07527ResBean.getHeadBean().getResMsg();
		logger.info("按客户号查询普通账户信息:resCode07527:"+resCode+"resMsg07527:"+errMsg);
		
		if(bck07527ResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","按客户号查询普通账户信息接口调用失败");
			return params;
		}
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		   
		String fileName = bck07527ResBean.getBody().getFILE_NAME()!=null && !"".equals(bck07527ResBean.getBody().getFILE_NAME().trim())?bck07527ResBean.getBody().getFILE_NAME().trim():"";//文件路径
		List<BillChangeInfoBean> list2  = null;  
		List<BillChangeInfoBean> list1 = new ArrayList<BillChangeInfoBean>(); 
		try {
			   // 下载文件
			   FtpFileUtils.downloadFileByFTPClient(Property.FTP_IP_DOWN, Integer.parseInt(Property.FTP_PORT_DOWN), Property.FTP_USER_DOWN, Property.FTP_PWD_DOWN, fileName, Property.FTP_LOCAL_PATH + fileName);
			   list2 = TextFileReader.paddingList(Property.FTP_LOCAL_PATH + fileName, BillChangeInfoBean.class);
			   logger.info(list2);
			  
		} catch (Exception e) {
			   
			   logger.error("按客户号查询普通账户信息查询列表下载失败"+e);
			   params.put("resCode","4444");
			   params.put("errMsg","按客户号查询普通账户信息查询列表下载失败");
			   return params;
		}
		   
		if(list2.size()>0){
			 for(BillChangeInfoBean eBean:list2){
				 eBean.getOpen_date().trim().replaceAll("/","");
				 //选择非销户的，非积享存的
				 if(!"*".equals(String.valueOf(eBean.getAcct_stat().charAt(0))) && !"Q".equals(String.valueOf(eBean.getAcct_stat().charAt(0))) && !eBean.getProduct_code().startsWith("JX")){
					 list1.add(eBean);
					 logger.info("未销户且非积享存的存单个人账号:"+eBean.getAcct_no());
				 }else{
					 logger.info("已销户或者是积享存的存单个人账号:"+eBean.getAcct_no());
				 }
			 }
		}
		  
		params.put("AccMsgList", list1);
		params.put("resCode",SUCCESS);
		return params;
	 }
	 
	 /**
	   * 开卡明细联动查询【70028】-07524
	   * @param 
	   * @return
	   * @throws Exception
	   */
	 public static Map inter07524(LostPubBean bean)throws Exception{
		 //获取数据
		 Map<String, String> map = new HashMap<String, String>();				    	
		 map.put("ID_TYPE", "10100");//证件类型
		 map.put("ID_NO", bean.getAllPubIDNo());//证件号
			
		 Map params = new HashMap();
			
		//连接socket
		String reqMsg=CreateXmlMsg.BCK_07524(map);
		AllPublicTransSocketClient socketClient =new AllPublicTransSocketClient();
		socketClient.createSocket();
			
		//发送请求报文、接收响应报文
		logger.info("请求报文："+reqMsg);
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
			
		//解析xml
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK07524ResBean.class);
		BCK07524ResBean bck07524ResBean = (BCK07524ResBean)reqXs.fromXML(resMsg);
			
		//获取返回报文错误码和错误信息
		String resCode = bck07524ResBean.getHeadBean().getResCode();
		String errMsg = bck07524ResBean.getHeadBean().getResMsg();
		logger.info("开卡明细联动查询:resCode07524:"+resCode+"resMsg07524:"+errMsg);
		
		if(bck07524ResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","开卡明细联动查询接口失败");
			return params;
		}
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		String fileName = bck07524ResBean.getBody().getFILE_NAME()!=null && !"".equals(bck07524ResBean.getBody().getFILE_NAME().trim())?bck07524ResBean.getBody().getFILE_NAME().trim():"";//文件路径
		List<BillChangeOpenInfoBean> list  = null;
			
		try {
			// 下载文件
			FtpFileUtils.downloadFileByFTPClient(Property.FTP_IP_DOWN, Integer.parseInt(Property.FTP_PORT_DOWN), Property.FTP_USER_DOWN, Property.FTP_PWD_DOWN, fileName, Property.FTP_LOCAL_PATH + fileName);
			list = TextFileReader.paddingList(Property.FTP_LOCAL_PATH + fileName, BillChangeOpenInfoBean.class);
				
		} catch (Exception e) {
			
			logger.error("开卡明细联动查询列表下载失败"+e);
			params.put("resCode","4444");
			params.put("errMsg","开卡明细联动查询列表下载失败");
			return params;
		}
		
		if(list.size()>0){
			bean.setCards(new String[list.size()]);
			for (int i = 0; i < list.size(); i++) {
				bean.getCards()[i] = list.get(i).getCard_no()!=null && !"".equals(list.get(i).getCard_no().trim())?list.get(i).getCard_no().trim():"";//所有银行卡号
			}
		}
		params.put("resCode",SUCCESS);
		return params;
	} 
		
	 /**
	   * 卡信息查询03845
	   * @param bean
	   * @return
	   * @throws Exception
	   */
	 public static Map inter03845(LostPubBean bean)throws Exception{
		 //获取数据
		 Map<String, String> map = new HashMap<String, String>();
		 if(bean.getIsPinPass()!=null && "1".equals(bean.getIsPinPass())){
			 map.put("CARD_NO", bean.getAllPubPassAccNo());//卡号
			 map.put("SUB_ACCT_NO", "");//子账号
			 map.put("ISPIN","1");//验密
			 map.put("PASSWD",bean.getAllPubAccPwd());//卡密码
		 }else{
			 map.put("CARD_NO", bean.getCardNo());//卡号
			 map.put("SUB_ACCT_NO", "");//子账号
			 map.put("ISPIN","0");//不验密		
			 map.put("PASSWD","");//卡密码
		 }
			
		 Map params = new HashMap();
			
		 //连接socket
		 String reqMsg=CreateXmlMsg.BCK_03845(map);
		 AllPublicTransSocketClient socketClient =new AllPublicTransSocketClient();
		 socketClient.createSocket();
			
		 //发送请求报文、接收响应报文
		 logger.info("请求报文："+reqMsg);
		 String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		 logger.info("响应报文："+resMsg);
			
		 //解析xml
		 XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		 reqXs.alias("Root", BCK03845ResBean.class);
		 BCK03845ResBean bck03845ResBean = (BCK03845ResBean)reqXs.fromXML(resMsg);
			
		 //获取返回报文错误码和错误信息
		 String resCode = bck03845ResBean.getHeadBean().getResCode();
		 String errMsg = bck03845ResBean.getHeadBean().getResMsg();
		 logger.info("卡信息查询:resCode03845:"+resCode+"resMsg03845:"+errMsg);
		 if(bck03845ResBean == null){
			 params.put("resCode","4444");
			 params.put("errMsg","调用卡信息查询接口失败");
			 return params;
		 }
		 if(!SUCCESS.equals(resCode)){
			 params.put("resCode",resCode);
			 params.put("errMsg",errMsg);
			 return params;
		 }
			
		 bean.setOpenDate(bck03845ResBean.getBody().getOPEN_DATE()!=null && !"".equals(bck03845ResBean.getBody().getOPEN_DATE().trim())?bck03845ResBean.getBody().getOPEN_DATE().trim():"");//开户日期
		 bean.setDepTerm(bck03845ResBean.getBody().getDEP_TERM()!=null && !"".equals(bck03845ResBean.getBody().getDEP_TERM().trim())?bck03845ResBean.getBody().getDEP_TERM().trim():"");//存期
		 bean.setCustName(bck03845ResBean.getBody().getACCT_NAME()!=null && !"".equals(bck03845ResBean.getBody().getACCT_NAME().trim())?bck03845ResBean.getBody().getACCT_NAME().trim():"");//客户名称
		 bean.setEndAmt(bck03845ResBean.getBody().getBALANCE()!=null && !"".equals(bck03845ResBean.getBody().getBALANCE().trim())?bck03845ResBean.getBody().getBALANCE().trim():"");//卡结存额
		 bean.setCardEndAmt(bck03845ResBean.getBody().getBALANCE()!=null && !"".equals(bck03845ResBean.getBody().getBALANCE().trim())?bck03845ResBean.getBody().getBALANCE().trim():"");//卡结存额
		 bean.setCardState(bck03845ResBean.getBody().getCARD_STAT()!=null && !"".equals(bck03845ResBean.getBody().getCARD_STAT().trim())?bck03845ResBean.getBody().getCARD_STAT().trim():"");//卡状态
		 bean.setAccState(bck03845ResBean.getBody().getACCT_STAT());//账户状态
		 bean.setCustNo(bck03845ResBean.getBody().getCUST_NO()!=null && !"".equals(bck03845ResBean.getBody().getCUST_NO().trim())?bck03845ResBean.getBody().getCUST_NO().trim():"");//客户号
		 bean.setDrawCond(bck03845ResBean.getBody().getPAY_COND()!=null && !"".equals(bck03845ResBean.getBody().getPAY_COND().trim())?bck03845ResBean.getBody().getPAY_COND().trim():"");//支付条件
		 bean.setOpenInst(bck03845ResBean.getBody().getOPEN_INST()!=null && !"".equals(bck03845ResBean.getBody().getOPEN_INST().trim())?bck03845ResBean.getBody().getOPEN_INST().trim():"");//开户机构
		 bean.setOpenRate(bck03845ResBean.getBody().getOPEN_RATE()!=null && !"".equals(bck03845ResBean.getBody().getOPEN_RATE().trim())?bck03845ResBean.getBody().getOPEN_RATE().trim():"");//开户利率
		 bean.setAccIdName(bck03845ResBean.getBody().getACCT_NAME()!=null && !"".equals(bck03845ResBean.getBody().getACCT_NAME().trim())?bck03845ResBean.getBody().getACCT_NAME().trim():"");//查询的客户名称
		 bean.setAccIdType(bck03845ResBean.getBody().getID_TYPE()!=null && !"".equals(bck03845ResBean.getBody().getID_TYPE().trim())?bck03845ResBean.getBody().getID_TYPE().trim():"");//查询的证件号类型
		 bean.setAccIdNo(bck03845ResBean.getBody().getID_NO()!=null && !"".equals(bck03845ResBean.getBody().getID_NO().trim())?bck03845ResBean.getBody().getID_NO().trim():"");//查询的证件号
		 bean.setIsAgreedDep(String.valueOf(bck03845ResBean.getBody().getACCT_STAT().charAt(18)));//约定转存标志
		 bean.setAccType("银行卡");//账户类型
		 bean.setProName("无");//产品名称
		 bean.setCertNo("无");//凭证号
			
		 params.put("resCode",SUCCESS);
		 return params;
	} 
	/**
	 * 卡账户信息查询1-前置03521
	 * @param bean
		 * @return
		 * @throws Exception
		 */
		public static Map card03521(LostPubBean bean)throws Exception{
			//获取数据
			Map<String, String> map = new HashMap<String, String>();
			map.put("ACCT_NO", bean.getCardNo());//卡号
			map.put("PASSWORD", "");//密码
			map.put("PIN_VAL_FLAG", "0");//验密标志(1-有密码 ，0-无密码)
			
			Map params = new HashMap();
			
			//连接socket
			String reqMsg=CreateXmlMsg.BCK_03521(map);
			AllPublicTransSocketClient socketClient =new AllPublicTransSocketClient();
			socketClient.createSocket();
			
			//发送请求报文、接收响应报文
			logger.info("请求报文："+reqMsg);
			String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
			logger.info("响应报文："+resMsg);
			
			//解析xml
			XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			reqXs.alias("Root", BCK03521ResBean.class);
			BCK03521ResBean bck03521ResBean = (BCK03521ResBean)reqXs.fromXML(resMsg);
			//获取返回报文错误码和错误信息
			String resCode = bck03521ResBean.getHeadBean().getResCode();
			String errMsg = bck03521ResBean.getHeadBean().getResMsg();
			logger.info("账户信息查询及密码验证resCode："+resCode+"resMsg:"+errMsg);
			if(bck03521ResBean == null){
				params.put("resCode","4444");
				params.put("errMsg","账户信息查询及密码验证接口失败");
				return params;
			}
			if(!SUCCESS.equals(resCode)){
				params.put("resCode",resCode);
				params.put("errMsg",errMsg);
				return params;
			}
			if("T".equals(bck03521ResBean.getBody().getACCT_STAT())){
				logger.info("停用");
				params.put("cardState1","停用");
			}else if("L".equals(bck03521ResBean.getBody().getACCT_STAT())){
				logger.info("银行卡证件到期中止 ");
				params.put("cardState1","证件到期中止");
			}else if("M".equals(bck03521ResBean.getBody().getACCT_STAT())){
				logger.info("银行卡中止且停用 ");
				params.put("cardState1","中止且停用");
			}else if("N".equals(bck03521ResBean.getBody().getACCT_STAT())){
				logger.info("银行卡中止且非柜面停用 ");
				params.put("cardState1","中止且非柜面停用");
			}
			
			params.put("resCode",SUCCESS);
			return params;		
		}
		
		/**
		 * 卡账户信息查询2-前置07601
		 * @param bean
		 * @return
		 * @throws Exception
		 */
		public static Map card07601(LostPubBean bean)throws Exception{
			//获取数据
			Map<String,String> map = new HashMap<String,String>();
			
			//校验银行卡时上送
			map.put("ACCT_NO", bean.getCardNo());//卡号
			map.put("SUB_ACCT_NO", "");//
			map.put("CHK_PIN", "0");//是否验密(0：不验密。1：验密)
			map.put("PASSWD", "");//
			map.put("CERT_TYPE", "");//凭证种类
			map.put("CERT_NO_ADD", "");//凭证号		
			
			Map params = new HashMap();
			
			//连接socket
			String reqMsg=CreateXmlMsg.BCK_07601(map);
			AllPublicTransSocketClient socketClient =new AllPublicTransSocketClient();
			socketClient.createSocket();
			
			//发送请求报文、接收响应报文
			logger.info("请求报文："+reqMsg);
			String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
			logger.info("响应报文："+resMsg);
			
			//解析xml
			XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			reqXs.alias("Root", BCK07601ResBean.class);
			BCK07601ResBean bck07601ResBean = (BCK07601ResBean)reqXs.fromXML(resMsg);
			//获取返回报文错误码和错误信息
			String resCode = bck07601ResBean.getHeadBean().getResCode();
			String errMsg = bck07601ResBean.getHeadBean().getResMsg();
			logger.info("账号信息综合查询resCode："+resCode+"resMsg:"+errMsg);
			if(bck07601ResBean == null){
				params.put("resCode","4444");
				params.put("errMsg","账号信息综合查询接口失败");
				return params;
			}
			if(!SUCCESS.equals(resCode)){
				params.put("resCode",resCode);
				params.put("errMsg",errMsg);
				return params;
			}
			
			if(bck07601ResBean.getBody().getAcctStat()!=null && !"".equals(bck07601ResBean.getBody().getAcctStat().trim())){
				
				String utilStat = bck07601ResBean.getBody().getAcctStat().trim();
				
				if("D".equals(String.valueOf(utilStat.charAt(0)))){
					logger.info("渠道限制");
					params.put("cardState2","渠道限制");
				}	
			}
			params.put("resCode",SUCCESS);
			return params;		
		}	 	
	
		
	/**
	  * 电子账号查询-前置07601
	  * @param bean
	  * @return
	  * @throws Exception
	  */
		public static Map eCard07601(LostPubBean bean)throws Exception{
			//获取数据
			Map<String,String> map = new HashMap<String,String>();
			
			//校验银行卡时上送
			map.put("ACCT_NO", bean.getCardNo());//电子账号
			map.put("SUB_ACCT_NO", "");//
			map.put("CHK_PIN", "0");//是否验密(0：不验密。1：验密)
			map.put("PASSWD", "");//
			map.put("CERT_TYPE", "");//凭证种类
			map.put("CERT_NO_ADD", "");//凭证号		
			
			Map params = new HashMap();
			
			//连接socket
			String reqMsg=CreateXmlMsg.BCK_07601(map);
			AllPublicTransSocketClient socketClient =new AllPublicTransSocketClient();
			socketClient.createSocket();
			
			//发送请求报文、接收响应报文
			logger.info("请求报文："+reqMsg);
			String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
			logger.info("响应报文："+resMsg);
			
			//解析xml
			XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			reqXs.alias("Root", BCK07601ResBean.class);
			BCK07601ResBean bck07601ResBean = (BCK07601ResBean)reqXs.fromXML(resMsg);
			//获取返回报文错误码和错误信息
			String resCode = bck07601ResBean.getHeadBean().getResCode();
			String errMsg = bck07601ResBean.getHeadBean().getResMsg();
			logger.info("账号信息综合查询resCode："+resCode+"resMsg:"+errMsg);
			if(bck07601ResBean == null){
				params.put("resCode","4444");
				params.put("errMsg","账号信息综合查询接口失败");
				return params;
			}
			if(!SUCCESS.equals(resCode)){
				params.put("resCode",resCode);
				params.put("errMsg",errMsg);
				return params;
			}
			bean.setAccState(bck07601ResBean.getBody().getAcctStat());
			params.put("resCode",SUCCESS);
			return params;		
		}	 		
		
	/**
	  * 子账户列表查询-【75109】前置03519
	  * @param bean
	  * @return
	  * @throws Exception
	  */
	public static Map inter03519(LostPubBean bean)throws Exception{
		//获取数据
		Map<String, String> map = new HashMap<String, String>();
		map.put("CARD_NO",bean.getCardNo());//卡号
					
		Map params = new HashMap();
					
		//连接socket
		String reqMsg=CreateXmlMsg.BCK_03519(map);
		AllPublicTransSocketClient socketClient =new AllPublicTransSocketClient();
		socketClient.createSocket();
			
		//发送请求报文、接收响应报文
		logger.info("请求报文："+reqMsg);
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
			
		//解析xml
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK03519ResBean.class);
		BCK03519ResBean bck03519ResBean = (BCK03519ResBean)reqXs.fromXML(resMsg);
			
		//获取返回报文错误码和错误信息
		String resCode = bck03519ResBean.getHeadBean().getResCode();
		String errMsg = bck03519ResBean.getHeadBean().getResMsg();
		logger.info("子账户列表查询:resCode03519:"+resCode+"resMsg03519:"+errMsg);
		if(bck03519ResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","子账户列表查询接口失败");
			return params;
		}
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		String fileName = bck03519ResBean.getBody().getFILE_NAME()!=null && !"".equals(bck03519ResBean.getBody().getFILE_NAME().trim())?bck03519ResBean.getBody().getFILE_NAME().trim():"";//文件路径
		List<SubAccInfoBean> list  = null;
		List<SubAccInfoBean> list1 = new ArrayList<SubAccInfoBean>();
		try {
			// 下载文件
			FtpFileUtils.downloadFileByFTPClient(Property.FTP_IP_DOWN, Integer.parseInt(Property.FTP_PORT_DOWN), Property.FTP_USER_DOWN, Property.FTP_PWD_DOWN, fileName, Property.FTP_LOCAL_PATH + fileName);
			list = TextFileReader.paddingList(Property.FTP_LOCAL_PATH + fileName, SubAccInfoBean.class);
		} catch (Exception e) {
			logger.error("子账户列表信息下载失败"+e);
			params.put("resCode","4444");
			params.put("errMsg","子账户列表信息下载失败");
			return params;
		}
		if(list.size()>0){
			for(SubAccInfoBean subBean:list){
				subBean.getOpenDate().trim().replaceAll("/","");
				if("0".equals(bean.getAllPubIsDeputy())){//本人
					//打印状态不为空
					if(subBean.getPrintState()!=null && !"".equals(subBean.getPrintState().trim())){
						//选择非销户、非积享存、非唐行宝、已打印的
						if(!"*".equals(subBean.getMark()) && !"Q".equals(subBean.getMark()) && !subBean.getProductCode().startsWith("JX") && !subBean.getProductCode().startsWith("TB") && "2".equals(subBean.getPrintState().trim())){							
							list1.add(subBean);
							logger.info("未销户且已打印且非积享存且非唐行宝的存单卡下子账号:"+subBean.getAccNo()+"-"+subBean.getSubAccNo());
						}else{
							logger.info("已销户或未打印或是积享存或是唐行宝的存单卡下子账号:"+subBean.getAccNo()+"-"+subBean.getSubAccNo());
						}
					}else{
						logger.info("没有打印状态的卡下子账号:"+subBean.getAccNo()+"-"+subBean.getSubAccNo());
					}
					
				}else{//代理人
					
					list1.add(subBean);
				}
			}
		}
		
		params.put("cardSubAcc", list1);
		params.put("resCode",SUCCESS);
		return params;
	} 
	
	/**
	  * 电子账户子账户列表查询【35109】（直连电子账户）-前置07819
	  * @param bean
	  * @return
	  * @throws Exception
	  */
	public static Map inter07819(LostPubBean bean)throws Exception{
		//获取数据
		Map<String, String> map = new HashMap<String, String>();
		map.put("CARD_NO","");//卡号
		map.put("CUST_NO",bean.getCustNo());//客户号
			
		Map params = new HashMap();
			
		//连接socket
		String reqMsg=CreateXmlMsg.BCK_07819(map);
		AllPublicTransSocketClient socketClient =new AllPublicTransSocketClient();
		socketClient.createSocket();
			
		//发送请求报文、接收响应报文
		logger.info("请求报文："+reqMsg);
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
			
		//解析xml
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK07819ResBean.class);
		BCK07819ResBean bck07819ResBean = (BCK07819ResBean)reqXs.fromXML(resMsg);
			
		//获取返回报文错误码和错误信息
		String resCode = bck07819ResBean.getHeadBean().getResCode();
		String errMsg = bck07819ResBean.getHeadBean().getResMsg();
		logger.info("电子子账户列表信息查询:resCode07819:"+resCode+"resMsg07819:"+errMsg);
		
		if(bck07819ResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","电子子账户列表信息查询接口失败");
			return params;
		}
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		String fileName = bck07819ResBean.getBody().getFILE_NAME()!=null && !"".equals(bck07819ResBean.getBody().getFILE_NAME().trim())?bck07819ResBean.getBody().getFILE_NAME().trim():"";//文件路径
		List<EAccInfoBean> list  = null;
		List<EAccInfoBean> list1 = new ArrayList<EAccInfoBean>(); 
		try {
			
			// 下载文件
			FtpFileUtils.downloadFileByFTPClient(Property.FTP_IP_DOWN, Integer.parseInt(Property.FTP_PORT_DOWN), Property.FTP_USER_DOWN, Property.FTP_PWD_DOWN, fileName, Property.FTP_LOCAL_PATH + fileName);
			list = TextFileReader.paddingList(Property.FTP_LOCAL_PATH + fileName, EAccInfoBean.class);
			
		} catch (Exception e) {
			
			logger.error("电子子账户列表信息下载失败"+e);
			params.put("resCode","4444");
			params.put("errMsg","电子子账户列表信息下载失败");
			return params;
		}
		
		if(list.size()>0){
				
			for(EAccInfoBean eBean:list){	
				eBean.geteOpenDate().trim().replaceAll("/","");
				
				if("0".equals(bean.getAllPubIsDeputy())){//本人
					//打印状态不为空
					if(eBean.getePrintState()!=null && !"".equals(eBean.getePrintState().trim())){
						//选择非销户、非积享存、已打印的
						if(!"*".equals(eBean.geteMark()) && !"Q".equals(eBean.geteMark()) && !eBean.geteProductCode().startsWith("JX") && "2".equals(eBean.getePrintState().trim())){
							list1.add(eBean);
							logger.info("未销户且已打印且非积享存的存单电子子账号:"+eBean.geteCardNo()+"-"+eBean.geteSubAccNo());
						}else{
							logger.info("已销户或未打印或是积享存的存单电子子账号:"+eBean.geteCardNo()+"-"+eBean.geteSubAccNo());
						}
					}else{
						logger.info("没有打印状态的电子子账号:"+eBean.geteCardNo()+"-"+eBean.geteSubAccNo());
					}
				
				}else{
					
					list1.add(eBean);
				}
			}
		}
		
		params.put("eSubAcc", list1);
		params.put("resCode",SUCCESS);
		return params;
	}
	
	/**
	  * 子账户列表查询-【75109】前置03519
	  * @param bean
	  * @return
	  * @throws Exception
	  */
	public static Map check03519(LostPubBean bean)throws Exception{
		//获取数据
		Map<String, String> map = new HashMap<String, String>();
		map.put("CARD_NO",bean.getCardNo());//卡号
					
		Map params = new HashMap();
					
		//连接socket
		String reqMsg=CreateXmlMsg.BCK_03519(map);
		AllPublicTransSocketClient socketClient =new AllPublicTransSocketClient();
		socketClient.createSocket();
			
		//发送请求报文、接收响应报文
		logger.info("请求报文："+reqMsg);
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
			
		//解析xml
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK03519ResBean.class);
		BCK03519ResBean bck03519ResBean = (BCK03519ResBean)reqXs.fromXML(resMsg);
			
		//获取返回报文错误码和错误信息
		String resCode = bck03519ResBean.getHeadBean().getResCode();
		String errMsg = bck03519ResBean.getHeadBean().getResMsg();
		logger.info("子账户列表查询:resCode03519:"+resCode+"resMsg03519:"+errMsg);
		if(bck03519ResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","子账户列表查询接口失败");
			return params;
		}
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		String fileName = bck03519ResBean.getBody().getFILE_NAME()!=null && !"".equals(bck03519ResBean.getBody().getFILE_NAME().trim())?bck03519ResBean.getBody().getFILE_NAME().trim():"";//文件路径
		List<SubAccInfoBean> list  = null;
		List<SubAccInfoBean> list1 = new ArrayList<SubAccInfoBean>();
		try {
			// 下载文件
			FtpFileUtils.downloadFileByFTPClient(Property.FTP_IP_DOWN, Integer.parseInt(Property.FTP_PORT_DOWN), Property.FTP_USER_DOWN, Property.FTP_PWD_DOWN, fileName, Property.FTP_LOCAL_PATH + fileName);
			list = TextFileReader.paddingList(Property.FTP_LOCAL_PATH + fileName, SubAccInfoBean.class);
		} catch (Exception e) {
			logger.error("子账户列表信息下载失败"+e);
			params.put("resCode","4444");
			params.put("errMsg","子账户列表信息下载失败");
			return params;
		}
		if(list.size()>0){
			for(SubAccInfoBean subBean:list){
				list1.add(subBean);
			}
		}
		
		params.put("cardSub", list1);
		params.put("resCode",SUCCESS);
		return params;
	} 
	
	/**
	  * 电子账户子账户列表查询【35109】（直连电子账户）-前置07819
	  * @param bean
	  * @return
	  * @throws Exception
	  */
	public static Map check07819(LostPubBean bean)throws Exception{
		//获取数据
		Map<String, String> map = new HashMap<String, String>();
		map.put("CARD_NO","");//卡号
		map.put("CUST_NO",bean.getCustNo());//客户号
			
		Map params = new HashMap();
			
		//连接socket
		String reqMsg=CreateXmlMsg.BCK_07819(map);
		AllPublicTransSocketClient socketClient =new AllPublicTransSocketClient();
		socketClient.createSocket();
			
		//发送请求报文、接收响应报文
		logger.info("请求报文："+reqMsg);
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
			
		//解析xml
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK07819ResBean.class);
		BCK07819ResBean bck07819ResBean = (BCK07819ResBean)reqXs.fromXML(resMsg);
			
		//获取返回报文错误码和错误信息
		String resCode = bck07819ResBean.getHeadBean().getResCode();
		String errMsg = bck07819ResBean.getHeadBean().getResMsg();
		logger.info("电子子账户列表信息查询:resCode07819:"+resCode+"resMsg07819:"+errMsg);
		
		if(bck07819ResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","电子子账户列表信息查询接口失败");
			return params;
		}
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		String fileName = bck07819ResBean.getBody().getFILE_NAME()!=null && !"".equals(bck07819ResBean.getBody().getFILE_NAME().trim())?bck07819ResBean.getBody().getFILE_NAME().trim():"";//文件路径
		List<EAccInfoBean> list  = null;
		List<EAccInfoBean> list1 = new ArrayList<EAccInfoBean>(); 
		try {
			
			// 下载文件
			FtpFileUtils.downloadFileByFTPClient(Property.FTP_IP_DOWN, Integer.parseInt(Property.FTP_PORT_DOWN), Property.FTP_USER_DOWN, Property.FTP_PWD_DOWN, fileName, Property.FTP_LOCAL_PATH + fileName);
			list = TextFileReader.paddingList(Property.FTP_LOCAL_PATH + fileName, EAccInfoBean.class);
			
		} catch (Exception e) {
			logger.error("电子子账户列表信息下载失败"+e);
			params.put("resCode","4444");
			params.put("errMsg","电子子账户列表信息下载失败");
			return params;
		}
		
		if(list.size()>0){
			for(EAccInfoBean eBean:list){	
				list1.add(eBean);
			}
		}
		
		params.put("eSubAcc", list1);
		params.put("resCode",SUCCESS);
		return params;
	}
	
	/**
	 * 柜员认证方式查询
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public static Map inter07659(LostPubBean bean)throws Exception{
		//获取数据
		Map<String, String> map = new HashMap<String, String>();
		map.put("QRY_TELLER_NO", bean.getAllPubFristSupTellerNo());//QRY_TELLER_NO	查询柜员号
		Map params = new HashMap();
		String reqMsg=CreateXmlMsg.BCK_07659(map);
		AllPublicTransSocketClient socketClient =new AllPublicTransSocketClient();
		socketClient.createSocket();
		logger.info("请求报文："+reqMsg);
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK07659ResBean.class);
		BCK07659ResBean bck07659ResBean = (BCK07659ResBean)reqXs.fromXML(resMsg);
		//获取返回报文错误码和错误信息
		String resCode = bck07659ResBean.getHeadBean().getResCode();
		String errMsg = bck07659ResBean.getHeadBean().getResMsg();
		logger.info("柜员认证方式查询resCode:"+resCode+"resMsg:"+resMsg);
		if(bck07659ResBean == null){
			params.put("resCode",resCode);
			params.put("errMsg","调用接口失败");
			return params;
		}
		if("44444".equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg","连接前置失败");
			return params;
		}
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		bean.setAllPubFristCheckMod(bck07659ResBean.getBody().getCHECK_MOD());
		logger.info("查询成功后信息："+bean);
		params.put("resCode",SUCCESS);
		return params;
	} 
	 
	/**
	 * 柜员授权
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public static Map inter07660(LostPubBean bean)throws Exception{
		//获取数据
		Map<String, String> map = new HashMap<String, String>();
		map.put("SUP_TELLER_NO", bean.getAllPubFristSupTellerNo());//SUP_TELLER_NO	查询柜员号
		map.put("SUP_TELLER_PWD", bean.getAllPubFristPass());//SUP_TELLER_PWD	授权密码
		map.put("FIN_PRI_LEN", bean.getAllPubFristFingerLenght());//FIN_PRI_LEN	指纹长度
		map.put("FIN_PRI_VAL", bean.getAllPubFristFingerVal());//FIN_PRI_VAL	指纹值
		Map params = new HashMap();
		String reqMsg=CreateXmlMsg.BCK_07660(map);
		AllPublicTransSocketClient socketClient =new AllPublicTransSocketClient();
		socketClient.createSocket();
		logger.info("请求报文："+reqMsg);
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK07660ResBean.class);
		BCK07660ResBean bck07660ResBean = (BCK07660ResBean)reqXs.fromXML(resMsg);
		//获取返回报文错误码和错误信息
		String resCode = bck07660ResBean.getHeadBean().getResCode();
		String errMsg = bck07660ResBean.getHeadBean().getResMsg();
		logger.info("柜员授权resCode："+resCode+"resMsg:"+resMsg);
		if(bck07660ResBean == null){
			params.put("resCode",resCode);
			params.put("errMsg","调用接口07660失败");
			return params;
		}
		if(!SUCCESS.equals(resCode)){
			//700043密码错误，700099指纹错误，700018授权员权限不够
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		logger.info("操作成功后信息："+bean);
		params.put("resCode",SUCCESS);
		return params;
	} 
	
	/**
	 * 日切接口（核心接口待完善）【00002】-前置【08169】
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public static Map inter08169(LostPubBean bean)throws Exception{
		//获取数据
		Map<String,String> map = new HashMap<String,String>();
		Map params = new HashMap();
		String reqMsg=CreateXmlMsg.BCK_08169(map);
		
		//连接socket
		AllPublicTransSocketClient socketClient =new AllPublicTransSocketClient();
		socketClient.createSocket();
		
		//发送请求报文、接收响应报文
		logger.info("请求报文："+reqMsg);
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		   
		//解析xml
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK08169ResBean.class);
		BCK08169ResBean bck08169ResBean = (BCK08169ResBean)reqXs.fromXML(resMsg);
		
		 //获取返回报文错误码和错误信息
		String resCode = bck08169ResBean.getHeadBean().getResCode();
		String errMsg = bck08169ResBean.getHeadBean().getResMsg();
		logger.info("查询客户号：resCode08169:"+resCode+"resMsg08169"+errMsg);
		
		if(bck08169ResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","查询核心日期接口调用失败");
			return params;
		}
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		
		String fileName = bck08169ResBean.getBody().getFILE_NAME()!=null && !"".equals(bck08169ResBean.getBody().getFILE_NAME().trim())?bck08169ResBean.getBody().getFILE_NAME().trim():"";//文件路径
		if("".equals(fileName)){
			 logger.error("未返回核心日期文件下载路径");
			 params.put("resCode","4444");
			 params.put("errMsg","查询核心日期失败");
			 return params;
		}
		
		String result = null;
		try {
			
			FtpFileUtils.downloadFileByFTPClient(Property.FTP_IP_DOWN, Integer.parseInt(Property.FTP_PORT_DOWN), Property.FTP_USER_DOWN, Property.FTP_PWD_DOWN, fileName, Property.FTP_LOCAL_PATH + fileName);
			result = TextFileReader.paddingStr(Property.FTP_LOCAL_PATH + fileName);
			logger.info(result);
			
		} catch (Exception e) {
			
			 logger.error("下载核心日期文件失败"+e);
			 params.put("resCode","4444");
			 params.put("errMsg","查询核心日期失败");
			 return params;
		}
		
		if(result!=null && !"".equals(result.trim())){
			bean.setAllPubSvrDate(result);
		}else{
			logger.error("文件内容为空：");
			params.put("resCode","4444");
			params.put("errMsg","查询核心日期文件返回内容为空");
			return params;
		}
				
		params.put("resCode", SUCCESS);
		return params;
	} 
	
	/**
	 * 账户信息查询-前置07601
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public static Map check07601(LostPubBean bean)throws Exception{
		//获取数据
		Map<String,String> map = new HashMap<String,String>();
		
		//校验银行卡时上送
		map.put("ACCT_NO", bean.getCardSubNo());//账号（卡号/存单号/存折号）
		map.put("SUB_ACCT_NO", "");//
		map.put("CHK_PIN", "0");//是否验密(0：不验密。1：验密)
		map.put("PASSWD", "");//密码
		map.put("CERT_TYPE", "");//凭证种类
		map.put("CERT_NO_ADD", "");//凭证号		
		
		Map params = new HashMap();
		
		//连接socket
		String reqMsg=CreateXmlMsg.BCK_07601(map);
		AllPublicTransSocketClient socketClient =new AllPublicTransSocketClient();
		socketClient.createSocket();
		
		//发送请求报文、接收响应报文
		logger.info("请求报文："+reqMsg);
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		
		//解析xml
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK07601ResBean.class);
		BCK07601ResBean bck07601ResBean = (BCK07601ResBean)reqXs.fromXML(resMsg);
		//获取返回报文错误码和错误信息
		String resCode = bck07601ResBean.getHeadBean().getResCode();
		String errMsg = bck07601ResBean.getHeadBean().getResMsg();
		logger.info("账号信息综合查询resCode："+resCode+"resMsg:"+errMsg);
		if(bck07601ResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","账号信息综合查询接口失败");
			return params;
		}
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		
		if(bck07601ResBean.getBody().getAcctStat()!=null && !"".equals(bck07601ResBean.getBody().getAcctStat().trim())){
			String utilStat = bck07601ResBean.getBody().getAcctStat();
			params.put("accStatus", utilStat);
		}
		params.put("proName", bck07601ResBean.getBody().getProdName()!=null && !"".equals(bck07601ResBean.getBody().getProdName())?bck07601ResBean.getBody().getProdName():"");//产品名称
		params.put("drawCond", bck07601ResBean.getBody().getDrawCond()!=null && !"".equals(bck07601ResBean.getBody().getDrawCond())?bck07601ResBean.getBody().getDrawCond():"");//支付条件
		params.put("endAmt", bck07601ResBean.getBody().getEndAmt()!=null && !"".equals(bck07601ResBean.getBody().getEndAmt())?bck07601ResBean.getBody().getEndAmt():"");//存折余额（挂失销户用）
		params.put("totalAmt", bck07601ResBean.getBody().getTotalAmt()!=null && !"".equals(bck07601ResBean.getBody().getTotalAmt())?bck07601ResBean.getBody().getTotalAmt():"");//存折余额（挂失销户用）
		params.put("openChanel", bck07601ResBean.getBody().getOpenChnal()!=null && !"".equals(bck07601ResBean.getBody().getOpenChnal())?bck07601ResBean.getBody().getOpenChnal():"");//开户渠道
		params.put("preDate", bck07601ResBean.getBody().getPreDate()!=null && !"".equals(bck07601ResBean.getBody().getPreDate())?bck07601ResBean.getBody().getPreDate():"");//预约日期
		params.put("startDate", bck07601ResBean.getBody().getStartIntDate()!=null && !"".equals(bck07601ResBean.getBody().getStartIntDate())?bck07601ResBean.getBody().getStartIntDate():"");//起息日期
		params.put("resCode",SUCCESS);
		return params;		
	}
	
	/**
	 * 卡账户信息查询2-前置07601
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public static Map inter07601(LostPubBean bean)throws Exception{
		//获取数据
		Map<String,String> map = new HashMap<String,String>();
		
		//校验银行卡时上送
		if("0".equals(bean.getIsPinPass())){
			map.put("ACCT_NO", bean.getAllPubAccNo());//账号（卡号/存单号/存折号）
		}else{
			map.put("ACCT_NO", bean.getAllPubPassAccNo());//账号（卡号/存单号/存折号）
		}
		map.put("SUB_ACCT_NO", "");//
		map.put("CHK_PIN", bean.getIsPinPass());//是否验密(0：不验密。1：验密)
		if(bean.getIsPinPass()!=null && "0".equals(bean.getIsPinPass())){
			map.put("PASSWD", "");//不验密，则不输入密码
		}else{
			map.put("PASSWD", bean.getAllPubAccPwd());//密码
		}
		map.put("CERT_TYPE", "");//凭证种类
		map.put("CERT_NO_ADD", "");//凭证号		
		
		Map params = new HashMap();
		
		//连接socket
		String reqMsg=CreateXmlMsg.BCK_07601(map);
		AllPublicTransSocketClient socketClient =new AllPublicTransSocketClient();
		socketClient.createSocket();
		
		//发送请求报文、接收响应报文
		logger.info("请求报文："+reqMsg);
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		
		//解析xml
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK07601ResBean.class);
		BCK07601ResBean bck07601ResBean = (BCK07601ResBean)reqXs.fromXML(resMsg);
		//获取返回报文错误码和错误信息
		String resCode = bck07601ResBean.getHeadBean().getResCode();
		String errMsg = bck07601ResBean.getHeadBean().getResMsg();
		logger.info("账号信息综合查询resCode："+resCode+"resMsg:"+errMsg);
		if(bck07601ResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","账号信息综合查询接口失败");
			return params;
		}
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		
		bean.setOpenDate(bck07601ResBean.getBody().getOpenDate()!=null && !"".equals(bck07601ResBean.getBody().getOpenDate().trim())?bck07601ResBean.getBody().getOpenDate().trim():"");//开户日期
		bean.setEndDate(bck07601ResBean.getBody().getEndDate()!=null && !"".equals(bck07601ResBean.getBody().getEndDate().trim())?bck07601ResBean.getBody().getEndDate().trim():"");//到期日
		bean.setOpenAmt(bck07601ResBean.getBody().getOpenAmt()!=null && !"".equals(bck07601ResBean.getBody().getOpenAmt().trim())?bck07601ResBean.getBody().getOpenAmt().trim():"");//开户金额
		bean.setDepTerm(bck07601ResBean.getBody().getDepTerm()!=null && !"".equals(bck07601ResBean.getBody().getDepTerm().trim())?bck07601ResBean.getBody().getDepTerm().trim():"");//存期
		bean.setEndAmt(bck07601ResBean.getBody().getEndAmt()!=null && !"".equals(bck07601ResBean.getBody().getEndAmt().trim())?bck07601ResBean.getBody().getEndAmt().trim():"");//结存额
		bean.setTotalAmt(bck07601ResBean.getBody().getTotalAmt()!=null && !"".equals(bck07601ResBean.getBody().getTotalAmt().trim())?bck07601ResBean.getBody().getTotalAmt().trim():"");//存折余额（挂失销户用）
		bean.setCertNo(bck07601ResBean.getBody().getCertNo()!=null && !"".equals(bck07601ResBean.getBody().getCertNo().trim())?bck07601ResBean.getBody().getCertNo().trim():"");//凭证号
		bean.setProName(bck07601ResBean.getBody().getProdName()!=null && !"".equals(bck07601ResBean.getBody().getProdName().trim())?bck07601ResBean.getBody().getProdName().trim():"");//产品名称
		bean.setDrawCond(bck07601ResBean.getBody().getDrawCond()!=null && !"".equals(bck07601ResBean.getBody().getDrawCond().trim())?bck07601ResBean.getBody().getDrawCond().trim():"");//支付条件
		bean.setCustName(bck07601ResBean.getBody().getCustName()!=null && !"".equals(bck07601ResBean.getBody().getCustName().trim())?bck07601ResBean.getBody().getCustName().trim():"");//客户名称
		bean.setAccIdName(bck07601ResBean.getBody().getCustName()!=null && !"".equals(bck07601ResBean.getBody().getCustName().trim())?bck07601ResBean.getBody().getCustName().trim():"");//客户名称
		bean.setAccIdType(bck07601ResBean.getBody().getIdType()!=null && !"".equals(bck07601ResBean.getBody().getIdType().trim())?bck07601ResBean.getBody().getIdType().trim():"");//证件类型
		bean.setAccIdNo(bck07601ResBean.getBody().getIdNo()!=null && !"".equals(bck07601ResBean.getBody().getIdNo().trim())?bck07601ResBean.getBody().getIdNo().trim():"");//证件号码
		bean.setProCode(bck07601ResBean.getBody().getProdCode()!=null && !"".equals(bck07601ResBean.getBody().getProdCode().trim())?bck07601ResBean.getBody().getProdCode().trim():"");//产品代码
		bean.setAccKind(bck07601ResBean.getBody().getAcctType()!=null && !"".equals(bck07601ResBean.getBody().getAcctType().trim())?bck07601ResBean.getBody().getAcctType().trim():"");//账户种类
		bean.setOpenRate(bck07601ResBean.getBody().getRate()!=null && !"".equals(bck07601ResBean.getBody().getRate().trim())?bck07601ResBean.getBody().getRate().trim():"");//开户利率
		bean.setPreDate(bck07601ResBean.getBody().getPreDate()!=null && !"".equals(bck07601ResBean.getBody().getPreDate().trim())?bck07601ResBean.getBody().getPreDate().trim():"");//预约日期
		bean.setAccState(bck07601ResBean.getBody().getAcctStat());//账户状态
		bean.setCustNo(bck07601ResBean.getBody().getCustNo()!=null &&!"".equals(bck07601ResBean.getBody().getCustNo().trim())?bck07601ResBean.getBody().getCustNo().trim():"");//客户号
		params.put("resCode",SUCCESS);
		return params;		
	}
	
	/**
	 * 客户基本信息查询
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public static Map inter04422(LostPubBean bean)throws Exception{
		//获取数据
		Map<String, String> map = new HashMap<String, String>();
		map.put("RESOLVE_TYPE", "2");//识别方式
		map.put("ECIF_CUST_NO", "");//客户编号
		map.put("PARTY_NAME", bean.getAllPubCheckIdCardName());//客户姓名
		map.put("CERT_TYPE", "10100");//证件类型
		map.put("CERT_NO", bean.getAllPubCheckIdCardNo());//证件号码
		map.put("ACCT_NO", "");//客户识别账号
		
		Map params = new HashMap();
		String reqMsg=CreateXmlMsg.BCK_04422(map);
		AllPublicTransSocketClient socketClient =new AllPublicTransSocketClient();
		socketClient.createSocket();
		logger.info("请求报文："+reqMsg);
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK04422ResBean.class);
		BCK04422ResBean bck04422ResBean = (BCK04422ResBean)reqXs.fromXML(resMsg);
		if(bck04422ResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","调用接口04422失败");
			return params;
		}
		//获取返回报文错误码和错误信息
		String resCode = bck04422ResBean.getHeadBean().getResCode();
		String errMsg = bck04422ResBean.getHeadBean().getResMsg();
		logger.info("客户基本信息查询resCode:"+resCode+"resMsg:"+errMsg);
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		
		params.put("phone", bck04422ResBean.getBody().getPHONE_NO()!=null&& !"".equals(bck04422ResBean.getBody().getPHONE_NO().trim())?bck04422ResBean.getBody().getPHONE_NO():"");//查询的固话
		params.put("Mphone", bck04422ResBean.getBody().getMOBILE_PHONE()!=null&& !"".equals(bck04422ResBean.getBody().getMOBILE_PHONE().trim())?bck04422ResBean.getBody().getMOBILE_PHONE():"");//查询的手机号
		params.put("address", bck04422ResBean.getBody().getADDR_LINE()!=null&& !"".equals(bck04422ResBean.getBody().getADDR_LINE().trim())?bck04422ResBean.getBody().getADDR_LINE():"");//客户地址
		params.put("resCode",SUCCESS);
		return params;
	} 
	
	
	/**
	 * 卡账户信息查询-前置03521
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public static Map inter03521(LostPubBean bean)throws Exception{
		//获取数据
		Map<String, String> map = new HashMap<String, String>();
		map.put("ACCT_NO", bean.getCardNo());//卡账号
		map.put("PASSWORD", "");//密码
		map.put("PIN_VAL_FLAG", "0");//验密标志
		
		Map params = new HashMap();
		
		//连接socket
		String reqMsg=CreateXmlMsg.BCK_03521(map);
		AllPublicTransSocketClient socketClient =new AllPublicTransSocketClient();
		socketClient.createSocket();
		
		//发送请求报文、接收响应报文
		logger.info("请求报文："+reqMsg);
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		
		//解析xml
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK03521ResBean.class);
		BCK03521ResBean bck03521ResBean = (BCK03521ResBean)reqXs.fromXML(resMsg);
		//获取返回报文错误码和错误信息
		String resCode = bck03521ResBean.getHeadBean().getResCode();
		String errMsg = bck03521ResBean.getHeadBean().getResMsg();
		logger.info("卡账户信息查询及密码验证resCode："+resCode+"resMsg:"+resMsg);
		if(bck03521ResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","卡账户信息查询及密码验证接口失败");
			return params;
		}
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		bean.setAccState(bck03521ResBean.getBody().getACCT_STAT());//卡账户状态
		params.put("resCode",SUCCESS);
		return params;		
	}
	
	/**
	 * 理财客户销户可销户状态查询【520104】-前置【08199】
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public static Map check08199(LostPubBean bean)throws Exception{
		//获取数据
		Map<String, String> map = new HashMap<String, String>();
		map.put("ACCT_NO", bean.getAllPubAccNo());//账号
		
		Map params = new HashMap();
		String reqMsg=CreateXmlMsg.BCK_08199(map);
		AllPublicTransSocketClient socketClient =new AllPublicTransSocketClient();
		socketClient.createSocket();
		logger.info("请求报文："+reqMsg);
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK08199ResBean.class);
		BCK08199ResBean bck08199ResBean = (BCK08199ResBean)reqXs.fromXML(resMsg);
		if(bck08199ResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","调用接口08199失败");
			return params;
		}
		//获取返回报文错误码和错误信息
		String resCode = bck08199ResBean.getHeadBean().getResCode();
		String errMsg = bck08199ResBean.getHeadBean().getResMsg();
		logger.info("理财客户销户可销户状态查询resCode:"+resCode+"resMsg:"+errMsg);
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		
		bean.setChkFlag(bck08199ResBean.getBody().getCHK_FLAG()!=null && !"".equals(bck08199ResBean.getBody().getCHK_FLAG().trim())?bck08199ResBean.getBody().getCHK_FLAG().trim():"");//可销户标志
		params.put("resCode",SUCCESS);
		return params;
	} 
	
	/**
	 * 帐户/卡户是否关联贷款信息查询【02945】-前置【08240】
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public static Map check08240(LostPubBean bean)throws Exception{
		//获取数据
		Map<String, String> map = new HashMap<String, String>();
		map.put("ACCT_NO", bean.getAllPubAccNo());//账号
		
		Map params = new HashMap();
		String reqMsg=CreateXmlMsg.BCK_08240(map);
		AllPublicTransSocketClient socketClient =new AllPublicTransSocketClient();
		socketClient.createSocket();
		logger.info("请求报文："+reqMsg);
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK08240ResBean.class);
		BCK08240ResBean bck08240ResBean = (BCK08240ResBean)reqXs.fromXML(resMsg);
		if(bck08240ResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","调用接口08240失败");
			return params;
		}
		//获取返回报文错误码和错误信息
		String resCode = bck08240ResBean.getHeadBean().getResCode();
		String errMsg = bck08240ResBean.getHeadBean().getResMsg();
		logger.info("帐户/卡户是否关联贷款信息查询resCode:"+resCode+"resMsg:"+errMsg);
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		
		bean.setAmtFlag(bck08240ResBean.getBody().getFLAG()!=null && !"".equals(bck08240ResBean.getBody().getFLAG().trim())?bck08240ResBean.getBody().getFLAG().trim():"");//是否有关联贷款未结清
		params.put("resCode",SUCCESS);
		return params;
	} 
	
	/**
	 * 产品利率信息查询
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public static Map inter02864(LostPubBean bean)throws Exception{
		//获取数据
		AccLostAndReturnInfoBean Abean = (AccLostAndReturnInfoBean)bean.getAccMap().get("resAccInfo");
		Map<String,String> map = new HashMap<String,String>();
		String startDate=null;
		String openDateStr = Abean.getStartRateDate().trim();
		Pattern p=Pattern.compile("^[0-9]*$");
		Matcher ma=p.matcher(openDateStr);
		boolean pMa= ma.matches();
		if(pMa && openDateStr.length()==8){
			startDate=openDateStr.trim();
		}else{
			startDate=openDateStr.substring(0,4)+openDateStr.substring(5,7)+openDateStr.substring(8);
		}
		map.put("CUST_NO", bean.getCustNo());
		map.put("FLOAT_FLAG","");//非必输1-浮动，非1-不浮动
		map.put("CHL_ID",bean.getOpenChannel());//渠道模块标识
		map.put("PROD_CODE", bean.getProCode());// 产品名称
		map.put("RATE_DATE", startDate);// 利率日期
		if(bean.getAllPubAccNo()!=null && bean.getAllPubAccNo().contains("-")){
			map.put("ACCT_NO", bean.getAllPubPassAccNo());//账号
			map.put("SUB_ACCT_NO", bean.getAllPubAccNo().substring(bean.getAllPubAccNo().indexOf("-")+1));//子账号
		}else{
			map.put("ACCT_NO", bean.getAllPubPassAccNo());//账号
			map.put("SUB_ACCT_NO", "");//子账号
		}
		
		Map params = new HashMap();
		String reqMsg=CreateXmlMsg.BCK_02864(map);
		AllPublicTransSocketClient socketClient =new AllPublicTransSocketClient();
		socketClient.createSocket();
		logger.info("请求报文："+reqMsg);
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK02864ResBean.class);
		BCK02864ResBean bck02864ResBean = (BCK02864ResBean)reqXs.fromXML(resMsg);
		String resCode02864 = bck02864ResBean.getHeadBean().getResCode();
		String resMsg02864 = bck02864ResBean.getHeadBean().getResMsg();
		logger.info("产品利率信息查询：resCode02864:"+resCode02864+"resMsg02864"+resMsg02864);
		if(!"000000".equals(resCode02864)){
			params.put("resCode",resCode02864);
			params.put("errMsg",resMsg02864);
			return params;
		}
		String fileName = bck02864ResBean.getBody().getFILE_NAME();
		FtpFileUtils.downloadFileByFTPClient(Property.FTP_IP_DOWN, Integer.parseInt(Property.FTP_PORT_DOWN), Property.FTP_USER_DOWN, Property.FTP_PWD_DOWN, fileName, Property.FTP_LOCAL_PATH + fileName);
		List<SearchProducRateInfo02864> list = TextFileReader.paddingList(Property.FTP_LOCAL_PATH + fileName, SearchProducRateInfo02864.class);
		logger.info(list);
		params.put("rateList", list);
		params.put("float", bck02864ResBean.getBody().getFLOAT_SUM());//浮动
		params.put("resCode", SUCCESS);
		return params;
	} 
	
	
	/**
	 * 账户限额查询【02879】-前置02781
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public static Map inter02781(LostPubBean bean)throws Exception{
		//获取数据
		Map<String,String> map = new HashMap<String,String>();
		map.put("DB_CR_FLAG", "1");//借贷标志
		map.put("ACCT_NO",bean.getAllPubAccNo());//卡号
		map.put("OPPO_ACCT_NO",bean.getSelectCardNo());//对方账号
		map.put("TRAN_AMT",bean.getEndAmt());//发生额
		
		Map<String,String> params = new HashMap<String,String>();
		
		String reqMsg=CreateXmlMsg.BCK_02781(map);
		
		AllPublicTransSocketClient socketClient =new AllPublicTransSocketClient();
		socketClient.createSocket();
		
		logger.info("请求报文："+reqMsg);
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK02781ResBean.class);
		BCK02781ResBean bck02781ResBean = (BCK02781ResBean)reqXs.fromXML(resMsg);
		if(bck02781ResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","调用接口02781失败");
			return params;
		}
		String resCode = bck02781ResBean.getHeadBean().getResCode();
		String errMsg = bck02781ResBean.getHeadBean().getResMsg();
		logger.info("账户限额查询-resCode："+resCode+"账户限额查询-resMsg："+resMsg);
		
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		
		params.put("resCode",resCode);
		return params;
	} 
	
	/**更新凭证信息
	 * @throws IOException 
	 * @throws UnknownHostException 
	 * @throws Exception */
	public static Map<String,String> Tms0006(Map<String,String> map) throws Exception {
		
		AllPublicTransSocketClient socketClient =new AllPublicTransSocketClient();
		socketClient.createSocket();
		String tms0006 = CreateXmlMsg.Tms0006(map);
		Map<String,String> params = new HashMap<String,String>();
		// 发送请求  响应
		String ropMsg = socketClient.sendMesg(tms0006,"UTF-8");
		if(null == ropMsg ){
			logger.error("更新凭证号失败");
			params.put("resCode","4444");
			params.put("errMsg","更新凭证号失败");
			return params;
		}
		logger.info("凭证更新结果:" + ropMsg);
		
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", Tms0005ResBean.class);
		reqXs.alias("Head", InResBean.class);
		reqXs.alias("Body", Tms0005ResBodyBean.class);
		Tms0005ResBean tms0005ResBean = (Tms0005ResBean)reqXs.fromXML(ropMsg);
		String resCode = tms0005ResBean.getHeadBean().getResCode();
		String errMsg = tms0005ResBean.getHeadBean().getResMsg();
		logger.info("更新凭证信息resCode:"+resCode+"resMsg:"+errMsg);
		if(!SUCCESS.equals(resCode)){
			logger.error("更新凭证号失败");
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		logger.info("成功后信息："+tms0005ResBean);
		params.put("resCode",SUCCESS);
		return params;	
		
	}
	/**
	 * 一本通账号查询【20098】-前置【08176】
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public static Map inter08176(LostPubBean bean)throws Exception{
		//获取数据
		Map<String, String> map = new HashMap<String, String>();
		map.put("REAL_ACCT_NO", bean.getAllPubAccNo());//账号
		
		Map params = new HashMap();
		String reqMsg=CreateXmlMsg.BCK_08176(map);
		AllPublicTransSocketClient socketClient =new AllPublicTransSocketClient();
		socketClient.createSocket();
		logger.info("请求报文："+reqMsg);
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK08176ResBean.class);
		BCK08176ResBean bck08176ResBean = (BCK08176ResBean)reqXs.fromXML(resMsg);
		if(bck08176ResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","调用接口08176失败");
			return params;
		}
		//获取返回报文错误码和错误信息
		String resCode = bck08176ResBean.getHeadBean().getResCode();
		String errMsg = bck08176ResBean.getHeadBean().getResMsg();
		logger.info("一本通账号查询resCode:"+resCode+"resMsg:"+errMsg);
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		
		params.put("SVR_JRNL_NO_R", bck08176ResBean.getBody().getACCT_NO()!=null && !"".equals(bck08176ResBean.getBody().getACCT_NO().trim())?bck08176ResBean.getBody().getACCT_NO().trim():"");//返回外部账号
		params.put("MX_STAT", bck08176ResBean.getBody().getMX_STAT()!=null && !"".equals(bck08176ResBean.getBody().getMX_STAT().trim())?bck08176ResBean.getBody().getMX_STAT().trim():"");//明细状态
		params.put("resCode",SUCCESS);
		return params;
	} 
	
	/**
	 * 挂失销户限额查询（20097）-前置【08177】(核心)
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public static Map inter08177(LostPubBean bean)throws Exception{
		
		//获取数据
		Map<String, String> map = new HashMap<String, String>();
		map.put("CHL_NO", "0035");//渠道号
		map.put("ACCT_NO", bean.getAllPubAccNo());//账号
		
		Map params = new HashMap();
		String reqMsg=CreateXmlMsg.BCK_08177(map);
		AllPublicTransSocketClient socketClient =new AllPublicTransSocketClient();
		socketClient.createSocket();
		logger.info("请求报文："+reqMsg);
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK08177ResBean.class);
		BCK08177ResBean bck08177ResBean = (BCK08177ResBean)reqXs.fromXML(resMsg);
		if(bck08177ResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","调用接口08177失败");
			return params;
		}
		//获取返回报文错误码和错误信息
		String resCode = bck08177ResBean.getHeadBean().getResCode();
		String errMsg = bck08177ResBean.getHeadBean().getResMsg();
		logger.info("核心限额查询resCode:"+resCode+"resMsg:"+errMsg);
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		
		bean.setLimitAmt(bck08177ResBean.getBody().getLIMIT_AMT()!=null && !"".equals(bck08177ResBean.getBody().getLIMIT_AMT().trim())?bck08177ResBean.getBody().getLIMIT_AMT().trim():"");//限额
		params.put("resCode",SUCCESS);
		return params;	
	}
	
	/**
	 * 挂失销户限额查询（75209）-前置【08178】(卡系统)
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public static Map inter08178(LostPubBean bean)throws Exception{

		//获取数据
		Map<String, String> map = new HashMap<String, String>();
		map.put("CHL_NO", "08");//渠道号
		map.put("ACCT_NO", bean.getAllPubAccNo());//账号
		
		Map params = new HashMap();
		String reqMsg=CreateXmlMsg.BCK_08178(map);
		AllPublicTransSocketClient socketClient =new AllPublicTransSocketClient();
		socketClient.createSocket();
		logger.info("请求报文："+reqMsg);
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK08178ResBean.class);
		BCK08178ResBean bck08178ResBean = (BCK08178ResBean)reqXs.fromXML(resMsg);
		if(bck08178ResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","调用接口08178失败");
			return params;
		}
		//获取返回报文错误码和错误信息
		String resCode = bck08178ResBean.getHeadBean().getResCode();
		String errMsg = bck08178ResBean.getHeadBean().getResMsg();
		logger.info("卡限额查询resCode:"+resCode+"resMsg:"+errMsg);
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		
		bean.setLimitAmt(bck08178ResBean.getBody().getLIMIT_AMT()!=null && !"".equals(bck08178ResBean.getBody().getLIMIT_AMT().trim())?bck08178ResBean.getBody().getLIMIT_AMT().trim():"");//限额
		params.put("resCode",SUCCESS);
		return params;			
	}
	
	/**
	 * 代理人身份信息核对
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public static Map inter08021(LostPubBean bean)throws Exception{
		//获取数据
		Map<String, String> map = new HashMap<String, String>();
		map.put("NAME", bean.getAllPubAgentIdCardName());//代理人姓名
		map.put("ID_NO", bean.getAllPubAgentIDNo());//代理身份证号
		
		Map params = new HashMap();
		String reqMsg=CreateXmlMsg.BCK_08021(map);
		AllPublicTransSocketClient socketClient =new AllPublicTransSocketClient();
		socketClient.createSocket();
		logger.info("请求报文："+reqMsg);
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK08021ResBean.class);
		BCK08021ResBean bck08021ResBean = (BCK08021ResBean)reqXs.fromXML(resMsg);
		//获取返回报文错误码和错误信息
		String resCode = bck08021ResBean.getHeadBean().getResCode();
		String errMsg = bck08021ResBean.getHeadBean().getResMsg();
		logger.info("柜员授权resCode："+resCode+"resMsg:"+resMsg);
		if(bck08021ResBean == null){
			params.put("resCode",resCode);
			params.put("errMsg","调用接口08021失败");
			return params;
		}
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		logger.info("操作成功后信息："+bean);
		params.put("checkResult",bck08021ResBean.getBody().getCHECK_RESULT());
		params.put("resCode",SUCCESS);
		return params;
	} 
	
//	/**
//	 * 取消约定转存【75032】前置【08189】   ----已废弃，未使用
//	 * @param bean
//	 * @return
//	 * @throws Exception
//	 */
//	public static Map inter08189(LostPubBean bean)throws Exception{
//		//获取数据
//		Map<String, String> map = new HashMap<String, String>();
//		map.put("CARD_NO", bean.getAllPubAccNo());//卡号
//		map.put("CARD_PWD", bean.getAllPubAccPwd());//密码
//		map.put("CERT_TYPE", "10100");//证件类别
//		map.put("CERT_NO", bean.getAllPubIDNo());//证件号
//		map.put("HAV_AGENT_FLAG", "1");//本人
//		map.put("AGENT_CUST_NAME", "");//代理人客户姓名
//		map.put("AGENT_SEX", "");//代理人性别
//		map.put("AGENT_ID_TYPE", "10100");//代理人证件类别
//		map.put("AGENT_ID_NO", "");//代理人证件号码
//		map.put("AGENT_ISSUE_DATE", "");//代理人签发日期
//		map.put("AGENT_ISSUE_INST", "");//代理人到期日期
//		map.put("AGENT_DUE_DATE", "");//代理人签发机关
//		map.put("AGENT_NATION", "");//代理人国籍
//		map.put("AGENT_OCCUPATION", "");//代理人职业
//		map.put("AGENT_REGIS_PER_RES", "");//代理人户口所在地
//		map.put("AGENT_J_C_ADD", "");//代理人经常居住地
//		map.put("AGENT_TELEPHONE", "");//代理人固定电话
//		map.put("AGENT_MOB_PHONE", "");//代理人移动电话
//		
//		Map params = new HashMap();
//		String reqMsg=CreateXmlMsg.BCK_08189(map);
//		
//		AllPublicTransSocketClient socketClient =new AllPublicTransSocketClient();
//		socketClient.createSocket();
//		logger.info("请求报文："+reqMsg);
//		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
//		logger.info("响应报文："+resMsg);
//		
//		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
//		reqXs.alias("Root", BCK08189ResBean.class);
//		BCK08189ResBean bck08189ResBean = (BCK08189ResBean)reqXs.fromXML(resMsg);
//		//获取返回报文错误码和错误信息
//		String resCode = bck08189ResBean.getHeadBean().getResCode();
//		String errMsg = bck08189ResBean.getHeadBean().getResMsg();
//		logger.info("取消约定转存resCode："+resCode+"resMsg:"+resMsg);
//		if(bck08189ResBean == null){
//			params.put("resCode",resCode);
//			params.put("errMsg","调用接口08189失败");
//			return params;
//		}
//		if(!SUCCESS.equals(resCode)){
//			params.put("resCode",resCode);
//			params.put("errMsg",errMsg);
//			return params;
//		}
//		
//		bean.setAgreeDepJrnlNo(bck08189ResBean.getBody().getSVR_JRNL_NO()!=null && !"".equals(bck08189ResBean.getBody().getSVR_JRNL_NO().trim())?bck08189ResBean.getBody().getSVR_JRNL_NO().trim():"");//取消约定转存流水号
//		bean.setAgreeInt(bck08189ResBean.getBody().getINT()!=null && !"".equals(bck08189ResBean.getBody().getINT().trim())?bck08189ResBean.getBody().getINT().trim():"");//约定转存利息
//		bean.setAgreeIntTax(bck08189ResBean.getBody().getINT_TAX()!=null && !"".equals(bck08189ResBean.getBody().getINT_TAX().trim())?bck08189ResBean.getBody().getINT_TAX().trim():"");//约定转存利息税
//		bean.setAgreeRealInt(bck08189ResBean.getBody().getREAL_INT()!=null && !"".equals(bck08189ResBean.getBody().getREAL_INT().trim())?bck08189ResBean.getBody().getREAL_INT().trim():"");//实付利息
//		bean.setAgreeAmt(bck08189ResBean.getBody().getAMT()!=null && !"".equals(bck08189ResBean.getBody().getAMT().trim())?bck08189ResBean.getBody().getAMT().trim():"");//约定转存金额
//		params.put("resCode",SUCCESS);
//		return params;
//	} 
	
	/**
	 * 卡权限管理【75008】前置03527
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public static Map inter03527(LostPubBean bean)throws Exception{
		//获取数据
		Map<String, String> map = new HashMap<String, String>();
		map.put("CARD_NO", bean.getAllPubAccNo());//卡号
		map.put("CARD_PIN", bean.getAllPubAccPwd());//密码
		map.put("ID_CODE", "10100");//证件类别
		map.put("ID_NO", bean.getAllPubIDNo());//证件号
		map.put("YDZC_FLAG", "1");//约定转存
		map.put("ZZSBZZ_FLAG", "");//自助设备转账
		map.put("SHORT_MSG_REMIND", "");//短信提醒
		map.put("BIND_MSG_PHONE_NO", "");//短信提醒手机号码
		map.put("NO_CARD_PAY_FLAG", "");//无卡支付
		map.put("CUST_NO", bean.getCustNo());//客户号
		map.put("USER_NAME", bean.getCustName());//姓名
		map.put("NO_CARD_PHONE_NO", "");//无卡支付手机号码
		map.put("VLI_DATE", "");//有效期
		map.put("HAV_AGENT_FLAG", "1");//代理标志
		if("0".equals(map.get("HAV_AGENT_FLAG"))){
			map.put("CUST_NAME", "");//客户姓名
			map.put("SEX", "");//性别
			map.put("ID_TYPE", "");//证件类别
			map.put("ID_NO", "");//证件号码
			map.put("ISSUE_DATE", "");//签发日期
			map.put("DUE_DATE", "");//到期日期
			map.put("ISSUE_INST", "");//签发机关
			map.put("NATION", "");//国籍
			map.put("OCCUPATION", "");//职业
			map.put("REGIS_PER_RES", "");//户口所在地
			map.put("J_C_ADD", "");//经常居住地
			map.put("TELEPHONE", "");//固定电话
			map.put("MOB_PHONE", "");//移动电话
		}
		map.put("DAY_LIMIT_AMT", "");//日累计限额
		map.put("DAY_LIMIT_NUM", "");//日累计笔数
		map.put("YEAR_LIMIT_AMT", "");//年累计限额
		map.put("XFDXF_OPER_CHOOSE", "");//消费豆消费操作选择
		map.put("JFXF_OPER_CHOOSE", "");//积分消费操作选择
		map.put("TDZX_OPER_CHOOSE", "");//唐豆折现操作选择
		
		Map params = new HashMap();
		String reqMsg=CreateXmlMsg.BCK_03527(map);
		
		AllPublicTransSocketClient socketClient =new AllPublicTransSocketClient();
		socketClient.createSocket();
		logger.info("请求报文："+reqMsg);
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK03527ResBean.class);
		BCK03527ResBean bck03527ResBean = (BCK03527ResBean)reqXs.fromXML(resMsg);
		//获取返回报文错误码和错误信息
		String resCode = bck03527ResBean.getHeadBean().getResCode();
		String errMsg = bck03527ResBean.getHeadBean().getResMsg();
		logger.info("取消约定转存resCode："+resCode+"resMsg:"+resMsg);
		if(bck03527ResBean == null){
			params.put("resCode",resCode);
			params.put("errMsg","调用接口08189失败");
			return params;
		}
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		
		bean.setAgreeDepJrnlNo(bck03527ResBean.getBody().getSVR_JRNL_NO()!=null && !"".equals(bck03527ResBean.getBody().getSVR_JRNL_NO().trim())?bck03527ResBean.getBody().getSVR_JRNL_NO().trim():"");//取消约定转存流水号
		bean.setAgreeInt(bck03527ResBean.getBody().getYDZC_INTEREST()!=null && !"".equals(bck03527ResBean.getBody().getYDZC_INTEREST().trim())?bck03527ResBean.getBody().getYDZC_INTEREST().trim():"");//约定转存利息
		bean.setAgreeIntTax(bck03527ResBean.getBody().getYDZC_INTEREST_TAX()!=null && !"".equals(bck03527ResBean.getBody().getYDZC_INTEREST_TAX().trim())?bck03527ResBean.getBody().getYDZC_INTEREST_TAX().trim():"");//约定转存利息税
		bean.setAgreeRealInt(bck03527ResBean.getBody().getYDZC_PAY_TAX()!=null && !"".equals(bck03527ResBean.getBody().getYDZC_PAY_TAX().trim())?bck03527ResBean.getBody().getYDZC_PAY_TAX().trim():"");//实付利息
		bean.setAgreeAmt(bck03527ResBean.getBody().getYDZC_AMT()!=null && !"".equals(bck03527ResBean.getBody().getYDZC_AMT().trim())?bck03527ResBean.getBody().getYDZC_AMT().trim():"");//约定转存本金
		params.put("resCode",SUCCESS);
		return params;
	} 
	
	/**
	 * 唐豆收回查询
	 */
	public static Map inter07622(LostPubBean bean)throws Exception{
		//获取数据
		Map<String,String> map = new HashMap<String,String>();
		
		map.put("ACCT_NO", bean.getAllPubAccNo());//账号
		map.put("SUB_ACCT_NO", "");//子账户
		map.put("PAY_DATE", bean.getAllPubSvrDate().replace("/", "").trim());//核心日期
		map.put("PAY_JRNL", bean.getLostJrnlNo().trim());//挂失销户流水号
		
		Map<String,String> params = new HashMap<String,String>();
		String reqMsg=CreateXmlMsg.BCK_07622(map);
		logger.info("请求报文："+reqMsg);
		
		AllPublicTransSocketClient socketClient =new AllPublicTransSocketClient();
		socketClient.createSocket();
		
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK07622ResBean.class);
		BCK07622ResBean bck07622ResBean = (BCK07622ResBean)reqXs.fromXML(resMsg);
		
		if(bck07622ResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","调用接口07622失败");
			return params;
		}
		String resCode = bck07622ResBean.getHeadBean().getResCode();
		String errMsg = bck07622ResBean.getHeadBean().getResMsg();
		logger.info("唐豆收回查询resCode:"+resCode);
		logger.info("唐豆收回查询resMsg:"+errMsg);
		
		if(!SUCCESS.equals(resCode)){//唐豆规则异常信息
			
			if(errMsg.indexOf("7091") != -1 || errMsg.indexOf("D156") != -1 || errMsg.indexOf("D999") != -1 || errMsg.indexOf("1909") != -1){
				//唐豆规则查询正常，不调用唐豆收回接口
				bean.setShtdy("0.00");
				params.put("resCode",SUCCESS);
				return params;

			}
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		Date endDate = DateUtil.getDate(bean.getEndDate().replace("/", ""));//到期日
		Date svrdate = DateUtil.getDate(bean.getAllPubSvrDate().replace("/", ""));//核心日
		Date startDate = DateUtil.getDate(bean.getOpenDate().replace("/", ""));//开户日期
		//获取存期
		String demTerm = bck07622ResBean.getBody().getDepTerm();
		//获取开户日期之后的一年
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDate);
		int year = calendar.get(Calendar.YEAR);
		calendar.set(Calendar.YEAR, year+1);
		//一年后的开户日
		startDate = calendar.getTime();
		
		//第一种情况，当唐豆金额为0.00元，不调用唐豆收回接口
		if(bck07622ResBean.getBody().getBack_Exchange_Amt()==null){
			
			bean.setShtdy("0.00");
			
		}else if("0.00".equals(bck07622ResBean.getBody().getBack_Exchange_Amt().trim()) || "".equals(bck07622ResBean.getBody().getBack_Exchange_Amt().trim())){
			
			bean.setShtdy("0.00");
		}
		//第二种情况，当存单到期，不调用唐豆收回接口
		else if(endDate.compareTo(svrdate) <= 0){
			bean.setShtdy("0.00");
		}
		//第三种情况，判断存期，当存期为两年，超过1年再支取的，不调用唐豆收回接口
		else if(demTerm.contains("Y") && Integer.parseInt(demTerm.replace("Y", "")) == 2 && startDate.compareTo(svrdate) <= 0){
			bean.setShtdy("0.00");
		}
		else{
		//调用唐豆收回接口
			bean.setTdPayAmt(bck07622ResBean.getBody().getPayAmt().trim());//支取金额
			bean.setShtds(bck07622ResBean.getBody().getBack_Count().trim());//唐豆数量
			bean.setShtdy(bck07622ResBean.getBody().getBack_Exchange_Amt().trim());//唐豆金额
		}
		params.put("resCode",resCode);
		return params;
	}
	
	/**
	 * 唐豆收回
	 */
	public static Map inter07665(LostPubBean bean)throws Exception{
		//获取数据
		Map<String,String> map = new HashMap<String,String>();
		
		map.put("ACCT_NO", bean.getAllPubAccNo());
		map.put("SUB_ACCT_NO", "");
		map.put("PAY_DATE", bean.getAllPubSvrDate().replace("/", "").trim());
		map.put("PAY_JRNL", bean.getLostJrnlNo().trim());
		map.put("PAY_AMT", bean.getTdPayAmt());//支取金额
		map.put("ORG_EXCHANGE_MODE", "");
		map.put("ORG_EXCHANGE_PROP", "");
		map.put("BACK_COUNT", bean.getShtds());//唐豆数量
		map.put("BACK_PROP", "");
		map.put("BACK_EXCHANGE_AMT", bean.getShtdy());//唐豆金额
		map.put("RECOV_TYPE", "1");
		map.put("OPPO_ACCT_NO",bean.getSelectCardNo());
		map.put("PASSWORD", "");
		
		Map<String,String> params = new HashMap<String,String>();
		String reqMsg=CreateXmlMsg.BCK_07665(map);
		logger.info("请求报文："+reqMsg);
		
		AllPublicTransSocketClient socketClient =new AllPublicTransSocketClient();
		socketClient.createSocket();
		
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK07665ResBean.class);
		BCK07665ResBean bck07665ResBean = (BCK07665ResBean)reqXs.fromXML(resMsg);
		
		if(bck07665ResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","调用接口07665失败");
			return params;
		}
		String resCode = bck07665ResBean.getHeadBean().getResCode();
		String errMsg = bck07665ResBean.getHeadBean().getResMsg();
		logger.info("唐豆收回resCode:"+resCode);
		logger.info("唐豆收回resMsg:"+errMsg);
		
		if(!SUCCESS.equals(resCode)){
			
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
			
		}
		bean.setTdshJrnlNo(bck07665ResBean.getBody().getSVR_JRNL_NO());//唐豆收回流水
		params.put("resCode",resCode);
		return params;
	}
	
	/**
	 * 挂失申请书打印查询02962-前置【08179】(核心)
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public static Map inter08179(LostPubBean bean)throws Exception{
		
		//获取数据
		Map<String, String> map = new HashMap<String, String>();
		map.put("DATE", bean.getLostApplNo().substring(5,13));//日期
		map.put("INST_NO_I", "");//机构号
		map.put("JRNL_NO_I", "");//流水
		map.put("LOST_APPL_NO", bean.getLostApplNo());//挂失申请书号
		map.put("TELLER_NO_I", "");//柜员号
		
		Map params = new HashMap();
		String reqMsg=CreateXmlMsg.BCK_08179(map);
		AllPublicTransSocketClient socketClient =new AllPublicTransSocketClient();
		socketClient.createSocket();
		logger.info("请求报文："+reqMsg);
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK08179ResBean.class);
		BCK08179ResBean bck08179ResBean = (BCK08179ResBean)reqXs.fromXML(resMsg);
		if(bck08179ResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","调用接口08179失败");
			return params;
		}
		//获取返回报文错误码和错误信息
		String resCode = bck08179ResBean.getHeadBean().getResCode();
		String errMsg = bck08179ResBean.getHeadBean().getResMsg();
		logger.info("挂失申请书打印查询(核心)resCode:"+resCode+"resMsg:"+errMsg);
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		String fileName = bck08179ResBean.getBody().getFILE_NAME();
		FtpFileUtils.downloadFileByFTPClient(Property.FTP_IP_DOWN, Integer.parseInt(Property.FTP_PORT_DOWN), Property.FTP_USER_DOWN, Property.FTP_PWD_DOWN, fileName, Property.FTP_LOCAL_PATH + fileName);
		List<LostCheckBean> list = TextFileReader.paddingList(Property.FTP_LOCAL_PATH + fileName, LostCheckBean.class);
		params.put("LostApplnoCheck", list);
		params.put("resCode",SUCCESS);
		return params;	
	}
	/**
	 * 挂失申请书打印查询70031-前置【08180】（到卡系统）
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public static Map inter08180(LostPubBean bean)throws Exception{
		
		//获取数据
		Map<String, String> map = new HashMap<String, String>();
		map.put("DATE", bean.getLostApplNo().substring(5,13));//日期
		map.put("INST_NO_I", "");//机构号
		map.put("JRNL_NO_I", "");//流水
		map.put("LOST_APPL_NO", bean.getLostApplNo());//挂失申请书号
		map.put("TELLER_NO_I", "");//柜员号
		
		Map params = new HashMap();
		String reqMsg=CreateXmlMsg.BCK_08180(map);
		AllPublicTransSocketClient socketClient =new AllPublicTransSocketClient();
		socketClient.createSocket();
		logger.info("请求报文："+reqMsg);
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK08180ResBean.class);
		BCK08180ResBean bck08180ResBean = (BCK08180ResBean)reqXs.fromXML(resMsg);
		if(bck08180ResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","调用接口08180失败");
			return params;
		}
		//获取返回报文错误码和错误信息
		String resCode = bck08180ResBean.getHeadBean().getResCode();
		String errMsg = bck08180ResBean.getHeadBean().getResMsg();
		logger.info("挂失申请书打印查询(卡系统)resCode:"+resCode+"resMsg:"+errMsg);
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		String fileName = bck08180ResBean.getBody().getFILE_NAME();
		FtpFileUtils.downloadFileByFTPClient(Property.FTP_IP_DOWN, Integer.parseInt(Property.FTP_PORT_DOWN), Property.FTP_USER_DOWN, Property.FTP_PWD_DOWN, fileName, Property.FTP_LOCAL_PATH + fileName);
		List<LostCheckBean> list = TextFileReader.paddingList(Property.FTP_LOCAL_PATH + fileName, LostCheckBean.class);
		params.put("LostApplnoCheck", list);
		params.put("resCode",SUCCESS);
		return params;	
	}
	/**
	 * 销户查询（100304） –前置 【08160】-针对有基金
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public static Map inter08160(LostPubBean bean)throws Exception{
		
		//获取数据
		Map<String, String> map = new HashMap<String, String>();
		map.put("ACCT_NO", bean.getAllPubAccNo());//账户
		map.put("ID_NO", bean.getAllPubIDNo());//证件号码
		map.put("OPEN_INST", "");//开户机构
	
		Map params = new HashMap();
		String reqMsg=CreateXmlMsg.BCK_08160(map);
		AllPublicTransSocketClient socketClient =new AllPublicTransSocketClient();
		socketClient.createSocket();
		logger.info("请求报文："+reqMsg);
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK08160ResBean.class);
		BCK08160ResBean bck08160ResBean = (BCK08160ResBean)reqXs.fromXML(resMsg);
		if(bck08160ResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","调用接口08160失败");
			return params;
		}
		//获取返回报文错误码和错误信息
		String resCode = bck08160ResBean.getHeadBean().getResCode();
		String errMsg = bck08160ResBean.getHeadBean().getResMsg();
		logger.info("销户查询resCode:"+resCode+"resMsg:"+errMsg);
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		bean.setFundFlag(bck08160ResBean.getBody().getCHK_FLAG()!=null && !"".equals(bck08160ResBean.getBody().getCHK_FLAG())?bck08160ResBean.getBody().getCHK_FLAG():"");//是否可销户  S.是  F.否
		params.put("resCode",SUCCESS);
		return params;	
	}
	
	
	/**
	 * 弱密码较验01325
	 */
	public static Map inter01325(LostPubBean bean)throws Exception{
		//获取数据
		Map<String, String> map = new HashMap<String, String>();
		map.put("NEW_PASS_WORD",bean.getAllPubAccPwd());//密码
		map.put("ACCT_NO1", bean.getAllPubPassAccNo());//账号
		Map params = new HashMap();
		String reqMsg=CreateXmlMsg.BCK_01325(map);
		AllPublicTransSocketClient socketClient =new AllPublicTransSocketClient();
		socketClient.createSocket();
		logger.info("请求报文："+reqMsg);
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK01325ResBean.class);
		BCK01325ResBean bck01325ResBean = (BCK01325ResBean)reqXs.fromXML(resMsg);
		if(bck01325ResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","调用接口01325失败");
			return params;
		}
		//获取返回报文错误码和错误信息
		String resCode = bck01325ResBean.getHeadBean().getResCode();
		String errMsg = bck01325ResBean.getHeadBean().getResMsg();
		logger.info("较验弱密码resCode:"+resCode+"resMsg"+errMsg);
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		
		logger.info("查询成功后信息："+bean);
		params.put("resCode",SUCCESS);
		params.put("errMsg",errMsg);
		return params;
	}
	
//	/**
//	  * 修改支付条件【02805】-前置07517   ----已废弃，未使用
//	  * 
//	  * @param bean
//	  * @return
//	  * @throws Exception
//	  */
//	 public static Map inter07517(LostPubBean bean)throws Exception{
//	  //获取数据
//	  Map<String, String> map = new HashMap<String, String>();
//	  map.put("ACC_NO", bean.getAllPubAccNo());//账号
//	  map.put("CUST_NO",""); //客户号
//	  map.put("CUST_NAME",bean.getApplInfos().getCust_name());//客户名称
//	  map.put("CERT_NO", bean.getApplInfos().getCert_no());//凭证号
//	  map.put("OLD_PAY_COND","1");//原支付条件 (0、无 1、凭密码 2、凭证件 3、凭印鉴 4、凭印鉴和密码)
//	  map.put("NEW_PAY_COND","1");//新支付条件(0、无 1、凭密码 2、凭证件 3、凭印鉴 4、凭印鉴和密码)
//	  map.put("CHANGE_REASON","1");//挂失更换
//	  map.put("LOST_APPL_NO",bean.getLostApplNo());//挂失申请号
//	  map.put("OLD_PASSWORD","");//原密码
//	  map.put("NEW_PASSWORD",bean.getReMakePwd());//新密码
//	  map.put("ID_YTPE","");//证件类型
//	  map.put("ID_NO", "");//证件号码   
//	     
//	     Map params = new HashMap();
//	  String reqMsg=CreateXmlMsg.BCK_07517(map);
//	  AllPublicTransSocketClient socketClient =new AllPublicTransSocketClient();
//	  socketClient.createSocket();
//	  logger.info("请求报文："+reqMsg);
//	  String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
//	  logger.info("响应报文："+resMsg);
//	  XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
//	  reqXs.alias("Root", BCK07517ResBean.class);
//	  BCK07517ResBean bck07517ResBean = (BCK07517ResBean)reqXs.fromXML(resMsg);
//	  if(bck07517ResBean == null){
//	   params.put("resCode","4444");
//	   params.put("errMsg","调用接口07517失败");
//	   return params;
//	  }
//	  //获取返回报文错误码和错误信息
//	  String resCode = bck07517ResBean.getHeadBean().getResCode();
//	  String errMsg = bck07517ResBean.getHeadBean().getResMsg();
//	  logger.info("查询信息resCode:"+resCode+"resMsg"+errMsg);
//	  if(!SUCCESS.equals(resCode)){
//	   params.put("resCode",resCode);
//	   params.put("errMsg",errMsg);
//	   return params;
//	  }
//	  bean.setReMakePwdJrnlNo(bck07517ResBean.getBody().getSVR_JRNL_NO()!=null && !"".equals(bck07517ResBean.getBody().getSVR_JRNL_NO())?bck07517ResBean.getBody().getSVR_JRNL_NO():"");//重置密码流水号
//	  logger.info("查询成功后信息："+bean);
//	  params.put("resCode",SUCCESS);
//	  params.put("errMsg",errMsg);
//	  return params;
//	 }
	 
//	 /**
//	  * 改密【75005】-前置【08159】     ----已废弃，未使用
//	  * 
//	  * @param bean
//	  * @return
//	  * @throws Exception
//	  */
//	 public static Map inter08159(LostPubBean bean)throws Exception{
//	  //获取数据
//	  Map<String, String> map = new HashMap<String, String>();
//	  map.put("NEW_CARD_PWD", bean.getReMakePwd());//新密码
//	  map.put("ORIG_CARD_PWD",""); //原密码
//	  map.put("CARD_NO2",bean.getAllPubAccNo());//卡号
//	  if("N".equals(bean.getCardState())){//判断未激活
//		  map.put("CHG_REASON", "3");//密码激活
//	  }else{
//		  map.put("CHG_REASON", "1");//挂失更换
//	  }
//	  map.put("LOST_APPL_NO",bean.getLostApplNo());//挂失申请书号
//	  map.put("LOST_DATE",bean.getLostApplNo().substring(5,13));//挂失日期
//	  map.put("INPUT_NAME",bean.getApplInfos().getCust_name());//姓名
//	  map.put("ID_TYPE","10100");//证件类型
//	  map.put("ID_NO",bean.getApplInfos().getId_no());//证件号码
//	  map.put("CHL_NO","08");//渠道号
//	     
//	     Map params = new HashMap();
//	  String reqMsg=CreateXmlMsg.BCK_08159(map);
//	  AllPublicTransSocketClient socketClient =new AllPublicTransSocketClient();
//	  socketClient.createSocket();
//	  logger.info("请求报文："+reqMsg);
//	  String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
//	  logger.info("响应报文："+resMsg);
//	  XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
//	  reqXs.alias("Root", BCK08159ResBean.class);
//	  BCK08159ResBean bck08159ResBean = (BCK08159ResBean)reqXs.fromXML(resMsg);
//	  if(bck08159ResBean == null){
//	   params.put("resCode","4444");
//	   params.put("errMsg","调用接口08159失败");
//	   return params;
//	  }
//	  //获取返回报文错误码和错误信息
//	  String resCode = bck08159ResBean.getHeadBean().getResCode();
//	  String errMsg = bck08159ResBean.getHeadBean().getResMsg();
//	  logger.info("查询信息resCode:"+resCode+"resMsg"+errMsg);
//	  if(!SUCCESS.equals(resCode)){
//	   params.put("resCode",resCode);
//	   params.put("errMsg",errMsg);
//	   return params;
//	  }
//	  bean.setReMakePwdJrnlNo(bck08159ResBean.getBody().getCARD_JRNL_NO()!=null && !"".equals(bck08159ResBean.getBody().getCARD_JRNL_NO())? bck08159ResBean.getBody().getCARD_JRNL_NO():"");//重置密码流水号
//	  logger.info("查询成功后信息："+bean);
//	  params.put("resCode",SUCCESS);
//	  params.put("errMsg",errMsg);
//	  return params;
//	 }
	 
	/**
	 * 按账号查全面信息【02884】-前置08223
	 * 
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public static Map inter08223(LostPubBean bean) throws Exception {
		// 获取数据
		Map<String, String> map = new HashMap<String, String>();

		map.put("ACCT_NO", bean.getAllPubAccNo());// 账号
		map.put("CUST_NO", "");// 客户号
		map.put("CUST_NAME", "");// 客户名称
		map.put("QRY_TYPE", "2");// 查询类型
		
		Map params = new HashMap();			
		//连接socket
		String reqMsg=CreateXmlMsg.BCK_08223(map);
		AllPublicTransSocketClient socketClient =new AllPublicTransSocketClient();
		socketClient.createSocket();			
		//发送请求报文、接收响应报文
		logger.info("请求报文："+reqMsg);
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);			
		//解析xml
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK08223ResBean.class);
		BCK08223ResBean bck08223ResBean = (BCK08223ResBean)reqXs.fromXML(resMsg);
			
		//获取返回报文错误码和错误信息
		String resCode = bck08223ResBean.getHeadBean().getResCode();
		String errMsg = bck08223ResBean.getHeadBean().getResMsg();
		logger.info("账户全面信息查询:resCode08223:"+resCode+"resMsg08223:"+errMsg);
		
		if(bck08223ResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","账户全面信息查询接口失败");
			return params;
		}
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		String fileName = bck08223ResBean.getBody().getFILE_NAME()!=null && !"".equals(bck08223ResBean.getBody().getFILE_NAME().trim())?bck08223ResBean.getBody().getFILE_NAME().trim():"";//文件路径	
		FtpFileUtils.downloadFileByFTPClient(Property.FTP_IP_DOWN, Integer.parseInt(Property.FTP_PORT_DOWN), Property.FTP_USER_DOWN, Property.FTP_PWD_DOWN, fileName, Property.FTP_LOCAL_PATH + fileName);
		List<AccNoCheck08223> list = TextFileReader.paddingList(Property.FTP_LOCAL_PATH + fileName, AccNoCheck08223.class);
		params.put("Acc08223", list);
		params.put("resCode", SUCCESS);
		return params;
	}
	
	/**
	 * 唐豆信息查询【02217】-前置07515
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public static Map inter07515(LostPubBean bean)throws Exception{
		//获取数据
		Map<String, String> map = new HashMap<String, String>();
		map.put("ACCT_NO", bean.getAllPubAccNo());//账号
		map.put("CUST_NO", "");//客户号
		map.put("ID_TYPE", "");//证件类别
		map.put("ID_NO", "");//证件号码
		
		Map params = new HashMap();
		String reqMsg=CreateXmlMsg.BCK_07515(map);
		
		AllPublicTransSocketClient socketClient =new AllPublicTransSocketClient();
		socketClient.createSocket();
		logger.info("请求报文："+reqMsg);
		
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK07515ResBean.class);
		BCK07515ResBean bck07515ResBean = (BCK07515ResBean)reqXs.fromXML(resMsg);
		//获取返回报文错误码和错误信息
		String resCode = bck07515ResBean.getHeadBean().getResCode();
		String errMsg = bck07515ResBean.getHeadBean().getResMsg();
		logger.info(" 唐豆信息查询resCode："+resCode+" 唐豆信息查询resMsg:"+errMsg);
		if(bck07515ResBean == null){
			params.put("resCode",resCode);
			params.put("errMsg","调用接口07515失败");
			return params;
		}
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		
		String fileName = bck07515ResBean.getBody().getFILE_NAME();
		List<AccTdMsgBean> list  = null;
		try {
			// 下载文件
			FtpFileUtils.downloadFileByFTPClient(Property.FTP_IP_DOWN, Integer.parseInt(Property.FTP_PORT_DOWN), Property.FTP_USER_DOWN, Property.FTP_PWD_DOWN, fileName, Property.FTP_LOCAL_PATH + fileName);
			list = TextFileReader.paddingList(Property.FTP_LOCAL_PATH + fileName, AccTdMsgBean.class);
		} catch (Exception e) {
			logger.error("唐豆信息文件下载失败"+e);
			params.put("resCode","4444");
			params.put("errMsg","唐豆信息文件下载失败");
			return params;
		}
		params.put("TdMsg", list);
		params.put("resCode",SUCCESS);
		return params;
	} 
	
	/**
	 * 普通预计利息查询
	 */
	public static Map inter07696(LostPubBean bean)throws Exception{
		//获取数据
		Map<String,String> map = new HashMap<String,String>();
		
		map.put("ACCT_NO", bean.getAllPubAccNo());//存单账号
		
		Map<String,String> params = new HashMap<String,String>();
		String reqMsg=CreateXmlMsg.BCK_07696(map);
		logger.info("请求报文："+reqMsg);
		
		AllPublicTransSocketClient socketClient =new AllPublicTransSocketClient();
		socketClient.createSocket();
		
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK07696ResBean.class);
		BCK07696ResBean bck07696ResBean = (BCK07696ResBean)reqXs.fromXML(resMsg);
		
		if(bck07696ResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","调用接口07696失败");
			return params;
		}
		String resCode = bck07696ResBean.getHeadBean().getResCode();
		String errMsg = bck07696ResBean.getHeadBean().getResMsg();
		logger.info("普通预计利息查询resCode:"+resCode);
		logger.info("普通预计利息查询resMsg:"+errMsg);
		
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		
		// 利息
		bean.setYjlx(bck07696ResBean.getBody().getInte().trim());//获取预计利息
		bean.setAdvnInit(bck07696ResBean.getBody().getADVN_INIT().trim());//已付收益
		if(bck07696ResBean.getBody().getPAYOFF_INTEREST()!=null && !"".equals(bck07696ResBean.getBody().getPAYOFF_INTEREST())){
			//如果已支付利息有值，则预计利息=预计利息+已支付利息
			BigDecimal bigDecimal = new BigDecimal(bck07696ResBean.getBody().getPAYOFF_INTEREST().trim());//已支付利息
			BigDecimal bigDecimal2 = new BigDecimal(bck07696ResBean.getBody().getInte().trim());//预计利息
			bean.setYjlx(bigDecimal.add(bigDecimal2).toString());//获取预计利息
		}else{
		}			
		params.put("resCode",resCode);
		return params;
	}
	/**
	 * 黑名单查询【20115】-前置【08236】
	 */
	public static Map inter08236(LostPubBean bean)throws Exception{
		//获取数据
		Map<String,String> map = new HashMap<String,String>();
		
		map.put("ACCT_NO", bean.getAllPubAccNo());//账号
		
		Map<String,String> params = new HashMap<String,String>();
		String reqMsg=CreateXmlMsg.BCK_08236(map);
		logger.info("请求报文："+reqMsg);
		
		AllPublicTransSocketClient socketClient =new AllPublicTransSocketClient();
		socketClient.createSocket();
		
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK08236ResBean.class);
		BCK08236ResBean bck08236ResBean = (BCK08236ResBean)reqXs.fromXML(resMsg);
		
		if(bck08236ResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","调用接口08236失败");
			return params;
		}
		String resCode = bck08236ResBean.getHeadBean().getResCode();
		String errMsg = bck08236ResBean.getHeadBean().getResMsg();
		logger.info("黑名单查询resCode:"+resCode);
		logger.info("黑名单查询resMsg:"+errMsg);
		
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
	    String  idState=bck08236ResBean.getBody().getCUST_STAT().substring(0,1);
	    params.put("IDstata", idState);
		params.put("resCode",resCode);
		return params;
	}
	/**
	 * 转入卡 黑名单查询【20115】-前置【08236】
	 */
	public static Map interCard08236(LostPubBean bean)throws Exception{
		//获取数据
		Map<String,String> map = new HashMap<String,String>();
		
		map.put("ACCT_NO", bean.getCardNo());//账号
		
		Map<String,String> params = new HashMap<String,String>();
		String reqMsg=CreateXmlMsg.BCK_08236(map);
		logger.info("请求报文："+reqMsg);
		
		AllPublicTransSocketClient socketClient =new AllPublicTransSocketClient();
		socketClient.createSocket();
		
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK08236ResBean.class);
		BCK08236ResBean bck08236ResBean = (BCK08236ResBean)reqXs.fromXML(resMsg);
		
		if(bck08236ResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","调用接口08236失败");
			return params;
		}
		String resCode = bck08236ResBean.getHeadBean().getResCode();
		String errMsg = bck08236ResBean.getHeadBean().getResMsg();
		logger.info("黑名单查询resCode:"+resCode);
		logger.info("黑名单查询resMsg:"+errMsg);
		
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
	    String  idState=bck08236ResBean.getBody().getCUST_STAT().trim();
	    params.put("IDstata", idState);
		params.put("resCode",resCode);
		return params;
	}
	
	
	/**
	 * 5.65实际账号查询外部账号【02966】-前置【08237】
	 */
	public static Map inter08237(LostPubBean bean)throws Exception{
		//获取数据
		Map<String,String> map = new HashMap<String,String>();
		
		map.put("IN_ACCT_NO", bean.getAllPubAccNo());//账号
		
		Map<String,String> params = new HashMap<String,String>();
		String reqMsg=CreateXmlMsg.BCK_08237(map);
		logger.info("请求报文："+reqMsg);
		
		AllPublicTransSocketClient socketClient =new AllPublicTransSocketClient();
		socketClient.createSocket();
		
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK08237ResBean.class);
		BCK08237ResBean bck08237ResBean = (BCK08237ResBean)reqXs.fromXML(resMsg);
		
		if(bck08237ResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","调用接口08237失败");
			return params;
		}
		String resCode = bck08237ResBean.getHeadBean().getResCode();
		String errMsg = bck08237ResBean.getHeadBean().getResMsg();
		logger.info("查询外部账号resCode:"+resCode);
		logger.info("查询外部账号resMsg:"+errMsg);
		
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		bean.setAllPubAccNo(bck08237ResBean.getBody().getOUT_ACCT_NO().trim());//获取到外部账号
		params.put("resCode",resCode);
		return params;
	}
	
	/**
	 * 账户信息查询【96010】-前置【08239】(电子账户)
	 */
	public static Map inter08239(LostPubBean bean)throws Exception{
		//获取数据
		Map<String,String> map = new HashMap<String,String>();
		
		map.put("ACCT_NO", bean.getCardNo());//电子账号
		map.put("PIN_VAL_FLAG", "0");//查询条件
		map.put("PASSWORD", "");//密码
		
		Map<String,String> params = new HashMap<String,String>();
		String reqMsg=CreateXmlMsg.BCK_08239(map);
		logger.info("请求报文："+reqMsg);
		
		AllPublicTransSocketClient socketClient =new AllPublicTransSocketClient();
		socketClient.createSocket();
		
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK08239ResBean.class);
		BCK08239ResBean bck08239ResBean = (BCK08239ResBean)reqXs.fromXML(resMsg);
		
		if(bck08239ResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","调用接口08239失败");
			return params;
		}
		String resCode = bck08239ResBean.getHeadBean().getResCode();
		String errMsg = bck08239ResBean.getHeadBean().getResMsg();
		logger.info("账户信息查询resCode:"+resCode);
		logger.info("账户信息查询resMsg:"+errMsg);
		
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		params.put("ACCT_STAT", bck08239ResBean.getBody().getACCT_STAT()!=null && !"".equals(bck08239ResBean.getBody().getACCT_STAT().trim())?bck08239ResBean.getBody().getACCT_STAT().trim():"");//账户状态
		params.put("LOST_STAT", bck08239ResBean.getBody().getLOST_STAT()!=null && !"".equals(bck08239ResBean.getBody().getLOST_STAT().trim())?bck08239ResBean.getBody().getLOST_STAT().trim():"");//挂失状态
		params.put("HOLD_STAT", bck08239ResBean.getBody().getHOLD_STAT()!=null && !"".equals(bck08239ResBean.getBody().getHOLD_STAT().trim())?bck08239ResBean.getBody().getHOLD_STAT().trim():"");//冻结状态
		params.put("STOP_STAT", bck08239ResBean.getBody().getSTOP_STAT()!=null && !"".equals(bck08239ResBean.getBody().getSTOP_STAT().trim())?bck08239ResBean.getBody().getSTOP_STAT().trim():"");//止付状态
		params.put("resCode",resCode);
		return params;
	}
	
	/**
	 * 上传挂失解挂申请书图片-前置【08233】
	 */
	public static Map inter08233(LostPubBean bean)throws Exception{
		//获取数据
		Map<String,String> map = new HashMap<String,String>();
		
		map.put("SVR_DATE", bean.getAllPubSvrDate().replace("/","").trim());//核心日期
		map.put("LOST_FLAG",bean.getLostselect());//挂失解挂标识  0.挂失  1.解挂
		map.put("LOST_APPL_NO",bean.getLostApplNo());//挂失申请书号
		map.put("FILE_NAME",bean.getLostJpgPath() );//申请书图片
		Map<String,String> params = new HashMap<String,String>();
		String reqMsg=CreateXmlMsg.BCK_08233(map);
		logger.info("请求报文："+reqMsg);
		
		AllPublicTransSocketClient socketClient =new AllPublicTransSocketClient();
		socketClient.createSocket();
		
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK08233ResBean.class);
		BCK08233ResBean bck08233ResBean = (BCK08233ResBean)reqXs.fromXML(resMsg);
		
		if(bck08233ResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","调用接口08233失败");
			return params;
		}
		String resCode = bck08233ResBean.getHeadBean().getResCode();
		String errMsg = bck08233ResBean.getHeadBean().getResMsg();
		logger.info("上传挂失解挂申请书图片resCode:"+resCode);
		logger.info("上传挂失解挂申请书图片resMsg:"+errMsg);
		
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		params.put("resCode",resCode);
		return params;
	}
	
	/**
	 * (卡)挂失【75001】-前置【08190】
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public static Map inter08190(LostPubBean bean)throws Exception{
		//获取数据
		Map<String,String> map = new HashMap<String,String>();
		
		//校验银行卡时上送
		if("0".equals(bean.getYseNoPass())){//知道密码
			map.put("LOST_TYPE", "1");//正式挂失
			map.put("CARD_PWD", bean.getAllPubAccPwd());//卡密码
		}else{//忘记密码
			if("N".equals(bean.getCardState())){
				map.put("LOST_TYPE", "1");//未激活卡  走单挂
			}else{
				map.put("LOST_TYPE", "3");//双挂失
			}
			
		}
		map.put("CARD_NO", bean.getAllPubAccNo());//卡号
		map.put("LOST_ID_TYPE", "10100");//挂失证件类别
		map.put("LOST_ID_NO", bean.getAllPubIDNo());//挂失证件号码
		if("1".equals(bean.getAllPubIsDeputy())){
			map.put("AGENT_ID_TYPE", "10100");//代理人证件类型
			map.put("AGENT_ID_NO", bean.getAllPubAgentIDNo());//代理人证件号
			map.put("AGENT_NAME", bean.getAllPubAgentIdCardName());//代理人名称
			map.put("AGENT_ADDR", bean.getAllPubAgentAddress());//代理人地址
			map.put("AGENT_PHONE", bean.getAllPubAgentPhone());//代理人电话
		}
		map.put("CHL_NO", "08");//渠道号
		map.put("APPL_NO","");//申请书号
		map.put("FEE_FLAG", "0");//是否收手续费
		map.put("FEE_AMT", "");//手续费金额
		map.put("DE_REASON", "");//减免原因
		map.put("CASH_FLAG", "");//现转标识
		Map params = new HashMap();
		
		//连接socket
		String reqMsg=CreateXmlMsg.BCK_08190(map);
		AllPublicTransSocketClient socketClient =new AllPublicTransSocketClient();
		socketClient.createSocket();
		
		//发送请求报文、接收响应报文
		logger.info("请求报文："+reqMsg);
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		
		//解析xml
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK08190ResBean.class);
		BCK08190ResBean bck08190ResBean = (BCK08190ResBean)reqXs.fromXML(resMsg);
		//获取返回报文错误码和错误信息
		String resCode = bck08190ResBean.getHeadBean().getResCode();
		String errMsg = bck08190ResBean.getHeadBean().getResMsg();
		logger.info("银行卡挂失resCode："+resCode+"resMsg:"+errMsg);
		if(bck08190ResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","银行卡挂失接口失败");
			return params;
		}
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		
		bean.setLostJrnlNo(bck08190ResBean.getBody().getCARD_JRNL_NO());//卡挂失的流水号
		bean.setLostApplNo(bck08190ResBean.getBody().getLOST_APPL_NO());//挂失申请书号
		
		params.put("resCode",SUCCESS);
		return params;		
	}
	
	
	/**
	 * (核心)账户正式挂失(85024)-前置【08193】
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public static Map inter08193(LostPubBean bean)throws Exception{
		//获取数据
		Map<String,String> map = new HashMap<String,String>();
		ShowAccNoMsgBean show=(ShowAccNoMsgBean) bean.getAccMap().get("selectMsg");//挂失种类
		//校验银行卡时上送
		String acc_no=bean.getAllPubAccNo();
		if(acc_no.contains("-")){
			String[] split=acc_no.split("-");
			map.put("ACCT_NO", split[0]);//账号（卡号/存单号/存折号）
			map.put("SUB_ACCT_NO", split[1]);//子账号
		}else{
		    map.put("ACCT_NO", acc_no);//账号（卡号/存单号/存折号）
		    map.put("SUB_ACCT_NO", "");//子账号
		}
		
		map.put("PROD_CODE", "");//产品代码
		map.put("CUST_NAME", bean.getAllPubIdCardName());//客户名称
		//校验银行卡时上送
		if("0".equals(bean.getYseNoPass())){//知道密码
			map.put("LOST_TYPE", "3");//挂失类型为单折挂失
		}else{
			if(bean.getDrawCond()!=null && ("0".equals(bean.getDrawCond())||"2".equals(bean.getDrawCond()))){//支取条件如果是凭证件或者无密码
				map.put("LOST_TYPE", "3");//挂失类型为单折挂失
			}else{
				map.put("LOST_TYPE", "6");//挂失类型为双挂失
			}
		}
		if("2".equals(show.getCardOrAccOrAcc1())){//存折
			map.put("BALANCE", bean.getEndAmt());//单折余额(结存额)
		}else{//存单
			map.put("BALANCE", bean.getTotalAmt());//单折余额(存折余额)
		}
		map.put("OPEN_DATE",show.getOpenDate());//开户日期
		map.put("ID_TYPE", "10100");//证件类型
		map.put("ID_NO", bean.getAllPubIDNo());//证件号码
		map.put("ISSUE_BRANCH", bean.getAllPubQfjg());//发证机关
		map.put("PASSWORD", bean.getAllPubAccPwd());//密码
		map.put("LOST_ID_TYPE", "10100");//证件类型（挂）
		map.put("LOST_ID_NO", bean.getAllPubIDNo());//证件号码（挂）
		map.put("LOST_ISSUE_BRANCH", bean.getAllPubQfjg());//发证机关

		if("1".equals(bean.getAllPubIsDeputy())){
			map.put("DEPU_FLAG", "0");//是否代理挂失  0.是  1.否
			map.put("DEPU_ID_TYPE", "10100");//代理人证件类型
			map.put("DEPU_ID_NO", bean.getAllPubAgentIDNo());//代理人证件号
			map.put("DEPU_ISSUE_BRANCH", bean.getAllPubAgentQfjg());//代理人发证机关
			map.put("AGENT_NAME", bean.getAllPubAgentIdCardName());//代理人名称
			map.put("AGENT_PHONE", bean.getAllPubAgentPhone());//代理人电话
			map.put("AGENT_ADDR",bean.getAllPubAgentAddress() );//代理人地址
		}else{
			map.put("DEPU_FLAG", "1");//是否代理挂失  0.有代理人  1.否
		}
		map.put("CHL_NO", "0035");//渠道号
		map.put("FEE","");//手续费
		Map params = new HashMap();
		
		//连接socket
		String reqMsg=CreateXmlMsg.BCK_08193(map);
		AllPublicTransSocketClient socketClient =new AllPublicTransSocketClient();
		socketClient.createSocket();
		
		//发送请求报文、接收响应报文
		logger.info("请求报文："+reqMsg);
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		
		//解析xml
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK08193ResBean.class);
		BCK08193ResBean bck08193ResBean = (BCK08193ResBean)reqXs.fromXML(resMsg);
		//获取返回报文错误码和错误信息
		String resCode = bck08193ResBean.getHeadBean().getResCode();
		String errMsg = bck08193ResBean.getHeadBean().getResMsg();
		logger.info("账户正式挂失resCode："+resCode+"resMsg:"+errMsg);
		if(bck08193ResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","账户正式挂失接口失败");
			return params;
		}
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		
		bean.setLostJrnlNo(bck08193ResBean.getBody().getSVR_JRNL_NO());//账户挂失的流水号
		bean.setLostApplNo(bck08193ResBean.getBody().getLOST_APPL_NO_R());//挂失申请书号
		
		params.put("resCode",SUCCESS);
		return params;		
	}
	
	/**
	 * (核心)挂失补发02960（新增）-前置【08181】
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public static Map inter08181(LostPubBean bean)throws Exception{
		//获取数据
		Map<String,String> map = new HashMap<String,String>();
		ShowAccNoMsgBean showBean = (ShowAccNoMsgBean)bean.getAccMap().get("selectMsg");
		//校验银行卡时上送
		if(bean.getAllPubAccNo()!=null && bean.getAllPubAccNo().contains("-")){
			map.put("ACCT_NO", bean.getAllPubPassAccNo());//账号
			map.put("SUB_ACCT_NO", bean.getAllPubAccNo().substring(bean.getAllPubAccNo().indexOf("-")+1));//子账号
		}else{
			map.put("ACCT_NO", bean.getAllPubPassAccNo());//账号
			map.put("SUB_ACCT_NO", "");//子账号
		}
		map.put("PROD_CODE", showBean.getProCode());//产品代码
		map.put("CUST_NO", bean.getCustNo());//客户号
		map.put("CUST_NAME", bean.getCustName());//客户名称
		map.put("BALANCE", showBean.getEndAmt());//余额
		map.put("DEP_TERM", showBean.getDepTerm());//存期
		map.put("PASSWORD", bean.getAllPubAccPwd());//密码
		map.put("ID_TYPE", "10010");//证件类型
		map.put("ID_NO", bean.getAllPubIDNo());//证件号码
		map.put("ISSUE_BRANCH", bean.getAllPubQfjg());//发证机关
		map.put("NEW_CERT_NO", bean.getCertNoAdd());//新凭证号码
		
		
		Map params = new HashMap();
		
		//连接socket
		String reqMsg=CreateXmlMsg.BCK_08181(map);
		AllPublicTransSocketClient socketClient =new AllPublicTransSocketClient();
		socketClient.createSocket();
		
		//发送请求报文、接收响应报文
		logger.info("请求报文："+reqMsg);
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		
		//解析xml
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK08181ResBean.class);
		BCK08181ResBean bck08181ResBean = (BCK08181ResBean)reqXs.fromXML(resMsg);
		//获取返回报文错误码和错误信息
		String resCode = bck08181ResBean.getHeadBean().getResCode();
		String errMsg = bck08181ResBean.getHeadBean().getResMsg();
		logger.info("账号信息综合查询resCode："+resCode+"resMsg:"+errMsg);
		if(bck08181ResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","账号信息综合查询接口失败");
			return params;
		}
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		
		String fileName = bck08181ResBean.getBody().getFILE_NAME()!=null && !"".equals(bck08181ResBean.getBody().getFILE_NAME().trim())?bck08181ResBean.getBody().getFILE_NAME().trim():"";//文件路径
		List<AccLostAndReturnInfoBean> resultList = null;
		try {
			
			FtpFileUtils.downloadFileByFTPClient(Property.FTP_IP_DOWN, Integer.parseInt(Property.FTP_PORT_DOWN), Property.FTP_USER_DOWN, Property.FTP_PWD_DOWN, fileName, Property.FTP_LOCAL_PATH + fileName);
			resultList = TextFileReader.paddingList(Property.FTP_LOCAL_PATH + fileName,AccLostAndReturnInfoBean.class);
			logger.info(resultList);
			
		} catch (Exception e) {
			
			logger.error("下载核心日期文件失败"+e);
			params.put("resCode","4444");
			params.put("errMsg","查询核心日期失败");
			return params;
		}
		if(resultList!=null && resultList.size()!=0){
			params.put("resList", resultList);
		}
		
		if("1".equals(showBean.getCardOrAccOrAcc1())){
			bean.setNewAccNo(bck08181ResBean.getBody().getNEW_ACCT_NO());//新账号
		}
		bean.setLostJrnlNo(bck08181ResBean.getBody().getSVR_JRNL_NO());//账户挂失的流水号
		bean.setLostApplNo(bck08181ResBean.getBody().getLOST_APPL_NO());//挂失申请书号
		
		params.put("resCode",SUCCESS);
		return params;		
	}
	
	/**
	 * (卡)挂失销户70030（新增）-前置【08186】
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public static Map inter08186(LostPubBean bean)throws Exception{
		//获取数据
		Map<String, String> map = new HashMap<String, String>();
		map.put("CARD_NO", bean.getAllPubAccNo());//挂失销户卡号
		map.put("PASSWORD", bean.getAllPubAccPwd());//密码
		map.put("LOST_DATE", bean.getAllPubSvrDate().replace("/", "").trim());//挂失核心日期
		map.put("ID_TYPE", "10100");//证件类型
		map.put("ID_NO", bean.getAllPubIDNo());//证件号
		map.put("OPP_ACCT_NO", bean.getSelectCardNo());//对方账号
		map.put("CASH_FLAG", "1");//现转标志
		map.put("CHL_NO", "08");//渠道号
		map.put("CERT_TYPE", "");//凭证种类
		map.put("CERT_NO", "");//凭证号
		map.put("OPEN_FLAG", "");//转存标志
		
		Map params = new HashMap();
		String reqMsg=CreateXmlMsg.BCK_08186(map);
		
		AllPublicTransSocketClient socketClient =new AllPublicTransSocketClient();
		socketClient.createSocket();
		logger.info("请求报文："+reqMsg);
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK08186ResBean.class);
		BCK08186ResBean bck08186ResBean = (BCK08186ResBean)reqXs.fromXML(resMsg);
		//获取返回报文错误码和错误信息
		String resCode = bck08186ResBean.getHeadBean().getResCode();
		String errMsg = bck08186ResBean.getHeadBean().getResMsg();
		logger.info("卡挂失销户resCode："+resCode+"resMsg:"+resMsg);
		if(bck08186ResBean == null){
			params.put("resCode",resCode);
			params.put("errMsg","调用接口08186失败");
			return params;
		}
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
	    bean.setLostJrnlNo(bck08186ResBean.getBody().getCARD_JRNL_NO()!=null && !"".equals(bck08186ResBean.getBody().getCARD_JRNL_NO())?bck08186ResBean.getBody().getCARD_JRNL_NO():"");//卡挂失的流水号
		bean.setLostApplNo(bck08186ResBean.getBody().getAPPL_NO()!=null && !"".equals(bck08186ResBean.getBody().getAPPL_NO())?bck08186ResBean.getBody().getAPPL_NO():"");//挂失申请书号
		bean.setCanceAmt(bck08186ResBean.getBody().getCANCEL_AMT()!=null && !"".equals(bck08186ResBean.getBody().getCANCEL_AMT())?bck08186ResBean.getBody().getCANCEL_AMT():"");//销户金额
		bean.setCancelRealInte(bck08186ResBean.getBody().getPAID_INTEREST()!=null && !"".equals(bck08186ResBean.getBody().getPAID_INTEREST())?bck08186ResBean.getBody().getPAID_INTEREST():"");//销户实付利息
		bean.setClearRate(bck08186ResBean.getBody().getCLEAR_RATE()!=null && !"".equals(bck08186ResBean.getBody().getCLEAR_RATE())?bck08186ResBean.getBody().getCLEAR_RATE():"");//销户清户利率
		params.put("resCode",SUCCESS);
		return params;
	} 
	
	/**
	 * (核心)挂失销户02961（新增）-前置【08182】
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public static Map inter08182(LostPubBean bean)throws Exception{
		//获取数据
		Map<String, String> map = new HashMap<String, String>();
		if(bean.getAllPubAccNo().contains("-")){
			
			String[] split = bean.getAllPubAccNo().split("-");
			map.put("ACCT_NO", split[0]);//卡号
			map.put("SUB_ACCT_NO", split[1]);//子账号
			
		}else{
			
			map.put("ACCT_NO", bean.getAllPubAccNo());//账号
			map.put("SUB_ACCT_NO", "");//子账号
		}
		map.put("PROD_CODE", bean.getProCode());//产品代码
		map.put("CUST_NO", bean.getCustNo());//客户号
		map.put("CUST_NAME", bean.getCustName());//客户名称
		if("2".equals(bean.getLostType())){//存折
			map.put("BALANCE", bean.getEndAmt());//余额(结存额)
		}else{//存单
			map.put("BALANCE", bean.getTotalAmt());//余额(存折余额)
		}
		map.put("START_DATE", bean.getOpenDate());//起息日期
		map.put("DEP_TERM", bean.getDepTerm());//存期
		map.put("OPEN_RATE", bean.getOpenRate()+"%");//开户利率(%)
		map.put("LOST_DATE", bean.getAllPubSvrDate().replace("/", "").trim());//挂失日期
		map.put("PAY_COND", "1");//支付条件
		map.put("PASSWORD", bean.getAllPubAccPwd());//密码
		map.put("ID_NO", bean.getAllPubIDNo());//证件号码
		map.put("ID_TYPE", "10100");//证件类型
		map.put("ISSUE_BRANCH", bean.getAllPubQfjg());//发证机关
		map.put("DRAW_CURRENCY", "00");//支取币种
		if("2".equals(bean.getLostType())){//存折
			map.put("DRAW_AMT", bean.getEndAmt());//支取额(结存额)
		}else{//存单
			map.put("DRAW_AMT", bean.getTotalAmt());//支取额(存折余额)
		}
		map.put("PRI_WAY", "1");//本金走向
		map.put("INT_WAY", "1");//利息走向
		map.put("OPP_ACCT_NO", bean.getSelectCardNo());//对方账号
		map.put("PRE_DATE", bean.getPreDate().trim().replace("/", ""));//预约日期
		map.put("BOOK_NO", "");//存折号码
		
		Map params = new HashMap();
		String reqMsg=CreateXmlMsg.BCK_08182(map);
		
		AllPublicTransSocketClient socketClient =new AllPublicTransSocketClient();
		socketClient.createSocket();
		logger.info("请求报文："+reqMsg);
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK08182ResBean.class);
		BCK08182ResBean bck08182ResBean = (BCK08182ResBean)reqXs.fromXML(resMsg);
		//获取返回报文错误码和错误信息
		String resCode = bck08182ResBean.getHeadBean().getResCode();
		String errMsg = bck08182ResBean.getHeadBean().getResMsg();
		logger.info("账户挂失销户resCode："+resCode+"resMsg:"+resMsg);
		if(bck08182ResBean == null){
			params.put("resCode",resCode);
			params.put("errMsg","调用接口08182失败");
			return params;
		}
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		bean.setXfdCount(bck08182ResBean.getBody().getXFD_COUNT()!=null && !"".equals(bck08182ResBean.getBody().getXFD_COUNT().trim())?bck08182ResBean.getBody().getXFD_COUNT().trim():"");//消费豆数量
		bean.setXfdAmt(bck08182ResBean.getBody().getXFD_AMT()!=null && !"".equals(bck08182ResBean.getBody().getXFD_AMT().trim())?bck08182ResBean.getBody().getXFD_AMT().trim():"");//消费豆金额
		bean.setLostJrnlNo(bck08182ResBean.getBody().getSVR_JRNL_NO()!=null && !"".equals(bck08182ResBean.getBody().getSVR_JRNL_NO())?bck08182ResBean.getBody().getSVR_JRNL_NO():"");//账户挂失的流水号
		bean.setLostApplNo(bck08182ResBean.getBody().getLOST_APPL_NO()!=null && !"".equals(bck08182ResBean.getBody().getLOST_APPL_NO())?bck08182ResBean.getBody().getLOST_APPL_NO():"");//挂失申请书号
		bean.setCanceAmt(bck08182ResBean.getBody().getPAID_PRINCIPAL()!=null && !"".equals(bck08182ResBean.getBody().getPAID_PRINCIPAL())?bck08182ResBean.getBody().getPAID_PRINCIPAL():"");//实付本金
		bean.setCancelRealInte(bck08182ResBean.getBody().getPAID_INTEREST()!=null && !"".equals(bck08182ResBean.getBody().getPAID_INTEREST())?bck08182ResBean.getBody().getPAID_INTEREST():"");//实付利息
		bean.setClearRate(bck08182ResBean.getBody().getCLEAR_RATE()!=null && !"".equals(bck08182ResBean.getBody().getCLEAR_RATE())?bck08182ResBean.getBody().getCLEAR_RATE():"");//清户利率
		bean.setXydAmt(bck08182ResBean.getBody().getADVN_INIT().trim()!=null && !"".equals(bck08182ResBean.getBody().getADVN_INIT().trim())?bck08182ResBean.getBody().getADVN_INIT().trim():"");//幸运豆
		//下载衍生品文件
		String fileName = bck08182ResBean.getBody().getFILE_NAME();
		if(fileName == null || "".equals(fileName.trim())){
			logger.info("销户衍生品文件下载路径为空");
		}
		List<AccCancelProFileBean> list  = null;
		try {
			// 下载文件
			FtpFileUtils.downloadFileByFTPClient(Property.FTP_IP_DOWN, Integer.parseInt(Property.FTP_PORT_DOWN), Property.FTP_USER_DOWN, Property.FTP_PWD_DOWN, fileName, Property.FTP_LOCAL_PATH + fileName);
			list = TextFileReader.paddingList(Property.FTP_LOCAL_PATH + fileName, AccCancelProFileBean.class);
		} catch (Exception e) {
			logger.error("销户衍生品文件下载失败"+e);
		}
		if(list == null){					
		    logger.info("销户衍生品信息获取失败");					
		}else if(list.size() == 0){					
			logger.info("销户衍生品返回文件信息为空");
		}
		params.put("ShdAmt",list);
		bean.getImportMap().put("cancelProFile", list);
		params.put("resCode",SUCCESS);
		return params;
	} 
	
	/**
	 * (核心)挂失补发（7天）【02834】-前置【08230】
	 * 
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public static Map inter08230(LostPubBean bean) throws Exception {
		// 获取数据
		Map<String, String> map = new HashMap<String, String>();
		if(bean.getAllPubAccNo().contains("-")){
			
			String[] split = bean.getAllPubAccNo().split("-");
			map.put("ACCT_NO", split[0]);//卡号
			map.put("SUB_ACCT_NO", split[1]);//子账号
			
		}else{
			
			map.put("ACCT_NO", bean.getAllPubAccNo());//账号
			map.put("SUB_ACCT_NO", "");//子账号
		}
		map.put("PRO_CODE", bean.getProCode());// 产品代码
		map.put("APP_NO", bean.getLostApplNo());// 申请书号
		map.put("CUST_NO", bean.getCustNo());// 客户号
		map.put("CUST_NAME", bean.getCustName());// 客户名称
		map.put("BAL_AMT", bean.getEndAmt());// 余额
		map.put("SAVE_TERM", bean.getDepTerm());// 存期
		map.put("LOST_DATE",  bean.getLostApplNo().substring(5,13));// 挂失日期
		map.put("PASS_WORD", bean.getAllPubAccPwd());// 密码
		map.put("CERT_TYPE1", "10100");// 证件类型
		map.put("CERT_NO1", bean.getAllPubIDNo());// 证件号码
		map.put("CRET_INST1", bean.getAllPubQfjg());// 发证机关
		map.put("CERT_TYPE2", "10100");// 证件类型(挂)
		map.put("CERT_NO2", bean.getAllPubIDNo());// 证件号码(挂)
		map.put("CRET_INST2", bean.getAllPubQfjg());// 发证机关(挂)
		map.put("NEW_CRET_NO", bean.getCertNoAdd());// 新凭证号码
		
		Map params = new HashMap();			
		//连接socket
		String reqMsg=CreateXmlMsg.BCK_08230(map);
		AllPublicTransSocketClient socketClient =new AllPublicTransSocketClient();
		socketClient.createSocket();			
		//发送请求报文、接收响应报文
		logger.info("请求报文："+reqMsg);
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);			
		//解析xml
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK08230ResBean.class);
		BCK08230ResBean bck08230ResBean = (BCK08230ResBean)reqXs.fromXML(resMsg);
			
		//获取返回报文错误码和错误信息
		String resCode = bck08230ResBean.getHeadBean().getResCode();
		String errMsg = bck08230ResBean.getHeadBean().getResMsg();
		logger.info("(核心)挂失补发（7天）【02834】-前置【08230】:resCode08230:"+resCode+"resMsg08230:"+errMsg);
		
		if(bck08230ResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","(核心)挂失补发（7天）接口失败");
			return params;
		}
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		
		String fileName = bck08230ResBean.getBody().getFILE_NAME()!=null && !"".equals(bck08230ResBean.getBody().getFILE_NAME().trim())?bck08230ResBean.getBody().getFILE_NAME().trim():"";//文件路径
		List<AccLostAndReturnInfoBean> resultList = null;
		try {
			
			FtpFileUtils.downloadFileByFTPClient(Property.FTP_IP_DOWN, Integer.parseInt(Property.FTP_PORT_DOWN), Property.FTP_USER_DOWN, Property.FTP_PWD_DOWN, fileName, Property.FTP_LOCAL_PATH + fileName);
			resultList = TextFileReader.paddingList(Property.FTP_LOCAL_PATH + fileName,AccLostAndReturnInfoBean.class);
			logger.info(resultList);
			
		} catch (Exception e) {
			
			logger.error("下载核心日期文件失败"+e);
			params.put("resCode","4444");
			params.put("errMsg","查询核心日期失败");
			return params;
		}
		if(resultList!=null && resultList.size()!=0){
			params.put("resList", resultList);
		}
		
		bean.setNewAccNo(bck08230ResBean.getBody().getNEW_ACCT_NO()!=null && !"".equals(bck08230ResBean.getBody().getNEW_ACCT_NO())?bck08230ResBean.getBody().getNEW_ACCT_NO():"");//如果是普通存单，则生成新账号
		bean.setLostJrnlNo(bck08230ResBean.getBody().getSVR_JRNL_NO()!=null && !"".equals(bck08230ResBean.getBody().getSVR_JRNL_NO())?bck08230ResBean.getBody().getSVR_JRNL_NO():"");//账户挂失补发的流水号
		params.put("resCode",SUCCESS);
		return params;
	}
	
	/**
	 * 一步解双挂并补发【02968】-前置【08257】
	 * 
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public static Map inter08257(LostPubBean bean) throws Exception {
		// 获取数据
		Map<String, String> map = new HashMap<String, String>();
		if(bean.getAllPubAccNo().contains("-")){
			
			String[] split = bean.getAllPubAccNo().split("-");
			map.put("ACCT_NO", split[0]);//卡号
			map.put("SUB_ACCT_NO", split[1]);//子账号
			
		}else{
			
			map.put("ACCT_NO", bean.getAllPubAccNo());//账号
			map.put("SUB_ACCT_NO", "");//子账号
		}
		map.put("CUST_NO", bean.getCustNo());// 客户号
		map.put("CUST_NAME", bean.getCustName());// 客户名称
		map.put("PASSWORD", bean.getReMakePwd());// 重置密码
		map.put("REI_PASSWORD", bean.getAllPubAccPwd());// 密码
		map.put("ID_TYPE", "10100");// 证件类型
		map.put("ID_NO", bean.getAllPubIDNo());// 证件号码(挂)
		map.put("CERT_NO", bean.getCertNo());// 凭证号码
		map.put("NEW_CERT_NO", bean.getCertNoAdd());// 新凭证号码
		map.put("APPL_NO", bean.getLostApplNo());// 申请书号
		
		Map params = new HashMap();			
		//连接socket
		String reqMsg=CreateXmlMsg.BCK_08257(map);
		AllPublicTransSocketClient socketClient =new AllPublicTransSocketClient();
		socketClient.createSocket();			
		//发送请求报文、接收响应报文
		logger.info("请求报文："+reqMsg);
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);			
		//解析xml
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK08257ResBean.class);
		BCK08257ResBean bck08257ResBean = (BCK08257ResBean)reqXs.fromXML(resMsg);
			
		//获取返回报文错误码和错误信息
		String resCode = bck08257ResBean.getHeadBean().getResCode();
		String errMsg = bck08257ResBean.getHeadBean().getResMsg();
		logger.info("一步解双挂并补发【02968】-前置【08257】:resCode08257:"+resCode+"resMsg08257:"+errMsg);
		
		if(bck08257ResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","一步解双挂并补发【02968】-前置【08257】");
			return params;
		}
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		
		String fileName = bck08257ResBean.getBody().getFILE_NAME()!=null && !"".equals(bck08257ResBean.getBody().getFILE_NAME().trim())?bck08257ResBean.getBody().getFILE_NAME().trim():"";//文件路径
		List<AccLostAndReturnInfoBean> resultList = null;
		try {
			
			FtpFileUtils.downloadFileByFTPClient(Property.FTP_IP_DOWN, Integer.parseInt(Property.FTP_PORT_DOWN), Property.FTP_USER_DOWN, Property.FTP_PWD_DOWN, fileName, Property.FTP_LOCAL_PATH + fileName);
			resultList = TextFileReader.paddingList(Property.FTP_LOCAL_PATH + fileName,AccLostAndReturnInfoBean.class);
			logger.info(resultList);
			
		} catch (Exception e) {
			
			logger.error("下载补发文件失败"+e);
			params.put("resCode","4444");
			params.put("errMsg","获取补发文件失败");
			return params;
		}
		if(resultList!=null && resultList.size()!=0){
			params.put("resList", resultList);
		}
		
		bean.setNewAccNo(bck08257ResBean.getBody().getNEW_ACCT_NO()!=null && !"".equals(bck08257ResBean.getBody().getNEW_ACCT_NO())?bck08257ResBean.getBody().getNEW_ACCT_NO():"");//如果是普通存单，则生成新账号
		bean.setLostJrnlNo(bck08257ResBean.getBody().getSVR_JRNL_NO()!=null && !"".equals(bck08257ResBean.getBody().getSVR_JRNL_NO())?bck08257ResBean.getBody().getSVR_JRNL_NO():"");//账户挂失补发的流水号
		params.put("resCode",SUCCESS);
		return params;
	}
	
	/**
	 * (卡)挂失期满销户（7天）【70008】-前置【08222】
	 * 
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public static Map inter08222(LostPubBean bean) throws Exception {
		// 获取数据
		Map<String, String> map = new HashMap<String, String>();
		
		map.put("CARD_NO", bean.getAllPubAccNo());// 卡号
		map.put("CARD_PWD", bean.getAllPubAccPwd());// 卡密码
		map.put("APPL_NO", bean.getLostApplNo());// 挂失申请书号
		map.put("LOST_DATE", bean.getLostApplNo().substring(5,13));// 挂失日期
		map.put("CASH_OR_CARRY", "1");// 现转标志
		map.put("OPPO_ACCT_NO", bean.getSelectCardNo());// 对方账号
		map.put("CERT_TYPE", "");// 凭证种类
		map.put("CERT_NO", "");// 凭证号
		map.put("ID_TYPE", "10100");// 证件类别
		map.put("ID_NO", bean.getAllPubIDNo());// 证件号
		map.put("CHL_NO", "08");// 交易渠道
		
		Map params = new HashMap();			
		//连接socket
		String reqMsg=CreateXmlMsg.BCK_08222(map);
		AllPublicTransSocketClient socketClient =new AllPublicTransSocketClient();
		socketClient.createSocket();			
		//发送请求报文、接收响应报文
		logger.info("请求报文："+reqMsg);
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);			
		//解析xml
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK08222ResBean.class);
		BCK08222ResBean bck08222ResBean = (BCK08222ResBean)reqXs.fromXML(resMsg);
		
		//获取返回报文错误码和错误信息
		String resCode = bck08222ResBean.getHeadBean().getResCode();
		String errMsg = bck08222ResBean.getHeadBean().getResMsg();
		logger.info("(卡)挂失期满销户（7天）【70008】-前置【08222】:resCode08222:"+resCode+"resMsg08222:"+errMsg);
		
		if(bck08222ResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","(卡)挂失期满销户（7天）接口失败");
			return params;
		}
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		bean.setLostJrnlNo(bck08222ResBean.getBody().getCARD_JRNL_NO()!=null && !"".equals(bck08222ResBean.getBody().getCARD_JRNL_NO())?bck08222ResBean.getBody().getCARD_JRNL_NO():"");//卡挂失销户的流水号
		bean.setCanceAmt(bck08222ResBean.getBody().getCANCEL_AMT()!=null && !"".equals(bck08222ResBean.getBody().getCANCEL_AMT())?bck08222ResBean.getBody().getCANCEL_AMT():"");//销户金额
		bean.setCancelRealInte(bck08222ResBean.getBody().getREAL_TAX()!=null && !"".equals(bck08222ResBean.getBody().getREAL_TAX())?bck08222ResBean.getBody().getREAL_TAX():"");//销户实付利息
		bean.setClearRate(bck08222ResBean.getBody().getCLN_TAX_RATE()!=null && !"".equals(bck08222ResBean.getBody().getCLN_TAX_RATE())?bck08222ResBean.getBody().getCLN_TAX_RATE():"");//销户清户利率
		params.put("resCode",SUCCESS);
		return params;
	}
	
	/**
	 * 改密销户一体化【70036】-前置【08260】
	 * 
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public static Map inter08260(LostPubBean bean) throws Exception {
		// 获取数据
		Map<String, String> map = new HashMap<String, String>();
		
		map.put("NEW_CARD_PWD", bean.getReMakePwd());//重置的密码
		map.put("OLD_CARD_PWD", bean.getAllPubAccPwd());//输入的密码
		map.put("CARD_TYPE", "");//卡类型
		map.put("CHG_REASON", "1");//挂失原因
		map.put("APPL_NO", bean.getLostApplNo());// 挂失申请书号
		map.put("LOST_DATE", bean.getLostApplNo().substring(5,13));// 挂失日期
		map.put("CUST_NAME", bean.getCustName());//姓名
		map.put("ID_TYPE", "10100");// 证件类别
		map.put("ID_NO", bean.getAllPubIDNo());// 证件号
		map.put("CHL_NO", "08");// 交易渠道
		map.put("CARD_NO", bean.getAllPubAccNo());// 卡号
		map.put("OPP_CARD_NO", bean.getSelectCardNo());// 对方账号
		map.put("CASH_FLAG", "1");// 现转标志
		
		
		Map params = new HashMap();			
		//连接socket
		String reqMsg=CreateXmlMsg.BCK_08260(map);
		AllPublicTransSocketClient socketClient =new AllPublicTransSocketClient();
		socketClient.createSocket();			
		//发送请求报文、接收响应报文
		logger.info("请求报文："+reqMsg);
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);			
		//解析xml
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK08260ResBean.class);
		BCK08260ResBean bck08260ResBean = (BCK08260ResBean)reqXs.fromXML(resMsg);
		
		//获取返回报文错误码和错误信息
		String resCode = bck08260ResBean.getHeadBean().getResCode();
		String errMsg = bck08260ResBean.getHeadBean().getResMsg();
		logger.info("(卡)挂失期满销户（7天）【70008】-前置【08260】:resCode08260:"+resCode+"resMsg08260:"+errMsg);
		
		if(bck08260ResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","(卡)挂失期满销户（7天）接口失败");
			return params;
		}
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		bean.setLostJrnlNo(bck08260ResBean.getBody().getCARD_JRNL_NO()!=null && !"".equals(bck08260ResBean.getBody().getCARD_JRNL_NO())?bck08260ResBean.getBody().getCARD_JRNL_NO():"");//卡挂失销户的流水号
		bean.setCanceAmt(bck08260ResBean.getBody().getCANCEL_AMT()!=null && !"".equals(bck08260ResBean.getBody().getCANCEL_AMT())?bck08260ResBean.getBody().getCANCEL_AMT():"");//销户金额
		bean.setCancelRealInte(bck08260ResBean.getBody().getPAID_INTEREST()!=null && !"".equals(bck08260ResBean.getBody().getPAID_INTEREST())?bck08260ResBean.getBody().getPAID_INTEREST():"");//销户实付利息
		bean.setClearRate(bck08260ResBean.getBody().getCLEAR_RATE()!=null && !"".equals(bck08260ResBean.getBody().getCLEAR_RATE())?bck08260ResBean.getBody().getCLEAR_RATE():"");//销户清户利率
		params.put("resCode",SUCCESS);
		return params;
	}
	
	/**
	 * 激活销户一体化【70037】-前置【08262】
	 * 
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public static Map inter08262(LostPubBean bean) throws Exception {
		// 获取数据
		Map<String, String> map = new HashMap<String, String>();
		
		map.put("NEW_CARD_PWD", bean.getReMakePwd());//重置的密码
		map.put("OLD_CARD_PWD", bean.getAllPubAccPwd());//输入的密码
		map.put("CARD_TYPE", "");//卡类型
		map.put("CHG_REASON", "5");//挂失原因(激活销户)
		map.put("APPL_NO", bean.getLostApplNo());// 挂失申请书号
		map.put("LOST_DATE", bean.getLostApplNo().substring(5,13));// 挂失日期
		map.put("CUST_NAME", bean.getCustName());//姓名
		map.put("ID_TYPE", "10100");// 证件类别
		map.put("ID_NO", bean.getAllPubIDNo());// 证件号
		map.put("CHL_NO", "08");// 交易渠道
		map.put("CARD_NO", bean.getAllPubAccNo());// 卡号
		map.put("OPP_CARD_NO", bean.getSelectCardNo());// 对方账号
		map.put("CASH_FLAG", "1");// 现转标志
		
		
		Map params = new HashMap();			
		//连接socket
		String reqMsg=CreateXmlMsg.BCK_08262(map);
		AllPublicTransSocketClient socketClient =new AllPublicTransSocketClient();
		socketClient.createSocket();			
		//发送请求报文、接收响应报文
		logger.info("请求报文："+reqMsg);
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);			
		//解析xml
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK08262ResBean.class);
		BCK08262ResBean bck08262ResBean = (BCK08262ResBean)reqXs.fromXML(resMsg);
		
		//获取返回报文错误码和错误信息
		String resCode = bck08262ResBean.getHeadBean().getResCode();
		String errMsg = bck08262ResBean.getHeadBean().getResMsg();
		logger.info("(卡)激活销户一体化【70037】-前置【08262】:resCode08262:"+resCode+"resMsg08262:"+errMsg);
		
		if(bck08262ResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","(卡)激活销户一体化【70037】-前置【08262】（7天）接口失败");
			return params;
		}
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		bean.setLostJrnlNo(bck08262ResBean.getBody().getCARD_JRNL_NO()!=null && !"".equals(bck08262ResBean.getBody().getCARD_JRNL_NO())?bck08262ResBean.getBody().getCARD_JRNL_NO():"");//卡挂失销户的流水号
		bean.setCanceAmt(bck08262ResBean.getBody().getCANCEL_AMT()!=null && !"".equals(bck08262ResBean.getBody().getCANCEL_AMT())?bck08262ResBean.getBody().getCANCEL_AMT():"");//销户金额
		bean.setCancelRealInte(bck08262ResBean.getBody().getPAID_INTEREST()!=null && !"".equals(bck08262ResBean.getBody().getPAID_INTEREST())?bck08262ResBean.getBody().getPAID_INTEREST():"");//销户实付利息
		bean.setClearRate(bck08262ResBean.getBody().getCLEAR_RATE()!=null && !"".equals(bck08262ResBean.getBody().getCLEAR_RATE())?bck08262ResBean.getBody().getCLEAR_RATE():"");//销户清户利率
		params.put("resCode",SUCCESS);
		return params;
	}
	
	/**
	 * (核心)挂失期满销户（7天）【02833】-前置【08231】
	 * 
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public static Map inter08231(LostPubBean bean) throws Exception {
		// 获取数据
		Map<String, String> map = new HashMap<String, String>();

		if(bean.getAllPubAccNo().contains("-")){
			
			String[] split = bean.getAllPubAccNo().split("-");
			map.put("ACCT_NO", split[0]);//卡号
			map.put("SUB_ACCT_NO", split[1]);//子账号
			
		}else{
			
			map.put("ACCT_NO", bean.getAllPubAccNo());//账号
			map.put("SUB_ACCT_NO", "");//子账号
		}
		map.put("PRO_CODE", bean.getProCode());// 产品代码
		map.put("APP_NO", bean.getLostApplNo());// 申请书号
		map.put("CUST_NO", bean.getCustNo());// 客户号
		map.put("CUST_NAME", bean.getCustName());// 客户名称
		if("2".equals(bean.getSolveLostType())){//存折
			map.put("BAL_AMT", bean.getEndAmt());// 余额(结存额)
		}else{
			map.put("BAL_AMT", bean.getTotalAmt());// 余额(存折余额)
		}
		map.put("INT_START_DATE", bean.getOpenDate());// 起息日期
		map.put("SAVE_TERM", bean.getDepTerm());// 存期
		map.put("OPEN_RATE", bean.getOpenRate()+"%");// 开户利率
		map.put("LOST_DATE", bean.getLostApplNo().substring(5,13));// 挂失日期
		map.put("PAY_COND", bean.getDrawCond());// 支付条件
		map.put("PASS_WORD", bean.getAllPubAccPwd());// 密码
		map.put("CERT_TYPE1", "10100");// 证件类型
		map.put("CERT_NO1", bean.getAllPubIDNo());// 证件号码
		map.put("CRET_INST1", bean.getAllPubQfjg());// 发证机关
		map.put("CERT_TYPE2", "10100");// 证件类型(挂)
		map.put("CERT_NO2", bean.getAllPubIDNo());// 证件号码(挂)
		map.put("CRET_INST2", bean.getAllPubQfjg());// 发证机关(挂)
		map.put("PAY_CURR", "00");// 支取币种
		map.put("PAY_AMT", bean.getTotalAmt());// 
		if("2".equals(bean.getSolveLostType())){//存折
			map.put("PAY_AMT", bean.getEndAmt());// 支取额(结存额)
		}else{//存单
			map.put("PAY_AMT", bean.getTotalAmt());// 支取额(存折余额)
		}
		map.put("AMT_DIRE", "1");// 本金走向
		map.put("INTER_DIRE", "1");// 利息走向
		map.put("BOOK_DATE", bean.getPreDate().trim().replace("/", ""));// 预约日期
		map.put("OPP_ACCT_NO", bean.getSelectCardNo());// 对方账号
		
		Map params = new HashMap();			
		//连接socket
		String reqMsg=CreateXmlMsg.BCK_08231(map);
		AllPublicTransSocketClient socketClient =new AllPublicTransSocketClient();
		socketClient.createSocket();			
		//发送请求报文、接收响应报文
		logger.info("请求报文："+reqMsg);
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);			
		//解析xml
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK08231ResBean.class);
		BCK08231ResBean bck08231ResBean = (BCK08231ResBean)reqXs.fromXML(resMsg);
			
		//获取返回报文错误码和错误信息
		String resCode = bck08231ResBean.getHeadBean().getResCode();
		String errMsg = bck08231ResBean.getHeadBean().getResMsg();
		logger.info("(核心)挂失期满销户（7天）【02833】-前置【08231】:resCode08231:"+resCode+"resMsg08231:"+errMsg);
		
		if(bck08231ResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","(核心)挂失期满销户（7天）接口失败");
			return params;
		}
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		bean.setLostJrnlNo(bck08231ResBean.getBody().getSVR_JRNL_NO()!=null && !"".equals(bck08231ResBean.getBody().getSVR_JRNL_NO())?bck08231ResBean.getBody().getSVR_JRNL_NO():"");//账户挂失销户的流水号
		bean.setCanceAmt(bck08231ResBean.getBody().getREAL_PRI()!=null && !"".equals(bck08231ResBean.getBody().getREAL_PRI())?bck08231ResBean.getBody().getREAL_PRI():"");//实付本金
		bean.setCancelRealInte(bck08231ResBean.getBody().getREAL_INTE()!=null && !"".equals(bck08231ResBean.getBody().getREAL_INTE())?bck08231ResBean.getBody().getREAL_INTE():"");//实付利息
		bean.setClearRate(bck08231ResBean.getBody().getCLEAR_RATE()!=null && !"".equals(bck08231ResBean.getBody().getCLEAR_RATE())?bck08231ResBean.getBody().getCLEAR_RATE():"");//清户利率
		bean.setXydAmt(bck08231ResBean.getBody().getADVN_INIT().trim()!=null && !"".equals(bck08231ResBean.getBody().getADVN_INIT().trim())?bck08231ResBean.getBody().getADVN_INIT().trim():"");//幸运豆
		bean.setXfdCount(bck08231ResBean.getBody().getXFD_COUNT().trim()!=null && !"".equals(bck08231ResBean.getBody().getXFD_COUNT().trim())?bck08231ResBean.getBody().getXFD_COUNT().trim():"");//消费豆
		//下载衍生品文件
		String fileName = bck08231ResBean.getBody().getPRO_FILE();
		if(fileName == null || "".equals(fileName.trim())){
			logger.info("销户衍生品文件下载路径为空");
		}
		List<AccCancelProFileBean> list  = null;
		try {
			// 下载文件
			FtpFileUtils.downloadFileByFTPClient(Property.FTP_IP_DOWN, Integer.parseInt(Property.FTP_PORT_DOWN), Property.FTP_USER_DOWN, Property.FTP_PWD_DOWN, fileName, Property.FTP_LOCAL_PATH + fileName);
			list = TextFileReader.paddingList(Property.FTP_LOCAL_PATH + fileName, AccCancelProFileBean.class);
		} catch (Exception e) {
			logger.error("销户衍生品文件下载失败"+e);
		}
		if(list == null){					
		    logger.info("销户衍生品信息获取失败");					
		}else if(list.size() == 0){					
			logger.info("销户衍生品返回文件信息为空");
		}
		params.put("ShdAmt",list);
		bean.getImportMap().put("cancelProFile", list);
		params.put("resCode",SUCCESS);
		return params;
	}
	
	
	/**
	 * 一步解双挂并销户【02969】-前置【08258】
	 * 
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public static Map inter08258(LostPubBean bean) throws Exception {
		// 获取数据
		Map<String, String> map = new HashMap<String, String>();

		if(bean.getAllPubAccNo().contains("-")){
			
			String[] split = bean.getAllPubAccNo().split("-");
			map.put("ACCT_NO", split[0]);//卡号
			map.put("SUB_ACCT_NO", split[1]);//子账号
			
		}else{
			
			map.put("ACCT_NO", bean.getAllPubAccNo());//账号
			map.put("SUB_ACCT_NO", "");//子账号
		}
		map.put("APPL_NO", bean.getLostApplNo());// 申请书号
		map.put("PASSWORD", bean.getReMakePwd());// 产品代码
		map.put("CLOSE_PASSWORD", bean.getAllPubAccPwd());// 密码
		map.put("ID_TYPE", "10100");// 证件类型
		map.put("ID_NO", bean.getAllPubIDNo());// 证件号码
		map.put("CURRENCY", "00");// 支取币种
		if("2".equals(bean.getSolveLostType())){//存折
			map.put("DRAW_AMT", bean.getEndAmt());// 支取额(结存额)
		}else{//存单
			map.put("DRAW_AMT", bean.getTotalAmt());// 支取额(存折余额)
		}
		map.put("PRI_WAY", "1");// 本金走向
		map.put("INT_WAY", "1");// 利息走向
		map.put("PRE_DATE", bean.getPreDate().trim().replace("/", ""));// 预约日期
		map.put("OPPO_ACCT_NO", bean.getSelectCardNo());// 对方账号
		map.put("CERT_NO", bean.getCertNo());// 凭证号

		
		Map params = new HashMap();			
		//连接socket
		String reqMsg=CreateXmlMsg.BCK_08258(map);
		AllPublicTransSocketClient socketClient =new AllPublicTransSocketClient();
		socketClient.createSocket();			
		//发送请求报文、接收响应报文
		logger.info("请求报文："+reqMsg);
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);			
		//解析xml
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK08258ResBean.class);
		BCK08258ResBean bck08258ResBean = (BCK08258ResBean)reqXs.fromXML(resMsg);
			
		//获取返回报文错误码和错误信息
		String resCode = bck08258ResBean.getHeadBean().getResCode();
		String errMsg = bck08258ResBean.getHeadBean().getResMsg();
		logger.info("一步解双挂并销户【02969】-前置【08258】:resCode08258:"+resCode+"resMsg08258:"+errMsg);
		
		if(bck08258ResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","一步解双挂并销户【02969】-前置【08258】");
			return params;
		}
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		bean.setLostJrnlNo(bck08258ResBean.getBody().getSVR_JRNL_NO()!=null && !"".equals(bck08258ResBean.getBody().getSVR_JRNL_NO())?bck08258ResBean.getBody().getSVR_JRNL_NO():"");//账户挂失销户的流水号
		bean.setCanceAmt(bck08258ResBean.getBody().getPAID_PRINCIPAL()!=null && !"".equals(bck08258ResBean.getBody().getPAID_PRINCIPAL())?bck08258ResBean.getBody().getPAID_PRINCIPAL():"");//实付本金
		bean.setCancelRealInte(bck08258ResBean.getBody().getPAID_INTEREST()!=null && !"".equals(bck08258ResBean.getBody().getPAID_INTEREST())?bck08258ResBean.getBody().getPAID_INTEREST():"");//实付利息
		bean.setClearRate(bck08258ResBean.getBody().getCLEAR_RATE()!=null && !"".equals(bck08258ResBean.getBody().getCLEAR_RATE())?bck08258ResBean.getBody().getCLEAR_RATE():"");//清户利率
		bean.setXydAmt(bck08258ResBean.getBody().getADVN_INIT().trim()!=null && !"".equals(bck08258ResBean.getBody().getADVN_INIT().trim())?bck08258ResBean.getBody().getADVN_INIT().trim():"");//幸运豆
		bean.setXfdCount(bck08258ResBean.getBody().getXFD_COUNT().trim()!=null && !"".equals(bck08258ResBean.getBody().getXFD_COUNT().trim())?bck08258ResBean.getBody().getXFD_COUNT().trim():"");//消费豆
		//下载衍生品文件
		String fileName = bck08258ResBean.getBody().getFILE_NAME();
		if(fileName == null || "".equals(fileName.trim())){
			logger.info("销户衍生品文件下载路径为空");
		}
		List<AccCancelProFileBean> list  = null;
		try {
			// 下载文件
			FtpFileUtils.downloadFileByFTPClient(Property.FTP_IP_DOWN, Integer.parseInt(Property.FTP_PORT_DOWN), Property.FTP_USER_DOWN, Property.FTP_PWD_DOWN, fileName, Property.FTP_LOCAL_PATH + fileName);
			list = TextFileReader.paddingList(Property.FTP_LOCAL_PATH + fileName, AccCancelProFileBean.class);
		} catch (Exception e) {
			logger.error("销户衍生品文件下载失败"+e);
		}
		if(list == null){					
		    logger.info("销户衍生品信息获取失败");					
		}else if(list.size() == 0){					
			logger.info("销户衍生品返回文件信息为空");
		}
		params.put("ShdAmt",list);
		bean.getImportMap().put("cancelProFile", list);
		params.put("resCode",SUCCESS);
		return params;
	}
	
	/**
	 * 解挂【75002】-前置【08198】卡
	 */
	public static Map inter08198(LostPubBean bean)throws Exception{
		//获取数据
		Map<String, String> map = new HashMap<String, String>();
		if(bean.getApplInfos().getLostType()!=null && !"".equals(bean.getApplInfos().getLostType())){
			if("4".equals(bean.getApplInfos().getLostType().trim())){
				map.put("LOST_TYPE", "4");//挂式类型
				map.put("CARD_PIN", "");
			}else{
				map.put("CARD_PIN", bean.getAllPubAccPwd());//卡密码
				map.put("LOST_TYPE", "1");//挂式类型
			}
		}
		map.put("ID_TYPE", "10100");//证件类别
		map.put("ID_NO", bean.getAllPubIDNo());//证件号
		map.put("CARD_NO", bean.getAllPubAccNo());//卡号
		map.put("LOST_DATE", bean.getLostDate());//挂失日期
		map.put("APPL_NO", bean.getLostApplNo());//申请书号
		map.put("CHL_NO","08");//渠道号
		map.put("FEE_FLAG", "0");//是否收手续费
		map.put("FEE_AMT", "");//手续费金额
		map.put("DE_REASON", "");//减免原因
		map.put("CASH_FLAG", "");//现转标识
		Map params = new HashMap();
		String reqMsg=CreateXmlMsg.BCK_08198(map);
		AllPublicTransSocketClient socketClient =new AllPublicTransSocketClient();
		socketClient.createSocket();
		logger.info("请求报文："+reqMsg);
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK08198ResBean.class);
		BCK08198ResBean bck08198ResBean = (BCK08198ResBean)reqXs.fromXML(resMsg);
		if(bck08198ResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","调用接口08198失败");
			return params;
		}
		//获取返回报文错误码和错误信息
		String resCode = bck08198ResBean.getHeadBean().getResCode();
		String errMsg = bck08198ResBean.getHeadBean().getResMsg();
		logger.info("挂失解挂失败resCode:"+resCode+"resMsg"+errMsg);
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		
		bean.setLostJrnlNo(bck08198ResBean.getBody().getSVR_JRNL_NO());//解挂流水号
		
		logger.info("查询成功后信息："+bean);
		params.put("resCode",SUCCESS);
		params.put("errMsg",errMsg);
		return params;
	}
	
	
	/**
	 * 账户正式挂失解挂【02817】-前置【08194】非卡
	 */
	public static Map inter08194(LostPubBean bean)throws Exception{
		//获取数据
		Map<String, String> map = new HashMap<String, String>();
		map.put("ACCT_NO", bean.getAllPubAccNo());//账户
		map.put("PRO_CODE", bean.getProCode());//产品代码
		map.put("CUST_NAME", bean.getCustName());//客户名称
		if(bean.getApplInfos().getLostType()!=null && !"".equals(bean.getApplInfos().getLostType()) && bean.getApplInfos().getLostType().startsWith("01")){
			if("6".equals(bean.getApplInfos().getLostType().substring(2,3))){
				map.put("DISL_TYPE", "6");//双挂失
			}else if("2".equals(bean.getApplInfos().getLostType().substring(2,3))){
				map.put("DISL_TYPE", "3");//单折挂失
			}
		}
		map.put("LOST_DATE", bean.getLostDate());//挂失日期
		map.put("CRET_INST", bean.getAllPubQfjg());//发证机关
		map.put("LOST_APP_NO", bean.getLostApplNo());//挂失申请书号
		map.put("CHL_NO","0035");//渠道号
		Map params = new HashMap();
		String reqMsg=CreateXmlMsg.BCK_08194(map);
		AllPublicTransSocketClient socketClient =new AllPublicTransSocketClient();
		socketClient.createSocket();
		logger.info("请求报文："+reqMsg);
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK08194ResBean.class);
		BCK08194ResBean bck08194ResBean = (BCK08194ResBean)reqXs.fromXML(resMsg);
		if(bck08194ResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","调用接口08194失败");
			return params;
		}
		//获取返回报文错误码和错误信息
		String resCode = bck08194ResBean.getHeadBean().getResCode();
		String errMsg = bck08194ResBean.getHeadBean().getResMsg();
		logger.info("挂失解挂失败resCode:"+resCode+"resMsg"+errMsg);
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		
		bean.setLostJrnlNo(bck08194ResBean.getBody().getSVR_JRNL_NO());//解挂流水号
		
		logger.info("查询成功后信息："+bean);
		params.put("resCode",SUCCESS);
		params.put("errMsg",errMsg);
		return params;
	}
}
