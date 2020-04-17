package com.boomhope.Bill.TransService.AccTransfer.Interface;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.boomhope.Bill.TransService.AccTransfer.Action.TransferAction;
import com.boomhope.Bill.TransService.AccTransfer.Bean.PaymentTermsMsgBean;
import com.boomhope.Bill.TransService.AccTransfer.Bean.PublicAccTransferBean;
import com.boomhope.Bill.TransService.AccTransfer.Bean.TelMsgBean;
import com.boomhope.Bill.TransService.AccTransfer.Bean.TransferSelectBean;
import com.boomhope.Bill.TransService.AccTransfer.Bean.UtilCardBean;
import com.boomhope.Bill.TransService.AccTransfer.TransferUtil.SocketClient;
import com.boomhope.Bill.Util.DateUtil;
import com.boomhope.Bill.Util.FileUtil;
import com.boomhope.Bill.Util.FtpFileUtils;
import com.boomhope.Bill.Util.Property;
import com.boomhope.Bill.Util.TextFileReader;
import com.boomhope.Bill.Util.UtilPreFile;
import com.boomhope.tms.message.XStreamNameCoder;
import com.boomhope.tms.message.account.in.BCK03845ResBean;
import com.boomhope.tms.message.in.bck.BCK01118ResBean;
import com.boomhope.tms.message.in.bck.BCK01569ResBean;
import com.boomhope.tms.message.in.bck.BCK01597ResBean;
import com.boomhope.tms.message.in.bck.BCK02013ResBean;
import com.boomhope.tms.message.in.bck.BCK02224ResBean;
import com.boomhope.tms.message.in.bck.BCK02600ResBean;
import com.boomhope.tms.message.in.bck.BCK02693ResBean;
import com.boomhope.tms.message.in.bck.BCK02781ResBean;
import com.boomhope.tms.message.in.bck.BCK02954ResBean;
import com.boomhope.tms.message.in.bck.BCK02956ResBean;
import com.boomhope.tms.message.in.bck.BCK03445ResBean;
import com.boomhope.tms.message.in.bck.BCK03453ResBean;
import com.boomhope.tms.message.in.bck.BCK03521ResBean;
import com.boomhope.tms.message.in.bck.BCK03740ResBean;
import com.boomhope.tms.message.in.bck.BCK07492ResBean;
import com.boomhope.tms.message.in.bck.BCK07494ResBean;
import com.boomhope.tms.message.in.bck.BCK07495ResBean;
import com.boomhope.tms.message.in.bck.BCK07496ResBean;
import com.boomhope.tms.message.in.bck.BCK07602ResBean;
import com.boomhope.tms.message.in.bck.BCK07659ResBean;
import com.boomhope.tms.message.in.bck.BCK07660ResBean;
import com.boomhope.tms.message.in.bck.BCK07670ResBean;
import com.boomhope.tms.message.in.bck.BCKCM021ResBean;
import com.boomhope.tms.message.in.bck.BCKCM022ResBean;
import com.boomhope.tms.message.in.bck.BCKQRY00ResBean;
import com.boomhope.tms.message.in.tms.Tms0008ResBean;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class InterfaceSendMsg {
static Logger logger = Logger.getLogger(InterfaceSendMsg.class);
	
//报文返回成功
	public static String SUCCESS = "000000";
	public static String KEY_PRODUCT_RATES="KEY_PRODUCT_RATES";
	public static String PRODUCT_INFOS="PRODUCT_INFOS";
	public static String TEL_MSG = "TEL_MSG";
	public static String TRANSFER_CANCEL_MSG="TRANSFER_CANCEL_MSG";
	public static String RESULT="RESULT";
	
	/**
	 * 查询灰名单
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static Map inter01597(PublicAccTransferBean bean)throws Exception{
		//获取数据
		Map<String,String> map = new HashMap<String,String>();
		//程序标识：单元素100501(上送出账信息)，双元素100503(上送出账、入账信息)
		map.put("PROGRAM_FLAG", bean.getProgramFlag());
		if("0".equals(bean.getIsChuRu())){
			//校验银行卡时上送
			map.put("CARD_NO1", bean.getChuZhangCardNo());//出账卡号
			map.put("ACC_NO1", bean.getChuZhangCardNo());//出账账号
			//校验身份证时上送
			map.put("ID_TYPE1", "1");//出账客户证件类型
			map.put("ID_NUMBER1", bean.getChuZhangIdCardNo());//出账客户证件号码
			map.put("ID_NAME1", bean.getChuZhangIdCardName());//出账客户证件姓名
			
		}else{
			//校验银行卡时上送
			map.put("CARD_NO2", bean.getRuZhangCardNo());//入账卡号
			map.put("ACC_NO2", bean.getRuZhangCardNo());//入账账号
			//校验身份证时上送
			map.put("ID_TYPE2", "1");//入账客户证件类型
			map.put("ID_NUMBER2", bean.getRuZhangIdCardNo());//入账客户证件号码
			map.put("ID_NAME2", bean.getRuZhangIdCardName());//入账客户证件姓名
		}
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
//		if(!"0000".equals(svrno1)){//检查为黑名单的时候
//			if("0010".equals(svrno1)){
//				params.put("resCode","0010");
//				params.put("errMsg","该客户为涉案客户，禁止交易");
//				return params;
//			}if("0020".equals(svrno1)){//检查为灰名单的时候
//				params.put("resCode","0020");
//				params.put("errMsg","该客户可疑，谨防诈骗，请到我行柜台办理");
//				return params;
//			}
//		}
		params.put("resCode",resCode);
		return params;
	} 
	
	/**
	 * 卡信息查询
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map inter03845(PublicAccTransferBean bean)throws Exception{
		//获取数据
		Map<String, String> map = new HashMap<String, String>();
		map.put("CARD_NO", bean.getChuZhangCardNo());//卡号
		map.put("SUB_ACCT_NO", "");//子账号
		map.put("PASSWD", "");//卡密码
		map.put("ISPIN", "0");//是否验密值为1时则必须进行验密
		
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
		if("N".equals(bck03845ResBean.getBody().getCARD_STAT())){
			logger.info("账户未激活 ");
			params.put("resCode","5555");
			params.put("errMsg","您的银行卡未激活，不能办理此业务");
			return params;
		}
		logger.info("账户可用余额:"+bck03845ResBean.getBody().getAVAL_BAL().trim());
		bean.setCardAmt(bck03845ResBean.getBody().getAVAL_BAL().trim());//账户可用余额
		bean.setBalance(bck03845ResBean.getBody().getBALANCE());//账户结存额
		bean.setChuZhangIdCardNo(bck03845ResBean.getBody().getID_NO()!=null?bck03845ResBean.getBody().getID_NO().trim():"");//身份证号
		bean.setChuZhangIdCardName(bck03845ResBean.getBody().getACCT_NAME()!=null?bck03845ResBean.getBody().getACCT_NAME().trim():"");//户名
		params.put("resCode",SUCCESS);
		return params;
	} 
	
	/**
	 * 证件信息查询
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public static Map inter03445(PublicAccTransferBean bean)throws Exception{
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
		params.put("resCode",SUCCESS);
		return params;
	} 
	
	/**
	 * 身份联网核查
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static Map inter07670(PublicAccTransferBean bean)throws Exception{
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
		bean.setResultCheckIdCard(bck07670ResBean.getBody().getCORE_RET_MSG());//联网核查结果
		params.put("resCode",resCode);
		return params;
	} 
	
	/**
	 * 柜员授权
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map inter07660(PublicAccTransferBean bean)throws Exception{
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
			params.put("resCode","4444");
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
	 * 柜员认证方式查询
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map inter07659(PublicAccTransferBean bean)throws Exception{
		//获取数据
		Map<String, String> map = new HashMap<String, String>();
		if("1".equals(bean.getTellerIsChecked())){
			map.put("QRY_TELLER_NO", bean.getSupTellerNo());//QRY_TELLER_NO	查询第一次授权的柜员号
		}else{
			map.put("QRY_TELLER_NO", bean.getSupTellerNo2());//QRY_TELLER_NO查询第二次授权的柜员号
		}
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
			params.put("resCode","4444");
			params.put("errMsg","调用柜员认证方式查询接口失败");
			return params;
		}
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		TransferAction.transferBean.setCheckMod(bck07659ResBean.getBody().getCHECK_MOD().trim());
		logger.info("查询成功后信息："+bean);
		params.put("resCode",SUCCESS);
		return params;
	} 
	
	/**
	 * 转账客户列表信息查询-前置07492
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map inter07492(PublicAccTransferBean bean)throws Exception{
		//获取数据
		Map<String, String> map = new HashMap<String, String>();
		map.put("ACCT_NAME", bean.getChuZhangcardName());//付款人名称
		map.put("ACCT_NO", bean.getChuZhangCardNo());//付款卡号
		map.put("QRY_CHNL", "0035");//查询渠道(选输：不为空，查对应渠道转账信息，为空，查所有渠道转账信息)
		Map params = new HashMap();
		
		//连接socket
		String reqMsg=CreateXmlMsg.BCK_07492(map);
		SocketClient socketClient =new SocketClient();
		socketClient.createSocket();
		
		//发送请求报文、接收响应报文
		logger.info("请求报文："+reqMsg);
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		
		//解析xml
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK07492ResBean.class);
		BCK07492ResBean bck07492ResBean = (BCK07492ResBean)reqXs.fromXML(resMsg);
		
		//获取返回报文错误码和错误信息
		String resCode = bck07492ResBean.getHeadBean().getResCode();
		String errMsg = bck07492ResBean.getHeadBean().getResMsg();
		logger.info("转账客户列表信息查询resCode:"+resCode+"resMsg:"+resMsg);
		if(bck07492ResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","调用转账客户列表信息查询接口失败");
			return params;
		}
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		String fileName = bck07492ResBean.getBody().getFILE_NAME();
		List<PaymentTermsMsgBean> list  = null;
		try {
			// 下载文件
			FtpFileUtils.downloadFileByFTPClient(Property.FTP_IP_DOWN, Integer.parseInt(Property.FTP_PORT_DOWN), Property.FTP_USER_DOWN, Property.FTP_PWD_DOWN, fileName, Property.FTP_LOCAL_PATH + fileName);
			list = TextFileReader.paddingList(Property.FTP_LOCAL_PATH + fileName, PaymentTermsMsgBean.class);
		} catch (Exception e) {
			logger.error("转账客户列表信息下载失败"+e);
			params.put("resCode","4444");
			params.put("errMsg","转账客户列表信息下载失败");
			return params;
		}
		params.put(KEY_PRODUCT_RATES, list);
		params.put("resCode",SUCCESS);
		return params;
	} 
	
	/**
	 * 大小额系统参数查询CM021
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map interCM021(PublicAccTransferBean bean)throws Exception{
		//获取数据
		Map<String, String> map = new HashMap<String, String>();
		map.put("SYSCODE", "HVPS");//系统代码(BEPS：小额,HVPS：大额)
		map.put("PARCODE", bean.getParCode());//参数代码(CUR_SYS_STAT 当前系统状态-与大额绑定,HOLIDAY_FLAG 节假日标志-与小额绑定)
		Map params = new HashMap();
		
		//连接socket
		String reqMsg=CreateXmlMsg.BCK_CM021(map);
		SocketClient socketClient =new SocketClient();
		socketClient.createSocket();
		
		//发送请求报文、接收响应报文
		logger.info("请求报文："+reqMsg);
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		
		//解析xml
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCKCM021ResBean.class);
		BCKCM021ResBean bckCM021ResBean = (BCKCM021ResBean)reqXs.fromXML(resMsg);
		//获取返回报文错误码和错误信息
		String resCode = bckCM021ResBean.getHeadBean().getResCode();
		String errMsg = bckCM021ResBean.getHeadBean().getResMsg();
		logger.info("大小额系统参数查询resCode："+resCode+"resMsg:"+resMsg);
		if(bckCM021ResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","调用大小额系统参数查询接口失败");
			return params;
		}
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		bean.setBigCode(bckCM021ResBean.getBody().getPARVALUE().trim());
		params.put("resCode",SUCCESS);
		return params;		
	}
	
	/**
	 * 机构关系查询交易【20102】--前置01569
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map inter01569(PublicAccTransferBean bean)throws Exception{
		//获取数据
		Map<String, String> map = new HashMap<String, String>();
		map.put("ACCT_NO", bean.getChuZhangCardNo());//账号(非必输:账号与机构号其一必输)
		map.put("INST_CODE", "");//机构号(非必输:账号与机构号其一必输)
		map.put("SVR_INST_NO", "");//交易机构号(非必输:不输时， 核心默认为本交易机构)
		map.put("CHL_NO", "0035");//渠道号(必输)
		Map params = new HashMap();
		
		//连接socket
		String reqMsg=CreateXmlMsg.BCK_01569(map);
		SocketClient socketClient =new SocketClient();
		socketClient.createSocket();
		
		//发送请求报文、接收响应报文
		logger.info("请求报文："+reqMsg);
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		
		//解析xml
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK01569ResBean.class);
		BCK01569ResBean bck01569ResBean = (BCK01569ResBean)reqXs.fromXML(resMsg);
		//获取返回报文错误码和错误信息
		String resCode = bck01569ResBean.getHeadBean().getResCode();
		String errMsg = bck01569ResBean.getHeadBean().getResMsg();
		logger.info("机构关系查询交易resCode："+resCode+"resMsg:"+resMsg);
		if(bck01569ResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","调用机构关系查询交易接口失败");
			return params;
		}
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		bean.setTcTdFlag(bck01569ResBean.getBody().getTC_TD_FLAG().trim());//0-能通存通兑1-不能通存通兑
		params.put("resCode",SUCCESS);
		return params;		
	}
	
	/**
	 * 单日累计划转金额查询-前置07494
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map inter07494(PublicAccTransferBean bean)throws Exception{
		//获取数据
		Map<String, String> map = new HashMap<String, String>();
		map.put("ACCT_NO", bean.getChuZhangCardNo());//账号
		Map params = new HashMap();
		
		//连接socket
		String reqMsg=CreateXmlMsg.BCK_07494(map);
		SocketClient socketClient =new SocketClient();
		socketClient.createSocket();
		
		//发送请求报文、接收响应报文
		logger.info("请求报文："+reqMsg);
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		
		//解析xml
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK07494ResBean.class);
		BCK07494ResBean bck07494ResBean = (BCK07494ResBean)reqXs.fromXML(resMsg);
		//获取返回报文错误码和错误信息
		String resCode = bck07494ResBean.getHeadBean().getResCode();
		String errMsg = bck07494ResBean.getHeadBean().getResMsg();
		logger.info("单日累计划转金额查询resCode："+resCode+"resMsg:"+resMsg);
		if(bck07494ResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","调用单日累计划转金额查询接口失败");
			return params;
		}
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		bean.setSumAmt(bck07494ResBean.getBody().getSUM_AMT().trim());//单日转账总金额
		params.put("resCode",SUCCESS);
		return params;		
	}
	
	/**
	 * 大额普通汇兑往帐发送交易接口（通用）02013
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map inter02013(PublicAccTransferBean bean)throws Exception{
		//获取数据: 必输（M）/选输（O）
		Map<String, String> map = new HashMap<String, String>();
		map.put("MSG_TYPE","100");//M 报文类型(100_汇兑)
		map.put("CURRENCY","00");//M 币种(00_人民币)
		map.put("BOOK_CARD","1");//M 折卡选择(0_账号，1_卡号)
		map.put("PAY_ACCT_NO","");//O 付款人帐号(BOOK_CARD为“0”时，必输)
		map.put("PAY_CARD_NO",bean.getChuZhangCardNo());//O 付款人卡号(BOOK_CARD为“1”时，必输)
		map.put("PAY_NAME",bean.getChuZhangcardName());//M 付款人名称(允许中文)
		map.put("PAY_ADDR","");//O 付款人地址
		map.put("BALANCE","");//O 余额
		map.put("SETT_TYPE","1");//M 结算方式(0_现金，1_转帐)
		map.put("DRAW_COND","0");//M 支取条件(0_无1_凭密码2_凭证件3_凭印鉴4_凭印鉴和密码)
		map.put("PASSWORD","");//O 密码(DRAW_COND为“1”时，必输)
		map.put("CERT_TYPE","308");//O 凭证种类(使用凭证时必输参照凭证种类对照表)
		map.put("CERT_NO","");//O 凭证号码(使用凭证时必输)
		map.put("SUMM_CODE","6");//M 摘要代码(6_汇出)
		map.put("SUMM_TEXT",bean.getSummText());//O 摘要内容(送资金用途：资金用途代码-对于公转私转账，适用资金用途编码包括001、002、003、004、010、011、012、013、014、015、016、017、018、000。2、对于公转公转账，适用资金用途编码包括019、020、021、022、023、024、000)
		map.put("REMIT_AMT",bean.getRemitAmt());//M  汇款金额(例如：1999.22)
		map.put("FEE_TYPE","");//M 手续费类型(0_个人手续费，1_对公手续费)
		map.put("FEE","0.00");//M 手续费(例如：19.20，若无手续费，填写0.00)
		map.put("POST_FEE","0.00");//M 邮电费(例如：19.20，若无邮电费，填写0.00)
		map.put("BUSI_TYPE","02102");//M 业务种类(02102_普通汇兑：通过转账方式发起的汇款业务,02103_网银支付)
		map.put("RECV_BANK_NO",bean.getRecvBankNo());//M 接收行行号(支付系统行号)
		map.put("RECV_BANK_NAME",bean.getRecvBankName());//M 接收行行名(支付系统行名)
		map.put("RECV_CLR_BANK_NO",bean.getRecvClrBankNo());//M 接收行清算行号(支付系统清算行号)
		map.put("RECV_CLR_BANK_NAME","");//M 接收行清算行名(支付系统清算行名)
		map.put("PAYEE_HBR_NO",bean.getRecvBankNo());//M 收款人开户行号
		map.put("PAYEE_HBR_NAME",bean.getRecvBankName());//M 收款人开户行名
		map.put("PAYEE_ACCT_NO",bean.getRuZhangCardNo());//M 收款人帐号
		map.put("PAYEE_NAME",bean.getRuZhangcardName());//M 收款人户名
		map.put("PAYEE_ADDR","");//O 收款人地址
		map.put("ORIG_SEND_DATE","");//O 原发报日期
		map.put("ORIG_TRAN_NO","");//O 原交易序号
		map.put("ORIG_TRUST_DATE","");//O 原委托日期
		map.put("ORIG_CMT_NO","");//O 原CMT号码
		map.put("ORIG_AMT","");//O 原金额
		map.put("ORIG_PAY_ACCT_NO","");//O 原付款人帐号
		map.put("ORIG_PAY_NAME","");//O 原付款人名称
		map.put("ORIG_PAYEE_ACCT_NO","");//O 原收款人帐号
		map.put("ORIG_PAYEE_NAME","");//O 原收款人名称
		map.put("ORIG_APPD_TEXT","");//O 原附言
		map.put("APPD_TEXT",bean.getAppdText());//O 附言
		map.put("BUSI_CLASS","A100");//M 业务类型(A100-普通汇兑)
		map.put("BUSI_PRIORITY","NORM");//M 业务优先级(NORM：普通,HIGH：紧急,URGT：特急)
		map.put("PAY_HBR_NO","");//O 付款人开户行行号
		map.put("PAY_HBR_NAME","");//O 付款人开户行名称
		map.put("PAY_HBR_INST_NO",bean.getPayHbrInstNo());//M 付款人开户机构号(上送付款人开户行内机构代码)
		map.put("REMARK","");//O 备注
		map.put("REMARK2","");//O 备注2
		map.put("APPD_TEXT2","");//O 附言2
		map.put("RETURN_TEXT","");//M 退汇原因(当MSG_TYPE为“108”退汇时必输)
		map.put("TASK_ID",bean.getTaskId());//M 任务号(标识唯一一笔大额往帐业务使用ZHFW+YYMMDD+付款人开户机构号+5位序号)
		map.put("OPER_DATE",DateUtil.getNowDate("yyyyMMdd"));//M 操作日期(交易发起日期)
		map.put("CORE_PRJ_NO","ZHFWHVPS");//M 核心项目编号(核心记账时使用，可存放上送渠道编号 ZHFWHVPS)
		map.put("CORE_PRO_CODE","ZHFWHVPS");//M 核心产品代码(核心记账时使用，可存放上送渠道代码ZHFWHVPS)
		map.put("CNAPS_MSG_TYPE","hvps.111.001.01");//M 二代报文种类(hvps.111.001.01)
		map.put("PP_No","");//O 平盘单号
		map.put("TRANSFER_FLAG","");//M 转账标志 (第一位： 0-本人 1-他人 第二位：0-本行 1-跨行,第一位按照收付款人是否相同：0-相同，1-不同；第二位送1.)
		map.put("NEXT_DAY_SEND_FLAG",bean.getNextDaySendFlag());//O 次日发送标志(0-普通转账；1-下一工作日转账只有当CNAPS_MSG_TYPE为“hvps.111.001.01”且BUSI_CLASS为“A100”且BUSI_TYPE为“02102”时，此字段可输入“1”；若为空，默认0-实时发送)
		map.put("TIMED_SEND_TIME","");//O 定时发送时间格式：HHMMSS若NEXT_DAY_SEND_FLAG为空或0，此域无效若NEXT_DAY_SEND_FLAG为1时，此域生效，若此域为空，默认100000之后发送
		map.put("TEL_NO", bean.getTelNo());//手机号
		Map params = new HashMap();
		
		//连接socket
		String reqMsg=CreateXmlMsg.BCK_02013(map);
		SocketClient socketClient =new SocketClient();
		socketClient.createSocket();
		
		//发送请求报文、接收响应报文
		logger.info("请求报文："+reqMsg);
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		
		//解析xml
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK02013ResBean.class);
		BCK02013ResBean bck02013ResBean = (BCK02013ResBean)reqXs.fromXML(resMsg);
		//获取返回报文错误码和错误信息
		String resCode = bck02013ResBean.getHeadBean().getResCode();
		String errMsg = bck02013ResBean.getHeadBean().getResMsg();
		logger.info("大额普通汇兑往帐发送交易查询resCode："+resCode+"resMsg:"+resMsg);
		if(bck02013ResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","调用大额普通汇兑往帐发送交易查询接口失败");
			return params;
		}
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		bean.setSvrJrnlNo(bck02013ResBean.getBody().getSVR_JRNL_NO().trim());//转账核心流水号
		bean.getReqMCM001().setCentertrjnno(bck02013ResBean.getBody().getSVR_JRNL_NO().trim());//上送流水的核心流水号
		bean.setSvrDate(bck02013ResBean.getBody().getSVR_DATE().trim());//转账核心日期
		bean.setTransDoTime(bck02013ResBean.getBody().getTRAN_TIME());//操作时间
		bean.setTaskIdNo(bck02013ResBean.getBody().getTASK_ID());//生成的完整任务号
		params.put("resCode",SUCCESS);
		return params;		
	}
	
	/**
	 * 小额普通贷记往帐录入（通用）02224
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map inter02224(PublicAccTransferBean bean)throws Exception{
		//获取数据:必输（M）/选输（O）
		Map<String, String> map = new HashMap<String, String>();
		map.put("BUSI_OPTION","0");//M 业务选择(0_普通贷记往帐)
		map.put("BUSI_CLASS","A100");//M 业务类型(A100-普通汇兑)
		map.put("CURRENCY","00");//M 币种(00-人民币)
		map.put("BOOK_CARD","1");//M 折卡选择(0_帐号,1_卡号)
		map.put("PAY_ACCT_NO","");//O 付款人帐号(BOOK_CARD为0时必输)
		map.put("PAY_CARD_NO",bean.getChuZhangCardNo());//O 付款人卡号(BOOK_CARD为1时必输)
		map.put("PAY_NAME",bean.getChuZhangcardName());//M 付款人名称(支持中文)
		map.put("PAY_ADDR","");//O 付款人地址()
		map.put("PAY_HBR_NO","");//O 付款人开户行号()
		map.put("PAY_HBR_NAME","");//O 付款人开户行名()
		map.put("PAY_HBR_INST_NO",bean.getPayHbrInstNo());//M 付款人开户机构号(上送付款人开户行内机构代码)
		map.put("DRAW_COND","0");//M 支付条件(0_无1_凭密2_凭证件3_凭印鉴4_凭印鉴和密码)
		map.put("BOOK_NO","");//O 存折号码()
		map.put("PASSWORD","");//O 密码(当DRAW_COND为1或4时必输)
		map.put("BALANCE",bean.getCardAmt());//O 余额(格式：1234.56)
		map.put("CERT_TYPE","308");//O 凭证种类(使用凭证时必输)
		map.put("CERT_NO","");//O 凭证号码(使用凭证时必输)
		map.put("SUMM_CODE","6");//O 摘要()
		map.put("SUMM_TEXT",bean.getSummText());//O 摘要内容(送资金用途：资金用途代码-1、对于公转私转账，适用资金用途编码包括001、002、003、004、010、011、012、013、014、015、016、017、018、000。2、对于公转公转账，适用资金用途编码包括019、020、021、022、023、024、000。)
		map.put("REMIT_AMT",bean.getRemitAmt());//M 汇款金额(格式：1234.56)
		map.put("FEE_TYPE","");//M 手续费类型(0-个人手续费,1-对公手续费)
		map.put("FEE","0.00");//M 手续费(格式：1234.56)
		map.put("POST_FEE","0.00");//M 邮电费(格式：1234.56)
		map.put("BUSI_TYPE","02102");//M 业务种类(02102-普通汇兑（客户通过转账方式发起额汇款）02103-网银支付)
		map.put("ITEM_NUM","1");//M 笔数(笔数上送“1”) 
		map.put("TRAN_AMT",bean.getRemitAmt());//M 交易金额()
		map.put("PAY_ACCT_TYPE","2");//M 付款类型(付款帐户类型:0银行账号,1贷记卡,2借记卡,9其他)
		map.put("PAYEE_ACCT_TYPE","2");//M 收款类型(收款帐户类型:0银行账号,1贷记卡,2借记卡,9其他)
		map.put("SETT_TYPE","1");//M 业务模式(0_现金,1_转账)
		map.put("TASK_ID",bean.getTaskId());//M 任务号(每笔交易任务号均不相同，若某笔交易需要重新发送时，任务号需要使用相同值，且交易各相关要素必须相同，标识唯一一笔小额往帐业务使用ZHFW+YYMMDD+付款人开户机构号+5位序号)
		map.put("CORE_PRJ_NO","ZHFWBEPS");//M 核心项目编号(核心记账时使用，可存放上送渠道编号ZHFWBEPS)
		map.put("CORE_PRO_CODE","ZHFWBEPS");//M 核心产品代码(核心记账时使用，可存放上送渠道代码ZHFWBEPS)
		map.put("REMARK1","");//M 备注1()
		map.put("REMARK2","");//M 备注2()
		map.put("TRANSFER_FLAG","");//M 转账标志(第一位： 0-本人 1-他人 第二位：0-本行 1-跨行第一位按照收付款人是否相同：0-相同，1-不同；第二位送1)
		map.put("NEXT_DAY_SEND_FLAG",bean.getNextDaySendFlag());//O 次日发送标志(0-普通到账；1-下一自然日到账只有当BUSI_OPTION为“0-普通”且BUSI_CLASS为“A100”时，此字段可输入“1”；若为空，默认0-实时发送)
		map.put("TIMED_SEND_TIME","");//O 定时发送时间(格式：HHMMSS若NEXT_DAY_SEND_FLAG为空或0，此域无效若NEXT_DAY_SEND_FLAG为1时，此域生效，若此域为空，默认100000之后发送)
		map.put("TEL_NO", bean.getTelNo());//手机号
		
		//收款方明细(ITEM_NUM大于0时必填)
		map.put("PAYEE_BANK_NO",bean.getRecvBankNo());//M 收款人行号()
		map.put("PAYEE_BANK_NAME",bean.getRecvBankName());//M 收款人行名()
		map.put("PAYEE_HBR_NO",bean.getRecvBankNo());//M 收款开户行号()
		map.put("PAYEE_HBR_NAME",bean.getRecvBankName());//M 收款开户行名()
		map.put("PAYEE_ACCT_NO",bean.getRuZhangCardNo());//M 收款人帐号()
		map.put("PAYEE_NAME",bean.getRuZhangcardName());//M 收款人户名()
		map.put("PAYEE_ADDR","");//O 收款人地址()
		map.put("PAY_AMT",bean.getRemitAmt());//M 支付金额()
		map.put("APPD_TEXT",bean.getAppdText());//O 附言()		
		Map params = new HashMap();
		
		//连接socket
		String reqMsg=CreateXmlMsg.BCK_02224(map);
		SocketClient socketClient =new SocketClient();
		socketClient.createSocket();
		
		//发送请求报文、接收响应报文
		logger.info("请求报文："+reqMsg);
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		
		//解析xml
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK02224ResBean.class);
		BCK02224ResBean bck02224ResBean = (BCK02224ResBean)reqXs.fromXML(resMsg);
		//获取返回报文错误码和错误信息
		String resCode = bck02224ResBean.getHeadBean().getResCode();
		String errMsg = bck02224ResBean.getHeadBean().getResMsg();
		logger.info("小额普通贷记往帐录入resCode："+resCode+"resMsg:"+resMsg);
		if(bck02224ResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","调用小额普通贷记往帐录入接口失败");
			return params;
		}
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		bean.setSvrJrnlNo(bck02224ResBean.getBody().getSVR_JRNL_NO().trim());//转账核心流水号
		bean.getReqMCM001().setCentertrjnno(bck02224ResBean.getBody().getSVR_JRNL_NO().trim());//上送流水的核心流水号
		bean.setSvrDate(bck02224ResBean.getBody().getSVR_DATE().trim());//转账核心日期
		bean.setTransDoTime(bck02224ResBean.getBody().getTRAN_TIME());//操作时间
		bean.setTaskIdNo(bck02224ResBean.getBody().getTASK_ID());//生成的完整任务号
		params.put("resCode",SUCCESS);
		return params;		
	}
	
	/**
	 * 出账-根据机构号查询支付行信息-前置01118
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map inter01118(PublicAccTransferBean bean)throws Exception{
		//获取数据
		Map<String, String> map = new HashMap<String, String>();
		map.put("SCH_INST_NO", bean.getPayHbrInstNo());//机构号(必输)
		Map params = new HashMap();
		
		//连接socket
		String reqMsg=CreateXmlMsg.BCK_01118(map);
		SocketClient socketClient =new SocketClient();
		socketClient.createSocket();
		
		//发送请求报文、接收响应报文
		logger.info("请求报文："+reqMsg);
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		
		//解析xml
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK01118ResBean.class);
		BCK01118ResBean bck01118ResBean = (BCK01118ResBean)reqXs.fromXML(resMsg);
		//获取返回报文错误码和错误信息
		String resCode = bck01118ResBean.getHeadBean().getResCode();
		String errMsg = bck01118ResBean.getHeadBean().getResMsg();
		logger.info("根据机构号查询支付行信息resCode："+resCode+"resMsg:"+resMsg);
		if(bck01118ResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","根据机构号查询支付行信息接口失败");
			return params;
		}
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		bean.setPayHbrInstName(bck01118ResBean.getBody().getBANK_NAME().trim());//开户行机构名
		bean.setSendBankNo(bck01118ResBean.getBody().getBANK_NO().trim());//开户行行号
		bean.setSendBankName(bck01118ResBean.getBody().getBANK_NAME().trim());//开户行行名
		params.put("resCode",SUCCESS);
		return params;		
	}
	
	/**
	 * 入账—根据机构号查询支付行信息-前置01118
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map ru01118(PublicAccTransferBean bean)throws Exception{
		//获取数据
		Map<String, String> map = new HashMap<String, String>();
		map.put("SCH_INST_NO", bean.getRecvBankNo());//机构号(必输)
		Map params = new HashMap();
		
		//连接socket
		String reqMsg=CreateXmlMsg.BCK_01118(map);
		SocketClient socketClient =new SocketClient();
		socketClient.createSocket();
		
		//发送请求报文、接收响应报文
		logger.info("请求报文："+reqMsg);
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		
		//解析xml
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK01118ResBean.class);
		BCK01118ResBean bck01118ResBean = (BCK01118ResBean)reqXs.fromXML(resMsg);
		//获取返回报文错误码和错误信息
		String resCode = bck01118ResBean.getHeadBean().getResCode();
		String errMsg = bck01118ResBean.getHeadBean().getResMsg();
		logger.info("根据机构号查询支付行信息resCode："+resCode+"resMsg:"+resMsg);
		if(bck01118ResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","根据机构号查询支付行信息接口失败");
			return params;
		}
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		bean.setRecvBankName(bck01118ResBean.getBody().getBANK_NAME().trim());//收款开户行名
		bean.setRecvBankNo(bck01118ResBean.getBody().getBANK_NO().trim());//收款开户行行号
		params.put("resCode",SUCCESS);
		return params;		
	}
	
	/**
	 * 行内汇划-前置02600
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map inter02600(PublicAccTransferBean bean)throws Exception{
		//获取数据
		Map<String, String> map = new HashMap<String, String>();
		map.put("BUSI_TYPE", "1");//业务类型( 1、代收  2、代付，默认为代收，代付我行开通未使用)
		map.put("DB_CR_FLAG", "1");//借贷标志(必输项，选项：1_借方2_贷方，默认为借方)
		map.put("AGENT_INST_NO","" );//被代理机构号(付款人账号所在机构，清算中心上送；网点不上送)
		map.put("SEND_BANK_NO",bean.getSendBankNo() );//提出行行号(默认为本机构)
		map.put("SEND_BANK_NAME",bean.getSendBankName() );//提出行名称(由提出行行号自动带出)
		map.put("PAY_ACCT_NO",bean.getChuZhangCardNo());//付款人账号(前端调用账号信息查询接口，前置返回开户行号，前端校验开户行号与提出行号是否一致，不一致报错)
		map.put("PAY_NAME",bean.getChuZhangcardName());//付款人名称(由付款账号自动带出)
		map.put("DRAW_COND","0");//支取条件(0_无 、1_凭印签，内部账户009026205（默认为1 凭印鉴 在讨论）、 其余默认为0，内部账选0 无 时，不验印需授权，对公客户账号默认为1 凭印签，默认为1、凭印签)
		map.put("RECV_BANK_NO",bean.getRecvBankNo());//提入行行号(必输项，核心校验是否为辖内机构号)
		map.put("RECV_BANK_NAME",bean.getRecvBankName());//提入行名称(由行号自动带出)
		map.put("PAYEE_ACCT_NO",bean.getRuZhangCardNo());//收款人账号(必输项，核心校验是否为所输提入行的账号)
		map.put("PAYEE_NAME",bean.getRuZhangcardName());//收款人名称(由收款人账号自动带出)
		map.put("BILL_TYPE","308");//票据种类(必输项（转账支票是代付业务，进账单是代收业务，通用可代收代付。（现凭证种类代码108转账支票， 308通用凭证，107进账单）如果为108时判断是否为该账号下的支票)
		map.put("BILL_NO","");//票据号码(凭证种类为108必输，其他不输自动为灰色)
		map.put("PIN","");//支付密码()
		map.put("AVAL_BAL",bean.getCardAmt());//可用余额(由付款账号自动带出)
		map.put("CUEERNCY","00");//币种(默认 RMB)
		map.put("AMT",bean.getRemitAmt());//金    额(必输项，金额圈存，必须小于等于可用余额，内部账户不圈存)
		map.put("BILL_DATE","");//出票日期(当票据种类为108时必输项，其余为空根据出票日期判断印签的有效日期（核心印签库不支持变更前的印鉴核验，要求更改）)
		map.put("NOTE_DATE","");//提示付款日期(当票据种类为108时必输项，其余为空)
		map.put("ENDOR_NUM","");//背书次数(非必输, 业务确定去掉字段，前置接口保留备用)
		map.put("REMARK","");//备注(当票据种类为108时必输项，其余选输)
		map.put("PURPOS",bean.getPurpos());//用途(选输项)
		map.put("TRN_REASON","");//转账原因(选输项)
		map.put("ORIG_CERT_TYPE","");//原始凭证种类(非必输，业务确定去掉字段，前置接口保留备用)
		map.put("ATTACH_NUM","");//张数(非必输，业务确定去掉字段，前置接口保留备用)
		map.put("APPD_TEXT","");//附加信息(非必输，业务确定去掉字段，前置接口保留备用)
		map.put("SUMM_CODE","6");//摘要代码()
		map.put("SUMM_TEXT",bean.getSummText());//摘要内容()
		map.put("NEXT_DAY_SEND_FLAG",bean.getNextDaySendFlag() );//次日发送标志(0-普通转账；1-下一工作日转账若为空，默认0-实时发送)
		map.put("TIMED_SEND_TIME","");//定时发送时间(格式：HHMMSS 若NEXT_DAY_SEND_FLAG为空或0，此域无效若NEXT_DAY_SEND_FLAG为1时，此域生效，若此域为空，默认100000之后发送)
		map.put("TASK_IDTRNS", bean.getTaskId());//任务号
		map.put("TEL_NO", bean.getTelNo());//手机号
		//背书人清单
		map.put("ENDOR_NAME", "");//背书人清单(最多支持6000字。非必输,业务确定去掉字段，前置接口保留备用)
		Map params = new HashMap();
		
		//连接socket
		String reqMsg=CreateXmlMsg.BCK_02600(map);
		SocketClient socketClient =new SocketClient();
		socketClient.createSocket();
		
		//发送请求报文、接收响应报文
		logger.info("请求报文："+reqMsg);
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		
		//解析xml
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK02600ResBean.class);
		BCK02600ResBean bck02600ResBean = (BCK02600ResBean)reqXs.fromXML(resMsg);
		//获取返回报文错误码和错误信息
		String resCode = bck02600ResBean.getHeadBean().getResCode();
		String errMsg = bck02600ResBean.getHeadBean().getResMsg();
		logger.info("行内汇划resCode："+resCode+"resMsg:"+resMsg);
		if(bck02600ResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","行内汇划接口失败");
			return params;
		}
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		bean.setSvrJrnlNo(bck02600ResBean.getBody().getSVR_JRNL_NO().trim());//转账核心流水号
		bean.getReqMCM001().setCentertrjnno(bck02600ResBean.getBody().getSVR_JRNL_NO().trim());//上送流水的核心流水号
		bean.setSvrDate(bck02600ResBean.getBody().getSVR_DATE().trim());//转账核心日期
		bean.setTransDoTime(bck02600ResBean.getBody().getTRAN_TIME());//交易时间
		bean.setTaskIdNo(bck02600ResBean.getBody().getTASK_ID());//生成的完整任务号
		params.put("resCode",SUCCESS);
		return params;		
	}
	
	/**
	 * 前置02956(权限明细查询)
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map inter02956(PublicAccTransferBean bean)throws Exception{
		//获取数据
		Map<String, String> map = new HashMap<String, String>();
		map.put("QRY_OPTION", "4");//查询选项(1_交易渠道类型限额信息 2_交易对手信息  3_动账及业务通知联系人信息 4_查询权限)
		map.put("CARD_NO", bean.getChuZhangCardNo());//卡号
		
		Map params = new HashMap();
		
		//连接socket
		String reqMsg=CreateXmlMsg.BCK_02956(map);
		SocketClient socketClient =new SocketClient();
		socketClient.createSocket();
		
		//发送请求报文、接收响应报文
		logger.info("请求报文："+reqMsg);
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		
		//解析xml
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK02956ResBean.class);
		BCK02956ResBean bck02956ResBean = (BCK02956ResBean)reqXs.fromXML(resMsg);
		//获取返回报文错误码和错误信息
		String resCode = bck02956ResBean.getHeadBean().getResCode();
		String errMsg = bck02956ResBean.getHeadBean().getResMsg();
		logger.info("权限明细查询resCode："+resCode+"resMsg:"+resMsg);
		if(bck02956ResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","权限明细查询接口失败");
			return params;
		}
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		String fileName = bck02956ResBean.getBody().getFILE_NAME();
		List<UtilCardBean>  list = null;
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null; //用于包装InputStreamReader,提高处理性能。因为BufferedReader有缓冲的，而InputStreamReader没有。
		String str = "";
		String str1 = "";
		try {
			// 下载文件
			FtpFileUtils.downloadFileByFTPClient(Property.FTP_IP_DOWN, Integer.parseInt(Property.FTP_PORT_DOWN), Property.FTP_USER_DOWN, Property.FTP_PWD_DOWN, fileName, Property.FTP_LOCAL_PATH + fileName);
			fis = new FileInputStream(Property.FTP_LOCAL_PATH + fileName);// FileInputStream
			// 从文件系统中的某个文件中获取字节
		    isr = new InputStreamReader(fis,"GBK");// InputStreamReader 是字节流通向字符流的桥梁,
		    br = new BufferedReader(isr);// 从字符输入流中读取文件中的内容,封装了一个new InputStreamReader的对象
		    while ((str = br.readLine()) != null) {
		    	str1 += str + "\n";
		    }
		    // 当读取的一行不为空时,把读到的str的值赋给str1
		    System.out.println(str1);// 打印出str1       
		    logger.info("单位查看权限："+str1);
		    
		} catch (FileNotFoundException e) {
			    System.out.println("找不到指定文件");
				logger.error("找不到指定文件"+e);
				params.put("resCode","4444");
				params.put("errMsg","权限信息查询找不到指定文件");
				return params;
		} catch (IOException e) {
			    System.out.println("读取文件失败");
				logger.error("读取权限查看文件失败"+e);
				params.put("resCode","4444");
				params.put("errMsg","读取权限查看文件失败");
				return params;
	    } finally {
	    	 try {
			      br.close();
			      isr.close();
			      fis.close();
			     // 关闭的时候最好按照先后顺序关闭最后开的先关闭所以先关s,再关n,最后关m
			    } catch (IOException e) {
			    	logger.error(e);   
			    }
	    }
		
		if(str1.contains("开通")){
			bean.setIsMoneyRoot("YES");
			
		}else if(str1.contains("关闭")){
			bean.setIsMoneyRoot("NO");
		}
		params.put("resCode",SUCCESS);
		return params;		
	}
	
	/**
	 * 出账账户信息查询及密码验证-前置03521
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map inter03521(PublicAccTransferBean bean)throws Exception{
		//获取数据
		Map<String, String> map = new HashMap<String, String>();
		map.put("ACCT_NO", bean.getChuZhangCardNo());//账号
		map.put("PASSWORD", bean.getCardPwd());//密码
		map.put("PIN_VAL_FLAG", "1");//验密标志
		
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
		logger.info("账户信息查询及密码验证resCode："+resCode+"resMsg:"+resMsg);
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
			params.put("resCode","5555");
			params.put("errMsg","您的账户为销户状态，不能办理此业务");
			return params;
		}else if("T".equals(bck03521ResBean.getBody().getACCT_STAT())){
			params.put("resCode","5555");
			params.put("errMsg","您的账户为停用状态，不能办理此业务");
			return params;
		}
		
		if("G".equals(bck03521ResBean.getBody().getLOST_STAT())){//挂失
			params.put("resCode","5555");
			params.put("errMsg","您的银行卡为挂失状态，不能办理此业务");
			return params;
		}
		if("D".equals(bck03521ResBean.getBody().getHOLD_STAT())){//冻结
			params.put("resCode","5555");
			params.put("errMsg","您的银行卡为封闭冻结状态，不能办理此业务");
			return params;
			
		}else if("S".equals(bck03521ResBean.getBody().getHOLD_STAT())){
			params.put("resCode","5555");
			params.put("errMsg","您的银行卡为只收不付状态，不能办理此业务");
			return params;
		}else if("Z".equals(bck03521ResBean.getBody().getHOLD_STAT())){
			params.put("resCode","5555");
			params.put("errMsg","您的银行卡为全额止付状态，不能办理此业务");
			return params;
		}
		logger.info("开户机构号:"+bck03521ResBean.getBody().getHBR_INST_NO().trim());
		logger.info("持卡户名:"+bck03521ResBean.getBody().getACCT_NAME().trim());
		TransferAction.transferBean.setPayHbrInstNo(bck03521ResBean.getBody().getHBR_INST_NO().trim());//开户机构
		TransferAction.transferBean.setChuZhangcardName(bck03521ResBean.getBody().getACCT_NAME().trim());//个人持卡户名
		
		if("12".equals(bck03521ResBean.getBody().getTYPE().trim())){//账户类别为单位卡
			logger.info("单位持卡户名:"+bck03521ResBean.getBody().getACCT_NAME().trim());
			logger.info("单位结算户名:"+bck03521ResBean.getBody().getSVR_ORGAACCNM().trim());
			logger.info("单位结算账号:"+bck03521ResBean.getBody().getCARD_NO().trim());
			logger.info("单位卡金额:"+bck03521ResBean.getBody().getBALANCE().trim());
			
			TransferAction.transferBean.setChuZhangAcctName(bck03521ResBean.getBody().getACCT_NAME().trim());//单位持卡户名
			TransferAction.transferBean.setChuZhangcardName(bck03521ResBean.getBody().getSVR_ORGAACCNM().trim());//单位结算户名
			TransferAction.transferBean.setChuZhangAcctNo(bck03521ResBean.getBody().getCARD_NO().trim());//单位卡结算账号
			
			String utilStat = bck03521ResBean.getBody().getSVR_ACC_STAT().trim();
			if("Q".equals(String.valueOf(utilStat.charAt(0)))){
				params.put("resCode","6666");
				params.put("errMsg","该卡账户已销户，不能办理此业务");
				return params;
			}else if("1".equals(String.valueOf(utilStat.charAt(0)))){
				params.put("resCode","6666");
				params.put("errMsg","该卡账户已停用，不能办理此业务");
				return params;
			}else if(!"0".equals(String.valueOf(utilStat.charAt(0)))){
				params.put("resCode","6666");
				params.put("errMsg","该卡账户状态异常，不能办理此业务");
				return params;
			}
			if("1".equals(String.valueOf(utilStat.charAt(2)))){
				params.put("resCode","6666");
				params.put("errMsg","您的银行卡为封闭冻结状态，不能办理此业务");
				return params;
			}else if("3".equals(String.valueOf(utilStat.charAt(2)))){
				params.put("resCode","6666");
				params.put("errMsg","您的银行卡为只收不付状态，不能办理此业务");
				return params;
			}
			if("1".equals(String.valueOf(utilStat.charAt(3)))){
				params.put("resCode","6666");
				params.put("errMsg","您的银行卡为全额止付状态，不能办理此业务");
				return params;
			}
			if(!"0".equals(String.valueOf(utilStat.charAt(6)))){
				params.put("resCode","6666");
				params.put("errMsg","您的印签为挂失状态，不能办理此业务");
				return params;
			}
			if(!"0".equals(String.valueOf(utilStat.charAt(7)))){
				params.put("resCode","6666");
				params.put("errMsg","您的单位存折为挂失状态，不能办理此业务");
				return params;
			}
			if(!"0".equals(String.valueOf(utilStat.charAt(8)))){
				params.put("resCode","6666");
				params.put("errMsg","您的银行卡为挂失状态，不能办理此业务");
				return params;
			}
			if(!"0".equals(String.valueOf(utilStat.charAt(9)))){
				params.put("resCode","6666");
				params.put("errMsg","您的银行卡为挂失状态，不能办理此业务");
				return params;
			}
		}
		params.put("resCode",SUCCESS);
		return params;		
	}
	
	/**
	 * 入账账户信息查询及密码验证-前置03521
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map Ru03521(PublicAccTransferBean bean)throws Exception{
		//获取数据
		Map<String, String> map = new HashMap<String, String>();
		map.put("ACCT_NO", bean.getRuZhangCardNo());//账号
		map.put("PASSWORD", "");//密码
		map.put("PIN_VAL_FLAG", "0");//验密标志
		
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
		logger.info("入账卡账户信息查询及密码验证resCode："+resCode+"resMsg:"+resMsg);
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
		
		logger.info("账户余额:"+bck03521ResBean.getBody().getBALANCE().trim());
		logger.info("开户机构号:"+bck03521ResBean.getBody().getHBR_INST_NO().trim());
		logger.info("持卡户名:"+bck03521ResBean.getBody().getACCT_NAME().trim());
		
		bean.setRecvBankNo(bck03521ResBean.getBody().getHBR_INST_NO().trim());//开户机构
		bean.setRuZhangcardName(bck03521ResBean.getBody().getACCT_NAME().trim());//个人账户/单位账户户名
		if("3".equals(bck03521ResBean.getBody().getTYPE().trim()) || "12".equals(bck03521ResBean.getBody().getTYPE().trim())){//单位账户
			bean.setIsUtilAcctNO("UtilAccNo");//判断单位
		}
		
		if("12".equals(bck03521ResBean.getBody().getTYPE().trim())){//账户类别为单位卡
			logger.info("单位结算户名:"+bck03521ResBean.getBody().getSVR_ORGAACCNM().trim());
			logger.info("单位结算账号:"+bck03521ResBean.getBody().getCARD_NO().trim());
			bean.setRuZhangcardName(bck03521ResBean.getBody().getSVR_ORGAACCNM().trim());//单位结算卡户名
		}
		params.put("resCode",SUCCESS);
		return params;		
	}
	/**
	 * 核心节假日查询-前置07495
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map inter07495(PublicAccTransferBean bean)throws Exception{
		//获取数据
		Map<String, String> map = new HashMap<String, String>();
//		map.put("QRY_DATE", DateUtil.getNowDate("yyyyMMdd"));//核心当前日期
//		map.put("QRY_DATE", "20170429");//核心当前日期
		
		map.put("QRY_DATE", bean.getSvrTranDate());//核心当前日期
		
		Map params = new HashMap();
		
		//连接socket
		String reqMsg=CreateXmlMsg.BCK_07495(map);
		SocketClient socketClient =new SocketClient();
		socketClient.createSocket();
		
		//发送请求报文、接收响应报文
		logger.info("请求报文："+reqMsg);
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		
		//解析xml
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK07495ResBean.class);
		BCK07495ResBean bck07495ResBean = (BCK07495ResBean)reqXs.fromXML(resMsg);
		//获取返回报文错误码和错误信息
		String resCode = bck07495ResBean.getHeadBean().getResCode();
		String errMsg = bck07495ResBean.getHeadBean().getResMsg();
		logger.info("核心节假日查询resCode："+resCode+"resMsg:"+resMsg);
		if(bck07495ResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","核心节假日查询接口失败");
			return params;
		}
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		bean.setIsWorkDay(bck07495ResBean.getBody().getDATE_FLAG().trim());//节假日标志
		params.put("resCode",SUCCESS);
		return params;		
	}
	
	/**
	 * 前置系统参数查询-前置QRY00
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static Map interQRY00(PublicAccTransferBean bean)throws Exception{
		//获取数据
		Map<String,String> map = new HashMap<String,String>();
		map.put("PARAM_CODE", "SVR_TRAN_DATE");//LAST_SVR_TRAN_DATE---核心上一个工作日期   、SVR_TRAN_DATE--核心工作日期
		Map<String,String> params = new HashMap<String,String>();
		
		String reqMsg=CreateXmlMsg.BCK_QRY00(map);
		
		SocketClient socketClient =new SocketClient();
		socketClient.createSocket();
		
		logger.info("请求报文："+reqMsg);
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCKQRY00ResBean.class);
		BCKQRY00ResBean bckQRY00ResBean = (BCKQRY00ResBean)reqXs.fromXML(resMsg);
		if(bckQRY00ResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","调用接口QRY00失败");
			return params;
		}
		String resCode = bckQRY00ResBean.getHeadBean().getResCode();
		String errMsg = bckQRY00ResBean.getHeadBean().getResMsg();
		logger.info("前置系统参数查询-resCode："+resCode+"前置系统参数查询-resMsg："+resMsg);
		
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		bean.setSvrTranDate(bckQRY00ResBean.getBody().getPARAM_VALUE().trim());
		params.put("resCode",resCode);
		return params;
	} 
	
	
	/**
	 * 单位卡信息查询
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static Map inter03453(PublicAccTransferBean bean)throws Exception{
		//获取数据
		Map<String,String> map = new HashMap<String,String>();
		map.put("CARD_NO",bean.getChuZhangCardNo());//卡号
		map.put("PASSWD",bean.getCardPwd());//密码
		map.put("TRACK2_INFO", bean.getErCiInfo());//二磁信息
		map.put("FALL_BACK_FLAG","1");//降级标志
		map.put("ARQC",bean.getArqc());//ARQC
		map.put("ARQC_SRC_DATA",bean.getArqcSrcData() );//ARQC生成数据
		map.put("TERM_RESULT", bean.getTermChkValue());//终端验证结果
		map.put("ISSUER_APP_DATA",bean.getIssAppData() );//发卡行应用数据
		map.put("ICCARD_DATA", bean.getAppAcctSeq());//应用主账户序列号
		map.put("DATE_EXPR", bean.getCardPov());//卡片有效期
		Map<String,String> params = new HashMap<String,String>();
		
		String reqMsg=CreateXmlMsg.BCK_03453(map);
		
		SocketClient socketClient =new SocketClient();
		socketClient.createSocket();
		
		logger.info("请求报文："+reqMsg);
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK03453ResBean.class);
		BCK03453ResBean bck03453ResBean = (BCK03453ResBean)reqXs.fromXML(resMsg);
		if(bck03453ResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","调用接口03453失败");
			return params;
		}
		String resCode = bck03453ResBean.getHeadBean().getResCode();
		String errMsg = bck03453ResBean.getHeadBean().getResMsg();
		logger.info("前置系统参数查询-resCode："+resCode+"前置系统参数查询-resMsg："+resMsg);
		
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		
		bean.setBalance(bck03453ResBean.getBody().getBALANCE());//结存额
		bean.setCardAmt(bck03453ResBean.getBody().getAVL_BALANCE());//可用余额
		params.put("resCode",resCode);
		return params;
	} 
	
	

	/**
	 * 个人IC卡验证(卡75048)	-前置【07602】
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static Map inter07602(PublicAccTransferBean bean)throws Exception{
		//获取数据
		Map<String,String> map = new HashMap<String,String>();
		map.put("IC_CHL", "");
		map.put("CARD_NO",bean.getChuZhangCardNo());//卡号
		map.put("AID", "A000000333010101");
		map.put("ARQC", bean.getArqc());
		map.put("ARQC_SRC_DATA", bean.getArqcSrcData());
		map.put("TERM_CHK_VALUE", bean.getTermChkValue());
		map.put("APP_ACCT_SEQ", bean.getAppAcctSeq());
		map.put("ISS_APP_DATA", bean.getIssAppData());
		map.put("CARD_POV", bean.getCardPov());
		
		Map<String,String> params = new HashMap<String,String>();
		
		String reqMsg=CreateXmlMsg.BCK_07602(map);
		
		SocketClient socketClient =new SocketClient();
		socketClient.createSocket();
		
		logger.info("请求报文："+reqMsg);
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK07602ResBean.class);
		BCK07602ResBean bck07602ResBean = (BCK07602ResBean)reqXs.fromXML(resMsg);
		if(bck07602ResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","调用接口07602失败");
			return params;
		}
		String resCode = bck07602ResBean.getHeadBean().getResCode();
		String errMsg = bck07602ResBean.getHeadBean().getResMsg();
		logger.info("前置系统参数查询-resCode："+resCode+"前置系统参数查询-resMsg："+resMsg);
		
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		
		params.put("resCode",resCode);
		return params;
	} 
	
	/**
	 * 权限明细查询【78010】(前置交易码03740)
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static Map inter03740(PublicAccTransferBean bean)throws Exception{
		//获取数据
		Map<String,String> map = new HashMap<String,String>();
		map.put("QRY_OPTION", "0");//查询选项
		map.put("CARD_NO", bean.getChuZhangCardNo());//卡号
		map.put("MP_NO", "");//手机号
		Map params = new HashMap();
		
		String reqMsg=CreateXmlMsg.BCK_03740(map);
		
		SocketClient socketClient =new SocketClient();
		socketClient.createSocket();
		
		logger.info("请求报文："+reqMsg);
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		
		if(resMsg == null){
			params.put("resCode","4444");
			params.put("errMsg","调用接口03740失败");
			return params;
		}
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK03740ResBean.class);
		BCK03740ResBean bck03740ResBean = (BCK03740ResBean)reqXs.fromXML(resMsg);
		String resCode = bck03740ResBean.getHeadBean().getResCode();
		String errMsg = bck03740ResBean.getHeadBean().getResMsg();
		logger.info("权限明细查询【78010】(前置交易码03740)-resCode："+resCode+"权限明细查询【78010】(前置交易码03740)："+resMsg);
		
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		
		String fileName = bck03740ResBean.getBody().getFILE_NAME();
		List<TelMsgBean> list  = null;
		try {
			// 下载文件
			FtpFileUtils.downloadFileByFTPClient(Property.FTP_IP_DOWN, Integer.parseInt(Property.FTP_PORT_DOWN), Property.FTP_USER_DOWN, Property.FTP_PWD_DOWN, fileName, Property.FTP_LOCAL_PATH + fileName);
			list = TextFileReader.paddingList(Property.FTP_LOCAL_PATH + fileName, TelMsgBean.class);
		} catch (Exception e) {
			logger.error("短信提醒信息下载失败"+e);
			params.put("resCode","4444");
			params.put("errMsg","短信提醒信息下载失败");
			return params;
		}
		params.put(TEL_MSG, list);
		params.put("resCode",resCode);
		return params;
	} 
	
	/**
	 * 校验对手信息【77016】(前置交易码02954)
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static Map inter02954(PublicAccTransferBean bean)throws Exception{
		//获取数据
		Map<String,String> map = new HashMap<String,String>();
		map.put("CARD_NO",bean.getChuZhangCardNo());//卡号
		map.put("OTHER_ACCT_NO",bean.getRuZhangCardNo());//对方账号
		map.put("OPER_CHOOSE", "1");//操作选择
		
		Map<String,String> params = new HashMap<String,String>();
		
		String reqMsg=CreateXmlMsg.BCK_02954(map);
		
		SocketClient socketClient =new SocketClient();
		socketClient.createSocket();
		
		logger.info("请求报文："+reqMsg);
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK02954ResBean.class);
		BCK02954ResBean bck02954ResBean = (BCK02954ResBean)reqXs.fromXML(resMsg);
		if(bck02954ResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","调用接口02954失败");
			return params;
		}
		String resCode = bck02954ResBean.getHeadBean().getResCode();
		String errMsg = bck02954ResBean.getHeadBean().getResMsg();
		logger.info("前置系统参数查询-resCode："+resCode+"前置系统参数查询-resMsg："+resMsg);
		
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		
		params.put("resCode",resCode);
		return params;
	} 
	
	
	/**
	 * 查询银行行名
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static String inter0008(Map<String,String> param)throws Exception{
		//获取数据
		Map<String,String> map = new HashMap<String,String>();
		map.put("bankNo","");
		map.put("bankName", param.get("bankName"));
		map.put("cityName", param.get("cityName"));
		map.put("cityCode", "");
		map.put("provinceCode", param.get("provinceCode"));
		
		String reqMsg=CreateXmlMsg.Tms_0008(map);
		
		SocketClient socketClient =new SocketClient();
		socketClient.createSocket();
		
		logger.info("请求报文："+reqMsg);
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", Tms0008ResBean.class);
		Tms0008ResBean tms0008ResBean = (Tms0008ResBean)reqXs.fromXML(resMsg);
		String infos=tms0008ResBean.getBodyBean().getBankInfoList();
		return infos;
	} 
	
	/**
	 * 定时发送交易信息查询
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static Map interCM022(PublicAccTransferBean bean)throws Exception{
		//获取数据
		Map<String,String> map = new HashMap<String,String>();
		map.put("TRAN_CHANNEL", "0035");//交易渠道
		map.put("START_DATE", "");//起始日期
		map.put("END_DATE", "");//终止日期
		map.put("QRY_INST_NO", "");//交易机构
		map.put("QRY_TRAN_CODE", "");//交易码(大额往帐：020010（综合服务端：020130）小额往帐：022010（综合服务端：022240）行内汇划往帐：026000（综合服务端：026000）)
		map.put("SEND_STAT", "00");//交易状态
		map.put("QRY_TASK_ID", "");//任务号
		map.put("MS_ACCT_NO", bean.getChuZhangCardNo());//付款账号
		map.put("TRAN_AMT", "");//交易金额
		Map params = new HashMap();
		
		String reqMsg=CreateXmlMsg.BCK_CM022(map);
		
		SocketClient socketClient =new SocketClient();
		socketClient.createSocket();
		
		logger.info("请求报文："+reqMsg);
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCKCM022ResBean.class);
		BCKCM022ResBean bckCM022ResBean = (BCKCM022ResBean)reqXs.fromXML(resMsg);
		if(bckCM022ResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","调用接口CM022失败");
			return params;
		}
		String resCode = bckCM022ResBean.getHeadBean().getResCode();
		String errMsg = bckCM022ResBean.getHeadBean().getResMsg();
		logger.info("定时发送交易信息查询-resCode："+resCode+"定时发送交易信息查询-resMsg："+resMsg);
		
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		String fileName = bckCM022ResBean.getBody().getFILE_NAME().trim();
		List<TransferSelectBean>  list = null;
		try {
			// 下载文件
			FtpFileUtils.downloadFileByFTPClient(Property.FTP_IP_DOWN, Integer.parseInt(Property.FTP_PORT_DOWN), Property.FTP_USER_DOWN, Property.FTP_PWD_DOWN, fileName, Property.FTP_LOCAL_PATH + fileName);
			list = TextFileReader.paddingList(Property.FTP_LOCAL_PATH + fileName, TransferSelectBean.class);
		} catch (Exception e) {
			logger.error("历史往账交易信息下载失败"+e);
			params.put("resCode","4444");
			params.put("errMsg","历史往账交易信息下载失败");
			return params;
		}
		params.put(TRANSFER_CANCEL_MSG,list);
		params.put("resCode",resCode);
		return params;
	} 
	
	/**
	 * 定时发送交易撤销
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static Map inter02693(PublicAccTransferBean bean)throws Exception{
		//获取数据
		Map<String,String> map = new HashMap<String,String>();
		map.put("ORIG_CHANNEL", bean.getOrigChannel());//原交易渠道
		map.put("ORIG_SYS_DATE", bean.getOrigSysDate());//原交易前置日期
		map.put("ORIG_TASK_ID", bean.getOrigTaskId());//原交易任务号
		Map<String,String> params = new HashMap<String,String>();
		
		String reqMsg=CreateXmlMsg.BCK_02693(map);
		
		SocketClient socketClient =new SocketClient();
		socketClient.createSocket();
		
		logger.info("请求报文："+reqMsg);
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		
		if(resMsg == null){
			params.put("resCode","4444");
			params.put("errMsg","未获取撤销返回数据");
			return params;
		}
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK02693ResBean.class);
		BCK02693ResBean bck02693ResBean = (BCK02693ResBean)reqXs.fromXML(resMsg);
		String resCode = bck02693ResBean.getHeadBean().getResCode();
		String errMsg = bck02693ResBean.getHeadBean().getResMsg();
		logger.info("定时发送交易撤销-resCode："+resCode+"定时发送交易撤销-resMsg："+resMsg);
		
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		bean.setMsJrnlNo(bck02693ResBean.getBody().getMS_JRNL_NO().trim());//撤销交易前置流水号
		bean.setMsTranDate(bck02693ResBean.getBody().getMS_TRAN_DATE().trim());//撤销交易前置日期(YYYYMMDD)
		params.put("resCode",resCode);
		return params;
	} 
	
	/**
	 * 账户限额查询【02879】-前置02781
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static Map inter02781(PublicAccTransferBean bean)throws Exception{
		//获取数据
		Map<String,String> map = new HashMap<String,String>();
		map.put("DB_CR_FLAG", "1");//借贷标志
		map.put("ACCT_NO",bean.getChuZhangCardNo());//卡号
		map.put("OPPO_ACCT_NO",bean.getRuZhangCardNo());//对方账号
		map.put("TRAN_AMT",bean.getRemitAmt());//发生额
		
		Map<String,String> params = new HashMap<String,String>();
		
		String reqMsg=CreateXmlMsg.BCK_02781(map);
		
		SocketClient socketClient =new SocketClient();
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
		logger.info("前置系统参数查询-resCode："+resCode+"前置系统参数查询-resMsg："+resMsg);
		
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		
		params.put("resCode",resCode);
		return params;
	} 
	
	
	/**
	 * 转账客户列表信息删除-前置07496
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static Map inter07496(Map<String,String> deletemap)throws Exception{
		//获取数据
		Map<String,String> map = new HashMap<String,String>();
		map.put("PAY_ACCT_NO", deletemap.get("chuZhangCardNo"));//转出账号
		map.put("PAY_ACCT_NAME",deletemap.get("chuZhangCardName"));//转出户名
		map.put("PAYEE_ACCT_NO",deletemap.get("ruZhangCardNo"));//转入账号
		map.put("PAYEE_ACCT_NAME",deletemap.get("ruZhangCardName"));//转入户名
		map.put("QRY_CHNL", "0035");//查询渠道
		
		Map<String,String> params = new HashMap<String,String>();
		
		String reqMsg=CreateXmlMsg.BCK_07496(map);
		
		SocketClient socketClient =new SocketClient();
		socketClient.createSocket();
		
		logger.info("请求报文："+reqMsg);
		String resMsg=socketClient.sendMesg(reqMsg, "UTF-8");
		logger.info("响应报文："+resMsg);
		
		XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
		reqXs.alias("Root", BCK07496ResBean.class);
		BCK07496ResBean bck07496ResBean = (BCK07496ResBean)reqXs.fromXML(resMsg);
		if(bck07496ResBean == null){
			params.put("resCode","4444");
			params.put("errMsg","调用接口07496失败");
			return params;
		}
		String resCode = bck07496ResBean.getHeadBean().getResCode();
		String errMsg = bck07496ResBean.getHeadBean().getResMsg();
		logger.info("前置系统参数查询-resCode："+resCode+"前置系统参数查询-resMsg："+resMsg);
		
		if(!SUCCESS.equals(resCode)){
			params.put("resCode",resCode);
			params.put("errMsg",errMsg);
			return params;
		}
		
		params.put("resCode",resCode);
		return params;
	} 
}
