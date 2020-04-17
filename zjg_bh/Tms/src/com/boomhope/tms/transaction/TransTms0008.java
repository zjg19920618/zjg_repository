package com.boomhope.tms.transaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.boomhope.tms.entity.TCnapsBkinfo;
import com.boomhope.tms.message.XStreamNameCoder;
import com.boomhope.tms.message.in.InReqHeadBean;
import com.boomhope.tms.message.in.InResHeadBean;
import com.boomhope.tms.message.in.tms.Tms0008ReqBean;
import com.boomhope.tms.message.in.tms.Tms0008ReqBodyBean;
import com.boomhope.tms.message.in.tms.Tms0008ResBean;
import com.boomhope.tms.message.in.tms.Tms0008ResBodyBean;
import com.boomhope.tms.service.ITCnapsBkinfoService;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * 
 * @Description: 凭证号信息修改
 * @author zjg   
 * @date 2016年11月16日 下午12:17:32
 */
public class TransTms0008 extends BaseTransaction{
	Logger logger = Logger.getLogger(TransTms0008.class);
	private Tms0008ReqBean reqBean;	// 渠道请求Bean
	private Tms0008ResBean resBean;	// 渠道响应Bean
	/***
	 * 交易处理
	 * @param inReqMsg
	 * @return
	 */
	public String handle(String inReqMsg){
		
		/**
		 * 1. 解析渠道请求报文
		 * 2. 修改凭证信息
		 * 3. 生成渠道响应报文
		 */
		ITCnapsBkinfoService tCnapsBkinfoService =null;
		try {
			tCnapsBkinfoService  = (ITCnapsBkinfoService)TransTms0008.getBean("TCnapsBkinfoServiceImpl");
		} catch (Exception e) {
			logger.error("银行信息查询service失败",e);
			return makeFailMsg("999999", "银行信息查询service失败:" + e.getMessage());
		}
		try {
			/* 解析请求报文并生成请求报文Bean */
			XStream reqXs = new XStream(new DomDriver("UTF-8"));
			reqXs.alias("Root", Tms0008ReqBean.class);
			reqXs.alias("Head", InReqHeadBean.class);
			reqXs.alias("Body", Tms0008ReqBodyBean.class);
			reqBean = (Tms0008ReqBean)reqXs.fromXML(inReqMsg);
			List<TCnapsBkinfo> list=new ArrayList<>();
			try {
				/* 查询银行行号流水号 */
				Map map=new HashMap();
				map.put("bankName", reqBean.getBodyBean().getBankName());
				map.put("cityCode", reqBean.getBodyBean().getCityCode());
				map.put("cityName", reqBean.getBodyBean().getCityName());
				map.put("provinceCode", reqBean.getBodyBean().getProvinceCode());
				map.put("bankNo", reqBean.getBodyBean().getBankNo());
				list=tCnapsBkinfoService.getBankInfoList(map);
			} catch (Exception e) {
				logger.error("连接数据库异常", e);
				return makeFailMsg("999999", "连接数据库异常");
			}
			
			Tms0008ResBodyBean resBodyBean=new Tms0008ResBodyBean();
			resBodyBean.setBankNum(list.size()+"");
			StringBuffer sb=new StringBuffer("");
			for (TCnapsBkinfo tCnapsBkinfo : list) {
				sb.append(tCnapsBkinfo.getBankNo());
				sb.append(",");
				sb.append(tCnapsBkinfo.getLname());
				sb.append(",");
				sb.append(tCnapsBkinfo.getClrBankNo());
				sb.append(",");
				sb.append(tCnapsBkinfo.getSname());
				sb.append(";");
			}
			resBodyBean.setBankInfoList(sb.toString());
			
			/* 生成渠道响应报文Bean */
			resBean = new Tms0008ResBean(); 
			resBean.getHeadBean().setResCode("000000");
			resBean.getHeadBean().setResMsg("交易成功");
			resBean.setBodyBean(resBodyBean);
			
			/* 生成渠道响应报文*/
			XStream resXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			
			resXs.alias("Root", Tms0008ResBean.class);
			resXs.alias("Head", InResHeadBean.class);
			resXs.alias("Body", Tms0008ResBodyBean.class);
			return resXs.toXML(resBean);
		} catch (Exception e) {
			logger.error("生成响应报文异常", e);
			return makeFailMsg("999999", "生成响应报文异常");
		}
	}
	
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "applicationContext.xml"});
		try {
			ITCnapsBkinfoService tCnapsBkinfoService  = (ITCnapsBkinfoService)context.getBean("TCnapsBkinfoServiceImpl");
			List<TCnapsBkinfo> list=new ArrayList<>();
			/* 查询银行行号流水号 */
			Map map=new HashMap();
			map.put("bankName", "招行");
			map.put("cityCode", "");
			map.put("cityName", "济南");
			map.put("provinceCode", "4510");
			map.put("bankNo", "");
			list=tCnapsBkinfoService.getBankInfoList(map);
			StringBuffer sb=new StringBuffer("");
			for (TCnapsBkinfo tCnapsBkinfo : list) {
				sb.append(tCnapsBkinfo.getBankNo());
				sb.append(",");
				sb.append(tCnapsBkinfo.getLname());
				sb.append(",");
				sb.append(tCnapsBkinfo.getClrBankNo());
				sb.append(",");
				sb.append(tCnapsBkinfo.getSname());
				sb.append(";");
			}
			System.out.println(sb);
//			for (int i = 0; i < 6; i++) {
//				TCnapsBkinfo bankInfo=new TCnapsBkinfo();
//				bankInfo.setBankNo("test1"+i);
//				bankInfo.setAddr("1");
//				bankInfo.setBankType("1");
//				bankInfo.setAlttype("1");
//				bankInfo.setCenterCode("1");
//				bankInfo.setEffdate("1");
//				bankInfo.setAlttype("CC00");
//				list.add(bankInfo);
//			}
//			
//			for (int i = 0; i < 4; i++) {
//				TCnapsBkinfo bankInfo=new TCnapsBkinfo();
//				bankInfo.setBankNo("test1"+i);
//				bankInfo.setAddr("2");
//				bankInfo.setBankType("2");
//				bankInfo.setAlttype("2");
//				bankInfo.setCenterCode("1");
//				bankInfo.setEffdate("1");
//				bankInfo.setFlag("2");
//				bankInfo.setInstType("3");
//				bankInfo.setAlttype("CC01");
//				list.add(bankInfo);
//			}
//			
//
//			for (int i = 0; i < 2; i++) {
//				TCnapsBkinfo bankInfo=new TCnapsBkinfo();
//				bankInfo.setBankNo("test1"+i);
//				bankInfo.setAddr("2");
//				bankInfo.setBankType("2");
//				bankInfo.setAlttype("2");
//				bankInfo.setCenterCode("1");
//				bankInfo.setEffdate("1");
//				bankInfo.setAlttype("CC02");
//				list.add(bankInfo);
//			}
//			
//			tCnapsBkinfoService.updateBankInfoList(list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		for (int i = 0; i < 5; i++) {
//			TCnapsBkinfo bankInfo=new TCnapsBkinfo();
//			bankInfo.setBankNo("test"+i);
//			bankInfo.setAddr("1");
//			bankInfo.setBankType("1");
//			bankInfo.setAlttype("1");
//			bankInfo.setCenterCode("1");
//			bankInfo.setEffdate("1");
//		}
	}
	
	
}
