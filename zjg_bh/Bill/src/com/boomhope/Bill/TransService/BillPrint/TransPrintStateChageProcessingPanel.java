package com.boomhope.Bill.TransService.BillPrint;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Socket.SocketClient;
import com.boomhope.Bill.TransService.BillPrint.Bean.BillPrintBean;
import com.boomhope.Bill.TransService.BillPrint.Interface.BillProductInfo;
import com.boomhope.Bill.TransService.BillPrint.Interface.ICBankBean;
import com.boomhope.Bill.TransService.BillPrint.Interface.ICSubAccNo;
import com.boomhope.Bill.Util.FtpFileUtils;
import com.boomhope.Bill.Util.Property;
import com.boomhope.Bill.Util.TextFileReader;
import com.boomhope.Bill.monitor.sendInterface.MakeMonitorInfo;
import com.boomhope.tms.message.XStreamNameCoder;
import com.boomhope.tms.message.in.bck.BCK0015ResBean;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * 
 * title:状态变更信息处理中
 * @author ly
 * 2016年11月9日上午10:39:35
 */
public class TransPrintStateChageProcessingPanel extends BaseTransPanelNew {

	private static final long serialVersionUID = 1L;
	
	static Logger logger = Logger.getLogger(TransPrintStateChageProcessingPanel.class);
	Timer checkTimer = null;

	public TransPrintStateChageProcessingPanel(final BillPrintBean transBean) {

		this.billPrintBean = transBean;
		logger.info("进入状态变更信息处理中");
		
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
		

		/* 警告图标 */
		JLabel billImage = new JLabel();
		billImage.setIcon(new ImageIcon("pic/processing.gif"));
		billImage.setIconTextGap(6);
		billImage.setBounds(233, 227, 200, 200);
		this.showJpanel.add(billImage);

		JLabel label = new JLabel("状态变更中，请稍后...");
		label.setHorizontalAlignment(0);
		label.setFont(new Font("微软雅黑", Font.BOLD, 40));
		label.setBounds(0,60,1009,60);
		this.showJpanel.add(label);
		
		

		/* 倒计时进入接口调用 */
		checkTimer = new Timer(500, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				success(transBean);
				stopTimer(checkTimer);
				
			}
		});
		checkTimer.start();
		
	}

	/**
	 * 业务处理
	 * 
	 * @return
	 */
	public void success(BillPrintBean transBean) {
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("ID_NO", transBean.getReadIdcard());
			map.put("OPER_CHOOSE", transBean.getChoose()); //状态变更
			//调用接口
			transBean.getReqMCM001().setReqBefor("03518");
			BCK0015ResBean resBean = getNotPrintBill(map);
			if(resBean == null){
				transBean.getReqMCM001().setIntereturnmsg("调用03518接口异常");
				MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
				serverStop("请联系大堂经理","", "获取银行卡信息异常");
				return;
			}
			String resCode = resBean.getHeadBean().getResCode();
			String resMsg = resBean.getHeadBean().getResMsg();
			logger.error("获取银行卡信息resCode：" + resCode);
			logger.error("获取银行卡信息resMsg：" + resMsg);
			transBean.getReqMCM001().setReqAfter(resCode, resMsg);
			if (!"000000".equals(resCode)) {
				MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
				serverStop("请联系大堂经理",resMsg,"");
				return;
			}
			String fileName = resBean.getBody().getFILE_NAME();
			FtpFileUtils.downloadFileByFTPClient(Property.FTP_IP_DOWN,Integer.parseInt(Property.FTP_PORT_DOWN),Property.FTP_USER_DOWN, Property.FTP_PWD_DOWN, fileName,Property.FTP_LOCAL_PATH + fileName);
			List<BillProductInfo> billProductInfoList = TextFileReader.paddingList(Property.FTP_LOCAL_PATH + fileName, BillProductInfo.class);
			//将从文件中取到的值，依次循环分类（按照银行卡号分类）
			if(billProductInfoList != null && billProductInfoList.size()>0){
				for (BillProductInfo billProductInfo : billProductInfoList) {
					String accNo = billProductInfo.getAccNo().trim();//获取卡号
					String type = billProductInfo.getType().trim();
					/**
					 * 将所有的卡号放在List中，然后判断这个list中是否包含下一个卡号，如果不存在，则直接添加，
					 */
					List<String> idBankCodes = new ArrayList<String>();
					if(bankBeans.size() > 0){ //先判断有没有银行卡信息
						for (ICBankBean icBankBean : bankBeans) {
							idBankCodes.add(icBankBean.getICAccNo());
						}
						if(idBankCodes.contains(accNo)){//如果卡号存在，则增加子账号的实体类
							for (ICBankBean icBankBean : bankBeans) {
								if(icBankBean.getICAccNo().equals(accNo)){
									ICSubAccNo icSubAccNo = copyBean(billProductInfo);
									icBankBean.getSubAccNo().add(icSubAccNo);
								}
							}
						}else{//如果卡号不存在，则新增一个卡号
							ICBankBean bankBean = new ICBankBean();
							bankBean.setICAccNo(accNo);
							bankBean.setType(type);
							bankBean.setTypeStr(type);
							ICSubAccNo icSubAccNo = copyBean(billProductInfo);
							bankBean.getSubAccNo().add(icSubAccNo);
							bankBeans.add(bankBean);
						}
					}else{//当为银行卡信息为空时，
						ICBankBean bankBean = new ICBankBean();
						bankBean.setICAccNo(accNo);
						bankBean.setType(type);
						bankBean.setTypeStr(type);
						ICSubAccNo icSubAccNo = copyBean(billProductInfo);
						bankBean.getSubAccNo().add(icSubAccNo);
						bankBeans.add(bankBean);
					}
					
				}
			}else{
				clearTimeText();
				openPanel(new TransPrintNoBankInfoPanel(transBean));
				return;
			}
		} catch (Exception e) {
			transBean.getReqMCM001().setIntereturnmsg("获取银行卡信息异常");
			MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
			serverStop("请联系大堂经理","","获取银行卡信息异常" );
			logger.error(e);
			return;
		}
		clearTimeText();
		openPanel(new TransPrintBillStateChage(transBean, bankBeans));
		
	}
	private BCK0015ResBean getNotPrintBill(Map<String, String> map) {
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
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os,
					"UTF-8"));
			// 向服务器端发送一条消息
			bw.write(sc.BCK_0015(map) + "\n");
			bw.flush();
			// 读取服务器返回的消息
			BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
			String value = null;
			while ((value = br.readLine()) != null) {
				retMsg += value + "\n";
				if ("</Root>".equals(value)) {
					break;
				}
			}
			logger.info(retMsg);
			XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			reqXs.alias("Root", BCK0015ResBean.class);
			BCK0015ResBean bck0015ResBean = (BCK0015ResBean) reqXs.fromXML(retMsg);
			logger.info(bck0015ResBean);
			logger.info("CLIENT retMsg:" + retMsg);
			return bck0015ResBean;
		} catch (UnknownHostException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
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
			}
		}
		return null;
	}
	private ICSubAccNo copyBean(BillProductInfo billProductInfo){
		ICSubAccNo icSubAccNo = new ICSubAccNo();
		icSubAccNo.setSubAccNo(billProductInfo.getSubAccNo().trim());
		icSubAccNo.setATM(billProductInfo.getATM().trim());
		icSubAccNo.setProductCode(billProductInfo.getProductCode().trim());
		icSubAccNo.setProductName(billProductInfo.getProductName().trim());
		icSubAccNo.setOpenInstNo(billProductInfo.getOpenInstNo().trim());
		icSubAccNo.setOpenInstName(billProductInfo.getOpenInstName().trim());
		icSubAccNo.setCustName(billProductInfo.getCustName().trim());
		icSubAccNo.setOpenDate(billProductInfo.getOpenDate().trim());
		icSubAccNo.setStartIntDate(billProductInfo.getStartIntDate().trim());
		icSubAccNo.setEndIntDate(billProductInfo.getEndIntDate().trim());
		icSubAccNo.setOpenRate(billProductInfo.getOpenRate().trim());
		icSubAccNo.setDepTerm(billProductInfo.getDepTerm().trim());
		icSubAccNo.setItemNo(billProductInfo.getItemNo().trim());
		icSubAccNo.setExchFlag(billProductInfo.getExchFlag().trim());
		icSubAccNo.setDrawCond(billProductInfo.getDrawCond().trim());
		return icSubAccNo;
	}
	
	
}
