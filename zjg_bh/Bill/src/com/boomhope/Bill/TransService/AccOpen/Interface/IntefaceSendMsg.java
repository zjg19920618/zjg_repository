package com.boomhope.Bill.TransService.AccOpen.Interface;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.boomhope.Bill.Public.GlobalParameter;
import com.boomhope.Bill.TransService.AccOpen.Bean.AccPublicBean;
import com.boomhope.Bill.TransService.AccOpen.Bean.OpenJxJInfo;
import com.boomhope.Bill.TransService.AccOpen.Bean.ProductRateInfo;
import com.boomhope.Bill.TransService.AccOpen.Bean.ProductRateInfo1;
import com.boomhope.Bill.TransService.AccOpen.Bean.ProductRateInfo2;
import com.boomhope.Bill.TransService.AccOpen.Bean.QXRateInfosBean;
import com.boomhope.Bill.TransService.AccOpen.Bean.SearchInteInfo;
import com.boomhope.Bill.TransService.AccOpen.Bean.SearchProducRateInfo;
import com.boomhope.Bill.TransService.AccOpen.Bean.SearchRYCDetail;
import com.boomhope.Bill.TransService.AccOpen.Bean.accOpenProFileBean02808And03870;
import com.boomhope.Bill.TransService.AccTransfer.TransferUtil.SocketClient;
import com.boomhope.Bill.Util.DateUtil;
import com.boomhope.Bill.Util.FileUtil;
import com.boomhope.Bill.Util.FtpFileUtils;
import com.boomhope.Bill.Util.Property;
import com.boomhope.Bill.Util.TextFileReader;
import com.boomhope.Bill.Util.UtilPreFile;
import com.boomhope.tms.message.XStreamNameCoder;
import com.boomhope.tms.message.account.in.BCK02864ResBean;
import com.boomhope.tms.message.account.in.BCK02867ResBean;
import com.boomhope.tms.message.account.in.BCK03510ReqBean;
import com.boomhope.tms.message.account.in.BCK03510ReqBodyBean;
import com.boomhope.tms.message.account.in.BCK03510ResBean;
import com.boomhope.tms.message.account.in.BCK03512ResBean;
import com.boomhope.tms.message.account.in.BCK03845ResBean;
import com.boomhope.tms.message.account.in.BCK03870ResBean;
import com.boomhope.tms.message.account.in.BCK07505ResBean;
import com.boomhope.tms.message.account.in.BCK07506ResBean;
import com.boomhope.tms.message.account.in.tms.TmsAccountSaveOrderResBean;
import com.boomhope.tms.message.cdjmac.CdjOpenAccResBean;
import com.boomhope.tms.message.in.InReqHeadBean;
import com.boomhope.tms.message.in.InResBean;
import com.boomhope.tms.message.in.bck.BCK01597ResBean;
import com.boomhope.tms.message.in.bck.BCK03445ResBean;
import com.boomhope.tms.message.in.bck.BCK03514ResBean;
import com.boomhope.tms.message.in.bck.BCK03521ResBean;
import com.boomhope.tms.message.in.bck.BCK03524ResBean;
import com.boomhope.tms.message.in.bck.BCK04422ResBean;
import com.boomhope.tms.message.in.bck.BCK07601ResBean;
import com.boomhope.tms.message.in.bck.BCK07659ResBean;
import com.boomhope.tms.message.in.bck.BCK07660ResBean;
import com.boomhope.tms.message.in.bck.BCK07670ResBean;
import com.boomhope.tms.message.in.bck.BCK08021ResBean;
import com.boomhope.tms.message.in.tms.Tms0005ResBean;
import com.boomhope.tms.message.in.tms.Tms0005ResBodyBean;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XppDriver;

/**
 * 接口发送类
 * @author Wang.xm
 *
 */
public class IntefaceSendMsg {
	
	static Logger logger = Logger.getLogger(IntefaceSendMsg.class);
	
	//报文返回成功
	public static String SUCCESS = "000000";
	public static String KEY_PRODUCT_RATES="KEY_PRODUCT_RATES";
	public static String PRODUCT_INFOS="PRODUCT_INFOS";
	public static String RESULT="RESULT";
	
	/**
	 * 查询灰名单
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static Map inter01597(AccPublicBean bean)throws Exception{
		//获取数据
		Map<String,String> map = new HashMap<String,String>();
		//证件号码
		map.put("ID_NUMBER1",bean.getIdCardNo());
		//证件类型
		map.put("ID_TYPE1", "1");
		//证件姓名
		map.put("ID_NAME1", bean.getIdCardName());
		map.put("CARD_NO1", bean.getCardNo());
		//卡号
		map.put("ACC_NO1", bean.getCardNo());
		//程序标识
		map.put("PROGRAM_FLAG","100501");
		Map<String,String> params = new HashMap<String,String>();
		String reqMsg=CreateXmlMsg.BCK_01597(map);
		SocketClient socketClient =new SocketClient();
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
				params.put("errMsg","该客户为涉案客户，禁止交易");
				return params;
			}if("2".equals(String.valueOf(svrno1.charAt(i)))){//检查为灰名单的时候
				params.put("resCode","0020");
				params.put("errMsg","该客户可疑，谨防诈骗，请到我行柜台办理");
				return params;
			}	
		}
		params.put("resCode",resCode);
		return params;
	} 
	
	/**
	 * 账户信息查询
	 */
	public static Map inter03521(AccPublicBean bean)throws Exception{
		//获取数据
		Map<String, String> map = new HashMap<String, String>();
		map.put("ACCT_NO", bean.getCardNo());//账号
		map.put("PASSWORD", "");//密码
		map.put("PIN_VAL_FLAG", "0");//验密标志(1-有密码 ，0-无密码)
		
		Map params = new HashMap();
		
		//连接socket
		String reqMsg=CreateXmlMsg.BCK_03521(map);
		SocketClient socketClient =new SocketClient();
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
		if("Q".equals(bck03521ResBean.getBody().getACCT_STAT())){//销户
			logger.info("银行卡销户 ");
			params.put("resCode","5555");
			params.put("errMsg","您的账户为销户状态，不能办理此业务");
			return params;
		}else if("T".equals(bck03521ResBean.getBody().getACCT_STAT())){
			logger.info("银行卡停用 ");
			params.put("resCode","5555");
			params.put("errMsg","您的账户为停用状态，不能办理此业务");
			return params;
		}else if("L".equals(bck03521ResBean.getBody().getACCT_STAT())){
			logger.info("银行卡证件到期中止 ");
			params.put("resCode","5555");
			params.put("errMsg","您的账户证件到期中止，不能办理此业务");
			return params;
		}else if("M".equals(bck03521ResBean.getBody().getACCT_STAT())){
			logger.info("银行卡中止且停用 ");
			params.put("resCode","5555");
			params.put("errMsg","您的账户中止且停用，不能办理此业务");
			return params;
		}else if("N".equals(bck03521ResBean.getBody().getACCT_STAT())){
			logger.info("银行卡中止且非柜面停用 ");
			params.put("resCode","5555");
			params.put("errMsg","您的账户中止且非柜面停用，不能办理此业务");
			return params;
		}
		
		if("G".equals(bck03521ResBean.getBody().getLOST_STAT())){//挂失
			logger.info("银行卡挂失 ");
			params.put("resCode","5555");
			params.put("errMsg","您的银行卡为挂失状态，不能办理此业务");
			return params;
		}
		if("D".equals(bck03521ResBean.getBody().getHOLD_STAT())){//冻结
			logger.info("银行卡封闭冻结 ");
			params.put("resCode","5555");
			params.put("errMsg","您的银行卡为封闭冻结状态，不能办理此业务");
			return params;
			
		}else if("S".equals(bck03521ResBean.getBody().getHOLD_STAT())){
			logger.info("银行卡只收不付 ");
			params.put("resCode","5555");
			params.put("errMsg","您的银行卡为只收不付状态，不能办理此业务");
			return params;
		}else if("Z".equals(bck03521ResBean.getBody().getHOLD_STAT())){
			logger.info("银行卡全额止付");
			params.put("resCode","5555");
			params.put("errMsg","您的银行卡为全额止付状态，不能办理此业务");
			return params;
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
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map card07601(AccPublicBean bean)throws Exception{
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
		SocketClient socketClient =new SocketClient();
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
				logger.info("卡渠道限制");
				params.put("resCode","5555");
				params.put("errMsg","您的银行卡有渠道限制，不能办理此业务");
				return params;
			}	
		}
		
		params.put("resCode",SUCCESS);
		return params;		
	}
	
	/**
	 * 产品利率信息查询
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map inter02864(AccPublicBean bean)throws Exception{
		//获取数据
		Map<String,String> map = new HashMap<String,String>();
		map.put("CUST_NO", bean.getCustNo());
		map.put("FLOAT_FLAG","1");//非必输1-浮动，非1-不浮动
		map.put("CHL_ID",bean.getChlId());//渠道模块标识
		map.put("PROD_CODE", bean.getProductCode());// 产品名称
		map.put("RATE_DATE", bean.getSvrDate());// 利率日期
		map.put("CUST_LEVEL", bean.getCustLevel());//客户评级
		Map params = new HashMap();
		String reqMsg=CreateXmlMsg.BCK_02864(map);
		SocketClient socketClient =new SocketClient();
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
		List<SearchProducRateInfo> list = TextFileReader.paddingList(Property.FTP_LOCAL_PATH + fileName, SearchProducRateInfo.class);
		logger.info(list);
		params.put(KEY_PRODUCT_RATES, list);
		params.put("float", bck02864ResBean.getBody().getFLOAT_SUM());//浮动
		params.put("resCode", SUCCESS);
		bean.setFloatSum(bck02864ResBean.getBody().getFLOAT_SUM());
		return params;
	} 
	
	/**
	 * 产品利率信息查询
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map inter02867(AccPublicBean bean)throws Exception{
		//获取数据
		Map<String,String> map = new HashMap<String,String>();
		map.put("CUST_NO", bean.getCustNo());
		map.put("PRODUCT_CODE", bean.getProductCode());
		map.put("QRY_TYPE",bean.getProductType());
		map.put("DEP_AMT", bean.getMinAmt());// 最低起存
		map.put("MAX_AMT", bean.getMaxAmt());// 最高起存
		map.put("CHL_ID", bean.getChlId());// 设置渠道模块标识
		map.put("CUST_LEVEL", bean.getCustLevel());//客户评级
		Map params = new HashMap();
		String reqMsg=CreateXmlMsg.BCK_02867(map);
		SocketClient socketClient =new SocketClient();
		socketClient.createSocket();
		logger.info("请求报文："+reqMsg);
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK02867ResBean.class);
		BCK02867ResBean bck02867ResBean = (BCK02867ResBean)reqXs.fromXML(resMsg);
		String resCode02867 = bck02867ResBean.getHeadBean().getResCode();
		String resMsg02867 = bck02867ResBean.getHeadBean().getResMsg();
		logger.info("resCode02867"+resCode02867+"resMsg02867"+resMsg02867);
		if(!"000000".equals(resCode02867)){
			params.put("resCode",resCode02867);
			params.put("errMsg",resMsg02867);
			return params;
		}else{
			params.put("resCode",resCode02867);
			params.put("errMsg",resMsg02867);
		}
		String fileName = bck02867ResBean.getBody().getPRODUCT_FI_NM();
		FtpFileUtils.downloadFileByFTPClient(Property.FTP_IP_DOWN, Integer.parseInt(Property.FTP_PORT_DOWN), Property.FTP_USER_DOWN, Property.FTP_PWD_DOWN, fileName, Property.FTP_LOCAL_PATH + fileName);
		if("1".equals(map.get("QRY_TYPE"))) {
			List<ProductRateInfo1> list = TextFileReader.paddingList(Property.FTP_LOCAL_PATH + fileName, ProductRateInfo1.class);
			params.put(KEY_PRODUCT_RATES, list);
			logger.info("产品查询类型："+map.get("QRY_TYPE")+list);
		}else if("2".equals(map.get("QRY_TYPE"))||"3".equals(map.get("QRY_TYPE"))) {
			List<ProductRateInfo2> list = TextFileReader.paddingList(Property.FTP_LOCAL_PATH + fileName, ProductRateInfo2.class);
			params.put(KEY_PRODUCT_RATES, list);
			logger.info("产品查询类型："+map.get("QRY_TYPE")+list);
		}else if("4".equals(map.get("QRY_TYPE"))) {
			List<ProductRateInfo> list = TextFileReader.paddingList(Property.FTP_LOCAL_PATH + fileName, ProductRateInfo.class);
			params.put(KEY_PRODUCT_RATES, list);
			logger.info("产品查询类型："+map.get("QRY_TYPE")+list);
		}
		return params;
	} 
	
	/**
	 * 账户信息查询
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map inter03445(AccPublicBean bean)throws Exception{
		//获取数据
		Map<String,String> map = new HashMap<String,String>();
		map.put("ID_NO", bean.getIdCardNo());
		map.put("CUST_NAME", bean.getIdCardName());
		map.put("ID_TYPE", "10100");
		Map<String,String> params = new HashMap<String,String>();
		String reqMsg=CreateXmlMsg.BCK_03445(map);
		SocketClient socketClient =new SocketClient();
		socketClient.createSocket();
		logger.info("请求报文："+reqMsg);
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK03445ResBean.class);
		BCK03445ResBean bck03445ResBean = (BCK03445ResBean)reqXs.fromXML(resMsg);
		if(bck03445ResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","调用接口03445失败");
			return params;
		}
		String resCode = bck03445ResBean.getHeadBean().getResCode();
		String errMsg = bck03445ResBean.getHeadBean().getResMsg();
		logger.info("证件信息查询resCode"+resCode+"证件信息查询resMsg"+resMsg);
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		//设置核心流水号
		bean.setSvrDate(bck03445ResBean.getBODY().getSVR_DATE());
		params.put("resCode",resCode);
		
		String fileName = bck03445ResBean.getBODY().getFILE_NAME();
		try {
			// 下载文件
			FtpFileUtils.downloadFileByFTPClient(Property.FTP_IP_DOWN, Integer.parseInt(Property.FTP_PORT_DOWN), Property.FTP_USER_DOWN, Property.FTP_PWD_DOWN, fileName, Property.FTP_LOCAL_PATH + fileName);
		} catch (Exception e) {
			logger.error("身份证文件下载失败"+e);
			params.put("resCode","4444");
			params.put("errMsg","身份证文件下载失败");
			return params;
		}
		List<String> custInfoList = null;
		try {
			custInfoList = UtilPreFile.preCustInfo(Property.FTP_LOCAL_PATH+fileName);
		} catch (Exception e) {
			logger.error("身份证文件解析失败"+e);
			params.put("resCode","4444");
			params.put("errMsg","身份证文件解析失败");
			return params;
		}
		bean.setCustNo(custInfoList.get(0).trim());
		bean.setIdCardName(custInfoList.get(2).trim());
		bean.setIdCardNo(custInfoList.get(5).trim());
		bean.setSvrRetCode(bck03445ResBean.getBODY().getSVR_RET_CODE());
		params.put("resCode",SUCCESS);
		return params;
	} 
	
	/**
	 * 预计利息信息查询（24小时）
	 * @param bean
	 * @return	
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map inter03510(AccPublicBean bean)throws Exception{
		//获取数据
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("PROD_CODE", bean.getProductCode());		//产品代码	账号不输时必输
		map.put("AMT",bean.getMoney());		//金额	账号不输时必输
		map.put("OPEN_DATE", bean.getSvrDate());	//开户日	必输	
		map.put("CUST_NO", bean.getCustNo());	//	客户号	必输
		map.put("MonthsUpper", bean.getMonthsUpper()+bean.getDepUnit());//取款期	必输
		map.put("CHL_ID", bean.getChlId());//渠道模块标识
		map.put("ACCT_NO", "");//账号
		map.put("SUB_ACCT_NO", "");//子账号
		map.put("CUST_LEVEL", bean.getCustLevel());//客户评级
		Map params = new HashMap();
		BCK03510ResBean bck03510ResBean=searchInteInfo(bean, map);
		if(bck03510ResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","调用接口03510失败");
			return params;
		}
		
		String resCode = bck03510ResBean.getHeadBean().getResCode();
		String errMsg = bck03510ResBean.getHeadBean().getResMsg();
		logger.info("预计利息信息查询resCode："+resCode+"resMsg:"+errMsg);
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		String fileName = bck03510ResBean.getBody().getFILE_NAME();
		FtpFileUtils.downloadFileByFTPClient(Property.FTP_IP_DOWN, Integer.parseInt(Property.FTP_PORT_DOWN), Property.FTP_USER_DOWN, Property.FTP_PWD_DOWN, fileName, Property.FTP_LOCAL_PATH + fileName);
		List<SearchInteInfo> list = TextFileReader.paddingList(Property.FTP_LOCAL_PATH + fileName, SearchInteInfo.class);
		if(list!=null&&list.size()>0){
			SearchInteInfo info=list.get(0);
			bean.setInte(info.getRegularInte());	//定期利息
			bean.setRate(info.getRate());          //利率
		}
		logger.info("预计利息查询成功后返回信息："+list);
		String actFileName = bck03510ResBean.getBody().getACT_RWD_FILE_NAME();
		FtpFileUtils.downloadFileByFTPClient(Property.FTP_IP_DOWN, Integer.parseInt(Property.FTP_PORT_DOWN), Property.FTP_USER_DOWN, Property.FTP_PWD_DOWN, actFileName, Property.FTP_LOCAL_PATH + actFileName);
		List<QXRateInfosBean> list1 = TextFileReader.paddingList(Property.FTP_LOCAL_PATH + actFileName, QXRateInfosBean.class);
		if(list1!=null && list1.size()>0){
			bean.getMapInfos().put("qxRateList", list1);
		}
		logger.info("活动奖励文件："+list1);
		params.put("resCode",SUCCESS);
		return params;
	} 
	
	/**
	 * 如意存明细查询
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map inter03512(AccPublicBean bean)throws Exception{
		//获取数据
		Map<String, String> map = new HashMap<String, String>();
		map.put("CUST_NO", bean.getCustNo());	//	客户号	必输
		String reqMsg=CreateXmlMsg.BCK_03512(map);
		SocketClient socketClient =new SocketClient();
		socketClient.createSocket();
		logger.info("请求报文："+reqMsg);
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK03512ResBean.class);
		BCK03512ResBean bck03512ResBean = (BCK03512ResBean)reqXs.fromXML(resMsg);
		Map params = new HashMap();
		if(bck03512ResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","调用接口03512失败");
			return params;
		}
		String resCode = bck03512ResBean.getHeadBean().getResCode();
		String errMsg = bck03512ResBean.getHeadBean().getResMsg();
		logger.info("如意存明细查询resCode"+resCode+"如意存明细查询resMsg"+resMsg);
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}else{
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
		}
		String fileName = bck03512ResBean.getBody().getFILE_NAME();
		FtpFileUtils.downloadFileByFTPClient(Property.FTP_IP_DOWN, Integer.parseInt(Property.FTP_PORT_DOWN), Property.FTP_USER_DOWN, Property.FTP_PWD_DOWN, fileName, Property.FTP_LOCAL_PATH + fileName);
		List<SearchRYCDetail> list=TextFileReader.paddingList(Property.FTP_LOCAL_PATH + fileName, SearchRYCDetail.class);
		params.put("resCode",SUCCESS);
		params.put(PRODUCT_INFOS, list);
		logger.info("如意存明细查询成功后返回信息："+list);
		return params;
	} 
	
	/**
	 * 存单打印
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map inter03514(AccPublicBean bean)throws Exception{
		//获取数据
		Map<String, String> map = new HashMap<String, String>();
		map.put("ACCT_NO", bean.getCardNo().trim());//账号
		map.put("SUB_ACCT_NO", bean.getSubAcctNo().trim());//子账号
		map.put("CERT_NO_ADD", bean.getCertNoAdd());//凭证号
		Map params = new HashMap();
		String reqMsg=CreateXmlMsg.BCK_03514(map);
		SocketClient socketClient =new SocketClient();
		socketClient.createSocket();
		logger.info("请求报文："+reqMsg);
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK03514ResBean.class);
		BCK03514ResBean bck03514ResBean = (BCK03514ResBean)reqXs.fromXML(resMsg);
		if(bck03514ResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","调用接口03514失败");
			return params;
		}
		
		String resCode = bck03514ResBean.getHeadBean().getResCode();
		String errMsg = bck03514ResBean.getHeadBean().getResMsg();
		logger.info("打印存单resCode:"+resCode+"resMsg"+errMsg);
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		params.put("resCode",SUCCESS);
		return params;
	} 
	
	/**
	 * 卡信息查询
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map inter03845(AccPublicBean bean)throws Exception{
		//获取数据
		Map<String, String> map = new HashMap<String, String>();
		map.put("CARD_NO", bean.getCardNo());//卡号
		map.put("SUB_ACCT_NO", "");//子账号
		map.put("PASSWD", bean.getCardPwd());//卡密码
		if("1".equals(bean.getCardIsPin())){//是否验密值为1时则必须进行验密
			map.put("ISPIN",bean.getCardIsPin());			
		}else{//否则不需要验密
			map.put("ISPIN", "0");
		}
		Map params = new HashMap();
		String reqMsg=CreateXmlMsg.BCK_03845(map);
		SocketClient socketClient =new SocketClient();
		socketClient.createSocket();
		logger.info("请求报文："+reqMsg);
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK03845ResBean.class);
		BCK03845ResBean bck03845ResBean = (BCK03845ResBean)reqXs.fromXML(resMsg);
		if(bck03845ResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","调用接口03845失败");
			return params;
		}
		//获取返回报文错误码和错误信息
		String resCode = bck03845ResBean.getHeadBean().getResCode();
		String errMsg = bck03845ResBean.getHeadBean().getResMsg();
		logger.info("卡信息查询resCode:"+resCode+"resMsg:"+errMsg);
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		if(bck03845ResBean.getBody().getCARD_STAT()!=null && !"".equals(bck03845ResBean.getBody().getCARD_STAT().trim())){
			
			if("N".equals(bck03845ResBean.getBody().getCARD_STAT())){
				logger.info("银行卡未激活 ");
				params.put("resCode","5555");
				params.put("errMsg","您的银行卡未激活，不能办理此业务");
				return params;
			}else if(!"0".equals(bck03845ResBean.getBody().getCARD_STAT())){
				logger.info("卡状态异常");
				params.put("resCode","5555");
				params.put("errMsg","您的银行卡状态异常，不能办理此业务");
				return params;
			}
		}
		
		if(bck03845ResBean.getBody().getACCT_STAT()!=null && !"".equals(bck03845ResBean.getBody().getACCT_STAT().trim())){
			
			String utilStat = bck03845ResBean.getBody().getACCT_STAT().trim();
			
			if("1".equals(String.valueOf(utilStat.charAt(1)))){
				logger.info("卡为封闭冻结状态");
				params.put("resCode","5555");
				params.put("errMsg","您的银行卡为封闭冻结状态，不能办理此业务");
				return params;
			}
			else if("1".equals(String.valueOf(utilStat.charAt(3)))){
				logger.info("卡为只收不付状态");
				params.put("resCode","5555");
				params.put("errMsg","您的银行卡为柜面只收不付状态，不能办理此业务");
				return params;
			}
			else if("2".equals(String.valueOf(utilStat.charAt(3)))){
				logger.info("卡为电信诈骗全额止付状态");
				params.put("resCode","5555");
				params.put("errMsg","您的银行卡为电信诈骗全额止付状态，不能办理此业务");
				return params;
			}else if("3".equals(String.valueOf(utilStat.charAt(3)))){
				logger.info("卡为电信诈骗全额冻结状态");
				params.put("resCode","5555");
				params.put("errMsg","您的银行卡为电信诈骗全额冻结状态，不能办理此业务");
				return params;
			}
			else if("1".equals(String.valueOf(utilStat.charAt(4))) || "2".equals(String.valueOf(utilStat.charAt(4)))){
				logger.info("卡为挂失状态");
				params.put("resCode","5555");
				params.put("errMsg","您的银行卡为挂失状态，不能办理此业务");
				return params;
			}else if("1".equals(String.valueOf(utilStat.charAt(11))) || "2".equals(String.valueOf(utilStat.charAt(11)))){
				logger.info("卡为挂失状态");
				params.put("resCode","5555");
				params.put("errMsg","您的银行卡为挂失状态，不能办理此业务");
				return params;
			}
			try {
				if("2".equals(String.valueOf(utilStat.charAt(21)))){
					logger.info("卡为全额止付状态");
					params.put("resCode","5555");
					params.put("errMsg","您的银行卡为全额止付状态，不能办理此业务");
					return params;
				}
			} catch (Exception e) {
				logger.error("卡状态校验失败");
			}
		}
		bean.setIdCardNo(bck03845ResBean.getBody().getID_NO()!=null?bck03845ResBean.getBody().getID_NO().trim():"");
		bean.setIdCardName(bck03845ResBean.getBody().getACCT_NAME()!=null?bck03845ResBean.getBody().getACCT_NAME().trim():"");
		bean.setCardAmt(bck03845ResBean.getBody().getAVAL_BAL());
		bean.setZzAmt(bck03845ResBean.getBody().getZZ_AMT().trim());
		bean.setBeiZzAmt(bck03845ResBean.getBody().getZZ_AMT().trim());
		BigDecimal amt1 = new BigDecimal(bck03845ResBean.getBody().getZZ_AMT().trim());
		BigDecimal amt2 = new BigDecimal(Property.getProperties().getProperty("acc_open_ckeckIdAmt"));
		if(amt1.compareTo(amt2)>=0){
			bean.setMoreThanAmt("0");
		}else{
			bean.setMoreThanAmt("1");
		}
		String status=bck03845ResBean.getBody().getACCT_STAT();
		String stat=bck03845ResBean.getBody().getCARD_STAT();
		if(!"0".equals(stat.trim())){
			params.put("resCode","9999");
			params.put("errMsg","抱歉此卡处于非正常状态");
			logger.info("卡查询信息结果为卡为非正常状态。");
			return params;
		}
		params.put("resCode",SUCCESS);
		return params;
	} 
	
	/**
	 * 开户
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map inter03870(AccPublicBean bean)throws Exception{
		//获取数据
		Map<String, String> map = new HashMap<String, String>();
		map.put("TRAN_CHANNEL", "08");//渠道号  00-柜面 ，50-手机银行，08-存单机
		map.put("CURRENCY","00");//币种
		map.put("CAL_MODE","1");//结算方式  0-现金，1-转账，默认转账
		map.put("ID_TYPE","10100");//证件类型
		map.put("CASH_ANALY_NO","0");//现金分析号
		
		map.put("CARD_NO", bean.getCardNo());//卡号
		map.put("PASSWORD", bean.getCardPwd());//卡密码
		map.put("ID_NO",bean.getIdCardNo());//证件代号
		map.put("CUST_NO",bean.getCustNo());//客户号
		map.put("PRO_CODE",bean.getProductCode());//产品代码
		map.put("DEP_TERM",bean.getMonthsUpper());//存期数字
		map.put("DEP_UNIT",bean.getDepUnit());//存期单位
		map.put("OPPO_ACCT_NO",bean.getCardNo());//对方账号
		map.put("AUTO_REDP_FLAG",bean.getAutoRedpFlag());//自动转存标志
		map.put("LOAD_BAL",bean.getMoney());//存款金额
		map.put("RELA_ACCT_NO",bean.getRelaAcctNo());//收益账号（如意存账号，积享存必输）
		map.put("SUB_RELA_ACCT_NO",bean.getSubRelaAcctNo());//关联子账号
		map.put("CHL_ID",bean.getChlId());//渠道模块标志
		map.put("CERT_PRINT", bean.getCertPrint());//是否打印存单0 不打印，1打印
		map.put("HAV_AGENT_FLAG",bean.getHaveAgentFlag());//是否有代理人标志0是 ，1否
		map.put("CUST_LEVEL", bean.getCustLevel());//客户级别
		map.put("RULE_NO", bean.getQxGetHaveType());//千禧收益方式编号
		if("0".equals(bean.getHaveAgentFlag())){
			map.put("AGENT_CUST_NAME",bean.getAgentIdCardName());//代理人姓名
			if("男".equals(bean.getAgentsex().trim())){
				map.put("AGENT_SEX", "1");
			}else if("女".equals(bean.getAgentsex().trim())){
				map.put("AGENT_SEX", "2");
			}
			map.put("AGENT_ID_TYPE","10100");//代理人证件类别
			map.put("AGENT_ID_NO",bean.getAgentIdCardNo());//代理人证件号码
			map.put("AGENT_ISSUE_INST",bean.getAgentqfjg());//代理人签发机关
			map.put("AGENT_ISSUE_DATE", "");//签发日期
			map.put("AGENT_DUE_DATE", "");//到期日期
			map.put("AGENT_NATION", "");//国籍
			map.put("AGENT_REGIS_PER_RES",bean.getAgentaddress());//代理人户口所在地
			map.put("AGENT_OCCUPATION","");//代理人职业
			map.put("AGENT_J_C_ADD", "");//经常居住地
			map.put("AGENT_TELEPHONE", "");//固定电话
			map.put("AGENT_MOB_PHONE", "");//移动电话
		}

		Map params = new HashMap();
		String reqMsg=CreateXmlMsg.BCK_03870(map);
		SocketClient socketClient =new SocketClient();
		socketClient.createSocket();
		logger.info("请求报文："+reqMsg);
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK03870ResBean.class);
		BCK03870ResBean bck03870ResBean = (BCK03870ResBean)reqXs.fromXML(resMsg);
		if(bck03870ResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","调用03870开户失败");
			bean.getReqMCM001().setReqAfter("4444", "调用03870开户失败");
			return params;
		}
		//获取返回报文错误码和错误信息
		String resCode = bck03870ResBean.getHeadBean().getResCode();
		String errMsg = bck03870ResBean.getHeadBean().getResMsg();
		logger.info("开户查询resCode:"+resCode+"resMsg:"+errMsg);
		bean.getReqMCM001().setIntereturncode(resCode);
		bean.getReqMCM001().setIntereturnmsg(errMsg);
		bean.getReqMCM001().setCentertrjnno(bck03870ResBean.getBody().getSVR_JRNL_NO());
		
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			bean.getReqMCM001().setReqAfter(resCode, errMsg);
			return params;
		}
		
		//保存开户返回报文
		bean.setRes03870(resMsg);
		
		List<OpenJxJInfo> list=null;
		if(StringUtils.isNotBlank(bck03870ResBean.getBody().getFILE_NAME_RET())){
			String fileName = bck03870ResBean.getBody().getFILE_NAME_RET();
			FtpFileUtils.downloadFileByFTPClient(Property.FTP_IP_DOWN, Integer.parseInt(Property.FTP_PORT_DOWN), Property.FTP_USER_DOWN, Property.FTP_PWD_DOWN, fileName, Property.FTP_LOCAL_PATH + fileName);										
				list = TextFileReader.paddingList(Property.FTP_LOCAL_PATH + fileName, OpenJxJInfo.class);
				logger.info("开户成功后信息："+list);
		}
		bean.setAccinputNo(bck03870ResBean.getBody().getACCT_NO());//账户
		bean.setSubAcctNo(bck03870ResBean.getBody().getSUB_ACCT_NO2());//子账户
		bean.setSvrDate(bck03870ResBean.getBody().getSVR_DATE());//核心日期
		bean.setSvrJrnlNo(bck03870ResBean.getBody().getSVR_JRNL_NO());//核心流水号
		bean.setAreaFloatRet(bck03870ResBean.getBody().getAREA_FLOAT_RET());//区域浮动利率
		bean.setChlFloatRet(bck03870ResBean.getBody().getCHL_FLOAT_RET());//渠道浮动利率
		bean.setBirthFloatRet(bck03870ResBean.getBody().getBIRTH_FLOAT_RET());//生日浮动利率
		bean.setTimeFloatRet(bck03870ResBean.getBody().getTIME_FLOAT_RET());//时间段浮动利率
		bean.setCombFloatRet(bck03870ResBean.getBody().getCOMB_FLOT_RET());//组合浮动利率
		bean.setStartDate(bck03870ResBean.getBody().getSTART_DATE());//起息日期
		if(bean.getProductCode().startsWith("AX")){
			bean.setRate(bck03870ResBean.getBody().getTRN_RATE());//开户利率
		}
		//加息劵
		if (list.size()>0 && list!= null) {
			float money = 0;
			for (int i = 0; i < list.size(); i++) {
				OpenJxJInfo openJxJInfo = list.get(i);
				String jxjType = openJxJInfo.getJxjType(); //1 /2
				String jxjBiZi = openJxJInfo.getJxjBiZi(); // 0/1
				if (jxjType.equals("2")&&jxjBiZi.equals("1")) {
					String jxjMoney = openJxJInfo.getJxjMoney();
					money += Float.parseFloat(jxjMoney);
					logger.info("加息券："+money);
				}
			}
			if (money!=0) {//判断加息券的金额是否为0
				params.put("hasJxq", "yes");
				params.put("jxqAmt", money);
			}
		}
		
		//下载衍生品文件
		String fileName = bck03870ResBean.getBody().getPRO_NAME();
		if(fileName == null || "".equals(fileName.trim())){
			logger.info("开户衍生品文件下载路径为空");
		}
		List<accOpenProFileBean02808And03870> list1  = null;
		try {
			// 下载文件
			FtpFileUtils.downloadFileByFTPClient(Property.FTP_IP_DOWN, Integer.parseInt(Property.FTP_PORT_DOWN), Property.FTP_USER_DOWN, Property.FTP_PWD_DOWN, fileName, Property.FTP_LOCAL_PATH + fileName);
			list1 = TextFileReader.paddingList(Property.FTP_LOCAL_PATH + fileName, accOpenProFileBean02808And03870.class);
		} catch (Exception e) {
			logger.error("开户衍生品文件下载失败"+e);
		}
		if(list1 == null){
			
			logger.info("开户衍生品信息获取失败");
			
		}else if(list1.size() == 0){
			
			logger.info("开户衍生品返回文件信息为空");
		}
		
		params.put("ZYD_FILE", list1);
		params.put("resCode",SUCCESS);
		bean.getReqMCM001().setReqAfter(resCode, errMsg);
		return params;
	} 
	
	/**
	 * 糖豆派发查询
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map inter07506(AccPublicBean bean)throws Exception{
		//获取数据
		Map<String, String> map = new HashMap<String, String>();
		map.put("PRODUCT_CODE", bean.getProductCode());//产品编号
		map.put("DEP_TERM", bean.getMonthsUpper()+bean.getDepUnit());//存期
		map.put("AMT", bean.getMoney());//存款
		map.put("ACCEPT_DATE", bean.getSvrDate());//兑付日期
		map.put("IN_INST_NO", GlobalParameter.branchNo);//机构号
		map.put("ACT_CHNL", "");//活动渠道
		map.put("DETAIL_NUM", "1");//循环数
		map.put("ACCT_NO", bean.getCardNo().trim()+"-"+bean.getSubAcctNo().trim());//账号
		map.put("SUB_ACCT_NO", bean.getSubAcctNo());//子账号
		map.put("ACCT_BAL", bean.getMoney());//金额
		Map params = new HashMap();
		String reqMsg=CreateXmlMsg.BCK_07506(map);
		SocketClient socketClient =new SocketClient();
		socketClient.createSocket();
		logger.info("请求报文："+reqMsg);
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK07506ResBean.class);
		BCK07506ResBean bck07506ResBean = (BCK07506ResBean)reqXs.fromXML(resMsg);
		
		//保存唐豆兑付返回报文
		bean.setRes07505(resMsg);
		
		if(bck07506ResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","调用接口07506糖豆查询失败");
			return params;
		}
		//获取返回报文错误码和错误信息
		String resCode = bck07506ResBean.getHeadBean().getResCode();
		String errMsg = bck07506ResBean.getHeadBean().getResMsg();
		logger.info("糖豆派发查询resCode:"+resCode+"resMsg"+errMsg);
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		//保存唐豆查询返回报文
		bean.setRes07506(resMsg);
		
		bean.setTermCode(bck07506ResBean.getBody().getTERM_CODE());//期次代号
		bean.setCount(bck07506ResBean.getBody().getCOUNT());//唐豆数量
		bean.setExchangeProp(bck07506ResBean.getBody().getEXCHANGE_PROP());//唐豆兑现比例
		bean.setExchangeAmt(bck07506ResBean.getBody().getEXCHANGE_AMT());//兑现金额
		bean.setDealType(bck07506ResBean.getBody().getDEAL_TYPE());//处理标志
		logger.info("查询成功后信息："+bean);
		params.put("resCode",SUCCESS);
		params.put("errMsg",errMsg);
		return params;
	} 
	
	/**
	 * 糖豆兑付
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map inter07505(AccPublicBean bean)throws Exception{
		//获取数据
		Map<String, String> map = new HashMap<String, String>();
		map.put("PRODUCT_CODE", bean.getProductCode());//产品编号
		map.put("DEP_TERM", bean.getMonthsUpper()+bean.getDepUnit());//存期
		map.put("AMT", bean.getMoney());//存款
		map.put("ACCEPT_DATE", bean.getSvrDate());//兑付日期
		map.put("TERM_CODE",bean.getTermCode());//TERM_CODE	唐豆期次代码
		map.put("COUNT",bean.getCount());//COUNT	唐豆数量
		map.put("TOTAL_BAL",bean.getMoney());//TOTAL_BAL	账户总余额
//		map.put("EXCHANGE_MODE",bean.getExchangeMode());//EXCHANGE_MODE	唐豆兑换方式
		map.put("EXCHANGE_MODE","1");//EXCHANGE_MODE	唐豆兑换方式	Char(1)	0-现金 1-转账 2-兑物
		map.put("EXCHANGE_PROP",bean.getExchangeProp());//EXCHANGE_PROP	唐豆兑现比例
		map.put("EXCHANGE_AMT",bean.getExchangeAmt());//EXCHANGE_AMT	兑现金额
		map.put("OPPO_ACCT_NO",bean.getCardNo());//OPPO_ACCT_NO	对方账号
//		map.put("DEAL_TYPE",bean.getDealType());//DEAL_TYPE	历史唐豆处理标志	1	0-历史唐豆 1-当期唐豆 ，必输
		map.put("DEAL_TYPE","1");
		map.put("ACCT_INFO", "1");//循环数
		map.put("ACCT_NO", bean.getCardNo().trim()+"-"+bean.getSubAcctNo().trim());//账号
		map.put("SUB_ACCT_NO", bean.getSubAcctNo());//子账号
		map.put("ACCT_BAL", bean.getMoney());//金额
		Map params = new HashMap();
		String reqMsg=CreateXmlMsg.BCK_07505(map);
		SocketClient socketClient =new SocketClient();
		socketClient.createSocket();
		logger.info("请求报文："+reqMsg);
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK07505ResBean.class);
		BCK07505ResBean bck07505ResBean = (BCK07505ResBean)reqXs.fromXML(resMsg);
		
		//保存唐豆兑付返回报文
		bean.setRes07505(resMsg);
		
		//获取返回报文错误码和错误信息
		String resCode = bck07505ResBean.getHeadBean().getResCode();
		String errMsg = bck07505ResBean.getHeadBean().getResMsg();
		if(bck07505ResBean == null){
			params.put("resCode",resCode);
			params.put("errMsg","调用接口失败");
			return params;
		}
		logger.info("糖豆兑付resCode:"+resCode+"resMsg:"+errMsg);
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		
		//唐豆兑付金额
		bean.setTangDouAmt(bck07505ResBean.getBody().getR_EXCHANGE_AMT());
		bean.setTangDDFSvrNo(bck07505ResBean.getBody().getSVR_JRNL_NO());
		logger.info("查询成功后信息："+bean);
		params.put("resCode",SUCCESS);
		return params;
	} 
	
	/**
	 * 柜员认证方式查询
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map inter07659(AccPublicBean bean)throws Exception{
		//获取数据
		Map<String, String> map = new HashMap<String, String>();
		map.put("QRY_TELLER_NO", bean.getSupTellerNo());//QRY_TELLER_NO	查询柜员号
		Map params = new HashMap();
		String reqMsg=CreateXmlMsg.BCK_07659(map);
		SocketClient socketClient =new SocketClient();
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
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		bean.setCheckMod(bck07659ResBean.getBody().getCHECK_MOD());
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
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map inter07660(AccPublicBean bean)throws Exception{
		//获取数据
		Map<String, String> map = new HashMap<String, String>();
		map.put("SUP_TELLER_NO", bean.getSupTellerNo());//SUP_TELLER_NO	查询柜员号
		map.put("SUP_TELLER_PWD", bean.getSupTellerPass());//SUP_TELLER_PWD	授权密码
		map.put("FIN_PRI_LEN", bean.getFinPriLen());//FIN_PRI_LEN	指纹长度
		map.put("FIN_PRI_VAL", bean.getFinPriVal());//FIN_PRI_VAL	指纹值
		Map params = new HashMap();
		String reqMsg=CreateXmlMsg.BCK_07660(map);
		SocketClient socketClient =new SocketClient();
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
	 * 联网核查
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static Map inter07670(AccPublicBean bean)throws Exception{
		//获取数据
		Map<String,String> map = new HashMap<String,String>();
		//证件号码
		map.put("ID", bean.getIdCardNo());
		//证件姓名
		map.put("NAME", bean.getIdCardName());
		Map<String,String> params = new HashMap<String,String>();
		String reqMsg=CreateXmlMsg.BCK_07670(map);
		SocketClient socketClient =new SocketClient();
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
			File to_f = new File(tmp+bean.getIdCardNo()+"_mePoic.jpg");
			FileUtil.copyFileUsingJava7Files(from_f, to_f);
			params.put("filePath", tmp+bean.getIdCardNo()+"_mePoic.jpg");
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
	 * 产品预计利息(24小时)-03510
	 * @param map
	 * @return
	 */
	private static BCK03510ResBean searchInteInfo(AccPublicBean bean,Map<String, Object> map)throws Exception{
		logger.info("开始调用  产品预计利息(24小时)-03510 接口");
		BCK03510ReqBean reqBean = new BCK03510ReqBean();
		InReqHeadBean inReqHeadBean = CreateXmlMsg.getInReqHeadBean("BCK_03510");
		reqBean.setHeadBean(inReqHeadBean);
		BCK03510ReqBodyBean bck03510ReqBodyBean = new BCK03510ReqBodyBean();
		bck03510ReqBodyBean.setPROD_CODE(map.get("PROD_CODE").toString());//产品代码
		bck03510ReqBodyBean.setAMT(map.get("AMT").toString());//金额
		bck03510ReqBodyBean.setACCT_NO(map.get("ACCT_NO").toString());//账号
		bck03510ReqBodyBean.setSUB_ACCT_NO(map.get("SUB_ACCT_NO").toString());//子账号
		String openDate = map.get("OPEN_DATE").toString();//开户日期
		bck03510ReqBodyBean.setOPEN_DATE(openDate);
		if(bck03510ReqBodyBean.getPROD_CODE().startsWith("JX") || bck03510ReqBodyBean.getPROD_CODE().startsWith("RJ")){
		
			bck03510ReqBodyBean.setDRAW_AMT_DATE(bean.getEndTime().replace("/", ""));//到期日
			
		}else{
			
			Calendar now = Calendar.getInstance();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
			Date date = dateFormat.parse(openDate);
			now.setTime(date);
			
			String monthsUpper=map.get("MonthsUpper").toString().toUpperCase();
			Integer n=Integer.parseInt(monthsUpper.replaceAll("\\D",""));
			if(monthsUpper.indexOf("D")!=-1){
				now.add(Calendar.DATE, n);	//一天
			}else if(monthsUpper.indexOf("M")!=-1){
				now.add(Calendar.MONTH,n);	//一天
			}else if(monthsUpper.indexOf("Y")!=-1){
				now.add(Calendar.YEAR, n);	//一年
			}
			bean.setEndTime(dateFormat.format(now.getTime()));
			bck03510ReqBodyBean.setDRAW_AMT_DATE(dateFormat.format(now.getTime()));//到期日
		}
		
		bck03510ReqBodyBean.setCUST_NO(map.get("CUST_NO").toString());//客户号
		Object object = map.get("CHL_ID");
		if(null != object){//该字段不传默认为1，为协议存款，，传3为私行快线
			bck03510ReqBodyBean.setCHL_ID(String.valueOf(object));
		}
		Object level = map.get("CUST_LEVEL");
		if(null != level){//客户评级
			bck03510ReqBodyBean.setCUST_LEVEL(String.valueOf(level));
		}
		reqBean.setBody(bck03510ReqBodyBean);
		XStream xstream = new XStream(new XppDriver(new XStreamNameCoder()));
		xstream.autodetectAnnotations(true);
		xstream.alias("ROOT", BCK03510ReqBean.class);
		xstream.alias("HEAD", InReqHeadBean.class);
		xstream.alias("BODY", BCK03510ReqBodyBean.class);
		String reqMsg = xstream.toXML(reqBean);
		SocketClient socketClient =new SocketClient();
		socketClient.createSocket();
		logger.info("请求报文："+reqMsg);
		String outResMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("前置返回报文：\n"+outResMsg);
		map.put(RESULT, outResMsg);
		if(StringUtils.isNotBlank(outResMsg)){
			XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			reqXs.alias("Root", BCK03510ResBean.class);
			
			BCK03510ResBean bck03510ResBean = (BCK03510ResBean)reqXs.fromXML(outResMsg);
			String resCode = bck03510ResBean.getHeadBean().getResCode();
			String errMsg = bck03510ResBean.getHeadBean().getResMsg();
			if(!"000000".equals(resCode)){
				logger.info("产品预计利息(24小时)查询失败resCode："+resCode+"resMsg:"+errMsg);
				return bck03510ResBean;
			}
			String fileName = bck03510ResBean.getBody().getFILE_NAME();
			FtpFileUtils.downloadFileByFTPClient(Property.FTP_IP_DOWN, Integer.parseInt(Property.FTP_PORT_DOWN), Property.FTP_USER_DOWN, Property.FTP_PWD_DOWN, fileName, Property.FTP_LOCAL_PATH + fileName);
			List<SearchInteInfo> list = TextFileReader.paddingList(Property.FTP_LOCAL_PATH + fileName, SearchInteInfo.class);
			map.put(KEY_PRODUCT_RATES, list);
			logger.info("产品预计利息查询成功："+list);
			return bck03510ResBean;
		}else{
			throw new Exception("调用03510接口异常");
		}
	}
	
	/**查询凭证信息
	 * @throws Exception */
	public static Map<String,String> Tms0005() throws Exception{
		
		SocketClient sc = null;
		sc = new SocketClient();
		sc.createSocket();
		String tms0005 = CreateXmlMsg.Tms0005();
		Map<String,String> params = new HashMap<String,String>();
		// 发送请求  响应
		String ropMsg = sc.sendMesg(tms0005,"UTF-8");
		if(null == ropMsg ){
			logger.error("查询凭证号失败");
			params.put("resCode","4444");
			params.put("errMsg","查询凭证号失败");
			return params;
		}
		logger.info("凭证查询结果:" + ropMsg);
			
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", Tms0005ResBean.class);
		reqXs.alias("Head", InResBean.class);
		reqXs.alias("Body", Tms0005ResBodyBean.class);
		Tms0005ResBean tms0005ResBean = (Tms0005ResBean)reqXs.fromXML(ropMsg);
		String resCode = tms0005ResBean.getHeadBean().getResCode();
		String errMsg = tms0005ResBean.getHeadBean().getResMsg();
		logger.info("查询凭证信息resCode:"+resCode+"resMsg:"+errMsg);
		if(!SUCCESS.equals(resCode)){
			logger.error("查询凭证号失败");
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		params.put("nowNo",tms0005ResBean.getBody().getNOW_BO());//当前凭证号
		params.put("startNo",tms0005ResBean.getBody().getSTART_NO());//起始凭证号
		params.put("endNo",tms0005ResBean.getBody().getEND_NO());//结尾凭证号
		params.put("nowId",tms0005ResBean.getBody().getID());//凭证ID
		params.put("createDate",tms0005ResBean.getBody().getCREATE_DATE());//创建日期
		params.put("resCode",SUCCESS);
		logger.info("查询成功："+tms0005ResBean);
		return params;
	}
	
	
	/**更新凭证信息
	 * @throws IOException 
	 * @throws UnknownHostException 
	 * @throws Exception */
	public static Map<String,String> Tms0006(Map<String,String> map) throws Exception {
		
		SocketClient sc = null;
		sc = new SocketClient();
		sc.createSocket();
		String tms0006 = CreateXmlMsg.Tms0006(map);
		Map<String,String> params = new HashMap<String,String>();
		// 发送请求  响应
		String ropMsg = sc.sendMesg(tms0006,"UTF-8");
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
	 * 代理人身份信息核对
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map inter08021(AccPublicBean bean)throws Exception{
		//获取数据
		Map<String, String> map = new HashMap<String, String>();
		map.put("NAME", bean.getAgentIdCardName());//代理人姓名
		map.put("ID_NO", bean.getAgentIdCardNo());//代理身份证号
		
		Map params = new HashMap();
		String reqMsg=CreateXmlMsg.BCK_08021(map);
		SocketClient socketClient =new SocketClient();
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
		bean.setAgentCheckResult(bck08021ResBean.getBody().getCHECK_RESULT());
		params.put("resCode",SUCCESS);
		return params;
	} 
	
	/**
	 * 卡信息查询
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map inter04422(AccPublicBean bean)throws Exception{
		//获取数据
		Map<String, String> map = new HashMap<String, String>();
		map.put("RESOLVE_TYPE", "2");//识别方式
		map.put("ECIF_CUST_NO", "");//客户编号
		map.put("PARTY_NAME", bean.getIdCardName());//客户姓名
		map.put("CERT_TYPE", "10100");//证件类型
		map.put("CERT_NO", bean.getIdCardNo());//证件号码
		map.put("ACCT_NO", "");//客户识别账号
		
		Map params = new HashMap();
		String reqMsg=CreateXmlMsg.BCK_04422(map);
		SocketClient socketClient =new SocketClient();
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
		logger.info("卡信息查询resCode:"+resCode+"resMsg:"+errMsg);
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		
		bean.setNineMsg(bck04422ResBean.getBody().getNINE_ITEM_COMPLETED_FLAG());
		bean.setInfoQualityFlag(bck04422ResBean.getBody().getINFO_QUALITY_FLAG());
		
		params.put("resCode",SUCCESS);
		return params;
	} 
	
	/**
	 * 客户评级查询03524
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map inter03524(AccPublicBean bean)throws Exception{
		//获取数据
		Map<String, String> map = new HashMap<String, String>();
		map.put("ACCT_NO", bean.getCardNo());//账号
		map.put("CUST_NO", "");//客户号
		
		Map params = new HashMap();
		String reqMsg=CreateXmlMsg.BCK_03524(map);
		SocketClient socketClient =new SocketClient();
		socketClient.createSocket();
		logger.info("请求报文："+reqMsg);
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK03524ResBean.class);
		BCK03524ResBean bck03524ResBean = (BCK03524ResBean)reqXs.fromXML(resMsg);
		if(bck03524ResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","调用接口03524失败");
			return params;
		}
		//获取返回报文错误码和错误信息
		String resCode = bck03524ResBean.getHeadBean().getResCode();
		String errMsg = bck03524ResBean.getHeadBean().getResMsg();
		logger.info("卡信息查询resCode:"+resCode+"resMsg:"+errMsg);
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		
		bean.setCustLevel(bck03524ResBean.getBody().getCUST_LEVEL());//客户评级
		
		params.put("resCode",SUCCESS);
		return params;
	} 
	
	/**
	 * 银行卡开户入库
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map saveOpenAcc(AccPublicBean bean)throws Exception{
		//获取数据
		Map<String, String> map = new HashMap<String, String>();
		map.put("TERMINAL_CODE", GlobalParameter.macFacNum);//机器号
		map.put("RATE", bean.getRate());//利率
		map.put("PRODUCT_NAME", bean.getProductName());//产品名称
		map.put("PRODUCT_CODE", bean.getProductCode());//产品编号
		map.put("UNIT_CODE", GlobalParameter.branchNo);//机构编号
		map.put("UNIT_NAME", GlobalParameter.unitName);//机构名称
		if(bean.getProductCode().startsWith("RJ") || bean.getProductCode().startsWith("JX")){
			map.put("DEPOSIT_PERIOD", bean.getJxRyjDepTem()+"天");//存期
		}else{
			
			map.put("DEPOSIT_PERIOD", bean.getMonthsUpperStr());//存期
		}
		map.put("DEPOSIT_AMT", String.valueOf(bean.getMoney()).trim());//金额
		map.put("DEPOSIT_RESAVE_ENABLED", bean.getAutoRedpFlag());//自动转存标志(0-否 1-是)
		map.put("CARD_NO", bean.getCardNo());//卡号
		map.put("SUB_ACCOUNT_NO", bean.getCardNo().trim()+"-"+bean.getSubAcctNo().trim());//存单账号
		map.put("CUSTOMER_NAME", bean.getIdCardName());//卡姓名
		map.put("DEPOSIT_PASSWORD_ENABLED", "1");//是否加密(1-银行卡加密密码)
		map.put("CERT_NO", bean.getCertNoAdd());//凭证号
		map.put("PAY_TYPE", "C");//支付类型(H-现金开户，C-银行卡开户)
		map.put("Res03870", bean.getRes03870());//开户返回报文
		map.put("Res07506", bean.getRes07506());//唐豆派发查询返回报文
		map.put("Res07505", bean.getRes07505());//唐豆兑付返回报文
		
		Map params = new HashMap();
		String reqMsg=CreateXmlMsg.saveOpenAccXml(map);
		SocketClient socketClient =new SocketClient();
		socketClient.createSocket();
		logger.info("请求报文："+reqMsg);
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", TmsAccountSaveOrderResBean.class);
		TmsAccountSaveOrderResBean tmsAccountSaveOrderResBean = (TmsAccountSaveOrderResBean)reqXs.fromXML(resMsg);
		
		if(tmsAccountSaveOrderResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","调用银行卡开户入库失败");
			return params;
		}
		//获取返回报文错误码和错误信息
		String resCode = tmsAccountSaveOrderResBean.getHeadBean().getResCode();
		String errMsg = tmsAccountSaveOrderResBean.getHeadBean().getResMsg();
		logger.info("银行卡开户入库resCode:"+resCode+"resMsg:"+errMsg);
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		
		params.put("resCode",SUCCESS);
		return params;
	} 
	
	/**
	 * 查询图片上传路径
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map checkUploadPath(AccPublicBean bean)throws Exception{
		//获取数据
		Map<String, String> map = new HashMap<String, String>();
		map.put("openAccNo", "");//开户账号
		map.put("openAccDate", "");//开户日期
		
		Map params = new HashMap();
		String reqMsg=CreateXmlMsg.checkUploadPathXml(map);
		SocketClient socketClient =new SocketClient();
		socketClient.createSocket();
		logger.info("请求报文："+reqMsg);
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", CdjOpenAccResBean.class);
		CdjOpenAccResBean cdjOpenAccResBean = (CdjOpenAccResBean)reqXs.fromXML(resMsg);
		
		if(cdjOpenAccResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","开户查询上传路径失败");
			return params;
		}
		//获取返回报文错误码和错误信息
		String resCode = cdjOpenAccResBean.getResponse().getResCode();
		String errMsg = cdjOpenAccResBean.getResponse().getResMsg();
		logger.info("开户查询上传路径resCode:"+resCode+"resMsg:"+errMsg);
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		//获取上传路径
		bean.setUploadPath(cdjOpenAccResBean.getBody().getUploadPath());
		params.put("resCode",SUCCESS);
		return params;		
	} 
	
}
