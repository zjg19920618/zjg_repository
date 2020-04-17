package com.boomhope.Bill.TransService.CashOpen.moneybox;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Public.GlobalPanelFlag;
import com.boomhope.Bill.Public.GlobalParameter;
import com.boomhope.Bill.Socket.SocketClient;
import com.boomhope.Bill.TransService.CashOpen.Bean.PublicCashOpenBean;
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
import com.boomhope.tms.message.in.bck.BCK04422ResBean;
import com.boomhope.tms.message.moneybox.MoneyBoxQueryOrderReqBean;
import com.hiaward.quicktrans.util.encrypt.Encrypter;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * 现金开户校验身份证
 * @author gyw
 *
 */
public class MoneyBoxCheckIdCardingPanel  extends BaseTransPanelNew{
	private static final long serialVersionUID = 1L;
	Timer checkTimer = null;
	static Logger logger = Logger.getLogger(MoneyBoxCheckIdCardingPanel.class);
	public MoneyBoxCheckIdCardingPanel(final PublicCashOpenBean  transBean){
		this.cashBean = transBean;
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
	private void success(PublicCashOpenBean transBean){
		
		String idCard_check_result = transBean.getImportMap().get("agent_persion");
		clearTimeText();
		if("yes".equals(idCard_check_result)){
			//代理人
			openPanel(new MoneyBoxInputAgentIdcardPanel(transBean));
				
		}else{
			transBean.setJijvOrPuhui("");//清空普惠标示
			openPanel(new MoneyBoxPageSelectlPanel(transBean));
				
		}
	}
	
	/**
	 * 身份信息核查
	 */
	public void checkIdCard(final PublicCashOpenBean transBean){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
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
			
			transBean.getReqMCM001().setReqBefor("01597");//接口调用前流水信息记录
			
			// 查询白名单
			BCK01597ResBean resBean1 = getBillInfo1(map1);
			if(resBean1 == null){
				transBean.getReqMCM001().setIntereturnmsg("查询白名单失败：调用01597接口异常");
				MakeMonitorInfo.makeInfos(transBean.getReqMCM001());//接口调用失败，上送流水信息
				serverStop("请联系大堂经理","调用接口失败", "");
				return;
			}
			String resCode1 = resBean1.getHeadBean().getResCode();
			String resMsg1 = resBean1.getHeadBean().getResMsg();
			transBean.getReqMCM001().setReqAfter(resCode1,resMsg1);//接口调用成功后记录流水信息
			logger.info("查询白名单resCode"+resCode1);
			logger.info("查询白名单resMsg"+resMsg1);
			if(!"000000".equals(resCode1)){
				MakeMonitorInfo.makeInfos(transBean.getReqMCM001());//接口调用失败，上送流水信息
				serverStop("请联系大堂经理",resMsg1, "");
				return;
			}
			String svrno1 = resBean1.getBody().getESB_LIST().getSTTN();
			if(!"0000".equals(svrno1)){
				if("0010".equals(svrno1)){
					String desc = resBean1.getBody().getESB_LIST().getSTTN();
					transBean.getReqMCM001().setIntereturnmsg("该客户为涉案客户，禁止交易");
					MakeMonitorInfo.makeInfos(transBean.getReqMCM001());//接口调用失败，上送流水信息
					serverStop("请联系大堂经理", "","该客户为涉案客户，禁止交易。");
					return;
				}if("0020".equals(svrno1)){
					transBean.getReqMCM001().setIntereturnmsg("该客户可疑");
					MakeMonitorInfo.makeInfos(transBean.getReqMCM001());//接口调用失败，上送流水信息
					serverStop("请联系大堂经理", "","该客户可疑，谨防诈骗，请到我行柜台办理。");
					return;
				}
				
			}
			
			//调用钱柜接口
			Map<String, String> queryOrderMap = new HashMap<String, String>();
			queryOrderMap.put("IDNum", transBean.getReadIdcard().trim());
			queryOrderMap.put("OrderNum", transBean.getOrderNo().trim());
			//这里需要给密码加密#待处理
			String passWord = Encrypter.encrypt(Property.getProperties().getProperty("parent_unit_no"), GlobalParameter.branchNo, transBean.getReadIdcard().trim(), transBean.getInputOrderPwd());
			transBean.setOrderPwd(passWord);
			queryOrderMap.put("Password", passWord);
			transBean.getReqMCM001().setReqBefor("QG0001");//接口调用前流水信息记录
			MoneyBoxQueryOrderReqBean moneyBoxQueryOrder = getMoneyBoxQueryOrder(queryOrderMap);
			if(moneyBoxQueryOrder == null){
				transBean.getReqMCM001().setIntereturnmsg("调用钱柜接口异常");
				MakeMonitorInfo.makeInfos(transBean.getReqMCM001());//接口调用失败，上送流水信息
				serverStop("请联系大堂经理", "","调用钱柜接口异常");
				return;
			}
			String retCode = moneyBoxQueryOrder.getResponse().getRetCode().trim();
			String errorMessage = moneyBoxQueryOrder.getResponse().getErrorMessage().trim();
			if("44444".equals(moneyBoxQueryOrder.getResponse().getRetCode())){
				transBean.getReqMCM001().setIntereturnmsg("调用钱柜接口异常");
				MakeMonitorInfo.makeInfos(transBean.getReqMCM001());//接口调用失败，上送流水信息
				serverStop("请联系大堂经理","","调用钱柜接口异常");
				return;
			}
			if(!"0000".equals(retCode)){
				transBean.getReqMCM001().setIntereturnmsg("调用钱柜接口查询失败："+errorMessage);
				MakeMonitorInfo.makeInfos(transBean.getReqMCM001());//接口调用失败，上送流水信息
				serverStop("请联系大堂经理",errorMessage, "");
				return;
			}
			//订单状态未知
			String orderStatus = moneyBoxQueryOrder.getResponse().getOrderStatus();
			transBean.setOrderStatus(orderStatus);
			//if(!"0".equals(orderStatus) && !"2".equals(orderStatus)){
			if(!"0".equals(orderStatus) ){
				transBean.getReqMCM001().setIntereturnmsg("调用钱柜接口查询失败：订单状态异常");
				MakeMonitorInfo.makeInfos(transBean.getReqMCM001());//接口调用失败，上送流水信息
				serverStop("请联系大堂经理","订单状态异常", "");
				return;
			}
			//保存订单余额
			transBean.setMoney(Integer.parseInt(moneyBoxQueryOrder.getResponse().getOrderAmount()));
			// 是否为本人身份证
	/*		if(!transBean.getIdCardNo().equals(transBean.getReadIdcard())){
				fail("非本账户身份证，请在身份证退出后重新插入");
				return;
			}*/
			
			transBean.getReqMCM001().setReqBefor("03445");//接口调用前流水信息记录
			// 上送核心检查
			Map<String,String> map = new HashMap<String,String>();
			map.put("idCardNo", transBean.getReadIdcard());
			map.put("custName", transBean.getIdCardName());
			// 获取本人身份信息
			BCK0001ResBean resBean = getBillInfo(map);
			if(resBean == null){
				transBean.getReqMCM001().setIntereturnmsg("查询本人身份信息失败：调用03448接口异常");
				MakeMonitorInfo.makeInfos(transBean.getReqMCM001());//接口调用失败，上送流水信息
				serverStop("请联系大堂经理","","调用接口失败");
				return;
			}
			String resCode = resBean.getHeadBean().getResCode();
			String resMsg = resBean.getHeadBean().getResMsg();
			logger.info("证件信息查询resCode"+resCode);
			logger.info("证件信息查询resMsg"+resMsg);
			if(!"000000".equals(resCode)){//不为000000，有可能是无记录，有可能是直接前置忙，
				transBean.getReqMCM001().setReqAfter(resCode,resMsg);//接口调用成功后记录流水信息
				if(resMsg.indexOf("0018") != -1){
					//如果为0018，则表示是无记录,此处不做处理，下面做处理
				}else{
					MakeMonitorInfo.makeInfos(transBean.getReqMCM001());//接口调用失败，上送流水信息
					serverStop("请联系大堂经理", resMsg,"");
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
			
			transBean.getReqMCM001().setReqBefor("07670");//接口调用前流水信息记录
			
			BCK0008ResBean bck0008resBean = getPoiceInfo(mapPoic);
			resCode = bck0008resBean.getHeadBean().getResCode();
			resMsg = bck0008resBean.getHeadBean().getResMsg();
			logger.info("本人身份核查resCode："+resCode);
			logger.info("本人身份核查resMsg："+resMsg);
			if(!"000000".equals(resCode)){
				transBean.getReqMCM001().setReqAfter(resCode,resMsg);//接口调用成功后记录流水信息
				MakeMonitorInfo.makeInfos(transBean.getReqMCM001());//接口调用失败，上送流水信息
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
				File to_f = new File(tmp+transBean.getIdCardNo()+"_mePoic.jpg");
				FileUtil.copyFileUsingJava7Files(from_f, to_f);
			} catch (Exception e) {
				transBean.getReqMCM001().setIntereturnmsg("获取联网核查结果图片失败：调用03448接口异常");
				MakeMonitorInfo.makeInfos(transBean.getReqMCM001());//接口调用失败，上送流水信息
				serverStop("请联系大堂经理", "","获取联网核查结果图片失败");
				logger.error(e);
				return;
			}
			
			//在证件信息查询，判断是否有用户信息
			String desc = resBean.getBody().getSVR_RET_DESC().trim();
			String svrno = resBean.getBody().getSVR_RET_CODE().trim();
			if("0018".equals(svrno)){
				//核心返回码为0018，代表在核心没有该用户的信息，需要创建，跳转到新建用户信息
				//这里分本人办理，和代理人办理，
				//该页面是代理人办理中的第一次插入身份证（先查被办理人的身份证），，也 是本人办理中的唯一一次插入身份证
				//当是本人办理的时候，如果为0018，则直接跳转新建用户信息
				//当是代理人办理时第一次插入身份证时：如果也是0018，鉴于还需要插入代理人的身份证，故，在此加一个标志位，以便后面直接跳转
				//customer_identification为是否新注册用户表示
				transBean.getImportMap().put("customer_identification", "yes");
				if("yes".equals(transBean.getImportMap().get("agent_persion"))){
					//这里是代理人办理
					//这是被代理人没有客户信息的标识
					transBean.getImportMap().put("agent_persion_result", "yes");
					clearTimeText();
					openPanel(new MoneyBoxInputAgentIdcardPanel(transBean));
					return;
				}else if ("no".equals(transBean.getImportMap().get("agent_persion"))){
					//这里是本人办理
					//直接跳转到新建用户页面
					clearTimeText();
					openPanel(new MoneyBoxRiskCustPromPanel(transBean));
					return;
				}
				
			}else if("0000".equals(svrno)){
				//核心返回码为0000，代表在核心有该用户的信息，不需要创建，直接跳转到选择开户
				String fileName = resBean.getBody().getFILE_NAME();
				// 下载文件
				FtpFileUtils.downloadFileByFTPClient(Property.FTP_IP_DOWN, Integer.parseInt(Property.FTP_PORT_DOWN), Property.FTP_USER_DOWN, Property.FTP_PWD_DOWN, fileName, Property.FTP_LOCAL_PATH + fileName);
				List<String> custInfoList = null;
				try {
					custInfoList = UtilPreFile.preCustInfo(Property.FTP_LOCAL_PATH+fileName);
				} catch (Exception e) {
					logger.error(e);
					transBean.getReqMCM001().setIntereturnmsg("解析身份证文件失败");
					MakeMonitorInfo.makeInfos(transBean.getReqMCM001());//接口调用失败，上送流水信息
					serverStop("请联系大堂经理","","身份证文件解析失败");
					return;
				}
				transBean.setCustNo(custInfoList.get(0).trim());
				transBean.setIdCardName(custInfoList.get(2).trim());
				transBean.setIdCardNo(custInfoList.get(5).trim());
				//客户基本信息查询
				Map<String, String> map2 = new HashMap<String, String>();
				map2.put("RESOLVE_TYPE", "2");//识别方式
				map2.put("ECIF_CUST_NO", "");//客户编号
				map2.put("PARTY_NAME", custInfoList.get(2).trim());	//客户姓名
				map2.put("CERT_TYPE", "10100");		//证件类型
				map2.put("CERT_NO",custInfoList.get(5).trim());			//证件号码
				map2.put("ACCT_NO","");			//客户识别账号
				
				transBean.getReqMCM001().setReqBefor("04422");//接口调用前流水信息记录
				
				BCK04422ResBean resInfo=inter04422(map2);
				if(resInfo==null){
					logger.info("调用反洗钱失败");
					transBean.getReqMCM001().setIntereturnmsg("查询客户基本信息失败：调用04422接口异常");
					MakeMonitorInfo.makeInfos(transBean.getReqMCM001());//接口调用失败，上送流水信息
					serverStop("请联系大堂经理","","反洗钱查询失败");
					return;
				}
				String resCode_new = resInfo.getHeadBean().getResCode();
				String errMsg = resInfo.getHeadBean().getResMsg();
				//接口调用成功后记录流水信息
				transBean.getReqMCM001().setReqAfter(resCode_new, errMsg);
				if(errMsg.indexOf("084R4000") != -1){
					logger.info("客户不存在");
					clearTimeText();
					openPanel(new MoneyBoxRiskCustPromPanel(transBean));
					
					return;
				}
				if(errMsg.indexOf("800001") != -1){
					logger.info("服务方系统忙，请核实流水后再试");
					MakeMonitorInfo.makeInfos(transBean.getReqMCM001());//接口调用失败，上送流水信息
					serverStop("请联系大堂经理", "","服务方系统忙，请核实流水后再试");
					return;
				}
				if(errMsg.indexOf("084R3250") != -1){
					logger.info("CERT_TYPE字段长度越界");
					MakeMonitorInfo.makeInfos(transBean.getReqMCM001());//接口调用失败，上送流水信息
					serverStop("请联系大堂经理", "","CERT_TYPE字段长度越界");
					return;
				}
				if(!"000000".equals(resCode_new)){
					logger.info("客户基本信息查询失败,调用反洗钱失败");
					MakeMonitorInfo.makeInfos(transBean.getReqMCM001());//接口调用失败，上送流水信息
					serverStop("请联系大堂经理","","客户基本信息查询失败:"+resMsg );
					return;
				}
				String nineMsg = resInfo.getBody().getNINE_ITEM_COMPLETED_FLAG();	//九项是否完整  1完整2不完整 3无关
				String riskQua = resInfo.getBody().getINFO_QUALITY_FLAG().charAt(11)+"";			//客户信息质量标志    第十二位：是否反洗钱高风险客户
				logger.info("客户基本信息查询resCode:"+resCode_new+"resMsg:"+errMsg);
				if("1".equals(riskQua)){
					clearTimeText();
					openPanel(new MoneyBoxRiskCustPromPanel(transBean));
					return;
				}else if("0".equals(nineMsg) && "0".equals(riskQua)){
					clearTimeText();
					openPanel(new MoneyBoxNotCompleteCustPromPanel(transBean));
					
					return;
				}
				// 成功处理
				success(transBean);
			}else{
				//其他未知
				transBean.getReqMCM001().setIntereturnmsg("身份核查失败：调用03448接口异常");
				MakeMonitorInfo.makeInfos(transBean.getReqMCM001());//接口调用失败，上送流水信息
				serverStop("请联系大堂经理","调用接口失败"+desc, "");
				return;
			}
				}
		}).start();
	}
	
	/**
	 * 获取钱柜订单数据
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public MoneyBoxQueryOrderReqBean getMoneyBoxQueryOrder(Map<String,String> map)  {
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
            bw.write(sc.moneyBoxQueryOrder(map) + "\n");
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
			reqXs.alias("Root", MoneyBoxQueryOrderReqBean.class);
			MoneyBoxQueryOrderReqBean boxQueryOrderReqBean = (MoneyBoxQueryOrderReqBean)reqXs.fromXML(retMsg);
			logger.info(boxQueryOrderReqBean);
			logger.info("CLIENT retMsg:" + retMsg);
			return boxQueryOrderReqBean;
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
	/**
	 * 客户基本信息查询
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static BCK04422ResBean inter04422(Map<String, String> map2){
		String retMsg = "";
		SocketClient sc = new SocketClient();
		Socket socket=null;
		InputStream is=null;
		 OutputStream os=null;
		 try{
			 socket = sc.createSocket();
	            //构建IO  
	            is = socket.getInputStream();  
	            os = socket.getOutputStream();  
	            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));  
	            //向服务器端发送一条消息  
	            bw.write(sc.BCK_04422(map2) + "\n");  
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
		reqXs.alias("Root", BCK04422ResBean.class);
		BCK04422ResBean bck04422ResBean = (BCK04422ResBean)reqXs.fromXML(retMsg);
		return bck04422ResBean;
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
