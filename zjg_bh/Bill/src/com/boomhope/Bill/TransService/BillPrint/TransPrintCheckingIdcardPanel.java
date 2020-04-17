package com.boomhope.Bill.TransService.BillPrint;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Public.GlobalPanelFlag;
import com.boomhope.Bill.Public.GlobalParameter;
import com.boomhope.Bill.Socket.SocketClient;
import com.boomhope.Bill.TransService.BillPrint.Bean.BillPrintBean;
import com.boomhope.Bill.TransService.BillPrint.Interface.CardsInfo;
import com.boomhope.Bill.TransService.BillPrint.Interface.IdCardCheckInfo;
import com.boomhope.Bill.TransService.BillPrint.Interface.InterfaceSendMsg;
import com.boomhope.Bill.Util.DateUtil;
import com.boomhope.Bill.Util.FileUtil;
import com.boomhope.Bill.Util.FtpFileUtils;
import com.boomhope.Bill.Util.Property;
import com.boomhope.Bill.Util.UtilPreFile;
import com.boomhope.Bill.monitor.sendInterface.MakeMonitorInfo;
import com.boomhope.tms.message.XStreamNameCoder;
import com.boomhope.tms.message.in.bck.BCK0001ResBean;
import com.boomhope.tms.message.in.bck.BCK0008ResBean;
import com.boomhope.tms.message.in.bck.BCK01597ResBean;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/** 
 * title:校验身份证
 * @author ly
 * 2016年11月7日下午9:53:12
 */
public class TransPrintCheckingIdcardPanel extends BaseTransPanelNew {
	Timer checkTimer = null;
	static Logger logger = Logger.getLogger(TransPrintCheckingIdcardPanel.class);

	public TransPrintCheckingIdcardPanel(final BillPrintBean transBean) {
		logger.info("进入本人联网核查页面");
		this.billPrintBean= transBean;
		
		/* 显示时间倒计时 */
		this.showTimeText(delaySecondShortTime);
		delayTimer = new Timer(delaySecondShortTime * 1000, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {  
            	/* 倒计时结束退出交易 */ 
            	clearTimeText();
            	serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！","","");
            }      
        });            
		delayTimer.start(); 
		
		/* 标题信息 */
		JLabel titleLabel = new JLabel("本人信息查询中，请稍后。。。");
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		titleLabel.setForeground(Color.decode("#412174"));
		titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 40));
		titleLabel.setBounds(0, 60, 1009, 60);
		this.showJpanel.add(titleLabel);
		
		/* 加载核查动画 */
		JLabel billImage = new JLabel();   
		billImage.setIcon(new ImageIcon("pic/checking.gif"));
		billImage.setIconTextGap(6);
		billImage.setBounds(395, 254, 220, 159);
		this.showJpanel.add(billImage);
		
		
		/* 执行身份检查 */
		checkTimer = new Timer(1000, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {  
            	stopTimer(checkTimer);
            	checkIdCard(transBean);
            }      
        });            
		checkTimer.start(); 
	
		
		
	
	}
	/**
	 * 成功
	 */
	private void success(BillPrintBean transBean){
		
		String idCard_check_result = transBean.getImportMap().get("agent_persion");
		clearTimeText();
		if("yes".equals(idCard_check_result)){
			//代理人
			if("0".equals(transBean.getPrintChoice())){
				openPanel(new TransPrintOrStateChage(transBean));
			}else{
				boolean result = getCardInfo();
				if(!result){
					return;
				}
				clearTimeText();
				openPanel(new TransPrintAgreementShowCardsPanel());
			}
		}else{
			openPanel(new TransferPrintCameraPanel(transBean));
		}
	}
	
	/**
	 * 若打印协议，则直接查询身份信息并查询卡信息
	 */
	public boolean getCardInfo(){
		
		//个人客户信息查询
		try {
			billPrintBean.getReqMCM001().setReqBefor("07519");
			Map map =InterfaceSendMsg.inter07519(billPrintBean);
			billPrintBean.getReqMCM001().setReqAfter((String)map.get("resCode"),(String)map.get("errMsg"));
			if(!"000000".equals(map.get("resCode"))){
				logger.info("个人客户查询失败(07519)："+map.get("resCode")+map.get("errMsg"));
				MakeMonitorInfo.makeInfos(billPrintBean.getReqMCM001());
				clearTimeText();
				serverStop("个人客户信息查询失败，请联系大堂经理。", (String)map.get("errMsg"), "");
				return false;
			}
			
			List<IdCardCheckInfo> list = (List<IdCardCheckInfo>)map.get("ID_INFO");
			if(list.size()>0){
				for(IdCardCheckInfo subInfo : list){
					if(subInfo.getId_No().equals(billPrintBean.getReadIdcard())){
						logger.info("对应的客户号："+subInfo.getCust_No());
						billPrintBean.setCustNo(subInfo.getCust_No());
					}
				}
			}else{
				logger.info("没有查到相应的客户号："+list.size());
				serverStop("没有查到相应的客户号，请联系大堂经理", "", "获取的文件中无内容");
				clearTimeText();
				return false;
			}
			
		} catch (Exception e) {
			logger.error("查询客户信息失败(07519):"+e);
			billPrintBean.getReqMCM001().setIntereturnmsg("调用07519接口异常");
			MakeMonitorInfo.makeInfos(billPrintBean.getReqMCM001());
			clearTimeText();
			serverStop("查询客户信息失败，请联系大堂经理。", "", "个人客户查询失败(07519)");
			return false;
		}
		
		//查询身份证下的卡信息
		try {
			billPrintBean.getReqMCM001().setReqBefor("07520");
			Map map = InterfaceSendMsg.inter07520(billPrintBean);
			billPrintBean.getReqMCM001().setReqAfter((String)map.get("resCode"),(String)map.get("errMsg"));
			if(!"000000".equals(map.get("resCode"))){
				logger.info("所有卡信息查询失败(07520)："+map.get("resCode")+map.get("errMsg"));
				MakeMonitorInfo.makeInfos(billPrintBean.getReqMCM001());
				clearTimeText();
				serverStop("所有卡信息查询失败，请联系大堂经理。", (String)map.get("errMsg"), "");
				return false;
			}
			List<CardsInfo> list = (List<CardsInfo>)map.get("CARDS_INFO");
			List<CardsInfo> list1 = new ArrayList<>();
			if(list.size()>0){
				logger.info("银行卡数量："+list.size());
				for(CardsInfo cardInfo : list){
					if("N000".equals(cardInfo.getCard_State())){
						list1.add(cardInfo);
					}else{
						continue;
					}
				}
			}else{
				logger.info("该身份证下没有卡信息：(获取的文件内容)"+list.size());
				clearTimeText();
				serverStop("该身份证下未查到任何银行卡信息", "", "银行卡条数:"+list.size());
				return false;
			}
			
			if(list1.size()>0){
				logger.info("可用银行卡数量："+list1.size());
				billPrintBean.getSubInfo().put("CardInfo", list1);
			}else{
				logger.info("该身份证下无可用卡(获取的正常卡数量)"+list1.size());
				clearTimeText();
				serverStop("该身份证下无正常状态的卡", "", "可用银行卡数量："+list1.size());
				return false;
			}
			
		} catch (Exception e) {
			logger.error("查询所有卡信息失败(07520)："+e);
			billPrintBean.getReqMCM001().setIntereturnmsg("调用07520接口异常");
			MakeMonitorInfo.makeInfos(billPrintBean.getReqMCM001());
			clearTimeText();
			serverStop("查询身份证下的所有卡信息失败，请联系大堂经理", "", "调用所有卡信息查询接口失败(07520)");
			return false;
		}
		return true;
	}
	
	
	/**
	 * 身份信息核查
	 */
	public void checkIdCard(BillPrintBean transBean){
		if (transBean.getReadIdcard().equals(transBean.getAgentIdCardNo())) {
			fail(transBean, "账户为代理人身份证，请插入本人身份证");
			return;
		}
		// 上送核心检查
		Map<String,String> map1 = new HashMap<String,String>();
		map1.put("id", transBean.getReadIdcard());
		logger.info("身份证号"+transBean.getReadIdcard());
		map1.put("idtype", "1");
		map1.put("name", transBean.getIdCardName());
		logger.info("名字"+transBean.getIdCardName());
		transBean.setCardName(transBean.getIdCardName());
		map1.put("cardNo", "");
//		map1.put("custName", transBean.getIdCardName());
		// 查询白名单
		billPrintBean.getReqMCM001().setReqBefor("01597");
		BCK01597ResBean resBean1 = getBillInfo1(map1);
		if(resBean1 == null){
			billPrintBean.getReqMCM001().setIntereturnmsg("调用01597接口异常");
			MakeMonitorInfo.makeInfos(billPrintBean.getReqMCM001());
			serverStop("请联系大堂经理","调用接口失败", "");
			return;
		}
		String resCode1 = resBean1.getHeadBean().getResCode();
		String resMsg1 = resBean1.getHeadBean().getResMsg();
		logger.info("查询白名单resCode"+resCode1);
		logger.info("查询白名单resMsg"+resMsg1);
		billPrintBean.getReqMCM001().setReqAfter(resCode1,resMsg1);
		if(!"000000".equals(resCode1)){
			MakeMonitorInfo.makeInfos(billPrintBean.getReqMCM001());
			serverStop("请联系大堂经理","调用接口失败:"+resMsg1, "");
			return;
		}
		String svrno1 = resBean1.getBody().getESB_LIST().getSTTN();
		if(!"0000".equals(svrno1)){
			if("0010".equals(svrno1)){
				String desc = resBean1.getBody().getESB_LIST().getSTTN();
				serverStop("请联系大堂经理", "","该客户为涉案客户，禁止交易。");
				return;
			}if("0020".equals(svrno1)){
				serverStop("请联系大堂经理", "","该客户可疑，谨防诈骗，请到我行柜台办理。");
				return;
			}
			
		}
		
		
		// 上送核心检查
		Map<String,String> map = new HashMap<String,String>();
		map.put("idCardNo", transBean.getReadIdcard());
		map.put("custName", transBean.getIdCardName());
		// 获取本人身份信息
		billPrintBean.getReqMCM001().setReqBefor("03445");
		BCK0001ResBean resBean = getBillInfo(map);
		if(resBean == null){
			billPrintBean.getReqMCM001().setIntereturnmsg("调用03445接口异常");
			MakeMonitorInfo.makeInfos(billPrintBean.getReqMCM001());
			serverStop("请联系大堂经理","","调用接口失败");
			return;
		}
		String resCode = resBean.getHeadBean().getResCode();
		String resMsg = resBean.getHeadBean().getResMsg();
		logger.info("证件信息查询resCode"+resCode);
		logger.info("证件信息查询resMsg"+resMsg);
		billPrintBean.getReqMCM001().setReqAfter(resCode,resMsg);
		if(!"000000".equals(resCode)){//不为000000，有可能是无记录，有可能是直接前置忙，
			if(resMsg.indexOf("0018") != -1){
				//如果为0018，则表示是无记录,此处不做处理，下面做处理
			}else{
				MakeMonitorInfo.makeInfos(billPrintBean.getReqMCM001());
				serverStop("请联系大堂经理", "","调用接口失败:"+resMsg);
				return;
			}
		}
		transBean.setSvrDate(resBean.getBody().getSVR_DATE().trim());
		if(transBean.getSvrDate()==null||"".equals(transBean.getSvrDate())){
			SimpleDateFormat PRE_FORMAT = new SimpleDateFormat("yyyyMMdd");
			Calendar now = Calendar.getInstance();
			transBean.setSvrDate(PRE_FORMAT.format(now.getTime()));
		}
		
		//先进行联网核查，
		Map<String,String> mapPoic = new HashMap<String,String>();
		mapPoic.put("id", transBean.getReadIdcard());
		mapPoic.put("name", transBean.getIdCardName());
		billPrintBean.getReqMCM001().setReqBefor("07670");
		BCK0008ResBean bck0008resBean = getPoiceInfo(mapPoic);
		resCode = bck0008resBean.getHeadBean().getResCode();
		resMsg = bck0008resBean.getHeadBean().getResMsg();
		logger.info("本人身份核查resCode："+resCode);
		logger.info("本人身份核查resMsg："+resMsg);
		billPrintBean.getReqMCM001().setReqAfter(resCode,resMsg);
		if(!"000000".equals(resCode)){
			MakeMonitorInfo.makeInfos(billPrintBean.getReqMCM001());
			serverStop("请联系大堂经理","联网核查失败:"+resMsg, "");
			return;
		}
		try {
			String fileName = bck0008resBean.getBody().getFILE_NAME();
			FtpFileUtils.downloadFileByFTPClient(Property.FTP_IP_DOWN, Integer.parseInt(Property.FTP_PORT_DOWN), Property.FTP_USER_DOWN, Property.FTP_PWD_DOWN, fileName, Property.FTP_LOCAL_PATH + fileName);
			UtilPreFile.getIdCardImage(Property.FTP_LOCAL_PATH+fileName,Property.ID_CARD_SELF);
			// 本人联网核查照片查看
			String tmp = Property.BH_TMP + "\\"+DateUtil.getNowDate("yyyyMMdd")+"\\" +transBean.getFid()+"\\";
			// 拷贝临时图片--------------------
			File from_f = new File(Property.ID_CARD_SELF);
			File to_f = new File(tmp+transBean.getReadIdcard()+"_mePoic.jpg");
			FileUtil.copyFileUsingJava7Files(from_f, to_f);
		} catch (Exception e) {
			serverStop("请联系大堂经理", "","获取联网核查结果图片失败");
			logger.error(e);
			return;
		}
		// 成功处理
		success(transBean);
		
		
	}
	
	
	
	private void fail(BillPrintBean transBean,String string) {
		transBean.setErrmsg(string);
		transBean.getImportMap().put("backStep", GlobalPanelFlag.INPUT_IDCARD+"");
		openPanel(new TransErrorPrintMsgPanel(transBean));
	}
	/**
	 * 获取本人身份证
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public BCK0001ResBean getBillInfo(Map<String,String> map)  {
		String retMsg = "";
		SocketClient sc = new SocketClient();
		Socket socket=null;
		InputStream is=null;
		 OutputStream os=null;
		try {
			 socket =sc.createSocket();
            //构建IO  
             is = socket.getInputStream();  
             os = socket.getOutputStream();  
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));  
            //向服务器端发送一条消息  
            bw.write(sc.BCK_0001(map) + "\n");  
            bw.flush();  
            //读取服务器返回的消息  
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));  
			
			String value = null;
			while ((value = br.readLine()) != null) {
				retMsg += value + "\n";
				if ("</Root>".equals(value)){
					break;
				}
			}
			logger.info(retMsg);
			XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			reqXs.alias("Root", BCK0001ResBean.class);
			BCK0001ResBean bck0002ResBean = (BCK0001ResBean)reqXs.fromXML(retMsg);
			logger.info(bck0002ResBean);
			logger.info("CLIENT retMsg:" + retMsg);
			return bck0002ResBean;
		} catch (UnknownHostException e) {
			logger.error(e);
			return null;
		} catch (IOException e) {
			logger.error(e);
			return null;
		}finally{
			try {
				if(os!=null){
					os.close();
				}
				if(is!=null){
					is.close();
				}
				if(socket!=null){
					socket.close();
				}
			} catch (Exception e2) {
				logger.info(e2);
				return null;
			}
		}
	}
	
	/**
	 * 联网核查
	 */
	public BCK0008ResBean getPoiceInfo(Map<String,String> map){
		
		String retMsg = "";
		SocketClient sc = new SocketClient();
		Socket socket=null;
		InputStream is=null;
		OutputStream os=null;
		try {
			 socket = sc.createSocket();
            //构建IO  
             is = socket.getInputStream();  
             os = socket.getOutputStream();  
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));  
            //向服务器端发送一条消息  
            bw.write(sc.BCK_07670(map) + "\n");  
            bw.flush();  
            //读取服务器返回的消息  
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));  
			String value = null;
			while ((value = br.readLine()) != null) {
				retMsg += value + "\n";
				if ("</Root>".equals(value)){
					break;
				}
			}
			logger.info(retMsg);
			XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			reqXs.alias("Root", BCK0008ResBean.class);
			BCK0008ResBean bck0002ResBean = (BCK0008ResBean)reqXs.fromXML(retMsg);
			logger.info(bck0002ResBean);
			logger.info("CLIENT retMsg:" + retMsg);
			return bck0002ResBean;
		} catch (UnknownHostException e) {
			logger.error(e);
			return null;
		} catch (IOException e) {
			logger.error(e);
			return null;
		}finally{
			try {
				if(os!=null){
					os.close();
				}
				if(is!=null){
					is.close();
				}
				if(socket!=null){
					socket.close();
				}
			} catch (Exception e2) {
				logger.info(e2);
				return null;
			}
		}
	}
	
	/**
	 * 调用接口是否白名单
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public BCK01597ResBean getBillInfo1(Map<String,String> map1)  {
		String retMsg = "";
		SocketClient sc = new SocketClient();
		Socket socket=null;
		InputStream is=null;
		 OutputStream os=null;
		try {
			socket = sc.createSocket();
            //构建IO  
            is = socket.getInputStream();  
            os = socket.getOutputStream();  
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));  
            //向服务器端发送一条消息  
            bw.write(sc.BCK_01597(map1) + "\n");  
            bw.flush();  
            //读取服务器返回的消息  
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));  
			
			String value = null;
			while ((value = br.readLine()) != null) {
				retMsg += value + "\n";
				if ("</Root>".equals(value)){
					break;
				}
			}
			logger.info(retMsg);
			XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			reqXs.alias("Root", BCK01597ResBean.class);
			BCK01597ResBean bck01597ResBean = (BCK01597ResBean)reqXs.fromXML(retMsg);
			logger.info("CLIENT retMsg:" + retMsg);
			return bck01597ResBean;
		} catch (UnknownHostException e) {
			logger.error(e);
			return null;
		} catch (IOException e) {
			logger.error(e);
			return null;
		}finally{
			try {
				if(os!=null){
					os.close();
				}
				if(is!=null){
					is.close();
				}
				if(socket!=null){
					socket.close();
				}
			} catch (Exception e2) {
				logger.error(e2);
				return null;
			}
		}
	}
	
	

	
}
